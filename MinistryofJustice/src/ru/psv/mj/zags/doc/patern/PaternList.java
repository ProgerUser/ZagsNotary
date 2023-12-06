package ru.psv.mj.zags.doc.patern;

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

/**
 * Контроллер документа об установлении отцовства,<br>
 * добавление, редактирование
 * 
 * @author Said
 *
 */
public class PaternList {

	@FXML
	private VBox VB;

	@FXML
	private TitledPane FILTER;

	@FXML
	private XTableColumn<PATERN_CERT, Long> PC_ID;

	@FXML
	private XTableColumn<PATERN_CERT, String> FatherFio;

	@FXML
	private XTableColumn<PATERN_CERT, String> OPER;

	@FXML
	private XTableColumn<PATERN_CERT, String> MotherFio;

	@FXML
	private XTableColumn<PATERN_CERT, LocalDate> FatherBirthDate;

	@FXML
	private XTableColumn<PATERN_CERT, LocalDate> MotherBirthDate;

	@FXML
	private XTableColumn<PATERN_CERT, String> DOC_NUMBER;

	@FXML
	private DatePicker DT1;

	@FXML
	private ProgressIndicator PB;

	@FXML
	private XTableView<PATERN_CERT> PATERN_CERT;

	// @FXML
	// private XTableColumn<PATERN_CERT, LocalDateTime> PС_DATE;

	@FXML
	private DatePicker DT2;

	@FXML
	private BorderPane ROOT;

	@FXML
	private XTableColumn<PATERN_CERT, String> ChildrenFio;

	@FXML
	private XTableColumn<PATERN_CERT, LocalDate> ChildrenBirth;

	@FXML
	private XTableColumn<PATERN_CERT, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<PATERN_CERT, String> CR_TIME;

	void Init(Long ID) {

	}

	@FXML
	void Spravka_32(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_PATERN_CERT.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_PATERN_CERT where PC_ID = ?");
			prepStmt.setLong(1, PATERN_CERT.getSelectionModel().getSelectedItem().getPC_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{DOC_DATE}", rs.getString("DOC_DATE")));
				variables.addTextVariable(new TextVariable("#{F_FIO}", rs.getString("F_FIO")));
				variables.addTextVariable(new TextVariable("#{CH_AFT_LNAME}", rs.getString("CH_AFT_LNAME")));
				variables.addTextVariable(new TextVariable("#{CH_AFT_FNAME}", rs.getString("CH_AFT_FNAME")));
				variables.addTextVariable(new TextVariable("#{CH_AFT_MNAME}", rs.getString("CH_AFT_MNAME")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_PATERN_CERT", ".docx",
					new File(System.getenv("MJ_PATH") + "OutReports"));
			FileOutputStream str = new FileOutputStream(tempFile);
			docx.save(str);
			str.close();
			tempFile.deleteOnExit();
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(tempFile);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * добавить
	 */
	void Add() {
		try {
			if (DbUtil.Odb_Action(101l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) PATERN_CERT.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/patern/IUPatern.fxml"));

			AddPatern controller = new AddPatern();
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
						InitThread();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public void manipulatePdf(String src, String dest) throws Exception {
		if (PATERN_CERT.getSelectionModel().getSelectedItem() == null) {
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

			PreparedStatement prp = conn.prepareStatement("select * from BLANK_PATERN_CERT where PC_ID = ?");
			prp.setLong(1, PATERN_CERT.getSelectionModel().getSelectedItem().getPC_ID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("f1"));
				fields.setField("Текст2", rs.getString("f2"));
				fields.setField("Текст3", rs.getString("f3"));
				fields.setField("Текст4", rs.getString("f4"));
				fields.setField("Текст5", rs.getString("f5"));
				fields.setField("Текст6", rs.getString("f6"));
				fields.setField("Текст7", rs.getString("f7"));
				fields.setField("Текст8", rs.getString("f8"));
				fields.setField("Текст9", rs.getString("f9"));
				fields.setField("Текст10", rs.getString("f10"));
				fields.setField("Текст11", rs.getString("f11"));
				fields.setField("Текст12", rs.getString("f12"));
				fields.setField("Текст13", rs.getString("f13"));
				fields.setField("Текст14", rs.getString("f14"));
				fields.setField("Текст15", rs.getString("f15"));
				fields.setField("Текст16", rs.getString("f16"));
				fields.setField("Текст17", rs.getString("f17"));
				fields.setField("Текст18", rs.getString("f18"));
				fields.setField("Текст19", rs.getString("f19"));
				fields.setField("Текст20", rs.getString("f20"));
				fields.setField("Текст21", rs.getString("f21"));
				fields.setField("Текст22", rs.getString("f22"));
				fields.setField("Текст23", rs.getString("f23"));
				fields.setField("Текст24", rs.getString("f24"));
				fields.setField("Текст25", rs.getString("f25"));
				fields.setField("Текст26", rs.getString("f26"));
				fields.setField("Текст27", rs.getString("f27"));
				fields.setField("Текст28", rs.getString("f28"));
				fields.setField("Текст29", rs.getString("f29"));
				fields.setField("Текст30", rs.getString("f30"));
				fields.setField("Текст31", rs.getString("f31"));
				fields.setField("Текст32", rs.getString("f32"));
				fields.setField("Текст33", rs.getString("f33"));
				fields.setField("Текст34", rs.getString("f34"));
				fields.setField("Текст35", rs.getString("f35"));
				fields.setField("Текст36", rs.getString("f36"));
				fields.setField("Текст37", rs.getString("f37"));
				fields.setField("Текст38", rs.getString("f38"));
				fields.setField("Текст39", rs.getString("f39"));
				fields.setField("Текст40", rs.getString("f40"));
				fields.setField("Текст41", rs.getString("f41"));
				fields.setField("Текст42", rs.getString("f42"));
				fields.setField("Текст43", rs.getString("f43"));
				fields.setField("Текст44", rs.getString("f44"));
				fields.setField("Текст45", rs.getString("f45"));
				fields.setField("Текст46", rs.getString("f46"));
				fields.setField("Текст47", rs.getString("f47"));
				fields.setField("Текст48", rs.getString("f48"));
				fields.setField("Текст49", rs.getString("f49"));
				fields.setField("Текст50", rs.getString("f50"));
				fields.setField("Текст51", rs.getString("f51"));
				fields.setField("Текст52", rs.getString("f52"));
				fields.setField("Текст53", rs.getString("f53"));
				fields.setField("Текст54", rs.getString("f54"));
				fields.setField("Текст55", rs.getString("f55"));
				fields.setField("Текст56", rs.getString("f56"));
				fields.setField("Текст57", rs.getString("f57"));
				fields.setField("Текст58", rs.getString("f58"));
				fields.setField("Текст59", rs.getString("f59"));
				fields.setField("Текст60", rs.getString("f60"));
				fields.setField("Текст61", rs.getString("f61"));
				fields.setField("Текст62", rs.getString("f62"));
				fields.setField("Текст63", rs.getString("f63"));
				fields.setField("Текст64", rs.getString("f64"));
				fields.setField("Текст65", rs.getString("f65"));
				fields.setField("Текст66", rs.getString("f66"));
				fields.setField("Текст67", rs.getString("f67"));
				fields.setField("Текст68", rs.getString("f68"));
				fields.setField("Текст69", rs.getString("f69"));
				fields.setField("Текст70", rs.getString("f70"));
				fields.setField("Текст72", rs.getString("f72"));
				fields.setField("Текст73", rs.getString("f73"));
				fields.setField("Текст75", rs.getString("f75"));
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
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/PATERN_CERT.pdf",
					System.getenv("MJ_PATH") + "/OutReports/PATERN_CERT.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/PATERN_CERT.pdf"));
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * удалить
	 */
	void Delete() {
		try {
			if (PATERN_CERT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {

				if (DbUtil.Odb_Action(102l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				Stage stage = (Stage) PATERN_CERT.getScene().getWindow();
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
											+ " delete from PATERN_CERT where PC_ID = ?;" + "commit;" + "end;");
							PATERN_CERT cl = PATERN_CERT.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getPC_ID());
							delete.executeUpdate();
							delete.close();

							InitThread();
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

	/**
	 * Открыта ли форма редактирования <br>
	 * Возможен только один экземпляр формы
	 */
	boolean isopen = false;

	/**
	 * Редактировать
	 */
	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {

				if (DbUtil.Odb_Action(103l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement selforupd = conn
						.prepareStatement("select * from PATERN_CERT where  PC_ID = ? for update nowait");
				PATERN_CERT cl = Initialize2(docid);
				selforupd.setLong(1, docid);
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DbUtil.Lock_Row(docid, "PATERN_CERT", conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/patern/IUPatern.fxml"));

						EditPatern controller = new EditPatern();
						controller.setId(cl.getPC_ID());
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
									// обновить без коммита
									controller.SaveCompare();
									if (controller.getStatus()) {
										conn.commit();
										if (from == null) {
											InitThread();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(docid, "PATERN_CERT", conn);
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
												String lock = DbUtil.Lock_Row_Delete(docid, "PATERN_CERT", conn);
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
										String lock = DbUtil.Lock_Row_Delete(docid, "PATERN_CERT", conn);
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
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(docid, "PATERN_CERT"));
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

	/**
	 * Печать
	 */
	void Print() {
		try {
			if (PATERN_CERT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {
					@Override
					public Object call() throws Exception {
						// Вызов
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/PATERN_CERT.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from V_REP_PATERN_CERT where PC_ID = ?");
						prepStmt.setLong(1, PATERN_CERT.getSelectionModel().getSelectedItem().getPC_ID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_PATERN_CERT list = null;
						if (rs.next()) {
							list = new V_REP_PATERN_CERT();
							list.setCH_FNAME(rs.getString("CH_FNAME"));
							list.setPС_DATE(rs.getString("PС_DATE"));
							list.setF_CITIZEN(rs.getString("F_CITIZEN"));
							list.setPC_ZPLACE(rs.getString("PC_ZPLACE"));
							list.setF_BR_DT(rs.getString("F_BR_DT"));
							list.setF_BR_PL(rs.getString("F_BR_PL"));
							list.setM_MNAME(rs.getString("M_MNAME"));
							list.setPC_ZFNAME(rs.getString("PC_ZFNAME"));
							list.setF_NAT(rs.getString("F_NAT"));
							list.setPС_FZ(rs.getString("PС_FZ"));
							list.setPС_NUMBER(rs.getString("PС_NUMBER"));
							list.setCH_LNAME(rs.getString("CH_LNAME"));
							list.setPС_AFT_FNAME(rs.getString("PС_AFT_FNAME"));
							list.setPС_TRZ(rs.getString("PС_TRZ"));
							list.setPС_AFT_MNAME(rs.getString("PС_AFT_MNAME"));
							list.setF_ADDR(rs.getString("F_ADDR"));
							list.setPС_SERIA(rs.getString("PС_SERIA"));
							list.setPС_CRNAME(rs.getString("PС_CRNAME"));
							list.setM_BR_PL(rs.getString("M_BR_PL"));
							list.setPС_CRDATE(rs.getString("PС_CRDATE"));
							list.setF_LNAME(rs.getString("F_LNAME"));
							list.setM_LNAME(rs.getString("M_LNAME"));
							list.setM_FNAME(rs.getString("M_FNAME"));
							list.setM_CITIZEN(rs.getString("M_CITIZEN"));
							list.setPC_ID(rs.getString("PC_ID"));
							list.setF_MNAME(rs.getString("F_MNAME"));
							list.setCH_ACT_NUM(rs.getString("CH_ACT_NUM"));
							list.setM_NAT(rs.getString("M_NAT"));
							list.setPC_ZMNAME(rs.getString("PC_ZMNAME"));
							list.setBR_ACT_DATE(rs.getString("BR_ACT_DATE"));
							list.setF_FNAME(rs.getString("F_FNAME"));
							list.setCH_SEX(rs.getString("CH_SEX"));
							list.setM_ADDR(rs.getString("M_ADDR"));
							list.setCH_MNAME(rs.getString("CH_MNAME"));
							list.setPC_ZLNAME(rs.getString("PC_ZLNAME"));
							list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
							list.setCH_BRDT(rs.getString("CH_BRDT"));
							list.setM_BR_DT(rs.getString("M_BR_DT"));
							list.setCH_BR_PL(rs.getString("CH_BR_PL"));
							list.setPС_AFT_LNAME(rs.getString("PС_AFT_LNAME"));
						}
						prepStmt.close();
						rs.close();
						// ------------
						variables.addTextVariable(new TextVariable("#{CH_FNAME}", list.getCH_FNAME()));
						variables.addTextVariable(new TextVariable("#{PС_DATE}", list.getPС_DATE()));
						variables.addTextVariable(new TextVariable("#{F_CITIZEN}", list.getF_CITIZEN()));
						variables.addTextVariable(new TextVariable("#{PC_ZPLACE}", list.getPC_ZPLACE()));
						variables.addTextVariable(new TextVariable("#{F_BR_DT}", list.getF_BR_DT()));
						variables.addTextVariable(new TextVariable("#{F_BR_PL}", list.getF_BR_PL()));
						variables.addTextVariable(new TextVariable("#{M_MNAME}", list.getM_MNAME()));
						variables.addTextVariable(new TextVariable("#{PC_ZFNAME}", list.getPC_ZFNAME()));
						variables.addTextVariable(new TextVariable("#{F_NAT}", list.getF_NAT()));
						variables.addTextVariable(new TextVariable("#{PС_FZ}", list.getPС_FZ()));
						variables.addTextVariable(new TextVariable("#{PС_NUMBER}", list.getPС_NUMBER()));
						variables.addTextVariable(new TextVariable("#{PС_AFT_FNAME}", list.getPС_AFT_FNAME()));
						variables.addTextVariable(new TextVariable("#{CH_LNAME}", list.getCH_LNAME()));
						variables.addTextVariable(new TextVariable("#{PС_TRZ}", list.getPС_TRZ()));
						variables.addTextVariable(new TextVariable("#{PС_AFT_MNAME}", list.getPС_AFT_MNAME()));
						variables.addTextVariable(new TextVariable("#{F_ADDR}", list.getF_ADDR()));
						variables.addTextVariable(new TextVariable("#{PС_SERIA}", list.getPС_SERIA()));
						variables.addTextVariable(new TextVariable("#{PС_CRNAME}", list.getPС_CRNAME()));
						variables.addTextVariable(new TextVariable("#{M_BR_PL}", list.getM_BR_PL()));
						variables.addTextVariable(new TextVariable("#{PС_CRDATE}", list.getPС_CRDATE()));
						variables.addTextVariable(new TextVariable("#{F_LNAME}", list.getF_LNAME()));
						variables.addTextVariable(new TextVariable("#{M_LNAME}", list.getM_LNAME()));
						variables.addTextVariable(new TextVariable("#{M_FNAME}", list.getM_FNAME()));
						variables.addTextVariable(new TextVariable("#{M_CITIZEN}", list.getM_CITIZEN()));
						variables.addTextVariable(new TextVariable("#{PC_ID}", list.getPC_ID()));
						variables.addTextVariable(new TextVariable("#{F_MNAME}", list.getF_MNAME()));
						variables.addTextVariable(new TextVariable("#{CH_ACT_NUM}", list.getCH_ACT_NUM()));
						variables.addTextVariable(new TextVariable("#{M_NAT}", list.getM_NAT()));
						variables.addTextVariable(new TextVariable("#{PC_ZMNAME}", list.getPC_ZMNAME()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", list.getBR_ACT_DATE()));
						variables.addTextVariable(new TextVariable("#{F_FNAME}", list.getF_FNAME()));
						variables.addTextVariable(new TextVariable("#{CH_SEX}", list.getCH_SEX()));
						variables.addTextVariable(new TextVariable("#{M_ADDR}", list.getM_ADDR()));
						variables.addTextVariable(new TextVariable("#{CH_MNAME}", list.getCH_MNAME()));
						variables.addTextVariable(new TextVariable("#{PC_ZLNAME}", list.getPC_ZLNAME()));
						variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", list.getZAGS_NAME()));
						variables.addTextVariable(new TextVariable("#{CH_BRDT}", list.getCH_BRDT()));
						variables.addTextVariable(new TextVariable("#{M_BR_DT}", list.getM_BR_DT()));
						variables.addTextVariable(new TextVariable("#{CH_BR_PL}", list.getCH_BR_PL()));
						variables.addTextVariable(new TextVariable("#{PС_AFT_LNAME}", list.getPС_AFT_LNAME()));
						// fill template
						docx.fillTemplate(variables);
						/*
						 * // save filled ".docx" file UUID uuid = UUID.randomUUID(); String filename =
						 * System.getenv("MJ_PATH") + "OutReports/PATERN_CERT_" + uuid.toString() +
						 * ".docx"; docx.save(filename);
						 * 
						 * if (Desktop.isDesktopSupported()) { Desktop.getDesktop().open(new
						 * File(filename)); }
						 */
						File tempFile = File.createTempFile("PATERN_CERT", ".docx",
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
			DbUtil.Log_Error(e);
		}
	}

	private Executor exec;

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	/**
	 * Добавление строк динамически
	 */
	void InitThread() {
		try {
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
					String selectStmt = "select * from v_patern_cert "
							+ ((getWhere() != null) ? getWhere() : " order by PC_ID desc");
					PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
					ResultSet rs = prepStmt.executeQuery();
					while (rs.next()) {
						PATERN_CERT list = new PATERN_CERT();

						list.setPС_FZ((rs.getDate("PС_FZ") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_FZ")), formatter) : null);
						list.setMOTHERBIRTHDATE((rs.getDate("MOTHERBIRTHDATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("MOTHERBIRTHDATE")), formatter)
								: null);
						list.setCHILDRENBIRTH((rs.getDate("CHILDRENBIRTH") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CHILDRENBIRTH")), formatter)
								: null);
						list.setFATHERFIO(rs.getString("FATHERFIO"));
						list.setPC_ACT_ID(rs.getLong("PC_ACT_ID"));
						list.setPС_CH(rs.getLong("PС_CH"));
						list.setPС_SERIA(rs.getString("PС_SERIA"));
						list.setPС_NUMBER(rs.getString("PС_NUMBER"));
						list.setTM$PС_DATE((rs.getDate("TM$PС_DATE") != null) ? LocalDateTime.parse(
								new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$PС_DATE")),
								formatterwt) : null);
						list.setPC_ZLNAME(rs.getString("PC_ZLNAME"));
						list.setMOTHERFIO(rs.getString("MOTHERFIO"));
						list.setPC_ZPLACE(rs.getString("PC_ZPLACE"));
						list.setCR_DATE((rs.getDate("CR_DATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter) : null);
						list.setCR_TIME(rs.getString("CR_TIME"));
						list.setFATHERBIRTHDATE((rs.getDate("FATHERBIRTHDATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("FATHERBIRTHDATE")), formatter)
								: null);
						list.setPС_F(rs.getLong("PС_F"));
						list.setPС_CRDATE((rs.getDate("PС_CRDATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_CRDATE")), formatter) : null);
						list.setPС_TRZ((rs.getDate("PС_TRZ") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_TRZ")), formatter) : null);
						list.setPC_ZMNAME(rs.getString("PC_ZMNAME"));
						list.setPС_AFT_LNAME(rs.getString("PС_AFT_LNAME"));
						list.setPC_ZFNAME(rs.getString("PC_ZFNAME"));
						list.setCHILDFIO(rs.getString("CHILDFIO"));
						list.setPС_CRNAME(rs.getString("PС_CRNAME"));
						list.setPС_M(rs.getLong("PС_M"));
						list.setPС_AFT_MNAME(rs.getString("PС_AFT_MNAME"));
						list.setPС_ZAGS(rs.getLong("PС_ZAGS"));
						list.setPC_ID(rs.getLong("PC_ID"));
						list.setPС_TYPE(rs.getString("PС_TYPE"));
						list.setPС_AFT_FNAME(rs.getString("PС_AFT_FNAME"));
						list.setPС_USER(rs.getString("PС_USER"));

						list.setBEF_LNAME(rs.getString("BEF_LNAME"));
						list.setBEF_FNAME(rs.getString("BEF_FNAME"));
						list.setBEF_MNAME(rs.getString("BEF_MNAME"));
						list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));

						PATERN_CERT.getItems().add(list);
					}
					prepStmt.close();
					rs.close();
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							TableFilter<PATERN_CERT> tableFilter = TableFilter.forTableView(PATERN_CERT).apply();
							tableFilter.setSearchStrategy((input, target) -> {
								try {
									return target.toLowerCase().contains(input.toLowerCase());
								} catch (Exception e) {
									return false;
								}
							});
						}
					});

					return null;
				}
			};
			task.setOnFailed(e -> Msg.Message(task.getException().getMessage()));
			// task.setOnSucceeded(e -> BlockMain());
			exec.execute(task);

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Обновить таблицу
	 */
	@Deprecated
	void Refresh_() {
		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String selectStmt = "select * from v_patern_cert "
					+ ((getWhere() != null) ? getWhere() : " order by PC_ID desc");
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<PATERN_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				PATERN_CERT list = new PATERN_CERT();

				list.setPС_FZ((rs.getDate("PС_FZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_FZ")), formatter)
						: null);
				list.setMOTHERBIRTHDATE((rs.getDate("MOTHERBIRTHDATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("MOTHERBIRTHDATE")), formatter) : null);
				list.setCHILDRENBIRTH((rs.getDate("CHILDRENBIRTH") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CHILDRENBIRTH")), formatter) : null);
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setPC_ACT_ID(rs.getLong("PC_ACT_ID"));
				list.setPС_CH(rs.getLong("PС_CH"));
				list.setPС_SERIA(rs.getString("PС_SERIA"));
				list.setPС_NUMBER(rs.getString("PС_NUMBER"));
				list.setTM$PС_DATE((rs.getDate("TM$PС_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$PС_DATE")), formatterwt)
						: null);
				list.setPC_ZLNAME(rs.getString("PC_ZLNAME"));
				list.setMOTHERFIO(rs.getString("MOTHERFIO"));
				list.setPC_ZPLACE(rs.getString("PC_ZPLACE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setFATHERBIRTHDATE((rs.getDate("FATHERBIRTHDATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("FATHERBIRTHDATE")), formatter) : null);
				list.setPС_F(rs.getLong("PС_F"));
				list.setPС_CRDATE((rs.getDate("PС_CRDATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_CRDATE")), formatter)
						: null);
				list.setPС_TRZ((rs.getDate("PС_TRZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_TRZ")), formatter)
						: null);
				list.setPC_ZMNAME(rs.getString("PC_ZMNAME"));
				list.setPС_AFT_LNAME(rs.getString("PС_AFT_LNAME"));
				list.setPC_ZFNAME(rs.getString("PC_ZFNAME"));
				list.setCHILDFIO(rs.getString("CHILDFIO"));
				list.setPС_CRNAME(rs.getString("PС_CRNAME"));
				list.setPС_M(rs.getLong("PС_M"));
				list.setPС_AFT_MNAME(rs.getString("PС_AFT_MNAME"));
				list.setPС_ZAGS(rs.getLong("PС_ZAGS"));
				list.setPC_ID(rs.getLong("PC_ID"));
				list.setPС_TYPE(rs.getString("PС_TYPE"));
				list.setPС_AFT_FNAME(rs.getString("PС_AFT_FNAME"));
				list.setPС_USER(rs.getString("PС_USER"));

				list.setBEF_LNAME(rs.getString("BEF_LNAME"));
				list.setBEF_FNAME(rs.getString("BEF_FNAME"));
				list.setBEF_MNAME(rs.getString("BEF_MNAME"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));

				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			PATERN_CERT.setItems(dlist);

			TableFilter<PATERN_CERT> tableFilter = TableFilter.forTableView(PATERN_CERT).apply();
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

	/**
	 * км обновить
	 * 
	 * @param event
	 */
	@FXML
	void CmRefresh(ActionEvent event) {
		InitThread();
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
		if (PATERN_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(PATERN_CERT.getSelectionModel().getSelectedItem().getPC_ID(),
					(Stage) PATERN_CERT.getScene().getWindow());
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
	 * добавить
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
		if (PATERN_CERT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(PATERN_CERT.getSelectionModel().getSelectedItem().getPC_ID(),
					(Stage) PATERN_CERT.getScene().getWindow());
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

	/**
	 * Подключение к базе
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Форматирование даты и времени
	 * 
	 * @param tc
	 */
	void CellDateFormatDT(TableColumn<PATERN_CERT, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<PATERN_CERT, LocalDateTime> cell = new TableCell<PATERN_CERT, LocalDateTime>() {
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
	void CellDateFormatD(TableColumn<PATERN_CERT, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<PATERN_CERT, LocalDate> cell = new TableCell<PATERN_CERT, LocalDate>() {
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

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.from = 1;
		this.conn.setAutoCommit(false);
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
		firstFilterable.selectedProperty().bindBidirectional(PC_ID.filterableProperty());

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
			ROOT.setBottom(createOptionPane(PATERN_CERT));

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			PC_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));

			OPER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			FatherFio.setColumnFilter(new PatternColumnFilter<>());
			ChildrenFio.setColumnFilter(new PatternColumnFilter<>());
			ChildrenBirth.setColumnFilter(new DateColumnFilter<>());
			MotherFio.setColumnFilter(new PatternColumnFilter<>());
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			FatherBirthDate.setColumnFilter(new DateColumnFilter<>());
			MotherBirthDate.setColumnFilter(new DateColumnFilter<>());

			dbConnect();
			// DbUtil.Run_Process(conn,getClass().getName());
			InitThread();
			/**
			 * Столбцы таблицы
			 */
			{
				PC_ID.setCellValueFactory(cellData -> cellData.getValue().PC_IDProperty().asObject());
				CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
				FatherFio.setCellValueFactory(cellData -> cellData.getValue().FATHERFIOProperty());
				ChildrenFio.setCellValueFactory(cellData -> cellData.getValue().CHILDFIOProperty());
				ChildrenBirth.setCellValueFactory(cellData -> cellData.getValue().CHILDRENBIRTHProperty());
				MotherFio.setCellValueFactory(cellData -> cellData.getValue().MOTHERFIOProperty());
				OPER.setCellValueFactory(cellData -> cellData.getValue().PС_USERProperty());
				DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
				FatherBirthDate.setCellValueFactory(cellData -> cellData.getValue().FATHERBIRTHDATEProperty());
				MotherBirthDate.setCellValueFactory(cellData -> cellData.getValue().MOTHERBIRTHDATEProperty());

			}

			// двойной щелчок
			PATERN_CERT.setRowFactory(tv -> {
				TableRow<PATERN_CERT> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PATERN_CERT.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите строку!");
						} else {
							Edit(PATERN_CERT.getSelectionModel().getSelectedItem().getPC_ID(),
									(Stage) PATERN_CERT.getScene().getWindow());
						}
					}
				});
				return row;
			});
			/**
			 * ФД
			 */
			{
				// CellDateFormatDT(PС_DATE);
				CellDateFormatD(FatherBirthDate);
				CellDateFormatD(MotherBirthDate);
				CellDateFormatD(ChildrenBirth);
				CellDateFormatD(CR_DATE);

			}

			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	PATERN_CERT Initialize2(Long docid) {
		PATERN_CERT list = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String selectStmt = "select * from v_patern_cert where PC_ID = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<PATERN_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new PATERN_CERT();

				list.setPС_FZ((rs.getDate("PС_FZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_FZ")), formatter)
						: null);
				list.setMOTHERBIRTHDATE((rs.getDate("MOTHERBIRTHDATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("MOTHERBIRTHDATE")), formatter) : null);
				list.setCHILDRENBIRTH((rs.getDate("CHILDRENBIRTH") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CHILDRENBIRTH")), formatter) : null);
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setPC_ACT_ID(rs.getLong("PC_ACT_ID"));
				list.setPС_CH(rs.getLong("PС_CH"));
				list.setPС_SERIA(rs.getString("PС_SERIA"));
				list.setPС_NUMBER(rs.getString("PС_NUMBER"));
				list.setTM$PС_DATE((rs.getDate("TM$PС_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$PС_DATE")), formatterwt)
						: null);
				list.setPC_ZLNAME(rs.getString("PC_ZLNAME"));
				list.setMOTHERFIO(rs.getString("MOTHERFIO"));
				list.setPC_ZPLACE(rs.getString("PC_ZPLACE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setFATHERBIRTHDATE((rs.getDate("FATHERBIRTHDATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("FATHERBIRTHDATE")), formatter) : null);
				list.setPС_F(rs.getLong("PС_F"));
				list.setPС_CRDATE((rs.getDate("PС_CRDATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_CRDATE")), formatter)
						: null);
				list.setPС_TRZ((rs.getDate("PС_TRZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_TRZ")), formatter)
						: null);
				list.setPC_ZMNAME(rs.getString("PC_ZMNAME"));
				list.setPС_AFT_LNAME(rs.getString("PС_AFT_LNAME"));
				list.setPC_ZFNAME(rs.getString("PC_ZFNAME"));
				list.setCHILDFIO(rs.getString("CHILDFIO"));
				list.setPС_CRNAME(rs.getString("PС_CRNAME"));
				list.setPС_M(rs.getLong("PС_M"));
				list.setPС_AFT_MNAME(rs.getString("PС_AFT_MNAME"));
				list.setPС_ZAGS(rs.getLong("PС_ZAGS"));
				list.setPC_ID(rs.getLong("PC_ID"));
				list.setPС_TYPE(rs.getString("PС_TYPE"));
				list.setPС_AFT_FNAME(rs.getString("PС_AFT_FNAME"));
				list.setPС_USER(rs.getString("PС_USER"));

				list.setBEF_LNAME(rs.getString("BEF_LNAME"));
				list.setBEF_FNAME(rs.getString("BEF_FNAME"));
				list.setBEF_MNAME(rs.getString("BEF_MNAME"));
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

	private StringProperty Where;

	public void setWhere(String value) {
		this.Where.set(value);
	}

	public String getWhere() {
		return this.Where.get();
	}

	public PaternList() {
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
			CallableStatement callStmt = conn.prepareCall("{ call PATERN.CompareXmls(?,?,?,?)}");
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
			CallableStatement callStmt = conn.prepareCall("{ call PATERN.RetXmls(?,?,?)}");
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
