package notary.doc.html.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;
import netscape.javascript.JSObject;
import notary.doc.html.model.NT_TEMP_LIST_PARAM;
import notary.doc.html.model.V_NT_TEMP_LIST;

public class AddDoc {

	public AddDoc() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
	}

	private BooleanProperty status;

	public void setStatus(Boolean status) {
		this.status.set(status);
	}

	public Boolean getStatus() {
		return status.get();
	}

	@FXML
	private VBox root;

	@FXML
	private ComboBox<V_NT_TEMP_LIST> TYPE_NAME;

	@FXML
	private WebView webView;

	@FXML
	void CENCEL(ActionEvent event) {
		onclose();
	}

	void onclose() {
		Stage stage = (Stage) webView.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public class JsToJava {
		public void run(String id) {
			ListVal(id);
		}
	}
	
	void ListVal(String id) {
		try {
			NT_TEMP_LIST_PARAM list = null;
			PreparedStatement prp = conn.prepareStatement("select * from NT_TEMP_LIST_PARAM t ");
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				list = new NT_TEMP_LIST_PARAM();
				list.setPRM_ID(rs.getInt("PRM_ID"));
				list.setPRM_NAME(rs.getString("PRM_NAME"));
				list.setPRM_TMP_ID(rs.getInt("PRM_TMP_ID"));
				list.setPRM_SQL(rs.getString("PRM_SQL"));
				list.setPRM_TYPE(rs.getInt("PRM_TYPE"));
				list.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
			}
			prp.close();
			rs.close();

			Stage stage = new Stage();
			Stage stage_ = (Stage) webView.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/doc/html/view/ParamList.fxml"));

			ParamList controller = new ParamList();
			controller.setQuery(list.getPRM_SQL());
			controller.setConn(conn);
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Список");
			stage.initOwner(stage_);
			stage.setResizable(true);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						webEngine.executeScript("SetValue('" + id + "','" + controller.getName_s() + "')");
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private WebEngine webEngine;

	@FXML
	void TYPE_NAME(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				webEngine = webView.getEngine();
				webEngine.loadContent(val.getHTML_TEMP());
				webEngine.setJavaScriptEnabled(true);
				webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
					@Override
					public void changed(ObservableValue<? extends State> observableValue, State oldState,
							State newState) {
						if (newState == State.SUCCEEDED) {
							JSObject window = (JSObject) webEngine.executeScript("window");
							window.setMember("invoke", new JsToJava());
						}
					}
				});
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DelSelType(ActionEvent event) {
		try {
			TYPE_NAME.getSelectionModel().select(null);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {

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

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddNotaryDoc");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void convert_TYPE_NAME(ComboBox<V_NT_TEMP_LIST> cmbbx) {
		cmbbx.setConverter(new StringConverter<V_NT_TEMP_LIST>() {
			@Override
			public String toString(V_NT_TEMP_LIST product) {
				return product != null ? product.getNAMES() : null;
			}

			@Override
			public V_NT_TEMP_LIST fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getNAMES().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			webView.getStyleClass().add("mylistview");
			webView.getStylesheets().add("/ScrPane.css");
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from V_NT_TEMP_LIST");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<V_NT_TEMP_LIST> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					V_NT_TEMP_LIST list = new V_NT_TEMP_LIST();
					list.setPARENT(rs.getInt("PARENT"));
					list.setNAMES(rs.getString("NAMES"));
					list.setNAME(rs.getString("NAME"));
					if (rs.getClob("HTML_TEMP") != null) {
						list.setHTML_TEMP(new ConvConst().ClobToString(rs.getClob("HTML_TEMP")));
					}
					list.setID(rs.getInt("ID"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				FilteredList<V_NT_TEMP_LIST> filterednationals = new FilteredList<V_NT_TEMP_LIST>(combolist);
				TYPE_NAME.getEditor().textProperty()
						.addListener(new InputFilter<V_NT_TEMP_LIST>(TYPE_NAME, filterednationals, false));
				TYPE_NAME.setItems(combolist);
				convert_TYPE_NAME(TYPE_NAME);
				rs.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
