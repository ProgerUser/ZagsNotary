package mj.users;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class Set_Up_Pass {

	String fnk = "{ ? = call MJUsers.Set_Up_Password(?,?,?,?)}";

	public void setFnk(String value) {
		this.fnk = value;
	}

	@FXML
	private PasswordField PASS;
	@FXML
	private PasswordField PASS2;

	@FXML
	private TextField CUSRLOGNAME;

	private BooleanProperty Status;
	private StringProperty Usr;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setUsr(String value) {
		this.Usr.set(value);
	}

	public String getUsr() {
		return this.Usr.get();
	}

	public Set_Up_Pass() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Usr = new SimpleStringProperty();
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) CUSRLOGNAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		dbConnect();
		DBUtil.RunProcess(conn);
		CUSRLOGNAME.setText(getUsr());
	}

	@FXML
	void save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall(fnk);
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.registerOutParameter(2, Types.VARCHAR);
			callStmt.setString(3, CUSRLOGNAME.getText());
			callStmt.setString(4, PASS.getText());
			callStmt.setString(5, PASS2.getText());
			callStmt.execute();
			if (callStmt.getLong(1) == 0) {
				setStatus(true);
				conn.commit();
				Msg.Message("Пароль у пользователя " + CUSRLOGNAME.getText() + ", изменен");
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Msg.Message(callStmt.getString(2));
				callStmt.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
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
			props.put("v$session.program", "Set_Up_Pass");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
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
			DBUtil.LOG_ERROR(e);
		}
	}
}
