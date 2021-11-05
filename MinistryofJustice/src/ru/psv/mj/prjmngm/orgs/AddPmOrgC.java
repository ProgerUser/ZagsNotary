package ru.psv.mj.prjmngm.orgs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class AddPmOrgC {

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

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call PM_SPRAV_PKG.ADD_ORG(?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// Наименование
			callStmt.setString(2, ORG_NAME.getText());
			// Руководитель ФИОF
			callStmt.setString(3, ORG_RUK.getText());
			// Руководитель ФИО кратк.
			callStmt.setString(4, ORG_SHNAME.getText());
			// Должность
			callStmt.setString(5, ORG_DOLJ.getText());
			// Обращение
			callStmt.setString(6, ORG_OBRASH.getText());
			// выполнение
			callStmt.execute();
			// выполнение
			callStmt.execute();
			if (callStmt.getString(1) == null) {
				conn.commit();
				callStmt.close();
				OnClose();
			} else {
				conn.rollback();
				Msg.Message(callStmt.getString(1));
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			dbConnect();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

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
		Stage stage = (Stage) ORG_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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
