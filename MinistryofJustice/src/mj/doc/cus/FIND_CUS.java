package mj.doc.cus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.DateColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.TitledBorderPane;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;

/**
 * Выбор гражданина из документов <br>
 * 28.11.2020-Сздание
 * 
 * @author Said
 *
 */
public class FIND_CUS {

	@FXML
	private XTableColumn<VCUS, String> COUNTRY_NAME;

	@FXML
	private XTableColumn<VCUS, String> NAME;

	/**
	 * Поле С
	 */
	@FXML
	private DatePicker DT1;

	/**
	 * Поле ПО
	 */
	@FXML
	private DatePicker DT2;

	@FXML
	private XTableColumn<VCUS, String> CCUSIDOPEN;
	
	/**
	 * Таблица Граждан
	 */
	@FXML
	private XTableView<VCUS> CUSLIST;

	/**
	 * Серия документа
	 */
	@FXML
	private XTableColumn<VCUS, String> DOC_SER;

	/**
	 * Отчество
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSMIDDLE_NAMET;

	@FXML
	private XTableColumn<VCUS, String> CR_TIME;

	@FXML
	private XTableColumn<Object, LocalDate> CR_DATE;
	
	/**
	 * Фамилия
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSLAST_NAMET;

	/**
	 * Дата рождения
	 */
	@FXML
	private XTableColumn<VCUS, LocalDate> DCUSBIRTHDAYT;

	/**
	 * Район
	 */
	@FXML
	private XTableColumn<VCUS, String> AREA;

	/**
	 * Страна
	 */
	@FXML
	private XTableColumn<VCUS, String> COUNTRY;

	/**
	 * Тип документа
	 */
	@FXML
	private XTableColumn<VCUS, String> ID_DOC_TP;

	/**
	 * Пол
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSSEX;

	/**
	 * Дом
	 */
	@FXML
	private XTableColumn<VCUS, String> DOM;

	/**
	 * Квартира
	 */
	@FXML
	private XTableColumn<VCUS, String> KV;

	/**
	 * Номер документа
	 */
	@FXML
	private XTableColumn<VCUS, String> DOC_NUM;

	/**
	 * Город
	 */
	@FXML
	private XTableColumn<VCUS, String> CITY;

	/**
	 * Орган выдавший документ
	 */
	@FXML
	private XTableColumn<VCUS, String> DOC_SUBDIV;

	/**
	 * Название инфраструктуры-улицы
	 */
	@FXML
	private XTableColumn<VCUS, String> INFR_NAME;

	/**
	 * Дата окончания документа
	 */
	@FXML
	private XTableColumn<VCUS, LocalDate> DOC_PERIOD;

	/**
	 * Дата выдачи документа
	 */
	@FXML
	private XTableColumn<VCUS, LocalDate> DOC_DATE;

	/**
	 * Имя фильтр
	 */
	@FXML
	private TextField CCUSFIRST_NAME;

	/**
	 * Фамилия фильтр
	 */
	@FXML
	private TextField CCUSLAST_NAME;

	/**
	 * Отчество фильтр
	 */
	@FXML
	private TextField CCUSMIDDLE_NAME;

	/**
	 * Имя таблица
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSFIRST_NAMET;

	/**
	 * ИД документа
	 */
	@FXML
	private XTableColumn<VCUS, Long> ICUSNUM;

	/**
	 * Обновление Таблицы Граждан
	 * 
	 * @param event
	 */
	@FXML
	void CmRefresh(ActionEvent event) {
		InitVCus(null, null, null, null, null, "null", null);
	}

	/**
	 * Таблица внутри
	 */
	@FXML
	private StackPane StackPane;

	/**
	 * Для выполнения в другом потоке, позволяет не <br>
	 * "замораживать" интерфейс при долгом выполнении запроса
	 * 
	 * @param lname
	 * @param fname
	 * @param mname
	 * @param dt1
	 * @param dt2
	 */
	void Progress(String lname, String fname, String mname, LocalDate dt1, LocalDate dt2) {
		try {
			// Если многопоточность
			StackPane.setDisable(true);
			PB.setVisible(true);
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {
					InitVCus(lname, fname, mname, dt1, dt2, "filter", null);
					return null;
				}
			};
			task.setOnFailed(e -> Msg.Message(task.getException().getMessage()));
			task.setOnSucceeded(e -> BlockMain());
			exec.execute(task);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Результат выполнения процесса
	 */
	void BlockMain() {
		StackPane.setDisable(false);
		PB.setVisible(false);
	}

	/**
	 * Индикатор прогресса
	 */
	@FXML
	private ProgressIndicator PB;

	/**
	 * Для фильтра
	 * 
	 * @param lname Фамилия
	 * @param fname Имя
	 * @param mname Отчество
	 * @param dt1   Дата с
	 * @param dt2   Дата по
	 * @param ID    Клиент
	 */
	private void InitVCus(String lname, String fname, String mname, LocalDate dt1, LocalDate dt2, String type,
			Integer ID) {
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String wlname = "";
			String wfname = "";
			String wmname = "";
			String wdt1 = "";
			String wdt2 = "";
			String retid = "";
			String order = "";

			if (type.equals("filter")) {
				if (!lname.equals("")) {
					wlname = "and upper(VCUS.CCUSLAST_NAME) like '" + lname + "%'\r\n";
				}

				if (!fname.equals("")) {
					wfname = "and upper(VCUS.CCUSFIRST_NAME) like '" + fname + "%'\r\n";
				}

				if (!mname.equals("")) {
					wmname = "and upper(VCUS.CCUSMIDDLE_NAME) like '" + mname + "%'\r\n";
				}

				if (!mname.equals("")) {
					wmname = "and upper(VCUS.CCUSMIDDLE_NAME) like '" + mname + "%'\r\n";
				}

				if (dt1 != null) {
					wdt1 = "and VCUS.DCUSBIRTHDAY >= to_date('" + dt1.format(formatter) + "','dd.mm.yyyy') \r\n";
				}
				if (dt2 != null) {
					wdt2 = "and VCUS.DCUSBIRTHDAY <= to_date('" + dt2.format(formatter) + "','dd.mm.yyyy') \r\n";
				}
			} else if (type.equals("crud")) {
				retid = " and VCUS.ICUSNUM = " + ID;
			} else if (type.equals("edit")) {
				order = " order by case ICUSNUM when " + ID + " then 1 else 2 end\r\n";
			} else {
				order = " order by ICUSNUM desc\r\n";
			}

			String selectStmt = "select * from VCUS where 1=1\r\n" + wlname + wfname + wmname + wdt1 + wdt2 + retid
					+ order;
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VCUS> cus_list = FXCollections.observableArrayList();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			while (rs.next()) {
				VCUS list = new VCUS();

				list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				list.setINFR_NAME(rs.getString("INFR_NAME"));
				list.setDOC_SER(rs.getString("DOC_SER"));
				list.setTM$DCUSOPEN((rs.getDate("TM$DCUSOPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DCUSOPEN")), dateFormatter)
						: null);
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setKV(rs.getString("KV"));
				list.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
				list.setCITY(rs.getString("CITY"));
				list.setDOM(rs.getString("DOM"));
				list.setID_DOC_TP(rs.getString("ID_DOC_TP"));
				list.setCCUSSEX(rs.getString("CCUSSEX"));
				list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
				list.setNAME(rs.getString("NAME"));
				list.setCOUNTRY(rs.getString("COUNTRY"));
				list.setDOC_PERIOD((rs.getDate("DOC_PERIOD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_PERIOD")), formatter) : null);
				list.setAREA(rs.getString("AREA"));
				list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
				list.setICUSNUM(rs.getLong("ICUSNUM"));
				list.setDOC_NUM(rs.getString("DOC_NUM"));
				list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
				list.setDOC_SUBDIV(rs.getString("DOC_SUBDIV"));
				list.setCCUSIDOPEN(rs.getString("CCUSIDOPEN"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter) : null);
				
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			CUSLIST.setItems(cus_list);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<VCUS> tableFilter = TableFilter.forTableView(CUSLIST).apply();
					tableFilter.setSearchStrategy((input, target) -> {
						try {
							return target.toLowerCase().contains(input.toLowerCase());
						} catch (Exception e) {
							return false;
						}
					});
				}
			});

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Только число
	 * 
	 * @param TxtFld
	 */
	void OnlyAlpha(TextField TxtFld) {
		TxtFld.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\sa-zA-Z*")) {
					TxtFld.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
				}
			}
		});
	}

	/**
	 * Сессия
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "FIND_CUS");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Отключить сессию
	 */
	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
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

	/**
	 * {@link LocalDateTime} форматирование {@link TableColumn}
	 * 
	 * @param tc
	 */
	void TableColumnLocalDate(TableColumn<VCUS, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<VCUS, LocalDate> cell = new TableCell<VCUS, LocalDate>() {
				private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						if(item != null) {
							setText(format.format(item));
						}
					}
				}
			};
			return cell;
		});
	}

	/**
	 * Для многопоточности
	 */
	private Executor exec;

	/**
	 * Поле С фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT1(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(), DT1.getValue(),
					DT2.getValue());
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Поле ПО фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT2(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(), DT1.getValue(),
					DT2.getValue());
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Очистить поля
	 * 
	 * @param event
	 */
	@FXML
	void Clear(ActionEvent event) {
		CCUSLAST_NAME.setText("");
		CCUSFIRST_NAME.setText("");
		CCUSMIDDLE_NAME.setText("");
		DT1.setValue(null);
		DT2.setValue(null);
	}

	/**
	 * Быстрый способ заполнения даты
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
	
	@SuppressWarnings("unused")
	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");

		CheckBox filterVisible = new CheckBox("Показать фильтр");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());

		CheckBox menuButtonVisible = new CheckBox("Показать кнопку меню");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());

		CheckBox firstFilterable = new CheckBox("Фильтруемый первый столбец");
		// XTableColumn<VCUS, Long> firstColumn = (XTableColumn<VCUS, Long>)
		// table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(ICUSNUM.filterableProperty());

		CheckBox includeHidden = new CheckBox("Включить скрытые столбцы");
		includeHidden.selectedProperty().bindBidirectional(table.getFilterController().includeHiddenProperty());

		CheckBox andFilters = new CheckBox("Используйте операцию \"И\" для многоколоночного фильтра");
		andFilters.selectedProperty().bindBidirectional(table.getFilterController().andFiltersProperty());

		pane.getChildren().addAll(filterVisible, menuButtonVisible, firstFilterable, includeHidden, andFilters);

		TitledBorderPane p = new TitledBorderPane("Настройки", pane);
		p.getStyleClass().add("top-border-only");
		p.setStyle("-fx-border-insets: 10 0 0 0");
		return p;
	}
	
    @FXML
    private VBox VB;
    
    @FXML
    private TitledPane FILTER;
    
	@FXML
	private BorderPane CUS_BORDER;
	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {

			//VB.getChildren().remove(FILTER);
			
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");

			CCUSIDOPEN.setColumnFilter(new PatternColumnFilter<>());

			CR_DATE.setColumnFilter(new DateColumnFilter<>());

			CR_TIME.setColumnFilter(new PatternColumnFilter<>());

			//CUS_BORDER.setBottom(createOptionPane(CUSLIST));

			ICUSNUM.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));

			CCUSLAST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSFIRST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSMIDDLE_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSLAST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSLAST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSSEX.setColumnFilter(new PatternColumnFilter<>());
			NAME.setColumnFilter(new PatternColumnFilter<>());
			DCUSBIRTHDAYT.setColumnFilter(new DateColumnFilter<>());
			COUNTRY_NAME.setColumnFilter(new PatternColumnFilter<>());
			ID_DOC_TP.setColumnFilter(new PatternColumnFilter<>());
			DOC_SER.setColumnFilter(new PatternColumnFilter<>());
			DOC_NUM.setColumnFilter(new PatternColumnFilter<>());
			DOC_DATE.setColumnFilter(new DateColumnFilter<>());
			DOC_PERIOD.setColumnFilter(new DateColumnFilter<>());
			DOC_SUBDIV.setColumnFilter(new PatternColumnFilter<>());
			COUNTRY.setColumnFilter(new PatternColumnFilter<>());
			AREA.setColumnFilter(new PatternColumnFilter<>());
			CITY.setColumnFilter(new PatternColumnFilter<>());
			INFR_NAME.setColumnFilter(new PatternColumnFilter<>());
			DOM.setColumnFilter(new PatternColumnFilter<>());
			KV.setColumnFilter(new PatternColumnFilter<>());
			
			DateAutoComma(DT1);
			DateAutoComma(DT2);
			
			/**
			 * Для создания многопоточности
			 */
			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});

			/**
			 * Только буквы в фильтре
			 */
			// OnlyAlpha(CCUSLAST_NAME);
			// OnlyAlpha(CCUSFIRST_NAME);
			// OnlyAlpha(CCUSMIDDLE_NAME);

			/**
			 * Создать сессию
			 */
			dbConnect();
			DBUtil.RunProcess(conn);
			/**
			 * Заполнение данными таблицу
			 */
			InitVCus(null, null, null, null, null, "null", null);

			/**
			 * Двойной щелчок по строке
			 */
			CUSLIST.setRowFactory(tv -> {
				TableRow<VCUS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						VCUS cus = CUSLIST.getSelectionModel().getSelectedItem();
						setStatus(true);
						setId(cus.getICUSNUM());
						setFio(cus.getCCUSLAST_NAME() + " " + cus.getCCUSFIRST_NAME() + " " + cus.getCCUSMIDDLE_NAME());
						onclose();
					}
				});
				return row;
			});

			/**
			 * Инициализация столбцов таблицы
			 */
			{
				CCUSIDOPEN.setCellValueFactory(cellData -> cellData.getValue().CCUSIDOPENProperty());

				CR_DATE.setCellValueFactory(cellData -> ((VCUS) cellData.getValue()).CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());

				ICUSNUM.setCellValueFactory(cellData -> cellData.getValue().ICUSNUMProperty().asObject());
				CCUSLAST_NAMET.setCellValueFactory(cellData -> cellData.getValue().CCUSLAST_NAMEProperty());
				CCUSFIRST_NAMET.setCellValueFactory(cellData -> cellData.getValue().CCUSFIRST_NAMEProperty());
				CCUSMIDDLE_NAMET.setCellValueFactory(cellData -> cellData.getValue().CCUSMIDDLE_NAMEProperty());
				DCUSBIRTHDAYT.setCellValueFactory(cellData -> cellData.getValue().DCUSBIRTHDAYProperty());
				ID_DOC_TP.setCellValueFactory(cellData -> cellData.getValue().ID_DOC_TPProperty());
				CCUSSEX.setCellValueFactory(cellData -> cellData.getValue().CCUSSEXProperty());
				DOC_SER.setCellValueFactory(cellData -> cellData.getValue().DOC_SERProperty());
				DOC_NUM.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMProperty());
				DOC_DATE.setCellValueFactory(cellData -> cellData.getValue().DOC_DATEProperty());
				DOC_PERIOD.setCellValueFactory(cellData -> cellData.getValue().DOC_PERIODProperty());
				DOC_SUBDIV.setCellValueFactory(cellData -> cellData.getValue().DOC_SUBDIVProperty());
				COUNTRY.setCellValueFactory(cellData -> cellData.getValue().COUNTRYProperty());
				AREA.setCellValueFactory(cellData -> cellData.getValue().AREAProperty());
				CITY.setCellValueFactory(cellData -> cellData.getValue().CITYProperty());
				INFR_NAME.setCellValueFactory(cellData -> cellData.getValue().INFR_NAMEProperty());
				DOM.setCellValueFactory(cellData -> cellData.getValue().DOMProperty());
				KV.setCellValueFactory(cellData -> cellData.getValue().KVProperty());
				COUNTRY_NAME.setCellValueFactory(cellData -> cellData.getValue().COUNTRY_NAMEProperty());
				NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			}

			/**
			 * Фильтр поля в верхнем регистре
			 */
			{
				UpperCase(CCUSLAST_NAME);
				UpperCase(CCUSFIRST_NAME);
				UpperCase(CCUSMIDDLE_NAME);
			}
			/**
			 * Форматирование Столбца "Дата рождения"
			 */
			{
				TableColumnLocalDate(DCUSBIRTHDAYT);
				TableColumnLocalDate(DOC_DATE);
				TableColumnLocalDate(DOC_PERIOD);
			}

			ICUSNUM.setOnEditCommit(new EventHandler<CellEditEvent<VCUS, Long>>() {
				@Override
				public void handle(CellEditEvent<VCUS, Long> t) {
					((VCUS) t.getTableView().getItems().get(t.getTablePosition().getRow())).setICUSNUM(t.getNewValue());
				}
			});

			/**
			 * Listener при нажатии на строку клиентов
			 */
			CUSLIST.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {

				}
			});

			/**
			 * Поиск при вводе
			 */
			{
				CCUSLAST_NAME.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.length() > 0) {
							Progress(newValue, CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(), DT1.getValue(),
									DT2.getValue());
						} else {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(),
									DT1.getValue(), DT2.getValue());
						}
					}
				});
				CCUSFIRST_NAME.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.length() > 0) {
							Progress(CCUSLAST_NAME.getText(), newValue, CCUSMIDDLE_NAME.getText(), DT1.getValue(),
									DT2.getValue());
						} else {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(),
									DT1.getValue(), DT2.getValue());
						}
					}
				});
				CCUSMIDDLE_NAME.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.length() > 0) {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), newValue, DT1.getValue(),
									DT2.getValue());
						} else {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(),
									DT1.getValue(), DT2.getValue());
						}
					}
				});
			}
			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);
			
			new ConvConst().TableColumnDate(CR_DATE);
			
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Выбрать
	 * 
	 * @param event
	 */
	@FXML
	void Select(ActionEvent event) {
		if (CUSLIST.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			VCUS cus = CUSLIST.getSelectionModel().getSelectedItem();
			setStatus(true);
			setId(cus.getICUSNUM());
			setFio(cus.getCCUSLAST_NAME() + " " + cus.getCCUSFIRST_NAME() + " " + cus.getCCUSMIDDLE_NAME());
			onclose();
		}
	}

	/**
	 * Закрыть
	 * 
	 * @param event
	 */
	@FXML
	void Cencel(ActionEvent event) {
		setStatus(false);
		onclose();
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) CCUSLAST_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private StringProperty Fio;
	private BooleanProperty Status;
	private LongProperty Id;

	public String getFio() {
		return this.Fio.get();
	}

	public void setFio(String value) {
		this.Fio.set(value);
	}

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

	public FIND_CUS() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Fio = new SimpleStringProperty();
		this.Id = new SimpleLongProperty();
	}

}
