package ru.psv.mj.prjmngm.emps;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	 * ������������
	 */
	@FXML
	private ComboBox<VPM_EMP> EMP_BOSS;
	/**
	 * ��� ����������
	 */
	@FXML
	private ComboBox<PM_EMP_JBTP> EMP_JBTYPE;

	/**
	 * ��
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call PM_EMP_PKG.ADD(?,?,?,?,?,?,?,?,?,?,?,?)}");
			/* ������ */
			callStmt.registerOutParameter(1, Types.VARCHAR);
			/* ������� */
			callStmt.setString(2, EMP_LASTNAME.getText());
			/* ��� */
			callStmt.setString(3, EMP_FIRSTNAME.getText());
			/* �������� */
			callStmt.setString(4, EMP_MIDDLENAME.getText());
			/* ��������� */
			callStmt.setString(5, EMP_POSITION.getText());
			/* Email */
			callStmt.setString(6, EMP_EMAIL.getText());
			// �������
			callStmt.setString(7, EMP_TEL.getText());
			// ����� � ���������
			if (EMP_LOGIN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(8, EMP_LOGIN.getSelectionModel().getSelectedItem().getIUSRID());
			} else {
				callStmt.setNull(8, java.sql.Types.INTEGER);
			}
			/* ���� ������ �� ������ */
			callStmt.setDate(9,
					(EMP_WORKSTART.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKSTART.getValue()) : null);
			/* ���� ���������� */
			callStmt.setDate(10,
					(EMP_WORKEND.getValue() != null) ? java.sql.Date.valueOf(EMP_WORKEND.getValue()) : null);
			// ������������
			if (EMP_BOSS.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(11, EMP_BOSS.getSelectionModel().getSelectedItem().getEMP_ID());
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			// ��� ����������
			if (EMP_JBTYPE.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(12, EMP_JBTYPE.getSelectionModel().getSelectedItem().getJB_ID());
			} else {
				callStmt.setNull(12, java.sql.Types.INTEGER);
			}
			// ����������
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
	 * �������������
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
			// �����������
			{
				PreparedStatement sqlStatement = DbUtil.conn.prepareStatement("select * from VPM_EMP");
				ResultSet rs = sqlStatement.executeQuery();
				ObservableList<VPM_EMP> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					VPM_EMP list = new VPM_EMP();

					list.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
					list.setEMP_ID(rs.getLong("EMP_ID"));
					list.setEMP_WORKSTART((rs.getDate("EMP_WORKSTART") != null)
							? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKSTART")),
									DateTimeFormatter.ofPattern("dd.MM.yyyy"))
							: null);
					list.setEMP_TEL(rs.getString("EMP_TEL"));
					list.setEMP_LOGIN(rs.getString("EMP_LOGIN"));
					list.setEMP_LOGIN_L(rs.getLong("EMP_LOGIN_L"));
					list.setEMP_POSITION(rs.getString("EMP_POSITION"));
					list.setEMP_FIRSTNAME(rs.getString("EMP_FIRSTNAME"));
					list.setEMP_LASTNAME(rs.getString("EMP_LASTNAME"));
					list.setEMP_MIDDLENAME(rs.getString("EMP_MIDDLENAME"));
					list.setEMP_WORKEND((rs.getDate("EMP_WORKEND") != null)
							? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKEND")),
									DateTimeFormatter.ofPattern("dd.MM.yyyy"))
							: null);
					areas.add(list);
				}

				sqlStatement.close();
				rs.close();

				EMP_BOSS.setItems(areas);

				FxUtilTest.getComboBoxValue(EMP_BOSS);
				FxUtilTest.autoCompleteComboBoxPlus(EMP_BOSS,
						(typedText, itemToCompare) -> (itemToCompare.getEMP_ID() + "=" + itemToCompare.getEMP_LASTNAME()
								+ " " + itemToCompare.getEMP_FIRSTNAME() + " " + itemToCompare.getEMP_MIDDLENAME())
										.toLowerCase().contains(typedText.toLowerCase()));

				ConvBoss();
			}
			// ��� ����������
			{
				PreparedStatement sqlStatement = DbUtil.conn.prepareStatement("select * from PM_EMP_JBTP t");
				ResultSet rs = sqlStatement.executeQuery();
				ObservableList<PM_EMP_JBTP> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					PM_EMP_JBTP list = new PM_EMP_JBTP();
					list.setJP_NAME(rs.getString("JP_NAME"));
					list.setJB_ID(rs.getLong("JB_ID"));
					areas.add(list);
				}

				sqlStatement.close();
				rs.close();

				EMP_JBTYPE.setItems(areas);

				FxUtilTest.getComboBoxValue(EMP_JBTYPE);
				FxUtilTest.autoCompleteComboBoxPlus(EMP_JBTYPE,
						(typedText, itemToCompare) -> (itemToCompare.getJB_ID() + "=" + itemToCompare.getJP_NAME())
								.toLowerCase().contains(typedText.toLowerCase()));

				ConvEmpJobType();

			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��� ���� ����������
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
	 * ��� ������������
	 */
	private void ConvBoss() {
		EMP_BOSS.setConverter(new StringConverter<VPM_EMP>() {
			@Override
			public String toString(VPM_EMP object) {
				return object != null
						? object.getEMP_ID() + "=" + object.getEMP_LASTNAME() + " " + object.getEMP_FIRSTNAME() + " "
								+ object.getEMP_MIDDLENAME()
						: "";
			}

			@Override
			public VPM_EMP fromString(final String string) {
				return EMP_BOSS.getItems().stream()
						.filter(product -> (product.getEMP_ID() + "=" + product.getEMP_LASTNAME() + " "
								+ product.getEMP_FIRSTNAME() + " " + product.getEMP_MIDDLENAME()).equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	/**
	 * ��� ���� ����������
	 */
	private void ConvEmpJobType() {
		EMP_JBTYPE.setConverter(new StringConverter<PM_EMP_JBTP>() {
			@Override
			public String toString(PM_EMP_JBTP object) {
				return object != null ? object.getJB_ID() + "=" + object.getJP_NAME() : "";
			}

			@Override
			public PM_EMP_JBTP fromString(final String string) {
				return EMP_JBTYPE.getItems().stream()
						.filter(product -> (product.getJB_ID() + "=" + product.getJP_NAME()).equals(string)).findFirst()
						.orElse(null);
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
	 * ������
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
