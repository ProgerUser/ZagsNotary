package notary.doc.old.controller;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class HtmTempEditor {

	public HtmTempEditor() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private HTMLEditor VisHtml;

	@FXML
	private TextArea CodeHtml;

	@FXML
	void AddField(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void ViewHtmlTag(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
