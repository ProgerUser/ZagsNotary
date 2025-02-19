package ru.psv.mj.zags.doc.adoptoin;

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

public class AdoptList {

	@FXML
	private XTableColumn<ADOPTOIN, String> ZAGS_NAME;
	@FXML
	private DatePicker DT1;
	@FXML
	private DatePicker DT2;
	@FXML
	private BorderPane ROOT;
	@FXML
	private XTableColumn<ADOPTOIN, String> NEW_FIRSTNAME;
	@FXML
	private XTableColumn<ADOPTOIN, String> DOC_NUMBER;
	@FXML
	private XTableColumn<ADOPTOIN, String> OLD_LASTNAME;
	@FXML
	private XTableColumn<ADOPTOIN, String> OPER;
	@FXML
	private XTableColumn<ADOPTOIN, String> NEW_MIDDLNAME;
	@FXML
	private ProgressIndicator PB;
	@FXML
	private XTableColumn<ADOPTOIN, String> NEW_LASTNAME;
	@FXML
	private XTableView<ADOPTOIN> ADOPTOIN;
	@FXML
	private XTableColumn<ADOPTOIN, Long> ID;
	@FXML
	private XTableColumn<ADOPTOIN, String> OLD_FIRSTNAME;
	// @FXML
	// private XTableColumn<ADOPTOIN, LocalDateTime> DOC_DATE;
	@FXML
	private XTableColumn<ADOPTOIN, String> OLD_MIDDLNAME;
	@FXML
	private VBox VB;
	@FXML
	private TitledPane FILTER;

	@FXML
	void Spravka_31(ActionEvent event) {
		try {
			// �����
			Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/SPR_ADOPTOIN.docx");
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = conn.prepareStatement("select * from SPR_ADOPTOIN where ID = ?");
			prepStmt.setLong(1, ADOPTOIN.getSelectionModel().getSelectedItem().getID());
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
				variables.addTextVariable(new TextVariable("#{CH_FIO}", rs.getString("CH_FIO")));
				variables.addTextVariable(new TextVariable("#{M_AD_FIO}", rs.getString("M_AD_FIO")));
				variables.addTextVariable(new TextVariable("#{F_AD_FIO}", rs.getString("F_AD_FIO")));
				variables.addTextVariable(new TextVariable("#{DOC_DATE}", rs.getString("DOC_DATE")));
				variables.addTextVariable(new TextVariable("#{ZAGS_NAME}", rs.getString("ZAGS_NAME")));
				variables.addTextVariable(new TextVariable("#{CH_BR_DATE}", rs.getString("CH_BR_DATE")));
				variables.addTextVariable(new TextVariable("#{CH_PL_BR}", rs.getString("CH_PL_BR")));
				variables.addTextVariable(new TextVariable("#{CH_AFT_LNAME}", rs.getString("CH_AFT_LNAME")));
				variables.addTextVariable(new TextVariable("#{CH_AFT_FNAME}", rs.getString("CH_AFT_FNAME")));
				variables.addTextVariable(new TextVariable("#{CH_AFT_MNAME}", rs.getString("CH_AFT_MNAME")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("SPR_ADOPTOIN", ".docx",
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

	public void manipulatePdf(String src, String dest) throws Exception {
		if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("�������� ������!");
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

			PreparedStatement prp = conn.prepareStatement("select * from BLANK_ADOPTOIN where ID = ?");
			prp.setLong(1, ADOPTOIN.getSelectionModel().getSelectedItem().getID());
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				fields.setField("�����1", rs.getString("F1"));
				fields.setField("�����2", rs.getString("F2"));
				fields.setField("�����3", rs.getString("F3"));
				fields.setField("�����4", rs.getString("F4"));
				fields.setField("�����5", rs.getString("F5"));
				fields.setField("�����6", rs.getString("F6"));
				fields.setField("�����7", rs.getString("F7"));
				fields.setField("�����8", rs.getString("F8"));
				fields.setField("�����9", rs.getString("F9"));
				fields.setField("�����10", rs.getString("F10"));
				fields.setField("�����11", rs.getString("F11"));
				fields.setField("�����12", rs.getString("F12"));
				fields.setField("�����14", rs.getString("F14"));
				fields.setField("�����15", rs.getString("F15"));
				fields.setField("�����16", rs.getString("F16"));
				fields.setField("�����17", rs.getString("F17"));
				fields.setField("�����18", rs.getString("F18"));
				fields.setField("�����19", rs.getString("F19"));
				fields.setField("�����20", rs.getString("F20"));
				fields.setField("�����21", rs.getString("F21"));
				fields.setField("�����22", rs.getString("F5"));
				fields.setField("�����23", rs.getString("F4"));
				fields.setField("�����24", rs.getString("F6"));
				fields.setField("�����25", rs.getString("F8"));
				fields.setField("�����26", rs.getString("F7"));
				fields.setField("�����27", rs.getString("F27"));
				fields.setField("�����28", rs.getString("F28"));
				fields.setField("�����29", rs.getString("F29"));
				fields.setField("�����30", rs.getString("F30"));
				fields.setField("�����31", rs.getString("F31"));
				fields.setField("�����32", rs.getString("F32"));
				fields.setField("�����33", rs.getString("F33"));
				fields.setField("�����34", rs.getString("F28"));
				fields.setField("�����35", rs.getString("F35"));
				fields.setField("�����36", rs.getString("F36"));
				fields.setField("�����69", rs.getString("F29"));
				fields.setField("�����70", rs.getString("F70"));
				fields.setField("�����71", rs.getString("F5"));
				fields.setField("�����72", rs.getString("F6"));
				fields.setField("�����73", rs.getString("F7"));
				fields.setField("�����74", rs.getString("F74"));
				fields.setField("�����75", rs.getString("F75"));
				fields.setField("�����76", rs.getString("F76"));
				fields.setField("�����77", rs.getString("F77"));
				fields.setField("�����78", rs.getString("F78"));
				fields.setField("�����79", rs.getString("F79"));
				fields.setField("�����80", rs.getString("F80"));
				fields.setField("�����81", rs.getString("F81"));
				fields.setField("�����82", rs.getString("F82"));
				fields.setField("�����83", rs.getString("F83"));
				fields.setField("�����84", rs.getString("F84"));
				fields.setField("�����85", rs.getString("F85"));
				fields.setField("�����86", rs.getString("F86"));
				fields.setField("�����87", rs.getString("F87"));
				fields.setField("�����88", rs.getString("F88"));
				fields.setField("�����89", rs.getString("F89"));
				fields.setField("�����90", rs.getString("F90"));
				fields.setField("�����91", rs.getString("F91"));
				fields.setField("�����92", rs.getString("F92"));
				fields.setField("�����93", rs.getString("F93"));
				fields.setField("�����94", rs.getString("F94"));
				fields.setField("�����95", rs.getString("F30"));
				fields.setField("�����96", rs.getString("F96"));
				fields.setField("�����97", rs.getString("F97"));
				fields.setField("�����98", "");
				fields.setField("�����100", rs.getString("F100"));

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
			manipulatePdf(System.getenv("MJ_PATH") + "/Reports/ADOPTOIN.pdf",
					System.getenv("MJ_PATH") + "/OutReports/ADOPTOIN.pdf");
			Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + "/OutReports/ADOPTOIN.pdf"));
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");

		CheckBox filterVisible = new CheckBox("�������� ������");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());

		CheckBox menuButtonVisible = new CheckBox("�������� ������ ����");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());

		CheckBox firstFilterable = new CheckBox("����������� ������ �������");
		// XTableColumn<VCUS, Long> firstColumn = (XTableColumn<VCUS, Long>)
		// table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(ID.filterableProperty());

		CheckBox includeHidden = new CheckBox("�������� ������� �������");
		includeHidden.selectedProperty().bindBidirectional(table.getFilterController().includeHiddenProperty());

		CheckBox andFilters = new CheckBox("����������� �������� \"�\" ��� ���������������� �������");
		andFilters.selectedProperty().bindBidirectional(table.getFilterController().andFiltersProperty());

		pane.getChildren().addAll(filterVisible, menuButtonVisible, firstFilterable, includeHidden, andFilters);

		TitledBorderPane p = new TitledBorderPane("���������", pane);
		p.getStyleClass().add("top-border-only");
		p.setStyle("-fx-border-insets: 10 0 0 0");
		return p;
	}

	void Add() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) ADOPTOIN.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/adoptoin/IUAdopt.fxml"));

			AddAdopt controller = new AddAdopt();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("�������� ����� ������");
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

	void Delete() {
		try {
			if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������!");
			} else {

				Stage stage = (Stage) ADOPTOIN.getScene().getWindow();
				Label alert = new Label("������� ������?");
				alert.setLayoutX(75.0);
				alert.setLayoutY(11.0);
				alert.setPrefHeight(17.0);

				Button no = new Button();
				no.setText("���");
				no.setLayoutX(111.0);
				no.setLayoutY(56.0);
				no.setPrefWidth(72.0);
				no.setPrefHeight(21.0);

				Button yes = new Button();
				yes.setText("��");
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
											+ " delete from ADOPTOIN where ID = ?;" + "commit;" + "end;");
							ADOPTOIN cl = ADOPTOIN.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getID());
							delete.executeUpdate();
							InitThread();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								Msg.Message(ExceptionUtils.getStackTrace(e1));
								Main.logger.error(
										ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
							}
							DbUtil.Log_Error(e);
						}
						newWindow_yn.close();
					}
				});
				newWindow_yn.setTitle("��������");
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

	ADOPTOIN Initialize2(Long docid) {
		ADOPTOIN list = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy
			// HH:mm:ss");

			String selectStmt = "select * from VADOPTOIN t where ID = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ADOPTOIN> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new ADOPTOIN();
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setZAP_SOVET_DEP_TRUD(rs.getString("ZAP_SOVET_DEP_TRUD"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setADMOTHERFIO(rs.getString("ADMOTHERFIO"));
				list.setID(rs.getLong("ID"));
				list.setCUSID_M(rs.getLong("CUSID_M"));
				list.setBRN_CITY(rs.getString("BRN_CITY"));
				list.setZAP_ISPOLKOM_RESH(rs.getString("ZAP_ISPOLKOM_RESH"));
				list.setOLD_BRTH((rs.getDate("OLD_BRTH") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("OLD_BRTH")), formatter)
						: null);
				list.setBRN_AREA(rs.getString("BRN_AREA"));
				list.setCHILDRENFIO(rs.getString("CHILDRENFIO"));
				list.setZAP_NUMBER(rs.getString("ZAP_NUMBER"));
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setCUSID_F_AD(rs.getLong("CUSID_F_AD"));
				list.setCUSID_F(rs.getLong("CUSID_F"));
				list.setADFATHERFIO(rs.getString("ADFATHERFIO"));
				list.setSVID_NOMER(rs.getString("SVID_NOMER"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setBRNACT(rs.getLong("BRNACT"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCUSID_CH(rs.getLong("CUSID_CH"));
				list.setOPER(rs.getString("OPER"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
				list.setADOPT_PARENTS(rs.getString("ADOPT_PARENTS"));
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setMOTHERFIO(rs.getString("MOTHERFIO"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setBRN_OBL_RESP(rs.getString("BRN_OBL_RESP"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setNEW_BRTH((rs.getDate("NEW_BRTH") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("NEW_BRTH")), formatter)
						: null);
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setCUSID_M_AD(rs.getLong("CUSID_M_AD"));
				list.setZAP_DATE((rs.getDate("ZAP_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("ZAP_DATE")), formatter)
						: null);
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));

				list.setOLD_LASTNAME_AB(rs.getString("OLD_LASTNAME_AB"));
				list.setOLD_FIRSTNAME_AB(rs.getString("OLD_FIRSTNAME_AB"));
				list.setOLD_MIDDLNAME_AB(rs.getString("OLD_MIDDLNAME_AB"));
				list.setNEW_LASTNAME_AB(rs.getString("NEW_LASTNAME_AB"));
				list.setNEW_FIRSTNAME_AB(rs.getString("NEW_FIRSTNAME_AB"));
				list.setNEW_MIDDLNAME_AB(rs.getString("NEW_MIDDLNAME_AB"));

				list.setGR_ADOPT(rs.getString("GR_ADOPT"));
				list.setGR_COURT_DATE((rs.getDate("GR_COURT_DATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("GR_COURT_DATE")), formatter) : null);
				list.setGR_COURT(rs.getLong("GR_COURT"));

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
		this.conn.setAutoCommit(false);
		this.from = 1;
		this.conn.setAutoCommit(false);
	}

	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {
				PreparedStatement selforupd = conn
						.prepareStatement("select * from ADOPTOIN where  ID = ? for update nowait");
				ADOPTOIN cl = Initialize2(docid);
				selforupd.setLong(1, cl.getID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DbUtil.Lock_Row(docid, "ADOPTOIN", conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/ru/psv/mj/zags/doc/adoptoin/IUAdopt.fxml"));

						EditAdopt controller = new EditAdopt();
						controller.setId(cl.getID());
						controller.setConn(conn, cl);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("��������������: ");
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									// ���� ������ ���������
									// �������� ��� ����������
									controller.SaveToCompare();
									if (controller.getStatus()) {
										conn.commit();

										if (from == null) {
											InitThread();
										}
										// ������� ������ � "�����"=
										String lock = DbUtil.Lock_Row_Delete(docid, "ADOPTOIN", conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
										isopen = false;
									} // ���� ������ "X" ��� "Cancel" � �� ����� ���-�� ������
									else if (!controller.getStatus() & CompareBeforeClose(docid) == 1) {
										/**
										 * �������� ������ ��� ����������
										 */
										Stage stage = stage_;
										Label alert = new Label("������� ����� ��� ����������?");
										alert.setLayoutX(75.0);
										alert.setLayoutY(11.0);
										alert.setPrefHeight(17.0);

										Button no = new Button();
										no.setText("���");
										no.setLayoutX(111.0);
										no.setLayoutY(56.0);
										no.setPrefWidth(72.0);
										no.setPrefHeight(21.0);

										Button yes = new Button();
										yes.setText("��");
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
												// ������� ������ � "�����"=
												String lock = DbUtil.Lock_Row_Delete(docid, "ADOPTOIN", conn);
												if (lock != null) {// if error add row
													Msg.Message(lock);
												}
												isopen = false;
											}
										});
										newWindow_yn.setTitle("��������");
										newWindow_yn.setScene(ynScene);
										newWindow_yn.initModality(Modality.WINDOW_MODAL);
										newWindow_yn.initOwner(stage);
										newWindow_yn.setResizable(false);
										newWindow_yn.getIcons().add(new Image("/icon.png"));
										newWindow_yn.showAndWait();
									} // ���� ������ "X" ��� "Cancel" � �� ����� ������ �� ������
									else if (!controller.getStatus() & CompareBeforeClose(docid) == 0) {
										isopen = false;
										// ������� ������ � "�����"=
										String lock = DbUtil.Lock_Row_Delete(docid, "ADOPTOIN", conn);
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
						Msg.Message("������ ������������� " + DbUtil.Lock_Row_View(docid, "ADOPTOIN"));
						DbUtil.Log_Error(e);
					} else {
						DbUtil.Log_Error(e);
					}
				}

			} else {
				Msg.Message("����� �������������� ��� �������!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	void Print() {
		try {
			if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {
					@Override
					public Object call() throws Exception {
						// �����
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/ADOPTOIN.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn.prepareStatement("select * from V_REP_ADOPTOIN where ID = ?");
						prepStmt.setLong(1, ADOPTOIN.getSelectionModel().getSelectedItem().getID());
						ResultSet rs = prepStmt.executeQuery();
						V_REP_ADOPTOIN list = null;
						if (rs.next()) {
							list = new V_REP_ADOPTOIN();
							list.setZAP_SOVET_DEP_TRUD(rs.getString("ZAP_SOVET_DEP_TRUD"));
							list.setID(rs.getString("ID"));
							list.setF_AD_ADDR_COUNTRY(rs.getString("F_AD_ADDR_COUNTRY"));
							list.setBRN_CITY(rs.getString("BRN_CITY"));
							list.setF_AD_NAT_NAME(rs.getString("F_AD_NAT_NAME"));
							list.setF_LNAME(rs.getString("F_LNAME"));
							list.setBRN_OBL_RESP(rs.getString("BRN_OBL_RESP"));
							list.setNEW_BRTH(rs.getString("NEW_BRTH"));
							list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
							list.setF_AD_ADDR_AREA(rs.getString("F_AD_ADDR_AREA"));
							list.setZAP_ISPOLKOM_RESH(rs.getString("ZAP_ISPOLKOM_RESH"));
							list.setBR_ACT_ID(rs.getString("BR_ACT_ID"));
							list.setSVID_SERIA(rs.getString("SVID_SERIA"));
							list.setBRN_AREA(rs.getString("BRN_AREA"));
							list.setM_NAT_NAME(rs.getString("M_NAT_NAME"));
							list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
							list.setM_AD_ADDR_AREA(rs.getString("M_AD_ADDR_AREA"));
							list.setM_AD_MNAME(rs.getString("M_AD_MNAME"));
							list.setBR_ACT_DATE(rs.getString("BR_ACT_DATE"));
							list.setF_AD_ST_H_KV(rs.getString("F_AD_ST_H_KV"));
							list.setF_AD_ADDR_CITY(rs.getString("F_AD_ADDR_CITY"));
							list.setF_AD_FNAME(rs.getString("F_AD_FNAME"));
							list.setOLD_BRTH(rs.getString("OLD_BRTH"));
							list.setM_FNAME(rs.getString("M_FNAME"));
							list.setM_AD_NAT_NAME(rs.getString("M_AD_NAT_NAME"));
							list.setF_NAT_NAME(rs.getString("F_NAT_NAME"));
							list.setADOPT_PARENTS(rs.getString("ADOPT_PARENTS"));
							list.setM_LNAME(rs.getString("M_LNAME"));
							list.setDOC_DATE(rs.getString("DOC_DATE"));
							list.setM_AD_LNAME(rs.getString("M_AD_LNAME"));
							list.setF_FNAME(rs.getString("F_FNAME"));
							list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
							list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
							list.setM_MNAME(rs.getString("M_MNAME"));
							list.setM_AD_ADDR_COUNTRY(rs.getString("M_AD_ADDR_COUNTRY"));
							list.setF_MNAME(rs.getString("F_MNAME"));
							list.setM_AD_ST_H_KV(rs.getString("M_AD_ST_H_KV"));
							list.setF_AD_LNAME(rs.getString("F_AD_LNAME"));
							list.setM_AD_FNAME(rs.getString("M_AD_FNAME"));
							list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
							list.setM_AD_ADDR_CITY(rs.getString("M_AD_ADDR_CITY"));
							list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
							list.setF_AD_MNAME(rs.getString("F_AD_MNAME"));
							list.setSVID_NOMER(rs.getString("SVID_NOMER"));

						}
						prepStmt.close();
						rs.close();
						// ------------
						variables.addTextVariable(new TextVariable("#{ID}", list.getID()));
						variables.addTextVariable(
								new TextVariable("#{ZAP_SOVET_DEP_TRUD}", list.getZAP_SOVET_DEP_TRUD()));
						variables.addTextVariable(new TextVariable("#{BRN_CITY}", list.getBRN_CITY()));
						variables
								.addTextVariable(new TextVariable("#{F_AD_ADDR_COUNTRY}", list.getF_AD_ADDR_COUNTRY()));
						variables.addTextVariable(new TextVariable("#{F_AD_NAT_NAME}", list.getF_AD_NAT_NAME()));
						variables.addTextVariable(new TextVariable("#{F_LNAME}", list.getF_LNAME()));
						variables.addTextVariable(new TextVariable("#{BRN_OBL_RESP}", list.getBRN_OBL_RESP()));
						variables.addTextVariable(new TextVariable("#{NEW_BRTH}", list.getNEW_BRTH()));
						variables.addTextVariable(new TextVariable("#{OLD_FIRSTNAME}", list.getOLD_FIRSTNAME()));
						variables.addTextVariable(new TextVariable("#{F_AD_ADDR_AREA}", list.getF_AD_ADDR_AREA()));
						variables
								.addTextVariable(new TextVariable("#{ZAP_ISPOLKOM_RESH}", list.getZAP_ISPOLKOM_RESH()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_ID}", list.getBR_ACT_ID()));
						variables.addTextVariable(new TextVariable("#{SVID_SERIA}", list.getSVID_SERIA()));
						variables.addTextVariable(new TextVariable("#{BRN_AREA}", list.getBRN_AREA()));
						variables.addTextVariable(new TextVariable("#{M_NAT_NAME}", list.getM_NAT_NAME()));
						variables.addTextVariable(new TextVariable("#{NEW_MIDDLNAME}", list.getNEW_MIDDLNAME()));
						variables.addTextVariable(new TextVariable("#{M_AD_ADDR_AREA}", list.getM_AD_ADDR_AREA()));
						variables.addTextVariable(new TextVariable("#{M_AD_MNAME}", list.getM_AD_MNAME()));
						variables.addTextVariable(new TextVariable("#{BR_ACT_DATE}", list.getBR_ACT_DATE()));
						variables.addTextVariable(new TextVariable("#{F_AD_ST_H_KV}", list.getF_AD_ST_H_KV()));
						variables.addTextVariable(new TextVariable("#{F_AD_ADDR_CITY}", list.getF_AD_ADDR_CITY()));
						variables.addTextVariable(new TextVariable("#{F_AD_FNAME}", list.getF_AD_FNAME()));
						variables.addTextVariable(new TextVariable("#{OLD_BRTH}", list.getOLD_BRTH()));
						variables.addTextVariable(new TextVariable("#{M_FNAME}", list.getM_FNAME()));
						variables.addTextVariable(new TextVariable("#{M_AD_NAT_NAME}", list.getM_AD_NAT_NAME()));
						variables.addTextVariable(new TextVariable("#{F_NAT_NAME}", list.getF_NAT_NAME()));
						variables.addTextVariable(new TextVariable("#{ADOPT_PARENTS}", list.getADOPT_PARENTS()));
						variables.addTextVariable(new TextVariable("#{M_LNAME}", list.getM_LNAME()));
						variables.addTextVariable(new TextVariable("#{M_AD_LNAME}", list.getM_AD_LNAME()));
						variables.addTextVariable(new TextVariable("#{DOC_DATE}", list.getDOC_DATE()));
						variables.addTextVariable(new TextVariable("#{F_FNAME}", list.getF_FNAME()));
						variables.addTextVariable(new TextVariable("#{NEW_LASTNAME}", list.getNEW_LASTNAME()));
						variables.addTextVariable(new TextVariable("#{OLD_MIDDLNAME}", list.getOLD_MIDDLNAME()));
						variables.addTextVariable(new TextVariable("#{M_MNAME}", list.getM_MNAME()));
						variables
								.addTextVariable(new TextVariable("#{M_AD_ADDR_COUNTRY}", list.getM_AD_ADDR_COUNTRY()));
						variables.addTextVariable(new TextVariable("#{F_MNAME}", list.getF_MNAME()));
						variables.addTextVariable(new TextVariable("#{M_AD_ST_H_KV}", list.getM_AD_ST_H_KV()));
						variables.addTextVariable(new TextVariable("#{F_AD_LNAME}", list.getF_AD_LNAME()));
						variables.addTextVariable(new TextVariable("#{M_AD_FNAME}", list.getM_AD_FNAME()));
						variables.addTextVariable(new TextVariable("#{NEW_FIRSTNAME}", list.getNEW_FIRSTNAME()));
						variables.addTextVariable(new TextVariable("#{M_AD_ADDR_CITY}", list.getM_AD_ADDR_CITY()));
						variables.addTextVariable(new TextVariable("#{OLD_LASTNAME}", list.getOLD_LASTNAME()));
						variables.addTextVariable(new TextVariable("#{F_AD_MNAME}", list.getF_AD_MNAME()));
						variables.addTextVariable(new TextVariable("#{SVID_NOMER}", list.getSVID_NOMER()));

						// fill template
						docx.fillTemplate(variables);
						File tempFile = File.createTempFile("ADOPTOIN", ".docx",
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
						 * System.getenv("MJ_PATH") + "OutReports/ADOPTOIN_" + uuid.toString() +
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

	/**
	 * ���������� ����� �����������
	 */
	void InitThread() {
		try {
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					String selectStmt = "select * from VADOPTOIN t" + ((getWhere() != null) ? getWhere() : "");
					PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
					ResultSet rs = prepStmt.executeQuery();
					while (rs.next()) {
						ADOPTOIN list = new ADOPTOIN();

						list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
						list.setZAGS_ID(rs.getLong("ZAGS_ID"));
						list.setZAP_SOVET_DEP_TRUD(rs.getString("ZAP_SOVET_DEP_TRUD"));
						list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
						list.setADMOTHERFIO(rs.getString("ADMOTHERFIO"));
						list.setID(rs.getLong("ID"));
						list.setCUSID_M(rs.getLong("CUSID_M"));
						list.setBRN_CITY(rs.getString("BRN_CITY"));
						list.setZAP_ISPOLKOM_RESH(rs.getString("ZAP_ISPOLKOM_RESH"));
						list.setOLD_BRTH((rs.getDate("OLD_BRTH") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("OLD_BRTH")), formatter) : null);
						list.setBRN_AREA(rs.getString("BRN_AREA"));
						list.setCHILDRENFIO(rs.getString("CHILDRENFIO"));
						list.setZAP_NUMBER(rs.getString("ZAP_NUMBER"));
						list.setFATHERFIO(rs.getString("FATHERFIO"));
						list.setCUSID_F_AD(rs.getLong("CUSID_F_AD"));
						list.setCUSID_F(rs.getLong("CUSID_F"));
						list.setADFATHERFIO(rs.getString("ADFATHERFIO"));
						list.setSVID_NOMER(rs.getString("SVID_NOMER"));
						list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
						list.setBRNACT(rs.getLong("BRNACT"));
						list.setCR_TIME(rs.getString("CR_TIME"));
						list.setCUSID_CH(rs.getLong("CUSID_CH"));
						list.setOPER(rs.getString("OPER"));
						list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
						list.setADOPT_PARENTS(rs.getString("ADOPT_PARENTS"));
						list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
						list.setMOTHERFIO(rs.getString("MOTHERFIO"));
						list.setDOC_DATE((rs.getDate("DOC_DATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter) : null);
						list.setBRN_OBL_RESP(rs.getString("BRN_OBL_RESP"));
						list.setSVID_SERIA(rs.getString("SVID_SERIA"));
						list.setNEW_BRTH((rs.getDate("NEW_BRTH") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("NEW_BRTH")), formatter) : null);
						list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
						list.setCUSID_M_AD(rs.getLong("CUSID_M_AD"));
						list.setZAP_DATE((rs.getDate("ZAP_DATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("ZAP_DATE")), formatter) : null);
						list.setCR_DATE((rs.getDate("CR_DATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter) : null);
						list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
						list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));

						list.setGR_ADOPT(rs.getString("GR_ADOPT"));
						list.setGR_COURT_DATE((rs.getDate("GR_COURT_DATE") != null) ? LocalDate.parse(
								new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("GR_COURT_DATE")), formatter)
								: null);
						list.setGR_COURT(rs.getLong("GR_COURT"));
						ADOPTOIN.getItems().add(list);
					}
					prepStmt.close();
					rs.close();

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							TableFilter<ADOPTOIN> tableFilter = TableFilter.forTableView(ADOPTOIN).apply();
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

	@Deprecated
	void Refresh_() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy
			// HH:mm:ss");

			String selectStmt = "select * from VADOPTOIN t" + ((getWhere() != null) ? getWhere() : "");

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ADOPTOIN> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				ADOPTOIN list = new ADOPTOIN();

				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setZAP_SOVET_DEP_TRUD(rs.getString("ZAP_SOVET_DEP_TRUD"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setADMOTHERFIO(rs.getString("ADMOTHERFIO"));
				list.setID(rs.getLong("ID"));
				list.setCUSID_M(rs.getLong("CUSID_M"));
				list.setBRN_CITY(rs.getString("BRN_CITY"));
				list.setZAP_ISPOLKOM_RESH(rs.getString("ZAP_ISPOLKOM_RESH"));
				list.setOLD_BRTH((rs.getDate("OLD_BRTH") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("OLD_BRTH")), formatter)
						: null);
				list.setBRN_AREA(rs.getString("BRN_AREA"));
				list.setCHILDRENFIO(rs.getString("CHILDRENFIO"));
				list.setZAP_NUMBER(rs.getString("ZAP_NUMBER"));
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setCUSID_F_AD(rs.getLong("CUSID_F_AD"));
				list.setCUSID_F(rs.getLong("CUSID_F"));
				list.setADFATHERFIO(rs.getString("ADFATHERFIO"));
				list.setSVID_NOMER(rs.getString("SVID_NOMER"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setBRNACT(rs.getLong("BRNACT"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCUSID_CH(rs.getLong("CUSID_CH"));
				list.setOPER(rs.getString("OPER"));
				list.setOLD_FIRSTNAME(rs.getString("OLD_FIRSTNAME"));
				list.setADOPT_PARENTS(rs.getString("ADOPT_PARENTS"));
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setMOTHERFIO(rs.getString("MOTHERFIO"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setBRN_OBL_RESP(rs.getString("BRN_OBL_RESP"));
				list.setSVID_SERIA(rs.getString("SVID_SERIA"));
				list.setNEW_BRTH((rs.getDate("NEW_BRTH") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("NEW_BRTH")), formatter)
						: null);
				list.setNEW_FIRSTNAME(rs.getString("NEW_FIRSTNAME"));
				list.setCUSID_M_AD(rs.getLong("CUSID_M_AD"));
				list.setZAP_DATE((rs.getDate("ZAP_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("ZAP_DATE")), formatter)
						: null);
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));

				list.setGR_ADOPT(rs.getString("GR_ADOPT"));
				list.setGR_COURT_DATE((rs.getDate("GR_COURT_DATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("GR_COURT_DATE")), formatter) : null);
				list.setGR_COURT(rs.getLong("GR_COURT"));

				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			ADOPTOIN.setItems(dlist);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<ADOPTOIN> tableFilter = TableFilter.forTableView(ADOPTOIN).apply();
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

	@FXML
	void CmRefresh(ActionEvent event) {
		InitThread();
	}

	@FXML
	void CmAdd(ActionEvent event) {
		Add();
	}

	@FXML
	void CmEdit(ActionEvent event) {
		if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("�������� ������!");
		} else {
			Edit(ADOPTOIN.getSelectionModel().getSelectedItem().getID(), (Stage) ADOPTOIN.getScene().getWindow());
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
		if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("�������� ������!");
		} else {
			Edit(ADOPTOIN.getSelectionModel().getSelectedItem().getID(), (Stage) ADOPTOIN.getScene().getWindow());
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

	void CellDateFormatDT(TableColumn<ADOPTOIN, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<ADOPTOIN, LocalDateTime> cell = new TableCell<ADOPTOIN, LocalDateTime>() {
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

	void CellDateFormatD(XTableColumn<ADOPTOIN, LocalDate> tc) {
		tc.setCellFactory(column -> {
			TableCell<ADOPTOIN, LocalDate> cell = new TableCell<ADOPTOIN, LocalDate>() {
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

	private Executor exec;

	@FXML
	private XTableColumn<ADOPTOIN, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<ADOPTOIN, String> CR_TIME;

	/**
	 * �������������
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			VB.getChildren().remove(FILTER);

			ROOT.setBottom(createOptionPane(ADOPTOIN));

			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});

			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			NEW_FIRSTNAME.setColumnFilter(new PatternColumnFilter<>());
			OLD_LASTNAME.setColumnFilter(new PatternColumnFilter<>());
			NEW_MIDDLNAME.setColumnFilter(new PatternColumnFilter<>());
			NEW_LASTNAME.setColumnFilter(new PatternColumnFilter<>());
			OLD_FIRSTNAME.setColumnFilter(new PatternColumnFilter<>());
			OLD_MIDDLNAME.setColumnFilter(new PatternColumnFilter<>());
			OPER.setColumnFilter(new PatternColumnFilter<>());
			ZAGS_NAME.setColumnFilter(new PatternColumnFilter<>());
			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			dbConnect();
			// DbUtil.Run_Process(conn,getClass().getName());
			//Refresh();
			InitThread();
			/**
			 * ������� �������
			 */
			{
				ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
				NEW_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().NEW_FIRSTNAMEProperty());
				OLD_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_LASTNAMEProperty());
				NEW_MIDDLNAME.setCellValueFactory(cellData -> cellData.getValue().NEW_MIDDLNAMEProperty());
				NEW_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().NEW_LASTNAMEProperty());
				OLD_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_FIRSTNAMEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
				CR_DATE.setCellValueFactory(cellData -> cellData.getValue().CR_DATEProperty());
				// DOC_DATE.setCellValueFactory(cellData ->
				// cellData.getValue().DOC_DATEProperty());
				OLD_MIDDLNAME.setCellValueFactory(cellData -> cellData.getValue().OLD_MIDDLNAMEProperty());
				OPER.setCellValueFactory(cellData -> cellData.getValue().OPERProperty());
				ZAGS_NAME.setCellValueFactory(cellData -> cellData.getValue().ZAGS_NAMEProperty());
				DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			}

			// ������� ������
			ADOPTOIN.setRowFactory(tv -> {
				TableRow<ADOPTOIN> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit(ADOPTOIN.getSelectionModel().getSelectedItem().getID(),
								(Stage) ADOPTOIN.getScene().getWindow());
					}
				});
				return row;
			});
			/**
			 * ��
			 */
			{
				// CellDateFormatDT(DOC_DATE);
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

	public AdoptList() {
		Main.logger = Logger.getLogger(getClass());
		this.Where = new SimpleStringProperty();
	}

	/**
	 * ��������� ������
	 * 
	 * @return
	 */
	Long CompareBeforeClose(Long docid) {
		Long ret = 0l;
		try {
			Clob lob = conn.createClob();
			lob.setString(1, RetXml);
			CallableStatement callStmt = conn.prepareCall("{ call ADOPT.CompareXmls(?,?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.setClob(2, lob);
			callStmt.registerOutParameter(3, Types.VARCHAR);
			callStmt.registerOutParameter(4, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(3) == null) {
				ret = callStmt.getLong(4);
				System.out.println("ret=" + callStmt.getLong(4));
				callStmt.close();
			} else {
				Msg.Message(callStmt.getString(3));
				Main.logger.error(callStmt.getString(3) + "~" + Thread.currentThread().getName());
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

		return ret;
	}

	String RetXml;

	/**
	 * ������� XML ������ ��� ���������
	 */
	void XmlsForCompare(Long docid) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call ADOPT.RetXmls(?,?,?)}");
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
				callStmt.close();
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
