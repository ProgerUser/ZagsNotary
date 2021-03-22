package mj.app.controller;

import org.apache.log4j.Logger;
import javafx.fxml.FXML;
import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class TempController {

	public TempController() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private void initialize() {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
