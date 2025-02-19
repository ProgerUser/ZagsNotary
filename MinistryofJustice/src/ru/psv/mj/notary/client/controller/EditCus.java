package ru.psv.mj.notary.client.controller;

import java.net.URL;
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
import java.time.format.DateTimeParseException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.app.model.InputFilter;
import ru.psv.mj.app.model.OTD;
import ru.psv.mj.app.model.SqlMap;
import ru.psv.mj.init.HttpsTrustManager;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.notary.client.model.ALL_DOCS;
import ru.psv.mj.notary.client.model.COUNTRIES;
import ru.psv.mj.notary.client.model.CUS;
import ru.psv.mj.notary.client.model.CUS_ADDR;
import ru.psv.mj.notary.client.model.CUS_CITIZEN;
import ru.psv.mj.notary.client.model.CUS_DOCUM;
import ru.psv.mj.notary.client.model.NT_CLI_TYPES;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;
import ru.psv.mj.widgets.KeyBoard;
import ru.psv.mj.zags.doc.birthact.AddBirthAct;
import ru.psv.mj.zags.doc.cus.Auth1c;
import ru.psv.mj.zags.doc.cus.VPUD;
import ru.psv.mj.zags.doc.death.AddDeath;
import ru.psv.mj.zags.doc.divorce.AddDivorce;
import ru.psv.mj.zags.doc.mercer.AddMercer;
import ru.psv.mj.zags.doc.patern.AddPatern;

/**
 * Редактирование Гражданина 29.10.2020 Переработка
 * 
 * @author Said
 *
 */
public class EditCus {
	@FXML
	private CheckBox PUNCT_NAME_NOT_LIST;
	@FXML
	private CheckBox AREA_NOT_LIST;
	@FXML
	private TextField AREA_T;
	@FXML
	private TextField PUNCT_NAME_T;
	// -------------
	@FXML
	private ComboBox<NT_CLI_TYPES> CUS_TYPE;
	@FXML
	private TextField CUS_INN;
	@FXML
	private TextField CUS_KPP;
	@FXML
	private DatePicker ORG_DATE_REG;
	// --------------
	@FXML
	private TextField CUS_OGRN;

	private void CombCusType(ComboBox<NT_CLI_TYPES> cmb) {
		cmb.setConverter(new StringConverter<NT_CLI_TYPES>() {
			@Override
			public String toString(NT_CLI_TYPES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public NT_CLI_TYPES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	void AREA_NOT_LIST(ActionEvent event) {
		try {
			if (AREA_NOT_LIST.isSelected()) {
				AREA.setValue(null);
				AREA.setVisible(false);

				AREA_T.setVisible(true);
			} else {
				AREA.setVisible(true);
				AREA_T.setVisible(false);
				AREA_T.setText("");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void PUNCT_NAME_NOT_LIST(ActionEvent event) {
		try {
			if (PUNCT_NAME_NOT_LIST.isSelected()) {
				PUNCT_NAME.setValue(null);
				PUNCT_NAME.setVisible(false);

				PUNCT_NAME_T.setVisible(true);
			} else {
				PUNCT_NAME.setVisible(true);

				PUNCT_NAME_T.setVisible(false);
				PUNCT_NAME_T.setText("");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void OpenKey() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) AB_LAST_NAME.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/widgets/KeyBoard.fxml"));

			KeyBoard controller = new KeyBoard();
			loader.setController(controller);
			controller.setTextField(AB_LAST_NAME.getScene());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Абхазская клавиатура");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {

				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private RadioButton AB_SUN;

	@FXML
	private RadioButton AB_DOUTH;

	@FXML
	private TextField AB_LAST_NAME;

	@FXML
	private TextField AB_FIRST_NAME;

	@FXML
	private TextField AB_MIDDLE_NAME;

	@FXML
	private TextField AB_PLACE_BIRTH;

	/**
	 * При изменении "Страна рождения"
	 */
	@FXML
	private void CombCountry() {
		CCUSSEX.requestFocus();
	}

	/**
	 * При изменении "Пол"
	 */
	@FXML
	private void CCUSSEX() {
		CCUSNATIONALITY.requestFocus();
	}

	/**
	 * При изменении "Пол"
	 */
	@FXML
	private void CCUSNATIONALITY() {
		Citizen.requestFocus();
	}

	/**
	 * При изменении подразделения
	 */
	@FXML
	private void ICUSOTD() {
		try {
			String readRecordSQL = "select * from OTD t, RAION g where t.area_id = g.code and COTDNAME = ? ";
			PreparedStatement stmt = conn.prepareStatement(readRecordSQL);
			stmt.setString(1, ICUSOTD.getValue());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				AREA.getSelectionModel().select(rs.getString("NAME"));
			}
			stmt.close();
			rs.close();
			CombCountry.requestFocus();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	void FillAllDocs() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			PreparedStatement sqlStatement = conn
					.prepareStatement(DbUtil.Sql_From_Prop("/ru/psv/mj/notary/client/controller/SQL.properties", "AllDocs"));
			sqlStatement.setString(1, String.valueOf(getId()));
			sqlStatement.setString(2, String.valueOf(getId()));
			ResultSet rs = sqlStatement.executeQuery();
			ObservableList<ALL_DOCS> val = FXCollections.observableArrayList();
			while (rs.next()) {
				ALL_DOCS list = new ALL_DOCS();
				list.setTM$DOC_DATE((rs.getDate("TM$DOC_DATE") != null) ? LocalDateTime
						.parse(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_DATE")), formatter)
						: null);
				list.setDOCNAME(rs.getString("DOCNAME"));
				//list.setTABLE_NAME(rs.getString("TABLE_NAME"));
				list.setDOC_ID(rs.getLong("DOC_ID"));
				//list.setTYPE_DOC(rs.getString("TYPE_DOC"));
				val.add(list);
			}
			sqlStatement.close();
			rs.close();
			all_docs.setItems(val);
			TableFilter<ALL_DOCS> tableFilter = TableFilter.forTableView(all_docs).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void RefreshAllDocs(ActionEvent event) {
		FillAllDocs();
	}

	/**
	 * заглушка, не используется
	 * 
	 * @param event
	 */
	@FXML
	void NoDubl(ActionEvent event) {

	}

	@FXML
	private TableView<ALL_DOCS> all_docs;

	@FXML
	private TableColumn<ALL_DOCS, LocalDateTime> DOC_DATET;

	@FXML
	private TableColumn<ALL_DOCS, String> DOCNAME;

	@FXML
	private TableColumn<ALL_DOCS, String> TYPE_DOC;

	@FXML
	private TableColumn<ALL_DOCS, Long> DOC_ID;

	@FXML
	private Tab DocTab;

	@FXML
	private TabPane CusTab;

	@FXML
	private TitledPane DublicateBorder;

	/**
	 * Корпус
	 */
	@FXML
	private TextField KORP;

	/**
	 * Таблица документов
	 */
	@FXML
	private TableView<CUS_DOCUM> CUS_DOCUM;

	/**
	 * Основной ли документ
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> PREF;

	/**
	 * Дата выдачи документа TextField
	 */
	@FXML
	private DatePicker DOC_DATE_T;

	/**
	 * Серия документа
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_SER;

	/**
	 * Тип документа
	 */
	@FXML
	private ComboBox<VPUD> ID_DOC_TP_T;

	/**
	 * Для добавления и редактирования документа
	 */
	@FXML
	private TitledPane TitledCRUDCusDocum;

	/**
	 * Район
	 */
	@FXML
	private ComboBox<String> AREA;

	/**
	 * Имя
	 */
	@FXML
	private TextField CCUSFIRST_NAME;

	/**
	 * Страна таблицы Гражданства
	 */
	// @FXML
	// private TableColumn<CUS_CITIZEN, String> COUNTRY;

	/**
	 * Серия документа
	 */
	@FXML
	private TextField DOC_SER_T;

	/**
	 * Отделение
	 */
	@FXML
	private ComboBox<String> ICUSOTD;

	/**
	 * Дата рождения
	 */
	@FXML
	private DatePicker DCUSBIRTHDAY;

	/**
	 * Место рождения
	 */
	@FXML
	private TextField CCUSPLACE_BIRTH;

	/**
	 * Основной ли документ
	 */
	@FXML
	private CheckBox PREF_T;

	/**
	 * Тип документа таблицы
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> ID_DOC_TP;

	/**
	 * Пол
	 */
	@FXML
	private ComboBox<String> CCUSSEX;

	/**
	 * Дом
	 */
	@FXML
	private TextField DOM;

	/**
	 * Кнопка добавить документ
	 */
	@FXML
	private Button AddCusDocum;

	/**
	 * Кнопка редактирования документа
	 */
	@FXML
	private Button EditCusDocum;

	/**
	 * Код подразделения
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_SUBDIV;

	/**
	 * Основной ли тип гражданства
	 */
	@FXML
	private TableColumn<CUS_CITIZEN, Boolean> OSN;

	/**
	 * Квартира
	 */
	@FXML
	private TextField KV;

	/**
	 * Номер документа
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_NUM;

	/**
	 * Населенные пункты
	 */
	@FXML
	private ComboBox<String> PUNCT_NAME;

	/**
	 * Номер документа
	 */
	@FXML
	private TextField DOC_NUM_T;

	/**
	 * Национальность
	 */
	@FXML
	private ComboBox<String> CCUSNATIONALITY;

	/**
	 * Кем выдан
	 */
	@FXML
	private TextField DOC_AGENCY_T;

	/**
	 * Фамилия
	 */
	@FXML
	private TextField CCUSLAST_NAME;

	@FXML
	private TextField CCUSNAME;
	@FXML
	private TextField CCUSNAME_SH;

	/**
	 * Отчество
	 */
	@FXML
	private TextField CCUSMIDDLE_NAME;

	/**
	 * Таблица гражданства
	 */
	@FXML
	private TableView<CUS_CITIZEN> CUS_CITIZEN;

	/**
	 * Кем выдан
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_AGENCY;

	/**
	 * Инфраструктура - улица
	 */
	@FXML
	private TextField INFR_NAME;

	/**
	 * Код подразделения
	 */
	@FXML
	private TextField DOC_SUBDIV_T;

	/**
	 * 
	 */
	@FXML
	private ScrollPane ScrollPaneCus;

	/**
	 * Дата окончания документа
	 */
	@FXML
	private TableColumn<CUS_DOCUM, LocalDate> DOC_PERIOD;

	/**
	 * 
	 */
	@FXML
	private DatePicker DOC_PERIOD_T;

	/**
	 * Дата выдачи документа
	 */
	@FXML
	private TableColumn<CUS_DOCUM, LocalDate> DOC_DATE;

	/**
	 * Наименование страны
	 */
	@FXML
	private TableColumn<CUS_CITIZEN, String> CLONGNAME;

	/**
	 * Отмена редактирования
	 * 
	 * @param event
	 */
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	/**
	 * Добавить национальность, если отсутствует
	 */
	void AddNationalityIfNotExist(boolean if_save) {
		Stage stage = (Stage) CCUSNATIONALITY.getScene().getWindow();
		Label alert = new Label("Национальность отсутствует в списке, добавить?");
		alert.setLayoutX(75.0);
		alert.setLayoutY(11.0);
		alert.setPrefHeight(17.0);

		Button no = new Button();
		no.setText("Нет");
		no.setLayoutX(111.0);
		no.setLayoutY(56.0);
		no.setPrefWidth(72.0);
		no.setPrefHeight(21.0);

		Button yes = new Button();
		yes.setText("Да");
		yes.setLayoutX(14.0);
		yes.setLayoutY(56.0);
		yes.setPrefWidth(72.0);
		yes.setPrefHeight(21.0);

		AnchorPane yn = new AnchorPane();
		yn.getChildren().add(alert);
		yn.getChildren().add(no);
		yn.getChildren().add(yes);
		Scene ynScene = new Scene(yn, 400, 100);
		Stage newWindow_yn = new Stage();
		no.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				CCUSNATIONALITY.setValue("");
				newWindow_yn.close();
			}
		});
		yes.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					PreparedStatement delete = conn.prepareStatement("declare\n" + "  pragma autonomous_transaction;\n"
							+ "begin\n" + "  insert into nationality (NAME) values (?);" + " commit;\n" + "end;\n");
					delete.setString(1, CCUSNATIONALITY.getValue());
					delete.executeUpdate();
					delete.close();
					if (if_save) {
						CallSaveToCompare();
					} else {
						CallSave();
					}
				} catch (SQLException e) {
					DbUtil.Log_Error(e);
				}
				newWindow_yn.close();
			}
		});
		newWindow_yn.setTitle("Внимание");
		newWindow_yn.setScene(ynScene);
		// Specifies the modality for new window.
		newWindow_yn.initModality(Modality.WINDOW_MODAL);
		// Specifies the owner Window (parent) for new window
		newWindow_yn.initOwner(stage);
		newWindow_yn.setResizable(false);
		newWindow_yn.getIcons().add(new Image("/icon.png"));
		newWindow_yn.show();
	}

	@FXML
	void AB_DOUTH(ActionEvent event) {
		try {
			if (CCUSSEX.getValue() != null && (CCUSSEX.getValue().equals("Женский") & AB_MIDDLE_NAME.getText() != null)
					&& (!AB_MIDDLE_NAME.getText().toLowerCase().contains("иԥа")
							& !AB_MIDDLE_NAME.getText().toLowerCase().contains("иԥҳа"))) {
				AB_MIDDLE_NAME.setText(AB_MIDDLE_NAME.getText() + "-иԥҳа");
			} else if (CCUSSEX.getValue() == null) {
				Msg.Message("Выберите пол!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void AB_SUN(ActionEvent event) {
		try {
			if (CCUSSEX.getValue() != null && (CCUSSEX.getValue().equals("Мужской") & AB_MIDDLE_NAME.getText() != null)
					&& (!AB_MIDDLE_NAME.getText().toLowerCase().contains("иԥа")
							& !AB_MIDDLE_NAME.getText().toLowerCase().contains("иԥҳа"))) {
				AB_MIDDLE_NAME.setText(AB_MIDDLE_NAME.getText() + "-иԥа");
			} else if (CCUSSEX.getValue() == null) {
				Msg.Message("Выберите пол!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	/**
	 * Добавить страну - Адрес
	 * 
	 * @param event
	 */
	@FXML
	void AddAddressCountry(ActionEvent event) {
		try {
			CitizenList("address");
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация гражданства
	 */
	void InitCitizen() {
		try {
			String selectStmt = "select ID,COUNTRY_CODE,COUNTRY_NAME, osn from CUS_CITIZEN where icusnum = ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, getId());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS_CITIZEN> infr_list = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS_CITIZEN infr = new CUS_CITIZEN();
				infr.setID(rs.getLong("ID"));
				infr.setCOUNTRY_CODE(rs.getLong("COUNTRY_CODE"));
				infr.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
				infr.setOSN((rs.getString("OSN").equals("Y")) ? true : false);
				infr_list.add(infr);
			}
			prepStmt.close();
			rs.close();
			CUS_CITIZEN.setItems(infr_list);
			// autoResizeColumns(CUS_CITIZEN);
			TableFilter<CUS_CITIZEN> tableFilter = TableFilter.forTableView(CUS_CITIZEN).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Добавить и редактировать гражданства
	 * 
	 * @param COUNTRY_I
	 * @param CLONGNAME_I
	 * @param osn
	 * @param oper
	 */
	void CRUDCitizen(Long COUNTRY_I, String CLONGNAME_I, String osn, String oper, String type2) {
		try {
			CallableStatement callStmt = conn.prepareCall(oper);
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setLong(2, COUNTRY_I);
			callStmt.setString(3, CLONGNAME_I);
			if (type2.equals("add")) {
				callStmt.setLong(4, getId());
			} else if (type2.equals("edit")) {
				CUS_CITIZEN cs = CUS_CITIZEN.getSelectionModel().getSelectedItem();
				callStmt.setLong(4, cs.getID());
			}
			callStmt.setString(5, osn);
			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
			} else {
				Stage stage_ = (Stage) CCUSLAST_NAME.getScene().getWindow();
				Msg.ErrorView(stage_, "MJCUS_CITIZEN", conn);
			}
			InitCitizen();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Показать список стран - в адресе, в гражданстве
	 * 
	 * @param type
	 */
	void CitizenList(String type) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();
			VBox vb = new VBox();

			CheckBox osn = new CheckBox();
			osn.setText("Основное");
			osn.setVisible(false);// убрать галочку
			if (type.equals("address") | type.equals("brn")) {
				osn.setVisible(false);
			}
			ToolBar toolBar = new ToolBar(Update, osn);
			XTableView<COUNTRIES> debtinfo = new XTableView<COUNTRIES>();
			XTableColumn<COUNTRIES, String> NAME = new XTableColumn<>("Название");
			NAME.setCellValueFactory(new PropertyValueFactory<>("NAME"));
			debtinfo.getColumns().add(NAME);
			vb.getChildren().add(debtinfo);
			vb.getChildren().add(toolBar);
			debtinfo.getStyleClass().add("mylistview");
			debtinfo.getStylesheets().add("/ScrPane.css");
			vb.setPadding(new Insets(10, 10, 10, 10));
			NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());

			debtinfo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			;
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			NAME.setColumnFilter(new PatternColumnFilter<>());

			String selectStmt = "select * from COUNTRIES t order by CODE asc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<COUNTRIES> user_o_list = FXCollections.observableArrayList();
			while (rs.next()) {
				COUNTRIES list = new COUNTRIES();
				list.setNAME(rs.getString("NAME"));
				list.setCODE(rs.getLong("CODE"));
				user_o_list.add(list);
			}
			prepStmt.close();
			rs.close();

			debtinfo.setItems(user_o_list);
			// autoResizeColumns(debtinfo);
			debtinfo.prefWidth(341);
			debtinfo.prefHeight(490);
			TableFilter<COUNTRIES> tableFilter = TableFilter.forTableView(debtinfo).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});

			/**
			 * двойной щелчок
			 */
			debtinfo.setRowFactory(tv -> {
				TableRow<COUNTRIES> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (debtinfo.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите сначала данные из таблицы!");
						} else {
							COUNTRIES country = debtinfo.getSelectionModel().getSelectedItem();
							// По типу вызова
							if (type.equals("add")) {
								CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
										"{ ? = call MJCUS.ADD_CUS_CITIZEN(?,?,?,?)}", "add");
							} else if (type.equals("edit")) {
								CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
										"{ ? = call MJCUS.EDIT_CUS_CITIZEN(?,?,?,?)}", "edit");
							} else if (type.equals("address")) {
								// CALFA_2.setText(String.valueOf(country.getCODE()));
								// CLONGNAMET.setText(country.getNAME());
								if (country.getCODE() == 1) {
									AREA.setEditable(true);
								}
							} else if (type.equals("brn")) {
								// CCUS_OK_SM.setText(String.valueOf(country.getCODE()));
								// BurthCountry.setText(country.getNAME());
							}
							((Node) (event.getSource())).getScene().getWindow().hide();
						}
					}
				});
				return row;
			});

			/**
			 * Фокусировка при редактировании
			 */
			if (type.equals("edit")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						CUS_CITIZEN cs = CUS_CITIZEN.getSelectionModel().getSelectedItem();
						for (COUNTRIES os : debtinfo.getItems()) {
							if (os.getCODE().equals(cs.getCOUNTRY_CODE())) {
								debtinfo.requestFocus();
								debtinfo.getSelectionModel().select(os);
								// debtinfo.getFocusModel().focus(os);
								break;
							}
						}

					}
				});
			}

			Update.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					if (debtinfo.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите сначала данные из таблицы!");
					} else {
						COUNTRIES country = debtinfo.getSelectionModel().getSelectedItem();
						// По типу вызова
						if (type.equals("add")) {
							CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
									"{ ? = call MJCUS.ADD_CUS_CITIZEN(?,?,?,?)}", "add");
						} else if (type.equals("edit")) {
							CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
									"{ ? = call MJCUS.EDIT_CUS_CITIZEN(?,?,?,?)}", "edit");
						} else if (type.equals("address")) {
							// CALFA_2.setText(String.valueOf(country.getCODE()));
							// CLONGNAMET.setText(country.getNAME());
							if (country.getCODE() == 1) {
								AREA.setEditable(true);
							}
						} else if (type.equals("brn")) {
							// CCUS_OK_SM.setText(String.valueOf(country.getCODE()));
							// BurthCountry.setText(country.getNAME());
						}
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}
			});
			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) CUS_CITIZEN.getScene().getWindow();
			Stage newWindow = new Stage();
			newWindow.setTitle("Список стран");
			newWindow.setScene(secondScene);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Добавить гражданство
	 * 
	 * @param event
	 */
	@FXML
	void CmAddCitizen(ActionEvent event) {
		try {
			CitizenList("add");
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Редактировать гражданство
	 * 
	 * @param event
	 */
	@FXML
	void CmEditCitizen(ActionEvent event) {
		try {
			// Если количество строк равно 1, то выбрать первую строку
			if (CUS_CITIZEN.getItems().size() == 1) {
				CUS_CITIZEN.getSelectionModel().select(0);
			}
			// ---------------
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				Main.logger = Logger.getLogger(getClass());
				CitizenList("edit");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Список стран рождения
	 * 
	 * @param event
	 */
	@FXML
	void BtBurnCountry(ActionEvent event) {
		try {
			CitizenList("brn");
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * При изменении района
	 * 
	 * @param event
	 */
	@FXML
	void AreaChange(ActionEvent event) {
		try {
			/**
			 * Нас. пункты
			 */
			{
				PreparedStatement sqlStatement = conn.prepareStatement("select * from NAS_PUNKT t where t.AREA = ?");
				sqlStatement.setString(1, AREA.getValue());
				ResultSet rs = sqlStatement.executeQuery();
				ObservableList<String> np = FXCollections.observableArrayList();
				while (rs.next()) {
					np.add(rs.getString(2));
				}
				sqlStatement.close();
				rs.close();

//				FilteredList<String> filterednationals = new FilteredList<String>(np);
//
//				PUNCT_NAME.getEditor().textProperty()
//						.addListener(new InputFilter<String>(PUNCT_NAME, filterednationals, false));
//				PUNCT_NAME.setItems(filterednationals);

				PUNCT_NAME.setItems(np);

				FxUtilTest.getComboBoxValue(PUNCT_NAME);
				FxUtilTest.autoCompleteComboBoxPlus(PUNCT_NAME,
						(typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));
			}

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Заполнить данными таблицу с документами
	 */
	void InitCusDocum() {
		try {
			SqlMap sql = new SqlMap().load("/ru/psv/mj/zags/doc/cus/SQL.xml");
			String doc_list = sql.getSql("doc_list");
			PreparedStatement prepStmt = conn.prepareStatement(doc_list);
			prepStmt.setLong(1, getId());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS_DOCUM> all_docs = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS_DOCUM list = new CUS_DOCUM();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				list.setDOC_SUBDIV(rs.getString("DOC_SUBDIV"));
				list.setDOC_PERIOD((rs.getDate("DOC_PERIOD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_PERIOD")), formatter) : null);
				list.setDOC_AGENCY(rs.getString("DOC_AGENCY"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setDOC_SER(rs.getString("DOC_SER"));
				list.setDOC_NUM(rs.getString("DOC_NUM"));
				list.setID_DOC_TP(rs.getString("ID_DOC_TP"));
				list.setPREF(rs.getString("PREF"));
				list.setID_DOC(rs.getLong("ID_DOC"));
				all_docs.add(list);
			}
			prepStmt.close();
			rs.close();
			CUS_DOCUM.setItems(all_docs);
			// autoResizeColumns(CUS_DOCUM);
			TableFilter<CUS_DOCUM> tableFilter = TableFilter.forTableView(CUS_DOCUM).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить документ
	 * 
	 * @param event
	 */
	@FXML
	void CmDelCusDocum(ActionEvent event) {
		try {
			if (CUS_DOCUM.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("delete from cus_docum where ID_DOC = ?");
				delete.setLong(1, cd.getID_DOC());
				delete.executeUpdate();
				delete.close();
				/**
				 * Обновление после удаления
				 */
				InitCusDocum();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Добавление и редактирование документа
	 * 
	 * @param type
	 */
	void CRUDDocum(String type, String type2) {
		try {
			CallableStatement callStmt = conn.prepareCall(type);
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, (PREF_T.isSelected()) ? "Y" : "N");
			if (ID_DOC_TP_T.getValue() != null) {
				VPUD vp = ID_DOC_TP_T.getSelectionModel().getSelectedItem();
				callStmt.setLong(3, vp.getIPUDID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}
			callStmt.setString(4, DOC_NUM_T.getText());
			callStmt.setString(5, DOC_SER_T.getText());
			callStmt.setDate(6, (DOC_DATE_T.getValue() != null) ? java.sql.Date.valueOf(DOC_DATE_T.getValue()) : null);
			callStmt.setString(7, DOC_AGENCY_T.getText());
			callStmt.setDate(8,
					(DOC_PERIOD_T.getValue() != null) ? java.sql.Date.valueOf(DOC_PERIOD_T.getValue()) : null);
			callStmt.setString(9, DOC_SUBDIV_T.getText());
			if (type2.equals("add")) {
				callStmt.setLong(10, getId());
			} else if (type2.equals("edit")) {
				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
				callStmt.setLong(10, cd.getID_DOC());
			}
			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
				/**
				 * Обновить
				 */
				InitCusDocum();
			} else {
				Stage stage_ = (Stage) CUS_CITIZEN.getScene().getWindow();
				Msg.ErrorView(stage_, "CUS_DOCUM", conn);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Кнопка удалить документ
	 */
	@FXML
	private Button BtDelDocum;

	/**
	 * Добавить документ
	 * 
	 * @param event
	 */
	@FXML
	void AddCusDocum(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/cus/IUCus_Doc.fxml"));

			Add_Cus_Doc controller = new Add_Cus_Doc();
			loader.setController(controller);
			controller.setConn(conn, false);
			controller.setId(getId());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить паспортные данные");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					// обновим таблицу при закрытии
					InitCusDocum();
				}
			});
			stage.show();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Редактировать документ
	 * 
	 * @param event
	 */
	@FXML
	void EditCusDocum(ActionEvent event) {
		try {
			// Если количество строк равно 1, то выбрать первую строку
			if (CUS_DOCUM.getItems().size() == 1) {
				CUS_DOCUM.getSelectionModel().select(0);
			}
			if (CUS_DOCUM.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				Stage stage = new Stage();
				Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/cus/IUCus_Doc.fxml"));

				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();

				Edit_Cus_Doc controller = new Edit_Cus_Doc();
				loader.setController(controller);
				controller.setConn(conn, false, cd);
				controller.setId(getId());

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать паспортные данные");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						// обновим таблицу при закрытии
						InitCusDocum();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) CUS_CITIZEN.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private ProgressIndicator PROGRESS;

	@FXML
	private BorderPane BP;

	/**
	 * Отправить данные в 1с
	 */
	void Save1c(Long dbid) {
		try {
			// Если многопоточность
//			BP.setDisable(true);
//			PROGRESS.setVisible(true);
//			Task<Object> task = new Task<Object>() {
//				@Override
//				public Object call() throws Exception {
			// разрешить любые сертификаты
			new HttpsTrustManager().allowAllSSL();
			Auth1c exdb = new Auth1c();
			String CPU_NAME = exdb.CPU_NAME();
			String DB_NAME = exdb.DB_NAME();
			String HDD_SERIAL = exdb.HDD_SERIAL();
			// String LAST_AUTH = exdb.LAST_AUTH();
			String ENCRYPT = exdb.ENCRYPT(DB_NAME, HDD_SERIAL, CPU_NAME);

			// Обращение к сервису
			String auth = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<Контейнер>\r\n"
					+ "<ДанныеДляАвторизации КодДоступа=\"" + ENCRYPT + "\" IDБазы=\"" + exdb.ID() + "\"/>\r\n"
					+ "</Контейнер>\r\n";
			URL url = new URL(exdb.FullAddress() + "/Authorization");
			String AuthReturn = exdb.Call1cHttpService(auth, exdb.LOGIN(), exdb.PASSWORD(), url);
			System.out.println(AuthReturn);
			String xml_last_auth = exdb.XML(AuthReturn);
			exdb.SAVE_AUTH_1C_DATE(xml_last_auth);

			SqlMap sql = new SqlMap().load("/ru/psv/mj/zags/doc/cus/SQL.xml");
			String readRecordSQL = sql.getSql("FOR_1c");
			String XML = sql.getSql("1C_XML");
			{
				PreparedStatement select = conn.prepareStatement(readRecordSQL);
				select.setLong(1, dbid);
				ResultSet rs = select.executeQuery();
				if (rs.next()) {
					XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<Контейнер>\r\n"
							+ "	<ДанныеАвторизации IDБазы=\"" + exdb.ID() + "\" ДатаПоследнейАвторизации=\""
							+ xml_last_auth + "\"/>\r\n" + "	<РодительскийЭлемент>\r\n"
							+ "		<ПерсональныеДанные Оператор=\"" + DB_NAME + "/" + rs.getString("OPER")
							+ "\" ID=\"\" КодСсылки=\"\" Фамилия=\"" + rs.getString("CCUSLAST_NAME") + "\" Имя=\""
							+ rs.getString("CCUSFIRST_NAME") + "\" Отчество=\"" + rs.getString("CCUSMIDDLE_NAME")
							+ "\" ДатаРождения=\"" + rs.getString("BR_DATE") + "\" КодПола=\"" + rs.getString("CCUSSEX")
							+ "\"/>\r\n" + "		<МестоЖительства КодРайона=\"" + rs.getString("CODE_AREA")
							+ "\" НаименованиеРайона=\"" + rs.getString("NAME_AREA") + "\" КодНасПункта=\""
							+ rs.getString("PUNCT_CODE") + "\" НаименованиеНасПункта=\"" + rs.getString("PUNCT_NAME")
							+ "\" Улица=\"" + rs.getString("STREET") + "\" Дом=\"" + rs.getString("DOM")
							+ "\" Корпус=\"" + rs.getString("KORP") + "\" Квартира=\"" + rs.getString("KV")
							+ "\"/>\r\n";
				}
				rs.close();
				select.close();
			}
			XML = XML + "		<Паспорта>\r\n";
			String docs = "";
			int cnt_doc = 0;
			{
				String XML_CUS_DOCUM = sql.getSql("1C_XML_CUS_DOCUM");
				PreparedStatement doc = conn.prepareStatement(XML_CUS_DOCUM);
				doc.setLong(1, dbid);
				ResultSet rs = doc.executeQuery();
				while (rs.next()) {
					cnt_doc++;
					docs = docs + "			<Паспорт" + String.valueOf(cnt_doc) + " ID=\"" + rs.getString("SYS_GUID")
							+ "\" Просрочен=\"0\" КодГосударства=\"" + rs.getString("COUNTRY_CODE")
							+ "\" НаименованиеГосударства=\"" + rs.getString("COUNTRY_NAME") + "\" КодВида=\""
							+ rs.getString("DOC_CODE") + "\" НаименованиеВида=\"" + rs.getString("DOC_NAME")
							+ "\" Серия=\"" + rs.getString("DOC_SER") + "\" Номер=\"" + rs.getString("DOC_NUM")
							+ "\" КемВыдан=\"" + rs.getString("DOC_AGENCY") + "\" ДатаВыдачи=\""
							+ rs.getString("DOC_DATE") + "\" СрокДействия=\"" + rs.getString("DOC_PERIOD") + "\"/>\r\n";
				}
				doc.close();
				rs.close();
			}
			XML = XML + docs + "		</Паспорта>\r\n	</РодительскийЭлемент>\r\n</Контейнер>";
			System.out.println(XML);
			URL url2 = new URL(exdb.FullAddress() + "Sync/ClientPriority");
			String save_ret_1c = // exdb.SAVEFIO_1C(XML);
					exdb.Call1cHttpService(XML, exdb.LOGIN(), exdb.PASSWORD(), url2);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println(save_ret_1c);
			if (!save_ret_1c.equals("Неудачная попытка авторизации") & !save_ret_1c.contains("{")
					& save_ret_1c.matches(".*\\d.*")) {
				// обновим id-шник
				PreparedStatement doc = conn.prepareStatement("update cus set cus.ID1C = ? where cus.ICUSNUM = ?");
				doc.setLong(1, Long.valueOf(save_ret_1c.replace(" ", "")));
				doc.setLong(2, dbid);
				doc.executeUpdate();
				doc.close();
			}
//					return null;
//				}
//			};
//			task.setOnFailed(e -> {
//				Msg.Message(task.getException().getMessage());
//				task.getException().printStackTrace();
//			});
//			task.setOnSucceeded(e -> {
//				BP.setDisable(false);
//				PROGRESS.setVisible(false);
//				onclose();
//			});
//			exec.execute(task);
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Для многопоточности
	 */
	@SuppressWarnings("unused")
	private Executor exec;
	/**
	 * Сессия текущая
	 */
	private Connection conn = null;

	/**
	 * Вызов пакета редактирования клиента
	 */
	void CallSave() {
		try {
			CallableStatement callStmt = conn.prepareCall(
					"{ ? = call MJCUS.UPDATE_NT_CUS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setDate(2,
					(DCUSBIRTHDAY.getValue() != null) ? java.sql.Date.valueOf(DCUSBIRTHDAY.getValue()) : null);
			callStmt.setString(3, CCUSLAST_NAME.getText());
			callStmt.setString(4, CCUSFIRST_NAME.getText());
			callStmt.setString(5, CCUSMIDDLE_NAME.getText());
			callStmt.setString(6, CCUSNATIONALITY.getValue());
			callStmt.setString(7, CCUSSEX.getValue());
			callStmt.setString(8, CCUSPLACE_BIRTH.getText());
			callStmt.setString(9, ICUSOTD.getValue());
			if (CombCountry.getValue() != null) {
				callStmt.setLong(10, CombCountry.getValue().getCODE());
			} else {
				callStmt.setNull(10, Types.INTEGER);
			}
			if (CombCountryAddr.getValue() != null) {
				callStmt.setLong(11, CombCountryAddr.getValue().getCODE());
			} else {
				callStmt.setNull(11, Types.INTEGER);
			}
			// Район
			if (AREA_NOT_LIST.isSelected()) {
				callStmt.setString(12, AREA_T.getText());
			} else if (!AREA_NOT_LIST.isSelected()) {
				callStmt.setString(12, AREA.getValue());
			}
			callStmt.setString(13, null);
			// Нас. пункт
			if (PUNCT_NAME_NOT_LIST.isSelected()) {
				callStmt.setString(14, PUNCT_NAME_T.getText());
			} else if (!PUNCT_NAME_NOT_LIST.isSelected()) {
				callStmt.setString(14, PUNCT_NAME.getValue());
			}
			callStmt.setString(15, INFR_NAME.getText());
			callStmt.setString(16, DOM.getText());
			callStmt.setString(17, KORP.getText());
			callStmt.setString(18, KV.getText());
			callStmt.setLong(19, getId());
			callStmt.setString(20, AB_FIRST_NAME.getText());
			callStmt.setString(21, AB_MIDDLE_NAME.getText());
			callStmt.setString(22, AB_LAST_NAME.getText());
			callStmt.setString(23, AB_PLACE_BIRTH.getText());
			// адрес-район если текст
			callStmt.setLong(24, (AREA_NOT_LIST.isSelected() ? 2 : 1));
			// адрес-населенный пункт если текст
			callStmt.setLong(25, (PUNCT_NAME_NOT_LIST.isSelected() ? 2 : 1));

			// Новые поля
			NT_CLI_TYPES val = CUS_TYPE.getSelectionModel().getSelectedItem();
			if (val != null) {
				callStmt.setLong(26, val.getCODE());
			} else {
				callStmt.setNull(26, Types.INTEGER);
			}
			callStmt.setString(27, CUS_INN.getText());
			callStmt.setString(28, CUS_KPP.getText());
			callStmt.setString(29, CUS_OGRN.getText());
			callStmt.setDate(30,
					(ORG_DATE_REG.getValue() != null) ? java.sql.Date.valueOf(ORG_DATE_REG.getValue()) : null);
			callStmt.setString(31, CCUSNAME.getText());
			callStmt.setString(32, CCUSNAME_SH.getText());

			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
				conn.commit();
				setStatus(true);
				// сохранить, обновить
				{
					NT_CLI_TYPES vals = CUS_TYPE.getSelectionModel().getSelectedItem();
					if (vals != null) {
						if (vals.getCODE() == 1 | vals.getCODE() == 3) {
							//Save1c(getId());
							onclose();
						} else if (vals.getCODE() == 2) {
							onclose();
						}
					}
				}
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
				Msg.ErrorView(stage_, "EDIT_CUS", conn);
			}
			callStmt.close();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Вызов пакета редактирования клиента, для сравнения
	 */
	void CallSaveToCompare() {
		try {
			CallableStatement callStmt = conn.prepareCall(
					"{ ? = call MJCUS.UPDATE_NT_CUS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setDate(2,
					(DCUSBIRTHDAY.getValue() != null) ? java.sql.Date.valueOf(DCUSBIRTHDAY.getValue()) : null);
			callStmt.setString(3, CCUSLAST_NAME.getText());
			callStmt.setString(4, CCUSFIRST_NAME.getText());
			callStmt.setString(5, CCUSMIDDLE_NAME.getText());
			callStmt.setString(6, CCUSNATIONALITY.getValue());
			callStmt.setString(7, CCUSSEX.getValue());
			callStmt.setString(8, CCUSPLACE_BIRTH.getText());
			callStmt.setString(9, ICUSOTD.getValue());
			if (CombCountry.getValue() != null) {
				callStmt.setLong(10, CombCountry.getValue().getCODE());
			} else {
				callStmt.setNull(10, Types.INTEGER);
			}
			if (CombCountryAddr.getValue() != null) {
				callStmt.setLong(11, CombCountryAddr.getValue().getCODE());
			} else {
				callStmt.setNull(11, Types.INTEGER);
			}
			// Район
			if (AREA_NOT_LIST.isSelected()) {
				callStmt.setString(12, AREA_T.getText());
			} else if (!AREA_NOT_LIST.isSelected()) {
				callStmt.setString(12, AREA.getValue());
			}
			callStmt.setString(13, null);
			// Нас. пункт
			if (PUNCT_NAME_NOT_LIST.isSelected()) {
				callStmt.setString(14, PUNCT_NAME_T.getText());
			} else if (!PUNCT_NAME_NOT_LIST.isSelected()) {
				callStmt.setString(14, PUNCT_NAME.getValue());
			}
			callStmt.setString(15, INFR_NAME.getText());
			callStmt.setString(16, DOM.getText());
			callStmt.setString(17, KORP.getText());
			callStmt.setString(18, KV.getText());
			callStmt.setLong(19, getId());
			callStmt.setString(20, AB_FIRST_NAME.getText());
			callStmt.setString(21, AB_MIDDLE_NAME.getText());
			callStmt.setString(22, AB_LAST_NAME.getText());
			callStmt.setString(23, AB_PLACE_BIRTH.getText());
			// адрес-район если текст
			callStmt.setLong(24, (AREA_NOT_LIST.isSelected() ? 2 : 1));
			// адрес-населенный пункт если текст
			callStmt.setLong(25, (PUNCT_NAME_NOT_LIST.isSelected() ? 2 : 1));

			// Новые поля
			NT_CLI_TYPES val = CUS_TYPE.getSelectionModel().getSelectedItem();
			if (val != null) {
				callStmt.setLong(26, val.getCODE());
			} else {
				callStmt.setNull(26, Types.INTEGER);
			}
			callStmt.setString(27, CUS_INN.getText());
			callStmt.setString(28, CUS_KPP.getText());
			callStmt.setString(29, CUS_OGRN.getText());
			callStmt.setDate(30,
					(ORG_DATE_REG.getValue() != null) ? java.sql.Date.valueOf(ORG_DATE_REG.getValue()) : null);
			callStmt.setString(31, CCUSNAME.getText());
			callStmt.setString(32, CCUSNAME_SH.getText());

			callStmt.execute();
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Форматирование даты ДД.ММ.ГГГГ
	 */
	DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

	/**
	 * Форматирование столбцов
	 * 
	 * @param TC
	 */
	void DateFormatCol(TableColumn<CUS_DOCUM, LocalDate> TC) {
		TC.setCellFactory(column -> {
			TableCell<CUS_DOCUM, LocalDate> cell = new TableCell<CUS_DOCUM, LocalDate>() {
				private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						if (item != null) {
							setText(format.format(item));
						}
					}
				}
			};
			return cell;
		});
	}

	void DateFormatColDT(TableColumn<ALL_DOCS, LocalDateTime> TC) {
		TC.setCellFactory(column -> {
			TableCell<ALL_DOCS, LocalDateTime> cell = new TableCell<ALL_DOCS, LocalDateTime>() {
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
	}

	/**
	 * Форматирование DatePiker
	 * 
	 * @param DP
	 */
	void DateFormatPiker(DatePicker DP) {
		DP.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * Первая буква заглавная
	 * 
	 * @param value
	 * @return
	 */
	public static String upperCaseAllFirst(String value) {
		char[] array = value.toCharArray();
		// Uppercase first letter.
		array[0] = Character.toUpperCase(array[0]);
		// Uppercase all letters that follow a whitespace character.
		for (int i = 1; i < array.length; i++) {
			if (Character.isWhitespace(array[i - 1])) {
				array[i] = Character.toUpperCase(array[i]);
			}
		}
		return new String(array);
	}

	/**
	 * Первая буква заглавная
	 * 
	 * @param TxtFld
	 */
	void FirstWUpp(TextField TxtFld) {
		TxtFld.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > 0) {
				TxtFld.setText(upperCaseAllFirst(newValue));
			}
		});
	}

	/**
	 * В верхнем регистре
	 * 
	 * @param tf
	 */
	void UpperCase(TextField tf) {
		tf.setTextFormatter(new TextFormatter<>((change) -> {
			change.setText(change.getText().toUpperCase());
			return change;
		}));
	}

	boolean UPD_NAT = false;
	boolean ADOPT = false;
	boolean BRN_BIRTH_ACT = false;
	boolean PATERN_CERT = false;
	boolean MC_MERCER = false;
	boolean UPD_NAME = false;
	boolean DEATH_CERT = false;
	boolean DIVORCE_CERT = false;
	boolean UPDATE_ABH_NAME = false;

	/**
	 * Открыть документ
	 * ************************************************************************************
	 */
	void OpenDocument() {
		try {
			if (all_docs.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите документ!");
			} else {

			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * При вводе фамилии
	 * 
	 * @param event
	 */
	@FXML
	void EnterLastName(ActionEvent event) {
		CCUSFIRST_NAME.requestFocus();
	}

	/**
	 * При вводе имени
	 * 
	 * @param event
	 */
	@FXML
	void EnterFirstName(ActionEvent event) {
		CCUSMIDDLE_NAME.requestFocus();
	}

	/**
	 * При вводе имени
	 * 
	 * @param event
	 */
	@FXML
	void EnterMiddleName(ActionEvent event) {
		DCUSBIRTHDAY.requestFocus();
	}

	/**
	 * При вводе "Место рождения"
	 * 
	 * @param event
	 */
	@FXML
	void EnterPlaceBirth(ActionEvent event) {
		ICUSOTD.requestFocus();
	}

	/**
	 * При вводе даты рождения
	 * 
	 * @param event
	 */
	@FXML
	void EnterBirthDate(ActionEvent event) {
		CCUSPLACE_BIRTH.requestFocus();
	}

	@FXML
	private AnchorPane AP;
	// _______Для_Блокировки___________
	@FXML
	private TableView<CUS> DUBL;

	@FXML
	private ToolBar DUBLIC_TOOL;

	/**
	 * Страна рождения
	 */
	@FXML
	private ComboBox<COUNTRIES> CombCountry;

	/**
	 * Для стран
	 */
	private void CombCountry(ComboBox<COUNTRIES> cmb) {
		cmb.setConverter(new StringConverter<COUNTRIES>() {
			@Override
			public String toString(COUNTRIES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public COUNTRIES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	/**
	 * Страна
	 */
	@FXML
	private ComboBox<COUNTRIES> CombCountryAddr;

	@FXML
	private TitledPane Citizen;

	@FXML
	private TitledPane Address;

	@FXML
	private TitledPane Docs;

	/**
	 * Основные данные
	 */
	@FXML
	private TitledPane OSN_DATA;

	@FXML
	private ButtonBar MainTool;

	@FXML
	private Button SaveEditB;

	@FXML
	private void OpenBTN() {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void AddNotary() {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрыть и открыть на редактирование
	 */
	@FXML
	private void SaveEdit() {
		try {
			NT_CLI_TYPES val = CUS_TYPE.getSelectionModel().getSelectedItem();
			if (val != null) {
				if (val.getCODE() == 1 | val.getCODE() == 3) {
					Statement sqlStatement = conn.createStatement();
					String readRecordSQL = "select count(*) from NATIONALITY where name = '"
							+ CCUSNATIONALITY.getValue() + "'";
					ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
					// Проверка существования национальности
					if (CCUSNATIONALITY.getValue() != null) {
						if (rs.next()) {
							if (rs.getLong(1) == 0) {
								AddNationalityIfNotExist(false);
							} else {
								CallSave();
							}
						}
					} else {
						Msg.Message("Выберите национальность");
					}
					rs.close();
					sqlStatement.close();
				} else if (val.getCODE() == 2) {
					CallSave();
				}
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	void custype() {
		try {
			NT_CLI_TYPES val = CUS_TYPE.getSelectionModel().getSelectedItem();
			if (val != null) {
				if (val.getCODE().equals(2l)) {

					CCUSLAST_NAME.setText("");
					CCUSFIRST_NAME.setText("");
					CCUSMIDDLE_NAME.setText("");

//					CCUSNAME.setText("");
//					CCUSNAME_SH.setText("");
//					
//					CCUSNAME.setEditable(true);
//					CCUSNAME_SH.setEditable(true);

					CCUSLAST_NAME.setDisable(true);
					CCUSFIRST_NAME.setDisable(true);
					CCUSMIDDLE_NAME.setDisable(true);
					
					DCUSBIRTHDAY.setDisable(true);
					
					CCUSNAME.setDisable(false);
					CCUSNAME_SH.setDisable(false);
				} else if (val.getCODE().equals(1l) || val.getCODE().equals(3l)) {

//					CCUSNAME.setEditable(true);
//					CCUSNAME_SH.setEditable(true);
					
					CCUSNAME.setDisable(true);
					CCUSNAME_SH.setDisable(true);
					
					CCUSLAST_NAME.setDisable(false);
					CCUSFIRST_NAME.setDisable(false);
					CCUSMIDDLE_NAME.setDisable(false);

					CCUSNAME.setText("");
					CCUSNAME_SH.setText("");
					
					DCUSBIRTHDAY.setDisable(false);

				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	@FXML
	void CUS_TYPE() {
		custype();
	}

	/**
	 * Быстрый способ заполнения даты
	 * 
	 * @param dp
	 */
	void DateAutoComma(DatePicker dp) {
		DateTimeFormatter fastFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		dp.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate object) {
				return (object != null) ? object.format(defaultFormatter) : null;
			}

			@Override
			public LocalDate fromString(String string) {
				try {
					return LocalDate.parse(string, fastFormatter);
				} catch (DateTimeParseException dtp) {

				}

				return LocalDate.parse(string, defaultFormatter);
			}
		});
	}

	@FXML
	void CCUSNAME() {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private CUS ForAddFioAndId;
	@FXML
	private MenuButton ZAGS_MENU_BUT;

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			
			CCUSLAST_NAME.setEditable(true);
//			final KeyCombination kb = new KeyCodeCombination(KeyCode.BACK_QUOTE, KeyCombination.CONTROL_DOWN);
//			AB_MIDDLE_NAME.setOnKeyReleased(event -> {
//				System.out.println(event.getCode());
//				if (kb.match(event)) {
//					System.out.println("Pressed!!");
//				}
//			});

			ToggleGroup toggleGroup = new ToggleGroup();

			AB_SUN.setToggleGroup(toggleGroup);
			AB_DOUTH.setToggleGroup(toggleGroup);

			// Проверка, работает ли пользователь в загсе, нотариате или админ
			if (DbUtil.Access_Level().equals("NOT"))
				DocTab.getTabPane().getTabs().remove(DocTab);
//			addIfNotPresent(BP.getStyleClass(), JMetroStyleClass.BACKGROUND);
//			addIfNotPresent(all_docs.getStyleClass(), JMetroStyleClass.TABLE_GRID_LINES);
//			addIfNotPresent(all_docs.getStyleClass(), JMetroStyleClass.ALTERNATING_ROW_COLORS);
//
//			addIfNotPresent(CUS_CITIZEN.getStyleClass(), JMetroStyleClass.TABLE_GRID_LINES);
//			addIfNotPresent(CUS_CITIZEN.getStyleClass(), JMetroStyleClass.ALTERNATING_ROW_COLORS);
//			
//			addIfNotPresent(CUS_DOCUM.getStyleClass(), JMetroStyleClass.TABLE_GRID_LINES);
//			addIfNotPresent(CUS_DOCUM.getStyleClass(), JMetroStyleClass.ALTERNATING_ROW_COLORS);

			// DateAutoComma(DCUSBIRTHDAY);
			/**
			 * Двойной щелчок по строке
			 */
			CUS_DOCUM.setRowFactory(tv -> {
				TableRow<CUS_DOCUM> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						try {

							Stage stage = new Stage();
							Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/cus/IUCus_Doc.fxml"));

							CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();

							Edit_Cus_Doc controller = new Edit_Cus_Doc();
							loader.setController(controller);
							controller.setConn(conn, false, cd);
							controller.setId(cd.getID_DOC());

							Parent root = loader.load();
							stage.setScene(new Scene(root));
							stage.getIcons().add(new Image("/icon.png"));
							stage.setTitle("Редактировать паспортные данные");
							stage.initOwner(stage_);
							stage.setResizable(false);
							stage.initModality(Modality.WINDOW_MODAL);
							stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
								@Override
								public void handle(WindowEvent paramT) {
									// обновим таблицу при закрытии
									InitCusDocum();
								}
							});
							stage.show();
						} catch (Exception e) {
							DbUtil.Log_Error(e);
						}
					}
				});
				return row;
			});

			// MainTool.getItems().remove(SaveEditB);
//			SaveEditB.setVisible(false);;
//			
//			Docs.heightProperty().addListener(
//					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));
//			Address.heightProperty().addListener(
//					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));
//
//			Citizen.heightProperty().addListener(
//					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));
//
//			OSN_DATA.heightProperty().addListener(
//					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));

			/**
			 * Для создания многопоточности
			 */
			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});
			// AP.getChildren().remove(AP.lookup(".DublicateBorder"));

			// delete
			AP.getChildren().remove(DublicateBorder);
			// DublicateBorder.getChildren().remove(DUBL);
			// DublicateBorder.getChildren().remove(DUBLIC_TOOL);

			DocTab.setDisable(false);
			// DocTab.setText("Документы");
			// CusTab.setMaxWidth(800);
			// DublicateBorder.setVisible(false);
			DOC_DATET.setCellValueFactory(cellData -> cellData.getValue().TM$DOC_DATEProperty());
			DOCNAME.setCellValueFactory(cellData -> cellData.getValue().DOCNAMEProperty());
			DOC_ID.setCellValueFactory(cellData -> cellData.getValue().DOC_IDProperty().asObject());
			TYPE_DOC.setCellValueFactory(cellData -> cellData.getValue().TYPE_DOCProperty());
			// Двойной щелчок по строке для открытия документа
			all_docs.setRowFactory(tv -> {
				TableRow<ALL_DOCS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						OpenDocument();
					}
				});
				return row;
			});
			// форматировать дату создания документа
			DateFormatColDT(DOC_DATET);

			// заполнить таблицу с документами данными
			FillAllDocs();
			/**
			 * Установить пол по окончанию отчества - "ич"-м, "на"-ж
			 * 
			 * @param event
			 */
			CCUSMIDDLE_NAME.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					if (newPropertyValue) {
					} else {
						if (!CCUSMIDDLE_NAME.getText().equals("")) {
							String sex = CCUSMIDDLE_NAME.getText().substring(CCUSMIDDLE_NAME.getText().length() - 2,
									CCUSMIDDLE_NAME.getText().length());
							if (sex.toLowerCase().equals("ич")) {
								// CCUSSEX.getItems().addAll("");
								// CCUSSEX.getItems().addAll("Мужской", "Женский");
								for (String ss : CCUSSEX.getItems()) {
									if (ss.equals("Мужской")) {
										CCUSSEX.getItems().addAll("");
										CCUSSEX.getSelectionModel().select(ss);
										break;
									}
								}
							} else if (sex.toLowerCase().equals("на")) {
								// CCUSSEX.getItems().addAll("");
								// CCUSSEX.getItems().addAll("Мужской", "Женский");
								for (String ss : CCUSSEX.getItems()) {
									if (ss.equals("Женский")) {
										CCUSSEX.getSelectionModel().select(ss);
										break;
									}
								}
							}
						}
					}
				}
			});

			/**
			 * Первая буква заглавная
			 */
			{
				FirstWUpp(CCUSLAST_NAME);
				FirstWUpp(CCUSFIRST_NAME);
				FirstWUpp(CCUSMIDDLE_NAME);
				// FirstWUpp(CCUSPLACE_BIRTH);
				FirstWUpp(INFR_NAME);
				// UpperCase(DOC_SUBDIV_T);
				// UpperCase(DOC_AGENCY_T);
			}
			/**
			 * Инициализация столбцов
			 */
			ID_DOC_TP.setCellValueFactory(cellData -> cellData.getValue().ID_DOC_TPProperty());
			DOC_SER.setCellValueFactory(cellData -> cellData.getValue().DOC_SERProperty());
			DOC_NUM.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMProperty());
			DOC_DATE.setCellValueFactory(cellData -> cellData.getValue().DOC_DATEProperty());
			DOC_PERIOD.setCellValueFactory(cellData -> cellData.getValue().DOC_PERIODProperty());
			PREF.setCellValueFactory(cellData -> cellData.getValue().PREFProperty());
			DOC_AGENCY.setCellValueFactory(cellData -> cellData.getValue().DOC_AGENCYProperty());
			DOC_SUBDIV.setCellValueFactory(cellData -> cellData.getValue().DOC_SUBDIVProperty());
			/**
			 * Форматирование дат
			 */
			DateFormatCol(DOC_DATE);
			DateFormatCol(DOC_PERIOD);
			/**
			 * Инициализация столбцов таблицы-Гражданства
			 */
			// COUNTRY.setCellValueFactory(cellData ->
			// cellData.getValue().COUNTRY_CODEProperty());
			CLONGNAME.setCellValueFactory(cellData -> cellData.getValue().COUNTRY_NAMEProperty());

			// ==== SINGLE? (CHECH BOX) ===
			OSN.setCellValueFactory(new Callback<CellDataFeatures<CUS_CITIZEN, Boolean>, ObservableValue<Boolean>>() {

				@Override
				public ObservableValue<Boolean> call(CellDataFeatures<CUS_CITIZEN, Boolean> param) {
					CUS_CITIZEN person = param.getValue();

					SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.getOSN());

					// Note: singleCol.setOnEditCommit(): Not work for
					// CheckBoxTableCell.

					// When "Single?" column change.
					booleanProp.addListener(new ChangeListener<Boolean>() {

						@Override
						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
								Boolean newValue) {
							CUS_CITIZEN.getSelectionModel().select(person);
							CRUDCitizen(person.getCOUNTRY_CODE(), person.getCOUNTRY_NAME(), (newValue) ? "Y" : "N",
									"{ ? = call MJCUS.EDIT_CUS_CITIZEN(?,?,?,?)}", "edit");
							person.setOSN(newValue);
						}
					});
					return booleanProp;
				}
			});

			OSN.setCellFactory(new Callback<TableColumn<CUS_CITIZEN, Boolean>, //
					TableCell<CUS_CITIZEN, Boolean>>() {
				@Override
				public TableCell<CUS_CITIZEN, Boolean> call(TableColumn<CUS_CITIZEN, Boolean> p) {
					CheckBoxTableCell<CUS_CITIZEN, Boolean> cell = new CheckBoxTableCell<CUS_CITIZEN, Boolean>();
					cell.setAlignment(Pos.CENTER);
					return cell;
				}
			});
			/**
			 * Заполнение данными *****************************
			 */
			CUS cus = INIT_CUS();

			CCUSNAME.setText(cus.getCCUSNAME());
			CCUSNAME_SH.setText(cus.getCCUSNAME_SH());
			ORG_DATE_REG.setValue(cus.getORG_DATE_REG());
			CUS_OGRN.setText(cus.getCUS_OGRN());

			CUS_INN.setText(cus.getCUS_INN());
			CUS_KPP.setText(cus.getCUS_KPP());

			ForAddFioAndId = cus;

			// тип клиента
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from NT_CLI_TYPES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<NT_CLI_TYPES> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_CLI_TYPES list = new NT_CLI_TYPES();
					list.setNAME(rs.getString("NAME"));
					list.setCODE(rs.getLong("CODE"));
					areas.add(list);
				}
				CUS_TYPE.setItems(areas);
				CombCusType(CUS_TYPE);
				rs.close();
				sqlStatement.close();
				for (NT_CLI_TYPES value : CUS_TYPE.getItems()) {
					if (value.getCODE().equals(cus.getCUS_TYPE())) {
						CUS_TYPE.getSelectionModel().select(value);
						break;
					}
				}
			}
			// На абхазском
			// 05.03.2021
			{
				AB_FIRST_NAME.setText(cus.getAB_FIRST_NAME());
				AB_MIDDLE_NAME.setText(cus.getAB_MIDDLE_NAME());
				AB_LAST_NAME.setText(cus.getAB_LAST_NAME());
				AB_PLACE_BIRTH.setText(cus.getAB_PLACE_BIRTH());
			}

			// страна рождения
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from COUNTRIES order by NAME";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<COUNTRIES> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					COUNTRIES countryes = new COUNTRIES();
					countryes.setCODE(rs.getLong("CODE"));
					countryes.setNAME(rs.getString("NAME"));
					nationals.add(countryes);
				}
				rs.close();
				sqlStatement.close();

//				FilteredList<COUNTRIES> filterednationals = new FilteredList<COUNTRIES>(nationals);
//				CombCountry.getEditor().textProperty()
//						.addListener(new InputFilter<COUNTRIES>(CombCountry, filterednationals, false));

				CombCountry.setItems(nationals);

				FxUtilTest.getComboBoxValue(CombCountry);
				FxUtilTest.autoCompleteComboBoxPlus(CombCountry, (typedText, itemToCompare) -> itemToCompare.getNAME()
						.toLowerCase().contains(typedText.toLowerCase()));

				CombCountry(CombCountry);

				for (COUNTRIES value : CombCountry.getItems()) {
					if (value.getNAME().equals(cus.getCCUS_OK_SM())) {
						CombCountry.getSelectionModel().select(value);
						break;
					}
				}
			}

			// страна
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from COUNTRIES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<COUNTRIES> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					COUNTRIES countryes = new COUNTRIES();
					countryes.setCODE(rs.getLong("CODE"));
					countryes.setNAME(rs.getString("NAME"));
					nationals.add(countryes);
				}
				rs.close();
				sqlStatement.close();
//				FilteredList<COUNTRIES> filterednationals = new FilteredList<COUNTRIES>(nationals);
//				CombCountryAddr.getEditor().textProperty()
//						.addListener(new InputFilter<COUNTRIES>(CombCountryAddr, filterednationals, false));

				CombCountryAddr.setItems(nationals);

				FxUtilTest.getComboBoxValue(CombCountryAddr);
				FxUtilTest.autoCompleteComboBoxPlus(CombCountryAddr, (typedText, itemToCompare) -> itemToCompare
						.getNAME().toLowerCase().contains(typedText.toLowerCase()));

				CombCountry(CombCountryAddr);

				for (COUNTRIES value : CombCountryAddr.getItems()) {
					if (value.getNAME().equals(cus.getCountry())) {
						CombCountryAddr.getSelectionModel().select(value);
						break;
					}
				}
			}

			for (String value : ICUSOTD.getItems()) {
				if (value.equals(cus.getOTD_NAME())) {
					ICUSOTD.getSelectionModel().select(value);
					break;
				}
			}

			CCUSLAST_NAME.setText(cus.getCCUSLAST_NAME());
			CCUSFIRST_NAME.setText(cus.getCCUSFIRST_NAME());
			CCUSMIDDLE_NAME.setText(cus.getCCUSMIDDLE_NAME());
			CCUSNATIONALITY.setValue(cus.getCCUSNATIONALITY());
			DCUSBIRTHDAY.setValue(cus.getDCUSBIRTHDAY());
			// CCUS_OK_SM.setText(cus.getCCUS_OK_SM());
			// BurthCountry.setText(cus.getBurthCountry());
			CCUSPLACE_BIRTH.setText(cus.getCCUSPLACE_BIRTH());
			/**
			 * Районы
			 */
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from RAION t";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					areas.add(rs.getString(2));
				}
				AREA.setItems(areas);
				for (String value : AREA.getItems()) {
					if (value.equals(cus.getAREA())) {
						AREA.getSelectionModel().select(value);
						break;
					}
				}
				sqlStatement.close();
				rs.close();
			}
			/**
			 * Нас. пункты
			 */
			{
				// SqlMap sql = new SqlMap().load("/SQLCUS.xml");
				// String readRecordSQL = sql.getSql("SelPunctWithAreaFirst");
				PreparedStatement sqlStatement = conn.prepareStatement("select * from NAS_PUNKT t");
				ResultSet rs = sqlStatement.executeQuery();
				ObservableList<String> np = FXCollections.observableArrayList();
				while (rs.next()) {
					np.add(rs.getString(2));
				}
				sqlStatement.close();
				rs.close();

//				FilteredList<String> filterednationals = new FilteredList<String>(np);
//
//				PUNCT_NAME.getEditor().textProperty()
//						.addListener(new InputFilter<String>(PUNCT_NAME, filterednationals, false));

				PUNCT_NAME.setItems(np);

				FxUtilTest.getComboBoxValue(PUNCT_NAME);
				FxUtilTest.autoCompleteComboBoxPlus(PUNCT_NAME,
						(typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

				for (String value : PUNCT_NAME.getItems()) {
					if (value.equals(cus.getINF())) {
						PUNCT_NAME.getSelectionModel().select(value);
						break;
					}
				}
				rs.close();
			}
			/**
			 * Пол
			 */
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from gender t order by case when t.id = " + cus.getCCUSSEX()
						+ " then 1 else 2 end \n";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					combolist.add(rs.getString("sex"));
				}
				CCUSSEX.setItems(combolist);
				CCUSSEX.getSelectionModel().select(0);

				sqlStatement.close();
				rs.close();

			}
			/**
			 * ОТДЕЛЕНИЯ
			 */
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from OTD t order by case when t.cotdname = '" + cus.getOTD_NAME()
						+ "' then 1 else 2 end \n";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					OTD tr = new OTD();
					tr.setCOTDNAME(rs.getString("COTDNAME"));
					combolist.add(rs.getString("COTDNAME"));
				}
				ICUSOTD.setItems(combolist);
				for (String value : ICUSOTD.getItems()) {
					if (value.equals(cus.getOTD_NAME())) {
						ICUSOTD.getSelectionModel().select(value);
						break;
					}
				}

				sqlStatement.close();
				rs.close();

			}
			/**
			 * Адрес
			 */
			{
				CUS_ADDR addr = INIT_CUS_ADDR();
				// CALFA_2.setText(addr.getCOUNTRY());
				// CLONGNAMET.setText(addr.getCLONGNAMET());
				INFR_NAME.setText(addr.getINFR_NAME());
				DOM.setText(addr.getDOM());
				KORP.setText(addr.getKORP());
				KV.setText(addr.getKV());
				AREA_T.setText(addr.getAREA());
				PUNCT_NAME_T.setText(addr.getPUNCT_NAME());
				// населенный пункт
				if (addr.getPUNCT_NAME_NOT_LIST() == 2) {
					PUNCT_NAME_NOT_LIST.setSelected(true);
					PUNCT_NAME.setValue(null);
					PUNCT_NAME.setVisible(false);
					PUNCT_NAME_T.setVisible(true);
				} else if (addr.getPUNCT_NAME_NOT_LIST() == 1) {
					PUNCT_NAME_NOT_LIST.setSelected(false);
					PUNCT_NAME.setVisible(true);
					PUNCT_NAME_T.setVisible(false);
					PUNCT_NAME_T.setText("");
				}
				// район
				if (addr.getAREA_NOT_LIST() == 2) {
					AREA_NOT_LIST.setSelected(true);
					AREA.setValue(null);
					AREA.setVisible(false);
					AREA_T.setVisible(true);
				} else if (addr.getAREA_NOT_LIST() == 1) {
					AREA_NOT_LIST.setSelected(false);
					AREA.setVisible(true);
					AREA_T.setText("");
					AREA_T.setVisible(false);
				}
			}
			/**
			 * Гражданства
			 */
			{
				InitCitizen();
			}

			/**
			 * Документы
			 */
			{
				InitCusDocum();
			}

			/**
			 * Национальность
			 */
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select name from NATIONALITY t order by ID\n";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					nationals.add(rs.getString(1));
				}

				FilteredList<String> filterednationals = new FilteredList<String>(nationals);
				CCUSNATIONALITY.getEditor().textProperty()
						.addListener(new InputFilter<String>(CCUSNATIONALITY, filterednationals, false));

				CCUSNATIONALITY.setItems(filterednationals);

//				FxUtilTest.getComboBoxValue(CCUSNATIONALITY);
//				FxUtilTest.autoCompleteComboBoxPlus(CCUSNATIONALITY,
//						(typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()));

				sqlStatement.close();
				rs.close();
			}

			new ConvConst().FormatDatePiker(ORG_DATE_REG);
			new ConvConst().FormatDatePiker(DOC_DATE_T);
			new ConvConst().FormatDatePiker(DCUSBIRTHDAY);
			new ConvConst().FormatDatePiker(DOC_PERIOD_T);
			
			custype();
			
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Возврат класса CUS
	 * 
	 * @return
	 */
	private CUS INIT_CUS() {
		CUS cus = new CUS();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			PreparedStatement prepStmt = conn
					.prepareStatement(DbUtil.Sql_From_Prop("/ru/psv/mj/notary/client/controller/SQL.properties", "SelCusList"));
			prepStmt.setLong(1, getId());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				cus.setCountry(rs.getString("Country"));
				cus.setICUSNUM(rs.getLong("ICUSNUM"));
				cus.setCCUSNAME(rs.getString("CCUSNAME"));
				cus.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
				cus.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
				cus.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
				cus.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				cus.setDCUSOPEN((rs.getDate("DCUSOPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getTimestamp("DCUSOPEN")), DTFormatter)
						: null);
				cus.setCCUSIDOPEN(rs.getString("CCUSIDOPEN"));
				cus.setCCUSNAME_SH(rs.getString("CCUSNAME_SH"));
				cus.setOTD_NAME(rs.getString("OTD_NAME"));
				cus.setCCUSNATIONALITY(rs.getString("CCUSNATIONALITY2"));
				cus.setCCUSSEX(rs.getLong("CCUSSEX"));
				cus.setCCUSPLACE_BIRTH(rs.getString("CCUSPLACE_BIRTH"));
				cus.setCCUS_OK_SM(rs.getString("CCUS_OK_SM"));
				cus.setCITY(rs.getString("CITY2"));
				cus.setAREA(rs.getString("AREA"));
				cus.setINF(rs.getString("INF"));
				cus.setBurthCountry(rs.getString("BurthCountry"));

				cus.setAB_FIRST_NAME(rs.getString("AB_FIRST_NAME"));
				cus.setAB_MIDDLE_NAME(rs.getString("AB_MIDDLE_NAME"));
				cus.setAB_LAST_NAME(rs.getString("AB_LAST_NAME"));
				cus.setAB_PLACE_BIRTH(rs.getString("AB_PLACE_BIRTH"));

				cus.setCUS_TYPE(rs.getLong("CUS_TYPE"));
				cus.setCUS_INN(rs.getString("CUS_INN"));
				cus.setCUS_KPP(rs.getString("CUS_KPP"));
				cus.setCUS_OGRN(rs.getString("CUS_OGRN"));
				cus.setORG_DATE_REG((rs.getDate("ORG_DATE_REG") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("ORG_DATE_REG")), formatter) : null);

				cus_list.add(cus);
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return cus;
	}

	/**
	 * Возврат адресов
	 * 
	 * @return
	 */
	private CUS_ADDR INIT_CUS_ADDR() {
		CUS_ADDR addr = new CUS_ADDR();
		try {
			Main.logger = Logger.getLogger(getClass());

			SqlMap sql = new SqlMap().load("/ru/psv/mj/zags/doc/cus/SQL.xml");
			String CUS_ADDR_LIST = sql.getSql("CUS_ADDR_LIST");
			PreparedStatement prepStmt = conn.prepareStatement(CUS_ADDR_LIST);
			prepStmt.setLong(1, getId());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS_ADDR> adrlist = FXCollections.observableArrayList();
			while (rs.next()) {
				addr.setICUSNUM(rs.getLong("ICUSNUM"));
				addr.setID_ADDR(rs.getLong("ID_ADDR"));
				addr.setCOUNTRY(rs.getString("COUNTRY"));
				addr.setPOST_INDEX(rs.getString("POST_INDEX"));
				addr.setAREA(rs.getString("AREA"));
				addr.setCITY(rs.getString("CITY"));
				addr.setPUNCT_NAME(rs.getString("PUNCT_NAME"));
				addr.setCITY_TYPE(rs.getString("CITY_TYPE"));
				addr.setAREA_TYPE(rs.getString("AREA_TYPE"));
				addr.setINFR_NAME(rs.getString("INFR_NAME"));
				addr.setDOM(rs.getString("DOM"));
				addr.setPUNCT_TYPE(rs.getString("PUNCT_TYPE"));
				addr.setKORP(rs.getString("KORP"));
				addr.setSTROY(rs.getString("STROY"));
				addr.setINFR_TYPE(rs.getString("INFR_TYPE"));
				addr.setKV(rs.getString("KV"));
				addr.setOFFICE(rs.getString("OFFICE"));
				addr.setPORCH(rs.getString("PORCH"));
				addr.setCLONGNAMET(rs.getString("CLONGNAMET"));
				addr.setAREA_NOT_LIST(rs.getLong("AREA_NOT_LIST"));
				addr.setPUNCT_NAME_NOT_LIST(rs.getLong("PUNCT_NAME_NOT_LIST"));
				adrlist.add(addr);
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return addr;
	}

	/**
	 * Редактировать документ кнопка
	 * 
	 * @param event
	 */
	@FXML
	void BtEditCitizen(ActionEvent event) {
		try {
			if (CUS_CITIZEN.getItems().size() == 1) {
				CUS_CITIZEN.getSelectionModel().select(0);
			}
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				Main.logger = Logger.getLogger(getClass());
				CitizenList("edit");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить документ кнопка
	 * 
	 * @param event
	 */
	@FXML
	void BtDelCitizen(ActionEvent event) {
		try {
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				CUS_CITIZEN cd = CUS_CITIZEN.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("delete from cus_citizen where id = ?");
				delete.setLong(1, cd.getID());
				delete.executeUpdate();
				delete.close();
				InitCitizen();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Добавить документ кнопка
	 * 
	 * @param event
	 */
	@FXML
	void BtAddCitizen(ActionEvent event) {
		try {
			CitizenList("add");
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить документ кнопка
	 * 
	 * @param event
	 */
	@FXML
	void BtDelDocum(ActionEvent event) {
		try {
			if (CUS_DOCUM.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("delete from cus_docum where ID_DOC = ?");
				delete.setLong(1, cd.getID_DOC());
				delete.executeUpdate();
				delete.close();
				/**
				 * Обновление после удаления
				 */
				InitCusDocum();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить гражданство
	 * 
	 * @param event
	 */
	@FXML
	void DelCitizen(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите сначала данные из таблицы!");
			} else {
				CUS_CITIZEN cd = CUS_CITIZEN.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("delete from cus_citizen where id = ?");
				delete.setLong(1, cd.getID());
				delete.executeUpdate();
				delete.close();
				InitCitizen();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Авто расширение
	 * 
	 * @param table
	 */
	public void autoResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column) -> {
			if (column.getText().equals("sess_id")) {
			} else {
				Text t = new Text(column.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					if (column.getCellData(i) != null) {
						t = new Text(column.getCellData(i).toString());
						double calcwidth = t.getLayoutBounds().getWidth();
						if (calcwidth > max) {
							max = calcwidth;
						}
					}
				}
				column.setPrefWidth(max + 10.0d);
			}
		});
	}

	private BooleanProperty Status;

	private LongProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;this.conn.setAutoCommit(false);
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

	public EditCus() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}

	@FXML
	void BRN_BIRTH_ACT(ActionEvent event) {
		try {
			Stage stage_ = (Stage) all_docs.getScene().getWindow();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/birthact/IUBirthAct.fxml"));
			AddBirthAct controller = new AddBirthAct();

			controller.setCusFio(ForAddFioAndId.getCCUSNAME());
			controller.setCusId(ForAddFioAndId.getICUSNUM());

			loader.setController(controller);
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Свидетельство о рождении");
			stage.setResizable(false);
			stage.initOwner(stage_);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						FillAllDocs();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void PATERN_CERT(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) all_docs.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/patern/IUPatern.fxml"));

			AddPatern controller = new AddPatern();
			controller.setConn(conn);
			loader.setController(controller);

			controller.setCusFio(ForAddFioAndId.getCCUSNAME());
			controller.setCusId(ForAddFioAndId.getICUSNUM());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Установление отцовства");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						FillAllDocs();
					}
					// controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void MC_MERCER(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) all_docs.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/mercer/IUMercer.fxml"));

			AddMercer controller = new AddMercer();
			loader.setController(controller);

			controller.setCusFio(ForAddFioAndId.getCCUSNAME());
			controller.setCusId(ForAddFioAndId.getICUSNUM());
			controller.setCusGen(ForAddFioAndId.getCCUSSEX());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Заключение брака");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						FillAllDocs();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DIVORCE_CERT(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) all_docs.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/divorce/IUDivorce.fxml"));

			AddDivorce controller = new AddDivorce();
			loader.setController(controller);

			controller.setCusFio(ForAddFioAndId.getCCUSNAME());
			controller.setCusId(ForAddFioAndId.getICUSNUM());
			controller.setCusGen(ForAddFioAndId.getCCUSSEX());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Расторжение брака");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						FillAllDocs();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DEATH_CERT(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) all_docs.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/death/IUDeath.fxml"));

			AddDeath controller = new AddDeath();
			loader.setController(controller);

			controller.setCusFio(ForAddFioAndId.getCCUSNAME());
			controller.setCusId(ForAddFioAndId.getICUSNUM());
			controller.setCusGen(ForAddFioAndId.getCCUSSEX());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Установление акта о смерти");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						FillAllDocs();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
