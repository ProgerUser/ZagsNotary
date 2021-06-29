package mj.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class PRJ_UPDATE {

	@FXML
	private TextField FilePath;

	@FXML
	private TextField PRJ_PARENT;

	@FXML
	private CheckBox IsFolder;

	@FXML
	private TextField FolderName;

	@FXML
	private Button IUBtn;

	@FXML
	private Button SelectFile;

	@FXML
	void IsFolder(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (IsFolder.isSelected()) {
				SelectFile.setDisable(true);
				FilePath.setDisable(true);
				// --------
				FolderName.setDisable(false);
			} else if (!IsFolder.isSelected()) {
				SelectFile.setDisable(false);
				FilePath.setDisable(false);
				// --------
				FolderName.setDisable(true);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void IUBtn(ActionEvent event) {
		try {
			// проверки
			if (IsFolder.isSelected() & !FolderName.getText().equals("")) {

			} else if (!IsFolder.isSelected() & !FilePath.getText().equals("")) {

			} else {
				Msg.Message("Заполните поля!");
				return;
			}
			// вызвать хранимую процедуру
			CallableStatement callStmt = conn.prepareCall("{ call UPDATE_PRJ.UpdateFileOrFolder(?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			if (!IsFolder.isSelected()) {
				// получить файл
				byte[] bArray = null;
				Path path = Paths.get(FilePath.getText());
				bArray = java.nio.file.Files.readAllBytes(path);
				InputStream is = new ByteArrayInputStream(bArray);
				//long bytes = Files.size(path);
				File f = new File(FilePath.getText());
				long file_size= f.length();
				// параметры
				callStmt.setString(2, FolderName.getText());
				callStmt.setString(3, path.getFileName().toString());
				callStmt.setString(4, "N");
				callStmt.setBlob(5, is, bArray.length);
				callStmt.setLong(6, prj_val.getPRJ_ID());
				callStmt.setLong(7, file_size);
				//Msg.Message(String.valueOf(file_size));
			} else {
				callStmt.setString(2, FolderName.getText());
				callStmt.setString(3, null);
				callStmt.setString(4, "Y");
				callStmt.setNull(5, Types.BLOB);
				callStmt.setLong(6, getId());
				callStmt.setNull(7, Types.INTEGER);
			}

			
			
			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) FolderName.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) FolderName.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();
			DbUtil.Run_Process(conn,getClass().getName());
			IUBtn.setText("Сохранить");

			IsFolder.setDisable(true);
			PRJ_PARENT.setText(String.valueOf(getId()));

			if (prj_val.getIS_FOLDER().equals("Y")) {

				FolderName.setText(prj_val.getPRJ_NAME());
				IsFolder.setSelected(true);

				FolderName.setDisable(false);
				SelectFile.setDisable(true);
				FilePath.setDisable(true);
			} else if (prj_val.getIS_FOLDER().equals("N")) {

				IsFolder.setSelected(false);

				FolderName.setDisable(true);
				SelectFile.setDisable(false);
				FilePath.setDisable(false);
			}

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Строка соединения
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
			props.put("v$session.program",getClass().getName());
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрыть
	 */
	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void SelectFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Выбрать файл");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Exe file", "*.exe"),
				new ExtensionFilter("Jar file", "*.jar"), new ExtensionFilter("DLL file", "*.dll"),
				new ExtensionFilter("SQLITE file", "*.db"), new ExtensionFilter("WORD file", "*.doc*"),
				new ExtensionFilter("ALL Others", "*.*"));
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			FilePath.setText(file.getAbsolutePath());
		}
	}

	private BooleanProperty Status;

	private LongProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	PROJECT prj_val;

	public void setPROJECT(PROJECT value) {
		this.prj_val = value;
	}

	public void setId(Long value) {
		this.Id.set(value);
	}

	public Long getId() {
		return this.Id.get();
	}

	public PRJ_UPDATE() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}

}
