package mj.doc.cus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.doc.birthact.AddBirthAct;
import mj.doc.birthact.FindBirth;
import mj.msg.Msg;

public class UtilCus {

	public UtilCus() {
		Main.logger = Logger.getLogger(getClass());
	}

	/**
	 * Создать гражданина
	 * 
	 * @param event
	 */
	public void Add_Cus(TextField fio, TextField id, Stage stage_, Connection conn) {
		try {
			Main.logger = Logger.getLogger(getClass());

			// проверка доступа
			if (DBUtil.OdbAction(27) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/cus/IUCus.fxml"));

			AddCus controller = new AddCus();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Создание записи");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						try {
							PreparedStatement sttmt = conn
									.prepareStatement("select CCUSNAME from cus where ICUSNUM = ?");
							sttmt.setInt(1, controller.getId());
							ResultSet rs = sttmt.executeQuery();
							if (rs.next()) {
								fio.setText(rs.getString("CCUSNAME"));
								id.setText(String.valueOf(controller.getId()));
							}
							sttmt.close();
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
							Msg.Message(ExceptionUtils.getStackTrace(e));
							Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
							String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
							String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
							int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
							DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
						}
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
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

	/**
	 * Найти гражданина
	 * 
	 * @param fio
	 * @param id
	 */
	public void Find_Cus(TextField fio, TextField id, Stage stage_) {
		try {
			Main.logger = Logger.getLogger(getClass());

			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/cus/FIND_CUS.fxml"));

			FIND_CUS controller = new FIND_CUS();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Список граждан");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						fio.setText(controller.getFio());
						id.setText(String.valueOf(controller.getId()));
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
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

	/**
	 * Найти акт о рождении
	 */
	public void Find_Brn(TextField id, Stage stage_) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/birthact/FindBirth.fxml"));

			FindBirth controller = new FindBirth();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Список Свидетельств о рождении");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						id.setText(String.valueOf(controller.getId()));
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Создать свид. о рождении
	 * 
	 * @param event
	 */
	public void Add_Brn(TextField id, Stage stage_, Connection conn) {
		try {
			Main.logger = Logger.getLogger(getClass());

			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/birthact/IUBirthAct.fxml"));

			AddBirthAct controller = new AddBirthAct();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Создание записи");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						id.setText(String.valueOf(controller.getId()));
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
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
