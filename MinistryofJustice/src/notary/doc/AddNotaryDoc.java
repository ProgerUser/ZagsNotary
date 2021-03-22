package notary.doc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;

public class AddNotaryDoc {

	@FXML
	private ComboBox<V_NT_TEMP_LIST> VAL_NT_VALUE;
	@FXML
	private TableView<?> nt_temp_param_val;
	@FXML
	private TableColumn<?, ?> id;
	@FXML
	private TableColumn<?, ?> name;
	@FXML
	private TableColumn<?, ?> value;
	@FXML
	private ScrollPane scroll;

	@FXML
	void AddParam(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

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
