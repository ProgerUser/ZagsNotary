package mj.doc.updatenat;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.ACTFORLIST;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.msg.Msg;

public class EditUpdNat {

	@FXML
	private TextField DOC_NUMBER;
	
	@FXML
	private TextField BRN_ACT_ID;

	@FXML
	private TextField CUSID;

	@FXML
	private ComboBox<NATIONALITY> NEW_NAT;

	@FXML
	private TextField SVID_NUMBER;

	@FXML
	private TextField FIO;

	@FXML
	private ComboBox<NATIONALITY> OLD_NAT;

	@FXML
	private TextField SVID_SERIA;

	@FXML
	void FindClient(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(FIO, CUSID, (Stage) CUSID.getScene().getWindow());
	}

	@FXML
	void AddClient(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(FIO, CUSID, (Stage) CUSID.getScene().getWindow(), conn);
	}

	@FXML
	void FindBrn(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Brn(BRN_ACT_ID, (Stage) CUSID.getScene().getWindow());
	}

	@FXML
	void AddBrn(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Brn(BRN_ACT_ID, (Stage) CUSID.getScene().getWindow(), conn);
	}

	/**
	 * Найти акты о рождении
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void ActList(TextField number) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);

			TableView<ACTFORLIST> cusllists = new TableView<ACTFORLIST>();

			TableColumn<ACTFORLIST, Integer> BR_ACT_ID = new TableColumn<>("Номер");

			BR_ACT_ID.setCellValueFactory(new PropertyValueFactory<>("BR_ACT_ID"));

			TableColumn<ACTFORLIST, String> CCUSNAME = new TableColumn<>("ФИО");

			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));

			TableColumn<ACTFORLIST, LocalDate> DCUSBIRTHDAY = new TableColumn<>("Дата рождения");

			DCUSBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("DCUSBIRTHDAY"));

			TableColumn<ACTFORLIST, LocalDateTime> BR_ACT_DATE = new TableColumn<>("Дата создания");

			BR_ACT_DATE.setCellValueFactory(new PropertyValueFactory<>("BR_ACT_DATE"));

			cusllists.getColumns().add(BR_ACT_ID);
			cusllists.getColumns().add(CCUSNAME);
			cusllists.getColumns().add(DCUSBIRTHDAY);
			cusllists.getColumns().add(BR_ACT_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);
			vb.setPadding(new Insets(10, 10, 10, 10));

			BR_ACT_ID.setCellValueFactory(cellData -> cellData.getValue().BR_ACT_IDProperty().asObject());

			CCUSNAME.setCellValueFactory(cellData -> cellData.getValue().CCUSNAMEProperty());

			DCUSBIRTHDAY.setCellValueFactory(cellData -> cellData.getValue().DCUSBIRTHDAYProperty());

			BR_ACT_DATE.setCellValueFactory(cellData -> cellData.getValue().BR_ACT_DATEProperty());

			DCUSBIRTHDAY.setCellFactory(column -> {
				TableCell<ACTFORLIST, LocalDate> cell = new TableCell<ACTFORLIST, LocalDate>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

					@Override
					protected void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(format.format(item));
						}
					}
				};
				return cell;
			});

			BR_ACT_DATE.setCellFactory(column -> {
				TableCell<ACTFORLIST, LocalDateTime> cell = new TableCell<ACTFORLIST, LocalDateTime>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

					@Override
					protected void updateItem(LocalDateTime item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(format.format(item));
						}
					}
				};
				return cell;
			});

			String selectStmt = "select BR_ACT_ID, cus.ccusname, BR_ACT_DATE, cus.dcusbirthday\n"
					+ "  from BRN_BIRTH_ACT t, cus\n" + " where t.br_act_ch = cus.icusnum(+)\n" + "";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ACTFORLIST> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				ACTFORLIST list = new ACTFORLIST();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				DateTimeFormatter formatterDT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				list.setCCUSNAME(rs.getString("CCUSNAME"));
				list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				list.setBR_ACT_DATE((rs.getDate("BR_ACT_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("BR_ACT_DATE")), formatterDT)
						: null);
				list.setBR_ACT_ID(rs.getInt("BR_ACT_ID"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			BR_ACT_ID.setPrefWidth(100);
			CCUSNAME.setPrefWidth(120);
			DCUSBIRTHDAY.setPrefWidth(120);
			BR_ACT_DATE.setPrefWidth(120);

			TableFilter<ACTFORLIST> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите данные из таблицы!");
					} else {
						ACTFORLIST country = cusllists.getSelectionModel().getSelectedItem();
						// name.setText(country.getCCUSNAME());
						number.setText(String.valueOf(country.getBR_ACT_ID()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) CUSID.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список актов о рождении");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	void CusList(TextField num, TextField name) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Integer> ICUSNUM = new TableColumn<>("Номер");
			ICUSNUM.setCellValueFactory(new PropertyValueFactory<>("ICUSNUM"));
			TableColumn<CUS, String> CCUSNAME = new TableColumn<>("ФИО");
			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));
			TableColumn<CUS, LocalDate> DCUSBIRTHDAY = new TableColumn<>("Дата рождения");
			DCUSBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("DCUSBIRTHDAY"));
			cusllists.getColumns().add(ICUSNUM);
			cusllists.getColumns().add(CCUSNAME);
			cusllists.getColumns().add(DCUSBIRTHDAY);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);
			vb.setPadding(new Insets(10, 10, 10, 10));

			ICUSNUM.setCellValueFactory(cellData -> cellData.getValue().ICUSNUMProperty().asObject());
			CCUSNAME.setCellValueFactory(cellData -> cellData.getValue().CCUSNAMEProperty());
			DCUSBIRTHDAY.setCellValueFactory(cellData -> cellData.getValue().DCUSBIRTHDAYProperty());
			DCUSBIRTHDAY.setCellFactory(column -> {
				TableCell<CUS, LocalDate> cell = new TableCell<CUS, LocalDate>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

					@Override
					protected void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(format.format(item));
						}
					}
				};
				return cell;
			});
			String selectStmt = "select * from cus";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS list = new CUS();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				list.setICUSNUM(rs.getInt("ICUSNUM"));
				list.setDCUSOPEN((rs.getDate("DCUSOPEN") != null)
						? LocalDateTime.parse(
								new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DCUSOPEN")), formatterdt)
						: null);
				list.setDCUSEDIT((rs.getDate("DCUSEDIT") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSEDIT")), formatter)
						: null);
				list.setCCUSIDOPEN(rs.getString("CCUSIDOPEN"));
				list.setCCUSNAME(rs.getString("CCUSNAME"));
				list.setCCUSCOUNTRY1(rs.getString("CCUSCOUNTRY1"));
				list.setCCUSNAME_SH(rs.getString("CCUSNAME_SH"));
				list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
				list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
				list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
				list.setCCUSSEX(rs.getInt("CCUSSEX"));
				list.setCCUSPLACE_BIRTH(rs.getString("CCUSPLACE_BIRTH"));
				list.setICUSOTD(rs.getInt("ICUSOTD"));
				list.setCCUS_OK_SM(rs.getString("CCUS_OK_SM"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			ICUSNUM.setPrefWidth(100);
			DCUSBIRTHDAY.setPrefWidth(120);
			DCUSBIRTHDAY.setPrefWidth(120);

			TableFilter<CUS> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите данные из таблицы!");
					} else {
						CUS country = cusllists.getSelectionModel().getSelectedItem();
						num.setText(country.getCCUSNAME());
						name.setText(String.valueOf(country.getICUSNUM()));

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) SVID_NUMBER.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список граждан");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void Save(ActionEvent event) {
		try {

			CallableStatement callStmt = conn.prepareCall("{ call UDPNAT.EditUpdNat(?,?,?,?,?,?,?,?,?) }");

			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setInt(2, upd.getID());

			if (NEW_NAT.getSelectionModel().getSelectedItem() != null) {
				NATIONALITY nat = NEW_NAT.getSelectionModel().getSelectedItem();
				callStmt.setInt(3, nat.getID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}

			if (OLD_NAT.getSelectionModel().getSelectedItem() != null) {
				NATIONALITY nat = OLD_NAT.getSelectionModel().getSelectedItem();
				callStmt.setInt(4, nat.getID());
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}

			if (!BRN_ACT_ID.getText().equals("")) {
				callStmt.setInt(5, Integer.valueOf(BRN_ACT_ID.getText()));
			} else {
				callStmt.setNull(5, java.sql.Types.INTEGER);
			}

			if (!CUSID.getText().equals("")) {
				callStmt.setInt(6, Integer.valueOf(CUSID.getText()));
			} else {
				callStmt.setNull(6, java.sql.Types.INTEGER);
			}
			callStmt.setString(7, SVID_SERIA.getText());
			callStmt.setString(8, SVID_NUMBER.getText());
			callStmt.setString(9, DOC_NUMBER.getText());
			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				// setId(callStmt.getInt(2));
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) CUSID.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void SaveForCompare() {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call UDPNAT.EditUpdNat(?,?,?,?,?,?,?,?,?) }");

			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setInt(2, upd.getID());

			if (NEW_NAT.getSelectionModel().getSelectedItem() != null) {
				NATIONALITY nat = NEW_NAT.getSelectionModel().getSelectedItem();
				callStmt.setInt(3, nat.getID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}

			if (OLD_NAT.getSelectionModel().getSelectedItem() != null) {
				NATIONALITY nat = OLD_NAT.getSelectionModel().getSelectedItem();
				callStmt.setInt(4, nat.getID());
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}

			if (!BRN_ACT_ID.getText().equals("")) {
				callStmt.setInt(5, Integer.valueOf(BRN_ACT_ID.getText()));
			} else {
				callStmt.setNull(5, java.sql.Types.INTEGER);
			}

			if (!CUSID.getText().equals("")) {
				callStmt.setInt(6, Integer.valueOf(CUSID.getText()));
			} else {
				callStmt.setNull(6, java.sql.Types.INTEGER);
			}
			callStmt.setString(7, SVID_SERIA.getText());
			callStmt.setString(8, SVID_NUMBER.getText());
			callStmt.setString(9, DOC_NUMBER.getText());
			callStmt.execute();
			callStmt.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) SVID_NUMBER.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	private void convertCombo(ComboBox<NATIONALITY> cmb) {
		cmb.setConverter(new StringConverter<NATIONALITY>() {
			@Override
			public String toString(NATIONALITY product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public NATIONALITY fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());

			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from NATIONALITY t order by ID\n";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<NATIONALITY> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					NATIONALITY list = new NATIONALITY();
					list.setID(rs.getInt("ID"));
					list.setNAME(rs.getString("NAME"));
					nationals.add(list);
				}
				sqlStatement.close();

				FilteredList<NATIONALITY> filterednationals1 = new FilteredList<NATIONALITY>(nationals);
				FilteredList<NATIONALITY> filterednationals2 = new FilteredList<NATIONALITY>(nationals);
				NEW_NAT.getEditor().textProperty()
						.addListener(new InputFilter<NATIONALITY>(NEW_NAT, filterednationals1, false));
				NEW_NAT.setItems(filterednationals1);
				OLD_NAT.getEditor().textProperty()
						.addListener(new InputFilter<NATIONALITY>(OLD_NAT, filterednationals2, false));
				OLD_NAT.setItems(filterednationals2);

				rs.close();
				convertCombo(NEW_NAT);
				convertCombo(OLD_NAT);
			}

			FIO.setText(upd.getFIO());

			if (upd.getCUSID() != 0) {
				CUSID.setText(String.valueOf(upd.getCUSID()));
			}

			if (upd.getNEW_NAT() != null) {
				for (NATIONALITY nt : NEW_NAT.getItems()) {
					if (nt.getID().equals(upd.getNEW_NAT())) {
						NEW_NAT.getSelectionModel().select(nt);
						break;
					}
				}
			}

			if (upd.getOLD_NAT() != null) {
				for (NATIONALITY nt : OLD_NAT.getItems()) {
					if (nt.getID().equals(upd.getOLD_NAT())) {
						OLD_NAT.getSelectionModel().select(nt);
						break;
					}
				}
			}

			if (upd.getBRN_ACT_ID() != 0) {
				BRN_ACT_ID.setText(String.valueOf(upd.getBRN_ACT_ID()));
			}

			SVID_SERIA.setText(upd.getSVID_SERIA());
			SVID_NUMBER.setText(upd.getSVID_NUMBER());
			DOC_NUMBER.setText(upd.getDOC_NUMBER());
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	UPD_NAT upd;

	public void setConn(Connection conn, UPD_NAT updnm) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.upd = updnm;
	}

	private BooleanProperty Status;

	private IntegerProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Integer value) {
		this.Id.set(value);
	}

	public Integer getId() {
		return this.Id.get();
	}

	public EditUpdNat() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}

}
