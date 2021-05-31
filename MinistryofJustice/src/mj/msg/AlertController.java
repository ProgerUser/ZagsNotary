package mj.msg;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.utils.DbUtil;

public class AlertController {

	private String TA = null;

	public void setText(String mes) {
		this.TA = mes;
	}

	public AlertController() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TextArea MES;

	@FXML
	void OK(ActionEvent event) {
		OnClose();
	}

	void OnClose() {
		Stage stage = (Stage) MES.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {

			MES.setText(TA);

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
