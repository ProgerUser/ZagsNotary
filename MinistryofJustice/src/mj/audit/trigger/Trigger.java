package mj.audit.trigger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class Trigger {

	@FXML
	private TextArea TriggerText;

	@FXML
	void Execute(ActionEvent event) {
		try {
			String exec = " begin execute immediate ? ; end;";
			PreparedStatement execimm = conn.prepareStatement(exec);
			execimm.setString(1, TriggerText.getText());
			execimm.executeUpdate();
			execimm.close();
			onclose();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Msg.Message(ExceptionUtils.getStackTrace(e1));
				Main.logger.error(ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
			}
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ������
	 * 
	 * @param event
	 */
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	/**
	 * �������� �����
	 */
	void onclose() {
		Stage stage = (Stage) TriggerText.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * �������������
	 */
	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();
			DbUtil.Run_Process(conn);
			INIT();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void INIT() {
		try {
			String sql = "";
			Main.logger = Logger.getLogger(getClass());
			String selectStmt = "select CTEXT, CTABLE, NLINE\n" + "  from V_AU_TRIGGER t\n" + " where CTABLE = ?\n";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setString(1, TableName);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				sql = sql + ((rs.getString("CTEXT") != null) ? rs.getString("CTEXT") : "") + "\r\n";
			}
			prepStmt.close();
			rs.close();
			TriggerText.setText(sql);
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
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "Trigger");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
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
			DbUtil.Log_Error(e);
		}
	}

	private BooleanProperty Status;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public Trigger() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

	String TableName;

	public void SetTableName(String tbl) {
		this.TableName = tbl;
	}

}
