package mj.access.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class AddAction {
	
	@FXML
	private TextField ACT_PARENT;

	@FXML
	private TextField ACT_PARENT_NEW;

	@FXML
	private TextField ACT_NAME;

	@FXML
	void Add(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall("{ call MJUsers.AddOdbActionItem(?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			if (!ACT_PARENT.getText().equals("")) {
				callStmt.setLong(2, Long.valueOf(ACT_PARENT.getText()));
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			callStmt.setString(3, ACT_NAME.getText());

			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);

				callStmt.close();

				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) ACT_NAME.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			Long lineNumber = (long) Thread.currentThread().getStackTrace()[2].getLineNumber();
			DbUtil.Log_To_Db(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void Save(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			try {
				Main.logger = Logger.getLogger(getClass());
				CallableStatement callStmt = conn.prepareCall("{ call MJUsers.AddOdbActionItem(?,?,?) }");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				if (!ACT_PARENT.getText().equals("")) {
					callStmt.setLong(2, Long.valueOf(ACT_PARENT.getText()));
				} else {
					callStmt.setNull(2, java.sql.Types.INTEGER);
				}
				callStmt.setString(3, ACT_NAME.getText());

				callStmt.execute();

				if (callStmt.getString(1) == null) {
					conn.commit();
					setStatus(true);
					callStmt.close();
					onclose();
				} else {
					conn.rollback();
					setStatus(false);
					Stage stage_ = (Stage) ACT_NAME.getScene().getWindow();
					Msg.MessageBox(callStmt.getString(1), stage_);
					callStmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Msg.Message(ExceptionUtils.getStackTrace(e));
				Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				Long lineNumber = (long) Thread.currentThread().getStackTrace()[2].getLineNumber();
				DbUtil.Log_To_Db(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
		}
	}

	void onclose() {
		Stage stage = (Stage) ACT_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();
			DbUtil.Run_Process(conn,getClass().getName());
			ACT_PARENT.setText(String.valueOf(parantid));

			// FirstWUpp(ACT_NAME);

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ������ ����������
	 */
	private Connection conn;

	/**
	 * ������� ������
	 * @throws UnknownHostException 
	 */
	private void dbConnect() throws UnknownHostException {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			
			Properties props = new Properties();
			props.setProperty("password", Connect.userPassword);
			props.setProperty("user", Connect.userID);
			props.put("v$session.osuser", System.getProperty("user.name").toString());
			props.put("v$session.machine", InetAddress.getLocalHost().getCanonicalHostName());
			props.put("v$session.program", getClass().getName());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + Connect.connectionURL, props);
			
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
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
			Long lineNumber = (long) Thread.currentThread().getStackTrace()[2].getLineNumber();
			DbUtil.Log_To_Db(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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

	Long parantid;

	public void setParantid(Long ID) {
		this.parantid = ID;
	}

	public AddAction() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}

}
