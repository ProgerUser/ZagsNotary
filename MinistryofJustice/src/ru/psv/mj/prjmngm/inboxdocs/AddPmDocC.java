package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.doc.type.PM_DOC_TYPES;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;

public class AddPmDocC {

	@FXML
	private Tab Scan;
	@FXML
	private Tab Word;
	@FXML
	private ComboBox<PM_DOC_TYPES> DOC_TYPE;
	@FXML
	private ComboBox<PM_ORG> DOC_ORG;
	@FXML
	private TextField DOC_NUMBER;
	@FXML
	private DatePicker DOC_END;
	@FXML
	private DatePicker DOC_DATE;
	@FXML
	private CheckBox DOC_ISFAST;
	@FXML
	private TextField DOC_COMMENT;
	@FXML
	private TextField DOC_REF;
	@FXML
	private TextField DOC_NAME;

	/**
	 * Для типа документов
	 */
	private void convertComboDisplayList() {
		DOC_TYPE.setConverter(new StringConverter<PM_DOC_TYPES>() {
			@Override
			public String toString(PM_DOC_TYPES object) {
				return object != null ? object.getDOC_TP_ID() + "=" + object.getDOC_TP_NAME() : "";
			}

			@Override
			public PM_DOC_TYPES fromString(final String string) {
				return DOC_TYPE.getItems().stream()
						.filter(product -> (product.getDOC_TP_ID() + "=" + product.getDOC_TP_NAME()).equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	@FXML
	void AddScan(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void AddWord(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DOC_REF(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DeleteScan(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DeleteWord(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void EditScan(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void EditWord(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void ReshreshScan(ActionEvent event) {
		try {
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void ReshreshWord(ActionEvent event) {
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
			CallableStatement callStmt = conn.prepareCall("{ call PM_DOC.ADD_DOC_INBOX(?,?,?,?,?,?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// Срок документа
			callStmt.setDate(2, (DOC_END.getValue() != null) ? java.sql.Date.valueOf(DOC_END.getValue()) : null);
			// Ссылка на типы документов
			if (DOC_TYPE.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(3, DOC_TYPE.getSelectionModel().getSelectedItem().getDOC_TP_ID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}
			// Комментарий
			callStmt.setString(4, DOC_COMMENT.getText());
			// Срочность, 'Y','N'
			if (DOC_ISFAST.isSelected()) {
				callStmt.setString(5, "Y");
			} else if (!DOC_ISFAST.isSelected()) {
				callStmt.setString(5, "N");
			}
			// Номер документа
			callStmt.setString(6, DOC_NUMBER.getText());
			// Дата поступления документа
			callStmt.setDate(7, (DOC_DATE.getValue() != null) ? java.sql.Date.valueOf(DOC_DATE.getValue()) : null);
			// Ссылка на связанный документ
			if (!DOC_REF.getText().equals("")) {
				callStmt.setLong(8, Integer.valueOf(DOC_REF.getText()));
			} else {
				callStmt.setNull(8, java.sql.Types.INTEGER);
			}
			// Ссылка на организацию
			if (DOC_ORG.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(9, DOC_ORG.getSelectionModel().getSelectedItem().getORG_ID());
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			// Наименование документа
			callStmt.setString(10, DOC_NAME.getText());
			callStmt.registerOutParameter(11, Types.INTEGER);
			// выполнение
			callStmt.execute();
			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true, callStmt.getLong(11));
				callStmt.close();
				OnClose();
			} else {
				conn.rollback();
				setStatus(false, callStmt.getLong(11));
				Msg.Message(callStmt.getString(1));
				callStmt.close();
			}
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
			Scan.setDisable(true);
			Word.setDisable(true);
			dbConnect();
			// -------------------
			{
				String selectStmt = "select * from PM_DOC_TYPES t";
				PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
				ResultSet rs = prepStmt.executeQuery();
				ObservableList<PM_DOC_TYPES> obslist = FXCollections.observableArrayList();
				while (rs.next()) {
					PM_DOC_TYPES list = new PM_DOC_TYPES();
					list.setDOC_TP_ID(rs.getLong("DOC_TP_ID"));
					list.setDOC_TP_NAME(rs.getString("DOC_TP_NAME"));
					obslist.add(list);
				}
				prepStmt.close();
				rs.close();

				DOC_TYPE.setItems(obslist);

				FxUtilTest.getComboBoxValue(DOC_TYPE);
				FxUtilTest.autoCompleteComboBoxPlus(DOC_TYPE,
						(typedText,
								itemToCompare) -> (itemToCompare.getDOC_TP_ID() + "=" + itemToCompare.getDOC_TP_NAME())
										.toLowerCase().contains(typedText.toLowerCase()));

				convertComboDisplayList();

			}
			// -------------------
			{
				String selectStmt = "select * from PM_ORG t";
				PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
				ResultSet rs = prepStmt.executeQuery();
				ObservableList<PM_ORG> obslist = FXCollections.observableArrayList();
				while (rs.next()) {
					PM_ORG list = new PM_ORG();
					list.setORG_DOLJ(rs.getString("ORG_DOLJ"));
					list.setORG_ID(rs.getLong("ORG_ID"));
					list.setORG_RUK(rs.getString("ORG_RUK"));
					list.setORG_NAME(rs.getString("ORG_NAME"));
					list.setORG_SHNAME(rs.getString("ORG_SHNAME"));
					obslist.add(list);
				}
				prepStmt.close();
				rs.close();
				DOC_ORG.setItems(obslist);
				FxUtilTest.getComboBoxValue(DOC_ORG);
				FxUtilTest.autoCompleteComboBoxPlus(DOC_ORG,
						(typedText, itemToCompare) -> (itemToCompare.getORG_ID() + "=" + itemToCompare.getORG_NAME())
								.toLowerCase().contains(typedText.toLowerCase()));
				ConvDocOrg();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Для организации
	 */
	private void ConvDocOrg() {
		DOC_ORG.setConverter(new StringConverter<PM_ORG>() {
			@Override
			public String toString(PM_ORG object) {
				return object != null ? object.getORG_ID() + "=" + object.getORG_NAME() : "";
			}

			@Override
			public PM_ORG fromString(final String string) {
				return DOC_ORG.getItems().stream()
						.filter(product -> (product.getORG_ID() + "=" + product.getORG_NAME()).equals(string))
						.findFirst().orElse(null);
			}
		});
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
		Stage stage = (Stage) DOC_ISFAST.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void CopyFromTempl(ActionEvent event) {

	}

	/**
	 * Конструктор
	 */
	public AddPmDocC() {
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
