package notary.doc.html.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

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
import netscape.javascript.JSObject;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

@SuppressWarnings("restriction")
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
				stage.setTitle("������");
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						if (controller.getStatus()) {
							try {
								// �������������
								System.out.println("!!-------------controller.getCode_s()=" + controller.getCode_s());
								System.out.println("1____PDJ_NAME=" + list.getPDJ_NAME());
								System.out.println("2____PRM_NAME=" + id);
								// ���� ����� �� ����
								if (list.getPDJ_NAME() != null) {
									// �������� ���������� �����
									String FioPod = (String) webView.getEngine()
											.executeScript("Padej('" + controller.getCode_s() + ". "
													+ controller.getName_s() + "','" + list.getPDJ_NAME() + "')");
									System.out.println("........FioPod=" + FioPod);
									webView.getEngine().executeScript("SetValue('" + id + "','" + FioPod + "')");
								} else {
									webView.getEngine().executeScript("SetValue('" + id + "','" + controller.getCode_s()
											+ ". " + controller.getName_s() + "')");
								}
								// _______________________
								{
									PreparedStatement prp = conn.prepareStatement(list.getPRM_FOR_PRM_SQL());
									prp.setInt(1, Integer.valueOf(controller.getCode_s()));
									ResultSet rs = prp.executeQuery();
									while (rs.next()) {
										System.out.println(
												rs.getString("NAME_").toLowerCase() + "=" + rs.getString("VALUE_"));
										if (rs.getString("NAME_") != null & rs.getString("VALUE_") != null) {
											webView.getEngine()
													.executeScript("SetValue('" + rs.getString("NAME_").toLowerCase()
															+ "','" + rs.getString("VALUE_") + "')");
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
				WebPage webPage = Accessor.getPageFor(webView.getEngine());

				String json = (String) webView.getEngine().executeScript("writeJSONfile()");
				Map<String, String> result = new ObjectMapper().readValue(json, HashMap.class);
				String JsonStr = "";
				for (Map.Entry<String, String> entry : result.entrySet()) {
					JsonStr = JsonStr + entry.getKey() + "|~|~|" + entry.getValue() + "\r\n";
				}
				// ------------------
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
						if (controller.getStatus()) {

//							System.out.println("document.execCommand('insertHTML', false, '"
//									+ controller.prm.getHTML_CODE() + "');");

//							webView.getEngine().executeScript("document.execCommand('insertHTML', false, '"
//									+ controller.prm.getHTML_CODE() + "');");

//							String html = HtmlEditor.getHtmlText();

							webPage.executeCommand("insertHTML", controller.prm.getHTML_CODE());

							String html = (String) webView.getEngine()
									.executeScript("document.documentElement.innerHTML");
							//System.out.println("-------" + controller.prm.getHTML_CODE());
							System.out.println(html);
							// ������� � ����
							//Reload2(html);
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
						Button myButton = new Button("�������� ��������", icon);
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
			// ������� � ����
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
				// ������� � ����
				{
					Writer out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(System.getenv("MJ_PATH") + "HTML/HTML.html"), StandardCharsets.UTF_8));
					out.write(val.getHTML_TEMP());
					out.close();
				}
//				String HTML = "";
//				{
//					// ���������� js ��������
//					PreparedStatement prp = conn
//							.prepareStatement("select * from NT_TEMP_LIST_JS where TMP_LIST_ID = ?");
//					prp.setInt(1, val.getID());
//					ResultSet rs = prp.executeQuery();
//					while (rs.next()) {
//						HTML = HTML + val.getHTML_TEMP().replace("{" + rs.getString("JSNAME") + "}",
//								new ConvConst().ClobToString(rs.getClob("JSFILE")));
//					}
//					prp.close();
//					rs.close();
//				}
				// URL url =
				// HtmlEditorTest.class.getResource("/notary/doc/html/controller/Test.html");
				// webEngine.load(url.toExternalForm());
				URL url = new File(System.getenv("MJ_PATH") + "HTML/HTML.html").toURI().toURL();
				webEngine.load(url.toExternalForm());
				// webEngine.loadContent(HTML);
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
								V_NT_TEMP_LIST vals = TYPE_NAME.getSelectionModel().getSelectedItem();
								if (vals.getREP_QUERY() != null) {
									PreparedStatement prp = conn.prepareStatement(vals.getREP_QUERY());
									ResultSet rs = prp.executeQuery();
									while (rs.next()) {
//										System.out.println("------------SetValue('" + rs.getString("NAME_").toLowerCase()
//												+ "','" + rs.getString("VALUE_") + "')");
										if (rs.getString("NAME_") != null & rs.getString("VALUE_") != null) {
											webEngine.executeScript("SetValue('" + rs.getString("NAME_").toLowerCase()
													+ "','" + rs.getString("VALUE_") + "')");
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

			}
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

	// limits the fonts a user can select from in the html editor.
	private static final List<String> limitedFonts = FXCollections.observableArrayList("Times New Roman");

	@FXML
	private void initialize() {
		try {
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
