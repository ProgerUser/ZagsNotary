package ru.psv.mj.prjmngm.projects;

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
import java.sql.Types;
import java.text.SimpleDateFormat;
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
import ru.psv.mj.prjmngm.projects.model.VPM_PROJECTS;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;
import ru.psv.mj.www.pl.jsolve.Docx;
import ru.psv.mj.www.pl.jsolve.TextVariable;
import ru.psv.mj.www.pl.jsolve.VariablePattern;
import ru.psv.mj.www.pl.jsolve.Variables;

public class EditPmPrjC {
	public File FileWord;
	public String AddEdit;

	/**
	 * Конструктор
	 */
	public EditPmPrjC() {
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
//			// <FXML>---------------------------------------
//			Stage stage = new Stage();
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/inboxdocs/SelRootPmDocView.fxml"));
//
//			SelRootPmDocController controller = new SelRootPmDocController();
//			controller.SetClass(class_, conn);
//			loader.setController(controller);
//
//			Parent root = loader.load();
//			stage.setScene(new Scene(root));
//			stage.getIcons().add(new Image("/icon.png"));
//			stage.setTitle("Список документов:");
//			stage.initOwner((Stage) DOC_ORG.getScene().getWindow());
//			stage.setResizable(true);
//			stage.initModality(Modality.WINDOW_MODAL);
//			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//				@Override
//				public void handle(WindowEvent paramT) {
//					try {
//						if (controller.getStatus()) {
//							DOC_REF.setText(String.valueOf(controller.getRetId()));
//						}
//					} catch (Exception e) {
//						DbUtil.Log_Error(e);
//					}
//				}
//			});
//			stage.show();
//			// </FXML>---------------------------------------
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
			CallableStatement callStmt = conn.prepareCall("{ call PM_DOC.EDIT_DOC_INBOX(?,?,?,?,?,?,?,?,?,?,?)}");
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
				callStmt.setLong(9, Long.valueOf(DOC_REF.getText()));
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			// Ссылка на организацию
			if (DOC_ORG.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(10, DOC_ORG.getSelectionModel().getSelectedItem().getORG_ID());
			} else {
				callStmt.setNull(10, java.sql.Types.INTEGER);
			}
			// Наименование
			callStmt.setString(11, DOC_NAME.getText());
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
			new ConvConst().TableColumnDateTime(DW_DATE);
			new ConvConst().TableColumnDateTime(DS_DATE);
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
			// WORD
			LoadTableWord();
			// Scan
			LoadTableScan();
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

			MenuItem menuFileSave = new MenuItem(menuFile, SWT.CASCADE);
			menuFileSave.setText("Сохранить");
			menuFileSave.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (AddEdit.equals("Edit")) {
						clientSite.save(FileWord, false);
						shell.close();
					} else if (AddEdit.equals("Add")) {
						FileWord = new File(
								System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
						clientSite.save(FileWord, false);
						shell.close();
					}
				}
			});
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
