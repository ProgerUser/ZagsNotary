package notary.doc.controller;

import java.net.URL;
import org.apache.log4j.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import netscape.javascript.JSObject;

public class HtmlEditor {
	public HtmlEditor() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private VBox root;
	@FXML
	private WebView webView;

	public class Hello {
		public void world(String id) {
			Msg.Message(id);
			webEngine.executeScript("SetValue('"+id+"','sdfsdf')");
		}
	}

	private WebEngine webEngine;

	@FXML
	void reload(ActionEvent event) {
		try {
			/* Load the web page URL (location of the resource) */
			URL url = HtmlEditor.class.getResource("/notary/doc/HTML.html");
			webEngine = webView.getEngine();
			webEngine.load(url.toExternalForm());
			/*
			 * Set the State listener as well as the name of the JavaScript object and its
			 * corresponding Java object (the class in which methods will be invoked) that
			 * will serve as the bridge for the two objects.
			 */
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> observableValue, State oldState, State newState) {
					if (newState == State.SUCCEEDED) {
						JSObject window = (JSObject) webEngine.executeScript("window");
						/* The two objects are named using the setMember() method. */
						window.setMember("invoke", new Hello());
					}
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	public void initialize() {

	}
}
