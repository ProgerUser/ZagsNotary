package mj.doc.divorce;

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

public class DivorceList {

	@FXML
	private DatePicker DT1;

	// @FXML
	// private TableColumn<DIVORCE_CERT, LocalDateTime> DIVC_DATE;

    @FXML
    private VBox VB;

    @FXML
    private TitledPane FILTER;
    
	@FXML
	private ProgressIndicator PB;

	@FXML
	private DatePicker DT2;

	@FXML
	private BorderPane ROOT;

	
	@FXML
	private XTableColumn<DIVORCE_CERT, String> DOC_NUMBER;
	
	@FXML
	private XTableView<DIVORCE_CERT> DIVORCE_CERT;

	@FXML
	private XTableColumn<DIVORCE_CERT, String> SheFio;

	@FXML
	private XTableColumn<DIVORCE_CERT, LocalDate> DIVC_DT;

	@FXML
	private XTableColumn<DIVORCE_CERT, String> HeFio;

	@FXML
	private XTableColumn<DIVORCE_CERT, Integer> DIVC_ID;

	@FXML
	private XTableColumn<DIVORCE_CERT, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<DIVORCE_CERT, String> CR_TIME;

	@FXML
	private XTableColumn<DIVORCE_CERT, String> OPER;

	public void manipulatePdf(String src, String dest) throws Exception {
		if (DIVORCE_CERT.getSelectionModel().getSelectedItem() == null) {
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

			PreparedStatement prp = conn.prepareStatement("select * from BLANK_DIVORCE_CERT where DIVC_ID = ?");
			prp.setInt(1, DIVORCE_CERT.getSelectionModel().getSelectedItem().getDIVC_ID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("F1"));
				fields.setField("Текст2", rs.getString("F2"));
				fields.setField("Текст3", rs.getString("F3"));
				fields.setField("Текст4", rs.getString("F4"));
				fields.setField("Текст5", rs.getString("F5"));
				fields.setField("Текст6", rs.getString("F6"));
				fields.setField("Текст7", rs.getString("F7"));
				fields.setField("Текст8", rs.getString("F8"));
				fields.setField("Текст9", rs.getString("F9"));
				fields.setField("Текст10", rs.getString("F10"));
				fields.setField("Текст11", rs.getString("F11"));
				fields.setField("Текст12", rs.getString("F12"));
				fields.setField("Текст13", rs.getString("F13"));
				fields.setField("Текст14", rs.getString("F14"));
				fields.setField("Текст15", rs.getString("F15"));
				fields.setField("Текст16", rs.getString("F16"));
				fields.setField("Текст17", rs.getString("F17"));
				fields.setField("Текст18", rs.getString("F18")+" шықәсазы");
				fields.setField("Текст19", "-");
				fields.setField("Текст20", rs.getString("F20"));
				fields.setField("Текст21", "-");
				fields.setField("Текст22", "-");
				fields.setField("Текст23", rs.getString("F23"));
				fields.setField("Текст24", rs.getString("F24"));
				fields.setField("Текст25", rs.getString("F25"));
				fields.setField("Текст26", rs.getString("F26"));
				fields.setField("Текст27", "-");
				fields.setField("Текст28", rs.getString("F28"));
				fields.setField("Текст29", rs.getString("F29"));
				fields.setField("Текст30", rs.getString("F30"));
				fields.setField("Текст31", rs.getString("F31"));
				fields.setField("Текст32", rs.getString("F32"));
				fields.setField("Текст33", rs.getString("F33"));
				fields.setField("Текст34", rs.getString("F34"));
				fields.setField("Текст35", rs.getString("F35"));
				fields.setField("Текст36", rs.getString("F36"));
				fields.setField("Текст37", rs.getString("F5"));
				fields.setField("Текст38", rs.getString("F38"));
				fields.setField("Текст39", rs.getString("F7"));
				fields.setField("Текст40", rs.getString("F40"));
				fields.setField("Текст41", rs.getString("F41"));
				fields.setField("Текст42", rs.getString("F42"));
				fields.setField("Текст43", rs.getString("F43"));
				fields.setField("Текст44", rs.getString("F44"));
				fields.setField("Текст45", rs.getString("F13"));
				fields.setField("Текст46", rs.getString("F46"));
				fields.setField("Текст47", rs.getString("F15"));
				fields.setField("Текст48", rs.getString("F48"));
				fields.setField("Текст49", rs.getString("F49"));
				fields.setField("Текст50", rs.getString("F50"));
				fields.setField("Текст51", rs.getString("F51"));
				fields.setField("Текст52", rs.getString("F52"));
				fields.setField("Текст53", "-");
				fields.setField("Текст54", rs.getString("F54"));
				fields.setField("Текст55", rs.getString("F55"));
				fields.setField("Текст56", rs.getString("F56"));
				fields.setField("Текст57", rs.getString("F72"));
				fields.setField("Текст58", rs.getString("F58"));
				fields.setField("Текст59", rs.getString("F24"));
				fields.setField("Текст60", rs.getString("F60"));
				fields.setField("Текст61", "-");
				fields.setField("Текст62", rs.getString("F62"));
				fields.setField("Текст63", rs.getString("F63"));
				fields.setField("Текст64", "ЗАГС");
				fields.setField("Текст65", rs.getString("F65"));
				fields.setField("Текст66", "Республики Абхазия");
				fields.setField("Текст67", "-");
				fields.setField("Текст68", rs.getString("F68"));
				fields.setField("Текст69", rs.getString("F69"));
				fields.setField("Текст70", rs.getString("F58"));
				fields.setField("Текст71", rs.getString("F72"));
				fields.setField("Текст72", rs.getString("F72"));
			}
			prp.close();
			rs.close();

			stamper.close();
			reader.close();
		}
	}
	
	
	
	@FXML
	void Spravka_30(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_DIVORCE.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_DIVORCE where DIVC_ID = ?");
			prepStmt.setInt(1, DIVORCE_CERT.getSelectionModel().getSelectedItem().getDIVC_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{HE_FIO}", rs.getString("HE_FIO")));
				variables.addTextVariable(new TextVariable("#{SHE_FIO}", rs.getString("SHE_FIO")));
				variables.addTextVariable(new TextVariable("#{DIVC_DT}", rs.getString("DIVC_DT")));
				variables.addTextVariable(new TextVariable("#{HE_SHE_FIO}", rs.getString("HE_SHE_FIO")));
				variables.addTextVariable(new TextVariable("#{DOC_DATE}", rs.getString("DOC_DATE")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_DIVORCE", ".docx",
					new File(System.getenv("MJ_PATH") + "OutReports"));
			FileOutputStream str = new FileOutputStream(tempFile);
			docx.save(str);
			str.close();
			tempFile.deleteOnExit();
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(tempFile);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
    
    @FXML
    void BtPrintBlank(ActionEvent event) {
    	try {
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/DIVORCE_CERT.pdf",
					System.getenv("MJ_PATH") + "/OutReports/DIVORCE_CERT.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/DIVORCE_CERT.pdf"));
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
    }
    
	void Add() {
		try {
			if (DBUtil.OdbAction(98) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) DIVORCE_CERT.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/divorce/IUDivorce.fxml"));

			AddDivorce controller = new AddDivorce();
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

	void Delete() {
		try {

			if (DBUtil.OdbAction(100) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			if (DIVORCE_CERT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				Main.logger = Logger.getLogger(getClass());

				Stage stage = (Stage) DIVORCE_CERT.getScene().getWindow();
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
											+ " delete from DIVORCE_CERT where DIVC_ID = ?;" + "commit;" + "end;");
							DIVORCE_CERT cl = DIVORCE_CERT.getSelectionModel().getSelectedItem();
							delete.setInt(1, cl.getDIVC_ID());
							delete.executeUpdate();
							delete.close();
							Refresh();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								Msg.Message(ExceptionUtils.getStackTrace(e1));
								Main.logger.error(ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
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

	boolean isopen = false;

	public void Edit(Integer docid, Stage stage_) {
		try {
			if (isopen == false) {

				Main.logger = Logger.getLogger(getClass());

				if (DBUtil.OdbAction(99) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement selforupd = conn
						.prepareStatement("select * from DIVORCE_CERT where  DIVC_ID = ? for update nowait");
				DIVORCE_CERT cl = Initialize2(docid);
				selforupd.setInt(1, cl.getDIVC_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DBUtil.Lock_Row(docid, "DIVORCE_CERT",conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/divorce/IUDivorce.fxml"));

						EditDivorce controller = new EditDivorce();
						controller.setId(cl.getDIVC_ID());
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
									// update without commit
									controller.SaveTocompare();
									if (controller.getStatus()) {
										conn.commit();

										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "DIVORCE_CERT",conn);
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
												String lock = DBUtil.Lock_Row_Delete(docid, "DIVORCE_CERT",conn);
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
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "DIVORCE_CERT",conn);
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
						Msg.Message("Запись редактируется " + DBUtil.Lock_Row_View(docid, "DIVORCE_CERT"));
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
			if (DIVORCE_CERT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {
					@Override
					public Object call() throws Exception {
						// Вызов
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/DIVORCE_CERT.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from V_REP_DIVORCE_CERT where DIVC_ID = ?");
						prepStmt.setInt(1, DIVORCE_CERT.getSelectionModel().getSelectedItem().getDIVC_ID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_DIVORCE_CERT list = null;
						if (rs.next()) {
							list = new V_REP_DIVORCE_CERT();
							list.setDIVC_HE_LNBEF(rs.getString("DIVC_HE_LNBEF"));
							list.setSHE_DOC_SER(rs.getString("SHE_DOC_SER"));
							list.setHE_M_NAME(rs.getString("HE_M_NAME"));
							list.setMERCER_ZAGS(rs.getString("MERCER_ZAGS"));
							list.setDIVC_ZOSFIO2(rs.getString("DIVC_ZOSFIO2"));
							list.setDIVC_ZOSFIO(rs.getString("DIVC_ZOSFIO"));
							list.setDIVC_DATE(rs.getString("DIVC_DATE"));
							list.setDIVC_CAD(rs.getString("DIVC_CAD"));
							list.setMERCER_ID(rs.getString("MERCER_ID"));
							list.setDIVC_ZOSCD(rs.getString("DIVC_ZOSCD"));
							list.setHE_DOC_NUM(rs.getString("HE_DOC_NUM"));
							list.setHE_DOC_AGEN(rs.getString("HE_DOC_AGEN"));
							list.setMERCER_DATE(rs.getString("MERCER_DATE"));
							list.setDIVC_ZOSCN(rs.getString("DIVC_ZOSCN"));
							list.setDIVC_ID(rs.getString("DIVC_ID"));
							list.setHE_F_NAME(rs.getString("HE_F_NAME"));
							list.setSHE_L_NAME(rs.getString("SHE_L_NAME"));
							list.setSHE_DOC_NUM(rs.getString("SHE_DOC_NUM"));
							list.setHE_CIT(rs.getString("HE_CIT"));
							list.setSHE_BR_PL(rs.getString("SHE_BR_PL"));
							list.setSHE_M_NAME(rs.getString("SHE_M_NAME"));
							list.setHE_NAT(rs.getString("HE_NAT"));
							list.setSHE_F_NAME(rs.getString("SHE_F_NAME"));
							list.setSHE_NAT(rs.getString("SHE_NAT"));
							list.setDIVC_ZOSCN2(rs.getString("DIVC_ZOSCN2"));
							list.setDIVC_ZOSPRISON(rs.getString("DIVC_ZOSPRISON"));
							list.setSHE_DOC_AGEN(rs.getString("SHE_DOC_AGEN"));
							list.setDIVC_CAN(rs.getString("DIVC_CAN"));
							list.setDIVC_TCHD(rs.getString("DIVC_TCHD"));
							list.setZAYAVIT(rs.getString("ZAYAVIT"));
							list.setDIVC_DT(rs.getString("DIVC_DT"));
							list.setDIVC_SHE_LNAFT(rs.getString("DIVC_SHE_LNAFT"));
							list.setSHE_CIT(rs.getString("SHE_CIT"));
							list.setHE_BR_PL(rs.getString("HE_BR_PL"));
							list.setHE_ADDR(rs.getString("HE_ADDR"));
							list.setHE_DOC_SER(rs.getString("HE_DOC_SER"));
							list.setDIVC_ZOSCD2(rs.getString("DIVC_ZOSCD2"));
							list.setDIVC_TCHNUM(rs.getString("DIVC_TCHNUM"));
							list.setDIV_NUM(rs.getString("DIV_NUM"));
							list.setDIV_SER(rs.getString("DIV_SER"));
							list.setSHE_ADDR(rs.getString("SHE_ADDR"));
							list.setHE_L_NAME(rs.getString("HE_L_NAME"));
							list.setDIVC_HE_LNAFT(rs.getString("DIVC_HE_LNAFT"));
							list.setDIVC_SHE_LNBEF(rs.getString("DIVC_SHE_LNBEF"));
						}
						prepStmt.close();
						rs.close();
						// ------------
						variables.addTextVariable(new TextVariable("#{DIVC_HE_LNBEF}", list.getDIVC_HE_LNBEF()));
						variables.addTextVariable(new TextVariable("#{SHE_DOC_SER}", list.getSHE_DOC_SER()));
						variables.addTextVariable(new TextVariable("#{HE_M_NAME}", list.getHE_M_NAME()));
						variables.addTextVariable(new TextVariable("#{MERCER_ZAGS}", list.getMERCER_ZAGS()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSFIO2}", list.getDIVC_ZOSFIO2()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSFIO}", list.getDIVC_ZOSFIO()));
						variables.addTextVariable(new TextVariable("#{DIVC_DATE}", list.getDIVC_DATE()));
						variables.addTextVariable(new TextVariable("#{DIVC_CAD}", list.getDIVC_CAD()));
						variables.addTextVariable(new TextVariable("#{MERCER_ID}", list.getMERCER_ID()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSCD}", list.getDIVC_ZOSCD()));
						variables.addTextVariable(new TextVariable("#{HE_DOC_NUM}", list.getHE_DOC_NUM()));
						variables.addTextVariable(new TextVariable("#{HE_DOC_AGEN}", list.getHE_DOC_AGEN()));
						variables.addTextVariable(new TextVariable("#{MERCER_DATE}", list.getMERCER_DATE()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSCN}", list.getDIVC_ZOSCN()));
						variables.addTextVariable(new TextVariable("#{DIVC_ID}", list.getDIVC_ID()));
						variables.addTextVariable(new TextVariable("#{HE_F_NAME}", list.getHE_F_NAME()));
						variables.addTextVariable(new TextVariable("#{SHE_L_NAME}", list.getSHE_L_NAME()));
						variables.addTextVariable(new TextVariable("#{SHE_DOC_NUM}", list.getSHE_DOC_NUM()));
						variables.addTextVariable(new TextVariable("#{HE_CIT}", list.getHE_CIT()));
						variables.addTextVariable(new TextVariable("#{SHE_BR_PL}", list.getSHE_BR_PL()));
						variables.addTextVariable(new TextVariable("#{SHE_M_NAME}", list.getSHE_M_NAME()));
						variables.addTextVariable(new TextVariable("#{HE_NAT}", list.getHE_NAT()));
						variables.addTextVariable(new TextVariable("#{SHE_F_NAME}", list.getSHE_F_NAME()));
						variables.addTextVariable(new TextVariable("#{SHE_NAT}", list.getSHE_NAT()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSCN2}", list.getDIVC_ZOSCN2()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSPRISON}", list.getDIVC_ZOSPRISON()));
						variables.addTextVariable(new TextVariable("#{SHE_DOC_AGEN}", list.getSHE_DOC_AGEN()));
						variables.addTextVariable(new TextVariable("#{DIVC_CAN}", list.getDIVC_CAN()));
						variables.addTextVariable(new TextVariable("#{DIVC_TCHD}", list.getDIVC_TCHD()));
						variables.addTextVariable(new TextVariable("#{ZAYAVIT}", list.getZAYAVIT()));
						variables.addTextVariable(new TextVariable("#{DIVC_DT}", list.getDIVC_DT()));
						variables.addTextVariable(new TextVariable("#{DIVC_SHE_LNAFT}", list.getDIVC_SHE_LNAFT()));
						variables.addTextVariable(new TextVariable("#{SHE_CIT}", list.getSHE_CIT()));
						variables.addTextVariable(new TextVariable("#{HE_BR_PL}", list.getHE_BR_PL()));
						variables.addTextVariable(new TextVariable("#{HE_ADDR}", list.getHE_ADDR()));
						variables.addTextVariable(new TextVariable("#{DIVC_ZOSCD2}", list.getDIVC_ZOSCD2()));
						variables.addTextVariable(new TextVariable("#{HE_DOC_SER}", list.getHE_DOC_SER()));
						variables.addTextVariable(new TextVariable("#{DIV_NUM}", list.getDIV_NUM()));
						variables.addTextVariable(new TextVariable("#{DIVC_TCHNUM}", list.getDIVC_TCHNUM()));
						variables.addTextVariable(new TextVariable("#{DIV_SER}", list.getDIV_SER()));
						variables.addTextVariable(new TextVariable("#{SHE_ADDR}", list.getSHE_ADDR()));
						variables.addTextVariable(new TextVariable("#{HE_L_NAME}", list.getHE_L_NAME()));
						variables.addTextVariable(new TextVariable("#{DIVC_HE_LNAFT}", list.getDIVC_HE_LNAFT()));
						variables.addTextVariable(new TextVariable("#{DIVC_SHE_LNBEF}", list.getDIVC_SHE_LNBEF()));

						// fill template
						docx.fillTemplate(variables);
						/*
						 * // save filled .docx file UUID uuid = UUID.randomUUID(); String filename =
						 * System.getenv("MJ_PATH") + "OutReports/DIVORCE_CERT_" + uuid.toString() +
						 * ".docx"; docx.save(filename);
						 * 
						 * if (Desktop.isDesktopSupported()) { Desktop.getDesktop().open(new
						 * File(filename)); }
						 */
						File tempFile = File.createTempFile("DIVORCE_CERT", ".docx",
								new File(System.getenv("MJ_PATH") + "OutReports"));
						FileOutputStream str = new FileOutputStream(tempFile);
						docx.save(str);
						str.close();
						tempFile.deleteOnExit();
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(tempFile);
						}
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

	void Refresh() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

			String selectStmt = "select * from VDIVORCE_CERT t " + ((getWhere() != null) ? getWhere() : " ORDER BY DIVC_ID DESC");

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DIVORCE_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				DIVORCE_CERT list = new DIVORCE_CERT();

				list.setDIVC_SHE_LNBEF(rs.getString("DIVC_SHE_LNBEF"));
				list.setDIVC_ZMNAME(rs.getString("DIVC_ZMNAME"));
				list.setDIVC_ZOSCN2(rs.getInt("DIVC_ZOSCN2"));
				list.setDIVC_NUM(rs.getString("DIVC_NUM"));
				list.setSHEFIO(rs.getString("SHEFIO"));
				list.setDIVC_ZOSPRISON(rs.getInt("DIVC_ZOSPRISON"));
				list.setDIVC_ZАNAME(rs.getString("DIVC_ZАNAME"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setDIVC_SERIA(rs.getString("DIVC_SERIA"));
				list.setDIVC_ZLNAME(rs.getString("DIVC_ZLNAME"));
				list.setDIVC_ID(rs.getInt("DIVC_ID"));
				list.setDIVC_USR(rs.getString("DIVC_USR"));
				list.setDIVC_CAN(rs.getInt("DIVC_CAN"));
				list.setDIVC_MC_MERCER(rs.getInt("DIVC_MC_MERCER"));
				list.setDIVC_HE_LNBEF(rs.getString("DIVC_HE_LNBEF"));
				list.setDIVC_SHE_LNAFT(rs.getString("DIVC_SHE_LNAFT"));
				list.setDIVC_ZOSCD2((rs.getDate("DIVC_ZOSCD2") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD2")), formatter) : null);
				list.setDIVC_CAD((rs.getDate("DIVC_CAD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_CAD")), formatter)
						: null);
				list.setDIVC_ZOSFIO(rs.getString("DIVC_ZOSFIO"));
				list.setDIVC_DT((rs.getDate("DIVC_DT") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_DT")), formatter)
						: null);
				list.setDIVC_ZOSCN(rs.getInt("DIVC_ZOSCN"));
				list.setDIVC_TYPE(rs.getString("DIVC_TYPE"));
				list.setDIVC_ZOSFIO2(rs.getString("DIVC_ZOSFIO2"));
				list.setDIVC_ZPLACE(rs.getString("DIVC_ZPLACE"));
				list.setDIVC_HE(rs.getInt("DIVC_HE"));
				list.setTM$DIVC_DATE((rs.getDate("TM$DIVC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DIVC_DATE")), formatterwt)
						: null);
				list.setDIVC_TCHNUM(rs.getString("DIVC_TCHNUM"));
				list.setDIVC_TCHD((rs.getDate("DIVC_TCHD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_TCHD")), formatter)
						: null);
				list.setDIVC_SHE(rs.getInt("DIVC_SHE"));
				list.setDIVC_HE_LNAFT(rs.getString("DIVC_HE_LNAFT"));
				list.setDIVC_ZAGS(rs.getInt("DIVC_ZAGS"));
				list.setDIVC_ZOSCD((rs.getDate("DIVC_ZOSCD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD")), formatter) : null);
				list.setHEFIO(rs.getString("HEFIO"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			DIVORCE_CERT.setItems(dlist);

			TableFilter<DIVORCE_CERT> tableFilter = TableFilter.forTableView(DIVORCE_CERT).apply();
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
		if (DIVORCE_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(DIVORCE_CERT.getSelectionModel().getSelectedItem().getDIVC_ID(),
					(Stage) DIVORCE_CERT.getScene().getWindow());
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

		if (DIVORCE_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(DIVORCE_CERT.getSelectionModel().getSelectedItem().getDIVC_ID(),
					(Stage) DIVORCE_CERT.getScene().getWindow());
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

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "DivorceList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	DIVORCE_CERT Initialize2(Integer docid) {
		DIVORCE_CERT list = null;
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

			String selectStmt = "select * from VDIVORCE_CERT t where DIVC_ID = ? ";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setInt(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DIVORCE_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new DIVORCE_CERT();
				list.setDIVC_SHE_LNBEF(rs.getString("DIVC_SHE_LNBEF"));
				list.setDIVC_ZMNAME(rs.getString("DIVC_ZMNAME"));
				list.setDIVC_ZOSCN2(rs.getInt("DIVC_ZOSCN2"));
				list.setDIVC_NUM(rs.getString("DIVC_NUM"));
				list.setSHEFIO(rs.getString("SHEFIO"));
				list.setDIVC_ZOSPRISON(rs.getInt("DIVC_ZOSPRISON"));
				list.setDIVC_ZАNAME(rs.getString("DIVC_ZАNAME"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setDIVC_SERIA(rs.getString("DIVC_SERIA"));
				list.setDIVC_ZLNAME(rs.getString("DIVC_ZLNAME"));
				list.setDIVC_ID(rs.getInt("DIVC_ID"));
				list.setDIVC_USR(rs.getString("DIVC_USR"));
				list.setDIVC_CAN(rs.getInt("DIVC_CAN"));
				list.setDIVC_MC_MERCER(rs.getInt("DIVC_MC_MERCER"));
				list.setDIVC_HE_LNBEF(rs.getString("DIVC_HE_LNBEF"));
				list.setDIVC_SHE_LNAFT(rs.getString("DIVC_SHE_LNAFT"));
				list.setDIVC_ZOSCD2((rs.getDate("DIVC_ZOSCD2") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD2")), formatter) : null);
				list.setDIVC_CAD((rs.getDate("DIVC_CAD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_CAD")), formatter)
						: null);
				list.setDIVC_ZOSFIO(rs.getString("DIVC_ZOSFIO"));
				list.setDIVC_DT((rs.getDate("DIVC_DT") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_DT")), formatter)
						: null);
				list.setDIVC_ZOSCN(rs.getInt("DIVC_ZOSCN"));
				list.setDIVC_TYPE(rs.getString("DIVC_TYPE"));
				list.setDIVC_ZOSFIO2(rs.getString("DIVC_ZOSFIO2"));
				list.setDIVC_ZPLACE(rs.getString("DIVC_ZPLACE"));
				list.setDIVC_HE(rs.getInt("DIVC_HE"));
				list.setTM$DIVC_DATE((rs.getDate("TM$DIVC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DIVC_DATE")), formatterwt)
						: null);
				list.setDIVC_TCHNUM(rs.getString("DIVC_TCHNUM"));
				list.setDIVC_TCHD((rs.getDate("DIVC_TCHD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_TCHD")), formatter)
						: null);
				list.setDIVC_SHE(rs.getInt("DIVC_SHE"));
				list.setDIVC_HE_LNAFT(rs.getString("DIVC_HE_LNAFT"));
				list.setDIVC_ZAGS(rs.getInt("DIVC_ZAGS"));
				list.setDIVC_ZOSCD((rs.getDate("DIVC_ZOSCD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD")), formatter) : null);
				list.setHEFIO(rs.getString("HEFIO"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return list;
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

	void CellDateFormatDT(TableColumn<DIVORCE_CERT, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<DIVORCE_CERT, LocalDateTime> cell = new TableCell<DIVORCE_CERT, LocalDateTime>() {
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

	void CellDateFormatD(TableColumn<DIVORCE_CERT, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<DIVORCE_CERT, LocalDate> cell = new TableCell<DIVORCE_CERT, LocalDate>() {
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
		firstFilterable.selectedProperty().bindBidirectional(DIVC_ID.filterableProperty());

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
			ROOT.setBottom(createOptionPane(DIVORCE_CERT));

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			DIVC_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			SheFio.setColumnFilter(new PatternColumnFilter<>());
			HeFio.setColumnFilter(new PatternColumnFilter<>());
			DIVC_DT.setColumnFilter(new DateColumnFilter<>());
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			
			dbConnect();
			DBUtil.RunProcess(conn);
			Refresh();

			// Столбцы таблицы
			DIVC_ID.setCellValueFactory(cellData -> cellData.getValue().DIVC_IDProperty().asObject());
			CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
			CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
			OPER.setCellValueFactory(cellData -> cellData.getValue().DIVC_USRProperty());
			HeFio.setCellValueFactory(cellData -> cellData.getValue().HEFIOProperty());
			SheFio.setCellValueFactory(cellData -> cellData.getValue().SHEFIOProperty());
			DIVC_DT.setCellValueFactory(cellData -> cellData.getValue().DIVC_DTProperty());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			// двойной щелчок
			DIVORCE_CERT.setRowFactory(tv -> {
				TableRow<DIVORCE_CERT> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (DIVORCE_CERT.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите строку!");
						} else {
							Edit(DIVORCE_CERT.getSelectionModel().getSelectedItem().getDIVC_ID(),
									(Stage) DIVORCE_CERT.getScene().getWindow());
						}
					}
				});
				return row;
			});
			// Форматирование дат
			// CellDateFormatDT(DIVC_DATE);
			CellDateFormatD(DIVC_DT);
			CellDateFormatD(CR_DATE);

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

	public DivorceList() {
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
			CallableStatement callStmt = conn.prepareCall("{ call Divorce.CompareXmls(?,?,?,?)}");
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
			CallableStatement callStmt = conn.prepareCall("{ call Divorce.RetXmls(?,?,?)}");
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
				// System.out.println(RetXml);
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
