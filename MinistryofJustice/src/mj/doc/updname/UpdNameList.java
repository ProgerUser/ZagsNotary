package mj.doc.updname;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.DateColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.TitledBorderPane;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.SqlMap;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class UpdNameList {

	@FXML
	private DatePicker DT1;

	@FXML
	private DatePicker DT2;

	@FXML
	private BorderPane ROOT;

	@FXML
	private XTableView<UPDATE_NAME> UPDATE_NAME;

	@FXML
	private XTableColumn<UPDATE_NAME, String> NEW_FIRSTNAME;

	@FXML
	private XTableColumn<UPDATE_NAME, String> DOC_NUMBER;
	
	@FXML
	private XTableColumn<UPDATE_NAME, String> OLD_LASTNAME;

	@FXML
	private XTableColumn<UPDATE_NAME, String> NEW_MIDDLNAME;

	@FXML
	private ProgressIndicator PB;

	@FXML
	private XTableColumn<UPDATE_NAME, String> NEW_LASTNAME;

	@FXML
	private XTableColumn<UPDATE_NAME, Long> ID;

	@FXML
	private XTableColumn<UPDATE_NAME, String> OLD_FIRSTNAME;

	// @FXML
	// private XTableColumn<UPDATE_NAME, LocalDateTime> DOC_DATE;

	@FXML
	private XTableColumn<UPDATE_NAME, String> OLD_MIDDLNAME;

	String UpdNmXml;

	/**
	 * Сравнение данных
	 * 
	 * @return
	 */
	Long CompareBeforeClose(Long docid) {
		Long ret = 0l;
		try {
			Clob lob = conn.createClob();
			lob.setString(1, UpdNmXml);
			CallableStatement callStmt = conn.prepareCall("{ call UpdName.CompareXmls(?,?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.setClob(2, lob);
			callStmt.registerOutParameter(3, Types.VARCHAR);
			callStmt.registerOutParameter(4, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(3) == null) {
				ret = callStmt.getLong(4);
				System.out.println("ret=" + callStmt.getLong(4));
			} else {
				Msg.Message(callStmt.getString(3));
				Main.logger.error(callStmt.getString(6) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

		return ret;
	}

	/**
	 * Возврат XML файлов для сравнения
	 */
	void XmlsForCompare(Long docid) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call UpdName.RetXmls(?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.registerOutParameter(2, Types.VARCHAR);
			callStmt.registerOutParameter(3, Types.CLOB);
			callStmt.execute();
			if (callStmt.getString(2) == null) {
				// clob
				Clob retcl = callStmt.getClob(3);
				// chars
				char char_xmls[] = new char[(int) retcl.length()];
				// read
				Reader r1 = retcl.getCharacterStream();
				r1.read(char_xmls);
				// strings
				UpdNmXml = new String(char_xmls);
				// System.out.println(UpdNmXml);
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void Add() {
		try {

			if (DbUtil.Odb_Action(107l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) UPDATE_NAME.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/updname/IUUpdName.fxml"));

			AddUpdName controller = new AddUpdName();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить новую запись");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						Refresh();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void Delete() {
		try {
			if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				Main.logger = Logger.getLogger(getClass());

				if (DbUtil.Odb_Action(109l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				Stage stage = (Stage) UPDATE_NAME.getScene().getWindow();
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
							PreparedStatement delete = conn
									.prepareStatement("declare " + "pragma autonomous_transaction;" + "begin "
											+ " delete from UPDATE_NAME where ID = ?;" + "commit;" + "end;");
							UPDATE_NAME cl = UPDATE_NAME.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getID());
							delete.executeUpdate();
							delete.close();
							Refresh();
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

	boolean isopen = false;

	UPDATE_NAME Initialize2(Long docid) {
		UPDATE_NAME list = null;
		try {
			String selectStmt = "select * from VUPDATE_NAME t\r\n where ID = ?";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<UPDATE_NAME> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new UPDATE_NAME();

				list.setID(rs.getLong("ID"));
				list.setOPER(rs.getString("OPER"));
				list.setBRN_ACT_ID(rs.getLong("BRN_ACT_ID"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
				list.setCUSID(rs.getLong("CUSID"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setFIO(rs.getString("FIO"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setTM$DOC_DATE((rs.getDate("TM$DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_DATE")), formatterwt)
						: null);
				list.setOLD_LASTNAME_AB(rs.getString("OLD_LASTNAME_AB"));
				list.setOLD_FIRSTNAME_AB(rs.getString("OLD_FIRSTNAME_AB"));
				list.setOLD_MIDDLNAME_AB(rs.getString("OLD_MIDDLNAME_AB"));
				list.setNEW_LASTNAME_AB(rs.getString("NEW_LASTNAME_AB"));
				list.setNEW_FIRSTNAME_AB(rs.getString("NEW_FIRSTNAME_AB"));
				list.setNEW_MIDDLNAME_AB(rs.getString("NEW_MIDDLNAME_AB"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return list;
	}

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.from = 1;
		this.conn.setAutoCommit(false);
	}

	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {

				if (DbUtil.Odb_Action(108l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement selforupd = conn
						.prepareStatement("select * from UPDATE_NAME where  ID = ? for update nowait");
				UPDATE_NAME cl = Initialize2(docid);
				selforupd.setLong(1, cl.getID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DbUtil.Lock_Row(docid, "UPDATE_NAME",conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// Заполнить xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/updname/IUUpdName.fxml"));

						EditUpdName controller = new EditUpdName();
						controller.setId(cl.getID());
						controller.setConn(conn, cl);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: ");
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									// обновить без сохранения
									controller.SaveWtOtCommit();
									// Если нажали сохранить
									if (controller.getStatus()) {
										conn.commit();
										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(docid, "UPDATE_NAME",conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
										isopen = false;
									} // Если нажали "X" или "Cancel" и до этого что-то меняли
									else if (!controller.getStatus() & CompareBeforeClose(docid) == 1) {
										/**
										 * Проверка выхода без сохранения
										 */
										Stage stage = stage_;
										Label alert = new Label("Закрыть форму без сохранения?");
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
										Scene ynScene = new Scene(yn, 300, 100);
										Stage newWindow_yn = new Stage();
										no.setOnAction(new EventHandler<ActionEvent>() {
											public void handle(ActionEvent event) {
												newWindow_yn.close();
												paramT.consume();
											}
										});
										yes.setOnAction(new EventHandler<ActionEvent>() {
											public void handle(ActionEvent event) {
												try {
													conn.rollback();
												} catch (SQLException e) {
													e.printStackTrace();
												}
												newWindow_yn.close();
												// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
												String lock = DbUtil.Lock_Row_Delete(docid, "UPDATE_NAME",conn);
												if (lock != null) {// if error add row
													Msg.Message(lock);
												}
												isopen = false;
											}
										});
										newWindow_yn.setTitle("Внимание");
										newWindow_yn.setScene(ynScene);
										newWindow_yn.initModality(Modality.WINDOW_MODAL);
										newWindow_yn.initOwner(stage);
										newWindow_yn.setResizable(false);
										newWindow_yn.getIcons().add(new Image("/icon.png"));
										newWindow_yn.showAndWait();
									} // Если нажали "X" или "Cancel" и до этого ничего не меняли
									else if (!controller.getStatus() & CompareBeforeClose(docid) == 0) {
										conn.rollback();
										isopen = false;
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"
										String lock = DbUtil.Lock_Row_Delete(docid, "UPDATE_NAME",conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
									}
								} catch (SQLException e) {
									DbUtil.Log_Error(e);
								}
							}
						});
						stage.show();
						isopen = true;
					}
				} catch (SQLException e) {
					if (e.getErrorCode() == 54) {
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(docid, "UPDATE_NAME"));
						DbUtil.Log_Error(e);
					} else {
						DbUtil.Log_Error(e);
					}
				}

			} else {
				Msg.Message("Форма редактирования уже открыта!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	void PrintOld() {
		try {
			if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				String output_DOCX = "D:/output.docx";
				Main.logger = Logger.getLogger(getClass());
				InputStream docs = getClass().getClassLoader().getResourceAsStream("upd_nm.docx");
				WordprocessingMLPackage wordMLPackage = Docx4J.load(docs);
				InputStream xmlStream = new ByteArrayInputStream(XmlReport().getBytes(Charset.forName("UTF-8")));
				Docx4J.bind(wordMLPackage, xmlStream,
						Docx4J.FLAG_BIND_INSERT_XML | Docx4J.FLAG_BIND_BIND_XML | Docx4J.FLAG_BIND_REMOVE_SDT);
				Docx4J.save(wordMLPackage, new File(output_DOCX), Docx4J.FLAG_NONE);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void Print() {
		try {
			if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {

					@Override
					public Object call() throws Exception {

						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/UPDATE_NAME.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));

						// preparing variables
						Variables variables = new Variables();
						// SqlMap sql = new SqlMap().load("/updname/sql.xml");
						// String readRecordSQL = sql.getSql("QueryForReport");
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from v_rep_update_name where ID = ?");
						prepStmt.setLong(1, UPDATE_NAME.getSelectionModel().getSelectedItem().getID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_UPDATE_NAME list = null;
						if (rs.next()) {
							list = new V_REP_UPDATE_NAME();
							list.setID(rs.getLong("ID"));
							list.setDCUSBIRTHDAY(rs.getString("DCUSBIRTHDAY"));
							list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
							list.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
							list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
							list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
							list.setADDRESS(rs.getString("ADDRESS"));
							list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
							list.setBR_ACT_ID(rs.getLong("BR_ACT_ID"));
							list.setBR_ACT_DATE(rs.getString("BR_ACT_DATE"));
							list.setCCUSPLACE_BIRTH(rs.getString("CCUSPLACE_BIRTH"));
							list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
							list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
							list.setSVID_SERIA(rs.getString("SVID_SERIA"));
							list.setNATIONALITY(rs.getString("NATIONALITY"));
							list.setCURDATE(rs.getString("CURDATE"));
							list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
							list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
						}
						prepStmt.close();
						rs.close();

						variables.addTextVariable(new TextVariable("#{DCUSBIRTHDAY}", list.getDCUSBIRTHDAY()));
						variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", list.getZAGS_NAME()));
						variables.addTextVariable(new TextVariable("#{COUNTRY_NAME}", list.getCOUNTRY_NAME()));
						variables.addTextVariable(new TextVariable("#{OLD_LASTNAME}", list.getOLD_LASTNAME()));
						variables.addTextVariable(new TextVariable("#{NEW_MIDDLNAME}", list.getNEW_MIDDLNAME()));
						variables.addTextVariable(new TextVariable("#{ADDRESS}", list.getADDRESS()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_ID}",
								(list.getBR_ACT_ID() != null) ? String.valueOf(list.getBR_ACT_ID()) : ""));
						variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", list.getBR_ACT_DATE()));
						variables.addTextVariable(new TextVariable("#{CCUSPLACE_BIRTH}", list.getCCUSPLACE_BIRTH()));
						variables.addTextVariable(new TextVariable("#{NEW_LASTNAME}", list.getNEW_LASTNAME()));
						variables.addTextVariable(new TextVariable("#{NEW_FIRSTNAME}", list.getNEW_FIRSTNAME()));
						variables.addTextVariable(new TextVariable("#{SVID_SERIA}", list.getSVID_SERIA()));
						variables.addTextVariable(new TextVariable("#{NATIONALITY}", list.getNATIONALITY()));
						variables.addTextVariable(new TextVariable("#{SVID_NUMBER}", list.getSVID_NUMBER()));
						variables.addTextVariable(new TextVariable("#{OLD_FIRSTNAME}", list.getOLD_FIRSTNAME()));
						variables.addTextVariable(new TextVariable("#{CURDATE}", list.getCURDATE()));
						variables.addTextVariable(new TextVariable("#{OLD_MIDDLNAME}", list.getOLD_MIDDLNAME()));
						variables.addTextVariable(
								new TextVariable("#{ID}", (list.getID() != null) ? String.valueOf(list.getID()) : ""));

						// find all variables satisfying the pattern #{...}
						// List<String> findVariables = docx.findVariables();

						// and display it
						// for (String var : findVariables) {
						// System.out.println("VARIABLE => " + var);
						// }

						// fill template
						docx.fillTemplate(variables);
						File tempFile = File.createTempFile("UPDATE_ABH_NAME", ".docx",
								new File(System.getenv("MJ_PATH") + "OutReports"));
						FileOutputStream str = new FileOutputStream(tempFile);
						docx.save(str);
						str.close();
						tempFile.deleteOnExit();
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(tempFile);
						}
						/*
						 * // save filled .docx file UUID uuid = UUID.randomUUID(); String filename =
						 * System.getenv("MJ_PATH") + "OutReports/UPDATE_NAME_" + uuid.toString() +
						 * ".docx"; docx.save(filename);
						 * 
						 * if (Desktop.isDesktopSupported()) { Desktop.getDesktop().open(new
						 * File(filename)); }
						 */
//						ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//						PdfOptions options = PdfOptions.create();
//						PdfConverter.getInstance().convert(docx.getXWPFDocument(), out, options);
//
//						byte[] xwpfDocumentBytes = out.toByteArray();
//
//						// build a component controller
//						SwingController controller = new SwingController();
//
//						SwingViewBuilder factory = new SwingViewBuilder(controller);
//
//						JPanel viewerComponentPanel = factory.buildViewerPanel();
//
//						// add interactive mouse link annotation support via callback
//						controller.getDocumentViewController().setAnnotationCallback(
//								new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
//
//						JFrame applicationFrame = new JFrame();
//						// applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//						applicationFrame.getContentPane().add(viewerComponentPanel);
//
//						// Now that the GUI is all in place, we can try openning a PDF
//						// controller.openDocument(filename);
//						// controller.openDocument(arg0, arg1, arg2);
//						controller.openDocument(xwpfDocumentBytes, 0, xwpfDocumentBytes.length, "", "");
//						controller.isDocumentViewMode(0);
//						// show the component
//						applicationFrame.pack();
//						applicationFrame.setVisible(true);
//						out.close();
						return null;
					}
				};
				task.setOnFailed(e -> Msg.Message(task.getException().getMessage()));
				task.setOnSucceeded(e -> BlockMain());
				exec.execute(task);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private Executor exec;

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	String XmlReport() {
		String ret = "";
		try {
			// xml part
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			// root element
			Element root = document.createElement("update_name");
			document.appendChild(root);

			// db qury
			SqlMap sql = new SqlMap().load("/updname/sql.xml");
			String readRecordSQL = sql.getSql("QueryForReport");
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, UPDATE_NAME.getSelectionModel().getSelectedItem().getID());
			ResultSet rs = prepStmt.executeQuery();
			V_REP_UPDATE_NAME list = null;
			if (rs.next()) {
				list = new V_REP_UPDATE_NAME();
				list.setID(rs.getLong("ID"));
				list.setDCUSBIRTHDAY(rs.getString("DCUSBIRTHDAY"));
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setADDRESS(rs.getString("ADDRESS"));
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setBR_ACT_ID(rs.getLong("BR_ACT_ID"));
				list.setBR_ACT_DATE(rs.getString("BR_ACT_DATE"));
				list.setCCUSPLACE_BIRTH(rs.getString("CCUSPLACE_BIRTH"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setNATIONALITY(rs.getString("NATIONALITY"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
			}
			prepStmt.close();
			rs.close();
			// ________________END_____________________________

			// _____start_create_xml____
			// doc element
			Element doc = document.createElement("doc");
			root.appendChild(doc);

			Element DCUSBIRTHDAY = document.createElement("DCUSBIRTHDAY");
			DCUSBIRTHDAY.appendChild(document.createTextNode(list.getDCUSBIRTHDAY()));
			doc.appendChild(DCUSBIRTHDAY);
			Element ZAGS_NAME = document.createElement("ZAGS_NAME");
			ZAGS_NAME.appendChild(document.createTextNode(list.getZAGS_NAME()));
			doc.appendChild(ZAGS_NAME);
			Element COUNTRY_NAME = document.createElement("COUNTRY_NAME");
			COUNTRY_NAME.appendChild(document.createTextNode(list.getCOUNTRY_NAME()));
			doc.appendChild(COUNTRY_NAME);
			Element OLD_LASTNAME = document.createElement("OLD_LASTNAME");
			OLD_LASTNAME.appendChild(document.createTextNode(list.getOLD_LASTNAME()));
			doc.appendChild(OLD_LASTNAME);
			Element NEW_MIDDLNAME = document.createElement("NEW_MIDDLNAME");
			NEW_MIDDLNAME.appendChild(document.createTextNode(list.getNEW_MIDDLNAME()));
			doc.appendChild(NEW_MIDDLNAME);
			Element ADDRESS = document.createElement("ADDRESS");
			ADDRESS.appendChild(document.createTextNode(list.getADDRESS()));
			doc.appendChild(ADDRESS);
			Element OLD_MIDDLNAME = document.createElement("OLD_MIDDLNAME");
			OLD_MIDDLNAME.appendChild(document.createTextNode(list.getOLD_MIDDLNAME()));
			doc.appendChild(OLD_MIDDLNAME);
			Element BR_ACT_ID = document.createElement("BR_ACT_ID");
			BR_ACT_ID.appendChild(
					document.createTextNode((list.getBR_ACT_ID() != 0) ? String.valueOf(list.getBR_ACT_ID()) : ""));
			doc.appendChild(BR_ACT_ID);
			Element BR_ACT_DATE = document.createElement("BR_ACT_DATE");
			BR_ACT_DATE.appendChild(document.createTextNode(list.getBR_ACT_DATE()));
			doc.appendChild(BR_ACT_DATE);
			Element CCUSPLACE_BIRTH = document.createElement("CCUSPLACE_BIRTH");
			CCUSPLACE_BIRTH.appendChild(document.createTextNode(list.getCCUSPLACE_BIRTH()));
			doc.appendChild(CCUSPLACE_BIRTH);
			Element NEW_LASTNAME = document.createElement("NEW_LASTNAME");
			NEW_LASTNAME.appendChild(document.createTextNode(list.getNEW_LASTNAME()));
			doc.appendChild(NEW_LASTNAME);
			Element NEW_FIRSTNAME = document.createElement("NEW_FIRSTNAME");
			CCUSPLACE_BIRTH.appendChild(document.createTextNode(list.getNEW_FIRSTNAME()));
			doc.appendChild(NEW_FIRSTNAME);
			Element SVID_SERIA = document.createElement("SVID_SERIA");
			SVID_SERIA.appendChild(document.createTextNode(list.getSVID_SERIA()));
			doc.appendChild(SVID_SERIA);
			Element NATIONALITY = document.createElement("NATIONALITY");
			NATIONALITY.appendChild(document.createTextNode(list.getNATIONALITY()));
			doc.appendChild(NATIONALITY);
			Element SVID_NUMBER = document.createElement("SVID_NUMBER");
			SVID_NUMBER.appendChild(document.createTextNode(list.getSVID_NUMBER()));
			doc.appendChild(SVID_NUMBER);
			Element OLD_FIRSTNAME = document.createElement("OLD_FIRSTNAME");
			OLD_FIRSTNAME.appendChild(document.createTextNode(list.getOLD_FIRSTNAME()));
			doc.appendChild(OLD_FIRSTNAME);
			// ________END___________________

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StringWriter sw = new StringWriter();
			StreamResult streamResult = new StreamResult(sw);
			transformer.transform(domSource, streamResult);

			// System.out.println(sw.toString());
			ret = sw.toString();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void Refresh() {
		try {
			String selectStmt = "select * from VUPDATE_NAME t\r\n" + ((getWhere() != null) ? getWhere() : " order by ID desc");

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<UPDATE_NAME> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				UPDATE_NAME list = new UPDATE_NAME();

				list.setID(rs.getLong("ID"));
				list.setOPER(rs.getString("OPER"));
				list.setBRN_ACT_ID(rs.getLong("BRN_ACT_ID"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
				list.setCUSID(rs.getLong("CUSID"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setFIO(rs.getString("FIO"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setTM$DOC_DATE((rs.getDate("TM$DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_DATE")), formatterwt)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setOLD_LASTNAME_AB(rs.getString("OLD_LASTNAME_AB"));
				list.setOLD_FIRSTNAME_AB(rs.getString("OLD_FIRSTNAME_AB"));
				list.setOLD_MIDDLNAME_AB(rs.getString("OLD_MIDDLNAME_AB"));
				list.setNEW_LASTNAME_AB(rs.getString("NEW_LASTNAME_AB"));
				list.setNEW_FIRSTNAME_AB(rs.getString("NEW_FIRSTNAME_AB"));
				list.setNEW_MIDDLNAME_AB(rs.getString("NEW_MIDDLNAME_AB"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			UPDATE_NAME.setItems(dlist);

			TableFilter<UPDATE_NAME> tableFilter = TableFilter.forTableView(UPDATE_NAME).apply();
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

	@FXML
	void CmRefresh(ActionEvent event) {
		Refresh();
	}

	@FXML
	void CmAdd(ActionEvent event) {
		Add();
	}

	@FXML
	void CmEdit(ActionEvent event) {
		if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(UPDATE_NAME.getSelectionModel().getSelectedItem().getID(), (Stage) UPDATE_NAME.getScene().getWindow());
		}
	}

	@FXML
	void CmPrint(ActionEvent event) {
		Print();
	}

	@FXML
	void CmDelete(ActionEvent event) {
		Delete();
	}

	@FXML
	void BtAdd(ActionEvent event) {
		Add();
	}

	@FXML
	void BtEdit(ActionEvent event) {
		if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(UPDATE_NAME.getSelectionModel().getSelectedItem().getID(), (Stage) UPDATE_NAME.getScene().getWindow());
		}
	}

	@FXML
	void BtDelete(ActionEvent event) {
		Delete();
	}
	
	@FXML
	void BtPrintBlank(ActionEvent event) {
		try {
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/UPDATE_NAME.pdf",
					System.getenv("MJ_PATH") + "/OutReports/UPDATE_NAME.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/UPDATE_NAME.pdf"));
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	
	@FXML
	void BtPrint(ActionEvent event) {
		Print();
	}

	Connection conn;

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

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	void CellDateFormatDT(TableColumn<UPDATE_NAME, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<UPDATE_NAME, LocalDateTime> cell = new TableCell<UPDATE_NAME, LocalDateTime>() {
				private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(format.format(item));
					}
				}
			};
			return cell;
		});
	}

	void CellDateFormatD(TableColumn<UPDATE_NAME, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<UPDATE_NAME, LocalDate> cell = new TableCell<UPDATE_NAME, LocalDate>() {
				private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						if (item != null) {
							setText(format.format(item));
						}
					}
				}
			};
			return cell;
		});
	}

	@FXML
	private XTableColumn<UPDATE_NAME, String> CR_TIME;

	@FXML
	private XTableColumn<UPDATE_NAME, String> OPER;

	@FXML
	private XTableColumn<UPDATE_NAME, LocalDate> CR_DATE;

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");

		CheckBox filterVisible = new CheckBox("Показать фильтр");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());

		CheckBox menuButtonVisible = new CheckBox("Показать кнопку меню");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());

		CheckBox firstFilterable = new CheckBox("Фильтруемый первый столбец");
		// XTableColumn<VCUS, Long> firstColumn = (XTableColumn<VCUS, Long>)
		// table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(ID.filterableProperty());

		CheckBox includeHidden = new CheckBox("Включить скрытые столбцы");
		includeHidden.selectedProperty().bindBidirectional(table.getFilterController().includeHiddenProperty());

		CheckBox andFilters = new CheckBox("Используйте операцию \"И\" для многоколоночного фильтра");
		andFilters.selectedProperty().bindBidirectional(table.getFilterController().andFiltersProperty());

		pane.getChildren().addAll(filterVisible, menuButtonVisible, firstFilterable, includeHidden, andFilters);

		TitledBorderPane p = new TitledBorderPane("Настройки", pane);
		p.getStyleClass().add("top-border-only");
		p.setStyle("-fx-border-insets: 10 0 0 0");
		return p;
	}
	
    @FXML
    private VBox VB;

    @FXML
    private TitledPane FILTER;

	public void manipulatePdf(String src, String dest) throws DocumentException, IOException, SQLException {
		if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			AcroFields fields = stamper.getAcroFields();
			// System.out.print(fields.getFields());
			PreparedStatement prp = conn.prepareStatement("select * from BLANK_UPDATE_NAME where ID = ?");
			prp.setLong(1, UPDATE_NAME.getSelectionModel().getSelectedItem().getID());
			ResultSet rs = prp.executeQuery();
			while(rs.next()) {
				fields.setField("Текст20", rs.getString("RU_LNAME"));
				fields.setField("Текст21", rs.getString("RU_FMNAME"));
				fields.setField("Текст40", rs.getString("YYYY_BIRTH"));
				fields.setField("Текст41", rs.getString("ZAGS_RUK"));
				fields.setField("Текст24", rs.getString("DD_BIRTH"));
				fields.setField("Текст25", rs.getString("RU_MM_BIRTH"));
				fields.setField("Текст22", rs.getString("RU_COUNTRY"));
				fields.setField("Текст23", rs.getString("RU_NAT"));
				fields.setField("Текст28", rs.getString("NEW_LASTNAME"));
				fields.setField("Текст29", rs.getString("RU_FMNAME_NEW"));
				fields.setField("Текст26", rs.getString("RU_BRTH_PLC"));
				fields.setField("Текст27", rs.getString("RU_CNTR_BRTH"));
				fields.setField("Текст4", rs.getString("ABH_COUNTRY"));
				fields.setField("Текст5", rs.getString("ABH_NAT"));
				fields.setField("Текст6", rs.getString("DD_BIRTH"));
				fields.setField("Текст7", rs.getString("ABH_MM_BIRTH"));
				fields.setField("Текст31", rs.getString("RU_MM"));
				fields.setField("Текст1", rs.getString("ABH_LNAME"));
				fields.setField("Текст10", rs.getString("NEW_LASTNAME_AB"));
				fields.setField("Текст32", rs.getString("RU_DD"));
				fields.setField("Текст3", rs.getString("ABH_FMNAME"));
				fields.setField("Текст30", rs.getString("RU_YYYY"));
				fields.setField("Текст13", rs.getString("RU_YYYY"));
				fields.setField("Текст35", rs.getString("ZAGS_ADR"));
				fields.setField("Текст14", rs.getString("RU_DD"));
				fields.setField("Текст36", "Республика Абхазия");
				fields.setField("Текст11", rs.getString("ABH_FMNAME_NEW"));
				fields.setField("Текст33", "-");
				fields.setField("Текст12", "-");
				fields.setField("Текст34", "ЗАГС");
				fields.setField("Текст17", rs.getString("ZAGS_ADR_ABH"));
				fields.setField("Текст39", rs.getString("RU_YYYY"));
				fields.setField("Текст18", rs.getString("RU_DD"));
				fields.setField("Текст15", rs.getString("ABH_MM"));
				fields.setField("Текст37", rs.getString("ZAGS_RUK_ABH"));
				fields.setField("Текст16", rs.getString("ZAGS_CITY_ABH"));
				fields.setField("Текст38", rs.getString("RU_MM"));
				fields.setField("Текст19", rs.getString("ABH_MM"));
				fields.setField("Текст8", rs.getString("YYYY_BIRTH"));
				fields.setField("Текст9", rs.getString("ABH_BRTH_PLC"));
			}
			prp.close();
			rs.close();
			// stamper.setFormFlattening(true);
			stamper.close();
			reader.close();
		}
	}
    
	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void initialize() {
		try {

			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});

			VB.getChildren().remove(FILTER);
			
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ROOT.setBottom(createOptionPane(UPDATE_NAME));
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));
			OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());

			NEW_FIRSTNAME.setColumnFilter(new PatternColumnFilter<>());
			OLD_LASTNAME.setColumnFilter(new PatternColumnFilter<>());
			NEW_MIDDLNAME.setColumnFilter(new PatternColumnFilter<>());
			NEW_LASTNAME.setColumnFilter(new PatternColumnFilter<>());
			OLD_FIRSTNAME.setColumnFilter(new PatternColumnFilter<>());
			OLD_MIDDLNAME.setColumnFilter(new PatternColumnFilter<>());
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			
			dbConnect();
			DbUtil.Run_Process(conn,getClass().getName());
			Refresh();
			/**
			 * Столбцы таблицы
			 */
			{
				OPER.setCellValueFactory(cellData -> cellData.getValue().OPERProperty());
				CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
				ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
				NEW_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().NEW_FIRSTNAMEProperty());
				OLD_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_LASTNAMEProperty());
				NEW_MIDDLNAME.setCellValueFactory(cellData -> cellData.getValue().NEW_MIDDLNAMEProperty());
				NEW_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().NEW_LASTNAMEProperty());
				OLD_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_FIRSTNAMEProperty());
				DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
				// DOC_DATE.setCellValueFactory(cellData ->
				// cellData.getValue().DOC_DATEProperty());
				OLD_MIDDLNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_MIDDLNAMEProperty());
			}

			// двойной щелчок
			UPDATE_NAME.setRowFactory(tv -> {
				TableRow<UPDATE_NAME> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите строку!");
						} else {
							Edit(UPDATE_NAME.getSelectionModel().getSelectedItem().getID(),
									(Stage) UPDATE_NAME.getScene().getWindow());
						}
					}
				});
				return row;
			});
			/**
			 * ФД
			 */
			{
				CellDateFormatD(CR_DATE);
			}
			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private StringProperty Where;

	public void setWhere(String value) {
		this.Where.set(value);
	}

	public String getWhere() {
		return this.Where.get();
	}

	public UpdNameList() {
		Main.logger = Logger.getLogger(getClass());
		this.Where = new SimpleStringProperty();
	}

}
