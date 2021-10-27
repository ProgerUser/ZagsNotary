package ru.psv.mj.prjmngm.emps;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.admin.users.USR;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;

public class AddPmEmpController {
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
    @FXML
    private DatePicker EMP_WORKSTART;
    @FXML
    private DatePicker EMP_WORKEND;
	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call PM_EMP_PKG.ADD(?,?,?,?,?,?,?,?,?,?)}");
			/* Ошибка */
			callStmt.registerOutParameter(1, Types.VARCHAR);
			/* Фамилия */
			callStmt.setString(2, EMP_LASTNAME.getText());
			/* Имя */
			callStmt.setString(3, EMP_FIRSTNAME.getText());
			/* Отчество */
			callStmt.setString(4, EMP_MIDDLENAME.getText());
			/* Должность */
			callStmt.setString(5, EMP_POSITION.getText());
			/* Email */
			callStmt.setString(6, EMP_EMAIL.getText());
			// Телефон
			callStmt.setString(7, EMP_TEL.getText());
			// Логин в программе
			if (EMP_LOGIN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(8, EMP_LOGIN.getSelectionModel().getSelectedItem().getIUSRID());
			} else {
				callStmt.setNull(8, java.sql.Types.INTEGER);
			}
			/* Дата приема на работу */
			callStmt.setDate(9,
					(EMP_WORKSTART.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKSTART.getValue()) : null);
			/* Дата увольнения */
			callStmt.setDate(10,
					(EMP_WORKEND.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKEND.getValue()) : null);

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
			new ConvConst().FormatDatePiker(EMP_WORKSTART);
			new ConvConst().FormatDatePiker(EMP_WORKEND);
			// -------------------
			{
				PreparedStatement sqlStatement = DbUtil.conn
						.prepareStatement("select * from usr where usr.dusrfire is null");
				ResultSet rs = sqlStatement.executeQuery();
				ObservableList<USR> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					USR list = new USR();
					list.setIUSRID(rs.getLong("IUSRID"));
					list.setCUSRLOGNAME(rs.getString("CUSRLOGNAME"));
					list.setCUSRNAME(rs.getString("CUSRNAME"));
					list.setCUSRPOSITION(rs.getString("CUSRPOSITION"));
					areas.add(list);
				}

				sqlStatement.close();
				rs.close();

				EMP_LOGIN.setItems(areas);

				FxUtilTest.getComboBoxValue(EMP_LOGIN);
				FxUtilTest.autoCompleteComboBoxPlus(EMP_LOGIN,
						(typedText,
								itemToCompare) -> (itemToCompare.getCUSRLOGNAME() + "=" + itemToCompare.getCUSRNAME())
										.toLowerCase().contains(typedText.toLowerCase()));

				convertComboDisplayList();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	
	/**
	 * Для типа документов
	 */
	private void convertComboDisplayList() {
		EMP_LOGIN.setConverter(new StringConverter<USR>() {
			@Override
			public String toString(USR object) {
				return object != null ? object.getCUSRLOGNAME() + "=" + object.getCUSRNAME() : "";
			}

			@Override
			public USR fromString(final String string) {
				return EMP_LOGIN.getItems().stream()
						.filter(product -> (product.getCUSRLOGNAME() + "=" + product.getCUSRNAME()).equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	public void SetConn(Connection conn) {
		this.conn = conn;
	}

	Connection conn;

	void OnClose() {
		Stage stage = (Stage) EMP_TEL.getScene().getWindow();
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
