package ru.psv.mj.prjmngm.outboxdocs;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import javafx.util.StringConverter;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.doc.type.PM_DOC_TYPES;
import ru.psv.mj.prjmngm.inboxdocs.model.PM_ORG;
import ru.psv.mj.prjmngm.inboxdocs.model.VPM_DOC_SCANS;
import ru.psv.mj.prjmngm.inboxdocs.model.VPM_DOC_WORD;
import ru.psv.mj.prjmngm.projects.model.PM_PRJ_STATUS;
import ru.psv.mj.prjmngm.projects.model.VPM_PROJECTS;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;
import ru.psv.mj.www.pl.jsolve.Docx;
import ru.psv.mj.www.pl.jsolve.TextVariable;
import ru.psv.mj.www.pl.jsolve.VariablePattern;
import ru.psv.mj.www.pl.jsolve.Variables;

public class EditOutBoxDoc {
	public File FileWord;
	public String AddEdit;

	/**
	 * Конструктор
	 */
	public EditOutBoxDoc() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

	@FXML
	private ComboBox<PM_DOC_TYPES> DOC_TYPE;
	@FXML
	private ComboBox<PM_ORG> DOC_ORG;
	/**
	 * Статус
	 */
	@FXML
	private ComboBox<PM_PRJ_STATUS> PRJ_STATUS;
	@FXML
	private TextField DOC_NUMBER;
	@FXML
	private TextField DOC_ISFH_NUMBER;
	@FXML
	private DatePicker DOC_END;
	@FXML
	private DatePicker DOC_DATE;
	@FXML
	private DatePicker DOC_ISH_DATE;
	@FXML
	private CheckBox DOC_ISFAST;
	@FXML
	private TextField DOC_COMMENT;
	@FXML
	private TextField DOC_REF;
	/**
	 * Сотрудник
	 */
	@FXML
	private TextField PRJ_EMP;
	@FXML
	private TextField DOC_NAME;
	@FXML
	private TableView<VPM_DOC_WORD> DocWord;
	@FXML
	private TableColumn<VPM_DOC_WORD, Long> DocWordId;
	@FXML
	private TableColumn<Object, LocalDateTime> DW_DATE;
	@FXML
	private TableColumn<VPM_DOC_WORD, String> DocWordFilename;
	@FXML
	private TableColumn<VPM_DOC_WORD, String> DocWordExt;
	@FXML
	private TableColumn<VPM_DOC_WORD, String> DocWordKb;
	@FXML
	private TableView<VPM_DOC_SCANS> DocScans;
	@FXML
	private TableColumn<VPM_DOC_SCANS, Long> DocScanId;
	@FXML
	private TableColumn<VPM_DOC_SCANS, String> DocScanFileName;
	@FXML
	private TableColumn<VPM_DOC_SCANS, String> DocScanExt;
	@FXML
	private TableColumn<VPM_DOC_SCANS, String> DocScanKb;
	@FXML
	private TableColumn<Object, LocalDateTime> DS_DATE;

	@FXML
	private Button BtSelEmp;

	/**
	 * Добавить Скан
	 * 
	 * @param event
	 */
	@FXML
	void AddScan(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбрать файл");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF документ", "*.pdf"),
					new ExtensionFilter("JPEG документ", "*.jpeg"), new ExtensionFilter("JPG документ", "*.jpg"));
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				String name = file.getName();
				String paths = file.getAbsolutePath();
				String ext = FilenameUtils.getExtension(file.getAbsolutePath());
				System.out.println(ext);
				// получить файл
				byte[] bArray = null;
				Path path = Paths.get(paths);
				bArray = java.nio.file.Files.readAllBytes(path);
				InputStream is = new ByteArrayInputStream(bArray);
				// Вставить
				PreparedStatement prp = conn.prepareStatement("insert into PM_DOC_SCANS "
						+ "(DS_FILENAME,DS_TYPE,DS_FILE,DS_DOCID) " + "values " + "(?,?,?,?)");
				prp.setString(1, name);
				prp.setString(2, ext);
				prp.setBlob(3, is, bArray.length);
				prp.setLong(4, class_.getDOC_ID());
				prp.executeUpdate();
				prp.close();
				is.close();
				// Сохраним транзакцию
				conn.commit();
				// Обновим
				LoadTableScan();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Добавить файл
	 * 
	 * @param event
	 */
	@FXML
	void AddWord(ActionEvent event) {
		try {
			AddEdit = "Add";
			new AbrirWordJFrame();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Распечатать pdf
	 * 
	 * @param event
	 */
	@FXML
	void ScanPrint(ActionEvent event) {
		try {
			if (DocScans.getSelectionModel().getSelectedItem() != null) {
				VPM_DOC_SCANS sel = DocScans.getSelectionModel().getSelectedItem();
				PreparedStatement prp = conn
						.prepareStatement("select DS_FILE,DS_FILENAME,DS_TYPE from PM_DOC_SCANS t where DS_ID = ?");
				prp.setLong(1, sel.getDS_ID());
				ResultSet rs = prp.executeQuery();
				String file_format = "";
				String fname = "";
				Blob blob = null;
				if (rs.next()) {
					blob = rs.getBlob("DS_FILE");
					file_format = rs.getString("DS_TYPE");
					fname = rs.getString("DS_FILENAME");
				}
				prp.close();
				rs.close();
				// ---------------------
				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);

				File tempFile = File.createTempFile(fname, "." + file_format,
						new File(System.getenv("MJ_PATH") + "OutReports"));
				OutputStream outStream = new FileOutputStream(tempFile);
				outStream.write(blobAsBytes);

				tempFile.deleteOnExit();
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(tempFile);
				}
				outStream.close();
				blob.free();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DelRef(ActionEvent event) {
		DOC_REF.setText("");
	}

	@FXML
	void DOC_REF(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Выбрать сотрудника
	 * 
	 * @param event
	 */
	@FXML
	void SelPrjRef(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить сотрудника
	 * 
	 * @param event
	 */
	@FXML
	void DelPrjRef(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить скан
	 * 
	 * @param event
	 */
	@FXML
	void DeleteScan(ActionEvent event) {
		try {
			if (DocScans.getSelectionModel().getSelectedItem() != null) {
				VPM_DOC_SCANS sel = DocScans.getSelectionModel().getSelectedItem();

				final Alert alert = new Alert(AlertType.CONFIRMATION, "Удалить " + sel.getDS_ID() + "?", ButtonType.YES,
						ButtonType.NO);
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
					PreparedStatement prp = conn.prepareStatement("delete from PM_DOC_SCANS where DS_ID = ?");
					prp.setLong(1, sel.getDS_ID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					LoadTableWord();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DeleteWord(ActionEvent event) {
		try {
			if (DocWord.getSelectionModel().getSelectedItem() != null) {
				VPM_DOC_WORD sel = DocWord.getSelectionModel().getSelectedItem();

				final Alert alert = new Alert(AlertType.CONFIRMATION, "Удалить " + sel.getDW_ID() + "?", ButtonType.YES,
						ButtonType.NO);
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
					PreparedStatement prp = conn.prepareStatement("delete from PM_DOC_WORD where DW_ID = ?");
					prp.setLong(1, sel.getDW_ID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					LoadTableWord();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Редактировать файл
	 * 
	 * @param event
	 */
	@FXML
	void EditWord(ActionEvent event) {
		try {
			if (DocWord.getSelectionModel().getSelectedItem() != null) {
				VPM_DOC_WORD sel = DocWord.getSelectionModel().getSelectedItem();
				{
					// <prp>
					PreparedStatement prp = conn.prepareStatement("select DW_FILE from PM_DOC_WORD t where DW_ID = ?");
					prp.setLong(1, sel.getDW_ID());
					ResultSet rs = prp.executeQuery();
					InputStream is = null;
					if (rs.next()) {
						is = rs.getBinaryStream("DW_FILE");
					}
					prp.close();
					rs.close();
					// </prp>
					File targetFile = new File(
							System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
					FileUtils.copyInputStreamToFile(is, targetFile);
					is.close();
					IOUtils.closeQuietly(is);
					FileWord = targetFile;
				}
				AddEdit = "Edit";
				new AbrirWordJFrame();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Обновить pdf
	 * 
	 * @param event
	 */
	@FXML
	void ReshreshScan(ActionEvent event) {
		try {
			LoadTableScan();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Обновить WORD
	 * 
	 * @param event
	 */
	@FXML
	void ReshreshWord(ActionEvent event) {
		try {
			LoadTableWord();
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
	void CopyFromTempl(ActionEvent event) {
		try {
			if (DOC_TYPE.getSelectionModel().getSelectedItem() != null & !DOC_NAME.getText().equals("")) {
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
				acc.setText(DOC_NAME.getText());

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
					try {
						String SQL = "";
						// <prp>
						{
							PreparedStatement prp = conn
									.prepareStatement("select DOC_TP_SQL from PM_DOC_TYPES where doc_tp_id = ?");
							prp.setLong(1, class_.getDOC_TP_ID());
							ResultSet rs = prp.executeQuery();
							if (rs.next()) {
								SQL = rs.getString(1);
							}
							prp.close();
							rs.close();
						}
						// </prp>
						InputStream is = null;
						// <prp>
						{
							PreparedStatement prp = conn
									.prepareStatement("select DOC_TP_WORD from PM_DOC_TYPES where doc_tp_id = ?");
							prp.setLong(1, class_.getDOC_TP_ID());
							ResultSet rs = prp.executeQuery();
							if (rs.next()) {
								is = rs.getBinaryStream("DOC_TP_WORD");
							}
							prp.close();
							rs.close();
						}
						// </prp>
						File targetFile = new File(
								System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
						FileUtils.copyInputStreamToFile(is, targetFile);
						is.close();
						IOUtils.closeQuietly(is);
						// Вызов
						Docx docx = new Docx(targetFile.getAbsolutePath());
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						String str = "";
						Map<String, String> map = new HashMap<String, String>();
						// <prp>
						{
							PreparedStatement prp = conn.prepareStatement(SQL);
							prp.setLong(1, class_.getDOC_ID());
							ResultSet rs = prp.executeQuery();
							while (rs.next()) {
								map.put(rs.getString("NAME_"), rs.getString("VALUE_"));
							}
							prp.close();
							rs.close();
						}
						// </prp>
						for (String list : docx.findVariables()) {
							if (list.contains("_")) {
								str = list.replace("#{", "").replace("}", "");
								str = str.substring(0, str.indexOf("_"));
							} else if (!list.contains("_")) {
								str = list.replace("#{", "").replace("}", "");
							}
							variables.addTextVariable(
									new TextVariable(list, (map.get(str) == null) ? "ПУСТО!" : map.get(str)));
						}
						// Сохранить
						docx.fillTemplate(variables);
						docx.save(targetFile.getAbsolutePath());
						// Добавить в таблицу
						{
							String fext = targetFile.getName();
							fext = fext.substring(fext.indexOf(".") + 1, fext.length());
							// System.out.println(fext);
							PreparedStatement prp = conn.prepareStatement(
									"insert into " + "PM_DOC_WORD (" + "dw_filename, \r\n" + "dw_type, \r\n"
											+ "dw_file, \r\n" + "dw_docid" + ") " + "values (?,?,?,?)");
							prp.setString(1, acc.getText());
							prp.setString(2, fext);
							// получить файл
							byte[] bArray = java.nio.file.Files.readAllBytes(Paths.get(targetFile.getAbsolutePath()));
							ByteArrayInputStream barr = new ByteArrayInputStream(bArray);
							InputStream ist = barr;

							// </>
							prp.setBlob(3, ist, bArray.length);
							prp.setLong(4, class_.getDOC_ID());
							prp.executeUpdate();
							prp.close();

							ist.close();
							barr.close();
							IOUtils.closeQuietly(barr);
							IOUtils.closeQuietly(ist);

							conn.commit();
							LoadTableWord();
							// delete file
							if (targetFile != null && targetFile.exists()) {
								targetFile.delete();
							}
							// ------------
						}
					} catch (Exception e) {
						DbUtil.Log_Error(e);
					}
				});
			} else {
				Msg.Message("Выберите шаблон и введите наименование!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Авто расширение
	 * 
	 * @param table
	 */
	void ResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column_) -> {
			if (column_.getText().equals("")) {
			} else {
				Text t = new Text(column_.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					if (column_.getCellData(i) != null) {
						if (column_.getCellData(i) != null) {
							t = new Text(column_.getCellData(i).toString());
							double calcwidth = t.getLayoutBounds().getWidth();
							if (calcwidth > max) {
								max = calcwidth;
							}
						}
					}
				}
				column_.setPrefWidth(max + 20.0d);
			}
		});
	}

	/**
	 * Initialize table Word
	 */
	void LoadTableWord() {
		try {
			// loop
			String selectStmt = "select * from vpm_doc_word t where DW_DOCID = ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, class_.getDOC_ID());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_DOC_WORD> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_DOC_WORD list = new VPM_DOC_WORD();
				list.setDW_DOCID(rs.getLong("DW_DOCID"));
				list.setTM$DW_DATE((rs.getDate("TM$DW_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DW_DATE")),
						DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
				list.setDW_ID(rs.getLong("DW_ID"));
				list.setDW_FILENAME(rs.getString("DW_FILENAME"));
				list.setDW_TYPE(rs.getString("DW_TYPE"));
				list.setDocWordKb(rs.getString("DocWordKb"));

				obslist.add(list);
			}
			// add data
			DocWord.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_DOC_WORD> tableFilter = TableFilter.forTableView(DocWord).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(DocWord);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Initialize table Scan
	 */
	void LoadTableScan() {
		try {
			// loop
			String selectStmt = "select * from VPM_DOC_SCANS t where DS_DOCID = ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, class_.getDOC_ID());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_DOC_SCANS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_DOC_SCANS list = new VPM_DOC_SCANS();
				list.setTM$DS_DATE((rs.getDate("TM$DS_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DS_DATE")),
						DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
				list.setDocScanKb(rs.getString("DocScanKb"));
				list.setDS_FILENAME(rs.getString("DS_FILENAME"));
				list.setDS_TYPE(rs.getString("DS_TYPE"));
				list.setDS_DOCID(rs.getLong("DS_DOCID"));
				list.setDS_ID(rs.getLong("DS_ID"));
				obslist.add(list);
			}
			// add data
			DocScans.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_DOC_SCANS> tableFilter = TableFilter.forTableView(DocScans).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(DocScans);
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
			CallableStatement callStmt = conn.prepareCall("{ call PM_DOC.EDIT_DOC_OUTBOX(?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// ID документы
			if (class_ != null) {
				callStmt.setLong(2, class_.getDOC_ID());
			} else {
				callStmt.setNull(2, java.sql.Types.INTEGER);
			}
			// ID ПРОЕКТА
			if (class_ != null) {
				callStmt.setLong(3, class_.getPRJ_ID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}
			// ID Сотрудника
			if (class_ != null) {
				callStmt.setLong(4, class_.getEMP_ID());
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}
			// Дата отправки
			callStmt.setDate(5,
					(DOC_ISH_DATE.getValue() != null) ? java.sql.Date.valueOf(DOC_ISH_DATE.getValue()) : null);
			// Номер документа исх.
			callStmt.setString(6, DOC_ISFH_NUMBER.getText());
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

	VPM_PROJECTS class_;
	Connection conn;

	public void SetClass(VPM_PROJECTS class_, Connection conn) {
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
	 * Статус
	 */
	private void PrjStatusCombo() {
		PRJ_STATUS.setConverter(new StringConverter<PM_PRJ_STATUS>() {
			@Override
			public String toString(PM_PRJ_STATUS object) {
				return object != null ? object.getPJST_ID() + "=" + object.getPJST_NAME() : "";
			}

			@Override
			public PM_PRJ_STATUS fromString(final String string) {
				return PRJ_STATUS.getItems().stream()
						.filter(product -> (product.getPJST_ID() + "=" + product.getPJST_NAME()).equals(string))
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
	 * Обновить данные модели
	 * 
	 * @throws SQLException
	 */
	void LoadModel() throws SQLException {
		String selectStmt = "SELECT * FROM VPM_PROJECTS PRJ where PRJ.PRJ_ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
		prepStmt.setLong(1, class_.getPRJ_ID());
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			VPM_PROJECTS list = new VPM_PROJECTS();

			list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
					? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")),
							DateTimeFormatter.ofPattern("dd.MM.yyyy"))
					: null);
			list.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
			list.setEMP_TEL(rs.getString("EMP_TEL"));
			list.setPRJ_STATUS(rs.getLong("PRJ_STATUS"));
			list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
			list.setDOC_REF(rs.getLong("DOC_REF"));
			list.setPRJ_STATUS_CHAR(rs.getString("PRJ_STATUS_CHAR"));
			list.setEMP_WORKEND((rs.getDate("EMP_WORKEND") != null)
					? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKEND")),
							DateTimeFormatter.ofPattern("dd.MM.yyyy"))
					: null);
			list.setEMP_ID(rs.getLong("EMP_ID"));
			list.setEMP_POSITION(rs.getString("EMP_POSITION"));
			list.setDOC_COMMENT(rs.getString("DOC_COMMENT"));
			list.setPRJ_EMP_LOGIN(rs.getString("PRJ_EMP_LOGIN"));
			list.setEMP_WORKSTART((rs.getDate("EMP_WORKSTART") != null)
					? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKSTART")),
							DateTimeFormatter.ofPattern("dd.MM.yyyy"))
					: null);
			list.setDTDIFF(rs.getLong("DTDIFF"));
			list.setTM$DOC_START((rs.getDate("TM$DOC_START") != null) ? LocalDateTime.parse(
					new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_START")),
					DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
			list.setORG_ID(rs.getLong("ORG_ID"));
			list.setORG_RUK(rs.getString("ORG_RUK"));
			list.setPRJ_ID(rs.getLong("PRJ_ID"));
			list.setEMP_LOGIN(rs.getLong("EMP_LOGIN"));
			list.setPRJ_CREUSR(rs.getString("PRJ_CREUSR"));
			list.setDOC_TP_NAME(rs.getString("DOC_TP_NAME"));
			list.setEMP_MIDDLENAME(rs.getString("EMP_MIDDLENAME"));
			list.setDOC_ISFAST(rs.getString("DOC_ISFAST"));
			list.setPRJ_DOCID(rs.getLong("PRJ_DOCID"));
			list.setEMP_FIRSTNAME(rs.getString("EMP_FIRSTNAME"));
			list.setEMP_BOSS(rs.getLong("EMP_BOSS"));
			list.setDTDIFF_CH(rs.getString("DTDIFF_CH"));
			list.setEMP_JBTYPE(rs.getLong("EMP_JBTYPE"));
			list.setDOC_ID(rs.getLong("DOC_ID"));
			list.setTM$PRJ_STARTDATE((rs.getDate("TM$PRJ_STARTDATE") != null) ? LocalDateTime.parse(
					new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$PRJ_STARTDATE")),
					DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
			list.setPRJ_EMP(rs.getLong("PRJ_EMP"));
			list.setDOC_USR(rs.getString("DOC_USR"));
			list.setDOC_TP_ID(rs.getLong("DOC_TP_ID"));
			list.setORG_NAME(rs.getString("ORG_NAME"));
			list.setEMP_LASTNAME(rs.getString("EMP_LASTNAME"));
			list.setDOC_END((rs.getDate("DOC_END") != null)
					? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_END")),
							DateTimeFormatter.ofPattern("dd.MM.yyyy"))
					: null);
			list.setDOC_NAME(rs.getString("DOC_NAME"));
			class_ = list;
		}
	}

	/**
	 * Изменить статус
	 */
	@FXML
	void ChangeStatus(ActionEvent event) {
		try {
			if (PRJ_STATUS.getSelectionModel().getSelectedItem() != null) {
				PM_PRJ_STATUS sel = PRJ_STATUS.getSelectionModel().getSelectedItem();

				if (!sel.getPJST_ID().equals(class_.getPRJ_STATUS())) {

					final Alert alert = new Alert(AlertType.CONFIRMATION, "Изменить статус " + sel.getPJST_NAME() + "?",
							ButtonType.YES, ButtonType.NO);
					if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
							.orElse(ButtonType.NO) == ButtonType.YES) {
						String update = "DECLARE\r\n" + "  STATUS_ NUMBER := ?;\r\n" + "  PRJID_  NUMBER := ?;\r\n"
								+ "  EMP_    NUMBER := ?;\r\n" + "BEGIN\r\n" + "  INSERT INTO PM_PRJ_STAT_HIST\r\n"
								+ "    (STH_PRJ, STH_STAT, STH_EMP)\r\n" + "  VALUES\r\n"
								+ "    (PRJID_, STATUS_, EMP_);\r\n"
								+ "  UPDATE PM_PROJECTS SET PRJ_STATUS = STATUS_ WHERE PRJ_ID = PRJID_;\r\n"
								+ "END;\r\n" + "";
						PreparedStatement prp = conn.prepareStatement(update);
						prp.setLong(1, sel.getPJST_ID());
						prp.setLong(2, class_.getPRJ_ID());
						prp.setLong(3, class_.getEMP_ID());
						try {
							prp.executeUpdate();
							conn.commit();
							LoadModel();
						} catch (SQLException e) {
							conn.rollback();
							DbUtil.Log_Error(e);
						}
						prp.close();
					} else {
						for (PM_PRJ_STATUS sel_ : PRJ_STATUS.getItems()) {
							if (sel_.getPJST_ID().equals(class_.getPRJ_STATUS())) {
								PRJ_STATUS.getSelectionModel().select(sel_);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void PrjStatus() throws SQLException {

		// -------------------
		{
			String selectStmt = "SELECT * from PM_PRJ_STATUS_GR";
			// System.out.println(selectStmt);
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<PM_PRJ_STATUS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				PM_PRJ_STATUS list = new PM_PRJ_STATUS();
				list.setPJST_ID(rs.getLong("PJST_ID"));
				list.setPJST_NAME(rs.getString("PJST_NAME"));
				obslist.add(list);
			}
			prepStmt.close();
			rs.close();
			PRJ_STATUS.setItems(obslist);
//						FxUtilTest.getComboBoxValue(PRJ_STATUS);
//						FxUtilTest.autoCompleteComboBoxPlus(PRJ_STATUS,
//								(typedText, itemToCompare) -> (itemToCompare.getORG_ID() + "=" + itemToCompare.getORG_NAME())
//										.toLowerCase().contains(typedText.toLowerCase()));
			PrjStatusCombo();
			for (PM_PRJ_STATUS sel : PRJ_STATUS.getItems()) {
				if (sel.getPJST_ID().equals(class_.getPRJ_STATUS())) {
					PRJ_STATUS.getSelectionModel().select(sel);
					break;
				}
			}
		}
	}

	void OrgRuk() throws SQLException {
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
	}

	void DocTypes() throws SQLException {
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
					(typedText, itemToCompare) -> (itemToCompare.getDOC_TP_ID() + "=" + itemToCompare.getDOC_TP_NAME())
							.toLowerCase().contains(typedText.toLowerCase()));
			convertComboDisplayList();
			for (PM_DOC_TYPES sel : DOC_TYPE.getItems()) {
				if (sel.getDOC_TP_ID().equals(class_.getDOC_TP_ID())) {
					DOC_TYPE.getSelectionModel().select(sel);
					break;
				}
			}
		}
	}

	void InitTabCpl() {
		// -------------------
		DocWordId.setCellValueFactory(cellData -> cellData.getValue().DW_IDProperty().asObject());
		DocWordExt.setCellValueFactory(cellData -> cellData.getValue().DW_TYPEProperty());
		DocWordFilename.setCellValueFactory(cellData -> cellData.getValue().DW_FILENAMEProperty());
		DocWordKb.setCellValueFactory(cellData -> cellData.getValue().DocWordKbProperty());
		DW_DATE.setCellValueFactory(cellData -> ((VPM_DOC_WORD) cellData.getValue()).TM$DW_DATEProperty());
		// =-=-=-=-=---=-==-=-=-=-=--=
		DocScanId.setCellValueFactory(cellData -> cellData.getValue().DS_IDProperty().asObject());
		DocScanExt.setCellValueFactory(cellData -> cellData.getValue().DS_TYPEProperty());
		DocScanFileName.setCellValueFactory(cellData -> cellData.getValue().DS_FILENAMEProperty());
		DocScanKb.setCellValueFactory(cellData -> cellData.getValue().DocScanKbProperty());
		DS_DATE.setCellValueFactory(cellData -> ((VPM_DOC_SCANS) cellData.getValue()).TM$DS_DATEProperty());
		// -------------------
	}

	void InitFields() {
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
		PRJ_EMP.setText(class_.getEMP_LASTNAME() + " " + class_.getEMP_FIRSTNAME() + " " + class_.getEMP_MIDDLENAME());
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			new ConvConst().FormatDatePiker(DOC_END);
			new ConvConst().FormatDatePiker(DOC_DATE);
			new ConvConst().TableColumnDateTime(DW_DATE);
			new ConvConst().TableColumnDateTime(DS_DATE);

			InitTabCpl();

			InitFields();

			PrjStatus();

			OrgRuk();

			DocTypes();

			// WORD
			LoadTableWord();
			// Scan
			LoadTableScan();
			// Права на изменение сотрудника
			if (DbUtil.Odb_Action(Long.valueOf(282)) > 0) {
				// BtSelEmp.setDisable(false);
			}
			// Выбор статуса

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Класс работы с Word
	 * 
	 * @author saidp
	 *
	 */
	public class AbrirWordJFrame {
		OleClientSite clientSite;
		OleFrame frame;
		KeyListener keyListener;
		Shell shell;
		Display display;

		public AbrirWordJFrame() {
			display = new Display();
			shell = new Shell(display);

			shell.setSize(800, 600);
			shell.setText("MS Word");
			shell.setLayout(new FillLayout());

			org.eclipse.swt.graphics.Image image = new org.eclipse.swt.graphics.Image(display,
					getClass().getResourceAsStream("/icon.png"));
			shell.setImage(image);

			try {
				frame = new OleFrame(shell, SWT.APPLICATION_MODAL);
				clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document");
				addFileMenu(frame);
				System.out.println(" I am in run method ");
			} catch (final SWTError e) {
				System.out.println("Unable to open activeX control");
				display.dispose();
				return;
			}

			// При закрытии
			shell.addListener(SWT.Close, new Listener() {
				public void handleEvent(Event event) {
					if (clientSite.isDirty()) {
						final Alert alert = new Alert(AlertType.CONFIRMATION, "Закрыть документ без сохранения?",
								ButtonType.YES, ButtonType.NO);
						if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
								.orElse(ButtonType.NO) == ButtonType.YES) {
							try {
								// delete file
								if (FileWord != null && FileWord.exists()) {
									FileUtils.forceDelete(FileUtils.getFile(FileWord));
								}
								// ------------
								FileWord = null;
							} catch (Exception e) {
								DbUtil.Log_Error(e);
							}
						} else {
							event.doit = false;
						}
					} else {
						if (FileWord != null) {
							if (AddEdit.equals("Edit")) {
								try {
									PreparedStatement prp = conn
											.prepareStatement("update PM_DOC_WORD set DW_FILE = ? where DW_ID = ?");
									// получить файл
									byte[] bArray = java.nio.file.Files
											.readAllBytes(Paths.get(FileWord.getAbsolutePath()));
									InputStream is = new ByteArrayInputStream(bArray);
									// </>
									prp.setBlob(1, is, bArray.length);
									prp.setLong(2, DocWord.getSelectionModel().getSelectedItem().getDW_ID());
									prp.executeUpdate();
									prp.close();
									is.close();
									conn.commit();
									// delete file
									if (FileWord != null && FileWord.exists()) {
										FileUtils.forceDelete(FileUtils.getFile(FileWord));
									}
									// ------------
									FileWord = null;
								} catch (Exception e) {
									DbUtil.Log_Error(e);
								}
							} else if (AddEdit.equals("Add")) {
								try {
									// Create the custom dialog.
									// Наименование создаваемого файла
									Dialog<Pair<String, String>> dialog = new Dialog<>();
									dialog.setTitle("Наименование создаваемого файла");

									Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
									stage.getIcons()
											.add(new Image(this.getClass().getResource("/icon.png").toString()));

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
									acc.setText(DOC_NAME.getText());

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
										try {
											String fext = FileWord.getName();
											fext = fext.substring(fext.indexOf(".") + 1, fext.length());
											PreparedStatement prp = conn.prepareStatement("insert into "
													+ "PM_DOC_WORD (" + "dw_filename, \r\n" + "dw_type, \r\n"
													+ "dw_file, \r\n" + "dw_docid" + ") " + "values (?,?,?,?)");
											prp.setString(1, acc.getText());
											prp.setString(2, fext);
											// получить файл
											byte[] bArray = java.nio.file.Files
													.readAllBytes(Paths.get(FileWord.getAbsolutePath()));
											InputStream ist = new ByteArrayInputStream(bArray);
											// </>
											prp.setBlob(3, ist, bArray.length);
											prp.setLong(4, class_.getDOC_ID());
											prp.executeUpdate();
											prp.close();
											ist.close();
											conn.commit();
											Platform.runLater(() -> {
												LoadTableWord();
											});
											// delete file
											if (FileWord != null && FileWord.exists()) {
												FileUtils.forceDelete(FileUtils.getFile(FileWord));
											}
											// ------------
											FileWord = null;
										} catch (Exception e) {
											DbUtil.Log_Error(e);
										}
									});

								} catch (Exception e) {
									DbUtil.Log_Error(e);
								}
							}
						}
					}
				}
			});

			// Открытие документа
			if (FileWord != null) {
				clientSite.dispose();
				clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document", FileWord);
				clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
			}
			shell.open();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			display.dispose();
		}

		void addFileMenu(OleFrame frame) {
			final Shell shell = frame.getShell();
			Menu menuBar = shell.getMenuBar();
			if (menuBar == null) {
				menuBar = new Menu(shell, SWT.BAR);
				shell.setMenuBar(menuBar);
			}
			MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
			fileMenu.setText("&Файл");
			Menu menuFile = new Menu(fileMenu);
			fileMenu.setMenu(menuFile);
			frame.setFileMenus(new MenuItem[] { fileMenu });

			MenuItem menuFilePrint = new MenuItem(menuFile, SWT.CASCADE);
			menuFilePrint.setText("Печать");
			menuFilePrint.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					clientSite.exec(OLE.OLECMDID_PRINT, OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
				}
			});

//			MenuItem menuFileSave = new MenuItem(menuFile, SWT.CASCADE);
//			menuFileSave.setText("Сохранить");
//			menuFileSave.addSelectionListener(new SelectionAdapter() {
//				public void widgetSelected(SelectionEvent e) {
//					if (AddEdit.equals("Edit")) {
//						clientSite.save(FileWord, false);
//						shell.close();
//					} else if (AddEdit.equals("Add")) {
//						FileWord = new File(
//								System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
//						clientSite.save(FileWord, false);
//						shell.close();
//					}
//				}
//			});
			MenuItem menuFileClose = new MenuItem(menuFile, SWT.CASCADE);
			menuFileClose.setText("Закрыть");
			menuFileClose.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					shell.close();
				}
			});
		}
	}
}
