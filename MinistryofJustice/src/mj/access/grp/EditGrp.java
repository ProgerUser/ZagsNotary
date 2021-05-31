package mj.access.grp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.utils.DbUtil;

public class EditGrp {

	public EditGrp() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

	ODB_GROUP_USR grp;
	
	private BooleanProperty Status;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}


	public void setConn(Connection conn, ODB_GROUP_USR grp) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.grp = grp;
	}
    @FXML
    private Button AddUpdate;
	@FXML
	private TextField GRP_NAME;

	@FXML
	private TextField NAME;

	@FXML
	void AddUpdate(ActionEvent event) {
		try {
			PreparedStatement prp = conn
					.prepareStatement("update ODB_GROUP_USR set GRP_NAME = ?,NAME = ? where GRP_ID = ? ");
			prp.setString(1, GRP_NAME.getText());
			prp.setString(2, NAME.getText());
			prp.setLong(3, grp.getGRP_ID());
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
			AddUpdate.setText("���������");
			GRP_NAME.setText(grp.getGRP_NAME());
			NAME.setText(grp.getNAME());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private Connection conn;
}
