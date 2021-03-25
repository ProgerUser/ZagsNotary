package mj.users;

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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class AddUser {

	@FXML
	private Button saveaddusr;

	@FXML
	private PasswordField PASS;

	@FXML
	private TextField CUSRNAME;

	@FXML
	private TextField CUSRLOGNAME;

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

	public AddUser() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) CUSRNAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		dbConnect();
		DBUtil.RunProcess(conn);
		UpperCase(CUSRLOGNAME);
		FirstWUpp(CUSRNAME);
	}

	/**
	 * Первая буква заглавная
	 * 
	 * @param TxtFld
	 */
	void FirstWUpp(TextField TxtFld) {
		TxtFld.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue.length() > 0) {
				TxtFld.setText(upperCaseAllFirst(newValue));
			}
		});
	}

	/**
	 * Первая буква заглавная
	 * 
	 * @param value
	 * @return
	 */
	public static String upperCaseAllFirst(String value) {
		char[] array = value.toCharArray();
		// Uppercase first letter.
		array[0] = Character.toUpperCase(array[0]);
		// Uppercase all letters that follow a whitespace character.
		for (int i = 1; i < array.length; i++) {
			if (Character.isWhitespace(array[i - 1])) {
				array[i] = Character.toUpperCase(array[i]);
			}
		}
		return new String(array);
	}

	/**
	 * В верхнем регистре
	 * 
	 * @param tf
	 */
	void UpperCase(TextField tf) {
		tf.setTextFormatter(new TextFormatter<>((change) -> {
			change.setText(change.getText().toUpperCase());
			return change;
		}));
	}

	@FXML
	void save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.createusr(?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, CUSRLOGNAME.getText());
			callStmt.setString(3, CUSRNAME.getText());
			callStmt.setString(4, PASS.getText());
			callStmt.execute();
			String ret = callStmt.getString(1);
			if (ret.equals("ok")) {
				setStatus(true);
				conn.commit();
				Msg.Message("Пользователь " + CUSRLOGNAME.getText() + " Создан");
				Main.logger.info("Пользователь " + CUSRLOGNAME + " Создан" + "~" + Thread.currentThread().getName());
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) CUSRNAME.getScene().getWindow();
				Msg.ErrorView(stage_, "CreateUser", conn);
				callStmt.close();
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

	/**
	 * Сессия
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
			props.put("v$session.program", "AddUsr");
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
	 * Отключить сессию
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
}
