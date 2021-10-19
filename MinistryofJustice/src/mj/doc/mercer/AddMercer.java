package mj.doc.mercer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.doc.death.DEATH_CERT;
import mj.doc.divorce.DIVORCE_CERT;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;

public class AddMercer {

	@FXML
	private GridPane SheTypeB;
	@FXML
	private TextField MERCER_NUM;
	@FXML
	private TextField MERCER_SHEAGE;
	@FXML
	private TextField MERCER_OTHER;
	@FXML
	private TextField HeFio;
	@FXML
	private TextField MERCER_HE_LNAFT;
	@FXML
	private TextField MERCER_DIVHE;
	@FXML
	private TextField MERCER_HE;
	@FXML
	private GridPane SheTypeA;
	@FXML
	private TextField MERCER_DIESHE;
	@FXML
	private TextField MERCER_SERIA;
	@FXML
	private ComboBox<String> MERCER_DSPMT_HE;
	@FXML
	private TextField MERCER_DIVSHE;
	@FXML
	private TextField MERCER_HE_LNBEF;
	@FXML
	private TextField SheFio;
	@FXML
	private TextField MERCER_SHE_LNBEF;
	@FXML
	private GridPane HeTypeA;
	@FXML
	private ComboBox<String> MERCER_DSPMT_SHE;
	@FXML
	private GridPane HeTypeB;
	@FXML
	private TextField MERCER_SHE_LNBAFT;
	@FXML
	private TextField MERCER_DIEHE;
	@FXML
	private TextField MERCER_HEAGE;
	@FXML
	private TextField MERCER_SHE;
	
	@FXML
	private TextField DOC_NUMBER;
	@FXML
	private DatePicker MC_DATE;
    
	@FXML
	void FindHe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(HeFio, MERCER_HE, (Stage) MERCER_HE.getScene().getWindow());
	}

	@FXML
	void FindShe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(SheFio, MERCER_SHE, (Stage) MERCER_HE.getScene().getWindow());
	}

	@FXML
	void AddHe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(HeFio, MERCER_HE, (Stage) MERCER_HE.getScene().getWindow(), conn);
	}

	@FXML
	void AddShe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(SheFio, MERCER_SHE, (Stage) MERCER_HE.getScene().getWindow(), conn);
	}

	@FXML
	void MERCER_DSPMT_HE(ActionEvent event) {
		if (MERCER_DSPMT_HE.getValue().equals("Свидетельство о расторжении брака")) {
			HeTypeA.setVisible(true);
			HeTypeB.setVisible(false);
			// почистить поля
			MERCER_DIVHE.setText("");
		} else if (MERCER_DSPMT_HE.getValue().equals("Свидетельство о смерти")) {
			HeTypeB.setVisible(true);
			HeTypeA.setVisible(false);
			// почистить поля
			MERCER_DIEHE.setText("");
		}
	}

	@FXML
	void FindHeDivorce(ActionEvent event) {
//		DivorceList(MERCER_DIVHE);
		UtilCus cus = new UtilCus();
		cus.FindDivorce(MERCER_DIVHE, (Stage) MERCER_DIVHE.getScene().getWindow(), conn);
	}

	@FXML
	void FindHeDeath(ActionEvent event) {
//		DeathList(MERCER_DIEHE);
		UtilCus cus = new UtilCus();
		cus.FindDeath(MERCER_DIEHE, (Stage) MERCER_DIEHE.getScene().getWindow(), conn);
	}

	@FXML
	void MERCER_DSPMT_SHE(ActionEvent event) {
		if (MERCER_DSPMT_SHE.getValue().equals("Свидетельство о расторжении брака")) {
			SheTypeA.setVisible(true);
			SheTypeB.setVisible(false);
			// почистить поля
			MERCER_DIVSHE.setText("");
		} else if (MERCER_DSPMT_SHE.getValue().equals("Свидетельство о смерти")) {
			SheTypeB.setVisible(true);
			SheTypeA.setVisible(false);
			// почистить поля
			MERCER_DIESHE.setText("");
		}
	}

	@FXML
	void FindSheDivorce(ActionEvent event) {
//		DivorceList(MERCER_DIVSHE);
		UtilCus cus = new UtilCus();
		cus.FindDivorce(MERCER_DIVSHE, (Stage) MERCER_DIVSHE.getScene().getWindow(), conn);
	}

	@FXML
	void FindSheDeath(ActionEvent event) {
//		DeathList(MERCER_DIESHE);
		UtilCus cus = new UtilCus();
		cus.FindDeath(MERCER_DIESHE, (Stage) MERCER_DIESHE.getScene().getWindow(), conn);
	}

	void DeathList(TextField number) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<DEATH_CERT> cusllists = new TableView<DEATH_CERT>();
			TableColumn<DEATH_CERT, Long> DC_ID = new TableColumn<>("Номер");
			DC_ID.setCellValueFactory(new PropertyValueFactory<>("DC_ID"));
			TableColumn<DEATH_CERT, String> DieFio = new TableColumn<>("ФИО");
			DieFio.setCellValueFactory(new PropertyValueFactory<>("DieFio"));
			TableColumn<DEATH_CERT, LocalDate> DC_DD = new TableColumn<>("Дата смерти");
			DC_DD.setCellValueFactory(new PropertyValueFactory<>("DC_DD"));
			cusllists.getColumns().add(DC_ID);
			cusllists.getColumns().add(DieFio);
			cusllists.getColumns().add(DC_DD);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);
			vb.setPadding(new Insets(10, 10, 10, 10));

			DC_ID.setCellValueFactory(cellData -> cellData.getValue().DC_IDProperty().asObject());
			DieFio.setCellValueFactory(cellData -> cellData.getValue().DFIOProperty());
			DC_DD.setCellValueFactory(cellData -> cellData.getValue().DC_DDProperty());

			DC_DD.setCellFactory(column -> {
				TableCell<DEATH_CERT, LocalDate> cell = new TableCell<DEATH_CERT, LocalDate>() {
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
			String selectStmt = "select t.*, (select c.ccusname from CUS c where c.icusnum = t.dc_cus) DFIO \n"
					+ "  from DEATH_CERT t";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DEATH_CERT> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				DEATH_CERT list = new DEATH_CERT();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				//DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				list.setDFIO(rs.getString("DFIO"));
				list.setDC_ID(rs.getLong("DC_ID"));
				list.setDC_CUS(rs.getLong("DC_CUS"));
				list.setDC_DD((rs.getDate("DC_DD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_DD")), formatter)
						: null);
				list.setDC_DPL(rs.getString("DC_DPL"));
				list.setDC_CD(rs.getString("DC_CD"));
				list.setDC_FNUM(rs.getString("DC_FNUM"));
				list.setDC_FD((rs.getDate("DC_FD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_FD")), formatter)
						: null);
				list.setDC_FTYPE(rs.getString("DC_FTYPE"));
				list.setDC_FMON(rs.getString("DC_FMON"));
				list.setDC_RCNAME(rs.getLong("DC_RCNAME"));
				list.setDC_NRNAME(rs.getString("DC_NRNAME"));
				list.setDC_LLOC(rs.getString("DC_LLOC"));
				list.setDC_ZTP(rs.getString("DC_ZTP"));
				list.setDC_FADFIRST_NAME(rs.getString("DC_FADFIRST_NAME"));
				list.setDC_FADLAST_NAME(rs.getString("DC_FADLAST_NAME"));
				list.setDC_FADMIDDLE_NAME(rs.getString("DC_FADMIDDLE_NAME"));
				list.setDC_FADLOCATION(rs.getString("DC_FADLOCATION"));
				list.setDC_FADORG_NAME(rs.getString("DC_FADORG_NAME"));
				list.setDC_FADREG_ADR(rs.getString("DC_FADREG_ADR"));
				list.setDC_SERIA(rs.getString("DC_SERIA"));
				list.setDC_NUMBER(rs.getString("DC_NUMBER"));
				list.setDC_USR(rs.getString("DC_USR"));
				/*list.setTM$DC_OPEN((rs.getDate("TM$DC_OPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DC_OPEN")), formatterdt) : null);
						*/
				list.setDC_ZAGS(rs.getLong("DC_ZAGS"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			DC_ID.setPrefWidth(100);
			DieFio.setPrefWidth(120);
			DC_DD.setPrefWidth(120);

			TableFilter<DEATH_CERT> CUSFilter = TableFilter.forTableView(cusllists).apply();
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
						DEATH_CERT country = cusllists.getSelectionModel().getSelectedItem();
						// name.setText(country.getCCUSNAME());
						number.setText(String.valueOf(country.getDC_ID()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) SheFio.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Свидетельства о смерти");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void DivorceList(TextField number) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<DIVORCE_CERT> cusllists = new TableView<DIVORCE_CERT>();
			TableColumn<DIVORCE_CERT, Long> DIVC_ID = new TableColumn<>("Номер");
			DIVC_ID.setCellValueFactory(new PropertyValueFactory<>("DIVC_ID"));
			TableColumn<DIVORCE_CERT, String> SheFio = new TableColumn<>("Ее ФИО");
			SheFio.setCellValueFactory(new PropertyValueFactory<>("SheFio"));
			TableColumn<DIVORCE_CERT, String> HeFio = new TableColumn<>("Его ФИО");
			HeFio.setCellValueFactory(new PropertyValueFactory<>("HeFio"));
			TableColumn<DIVORCE_CERT, LocalDateTime> DIVC_DATE = new TableColumn<>("Дата документа");
			DIVC_DATE.setCellValueFactory(new PropertyValueFactory<>("DIVC_DATE"));
			cusllists.getColumns().add(DIVC_ID);
			cusllists.getColumns().add(SheFio);
			cusllists.getColumns().add(HeFio);
			cusllists.getColumns().add(DIVC_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);
			vb.setPadding(new Insets(10, 10, 10, 10));

			DIVC_ID.setCellValueFactory(cellData -> cellData.getValue().DIVC_IDProperty().asObject());
			SheFio.setCellValueFactory(cellData -> cellData.getValue().SHEFIOProperty());
			HeFio.setCellValueFactory(cellData -> cellData.getValue().HEFIOProperty());
			DIVC_DATE.setCellValueFactory(cellData -> cellData.getValue().TM$DIVC_DATEProperty());

			DIVC_DATE.setCellFactory(column -> {
				TableCell<DIVORCE_CERT, LocalDateTime> cell = new TableCell<DIVORCE_CERT, LocalDateTime>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

			String selectStmt = "select (select g.ccusname from cus g where g.icusnum = t.divc_he) HeFio,\r\n"
					+ "       (select g.ccusname from cus g where g.icusnum = t.divc_she) SheFio,\r\n"
					+ "       t.*\r\n" + "  from DIVORCE_CERT t";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DIVORCE_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				DIVORCE_CERT list = new DIVORCE_CERT();

				list.setHEFIO(rs.getString("HeFio"));
				list.setSHEFIO(rs.getString("SheFio"));
				list.setDIVC_ID(rs.getLong("DIVC_ID"));
				list.setDIVC_HE(rs.getLong("DIVC_HE"));
				list.setDIVC_SHE(rs.getLong("DIVC_SHE"));
				list.setDIVC_HE_LNBEF(rs.getString("DIVC_HE_LNBEF"));
				list.setDIVC_HE_LNAFT(rs.getString("DIVC_HE_LNAFT"));
				list.setDIVC_SHE_LNBEF(rs.getString("DIVC_SHE_LNBEF"));
				list.setDIVC_SHE_LNAFT(rs.getString("DIVC_SHE_LNAFT"));
				list.setTM$DIVC_DATE((rs.getDate("DIVC_DATE") != null) ? LocalDateTime
						.parse(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DIVC_DATE")), formatterdt)
						: null);
				list.setDIVC_DT((rs.getDate("DIVC_DT") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_DT")), formatter)
						: null);
				list.setDIVC_USR(rs.getString("DIVC_USR"));
				list.setDIVC_TYPE(rs.getString("DIVC_TYPE"));
				list.setDIVC_TCHD((rs.getDate("DIVC_TCHD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_TCHD")), formatter)
						: null);
				list.setDIVC_TCHNUM(rs.getString("DIVC_TCHNUM"));
				list.setDIVC_CAN(rs.getLong("DIVC_CAN"));
				list.setDIVC_CAD((rs.getDate("DIVC_CAD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_CAD")), formatter)
						: null);
				list.setDIVC_ZOSCN(rs.getLong("DIVC_ZOSCN"));
				list.setDIVC_ZOSCD((rs.getDate("DIVC_ZOSCD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD")), formatter) : null);
				list.setDIVC_ZOSFIO(rs.getString("DIVC_ZOSFIO"));
				list.setDIVC_ZOSCN2(rs.getLong("DIVC_ZOSCN2"));
				list.setDIVC_ZOSCD2((rs.getDate("DIVC_ZOSCD2") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD2")), formatter) : null);
				list.setDIVC_ZOSFIO2(rs.getString("DIVC_ZOSFIO2"));
				list.setDIVC_ZOSPRISON(rs.getLong("DIVC_ZOSPRISON"));
				list.setDIVC_MC_MERCER(rs.getLong("DIVC_MC_MERCER"));
				list.setDIVC_NUM(rs.getString("DIVC_NUM"));
				list.setDIVC_SERIA(rs.getString("DIVC_SERIA"));
				list.setDIVC_ZAGS(rs.getLong("DIVC_ZAGS"));
				list.setDIVC_ZMNAME(rs.getString("DIVC_ZMNAME"));
				list.setDIVC_ZLNAME(rs.getString("DIVC_ZLNAME"));
				list.setDIVC_ZPLACE(rs.getString("DIVC_ZPLACE"));
				list.setDIVC_ZАNAME(rs.getString("DIVC_ZАNAME"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(dlist);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			DIVC_ID.setPrefWidth(100);
			SheFio.setPrefWidth(120);
			HeFio.setPrefWidth(120);
			DIVC_DATE.setPrefWidth(120);

			TableFilter<DIVORCE_CERT> CUSFilter = TableFilter.forTableView(cusllists).apply();
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
						DIVORCE_CERT country = cusllists.getSelectionModel().getSelectedItem();
						// num.setText(country.getCCUSNAME());
						number.setText(String.valueOf(country.getDIVC_ID()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) MERCER_HE.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Свидетельство о расторжении брака");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void CusList(TextField num, TextField name) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Long> ICUSNUM = new TableColumn<>("Номер");
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
				CUS cus = new CUS();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				String DCUSBIRTHDAYt = new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY"));
				cus.setICUSNUM(rs.getLong("ICUSNUM"));
				cus.setCCUSNAME(rs.getString("CCUSNAME"));
				cus.setDCUSBIRTHDAY(LocalDate.parse(DCUSBIRTHDAYt, formatter));
				cuslist.add(cus);
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
			Stage stage = (Stage) SheFio.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список граждан");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ call Mercer.AddMercer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.registerOutParameter(2, Types.INTEGER);
			callStmt.setString(3, MERCER_OTHER.getText());
			if (!MERCER_DIEHE.getText().equals("")) {
				callStmt.setLong(4, Long.valueOf(MERCER_DIEHE.getText()));
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}
			if (!MERCER_DIESHE.getText().equals("")) {
				callStmt.setLong(5, Long.valueOf(MERCER_DIESHE.getText()));
			} else {
				callStmt.setNull(5, java.sql.Types.INTEGER);
			}
			callStmt.setString(6, MERCER_SERIA.getText());
			callStmt.setString(7, MERCER_NUM.getText());
			callStmt.setString(8, MERCER_DSPMT_HE.getValue());
			if (!MERCER_DIVHE.getText().equals("")) {
				callStmt.setLong(9, Long.valueOf(MERCER_DIVHE.getText()));
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			if (!MERCER_DIVSHE.getText().equals("")) {
				callStmt.setLong(10, Long.valueOf(MERCER_DIVSHE.getText()));
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
			if (!MERCER_SHEAGE.getText().equals("")) {
				callStmt.setLong(11, Long.valueOf(MERCER_SHEAGE.getText()));
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			if (!MERCER_HEAGE.getText().equals("")) {
				callStmt.setLong(12, Long.valueOf(MERCER_HEAGE.getText()));
			} else {
				callStmt.setNull(12, java.sql.Types.INTEGER);
			}
			callStmt.setString(13, MERCER_SHE_LNBAFT.getText());
			callStmt.setString(14, MERCER_SHE_LNBEF.getText());
			callStmt.setString(15, MERCER_HE_LNAFT.getText());
			callStmt.setString(16, MERCER_HE_LNBEF.getText());
			if (!MERCER_SHE.getText().equals("")) {
				callStmt.setLong(17, Long.valueOf(MERCER_SHE.getText()));
			} else {
				callStmt.setNull(17, java.sql.Types.INTEGER);
			}
			if (!MERCER_HE.getText().equals("")) {
				callStmt.setLong(18, Long.valueOf(MERCER_HE.getText()));
			} else {
				callStmt.setNull(18, java.sql.Types.INTEGER);
			}
			callStmt.setString(19, MERCER_DSPMT_SHE.getValue());
			callStmt.setDate(20, (MC_DATE.getValue() != null) ? java.sql.Date.valueOf(MC_DATE.getValue()) : null);
			callStmt.setString(21, DOC_NUMBER.getText());
			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				setId(callStmt.getLong(2));
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) HeTypeB.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) MERCER_DSPMT_HE.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	private ScrollPane MainScroll;

	@FXML
	private TitledPane ShePane;

	@FXML
	private TitledPane Pane6;

	@FXML
	private TitledPane Pane3;

	@FXML
	private TitledPane Pane5;

	@FXML
	private TitledPane Pane4;

	@FXML
	private TitledPane HePane;

	@FXML
	private void initialize() {
		try {
			new ConvConst().FormatDatePiker(MC_DATE);
			if (getCusGen() == 1) {
				HeFio.setText(getCusFio());
				MERCER_HE.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);
			} else if (getCusGen() == 2) {
				SheFio.setText(getCusFio());
				MERCER_SHE.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);
			}

			
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			ShePane.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			HePane.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane4.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane5.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane3.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane6.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));

			MERCER_DSPMT_HE.getItems().addAll("Свидетельство о расторжении брака", "Свидетельство о смерти");
			MERCER_DSPMT_SHE.getItems().addAll("Свидетельство о расторжении брака", "Свидетельство о смерти");

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	private BooleanProperty Status;

	private LongProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Long value) {
		this.Id.set(value);
	}

	public Long getId() {
		return this.Id.get();
	}

	//
	// если открыт от гражданина
	//
	private LongProperty CusId;
	private LongProperty CusGen;
	private StringProperty CusFio;

	public void setCusGen(Long value) {
		this.CusGen.set(value);
	}
	
	public Long getCusGen() {
		return this.CusGen.get();
	}
	
	public void setCusId(Long value) {
		this.CusId.set(value);
	}

	public void setCusFio(String value) {
		this.CusFio.set(value);
	}

	public Long getCusId() {
		return this.CusId.get();
	}

	public String getCusFio() {
		return this.CusFio.get();
	}

	//
	// ---------------------------------------
	//
	
	public AddMercer() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
		this.CusId = new SimpleLongProperty();
		this.CusFio = new SimpleStringProperty();
		this.CusGen = new SimpleLongProperty();
	}

}
