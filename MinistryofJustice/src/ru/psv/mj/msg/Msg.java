package ru.psv.mj.msg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;

public class Msg {

	public Msg() {
		Main.logger = Logger.getLogger(getClass());
	}

	public static void MessageBox(String Mess, Stage stage) {
		TextArea alert = new TextArea();
		alert.setPrefSize(600, 400);
		AnchorPane yn = new AnchorPane();
		Scene ynScene = new Scene(yn, 600, 400);
		yn.getChildren().add(alert);
		Stage newWindow_yn = new Stage();
		newWindow_yn.setTitle("Внимание");
		newWindow_yn.setScene(ynScene);
		// Specifies the modality for new window.
		newWindow_yn.initModality(Modality.WINDOW_MODAL);
		// Specifies the owner Window (parent) for new window
		newWindow_yn.initOwner(stage);
		newWindow_yn.getIcons().add(new Image("/icon.png"));
		newWindow_yn.show();
		alert.setText(Mess);
	}

	public static void ErrorView(Stage stage_, String pknm, Connection conn) {
		try {
			Main.logger = Logger.getLogger(Msg.class);
			String readRecordSQL = "select * from LOG_ERROR where PACKNAME = ?";
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setString(1, pknm);
			ResultSet rsEmps = prepStmt.executeQuery();
			String error = "";
			while (rsEmps.next()) {
				error = error + "\r\n" + rsEmps.getString("ERID") + ";" + rsEmps.getString("DESC_ERROR_") + "\r\n";
			}
			rsEmps.close();
			prepStmt.close();
			PreparedStatement delete = conn.prepareStatement("begin MJCUS.DelLog(?); end;");
			delete.setString(1, pknm);
			delete.executeUpdate();
			delete.close();
			Msg.MessageBox(error, stage_);
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	public static void Message1(String mess) {
		try {
			if (mess != null && !mess.equals("")) {

//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//				stage.getIcons().add(new Image("/icon.png"));
//				alert.setTitle("Внимание");
//				alert.setHeaderText(null);
//				alert.setContentText(mess);
//				alert.showAndWait();

//				DetailsDialog<ActionType> d = DialogFactory.createDetails(null, "Сообщение", "", "Внимание", null);
//				TextArea n = new TextArea(mess);
//				// n.setWrapText(true);
//				d.setDetailsContent(n);
//				d.showAndWait();

				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Msg.class.getResource("/ru/psv/mj/msg/Alert.fxml"));
				AlertController controller = new AlertController();
				controller.setText(mess);
				loader.setController(controller);
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Внимание");
				stage.setResizable(true);
				// stage.initOwner(stage_);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {

					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public static Alert setDefaultButton(Alert alert, ButtonType defBtn) {
		DialogPane pane = alert.getDialogPane();
		for (ButtonType t : alert.getButtonTypes())
			((Button) pane.lookupButton(t)).setDefaultButton(t == defBtn);
		return alert;
	}
	
	public static void Message(String mess) {
		if (mess != null && !mess.equals("")) {

			AlertType AlertTp = null;
			String error = null;
			if (mess.length() >= 200) {
				AlertTp = AlertType.ERROR;
				error = mess.substring(0, 150);
			} else {
				AlertTp = AlertType.INFORMATION;
				error = mess;
			}
			Alert alert = new Alert(AlertTp);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/icon.png"));
			alert.setTitle("Внимание");
			alert.setHeaderText(error);
			// alert.setContentText(mess.substring(0, mess.indexOf("\r\n")));
			Label label = new Label("Трассировка стека исключения:");

			TextArea textArea = new TextArea(mess);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.getStyleClass().add("mylistview");
			textArea.getStylesheets().add("/ScrPane.css");

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);

			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);

			alert.showAndWait();
		}
	}
}
