package mj.notary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.users.OTD;

public class AddNotary {

	@FXML
	private TextField NOT_NAME;

	@FXML
	private ComboBox<OTD> NOT_OTD;

	@FXML
	private TextField NOT_RUK;

	@FXML
	private TextField NOT_ID;

	/**
	 * Для отделения
	 */
	private void convertComboDisplayList() {
		NOT_OTD.setConverter(new StringConverter<OTD>() {
			@Override
			public String toString(OTD product) {
				return product.getCOTDNAME();
			}

			@Override
			public OTD fromString(final String string) {
				return NOT_OTD.getItems().stream().filter(product -> product.getCOTDNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	void Save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			PreparedStatement oper = conn
					.prepareStatement("insert into NOTARY (NOT_ID, NOT_OTD,NOT_NAME,NOT_RUK) values (?,?,?,?)");
			oper.setInt(1, Integer.valueOf(NOT_ID.getText()));
			oper.setInt(2, NOT_OTD.getValue().getIOTDNUM());
			oper.setString(3, NOT_NAME.getText());
			oper.setString(4, NOT_RUK.getText());
			oper.executeUpdate();
			oper.close();

			conn.commit();
			setStatus(true);
			onclose();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				Main.logger.error(ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
			}
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
		Stage stage = (Stage) NOT_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();

			/* Отделение */
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from otd");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<OTD> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					OTD list = new OTD();
					list.setIOTDNUM(rs.getInt("IOTDNUM"));
					list.setCOTDNAME(rs.getString("COTDNAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();

				NOT_OTD.setItems(combolist);
				rs.close();
			}
			convertComboDisplayList();
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

	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddNotary");
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

	public AddNotary() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}
}
