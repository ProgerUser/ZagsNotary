package ru.psv.mj.prjmngm.orgs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class EditPmOrgC {

	/**
	 * Конструктор
	 */
	public EditPmOrgC() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

	@FXML
	private TextField ORG_NAME;
	@FXML
	private TextField ORG_RUK;
	@FXML
	private TextField ORG_SHNAME;
	@FXML
	private TextField ORG_DOLJ;
	@FXML
	private TextField ORG_OBRASH;

	// _________________________
	private BooleanProperty Status;

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}
	// _________________________

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
			CallableStatement callStmt = conn.prepareCall("{ call pm_sprav_pkg.edit_org(?,?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// ID
			if (class_ != null) {
				callStmt.setLong(2, class_.getORG_ID());
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			// Наименование
			callStmt.setString(3, ORG_NAME.getText());
			// Руководитель ФИО
			callStmt.setString(4, ORG_RUK.getText());
			// Руководитель ФИО кратк.
			callStmt.setString(5, ORG_SHNAME.getText());
			// Должность
			callStmt.setString(6, ORG_DOLJ.getText());
			// Обращение
			callStmt.setString(7, ORG_OBRASH.getText());
			// выполнение
			callStmt.execute();
			// выполнение
			callStmt.execute();
			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				callStmt.close();
				OnClose();
			} else {
				conn.rollback();
				setStatus(false);
				Msg.Message(callStmt.getString(1));
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private PM_ORG class_;
	Connection conn;

	public void SetClass(PM_ORG class_, Connection conn) {
		this.class_ = class_;
		this.conn = conn;
	}

	void OnClose() {
		Stage stage = (Stage) ORG_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			ORG_NAME.setText(class_.getORG_NAME());
			ORG_RUK.setText(class_.getORG_RUK());
			ORG_SHNAME.setText(class_.getORG_SHNAME());
			ORG_DOLJ.setText(class_.getORG_DOLJ());
			ORG_OBRASH.setText(class_.getORG_OBRASH());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
