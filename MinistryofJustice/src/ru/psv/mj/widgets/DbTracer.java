package ru.psv.mj.widgets;

import java.io.File;
import java.util.Scanner;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.app.model.Connect;
import ru.psv.mj.utils.DbUtil;

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
	private CheckBox Turn;

	@FXML
	void Refresh(ActionEvent event) {
		try {
			Trace.setText("");
			Scanner s = new Scanner(new File(System.getenv("MJ_PATH") + "Logs/text.log")).useDelimiter("\r\n");
			while (s.hasNext()) {
				if (s.hasNextInt()) { // check if next token is an int
					Trace.appendText(s.nextInt() + "\r\n"); // display the found integer
				} else {
					Trace.appendText(s.next() + "\r\n"); // else read the next token
				}
			}
			s.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Turn(ActionEvent event) {
		try {
			if (Turn.isSelected()) {
				Connect.dbmsOutput = true;
			} else {
				Connect.dbmsOutput = false;
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			Tab1.setText(Connect.userID + "/" + Connect.connectionURL);
			
			if (Connect.dbmsOutput) {
				Turn.setSelected(true);
			} else {
				Turn.setSelected(false);
			}
			
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
