package notary.template.html.controller;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;
import notary.template.html.model.NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

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

    @FXML
    private VBox root;

    @FXML
    private ToolBar toptbr;

    
    
    @FXML
    void PlusDocParamCliRef(ActionEvent event) {

    }
    

    @FXML
    void EditLocalParam(ActionEvent event) {

    }
    
    @FXML
    void DeleteLocalParam(ActionEvent event) {

    }
    
    @FXML
    void AddParamLocal(ActionEvent event) {

    }
    
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
			CodeHtml.replaceText(0, CodeHtml.getLength(), html);
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
	void Beautifier(ActionEvent event) {
		try {

//			String html = CodeHtml.getText();
//			org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);
//
//			String res = doc.body().html();
//
//			CodeHtml.replaceText(0, CodeHtml.getLength(), res);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			PreparedStatement prp = conn.prepareStatement("update NT_TEMP_LIST set HTML_TEMP = ? where ID = ?");
			Clob clob = conn.createClob();
			clob.setString(1,
					CodeHtml.getText().replace("<html dir=\"ltr\"><head>", "<!DOCTYPE html>\r\n<html>\r\n<head>"));
			prp.setClob(1, clob);
			prp.setLong(2, val_list.getID());
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

	@SuppressWarnings("rawtypes")
	TreeItem roots = new TreeItem<>("Root");
	
	public NT_TEMP_LIST_PARAM prm;
	
	@SuppressWarnings("unchecked")
	void fillTree() {
		try {
			if (val_list != null) {
				WebView webView = (WebView) VisHtml.lookup("WebView");
				// Получить поля из страницы
				String json = (String) webView.getEngine().executeScript("writeJSONfile()");
				Map<String, String> result = new ObjectMapper().readValue(json, HashMap.class);
				String JsonStr = "";
				for (Map.Entry<String, String> entry : result.entrySet()) {
					JsonStr = JsonStr + entry.getKey() + "|~|~|" + entry.getValue() + "\r\n";
				}

				// System.out.println(JsonStr);

				roots = new TreeItem<>("Root");
				Map<Long, TreeItem<NT_TEMP_LIST_PARAM>> itemById = new HashMap<>();
				Map<Long, Long> parents = new HashMap<>();

				PreparedStatement prp = conn.prepareStatement(
						DBUtil.SqlFromProp("/notary/doc/html/controller/Sql.properties", "AddParamForDoc"));
				Clob clob = conn.createClob();
				clob.setString(1, JsonStr.trim());
				prp.setLong(1, val_list.getID());
				prp.setClob(2, clob);
				prp.setLong(3, val_list.getID());
				prp.setLong(4, val_list.getID());
				prp.setClob(5, clob);
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					prm = new NT_TEMP_LIST_PARAM();
					prm.setPRM_ID(rs.getLong("PRM_ID"));
					prm.setPRM_NAME(rs.getString("PRM_NAME"));
					prm.setPRM_R_NAME(rs.getString("PRM_R_NAME"));
					prm.setPRM_TMP_ID(rs.getLong("PRM_TMP_ID"));
					prm.setPRM_SQL(rs.getString("PRM_SQL"));
					prm.setPRM_TYPE(rs.getLong("PRM_TYPE"));
					prm.setPRM_PADEJ(rs.getLong("PRM_PADEJ"));
					prm.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
					if (rs.getClob("PRM_FOR_PRM_SQL") != null) {
						prm.setPRM_FOR_PRM_SQL(new ConvConst().ClobToString(rs.getClob("PRM_FOR_PRM_SQL")));
					}
					prm.setTYPE_NAME(rs.getString("TYPE_NAME"));
					prm.setREQUIRED(rs.getString("REQUIRED"));
					prm.setPARENTS(rs.getLong("PARENTS"));
					prm.setHTML_CODE(rs.getString("HTML_CODE"));
					prm.setIS_LOC(rs.getString("IS_LOC"));
					itemById.put(rs.getLong("PRM_ID"), new TreeItem<>(prm));
					parents.put(rs.getLong("PRM_ID"), rs.getLong("PARENTS"));
				}
				prp.close();
				rs.close();

				for (Map.Entry<Long, TreeItem<NT_TEMP_LIST_PARAM>> entry : itemById.entrySet()) {
					Long key = entry.getKey();
					Long parent = parents.get(key);
					if (parent.equals(key)) {
						roots = entry.getValue();
					} else {
						TreeItem<NT_TEMP_LIST_PARAM> parentItem = itemById.get(parent);
						if (parentItem == null) {
							roots.getChildren().add(entry.getValue());
						} else {
							parentItem.getChildren().add(entry.getValue());
						}
					}
				}
				param.setRoot(roots);
				param.setShowRoot(false);
			}
			expandTreeView(roots);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	private void expandTreeView(TreeItem<NT_TEMP_LIST_PARAM> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(true);
			for (TreeItem<NT_TEMP_LIST_PARAM> child : item.getChildren()) {
				expandTreeView(child);
			}
		}
	}
	
	@FXML
	private TreeTableView<NT_TEMP_LIST_PARAM> param;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, Long> id;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> name;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> req;
	
//	@FXML
//	private SplitPane Split;

	private CodeArea CodeHtml;
	@FXML
	private Tab HtmlTag;

	void init() {
		try {
			WebView webView = (WebView) VisHtml.lookup("WebView");
			webView.getEngine().loadContent(val_list.getHTML_TEMP());
			webView.setPrefHeight(5000);

			final WebEngine webEngine = webView.getEngine();
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
				@Override
				public void changed(ObservableValue<? extends State> observableValue, State oldState, State newState) {
					if (newState == State.SUCCEEDED) {
						// Оформление
						{
							Node node = VisHtml.lookup(".top-toolbar");
							Node NodeNottom = VisHtml.lookup(".bottom-toolbar");
							if (node instanceof ToolBar) {
								boolean check = true;
								ToolBar bar = (ToolBar) node;
								ToolBar BarBottom = (ToolBar) NodeNottom;
								ObservableList<Node> list = BarBottom.getItems();
								for (Node item : list) {
									if (item.getId() != null && (item.getId().equals("TableAdd"))) {
										check = false;
										break;
									}
								}

								///////
								hideImageNodesMatching(node, Pattern.compile(".*(Color).*"), 0);
								///////
								if (check) {
									// table
									{
										FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TABLE);
										icon.setFontSmoothingType(FontSmoothingType.LCD);
										icon.setSize("18");
										Separator sep = new Separator();
										Button myButton = new Button("", icon);
										myButton.setId("TableAdd");
										myButton.setTooltip(new Tooltip("Таблица"));

										BarBottom.getItems().add(sep);
										BarBottom.getItems().add(myButton);

										myButton.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent arg0) {
												// tblManage();
											}
										});
									}
								}
							}
							// modify font selections.
							int i = 0;
							for (Node candidate : (VisHtml.lookupAll("ComboBox"))) {
								// fonts are selected by the second menu in the htmlEditor.
								if (candidate instanceof ComboBox && i == 1) {
									// System.out.println("`````");
									// limit the font selections to our predefined list.
									ComboBox menuButton = (ComboBox) candidate;
									menuButton.setMinWidth(200);
									// System.out.println(menuButton.getSelectionModel().getSelectedItem());
									List<String> removalList = FXCollections.observableArrayList();
									final List<String> fontSelections = menuButton.getItems();
									for (String item : fontSelections) {
										if (!limitedFonts.contains(item)) {
											removalList.add(item);
										}
									}
									fontSelections.removeAll(removalList);
								}
								i++;
							}
						}
						//--------------
						Platform.runLater(() -> {
							fillTree();
						});
						//--------------
					}
				}
			});

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void initialize() {
		try {
			root.getChildren().remove(toptbr);
			CodeHtml = new CodeArea();
//			InputStream is = getClass().getResourceAsStream("/notary/doc/old/controller/Test.html");
//			String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());

			// Split.getItems().add(new StackPane(new VirtualizedScrollPane<>(CodeHtml)));

			HtmlTag.setContent(new StackPane(new VirtualizedScrollPane<>(CodeHtml)));

			CodeHtml.setParagraphGraphicFactory(LineNumberFactory.get(CodeHtml));

			CodeHtml.textProperty().addListener((obs, oldText, newText) -> {
				CodeHtml.setStyleSpans(0, computeHighlighting(newText));

				{
					WebView webView = (WebView) VisHtml.lookup("WebView");
					String html = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
					if (!html.equals(CodeHtml.getText())) {
						VisHtml.setHtmlText(CodeHtml.getText());
					}
				}
//				System.out.println("`````");

			});

//			Platform.runLater(new Runnable() {
//				@Override
//				public void run() {
//				}
//			});

			if (val_list.getHTML_TEMP() != null) {
				CodeHtml.replaceText(0, 0, val_list.getHTML_TEMP());
			}

//			Split.getStyleClass().add("mylistview");
//			Split.getStylesheets().add("/ScrPane.css");

			CodeHtml.getStylesheets().add(
					getClass().getResource("/notary/template/html/controller/xml-highlighting.css").toExternalForm());

			// String IMAGE_URL = "http://...";

			VisHtml.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (isValidEvent(event)) {
//						System.out.println("`````");
						{
							WebView webView = (WebView) VisHtml.lookup("WebView");
							String html = (String) webView.getEngine()
									.executeScript("document.documentElement.outerHTML");
							if (!CodeHtml.getText().equals(html)) {
								CodeHtml.replaceText(0, CodeHtml.getLength(), html);
							}
						}
					}
				}

				private boolean isValidEvent(KeyEvent event) {
					return !isSelectAllEvent(event) && ((isPasteEvent(event)) || isCharacterKeyReleased(event));
				}

				private boolean isSelectAllEvent(KeyEvent event) {
					return event.isShortcutDown() && event.getCode() == KeyCode.A;
				}

				private boolean isPasteEvent(KeyEvent event) {
					return event.isShortcutDown() && event.getCode() == KeyCode.V;
				}

				private boolean isCharacterKeyReleased(KeyEvent event) {
					// Make custom changes here..
					switch (event.getCode()) {
					case ALT:
					case COMMAND:
					case CONTROL:
					case SHIFT:
						return false;
					default:
						return true;
					}
				}
			});

			init();
			
			id.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_ID());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			req.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getREQUIRED());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			name.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_R_NAME());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	// limits the fonts a user can select from in the html editor.
	private static final List<String> limitedFonts = FXCollections.observableArrayList("Times New Roman");

	@SuppressWarnings("deprecation")
	public void hideImageNodesMatching(Node node, Pattern imageNamePattern, int depth) {
		if (node instanceof ImageView) {
			ImageView imageView = (ImageView) node;
			String url = imageView.getImage().impl_getUrl();
			if (url != null && url.contains("Color")) {
				Node button = imageView.getParent().getParent().getParent();
				button.setVisible(false);
				button.setManaged(false);
				// System.out.println(url);
			}
		}
		if (node instanceof Parent) {
			for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
				hideImageNodesMatching(child, imageNamePattern, depth + 1);
			}
		}
	}
}
