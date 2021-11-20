package ru.psv.mj.prjmngm.projects;

import java.sql.Connection;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;

public class AddPmPrjC {

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

	private BooleanProperty Status;
	private LongProperty RetId;

	public boolean getStatus() {
		return this.Status.get();
	}

	public Long getRetId() {
		return this.RetId.get();
	}

	public void setStatus(Boolean value, Long RetId) {
		this.Status.set(value);
		this.RetId.set(RetId);
	}

	@FXML
	void ScanPrint(ActionEvent event) {

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

	/**
	 * Открыть сессию
	 */
//	private void dbConnect() {
//		try {
//			conn = DbUtil.GetConnect(getClass().getName());
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}

	/**
	 * Закрыть
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
				conn.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	Connection conn;

	void OnClose() {
//		Stage stage = (Stage) DOC_ISFAST.getScene().getWindow();
//		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void CopyFromTempl(ActionEvent event) {

	}

	/**
	 * Конструктор
	 */
	public AddPmPrjC() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.RetId = new SimpleLongProperty();
	}

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

}
