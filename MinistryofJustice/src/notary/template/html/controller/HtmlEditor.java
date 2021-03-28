package notary.template.html.controller;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import notary.template.html.model.NT_TEMP_LIST;

public class HtmlEditor {

	public HtmlEditor() {
		Main.logger = Logger.getLogger(getClass());
	}

	NT_TEMP_LIST val_list;

	public void setConn(Connection conn, NT_TEMP_LIST val_list) {
		try {
			this.val_list = val_list;
			this.conn = conn;
			this.conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;
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
	void CENCEL(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) VisHtml.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			PreparedStatement prp = conn.prepareStatement("update NT_TEMP_LIST set HTML_TEMP = ? where ID = ?");
			Clob clob = conn.createClob();
			clob.setString(1, VisHtml.getHtmlText());
			prp.setClob(1, clob);
			prp.setInt(2, val_list.getID());
			prp.executeUpdate();
			prp.close();
			clob.free();
			conn.commit();
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
//			InputStream is = getClass().getResourceAsStream("/notary/doc/old/controller/Test.html");
//			String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());

			VisHtml.setHtmlText(val_list.getHTML_TEMP());
			CodeHtml.setText(val_list.getHTML_TEMP());
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
