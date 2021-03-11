package mj.widgets;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;

public class DbTracer {

	public DbTracer() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TabPane BorderPane;

	@FXML
	private Tab Tab1;

	@FXML
	private TextArea Trace;

	@FXML
	private void initialize() {
		try {
			Tab1.setText(Connect.userID + Connect.connectionURL);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
