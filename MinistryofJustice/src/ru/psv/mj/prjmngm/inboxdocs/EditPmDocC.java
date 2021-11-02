package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import javafx.util.StringConverter;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.doc.type.PM_DOC_TYPES;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;

public class EditPmDocC {
	/**
	 * Конструктор
	 */
	public EditPmDocC() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

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
	@FXML
	private TableView<PM_DOC_WORD> DocWord;
	@FXML
	private TableColumn<PM_DOC_WORD, ?> DocWordId;
	@FXML
	private TableColumn<PM_DOC_WORD, ?> DocWordFilename;
	@FXML
	private TableColumn<PM_DOC_WORD, ?> DocWordExt;
	@FXML
	private TableColumn<PM_DOC_WORD, ?> DocWordKb;
	@FXML
	private TableView<PM_DOC_SCANS> DocScans;
	@FXML
	private TableColumn<PM_DOC_SCANS, ?> DocScanId;
	@FXML
	private TableColumn<PM_DOC_SCANS, ?> DocScanFileName;
	@FXML
	private TableColumn<PM_DOC_SCANS, ?> DocScanExt;
	@FXML
	private TableColumn<PM_DOC_SCANS, ?> DocScanKb;

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

	private BooleanProperty Status;

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setStatus(Boolean value) {
		this.Status.set(value);
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

	/**
	 * Создать ворд с шаблона
	 * 
	 * @param event
	 */
	@FXML
	void CopeFromTempl(ActionEvent event) {
		try {
			if (DOC_TYPE.getSelectionModel().getSelectedItem() != null) {
				// Create the custom dialog.
				Dialog<Pair<String, String>> dialog = new Dialog<>();
				dialog.setTitle("Создание из шаблона");

				Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(this.getClass().getResource("/icon.png").toString()));

				// Set the button types.
				ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

				GridPane gridPane = new GridPane();
				gridPane.setHgap(10);
				gridPane.setVgap(10);
				gridPane.setPadding(new Insets(20, 150, 10, 10));

				// текстовое поле
				TextField acc = new TextField();
				acc.setPrefWidth(200);

				gridPane.add(new Label("Наименование файла:"), 0, 0);
				gridPane.add(acc, 1, 0);

				dialog.getDialogPane().setContent(gridPane);

				Platform.runLater(() -> acc.requestFocus());
				// Convert the result to
				// clicked.
				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == loginButtonType) {
						return new Pair<>(acc.getText(), acc.getText());
					}
					return null;
				});

				Optional<Pair<String, String>> result = dialog.showAndWait();
				// Нажали OK
				result.ifPresent(pair -> {
					System.out.println("OK");
				});
			} else {
				Msg.Message("Выберите тип документа!");
			}
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
			CallableStatement callStmt = conn.prepareCall("{ call PM_DOC.EDIT_DOC_INBOX(?,?,?,?,?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// ID документы
			if (class_ != null) {
				callStmt.setLong(2, class_.getDOC_ID());
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			// Срок документа
			callStmt.setDate(3, (DOC_END.getValue() != null) ? java.sql.Date.valueOf(DOC_END.getValue()) : null);
			// Ссылка на типы документов
			if (DOC_TYPE.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(4, DOC_TYPE.getSelectionModel().getSelectedItem().getDOC_TP_ID());
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}
			// Комментарий
			callStmt.setString(5, DOC_COMMENT.getText());
			// Срочность, 'Y','N'
			if (DOC_ISFAST.isSelected()) {
				callStmt.setString(6, "Y");
			} else if (!DOC_ISFAST.isSelected()) {
				callStmt.setString(6, "N");
			}
			// Номер документа
			callStmt.setString(7, DOC_NUMBER.getText());
			// Дата поступления документа
			callStmt.setDate(8, (DOC_DATE.getValue() != null) ? java.sql.Date.valueOf(DOC_DATE.getValue()) : null);
			// Ссылка на связанный документ
			if (!DOC_REF.getText().equals("")) {
				callStmt.setLong(9, Integer.valueOf(DOC_REF.getText()));
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			// Ссылка на организацию
			if (DOC_ORG.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(10, DOC_ORG.getSelectionModel().getSelectedItem().getORG_ID());
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
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
		Stage stage = (Stage) DOC_TYPE.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	VPM_DOCS class_;
	Connection conn;

	public void SetClass(VPM_DOCS class_, Connection conn) {
		this.class_ = class_;
		this.conn = conn;
	}

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
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			new ConvConst().FormatDatePiker(DOC_END);
			new ConvConst().FormatDatePiker(DOC_DATE);
			// -------------------
			DOC_NUMBER.setText(class_.getDOC_NUMBER());

			DOC_END.setValue(class_.getDOC_END());
			DOC_DATE.setValue(class_.getDOC_DATE());

			if (class_.getDOC_ISFAST().equals("Да")) {
				DOC_ISFAST.setSelected(true);
			} else if (class_.getDOC_ISFAST().equals("Нет")) {
				DOC_ISFAST.setSelected(false);
			}
			DOC_COMMENT.setText(class_.getDOC_COMMENT());
			if (class_.getDOC_REF() != 0) {
				DOC_REF.setText(String.valueOf(class_.getDOC_REF()));
			}
			DOC_NAME.setText(class_.getDOC_NAME());
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
				for (PM_DOC_TYPES sel : DOC_TYPE.getItems()) {
					if (sel.getDOC_TP_ID().equals(class_.getDOC_TP_ID())) {
						DOC_TYPE.getSelectionModel().select(sel);
						break;
					}
				}
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
				for (PM_ORG sel : DOC_ORG.getItems()) {
					if (sel.getORG_ID().equals(class_.getORG_ID())) {
						DOC_ORG.getSelectionModel().select(sel);
						break;
					}
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

}
