package mj.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

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
import mj.msg.Msg;
import mj.utils.DbUtil;

public class PRJ_ADD {

	@FXML
	private TextField FilePath;

	@FXML
	private TextField PRJ_PARENT;

	@FXML
	private CheckBox IsFolder;

	@FXML
	private TextField FolderName;

	@FXML
	private Button SelectFile;
	
	@FXML
	private Button IUBtn;

	@FXML
	void IsFolder(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (IsFolder.isSelected()) {
				SelectFile.setDisable(true);
				FilePath.setDisable(true);
				// --------
				FolderName.setDisable(false);
				IUBtn.setDisable(false);
			} else if (!IsFolder.isSelected()) {
				SelectFile.setDisable(false);
				FilePath.setDisable(false);
				// --------
				FolderName.setDisable(true);
				IUBtn.setDisable(true);
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
			CallableStatement callStmt = conn.prepareCall("{ call UPDATE_PRJ.AddFileOrFolder(?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			if (!IsFolder.isSelected()) {
				// получить файл
				byte[] bArray = null;
				Path path = Paths.get(FilePath.getText());
				bArray = java.nio.file.Files.readAllBytes(path);
				InputStream is = new ByteArrayInputStream(bArray);
				// long bytes = Files.size(path);

				File f = new File(FilePath.getText());
				long file_size = f.length();
				// параметры
				callStmt.setString(2, FolderName.getText());
				callStmt.setString(3, path.getFileName().toString());
				callStmt.setString(4, "N");
				callStmt.setBlob(5, is, bArray.length);
				callStmt.setLong(6, getId());
				callStmt.setLong(7, file_size);

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
			DbUtil.Run_Process(conn, getClass().getName());
			PRJ_PARENT.setText(String.valueOf(getId()));

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
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрыть
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void SelectFile(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбрать файл");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ALL Others", "*.*"),
					new ExtensionFilter("Exe file", "*.exe"), new ExtensionFilter("Jar file", "*.jar"),
					new ExtensionFilter("DLL file", "*.dll"), new ExtensionFilter("SQLITE file", "*.db"),
					new ExtensionFilter("WORD file", "*.doc*"));
			List<File> file = fileChooser.showOpenMultipleDialog(null);
			if (file != null) {
				for (File fls : file) {
					// вызвать хранимую процедуру
					CallableStatement callStmt = conn.prepareCall("{ call UPDATE_PRJ.AddFileOrFolder(?,?,?,?,?,?,?) }");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					if (!IsFolder.isSelected()) {
						// получить файл
						byte[] bArray = null;
						Path path = Paths.get(fls.getAbsolutePath());
						bArray = java.nio.file.Files.readAllBytes(path);
						InputStream is = new ByteArrayInputStream(bArray);
						// long bytes = Files.size(path);

						File f = new File(fls.getAbsolutePath());
						long file_size = f.length();
						// параметры
						callStmt.setString(2, FolderName.getText());
						callStmt.setString(3, path.getFileName().toString());
						callStmt.setString(4, "N");
						callStmt.setBlob(5, is, bArray.length);
						callStmt.setLong(6, getId());
						callStmt.setLong(7, file_size);

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
					} else {
						conn.rollback();
						setStatus(false);
						Msg.Message(callStmt.getString(1));
						callStmt.close();
					}
				}
				onclose();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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

	public void setId(Long value) {
		this.Id.set(value);
	}

	public Long getId() {
		return this.Id.get();
	}

	public PRJ_ADD() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}

}
