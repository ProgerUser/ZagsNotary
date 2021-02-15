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
 * Главная точка входа и перехода по меню
 * 
 * @author Said
 *
 */
@SuppressWarnings("unused")
public class Main extends Application {
	/**
	 * Для сохранения лог-а
	 */
	public static Logger logger = Logger.getLogger(Main.class);
	/**
	 * Stage для всего приложения
	 */
	public static Stage primaryStage;
	/**
	 * BorderPane для всего приложения
	 */
	public static BorderPane rootLayout;
	/**
	 * Не используется
	 */
	public static Stage enterdtage = null;
	/**
	 * Проверка формы граждан, если открыта одна форма
	 */
	public static boolean CusWin = true;
	/**
	 * Проверка формы актов о рождении, если открыта одна форма
	 */
	public static boolean BActWin = true;
	/**
	 * Проверка формы пользователей, если открыта одна форма
	 */
	public static boolean UsrWin = true;
	/**
	 * Проверка формы пользователей и пунктов меню, если открыта одна форма
	 */
	public static boolean UsrMenuWin = true;
	/**
	 * Проверка формы пользователей и пунктов меню, если открыта одна форма
	 */
	public static boolean UsrActWin = true;
	/**
	 * Проверка формы аудита и пунктов меню, если открыта одна форма
	 */
	public static boolean AuditWin = true;
	/**
	 * Заключение брака
	 */
	public static boolean MercerWin = true;
	/**
	 * Расторжение брака
	 */
	public static boolean DivorcerWin = true;
	/**
	 * Свидетельство о смерти 
	 */
	public static boolean DeathWin = true;
	/**
	 * Установление отцовстава
	 */
	public static boolean PaternWin = true;
	/**
	 * Действия
	 */
	public static boolean ActWin = true;
	/**
	 * Меню
	 */
	public static boolean MenuWin = true;
	/**
	 * Перемена имени
	 */
	public static boolean UpdateNameWin = true;
	/**
	 * Восстановление АБХ фамилии
	 */
	public static boolean UpdateAbhNameWin = true;
	/**
	 * Перемена национальности
	 */
	public static boolean UpdNatWin = true;
	/**
	 * Усыновление (удочерение)
	 */
	public static boolean AdoptWin = true;
	/**
	 * Настройка аудита
	 */
	public static boolean AuSetupWin = true;
	/**
	 * Отделения
	 */
	public static boolean OtdWin = true;
	/**
	 * ЗАГС-ы
	 */
	public static boolean ZagsWin = true;
	
	public static boolean NotaryWin = true;

	/**
	 * Паспорта
	 */
	public static boolean Doc1cWin = true;

	/**
	 * ОБЩ настройки
	 */
	public static boolean SettingsWin = true;

	/**
	 * Лог
	 */
	public static boolean LogWin = true;

	/**
	 * Проект
	 */
	public static boolean PrjWin = true;

	public static boolean ReportsWin = true;
	
	public static boolean GrpAccessWin = true;

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
			//logger.info("MJ Start: " + Thread.currentThread().getName());
			Main.primaryStage = primaryStage;
			primaryStage.getIcons().add(new Image("/icon.png"));
			Main.primaryStage.setTitle("Министерство юстиции");

			/*
			 * { // открыть сессию с sqlite SQLIETEConnect(); PreparedStatement stsmt =
			 * SQLIETE.prepareStatement("select value from props where name = 'init'");
			 * ResultSet rs = stsmt.executeQuery(); if (rs.next()) { // если первая
			 * инициализация if (rs.getString(1).equals("N")) { Stage stage = new Stage();
			 * Stage stage_ = primaryStage; FXMLLoader loader = new FXMLLoader();
			 * loader.setLocation(getClass().getResource("/init/Setup.fxml"));
			 * 
			 * Setup controller = new Setup(); loader.setController(controller);
			 * 
			 * Parent root = loader.load(); stage.setScene(new Scene(root));
			 * stage.getIcons().add(new Image("/icon.png"));
			 * stage.setTitle("Инициализация"); stage.initOwner(stage_);
			 * stage.setResizable(false); stage.setOnCloseRequest(new
			 * EventHandler<WindowEvent>() {
			 * 
			 * @Override public void handle(WindowEvent paramT) { if
			 * (controller.getStatus()) { try { SQLIETEDisconnect(); SQLIETEConnect();
			 * PreparedStatement update = SQLIETE
			 * .prepareStatement("update props set value = 'Y' where name = 'init'");
			 * update.executeUpdate(); SQLIETEDisconnect(); SQLIETEConnect();
			 * PreparedStatement update2 = SQLIETE
			 * .prepareStatement("update props set value = ? where name = 'dbname'");
			 * update2.setString(1, controller.getUUID()); update2.executeUpdate(); } catch
			 * (SQLException e) { e.printStackTrace(); String fullClassName =
			 * Thread.currentThread().getStackTrace()[2].getClassName(); String className =
			 * fullClassName.substring(fullClassName.lastIndexOf(".") + 1); String
			 * methodName = Thread.currentThread().getStackTrace()[2].getMethodName(); int
			 * lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			 * Msg.Message(ExceptionUtils.getStackTrace(e)); Main.logger.error("error=" +
			 * ExceptionUtils.getStackTrace(e) + ";LineNumber = " + lineNumber +
			 * ";className=" + className + ";methodName=" + methodName); } } } });
			 * stage.show(); } else if (rs.getString(1).equals("Y")) { //Enter(); } } //
			 * закрыть сессию с sqlite SQLIETEDisconnect(); }
			 */

			// Enter();

//
//	Fast enter
//			
//			Connect.connectionURL = "194.87.237.206:1521/XE";
			Connect.connectionURL = "localhost:1521/XE";
			Connect.userID = "xxi";
			Connect.userPassword = "123";
			DBUtil.dbConnect();
			initRootLayout();
			RT();
//
//	END 		
//			
			primaryStage.setOnCloseRequest(e -> {
				try {
					killProcess();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
					String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
					String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
					int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					Msg.Message(ExceptionUtils.getStackTrace(e1));
					Main.logger.error("error=" + ExceptionUtils.getStackTrace(e1) + ";LineNumber = " + lineNumber
							+ ";className=" + className + ";methodName=" + methodName);
				}
				DBUtil.dbDisconnect();
				SQLIETEDisconnect();
				Platform.exit();
				System.exit(0);
			});
			/* Максимально растянуть окно */
			// primaryStage.setMaximized(true);
		} catch (Exception e) {
			e.printStackTrace();
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error("error=" + ExceptionUtils.getStackTrace(e) + ";LineNumber = " + lineNumber + ";className="
					+ className + ";methodName=" + methodName);
		}
	}

	/**
	 * Инициализация главного меню
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
			logger = Logger.getLogger(Main.class);
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Первая форма
	 */
	public static void RT() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/First.fxml"));
			AnchorPane employeeOperationsView = (AnchorPane) loader.load();
			rootLayout.setCenter(employeeOperationsView);
		} catch (Exception e) {
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Первый вход
	 */
	void Setup() {
		// проверка инициализации базы в 1с
		try {

		} catch (Exception e) {
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Форма входа
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/access/action/ODB_ACTION.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/access/grp/GrpMember.fxml"));

				GrpController controller = new GrpController();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setResizable(false);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Группы доступа по функционалу");
				stage.initOwner(primaryStage);

				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						GrpAccessWin = true;
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/init/Settings.fxml"));

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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/log/LogList.fxml"));

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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/report/Report.fxml"));

				Report controller = new Report();
				controller.setId(1);
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Отделение
	 */
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/zags/ZagsList.fxml"));

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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/access/menu/ODB_MNU.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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

				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/audit/view/Audit.fxml"));

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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/updname/UpdNameList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/audit/trigger/AuList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/adoptoin/AdoptList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/updabhname/UpdAbhNameList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/birthact/BirthList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/users/Usr.fxml"));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Пользователи");
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/mercer/MercerList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/divorce/DivorceList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/patern/PaternList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("/mj/doc/death/DeathList.fxml"));
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
			e.printStackTrace();
			logger = Logger.getLogger(Main.class);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			column.setPrefWidth(max + 10.0d);
		});
	}
}
