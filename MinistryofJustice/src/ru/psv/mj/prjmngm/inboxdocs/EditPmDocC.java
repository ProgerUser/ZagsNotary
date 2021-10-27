package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;
import ru.psv.mj.widgets.MaskField;
import ru.psv.mj.www.plushaze.traynotification.Notifications;
import ru.psv.mj.www.plushaze.traynotification.TrayNotification;

public class EditPmDocC {
	/**
	 * Конструктор
	 */
	public EditPmDocC() {
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
	private MaskField EMP_TEL;
	@FXML
	private DatePicker EMP_WORKSTART;
	@FXML
	private DatePicker EMP_WORKEND;

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
			CallableStatement callStmt = conn.prepareCall("{ call PM_EMP_PKG.EDIT(?,?,?,?,?,?,?,?,?,?,?)}");
			// Ошибка
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// ИД сотрудника
			if (pm_emp != null) {
				callStmt.setLong(2, pm_emp.getEMP_ID());
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			/* Фамилия */
			callStmt.setString(3, EMP_LASTNAME.getText());
			/* Имя */
			callStmt.setString(4, EMP_FIRSTNAME.getText());
			/* Отчество */
			callStmt.setString(5, EMP_MIDDLENAME.getText());
			/* Должность */
			callStmt.setString(6, EMP_POSITION.getText());
			/* Email */
			callStmt.setString(7, EMP_EMAIL.getText());
			/* Телефон */
			callStmt.setString(8, EMP_TEL.getText());
			// Логин в программе
			if (EMP_LOGIN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(9, EMP_LOGIN.getSelectionModel().getSelectedItem().getIUSRID());
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			/* Дата приема на работу */
			callStmt.setDate(10,
					(EMP_WORKSTART.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKSTART.getValue()) : null);
			/* Дата увольнения */
			callStmt.setDate(11,
					(EMP_WORKEND.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKEND.getValue()) : null);
			// выполнение
			callStmt.execute();
			// закрыть
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			if (validate(EMP_EMAIL.getText()) == false) {
				TrayNotification tray = new TrayNotification("Ошибка", "Ошибка в поле Email!", Notifications.ERROR);
				tray.showAndWait();
				Platform.runLater(() -> {
					EMP_EMAIL.requestFocus();
				});
				return;
			}

			CallableStatement callStmt = conn.prepareCall("{ call PM_EMP_PKG.EDIT(?,?,?,?,?,?,?,?,?,?,?)}");
			// Ошибка
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// ИД сотрудника
			if (pm_emp != null) {
				callStmt.setLong(2, pm_emp.getEMP_ID());
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			/* Фамилия */
			callStmt.setString(3, EMP_LASTNAME.getText());
			/* Имя */
			callStmt.setString(4, EMP_FIRSTNAME.getText());
			/* Отчество */
			callStmt.setString(5, EMP_MIDDLENAME.getText());
			/* Должность */
			callStmt.setString(6, EMP_POSITION.getText());
			/* Email */
			callStmt.setString(7, EMP_EMAIL.getText());
			/* Телефон */
			callStmt.setString(8, EMP_TEL.getText());
			// Логин в программе
			if (EMP_LOGIN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(9, EMP_LOGIN.getSelectionModel().getSelectedItem().getIUSRID());
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			/* Дата приема на работу */
			callStmt.setDate(10,
					(EMP_WORKSTART.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKSTART.getValue()) : null);
			/* Дата увольнения */
			callStmt.setDate(11,
					(EMP_WORKEND.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKEND.getValue()) : null);
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

	void OnClose() {
		Stage stage = (Stage) EMP_TEL.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			new ConvConst().FormatDatePiker(EMP_WORKSTART);
			new ConvConst().FormatDatePiker(EMP_WORKEND);

			EMP_LASTNAME.setText(pm_emp.getEMP_LASTNAME());
			EMP_FIRSTNAME.setText(pm_emp.getEMP_FIRSTNAME());
			EMP_MIDDLENAME.setText(pm_emp.getEMP_MIDDLENAME());
			EMP_POSITION.setText(pm_emp.getEMP_POSITION());
			EMP_EMAIL.setText(pm_emp.getEMP_EMAIL());
			EMP_TEL.setText(pm_emp.getEMP_TEL());
			EMP_WORKSTART.setValue(pm_emp.getEMP_WORKSTART());
			EMP_WORKEND.setValue(pm_emp.getEMP_WORKEND());
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

				for (USR usr : EMP_LOGIN.getItems()) {
					if (usr.getIUSRID().equals(pm_emp.getEMP_LOGIN_L())) {
						EMP_LOGIN.getSelectionModel().select(usr);
						break;
					}
				}
			}
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
