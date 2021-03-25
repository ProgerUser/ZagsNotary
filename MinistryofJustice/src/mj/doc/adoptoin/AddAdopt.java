package mj.doc.adoptoin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.ACTFORLIST;
import mj.app.model.Connect;
import mj.courts.VCOURTS;
import mj.dbutil.DBUtil;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.widgets.KeyBoard;

public class AddAdopt {

    @FXML
    private ComboBox<VCOURTS> GR_COURT;
    @FXML
    private GridPane GR_ADOPT_B;
    @FXML
    private DatePicker GR_COURT_DATE;
    @FXML
    private ComboBox<String> GR_ADOPT;
    @FXML
    private GridPane GR_ADOPT_A;
    
	@FXML
	private TextField OLD_LASTNAME_AB;
	@FXML
	private TextField OLD_FIRSTNAME_AB;
	@FXML
	private TextField OLD_MIDDLNAME_AB;
	@FXML
	private TextField NEW_LASTNAME_AB;
	@FXML
	private TextField NEW_FIRSTNAME_AB;
	@FXML
	private TextField NEW_MIDDLNAME_AB;
	@FXML
	private TextField DOC_NUMBER;
	@FXML
	private TextField CUSID_F;
	@FXML
	private TextField NEW_FIRSTNAME;
	@FXML
	private DatePicker OLD_BRTH;
	@FXML
	private TextField OLD_LASTNAME;
	@FXML
	private TextField CUSID_M;
	@FXML
	private TextField AdMotherFio;
	@FXML
	private TextField NEW_LASTNAME;
	@FXML
	private TextField ZAP_NUMBER;
	@FXML
	private TextField AdFatherFio;
	@FXML
	private TextField ChildrenFio;
	@FXML
	private DatePicker ZAP_DATE;
	@FXML
	private DatePicker NEW_BRTH;
	@FXML
	private TextField OLD_FIRSTNAME;
	@FXML
	private TextField CUSID_F_AD;
	@FXML
	private TextField FatherFio;
	@FXML
	private TextField NEW_MIDDLNAME;
	@FXML
	private TextField SVID_SERIA;
	@FXML
	private TextField CUSID_CH;
	@FXML
	private TextField BRN_CITY;
	@FXML
	private TextField BRN_AREA;
	@FXML
	private TextField BRN_OBL_RESP;
	@FXML
	private TextField MotherFio;
	@FXML
	private TextField SVID_NOMER;
	@FXML
	private TextField BRNACT;
	@FXML
	private TextField ZAP_ISPOLKOM_RESH;
	@FXML
	private TextField CUSID_M_AD;
	@FXML
	private CheckBox ADOPT_PARENTS;

	@FXML
	private TextField ZAP_SOVET_DEP_TRUD;

	@FXML
	private TextField OLD_MIDDLNAME;

	@FXML
	void GR_ADOPT(ActionEvent event) {
		try {
			if (GR_ADOPT.getValue().equals("������� ����")) {
				GR_ADOPT_A.setVisible(false);
				GR_ADOPT_B.setVisible(true);
				ZAP_ISPOLKOM_RESH.setText("");
				ZAP_SOVET_DEP_TRUD.setText("");
				ZAP_DATE.setValue(null);
				ZAP_NUMBER.setText("");
			} else if (GR_ADOPT.getValue().equals("������� ���������")) {
				GR_ADOPT_B.setVisible(false);
				GR_ADOPT_A.setVisible(true);
				GR_COURT_DATE.setValue(null);
				GR_COURT.setValue(null);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
    
	@FXML
	void FindChildren(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(ChildrenFio, CUSID_CH, (Stage) CUSID_CH.getScene().getWindow());
	}

	@FXML
	void AddChildren(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(ChildrenFio, CUSID_CH, (Stage) CUSID_CH.getScene().getWindow(), conn);
	}

	@FXML
	void FindChBrn(ActionEvent event) {
//		ActList(BRNACT);
		UtilCus cus = new UtilCus();
		cus.Find_Brn(BRNACT, (Stage) BRNACT.getScene().getWindow());
	}

	@FXML
	void FindFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(FatherFio, CUSID_F, (Stage) CUSID_CH.getScene().getWindow());
	}

	@FXML
	void AddFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(FatherFio, CUSID_F, (Stage) CUSID_CH.getScene().getWindow(), conn);
	}

	@FXML
	void FindMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(MotherFio, CUSID_M, (Stage) CUSID_CH.getScene().getWindow());
	}

	@FXML
	void AddMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(MotherFio, CUSID_M, (Stage) CUSID_CH.getScene().getWindow(), conn);
	}

	@FXML
	void FindAdFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(AdFatherFio, CUSID_F_AD, (Stage) CUSID_CH.getScene().getWindow());
	}

	@FXML
	void AddAdFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(AdFatherFio, CUSID_F_AD, (Stage) CUSID_CH.getScene().getWindow(), conn);
	}

	@FXML
	void AddAdMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(AdMotherFio, CUSID_M_AD, (Stage) CUSID_CH.getScene().getWindow(), conn);
	}

	@FXML
	void FindAdMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(AdMotherFio, CUSID_M_AD, (Stage) CUSID_CH.getScene().getWindow());
	}

	/**
	 * ����� ���� � ��������
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void ActList(TextField number) {
		try {
			Button Update = new Button();
			Update.setText("�������");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);

			TableView<ACTFORLIST> cusllists = new TableView<ACTFORLIST>();

			TableColumn<ACTFORLIST, Integer> BR_ACT_ID = new TableColumn<>("�����");

			BR_ACT_ID.setCellValueFactory(new PropertyValueFactory<>("BR_ACT_ID"));

			TableColumn<ACTFORLIST, String> CCUSNAME = new TableColumn<>("���");

			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));

			TableColumn<ACTFORLIST, LocalDate> DCUSBIRTHDAY = new TableColumn<>("���� ��������");

			DCUSBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("DCUSBIRTHDAY"));

			TableColumn<ACTFORLIST, LocalDateTime> BR_ACT_DATE = new TableColumn<>("���� ��������");

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
						Msg.Message("�������� ������ �� �������!");
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
			Stage stage = (Stage) CUSID_F.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("������ ����� � ��������");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void CusList2(TextField num, TextField name) {
		try {
			Button Update = new Button();
			Update.setText("�������");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Integer> ICUSNUM = new TableColumn<>("�����");
			ICUSNUM.setCellValueFactory(new PropertyValueFactory<>("ICUSNUM"));
			TableColumn<CUS, String> CCUSNAME = new TableColumn<>("���");
			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));
			TableColumn<CUS, LocalDate> DCUSBIRTHDAY = new TableColumn<>("���� ��������");
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
						Msg.Message("�������� ������ �� �������!");
					} else {
						CUS country = cusllists.getSelectionModel().getSelectedItem();
						num.setText(country.getCCUSNAME());
						name.setText(String.valueOf(country.getICUSNUM()));

						OLD_LASTNAME.setText(country.getCCUSLAST_NAME());
						OLD_FIRSTNAME.setText(country.getCCUSFIRST_NAME());
						OLD_MIDDLNAME.setText(country.getCCUSMIDDLE_NAME());
						OLD_BRTH.setValue(country.getDCUSBIRTHDAY());

						NEW_LASTNAME.setText(country.getCCUSLAST_NAME());
						NEW_FIRSTNAME.setText(country.getCCUSFIRST_NAME());
						NEW_MIDDLNAME.setText(country.getCCUSMIDDLE_NAME());
						NEW_BRTH.setValue(country.getDCUSBIRTHDAY());

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) CUSID_F.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("������ �������");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void CusList(TextField num, TextField name) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("�������");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Integer> ICUSNUM = new TableColumn<>("�����");
			ICUSNUM.setCellValueFactory(new PropertyValueFactory<>("ICUSNUM"));
			TableColumn<CUS, String> CCUSNAME = new TableColumn<>("���");
			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));
			TableColumn<CUS, LocalDate> DCUSBIRTHDAY = new TableColumn<>("���� ��������");
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
						Msg.Message("�������� ������ �� �������!");
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
			Stage stage = (Stage) CUSID_F.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("������ �������");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ call ADOPT.AddAdopt(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.registerOutParameter(2, Types.INTEGER);
			callStmt.setString(3, OLD_LASTNAME.getText());
			callStmt.setString(4, OLD_FIRSTNAME.getText());
			callStmt.setString(5, OLD_MIDDLNAME.getText());
			callStmt.setString(6, NEW_LASTNAME.getText());
			callStmt.setString(7, NEW_FIRSTNAME.getText());
			callStmt.setString(8, NEW_MIDDLNAME.getText());
			if (!CUSID_CH.getText().equals("")) {
				callStmt.setInt(9, Integer.valueOf(CUSID_CH.getText()));
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			if (!CUSID_M.getText().equals("")) {
				callStmt.setInt(10, Integer.valueOf(CUSID_M.getText()));
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
			if (!CUSID_F.getText().equals("")) {
				callStmt.setInt(11, Integer.valueOf(CUSID_F.getText()));
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			if (!BRNACT.getText().equals("")) {
				callStmt.setInt(12, Integer.valueOf(BRNACT.getText()));
			} else {
				callStmt.setNull(12, java.sql.Types.INTEGER);
			}
			callStmt.setString(13, SVID_SERIA.getText());
			callStmt.setString(14, SVID_NOMER.getText());
			if (!CUSID_M_AD.getText().equals("")) {
				callStmt.setInt(15, Integer.valueOf(CUSID_M_AD.getText()));
			} else {
				callStmt.setNull(15, java.sql.Types.INTEGER);
			}
			if (!CUSID_F_AD.getText().equals("")) {
				callStmt.setInt(16, Integer.valueOf(CUSID_F_AD.getText()));
			} else {
				callStmt.setNull(16, java.sql.Types.INTEGER);
			}
			callStmt.setString(17, (ADOPT_PARENTS.isSelected()) ? "Y" : "N");
			callStmt.setString(18, ZAP_ISPOLKOM_RESH.getText());
			callStmt.setString(19, ZAP_SOVET_DEP_TRUD.getText());
			callStmt.setDate(20, (ZAP_DATE.getValue() != null) ? java.sql.Date.valueOf(ZAP_DATE.getValue()) : null);
			callStmt.setString(21, ZAP_NUMBER.getText());
			callStmt.setDate(22, (NEW_BRTH.getValue() != null) ? java.sql.Date.valueOf(NEW_BRTH.getValue()) : null);
			callStmt.setDate(23, (OLD_BRTH.getValue() != null) ? java.sql.Date.valueOf(OLD_BRTH.getValue()) : null);

			callStmt.setString(24, BRN_CITY.getText());
			callStmt.setString(25, BRN_AREA.getText());
			callStmt.setString(26, BRN_OBL_RESP.getText());
			callStmt.setString(27, DOC_NUMBER.getText());
			
			callStmt.setString(28, OLD_LASTNAME_AB.getText());
			callStmt.setString(29, OLD_FIRSTNAME_AB.getText());
			callStmt.setString(30, OLD_MIDDLNAME_AB.getText());
			callStmt.setString(31, NEW_LASTNAME_AB.getText());
			callStmt.setString(32, NEW_FIRSTNAME_AB.getText());
			callStmt.setString(33, NEW_MIDDLNAME_AB.getText());
			
			// ��������� ������ �� �����������
			callStmt.setString(34, (GR_ADOPT.getValue().equals("������� ����") ? "B" : "A"));
			// ������� ���� ����
			callStmt.setDate(35,
					(GR_COURT_DATE.getValue() != null) ? java.sql.Date.valueOf(GR_COURT_DATE.getValue()) : null);
			// ������� ���� ����
			if (GR_COURT.getValue() != null) {
				callStmt.setInt(36, GR_COURT.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(36, java.sql.Types.INTEGER);
			}

			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				setId(callStmt.getInt(2));
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) OLD_FIRSTNAME.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) CUSID_F.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void OpenKey() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) OLD_MIDDLNAME.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/widgets/KeyBoard.fxml"));

			KeyBoard controller = new KeyBoard();
			loader.setController(controller);
			controller.setTextField(OLD_MIDDLNAME.getScene());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("��������� ����������");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {

				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	private ScrollPane MainScroll;

	@FXML
	private TitledPane Pane1;

	@FXML
	private TitledPane Pane2;

	@FXML
	private TitledPane Pane3;

	@FXML
	private TitledPane Pane4;

	@FXML
	private TitledPane Pane5;
	@FXML
	private TitledPane Pane6;
	@FXML
	private TitledPane Pane7;
	@FXML
	private TitledPane Pane8;
	@FXML
	private TitledPane Pane9;
	@FXML
	private TitledPane Pane10;
	@FXML
	private TitledPane Pane11;

	private void convert_GR_COURT() {
		GR_COURT.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : null;
			}

			@Override
			public VCOURTS fromString(final String string) {
				return GR_COURT.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	
	@FXML
	private void initialize() {
		try {
			
			GR_ADOPT.getItems().addAll("������� ���������","������� ����");
			
			ChildrenFio.setText(getCusFio());
			//
			OLD_LASTNAME.setText(getCusLname());
			OLD_FIRSTNAME.setText(getCusFname());
			OLD_MIDDLNAME.setText(getCusMname());
			OLD_BRTH.setValue(getCusBrdate() != null ? getCusBrdate() : null);
			//
			NEW_LASTNAME.setText(getCusLname());
			NEW_FIRSTNAME.setText(getCusFname());
			NEW_MIDDLNAME.setText(getCusMname());
			
			NEW_BRTH.setValue(getCusBrdate() != null ? getCusBrdate() : null);
			//
			CUSID_CH.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);

			
			Pane1.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane2.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane3.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane4.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane5.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane6.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane7.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane8.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane9.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane10.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane11.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));

			if (conn == null) {
				dbConnect();
				DBUtil.RunProcess(conn);
			}
			
			// ����
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from VCOURTS");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<VCOURTS> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					VCOURTS list = new VCOURTS();
					list.setCOTDNAME(rs.getString("COTDNAME"));
					list.setID(rs.getInt("ID"));
					list.setABH_NAME(rs.getString("ABH_NAME"));
					list.setNAME_ROD(rs.getString("NAME_ROD"));
					list.setNAME(rs.getString("NAME"));
					list.setAREA_ID(rs.getInt("AREA_ID"));
					list.setOTD(rs.getInt("OTD"));
					list.setIOTDNUM(rs.getInt("IOTDNUM"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				GR_COURT.setItems(combolist);
				//������������ ����, ���� ����
				convert_GR_COURT();
			}
			
			new ConvConst().FormatDatePiker(GR_COURT_DATE);
			new ConvConst().FormatDatePiker(OLD_BRTH);
			new ConvConst().FormatDatePiker(ZAP_DATE);
			new ConvConst().FormatDatePiker(NEW_BRTH);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddAdoption");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
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

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
	}

	public Integer getId() {
		return this.Id.get();
	}

	//
	// ���� ������ �� ����������
	//
	private IntegerProperty CusId;
	private StringProperty CusFio;
	
	private StringProperty CusLname;
	private StringProperty CusFname;
	private StringProperty CusMname;
	private SimpleObjectProperty<LocalDate> CusBrdate;

	public void setCusBrdate(LocalDate value) {
		this.CusBrdate.set(value);
	}
	
	public LocalDate getCusBrdate() {
		return CusBrdate.get();
	}
	
	public void setCusLname(String value) {
		this.CusLname.set(value);
	}
	public void setCusFname(String value) {
		this.CusFname.set(value);
	}
	public void setCusMname(String value) {
		this.CusMname.set(value);
	}
	
	public String getCusLname() {
		return this.CusLname.get();
	}
	public String getCusFname() {
		return this.CusFname.get();
	}
	public String getCusMname() {
		return this.CusMname.get();
	}
	
	public void setCusId(Integer value) {
		this.CusId.set(value);
	}

	public void setCusFio(String value) {
		this.CusFio.set(value);
	}

	public Integer getCusId() {
		return this.CusId.get();
	}

	public String getCusFio() {
		return this.CusFio.get();
	}

	//
	// ---------------------------------------
	//
	
	public AddAdopt() {
		Main.logger = Logger.getLogger(getClass());
		
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
		
		this.CusBrdate = new SimpleObjectProperty<>();
		
		this.CusId = new SimpleIntegerProperty();
		this.CusFio = new SimpleStringProperty();
		this.CusLname = new SimpleStringProperty();
		this.CusFname = new SimpleStringProperty();
		this.CusMname = new SimpleStringProperty();
		
	}
}
