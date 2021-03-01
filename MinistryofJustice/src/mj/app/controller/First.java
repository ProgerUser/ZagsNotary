package mj.app.controller;

import java.awt.SplashScreen;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class First {

	public First() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private ImageView imageView;

	@FXML
	private void initialize() {
		try {

			if (Main.enterdtage != null)
				Main.enterdtage.close();

			final SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash != null) {
				splash.close();
			}

			Main.primaryStage.setTitle(Connect.userID + "/" + Connect.connectionURL);

			Image image = new Image(ClassLoader.class.getResourceAsStream("/logo.png"));
			imageView.setImage(image);
			// Setting the image view parameters
			imageView.setX(500);
			imageView.setY(50);
			imageView.setFitWidth(500);
			imageView.setFitHeight(500);
			imageView.setPreserveRatio(true);
			imageView.setPickOnBounds(true);
		
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
