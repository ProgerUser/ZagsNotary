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

import com.jyloo.syntheticafx.TitledBorderPane;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;

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
	private XTableColumn<V_NT_DOC, ?> DOC_NUMBER;
	@FXML
	private XTableColumn<V_NT_DOC, ?> OPER;
	@FXML
	private XTableColumn<V_NT_DOC, ?> CR_DATE;
	@FXML
	private XTableColumn<V_NT_DOC, ?> CR_TIME;
	@FXML
	private XTableColumn<V_NT_DOC, ?> NT_TYPE;
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
					controller.dbDisconnect();
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

	@FXML
	void Edit(ActionEvent event) {
		try {

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

	@FXML
	private void initialize() {
		try {
			dbConnect();
			ROOT.setBottom(createOptionPane(NT_DOC));
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
