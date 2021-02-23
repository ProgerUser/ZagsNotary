package mj.app.enter;

import java.awt.SplashScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.users.Set_Up_Pass;

public class Enter {

	public Enter() {
		Main.logger = Logger.getLogger(getClass());
	}

    @FXML
    private TextField DBIP;
    
	@FXML
	private Button enter_id;

	@FXML
	private AnchorPane ap;

	@FXML
	private ComboBox<String> login;

	@FXML
	private PasswordField pass;

	//public static String DB;

	final static String driverClass = "oracle.jdbc.OracleDriver";

	Connection conn = null;

	PreparedStatement sqlStatement = null;

	void onclose() {
		Stage stage = (Stage) login.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	void Set_Up_Pass() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Stage stage = new Stage();
			Stage stage_ = (Stage) login.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/users/Set_Up_Pass.fxml"));

			Set_Up_Pass controller = new Set_Up_Pass();
			controller.setFnk("{ ? = call MJUsers.Set_Up_Password2(?,?,?,?)}");
			controller.setUsr(Connect.userID.toUpperCase());
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Изменить пароль");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						Main.initRootLayout();
						Main.RT();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Разные проверки перед входом
	 */
	void Check_Enter() {
		// Выполнить проверку соединения с базой
		try {
			Main.logger = Logger.getLogger(getClass());
			// __________________Проверки____________

			DBUtil.dbConnect();
			conn = DBUtil.conn;
			String sql = "SELECT count(*) cnt FROM usr where usr.DUSRFIRE is null and CUSRLOGNAME = ?";
			sqlStatement = conn.prepareStatement(sql);
			sqlStatement.setString(1, Connect.userID);
			ResultSet myResultSet = sqlStatement.executeQuery();
			if (!myResultSet.next()) {
				Msg.Message("Ошибка ввода логина или пароля");
				Main.logger.error("Ошибка ввода логина или пароля" + "~" + Thread.currentThread().getName());
			} else {
				if (myResultSet.getInt("cnt") > 0) {
					PreparedStatement stsmt = conn.prepareStatement("select null from usr "
							+ "where upper(usr.CUSRLOGNAME) = ? " + "and MUST_CHANGE_PASSWORD = 'Y'");
					stsmt.setString(1, Connect.userID.toUpperCase());
					ResultSet rs = stsmt.executeQuery();
					if (rs.next()) {
						Msg.Message("Необходимо изменить пароль!");
						// изменить пароль, если установлена настройка
						Set_Up_Pass();
					} else {
						Main.initRootLayout();
						Main.RT();
						InsertIfNotExists(Connect.userID.toUpperCase());
					}
					stsmt.close();
					rs.close();
				} else {
					Msg.Message("Пользователь заблокирован!");
				}

			}
			myResultSet.close();
			sqlStatement.close();
		} catch (SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		} finally {
			if (sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					Msg.Message(ExceptionUtils.getStackTrace(e));
					Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
					String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
					String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
					int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
				}
			}
			if (conn != null) {
				// DBUtil.dbDisconnect();
			}
		}
	}

	@FXML
	void enter(ActionEvent event) {
		try {
			if (login.getValue() != null & !pass.getText().equals("")) {
				Main.logger = Logger.getLogger(getClass());
				Connect.connectionURL = DBIP.getText();
				Connect.userID = login.getValue().toString();
				Connect.userPassword = pass.getText();
				Check_Enter();
			} else {
				Msg.Message("Введите данные!");
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}

	}

	@FXML
	void enter_(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			try {
				if (login.getValue() != null & !pass.getText().equals("")) {
					Connect.connectionURL = DBIP.getText();
					Connect.userID = login.getValue().toString();
					Connect.userPassword = pass.getText();
					Check_Enter();
				} else {
					Msg.Message("Введите данные!");
				}
			} catch (Exception e) {
				Msg.Message(ExceptionUtils.getStackTrace(e));
				Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
		}
	}

	/**
	 * Connect to the test.db database
	 * 
	 * @return the Connection object
	 */
	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
		}
		return conn;
	}

	/**
	 * Вставить данные в таблицу, если нет такого в списке
	 * 
	 * @param logname
	 */
	void InsertIfNotExists(String logname) {
		try {
			{
				sqllite_conn = connect();
				PreparedStatement stmt = sqllite_conn
						.prepareStatement("select count(*) cnt from users where USR_NAME = ?");
				stmt.setString(1, logname);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					if (rs.getInt("cnt") == 0) {
						PreparedStatement insert = sqllite_conn
								.prepareStatement("insert into users (USR_NAME) values (?)");
						insert.setString(1, logname);
						insert.executeUpdate();
						insert.close();
					}
				}
				stmt.close();
				rs.close();
				sqllite_conn.close();
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	public static Connection sqllite_conn = null;

	@FXML
	private void initialize() {
		try {
			sqllite_conn = connect();
			{
				PreparedStatement prp = sqllite_conn.prepareStatement("select value from props where name = 'url'");
				ResultSet rs = prp.executeQuery();
				if(rs.next()) {
					DBIP.setText(rs.getString("value"));
				}
				prp.close();
			}
			
			ObservableList<String> logins = FXCollections.observableArrayList();
			{
				PreparedStatement stmt = sqllite_conn.prepareStatement("select * from users");
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					logins.add(rs.getString("USR_NAME"));
				}
				stmt.close();
				rs.close();
			}
			
//			ObservableList<String> url = FXCollections.observableArrayList();
//			{
//				PreparedStatement stmt = sqllite_conn.prepareStatement("select * from props where name = 'url'");
//				ResultSet rs = stmt.executeQuery();
//				while (rs.next()) {
//					url.add(rs.getString("VALUE"));
//					DB = rs.getString("VALUE");
//				}
//				stmt.close();
//				rs.close();
//			}

			FilteredList<String> filteredlogin = new FilteredList<String>(logins);
			login.getEditor().textProperty().addListener(new InputFilter<String>(login, filteredlogin, true));
			login.setItems(logins);

			final SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash != null) {
				splash.close();
			}
			sqllite_conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}
}