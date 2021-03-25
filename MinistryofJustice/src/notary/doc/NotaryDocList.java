package notary.doc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;

public class NotaryDocList {

	public NotaryDocList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private BorderPane ROOT;
	@FXML
	private VBox VB;
	@FXML
	private XTableView<V_NT_DOC> NT_DOC;
	@FXML
	private XTableColumn<V_NT_DOC, Integer> ID;
	@FXML
	private XTableColumn<V_NT_DOC, String> DOC_NUMBER;
	@FXML
	private XTableColumn<V_NT_DOC, String> OPER;
	@FXML
	private XTableColumn<Object, LocalDate> CR_DATE;
	@FXML
	private XTableColumn<V_NT_DOC, String> CR_TIME;
	@FXML
	private XTableColumn<V_NT_DOC, String> NT_TYPE;
	@FXML
	private ProgressIndicator PB;

	@FXML
	void Add(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) NT_DOC.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/doc/IUNotary.fxml"));

			AddNotaryDoc controller = new AddNotaryDoc();
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
					if (controller.getStatus()) {
						Init();
						controller.dbDisconnect();
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void Edit() {
		try {
			V_NT_DOC val = NT_DOC.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_DOC.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/doc/IUNotary.fxml"));

				EditNotaryDoc controller = new EditNotaryDoc();
				controller.setConn(conn,val);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать: " + val.getID());
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						if (controller.getStatus()) {
							Init();
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
	void Delete(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		try {
			Edit();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Print(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
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
			dbConnect();
			ROOT.setBottom(createOptionPane(NT_DOC));
			ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			OPER.setCellValueFactory(cellData -> cellData.getValue().OPERProperty());
			CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
			NT_TYPE.setCellValueFactory(cellData -> cellData.getValue().TYPE_NAMEProperty());
			CR_DATE.setCellValueFactory(cellData -> ((V_NT_DOC) cellData.getValue()).CR_DATEProperty());
			// Фильтр
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			NT_TYPE.setColumnFilter(new PatternColumnFilter<>());
			OPER.setColumnFilter(new PatternColumnFilter<>());
			Init();
			// Двойной щелчок по строке для открытия документа
			NT_DOC.setRowFactory(tv -> {
				TableRow<V_NT_DOC> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit();
					}
				});
				return row;
			});
			new ConvConst().TableColumnDate(CR_DATE);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "IUTempParam");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) NT_DOC.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void Init() {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_DOC");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_DOC> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_DOC list = new V_NT_DOC();
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setID(rs.getInt("ID"));
				list.setOPER(rs.getString("OPER"));
				list.setNOTARY(rs.getInt("NOTARY"));
				list.setNT_TYPE(rs.getInt("NT_TYPE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setTYPE_NAME(rs.getString("TYPE_NAME"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			NT_DOC.setItems(dlist);

//			// enable filter support
//			FilteredList filtered = new FilteredList(dlist);
//			// data should be sortable too
//			SortedList sorted = new SortedList(filtered);
//			sorted.comparatorProperty().bind(NT_DOC.comparatorProperty());
//			NT_DOC.setEditable(true);

			TableFilter<V_NT_DOC> tableFilter = TableFilter.forTableView(NT_DOC).apply();
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

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
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
		firstFilterable.selectedProperty().bindBidirectional(ID.filterableProperty());
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
}
