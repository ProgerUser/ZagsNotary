package notary.doc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;

public class AddNotaryDoc {

	@FXML private ComboBox<V_NT_TEMP_LIST> VAL_NT_VALUE;
	@FXML private TableView<V_NT_DOC_PRM> nt_temp_param_val;
	@FXML private TableColumn<V_NT_DOC_PRM, Integer> id;
	@FXML private TableColumn<V_NT_DOC_PRM, String> name;
	@FXML private TableColumn<V_NT_DOC_PRM, String> value;
	@FXML private ScrollPane scroll;

	@FXML
	void AddParam(ActionEvent event) {
		try {
			V_NT_DOC_PRM val = nt_temp_param_val.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) scroll.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/doc/IUParam.fxml"));

				AddParam controller = new AddParam();
				controller.setConn(conn, val);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Добавить");
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
//	DBUtil.SqlFromProp("notary/doc/SQL.properties", "NtParamVal")
	void Init(Integer id) {
		try {
			{
				PreparedStatement prp = conn.prepareStatement("delete from NT_TEMP_PARAM_VAL_TEMP");
				prp.executeUpdate();
				prp.close();
				conn.commit();
			}
			PreparedStatement prepStmt = conn
					.prepareStatement("select * from V_NT_DOC_PRM t where t.PRM_TMP_ID = ?");
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_DOC_PRM> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_DOC_PRM list = new V_NT_DOC_PRM();
				list.setPRM_TMP_ID(rs.getInt("PRM_TMP_ID"));
				list.setPRM_NAME(rs.getString("PRM_NAME"));
				list.setPRM_TYPE(rs.getInt("PRM_TYPE"));
				list.setVAL_PRM_ID(rs.getInt("VAL_PRM_ID"));
				list.setVAL_NT_VALUE(rs.getString("VAL_NT_VALUE"));
				list.setPRM_ID(rs.getInt("PRM_ID"));
				list.setVAL_NT_DOC(rs.getInt("VAL_NT_DOC"));
				list.setPRM_SQL(rs.getString("PRM_SQL"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			nt_temp_param_val.setItems(dlist);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	@FXML
	void Cencel(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DeleteParam(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void EditParam(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void VAL_NT_VALUE(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val = VAL_NT_VALUE.getSelectionModel().getSelectedItem();
			if(val != null) {
				Init(val.getID());
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public AddNotaryDoc() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			scroll.setFitToHeight(true);
			scroll.setFitToWidth(true);
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from V_NT_TEMP_LIST");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<V_NT_TEMP_LIST> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					V_NT_TEMP_LIST list = new V_NT_TEMP_LIST();
					list.setPARENT(rs.getInt("PARENT"));
					list.setNAMES(rs.getString("NAMES"));
					list.setNAME(rs.getString("NAME"));
					list.setFILE_PATH(rs.getString("FILE_PATH"));
					list.setID(rs.getInt("ID"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				FilteredList<V_NT_TEMP_LIST> filterednationals = new FilteredList<V_NT_TEMP_LIST>(combolist);
				VAL_NT_VALUE.getEditor().textProperty()
						.addListener(new InputFilter<V_NT_TEMP_LIST>(VAL_NT_VALUE, filterednationals, false));
				VAL_NT_VALUE.setItems(combolist);
				convert_VAL_NT_VALUE(VAL_NT_VALUE);
				rs.close();
			}
			id.setCellValueFactory(cellData -> cellData.getValue().PRM_IDProperty().asObject());
			name.setCellValueFactory(cellData -> cellData.getValue().PRM_NAMEProperty());
			value.setCellValueFactory(cellData -> cellData.getValue().VAL_NT_VALUEProperty());
			Main.autoResizeColumns(nt_temp_param_val);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void convert_VAL_NT_VALUE(ComboBox<V_NT_TEMP_LIST> cmbbx) {
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
		Stage stage = (Stage) VAL_NT_VALUE.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
