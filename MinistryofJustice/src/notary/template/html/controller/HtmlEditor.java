package notary.template.html.controller;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class HtmlEditor {

	public HtmlEditor() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private HTMLEditor VisHtml;

	@FXML
	private TextArea CodeHtml;

	@FXML
	void HtmlToView(ActionEvent event) {
		try {
			VisHtml.setHtmlText(CodeHtml.getText());
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void ViewHtmlTag(ActionEvent event) {
		try {
			CodeHtml.setText(VisHtml.getHtmlText());
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			InputStream is = getClass().getResourceAsStream("/notary/doc/old/controller/Test.html");
			String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());
			VisHtml.setHtmlText(text);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
