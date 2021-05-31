package mj.app.controller;

import org.apache.log4j.Logger;
import javafx.fxml.FXML;
import mj.app.main.Main;
import mj.utils.DbUtil;

public class TempController {

	public TempController() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private void initialize() {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
