package notary.template.controller;

import java.io.File;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import notary.template.model.NT_TEMPLATE;
import notary.template.model.NT_TEMP_LIST;
import oracle.sql.CLOB;

@SuppressWarnings("deprecation")
public class IUTemplateList {


	private StringProperty type;
	@FXML
	private TextField FILE_PATH;
	@FXML
	private Button OK;

	public IUTemplateList() {
		Main.logger = Logger.getLogger(getClass());
		this.type = new SimpleStringProperty();
	}
	public void settype(String type) {
		this.type.set(type);
	}
	
	NT_TEMPLATE val;
	NT_TEMP_LIST val_list;
	
	public void  setVal(NT_TEMPLATE val) {
		this.val = val;
	}

	public void setVal(NT_TEMP_LIST val_list) {
		this.val_list = val_list;
	}
	public String gettype() {
		return type.get();
	}
	private Connection conn;
	@FXML
	private TextField NAME;
    @FXML
    private TextArea REP_QUERY;
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!NAME.getText().equals("")) {
				if (gettype().equals("I")) {
					PreparedStatement prp = conn
							.prepareStatement("insert into NT_TEMP_LIST (NAME,PARENT,FILE_PATH,REP_QUERY) values (?,?,?,?)");
					prp.setString(1, NAME.getText());
					prp.setInt(2, val.getNT_ID());
					prp.setString(3, FILE_PATH.getText());
					Clob clob = oracle.sql.CLOB.createTemporary(conn, false, CLOB.DURATION_SESSION);
					clob.setString(1, REP_QUERY.getText());
					prp.setClob(4, clob);
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn
							.prepareStatement("update NT_TEMP_LIST set NAME = ?,FILE_PATH=?,REP_QUERY=? where ID = ?");
					prp.setString(1, NAME.getText());
					prp.setString(2, FILE_PATH.getText());
					Clob clob = oracle.sql.CLOB.createTemporary(conn, false, CLOB.DURATION_SESSION);
					clob.setString(1, REP_QUERY.getText());
					prp.setClob(3, clob);
					prp.setInt(4, val_list.getID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddTemplateList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) FILE_PATH.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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

	@FXML
	void AddPath(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбрать файл");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ALL Others", "*.*"),
					new ExtensionFilter("Exe file", "*.exe"), new ExtensionFilter("Jar file", "*.jar"),
					new ExtensionFilter("DLL file", "*.dll"), new ExtensionFilter("SQLITE file", "*.db"),
					new ExtensionFilter("WORD file", "*.doc*"));
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				FILE_PATH.setText(file.getAbsolutePath().substring(
						file.getAbsolutePath().indexOf(System.getenv("MJ_PATH")) + System.getenv("MJ_PATH").length()));
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			if (gettype().equals("U")) {
				NAME.setText(val_list.getNAME());
				FILE_PATH.setText(val_list.getFILE_PATH());
				REP_QUERY.setText(val_list.getREP_QUERY());
				OK.setText("Сохранить");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
