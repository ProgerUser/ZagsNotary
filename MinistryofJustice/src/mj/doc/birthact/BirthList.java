package mj.doc.birthact;

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
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.DateColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.RootPane;
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
import javafx.scene.control.TableColumn.CellEditEvent;
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
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class BirthList {

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_LD;

    @FXML
    private TitledPane FILTER;
    
	@FXML
	private XTableView<SELECTBIRTH> BIRTH_ACT;

	@FXML
	private Button BIRTH_ACT_FILTER;

	
	@FXML
	private XTableColumn<SELECTBIRTH, String> DOC_NUMBER;
	
	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_CH_FNAME;

	@FXML
	private Button BIRTH_ACT_PRINT;

	@FXML
	private XTableColumn<SELECTBIRTH, Integer> BIRTH_ACT_ID;

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_CH_MNAME;

	@FXML
	private BorderPane ap;

	@FXML
	private XTableColumn<SELECTBIRTH, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<SELECTBIRTH, String> CR_TIME;

	@FXML
	private XTableColumn<SELECTBIRTH, String> BR_ACT_USER;

	// @FXML
	// private XTableColumn<SELECTBIRTH, LocalDateTime> BIRTH_ACT_DATE;

	@FXML
	private XTableColumn<SELECTBIRTH, String> FFIO;
	@FXML
	private XTableColumn<SELECTBIRTH, String> MFIO;

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_ZAGS;

	@FXML
	private Button BIRTH_ACT_ADD;

	@FXML
	private XTableColumn<SELECTBIRTH, String> ChFio;

	@FXML
	private Button BIRTH_ACT_DELETE;

	@FXML
	private Button BIRTH_ACT_EDIT;

	@FXML
	void BIRTH_ACT_FILTER(ActionEvent event) {
		try {
			/* LOG */
			Main.logger = Logger.getLogger(getClass());
			/*******/

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

    @FXML
    void BtnRefresh(ActionEvent event) {
    	InitBirths();
    }
    
	@FXML
	void BIRTH_ACT_PRINT(ActionEvent event) {
		Print();
	}

	
	
	@FXML
	void Spravka_25(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_BRN_BIRTH_ACT_25.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_BRN_BIRTH_ACT where BR_ACT_ID = ?");
			prepStmt.setInt(1, BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{CH_FIO}", rs.getString("CH_FIO")));
				variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", rs.getString("BR_ACT_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_BR_DATE}", rs.getString("CH_BR_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_PL_BR}", rs.getString("CH_PL_BR")));
				variables.addTextVariable(new TextVariable("#{F_FIO}", rs.getString("F_FIO")));
				variables.addTextVariable(new TextVariable("#{M_FIO}", rs.getString("M_FIO")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_BRN_BIRTH_ACT_25", ".docx",
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
	void Spravka_26(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_BRN_BIRTH_ACT_26.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_BRN_BIRTH_ACT where BR_ACT_ID = ?");
			prepStmt.setInt(1, BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{CH_FIO}", rs.getString("CH_FIO")));
				variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", rs.getString("BR_ACT_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_BR_DATE}", rs.getString("CH_BR_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_PL_BR}", rs.getString("CH_PL_BR")));
				variables.addTextVariable(new TextVariable("#{F_FIO}", rs.getString("F_FIO")));
				variables.addTextVariable(new TextVariable("#{M_FIO}", rs.getString("M_FIO")));
				
				variables.addTextVariable(new TextVariable("#{F_NAT}", rs.getString("F_NAT")));
				variables.addTextVariable(new TextVariable("#{M_NAT}", rs.getString("M_NAT")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_BRN_BIRTH_ACT_26", ".docx",
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
	void Spravka_27(ActionEvent event) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_BRN_BIRTH_ACT_27.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_BRN_BIRTH_ACT where BR_ACT_ID = ?");
			prepStmt.setInt(1, BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{CH_FIO}", rs.getString("CH_FIO")));
				variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", rs.getString("BR_ACT_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_BR_DATE}", rs.getString("CH_BR_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_PL_BR}", rs.getString("CH_PL_BR")));
				variables.addTextVariable(new TextVariable("#{F_FIO}", rs.getString("F_FIO")));
				variables.addTextVariable(new TextVariable("#{M_FIO}", rs.getString("M_FIO")));
				
				variables.addTextVariable(new TextVariable("#{F_NAT}", rs.getString("F_NAT")));
				variables.addTextVariable(new TextVariable("#{M_NAT}", rs.getString("M_NAT")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_BRN_BIRTH_ACT_27", ".docx",
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
	public void Add(Stage stage_) {
		try {
			Logger.getLogger(getClass());

			if (DBUtil.OdbAction(82) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/mj/doc/birthact/IUBirthAct.fxml"));

			AddBirthAct controller = new AddBirthAct();

			loader.setController(controller);

			Parent root = loader.load();
			// stage.setScene(new Scene(root));
			RootPane rp = new RootPane(stage, root, true, true);

			stage.setScene(new Scene(rp));
			// stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить акт о рождении");
			stage.setResizable(false);
			stage.setIconified(false);
			stage.initOwner(stage_);
			// stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						if (from == null) {
							AfterAdd(controller.getId());
						}
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void manipulatePdf(String src, String dest) throws Exception {
		if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			AcroFields fields = stamper.getAcroFields();
			// System.out.print(fields.getFields());
			PreparedStatement prp = conn.prepareStatement("select * from BLANK_BRN_BIRTH_ACT where BR_ACT_ID = ?");
			prp.setInt(1, BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("Текст1", rs.getString("F79"));
				fields.setField("Text1", rs.getString("FT1") + " шықәсазы");
				fields.setField("Текст2", rs.getString("F79"));
				fields.setField("Text2", rs.getString("TEXT2"));
				fields.setField("Текст3", rs.getString("F3"));
				fields.setField("Текст4", rs.getString("F4"));
				fields.setField("Текст72", rs.getString("F72"));
				fields.setField("Текст73", rs.getString("F73"));
				fields.setField("Текст74", rs.getString("F74"));
				fields.setField("Текст75", "");
				fields.setField("Текст76", rs.getString("F76"));
				fields.setField("Текст77", rs.getString("F77"));
				fields.setField("Текст78", rs.getString("F78"));
				fields.setField("Текст79", rs.getString("F79"));
				fields.setField("Текст80", rs.getString("F80"));
				fields.setField("Текст81", rs.getString("F81"));
				fields.setField("Текст82", rs.getString("F82"));
				fields.setField("Текст83", rs.getString("F83") + " атәылауаҩы");
				fields.setField("Текст84", rs.getString("F84"));
				fields.setField("Текст85", rs.getString("F85"));
				fields.setField("Текст86", rs.getString("F86"));
				fields.setField("Текст87", rs.getString("F87") + " атәылауаҩы");
				fields.setField("Текст88", rs.getString("F88"));
				fields.setField("Текст89", rs.getString("F89"));
				fields.setField("Текст90", rs.getString("F90"));
				fields.setField("Текст91", rs.getString("F80"));
				fields.setField("Текст92", rs.getString("TEXT2"));
				fields.setField("Текст93", rs.getString("F93"));
				fields.setField("Текст94", rs.getString("F94"));
				fields.setField("Текст95", rs.getString("F95"));
				fields.setField("Текст96", rs.getString("F96"));
				fields.setField("Текст97", rs.getString("F97"));
				fields.setField("Текст98", rs.getString("F98"));
				fields.setField("Текст99", rs.getString("F99"));
				fields.setField("Текст100", rs.getString("F79"));
				fields.setField("Текст101", rs.getString("F101"));
				fields.setField("Текст102", rs.getString("F80"));
				fields.setField("Текст103", rs.getString("F78"));
				fields.setField("Текст104", rs.getString("F104"));
				fields.setField("Текст105", rs.getString("F105"));
				fields.setField("Текст106", rs.getString("F106"));
				fields.setField("Текст107", rs.getString("F107"));
				fields.setField("Текст108", rs.getString("F108"));
				fields.setField("Текст109", rs.getString("F109"));
				fields.setField("Текст110", rs.getString("F110"));
				fields.setField("Текст111", rs.getString("F111"));
				fields.setField("Текст112", "ЗАГС");
				fields.setField("Текст113", rs.getString("F113"));
				fields.setField("Текст115", rs.getString("F80"));
				fields.setField("Текст116", rs.getString("F101"));
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
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/BRN_BIRTH_ACT.pdf",
					System.getenv("MJ_PATH") + "/OutReports/BRN_BIRTH_ACT.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/BRN_BIRTH_ACT.pdf"));
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
    }
    
	@FXML
	void BIRTH_ACT_ADD(ActionEvent event) {
		Add((Stage) BIRTH_ACT.getScene().getWindow());
	}

	@FXML
	void BIRTH_ACT_EDIT(ActionEvent event) {
		if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите документ!");
		} else {
			Edit(BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID(),
					(Stage) BIRTH_ACT.getScene().getWindow());
		}
	}

	@FXML
	void BIRTH_ACT_DELETE(ActionEvent event) {
		Delete();
	}

	@FXML
	void AddBirth(ActionEvent event) {
		Add((Stage) BIRTH_ACT.getScene().getWindow());
	}

	boolean isopen = false;

	public void Edit(Integer docid, Stage stage_) {
		try {
			//Проверка доступа к действию
			if (DBUtil.OdbAction(83) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (isopen == false) {
				/* LOG */
				Main.logger = Logger.getLogger(getClass());
				PreparedStatement selforupd = conn
						.prepareStatement("select * from brn_birth_act where  BR_ACT_ID = ? /*for update nowait*/");
				selforupd.setInt(1, docid);
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DBUtil.Lock_Row(docid, "brn_birth_act");
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();

						loader.setLocation(getClass().getResource("/mj/doc/birthact/IUBirthAct.fxml"));

						EditBirthAct controller = new EditBirthAct();
						controller.setId(docid);
						controller.setConn(conn);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование:");
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									// обновить без сохранения
									controller.SAVECOMPARE();
									// Если нажали сохранить
									if (controller.getStatus()) {
										if (from == null) {
											AfterAdd(controller.getId());
										}
										conn.commit();
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "brn_birth_act");
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
												String lock = DBUtil.Lock_Row_Delete(docid, "brn_birth_act");
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
										isopen = false;
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"
										String lock = DBUtil.Lock_Row_Delete(docid, "brn_birth_act");
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
						Msg.Message("Запись редактируется " + DBUtil.Lock_Row_View(docid, "brn_birth_act"));
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

	@FXML
	void EditBirth(ActionEvent event) {
		if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите документ!");
		} else {
			Edit(BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID(),
					(Stage) BIRTH_ACT.getScene().getWindow());
		}
	}

	@FXML
	void ClearFilterDates(ActionEvent event) {
		DT1.setValue(null);
		DT2.setValue(null);
	}

	private void InitBirths2() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// SqlMap sql = new SqlMap().load("/BirthSql.xml");
			// String readRecordSQL = sql.getSql("SelectBirth");
			PreparedStatement prepStmt = conn.prepareStatement("select * from SelectBirth t where 1=1 "
					+ ((DT1.getValue() != null)
							? "  and to_date(TM$_DOC_DATE,'DD.MM.RRRR') >= to_date('"
									+ DT1.getValue().format(formatter2) + "','dd.mm.yyyy')"
							: "")
					+ ((DT2.getValue() != null)
							? "  and to_date(TM$_DOC_DATE,'DD.MM.RRRR') <= to_date('"
									+ DT2.getValue().format(formatter2) + "','DD.MM.RRRR')"
							: ""));
			// System.out.println("select * from SelectBirth t " + getWhere());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<SELECTBIRTH> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				SELECTBIRTH list = new SELECTBIRTH();
				list.setTM$_DOC_DATE((rs.getDate("TM$_DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$_DOC_DATE")), formatter)
						: null);
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setLIVE_DEAD(rs.getString("LIVE_DEAD"));
				list.setFFIO(rs.getString("FFIO"));
				list.setBRN_AC_ID(rs.getInt("BRN_AC_ID"));
				list.setCHILDREN_FIO(rs.getString("CHILDREN_FIO"));
				list.setMFIO(rs.getString("MFIO"));
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			BIRTH_ACT.setItems(cus_list);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<SELECTBIRTH> tableFilter = TableFilter.forTableView(BIRTH_ACT).apply();
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
			DBUtil.LOG_ERROR(e);
		}
	}

	private void InitBirths() {
		try {
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// SqlMap sql = new SqlMap().load("/BirthSql.xml");
			// String readRecordSQL = sql.getSql("SelectBirth");
			PreparedStatement prepStmt = conn
					.prepareStatement("select * from SelectBirth t " + ((getWhere() != null) ? getWhere() : " order by BRN_AC_ID desc"));
			// System.out.println("select * from SelectBirth t " + getWhere());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<SELECTBIRTH> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				SELECTBIRTH list = new SELECTBIRTH();
				list.setBRN_AC_ID(rs.getInt("BRN_AC_ID"));
				list.setBR_ACT_F(rs.getInt("BR_ACT_F"));
				list.setBR_ACT_M(rs.getInt("BR_ACT_M"));
				list.setFFIO(rs.getString("FFIO"));
				list.setLIVE_DEAD(rs.getString("LIVE_DEAD"));
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setBR_ACT_CH(rs.getInt("BR_ACT_CH"));
				list.setBR_ACT_USER(rs.getString("BR_ACT_USER"));
				list.setMFIO(rs.getString("MFIO"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCHILDREN_FIO(rs.getString("CHILDREN_FIO"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setTM$_DOC_DATE((rs.getDate("TM$_DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$_DOC_DATE")), formatterwt)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			BIRTH_ACT.setItems(cus_list);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<SELECTBIRTH> tableFilter = TableFilter.forTableView(BIRTH_ACT).apply();
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
			DBUtil.LOG_ERROR(e);
		}
	}

	private void AfterAdd(Integer id) {
		try {
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// SqlMap sql = new SqlMap().load("/BirthSql.xml");
			// String readRecordSQL = sql.getSql("SelectBirth");
			PreparedStatement prepStmt = conn.prepareStatement("select * from SelectBirth t");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<SELECTBIRTH> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				SELECTBIRTH list = new SELECTBIRTH();
				list.setBRN_AC_ID(rs.getInt("BRN_AC_ID"));
				list.setBR_ACT_F(rs.getInt("BR_ACT_F"));
				list.setBR_ACT_M(rs.getInt("BR_ACT_M"));
				list.setFFIO(rs.getString("FFIO"));
				list.setLIVE_DEAD(rs.getString("LIVE_DEAD"));
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setBR_ACT_CH(rs.getInt("BR_ACT_CH"));
				list.setBR_ACT_USER(rs.getString("BR_ACT_USER"));
				list.setMFIO(rs.getString("MFIO"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCHILDREN_FIO(rs.getString("CHILDREN_FIO"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setTM$_DOC_DATE((rs.getDate("TM$_DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$_DOC_DATE")), formatterwt)
						: null);
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			BIRTH_ACT.setItems(cus_list);
			TableFilter<SELECTBIRTH> tableFilter = TableFilter.forTableView(BIRTH_ACT).apply();
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
	void RefreshBirthList(ActionEvent event) {
		try {
			InitBirths();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Сессия BirtchList.fxml
	 */
	Connection conn = null;

	/**
	 * Открыть сессию BirtchList.fxml
	 */
	void dbConnect() {
		try {
			Properties props = new Properties();
			props.put("v$session.program", "BirthList");

			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Отключить сессию BirtchList.fxml
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

	void Delete() {
		try {
			if (DBUtil.OdbAction(84) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите документ!");
			} else {

				Stage stage = (Stage) BIRTH_ACT.getScene().getWindow();
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
							SELECTBIRTH id = BIRTH_ACT.getSelectionModel().getSelectedItem();
							PreparedStatement prst = conn
									.prepareStatement(" " + "declare " + "pragma autonomous_transaction;" + "begin "
											+ "delete from brn_birth_act where br_act_id = ?;" + "commit;" + "end;");
							prst.setInt(1, id.getBRN_AC_ID());
							prst.executeUpdate();
							prst.close();
							InitBirths();
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
				// Specifies the modality for new window.
				newWindow_yn.initModality(Modality.WINDOW_MODAL);
				// Specifies the owner Window (parent) for new window
				newWindow_yn.initOwner(stage);
				newWindow_yn.setResizable(false);
				newWindow_yn.getIcons().add(new Image("/icon.png"));
				newWindow_yn.show();

			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DeleteCont(ActionEvent event) {
		Delete();
	}

	@FXML
	private ProgressIndicator PB;
	@FXML
	private BorderPane ROOT;

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	/**
	 * Поле С фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT1(ActionEvent event) {
		try {
			InitBirths2();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Поле ПО фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT2(ActionEvent event) {
		try {
			InitBirths2();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Executor exec;

	@FXML
	void Print_Old(ActionEvent event) {
		try {
			ROOT.setDisable(true);
			PB.setVisible(true);
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {
					SELECTBIRTH auact = BIRTH_ACT.getSelectionModel().getSelectedItem();
					new PrintReport().showReport(auact.getBRN_AC_ID());
					return null;
				}
			};
			task.setOnFailed(e -> Msg.Message(task.getException().getMessage()));
			task.setOnSucceeded(e -> BlockMain());
			exec.execute(task);

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void Print() {
		try {
			if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {

				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {

					@Override
					public Object call() throws Exception {

						// Вызов
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/BRN_BIRTH_ACT.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn
								.prepareStatement("select * from V_REP_BRN_BIRTH_ACT where BR_ACT_ID = ?");
						prepStmt.setInt(1, BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_BRN_BIRTH_ACT list = null;
						if (rs.next()) {
							list = new V_REP_BRN_BIRTH_ACT();

							list.setPATERN_DATE(rs.getString("PATERN_DATE"));
							list.setCH_CNT(rs.getString("CH_CNT"));
							list.setMERCER_ZAGS(rs.getString("MERCER_ZAGS"));
							list.setM_M_NAME(rs.getString("M_M_NAME"));
							list.setCH_LNAME(rs.getString("CH_LNAME"));
							list.setDATEDOCA(rs.getString("DATEDOCA"));
							list.setMERCER_ID(rs.getString("MERCER_ID"));
							list.setCH_MNAME(rs.getString("CH_MNAME"));
							list.setF_BR_DT(rs.getString("F_BR_DT"));
							list.setMZDATE(rs.getString("MZDATE"));
							list.setM_PL_BR(rs.getString("M_PL_BR"));
							list.setFIOB(rs.getString("FIOB"));
							list.setBR_ACT_ID(rs.getString("BR_ACT_ID"));
							list.setACT_NUM(rs.getString("ACT_NUM"));
							list.setF_ADDR(rs.getString("F_ADDR"));
							list.setM_F_NAME(rs.getString("M_F_NAME"));
							list.setACT_SERIA(rs.getString("ACT_SERIA"));
							list.setCH_BR_DT(rs.getString("CH_BR_DT"));
							list.setCH_PL_BR(rs.getString("CH_PL_BR"));
							list.setPATERN_ZAGS(rs.getString("PATERN_ZAGS"));
							list.setNDOCA(rs.getString("NDOCA"));
							list.setCH_GENDR(rs.getString("CH_GENDR"));
							list.setPATERN_ID(rs.getString("PATERN_ID"));
							list.setF_M_NAME(rs.getString("F_M_NAME"));
							list.setMERCER_DATE(rs.getString("MERCER_DATE"));
							list.setF_L_NAME(rs.getString("F_L_NAME"));
							list.setDCOURT(rs.getString("DCOURT"));
							list.setM_NAT(rs.getString("M_NAT"));
							list.setM_L_NAME(rs.getString("M_L_NAME"));
							list.setF_CITIZ(rs.getString("F_CITIZ"));
							list.setF_F_NAME(rs.getString("F_F_NAME"));
							list.setM_BR_DT(rs.getString("M_BR_DT"));
							list.setDESCCOURT(rs.getString("DESCCOURT"));
							list.setF_PL_BR(rs.getString("F_PL_BR"));
							list.setM_CITIZ(rs.getString("M_CITIZ"));
							list.setCH_FNAME(rs.getString("CH_FNAME"));
							list.setM_ADDR(rs.getString("M_ADDR"));
							list.setLB_DB(rs.getString("LB_DB"));
							list.setMEDORGA(rs.getString("MEDORGA"));
							list.setF_NAT(rs.getString("F_NAT"));
							list.setBR_ACT_DATE(rs.getString("BR_ACT_DATE"));
							list.setDATEDOCB(rs.getString("DATEDOCB"));
							list.setNAMECOURT(rs.getString("NAMECOURT"));

						}
						prepStmt.close();
						rs.close();
						// ------------
						variables.addTextVariable(new TextVariable("#{PATERN_DATE}", list.getPATERN_DATE()));
						variables.addTextVariable(new TextVariable("#{CH_CNT}", list.getCH_CNT()));
						variables.addTextVariable(new TextVariable("#{MERCER_ZAGS}", list.getMERCER_ZAGS()));
						variables.addTextVariable(new TextVariable("#{M_M_NAME}", list.getM_M_NAME()));
						variables.addTextVariable(new TextVariable("#{CH_LNAME}", list.getCH_LNAME()));
						variables.addTextVariable(new TextVariable("#{DATEDOCA}", list.getDATEDOCA()));
						variables.addTextVariable(new TextVariable("#{MERCER_ID}", list.getMERCER_ID()));
						variables.addTextVariable(new TextVariable("#{CH_MNAME}", list.getCH_MNAME()));
						variables.addTextVariable(new TextVariable("#{F_BR_DT}", list.getF_BR_DT()));
						variables.addTextVariable(new TextVariable("#{MZDATE}", list.getMZDATE()));
						variables.addTextVariable(new TextVariable("#{M_PL_BR}", list.getM_PL_BR()));
						variables.addTextVariable(new TextVariable("#{FIOB}", list.getFIOB()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_ID}", list.getBR_ACT_ID()));
						variables.addTextVariable(new TextVariable("#{ACT_NUM}", list.getACT_NUM()));
						variables.addTextVariable(new TextVariable("#{F_ADDR}", list.getF_ADDR()));
						variables.addTextVariable(new TextVariable("#{M_F_NAME}", list.getM_F_NAME()));
						variables.addTextVariable(new TextVariable("#{ACT_SERIA}", list.getACT_SERIA()));
						variables.addTextVariable(new TextVariable("#{CH_BR_DT}", list.getCH_BR_DT()));
						variables.addTextVariable(new TextVariable("#{CH_PL_BR}", list.getCH_PL_BR()));
						variables.addTextVariable(new TextVariable("#{PATERN_ZAGS}", list.getPATERN_ZAGS()));
						variables.addTextVariable(new TextVariable("#{NDOCA}", list.getNDOCA()));
						variables.addTextVariable(new TextVariable("#{CH_GENDR}", list.getCH_GENDR()));
						variables.addTextVariable(new TextVariable("#{PATERN_ID}", list.getPATERN_ID()));
						variables.addTextVariable(new TextVariable("#{F_M_NAME}", list.getF_M_NAME()));
						variables.addTextVariable(new TextVariable("#{MERCER_DATE}", list.getMERCER_DATE()));
						variables.addTextVariable(new TextVariable("#{F_L_NAME}", list.getF_L_NAME()));
						variables.addTextVariable(new TextVariable("#{DCOURT}", list.getDCOURT()));
						variables.addTextVariable(new TextVariable("#{M_NAT}", list.getM_NAT()));
						variables.addTextVariable(new TextVariable("#{M_L_NAME}", list.getM_L_NAME()));
						variables.addTextVariable(new TextVariable("#{F_CITIZ}", list.getF_CITIZ()));
						variables.addTextVariable(new TextVariable("#{F_F_NAME}", list.getF_F_NAME()));
						variables.addTextVariable(new TextVariable("#{M_BR_DT}", list.getM_BR_DT()));
						variables.addTextVariable(new TextVariable("#{DESCCOURT}", list.getDESCCOURT()));
						variables.addTextVariable(new TextVariable("#{F_PL_BR}", list.getF_PL_BR()));
						variables.addTextVariable(new TextVariable("#{M_CITIZ}", list.getM_CITIZ()));
						variables.addTextVariable(new TextVariable("#{CH_FNAME}", list.getCH_FNAME()));
						variables.addTextVariable(new TextVariable("#{M_ADDR}", list.getM_ADDR()));
						variables.addTextVariable(new TextVariable("#{LB_DB}", list.getLB_DB()));
						variables.addTextVariable(new TextVariable("#{MEDORGA}", list.getMEDORGA()));
						variables.addTextVariable(new TextVariable("#{F_NAT}", list.getF_NAT()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", list.getBR_ACT_DATE()));
						variables.addTextVariable(new TextVariable("#{DATEDOCB}", list.getDATEDOCB()));
						variables.addTextVariable(new TextVariable("#{NAMECOURT}", list.getNAMECOURT()));

						// fill template
						docx.fillTemplate(variables);
						// save filled .docx file
//						DotNet dotnet = new DotNet();
//						Integer index = dotnet.FileName("BRN_BIRTH_ACT");
//						System.out.println("index=" + index);
//						String filename = System.getenv("MJ_PATH") + "OutReports/BRN_BIRTH_ACT~" + String.valueOf(index)
//								+ ".docx";
//
//						File tempFile = new File(filename);
//						FileOutputStream str = new FileOutputStream(tempFile);
//						docx.save(str);
//						str.close();
//
//						String ret = dotnet.OpenAndDelDocx(filename);
//						System.out.println(ret);

						File tempFile = File.createTempFile("BRN_BIRTH_ACT", ".docx",
								new File(System.getenv("MJ_PATH") + "OutReports"));
						FileOutputStream str = new FileOutputStream(tempFile);
						docx.save(str);
						str.close();
						tempFile.deleteOnExit();
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(tempFile);
						}

						// ByteArrayOutputStream out = new ByteArrayOutputStream();
						// docx.getXWPFDocument().write(out);
						// out.close();

						// byte[] xwpfDocumentBytes = out.toByteArray();

						// ___________________________________
						// Метод 1, сформировать файл в C:\Users\<UserName>\AppData\Local\Temp\
						// который будет удален jvm при закрытии программы
						// не очень нравится
						{
							/*
							 * File tempFile = File.createTempFile("BRN_BIRTH_ACT", ".docx");
							 * tempFile.deleteOnExit();
							 * 
							 * FileOutputStream str = new FileOutputStream(tempFile); docx.save(str);
							 * str.close();
							 * 
							 * if (Desktop.isDesktopSupported()) { Desktop.getDesktop().open(tempFile); }
							 */
						}

						// метод 2
						// конвертировать в pdf и открыть в view-ере
						{
//							ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//							PdfOptions options = PdfOptions.create();
//							PdfConverter.getInstance().convert(docx.getXWPFDocument(), out, options);
//
//							byte[] xwpfDocumentBytes = out.toByteArray();
//
//							// build a component controller
//							SwingController controller = new SwingController();
//
//							SwingViewBuilder factory = new SwingViewBuilder(controller);
//
//							JPanel viewerComponentPanel = factory.buildViewerPanel();
//
//							// add interactive mouse link annotation support via callback
//							controller.getDocumentViewController()
//									.setAnnotationCallback(new org.icepdf.ri.common.MyAnnotationCallback(
//											controller.getDocumentViewController()));
//
//							JFrame applicationFrame = new JFrame();
//							// applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//							applicationFrame.getContentPane().add(viewerComponentPanel);
//
//							// Now that the GUI is all in place, we can try openning a PDF
//							// controller.openDocument(filename);
//							// controller.openDocument(arg0, arg1, arg2);
//							controller.openDocument(xwpfDocumentBytes, 0, xwpfDocumentBytes.length, "", "");
//							controller.isDocumentViewMode(0);
//							// show the component
//							applicationFrame.pack();
//							applicationFrame.setVisible(true);
//							out.close();

						}

						// ByteArrayOutputStream out = new ByteArrayOutputStream();
						// docx.getXWPFDocument().write(out);
						// byte[] xwpfDocumentBytes = out.toByteArray();
						// InputStream targetStream = new ByteArrayInputStream(xwpfDocumentBytes);

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

//	public void ConvertToPDF(String docPath, String pdfPath) {
//		try {
//			InputStream doc = new FileInputStream(new File(docPath));
//			XWPFDocument document = new XWPFDocument(doc);
//			PdfOptions options = PdfOptions.create();
//			FileOutputStream out = new FileOutputStream(new File(pdfPath));
//			PdfConverter.getInstance().convert(document, out, options);
//		} catch (IOException ex) {
//			System.out.println(ex.getMessage());
//		}
//	}

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
		firstFilterable.selectedProperty().bindBidirectional(BIRTH_ACT_ID.filterableProperty());

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
	void Print(ActionEvent event) {
		Print();
	}

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.from = 1;
		this.conn.setAutoCommit(false);
	}

	/**
	 * Быстрый способ заполнения даты
	 * 
	 * @param dp
	 */
	void DateAutoComma(DatePicker dp) {
		DateTimeFormatter fastFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		dp.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate object) {
				return (object != null) ? object.format(defaultFormatter) : null;
			}

			@Override
			public LocalDate fromString(String string) {
				try {
					return LocalDate.parse(string, fastFormatter);
				} catch (DateTimeParseException dtp) {

				}

				return LocalDate.parse(string, defaultFormatter);
			}
		});
	}

	/**
	 * Поле С
	 */
	@FXML
	private DatePicker DT1;

	/**
	 * Поле ПО
	 */
	@FXML
	private DatePicker DT2;

    @FXML
    private VBox VB;
    
	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			VB.getChildren().remove(FILTER);
			
			ROOT.setBottom(createOptionPane(BIRTH_ACT));

			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());

			BIRTH_ACT_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));

			BIRTH_ACT_LD.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			ChFio.setColumnFilter(new PatternColumnFilter<>());
			BIRTH_ACT_ZAGS.setColumnFilter(new PatternColumnFilter<>());
			MFIO.setColumnFilter(new PatternColumnFilter<>());
			FFIO.setColumnFilter(new PatternColumnFilter<>());
			BR_ACT_USER.setColumnFilter(new PatternColumnFilter<>());
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			
			DateAutoComma(DT1);
			DateAutoComma(DT2);

			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});
			InitBirths();
			// двойной щелчок
			BIRTH_ACT.setRowFactory(tv -> {
				TableRow<SELECTBIRTH> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите документ!");
						} else {
							Edit(BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID(),
									(Stage) BIRTH_ACT.getScene().getWindow());
						}
					}
				});
				return row;
			});
			BIRTH_ACT_LD.setCellValueFactory(cellData -> cellData.getValue().LIVE_DEADProperty());
			BIRTH_ACT_ID.setCellValueFactory(cellData -> cellData.getValue().BRN_AC_IDProperty().asObject());
			CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
			CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
			ChFio.setCellValueFactory(cellData -> cellData.getValue().CHILDREN_FIOProperty());
			BIRTH_ACT_ZAGS.setCellValueFactory(cellData -> cellData.getValue().ZAGS_NAMEProperty());
			MFIO.setCellValueFactory(cellData -> cellData.getValue().MFIOProperty());
			FFIO.setCellValueFactory(cellData -> cellData.getValue().FFIOProperty());
			BR_ACT_USER.setCellValueFactory(cellData -> cellData.getValue().BR_ACT_USERProperty());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());

			/*
			 * BIRTH_ACT_ID.setCellFactory( TextFieldTableCell.<BIRTH_ACT,
			 * Integer>forTableColumn(new IntegerStringConverter()));
			 * BIRTH_ACT_DATE.setCellFactory( TextFieldTableCell.<BIRTH_ACT,
			 * LocalDateTime>forTableColumn(new LocalDateTimeStringConverter()));
			 */
			BIRTH_ACT_ID.setOnEditCommit(new EventHandler<CellEditEvent<SELECTBIRTH, Integer>>() {
				@Override
				public void handle(CellEditEvent<SELECTBIRTH, Integer> t) {
					((SELECTBIRTH) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setBRN_AC_ID(t.getNewValue());
				}
			});

			CR_DATE.setCellFactory(column -> {
				TableCell<SELECTBIRTH, LocalDate> cell = new TableCell<SELECTBIRTH, LocalDate>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

					@Override
					protected void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							if (item != null)
								setText(format.format(item));
						}
					}
				};
				return cell;
			});
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

	public BirthList() {
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
			CallableStatement callStmt = conn.prepareCall("{ call BURN_UTIL.CompareXmls(?,?,?,?)}");
			callStmt.setInt(1, docid);
			callStmt.setClob(2, lob);
			callStmt.registerOutParameter(3, Types.VARCHAR);
			callStmt.registerOutParameter(4, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(3) == null) {
				ret = callStmt.getInt(4);
				System.out.println("ret=" + callStmt.getInt(4));
				callStmt.close();
			} else {
				Msg.Message(callStmt.getString(3));
				Main.logger.error(callStmt.getString(6) + "~" + Thread.currentThread().getName());
				callStmt.close();
			}
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
			CallableStatement callStmt = conn.prepareCall("{ call BURN_UTIL.RetXmls(?,?,?)}");
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
				callStmt.close();
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
				callStmt.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
