package ru.psv.mj.access.menu;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class EditMnu {

	@FXML
	private TextField MNU_PARENT;

	@FXML
	private TextField MNU_NAME;

	@FXML
	private TextField MNU_PARENT_NEW;
	
	
	@FXML
	void Save(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			try {
				Main.logger = Logger.getLogger(getClass());
				CallableStatement callStmt = conn.prepareCall("{ call MJUsers.EditOdbMenuItem(?,?,?,?) }");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				if (!MNU_PARENT.getText().equals("")) {
					callStmt.setLong(2, Long.valueOf(MNU_PARENT.getText()));
				} else {
					callStmt.setNull(2, java.sql.Types.INTEGER);
				}
				callStmt.setString(3, MNU_NAME.getText());

				if (!MNU_PARENT_NEW.getText().equals("")) {
					callStmt.setLong(4, Long.valueOf(MNU_PARENT_NEW.getText()));
				} else {
					callStmt.setNull(4, java.sql.Types.INTEGER);
				}
				
				callStmt.execute();

				if (callStmt.getString(1) == null) {
					conn.commit();
					setStatus(true);
					callStmt.close();
					
					onclose();
				} else {
					conn.rollback();
					setStatus(false);
					Stage stage_ = (Stage) MNU_NAME.getScene().getWindow();
					Msg.MessageBox(callStmt.getString(1), stage_);
					
					callStmt.close();
				}
			} catch (Exception e) {
				DbUtil.Log_Error(e);
			}
		}
	}

	@FXML
	void Add(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall("{ call MJUsers.EditOdbMenuItem(?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			if (!MNU_PARENT.getText().equals("")) {
				callStmt.setLong(2, Long.valueOf(MNU_PARENT.getText()));
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			callStmt.setString(3, MNU_NAME.getText());
			
			if (!MNU_PARENT_NEW.getText().equals("")) {
				callStmt.setLong(4, Long.valueOf(MNU_PARENT_NEW.getText()));
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}
			
			callStmt.execute();

			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) MNU_NAME.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) MNU_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			MNU_PARENT.setText(String.valueOf(parantid));
			MNU_NAME.setText(txt);
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
	 */
	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
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

	String txt;

	public void setParantid(Long ID, String txt) {
		this.parantid = ID;
		this.txt = txt;
	}

	public EditMnu() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}

}
