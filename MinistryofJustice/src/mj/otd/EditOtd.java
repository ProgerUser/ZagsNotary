package mj.otd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.users.OTD;

public class EditOtd {

	@FXML
	private TextField COTDNAME;

	@FXML
	private TextField IOTDNUM;

	@FXML
	void Save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			PreparedStatement oper = conn
					.prepareStatement("update otd set IOTDNUM = ? , COTDNAME = ? where IOTDNUM = ?");
			oper.setInt(1, Integer.valueOf(IOTDNUM.getText()));
			oper.setString(2, COTDNAME.getText());
			oper.setInt(3, Integer.valueOf(IOTDNUM.getText()));
			oper.executeUpdate();
			oper.close();
			setStatus(true);
			onclose();
		} catch (SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
		setStatus(false);
	}

	void onclose() {
		Stage stage = (Stage) COTDNAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());

			COTDNAME.setText(otd.getCOTDNAME());
			IOTDNUM.setText(String.valueOf(otd.getIOTDNUM()));

		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	Connection conn;

	OTD otd;
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

	public void setConn(Connection conn, OTD value) {
		this.otd = value;
		this.conn = conn;
	}

	public Integer getId() {
		return this.Id.get();
	}

	public EditOtd() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}
}
