package notary.doc.html.controller;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.JPopupMenu;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
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
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;
import mj.widgets.DbmsOutputCapture;
import mj.widgets.FxUtilTest;
import netscape.javascript.JSObject;
import notary.doc.html.model.NT_SCANS;
import notary.doc.html.model.V_NT_DOC;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;
import test.JorthoExample;
import test.SpellCheckExampleUi;

public class EditDoc {

	public EditDoc() {
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
	private Button DelSelType;

	@FXML
	void CENCEL(ActionEvent event) {
		onclose();
	}

	void onclose() {
		Stage stage = (Stage) TYPE_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private TableView<NT_SCANS> NT_SCANS;

	@FXML
	private TableColumn<NT_SCANS, String> SC_FILE_NAME;

	@FXML
	private TableColumn<NT_SCANS, String> SC_TYPE;

	@FXML
	private TableColumn<NT_SCANS, String> SC_OPER;

	@FXML
	private TableColumn<Object, LocalDateTime> SC_DATE;

	@FXML
	private TabPane Tabs;

	@FXML
	private Tab scans;

	@FXML
	private MenuButton LocalParams;

	@FXML
	private Button EditLocalParam;

	@FXML
	private Button DeleteLocalParam;

	@FXML
	void EditLocalParam(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val_tp = TYPE_NAME.getSelectionModel().getSelectedItem();
			TreeItem<NT_TEMP_LIST_PARAM> val = param.getSelectionModel().getSelectedItem();
			if (val != null & val_tp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) Tabs.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/doc/html/view/DocParamAdd.fxml"));

				DocParamEdit controller = new DocParamEdit();
				controller.setConn(conn, val_tp, NT_DOC, val.getValue());
				loader.setController(controller);

				Parent root = loader.load();

				Scene scene = new Scene(root);
//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);

				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать параметр");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						try {
							fillTree();
						} catch (Exception e) {
							DbUtil.Log_Error(e);
						}
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DeleteLocalParam(ActionEvent event) {
		try {
			TreeItem<NT_TEMP_LIST_PARAM> val = param.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = (Stage) param.getScene().getWindow();
				Label alert = new Label("Удалить параметр?");
				alert.setLayoutX(75.0);
				alert.setLayoutY(11.0);
				alert.setPrefHeight(17.0);

				Button no = new Button();
				no.setText("Нет");
				no.setLayoutX(111.0);
				no.setLayoutY(56.0);
				no.setPrefWidth(72.0);
				no.setPrefHeight(21.0);

				Button yes = new Button();
				yes.setText("Да");
				yes.setLayoutX(14.0);
				yes.setLayoutY(56.0);
				yes.setPrefWidth(72.0);
				yes.setPrefHeight(21.0);

				AnchorPane yn = new AnchorPane();
				yn.getChildren().add(alert);
				yn.getChildren().add(no);
				yn.getChildren().add(yes);
				Scene ynScene = new Scene(yn, 250, 100);
				Stage newWindow_yn = new Stage();
				no.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						newWindow_yn.close();
					}
				});
				yes.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							PreparedStatement delete = conn.prepareStatement("declare "
									+ "pragma autonomous_transaction;" + "begin "
									+ " delete from nt_temp_list_param_doc where PRM_ID = ?;" + "commit;" + "end;");
							delete.setLong(1, val.getValue().getPRM_ID());
							delete.executeUpdate();
							delete.close();
							fillTree();
						} catch (Exception e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DbUtil.Log_Error(e1);
							}
							DbUtil.Log_Error(e);
						}
						newWindow_yn.close();
					}
				});
				newWindow_yn.setTitle("Внимание");
				newWindow_yn.setScene(ynScene);
				newWindow_yn.initModality(Modality.WINDOW_MODAL);
				newWindow_yn.initOwner(stage);
				newWindow_yn.setResizable(false);
				newWindow_yn.getIcons().add(new Image("/icon.png"));
				newWindow_yn.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void PlusDocParamCliRef() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) Tabs.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/doc/html/view/DocParamAdd.fxml"));

				DocParamAdd controller = new DocParamAdd();
				controller.setConn(conn, val, NT_DOC);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Добавить параметр");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						try {
							fillTree();
						} catch (Exception e) {
							DbUtil.Log_Error(e);
						}
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void InitScans() {
		try {
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			PreparedStatement prp = conn.prepareStatement("select SC_ID," + "SC_FILE_NAME," + " SC_DATE," + "SC_OPER,"
					+ "SC_TYPE," + "SC_DOC " + "from NT_SCANS where sc_doc = ?");
			prp.setLong(1, NT_DOC.getID());
			ResultSet rs = prp.executeQuery();
			ObservableList<NT_SCANS> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				NT_SCANS list = new NT_SCANS();
				list.setSC_ID(rs.getLong("SC_ID"));
				list.setSC_FILE_NAME(rs.getString("SC_FILE_NAME"));
				list.setSC_DATE((rs.getDate("SC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("SC_DATE")), formatterwt) : null);
				list.setSC_OPER(rs.getString("SC_OPER"));
				list.setSC_TYPE(rs.getString("SC_TYPE"));
				list.setSC_DOC(rs.getLong("SC_DOC"));

				dlist.add(list);
			}
			prp.close();
			rs.close();
			NT_SCANS.setItems(dlist);

			TableFilter<NT_SCANS> tableFilter = TableFilter.forTableView(NT_SCANS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public static String transliterate(String message) {
		char[] abcCyr = { ' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р',
				'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е',
				'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
				'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
				'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		String[] abcLat = { " ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p",
				"r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G",
				"D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts",
				"Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			for (int x = 0; x < abcCyr.length; x++) {
				if (message.charAt(i) == abcCyr[x]) {
					builder.append(abcLat[x]);
				}
			}
		}
		return builder.toString();
	}

	void Print() {
		try {
			Save(true);
			// при печати сохраним содержимое страницы
			// Замена input = span
			WebView webView = (WebView) HtmlEditor.lookup("WebView");
			webView.getEngine()
					.executeScript(DbUtil.Sql_From_Prop("/notary/doc/html/controller/Sql.properties", "HTMLInputToSpan"));

//			Printer pdfPrinter = null;
//			Iterator<Printer> iter = Printer.getAllPrinters().iterator();
//			while (iter.hasNext()) {
//				Printer printer = iter.next();
//				if (printer.getName().equals(PRINTER_ID.getValue())) {
//					pdfPrinter = printer;
//				}
//			}

			Stage stage = (Stage) HtmlEditor.getScene().getWindow();

			PrinterJob job = null;
			try {
				job = PrinterJob.createPrinterJob();
				// Show the print setup dialog
				boolean proceed = job.showPrintDialog(stage);

				if (proceed) {

					Printer pdfPrinter = job.getPrinter();
					PageLayout layout = pdfPrinter.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
							50 /* lMargin */, 25 /* rMargin */, 25 /* tMargin */, 25 /* bMargin */);
					job = PrinterJob.createPrinterJob(pdfPrinter);
					job.getJobSettings().setPageLayout(layout);

					job.getJobSettings().setJobName(NT_DOC.getID() + ". "
							+ ((NT_DOC.getTYPE_NODE() != null) ? transliterate(NT_DOC.getTYPE_NODE()) : ""));

					// Show the print setup dialog
					boolean proceed2 = job.showPageSetupDialog(stage);

					if (proceed2) {
						webView.getEngine().print(job);
					}
				}

				job.endJob();
			} finally {
				if (job != null) {
					job.endJob();
				}
			}

//			{
//				PrinterJob job2 = PrinterJob.createPrinterJob();
//				if (job2 != null && job2.showPrintDialog(null) ){
//					boolean success = job2.printPage(webView);
//					if (success) {
//						job2.endJob();
//					}
//				}
//			}
			RefrNtObj();
			// Заново заполнить страницу
			Init();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Print(ActionEvent event) {
		try {
			// Print(PRINTER_ID);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void RefrNtObj() {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_DOC where id = ?");
			prepStmt.setLong(1, NT_DOC.getID());
			ResultSet rs = prepStmt.executeQuery();
			V_NT_DOC list = null;
			while (rs.next()) {
				list = new V_NT_DOC();
				if (rs.getClob("HTML_DOCUMENT") != null) {
					list.setHTML_DOCUMENT(new ConvConst().ClobToString(rs.getClob("HTML_DOCUMENT")));
				}
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setID(rs.getLong("ID"));
				list.setOPER(rs.getString("OPER"));
				list.setNOTARY(rs.getLong("NOTARY"));
				list.setNT_TYPE(rs.getLong("NT_TYPE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setTYPE_NAME(rs.getString("TYPE_NAME"));
				list.setTYPE_NODE(rs.getString("TYPE_NODE"));
			}
			prepStmt.close();
			rs.close();
			NT_DOC = list;
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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
				PreparedStatement prp = conn.prepareStatement(
						DbUtil.Sql_From_Prop("/notary/doc/html/controller/Sql.properties", "EditDocListValWithLocPrm"));
				prp.setString(1, id);
				prp.setLong(2, val.getID());
				prp.setString(3, id);
				prp.setLong(4, val.getID());
				prp.setLong(5, NT_DOC.getID());
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
					list.setIS_LOC(rs.getString("IS_LOC"));
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
										if (list.getPRM_FOR_PRM_SQL() != null
												&& list.getPRM_FOR_PRM_SQL().length() > 10) {
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

//	@FXML
//	private ComboBox<String> PRINTER_ID;

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

				Scene scene = new Scene(root);
//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);

				stage.setScene(scene);
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
			DbUtil.Log_Error(e);
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
//						FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.LIST_ALT);
//						icon.setFontSmoothingType(FontSmoothingType.LCD);
//						icon.setSize("18");
//						Button myButton = new Button("Добавить параметр", icon);
//						myButton.setId("MJAddParam");
//
//						bar.getItems().add(myButton);
//						myButton.setOnAction(new EventHandler<ActionEvent>() {
//							@Override
//							public void handle(ActionEvent arg0) {
//								AddParam();
//							}
//						});
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
			Reload();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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
	@SuppressWarnings("rawtypes")
	TreeItem roots = new TreeItem<>("Root");

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

				// System.out.println(JsonStr);

				roots = new TreeItem<>("Root");
				Map<Long, TreeItem<NT_TEMP_LIST_PARAM>> itemById = new HashMap<>();
				Map<Long, Long> parents = new HashMap<>();

				PreparedStatement prp = conn.prepareStatement(
						DbUtil.Sql_From_Prop("/notary/doc/html/controller/Sql.properties", "AddParamForDoc"));
				Clob clob = conn.createClob();
				clob.setString(1, JsonStr.trim());
				prp.setLong(1, val.getID());
				prp.setClob(2, clob);
				prp.setLong(3, val.getID());
				prp.setLong(4, NT_DOC.getID());
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
			DbUtil.Log_Error(e);
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

	public NT_TEMP_LIST_PARAM prm;

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

	public static String br2nl(String html) {
		if (html == null)
			return html;
		Document document = Jsoup.parse(html);
		document.outputSettings(new Document.OutputSettings().prettyPrint(false));// makes html() preserve linebreaks
																					// and spacing
		document.select("br").append("\\n");
		// document.select("p").prepend("\\n\\n");
		String s = document.html().replaceAll("\\\\n", "\n").replace("&nbsp;", "");
		return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
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

	private void tblManage() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) DelSelType.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/doc/html/view/TableManage.fxml"));

			TableManage controller = new TableManage();
			loader.setController(controller);

			Parent root = loader.load();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Редактор таблиц");
			stage.initOwner(stage_);
			stage.setResizable(true);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getHTML() != null) {
			
						String content = controller.getHTML();
					    //content = content.replace("'", "\\'");
					    //content = content.replace("class=\"source-tableeditor\"", "");
					    content = content.replace(System.getProperty("line.separator"), "\\n");
					    content = content.replace("\n", "\\n");
					    content = content.replace("\r", "\\n");
					    
					    String js = 
								 "function pasteHtmlAtCaret(html) {\n"
								 + "    var sel, range;\n"
								 + "    if (window.getSelection) {\n"
								 + "        // IE9 and non-IE\n"
								 + "        sel = window.getSelection();\n"
								 + "        if (sel.getRangeAt && sel.rangeCount) {\n"
								 + "            range = sel.getRangeAt(0);\n"
								 + "            range.deleteContents();\n"
								 + "\n"
								 + "            // Range.createContextualFragment() would be useful here but is\n"
								 + "            // non-standard and not supported in all browsers (IE9, for one)\n"
								 + "            var el = document.createElement(\"div\");\n"
								 + "            el.innerHTML = html;\n"
								 + "            var frag = document.createDocumentFragment(), node, lastNode;\n"
								 + "            while ( (node = el.firstChild) ) {\n"
								 + "                lastNode = frag.appendChild(node);\n"
								 + "            }\n"
								 + "            range.insertNode(frag);\n"
								 + "            \n"
								 + "            // Preserve the selection\n"
								 + "            if (lastNode) {\n"
								 + "                range = range.cloneRange();\n"
								 + "                range.setStartAfter(lastNode);\n"
								 + "                range.collapse(true);\n"
								 + "                sel.removeAllRanges();\n"
								 + "                sel.addRange(range);\n"
								 + "            }\n"
								 + "        }\n"
								 + "    } else if (document.selection && document.selection.type != \"Control\") {\n"
								 + "        // IE < 9\n"
								 + "        document.selection.createRange().pasteHTML(html);\n"
								 + "    }\n"
								 + "}\n"
								 + "pasteHtmlAtCaret('"+content+"');";
					    
					    System.out.println(js);
					    
						WebView webView = (WebView) HtmlEditor.lookup("WebView");
						webView.getEngine().executeScript(js);
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void Init() {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				webView.setPrefHeight(5000);
//				Stage stage = (Stage) TYPE_NAME.getScene().getWindow();
//				
//				webView.prefHeightProperty().bind(stage.heightProperty());
//				webView.prefWidthProperty().bind(stage.widthProperty());

//				StackPane stackPane = new StackPane();
//				stackPane.getChildren().add(webview);

				final WebEngine webEngine = webView.getEngine();
				final JsToJava jstojava = new JsToJava();
				// Запишем в файл
				{
					Writer out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(System.getenv("MJ_PATH") + "HTML/EDIT_HTML.html"),
							StandardCharsets.UTF_8));
					out.write(NT_DOC.getHTML_DOCUMENT());
					out.close();
				}
				URL url = new File(System.getenv("MJ_PATH") + "HTML/EDIT_HTML.html").toURI().toURL();
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
							// Оформление
							{
								Node node = HtmlEditor.lookup(".top-toolbar");
								Node NodeNottom = HtmlEditor.lookup(".bottom-toolbar");
								if (node instanceof ToolBar) {
									boolean check = true;
									ToolBar bar = (ToolBar) node;
									ToolBar BarBottom = (ToolBar) NodeNottom;
									ObservableList<Node> list = bar.getItems();
									for (Node item : list) {
										if (item.getId() != null && (item.getId().equals("ViewParams")
												| item.getId().equals("HideParams"))) {
											check = false;
											break;
										}
									}

									///////
									hideImageNodesMatching(node, Pattern.compile(".*(Color).*"), 0);
									///////
									if (check) {
										// spell checker
										{
											FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FONT);
											icon.setFontSmoothingType(FontSmoothingType.LCD);
											icon.setSize("18");
											Button myButton = new Button("", icon);
											myButton.setId("TableAdd");
											myButton.setTooltip(new Tooltip("Проверка правописания"));

											BarBottom.getItems().add(myButton);
											myButton.setOnAction(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent arg0) {
													///
//													HTMLEditor editor = HtmlEditor;
													//
													WebView webView = (WebView) HtmlEditor.lookup("WebView");
													webView.getEngine()
															.executeScript(DbUtil.Sql_From_Prop(
																	"/notary/doc/html/controller/Sql.properties",
																	"HTMLInputToSpan"));

													///
													String html = (String) webView.getEngine()
															.executeScript("document.documentElement.outerHTML");
													String text = br2nl(html);
													///
													SpellCheckExampleUi ui = new SpellCheckExampleUi();
													ui.getTextComponent().setText(text);

													SpellChecker.setUserDictionaryProvider(new FileUserDictionary());

													SpellChecker.registerDictionaries(
															JorthoExample.class.getResource("/dictionary_ru.ortho"),
															"ru");
													SpellChecker.register(ui.getTextComponent());

													SpellCheckerOptions sco = new SpellCheckerOptions();
													sco.setCaseSensitive(true);
													sco.setSuggestionsLimitMenu(15);

													JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
													ui.getTextComponent().setComponentPopupMenu(popup);

													ui.showUI();
													//
													RefrNtObj();
													Init();
												}
											});
										}
										// table
										{
											FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TABLE);
											icon.setFontSmoothingType(FontSmoothingType.LCD);
											icon.setSize("18");
											Button myButton = new Button("", icon);
											myButton.setId("TableAdd");
											myButton.setTooltip(new Tooltip("Таблица"));

											BarBottom.getItems().add(myButton);
											myButton.setOnAction(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent arg0) {
													tblManage();
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
											myButton.setTooltip(new Tooltip("Показать/спрятать параметры"));

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

//										// printers
//										{
//											FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PRINT);
//											icon.setFontSmoothingType(FontSmoothingType.LCD);
//											icon.setSize("18");
//											Separator sep = new Separator();
//											ComboBox<String> printer = new ComboBox<String>();
//											printer.setId("ListPrinters");
//											PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null,
//													null);
//
//											PrintService service = PrintServiceLookup.lookupDefaultPrintService();
//											for (PrintService printers : printServices) {
//												printer.getItems().add(printers.getName());
//											}
//											printer.getSelectionModel().select(service.getName());
//
//											printer.setTooltip(new Tooltip("Список принтеров"));
//
//											bar.getItems().add(sep);
//											bar.getItems().add(printer);
//										}

										// print
										{
											FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PRINT);
											icon.setFontSmoothingType(FontSmoothingType.LCD);
											icon.setSize("18");
											Separator sep = new Separator();
											Button myButton = new Button("", icon);
											myButton.setId("Print");
											myButton.setTooltip(new Tooltip("Печать"));

											bar.getItems().add(sep);

											bar.getItems().add(myButton);
											myButton.setOnAction(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent arg0) {
//													ComboBox<String> printer = null;
//													for (Node item : list) {
//														if (item.getId() != null
//																&& item.getId().equals("ListPrinters")) {
//															printer = (ComboBox<String>) item;
//															break;
//														}
//													}
													Print();
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
							// ________________
							// При открытии
							try {
								// Сами параметры
								{
									PreparedStatement stsmt = conn.prepareStatement(DbUtil.Sql_From_Prop(
											"/notary/doc/html/controller/Sql.properties", "EditNtDocPrmVals"));
									stsmt.setLong(1, NT_DOC.getID());
									stsmt.setLong(2, NT_DOC.getID());
									ResultSet rs = stsmt.executeQuery();
									while (rs.next()) {
										if (rs.getString("PRM_NAME") != null) {
											if (rs.getLong("PRM_TYPE") == 1) {
												// Получение буквенного значения
												String SelVal = "";
												{
													PreparedStatement prp = conn.prepareStatement(
															rs.getString("PRM_SQL") + " where code = ?");
													prp.setLong(1, Long.valueOf(rs.getString("VAL_NT_VALUE")));
													ResultSet rs_ = prp.executeQuery();
													if (rs_.next()) {
														SelVal = rs_.getString("name");
													}
													rs_.close();
													prp.close();
												}
												if (rs.getString("IS_LOC").equals("Y")) {
													System.out.println(
															"~~~~~~~~~~~~~~~~~~~~~IS_LOC~" + rs.getString("IS_LOC"));
												}
												// Если падеж не пуст
												if (rs.getString("PDJ_NAME") != null) {
													String json = (String) webView.getEngine()
															.executeScript("writeJSONfile()");
													if (json.contains(rs.getString("PRM_NAME").toLowerCase())) {
														// Получить измененный падеж
														String FioPod = (String) webView.getEngine()
																.executeScript("Padej('" + SelVal + "','"
																		+ rs.getString("PDJ_NAME") + "')");
														webView.getEngine().executeScript("SetValue('"
																+ rs.getString("PRM_NAME") + "','" + FioPod + "')");
													}
												} else {
													String json = (String) webView.getEngine()
															.executeScript("writeJSONfile()");
													if (json.contains(rs.getString("PRM_NAME").toLowerCase())) {
														webView.getEngine()
																.executeScript("SetValue('"
																		+ rs.getString("PRM_NAME").toLowerCase() + "','"
																		+ SelVal + "');");
													}
												}
											} else {
												String json = (String) webView.getEngine()
														.executeScript("writeJSONfile()");
												if (json.contains(rs.getString("PRM_NAME").toLowerCase())) {
													webView.getEngine()
															.executeScript("SetValue('"
																	+ rs.getString("PRM_NAME").toLowerCase() + "','"
																	+ rs.getString("VAL_NT_VALUE") + "');");
												}
											}
										}
									}
									stsmt.close();
									rs.close();
								}
								Platform.runLater(() -> {
									fillTree();
								});
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
				Writer out = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(System.getenv("MJ_PATH") + "HTML/EDIT_HTML.html"),
								StandardCharsets.UTF_8));
				out.write(html);
				out.close();
			}
			URL url = new File(System.getenv("MJ_PATH") + "HTML/EDIT_HTML.html").toURI().toURL();
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
				final WebEngine webEngine = webView.getEngine();
				final JsToJava jstojava = new JsToJava();
				// Запишем в файл
				{
					Writer out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(System.getenv("MJ_PATH") + "HTML/EDIT_HTML.html"),
							StandardCharsets.UTF_8));
					out.write(val.getHTML_TEMP());
					out.close();
				}

				URL url = new File(System.getenv("MJ_PATH") + "HTML/EDIT_HTML.html").toURI().toURL();
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
								// Fill
								fillTree();
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

	void Save(Boolean OnPrint) {
		try {
			V_NT_TEMP_LIST val = TYPE_NAME.getSelectionModel().getSelectedItem();
			if (val != null) {
				WebView webView = (WebView) HtmlEditor.lookup("WebView");
				// Получить поля из страницы
				String json = (String) webView.getEngine().executeScript("writeJSONfile()");
				// System.out.println(json);
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
					// System.out.print(KeyValue.trim());
				}
				// Локальные параметры
				String KeyValueLoc = "";
				{
					PreparedStatement prp = conn.prepareStatement(
							"select * from NT_TEMP_LIST_PARAM_DOC t where PRM_TMP_ID = ? AND DOC_ID = ?");
					prp.setLong(1, val.getID());
					prp.setLong(2, NT_DOC.getID());
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
									KeyValueLoc = KeyValueLoc + rs.getString("PRM_ID") + "|~|~|" + "\r\n";
								} // Иначе
								else if (values != null) {
									KeyValueLoc = KeyValueLoc + rs.getString("PRM_ID") + "|~|~|" + values + "\r\n";
								}
							} // Если параметр отсутствует на странице
							else {
								KeyValueLoc = KeyValueLoc + rs.getString("PRM_ID") + "|~|~|" + "\r\n";
							}
						} // Если тип параметра не список
						else {
							// Если параметр присутствует на странице
							if (json.contains(rs.getString("PRM_NAME"))) {
								KeyValueLoc = KeyValueLoc + rs.getString("PRM_ID") + "|~|~|"
										+ (String) webView.getEngine()
												.executeScript("ReturnValue('" + rs.getString("PRM_NAME") + "')")
										+ "\r\n";
							} else {
								KeyValueLoc = KeyValueLoc + rs.getString("PRM_ID") + "|~|~|" + "\r\n";
							}
						}
					}
					prp.close();
					rs.close();
					System.out.print("~~~~~~loc_prm~~~~~\r\n" + KeyValueLoc.trim());
				}

				CallableStatement cls = conn.prepareCall("{call NT_PKG.EDIT_DOC_HTML(?,?,?,?,?,?)}");
				cls.registerOutParameter(1, Types.VARCHAR);
				cls.setLong(2, NT_DOC.getID());
				Clob clob = conn.createClob();
				clob.setString(1, KeyValue.trim());
				cls.setClob(3, clob);
				cls.setLong(4, val.getID());
				Clob PAGE = conn.createClob();
				PAGE.setString(1, (String) webView.getEngine().executeScript("document.documentElement.outerHTML"));
				cls.setClob(5, PAGE);
				Clob clob_loc = conn.createClob();
				clob_loc.setString(1, KeyValueLoc.trim());
				cls.setClob(6, clob_loc);
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
					setStatus(true);
					if (!OnPrint) {
						onclose();
					}
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

	@FXML
	void OK(ActionEvent event) {
		try {
			Save(false);
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

	public void setConn(Connection conn, V_NT_DOC val) {
		this.conn = conn;
		this.NT_DOC = val;
	}

	V_NT_DOC NT_DOC;

	private void convert_TYPE_NAME(ComboBox<V_NT_TEMP_LIST> cmbbx) {
		cmbbx.setConverter(new StringConverter<V_NT_TEMP_LIST>() {
			@Override
			public String toString(V_NT_TEMP_LIST product) {
				return product != null ? product.getNAMES() : "";
			}

			@Override
			public V_NT_TEMP_LIST fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getNAMES().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	// limits the fonts a user can select from in the html editor.
	private static final List<String> limitedFonts = FXCollections.observableArrayList("Times New Roman","Arial");

	@FXML
	void PrintScan(ActionEvent event) {
		try {
			NT_SCANS val = NT_SCANS.getSelectionModel().getSelectedItem();
			if (val != null) {
				PreparedStatement prp = conn.prepareStatement(
						"select sc_file ,SC_TYPE,substr(SC_FILE_NAME, 1, instr(SC_FILE_NAME, '.') - 1) fname from nt_scans where sc_id=?");
				prp.setLong(1, val.getSC_ID());
				ResultSet rs = prp.executeQuery();
				String file_format = "";
				String fname = "";
				Blob blob = null;
				if (rs.next()) {
					blob = rs.getBlob("sc_file");
					file_format = rs.getString("SC_TYPE");
					fname = rs.getString("fname");
				}
				prp.close();
				rs.close();
				// ---------------------
				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);

				// InputStream targetStream = new ByteArrayInputStream(blobAsBytes);

				File tempFile = File.createTempFile(fname, "." + file_format,
						new File(System.getenv("MJ_PATH") + "OutReports"));
				OutputStream outStream = new FileOutputStream(tempFile);
				outStream.write(blobAsBytes);

				tempFile.deleteOnExit();
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(tempFile);
				}
				outStream.close();
				blob.free();

//				// build a component controller
//				SwingController controller = new SwingController();
//				SwingViewBuilder factory = new SwingViewBuilder(controller);
//				JPanel viewerComponentPanel = factory.buildViewerPanel();
//				// add interactive mouse link annotation support via callback
//				controller.getDocumentViewController().setAnnotationCallback(
//						new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
//				JFrame applicationFrame = new JFrame();
//				// applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				applicationFrame.getContentPane().add(viewerComponentPanel);
//				// Now that the GUI is all in place, we can try openning a PDF
//				// controller.openDocument(filename);
//				// controller.openDocument(arg0, arg1, arg2);
//				controller.openDocument(blobAsBytes, 0, blobAsBytes.length, "", "");
//				controller.isDocumentViewMode(0);
//				// show the component
//				applicationFrame.pack();
//				applicationFrame.setVisible(true);

			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void DeleteScan(ActionEvent event) {
		try {
			NT_SCANS val = NT_SCANS.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = (Stage) NT_SCANS.getScene().getWindow();
				Label alert = new Label("Удалить запись?");
				alert.setLayoutX(75.0);
				alert.setLayoutY(11.0);
				alert.setPrefHeight(17.0);

				Button no = new Button();
				no.setText("Нет");
				no.setLayoutX(111.0);
				no.setLayoutY(56.0);
				no.setPrefWidth(72.0);
				no.setPrefHeight(21.0);

				Button yes = new Button();
				yes.setText("Да");
				yes.setLayoutX(14.0);
				yes.setLayoutY(56.0);
				yes.setPrefWidth(72.0);
				yes.setPrefHeight(21.0);

				AnchorPane yn = new AnchorPane();
				yn.getChildren().add(alert);
				yn.getChildren().add(no);
				yn.getChildren().add(yes);
				Scene ynScene = new Scene(yn, 250, 100);
				Stage newWindow_yn = new Stage();
				no.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						newWindow_yn.close();
					}
				});
				yes.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							PreparedStatement prp = conn.prepareStatement("delete from nt_scans where sc_id = ?");
							prp.setLong(1, val.getSC_ID());
							prp.executeUpdate();
							prp.close();
							//
							conn.commit();
							InitScans();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DbUtil.Log_Error(e1);
							}
							DbUtil.Log_Error(e);
						}
						newWindow_yn.close();
					}
				});
				newWindow_yn.setTitle("Внимание");
				newWindow_yn.setScene(ynScene);
				newWindow_yn.initModality(Modality.WINDOW_MODAL);
				newWindow_yn.initOwner(stage);
				newWindow_yn.setResizable(false);
				newWindow_yn.getIcons().add(new Image("/icon.png"));
				newWindow_yn.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void EditScan(ActionEvent event) {
		try {
			NT_SCANS val = NT_SCANS.getSelectionModel().getSelectedItem();
			if (val != null) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Выбрать файл");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF документ", "*.pdf"));
				File file = fileChooser.showOpenDialog(null);
				if (file != null) {
					String name = file.getName();
					String paths = file.getAbsolutePath();
					String ext = FilenameUtils.getExtension(file.getAbsolutePath());
					System.out.println(ext);
					// получить файл
					byte[] bArray = null;
					Path path = Paths.get(paths);
					bArray = java.nio.file.Files.readAllBytes(path);
					InputStream is = new ByteArrayInputStream(bArray);
					// Вставить
					PreparedStatement prp = conn.prepareStatement(
							"update NT_SCANS " + " set SC_FILE = ?, SC_FILE_NAME=?, SC_TYPE = ? where sc_id = ?");
					prp.setBlob(1, is, bArray.length);
					prp.setString(2, name);
					prp.setString(3, ext);
					prp.setLong(4, val.getSC_ID());
					prp.executeUpdate();
					prp.close();
					// Сохраним транзакцию
					conn.commit();
					// Обновим
					InitScans();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void AddScan(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбрать файл");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF документ", "*.pdf"),
					new ExtensionFilter("JPEG документ", "*.jpeg"), new ExtensionFilter("JPG документ", "*.jpg"));
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				String name = file.getName();
				String paths = file.getAbsolutePath();
				String ext = FilenameUtils.getExtension(file.getAbsolutePath());
				System.out.println(ext);
				// получить файл
				byte[] bArray = null;
				Path path = Paths.get(paths);
				bArray = java.nio.file.Files.readAllBytes(path);
				InputStream is = new ByteArrayInputStream(bArray);
				// Вставить
				PreparedStatement prp = conn.prepareStatement(
						"insert into NT_SCANS " + "(SC_DOC,SC_FILE,SC_FILE_NAME,SC_TYPE) " + "values " + "(?,?,?,?)");
				prp.setLong(1, NT_DOC.getID());
				prp.setBlob(2, is, bArray.length);
				prp.setString(3, name);
				prp.setString(4, ext);
				prp.executeUpdate();
				prp.close();
				// Сохраним транзакцию
				conn.commit();
				// Обновим
				InitScans();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			DelSelType.setDisable(true);
			TYPE_NAME.setDisable(true);
			EditLocalParam.setVisible(false);
			DeleteLocalParam.setVisible(false);

			param.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<NT_TEMP_LIST_PARAM> val = param.getSelectionModel().getSelectedItem();
				if (val != null) {
					if (val.getValue().getIS_LOC().equals("Y")) {
						EditLocalParam.setVisible(true);
						DeleteLocalParam.setVisible(true);
					} else if (val.getValue().getIS_LOC().equals("N")) {
						EditLocalParam.setVisible(false);
						DeleteLocalParam.setVisible(false);
					}
				}
			});

			LocalParams.setVisible(true);
			fillTree();
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

			new ConvConst().TableColumnDateTime(SC_DATE);

			SC_TYPE.setCellValueFactory(cellData -> cellData.getValue().SC_TYPEProperty());
			SC_FILE_NAME.setCellValueFactory(cellData -> cellData.getValue().SC_FILE_NAMEProperty());
			SC_OPER.setCellValueFactory(cellData -> cellData.getValue().SC_OPERProperty());
			SC_DATE.setCellValueFactory(cellData -> ((NT_SCANS) cellData.getValue()).SC_DATEProperty());

//			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//
//			PrintService service = PrintServiceLookup.lookupDefaultPrintService();
//			for (PrintService printer : printServices) {
//				PRINTER_ID.getItems().add(printer.getName());
//			}
//			PRINTER_ID.getSelectionModel().select(service.getName());

//			HtmlEditor.getStyleClass().add("mylistview");
//			HtmlEditor.getStylesheets().add("/ScrPane.css");
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
			if (NT_DOC.getNT_TYPE() != null) {
				for (V_NT_TEMP_LIST ld : TYPE_NAME.getItems()) {
					if (NT_DOC.getNT_TYPE().equals(ld.getID())) {
						TYPE_NAME.getSelectionModel().select(ld);
						break;
					}
				}
			}
			Platform.runLater(() -> {
				Init();
			});
			InitScans();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public static Node componentsPane;

	@FXML
	private SplitPane MainSplitPane;
}
