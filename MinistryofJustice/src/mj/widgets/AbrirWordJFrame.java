package mj.widgets;

import java.io.File;
import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import mj.msg.Msg;

public class AbrirWordJFrame {
	static OleClientSite clientSite;
	static OleFrame frame;
	static KeyListener keyListener;
	static File file;
	static Shell shell;
	static Display display;

	public static void main(final String[] args) {
		display = new Display();
		shell = new Shell(display);

		shell.setSize(800, 600);
		shell.setText("MS WORD");
		shell.setLayout(new FillLayout());

		Image image = new Image(display, AbrirWordJFrame.class.getResourceAsStream("/icon.png"));
		shell.setImage(image);

		try {
			frame = new OleFrame(shell, SWT.NONE);
			// esto abre un documento existente
			// clientSite = new OleClientSite(frame, SWT.NONE, new File("Doc1.doc"));
			// esto abre un documento en blanco
			clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document");
			addFileMenu(frame);
			System.out.println(" I am in run method ");
		} catch (final SWTError e) {
			System.out.println("Unable to open activeX control");
			display.dispose();
			return;
		}

		final Color green = display.getSystemColor(SWT.COLOR_GREEN);
		final Color orig = shell.getBackground();

		keyListener = new KeyListener() {
			public void keyReleased(KeyEvent e) {
				if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 's')) {
					shell.setBackground(orig);
					System.out.println("Key up !!");
				}
			}

			public void keyPressed(KeyEvent paramKeyEvent) {
				if (((paramKeyEvent.stateMask & SWT.CTRL) == SWT.CTRL) && (paramKeyEvent.keyCode == 's')) {
					System.out.println("CTRL+S");
					JOptionPane.showMessageDialog(null, "ctrl+s is pressed down initial ", "Warning Message",
							JOptionPane.WARNING_MESSAGE);
					if (file != null) {
						System.out.println("CTRL+S");
						clientSite.save(file, true);
						// fileSave();
						JOptionPane.showMessageDialog(null, "ctrl+s is pressed down", "Warning Message",
								JOptionPane.WARNING_MESSAGE);
					} else
						System.out.println("CTRL+S");
					JOptionPane.showMessageDialog(null, "File is null", "Warning Message", JOptionPane.WARNING_MESSAGE);
				}
			}
		};

		display.addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 's')) {
					System.out.println("From Display I am the Key down !!" + e.keyCode);
				}
			}
		});

		shell.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 's')) {
					shell.setBackground(orig);
					System.out.println("Key up !!");
				}
			}

			public void keyPressed(KeyEvent e) {
				if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 's')) {
					shell.setBackground(green);
					System.out.println("Key down !!");
				}
			}
		});

		// close
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				if(clientSite.isDirty()) {
					final Alert alert = new Alert(AlertType.CONFIRMATION,
							"Закрыть документ без сохранения?", ButtonType.YES, ButtonType.NO);
					if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
							.orElse(ButtonType.NO) == ButtonType.YES) {
					}else {
						event.doit = false;
					}
				}
			}
		});

		
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	static void addFileMenu(OleFrame frame) {
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

		MenuItem menuFileOpen = new MenuItem(menuFile, SWT.CASCADE);
		menuFileOpen.setText("Открыть");
		menuFileOpen.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fileOpen();
			}
		});

		MenuItem menuFilePrint = new MenuItem(menuFile, SWT.CASCADE);
		menuFilePrint.setText("Печать");
		menuFilePrint.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				clientSite.exec(OLE.OLECMDID_PRINT, OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
			}
		});
		
//		MenuItem menuFilePrintPreview = new MenuItem(menuFile, SWT.CASCADE);
//		menuFilePrintPreview.setText("Печать превью");
//		menuFilePrintPreview.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				clientSite.exec(OLE.OLECMDID_PRINTPREVIEW, OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
//			}
//		});
		
		MenuItem menuFileSave = new MenuItem(menuFile, SWT.CASCADE);
		menuFileSave.setText("Сохранить");
		menuFileSave.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (file != null) {
					clientSite.save(file, false);
					//clientSite.print(null)
				}
			}
		});

//		MenuItem menuFileExit = new MenuItem(menuFile, SWT.CASCADE);
//		menuFileExit.setText("Exit");
//		menuFileExit.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				shell.dispose();
//			}
//		});
	}

	static class fileSaveItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			fileOpen();
			System.out.println(" I am in widgetSelected method ");
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			fileOpen();
			System.out.println(" I am in widgetSelected method ");
		}
	}

	static void fileOpen() {
		FileDialog dialog = new FileDialog(clientSite.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.docx", "*.doc" });
		String fileName = dialog.open();
		if (fileName != null) {
			file = new File(fileName);
			clientSite.dispose();
			clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document", new File(fileName));
			clientSite.addKeyListener(keyListener);
			clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		}
	}
}