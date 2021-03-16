package mj.doc.adoptoin;

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
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

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
	private XTableColumn<ADOPTOIN, Integer> ID;

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

	void Add() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) ADOPTOIN.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/adoptoin/IUAdopt.fxml"));

			AddAdopt controller = new AddAdopt();
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
			if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {

				Stage stage = (Stage) ADOPTOIN.getScene().getWindow();
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
											+ " delete from ADOPTOIN where ID = ?;" + "commit;" + "end;");
							ADOPTOIN cl = ADOPTOIN.getSelectionModel().getSelectedItem();
							delete.setInt(1, cl.getID());
							delete.executeUpdate();
							Refresh();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								Msg.Message(ExceptionUtils.getStackTrace(e1));
								Main.logger.error(
										ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
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

	ADOPTOIN Initialize2(Integer docid) {
		ADOPTOIN list = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy
			// HH:mm:ss");

			String selectStmt = "select * from VADOPTOIN t where ID = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setInt(1, docid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ADOPTOIN> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				list = new ADOPTOIN();
				list.setOLD_MIDDLNAME(rs.getString("OLD_MIDDLNAME"));
				list.setZAGS_ID(rs.getInt("ZAGS_ID"));
				list.setZAP_SOVET_DEP_TRUD(rs.getString("ZAP_SOVET_DEP_TRUD"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setADMOTHERFIO(rs.getString("ADMOTHERFIO"));
				list.setID(rs.getInt("ID"));
				list.setCUSID_M(rs.getInt("CUSID_M"));
				list.setBRN_CITY(rs.getString("BRN_CITY"));
				list.setZAP_ISPOLKOM_RESH(rs.getString("ZAP_ISPOLKOM_RESH"));
				list.setOLD_BRTH((rs.getDate("OLD_BRTH") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("OLD_BRTH")), formatter)
						: null);
				list.setBRN_AREA(rs.getString("BRN_AREA"));
				list.setCHILDRENFIO(rs.getString("CHILDRENFIO"));
				list.setZAP_NUMBER(rs.getString("ZAP_NUMBER"));
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setCUSID_F_AD(rs.getInt("CUSID_F_AD"));
				list.setCUSID_F(rs.getInt("CUSID_F"));
				list.setADFATHERFIO(rs.getString("ADFATHERFIO"));
				list.setSVID_NOMER(rs.getString("SVID_NOMER"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setBRNACT(rs.getInt("BRNACT"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCUSID_CH(rs.getInt("CUSID_CH"));
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
				list.setCUSID_M_AD(rs.getInt("CUSID_M_AD"));
				list.setZAP_DATE((rs.getDate("ZAP_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("ZAP_DATE")), formatter)
						: null);
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
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

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.from = 1;
		this.conn.setAutoCommit(false);
	}

	public void Edit(Integer docid, Stage stage_) {
		try {
			if (isopen == false) {
				PreparedStatement selforupd = conn
						.prepareStatement("select * from ADOPTOIN where  ID = ? /*for update nowait*/");
				ADOPTOIN cl = Initialize2(docid);
				selforupd.setInt(1, cl.getID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DBUtil.Lock_Row(docid, "ADOPTOIN");
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/doc/adoptoin/IUAdopt.fxml"));

						EditAdopt controller = new EditAdopt();
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
									controller.SaveToCompare();
									if (controller.getStatus()) {
										conn.commit();

										if (from == null) {
											Refresh();
										}
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "ADOPTOIN");
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
												String lock = DBUtil.Lock_Row_Delete(docid, "ADOPTOIN");
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
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DBUtil.Lock_Row_Delete(docid, "ADOPTOIN");
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
						Msg.Message("Запись редактируется " + DBUtil.Lock_Row_View(docid, "ADOPTOIN"));
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

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	void Print() {
		try {
			if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				ROOT.setDisable(true);
				PB.setVisible(true);
				Task<Object> task = new Task<Object>() {
					@Override
					public Object call() throws Exception {
						// Вызов
						Docx docx = new Docx(System.getenv("MJ_PATH") + "Reports/ADOPTOIN.docx");
						docx.setVariablePattern(new VariablePattern("#{", "}"));
						// preparing variables
						Variables variables = new Variables();
						PreparedStatement prepStmt = conn.prepareStatement("select * from V_REP_ADOPTOIN where ID = ?");
						prepStmt.setInt(1, ADOPTOIN.getSelectionModel().getSelectedItem().getID());
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
			DBUtil.LOG_ERROR(e);
		}
	}

	void Refresh() {
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
				list.setZAGS_ID(rs.getInt("ZAGS_ID"));
				list.setZAP_SOVET_DEP_TRUD(rs.getString("ZAP_SOVET_DEP_TRUD"));
				list.setNEW_MIDDLNAME(rs.getString("NEW_MIDDLNAME"));
				list.setADMOTHERFIO(rs.getString("ADMOTHERFIO"));
				list.setID(rs.getInt("ID"));
				list.setCUSID_M(rs.getInt("CUSID_M"));
				list.setBRN_CITY(rs.getString("BRN_CITY"));
				list.setZAP_ISPOLKOM_RESH(rs.getString("ZAP_ISPOLKOM_RESH"));
				list.setOLD_BRTH((rs.getDate("OLD_BRTH") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("OLD_BRTH")), formatter)
						: null);
				list.setBRN_AREA(rs.getString("BRN_AREA"));
				list.setCHILDRENFIO(rs.getString("CHILDRENFIO"));
				list.setZAP_NUMBER(rs.getString("ZAP_NUMBER"));
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setCUSID_F_AD(rs.getInt("CUSID_F_AD"));
				list.setCUSID_F(rs.getInt("CUSID_F"));
				list.setADFATHERFIO(rs.getString("ADFATHERFIO"));
				list.setSVID_NOMER(rs.getString("SVID_NOMER"));
				list.setNEW_LASTNAME(rs.getString("NEW_LASTNAME"));
				list.setBRNACT(rs.getInt("BRNACT"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCUSID_CH(rs.getInt("CUSID_CH"));
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
				list.setCUSID_M_AD(rs.getInt("CUSID_M_AD"));
				list.setZAP_DATE((rs.getDate("ZAP_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("ZAP_DATE")), formatter)
						: null);
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setOLD_LASTNAME(rs.getString("OLD_LASTNAME"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
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
		if (ADOPTOIN.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
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
			Msg.Message("Выберите строку!");
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
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AdoptList");
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
	 * Инициализация
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
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			dbConnect();
			Refresh();
			/**
			 * Столбцы таблицы
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

			// двойной щелчок
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
			 * ФД
			 */
			{
				// CellDateFormatDT(DOC_DATE);
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

	public AdoptList() {
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
			CallableStatement callStmt = conn.prepareCall("{ call ADOPT.CompareXmls(?,?,?,?)}");
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
			CallableStatement callStmt = conn.prepareCall("{ call ADOPT.RetXmls(?,?,?)}");
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
