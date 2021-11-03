package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.prjmngm.doc.type.PM_DOC_TYPES;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;

public class AddPmDocC {
	@FXML
	private ComboBox<PM_DOC_TYPES> DOC_TYPE;
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
	private TableView<VPM_DOC_WORD> DocWord;
	@FXML
	private TableColumn<VPM_DOC_WORD, ?> DocWordId;
	@FXML
	private TableColumn<VPM_DOC_WORD, ?> DocWordFilename;
	@FXML
	private TableColumn<VPM_DOC_WORD, ?> DocWordExt;
	@FXML
	private TableColumn<VPM_DOC_WORD, ?> DocWordKb;
	@FXML
	private TableView<VPM_DOC_SCANS> DocScans;
	@FXML
	private TableColumn<VPM_DOC_SCANS, ?> DocScanId;
	@FXML
	private TableColumn<VPM_DOC_SCANS, ?> DocScanFileName;
	@FXML
	private TableColumn<VPM_DOC_SCANS, ?> DocScanExt;
	@FXML
	private TableColumn<VPM_DOC_SCANS, ?> DocScanKb;

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
	void DOC_ISFAST(ActionEvent event) {
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
		Stage stage = (Stage) DOC_ISFAST.getScene().getWindow();
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
