package mj.prjmngm.emps;

import java.sql.Connection;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.users.USR;
import mj.utils.DbUtil;

public class EditPmEmpController {
	/**
	 * Конструктор
	 */
	public EditPmEmpController() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

	private BooleanProperty Status;

	public boolean getStatus() {
		return this.Status.get();
	}
	
	public void setStatus(Boolean value) {
		this.Status.set(value);
	}
	
	@FXML
	private TextField EMP_LASTNAME;
	@FXML
	private TextField EMP_FIRSTNAME;
	@FXML
	private TextField EMP_MIDDLENAME;
	@FXML
	private TextField EMP_POSITION;
	@FXML
	private TextField EMP_EMAIL;
	@FXML
	private ComboBox<USR> EMP_LOGIN;
	@FXML
	private TextField EMP_TEL;

	/**
	 * Отмена
	 * 
	 * @param event
	 */
	@FXML
	void Cancel(ActionEvent event) {
		try {
			OnClose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			setStatus(true);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void OnClose() {
		Stage stage = (Stage) EMP_TEL.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			EMP_LASTNAME.setText(pm_emp.getEMP_LASTNAME());
			EMP_FIRSTNAME.setText(pm_emp.getEMP_FIRSTNAME());
			EMP_MIDDLENAME.setText(pm_emp.getEMP_MIDDLENAME());
			EMP_POSITION.setText(pm_emp.getEMP_POSITION());
			EMP_EMAIL.setText(pm_emp.getEMP_EMAIL());
			EMP_TEL.setText(pm_emp.getEMP_TEL());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private VPM_EMP pm_emp;
	Connection conn;

	public void SetClass(VPM_EMP pm_emp, Connection conn) {
		this.pm_emp = pm_emp;
		this.conn = conn;
	}

}
