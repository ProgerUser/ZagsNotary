package mj.prjmngm.emps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.utils.DbUtil;

public class RootPmEmpController {

	public RootPmEmpController() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TableView<VPM_EMP> PM_EMP;
	@FXML
	private TableColumn<VPM_EMP, Long> EMP_ID;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_LASTNAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_FIRSTNAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_MIDDLENAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_POSITION;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_TEL;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_EMAIL;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_LOGIN;

	@FXML
	void Add(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Reshresh(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void initialize() {
		try {

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

}
