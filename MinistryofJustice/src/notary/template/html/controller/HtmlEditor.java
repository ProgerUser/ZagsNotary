package notary.template.html.controller;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import notary.template.html.model.NT_TEMP_LIST;

public class HtmlEditor {

	public HtmlEditor() {
		Main.logger = Logger.getLogger(getClass());
	}

	private static final Pattern XML_TAG = Pattern
			.compile("(?<ELEMENT>(</?\\h*)(\\w+)([^<>]*)(\\h*/?>))" + "|(?<COMMENT><!--[^<>]+-->)");

	private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")");

	private static final int GROUP_OPEN_BRACKET = 2;
	private static final int GROUP_ELEMENT_NAME = 3;
	private static final int GROUP_ATTRIBUTES_SECTION = 4;
	private static final int GROUP_CLOSE_BRACKET = 5;
	private static final int GROUP_ATTRIBUTE_NAME = 1;
	private static final int GROUP_EQUAL_SYMBOL = 2;
	private static final int GROUP_ATTRIBUTE_VALUE = 3;

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

	// @FXML
	// private CodeArea CodeHtml;

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
			WebView webView = (WebView) VisHtml.lookup("WebView");
			String html = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
			CodeHtml.replaceText(0, 0, html);
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
			clob.setString(1,
					CodeHtml.getText().replace("<html dir=\"ltr\"><head>", "<!DOCTYPE html>\r\n<html>\r\n<head>"));
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

	private static StyleSpans<Collection<String>> computeHighlighting(String text) {

		Matcher matcher = XML_TAG.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {

			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			if (matcher.group("COMMENT") != null) {
				spansBuilder.add(Collections.singleton("comment"), matcher.end() - matcher.start());
			} else {
				if (matcher.group("ELEMENT") != null) {
					String attributesText = matcher.group(GROUP_ATTRIBUTES_SECTION);

					spansBuilder.add(Collections.singleton("tagmark"),
							matcher.end(GROUP_OPEN_BRACKET) - matcher.start(GROUP_OPEN_BRACKET));
					spansBuilder.add(Collections.singleton("anytag"),
							matcher.end(GROUP_ELEMENT_NAME) - matcher.end(GROUP_OPEN_BRACKET));

					if (!attributesText.isEmpty()) {

						lastKwEnd = 0;

						Matcher amatcher = ATTRIBUTES.matcher(attributesText);
						while (amatcher.find()) {
							spansBuilder.add(Collections.emptyList(), amatcher.start() - lastKwEnd);
							spansBuilder.add(Collections.singleton("attribute"),
									amatcher.end(GROUP_ATTRIBUTE_NAME) - amatcher.start(GROUP_ATTRIBUTE_NAME));
							spansBuilder.add(Collections.singleton("tagmark"),
									amatcher.end(GROUP_EQUAL_SYMBOL) - amatcher.end(GROUP_ATTRIBUTE_NAME));
							spansBuilder.add(Collections.singleton("avalue"),
									amatcher.end(GROUP_ATTRIBUTE_VALUE) - amatcher.end(GROUP_EQUAL_SYMBOL));
							lastKwEnd = amatcher.end();
						}
						if (attributesText.length() > lastKwEnd)
							spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
					}

					lastKwEnd = matcher.end(GROUP_ATTRIBUTES_SECTION);

					spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_CLOSE_BRACKET) - lastKwEnd);
				}
			}
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

	@FXML
	private SplitPane Split;

	private CodeArea CodeHtml;

	@FXML
	private void initialize() {
		try {
			CodeHtml = new CodeArea();
//			InputStream is = getClass().getResourceAsStream("/notary/doc/old/controller/Test.html");
//			String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());

			Split.getItems().add(new StackPane(new VirtualizedScrollPane<>(CodeHtml)));

			CodeHtml.setParagraphGraphicFactory(LineNumberFactory.get(CodeHtml));
			CodeHtml.textProperty().addListener((obs, oldText, newText) -> {
				CodeHtml.setStyleSpans(0, computeHighlighting(newText));
			});

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					WebView webView = (WebView) VisHtml.lookup("WebView");
					webView.getEngine().loadContent(val_list.getHTML_TEMP());
				}
			});

			if (val_list.getHTML_TEMP() != null) {
				CodeHtml.replaceText(0, 0, val_list.getHTML_TEMP());
			}
			
			Split.getStyleClass().add("mylistview");
			Split.getStylesheets().add("/ScrPane.css");
			
			CodeHtml.getStylesheets().add(
					getClass().getResource("/notary/template/html/controller/xml-highlighting.css").toExternalForm());

			// String IMAGE_URL = "http://...";

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

}
