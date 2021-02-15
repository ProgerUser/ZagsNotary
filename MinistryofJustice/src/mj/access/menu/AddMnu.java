package mj.access.menu;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class AddMnu {

	@FXML
	private TextField MNU_PARENT;

	@FXML
	private TextField MNU_NAME;

	@FXML
	void Add(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall("{ call MJUsers.AddOdbMenuItem(?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			if (!MNU_PARENT.getText().equals("")) {
				callStmt.setInt(2, Integer.valueOf(MNU_PARENT.getText()));
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			callStmt.setString(3, MNU_NAME.getText());

			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				callStmt.close();
				
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) MNU_NAME.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	void onclose() {
		Stage stage = (Stage) MNU_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Save(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			try {
				Main.logger = Logger.getLogger(getClass());
				CallableStatement callStmt = conn.prepareCall("{ call MJUsers.AddOdbMenuItem(?,?,?) }");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				if (!MNU_PARENT.getText().equals("")) {
					callStmt.setInt(2, Integer.valueOf(MNU_PARENT.getText()));
				} else {
					callStmt.setNull(2, java.sql.Types.INTEGER);
				}
				callStmt.setString(3, MNU_NAME.getText());

				callStmt.execute();

				if (callStmt.getString(1) == null) {
					conn.commit();
					setStatus(true);
					callStmt.close();
					onclose();
				} else {
					conn.rollback();
					setStatus(false);
					Stage stage_ = (Stage) MNU_NAME.getScene().getWindow();
					Msg.MessageBox(callStmt.getString(1), stage_);
					callStmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Msg.Message(ExceptionUtils.getStackTrace(e));
				Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
		}
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();

			MNU_PARENT.setText(String.valueOf(parantid));

			// FirstWUpp(MNU_NAME);

		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������ ����������
	 */
	private Connection conn;

	/**
	 * ������� ������
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddActionChild");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������
	 */
	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	void FirstWUpp(TextField TxtFld) {
		TxtFld.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				TxtFld.setText(upperCaseAllFirst(newValue));
			}
		});
	}

	public static String upperCaseAllFirst(String value) {
		char[] array = value.toCharArray();
		if (!value.equals("")) {
			// Uppercase first letter.
			array[0] = Character.toUpperCase(array[0]);
			// Uppercase all letters that follow a whitespace character.
			for (int i = 1; i < array.length; i++) {
				if (Character.isWhitespace(array[i - 1])) {
					array[i] = Character.toUpperCase(array[i]);
				}
			}
		}
		return new String(array);
	}

	private BooleanProperty Status;

	private IntegerProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Integer value) {
		this.Id.set(value);
	}

	public Integer getId() {
		return this.Id.get();
	}

	Integer parantid;

	public void setParantid(Integer ID) {
		this.parantid = ID;
	}

	public AddMnu() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
		this.parantid = new Integer(0);
	}

}
