package ru.psv.mj.zags.doc.patern;

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
import ru.psv.mj.app.main.Main;
import ru.psv.mj.app.model.ACTFORLIST;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.zags.doc.cus.CUS;
import ru.psv.mj.zags.doc.cus.UtilCus;

public class EditPatern {

	@FXML
	private TextField P�_AFT_LNAME;

	@FXML
	private DatePicker P�_FZ;

	@FXML
	private TextField P�_F_NAME;

	@FXML
	private DatePicker P�_CRDATE;

	@FXML
	private TextField P�_NUMBER;

	@FXML
	private TextField P�_AFT_FNAME;

	@FXML
	private TextField PC_ACT_ID;

	@FXML
	private GridPane PC_B;

	@FXML
	private TextField PC_ZFNAME;

	@FXML
	private TextField P�_CH;

	@FXML
	private GridPane P�_A;

	@FXML
	private TextField PC_ZLNAME;

	@FXML
	private TextField P�_CRNAME;

	@FXML
	private TextField P�_F;

	@FXML
	private TextField P�_M;

	@FXML
	private TextField P�_SERIA;

	@FXML
	private ComboBox<String> P�_TYPE;

	@FXML
	private GridPane PC_V;

	@FXML
	private DatePicker P�_TRZ;

	@FXML
	private TextField P�_CH_NAME;

	@FXML
	private TextField PC_ZPLACE;

	@FXML
	private TextField P�_M_NAME;

	@FXML
	private TextField P�_AFT_MNAME;

	@FXML
	private TextField PC_ZMNAME;


    
    //����� ���� 11.03.2021
    @FXML
    private TitledPane Bef;
    @FXML
    private TitledPane Doc_Num;

    @FXML
    private TextField BEF_LNAME;

    @FXML
    private TextField BEF_FNAME;

    @FXML
    private TextField BEF_MNAME;
    
    @FXML
    private TextField DOC_NUMBER;
    
	// ______________������_____________
	/**
	 * ������� �������
	 * 
	 * @param event
	 */
	@FXML
	void FindChildren(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(P�_CH, P�_CH_NAME, (Stage) P�_CH_NAME.getScene().getWindow());
	}

	/**
	 * ������� �������
	 * 
	 * @param event
	 */
	@FXML
	void AddChildren(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(P�_CH, P�_CH_NAME, (Stage) P�_M_NAME.getScene().getWindow(), conn);
	}

	/**
	 * ��������
	 * 
	 * @param event
	 */
	@FXML
	void AddMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(P�_M, P�_M_NAME, (Stage) P�_M_NAME.getScene().getWindow(), conn);
	}

	/**
	 * �������� ����
	 * 
	 * @param event
	 */
	@FXML
	void AddFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(P�_F, P�_F_NAME, (Stage) P�_M_NAME.getScene().getWindow(), conn);
	}

	/**
	 * ������� ��� � ��������
	 * 
	 * @param event
	 */
	@FXML
	void FindAct(ActionEvent event) {
		//ActList(PC_ACT_ID);
				UtilCus cus = new UtilCus();
				cus.Find_Brn(PC_ACT_ID, (Stage) PC_ACT_ID.getScene().getWindow());
	}

	@FXML
	void AddAct(ActionEvent event) {
		//ActList(PC_ACT_ID);
		UtilCus cus = new UtilCus();
		cus.Add_Brn(PC_ACT_ID, (Stage) PC_ACT_ID.getScene().getWindow(), conn);
	}
	
	/**
	 * ������� ����
	 * 
	 * @param event
	 */
	@FXML
	void FindMother(ActionEvent event) {
		
		UtilCus cus = new UtilCus();
		cus.Find_Cus(P�_M, P�_M_NAME, (Stage) P�_M_NAME.getScene().getWindow());
		
//		CusList(P�_M, P�_M_NAME);
	}

	/**
	 * ������� ����
	 * 
	 * @param event
	 */
	@FXML
	void FindFather(ActionEvent event) {
		
		UtilCus cus = new UtilCus();
		cus.Find_Cus(P�_F, P�_F_NAME, (Stage) P�_F_NAME.getScene().getWindow());
//		CusList(P�_F, P�_F_NAME);
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

			TableColumn<ACTFORLIST, Long> BR_ACT_ID = new TableColumn<>("�����");

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
				list.setBR_ACT_ID(rs.getLong("BR_ACT_ID"));

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
			Stage stage = (Stage) P�_AFT_LNAME.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("������ ����� � ��������");
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

	/**
	 * ����� ����������
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void CusList0(TextField number, TextField name) {
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
						name.setText(country.getCCUSNAME());
						number.setText(String.valueOf(country.getICUSNUM()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) P�_AFT_LNAME.getScene().getWindow();

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

	/**
	 * ��� ��������� ��� ���. ���������
	 * 
	 * @param event
	 */
	@FXML
	void P�_TYPE(ActionEvent event) {
		if (P�_TYPE.getValue().equals("���������� ��������� ���������, �� �������������� ����� � �����")) {
			PC_B.setVisible(false);
			PC_V.setVisible(false);
			P�_A.setVisible(true);
			// �������� �����
			P�_FZ.setValue(null);
			P�_TRZ.setValue(null);
			P�_CRDATE.setValue(null);
			P�_CRNAME.setText("");
		} else if (P�_TYPE.getValue().equals("������� ���� �� ������������ ���������")
				| P�_TYPE.getValue().equals("������� ���� �� ������������ ����� ���������")) {
			PC_B.setVisible(false);
			PC_V.setVisible(true);
			P�_A.setVisible(false);
			// �������� �����
			P�_FZ.setValue(null);
			P�_TRZ.setValue(null);
			P�_CRDATE.setValue(null);
			P�_CRNAME.setText("");
		} else if (P�_TYPE.getValue().equals("��������� ���� �������")) {
			PC_B.setVisible(true);
			PC_V.setVisible(false);
			P�_A.setVisible(false);
			// �������� �����
			P�_FZ.setValue(null);
			P�_TRZ.setValue(null);
			P�_CRDATE.setValue(null);
			P�_CRNAME.setText("");
		}
	}

	/**
	 * ���������
	 * 
	 * @param event
	 */
	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ call PATERN.EditPatern(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setLong(2, pat_cert.getPC_ID());
			callStmt.setString(3, P�_NUMBER.getText());
			callStmt.setString(4, P�_SERIA.getText());
			callStmt.setDate(5, (P�_CRDATE.getValue() != null) ? java.sql.Date.valueOf(P�_CRDATE.getValue()) : null);
			callStmt.setString(6, P�_CRNAME.getText());
			callStmt.setDate(7, (P�_FZ.getValue() != null) ? java.sql.Date.valueOf(P�_FZ.getValue()) : null);
			callStmt.setDate(8, (P�_TRZ.getValue() != null) ? java.sql.Date.valueOf(P�_TRZ.getValue()) : null);
			callStmt.setString(9, P�_TYPE.getValue());
			if (!P�_M.getText().equals("")) {
				callStmt.setLong(10, Long.valueOf(P�_M.getText()));
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
			if (!P�_F.getText().equals("")) {
				callStmt.setLong(11, Long.valueOf(P�_F.getText()));
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			if (!P�_CH.getText().equals("")) {
				callStmt.setLong(12, Long.valueOf(P�_CH.getText()));
			} else {
				callStmt.setNull(12, java.sql.Types.INTEGER);
			}
			callStmt.setString(13, P�_AFT_MNAME.getText());
			callStmt.setString(14, P�_AFT_FNAME.getText());
			callStmt.setString(15, P�_AFT_LNAME.getText());
			if (!PC_ACT_ID.getText().equals("")) {
				callStmt.setLong(16, Long.valueOf(PC_ACT_ID.getText()));
			} else {
				callStmt.setNull(16, java.sql.Types.INTEGER);
			}
			callStmt.setString(17, PC_ZPLACE.getText());
			callStmt.setString(18, PC_ZLNAME.getText());
			callStmt.setString(19, PC_ZFNAME.getText());
			callStmt.setString(20, PC_ZMNAME.getText());

			callStmt.setString(21, BEF_LNAME.getText());
			callStmt.setString(22, BEF_FNAME.getText());
			callStmt.setString(23, BEF_MNAME.getText());
			callStmt.setString(24, DOC_NUMBER.getText());
			
			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				// setId(callStmt.getLong(20));
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) P�_SERIA.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	void SaveCompare() {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ call PATERN.EditPatern(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setLong(2, pat_cert.getPC_ID());
			callStmt.setString(3, P�_NUMBER.getText());
			callStmt.setString(4, P�_SERIA.getText());
			callStmt.setDate(5, (P�_CRDATE.getValue() != null) ? java.sql.Date.valueOf(P�_CRDATE.getValue()) : null);
			callStmt.setString(6, P�_CRNAME.getText());
			callStmt.setDate(7, (P�_FZ.getValue() != null) ? java.sql.Date.valueOf(P�_FZ.getValue()) : null);
			callStmt.setDate(8, (P�_TRZ.getValue() != null) ? java.sql.Date.valueOf(P�_TRZ.getValue()) : null);
			callStmt.setString(9, P�_TYPE.getValue());
			if (!P�_M.getText().equals("")) {
				callStmt.setLong(10, Long.valueOf(P�_M.getText()));
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
			if (!P�_F.getText().equals("")) {
				callStmt.setLong(11, Long.valueOf(P�_F.getText()));
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			if (!P�_CH.getText().equals("")) {
				callStmt.setLong(12, Long.valueOf(P�_CH.getText()));
			} else {
				callStmt.setNull(12, java.sql.Types.INTEGER);
			}
			callStmt.setString(13, P�_AFT_MNAME.getText());
			callStmt.setString(14, P�_AFT_FNAME.getText());
			callStmt.setString(15, P�_AFT_LNAME.getText());
			if (!PC_ACT_ID.getText().equals("")) {
				callStmt.setLong(16, Long.valueOf(PC_ACT_ID.getText()));
			} else {
				callStmt.setNull(16, java.sql.Types.INTEGER);
			}
			callStmt.setString(17, PC_ZPLACE.getText());
			callStmt.setString(18, PC_ZLNAME.getText());
			callStmt.setString(19, PC_ZFNAME.getText());
			callStmt.setString(20, PC_ZMNAME.getText());
			
			callStmt.setString(21, BEF_LNAME.getText());
			callStmt.setString(22, BEF_FNAME.getText());
			callStmt.setString(23, BEF_MNAME.getText());
			callStmt.setString(24, DOC_NUMBER.getText());
			
			callStmt.execute();
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��� ��������
	 */
	void onclose() {
		Stage stage = (Stage) P�_M.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * ������
	 * 
	 * @param event
	 */
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

	/**
	 * �������������
	 */
	@FXML
	private void initialize() {
		try {
			Bef.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Doc_Num.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
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

			// �������� � �������
			P�_CH_NAME.setText(pat_cert.getCHILDFIO());
			if (pat_cert.getP�_CH() != 0) {
				P�_CH.setText(String.valueOf(pat_cert.getP�_CH()));
			}
			// �������� ����� ������������ ���������
			P�_AFT_LNAME.setText(pat_cert.getP�_AFT_LNAME());
			P�_AFT_FNAME.setText(pat_cert.getP�_AFT_FNAME());
			P�_AFT_MNAME.setText(pat_cert.getP�_AFT_MNAME());
			// ��� � ��������
			if (pat_cert.getPC_ACT_ID() != 0) {
				PC_ACT_ID.setText(String.valueOf(pat_cert.getPC_ACT_ID()));
			}
			// �������� � ������
			P�_M_NAME.setText(pat_cert.getMOTHERFIO());
			if (pat_cert.getP�_M() != 0) {
				P�_M.setText(String.valueOf(pat_cert.getP�_M()));
			}
			// �������� �� ����
			P�_F_NAME.setText(pat_cert.getFATHERFIO());
			if (pat_cert.getP�_F() != 0) {
				P�_F.setText(String.valueOf(pat_cert.getP�_F()));
			}
			// ��� ��������� ��� ���. ���������
			P�_TYPE.getItems().addAll("���������� ��������� ���������, �� �������������� ����� � �����",
					"��������� ���� �������", "������� ���� �� ������������ ���������",
					"������� ���� �� ������������ ����� ���������");
			// ��������� ��� ��������������� ����������� ������������ ���������
			if (pat_cert.getP�_TYPE() != null) {
				if (pat_cert.getP�_TYPE().equals("A")) {
					P�_TYPE.getSelectionModel()
							.select("���������� ��������� ���������, �� �������������� ����� � �����");
					P�_A.setVisible(true);

					P�_TRZ.setValue(pat_cert.getP�_TRZ());

				} else if (pat_cert.getP�_TYPE().equals("B")) {
					P�_TYPE.getSelectionModel().select("��������� ���� �������");
					PC_B.setVisible(true);

					P�_FZ.setValue(pat_cert.getP�_FZ());

				} else if (pat_cert.getP�_TYPE().equals("V1")) {

					P�_TYPE.getSelectionModel().select("������� ���� �� ������������ ���������");
					PC_V.setVisible(true);

					P�_CRDATE.setValue(pat_cert.getP�_CRDATE());
					P�_CRNAME.setText(pat_cert.getP�_CRNAME());
				} else if (pat_cert.getP�_TYPE().equals("V2")) {
					P�_TYPE.getSelectionModel().select("������� ���� �� ������������ ����� ���������");
					PC_V.setVisible(true);

					P�_CRDATE.setValue(pat_cert.getP�_CRDATE());
					P�_CRNAME.setText(pat_cert.getP�_CRNAME());
				}
			}
			// ���������
			PC_ZLNAME.setText(pat_cert.getPC_ZLNAME());
			PC_ZFNAME.setText(pat_cert.getPC_ZFNAME());
			PC_ZMNAME.setText(pat_cert.getPC_ZMNAME());
			PC_ZPLACE.setText(pat_cert.getPC_ZPLACE());
			// ������ �������������
			P�_SERIA.setText(pat_cert.getP�_SERIA());
			P�_NUMBER.setText(pat_cert.getP�_NUMBER());
			
			BEF_LNAME.setText(pat_cert.getBEF_LNAME());
			BEF_FNAME.setText(pat_cert.getBEF_FNAME());
			BEF_MNAME.setText(pat_cert.getBEF_MNAME());
			DOC_NUMBER.setText(pat_cert.getDOC_NUMBER());
			
			new ConvConst().FormatDatePiker(P�_FZ);
			new ConvConst().FormatDatePiker(P�_CRDATE);
			new ConvConst().FormatDatePiker(P�_TRZ);
			
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ������ ������������� � ������
	 */
	private PATERN_CERT pat_cert;

	public void setConn(Connection conn, PATERN_CERT ptc) throws SQLException {
		this.conn = conn;this.conn.setAutoCommit(false);
		this.pat_cert = ptc;
	}

	/**
	 * ������ ����������
	 */
	private Connection conn;

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

	public EditPatern() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}

}
