package ru.psv.mj.zags.doc.divorce;

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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.util.StringConverter;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.sprav.courts.VCOURTS;
import ru.psv.mj.sprav.zags.SelZags;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.zags.doc.cus.CUS;
import ru.psv.mj.zags.doc.cus.UtilCus;
import ru.psv.mj.zags.doc.mercer.MC_MERCER;

public class AddDivorce {

	@FXML
	private TextField DOC_NUMBER;

	@FXML
	private DatePicker DIVC_ZOSCD2;

	@FXML
	private TextField DIVC_HE_LNAFT;

	@FXML
	private TextField DIVC_SHE_LNAFT;

	@FXML
	private TextField HeFio;

	@FXML
	private DatePicker DIVC_CAD;

	@FXML
	private TextField DIVC_ZOSFIO2;

	@FXML
	private TextField DIVC_NUM;

	@FXML
	private TextField DIVC_TCHNUM;

	@FXML
	private TextField DIVC_ZPLACE;

	@FXML
	private GridPane DIV_A;

	@FXML
	private GridPane DIV_B;

	@FXML
	private ComboBox<String> DIVC_TYPE;

	@FXML
	private TextField SheFio;

	@FXML
	private TextField DIVC_ZOSPRISON;

	@FXML
	private TextField DIVC_SHE;

	@FXML
	private GridPane DIV_V2;

	@FXML
	private TextField DIVC_ZLNAME;

	@FXML
	private GridPane DIV_V1;

	@FXML
	private DatePicker DIVC_DT;

	@FXML
	private DatePicker DIVC_TCHD;

	@FXML
	private TextField DIVC_MC_MERCER;

	@FXML
	private TextField DIVC_ZMNAME;

	@FXML
	private TextField DIVC_Z�NAME;

	@FXML
	private ComboBox<VCOURTS> DIVC_CAN;

	@FXML
	private DatePicker DIVC_ZOSCD;

	@FXML
	private TextField DIVC_SERIA;

	@FXML
	private TextField DIVC_HE_LNBEF;

	@FXML
	private ComboBox<VCOURTS> DIVC_ZOSCN2;

	@FXML
	private TextField DIVC_SHE_LNBEF;

	@FXML
	private TextField DIVC_ZOSFIO;

	@FXML
	private ComboBox<VCOURTS> DIVC_ZOSCN;

	@FXML
	private TextField DIVC_HE;

	// _______________������______________________________

	@FXML
	void FindHe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(HeFio, DIVC_HE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void FindShe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(SheFio, DIVC_SHE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void AddHe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(HeFio, DIVC_HE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void AddShe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(SheFio, DIVC_SHE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void FindMercer(ActionEvent event) {
//		Mercer(DIVC_MC_MERCER);
		UtilCus cus = new UtilCus();
		cus.FindMercer(DIVC_MC_MERCER, (Stage) DIVC_MC_MERCER.getScene().getWindow(), conn);
	}

	void CusList(TextField num, TextField name) {
		try {
			Button Update = new Button();
			Update.setText("�������");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Long> ICUSNUM = new TableColumn<>("�����");
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
			Stage stage = (Stage) DIVC_DT.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("������ �������");
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
	void DIVC_TYPE(ActionEvent event) {
		if (DIVC_TYPE.getValue()
				.equals("���������� ��������� ��������, �� ������� ����� �����, �� ��������� ���������������")) {
			DIV_A.setVisible(true);
			DIV_B.setVisible(false);
			DIV_V1.setVisible(false);
			DIV_V2.setVisible(false);
			// �������� �����
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		} else if (DIVC_TYPE.getValue().equals("������� ���� � ����������� �����")) {
			DIV_A.setVisible(false);
			DIV_B.setVisible(true);
			DIV_V1.setVisible(false);
			DIV_V2.setVisible(false);
			// �������� �����
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		} else if (DIVC_TYPE.getValue()
				.equals("��������� ������ �� �������� � ������� ���� � ��������� ��������� �������������")
				| DIVC_TYPE.getValue()
						.equals("��������� ������ �� �������� � ������� ���� � ��������� ��������������")) {
			DIV_A.setVisible(false);
			DIV_B.setVisible(false);
			DIV_V1.setVisible(true);
			DIV_V2.setVisible(false);
			// �������� �����
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		} else if (DIVC_TYPE.getValue().equals("�������� ���� �� ��������� � ������� �������")) {
			DIV_A.setVisible(false);
			DIV_B.setVisible(false);
			DIV_V1.setVisible(false);
			DIV_V2.setVisible(true);
			// �������� �����
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		}
	}

	Long ZagsId = null;
	boolean IfArchiveNotSelect = false;

	@FXML
	void Save(ActionEvent event) {
		try {
			// �������� �� �����
			{
				PreparedStatement prp = conn.prepareStatement("select zags_id from usr where usr.cusrlogname = user");
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					if (rs.getInt(1) == 5) {
						// <FXML>---------------------------------------
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/ru/psv/mj/sprav/zags/SelZags.fxml"));

						SelZags controller = new SelZags();

						loader.setController(controller);

						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("������� ����");
						stage.initOwner((Stage) DIVC_ZOSFIO.getScene().getWindow());
						stage.setResizable(true);
						stage.initModality(Modality.WINDOW_MODAL);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									if (controller.getStatus()) {
										ZagsId = controller.getZagsId();
										IfArchiveNotSelect = false;
									} else {
										IfArchiveNotSelect = true;
										Msg.Message("�� ������!");
									}
									controller.dbDisconnect();
								} catch (Exception e) {
									DbUtil.Log_Error(e);
								}
							}
						});
						stage.showAndWait();
						// </FXML>---------------------------------------
					}
				}
				prp.close();
				rs.close();
			}
			if (IfArchiveNotSelect == false) {
				CallableStatement callStmt = conn.prepareCall(
						"{ call Divorce.AddDivorce(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				callStmt.setString(2, DIVC_SERIA.getText());
				callStmt.setString(3, DIVC_NUM.getText());
				if (!DIVC_MC_MERCER.getText().equals("")) {
					callStmt.setLong(4, Long.valueOf(DIVC_MC_MERCER.getText()));
				} else {
					callStmt.setNull(4, java.sql.Types.INTEGER);
				}
				if (!DIVC_ZOSPRISON.getText().equals("")) {
					callStmt.setLong(5, Long.valueOf(DIVC_ZOSPRISON.getText()));
				} else {
					callStmt.setNull(5, java.sql.Types.INTEGER);
				}
				callStmt.setString(6, DIVC_ZOSFIO2.getText());
				callStmt.setDate(7,
						(DIVC_ZOSCD2.getValue() != null) ? java.sql.Date.valueOf(DIVC_ZOSCD2.getValue()) : null);
				if (DIVC_ZOSCN2.getSelectionModel().getSelectedItem() != null) {
					callStmt.setLong(8, DIVC_ZOSCN2.getSelectionModel().getSelectedItem().getID());
				} else {
					callStmt.setNull(8, java.sql.Types.INTEGER);
				}
				callStmt.setString(9, DIVC_ZOSFIO.getText());
				callStmt.setDate(10,
						(DIVC_ZOSCD.getValue() != null) ? java.sql.Date.valueOf(DIVC_ZOSCD.getValue()) : null);
				if (DIVC_ZOSCN.getSelectionModel().getSelectedItem() != null) {
					callStmt.setLong(11, DIVC_ZOSCN.getSelectionModel().getSelectedItem().getID());
				} else {
					callStmt.setNull(11, java.sql.Types.INTEGER);
				}
				callStmt.setDate(12, (DIVC_CAD.getValue() != null) ? java.sql.Date.valueOf(DIVC_CAD.getValue()) : null);
				if (DIVC_CAN.getSelectionModel().getSelectedItem() != null) {
					callStmt.setLong(13, DIVC_CAN.getSelectionModel().getSelectedItem().getID());
				} else {
					callStmt.setNull(13, java.sql.Types.INTEGER);
				}
				callStmt.setString(14, DIVC_TCHNUM.getText());
				callStmt.setDate(15,
						(DIVC_TCHD.getValue() != null) ? java.sql.Date.valueOf(DIVC_TCHD.getValue()) : null);
				callStmt.setString(16, DIVC_TYPE.getValue());
				callStmt.setDate(17, (DIVC_DT.getValue() != null) ? java.sql.Date.valueOf(DIVC_DT.getValue()) : null);
				callStmt.setString(18, DIVC_SHE_LNAFT.getText());
				callStmt.setString(19, DIVC_SHE_LNBEF.getText());
				callStmt.setString(20, DIVC_HE_LNAFT.getText());
				callStmt.setString(21, DIVC_HE_LNBEF.getText());
				if (!DIVC_SHE.getText().equals("")) {
					callStmt.setLong(22, Long.valueOf(DIVC_SHE.getText()));
				} else {
					callStmt.setNull(22, java.sql.Types.INTEGER);
				}
				if (!DIVC_HE.getText().equals("")) {
					callStmt.setLong(23, Long.valueOf(DIVC_HE.getText()));
				} else {
					callStmt.setNull(23, java.sql.Types.INTEGER);
				}
				callStmt.setString(24, DIVC_ZPLACE.getText());
				callStmt.setString(25, DIVC_ZMNAME.getText());
				callStmt.setString(26, DIVC_Z�NAME.getText());
				callStmt.setString(27, DIVC_ZLNAME.getText());
				callStmt.registerOutParameter(28, Types.INTEGER);
				callStmt.setString(29, DOC_NUMBER.getText());
				
				if (ZagsId != null) {
					callStmt.setLong(30, ZagsId);
				} else {
					callStmt.setNull(30, java.sql.Types.INTEGER);
				}
				
				callStmt.execute();

				if (callStmt.getString(1) == null) {
					conn.commit();
					setStatus(true);
					setId(callStmt.getLong(28));
					callStmt.close();
					onclose();
				} else {
					conn.rollback();
					setStatus(false);
					Stage stage_ = (Stage) DIVC_HE_LNBEF.getScene().getWindow();
					Msg.MessageBox(callStmt.getString(1), stage_);
					callStmt.close();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) DIVC_HE_LNBEF.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	/**
	 * ����� ���������� �����
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void Mercer(TextField ID) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("�������");
			// Update.setLayoutX(30.0);
			// Update.setLayoutY(450.0);
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<MC_MERCER> cusllists = new TableView<MC_MERCER>();
			TableColumn<MC_MERCER, Long> MERCER_ID = new TableColumn<>("�����");
			MERCER_ID.setCellValueFactory(new PropertyValueFactory<>("MERCER_ID"));

			TableColumn<MC_MERCER, String> HE = new TableColumn<>("��");
			TableColumn<MC_MERCER, String> HeFio = new TableColumn<>("���");
			HeFio.setCellValueFactory(new PropertyValueFactory<>("HeFio"));
			HE.getColumns().add(HeFio);

			TableColumn<MC_MERCER, String> SHE = new TableColumn<>("���");
			TableColumn<MC_MERCER, String> SheFio = new TableColumn<>("���");
			SheFio.setCellValueFactory(new PropertyValueFactory<>("SheFio"));
			SHE.getColumns().add(SheFio);

			TableColumn<MC_MERCER, LocalDateTime> MERCER_DATE = new TableColumn<>("���� ���������");
			MERCER_DATE.setCellValueFactory(new PropertyValueFactory<>("MERCER_DATE"));

			cusllists.getColumns().add(MERCER_ID);
			cusllists.getColumns().add(HE);
			cusllists.getColumns().add(SHE);
			cusllists.getColumns().add(MERCER_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);

			vb.setPadding(new Insets(10, 10, 10, 10));
			/**/
			MERCER_ID.setCellValueFactory(cellData -> cellData.getValue().MERCER_IDProperty().asObject());
			SheFio.setCellValueFactory(cellData -> cellData.getValue().SHEFIOProperty());
			HeFio.setCellValueFactory(cellData -> cellData.getValue().HEFIOProperty());
			MERCER_DATE.setCellValueFactory(cellData -> cellData.getValue().TM$MERCER_DATEProperty());

			MERCER_DATE.setCellFactory(column -> {
				TableCell<MC_MERCER, LocalDateTime> cell = new TableCell<MC_MERCER, LocalDateTime>() {
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
			/* SelData */
			String selectStmt = "select (select g.ccusname from cus g where g.icusnum = t.mercer_he) HeFio,\n"
					+ "       (select g.ccusname from cus g where g.icusnum = t.mercer_she) SheFio,\n" + "       t.*\n"
					+ "  from MC_MERCER t\n";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			ObservableList<MC_MERCER> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				MC_MERCER list = new MC_MERCER();

				list.setSHEFIO(rs.getString("SheFio"));
				list.setHEFIO(rs.getString("HeFio"));
				list.setMERCER_ID(rs.getLong("MERCER_ID"));
				list.setMERCER_HE(rs.getLong("MERCER_HE"));
				list.setMERCER_SHE(rs.getLong("MERCER_SHE"));
				list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
				list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
				list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
				list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
				list.setMERCER_HEAGE(rs.getLong("MERCER_HEAGE"));
				list.setMERCER_SHEAGE(rs.getLong("MERCER_SHEAGE"));
				list.setTM$MERCER_DATE((rs.getDate("MERCER_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("MERCER_DATE")), formatterdt)
						: null);
				list.setMERCER_USR(rs.getString("MERCER_USR"));
				list.setMERCER_ZAGS(rs.getLong("MERCER_ZAGS"));
				list.setMERCER_DIVSHE(rs.getLong("MERCER_DIVSHE"));
				list.setMERCER_DIVHE(rs.getLong("MERCER_DIVHE"));
				list.setMERCER_DSPMT_HE(rs.getString("MERCER_DSPMT_HE"));
				list.setMERCER_NUM(rs.getString("MERCER_NUM"));
				list.setMERCER_SERIA(rs.getString("MERCER_SERIA"));
				list.setMERCER_DIESHE(rs.getLong("MERCER_DIESHE"));
				list.setMERCER_DIEHE(rs.getLong("MERCER_DIEHE"));
				list.setMERCER_OTHER(rs.getString("MERCER_OTHER"));
				list.setMERCER_DSPMT_SHE(rs.getString("MERCER_DSPMT_SHE"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			// Main.autoResizeColumns(cusllists);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			// ICUSNUM.setPrefWidth(100);
			// DCUSBIRTHDAY.setPrefWidth(120);
			// DCUSBIRTHDAY.setPrefWidth(120);

			TableFilter<MC_MERCER> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			/**/
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("�������� ������ �� �������!");
					} else {
						MC_MERCER country = cusllists.getSelectionModel().getSelectedItem();
						// ID.setText(country.getMERCER_ID());
						ID.setText(String.valueOf(country.getMERCER_ID()));

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);
			// secondaryLayout.getChildren().add(cusllists);

			// VBox vbox = new VBox(debtinfo);
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE,
					Control.USE_COMPUTED_SIZE);/* Control.USE_COMPUTED_SIZE */
			Stage stage = (Stage) DIVC_TYPE.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("���� � ���������� �����");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			// Specifies the modality for new window.
			newWindow.initModality(Modality.WINDOW_MODAL);
			// Specifies the owner Window (parent) for new window
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
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

	private void convert_DIVC_ZOSCN2() {
		DIVC_ZOSCN2.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DIVC_ZOSCN2.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void convert_DIVC_ZOSCN() {
		DIVC_ZOSCN.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DIVC_ZOSCN.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void convert_DIVC_CAN() {
		DIVC_CAN.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DIVC_CAN.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	private void initialize() {
		try {

			if (getCusGen() == 1) {
				HeFio.setText(getCusFio());
				DIVC_HE.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);
			} else if (getCusGen() == 2) {
				SheFio.setText(getCusFio());
				DIVC_SHE.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);
			}

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

			dbConnect();
			// DbUtil.Run_Process(conn,getClass().getName());

			// ����
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from VCOURTS");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<VCOURTS> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					VCOURTS list = new VCOURTS();
					list.setCOTDNAME(rs.getString("COTDNAME"));
					list.setID(rs.getLong("ID"));
					list.setABH_NAME(rs.getString("ABH_NAME"));
					list.setNAME_ROD(rs.getString("NAME_ROD"));
					list.setNAME(rs.getString("NAME"));
					list.setAREA_ID(rs.getLong("AREA_ID"));
					list.setOTD(rs.getLong("OTD"));
					list.setIOTDNUM(rs.getLong("IOTDNUM"));
					combolist.add(list);
				}

				stsmt.close();
				rs.close();

				DIVC_CAN.setItems(combolist);
				DIVC_ZOSCN2.setItems(combolist);
				DIVC_ZOSCN.setItems(combolist);

				// ������������ ����, ���� ����
				convert_DIVC_CAN();
				convert_DIVC_ZOSCN2();
				convert_DIVC_ZOSCN();

				rs.close();
			}

			DIVC_TYPE.getItems().addAll(
					"���������� ��������� ��������, �� ������� ����� �����, �� ��������� ���������������",
					"������� ���� � ����������� �����",
					"��������� ������ �� �������� � ������� ���� � ��������� ��������� �������������",
					"��������� ������ �� �������� � ������� ���� � ��������� ��������������",
					"�������� ���� �� ��������� � ������� �������");

			new ConvConst().FormatDatePiker(DIVC_ZOSCD2);
			new ConvConst().FormatDatePiker(DIVC_CAD);
			new ConvConst().FormatDatePiker(DIVC_DT);
			new ConvConst().FormatDatePiker(DIVC_TCHD);
			new ConvConst().FormatDatePiker(DIVC_ZOSCD);

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
	// ���� ������ �� ����������
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

	public AddDivorce() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();

		this.CusId = new SimpleLongProperty();
		this.CusFio = new SimpleStringProperty();
		this.CusGen = new SimpleLongProperty();
	}

}
