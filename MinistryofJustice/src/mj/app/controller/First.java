package mj.app.controller;

import java.awt.SplashScreen;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;

public class First {

	public First() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private ImageView imageView;

	@FXML
	private void initialize() {
		try {
			//проверка не используется
			if (Main.enterdtage != null)
				Main.enterdtage.close();
			//splash - не используется
			final SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash != null) {
				splash.close();
			}
			//логин и ФИО
			String usrlogin = "";
			PreparedStatement prp = DBUtil.conn
					.prepareStatement("select CUSRNAME||' - '||CUSRLOGNAME login from usr t where upper(cusrlogname) = ?");
			prp.setString(1, Connect.userID.toUpperCase());
			ResultSet rs = prp.executeQuery();
			if (rs.next()) {
				usrlogin = rs.getString("login");
			}
			rs.close();
			prp.close();
			Main.primaryStage.setTitle("(" + usrlogin + ") " + Connect.connectionURL);
			//картинка в центр
			Image image = new Image(getClass().getResourceAsStream("/logo.png"));
			imageView.setImage(image);
			// Setting the image view parameters
			imageView.setX(500);
			imageView.setY(50);
			imageView.setFitWidth(500);
			imageView.setFitHeight(500);
			imageView.setPreserveRatio(true);
			imageView.setPickOnBounds(true);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
