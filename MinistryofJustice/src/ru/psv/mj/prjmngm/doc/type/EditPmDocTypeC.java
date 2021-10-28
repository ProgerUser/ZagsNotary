package ru.psv.mj.prjmngm.doc.type;

import java.sql.Connection;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;

public class EditPmDocTypeC {
	/**
	 * Конструктор
	 */
	public EditPmDocTypeC() {
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
	private TextField DOC_TP_NAME;
	@FXML
	private TextField WordPath;

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

	void SaveCompare() {
		try {
		
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
			
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	
	private PM_DOC_TYPES class_;
	Connection conn;

	public void SetClass(PM_DOC_TYPES class_, Connection conn) {
		this.class_ = class_;
		this.conn = conn;
	}

	void OnClose() {
		Stage stage = (Stage) DOC_TP_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}


	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

}
