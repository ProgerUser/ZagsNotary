package notary.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;

public class ClientsList {

	public ClientsList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private VBox VBOX;
	@FXML
	private XTableView<V_NT_CLI> NT_CLIENTS;
	@FXML
	private XTableColumn<V_NT_CLI, Integer> CLI_ID;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_OPER;
	@FXML
	private XTableColumn<Object, LocalDate> CR_DATE;
	@FXML
	private XTableColumn<V_NT_CLI, String> CR_TIME;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_NAME;

	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_SH_NAME;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_LAST_NAME;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_FIRST_NAME;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_MIDDLE_NAME;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_TYPE;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_OGRN;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_INN;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_KPP;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_PLACE_BIRTH;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_GENDER;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_BIRTH_COUNTRY;
	@FXML
	private XTableColumn<Object, LocalDate> CLI_BR_DATE;

	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_COUNTRY;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_RAION;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_NAS_PUNKT;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_STREET;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_HOME;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_CORP;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_ADR_KV;

	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_DOC_TYPE;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_DOC_SERIA;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_DOC_NUMBER;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_DOC_AGENCY;
	@FXML
	private XTableColumn<Object, LocalDate> CLI_DOC_START;
	@FXML
	private XTableColumn<Object, LocalDate> CLI_DOC_END;
	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_DOC_SUBDIV;

	@FXML
	void Add(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) NT_CLIENTS.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/client/IUClients.fxml"));

			AddClients controller = new AddClients();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить новую запись");
			stage.initOwner(stage_);
			stage.setResizable(true);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					controller.dbDisconnect();
					if (controller.getStatus()) {
						Init();
					}
				}
			});
			stage.showAndWait();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		try {
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void Edit() {
		try {
			V_NT_CLI val = NT_CLIENTS.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_CLIENTS.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/client/IUClients.fxml"));

				EditClients controller = new EditClients();
				controller.setConn(conn, val);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать: " + val.getCLI_NAME());
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						try {
							if (controller.getStatus()) {
								conn.commit();
								Init();
							} else {
								conn.rollback();
							}
						} catch (Exception e) {
							DBUtil.LOG_ERROR(e);
						}
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	@FXML
	void Edit(ActionEvent event) {
		Edit();
	}

	@FXML
	void Refresh(ActionEvent event) {
		try {
			Init();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			// Соединение
			dbConnect();
			// Двойной щелчок по строке для открытия документа
			NT_CLIENTS.setRowFactory(tv -> {
				TableRow<V_NT_CLI> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit();
					}
				});
				return row;
			});
			// Кнопки управления фильтром
			VBOX.getChildren().add(createOptionPane(NT_CLIENTS));
			// Поля
			CLI_ID.setCellValueFactory(cellData -> cellData.getValue().CLI_IDProperty().asObject());
			CLI_OPER.setCellValueFactory(cellData -> cellData.getValue().CLI_OPERProperty());
			CR_DATE.setCellValueFactory(cellData -> ((V_NT_CLI) cellData.getValue()).CR_DATEProperty());
			CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
			CLI_NAME.setCellValueFactory(cellData -> cellData.getValue().CLI_NAMEProperty());
			CLI_SH_NAME.setCellValueFactory(cellData -> cellData.getValue().CLI_SH_NAMEProperty());
			CLI_LAST_NAME.setCellValueFactory(cellData -> cellData.getValue().CLI_LAST_NAMEProperty());
			CLI_FIRST_NAME.setCellValueFactory(cellData -> cellData.getValue().CLI_FIRST_NAMEProperty());
			CLI_MIDDLE_NAME.setCellValueFactory(cellData -> cellData.getValue().CLI_MIDDLE_NAMEProperty());
			CLI_TYPE.setCellValueFactory(cellData -> cellData.getValue().CLI_TYPE_SProperty());
			CLI_OGRN.setCellValueFactory(cellData -> cellData.getValue().CLI_OGRNProperty());
			CLI_INN.setCellValueFactory(cellData -> cellData.getValue().CLI_INNProperty());
			CLI_KPP.setCellValueFactory(cellData -> cellData.getValue().CLI_KPPProperty());
			CLI_PLACE_BIRTH.setCellValueFactory(cellData -> cellData.getValue().CLI_PLACE_BIRTHProperty());
			CLI_GENDER.setCellValueFactory(cellData -> cellData.getValue().CLI_GENDER_SProperty());
			CLI_BIRTH_COUNTRY.setCellValueFactory(cellData -> cellData.getValue().CLI_BIRTH_COUNTRY_SProperty());
			CLI_BR_DATE.setCellValueFactory(cellData -> ((V_NT_CLI) cellData.getValue()).CLI_BR_DATEProperty());
			CLI_ADR_COUNTRY.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_COUNTRY_SProperty());
			CLI_ADR_RAION.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_RAION_SProperty());
			CLI_ADR_NAS_PUNKT.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_NAS_PUNKT_SProperty());
			CLI_ADR_STREET.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_STREETProperty());
			CLI_ADR_HOME.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_HOMEProperty());
			CLI_ADR_CORP.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_CORPProperty());
			CLI_ADR_KV.setCellValueFactory(cellData -> cellData.getValue().CLI_ADR_KVProperty());
			CLI_DOC_TYPE.setCellValueFactory(cellData -> cellData.getValue().CLI_DOC_TYPE_SProperty());
			CLI_DOC_SERIA.setCellValueFactory(cellData -> cellData.getValue().CLI_DOC_SERIAProperty());
			CLI_DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().CLI_DOC_NUMBERProperty());
			CLI_DOC_AGENCY.setCellValueFactory(cellData -> cellData.getValue().CLI_DOC_AGENCYProperty());
			CLI_DOC_START.setCellValueFactory(cellData -> ((V_NT_CLI) cellData.getValue()).CLI_DOC_STARTProperty());
			CLI_DOC_END.setCellValueFactory(cellData -> ((V_NT_CLI) cellData.getValue()).CLI_DOC_ENDProperty());
			CLI_DOC_SUBDIV.setCellValueFactory(cellData -> cellData.getValue().CLI_DOC_SUBDIVProperty());
			// Фильтр
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			CLI_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			CLI_OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			CLI_NAME.setColumnFilter(new PatternColumnFilter<>());
			CLI_SH_NAME.setColumnFilter(new PatternColumnFilter<>());
			CLI_LAST_NAME.setColumnFilter(new PatternColumnFilter<>());
			CLI_FIRST_NAME.setColumnFilter(new PatternColumnFilter<>());
			CLI_MIDDLE_NAME.setColumnFilter(new PatternColumnFilter<>());
			CLI_TYPE.setColumnFilter(new PatternColumnFilter<>());
			CLI_OGRN.setColumnFilter(new PatternColumnFilter<>());
			CLI_INN.setColumnFilter(new PatternColumnFilter<>());
			CLI_KPP.setColumnFilter(new PatternColumnFilter<>());
			CLI_PLACE_BIRTH.setColumnFilter(new PatternColumnFilter<>());
			CLI_GENDER.setColumnFilter(new PatternColumnFilter<>());
			CLI_BIRTH_COUNTRY.setColumnFilter(new PatternColumnFilter<>());
			CLI_BR_DATE.setColumnFilter(new DateColumnFilter<>());
			CLI_ADR_COUNTRY.setColumnFilter(new PatternColumnFilter<>());
			CLI_ADR_RAION.setColumnFilter(new PatternColumnFilter<>());
			CLI_ADR_NAS_PUNKT.setColumnFilter(new PatternColumnFilter<>());
			CLI_ADR_STREET.setColumnFilter(new PatternColumnFilter<>());
			CLI_ADR_HOME.setColumnFilter(new PatternColumnFilter<>());
			CLI_ADR_CORP.setColumnFilter(new PatternColumnFilter<>());
			CLI_ADR_KV.setColumnFilter(new PatternColumnFilter<>());
			CLI_DOC_TYPE.setColumnFilter(new PatternColumnFilter<>());
			CLI_DOC_SERIA.setColumnFilter(new PatternColumnFilter<>());
			CLI_DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			CLI_DOC_AGENCY.setColumnFilter(new PatternColumnFilter<>());
			CLI_DOC_START.setColumnFilter(new DateColumnFilter<>());
			CLI_DOC_END.setColumnFilter(new DateColumnFilter<>());
			CLI_DOC_SUBDIV.setColumnFilter(new PatternColumnFilter<>());
			// Форматирование
			new ConvConst().TableColumnDate(CR_DATE);
			new ConvConst().TableColumnDate(CLI_BR_DATE);
			new ConvConst().TableColumnDate(CLI_DOC_START);
			new ConvConst().TableColumnDate(CLI_DOC_END);
			// Заполнение данными
			Init();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "ClientsList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void Init() {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_CLI t");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_CLI> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_CLI list = new V_NT_CLI();
				list.setNOT_NAME(rs.getString("NOT_NAME"));
				list.setCLI_OPER(rs.getString("CLI_OPER"));
				list.setCLI_DATE_REG((rs.getDate("CLI_DATE_REG") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DATE_REG")), formatter) : null);
				list.setCLI_ADR_STREET(rs.getString("CLI_ADR_STREET"));
				list.setCLI_DOC_TYPE(rs.getInt("CLI_DOC_TYPE"));
				list.setCLI_FIRST_NAME(rs.getString("CLI_FIRST_NAME"));
				list.setCLI_ADR_COUNTRY(rs.getInt("CLI_ADR_COUNTRY"));
				list.setCLI_ADR_RAION(rs.getInt("CLI_ADR_RAION"));
				list.setCLI_DOC_AGENCY(rs.getString("CLI_DOC_AGENCY"));
				list.setCLI_DOC_START((rs.getDate("CLI_DOC_START") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DOC_START")), formatter) : null);
				list.setCLI_DOC_END((rs.getDate("CLI_DOC_END") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DOC_END")), formatter) : null);
				list.setCLI_PLACE_BIRTH(rs.getString("CLI_PLACE_BIRTH"));
				list.setCLI_TYPE_S(rs.getString("CLI_TYPE_S"));
				list.setCLI_ADR_RAION_S(rs.getString("CLI_ADR_RAION_S"));
				list.setCLI_ADR_NAS_PUNKT(rs.getInt("CLI_ADR_NAS_PUNKT"));
				list.setCLI_DOC_SERIA(rs.getString("CLI_DOC_SERIA"));
				list.setCLI_ADR_NAS_PUNKT_T(rs.getString("CLI_ADR_NAS_PUNKT_T"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCLI_ADR_HOME(rs.getString("CLI_ADR_HOME"));
				list.setCLI_LAST_NAME(rs.getString("CLI_LAST_NAME"));
				list.setCLI_KPP(rs.getString("CLI_KPP"));
				list.setCLI_ADR_RAION_T(rs.getString("CLI_ADR_RAION_T"));
				list.setCLI_DOC_SUBDIV(rs.getString("CLI_DOC_SUBDIV"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCLI_ADR_NAS_PUNKT_S(rs.getString("CLI_ADR_NAS_PUNKT_S"));
				list.setCLI_NAME(rs.getString("CLI_NAME"));
				list.setCLI_MIDDLE_NAME(rs.getString("CLI_MIDDLE_NAME"));
				list.setCLI_NOTARY(rs.getInt("CLI_NOTARY"));
				list.setCLI_ADR_CORP(rs.getString("CLI_ADR_CORP"));
				list.setCLI_GENDER_S(rs.getString("CLI_GENDER_S"));
				list.setCLI_DATE((rs.getDate("CLI_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DATE")), formatter)
						: null);
				list.setCLI_BR_DATE((rs.getDate("CLI_BR_DATE") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_BR_DATE")), formatter) : null);
				list.setCLI_ADR_COUNTRY_S(rs.getString("CLI_ADR_COUNTRY_S"));
				list.setRAION_NOT_LIST(rs.getInt("RAION_NOT_LIST"));
				list.setCLI_ADR_KV(rs.getString("CLI_ADR_KV"));
				list.setCLI_SH_NAME(rs.getString("CLI_SH_NAME"));
				list.setCLI_GENDER(rs.getInt("CLI_GENDER"));
				list.setCLI_INN(rs.getString("CLI_INN"));
				list.setCLI_TYPE(rs.getInt("CLI_TYPE"));
				list.setCLI_DOC_NUMBER(rs.getString("CLI_DOC_NUMBER"));
				list.setCLI_BIRTH_COUNTRY(rs.getInt("CLI_BIRTH_COUNTRY"));
				list.setCLI_BIRTH_COUNTRY_S(rs.getString("CLI_BIRTH_COUNTRY_S"));
				list.setCLI_DOC_TYPE_S(rs.getString("CLI_DOC_TYPE_S"));
				list.setNAS_P_NOT_LIST(rs.getInt("NAS_P_NOT_LIST"));
				list.setCLI_ID(rs.getInt("CLI_ID"));
				list.setCLI_OGRN(rs.getString("CLI_OGRN"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			NT_CLIENTS.setItems(dlist);

			TableFilter<V_NT_CLI> tableFilter = TableFilter.forTableView(NT_CLIENTS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");
		CheckBox filterVisible = new CheckBox("Показать фильтр");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());
		CheckBox menuButtonVisible = new CheckBox("Показать кнопку меню");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());
		CheckBox firstFilterable = new CheckBox("Фильтруемый первый столбец");
		firstFilterable.selectedProperty().bindBidirectional(CLI_ID.filterableProperty());
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

	void onclose() {
		Stage stage = (Stage) NT_CLIENTS.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
