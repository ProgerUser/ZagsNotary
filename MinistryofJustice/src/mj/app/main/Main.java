package mj.app.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.jyloo.syntheticafx.DesktopPane;
import com.jyloo.syntheticafx.InternalFrame;
import com.jyloo.syntheticafx.RootPane;
import com.jyloo.syntheticafx.SyntheticaFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.access.action.OdbActions;
import mj.access.grp.GrpController;
import mj.access.menu.OdbMNU;
import mj.app.model.Connect;
import mj.audit.trigger.AuList;
import mj.audit.view.Audit;
import mj.courts.CourtList;
import mj.dbutil.DBUtil;
import mj.doc.adoptoin.AdoptList;
import mj.doc.birthact.BirthList;
import mj.doc.cus.CusList;
import mj.doc.death.DeathList;
import mj.doc.divorce.DivorceList;
import mj.doc.mercer.MercerList;
import mj.doc.patern.PaternList;
import mj.doc.updabhname.UpdAbhNameList;
import mj.doc.updatenat.UpdNatList;
import mj.doc.updname.UpdNameList;
import mj.init.Settings;
import mj.init.Setup;
import mj.log.LogList;
import mj.msg.Msg;
import mj.notary.NotaryList;
import mj.otd.OtdList;
import mj.project.PRJ_FLS_FLDR;
import mj.report.FRREPRunner;
import mj.report.Report;
import mj.users.UsrC;
import mj.zags.ZagsList;


/**
 * ������� ����� ����� � �������� �� ����
 * 
 * @author Said
 *
 */
@SuppressWarnings("unused")
public class Main extends Application {
	/**
	 * ��� ���������� ���-�
	 */
	public static Logger logger = Logger.getLogger(Main.class);
	/**
	 * Stage ��� ����� ����������
	 */
	public static Stage primaryStage;
	/**
	 * BorderPane ��� ����� ����������
	 */
	public static BorderPane rootLayout;
	/**
	 * �� ������������
	 */
	public static Stage enterdtage = null;
	/**
	 * �������� ����� �������, ���� ������� ���� �����
	 */
	public static boolean CusWin = true;
	/**
	 * �������� ����� ����� � ��������, ���� ������� ���� �����
	 */
	public static boolean BActWin = true;
	/**
	 * �������� ����� �������������, ���� ������� ���� �����
	 */
	public static boolean UsrWin = true;
	/**
	 * �������� ����� ������������� � ������� ����, ���� ������� ���� �����
	 */
	public static boolean UsrMenuWin = true;
	/**
	 * �������� ����� ������������� � ������� ����, ���� ������� ���� �����
	 */
	public static boolean UsrActWin = true;
	/**
	 * �������� ����� ������ � ������� ����, ���� ������� ���� �����
	 */
	public static boolean AuditWin = true;
	/**
	 * ���������� �����
	 */
	public static boolean MercerWin = true;
	/**
	 * ����������� �����
	 */
	public static boolean DivorcerWin = true;
	/**
	 * ������������� � ������ 
	 */
	public static boolean DeathWin = true;
	/**
	 * ������������ ����������
	 */
	public static boolean PaternWin = true;
	/**
	 * ��������
	 */
	public static boolean ActWin = true;
	/**
	 * ����
	 */
	public static boolean MenuWin = true;
	/**
	 * �������� �����
	 */
	public static boolean UpdateNameWin = true;
	/**
	 * �������������� ��� �������
	 */
	public static boolean UpdateAbhNameWin = true;
	/**
	 * �������� ��������������
	 */
	public static boolean UpdNatWin = true;
	/**
	 * ����������� (����������)
	 */
	public static boolean AdoptWin = true;
	/**
	 * ��������� ������
	 */
	public static boolean AuSetupWin = true;
	/**
	 * ���������
	 */
	public static boolean OtdWin = true;
	public static boolean CourtsWin = true;
	/**
	 * ����-�
	 */
	public static boolean ZagsWin = true;
	
	public static boolean NotaryWin = true;

	/**
	 * ��������
	 */
	public static boolean Doc1cWin = true;

	/**
	 * ��� ���������
	 */
	public static boolean SettingsWin = true;

	/**
	 * ���
	 */
	public static boolean LogWin = true;

	/**
	 * ������
	 */
	public static boolean PrjWin = true;

	public static boolean ReportsWin = true;
	
	public static boolean GrpAccessWin = true;

	public static DesktopPane desktopPane;

	/**
	 * ����� ������� word � ������� �����
	 * 
	 * @throws Exception
	 */
	public void killProcess() throws Exception {

//		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
//				"wmic Path win32_process Where \"CommandLine Like '%WORD.EXE%'\" Call Terminate");
//		builder.redirectErrorStream(true);
//		Process p = builder.start();
//
//		boolean exitStatus = p.waitFor(15, TimeUnit.SECONDS);
//
//		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		String line;
//		while (true) {
//			line = r.readLine();
//			if (line == null) {
//				break;
//			}
//			System.out.println(line);
//		}
		// ���������...
		// Thread.sleep(1000);

		// ������� ��� ����� �������
		for (File file : new File(System.getenv("MJ_PATH") + "OutReports").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		// FileUtils.cleanDirectory(new File(System.getenv("MJ_PATH") + "OutReports"));
	}

	/**
	 * ���� � ����������
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			/* log4j */
			DOMConfigurator.configure(getClass().getResource("/log4j.xml"));
			//logger.info("MJ Start: " + Thread.currentThread().getName());
			Main.primaryStage = primaryStage;
			primaryStage.getIcons().add(new Image("/icon.png"));
			Main.primaryStage.setTitle("������������ �������");

			Enter();

//
//	Fast enter
//			
//			Connect.connectionURL = "localhost:1522/orcl";
//			Connect.userID = "xxi";
//			Connect.userPassword = "xxx";
//			DBUtil.dbConnect();
//			initRootLayout();
//			RT();
//
//	END 		
//			
			primaryStage.setOnCloseRequest(e -> {
				try {
					killProcess();
				} catch (Exception e1) {
					DBUtil.LOG_ERROR(e1);
				}
				DBUtil.dbDisconnect();
				SQLIETEDisconnect();
				Platform.exit();
				System.exit(0);
			});
			/* ����������� ��������� ���� */
			// primaryStage.setMaximized(true);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������������� �������� ����
	 */
	public static void initRootLayout() {
		try {
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/mj/app/rootlayout/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// desktopPane = new DesktopPane();
			// rootLayout.setCenter(desktopPane);
			Scene scene = new Scene(rootLayout); // We are sending rootLayout to the Scene.
//			Style startingStyle = Style.LIGHT;
//			JMetro jMetro = new JMetro(startingStyle);
//			System.setProperty("prism.lcdtext", "false");
//			jMetro.setScene(scene);
			// Scene scene = new Scene(new RootPane(primaryStage, rootLayout));
			primaryStage.setScene(scene); // Set the scene in primary stage.
			primaryStage.show(); // Display the primary stage

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������ �����
	 */
	public static void RT() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/First.fxml"));
			BorderPane employeeOperationsView = (BorderPane) loader.load();
			rootLayout.setCenter(employeeOperationsView);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������
	 */
	private Connection SQLIETE;

	/**
	 * ������� ������
	 */
	private void SQLIETEConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Main.logger = Logger.getLogger(getClass());
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			SQLIETE = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ��������� ������
	 */
	public void SQLIETEDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (SQLIETE != null && !SQLIETE.isClosed()) {
				SQLIETE.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������ ����
	 */
	void Setup() {
		// �������� ������������� ���� � 1�
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ����� �����
	 */
	public void Enter() {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/mj/app/enter/Enter.fxml"));
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Login");
			stage.initOwner(primaryStage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setResizable(false);
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					primaryStage.setTitle(Connect.userID + "/" + Connect.connectionURL);
				}
			});
			enterdtage = stage;
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������ �� ���������
	 */
	public static void Access3() {
		try {
			if (ActWin) {
				ActWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/access/action/ODB_ACTION.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ �� ���������");
				stage.initOwner(primaryStage);
				// stage.initModality(Modality.WINDOW_MODAL);
				OdbActions controller = loader.getController();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						ActWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * 
	 */
	public static void doc1c() {
		try {
			if (Doc1cWin) {
				Doc1cWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/init/Setup.fxml"));

				Setup controller = new Setup();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(false);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("��������� ������� � ���� ���������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						Doc1cWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	
	
	/**
	 * ����� ���������
	 */
	public static void grp_acces() {
		try {
			if (GrpAccessWin) {
				GrpAccessWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/access/grp/GrpMember.fxml"));

				GrpController controller = new GrpController();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(false);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ ������� �� �����������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						GrpAccessWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	
	/**
	 * ����� ���������
	 */
	public static void settings() {
		try {
			if (SettingsWin) {
				SettingsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/init/Settings.fxml"));

				Settings controller = new Settings();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(false);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("����� ���������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						SettingsWin = true;
						controller.SQLIETEDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ��� ���������
	 */
	public static void prglog() {
		try {
			if (LogWin) {
				LogWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/log/LogList.fxml"));

				LogList controller = new LogList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("��� ���������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						LogWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ��� ���������
	 */
	public static void reports() {
		try {
			if (ReportsWin) {
				ReportsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/report/Report.fxml"));

				Report controller = new Report();
				controller.setId(1);
				// FRREPRunner runner = new FRREPRunner();
				// controller.setDllOption(runner);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("(" + controller.getId() + ") ������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						ReportsWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ���������
	 */
	public static void COURTS() {
		try {
			if (CourtsWin) {
				CourtsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/courts/CourtList.fxml"));

				CourtList controller = new CourtList();
				loader.setController(controller);
				Parent root = loader.load();
				Scene scene = new Scene(root);
						
//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ �����");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						CourtsWin = true;
					}
				});

				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public static void OTD() {
		try {
			if (OtdWin) {
				OtdWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/otd/OtdList.fxml"));

				OtdList controller = new OtdList();
				loader.setController(controller);
				Parent root = loader.load();
				Scene scene = new Scene(root);
						
//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ ���������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						OtdWin = true;
					}
				});

				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	/**
	 * ����
	 */
	public static void ZAGS() {
		try {
			if (ZagsWin) {
				ZagsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/zags/ZagsList.fxml"));

				ZagsList controller = new ZagsList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ ����");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						ZagsWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	public static void NOTARY() {
		try {
			if (NotaryWin) {
				NotaryWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/notary/NotaryList.fxml"));

				NotaryList controller = new NotaryList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ ����������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						NotaryWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public static void prj() {
		try {
			if (PrjWin) {
				PrjWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/project/PRJ_FLS_FLDR.fxml"));

				PRJ_FLS_FLDR controller = new PRJ_FLS_FLDR();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("����� � ����� ���������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						PrjWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������ �� ���������
	 */
	public static void Admin_Menu() {
		try {
			if (MenuWin) {
				MenuWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/access/menu/ODB_MNU.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("����� ������� � ������� ����");
				stage.initOwner(primaryStage);
				// stage.initModality(Modality.WINDOW_MODAL);
				OdbMNU controller = loader.getController();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						MenuWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ����� ������
	 */
	public static void AUVIEW() {
		try {
			if (AuditWin) {
				AuditWin = false;
				Stage stage = new Stage();

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/audit/view/Audit.fxml"));

				Audit controller = new Audit();
				loader.setController(controller);
				Parent root = loader.load();

				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("�����");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						AuditWin = true;
					}
				});
				stage.show();
			}

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * �������� �����
	 */
	public static void UPDATE_NAME() {
		try {
			if (UpdateNameWin) {
				UpdateNameWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/updname/UpdNameList.fxml"));
				UpdNameList controller = new UpdNameList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("�������� �����");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						UpdateNameWin = true;
					}
				});
				stage.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ��������� ������
	 */
	public static void au_setup() {
		try {
			if (AuSetupWin) {
				AuSetupWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/audit/trigger/AuList.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("��������� ������");
				stage.initOwner(primaryStage);
				AuList controller = loader.getController();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						AuSetupWin = true;
					}
				});
				stage.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ����������� (����������)
	 */
	public static void ADOPTOIN() {
		try {
			if (AdoptWin) {
				AdoptWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/adoptoin/AdoptList.fxml"));
				AdoptList controller = new AdoptList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("����������� (����������)");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						AdoptWin = true;
					}
				});
				stage.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * �������������� ��������� �������
	 */
	public static void UPDATE_ABH_NAME() {
		try {
			if (UpdateAbhNameWin) {
				UpdateAbhNameWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/updabhname/UpdAbhNameList.fxml"));
				UpdAbhNameList controller = new UpdAbhNameList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("�������������� ��������� �������");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						UpdateAbhNameWin = true;
					}
				});
				stage.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public static void UPD_NAT() {
		try {
			if (UpdNatWin) {
				UpdNatWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/updatenat/UpdNatList.fxml"));
				UpdNatList controller = new UpdNatList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("�������� ������������ ��������������");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						UpdNatWin = true;
					}
				});
				stage.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������ �������
	 */
	public static void CUSLIST() {
		try { 
			if (CusWin) {
				CusWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/cus/CusList.fxml"));
				CusList controller = new CusList();
				loader.setController(controller);
				Parent root = loader.load();
				
				Scene scene = new Scene(root);
//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);
				
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������ �������");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						CusWin = true;
					}
				});
				stage.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ���� � ��������
	 */
	public static void BIRTH_ACT() {
		try {
			if (BActWin) {
				BActWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/birthact/BirthList.fxml"));
				BirthList controller = new BirthList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("���� � ��������");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						BActWin = true;
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������������
	 */
	public static void Users() {
		try {
			if (UsrWin) {
				UsrWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/users/Usr.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������������");
				stage.initOwner(primaryStage);
				UsrC controller = loader.getController();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						UsrWin = true;
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ���������� �����
	 * 
	 * @param event
	 */
	public static void MC_MERCER() {
		try {
			if (MercerWin) {
				MercerWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/mercer/MercerList.fxml"));
				MercerList controller = new MercerList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("���������� �����");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						MercerWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ����������� �����
	 * 
	 * @param event
	 */
	public static void DIVORCE_CERT() {
		try {
			if (DivorcerWin) {
				DivorcerWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/divorce/DivorceList.fxml"));
				DivorceList controller = new DivorceList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("����������� �����");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						DivorcerWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������������ ����������
	 * 
	 * @param event
	 */
	public static void PATERN_CERT() {
		try {
			if (PaternWin) {
				PaternWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/patern/PaternList.fxml"));
				PaternList controller = new PaternList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������������ ���������");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						PaternWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������������� � ������
	 * 
	 * @param event
	 */
	public static void DEATH_CERT() {
		try {
			if (DeathWin) {
				DeathWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/death/DeathList.fxml"));
				DeathList controller = new DeathList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������������� � ������");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						DeathWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ������� ����� �����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Locale.setDefault(Locale.ENGLISH);
		launch(args);
	}

	/**
	 * ���� ������ ���������� ����� ���������
	 * 
	 * @param table
	 */
	public static void autoResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column) -> {
			Text t = new Text(column.getText());
			double max = t.getLayoutBounds().getWidth();
			for (int i = 0; i < table.getItems().size(); i++) {
				if (column.getCellData(i) != null) {
					t = new Text(column.getCellData(i).toString());
					double calcwidth = t.getLayoutBounds().getWidth();
					if (calcwidth > max) {
						max = calcwidth;
					}
				}
			}
			column.setPrefWidth(max + 10.0d);
		});
	}
}
