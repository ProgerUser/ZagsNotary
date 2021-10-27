package ru.psv.mj.zags.doc.updatenat;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

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
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import javafx.application.Platform;
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
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.www.pl.jsolve.Docx;
import ru.psv.mj.www.pl.jsolve.TextVariable;
import ru.psv.mj.www.pl.jsolve.VariablePattern;
import ru.psv.mj.www.pl.jsolve.Variables;

public class UpdNatList {

    @FXML
    private VBox VB;

    @FXML
    private TitledPane FILTER;
    
	@FXML
	private Button AddBtn;

	@FXML
	private Button AltPrint;

	@FXML
	private DatePicker DT1;

	@FXML
	private DatePicker DT2;

	@FXML
	private BorderPane ROOT;

	@FXML
	private ProgressIndicator PB;

	@FXML
	private XTableColumn<VUPD_NAT, String> DOC_NUMBER;
	
	@FXML
	private XTableView<VUPD_NAT> UPD_NAT;

	@FXML
	private XTableColumn<VUPD_NAT, String> NEW_NAT;

	@FXML
	private XTableColumn<VUPD_NAT, String> FIO;

	@FXML
	private XTableColumn<VUPD_NAT, String> CR_TIME;

	@FXML
	private XTableColumn<VUPD_NAT, String> OPER;

	@FXML
	private XTableColumn<VUPD_NAT, Long> ID;

	@FXML
	private XTableColumn<VUPD_NAT, LocalDate> CR_DATE;

	// @FXML
	// private XTableColumn<VUPD_NAT, LocalDateTime> DOC_DATE;

	@FXML
	private XTableColumn<VUPD_NAT, String> OLD_NAT;

	void Add() {
		try {
			Main.logger = Logger.getLogger(getClass());

			if (DbUtil.Odb_Action(110l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) ROOT.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/updatenat/IUUpdNat.fxml"));

			AddUpdNat controller = new AddUpdNat();
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
			if (UPD_NAT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {

				if (DbUtil.Odb_Action(112l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				Stage stage = (Stage) UPD_NAT.getScene().getWindow();
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
											+ " delete from UPD_NAT where ID = ?;" + "commit;" + "end;");
							VUPD_NAT cl = UPD_NAT.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getID());
							delete.executeUpdate();
							delete.close();
							
							Refresh();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DbUtil.Log_Error(e);
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

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;this.conn.setAutoCommit(false);
		this.from = 1;
		this.conn.setAutoCommit(false);
	}

	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {

				if (DbUtil.Odb_Action(111l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement selforupd = conn
						.prepareStatement(
								"select * from UPD_NAT where  ID = ? for update nowait");
				UPD_NAT cl = Initialize2(docid);
				selforupd.setLong(1, cl.getID());
				//selforupd.setLong(2, cl.getCUSID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DbUtil.Lock_Row(docid, "UPD_NAT",conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// XML
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/updatenat/IUUpdNat.fxml"));

						EditUpdNat controller = new EditUpdNat();
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
									// Если нажали сохранить
									// обновить без сохранения
									controller.SaveForCompare();
									if (controller.getStatus()) {
										conn.commit();
										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(docid, "UPD_NAT",conn);
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
												String lock = DbUtil.Lock_Row_Delete(docid, "UPD_NAT",conn);
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
										String lock = DbUtil.Lock_Row_Delete(docid, "UPD_NAT",conn);
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
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(docid, "UPD_NAT"));
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

	void Print() {
		try {
			if (UPD_NAT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {

				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {

					@Override
					public Object call() throws Exception {
						Main.logger = Logger.getLogger(getClass());

						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/UPD_NAT.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));

						// preparing variables
						Variables variables = new Variables();
						// SqlMap sql = new SqlMap().load("/updname/sql.xml");
						// String readRecordSQL = sql.getSql("QueryForReport");
						PreparedStatement prepStmt = conn.prepareStatement("select * from V_REP_UPD_NAT where ID = ?");
						prepStmt.setLong(1, UPD_NAT.getSelectionModel().getSelectedItem().getID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_UPD_NAT list = null;
						if (rs.next()) {
							list = new V_REP_UPD_NAT();

							list.setSVID_SERIA(rs.getString("SVID_SERIA"));
							list.setCURDATE(rs.getString("CURDATE"));
							list.setDCUSBIRTHDAY(rs.getString("DCUSBIRTHDAY"));
							list.setBR_ACT_DATE(rs.getString("BR_ACT_DATE"));
							list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
							list.setNEW_NAT(rs.getString("NEW_NAT"));
							list.setCCUSPLACE_BIRTH(rs.getString("CCUSPLACE_BIRTH"));
							list.setADDRESS(rs.getString("ADDRESS"));
							list.setNATIONALITY(rs.getString("NATIONALITY"));
							list.setLASTNAME(rs.getString("LASTNAME"));
							list.setID(rs.getLong("ID"));
							list.setMIDDLNAME(rs.getString("MIDDLNAME"));
							list.setOLD_NAT(rs.getString("OLD_NAT"));
							list.setFIRSTNAME(rs.getString("FIRSTNAME"));
							list.setBR_ACT_ID(rs.getLong("BR_ACT_ID"));
							list.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
							list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
						}
						prepStmt.close();
						rs.close();

						variables.addTextVariable(new TextVariable("#{DCUSBIRTHDAY}", list.getDCUSBIRTHDAY()));
						variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", list.getZAGS_NAME()));
						variables.addTextVariable(new TextVariable("#{COUNTRY_NAME}", list.getCOUNTRY_NAME()));
						variables.addTextVariable(new TextVariable("#{LASTNAME}", list.getLASTNAME()));
						variables.addTextVariable(new TextVariable("#{MIDDLENAME}", list.getMIDDLNAME()));
						variables.addTextVariable(new TextVariable("#{FIRSTNAME}", list.getFIRSTNAME()));
						variables.addTextVariable(new TextVariable("#{ADDRESS}", list.getADDRESS()));
						variables.addTextVariable(new TextVariable("#{NEW_NAT}", list.getNEW_NAT()));
						variables.addTextVariable(new TextVariable("#{OLD_NAT}", list.getOLD_NAT()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_ID}",
								(list.getBR_ACT_ID() != null) ? String.valueOf(list.getBR_ACT_ID()) : ""));
						variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", list.getBR_ACT_DATE()));
						variables.addTextVariable(new TextVariable("#{CCUSPLACE_BIRTH}", list.getCCUSPLACE_BIRTH()));
						variables.addTextVariable(new TextVariable("#{NEW_FIRSTNAME}", list.getFIRSTNAME()));
						variables.addTextVariable(new TextVariable("#{SVID_SERIA}", list.getSVID_SERIA()));
						variables.addTextVariable(new TextVariable("#{NATIONALITY}", list.getNATIONALITY()));
						variables.addTextVariable(new TextVariable("#{SVID_NUMBER}", list.getSVID_NUMBER()));
						variables.addTextVariable(new TextVariable("#{CURDATE}", list.getCURDATE()));
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
						File tempFile = File.createTempFile("UPD_NAT", ".docx",
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
						 * System.getenv("MJ_PATH") + "OutReports/UPD_NAT_" + uuid.toString() + ".docx";
						 * docx.save(filename);
						 * 
						 * if (Desktop.isDesktopSupported()) { Desktop.getDesktop().open(new
						 * File(filename)); }
						 */
						//ByteArrayOutputStream out = new ByteArrayOutputStream();
						// ByteArrayOutputStream out2 = new ByteArrayOutputStream();

						//PdfOptions options = PdfOptions.create();

						//PdfConverter.getInstance().convert(docx.getXWPFDocument(), out, options);

						// XHTMLOptions options2 = XHTMLOptions.create();
						// docx.getXWPFDocument().createStyles();
						// XHTMLConverter.getInstance().convert(docx.getXWPFDocument(), out2, options2);

						// options2.
						//byte[] xwpfDocumentBytes = out.toByteArray();

						// byte[] xwpfDocumentBytes2 = out2.toByteArray();
						// try (FileOutputStream stream = new FileOutputStream("D:/XWPFDocument.html"))
						// {
						// stream.write(xwpfDocumentBytes2);
						// }
						/*
						 * ByteArrayOutputStream out_ = new ByteArrayOutputStream();
						 * docx.getXWPFDocument().write(out_); byte[] docx_ = out_.toByteArray();
						 * InputStream targetStream = new ByteArrayInputStream(docx_);
						 * 
						 * //__________________ DocumentConverter converter = new DocumentConverter();
						 * Result<String> result = converter.convertToHtml(targetStream); String html =
						 * result.getValue(); // The generated HTML
						 * 
						 * Path path = Paths.get("D:/myarticle.html"); try (BufferedWriter writer =
						 * Files.newBufferedWriter(path)) { writer.write(html); }
						 * 
						 * Set<String> warnings = result.getWarnings(); // Any warnings during
						 * conversion System.out.println(warnings);
						 */
						// __________________
						// build a component controller
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
//						// applicationFrame.setDefaultCloseOperation(JFrame.NORMAL);
//						applicationFrame.getContentPane().add(viewerComponentPanel);
//
//						// Now that the GUI is all in place, we can try openning a PDF
//						// controller.openDocument(filename);
//						// controller.openDocument(arg0, arg1, arg2);
//						controller.openDocument(xwpfDocumentBytes, 0, xwpfDocumentBytes.length, "", "");
//						// controller.isDocumentViewMode(0);
//						// controller.print(true);
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

	void Refresh() {
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

			String selectStmt = "select * from VUPD_NAT t" + ((getWhere() != null) ? getWhere() : "");

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VUPD_NAT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				VUPD_NAT list = new VUPD_NAT();
				list.setCUSID(rs.getLong("CUSID"));
				list.setOLD_NAT(rs.getString("OLD_NAT"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setOPER(rs.getString("OPER"));
				list.setID(rs.getLong("ID"));
				list.setBRN_ACT_ID(rs.getLong("BRN_ACT_ID"));
				list.setFIO(rs.getString("FIO"));
				list.setNEW_NAT(rs.getString("NEW_NAT"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setTM$DOC_DATE((rs.getDate("TM$DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_DATE")), formatterdt)
						: null);
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			UPD_NAT.setItems(dlist);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<VUPD_NAT> tableFilter = TableFilter.forTableView(UPD_NAT).apply();
					tableFilter.setSearchStrategy((input, target) -> {
						try {
							return target.toLowerCase().contains(input.toLowerCase());
						} catch (Exception e) {
							return false;
						}
					});
				}
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	UPD_NAT Initialize2(Long docid) {
		UPD_NAT list = null;
		try {
			Main.logger = Logger.getLogger(getClass());
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String selectStmt = "" + "select " + "(select g.ccusname from cus g where g.icusnum = t.cusid) FIO,"
					+ " t.*" + " from UPD_NAT t " + "where t.ID = ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<UPD_NAT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new UPD_NAT();
				list.setID(rs.getLong("ID"));
				list.setCUSID(rs.getLong("CUSID"));
				list.setOPER(rs.getString("OPER"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDateTime.parse(
								new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DOC_DATE")), formatterdt)
						: null);
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setBRN_ACT_ID(rs.getLong("BRN_ACT_ID"));
				list.setOLD_NAT(rs.getLong("OLD_NAT"));
				list.setNEW_NAT(rs.getLong("NEW_NAT"));
				list.setFIO(rs.getString("FIO"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
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
		if (UPD_NAT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(UPD_NAT.getSelectionModel().getSelectedItem().getID(), (Stage) UPD_NAT.getScene().getWindow());
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
		if (UPD_NAT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(UPD_NAT.getSelectionModel().getSelectedItem().getID(), (Stage) UPD_NAT.getScene().getWindow());
		}
	}

	@FXML
	void BtDelete(ActionEvent event) {
		Delete();
	}

	@FXML
	void BtPrint(ActionEvent event) {
		Print();
	}

	public void manipulatePdf(String src, String dest) throws Exception{
		if (UPD_NAT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			AcroFields fields = stamper.getAcroFields();
		    //System.out.print(fields.getFields());


			PreparedStatement prp = conn.prepareStatement("select * from BLANK_UPD_NAT where ID = ?");
			prp.setLong(1, UPD_NAT.getSelectionModel().getSelectedItem().getID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("ABH_LNAME"));
				fields.setField("Текст2", rs.getString("ABH_FMNAME"));
				fields.setField("Текст3", rs.getString("ABH_COUNTRY"));
				fields.setField("Текст4", rs.getString("ABH_OLD_NAT"));
				fields.setField("Текст5", rs.getString("DD_BIRTH"));
				fields.setField("Текст6", rs.getString("ABH_MM_BIRTH") + " " + rs.getString("YYYY_BIRTH") + " ш. "
						+ rs.getString("AB_PLACE_BIRTH"));
				fields.setField("Текст8", rs.getString("ABH_NEW_NAT"));
				fields.setField("Текст9","");
				fields.setField("Текст10", rs.getString("RU_YYYY"));
				fields.setField("Текст11", rs.getString("MM"));
				fields.setField("Текст12", rs.getString("DD"));
				fields.setField("Текст13", rs.getString("ZAGS_CITY_ABH"));
				fields.setField("Текст14", rs.getString("ZAGS_ADR_ABH"));
				fields.setField("Текст15", rs.getString("ABH"));
				fields.setField("Текст16", rs.getString("DD"));
				fields.setField("Текст17", rs.getString("DOC_DATE"));
				fields.setField("Текст18", rs.getString("RU_LNAME"));
				fields.setField("Текст19", rs.getString("RU_FMNAME"));
				fields.setField("Текст20", rs.getString("RU_COUNTRY"));
				
				fields.setField("Текст21", rs.getString("HE"));
				fields.setField("Текст22", rs.getString("DD_BIRTH"));
				fields.setField("Текст23", rs.getString("RU_MM_BIRTH"));
				fields.setField("Текст24", rs.getString("YYYY_BIRTH"));
				fields.setField("Текст25", rs.getString("RU_BRTH_PLC"));
				fields.setField("Текст26", rs.getString("NAME_FULL"));
				fields.setField("Текст27", rs.getString("RU_NAT"));
				fields.setField("Текст28", rs.getString("RU_YYYY"));
				fields.setField("Текст29", rs.getString("RU_MM"));
				fields.setField("Текст30", rs.getString("RU_DD"));
				fields.setField("Текст31", "");
				fields.setField("Текст32", "ЗАГС");
				fields.setField("Текст33", rs.getString("ADDR"));
				fields.setField("Текст34", rs.getString("NAME_FULL"));
				fields.setField("Текст36", rs.getString("RU_MM"));
				
				fields.setField("Текст69", rs.getString("RU_YYYY"));
				fields.setField("Текст71", rs.getString("ZAGS_RUK_ABH"));
				fields.setField("Текст72", rs.getString("ZAGS_RUK"));
			}
			prp.close();
			rs.close();
			
			stamper.close();
			reader.close();
		}
	}
	
	Connection conn;

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
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

	void CellDateFormatDT(TableColumn<VUPD_NAT, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<VUPD_NAT, LocalDateTime> cell = new TableCell<VUPD_NAT, LocalDateTime>() {
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

	void CellDateFormatD(TableColumn<VUPD_NAT, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<VUPD_NAT, LocalDate> cell = new TableCell<VUPD_NAT, LocalDate>() {
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
	void AltPrint(ActionEvent event) {
		try {
			if (UPD_NAT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
//				VUPD_NAT cl = UPD_NAT.getSelectionModel().getSelectedItem();
//				Stage stage = new Stage();
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/mj/report/Report.fxml"));
//
//				Report controller = new Report();
//				controller.setId(3);
//				controller.setRecId(cl.getID());
//				loader.setController(controller);
//
//				Stage stage_ = (Stage) UPD_NAT.getScene().getWindow();
//				Parent root = loader.load();
//				stage.setScene(new Scene(root));
//				stage.getIcons().add(new Image("/icon.png"));
//				stage.setResizable(false);
//				stage.initModality(Modality.WINDOW_MODAL);
//				stage.setTitle("Печать (" + controller.getId() + ")");
//				stage.initOwner(stage_);
//
//				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//					@Override
//					public void handle(WindowEvent paramT) {
//						controller.dbDisconnect();
//					}
//				});
//				stage.show();
				manipulatePdf(System.getenv("MJ_PATH") + "/Reports/UPD_NAT.pdf",
						System.getenv("MJ_PATH") + "/OutReports/UPD_NAT.pdf");
				Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/UPD_NAT.pdf"));
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

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

	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			VB.getChildren().remove(FILTER);

			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});

			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ROOT.setBottom(createOptionPane(UPD_NAT));

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());

			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));
			OLD_NAT.setColumnFilter(new PatternColumnFilter<>());
			NEW_NAT.setColumnFilter(new PatternColumnFilter<>());
			OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			FIO.setColumnFilter(new PatternColumnFilter<>());
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			Refresh();
			/**
			 * Столбцы таблицы
			 */
			{
				ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
				OLD_NAT.setCellValueFactory(cellData -> cellData.getValue().OLD_NATProperty());
				NEW_NAT.setCellValueFactory(cellData -> cellData.getValue().NEW_NATProperty());
				OPER.setCellValueFactory(cellData -> cellData.getValue().OPERProperty());
				CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
				FIO.setCellValueFactory(cellData -> cellData.getValue().FIOProperty());
				DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			}

			// двойной щелчок
			UPD_NAT.setRowFactory(tv -> {
				TableRow<VUPD_NAT> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit(UPD_NAT.getSelectionModel().getSelectedItem().getID(),
								(Stage) UPD_NAT.getScene().getWindow());
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

	public UpdNatList() {
		Main.logger = Logger.getLogger(getClass());
		this.Where = new SimpleStringProperty();
	}

	/**
	 * Сравнение данных
	 * 
	 * @return
	 */
	Long CompareBeforeClose(Long docid) {
		Long ret = 0l;
		try {
			Clob lob = conn.createClob();
			lob.setString(1, RetXml);
			CallableStatement callStmt = conn.prepareCall("{ call UDPNAT.CompareXmls(?,?,?,?)}");
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

	String RetXml;

	/**
	 * Возврат XML файлов для сравнения
	 */
	void XmlsForCompare(Long docid) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call UDPNAT.RetXmls(?,?,?)}");
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
				RetXml = new String(char_xmls);
				// System.out.println(RetXml);
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
