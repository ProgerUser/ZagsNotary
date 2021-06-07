package mj.doc.updabhname;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;
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
import mj.doc.updname.V_REP_UPDATE_NAME;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class UpdAbhNameList {

	@FXML
	private DatePicker DT1;

	@FXML
	private DatePicker DT2;

	@FXML
	private BorderPane ROOT;

	@FXML
	private XTableView<UPDATE_ABH_NAME> UPDATE_NAME;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> DOC_NUMBER;
	
	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> NEW_FIRSTNAME;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> OLD_LASTNAME;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> NEW_MIDDLNAME;

	@FXML
	private ProgressIndicator PB;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> NEW_LASTNAME;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, Long> ID;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> OLD_FIRSTNAME;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> CR_TIME;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> OPER;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, LocalDate> CR_DATE;

	// @FXML
	// private XTableColumn<UPDATE_ABH_NAME, LocalDateTime> DOC_DATE;

	@FXML
	private XTableColumn<UPDATE_ABH_NAME, String> OLD_MIDDLNAME;

	void Add() {
		try {
			if (DbUtil.Odb_Action(113l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) UPDATE_NAME.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/updabhname/IUUpdAbhName.fxml"));

			AddUpdAbhName controller = new AddUpdAbhName();
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

				if (DbUtil.Odb_Action(115l) == 0) {
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
							UPDATE_ABH_NAME cl = UPDATE_NAME.getSelectionModel().getSelectedItem();
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

	UPDATE_ABH_NAME Initialize2(Long docid) {
		UPDATE_ABH_NAME list = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

			String selectStmt = "select * from VUPDATE_ABH_NAME t\r\n where ID = ?";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<UPDATE_ABH_NAME> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new UPDATE_ABH_NAME();

				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setBRN_ACT_ID(rs.getLong("BRN_ACT_ID"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setTM$DOC_DATE((rs.getDate("TM$DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_DATE")), formatterwt)
						: null);
				list.setCUSID(rs.getLong("CUSID"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setFIO(rs.getString("FIO"));
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setOPER(rs.getString("OPER"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setID(rs.getLong("ID"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
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
				if (DbUtil.Odb_Action(114l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}
				PreparedStatement selforupd = conn
						.prepareStatement("select * from UPDATE_ABH_NAME where  ID = ? for update nowait");
				UPDATE_ABH_NAME cl = Initialize2(docid);
				selforupd.setLong(1, cl.getID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DbUtil.Lock_Row(docid, "UPDATE_ABH_NAME",conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// Заполнить xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/updabhname/IUUpdAbhName.fxml"));

						EditUpdAbhName controller = new EditUpdAbhName();
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
									controller.SaveCompare();
									// Если нажали сохранить
									if (controller.getStatus()) {
										conn.commit();
										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(docid, "UPDATE_ABH_NAME",conn);
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
												String lock = DbUtil.Lock_Row_Delete(docid, "UPDATE_ABH_NAME",conn);
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
										String lock = DbUtil.Lock_Row_Delete(docid, "UPDATE_ABH_NAME",conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
									}
								} catch (Exception e) {
									DbUtil.Log_Error(e);
								}
							}
						});
						stage.show();
						isopen = true;
					}
				} catch (SQLException e) {
					if (e.getErrorCode() == 54) {
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(docid, "UPDATE_ABH_NAME"));
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
			if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {

					@Override
					public Object call() throws Exception {
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/UPDATE_ABH_NAME.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));

						// preparing variables
						Variables variables = new Variables();
						// SqlMap sql = new SqlMap().load("/updname/sql.xml");
						// String readRecordSQL = sql.getSql("QueryForReport");
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from v_rep_UPDATE_ABH_NAME where ID = ?");
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
						 * System.getenv("MJ_PATH") + "OutReports/UPDATE_ABH_NAME_" + uuid.toString() +
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

	void Refresh() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

			String selectStmt = "select * from VUPDATE_ABH_NAME t\r\n" + ((getWhere() != null) ? getWhere() : "");

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<UPDATE_ABH_NAME> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				UPDATE_ABH_NAME list = new UPDATE_ABH_NAME();

				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setBRN_ACT_ID(rs.getLong("BRN_ACT_ID"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setTM$DOC_DATE((rs.getDate("TM$DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_DATE")), formatterwt)
						: null);
				list.setCUSID(rs.getLong("CUSID"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setFIO(rs.getString("FIO"));
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setOPER(rs.getString("OPER"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setID(rs.getLong("ID"));
				list.setSVID_NUMBER(rs.getString("SVID_NUMBER"));
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

			TableFilter<UPDATE_ABH_NAME> tableFilter = TableFilter.forTableView(UPDATE_NAME).apply();
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
	
	public void manipulatePdf(String src, String dest) throws Exception{
		if (UPDATE_NAME.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			AcroFields fields = stamper.getAcroFields();
//			Iterator it = fields.getFields().entrySet().iterator();
//			while (it.hasNext()) {
//				Map.Entry pair = (Map.Entry) it.next();
//				System.out.println(pair.getKey());
//				it.remove(); // avoids a ConcurrentModificationException
//			}
			
			PreparedStatement prp = conn.prepareStatement("select * from BLANK_UPDATE_ABH_NAME where ID = ?");
			prp.setLong(1, UPDATE_NAME.getSelectionModel().getSelectedItem().getID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("F1"));
				fields.setField("Текст2", rs.getString("F2"));
				fields.setField("Текст3", rs.getString("F3"));
				fields.setField("Текст4", rs.getString("F4"));
				fields.setField("Текст5", rs.getString("F5"));
				fields.setField("Текст6", "");
				fields.setField("Текст7", rs.getString("F7"));
				fields.setField("Текст8", rs.getString("F8"));
				fields.setField("Текст9", "");
				fields.setField("Текст10", rs.getString("F10"));
				fields.setField("Текст11", rs.getString("F11"));
				fields.setField("Текст12", rs.getString("F12"));
				fields.setField("Текст13", rs.getString("F13"));
				fields.setField("Текст14", rs.getString("F14"));
				fields.setField("Текст15", rs.getString("F15"));
				fields.setField("Текст16", rs.getString("F13"));
				fields.setField("Текст17", rs.getString("F12"));
				fields.setField("Текст18", rs.getString("F18"));
				fields.setField("Текст19", rs.getString("F19"));
				fields.setField("Текст20", rs.getString("F4"));
				fields.setField("Текст21", rs.getString("F21"));
				fields.setField("Текст22", rs.getString("F22"));
				fields.setField("Текст23", rs.getString("F23"));
				fields.setField("Текст24", rs.getString("F24"));
				fields.setField("Текст25", rs.getString("F25"));
				fields.setField("Текст26", rs.getString("F11"));
				fields.setField("Текст27", rs.getString("F27"));
				fields.setField("Текст28", rs.getString("F13"));
				fields.setField("Текст29", rs.getString("F10"));
				fields.setField("Текст30", rs.getString("F30"));
				fields.setField("Текст31", rs.getString("F31"));
				fields.setField("Текст34", rs.getString("F27"));
				fields.setField("Текст35", rs.getString("F11"));
				fields.setField("Текст36", rs.getString("F36"));
				fields.setField("Текст37", rs.getString("F36"));
				fields.setField("Текст42", rs.getString("F42"));
				fields.setField("Текст43", rs.getString("F43"));
			}
			prp.close();
			rs.close();
			
			stamper.close();
			reader.close();
		}
	}

	@FXML
	void PrintBlank(ActionEvent event) {
		try {
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/UPDATE_ABH_NAME.pdf",
					System.getenv("MJ_PATH") + "/OutReports/UPDATE_ABH_NAME.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/UPDATE_ABH_NAME.pdf"));
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
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
	void BtPrint(ActionEvent event) {
		Print();
	}

	Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "UpdNameList");
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

	void CellDateFormatDT(TableColumn<UPDATE_ABH_NAME, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<UPDATE_ABH_NAME, LocalDateTime> cell = new TableCell<UPDATE_ABH_NAME, LocalDateTime>() {
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

	void CellDateFormatD(XTableColumn<UPDATE_ABH_NAME, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<UPDATE_ABH_NAME, LocalDate> cell = new TableCell<UPDATE_ABH_NAME, LocalDate>() {
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
    
	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
			DbUtil.Run_Process(conn);
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
				OLD_MIDDLNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_MIDDLNAMEProperty());
				DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			}

			// двойной щелчок
			UPDATE_NAME.setRowFactory(tv -> {
				TableRow<UPDATE_ABH_NAME> row = new TableRow<>();
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

	public UpdAbhNameList() {
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
			CallableStatement callStmt = conn.prepareCall("{ call UpdAbhName.CompareXmls(?,?,?,?)}");
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
			CallableStatement callStmt = conn.prepareCall("{ call UpdAbhName.RetXmls(?,?,?)}");
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
