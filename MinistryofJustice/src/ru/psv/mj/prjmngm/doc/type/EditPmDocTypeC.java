package ru.psv.mj.prjmngm.doc.type;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class EditPmDocTypeC {
	public File FileWord;

	/**
	 * Конструктор
	 */
	public EditPmDocTypeC() {
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
	private TextField DOC_TP_NAME;
	@FXML
	private TextField WordPath;
	@FXML
	private TextArea SQL;

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

	@FXML
	void CreWord(ActionEvent event) {
		try {
			new AbrirWordJFrame();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void OpenWord(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбрать файл");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DOCX", "*.docx*"),
					new ExtensionFilter("DOC", "*.doc"));
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				FileWord = file;
				WordPath.setText(file.getAbsolutePath());
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
			try {
				CallableStatement callStmt = conn.prepareCall("{call pm_sprav_pkg.EDIT_DOC_TYPE(?,?,?,?,?)}");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				/* ID */
				callStmt.setLong(2, class_.getDOC_TP_ID());
				/* Название */
				callStmt.setString(3, DOC_TP_NAME.getText());
				/* Word шаблон */
				// получить файл
				byte[] bArray = java.nio.file.Files.readAllBytes(Paths.get(FileWord.getAbsolutePath()));
				InputStream is = new ByteArrayInputStream(bArray);
				// </>
				callStmt.setBlob(4, is, bArray.length);
				callStmt.setString(5, SQL.getText());
				// выполнение
				callStmt.execute();
				if (callStmt.getString(1) == null) {
					is.close();
					conn.commit();
					callStmt.close();
					setStatus(true);
					OnClose();
				} else {
					is.close();
					conn.rollback();
					Msg.Message(callStmt.getString(1));
					callStmt.close();
				}
			} catch (Exception e) {
				DbUtil.Log_Error(e);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private PM_DOC_TYPES class_;
	Connection conn;

	public void SetClass(PM_DOC_TYPES class_, Connection conn) {
		this.class_ = class_;
		this.conn = conn;
	}

	void OnClose() {
		Stage stage = (Stage) DOC_TP_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			DOC_TP_NAME.setText(class_.getDOC_TP_NAME());
			SQL.setText(class_.getDOC_TP_SQL());
			{
				// <prp>
				PreparedStatement prp = conn
						.prepareStatement("select DOC_TP_WORD from PM_DOC_TYPES where doc_tp_id = ?");
				prp.setLong(1, class_.getDOC_TP_ID());
				ResultSet rs = prp.executeQuery();
				InputStream is = null;
				if (rs.next()) {
					is = rs.getBinaryStream("DOC_TP_WORD");
				}
				prp.close();
				rs.close();
				// </prp>
				File targetFile = new File(
						System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
				FileUtils.copyInputStreamToFile(is, targetFile);
				FileWord = targetFile;
			}
			WordPath.setText(FileWord.getAbsolutePath());
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
			shell.setText("MS WORD");
			shell.setLayout(new FillLayout());

			Image image = new Image(display, AbrirWordJFrame.class.getResourceAsStream("/icon.png"));
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

			// close
			shell.addListener(SWT.Close, new Listener() {
				public void handleEvent(Event event) {
					if (clientSite.isDirty()) {
						final Alert alert = new Alert(AlertType.CONFIRMATION, "Закрыть документ без сохранения?",
								ButtonType.YES, ButtonType.NO);
						if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
								.orElse(ButtonType.NO) == ButtonType.YES) {
						} else {
							event.doit = false;
						}
					} else {
						if (FileWord != null) {
							System.out.println("SaveClose");
							Platform.runLater(() -> {
								WordPath.setText(FileWord.getAbsolutePath());
							});
						}
					}
				}
			});

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
//					FileWord = new File(
//							System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
					clientSite.save(FileWord, false);
					shell.close();
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
