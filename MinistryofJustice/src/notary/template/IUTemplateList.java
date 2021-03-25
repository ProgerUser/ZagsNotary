package notary.template;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;

public class IUTemplateList {

	private IntegerProperty ID;
	private StringProperty type;
	private StringProperty NAME_;
	private StringProperty FilePath;
	@FXML
	private TextField FILE_PATH;
	@FXML
	private Button OK;

	public IUTemplateList() {
		Main.logger = Logger.getLogger(getClass());
		this.ID = new SimpleIntegerProperty();
		this.type = new SimpleStringProperty();
		this.NAME_ = new SimpleStringProperty();
		this.FilePath = new SimpleStringProperty();
	}

	public void setFilePath(String FilePath) {
		this.FilePath.set(FilePath);
	}

	public String getFilePath() {
		return FilePath.get();
	}

	public void setNAME(String NAME) {
		this.NAME_.set(NAME);
	}

	public String getNAME() {
		return NAME_.get();
	}

	public void settype(String type) {
		this.type.set(type);
	}

	public String gettype() {
		return type.get();
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public Integer getID() {
		return ID.get();
	}

	private Connection conn;
	@FXML
	private TextField NAME;

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
							.prepareStatement("insert into NT_TEMP_LIST (NAME,PARENT,FILE_PATH) values (?,?,?)");
					prp.setString(1, NAME.getText());
					prp.setInt(2, getID());
					prp.setString(3, FILE_PATH.getText());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn
							.prepareStatement("update NT_TEMP_LIST set NAME = ?,FILE_PATH=? where ID = ?");
					prp.setString(1, NAME.getText());
					prp.setString(2, FILE_PATH.getText());
					prp.setInt(3, getID());
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
				NAME.setText(getNAME());
				FILE_PATH.setText(getFilePath());
				OK.setText("Сохранить");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
