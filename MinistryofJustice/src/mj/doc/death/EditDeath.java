package mj.doc.death;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import mj.dbutil.DBUtil;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.msg.Msg;
import mj.util.ConvConst;

public class EditDeath {

	@FXML
	private TextField DC_NRNAME;
	
	/**
	 * Наименование суда
	 */
	@FXML
	private TextField DOC_NUMBER;

	/**
	 * Решение суда об установлении
	 */
	@FXML
	private GridPane D_B;

	/**
	 * Документ установленной формы о смерти
	 */
	@FXML
	private GridPane D_A;

	/**
	 * Последнее место жительства
	 */
	@FXML
	private TextField DC_LLOC;

	/**
	 * Документ о факте смерти лица, необоснованно репрессированного
	 */
	@FXML
	private GridPane D_V;

	/**
	 * Дата смерти
	 */
	@FXML
	private DatePicker DC_DD;

	/**
	 * A-Док. подтвержд. факт смерти-Док. установл. формы о смерти Дата
	 */
	@FXML
	private DatePicker DC_FD;

	/**
	 * Причина смерти
	 */
	@FXML
	private TextField DC_DPL;

	/**
	 * Физик или юрик
	 */
	@FXML
	private GridPane FIZIK;

	/**
	 * Место жительства
	 */
	@FXML
	private TextField DC_FADLOCATION;

	/**
	 * Отчество, если физик
	 */
	@FXML
	private TextField DC_FADMIDDLE_NAME;

	/**
	 * Физик или юрик
	 */
	@FXML
	private ComboBox<String> DC_FTYPE;

	/**
	 * Документ подтверждающий факт смерти тип
	 */
	@FXML
	private ComboBox<String> DC_ZTP;

	/**
	 * наименование юр. организации
	 */
	@FXML
	private TextField DC_FADORG_NAME;

	/**
	 * Номер
	 */
	@FXML
	private TextField DC_NUMBER;

	/**
	 * Наименование суда
	 */
	@FXML
	private TextField DC_RCNAME;

	/**
	 * юрик
	 */
	@FXML
	private GridPane JURIK;

	/**
	 * ФИО умершего
	 */
	@FXML
	private TextField DFIO;

	/**
	 * Номер
	 */
	@FXML
	private TextField DC_FNUM;

	/**
	 * Серия
	 */
	@FXML
	private TextField DC_SERIA;

	/**
	 * Фамилия если физик
	 */
	@FXML
	private TextField DC_FADLAST_NAME;

	/**
	 * Юр. адрес организации заявившей
	 */
	@FXML
	private TextField DC_FADREG_ADR;

	/**
	 * Причина смерти
	 */
	@FXML
	private TextField DC_CD;

	/**
	 * Имя, если физик заявитель
	 */
	@FXML
	private TextField DC_FADFIRST_NAME;

	/**
	 * Наим. мед. орг. выд. документ
	 */
	@FXML
	private TextField DC_FMON;

	/**
	 * ID гражданина
	 */
	@FXML
	private TextField DC_CUS;

	@FXML
	void FindClient(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(DFIO, DC_CUS, (Stage) DC_CUS.getScene().getWindow());
	}

	@FXML
	void AddClient(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(DFIO, DC_CUS, (Stage) DC_CUS.getScene().getWindow(), conn);
	}

	/**
	 * Найти гражданина
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
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
				CUS cus = new CUS();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				String DCUSBIRTHDAYt = new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY"));
				cus.setICUSNUM(rs.getInt("ICUSNUM"));
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
			Stage stage = (Stage) DC_CD.getScene().getWindow();

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

	/**
	 * Тип документа подтверждающий факт о смерти
	 * 
	 * @param event
	 */
	@FXML
	void DC_FTYPE(ActionEvent event) {
		if (DC_FTYPE.getValue().equals("Документ установленной формы о смерти")) {
			D_B.setVisible(false);
			D_B.setVisible(false);
			D_A.setVisible(true);
			// значения полей
			DC_FNUM.setText("");
			DC_FD.setValue(null);
			DC_FMON.setText("");
			DC_RCNAME.setText("");
			DC_NRNAME.setText("");
		} else if (DC_FTYPE.getValue().equals("Решение суда об установлении факта о смерти")
				| DC_FTYPE.getValue().equals("Решение суда об установлении лица умершим")) {
			D_A.setVisible(false);
			D_V.setVisible(false);
			D_B.setVisible(true);
			// значения полей
			DC_FNUM.setText("");
			DC_FD.setValue(null);
			DC_FMON.setText("");
			DC_RCNAME.setText("");
			DC_NRNAME.setText("");
		} else if (DC_FTYPE.getValue().equals("Документ о факте смерти лица, необоснованно репрессированного")) {
			D_A.setVisible(false);
			D_B.setVisible(false);
			D_V.setVisible(true);
			// значения полей
			DC_FNUM.setText("");
			DC_FD.setValue(null);
			DC_FMON.setText("");
			DC_RCNAME.setText("");
			DC_NRNAME.setText("");
		}
	}

	/**
	 * Тип заявителя
	 * 
	 * @param event
	 */
	@FXML
	void DC_ZTP(ActionEvent event) {
		if (DC_ZTP.getValue().equals("Физ. лицо")) {
			JURIK.setVisible(false);
			FIZIK.setVisible(true);

			DC_FADLAST_NAME.setText("");
			DC_FADFIRST_NAME.setText("");
			DC_FADMIDDLE_NAME.setText("");
			DC_FADLOCATION.setText("");
			DC_FADORG_NAME.setText("");
			DC_FADREG_ADR.setText("");

		} else if (DC_ZTP.getValue().equals("Организация")) {
			FIZIK.setVisible(false);
			JURIK.setVisible(true);

			DC_FADLAST_NAME.setText("");
			DC_FADFIRST_NAME.setText("");
			DC_FADMIDDLE_NAME.setText("");
			DC_FADLOCATION.setText("");
			DC_FADORG_NAME.setText("");
			DC_FADREG_ADR.setText("");
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
					.prepareCall("{ ? = call Deatch.EditDeath(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, DC_NUMBER.getText());
			callStmt.setString(3, DC_SERIA.getText());
			callStmt.setString(4, DC_FADREG_ADR.getText());
			callStmt.setString(5, DC_FADORG_NAME.getText());
			callStmt.setString(6, DC_FADLOCATION.getText());
			callStmt.setString(7, DC_FADMIDDLE_NAME.getText());
			callStmt.setString(8, DC_FADLAST_NAME.getText());
			callStmt.setString(9, DC_FADFIRST_NAME.getText());
			callStmt.setString(10, DC_ZTP.getValue());
			callStmt.setString(11, DC_LLOC.getText());
			callStmt.setString(12, DC_NRNAME.getText());
			callStmt.setString(13, DC_RCNAME.getText());
			callStmt.setString(14, DC_FMON.getText());
			callStmt.setString(15, DC_FTYPE.getValue());
			callStmt.setDate(16, (DC_FD.getValue() != null) ? java.sql.Date.valueOf(DC_FD.getValue()) : null);
			callStmt.setString(17, DC_FNUM.getText());
			callStmt.setString(18, DC_CD.getText());
			callStmt.setString(19, DC_DPL.getText());
			callStmt.setDate(20, (DC_DD.getValue() != null) ? java.sql.Date.valueOf(DC_DD.getValue()) : null);
			if (!DC_CUS.getText().equals("")) {
				callStmt.setInt(21, Integer.valueOf(DC_CUS.getText()));
			} else {
				callStmt.setNull(21, java.sql.Types.INTEGER);
			}
			callStmt.setInt(22, getId());
			callStmt.setString(23, DOC_NUMBER.getText());
			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
				conn.commit();
				setStatus(true);
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) DC_ZTP.getScene().getWindow();
				Msg.ErrorView(stage_, "EditDeath", conn);
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void SaveToCompare() {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ ? = call Deatch.EditDeath(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, DC_NUMBER.getText());
			callStmt.setString(3, DC_SERIA.getText());
			callStmt.setString(4, DC_FADREG_ADR.getText());
			callStmt.setString(5, DC_FADORG_NAME.getText());
			callStmt.setString(6, DC_FADLOCATION.getText());
			callStmt.setString(7, DC_FADMIDDLE_NAME.getText());
			callStmt.setString(8, DC_FADLAST_NAME.getText());
			callStmt.setString(9, DC_FADFIRST_NAME.getText());
			callStmt.setString(10, DC_ZTP.getValue());
			callStmt.setString(11, DC_LLOC.getText());
			callStmt.setString(12, DC_NRNAME.getText());
			callStmt.setString(13, DC_RCNAME.getText());
			callStmt.setString(14, DC_FMON.getText());
			callStmt.setString(15, DC_FTYPE.getValue());
			callStmt.setDate(16, (DC_FD.getValue() != null) ? java.sql.Date.valueOf(DC_FD.getValue()) : null);
			callStmt.setString(17, DC_FNUM.getText());
			callStmt.setString(18, DC_CD.getText());
			callStmt.setString(19, DC_DPL.getText());
			callStmt.setDate(20, (DC_DD.getValue() != null) ? java.sql.Date.valueOf(DC_DD.getValue()) : null);
			if (!DC_CUS.getText().equals("")) {
				callStmt.setInt(21, Integer.valueOf(DC_CUS.getText()));
			} else {
				callStmt.setNull(21, java.sql.Types.INTEGER);
			}
			callStmt.setInt(22, getId());
			callStmt.setString(23, DOC_NUMBER.getText());
			callStmt.execute();
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * При закрытии
	 */
	void onclose() {
		Stage stage = (Stage) DC_ZTP.getScene().getWindow();
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

	/**
	 * Сессия
	 */
	private Connection conn;

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			Pane1.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane2.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane3.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane4.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));

			// Тип заявителя
			{
				DC_ZTP.getItems().addAll("Физ. лицо", "Организация");
			}
			// Тип документа
			{
				DC_FTYPE.getItems().addAll("Документ установленной формы о смерти",
						"Решение суда об установлении факта о смерти", "Решение суда об установлении лица умершим",
						"Документ о факте смерти лица, необоснованно репрессированного");
			}
			// Сведения об умершем
			{
				DFIO.setText(death.getDFIO());
				if (death.getDC_ID() != null) {
					DC_CUS.setText(String.valueOf(death.getDC_CUS()));
				}
				DC_DD.setValue(death.getDC_DD());
				DC_DPL.setText(death.getDC_DPL());
				DC_CD.setText(death.getDC_CD());
				DC_LLOC.setText(death.getDC_LLOC());
			}
			// Документ подтверждающий факт о смерти
			{
				if (death.getDC_FTYPE() != null) {
					if (death.getDC_FTYPE().equals("A")) {
						DC_FTYPE.getSelectionModel().select("Документ установленной формы о смерти");
						D_A.setVisible(true);

						DC_FNUM.setText(death.getDC_FNUM());
						DC_FD.setValue(death.getDC_FD());
						DC_FMON.setText(death.getDC_FMON());
					} else if (death.getDC_FTYPE().equals("B")) {
						DC_FTYPE.getSelectionModel().select("Решение суда об установлении факта о смерти");
						D_B.setVisible(true);

						DC_RCNAME.setText(death.getDC_RCNAME());
					} else if (death.getDC_FTYPE().equals("B1")) {
						DC_FTYPE.getSelectionModel().select("Решение суда об установлении лица умершим");
						D_B.setVisible(true);
						DC_RCNAME.setText(death.getDC_RCNAME());
					} else if (death.getDC_FTYPE().equals("V")) {
						DC_FTYPE.getSelectionModel()
								.select("Документ о факте смерти лица, необоснованно репрессированного");
						D_V.setVisible(true);
						DC_NRNAME.setText(death.getDC_NRNAME());
					}
				}

			}
			// тип заявителя
			{

				if (death.getDC_ZTP() != null) {
					if (death.getDC_ZTP().equals("F")) {
						DC_ZTP.getSelectionModel().select("Физ. лицо");
						FIZIK.setVisible(true);
						DC_FADLAST_NAME.setText(death.getDC_FADLAST_NAME());
						DC_FADFIRST_NAME.setText(death.getDC_FADFIRST_NAME());
						DC_FADMIDDLE_NAME.setText(death.getDC_FADMIDDLE_NAME());
						DC_FADLOCATION.setText(death.getDC_FADLOCATION());
					} else if (death.getDC_ZTP().equals("J")) {
						DC_ZTP.getSelectionModel().select("Организация");
						JURIK.setVisible(true);
						DC_FADORG_NAME.setText(death.getDC_FADORG_NAME());
						DC_FADREG_ADR.setText(death.getDC_FADREG_ADR());
					}
				}
			}
			// выдано свидетельство
			{
				DC_SERIA.setText(death.getDC_SERIA());
				DC_NUMBER.setText(death.getDC_NUMBER());
			}
			DOC_NUMBER.setText(death.getDOC_NUMBER());
			new ConvConst().FormatDatePiker(DC_DD);
			new ConvConst().FormatDatePiker(DC_FD);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Объект свидетельства о смерти
	 */
	private DEATH_CERT death;

	public void setConn(Connection conn, DEATH_CERT dc) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.death = dc;
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

	public EditDeath() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}

}
