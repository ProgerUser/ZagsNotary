package mj.doc.patern;

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
import mj.app.model.ACTFORLIST;
import mj.app.model.Connect;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;

public class AddPatern {

	@FXML
	private TextField PС_AFT_LNAME;

	@FXML
	private DatePicker PС_FZ;

	@FXML
	private TextField PС_F_NAME;

	@FXML
	private DatePicker PС_CRDATE;

	@FXML
	private TextField PС_NUMBER;

	@FXML
	private TextField PС_AFT_FNAME;

	@FXML
	private TextField PC_ACT_ID;

	@FXML
	private GridPane PC_B;

	@FXML
	private TextField PC_ZFNAME;

	@FXML
	private TextField PС_CH;

	@FXML
	private GridPane PС_A;

	@FXML
	private TextField PC_ZLNAME;

	@FXML
	private TextField PС_CRNAME;

	@FXML
	private TextField PС_F;

	@FXML
	private TextField PС_M;

	@FXML
	private TextField PС_SERIA;

	@FXML
	private ComboBox<String> PС_TYPE;

	@FXML
	private GridPane PC_V;

	@FXML
	private DatePicker PС_TRZ;

	@FXML
	private TextField PС_CH_NAME;

	@FXML
	private TextField PC_ZPLACE;

	@FXML
	private TextField PС_M_NAME;

	@FXML
	private TextField PС_AFT_MNAME;

	@FXML
	private TextField PC_ZMNAME;

    //Новые поля 11.03.2021
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
    
	// ______________Методы_____________
	/**
	 * Выбрать ребенка
	 * 
	 * @param event
	 */
	@FXML
	void FindChildren(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(PС_CH_NAME, PС_CH, (Stage) PС_CH_NAME.getScene().getWindow());
	}

	/**
	 * Выбрать ребенка
	 * 
	 * @param event
	 */
	@FXML
	void AddChildren(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(PС_CH_NAME, PС_CH, (Stage) PС_M_NAME.getScene().getWindow(), conn);
	}

	/**
	 * Выбрать акт о рождении
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
	 * Выбрать мать
	 * 
	 * @param event
	 */
	@FXML
	void FindMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(PС_M_NAME, PС_M, (Stage) PС_CH_NAME.getScene().getWindow());
	}

	/**
	 * Добавить
	 * 
	 * @param event
	 */
	@FXML
	void AddMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(PС_M_NAME, PС_M, (Stage) PС_M_NAME.getScene().getWindow(), conn);
	}

	/**
	 * Выбрать Отца
	 * 
	 * @param event
	 */
	@FXML
	void FindFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(PС_F_NAME, PС_F, (Stage) PС_CH_NAME.getScene().getWindow());
	}

	/**
	 * Добавить Отца
	 * 
	 * @param event
	 */
	@FXML
	void AddFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(PС_F_NAME, PС_F, (Stage) PС_M_NAME.getScene().getWindow(), conn);
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
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);

			TableView<ACTFORLIST> cusllists = new TableView<ACTFORLIST>();

			TableColumn<ACTFORLIST, Long> BR_ACT_ID = new TableColumn<>("Номер");

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
			Stage stage = (Stage) PС_AFT_LNAME.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список актов о рождении");
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
	 * Найти гражданина
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void CusList(TextField number, TextField name) {
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
						name.setText(country.getCCUSNAME());
						number.setText(String.valueOf(country.getICUSNUM()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) PС_AFT_LNAME.getScene().getWindow();

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

	/**
	 * Тип основания для уст. отцовства
	 * 
	 * @param event
	 */
	@FXML
	void PС_TYPE(ActionEvent event) {
		if (PС_TYPE.getValue().equals("Совместное заявление родителей, не состоящихмежду собой в браке")) {
			PC_B.setVisible(false);
			PC_V.setVisible(false);
			PС_A.setVisible(true);
			// значения полей
			PС_FZ.setValue(null);
			PС_TRZ.setValue(null);
			PС_CRDATE.setValue(null);
			PС_CRNAME.setText("");
		} else if (PС_TYPE.getValue().equals("Решение суда об установлении отцовства")
				| PС_TYPE.getValue().equals("Решение суда об установления факта признания")) {
			PC_B.setVisible(false);
			PC_V.setVisible(true);
			PС_A.setVisible(false);
			// значения полей
			PС_FZ.setValue(null);
			PС_TRZ.setValue(null);
			PС_CRDATE.setValue(null);
			PС_CRNAME.setText("");
		} else if (PС_TYPE.getValue().equals("Заявление отца ребенка")) {
			PC_B.setVisible(true);
			PC_V.setVisible(false);
			PС_A.setVisible(false);
			// значения полей
			PС_FZ.setValue(null);
			PС_TRZ.setValue(null);
			PС_CRDATE.setValue(null);
			PС_CRNAME.setText("");
		}
	}

	/**
	 * Сохранить
	 * 
	 * @param event
	 */
	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ call PATERN.AddPatern(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, PС_NUMBER.getText());
			callStmt.setString(3, PС_SERIA.getText());
			callStmt.setDate(4, (PС_CRDATE.getValue() != null) ? java.sql.Date.valueOf(PС_CRDATE.getValue()) : null);
			callStmt.setString(5, PС_CRNAME.getText());
			callStmt.setDate(6, (PС_FZ.getValue() != null) ? java.sql.Date.valueOf(PС_FZ.getValue()) : null);
			callStmt.setDate(7, (PС_TRZ.getValue() != null) ? java.sql.Date.valueOf(PС_TRZ.getValue()) : null);
			callStmt.setString(8, PС_TYPE.getValue());
			if (!PС_M.getText().equals("")) {
				callStmt.setLong(9, Long.valueOf(PС_M.getText()));
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			if (!PС_F.getText().equals("")) {
				callStmt.setLong(10, Long.valueOf(PС_F.getText()));
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
			if (!PС_CH.getText().equals("")) {
				callStmt.setLong(11, Long.valueOf(PС_CH.getText()));
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			callStmt.setString(12, PС_AFT_MNAME.getText());
			callStmt.setString(13, PС_AFT_FNAME.getText());
			callStmt.setString(14, PС_AFT_LNAME.getText());
			if (!PC_ACT_ID.getText().equals("")) {
				callStmt.setLong(15, Long.valueOf(PC_ACT_ID.getText()));
			} else {
				callStmt.setNull(15, java.sql.Types.INTEGER);
			}
			callStmt.setString(16, PC_ZPLACE.getText());
			callStmt.setString(17, PC_ZLNAME.getText());
			callStmt.setString(18, PC_ZFNAME.getText());
			callStmt.setString(19, PC_ZMNAME.getText());
			
			callStmt.registerOutParameter(20, Types.INTEGER);
			
			callStmt.setString(21, BEF_LNAME.getText());
			callStmt.setString(22, BEF_FNAME.getText());
			callStmt.setString(23, BEF_MNAME.getText());
			callStmt.setString(24, DOC_NUMBER.getText());
			
			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				setId(callStmt.getLong(20));
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) PС_SERIA.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * При закрытии
	 */
	void onclose() {
		Stage stage = (Stage) PС_M.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Отмена
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
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			Bef.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Doc_Num.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			
			PС_CH_NAME.setText(getCusFio());
			PС_CH.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);

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

			if (conn == null) {
				dbConnect();
				DbUtil.Run_Process(conn,getClass().getName());
			}
			/**
			 * Тип основания для уст. отцовства
			 */
			PС_TYPE.getItems().addAll("Совместное заявление родителей, не состоящихмежду собой в браке",
					"Заявление отца ребенка", "Решение суда об установлении отцовства",
					"Решение суда об установления факта признания");
			
			new ConvConst().FormatDatePiker(PС_FZ);
			new ConvConst().FormatDatePiker(PС_CRDATE);
			new ConvConst().FormatDatePiker(PС_TRZ);
			
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Строка соединения
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program",getClass().getName());
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрыть
	 */
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

	//
	// если открыт от гражданина
	//
	private LongProperty CusId;
	private StringProperty CusFio;

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
	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
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

	public AddPatern() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
		this.CusId = new SimpleLongProperty();
		this.CusFio = new SimpleStringProperty();
	}

}
