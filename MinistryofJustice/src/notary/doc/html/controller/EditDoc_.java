package notary.doc.html.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.apache.log4j.Logger;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import netscape.javascript.JSObject;
import notary.doc.html.model.V_NT_DOC;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class EditDoc_ {

	public EditDoc_() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
	}

	private BooleanProperty status;

	Connection conn;

	public void setConn(Connection conn, V_NT_DOC val) {
		this.conn = conn;
		this.NT_DOC = val;
	}

	V_NT_DOC NT_DOC;

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
	private WebView webView;

	@FXML
	void CENCEL(ActionEvent event) {
		onclose();
	}

	void onclose() {
		Stage stage = (Stage) webView.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	public class JsToJava {
		public void run(String id) {
			ListVal(id);
		}

		public void Text(String Mes) {
			Msg.Message(Mes);
		}
	}

	@SuppressWarnings("unused")
	@FXML
	void Print(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				String path = null;
				String sql = null;
				{
					PreparedStatement prp = conn
							.prepareStatement("select DOCX_PATH,REP_QUERY from NT_TEMP_LIST t where t.id = ? ");
					prp.setInt(1, val.getID());
					ResultSet rs = prp.executeQuery();
					if (rs.next()) {
						path = rs.getString("DOCX_PATH");
						sql = rs.getString("REP_QUERY");
						if (rs.getClob("REP_QUERY") != null) {
							sql = new ConvConst().ClobToString(rs.getClob("REP_QUERY"));
						}
					}
					rs.close();
					prp.close();
				}
				// Вызов
				Docx docx = new Docx(System.getenv("MJ_PATH") + path);
				docx.setVariablePattern(new VariablePattern("#{", "}"));
				// preparing variables
				Variables variables = new Variables();
				PreparedStatement prepStmt = DBUtil.conn.prepareStatement(
						DBUtil.SqlFromProp("/notary/doc/html/controller/Sql.properties", "PrintNtDocPrmVals"));
				prepStmt.setInt(1, NT_DOC.getID());
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					variables.addTextVariable(new TextVariable("#{" + rs.getString("NAME").toLowerCase() + "}",
							(rs.getString("VALUE") == null || rs.getString("VALUE").length() < 2 ? "ПУСТО!"
									: rs.getString("VALUE"))));
				}
				rs.close();
				prepStmt.close();

				// fill template
				docx.fillTemplate(variables);
				File tempFile = File.createTempFile("Документ", ".docx",
						new File(System.getenv("MJ_PATH") + "OutReports"));
				FileOutputStream str = new FileOutputStream(tempFile);
				docx.save(str);
				str.close();
				tempFile.deleteOnExit();
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(tempFile);
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	NT_TEMP_LIST_PARAM list = null;

	void ListVal(String id) {
		try {
			PreparedStatement prp = conn.prepareStatement("select * from NT_TEMP_LIST_PARAM t where PRM_NAME = ?");
			prp.setString(1, id);
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
			stage.setTitle("Список");
			stage.initOwner(stage_);
			stage.setResizable(true);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						try {
							// Инициализация
							System.out.println("!!-------------controller.getCode_s()=" + controller.getCode_s());
							System.out.println("1____PDJ_NAME=" + list.getPDJ_NAME());
							System.out.println("2____PRM_NAME=" + id);
							// Если падеж не пуст
							if (list.getPDJ_NAME() != null) {
								// Получить измененный падеж
								String FioPod = (String) webView.getEngine()
										.executeScript("Padej('" + controller.getCode_s() + ". "
												+ controller.getName_s() + "','" + list.getPDJ_NAME() + "')");
								System.out.println("........FioPod=" + FioPod);
								webView.getEngine().executeScript("SetValue('" + id + "','" + FioPod + "')");
							} else {
								webView.getEngine().executeScript("SetValue('" + id + "','" + controller.getCode_s()
										+ ". " + controller.getName_s() + "')");
							}
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
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	// private WebEngine webEngine;

	void Reload() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				final WebEngine webEngine = webView.getEngine();
				final JsToJava jstojava = new JsToJava();
				String HTML = "";
				{
					PreparedStatement prp = conn
							.prepareStatement("select * from NT_TEMP_LIST_JS where TMP_LIST_ID = ?");
					prp.setInt(1, val.getID());
					ResultSet rs = prp.executeQuery();
					while (rs.next()) {
						HTML = HTML + val.getHTML_TEMP().replace("{" + rs.getString("JSNAME") + "}",
								new ConvConst().ClobToString(rs.getClob("JSFILE")));
					}
					prp.close();
					rs.close();
				}
//				URL url = HtmlEditorTest.class.getResource("/notary/doc/html/controller/HTML.html");
//				webEngine.load(url.toExternalForm());
				webEngine.loadContent(HTML);
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
								PreparedStatement prp = conn.prepareStatement(
										TYPE_NAME.getSelectionModel().getSelectedItem().getREP_QUERY());
								ResultSet rs = prp.executeQuery();
								while (rs.next()) {
									System.out.println("------------SetValue('" + rs.getString("NAME_").toLowerCase()
											+ "','" + rs.getString("VALUE_") + "')");
									if (rs.getString("NAME_") != null & rs.getString("VALUE_") != null) {
										webEngine.executeScript("SetValue('" + rs.getString("NAME_").toLowerCase()
												+ "','" + rs.getString("VALUE_") + "')");
									}
								}
								prp.close();
								rs.close();
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

	void Init() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				final WebEngine webEngine = webView.getEngine();
				final JsToJava jstojava = new JsToJava();

				String HTML = "";
				{
					PreparedStatement prp = conn
							.prepareStatement("select * from NT_TEMP_LIST_JS where TMP_LIST_ID = ?");
					prp.setInt(1, val.getID());
					ResultSet rs = prp.executeQuery();
					while (rs.next()) {
						HTML = HTML + val.getHTML_TEMP().replace("{" + rs.getString("JSNAME") + "}",
								new ConvConst().ClobToString(rs.getClob("JSFILE")));
					}
					prp.close();
					rs.close();
				}
				webEngine.loadContent(HTML);
				webView.setContextMenuEnabled(false);
				webEngine.setJavaScriptEnabled(true);
				webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
					@Override
					public void changed(ObservableValue<? extends State> observableValue, State oldState,
							State newState) {
						if (newState == State.SUCCEEDED) {
							JSObject window = (JSObject) webEngine.executeScript("window");
							window.setMember("invoke", jstojava);
							// При открытии
							try {
								// Сами параметры
								{
									PreparedStatement stsmt = conn.prepareStatement(DBUtil.SqlFromProp(
											"/notary/doc/html/controller/Sql.properties", "EditNtDocPrmVals"));
									stsmt.setInt(1, NT_DOC.getID());
									ResultSet rs = stsmt.executeQuery();
									while (rs.next()) {
										if (rs.getString("PRM_NAME") != null & rs.getString("VAL_NT_VALUE") != null) {
											System.out.println(
													"------------SetValue('" + rs.getString("PRM_NAME").toLowerCase()
															+ "','" + rs.getString("VAL_NT_VALUE") + "')");
											webView.getEngine()
													.executeScript("SetValue('" + rs.getString("PRM_NAME").toLowerCase()
															+ "','" + rs.getString("VAL_NT_VALUE") + "')");
										}
									}
									stsmt.close();
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
	void TYPE_NAME(ActionEvent event) {
		Reload();
	}

	@FXML
	void DelSelType(ActionEvent event) {
		try {
			TYPE_NAME.getSelectionModel().select(null);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				String KeyValue = "";
				PreparedStatement prp = conn
						.prepareStatement("select * from NT_TEMP_LIST_PARAM t where PRM_TMP_ID = ?");
				prp.setInt(1, val.getID());
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					KeyValue = KeyValue + rs.getString("PRM_ID") + "|~|~|" + (String) webView.getEngine()
							.executeScript("ReturnValue('" + rs.getString("PRM_NAME") + "')") + "\r\n";
				}
				prp.close();
				rs.close();
				System.out.print(KeyValue);
				if (!KeyValue.equals("")) {
					CallableStatement cls = conn.prepareCall("{call NT_PKG.EDIT_DOC_HTML(?,?,?,?)}");
					cls.registerOutParameter(1, Types.VARCHAR);
					cls.setInt(2, NT_DOC.getID());
					Clob clob = conn.createClob();
					clob.setString(1, KeyValue);
					cls.setClob(3, clob);
					cls.setInt(4, val.getID());
					cls.execute();
					if (cls.getString(1) == null) {
						setStatus(true);
						onclose();
					} else {
						setStatus(false);
						Msg.Message(cls.getString(1));
					}
					cls.close();
				} else {
					Msg.Message("ПУСТО!");
				}
			}
		} catch (Exception e) {
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
	private void initialize() {
		try {
			webView.getStyleClass().add("mylistview");
			webView.getStylesheets().add("/ScrPane.css");
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
				TYPE_NAME.setItems(combolist);
				convert_TYPE_NAME(TYPE_NAME);
				rs.close();
			}
			if (NT_DOC.getNT_TYPE() != null) {
				for (V_NT_TEMP_LIST ld : TYPE_NAME.getItems()) {
					if (NT_DOC.getNT_TYPE().equals(ld.getID())) {
						TYPE_NAME.getSelectionModel().select(ld);
						break;
					}
				}
			}
			Init();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
