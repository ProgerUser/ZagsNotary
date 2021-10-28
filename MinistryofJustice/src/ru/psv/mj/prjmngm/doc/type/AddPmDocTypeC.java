package ru.psv.mj.prjmngm.doc.type;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class AddPmDocTypeC {

	public static File FileWord;
	@FXML
	private TextField DOC_TP_NAME;
	@FXML
	private TextField WordPath;

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			CallableStatement callStmt = conn.prepareCall("{call pm_sprav_pkg.ADD_DOC_TYPE(?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			/* Название */
			callStmt.setString(2, DOC_TP_NAME.getText());
			/* Word шаблон */
			// получить файл
			byte[] bArray = java.nio.file.Files.readAllBytes(Paths.get(WordPath.getText()));
			InputStream is = new ByteArrayInputStream(bArray);
			// </>
			callStmt.setBlob(3, is, bArray.length);
			// выполнение
			callStmt.execute();
			if (callStmt.getString(1) == null) {
				is.close();
				conn.commit();
				callStmt.close();
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
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			dbConnect();
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

	/**
	 * 
	 */
	void OnClose() {
		Stage stage = (Stage) DOC_TP_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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
				WordPath.setText(file.getAbsolutePath());
			}
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

			if(FileWord!=null) {
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
					FileWord = new File(
							System.getenv("MJ_PATH") + "OutReports/" + java.util.UUID.randomUUID() + ".docx");
					clientSite.save(FileWord, false);
					System.out.println(FileWord);
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
