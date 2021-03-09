package mj.doc.death;

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

import org.apache.commons.lang3.exception.ExceptionUtils;
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
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

/**
 * Контроллер документа о смерти,<br>
 * добавление, редактирование
 * 
 * @author Said
 *
 */
public class DeathList {

	@FXML
	private VBox VB;

	@FXML
	private TitledPane FILTER;

	/**
	 * с
	 */
	@FXML
	private DatePicker DT1;

	/**
	 * прогресс индикатор
	 */
	@FXML
	private ProgressIndicator PB;

	/**
	 * по
	 */
	@FXML
	private DatePicker DT2;

	/**
	 * 
	 */
	@FXML
	private BorderPane ROOT;

	/**
	 * ид документа
	 */
	@FXML
	private XTableColumn<DEATH_CERT, Integer> DC_ID;

	@FXML
	private XTableColumn<DEATH_CERT, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<DEATH_CERT, String> CR_TIME;

	@FXML
	private XTableColumn<DEATH_CERT, String> OPER;

	/**
	 * Место смерти
	 */
	@FXML
	private XTableColumn<DEATH_CERT, String> DC_DPL;

	/**
	 * дата создания документа
	 */
	// @FXML
	// private XTableColumn<DEATH_CERT, LocalDateTime> DC_OPEN;

	/**
	 * фио
	 */
	@FXML
	private XTableColumn<DEATH_CERT, String> DFIO;

	/**
	 * дата рождения
	 */
	@FXML
	private XTableColumn<DEATH_CERT, LocalDate> DBDATE;

	/**
	 * таблица
	 */
	@FXML
	private XTableView<DEATH_CERT> DEATH_CERT;

	/**
	 * дата смерти
	 */
	@FXML
	private XTableColumn<DEATH_CERT, LocalDate> DC_DD;

	/**
	 * 
	 * @param ID
	 */
	void Init(Integer ID) {

	}

	/**
	 * добавить
	 */
	void Add() {
		try {
			Main.logger = Logger.getLogger(getClass());

			if (DBUtil.OdbAction(104) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) DEATH_CERT.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/death/IUDeath.fxml"));

			AddDeath controller = new AddDeath();
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
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * удалить
	 */
	void Delete() {
		try {
			if (DEATH_CERT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				Main.logger = Logger.getLogger(getClass());

				if (DBUtil.OdbAction(106) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				Stage stage = (Stage) DEATH_CERT.getScene().getWindow();
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
											+ " delete from DEATH_CERT where DC_ID = ?;" + "commit;" + "end;");
							DEATH_CERT cl = DEATH_CERT.getSelectionModel().getSelectedItem();
							delete.setInt(1, cl.getDC_ID());
							delete.executeUpdate();
							delete.close();

							Refresh();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								Msg.Message(ExceptionUtils.getStackTrace(e1));
								Main.logger.error(
										ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
							}
							Msg.Message(ExceptionUtils.getStackTrace(e));
							Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
							String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
							String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
							int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
							DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Открыта ли форма редактирования <br>
	 * Возможен только один экземпляр формы
	 */
	boolean isopen = false;

	DEATH_CERT Initialize2(Integer docid) {
		DEATH_CERT list = null;
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String selectStmt = "select * from vdeath_cert t where DC_ID  = ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setInt(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DEATH_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new DEATH_CERT();

				list.setDBDATE((rs.getDate("DBDATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DBDATE")), formatter)
						: null);
				list.setDC_FADORG_NAME(rs.getString("DC_FADORG_NAME"));
				list.setDC_FTYPE(rs.getString("DC_FTYPE"));
				list.setDC_USR(rs.getString("DC_USR"));
				list.setDC_LLOC(rs.getString("DC_LLOC"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setDC_NUMBER(rs.getString("DC_NUMBER"));
				list.setDC_FNUM(rs.getString("DC_FNUM"));
				list.setDC_ZTP(rs.getString("DC_ZTP"));
				list.setDC_DPL(rs.getString("DC_DPL"));
				list.setDFIO(rs.getString("DFIO"));
				list.setDC_FADLAST_NAME(rs.getString("DC_FADLAST_NAME"));
				list.setDC_FMON(rs.getString("DC_FMON"));
				list.setDC_FADREG_ADR(rs.getString("DC_FADREG_ADR"));
				list.setTM$DC_OPEN((rs.getDate("TM$DC_OPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DC_OPEN")), formatterwt)
						: null);
				list.setDC_FD((rs.getDate("DC_FD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_FD")), formatter)
						: null);
				list.setDC_NRNAME(rs.getString("DC_NRNAME"));
				list.setDC_FADMIDDLE_NAME(rs.getString("DC_FADMIDDLE_NAME"));
				list.setDC_RCNAME(rs.getString("DC_RCNAME"));
				list.setDC_FADFIRST_NAME(rs.getString("DC_FADFIRST_NAME"));
				list.setDC_FADLOCATION(rs.getString("DC_FADLOCATION"));
				list.setDC_ID(rs.getInt("DC_ID"));
				list.setDC_CUS(rs.getInt("DC_CUS"));
				list.setDC_CD(rs.getString("DC_CD"));
				list.setDC_ZAGS(rs.getInt("DC_ZAGS"));
				list.setDC_DD((rs.getDate("DC_DD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_DD")), formatter)
						: null);
				list.setDC_SERIA(rs.getString("DC_SERIA"));
				list.setCR_TIME(rs.getString("CR_TIME"));

				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return list;
	}

	/**
	 * Редактировать
	 */
	public void Edit(Integer docid, Stage stage_) {
		try {
			if (isopen == false) {
				Main.logger = Logger.getLogger(getClass());

				if (DBUtil.OdbAction(105) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement selforupd = conn
						.prepareStatement("select * from DEATH_CERT where  DC_ID = ? /*for update nowait*/");
				DEATH_CERT cl = Initialize2(docid);
				selforupd.setInt(1, cl.getDC_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DBUtil.Lock_Row(docid, "DEATH_CERT");
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/death/IUDeath.fxml"));

						EditDeath controller = new EditDeath();
						controller.setId(cl.getDC_ID());
						controller.setConn(conn, cl);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: " + cl.getDFIO());
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									// Если нажали сохранить
									// обновить без сохранения
									controller.SaveToCompare();
									if (controller.getStatus()) {
										conn.commit();
										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "DEATH_CERT");
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
												String lock = DBUtil.Lock_Row_Delete(docid, "DEATH_CERT");
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
									}
									// Если нажали "X" или "Cancel" и до этого ничего не меняли
									else if (!controller.getStatus() & CompareBeforeClose(docid) == 0) {
										conn.rollback();
										isopen = false;
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "DEATH_CERT");
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
									}
								} catch (SQLException e) {
									DBUtil.LOG_ERROR(e);
								}
							}
						});
						stage.show();
						isopen = true;
					}
				} catch (SQLException e) {
					if (e.getErrorCode() == 54) {
						Msg.Message("Запись редактируется " + DBUtil.Lock_Row_View(docid, "DEATH_CERT"));
						DBUtil.LOG_ERROR(e);
					} else {
						DBUtil.LOG_ERROR(e);
					}
				}
			} else {
				Msg.Message("Форма редактирования уже открыта!");
			}

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Печать
	 */
	void Print() {
		try {
			if (DEATH_CERT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {
					@Override
					public Object call() throws Exception {
						// Вызов
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/DEATH_CERT.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from v_rep_death_cert where DC_ID = ?");
						prepStmt.setInt(1, DEATH_CERT.getSelectionModel().getSelectedItem().getDC_ID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_DEATH_CERT list = null;
						if (rs.next()) {
							list = new V_REP_DEATH_CERT();
							list.setDC_RCNAME(rs.getString("DC_RCNAME"));
							list.setDC_NRNAME(rs.getString("DC_NRNAME"));
							list.setDC_CD(rs.getString("DC_CD"));
							list.setNAT_NAME(rs.getString("NAT_NAME"));
							list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
							list.setDC_DPL(rs.getString("DC_DPL"));
							list.setDC_ID(rs.getString("DC_ID"));
							list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
							list.setDC_LLOC(rs.getString("DC_LLOC"));
							list.setDC_FD(rs.getString("DC_FD"));
							list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
							list.setDC_FNUM(rs.getString("DC_FNUM"));
							list.setDC_OPEN(rs.getString("DC_OPEN"));
							list.setCITIZEN(rs.getString("CITIZEN"));
							list.setDC_DD(rs.getString("DC_DD"));
							list.setDC_NUMBER(rs.getString("DC_NUMBER"));
							list.setDC_FMON(rs.getString("DC_FMON"));
							list.setDCUSBIRTHDAY(rs.getString("DCUSBIRTHDAY"));
							list.setADDRESS(rs.getString("ADDRESS"));
							list.setSEX(rs.getString("SEX"));
							list.setDC_SERIA(rs.getString("DC_SERIA"));
							list.setDC_ZTP(rs.getString("DC_ZTP"));
						}
						prepStmt.close();
						rs.close();
						// ------------
						variables.addTextVariable(new TextVariable("#{DC_RCNAME}", list.getDC_RCNAME()));
						variables.addTextVariable(new TextVariable("#{DC_NRNAME}", list.getDC_NRNAME()));
						variables.addTextVariable(new TextVariable("#{DC_CD}", list.getDC_CD()));
						variables.addTextVariable(new TextVariable("#{NAT_NAME}", list.getNAT_NAME()));
						variables.addTextVariable(new TextVariable("#{CCUSFIRST_NAME}", list.getCCUSFIRST_NAME()));
						variables.addTextVariable(new TextVariable("#{DC_DPL}", list.getDC_DPL()));
						variables.addTextVariable(new TextVariable("#{DC_ID}", list.getDC_ID()));
						variables.addTextVariable(new TextVariable("#{CCUSMIDDLE_NAME}", list.getCCUSMIDDLE_NAME()));
						variables.addTextVariable(new TextVariable("#{DC_LLOC}", list.getDC_LLOC()));
						variables.addTextVariable(new TextVariable("#{DC_FD}", list.getDC_FD()));
						variables.addTextVariable(new TextVariable("#{CCUSLAST_NAME}", list.getCCUSLAST_NAME()));
						variables.addTextVariable(new TextVariable("#{DC_FNUM}", list.getDC_FNUM()));
						variables.addTextVariable(new TextVariable("#{DC_OPEN}", list.getDC_OPEN()));
						variables.addTextVariable(new TextVariable("#{DC_DD}", list.getDC_DD()));
						variables.addTextVariable(new TextVariable("#{CITIZEN}", list.getCITIZEN()));
						variables.addTextVariable(new TextVariable("#{DC_NUMBER}", list.getDC_NUMBER()));
						variables.addTextVariable(new TextVariable("#{DC_FMON}", list.getDC_FMON()));
						variables.addTextVariable(new TextVariable("#{DCUSBIRTHDAY}", list.getDCUSBIRTHDAY()));
						variables.addTextVariable(new TextVariable("#{ADDRESS}", list.getADDRESS()));
						variables.addTextVariable(new TextVariable("#{SEX}", list.getSEX()));
						variables.addTextVariable(new TextVariable("#{DC_SERIA}", list.getDC_SERIA()));
						variables.addTextVariable(new TextVariable("#{DC_ZTP}", list.getDC_ZTP()));
						// fill template
						docx.fillTemplate(variables);
						File tempFile = File.createTempFile("DIVORCE_CERT", ".docx",
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
						 * System.getenv("MJ_PATH") + "OutReports/DEATH_CERT_" + uuid.toString() +
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
			DBUtil.LOG_ERROR(e);
		}
	}

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	private Executor exec;

	/**
	 * Обновить таблицу
	 */
	void Refresh() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String selectStmt = "select * from vdeath_cert t " + ((getWhere() != null) ? getWhere() : "");
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DEATH_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				DEATH_CERT list = new DEATH_CERT();

				list.setDBDATE((rs.getDate("DBDATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DBDATE")), formatter)
						: null);
				list.setDC_FADORG_NAME(rs.getString("DC_FADORG_NAME"));
				list.setDC_FTYPE(rs.getString("DC_FTYPE"));
				list.setDC_USR(rs.getString("DC_USR"));
				list.setDC_LLOC(rs.getString("DC_LLOC"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setDC_NUMBER(rs.getString("DC_NUMBER"));
				list.setDC_FNUM(rs.getString("DC_FNUM"));
				list.setDC_ZTP(rs.getString("DC_ZTP"));
				list.setDC_DPL(rs.getString("DC_DPL"));
				list.setDFIO(rs.getString("DFIO"));
				list.setDC_FADLAST_NAME(rs.getString("DC_FADLAST_NAME"));
				list.setDC_FMON(rs.getString("DC_FMON"));
				list.setDC_FADREG_ADR(rs.getString("DC_FADREG_ADR"));
				list.setTM$DC_OPEN((rs.getDate("TM$DC_OPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DC_OPEN")), formatterwt)
						: null);
				list.setDC_FD((rs.getDate("DC_FD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_FD")), formatter)
						: null);
				list.setDC_NRNAME(rs.getString("DC_NRNAME"));
				list.setDC_FADMIDDLE_NAME(rs.getString("DC_FADMIDDLE_NAME"));
				list.setDC_RCNAME(rs.getString("DC_RCNAME"));
				list.setDC_FADFIRST_NAME(rs.getString("DC_FADFIRST_NAME"));
				list.setDC_FADLOCATION(rs.getString("DC_FADLOCATION"));
				list.setDC_ID(rs.getInt("DC_ID"));
				list.setDC_CUS(rs.getInt("DC_CUS"));
				list.setDC_CD(rs.getString("DC_CD"));
				list.setDC_ZAGS(rs.getInt("DC_ZAGS"));
				list.setDC_DD((rs.getDate("DC_DD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_DD")), formatter)
						: null);
				list.setDC_SERIA(rs.getString("DC_SERIA"));
				list.setCR_TIME(rs.getString("CR_TIME"));

				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			DEATH_CERT.setItems(dlist);

			TableFilter<DEATH_CERT> tableFilter = TableFilter.forTableView(DEATH_CERT).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * км обновить
	 * 
	 * @param event
	 */
	@FXML
	void CmRefresh(ActionEvent event) {
		Refresh();
	}

	/**
	 * кс добавить
	 * 
	 * @param event
	 */
	@FXML
	void CmAdd(ActionEvent event) {
		Add();
	}

	/**
	 * км редактировать
	 * 
	 * @param event
	 */
	@FXML
	void CmEdit(ActionEvent event) {
		if (DEATH_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(DEATH_CERT.getSelectionModel().getSelectedItem().getDC_ID(),
					(Stage) DEATH_CERT.getScene().getWindow());
		}
	}

	/**
	 * км печать
	 * 
	 * @param event
	 */
	@FXML
	void CmPrint(ActionEvent event) {
		Print();
	}

	/**
	 * км удалить
	 * 
	 * @param event
	 */
	@FXML
	void CmDelete(ActionEvent event) {
		Delete();
	}

	/**
	 * добавть
	 * 
	 * @param event
	 */
	@FXML
	void BtAdd(ActionEvent event) {
		Add();
	}

	/**
	 * кн редактировать
	 * 
	 * @param event
	 */
	@FXML
	void BtEdit(ActionEvent event) {
		if (DEATH_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(DEATH_CERT.getSelectionModel().getSelectedItem().getDC_ID(),
					(Stage) DEATH_CERT.getScene().getWindow());
		}
	}

	/**
	 * Кн удалить
	 * 
	 * @param event
	 */
	@FXML
	void BtDelete(ActionEvent event) {
		Delete();
	}

	/**
	 * Кнопка печать
	 * 
	 * @param event
	 */
	@FXML
	void BtPrint(ActionEvent event) {
		Print();
	}
	
	public void manipulatePdf(String src, String dest) throws Exception{
		if (DEATH_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			AcroFields fields = stamper.getAcroFields();
		    //System.out.print(fields.getFields());

			PreparedStatement prp = conn.prepareStatement("select * from BLANK_DEATH_CERT where DC_ID = ?");
			prp.setInt(1, DEATH_CERT.getSelectionModel().getSelectedItem().getDC_ID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("AB_LAST_NAME"));
				fields.setField("Текст2", rs.getString("ABH_FMNAME"));
				fields.setField("Текст3", rs.getString("ABH_COUNTRY"));
				fields.setField("Текст4", rs.getString("BR_DD"));
				fields.setField("Текст5", rs.getString("ABH_MM"));
				fields.setField("Текст6", rs.getString("BR_YYYY"));
				fields.setField("Текст7", rs.getString("ABH_BRTH_PLC"));
				fields.setField("Текст8", rs.getString("FIO_ABH_SH"));
				fields.setField("Текст9", rs.getString("DETH_DATE"));
				fields.setField("Текст10", "-");
				fields.setField("Текст11", "-");
				fields.setField("Текст12", "-");
				fields.setField("Текст13", rs.getString("DETH_OPEN_YYYY"));
				fields.setField("Текст14", rs.getString("DETH_OPEN_MM"));
				fields.setField("Текст15", rs.getString("DETH_OPEN_DD"));
				fields.setField("Текст16", rs.getString("ADDR_ABH"));
				fields.setField("Текст17", "Аԥсны Аҳәынҭқарра");
				fields.setField("Текст18", "-");
				fields.setField("Текст19", rs.getString("DETH_OPEN_DD"));
				fields.setField("Текст20", rs.getString("ABH_DC_OPEN"));
				fields.setField("Текст21", rs.getString("DETH_OPEN_YYYY"));
				fields.setField("Текст22", rs.getString("ZAGS_CITY_ABH"));
				fields.setField("Текст23", rs.getString("F23"));
				fields.setField("Текст24", rs.getString("F24"));	
				fields.setField("Текст25", rs.getString("F25"));
				fields.setField("Текст26", rs.getString("F26"));
				fields.setField("Текст27", rs.getString("F27"));
				fields.setField("Текст28", rs.getString("BR_DD"));
				fields.setField("Текст29", rs.getString("F29"));
				fields.setField("Текст30", rs.getString("BR_YYYY"));
				fields.setField("Текст31", rs.getString("F31"));
				fields.setField("Текст32", rs.getString("F32"));
				fields.setField("Текст33", rs.getString("F33"));
				fields.setField("Текст34", rs.getString("F34"));
				fields.setField("Текст35", rs.getString("F35"));
				fields.setField("Текст36", rs.getString("DETH_OPEN_YYYY"));
				fields.setField("Текст37", rs.getString("F37"));
				fields.setField("Текст38", rs.getString("F38"));
				fields.setField("Текст39", "-");
				fields.setField("Текст40", rs.getString("F40"));
				fields.setField("Текст41", "Республики Абхазия");
				fields.setField("Текст42", "-");
				fields.setField("Текст43", "ЗАГС");
				fields.setField("Текст44", rs.getString("F44"));
				fields.setField("Текст45", "Республики Абхазия");
				fields.setField("Текст46", rs.getString("F46"));
				fields.setField("Текст47", rs.getString("F37"));
			}
			prp.close();
			rs.close();
			
			stamper.close();
			reader.close();
		}
	}

	/**
	 * Кнопка печать бланка
	 * 
	 * @param event
	 */
	@FXML
	void BtPrintBlank(ActionEvent event) {
		try {
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/DEATH_CERT.pdf",
					System.getenv("MJ_PATH") + "/OutReports/DEATH_CERT.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/DEATH_CERT.pdf"));
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	/**
	 * Подключение к базе
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "DeathList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Закрыть сессию
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Форматирование даты и времени
	 * 
	 * @param tc
	 */
	void CellDateFormatDT(TableColumn<DEATH_CERT, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<DEATH_CERT, LocalDateTime> cell = new TableCell<DEATH_CERT, LocalDateTime>() {
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

	/**
	 * Форматирование даты
	 * 
	 * @param tc
	 */
	void CellDateFormatD(TableColumn<DEATH_CERT, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<DEATH_CERT, LocalDate> cell = new TableCell<DEATH_CERT, LocalDate>() {
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
		// XTableColumn<VCUS, Integer> firstColumn = (XTableColumn<VCUS, Integer>)
		// table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(DC_ID.filterableProperty());

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
			ROOT.setBottom(createOptionPane(DEATH_CERT));

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());

			DC_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			DC_DPL.setColumnFilter(new PatternColumnFilter<>());
			DFIO.setColumnFilter(new PatternColumnFilter<>());
			DBDATE.setColumnFilter(new DateColumnFilter<>());
			DC_DD.setColumnFilter(new DateColumnFilter<>());

			dbConnect();
			Refresh();
			/**
			 * Столбцы таблицы
			 */
			{
				OPER.setCellValueFactory(cellData -> cellData.getValue().DC_USRProperty());
				CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
				DC_ID.setCellValueFactory(cellData -> cellData.getValue().DC_IDProperty().asObject());
				DC_DPL.setCellValueFactory(cellData -> cellData.getValue().DC_DPLProperty());
				DFIO.setCellValueFactory(cellData -> cellData.getValue().DFIOProperty());
				DBDATE.setCellValueFactory(cellData -> cellData.getValue().DBDATEProperty());
				DC_DD.setCellValueFactory(cellData -> cellData.getValue().DC_DDProperty());
			}

			// двойной щелчок
			DEATH_CERT.setRowFactory(tv -> {
				TableRow<DEATH_CERT> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (DEATH_CERT.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите строку!");
						} else {
							Edit(DEATH_CERT.getSelectionModel().getSelectedItem().getDC_ID(),
									(Stage) DEATH_CERT.getScene().getWindow());
						}
					}
				});
				return row;
			});
			/**
			 * ФД
			 */
			{
				// CellDateFormatDT(DC_OPEN);
				CellDateFormatD(DBDATE);
				CellDateFormatD(DC_DD);
				CellDateFormatD(CR_DATE);
			}
			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private StringProperty Where;

	public void setWhere(String value) {
		this.Where.set(value);
	}

	public String getWhere() {
		return this.Where.get();
	}

	public DeathList() {
		Main.logger = Logger.getLogger(getClass());
		this.Where = new SimpleStringProperty();
	}

	/**
	 * Сравнение данных
	 * 
	 * @return
	 */
	int CompareBeforeClose(Integer docid) {
		int ret = 0;
		try {
			Clob lob = conn.createClob();
			lob.setString(1, RetXml);
			CallableStatement callStmt = conn.prepareCall("{ call Deatch.CompareXmls(?,?,?,?)}");
			callStmt.setInt(1, docid);
			callStmt.setClob(2, lob);
			callStmt.registerOutParameter(3, Types.VARCHAR);
			callStmt.registerOutParameter(4, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(3) == null) {
				ret = callStmt.getInt(4);
				System.out.println("ret=" + callStmt.getInt(4));
			} else {
				Msg.Message(callStmt.getString(3));
				Main.logger.error(callStmt.getString(6) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}

		return ret;
	}

	String RetXml;

	/**
	 * Возврат XML файлов для сравнения
	 */
	void XmlsForCompare(Integer docid) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call Deatch.RetXmls(?,?,?)}");
			callStmt.setInt(1, docid);
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
				//System.out.println(RetXml);
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.from = 1;
		this.conn.setAutoCommit(false);
	}
}
