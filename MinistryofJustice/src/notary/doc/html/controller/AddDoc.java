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

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
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
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.widgets.DbmsOutputCapture;
import netscape.javascript.JSObject;
import notary.doc.html.model.V_NT_DOC;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

public class AddDoc {

	public AddDoc() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
	}

	private BooleanProperty status;

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

	@FXML
	private Button PrintBtn;

	@FXML
	private ToolBar PrintToolbar;

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
			DBUtil.LOG_ERROR(e);
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
				prp.setInt(2, val.getID());
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					list = new NT_TEMP_LIST_PARAM();
					list.setPRM_ID(rs.getInt("PRM_ID"));
					list.setPRM_NAME(rs.getString("PRM_NAME"));
					list.setPRM_R_NAME(rs.getString("PRM_R_NAME"));
					list.setPRM_TMP_ID(rs.getInt("PRM_TMP_ID"));
					list.setPRM_SQL(rs.getString("PRM_SQL"));
					list.setPRM_TYPE(rs.getInt("PRM_TYPE"));
					list.setPRM_PADEJ(rs.getInt("PRM_PADEJ"));
					list.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
					if (rs.getClob("PRM_FOR_PRM_SQL") != null) {
						list.setPRM_FOR_PRM_SQL(new ConvConst().ClobToString(rs.getClob("PRM_FOR_PRM_SQL")));
					}
					list.setPDJ_NAME(rs.getString("PDJ_NAME"));
					list.setPRM_PADEJ(rs.getInt("PRM_PADEJ"));
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
					stage.setScene(new Scene(root));
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
										webView.getEngine().executeScript(
												"SetValue('" + id + "','" + controller.getName_s() + "')");
									}
									// _______________________
									// Сами данные
									{
										PreparedStatement prp = conn.prepareStatement(list.getPRM_FOR_PRM_SQL());
										prp.setInt(1, Integer.valueOf(controller.getCode_s()));
										ResultSet rs = prp.executeQuery();
										while (rs.next()) {
											if (rs.getString("NAME_") != null & rs.getString("VALUE_") != null) {
												// Если на странице расположен тот элемент
												if (json.contains(rs.getString("NAME_").toLowerCase())) {
													webView.getEngine().executeScript(
															"SetValue('" + rs.getString("NAME_").toLowerCase() + "','"
																	+ rs.getString("VALUE_") + "')");
												}
											}
										}
										prp.close();
										rs.close();
									}
								} catch (Exception e) {
									DBUtil.LOG_ERROR(e);
								}
							}
						}
					});
					stage.show();
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	void AddParam() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				// WebPage webPage = Accessor.getPageFor(webView.getEngine());

				// Получить поля из страницы
				String json = (String) webView.getEngine().executeScript("writeJSONfile()");
				// String html = (String)
				// webView.getEngine().executeScript("document.documentElement.outerHTML");
				System.out.println("-------\r\n" + json);
				Map<String, String> result = new ObjectMapper().readValue(json, HashMap.class);
				String JsonStr = "";
				for (Map.Entry<String, String> entry : result.entrySet()) {
					JsonStr = JsonStr + entry.getKey() + "|~|~|" + entry.getValue() + "\r\n";
				}
				// System.out.println(JsonStr.trim());
				// ------------------
				// открыть формы с параметрами за минусом тех, что находятся на странице
				Stage stage = new Stage();
				Stage stage_ = (Stage) HtmlEditor.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/doc/html/view/AddParam.fxml"));

				AddParam controller = new AddParam();
				controller.setConn(conn, JsonStr.trim(), val);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						// если выбран параметр
						// 1. получить html разметку параметра
						// 2. постараться внедрить в то место где стоит курсор
						if (controller.getStatus()) {
							webView.getEngine().executeScript(controller.prm_ret.getHTML_CODE());
							String html = (String) webView.getEngine()
									.executeScript("document.documentElement.outerHTML");
							// Запишем в файл
							Reload2(html);
							System.out.println("JS_CODE_ID\r\n" + controller.prm_ret.getPRM_NAME());
							System.out.println("JS_CODE_\r\n" + controller.prm_ret.getHTML_CODE());
						}
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void TYPE_NAME(ActionEvent event) {
		try {
			{
				Node node = HtmlEditor.lookup(".top-toolbar");
				if (node instanceof ToolBar) {
					boolean check = true;
					ToolBar bar = (ToolBar) node;
					ObservableList<Node> list = bar.getItems();
					for (Node item : list) {
						if (item.getId() != null && item.getId().equals("MJAddParam")) {
							check = false;
							break;
						}
					}
					if (check) {
						FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.LIST_ALT);
						icon.setFontSmoothingType(FontSmoothingType.LCD);
						icon.setSize("18");
						Button myButton = new Button("Добавить параметр", icon);
						myButton.setId("MJAddParam");

						bar.getItems().add(myButton);
						myButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								AddParam();
							}
						});
					}
				}

//			Node node = HtmlEditor.lookup(".bottom-toolbar");
//			ToolBar bar = (ToolBar) node;
//			ObservableList<Node> list = bar.getItems();
//			for (Node item : list) {
//				System.out.println(item.getTypeSelector() + " " + item.getId());
//			}
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
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DelSelType(ActionEvent event) {
		try {
			TYPE_NAME.getSelectionModel().select(null);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
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
			DBUtil.LOG_ERROR(e);
		}
	}

	void Reload() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
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
								{
									Document doc = webEngine.getDocument();
									Transformer transformer = TransformerFactory.newInstance().newTransformer();
									transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
									transformer.setOutputProperty(OutputKeys.METHOD, "html");
									transformer.setOutputProperty(OutputKeys.INDENT, "yes");
									transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
									transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
									transformer.transform(new DOMSource(doc),
											new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
								}

								// текущие поля на странице
								String json = (String) webView.getEngine().executeScript("writeJSONfile()");
								V_NT_TEMP_LIST vals = TYPE_NAME.getSelectionModel().getSelectedItem();
								if (vals.getREP_QUERY() != null) {
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
							} catch (Exception e) {
								DBUtil.LOG_ERROR(e);
							}
						}
					}
				});
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
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
					prp.setInt(1, val.getID());
					ResultSet rs = prp.executeQuery();
					while (rs.next()) {
						// Если тип параметра список
						if (rs.getInt("PRM_TYPE") == 1) {
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
				cls.setInt(2, val.getID());
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
					DBUtil.LOG_ERROR(e);
				}
				// --------------
				if (cls.getString(1) == null) {
					conn.commit();
					AddDocId(cls.getInt(5));
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
			DBUtil.LOG_ERROR(e);
		}
	}

	public V_NT_DOC NT_DOC;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void AddDocId(Integer Ids) {
		try {
			PreparedStatement prp = conn.prepareStatement("select * from V_NT_DOC where ID = ?");
			prp.setInt(1, Ids);
			ResultSet rs = prp.executeQuery();
			ObservableList<V_NT_DOC> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				NT_DOC = new V_NT_DOC();
				if (rs.getClob("HTML_DOCUMENT") != null) {
					NT_DOC.setHTML_DOCUMENT(new ConvConst().ClobToString(rs.getClob("HTML_DOCUMENT")));
				}
				NT_DOC.setCR_TIME(rs.getString("CR_TIME"));
				NT_DOC.setID(rs.getInt("ID"));
				NT_DOC.setOPER(rs.getString("OPER"));
				NT_DOC.setNOTARY(rs.getInt("NOTARY"));
				NT_DOC.setNT_TYPE(rs.getInt("NT_TYPE"));
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
			DBUtil.LOG_ERROR(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddNotaryDoc");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void convert_TYPE_NAME(ComboBox<V_NT_TEMP_LIST> cmbbx) {
		cmbbx.setConverter(new StringConverter<V_NT_TEMP_LIST>() {
			@Override
			public String toString(V_NT_TEMP_LIST product) {
				return product != null ? product.getNAMES() : null;
			}

			@Override
			public V_NT_TEMP_LIST fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getNAMES().equals(string)).findFirst()
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

	// limits the fonts a user can select from in the html editor.
	private static final List<String> limitedFonts = FXCollections.observableArrayList("Times New Roman");

	@FXML
	private void initialize() {
		try {
			PrintToolbar.setDisable(true);
			Tabs.getTabs().remove(scans);

			HtmlEditor.getStyleClass().add("mylistview");
			HtmlEditor.getStylesheets().add("/ScrPane.css");
			dbConnect();
			DBUtil.RunProcess(conn);
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from V_NT_TEMP_LIST");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<V_NT_TEMP_LIST> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					V_NT_TEMP_LIST list = new V_NT_TEMP_LIST();
					if (rs.getClob("REP_QUERY") != null) {
						list.setREP_QUERY(new ConvConst().ClobToString(rs.getClob("REP_QUERY")));
					}
					list.setPARENT(rs.getInt("PARENT"));
					list.setNAMES(rs.getString("NAMES"));
					list.setNAME(rs.getString("NAME"));
					if (rs.getClob("HTML_TEMP") != null) {
						list.setHTML_TEMP(new ConvConst().ClobToString(rs.getClob("HTML_TEMP")));
					}
					list.setID(rs.getInt("ID"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				FilteredList<V_NT_TEMP_LIST> filterednationals = new FilteredList<V_NT_TEMP_LIST>(combolist);
				TYPE_NAME.getEditor().textProperty()
						.addListener(new InputFilter<V_NT_TEMP_LIST>(TYPE_NAME, filterednationals, false));
				TYPE_NAME.setItems(filterednationals);
				convert_TYPE_NAME(TYPE_NAME);
				rs.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
