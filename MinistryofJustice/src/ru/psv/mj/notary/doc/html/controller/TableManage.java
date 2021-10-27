package ru.psv.mj.notary.doc.html.controller;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import netscape.javascript.JSObject;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.notary.doc.html.model.TableModel;
import ru.psv.mj.utils.DbUtil;

public class TableManage {

	public TableManage() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private WebView webView;

	void onclose() {
		Stage stage = (Stage) webView.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private TableModel tbl = null;

	public TableModel getTbl() {
		return tbl;
	}

	@FXML
	void CENCEL(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private String HTML = null;

	@FXML
	void OK(ActionEvent event) {
		try {
			String mes = (String) webView.getEngine().executeScript(
					"function getTextArea() {\n" + 
							"var myContent = tinymce.get(\"txtarea\").getContent();\n"+ 
							"return myContent;\n" + 
							"}\n" + 
					"getTextArea();");
			HTML = mes;
			//System.out.println(mes);
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public String getHTML() {
		return HTML;
	}

	public class JsToJava {
		public void run(String id) {

		}

		public void Text(String Mes) {
			Msg.Message(Mes);
		}
	}

	@FXML
	private void initialize() {
		try {
			final WebEngine webEngine = webView.getEngine();
			final JsToJava jstojava = new JsToJava();
			URL url = new File(System.getenv("MJ_PATH") + "HTML/TBLED/generic-tables.html").toURI().toURL();
			webEngine.load(url.toExternalForm());
			webView.setContextMenuEnabled(false);
			webEngine.setJavaScriptEnabled(true);
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> observableValue, State oldState, State newState) {
					if (newState == State.SUCCEEDED) {
						JSObject window = (JSObject) webEngine.executeScript("window");
						window.setMember("invoke", jstojava);
					}
				}
			});

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
