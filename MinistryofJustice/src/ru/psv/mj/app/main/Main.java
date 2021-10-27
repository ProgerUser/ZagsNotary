package ru.psv.mj.app.main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.jyloo.syntheticafx.DesktopPane;
import com.jyloo.syntheticafx.RootPane;
import com.jyloo.syntheticafx.SyntheticaFX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.access.action.OdbActions;
import ru.psv.mj.access.grp.GrpController;
import ru.psv.mj.access.menu.OdbMNU;
import ru.psv.mj.admin.project.PRJ_FLS_FLDR;
import ru.psv.mj.admin.users.UsrC;
import ru.psv.mj.app.model.Connect;
import ru.psv.mj.audit.trigger.AuList;
import ru.psv.mj.audit.view.Audit;
import ru.psv.mj.init.Settings;
import ru.psv.mj.init.Setup;
import ru.psv.mj.log.LogList;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.notary.doc.html.controller.NotaryDocList;
import ru.psv.mj.notary.template.html.controller.NtTemplate;
import ru.psv.mj.prjmngm.emps.RootPmEmpController;
import ru.psv.mj.report.Report;
import ru.psv.mj.sprav.courts.CourtList;
import ru.psv.mj.sprav.notary.NotaryList;
import ru.psv.mj.sprav.otd.OtdList;
import ru.psv.mj.sprav.zags.ZagsList;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.www.mulya.petrovich.Case;
import ru.psv.mj.www.mulya.petrovich.Gender;
import ru.psv.mj.www.mulya.petrovich.NamePart;
import ru.psv.mj.www.mulya.petrovich.PetrovichDeclinationMaker;
import ru.psv.mj.www.plushaze.traynotification.Notification;
import ru.psv.mj.www.plushaze.traynotification.Notifications;
import ru.psv.mj.www.plushaze.traynotification.TrayNotification;
import ru.psv.mj.zags.doc.adoptoin.AdoptList;
import ru.psv.mj.zags.doc.birthact.BirthList;
import ru.psv.mj.zags.doc.cus.CusList;
import ru.psv.mj.zags.doc.death.DeathList;
import ru.psv.mj.zags.doc.divorce.DivorceList;
import ru.psv.mj.zags.doc.mercer.MercerList;
import ru.psv.mj.zags.doc.patern.PaternList;
import ru.psv.mj.zags.doc.updabhname.UpdAbhNameList;
import ru.psv.mj.zags.doc.updatenat.UpdNatList;
import ru.psv.mj.zags.doc.updname.UpdNameList;

/**
 * Главная точка входа и перехода по меню
 * 
 * @author Said
 *
 */
@SuppressWarnings("unused")
public class Main extends Application {

	public static Logger logger = Logger.getLogger(Main.class);
	public static Stage primaryStage;
	public static BorderPane rootLayout;
	public static Stage enterdtage = null;
	// Только одна форма
	public static boolean CusWin = true;
	public static boolean BActWin = true;
	public static boolean UsrWin = true;
	public static boolean UsrMenuWin = true;
	public static boolean UsrActWin = true;
	public static boolean AuditWin = true;
	public static boolean MercerWin = true;
	public static boolean DivorcerWin = true;
	public static boolean DeathWin = true;
	public static boolean PaternWin = true;
	public static boolean ActWin = true;
	public static boolean MenuWin = true;
	public static boolean UpdateNameWin = true;
	public static boolean UpdateAbhNameWin = true;
	public static boolean UpdNatWin = true;
	public static boolean AdoptWin = true;
	public static boolean AuSetupWin = true;
	public static boolean OtdWin = true;
	public static boolean PmEmpWin = true;
	public static boolean PmInBoxDocsWin = true;
	public static boolean CourtsWin = true;
	public static boolean ZagsWin = true;
	public static boolean NotaryWin = true;
	public static boolean Doc1cWin = true;
	public static boolean SettingsWin = true;
	public static boolean LogWin = true;
	public static boolean PrjWin = true;
	public static boolean ReportsWin = true;
	public static boolean GrpAccessWin = true;
	public static boolean NtTempWin = true;
	public static boolean NtDocWin = true;
	public static boolean NtCliWin = true;
	// ______________________________________
	public static DesktopPane desktopPane;

	/**
	 * Убить процесс word и удалить файлы
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
		// Подождать...
		// Thread.sleep(1000);

		// Удалить все файлы отчетов
		for (File file : new File(System.getenv("MJ_PATH") + "OutReports").listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		// FileUtils.cleanDirectory(new File(System.getenv("MJ_PATH") + "OutReports"));
	}

	/**
	 * Вход в Приложение
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			/* log4j */
			DOMConfigurator.configure(getClass().getResource("/log4j.xml"));
			// logger.info("MJ Start: " + Thread.currentThread().getName());
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			Main.primaryStage = primaryStage;
			primaryStage.getIcons().add(new Image("/icon.png"));
			Main.primaryStage.setTitle("Министерство юстиции");
			

			//boolean is_upd = new ru.psv.mj.update.root.Main().start();

//			if(is_upd == false) {
//				Enter();	
//			}

//
//	Fast enter
//			
			Connect.connectionURL = "localhost:1522/orcl";
			Connect.userID = "xxi";
			Connect.userPassword = "mj_pass_123";
			DbUtil.Db_Connect();
			initRootLayout();
			RT();
			// OTD();
//			new NotaryDocList().HtmlEditor(primaryStage);
//
//	END 		
//			
			primaryStage.setOnCloseRequest(e -> {
				final Alert alert = new Alert(AlertType.CONFIRMATION, "Закрыть программу?", ButtonType.YES,
						ButtonType.NO);
				((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/icon.png"));
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
					Close();
				}else {
					e.consume();
				}
			});
			/* Максимально растянуть окно */
			//primaryStage.setMaximized(true);
			//PetrovichDeclinationMaker maker =
			// PetrovichDeclinationMaker.getInstance();
//			System.out.println(Petrovich.Lname("MALE", "Пачулия"));
//			System.out.println(Petrovich.Fname("MALE", "Саид"));
//			System.out.println(Petrovich.Mname("MALE", "Викторович"));
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация главного меню
	 */
	public static void initRootLayout() {
		try {
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/ru/psv/mj/app/rootlayout/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// desktopPane = new DesktopPane();
			// rootLayout.setCenter(desktopPane);
			Scene scene = new Scene(rootLayout); // We are sending rootLayout to the
			// Scene.
//			Style startingStyle = Style.LIGHT;
//			JMetro jMetro = new JMetro(startingStyle);
//			System.setProperty("prism.lcdtext", "false");
//			jMetro.setScene(scene);
			// Scene scene = new Scene(new RootPane(primaryStage, rootLayout, true, true));
			primaryStage.setScene(scene); // Set the scene in primary stage.
			primaryStage.show(); // Display the primary stage
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Первая форма
	 */
	public static void RT() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/First.fxml"));
			BorderPane employeeOperationsView = (BorderPane) loader.load();
			rootLayout.setCenter(employeeOperationsView);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Сессия
	 */
	private Connection SQLIETE;

	/**
	 * Открыть сессию
	 */
	private void SQLIETEConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Main.logger = Logger.getLogger(getClass());
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			SQLIETE = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отключить сессию
	 */
	public void SQLIETEDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (SQLIETE != null && !SQLIETE.isClosed()) {
				SQLIETE.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Первый вход
	 */
	void Setup() {
		// проверка инициализации базы в 1с
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Форма входа
	 */
	public void Enter() {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/ru/psv/mj/app/enter/Enter.fxml"));
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("МИНЮСТ РА");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Доступ по действиям
	 */
	public static void Access3() {
		try {
			if (ActWin) {
				ActWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/access/action/ODB_ACTION.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Доступ по действиям");
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
			DbUtil.Log_Error(e);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/init/Setup.fxml"));

				Setup controller = new Setup();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(false);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Настройка доступа к базе паспортов");
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
			DbUtil.Log_Error(e);
		}
	}

	public static void Nt_Temp() {
		try {
			if (NtTempWin) {
				NtTempWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(
						Main.class.getResource("/ru/psv/mj/notary/template/html/view/NT_TEMPLATE.fxml"));

				NtTemplate controller = new NtTemplate();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(true);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Шаблоны");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						NtTempWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Общие настройки
	 */
	public static void grp_acces() {
		try {
			if (GrpAccessWin) {
				GrpAccessWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/access/grp/GrpMember.fxml"));

				GrpController controller = new GrpController();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(true);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Группы доступа по функционалу");
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
			DbUtil.Log_Error(e);
		}
	}

	public static void NtClients() {
		try {
			if (NtCliWin) {
				NtCliWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/notary/client/view/CusList.fxml"));

				ru.psv.mj.notary.client.controller.CusList controller = new ru.psv.mj.notary.client.controller.CusList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(true);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Клиенты");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						NtCliWin = true;
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public static void Nt_Doc() {
		try {
			if (NtDocWin) {
				NtDocWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/notary/doc/html/view/Notary.fxml"));

				NotaryDocList controller = new NotaryDocList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(true);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Документы");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						NtDocWin = true;
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Общие настройки
	 */
	public static void settings() {
		try {
			if (SettingsWin) {
				SettingsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/init/Settings.fxml"));

				Settings controller = new Settings();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(false);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Общие настройки");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Лог программы
	 */
	public static void prglog() {
		try {
			if (LogWin) {
				LogWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/log/LogList.fxml"));

				LogList controller = new LogList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Лог программы");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Лог программы
	 */
	public static void reports() {
		try {
			if (ReportsWin) {
				ReportsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/report/Report.fxml"));

				Report controller = new Report();
				controller.setId(1l);
				// FRREPRunner runner = new FRREPRunner();
				// controller.setDllOption(runner);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("(" + controller.getId() + ") Печать");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отделение
	 */
	public static void COURTS() {
		try {
			if (CourtsWin) {
				CourtsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/sprav/courts/CourtList.fxml"));

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
				stage.setTitle("Список судов");
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
			DbUtil.Log_Error(e);
		}
	}

	public static void OTD() {
		try {
			if (OtdWin) {
				OtdWin = false;
				Stage stage = new Stage();

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/sprav/otd/OtdList.fxml"));

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
				stage.setTitle("Список отделении");
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
			DbUtil.Log_Error(e);
		}
	}

	public void Close() {
		try {
			killProcess();
		} catch (Exception e1) {
			DbUtil.Log_Error(e1);
		}
		DbUtil.Db_Disconnect();
		SQLIETEDisconnect();
		Platform.exit();
		System.exit(0);
	}

	public static void PmInBoxDocs() {
		try {
			if (PmInBoxDocsWin) {
				PmInBoxDocsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/prjmngm/emps/RootPmEmpView.fxml"));
				RootPmEmpController controller = new RootPmEmpController();
				loader.setController(controller);
				Parent root = loader.load();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Входящие документы");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						PmInBoxDocsWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	
	public static void PmEmp() {
		try {
			if (PmEmpWin) {
				PmEmpWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/prjmngm/emps/RootPmEmpView.fxml"));
				RootPmEmpController controller = new RootPmEmpController();
				loader.setController(controller);
				Parent root = loader.load();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Сотрудники");
				stage.initOwner(primaryStage);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						PmEmpWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ЗАГС
	 */
	public static void ZAGS() {
		try {
			if (ZagsWin) {
				ZagsWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/sprav/zags/ZagsList.fxml"));

				ZagsList controller = new ZagsList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Список ЗАГС");
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
			DbUtil.Log_Error(e);
		}
	}

	public static void NOTARY() {
		try {
			if (NotaryWin) {
				NotaryWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/sprav/notary/NotaryList.fxml"));

				NotaryList controller = new NotaryList();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Список Нотариусов");
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
			DbUtil.Log_Error(e);
		}
	}

	public static void prj() {
		try {
			if (PrjWin) {
				PrjWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/admin/project/PRJ_FLS_FLDR.fxml"));

				PRJ_FLS_FLDR controller = new PRJ_FLS_FLDR();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Файлы и папки программы");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Доступ по действиям
	 */
	public static void Admin_Menu() {
		try {
			if (MenuWin) {
				MenuWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/access/menu/ODB_MNU.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Права доступа к пунктам меню");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Форма аудита
	 */
	public static void AUVIEW() {
		try {
			if (AuditWin) {
				AuditWin = false;
				Stage stage = new Stage();

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/audit/view/Audit.fxml"));

				Audit controller = new Audit();
				loader.setController(controller);
				Parent root = loader.load();

				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Аудит");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Перемена имени
	 */
	public static void UPDATE_NAME() {
		try {
			if (UpdateNameWin) {
				UpdateNameWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/updname/UpdNameList.fxml"));
				UpdNameList controller = new UpdNameList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Перемена имени");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Настройка аудита
	 */
	public static void au_setup() {
		try {
			if (AuSetupWin) {
				AuSetupWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/audit/trigger/AuList.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Настройка аудита");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Усыновление (удочерение)
	 */
	public static void ADOPTOIN() {
		try {
			if (AdoptWin) {
				AdoptWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/adoptoin/AdoptList.fxml"));
				AdoptList controller = new AdoptList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Усыновление (удочерение)");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Восстановление абхазской фамилии
	 */
	public static void UPDATE_ABH_NAME() {
		try {
			if (UpdateAbhNameWin) {
				UpdateAbhNameWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/updabhname/UpdAbhNameList.fxml"));
				UpdAbhNameList controller = new UpdAbhNameList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Восстановление абхазской фамилии");
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
			DbUtil.Log_Error(e);
		}
	}

	public static void UPD_NAT() {
		try {
			if (UpdNatWin) {
				UpdNatWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/updatenat/UpdNatList.fxml"));
				UpdNatList controller = new UpdNatList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Перемена национальной принадлежности");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Список граждан
	 */
	public static void CUSLIST() {
		try {
			if (CusWin) {
				CusWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/cus/CusList.fxml"));
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
				stage.setTitle("Список граждан");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Акты о рождении
	 */
	public static void BIRTH_ACT() {
		try {
			if (BActWin) {
				BActWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/birthact/BirthList.fxml"));
				BirthList controller = new BirthList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Акты о рождении");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Пользователи
	 */
	public static void Users() {
		try {
			if (UsrWin) {

				UsrWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/admin/users/Usr.fxml"));
				Parent root = loader.load();
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Пользователи");
				stage.initOwner(primaryStage);
				UsrC controller = loader.getController();
				Scene scene = new Scene(new RootPane(stage, root, true, true));
				// stage.setScene(new Scene(root));

//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);

				stage.setScene(scene);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						UsrWin = true;
						controller.dbDisconnect();
					}
				});
//				InternalFrame iFrame = new InternalFrame("Пользователи", new RootPane(stage,root,true,true));
//				iFrame.getIcons().add(new Image("/icon.png"));
//				iFrame.setSelected(true);
//				desktopPane.getChildren().add(iFrame);
//				rootLayout.setCenter(desktopPane);
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Заключение брака
	 * 
	 * @param event
	 */
	public static void MC_MERCER() {
		try {
			if (MercerWin) {
				MercerWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/mercer/MercerList.fxml"));
				MercerList controller = new MercerList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Заключение брака");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Расторжение брака
	 * 
	 * @param event
	 */
	public static void DIVORCE_CERT() {
		try {
			if (DivorcerWin) {
				DivorcerWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/divorce/DivorceList.fxml"));
				DivorceList controller = new DivorceList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Расторжение брака");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Установление отцовстава
	 * 
	 * @param event
	 */
	public static void PATERN_CERT() {
		try {
			if (PaternWin) {
				PaternWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/patern/PaternList.fxml"));
				PaternList controller = new PaternList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Установление отцовства");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Свидетельство о смерти
	 * 
	 * @param event
	 */
	public static void DEATH_CERT() {
		try {
			if (DeathWin) {
				DeathWin = false;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ru/psv/mj/zags/doc/death/DeathList.fxml"));
				DeathList controller = new DeathList();
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Свидетельство о смерти");
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Главная точка входа
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Locale.setDefault(Locale.ENGLISH);
		launch(args);
	}

	/**
	 * Авто подбор расстояния между столбцами
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
			column.setPrefWidth(max + 25.0d);
		});
	}
}
