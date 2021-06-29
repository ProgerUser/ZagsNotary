package notary.doc.html.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;
import mj.widgets.DbmsOutputCapture;
import mj.widgets.FxUtilTest;
import netscape.javascript.JSObject;
import notary.doc.html.model.V_NT_DOC;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

public class AddDoc {

	public AddDoc() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
	}

	@SuppressWarnings("deprecation")
	public void hideImageNodesMatching(Node node, Pattern imageNamePattern, int depth) {
		if (node instanceof ImageView) {
			ImageView imageView = (ImageView) node;
			String url = imageView.getImage().impl_getUrl();
			if (url != null && url.contains("Color")) {
				Node button = imageView.getParent().getParent().getParent();
				button.setVisible(false);
				button.setManaged(false);
				//System.out.println(url);
			}
		}
		if (node instanceof Parent) {
			for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
				hideImageNodesMatching(child, imageNamePattern, depth + 1);
			}
		}
	}

	@FXML
	private Button EditLocalParam;

	@FXML
	private Button DeleteLocalParam;

	@FXML
	private MenuButton LocalParams;
	@FXML
	private TreeTableView<NT_TEMP_LIST_PARAM> param;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, Long> id;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> name;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> req;

	private BooleanProperty status;
	@SuppressWarnings("rawtypes")
	TreeItem roots = new TreeItem<>("Root");

	public NT_TEMP_LIST_PARAM prm;

	@FXML
	void EditLocalParam(ActionEvent event) {

	}

	@FXML
	void DeleteLocalParam(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	void fillTree() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				// Получить поля из страницы
				String json = (String) webView.getEngine().executeScript("writeJSONfile()");
				Map<String, String> result = new ObjectMapper().readValue(json, HashMap.class);
				String JsonStr = "";
				for (Map.Entry<String, String> entry : result.entrySet()) {
					JsonStr = JsonStr + entry.getKey() + "|~|~|" + entry.getValue() + "\r\n";
				}

				//System.out.println(JsonStr);

				roots = new TreeItem<>("Root");
				Map<Long, TreeItem<NT_TEMP_LIST_PARAM>> itemById = new HashMap<>();
				Map<Long, Long> parents = new HashMap<>();

				PreparedStatement prp = conn.prepareStatement(
						DbUtil.Sql_From_Prop("/notary/doc/html/controller/Sql.properties", "AddParamForDoc1"));
				Clob clob = conn.createClob();
				clob.setString(1, JsonStr.trim());
				prp.setLong(1, val.getID());
				prp.setClob(2, clob);
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
			e.printStackTrace();
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

	public void setStatus(Boolean status) {
		this.status.set(status);
	}

	public Boolean getStatus() {
		return status.get();
	}

	@FXML
	private VBox root;

	@FXML
	private ComboBox<V_NT_TEMP_LIST> TYPE_NAME;

//	@FXML
//	private Button PrintBtn;

//	@FXML
//	private ToolBar PrintToolbar;

	@FXML
	void CENCEL(ActionEvent event) {
		onclose();
	}

	void onclose() {
		Stage stage = (Stage) TYPE_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Print(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	NT_TEMP_LIST_PARAM list = null;

	@FXML
	private TabPane Tabs;

	@FXML
	private Tab scans;

	@FXML
	private HTMLEditor HtmlEditor;

	void ListVal(String id) {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				PreparedStatement prp = conn
						.prepareStatement("select * from VNT_TEMP_LIST_PARAM t where PRM_NAME = ? and PRM_TMP_ID = ?");
				prp.setString(1, id);
				prp.setLong(2, val.getID());
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					list = new NT_TEMP_LIST_PARAM();
					list.setPRM_ID(rs.getLong("PRM_ID"));
					list.setPRM_NAME(rs.getString("PRM_NAME"));
					list.setPRM_R_NAME(rs.getString("PRM_R_NAME"));
					list.setPRM_TMP_ID(rs.getLong("PRM_TMP_ID"));
					list.setPRM_SQL(rs.getString("PRM_SQL"));
					list.setPRM_TYPE(rs.getLong("PRM_TYPE"));
					list.setPRM_PADEJ(rs.getLong("PRM_PADEJ"));
					list.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
					if (rs.getClob("PRM_FOR_PRM_SQL") != null) {
						list.setPRM_FOR_PRM_SQL(new ConvConst().ClobToString(rs.getClob("PRM_FOR_PRM_SQL")));
					}
					list.setPDJ_NAME(rs.getString("PDJ_NAME"));
					list.setPRM_PADEJ(rs.getLong("PRM_PADEJ"));
					list.setTYPE_NAME(rs.getString("TYPE_NAME"));
					list.setREQUIRED(rs.getString("REQUIRED"));
				}
				prp.close();
				rs.close();

				if (list != null) {
					// текущие поля на странице
					String json = (String) webView.getEngine().executeScript("writeJSONfile()");

					Stage stage = new Stage();
					Stage stage_ = (Stage) webView.getScene().getWindow();

					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/notary/doc/html/view/ParamList.fxml"));

					ParamList controller = new ParamList();
					controller.setQuery(list.getPRM_SQL());
					controller.setConn(conn);
					loader.setController(controller);

					Parent root = loader.load();
					
					Scene scene = new Scene(root);
//					Style startingStyle = Style.LIGHT;
//					JMetro jMetro = new JMetro(startingStyle);
//					System.setProperty("prism.lcdtext", "false");
//					jMetro.setScene(scene);
					
					stage.setScene(scene);
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Список");
					stage.initOwner(stage_);
					stage.setResizable(true);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							if (controller.getStatus()) {
								try {
									// Инициализация
									// Если падеж не пуст
									if (list.getPDJ_NAME() != null) {
										// Получить измененный падеж
										String FioPod = (String) webView.getEngine().executeScript(
												"Padej('" + controller.getName_s() + "','" + list.getPDJ_NAME() + "')");
										webView.getEngine().executeScript("SetValue('" + id + "','" + FioPod + "')");

										webView.getEngine().executeScript("document.getElementById(\"" + id
												+ "\").setAttribute(\"value\", \"" + controller.getCode_s() + "\");");
//										System.out.println((String) webView.getEngine()
//												.executeScript("document.documentElement.outerHTML"));
									} else {
										webView.getEngine().executeScript("document.getElementById(\"" + id
												+ "\").setAttribute(\"value\", \"" + controller.getCode_s() + "\");");
										webView.getEngine().executeScript(
												"SetValue('" + id + "','" + controller.getName_s() + "')");
									}
									// _______________________
									// Сами данные
									{
										if (list.getPRM_FOR_PRM_SQL().length() > 10) {
											PreparedStatement prp = conn.prepareStatement(list.getPRM_FOR_PRM_SQL());
											prp.setLong(1, Long.valueOf(controller.getCode_s()));
											ResultSet rs = prp.executeQuery();
											while (rs.next()) {
												if (rs.getString("NAME_") != null & rs.getString("VALUE_") != null) {
													// Если на странице расположен тот элемент
													if (json.contains(rs.getString("NAME_").toLowerCase())) {
														webView.getEngine()
																.executeScript("SetValue('"
																		+ rs.getString("NAME_").toLowerCase() + "','"
																		+ rs.getString("VALUE_") + "')");
													}
												}
											}
											prp.close();
											rs.close();
										}
									}
								} catch (Exception e) {
									DbUtil.Log_Error(e);
								}
							}
						}
					});
					stage.show();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@SuppressWarnings("unused")
	@FXML
	void AddParamLocal() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			TreeItem<NT_TEMP_LIST_PARAM> tbl = param.getSelectionModel().getSelectedItem();
			if (val != null & tbl != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				webView.getEngine().executeScript(tbl.getValue().getHTML_CODE());
				String html = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
				// Запишем в файл
				//Reload2(html);
				fillTree();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	int ellen = 0;
	public static Node componentsPane;

	@FXML
	private SplitPane MainSplitPane;

	/**
	 * 
	 */
	private void InsertTable() {
		try {
//			Stage stage = new Stage();
//			Stage stage_ = (Stage) EditLocalParam.getScene().getWindow();
//
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/notary/doc/html/view/TableManage.fxml"));
//
//			TableManage controller = new TableManage();
//			loader.setController(controller);
//
//			Parent root = loader.load();
//			stage.setScene(new Scene(root));
//			stage.getIcons().add(new Image("/icon.png"));
//			stage.setTitle("Вставить таблицу");
//			stage.initOwner(stage_);
//			stage.setResizable(true);
//			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//				@Override
//				public void handle(WindowEvent paramT) {
//					if (controller.getTbl() != null) {
//						String InsTbl = DBUtil.SqlFromProp("/notary/doc/html/controller/Sql.properties", "TBL");
//						InsTbl = InsTbl.replace("$cell$", controller.getTbl().getColumnCnt()).replace("$row$",
//								controller.getTbl().getRowCnt());
//						WebView webView = (WebView) HtmlEditor.lookup("WebView");
//						webView.getEngine().executeScript(InsTbl);
//					}
//				}
//			});
//			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Показать параметры
	 */
	void ShowParam() {
		ellen = 0;
		MainSplitPane.getItems().forEach(item -> {
			ellen++;
//			System.out.println(item.getId());
		});

		if (componentsPane != null & ellen == 1) {
			MainSplitPane.getItems().add(1, componentsPane);
			MainSplitPane.setDividerPosition(0, 0.8);
		}
		MainSplitPane.getItems().toArray();
	}

	/**
	 * Спрятать параметры
	 */
	void HideParam() {
		ellen = 0;
		MainSplitPane.getItems().forEach(item -> {
			ellen++;
//			System.out.println(item.getId());
		});

		if (ellen == 2) {
			componentsPane = MainSplitPane.getItems().get(1);
			MainSplitPane.getItems().remove(componentsPane);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void TYPE_NAME(ActionEvent event) {
		try {
			{
				Node node = HtmlEditor.lookup(".top-toolbar");
				Node NodeNottom = HtmlEditor.lookup(".bottom-toolbar");
				if (node instanceof ToolBar) {
					boolean check = true;
					ToolBar bar = (ToolBar) node;
					ToolBar BarBottom = (ToolBar) NodeNottom;
					ObservableList<Node> list = bar.getItems();
					for (Node item : list) {
						if (item.getId() != null
								&& (item.getId().equals("ViewParams") | item.getId().equals("HideParams"))) {
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
							Button myButton = new Button("", icon);
							myButton.setId("TableAdd");

							BarBottom.getItems().add(myButton);
							myButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent arg0) {
									InsertTable();
								}
							});
						}
						// show
						{
							FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EYE_SLASH);
							icon.setFontSmoothingType(FontSmoothingType.LCD);
							icon.setSize("18");
							ToggleButton myButton = new ToggleButton("", icon);
							myButton.setId("ViewParams");
							myButton.setTooltip(new Tooltip("Показать/спрятать"));

							bar.getItems().add(myButton);
							myButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent arg0) {
									if (myButton.isSelected()) {
										HideParam();
									} else {
										ShowParam();
									}
								}
							});
						}
					}
				}

				// modify font selections.
				int i = 0;
				for (Node candidate : (HtmlEditor.lookupAll("ComboBox"))) {
					// fonts are selected by the second menu in the htmlEditor.
					if (candidate instanceof ComboBox && i == 1) {
						System.out.println("`````");
						// limit the font selections to our predefined list.
						ComboBox menuButton = (ComboBox) candidate;
						menuButton.setMinWidth(200);
						System.out.println(menuButton.getSelectionModel().getSelectedItem());
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
			Reload();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DelSelType(ActionEvent event) {
		try {
			TYPE_NAME.getSelectionModel().select(null);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public class JsToJava {
		public void run(String id) {
			ListVal(id);
		}

		public void Text(String Mes) {
			Msg.Message(Mes);
		}
	}

	void Reload2(String html) {
		try {
			WebView webView = (WebView) HtmlEditor.lookup("WebView");
			final WebEngine webEngine = webView.getEngine();
			final JsToJava jstojava = new JsToJava();
			// Запишем в файл
			{
				Writer out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(System.getenv("MJ_PATH") + "HTML/HTML.html"), StandardCharsets.UTF_8));
				out.write(html);
				out.close();
			}
			URL url = new File(System.getenv("MJ_PATH") + "HTML/HTML.html").toURI().toURL();
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

	void Reload() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				webView.setPrefHeight(5000);
				final WebEngine webEngine = webView.getEngine();
				final JsToJava jstojava = new JsToJava();
				// Запишем в файл
				{
					Writer out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(System.getenv("MJ_PATH") + "HTML/HTML.html"), StandardCharsets.UTF_8));
					out.write(val.getHTML_TEMP());
					out.close();
				}

				URL url = new File(System.getenv("MJ_PATH") + "HTML/HTML.html").toURI().toURL();
				webEngine.load(url.toExternalForm());
				webView.setContextMenuEnabled(false);
				webEngine.setJavaScriptEnabled(true);
				webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
					@Override
					public void changed(ObservableValue<? extends State> observableValue, State oldState,
							State newState) {
						if (newState == State.SUCCEEDED) {
							JSObject window = (JSObject) webEngine.executeScript("window");
							window.setMember("invoke", jstojava);
							try {
								// текущие поля на странице
								String json = (String) webView.getEngine().executeScript("writeJSONfile()");
								V_NT_TEMP_LIST vals = TYPE_NAME.getSelectionModel().getSelectedItem();
								if (vals.getREP_QUERY().length() > 10) {
									PreparedStatement prp = conn.prepareStatement(vals.getREP_QUERY());
									ResultSet rs = prp.executeQuery();
									while (rs.next()) {
										if (rs.getString("NAME_") != null & rs.getString("VALUE_") != null) {
											if (json.contains(rs.getString("NAME_").toLowerCase())) {
												webEngine.executeScript(
														"SetValue('" + rs.getString("NAME_").toLowerCase() + "','"
																+ rs.getString("VALUE_") + "')");
											}
										}
									}
									prp.close();
									rs.close();
								}
								// Fill
								fillTree();
							} catch (Exception e) {
								DbUtil.Log_Error(e);
							}
						}
					}
				});
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				// Получить поля из страницы
				String json = (String) webView.getEngine().executeScript("writeJSONfile()");
				// Список связанных параметров из шаблона
				String KeyValue = "";
				{
					PreparedStatement prp = conn
							.prepareStatement("select * from NT_TEMP_LIST_PARAM t where PRM_TMP_ID = ?");
					prp.setLong(1, val.getID());
					ResultSet rs = prp.executeQuery();
					while (rs.next()) {
						// Если тип параметра список
						if (rs.getLong("PRM_TYPE") == 1) {
							// Если параметр присутствует на странице
							if (json.contains(rs.getString("PRM_NAME"))) {
								// Выполнить функцию которая вернет значение атрибута "value"
								String values = (String) webView.getEngine()
										.executeScript("	function GetAtrVal(Ids) {\n"
												+ "		var div1 = document.getElementById(Ids);\n"
												+ "		var align = div1.getAttribute(\"value\");\n"
												+ "		return align;\n" + "	}\n" + "	GetAtrVal('"
												+ rs.getString("PRM_NAME") + "');");
								// Если параметр еще не инициализирован значением
								if (values == null) {
									KeyValue = KeyValue + rs.getString("PRM_ID") + "|~|~|" + "\r\n";
								} // Иначе
								else if (values != null) {
									KeyValue = KeyValue + rs.getString("PRM_ID") + "|~|~|" + values + "\r\n";
								}
							} // Если параметр отсутствует на странице
							else {
								KeyValue = KeyValue + rs.getString("PRM_ID") + "|~|~|" + "\r\n";
							}
						} // Если тип параметра не список
						else {
							// Если параметр присутствует на странице
							if (json.contains(rs.getString("PRM_NAME"))) {
								KeyValue = KeyValue + rs.getString("PRM_ID") + "|~|~|"
										+ (String) webView.getEngine()
												.executeScript("ReturnValue('" + rs.getString("PRM_NAME") + "')")
										+ "\r\n";
							} else {
								KeyValue = KeyValue + rs.getString("PRM_ID") + "|~|~|" + "\r\n";
							}
						}
					}
					prp.close();
					rs.close();
					System.out.print(KeyValue.trim());
				}
				CallableStatement cls = conn.prepareCall("{call NT_PKG.ADD_DOC_HTML(?,?,?,?,?)}");
				cls.registerOutParameter(1, Types.VARCHAR);
				cls.registerOutParameter(5, Types.VARCHAR);
				cls.setLong(2, val.getID());
				Clob clob = conn.createClob();
				clob.setString(1, KeyValue.trim());
				cls.setClob(3, clob);
				Clob PAGE = conn.createClob();
				PAGE.setString(1, (String) webView.getEngine().executeScript("document.documentElement.outerHTML"));
				cls.setClob(4, PAGE);
				// DbmsOutput
				try (DbmsOutputCapture capture = new DbmsOutputCapture(conn)) {
					List<String> lines = capture.execute(cls);
					System.out.println(lines);
				} catch (Exception e) {
					DbUtil.Log_Error(e);
				}
				// --------------
				if (cls.getString(1) == null) {
					conn.commit();
					AddDocId(cls.getLong(5));
					setStatus(true);
					onclose();
				} else {
					conn.rollback();
					setStatus(false);
					Msg.Message(cls.getString(1));
				}
				cls.close();
			} else {
				Msg.Message("ПУСТО!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public V_NT_DOC NT_DOC;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void AddDocId(Long Ids) {
		try {
			PreparedStatement prp = conn.prepareStatement("select * from V_NT_DOC where ID = ?");
			prp.setLong(1, Ids);
			ResultSet rs = prp.executeQuery();
			ObservableList<V_NT_DOC> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				NT_DOC = new V_NT_DOC();
				if (rs.getClob("HTML_DOCUMENT") != null) {
					NT_DOC.setHTML_DOCUMENT(new ConvConst().ClobToString(rs.getClob("HTML_DOCUMENT")));
				}
				NT_DOC.setCR_TIME(rs.getString("CR_TIME"));
				NT_DOC.setID(rs.getLong("ID"));
				NT_DOC.setOPER(rs.getString("OPER"));
				NT_DOC.setNOTARY(rs.getLong("NOTARY"));
				NT_DOC.setNT_TYPE(rs.getLong("NT_TYPE"));
				NT_DOC.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				NT_DOC.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				NT_DOC.setTYPE_NAME(rs.getString("TYPE_NAME"));
				dlist.add(NT_DOC);
			}
			prp.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program",getClass().getName());
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	private void convert_TYPE_NAME(ComboBox<V_NT_TEMP_LIST> cmbbx) {
		cmbbx.setConverter(new StringConverter<V_NT_TEMP_LIST>() {
			@Override
			public String toString(V_NT_TEMP_LIST object) {
				return object != null ? object.getNAMES() : "";
			}

			@Override
			public V_NT_TEMP_LIST fromString(String string) {
				return cmbbx.getItems().stream().filter(object -> object.getNAMES().equals(string)).findFirst()
						.orElse(null);
			}

		});
	}

	@FXML
	void PrintScan(ActionEvent event) {

	}

	@FXML
	void EditScan(ActionEvent event) {

	}

	@FXML
	void AddScan(ActionEvent event) {

	}

	@FXML
	void DeleteScan(ActionEvent event) {

	}

	@FXML
	void PlusDocParamCliRef() {

	}

	// limits the fonts a user can select from in the html editor.
	private static final List<String> limitedFonts = FXCollections.observableArrayList("Times New Roman", "Arial");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			EditLocalParam.setVisible(false);
			DeleteLocalParam.setVisible(false);

			LocalParams.setVisible(false);
//			Platform.runLater(new Runnable() {
//				@Override
//				public void run() {
//
//				}
//			});

			// Двойной щелчок по строке для открытия документа
			param.setRowFactory(tv -> {
				TreeTableRow<NT_TEMP_LIST_PARAM> row = new TreeTableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						AddParamLocal();
					}
				});
				return row;
			});

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

			HtmlEditor.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (isValidEvent(event)) {
						fillTree();
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

			//PrintToolbar.setDisable(true);
			Tabs.getTabs().remove(scans);

			// HtmlEditor.getStyleClass().add("mylistview");
			// HtmlEditor.getStylesheets().add("/ScrPane.css");
			dbConnect();
			DbUtil.Run_Process(conn,getClass().getName());
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from V_NT_TEMP_LIST");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<V_NT_TEMP_LIST> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					V_NT_TEMP_LIST list = new V_NT_TEMP_LIST();
					if (rs.getClob("REP_QUERY") != null) {
						list.setREP_QUERY(new ConvConst().ClobToString(rs.getClob("REP_QUERY")));
					}
					list.setPARENT(rs.getLong("PARENT"));
					list.setNAMES(rs.getString("NAMES"));
					list.setNAME(rs.getString("NAME"));
					if (rs.getClob("HTML_TEMP") != null) {
						list.setHTML_TEMP(new ConvConst().ClobToString(rs.getClob("HTML_TEMP")));
					}
					list.setID(rs.getLong("ID"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();

//				FilteredList<V_NT_TEMP_LIST> filterednationals = new FilteredList<V_NT_TEMP_LIST>(combolist);
//				TYPE_NAME.getEditor().textProperty()
//						.addListener(new InputFilter<V_NT_TEMP_LIST>(TYPE_NAME, filterednationals, false));

				TYPE_NAME.setItems(combolist);

				FxUtilTest.getComboBoxValue(TYPE_NAME);
				FxUtilTest.autoCompleteComboBoxPlus(TYPE_NAME, (typedText, itemToCompare) -> itemToCompare.getNAMES()
						.toLowerCase().contains(typedText.toLowerCase()));

				convert_TYPE_NAME(TYPE_NAME);
			}

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
