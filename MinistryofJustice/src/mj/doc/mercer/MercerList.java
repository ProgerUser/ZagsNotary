package mj.doc.mercer;

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
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class MercerList {

	@FXML
	private DatePicker DT1;

	// @FXML
	// private XTableColumn<MC_MERCER, LocalDateTime> MERCER_DATE;

	@FXML
	private ProgressIndicator PB;

	@FXML
	private DatePicker DT2;

	@FXML
	private BorderPane ROOT;

	@FXML
	private XTableView<MC_MERCER> MC_MERCER;

	@FXML
	private XTableColumn<MC_MERCER, String> DOC_NUMBER;
	
	@FXML
	private XTableColumn<MC_MERCER, String> SheFio;

	@FXML
	private XTableColumn<MC_MERCER, Integer> MERCER_ID;

	@FXML
	private XTableColumn<MC_MERCER, String> OPER;

	@FXML
	private XTableColumn<MC_MERCER, String> HeFio;

	@FXML
	private XTableColumn<MC_MERCER, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<MC_MERCER, String> CR_TIME;

	
	@FXML
	void Spravka_28(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_MC_MERCER_28.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_MC_MERCER where MERCER_ID = ?");
			prepStmt.setInt(1, MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{HE_FIO}", rs.getString("HE_FIO")));
				variables.addTextVariable(new TextVariable("#{SHE_FIO}", rs.getString("SHE_FIO")));
				variables.addTextVariable(new TextVariable("#{HE_CIT}", rs.getString("HE_CIT")));
				variables.addTextVariable(new TextVariable("#{SHE_CIT}", rs.getString("SHE_CIT")));
				variables.addTextVariable(new TextVariable("#{MC_DATE}", rs.getString("MC_DATE")));
				variables.addTextVariable(new TextVariable("#{SHE_AFT_LNAME}", rs.getString("SHE_AFT_LNAME")));
				variables.addTextVariable(new TextVariable("#{HE_AFT_LNAME}", rs.getString("HE_AFT_LNAME")));
				variables.addTextVariable(new TextVariable("#{DOC_DATE}", rs.getString("DOC_DATE")));
				
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_MC_MERCER_28", ".docx",
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
	void Spravka_29(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_MC_MERCER_29.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_MC_MERCER where MERCER_ID = ?");
			prepStmt.setInt(1, MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{HE_FIO}", rs.getString("HE_FIO")));
				variables.addTextVariable(new TextVariable("#{SHE_FIO}", rs.getString("SHE_FIO")));
				variables.addTextVariable(new TextVariable("#{HE_CIT}", rs.getString("HE_CIT")));
				variables.addTextVariable(new TextVariable("#{SHE_CIT}", rs.getString("SHE_CIT")));
				variables.addTextVariable(new TextVariable("#{MC_DATE}", rs.getString("MC_DATE")));
				variables.addTextVariable(new TextVariable("#{SHE_AFT_LNAME}", rs.getString("SHE_AFT_LNAME")));
				variables.addTextVariable(new TextVariable("#{HE_AFT_LNAME}", rs.getString("HE_AFT_LNAME")));
				variables.addTextVariable(new TextVariable("#{DOC_DATE}", rs.getString("DOC_DATE")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_MC_MERCER_29", ".docx",
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
	
	void Add() {
		try {

			if (DBUtil.OdbAction(95) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) MC_MERCER.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/mercer/IUMercer.fxml"));

			AddMercer controller = new AddMercer();
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

			if (DBUtil.OdbAction(97) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			if (MC_MERCER.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				Main.logger = Logger.getLogger(getClass());

				Stage stage = (Stage) MC_MERCER.getScene().getWindow();
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
											+ " delete from MC_MERCER where MERCER_ID = ?;" + "commit;" + "end;");
							MC_MERCER cl = MC_MERCER.getSelectionModel().getSelectedItem();
							delete.setInt(1, cl.getMERCER_ID());
							delete.executeUpdate();
							delete.close();
							Refresh();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DBUtil.LOG_ERROR(e1);
							}
							DBUtil.LOG_ERROR(e);
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

	MC_MERCER Initialize2(Integer docid) {
		MC_MERCER list = null;
		try {
			
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String selectStmt = "select * from vmc_mercer t\n where MERCER_ID = ? ";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setInt(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<MC_MERCER> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new MC_MERCER();
				list.setTM$MERCER_DATE((rs.getDate("TM$MERCER_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$MERCER_DATE")), formatterwt)
						: null);
				list.setMERCER_DIESHE(rs.getInt("MERCER_DIESHE"));
				list.setMERCER_DIVSHE(rs.getInt("MERCER_DIVSHE"));
				list.setMERCER_DIEHE(rs.getInt("MERCER_DIEHE"));
				list.setMERCER_USR(rs.getString("MERCER_USR"));
				list.setMERCER_HEAGE(rs.getInt("MERCER_HEAGE"));
				list.setMERCER_HE(rs.getInt("MERCER_HE"));
				list.setMERCER_NUM(rs.getString("MERCER_NUM"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
				list.setMERCER_DIVHE(rs.getInt("MERCER_DIVHE"));
				list.setMERCER_DSPMT_SHE(rs.getString("MERCER_DSPMT_SHE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setMERCER_DSPMT_HE(rs.getString("MERCER_DSPMT_HE"));
				list.setMERCER_SERIA(rs.getString("MERCER_SERIA"));
				list.setMERCER_OTHER(rs.getString("MERCER_OTHER"));
				list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
				list.setMERCER_ID(rs.getInt("MERCER_ID"));
				list.setHEFIO(rs.getString("HEFIO"));
				list.setMERCER_SHEAGE(rs.getInt("MERCER_SHEAGE"));
				list.setSHEFIO(rs.getString("SHEFIO"));
				list.setMERCER_SHE(rs.getInt("MERCER_SHE"));
				list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
				list.setMERCER_ZAGS(rs.getInt("MERCER_ZAGS"));
				list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
				list.setMC_DATE((rs.getDate("MC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("MC_DATE")), formatter)
						: null);
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

	public void Edit(Integer docid, Stage stage_) {
		try {
			if (isopen == false) {

				if (DBUtil.OdbAction(96) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement selforupd = conn
						.prepareStatement("select * from MC_MERCER where  MERCER_ID = ? for update nowait");
				MC_MERCER cl = Initialize2(docid);
				selforupd.setInt(1, cl.getMERCER_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DBUtil.Lock_Row(docid, "MC_MERCER",conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/mercer/IUMercer.fxml"));

						EditMercer controller = new EditMercer();
						controller.setId(cl.getMERCER_ID());
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
									controller.SaveCompare();
									if (controller.getStatus()) {
										conn.commit();
										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "MC_MERCER",conn);
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
												String lock = DBUtil.Lock_Row_Delete(docid, "MC_MERCER",conn);
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
									}// Если нажали "X" или "Cancel" и до этого ничего не меняли
									else if (!controller.getStatus() & CompareBeforeClose(docid) == 0) {
										conn.rollback();
										isopen = false;
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "MC_MERCER",conn);
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
						Msg.Message("Запись редактируется " + DBUtil.Lock_Row_View(docid, "MC_MERCER"));
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

	void Print() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (MC_MERCER.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {
					@Override
					public Object call() throws Exception {
						// Вызов
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/MC_MERCER.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from V_REP_MC_MERCER where MERCER_ID = ?");
						prepStmt.setInt(1, MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_MC_MERCER list = null;
						if (rs.next()) {
							list = new V_REP_MC_MERCER();
							list.setHE_CIT(rs.getString("HE_CIT"));
							list.setHE_DC_ID(rs.getString("HE_DC_ID"));
							list.setHE_DIVC_DATE(rs.getString("HE_DIVC_DATE"));
							list.setHE_DC_OPEN(rs.getString("HE_DC_OPEN"));
							list.setSHE_DOC_SER(rs.getString("SHE_DOC_SER"));
							list.setSHE_DOC_AGEN(rs.getString("SHE_DOC_AGEN"));
							list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
							list.setSHE_CIT(rs.getString("SHE_CIT"));
							list.setSHE_DOC_NUM(rs.getString("SHE_DOC_NUM"));
							list.setHE_DOC_NUM(rs.getString("HE_DOC_NUM"));
							list.setSHE_BR_PL(rs.getString("SHE_BR_PL"));
							list.setSHE_DIVC_ID(rs.getString("SHE_DIVC_ID"));
							list.setSHE_DC_ID(rs.getString("SHE_DC_ID"));
							list.setHE_DOC_AGEN(rs.getString("HE_DOC_AGEN"));
							list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
							list.setSHE_L_NAME(rs.getString("SHE_L_NAME"));
							list.setHE_F_NAME(rs.getString("HE_F_NAME"));
							list.setSHE_NAT(rs.getString("SHE_NAT"));
							list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
							list.setHE_DOC_SER(rs.getString("HE_DOC_SER"));
							list.setSHE_DC_OPEN(rs.getString("SHE_DC_OPEN"));
							list.setHE_ADDR(rs.getString("HE_ADDR"));
							list.setSHE_DIVC_DATE(rs.getString("SHE_DIVC_DATE"));
							list.setHE_DIVC_ID(rs.getString("HE_DIVC_ID"));
							list.setSHE_DIVC_ZAGS(rs.getString("SHE_DIVC_ZAGS"));
							list.setHE_DC_ZAGS(rs.getString("HE_DC_ZAGS"));
							list.setSHE_F_NAME(rs.getString("SHE_F_NAME"));
							list.setMERCER_DATE(rs.getString("MERCER_DATE"));
							list.setHE_DIVC_ZAGS(rs.getString("HE_DIVC_ZAGS"));
							list.setMERCER_SERIA(rs.getString("MERCER_SERIA"));
							list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
							list.setMERCER_ID(rs.getString("MERCER_ID"));
							list.setHE_NAT(rs.getString("HE_NAT"));
							list.setSHE_ADDR(rs.getString("SHE_ADDR"));
							list.setMERCER_NUM(rs.getString("MERCER_NUM"));
							list.setSHE_M_NAME(rs.getString("SHE_M_NAME"));
							list.setHE_L_NAME(rs.getString("HE_L_NAME"));
							list.setHE_M_NAME(rs.getString("HE_M_NAME"));
							list.setHE_BR_PL(rs.getString("HE_BR_PL"));
							list.setSHE_DC_ZAGS(rs.getString("SHE_DC_ZAGS"));
						}
						prepStmt.close();
						rs.close();
						// ------------
						variables.addTextVariable(new TextVariable("#{HE_CIT}", list.getHE_CIT()));
						variables.addTextVariable(new TextVariable("#{HE_DC_ID}", list.getHE_DC_ID()));
						variables.addTextVariable(new TextVariable("#{HE_DIVC_DATE}", list.getHE_DIVC_DATE()));
						variables.addTextVariable(new TextVariable("#{HE_DC_OPEN}", list.getHE_DC_OPEN()));
						variables.addTextVariable(new TextVariable("#{SHE_DOC_SER}", list.getSHE_DOC_SER()));
						variables.addTextVariable(new TextVariable("#{SHE_DOC_AGEN}", list.getSHE_DOC_AGEN()));
						variables.addTextVariable(new TextVariable("#{MERCER_SHE_LNBEF}", list.getMERCER_SHE_LNBEF()));
						variables.addTextVariable(new TextVariable("#{SHE_CIT}", list.getSHE_CIT()));
						variables.addTextVariable(new TextVariable("#{SHE_DOC_NUM}", list.getSHE_DOC_NUM()));
						variables.addTextVariable(new TextVariable("#{HE_DOC_NUM}", list.getHE_DOC_NUM()));
						variables.addTextVariable(new TextVariable("#{SHE_BR_PL}", list.getSHE_BR_PL()));
						variables.addTextVariable(new TextVariable("#{SHE_DIVC_ID}", list.getSHE_DIVC_ID()));
						variables.addTextVariable(new TextVariable("#{SHE_DC_ID}", list.getSHE_DC_ID()));
						variables.addTextVariable(new TextVariable("#{HE_DOC_AGEN}", list.getHE_DOC_AGEN()));
						variables.addTextVariable(new TextVariable("#{MERCER_HE_LNAFT}", list.getMERCER_HE_LNAFT()));
						variables.addTextVariable(new TextVariable("#{SHE_L_NAME}", list.getSHE_L_NAME()));
						variables.addTextVariable(new TextVariable("#{HE_F_NAME}", list.getHE_F_NAME()));
						variables.addTextVariable(new TextVariable("#{SHE_NAT}", list.getSHE_NAT()));
						variables.addTextVariable(new TextVariable("#{MERCER_HE_LNBEF}", list.getMERCER_HE_LNBEF()));
						variables.addTextVariable(new TextVariable("#{HE_DOC_SER}", list.getHE_DOC_SER()));
						variables.addTextVariable(new TextVariable("#{SHE_DC_OPEN}", list.getSHE_DC_OPEN()));
						variables.addTextVariable(new TextVariable("#{HE_ADDR}", list.getHE_ADDR()));
						variables.addTextVariable(new TextVariable("#{SHE_DIVC_DATE}", list.getSHE_DIVC_DATE()));
						variables.addTextVariable(new TextVariable("#{HE_DIVC_ID}", list.getHE_DIVC_ID()));
						variables.addTextVariable(new TextVariable("#{SHE_DIVC_ZAGS}", list.getSHE_DIVC_ZAGS()));
						variables.addTextVariable(new TextVariable("#{HE_DC_ZAGS}", list.getHE_DC_ZAGS()));
						variables.addTextVariable(new TextVariable("#{SHE_F_NAME}", list.getSHE_F_NAME()));
						variables.addTextVariable(new TextVariable("#{MERCER_DATE}", list.getMERCER_DATE()));
						variables.addTextVariable(new TextVariable("#{HE_DIVC_ZAGS}", list.getHE_DIVC_ZAGS()));
						variables.addTextVariable(new TextVariable("#{MERCER_SERIA}", list.getMERCER_SERIA()));
						variables
								.addTextVariable(new TextVariable("#{MERCER_SHE_LNBAFT}", list.getMERCER_SHE_LNBAFT()));
						variables.addTextVariable(new TextVariable("#{MERCER_ID}", list.getMERCER_ID()));
						variables.addTextVariable(new TextVariable("#{HE_NAT}", list.getHE_NAT()));
						variables.addTextVariable(new TextVariable("#{SHE_ADDR}", list.getSHE_ADDR()));
						variables.addTextVariable(new TextVariable("#{MERCER_NUM}", list.getMERCER_NUM()));
						variables.addTextVariable(new TextVariable("#{SHE_M_NAME}", list.getSHE_M_NAME()));
						variables.addTextVariable(new TextVariable("#{HE_L_NAME}", list.getHE_L_NAME()));
						variables.addTextVariable(new TextVariable("#{HE_M_NAME}", list.getHE_M_NAME()));
						variables.addTextVariable(new TextVariable("#{SHE_DC_ZAGS}", list.getSHE_DC_ZAGS()));
						variables.addTextVariable(new TextVariable("#{HE_BR_PL}", list.getHE_BR_PL()));
						// fill template
						docx.fillTemplate(variables);
						/*
						 * // save filled ".docx" file UUID uuid = UUID.randomUUID(); String filename =
						 * System.getenv("MJ_PATH") + "OutReports/MC_MERCER_" + uuid.toString() +
						 * ".docx"; docx.save(filename);
						 * 
						 * if (Desktop.isDesktopSupported()) { Desktop.getDesktop().open(new
						 * File(filename)); }
						 */
						File tempFile = File.createTempFile("MC_MERCER", ".docx",
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
			String selectStmt = "select * from vmc_mercer t\n " + ((getWhere() != null) ? getWhere() : " order by MERCER_ID desc");

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<MC_MERCER> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				MC_MERCER list = new MC_MERCER();
				list.setTM$MERCER_DATE((rs.getDate("TM$MERCER_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$MERCER_DATE")), formatterwt)
						: null);
				list.setMERCER_DIESHE(rs.getInt("MERCER_DIESHE"));
				list.setMERCER_DIVSHE(rs.getInt("MERCER_DIVSHE"));
				list.setMERCER_DIEHE(rs.getInt("MERCER_DIEHE"));
				list.setMERCER_USR(rs.getString("MERCER_USR"));
				list.setMERCER_HEAGE(rs.getInt("MERCER_HEAGE"));
				list.setMERCER_HE(rs.getInt("MERCER_HE"));
				list.setMERCER_NUM(rs.getString("MERCER_NUM"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
				list.setMERCER_DIVHE(rs.getInt("MERCER_DIVHE"));
				list.setMERCER_DSPMT_SHE(rs.getString("MERCER_DSPMT_SHE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setMERCER_DSPMT_HE(rs.getString("MERCER_DSPMT_HE"));
				list.setMERCER_SERIA(rs.getString("MERCER_SERIA"));
				list.setMERCER_OTHER(rs.getString("MERCER_OTHER"));
				list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
				list.setMERCER_ID(rs.getInt("MERCER_ID"));
				list.setHEFIO(rs.getString("HEFIO"));
				list.setMERCER_SHEAGE(rs.getInt("MERCER_SHEAGE"));
				list.setSHEFIO(rs.getString("SHEFIO"));
				list.setMERCER_SHE(rs.getInt("MERCER_SHE"));
				list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
				list.setMERCER_ZAGS(rs.getInt("MERCER_ZAGS"));
				list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
				list.setMC_DATE((rs.getDate("MC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("MC_DATE")), formatter)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			MC_MERCER.setItems(dlist);

			TableFilter<MC_MERCER> tableFilter = TableFilter.forTableView(MC_MERCER).apply();
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
		if (MC_MERCER.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID(),
					(Stage) MC_MERCER.getScene().getWindow());
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
		if (MC_MERCER.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID(),
					(Stage) MC_MERCER.getScene().getWindow());
		}
	}

	@FXML
	void BtDelete(ActionEvent event) {
		Delete();
	}
	
	public void manipulatePdf(String src, String dest) throws Exception {
		if (MC_MERCER.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			AcroFields fields = stamper.getAcroFields();

//			Iterator it = fields.getFields().entrySet().iterator();
//			while (it.hasNext()) {
//				Map.Entry pair = (Map.Entry) it.next();
//				System.out.println(pair.getKey() + " = " + pair.getValue());
//				it.remove(); // avoids a ConcurrentModificationException
//			}

			PreparedStatement prp = conn.prepareStatement("select * from BLANK_MC_MERCER where MERCER_ID = ?");
			prp.setInt(1, MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("F1"));
				fields.setField("Текст2", rs.getString("F2"));
				fields.setField("Текст3", rs.getString("F3"));
				fields.setField("Текст6", rs.getString("F6"));
				fields.setField("Текст7", rs.getString("F7"));
				fields.setField("Текст9", rs.getString("F9"));
				fields.setField("Текст10", rs.getString("F10"));
				fields.setField("Текст11", rs.getString("F11"));
				fields.setField("Текст12", rs.getString("F12"));
				fields.setField("Текст13", rs.getString("F13"));
				fields.setField("Текст14", rs.getString("F14"));
				fields.setField("Текст15", rs.getString("F15"));
				fields.setField("Текст16", rs.getString("F16"));
				fields.setField("Текст17", rs.getString("F17"));
				fields.setField("Текст18", rs.getString("F18"));
				fields.setField("Текст19", rs.getString("F19"));
				fields.setField("Текст20", rs.getString("F20"));
				fields.setField("Текст21", rs.getString("F21"));
				fields.setField("Текст22", "-");
				fields.setField("Текст24", "-");
				fields.setField("Текст25", rs.getString("F25"));
				fields.setField("Текст26", rs.getString("F26"));
				fields.setField("Текст27", rs.getString("F6"));
				fields.setField("Текст28", rs.getString("F14"));
				fields.setField("Текст30", rs.getString("F30"));
				fields.setField("Текст31", rs.getString("F31"));
				fields.setField("Текст33", rs.getString("F26"));
				fields.setField("Текст34", rs.getString("F1"));
				fields.setField("Текст35", rs.getString("F35"));
				fields.setField("Текст36", rs.getString("F36"));
				fields.setField("Текст37", rs.getString("F37"));
				fields.setField("Текст38", rs.getString("F38"));
				fields.setField("Текст39", rs.getString("F11"));
				fields.setField("Текст40", rs.getString("F39"));
				fields.setField("Текст41", rs.getString("F64"));
				fields.setField("Текст42", rs.getString("F42"));
				fields.setField("Текст43", rs.getString("F43"));
				fields.setField("Текст44", rs.getString("F44"));
				fields.setField("Текст45", rs.getString("F45"));
				fields.setField("Текст46", rs.getString("F46"));
				fields.setField("Текст47", rs.getString("F18"));
				fields.setField("Текст48", rs.getString("F48"));
				fields.setField("Текст49", rs.getString("F49"));
				fields.setField("Текст51", rs.getString("F51"));
				fields.setField("Текст52", rs.getString("F52"));
				fields.setField("Текст53", rs.getString("F25"));
				fields.setField("Текст54", rs.getString("F54"));
				fields.setField("Текст55", rs.getString("F1"));
				fields.setField("Текст56", "-");
				fields.setField("Текст57", rs.getString("F35"));
				fields.setField("Текст58", rs.getString("F43"));
				fields.setField("Текст59", "ЗАГС");
				fields.setField("Текст61", rs.getString("F61"));
				fields.setField("Текст63", rs.getString("F54"));
				fields.setField("Текст64", rs.getString("F64"));
				fields.setField("Текст65", rs.getString("F25"));
				fields.setField("Текст66", rs.getString("F67"));
				fields.setField("Текст67", rs.getString("F67"));
			}
			prp.close();
			rs.close();

			stamper.close();
			reader.close();
		}
	}
	
	@FXML
	void BtPrintBlank(ActionEvent event) {
		try {
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/MC_MERCER.pdf",
					System.getenv("MJ_PATH") + "/OutReports/MC_MERCER.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/MC_MERCER.pdf"));
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
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
			props.put("v$session.program", "MercerList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
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

	void CellDateFormatDT(TableColumn<MC_MERCER, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<MC_MERCER, LocalDateTime> cell = new TableCell<MC_MERCER, LocalDateTime>() {
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

	void CellDateFormatD(TableColumn<MC_MERCER, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<MC_MERCER, LocalDate> cell = new TableCell<MC_MERCER, LocalDate>() {
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
		firstFilterable.selectedProperty().bindBidirectional(MERCER_ID.filterableProperty());

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
			ROOT.setBottom(createOptionPane(MC_MERCER));

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			
			MERCER_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			
			OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			SheFio.setColumnFilter(new PatternColumnFilter<>());
			HeFio.setColumnFilter(new PatternColumnFilter<>());
			
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			
			dbConnect();
			Refresh();
			/**
			 * Столбцы таблицы
			 */
			{
				CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
				SheFio.setCellValueFactory(cellData -> cellData.getValue().SHEFIOProperty());
				HeFio.setCellValueFactory(cellData -> cellData.getValue().HEFIOProperty());
				MERCER_ID.setCellValueFactory(cellData -> cellData.getValue().MERCER_IDProperty().asObject());
				OPER.setCellValueFactory(cellData -> cellData.getValue().MERCER_USRProperty());
				DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			}

			// двойной щелчок
			MC_MERCER.setRowFactory(tv -> {
				TableRow<MC_MERCER> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (MC_MERCER.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите строку!");
						} else {
							Edit(MC_MERCER.getSelectionModel().getSelectedItem().getMERCER_ID(),
									(Stage) MC_MERCER.getScene().getWindow());
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

	public MercerList() {
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
			CallableStatement callStmt = conn.prepareCall("{ call Mercer.CompareXmls(?,?,?,?)}");
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
			CallableStatement callStmt = conn.prepareCall("{ call Mercer.RetXmls(?,?,?)}");
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