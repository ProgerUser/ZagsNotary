package ru.psv.mj.notary.template.html.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.notary.template.html.model.NT_TEMPLATE;
import ru.psv.mj.utils.DbUtil;

public class IUTemplate {

	private LongProperty NT_PARENT;
	private LongProperty ID;
	private StringProperty type;
	private StringProperty NAME;

	NT_TEMPLATE values;
	@FXML
	private Button OK;

	public IUTemplate() {
		Main.logger = Logger.getLogger(getClass());
		this.NT_PARENT = new SimpleLongProperty();
		this.ID = new SimpleLongProperty();
		this.type = new SimpleStringProperty();
		this.NAME = new SimpleStringProperty();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public String getNAME() {
		return NAME.get();
	}

	public void settype(String type) {
		this.type.set(type);
	}

	public String gettype() {
		return type.get();
	}

	public void setID(Long ID, NT_TEMPLATE values) {
		this.values = values;
		this.ID.set(ID);
	}

	public Long getID() {
		return ID.get();
	}

	public void setNT_PARENT(Long NT_PARENT) {
		this.NT_PARENT.set(NT_PARENT);
	}

	public Long getNT_PARENT() {
		return NT_PARENT.get();
	}

	private Connection conn;
	@FXML
	private TextField NT_NAME;

	@FXML
	private TextField NT_PARENT_ID;

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!NT_NAME.getText().equals("")) {
				if (gettype().equals("I")) {
					PreparedStatement prp = conn
							.prepareStatement("insert into NT_TEMPLATE (NT_PARENT,NT_NAME) values (?,?)");
					prp.setLong(1, getNT_PARENT());
					prp.setString(2, NT_NAME.getText());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn
							.prepareStatement("update NT_TEMPLATE set NT_NAME = ?,NT_PARENT=? where NT_ID = ?");
					prp.setLong(3, getID());
					prp.setLong(2, Long.valueOf(NT_PARENT_ID.getText()));
					prp.setString(1, NT_NAME.getText());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) NT_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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

	@FXML
	private void initialize() {
		try {
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			if (gettype().equals("U")) {
				NT_NAME.setText(getNAME());
				NT_PARENT_ID.setText(String.valueOf(values.getNT_PARENT()));
				OK.setText("���������");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
