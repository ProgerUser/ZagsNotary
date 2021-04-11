package notary.client.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.log4j.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import notary.client.model.CUS_DOCUM;
import notary.client.model.VPUD;

public class Edit_Cus_Doc {

	@FXML
	private TextField DOC_AGENCY_T;

	@FXML
	private TextField DOC_SER_T;

	@FXML
	private TextField DOC_SUBDIV_T;

	@FXML
	private DatePicker DOC_DATE_T;

	@FXML
	private VBox PANE;

	@FXML
	private ComboBox<VPUD> ID_DOC_TP_T;

	@FXML
	private Button SaveAdd;

	@FXML
	private DatePicker DOC_PERIOD_T;

	@FXML
	private TextField DOC_NUM_T;

	@FXML
	private CheckBox PREF_T;

	@FXML
	void ID_DOC_TP_T(ActionEvent event) {
		try {
			VPUD val = ID_DOC_TP_T.getSelectionModel().getSelectedItem();
			if (val != null) {
				if (val.getIPUDID() == 1 || val.getIPUDID() == 2) {
					new ConvConst().OnlyNumber(DOC_SER_T);
					new ConvConst().OnlyNumber(DOC_NUM_T);
				} else {
					new ConvConst().TxtFldDeleteListener(DOC_SER_T);
					new ConvConst().TxtFldDeleteListener(DOC_NUM_T);
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
    
	/**
	 * Для типа документов
	 */
	private void convertComboDisplayList() {
		ID_DOC_TP_T.setConverter(new StringConverter<VPUD>() {
			@Override
			public String toString(VPUD product) {
				return product.getCPUDDOC();
			}

			@Override
			public VPUD fromString(final String string) {
				return ID_DOC_TP_T.getItems().stream().filter(product -> product.getCPUDDOC().equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	/**
	 * Сессия текущая
	 */
	private Connection conn = null;

	boolean istemp = false;

	public void setConn(Connection conn, boolean istemp, CUS_DOCUM docs) throws SQLException {
		this.conn = conn;
		this.cus_docs = docs;
		this.istemp = istemp;
		this.conn.setAutoCommit(false);
	}

	public void setId(Integer value) {
		System.out.println(value);
		this.Id.set(value);
	}

	private IntegerProperty Id;

	public Integer getId() {
		return this.Id.get();
	}

	public Edit_Cus_Doc() {
		Main.logger = Logger.getLogger(getClass());
		this.Id = new SimpleIntegerProperty();
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) PREF_T.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Нажать добавить
	 * 
	 * @param event
	 */
	@FXML
	void SaveAdd(ActionEvent event) {
		try {
			if (ID_DOC_TP_T.getValue() != null 
					& (DOC_SER_T.getText() != null && !DOC_SER_T.getText().equals(""))
					& (DOC_NUM_T.getText() != null && !DOC_NUM_T.getText().equals(""))
					/*& (DOC_SUBDIV_T.getText() != null && !DOC_SUBDIV_T.getText().equals(""))*/
					/*& (DOC_AGENCY_T.getText() != null && !DOC_AGENCY_T.getText().equals(""))*/
					& DOC_DATE_T.getValue() != null) {
				if (istemp) {
					CRUDDocumTemp("{ ? = call MJCUS.EDIT_CUS_DOCUM_TEMP(?,?,?,?,?,?,?,?,?)}");
				} else {
					CRUDDocum("{ ? = call MJCUS.EDIT_CUS_DOCUM(?,?,?,?,?,?,?,?,?)}");
				}
			} else {
				Msg.Message("Заполните поля");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Добавление и редактирование документа
	 * 
	 * @param type
	 */
	void CRUDDocumTemp(String type) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall(type);
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, (PREF_T.isSelected()) ? "Y" : "N");
			if (ID_DOC_TP_T.getValue() != null) {
				VPUD vp = ID_DOC_TP_T.getSelectionModel().getSelectedItem();
				callStmt.setInt(3, vp.getIPUDID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}
			callStmt.setString(4, DOC_NUM_T.getText());
			callStmt.setString(5, DOC_SER_T.getText());
			callStmt.setDate(6, (DOC_DATE_T.getValue() != null) ? java.sql.Date.valueOf(DOC_DATE_T.getValue()) : null);
			callStmt.setString(7, DOC_AGENCY_T.getText());
			callStmt.setDate(8,
					(DOC_PERIOD_T.getValue() != null) ? java.sql.Date.valueOf(DOC_PERIOD_T.getValue()) : null);
			callStmt.setString(9, DOC_SUBDIV_T.getText());
			callStmt.setInt(10, getId());

			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
				onclose();
			} else {
				Stage stage_ = (Stage) PANE.getScene().getWindow();
				Msg.ErrorView(stage_, "CUS_DOCUM_TEMP", conn);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Добавление и редактирование документа
	 * 
	 * @param type
	 */
	void CRUDDocum(String type) {
		try {
			CallableStatement callStmt = conn.prepareCall(type);
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, (PREF_T.isSelected()) ? "Y" : "N");
			if (ID_DOC_TP_T.getValue() != null) {
				VPUD vp = ID_DOC_TP_T.getSelectionModel().getSelectedItem();
				callStmt.setInt(3, vp.getIPUDID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}
			callStmt.setString(4, DOC_NUM_T.getText());
			callStmt.setString(5, DOC_SER_T.getText());
			callStmt.setDate(6, (DOC_DATE_T.getValue() != null) ? java.sql.Date.valueOf(DOC_DATE_T.getValue()) : null);
			callStmt.setString(7, DOC_AGENCY_T.getText());
			callStmt.setDate(8,
					(DOC_PERIOD_T.getValue() != null) ? java.sql.Date.valueOf(DOC_PERIOD_T.getValue()) : null);
			callStmt.setString(9, DOC_SUBDIV_T.getText());
			callStmt.setInt(10, getId());

			System.out.println(getId());

			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
				onclose();
			} else {
				Stage stage_ = (Stage) PANE.getScene().getWindow();
				Msg.ErrorView(stage_, "CUS_DOCUM", conn);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Отмена
	 * 
	 * @param event
	 */
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	CUS_DOCUM cus_docs;

	/**
	 * Быстрый способ заполнения даты
	 * @param dp
	 */
	void DateAutoComma(DatePicker dp) {
		DateTimeFormatter fastFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		dp.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate object) {
				return (object != null) ? object.format(defaultFormatter) : null;
			}

			@Override
			public LocalDate fromString(String string) {
				try {
					return LocalDate.parse(string, fastFormatter);
				} catch (DateTimeParseException dtp) {

				}

				return LocalDate.parse(string, defaultFormatter);
			}
		});
	}
	
	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			DateAutoComma(DOC_DATE_T);
			DateAutoComma(DOC_PERIOD_T);
			
			SaveAdd.setText("Сохранить изменения");

			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from VPUD";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<VPUD> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					VPUD pud = new VPUD();
					pud.setCPUDDOC(rs.getString("name"));
					pud.setIPUDID(rs.getInt("code"));
					combolist.add(pud);
				}
				ID_DOC_TP_T.setItems(combolist);
				convertComboDisplayList();
				rs.close();
				sqlStatement.close();

				if (cus_docs.getID_DOC_TP() != null) {
					for (VPUD ld : ID_DOC_TP_T.getItems()) {
						if (cus_docs.getID_DOC_TP().equals(ld.getCPUDDOC())) {
							ID_DOC_TP_T.getSelectionModel().select(ld);
							break;
						}
					}
				}
			}

			DOC_SER_T.setText(cus_docs.getDOC_SER());
			DOC_NUM_T.setText(cus_docs.getDOC_NUM());
			DOC_DATE_T.setValue(cus_docs.getDOC_DATE());
			DOC_PERIOD_T.setValue(cus_docs.getDOC_PERIOD());
			if (cus_docs.getPREF().equals("Да"))
				PREF_T.setSelected(true);
			else
				PREF_T.setSelected(false);
			DOC_AGENCY_T.setText(cus_docs.getDOC_AGENCY());
			DOC_SUBDIV_T.setText(cus_docs.getDOC_SUBDIV());

			new ConvConst().FormatDatePiker(DOC_DATE_T);
			new ConvConst().FormatDatePiker(DOC_PERIOD_T);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}

