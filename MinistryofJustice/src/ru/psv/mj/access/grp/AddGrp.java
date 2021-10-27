package ru.psv.mj.access.grp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;

public class AddGrp {

	public AddGrp() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TextField GRP_NAME;

	@FXML
	private TextField NAME;

	@FXML
	void AddUpdate(ActionEvent event) {
		try {
			PreparedStatement prp = conn.prepareStatement("insert into ODB_GROUP_USR (GRP_NAME,NAME) values (?,?) ");
			prp.setString(1, GRP_NAME.getText());
			prp.setString(2, NAME.getText());
			prp.executeUpdate();
			prp.close();
			conn.commit();
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}
}
