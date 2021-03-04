package mj.doc.cus;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.app.model.OTD;
import mj.app.model.SqlMap;
import mj.dbutil.DBUtil;
import mj.init.HttpsTrustManager;
import mj.msg.Msg;
import mj.util.ConvConst;

/**
 * �������� ������ ���������� 29.10.2020
 * 
 * @author Said
 *
 */
public class AddCus {

	/**
	 * ��� ��������� "������ ��������"
	 */
	@FXML
	private void CombCountry() {
		CCUSSEX.requestFocus();
	}

	/**
	 * ��� ��������� "���"
	 */
	@FXML
	private void CCUSSEX() {
		CCUSNATIONALITY.requestFocus();
	}

	/**
	 * ��� ��������� "���"
	 */
	@FXML
	private void CCUSNATIONALITY() {
		Citizen.requestFocus();
	}

	String FIRST_NAME = "";
	String LAST_NAME = "";
	String MIDDLE_NAME = "";
	boolean find = false;

	/**
	 * ��� ����������, ����� ����������, ��� ����������� ������ ������...
	 * 
	 * @param event
	 */
	@FXML
	void NoDubl(ActionEvent event) {
		try {
			// ������ �� ���������� �.�.�.
			DUBL.getItems().stream().forEach(o -> {
				try {
					PreparedStatement prpstmt = conn.prepareStatement("select count(*) cnt from cus where "
							+ "upper(cus.CCUSLAST_NAME) = upper(?) " + "and upper(cus.CCUSFIRST_NAME) = upper(?) "
							+ "and upper(cus.CCUSMIDDLE_NAME) =  upper(?) "
					/* + "and cus.dcusbirthday = ?" */);
					prpstmt.setString(1, o.getCCUSLAST_NAME());
					prpstmt.setString(2, o.getCCUSFIRST_NAME());
					prpstmt.setString(3, o.getCCUSMIDDLE_NAME());
					System.out.println(o.getCCUSLAST_NAME());
					System.out.println(o.getCCUSFIRST_NAME());
					System.out.println(o.getCCUSMIDDLE_NAME());
					/*
					 * prpstmt.setDate(4, (o.getDCUSBIRTHDAY() != null) ?
					 * java.sql.Date.valueOf(o.getDCUSBIRTHDAY()) : null);
					 */
					ResultSet rs = prpstmt.executeQuery();
					if (rs.next()) {
						System.out.println("cnt=" + rs.getInt("cnt"));
						if (rs.getInt("cnt") > 0) {
							find = true;
							LAST_NAME = o.getCCUSLAST_NAME();
							FIRST_NAME = o.getCCUSFIRST_NAME();
							MIDDLE_NAME = o.getCCUSMIDDLE_NAME();
						}
					}
					prpstmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					Msg.Message(ExceptionUtils.getStackTrace(e));
					Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
					String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
					String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
					int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
				}
			});

			if (find) {
				Stage stage = (Stage) CCUSFIRST_NAME.getScene().getWindow();
				Label alert = new Label("���������� ������ ���������� ���, ���������� �������� ���������?");
				alert.setLayoutX(10.0);
				alert.setLayoutY(10.0);
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
				Scene ynScene = new Scene(yn, 450, 100);
				Stage newWindow_yn = new Stage();
				no.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						newWindow_yn.close();
					}
				});
				yes.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						DublicateBorder.setVisible(false);
						setUnDisable();
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
			} else {
				setUnDisable();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ��� ����� �������
	 * 
	 * @param event
	 */
	@FXML
	void EnterLastName(ActionEvent event) {
		if (!CCUSLAST_NAME.getText().equals("") & CCUSLAST_NAME.getLength() >= 2) {
			CCUSFIRST_NAME.setDisable(false);
			CCUSFIRST_NAME.requestFocus();
		}
	}

	/**
	 * ��� ����� �����
	 * 
	 * @param event
	 */
	@FXML
	void EnterFirstName(ActionEvent event) {
		if (!CCUSFIRST_NAME.getText().equals("") & CCUSFIRST_NAME.getLength() >= 2) {
			CCUSMIDDLE_NAME.setDisable(false);
			CCUSMIDDLE_NAME.requestFocus();
		}
	}

	/**
	 * ��� ����� �����
	 * 
	 * @param event
	 */
	@FXML
	void EnterMiddleName(ActionEvent event) {
		if (!CCUSMIDDLE_NAME.getText().equals("") & CCUSMIDDLE_NAME.getLength() >= 2) {
			DCUSBIRTHDAY.setDisable(false);
			DCUSBIRTHDAY.requestFocus();
		}
	}

	@FXML
	private ProgressIndicator PROGRESS;

	@FXML
	private BorderPane BP;

	/**
	 * ��� ����� ���� ��������
	 * 
	 * @param event
	 */
	@FXML
	void EnterBirthDate_(ActionEvent event) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			if (!CCUSFIRST_NAME.getText().equals("") & !CCUSLAST_NAME.getText().equals("")
					& !CCUSMIDDLE_NAME.getText().equals("") & DCUSBIRTHDAY.getValue() != null) {
				boolean exists = InitDubli�Table(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(),
						CCUSMIDDLE_NAME.getText(), DCUSBIRTHDAY.getValue());
				System.out.println(exists);
				if (!exists) {
					// ��������� ����� �����������
					new HttpsTrustManager().allowAllSSL();
					Auth1c exdb = new Auth1c();
					String CPU_NAME = exdb.CPU_NAME();
					String DB_NAME = exdb.DB_NAME();
					String HDD_SERIAL = exdb.HDD_SERIAL();
					String LAST_AUTH = exdb.LAST_AUTH();
					String ENCRYPT = exdb.ENCRYPT(DB_NAME, HDD_SERIAL, CPU_NAME);
					String AUTH_1C_DATE = exdb.AUTH_1C_DATE(DB_NAME, LAST_AUTH, ENCRYPT);
					String xml_last_auth = exdb.XML(AUTH_1C_DATE);
					exdb.SAVE_AUTH_1C_DATE(xml_last_auth);
					String request;
					{
						request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<���������>\r\n"
								+ "	<����������������� �������=\"" + DB_NAME + "\" ������������������������=\""
								+ xml_last_auth + "\"/>\r\n" + "	<�������������������>\r\n"
								+ "		<������������������ �������=\"" + CCUSLAST_NAME.getText() + "\" ���=\""
								+ CCUSFIRST_NAME.getText() + "\" ��������=\"" + CCUSMIDDLE_NAME.getText()
								+ "\" ������������=\"" + DCUSBIRTHDAY.getValue().format(formatter) + " 0:00:00\"/>\r\n"
								+ "	</�������������������>\r\n" + "</���������>";
						System.out.println(request);
					}
					String SENDFIO_1C = exdb.SENDFIO_1C(request);
					System.out.println(SENDFIO_1C);
					// ���� ����� ������������ ������ ������ ������ ������� � �� �����
					if (!request.equals(SENDFIO_1C)) {

						// osn data
						{
							SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
							String readRecordSQL = sql.getSql("osn_data1c");
							// xml ��� clob
							Clob xml_clob = conn.createClob();
							xml_clob.setString(1, SENDFIO_1C);
							PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
							prepStmt.setClob(1, xml_clob);
							ResultSet rs = prepStmt.executeQuery();
							if (rs.next()) {
								// -----------��������___������----------
								CCUSLAST_NAME.setText(rs.getString("LAST_NAME"));
								CCUSFIRST_NAME.setText(rs.getString("FIRST_NAME"));
								CCUSMIDDLE_NAME.setText(rs.getString("MIDDLE_NAME"));
								/*
								 * if (rs.getDate("BIRTH_DATE") != null) { DCUSBIRTHDAY
								 * .setValue((rs.getDate("BIRTH_DATE") != null) ? LocalDate.parse(new
								 * SimpleDateFormat("dd.MM.yyyy") .format(rs.getDate("BIRTH_DATE")), formatter)
								 * : null); }
								 */
								if (rs.getInt("SEX_CODE") == 1) {
									CCUSSEX.getSelectionModel().select("�������");
								}
								if (rs.getInt("SEX_CODE") == 2) {
									CCUSSEX.getSelectionModel().select("�������");
								}
								// ---------------------
							}
							prepStmt.close();
							rs.close();
						}
						// address
						{
							SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
							String readRecordSQL = sql.getSql("address1c");
							// xml ��� clob
							Clob xml_clob = conn.createClob();
							xml_clob.setString(1, SENDFIO_1C);
							PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
							prepStmt.setClob(1, xml_clob);
							ResultSet rs = prepStmt.executeQuery();
							if (rs.next()) {
								Address.setExpanded(true);
								// CALFA_2.setText(String.valueOf(rs.getInt("AREA_CODE")));
								// CLONGNAMET.setText(rs.getString("AREA_NAME"));
								AREA.getSelectionModel().select(rs.getString("AREA_NAME"));
								PUNCT_NAME.getSelectionModel().select(rs.getString("NASPUNCT_NAME"));
								INFR_NAME.setText(rs.getString("STREET"));
								DOM.setText(rs.getString("DOM"));
								KORP.setText(rs.getString("KORPUS"));
								KV.setText(rs.getString("KV"));
								// ---------------------
							}
							prepStmt.close();
							rs.close();
						}
						// documents
						{
							SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
							String readRecordSQL = sql.getSql("docs1c");
							String insert_doc_temp = sql.getSql("insert_doc_temp");
							// xml ��� clob
							Clob xml_clob = conn.createClob();
							xml_clob.setString(1, SENDFIO_1C);
							PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
							prepStmt.setClob(1, xml_clob);
							ResultSet rs = prepStmt.executeQuery();
							Docs.setExpanded(true);
							while (rs.next()) {
								PreparedStatement insert = conn.prepareStatement(insert_doc_temp);
								insert.setInt(1, rs.getInt("DOC_TYPE"));
								insert.setString(2, rs.getString("NOMER"));
								insert.setString(3, rs.getString("SERIA"));
								insert.setDate(4, rs.getDate("DATE_VID"));
								insert.setDate(5, rs.getDate("DATE_DEIST"));
								insert.setString(6, rs.getString("GUID"));
								insert.setString(7, rs.getString("KEM_VIDAN"));
								insert.execute();
								insert.close();
							}
							prepStmt.close();
							rs.close();
						}
						// refresh doc table
						InitCusDocum();
						setUnDisable();
					}
				}
			}
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ��� ����� ���� ��������
	 * 
	 * @param event
	 * @throws MalformedURLException 
	 */
	@FXML
	void EnterBirthDate(ActionEvent event) throws MalformedURLException {
		// ScrollPaneCus.vvalueProperty().bind(OsnVbox.heightProperty());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		if (!CCUSFIRST_NAME.getText().equals("") & !CCUSLAST_NAME.getText().equals("")
				& !CCUSMIDDLE_NAME.getText().equals("") & DCUSBIRTHDAY.getValue() != null) {
			boolean exists = InitDubli�Table(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(),
					CCUSMIDDLE_NAME.getText(), DCUSBIRTHDAY.getValue());
			System.out.println("~" + exists);
			if (exists == false) {
				// ���� ���������������
//				BP.setDisable(true);
//				PROGRESS.setVisible(true);
//				Task<Object> task = new Task<Object>() {
//					@Override
//					public Object call() throws Exception {
						// ��������� ����� �����������
						new HttpsTrustManager().allowAllSSL();
						Auth1c exdb = new Auth1c();

						// ��������� ������������� ������
						String CPU_NAME = exdb.CPU_NAME();
						String DB_NAME = exdb.DB_NAME();
						String HDD_SERIAL = exdb.HDD_SERIAL();
						// String LAST_AUTH = exdb.LAST_AUTH();
						String ENCRYPT = exdb.ENCRYPT(DB_NAME, HDD_SERIAL, CPU_NAME);

						// ��������� � �������
						String auth = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<���������>\r\n"
								+ "<�������������������� ����������=\"" + ENCRYPT + "\" ID����=\"" + exdb.ID()
								+ "\"/>\r\n" + "</���������>\r\n";
						URL url = new URL(exdb.FullAddress() + "/Authorization");
						String AuthReturn = exdb.Call1cHttpService(auth, exdb.LOGIN(), exdb.PASSWORD(), url);
						Main.logger.info("~~~~~~~~~~~~~~~");
						Main.logger.info("AuthReturn=<"+AuthReturn+">");
						Main.logger.info("~~~~~~~~~~~~~~~");
						String xml_last_auth = exdb.XML(AuthReturn);
						exdb.SAVE_AUTH_1C_DATE(xml_last_auth);
						String request;
						{
							request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<���������>\r\n"
									+ "	<����������������� ID����=\"" + exdb.ID() + "\" ������������������������=\""
									+ xml_last_auth + "\"/>\r\n" + "	<�������������������>\r\n"
									+ "		<������������������ �������=\"" + CCUSLAST_NAME.getText() + "\" ���=\""
									+ CCUSFIRST_NAME.getText() + "\" ��������=\"" + CCUSMIDDLE_NAME.getText()
									+ "\" ������������=\"" + DCUSBIRTHDAY.getValue().format(formatter)
									+ " 0:00:00\"/>\r\n" + "	</�������������������>\r\n" + "</���������>";
							System.out.println(request);
						}
						URL url2 = new URL(exdb.FullAddress() + "GetData/FIO");
						String SENDFIO_1C = exdb.Call1cHttpService(request, exdb.LOGIN(), exdb.PASSWORD(), url2);// exdb.SENDFIO_1C(request);
						System.out.println(SENDFIO_1C);
						// ���� ����� ������������ ������ ������ ������ ������� � �� �����
						if (!SENDFIO_1C.equals("")) {
							Platform.runLater(() -> {
								try {
									// osn data
									{
										SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
										String readRecordSQL = sql.getSql("osn_data1c");
										// xml ��� clob
										Clob xml_clob = conn.createClob();
										xml_clob.setString(1, SENDFIO_1C);
										PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
										prepStmt.setClob(1, xml_clob);
										ResultSet rs = prepStmt.executeQuery();
										if (rs.next()) {
											// -----------��������___������----------
											CCUSLAST_NAME.setText(rs.getString("LAST_NAME"));
											CCUSFIRST_NAME.setText(rs.getString("FIRST_NAME"));
											CCUSMIDDLE_NAME.setText(rs.getString("MIDDLE_NAME"));
											/*
											 * if (rs.getDate("BIRTH_DATE") != null) { DCUSBIRTHDAY
											 * .setValue((rs.getDate("BIRTH_DATE") != null) ? LocalDate.parse(new
											 * SimpleDateFormat("dd.MM.yyyy") .format(rs.getDate("BIRTH_DATE")),
											 * formatter) : null); }
											 */
											if (rs.getInt("SEX_CODE") == 1) {
												CCUSSEX.getSelectionModel().select("�������");
											}
											if (rs.getInt("SEX_CODE") == 2) {
												CCUSSEX.getSelectionModel().select("�������");
											}
											// ---------------------
										}
										prepStmt.close();
										rs.close();
									}
									// address
									{
										SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
										String readRecordSQL = sql.getSql("address1c");
										// xml ��� clob
										Clob xml_clob = conn.createClob();
										xml_clob.setString(1, SENDFIO_1C);
										PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
										prepStmt.setClob(1, xml_clob);
										ResultSet rs = prepStmt.executeQuery();
										if (rs.next()) {
											Address.setExpanded(true);
											// CALFA_2.setText(String.valueOf(rs.getInt("AREA_CODE")));
											// CLONGNAMET.setText(rs.getString("AREA_NAME"));
											AREA.getSelectionModel().select(rs.getString("AREA_NAME"));
											PUNCT_NAME.getSelectionModel().select(rs.getString("NASPUNCT_NAME"));
											INFR_NAME.setText(rs.getString("STREET"));
											DOM.setText(rs.getString("DOM"));
											KORP.setText(rs.getString("KORPUS"));
											KV.setText(rs.getString("KV"));
											// ---------------------
										}
										prepStmt.close();
										rs.close();
									}
									// documents
									{
										SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
										String readRecordSQL = sql.getSql("docs1c");
										String insert_doc_temp = sql.getSql("insert_doc_temp");
										// xml ��� clob
										Clob xml_clob = conn.createClob();
										xml_clob.setString(1, SENDFIO_1C);
										PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
										prepStmt.setClob(1, xml_clob);
										ResultSet rs = prepStmt.executeQuery();
										Docs.setExpanded(true);
										while (rs.next()) {
											PreparedStatement insert = conn.prepareStatement(insert_doc_temp);
											insert.setInt(1, rs.getInt("DOC_TYPE"));
											insert.setString(2, rs.getString("NOMER"));
											insert.setString(3, rs.getString("SERIA"));
											insert.setDate(4, rs.getDate("DATE_VID"));
											insert.setDate(5, rs.getDate("DATE_DEIST"));
											insert.setString(6, rs.getString("GUID"));
											insert.setString(7, rs.getString("KEM_VIDAN"));
											insert.execute();
											insert.close();
										}
										prepStmt.close();
										rs.close();
									}
									// refresh doc table
									InitCusDocum();
									setUnDisable();
								} catch (Exception e) {
									Main.logger = Logger.getLogger(getClass());
									e.printStackTrace();
									Msg.Message(ExceptionUtils.getStackTrace(e));
									Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
									String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
									String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
									int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
									DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
								}
							});
						} else {
							setUnDisable();
						}
//						return null;
//					}
//				};
//				task.setOnFailed(e -> {
//					Msg.Message(task.getException().getMessage());
//					task.getException().printStackTrace();
//				});
//				task.setOnSucceeded(e -> {
//					BP.setDisable(false);
//					PROGRESS.setVisible(false);
//				});
//				exec.execute(task);
			} else {
				// setUnDisable();
			}
		}
	}

	/**
	 * �������������
	 */
	void setDisable() {
		Citizen.setDisable(true);
		Docs.setDisable(true);
		Address.setDisable(true);

		CCUSFIRST_NAME.setDisable(true);
		CCUSMIDDLE_NAME.setDisable(true);
		DCUSBIRTHDAY.setDisable(true);
		CCUSPLACE_BIRTH.setDisable(true);
		ICUSOTD.setDisable(true);
		CCUSSEX.setDisable(true);
		CombCountry.setDisable(true);
		CCUSNATIONALITY.setDisable(true);
	}

	/**
	 * ��������������
	 */
	void setUnDisable() {

		Citizen.setDisable(false);
		Docs.setDisable(false);
		Address.setDisable(false);
		CCUSFIRST_NAME.setDisable(false);
		CCUSMIDDLE_NAME.setDisable(false);
		DCUSBIRTHDAY.setDisable(false);
		CCUSPLACE_BIRTH.setDisable(false);
		ICUSOTD.setDisable(false);
		CombCountry.setDisable(false);
		CCUSSEX.setDisable(false);
		CCUSNATIONALITY.setDisable(false);
		DublicateBorder.setVisible(false);
	}

	/**
	 * ��������� �������, ���� ������
	 */
	void InitIfDublic() {

	}

	/**
	 * ��������� �������, ���� ������
	 */
	boolean InitDubli�Table(String lname, String fname, String mname, LocalDate bdate) {
		boolean exists = false;
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

			String selectStmt = "select * from table(MJCUS.CheckBeforeAdd(last_name   => ?,\n"
					+ "                                  first_name  => ?,\n"
					+ "                                  middle_name => ?,\n"
					+ "                                  birth_date  => ?))";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setString(1, lname);
			prepStmt.setString(2, fname);
			prepStmt.setString(3, mname);
			prepStmt.setDate(4, java.sql.Date.valueOf(bdate));
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS list = new CUS();
				list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
				list.setICUSNUM(rs.getInt("ICUSNUM"));
				list.setCCUSIDOPEN(rs.getString("CCUSIDOPEN"));
				list.setICUSOTD(rs.getInt("ICUSOTD"));
				list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
				list.setDCUSEDIT((rs.getDate("DCUSEDIT") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSEDIT")), formatter)
						: null);
				list.setCCUSPLACE_BIRTH(rs.getString("CCUSPLACE_BIRTH"));
				list.setCCUSNAME(rs.getString("CCUSNAME"));
				list.setDCUSOPEN((rs.getDate("DCUSOPEN") != null)
						? LocalDateTime.parse(
								new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DCUSOPEN")), formatterdt)
						: null);
				list.setCCUSSEX(rs.getInt("CCUSSEX"));
				list.setCCUSNAME_SH(rs.getString("CCUSNAME_SH"));
				list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
				list.setCCUSCOUNTRY1(rs.getString("CCUSCOUNTRY1"));
				list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				list.setCCUS_OK_SM(rs.getString("CCUS_OK_SM"));

				dlist.add(list);
				exists = true;
			}
			prepStmt.close();
			rs.close();

			if (exists) {
				DublicateBorder.setVisible(true);
				// DublicateBorder.setMaxHeight(150.0);
			}

			DUBL.setItems(dlist);

			TableFilter<CUS> tableFilter = TableFilter.forTableView(DUBL).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
		return exists;
	}

	// _______���_����������___________
	@FXML
	private TableView<CUS> DUBL;

	@FXML
	private TableColumn<CUS, String> LastName;

	@FXML
	private TableColumn<CUS, String> FirstName;

	@FXML
	private TableColumn<CUS, String> MiddleName;

	@FXML
	private TableColumn<Object, LocalDate> BirthDate;

	@FXML
	private TitledPane Citizen;

	@FXML
	private TitledPane DublicateBorder;

	@FXML
	private TitledPane Address;

	@FXML
	private TitledPane Docs;

	// _______________________
	@FXML
	private Tab DocTab;

	/**
	 * ������
	 */
	@FXML
	private TextField KORP;

	/**
	 * ������� ����������
	 */
	@FXML
	private TableView<CUS_DOCUM> CUS_DOCUM;

	/**
	 * �������� �� ��������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> PREF;

	/**
	 * ���� ������ ��������� TextField
	 */
	@FXML
	private DatePicker DOC_DATE_T;

	/**
	 * ����� ���������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_SER;

	/**
	 * ������ ��������
	 */
	@FXML
	private ComboBox<COUNTRIES> CombCountry;

	/**
	 * ������
	 */
	@FXML
	private ComboBox<COUNTRIES> CombCountryAddr;

	/**
	 * ��� ���������
	 */
	@FXML
	private ComboBox<VPUD> ID_DOC_TP_T;

	/**
	 * ��� ���������� � �������������� ���������
	 */
	@FXML
	private TitledPane TitledCRUDCusDocum;

	/**
	 * �������� ������
	 */
	@FXML
	private TitledPane OSN_DATA;

	/**
	 * �����
	 */
	@FXML
	private ComboBox<String> AREA;

	/**
	 * ���
	 */
	@FXML
	private TextField CCUSFIRST_NAME;

	/**
	 * ������ ������� �����������
	 */
	// @FXML
	// private TableColumn<CUS_CITIZEN, String> COUNTRY;

	/**
	 * ����� ���������
	 */
	@FXML
	private TextField DOC_SER_T;

	/**
	 * ���������
	 */
	@FXML
	private ComboBox<String> ICUSOTD;

	/**
	 * ���� ��������
	 */
	@FXML
	private DatePicker DCUSBIRTHDAY;

	/**
	 * ����� ��������
	 */
	@FXML
	private TextField CCUSPLACE_BIRTH;

	/**
	 * �������� �� ��������
	 */
	@FXML
	private CheckBox PREF_T;

	/**
	 * ��� ��������� �������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> ID_DOC_TP;

	/**
	 * ���
	 */
	@FXML
	private ComboBox<String> CCUSSEX;

	/**
	 * ���
	 */
	@FXML
	private TextField DOM;

	/**
	 * ������ �������� ��������
	 */
	@FXML
	private Button AddCusDocum;

	/**
	 * ������ �������������� ���������
	 */
	@FXML
	private Button EditCusDocum;

	/**
	 * ��� ����� ��������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_SUBDIV;

	/**
	 * �������� �� ��� �����������
	 */
	@FXML
	private TableColumn<CUS_CITIZEN, Boolean> OSN;

	/**
	 * ��������
	 */
	@FXML
	private TextField KV;

	/**
	 * ����� ���������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_NUM;

	/**
	 * ���������� ������
	 */
	@FXML
	private ComboBox<String> PUNCT_NAME;

	/**
	 * ����� ���������
	 */
	@FXML
	private TextField DOC_NUM_T;

	/**
	 * ��������������
	 */
	@FXML
	private ComboBox<String> CCUSNATIONALITY;

	/**
	 * ��� �����
	 */
	@FXML
	private TextField DOC_AGENCY_T;

	/**
	 * �������
	 */
	@FXML
	private TextField CCUSLAST_NAME;

	/**
	 * ������ ������� ��������
	 */
	@FXML
	private Button BtDelDocum;

	/**
	 * ��������
	 */
	@FXML
	private TextField CCUSMIDDLE_NAME;

	/**
	 * ������� �����������
	 */
	@FXML
	private TableView<CUS_CITIZEN> CUS_CITIZEN;

	/**
	 * ��� �����
	 */
	@FXML
	private TableColumn<CUS_DOCUM, String> DOC_AGENCY;

	/**
	 * �������������� - �����
	 */
	@FXML
	private TextField INFR_NAME;

	/**
	 * ��� �������������
	 */
	@FXML
	private TextField DOC_SUBDIV_T;

	/**
	 * 
	 */
	@FXML
	private ScrollPane ScrollPaneCus;

	/**
	 * ���� ��������� ���������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, LocalDate> DOC_PERIOD;

	/**
	 * 
	 */
	@FXML
	private DatePicker DOC_PERIOD_T;

	/**
	 * ���� ������ ���������
	 */
	@FXML
	private TableColumn<CUS_DOCUM, LocalDate> DOC_DATE;

	/**
	 * ������������ ������
	 */
	@FXML
	private TableColumn<CUS_CITIZEN, String> CLONGNAME;

	/**
	 * ������ ��������������
	 * 
	 * @param event
	 */
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	/**
	 * �������� ��������������, ���� �����������
	 */
	void AddNationalityIfNotExist() {
		Stage stage = (Stage) CCUSNATIONALITY.getScene().getWindow();
		Label alert = new Label("�������������� ����������� � ������, ��������?");
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
		Scene ynScene = new Scene(yn, 400, 100);
		Stage newWindow_yn = new Stage();
		no.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				CCUSNATIONALITY.setValue("");
				newWindow_yn.close();
			}
		});
		yes.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					PreparedStatement delete = conn.prepareStatement("declare\n" + "  pragma autonomous_transaction;\n"
							+ "begin\n" + "  insert into nationality (NAME) values (?);" + " commit;\n" + "end;\n");
					delete.setString(1, CCUSNATIONALITY.getValue());
					delete.executeUpdate();
					delete.close();
					CallSave("N");
				} catch (SQLException e) {
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
		newWindow_yn.setTitle("��������");
		newWindow_yn.setScene(ynScene);
		// Specifies the modality for new window.
		newWindow_yn.initModality(Modality.WINDOW_MODAL);
		// Specifies the owner Window (parent) for new window
		newWindow_yn.initOwner(stage);
		newWindow_yn.setResizable(false);
		newWindow_yn.getIcons().add(new Image("/icon.png"));
		newWindow_yn.show();
	}

	/**
	 * �������� ������ - �����
	 * 
	 * @param event
	 */
	@FXML
	void AddAddressCountry(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CitizenList("address");
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������������� �����������
	 */
	void InitCitizen() {
		try {
			Main.logger = Logger.getLogger(getClass());
			String selectStmt = "select ID,COUNTRY_CODE,COUNTRY_NAME,osn from CUS_CITIZEN_TEMP";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS_CITIZEN> infr_list = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS_CITIZEN infr = new CUS_CITIZEN();
				infr.setID(rs.getInt("ID"));
				infr.setCOUNTRY_CODE(rs.getInt("COUNTRY_CODE"));
				infr.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
				infr.setOSN((rs.getString("OSN").equals("Y")) ? true : false);
				infr_list.add(infr);
			}
			prepStmt.close();
			rs.close();
			CUS_CITIZEN.setItems(infr_list);
			// autoResizeColumns(CUS_CITIZEN);
			TableFilter<CUS_CITIZEN> tableFilter = TableFilter.forTableView(CUS_CITIZEN).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������� � ������������� �����������
	 * 
	 * @param COUNTRY_I
	 * @param CLONGNAME_I
	 * @param osn
	 * @param oper
	 */
	void CRUDCitizen(Integer COUNTRY_I, String CLONGNAME_I, String osn, String oper, String type) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall(oper);
			callStmt.registerOutParameter(1, Types.VARCHAR);
			if (type.equals("add")) {
				callStmt.setInt(2, COUNTRY_I);
				callStmt.setString(3, CLONGNAME_I);
				callStmt.setString(4, osn);
			} else if (type.equals("edit")) {
				CUS_CITIZEN cs = CUS_CITIZEN.getSelectionModel().getSelectedItem();
				callStmt.setInt(2, COUNTRY_I);
				callStmt.setString(3, CLONGNAME_I);
				callStmt.setInt(4, cs.getID());
				callStmt.setString(5, osn);
			}
			callStmt.execute();
			String ret = callStmt.getString(1);
			if (ret.equals("ok")) {
				callStmt.close();
			} else {
				Stage stage_ = (Stage) CCUSLAST_NAME.getScene().getWindow();
				Msg.ErrorView(stage_, "MJCUS_CITIZEN_TEMP", conn);
				callStmt.close();
			}
			InitCitizen();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������� �������� ������
	 * 
	 * @param event
	 */
	@FXML
	void BtAddCitizen(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CitizenList("add");
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������������� �������� ������
	 * 
	 * @param event
	 */
	@FXML
	void BtEditCitizen(ActionEvent event) {
		try {
			if (CUS_CITIZEN.getItems().size() == 1) {
				CUS_CITIZEN.getSelectionModel().select(0);
			}
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {
				Main.logger = Logger.getLogger(getClass());
				CitizenList("edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������� �������� ������
	 * 
	 * @param event
	 */
	@FXML
	void BtDelCitizen(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {
				CUS_CITIZEN cd = CUS_CITIZEN.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("declare " + "pragma autonomous_transaction;begin "
						+ "delete from CUS_CITIZEN_TEMP where id = ?;commit;" + "end;");
				delete.setInt(1, cd.getID());
				delete.executeUpdate();
				delete.close();
				InitCitizen();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������� �������� ������
	 * 
	 * @param event
	 */
	@FXML
	void BtDelDocum(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (CUS_DOCUM.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {
				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("declare " + "pragma autonomous_transaction;begin "
						+ " delete from CUS_DOCUM_TEMP where ID_DOC = ?;commit;" + "end;");
				delete.setInt(1, cd.getID_DOC());
				delete.executeUpdate();
				delete.close();
				/**
				 * ���������� ����� ��������
				 */
				InitCusDocum();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������� ������ ����� - � ������, � �����������
	 * 
	 * @param type
	 */
	void CitizenList(String type) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("�������");
			AnchorPane secondaryLayout = new AnchorPane();
			VBox vb = new VBox();

			CheckBox osn = new CheckBox();
			osn.setText("��������");
			osn.setVisible(false);// ������ �������
			if (type.equals("address") | type.equals("brn")) {
				osn.setVisible(false);
			}
			ToolBar toolBar = new ToolBar(Update, osn);
			XTableView<COUNTRIES> debtinfo = new XTableView<COUNTRIES>();
			XTableColumn<COUNTRIES, String> NAME = new XTableColumn<>("��������");
			NAME.setCellValueFactory(new PropertyValueFactory<>("NAME"));
			debtinfo.getColumns().add(NAME);
			vb.getChildren().add(debtinfo);
			vb.getChildren().add(toolBar);
			debtinfo.getStyleClass().add("mylistview");
			debtinfo.getStylesheets().add("/ScrPane.css");
			vb.setPadding(new Insets(10, 10, 10, 10));
			NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());

			debtinfo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			;
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			NAME.setColumnFilter(new PatternColumnFilter<>());

			String selectStmt = "select * from COUNTRIES t order by CODE asc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<COUNTRIES> user_o_list = FXCollections.observableArrayList();
			while (rs.next()) {
				COUNTRIES list = new COUNTRIES();
				list.setNAME(rs.getString("NAME"));
				list.setCODE(rs.getInt("CODE"));
				user_o_list.add(list);
			}
			
			prepStmt.close();
			rs.close();
			
			debtinfo.setItems(user_o_list);
			autoResizeColumns(debtinfo);
			debtinfo.prefWidth(341);
			debtinfo.prefHeight(490);
			TableFilter<COUNTRIES> tableFilter = TableFilter.forTableView(debtinfo).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});

			/**
			 * ������� ������ �� �������
			 */
			debtinfo.setRowFactory(tv -> {
				TableRow<COUNTRIES> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (debtinfo.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("�������� ������� ������ �� �������!");
						} else {
							COUNTRIES country = debtinfo.getSelectionModel().getSelectedItem();
							/**
							 * �� ���� ������
							 */
							if (type.equals("add")) {
								CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
										"{ ? = call MJCUS.ADD_CUS_CITIZEN_TEMP(?,?,?)}", "add");
							} else if (type.equals("edit")) {
								CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
										"{ ? = call MJCUS.EDIT_CUS_CITIZEN_TEMP(?,?,?,?)}", "edit");
							} else if (type.equals("address")) {
								// CALFA_2.setText(String.valueOf(country.getCODE()));
								// CLONGNAMET.setText(country.getNAME());
								if (country.getCODE() == 1) {
									// AREA.setEditable(true);
								}
							} else if (type.equals("brn")) {
								// CCUS_OK_SM.setText(String.valueOf(country.getCODE()));
								// BurthCountry.setText(country.getNAME());
							}

							((Node) (event.getSource())).getScene().getWindow().hide();
						}
					}
				});
				return row;
			});

			/**
			 * ����������� ��� ��������������
			 */
			if (type.equals("edit")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						CUS_CITIZEN cs = CUS_CITIZEN.getSelectionModel().getSelectedItem();
						for (COUNTRIES os : debtinfo.getItems()) {
							if (os.getCODE().equals(cs.getCOUNTRY_CODE())) {
								debtinfo.requestFocus();
								debtinfo.getSelectionModel().select(os);
								// debtinfo.getFocusModel().focus(os);
								break;
							}
						}

					}
				});
			}

			Update.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					if (debtinfo.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("�������� ������� ������ �� �������!");
					} else {
						COUNTRIES country = debtinfo.getSelectionModel().getSelectedItem();
						/**
						 * �� ���� ������
						 */
						if (type.equals("add")) {
							CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
									"{ ? = call MJCUS.ADD_CUS_CITIZEN_TEMP(?,?,?)}", "add");
						} else if (type.equals("edit")) {
							CRUDCitizen(country.getCODE(), country.getNAME(), (osn.isSelected()) ? "Y" : "N",
									"{ ? = call MJCUS.EDIT_CUS_CITIZEN_TEMP(?,?,?,?)}", "edit");
						} else if (type.equals("address")) {
							// CALFA_2.setText(String.valueOf(country.getCODE()));
							// CLONGNAMET.setText(country.getNAME());
							if (country.getCODE() == 1) {
								// AREA.setEditable(true);
							}
						} else if (type.equals("brn")) {
							// CCUS_OK_SM.setText(String.valueOf(country.getCODE()));
							// BurthCountry.setText(country.getNAME());
						}

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});
			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) CombCountryAddr.getScene().getWindow();
			Stage newWindow = new Stage();
			newWindow.setTitle("������ �����");
			newWindow.setScene(secondScene);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������� �����������
	 * 
	 * @param event
	 */
	@FXML
	void CmAddCitizen(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CitizenList("add");
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������������� �����������
	 * 
	 * @param event
	 */
	@FXML
	void CmEditCitizen(ActionEvent event) {
		try {
			if (CUS_CITIZEN.getItems().size() == 1) {
				CUS_CITIZEN.getSelectionModel().select(0);
			}
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {
				Main.logger = Logger.getLogger(getClass());
				CitizenList("edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������ ����� ��������
	 * 
	 * @param event
	 */
	@FXML
	void BtBurnCountry(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CitizenList("brn");
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ��� ��������� ������
	 * 
	 * @param event
	 */
	@FXML
	void AreaChange(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			/**
			 * ���. ������
			 */
			{
				PreparedStatement sqlStatement = conn.prepareStatement("select * from NAS_PUNKT t where t.AREA = ?");
				sqlStatement.setString(1, AREA.getValue());
				ResultSet rs = sqlStatement.executeQuery();
				ObservableList<String> np = FXCollections.observableArrayList();
				while (rs.next()) {
					np.add(rs.getString(2));
				}
				sqlStatement.close();
				rs.close();
				
				FilteredList<String> filterednationals = new FilteredList<String>(np);

				PUNCT_NAME.getEditor().textProperty()
						.addListener(new InputFilter<String>(PUNCT_NAME, filterednationals, false));
				PUNCT_NAME.setItems(filterednationals);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ��������� ������� ������� � �����������
	 */
	void InitCusDocum() {
		try {
			SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
			String doc_list_temp = sql.getSql("doc_list_temp");
			PreparedStatement prepStmt = conn.prepareStatement(doc_list_temp);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS_DOCUM> docs = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS_DOCUM list = new CUS_DOCUM();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				list.setDOC_SUBDIV(rs.getString("DOC_SUBDIV"));
				list.setDOC_PERIOD((rs.getDate("DOC_PERIOD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_PERIOD")), formatter) : null);
				list.setDOC_AGENCY(rs.getString("DOC_AGENCY"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setDOC_SER(rs.getString("DOC_SER"));
				list.setDOC_NUM(rs.getString("DOC_NUM"));
				list.setID_DOC_TP(rs.getString("ID_DOC_TP"));
				list.setPREF(rs.getString("PREF"));
				list.setID_DOC(rs.getInt("ID_DOC"));
				docs.add(list);
			}
			prepStmt.close();
			rs.close();
			CUS_DOCUM.setItems(docs);
			// autoResizeColumns(CUS_DOCUM);
			TableFilter<CUS_DOCUM> tableFilter = TableFilter.forTableView(CUS_DOCUM).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������� ��������
	 * 
	 * @param event
	 */
	@FXML
	void CmDelCusDocum(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (CUS_DOCUM.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {
				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("declare " + "pragma autonomous_transaction; begin "
						+ " delete from CUS_DOCUM_TEMP where ID_DOC = ?;commit;" + "end;");
				delete.setInt(1, cd.getID_DOC());
				delete.executeUpdate();
				delete.close();
				/**
				 * ���������� ����� ��������
				 */
				InitCusDocum();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ���������� � �������������� ���������
	 * 
	 * @param type
	 */
	void CRUDDocum(String type, String type2) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn.prepareCall(type);
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, (PREF_T.isSelected()) ? "Y" : "N");

			System.out.println((PREF_T.isSelected()) ? "Y" : "N");

			if (ID_DOC_TP_T.getValue() != null) {
				VPUD vp = ID_DOC_TP_T.getSelectionModel().getSelectedItem();
				callStmt.setInt(3, vp.getIPUDID());
			} else {
				callStmt.setNull(3, java.sql.Types.INTEGER);
			}
			callStmt.setString(4, DOC_NUM_T.getText());
			callStmt.setString(5, DOC_SER_T.getText());
			callStmt.setDate(6, (DOC_DATE_T.getValue() != null) ? java.sql.Date.valueOf(DOC_DATE_T.getValue()) : null);
			callStmt.setString(7, DOC_AGENCY_T.getText());
			callStmt.setDate(8,
					(DOC_PERIOD_T.getValue() != null) ? java.sql.Date.valueOf(DOC_PERIOD_T.getValue()) : null);
			callStmt.setString(9, DOC_SUBDIV_T.getText());
			if (type2.equals("edit")) {
				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
				callStmt.setInt(10, cd.getID_DOC());
				System.out.println(cd.getID_DOC());
			}
			callStmt.execute();
			String ret = callStmt.getString(1);
			callStmt.close();
			if (ret.equals("ok")) {
				/**
				 * ��������
				 */
				InitCusDocum();
				System.out.println("��������");
			} else {
				Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
				Msg.ErrorView(stage_, "CUS_DOCUM_TEMP", conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������� ��������
	 * 
	 * @param event
	 */
	@FXML
	void AddCusDocum(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());

			Stage stage = new Stage();
			Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/cus/IUCus_Doc.fxml"));

			Add_Cus_Doc controller = new Add_Cus_Doc();
			loader.setController(controller);
			controller.setConn(conn, true);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("�������� ���������� ������");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					// ������� ������� ��� ��������
					InitCusDocum();
				}
			});
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ������������� ��������
	 * 
	 * @param event
	 */
	@FXML
	void EditCusDocum(ActionEvent event) {
		try {
			// ���� ���������� ����� ����� 1, �� ������� ������ ������
			if (CUS_DOCUM.getItems().size() == 1) {
				CUS_DOCUM.getSelectionModel().select(0);
			}
			if (CUS_DOCUM.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {

				Stage stage = new Stage();
				Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/mj/doc/cus/IUCus_Doc.fxml"));

				CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();

				Edit_Cus_Doc controller = new Edit_Cus_Doc();
				loader.setController(controller);
				controller.setConn(conn, true, cd);
				controller.setId(cd.getID_DOC());

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("������������� ���������� ������");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						// ������� ������� ��� ��������
						InitCusDocum();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������� �����
	 */
	void onclose() {
		Stage stage = (Stage) CombCountryAddr.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void RefreshAllDocs(ActionEvent event) {
		// null
	}

	/**
	 * ������ �������
	 */
	private Connection conn = null;

	/**
	 * ��������� ������ � 1�
	 */
	void Save1c(Integer dbid, String edit) {
		try {
			Main.logger = Logger.getLogger(getClass());
			// ���� ���������������
			BP.setDisable(true);
			PROGRESS.setVisible(true);
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {
					// ��������� ����� �����������
					new HttpsTrustManager().allowAllSSL();
					Auth1c exdb = new Auth1c();
					String CPU_NAME = exdb.CPU_NAME();
					String DB_NAME = exdb.DB_NAME();
					String HDD_SERIAL = exdb.HDD_SERIAL();
					// String LAST_AUTH = exdb.LAST_AUTH();
					String ENCRYPT = exdb.ENCRYPT(DB_NAME, HDD_SERIAL, CPU_NAME);

					// ��������� � �������
					String auth = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<���������>\r\n"
							+ "<�������������������� ����������=\"" + ENCRYPT + "\" ID����=\"" + exdb.ID() + "\"/>\r\n"
							+ "</���������>\r\n";
					URL url = new URL(exdb.FullAddress() + "/Authorization");
					String AuthReturn = exdb.Call1cHttpService(auth, exdb.LOGIN(), exdb.PASSWORD(), url);
					System.out.println(AuthReturn);
					String xml_last_auth = exdb.XML(AuthReturn);
					exdb.SAVE_AUTH_1C_DATE(xml_last_auth);

					SqlMap sql = new SqlMap().load("/mj/doc/cus/SQL.xml");
					String readRecordSQL = sql.getSql("FOR_1c");
					String XML = sql.getSql("1C_XML");
					{
						PreparedStatement select = conn.prepareStatement(readRecordSQL);
						select.setInt(1, dbid);
						ResultSet rs = select.executeQuery();
						if (rs.next()) {
							/*
							 * XML.replace("\"DB_NAME\"", DB_NAME); XML.replace("\"LAST_AUTH\"",
							 * AUTH_1C_DATE); XML.replace("\"OPERATOR\"", DB_NAME + "/" +
							 * rs.getString("OPER")); XML.replace("\"ID\"", ""); XML.replace("\"REF_CODE\"",
							 * ""); XML.replace("\"L_NAME\"", rs.getString("CCUSLAST_NAME"));
							 * XML.replace("\"F_NAME\"", rs.getString("CCUSFIRST_NAME"));
							 * XML.replace("\"M_NAME\"", rs.getString("CCUSMIDDLE_NAME"));
							 * XML.replace("\"B_RDATE\"", rs.getString("BR_DATE"));
							 * XML.replace("\"SEX_CODE\"", rs.getString("CCUSSEX"));
							 * XML.replace("\"AREA_CODE\"", rs.getString("CODE_AREA"));
							 * XML.replace("\"AREA_NAME\"", rs.getString("NAME_AREA"));
							 * XML.replace("\"NASPUNCT_CODE\"", rs.getString("PUNCT_CODE"));
							 * XML.replace("\"NASPUNCT_NAME\"", rs.getString("PUNCT_NAME"));
							 * XML.replace("\"STREET\"", rs.getString("STREET")); XML.replace("\"HOME\"",
							 * rs.getString("DOM")); XML.replace("\"KORPUS\"", rs.getString("KORP"));
							 * XML.replace("\"KVARTIRA\"", rs.getString("KV"));
							 */
							XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<���������>\r\n"
									+ "	<����������������� ID����=\"" + exdb.ID() + "\" ������������������������=\""
									+ xml_last_auth + "\"/>\r\n" + "	<�������������������>\r\n"
									+ "		<������������������ ��������=\"" + DB_NAME + "/" + rs.getString("OPER")
									+ "\" ID=\"\" ���������=\"\" �������=\"" + rs.getString("CCUSLAST_NAME")
									+ "\" ���=\"" + rs.getString("CCUSFIRST_NAME") + "\" ��������=\""
									+ rs.getString("CCUSMIDDLE_NAME") + "\" ������������=\"" + rs.getString("BR_DATE")
									+ "\" �������=\"" + rs.getString("CCUSSEX") + "\"/>\r\n"
									+ "		<��������������� ���������=\"" + rs.getString("CODE_AREA")
									+ "\" ������������������=\"" + rs.getString("NAME_AREA") + "\" ������������=\""
									+ rs.getString("PUNCT_CODE") + "\" ���������������������=\""
									+ rs.getString("PUNCT_NAME") + "\" �����=\"" + rs.getString("STREET") + "\" ���=\""
									+ rs.getString("DOM") + "\" ������=\"" + rs.getString("KORP") + "\" ��������=\""
									+ rs.getString("KV") + "\"/>\r\n";
						}
						select.close();
						rs.close();
					}
					XML = XML + "		<��������>\r\n";
					String docs = "";
					int cnt_doc = 0;
					{
						String XML_CUS_DOCUM = sql.getSql("1C_XML_CUS_DOCUM");
						PreparedStatement doc = conn.prepareStatement(XML_CUS_DOCUM);
						doc.setInt(1, dbid);
						ResultSet rs = doc.executeQuery();
						while (rs.next()) {
							cnt_doc++;
							docs = docs + "			<�������" + String.valueOf(cnt_doc) + " ID=\""
									+ rs.getString("SYS_GUID") + "\" ���������=\"0\" ��������������=\""
									+ rs.getString("COUNTRY_CODE") + "\" �����������������������=\""
									+ rs.getString("COUNTRY_NAME") + "\" �������=\"" + rs.getString("DOC_CODE")
									+ "\" ����������������=\"" + rs.getString("DOC_NAME") + "\" �����=\""
									+ rs.getString("DOC_SER") + "\" �����=\"" + rs.getString("DOC_NUM")
									+ "\" ��������=\"" + rs.getString("DOC_AGENCY") + "\" ����������=\""
									+ rs.getString("DOC_DATE") + "\" ������������=\""
									+ ((!rs.getString("DOC_PERIOD").equals(" 0:00:00")) ? rs.getString("DOC_PERIOD")
											: "01.01.0001 0:00:00")
									+ "\"/>\r\n";
							System.out.println(rs.getString("DOC_PERIOD") + "=DOC_PERIOD");
						}
						doc.close();
						rs.close();
					}
					XML = XML + docs + "		</��������>\r\n	</�������������������>\r\n</���������>";
					System.out.println(XML);
					URL url2 = new URL(exdb.FullAddress() + "Sync/ClientPriority");
					String save_ret_1c = // exdb.SAVEFIO_1C(XML);
							exdb.Call1cHttpService(XML, exdb.LOGIN(), exdb.PASSWORD(), url2);
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println(save_ret_1c);
					if (!save_ret_1c.equals("��������� ������� �����������")) {
						// ������� id-����
						PreparedStatement doc = conn
								.prepareStatement("update cus set cus.ID1C = ? where cus.ICUSNUM = ?");
						doc.setInt(1, Integer.valueOf(save_ret_1c.replace("�", "")));
						doc.setInt(2, dbid);
						doc.executeUpdate();
						doc.close();
					}
					return null;
				}
			};
			task.setOnFailed(e -> {
				Msg.Message(task.getException().getMessage());
				task.getException().printStackTrace();
			});
			task.setOnSucceeded(e -> {
				BP.setDisable(false);
				PROGRESS.setVisible(false);
				onclose();
				if (edit.equals("Y")) {
					CusList cus = new CusList();
					cus.Edit(getId(), (Stage) ((txtfld != null) ? txtfld.getScene().getWindow()
							: CCUSLAST_NAME.getScene().getWindow())/*, ForConn*/);
				}
			});
			exec.execute(task);
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ����� ������ �������������� �������
	 */
	void CallSave(String edit) {
		try {
			Main.logger = Logger.getLogger(getClass());
			CallableStatement callStmt = conn
					.prepareCall("{ ? = call MJCUS.ADD_CUS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setDate(2,
					(DCUSBIRTHDAY.getValue() != null) ? java.sql.Date.valueOf(DCUSBIRTHDAY.getValue()) : null);
			callStmt.setString(3, CCUSLAST_NAME.getText());
			callStmt.setString(4, CCUSFIRST_NAME.getText());
			callStmt.setString(5, CCUSMIDDLE_NAME.getText());
			callStmt.setString(6, CCUSNATIONALITY.getValue());
			callStmt.setString(7, CCUSSEX.getValue());
			callStmt.setString(8, CCUSPLACE_BIRTH.getText());
			callStmt.setString(9, ICUSOTD.getValue());
			if (CombCountryAddr.getValue() != null) {
				callStmt.setInt(10, CombCountryAddr.getValue().getCODE());
			} else {
				callStmt.setNull(10, Types.INTEGER);
			}
			if (CombCountry.getValue() != null) {
				callStmt.setInt(11, CombCountry.getValue().getCODE());
			} else {
				callStmt.setNull(11, Types.INTEGER);
			}
			callStmt.setString(12, AREA.getValue());
			callStmt.setString(13, null);
			callStmt.setString(14, PUNCT_NAME.getValue());
			callStmt.setString(15, INFR_NAME.getText());
			callStmt.setString(16, DOM.getText());
			callStmt.setString(17, KORP.getText());
			callStmt.setString(18, KV.getText());
			callStmt.registerOutParameter(19, Types.INTEGER);
			callStmt.execute();
			String ret = callStmt.getString(1);
			Integer retid = callStmt.getInt(19);
			if (ret.equals("ok")) {
				conn.commit();
				setStatus(true);
				setId(retid);
				// ���������
				{
					Save1c(retid, edit);
				}
				callStmt.close();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
				Msg.ErrorView(stage_, "ADD_CUS", conn);
				callStmt.close();
			}
			callStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ��� ������� ������ ���������
	 * 
	 * @param event
	 */
	@FXML
	void Save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Statement sqlStatement = conn.createStatement();
			String readRecordSQL = "select count(*) from NATIONALITY where name = '" + CCUSNATIONALITY.getValue() + "'";
			ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
			/**
			 * �������� ������������� ��������������
			 */
			if (CCUSNATIONALITY.getValue() != null) {
				if (rs.next()) {
					if (rs.getInt(1) == 0) {
						AddNationalityIfNotExist();
					} else {
						CallSave("N");
					}
				}
			} else {
				Msg.Message("�������� ��������������");
			}
			rs.close();
			sqlStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������������� ���� ��.��.����
	 */
	DateTimeFormatter DTFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

	/**
	 * �������������� ��������
	 * 
	 * @param TC
	 */
	void DateFormatCol(TableColumn<CUS_DOCUM, LocalDate> TC) {
		TC.setCellFactory(column -> {
			TableCell<CUS_DOCUM, LocalDate> cell = new TableCell<CUS_DOCUM, LocalDate>() {
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

	/**
	 * ��� �����
	 */
	private void CombCountry(ComboBox<COUNTRIES> cmb) {
		cmb.setConverter(new StringConverter<COUNTRIES>() {
			@Override
			public String toString(COUNTRIES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public COUNTRIES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	/**
	 * �������������� DatePiker
	 * 
	 * @param DP
	 */
	void DateFormatPiker(DatePicker DP) {
		DP.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	@FXML
	private AnchorPane AP;

	@FXML
	private ToolBar DUBLIC_TOOL;

	/**
	 * ������� �������� �� ��������������
	 */
	void OpenDocument() {
		try {
			if (DUBL.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ��������!");
			} else {
				CUS cus = DUBL.getSelectionModel().getSelectedItem();
				CusList doc = new CusList();
				//doc.setConn(ConnToDublOpenEdit, "AddEdit");
				doc.Edit(cus.getICUSNUM(), (Stage) ((txtfld != null) ? txtfld.getScene().getWindow()
						: CCUSLAST_NAME.getScene().getWindow())/*, ConnToDublOpenEdit*/);
			}
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	Connection ConnToDublOpenEdit;

	/**
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void setConn(Connection conn) throws SQLException {
		this.ConnToDublOpenEdit = conn;
		this.ConnToDublOpenEdit.setAutoCommit(false);
	}

	private TextField txtfld;

	public void setStage(TextField txtfld) throws SQLException {
		this.txtfld = txtfld;
	}

	@FXML
	private TabPane CusTab;

	/**
	 * ��� ������ ������ ���
	 * 
	 * @param event
	 */
	void FIO_LOST_FOCUS(TextField txt, TextField txt2, DatePicker dt) {
		if (dt != null) {
			if (!txt.getText().equals("") & txt.getLength() >= 2) {
				dt.setDisable(false);
				dt.requestFocus();
			}
		} else {
			if (!txt.getText().equals("") & txt.getLength() >= 2) {
				txt2.setDisable(false);
				txt2.requestFocus();
			}
		}
	}

	/**
	 * ��� ������ ������ ���
	 * 
	 * @param event
	 */
	void focusedProperty(TextField txt, TextField txt2, DatePicker dt) {
		txt.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) { // focus lost
				FIO_LOST_FOCUS(txt, txt2, dt);
			}
		});
	}

	/**
	 * ��� ����� "����� ��������"
	 * 
	 * @param event
	 */
	@FXML
	void EnterPlaceBirth(ActionEvent event) {
		ICUSOTD.requestFocus();
	}

	/**
	 * ��� ��������� �������������
	 */
	@FXML
	private void ICUSOTD() {
		try {
			String readRecordSQL = "select * from OTD t, RAION g where t.area_id = g.code and COTDNAME = ? ";
			PreparedStatement stmt = conn.prepareStatement(readRecordSQL);
			stmt.setString(1, ICUSOTD.getValue());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				AREA.getSelectionModel().select(rs.getString("NAME"));
			}
			stmt.close();
			rs.close();
			CombCountry.requestFocus();
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
		}
	}

	@FXML
	private VBox OsnVbox;

	/**
	 * ��� ���������������
	 */
	private Executor exec;

	@FXML
	private ButtonBar MainTool;

	/**
	 * ������� � ������� �� ��������������
	 */
	@FXML
	private void SaveEdit() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Statement sqlStatement = conn.createStatement();
			String readRecordSQL = "select count(*) from NATIONALITY where name = '" + CCUSNATIONALITY.getValue() + "'";
			ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
			/**
			 * �������� ������������� ��������������
			 */
			if (CCUSNATIONALITY.getValue() != null) {
				if (rs.next()) {
					if (rs.getInt(1) == 0) {
						AddNationalityIfNotExist();
					} else {
						CallSave("Y");
					}
				}
			} else {
				Msg.Message("�������� ��������������");
			}
			rs.close();
			sqlStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * �������������
	 */
	@FXML
	private void initialize() {
		try {
			
			dbConnect();

			/**
			 * ������� ������ �� ������
			 */
			CUS_DOCUM.setRowFactory(tv -> {
				TableRow<CUS_DOCUM> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						try {
							Stage stage = new Stage();
							Stage stage_ = (Stage) CombCountryAddr.getScene().getWindow();
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("/mj/doc/cus/IUCus_Doc.fxml"));

							CUS_DOCUM cd = CUS_DOCUM.getSelectionModel().getSelectedItem();
							Edit_Cus_Doc controller = new Edit_Cus_Doc();
							loader.setController(controller);
							controller.setConn(conn, true, cd);
							controller.setId(cd.getID_DOC());

							Parent root = loader.load();
							stage.setScene(new Scene(root));
							stage.getIcons().add(new Image("/icon.png"));
							stage.setTitle("������������� ���������� ������");
							stage.initOwner(stage_);
							stage.setResizable(false);
							stage.initModality(Modality.WINDOW_MODAL);
							stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
								@Override
								public void handle(WindowEvent paramT) {
									// ������� ������� ��� ��������
									InitCusDocum();
								}
							});
							stage.show();
						} catch (Exception e) {
							Main.logger = Logger.getLogger(getClass());
							e.printStackTrace();
							Msg.Message(ExceptionUtils.getStackTrace(e));
							Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
							String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
							String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
							int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
							DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
						}
					}
				});
				return row;
			});

			Docs.heightProperty().addListener(
					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));
			Address.heightProperty().addListener(
					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));
			Citizen.heightProperty().addListener(
					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));
			OSN_DATA.heightProperty().addListener(
					(observable, oldValue, newValue) -> ScrollPaneCus.vvalueProperty().set(newValue.doubleValue()));

			// Button SaveClose = new Button("���������");
			// MainTool.getItems().add(SaveClose);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					CCUSLAST_NAME.requestFocus();
				}
			});
			/**
			 * ��� ����� ���� ����������� �������������
			 */
			{
				//new ConvConst().DateAutoComma(DCUSBIRTHDAY);
				// DCUSBIRTHDAY.getEditor().replaceText(DCUSBIRTHDAY.getEditor().getSelection(),
				// "");
			}
			/**
			 * ��� �������� ���������������
			 */
			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});

			// ��� ����� ������ ���
			{
				focusedProperty(CCUSLAST_NAME, CCUSFIRST_NAME, null);
				focusedProperty(CCUSFIRST_NAME, CCUSMIDDLE_NAME, null);
				focusedProperty(CCUSMIDDLE_NAME, CCUSFIRST_NAME, DCUSBIRTHDAY);
			}
			// ��� ���������� ������� ����� "���������", ��� �� �����
			DocTab.getTabPane().getTabs().remove(DocTab);

			// ������� ������ �� ������� ����������
			DUBL.setRowFactory(tv -> {
				TableRow<CUS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						// ������� �������� �� �������������� � ������� ������� ����� ����������
						OpenDocument();
						onclose();
					}
				});
				return row;
			});
			// add
			// AP.getChildren().remove(DublicateBorder);
			// DublicateBorder.getChildren().remove(DUBL);
			// DublicateBorder.getChildren().remove(DUBLIC_TOOL);

			LastName.setCellValueFactory(cellData -> cellData.getValue().CCUSLAST_NAMEProperty());
			FirstName.setCellValueFactory(cellData -> cellData.getValue().CCUSFIRST_NAMEProperty());
			MiddleName.setCellValueFactory(cellData -> cellData.getValue().CCUSMIDDLE_NAMEProperty());
			BirthDate.setCellValueFactory(cellData -> ((CUS) cellData.getValue()).DCUSBIRTHDAYProperty());

			//TableColumnLocalDate(BirthDate);
			new ConvConst().TableColumnDate(BirthDate);

			DocTab.setDisable(true);
			DocTab.setText("");

			// ����������� �� ������
			setDisable();

			/**
			 * ���������� ��� �� ��������� �������� - "��"-�, "��"-�
			 * 
			 * @param event
			 */
			CCUSMIDDLE_NAME.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					if (newPropertyValue) {
					} else {
						if (!CCUSMIDDLE_NAME.getText().equals("")) {
							String sex = CCUSMIDDLE_NAME.getText().substring(CCUSMIDDLE_NAME.getText().length() - 2,
									CCUSMIDDLE_NAME.getText().length());
							if (sex.toLowerCase().equals("��")) {
								// CCUSSEX.getItems().addAll("�������", "�������");
								for (String ss : CCUSSEX.getItems()) {
									if (ss.equals("�������")) {
										CCUSSEX.getSelectionModel().select(ss);
										break;
									}
								}
							} else if (sex.toLowerCase().equals("��")) {
								// CCUSSEX.getItems().addAll("�������", "�������");
								for (String ss : CCUSSEX.getItems()) {
									if (ss.equals("�������")) {
										CCUSSEX.getSelectionModel().select(ss);
										break;
									}
								}
							}
						}
					}
				}
			});

			/**
			 * ���������� ������� �� ��������� � ������ �����������
			 */
			{
				CRUDCitizen(1, "�������", "Y", "{ ? = call MJCUS.ADD_CUS_CITIZEN_TEMP(?,?,?)}", "add");
			}
			/**
			 * ������ ����� ���������
			 */
			{
				new ConvConst().FirstWUpp(CCUSLAST_NAME);
				new ConvConst().FirstWUpp(CCUSFIRST_NAME);
				new ConvConst().FirstWUpp(CCUSMIDDLE_NAME);
				new ConvConst().FirstWUpp(CCUSPLACE_BIRTH);
				new ConvConst().FirstWUpp(INFR_NAME);
				// UpperCase(DOC_SUBDIV_T);
				// UpperCase(DOC_AGENCY_T);

			}
			/**
			 * ������������� ��������
			 */
			ID_DOC_TP.setCellValueFactory(cellData -> cellData.getValue().ID_DOC_TPProperty());
			DOC_SER.setCellValueFactory(cellData -> cellData.getValue().DOC_SERProperty());
			DOC_NUM.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMProperty());
			DOC_DATE.setCellValueFactory(cellData -> cellData.getValue().DOC_DATEProperty());
			DOC_PERIOD.setCellValueFactory(cellData -> cellData.getValue().DOC_PERIODProperty());
			PREF.setCellValueFactory(cellData -> cellData.getValue().PREFProperty());
			DOC_AGENCY.setCellValueFactory(cellData -> cellData.getValue().DOC_AGENCYProperty());
			DOC_SUBDIV.setCellValueFactory(cellData -> cellData.getValue().DOC_SUBDIVProperty());
			/**
			 * �������������� ���
			 */
			DateFormatCol(DOC_DATE);
			DateFormatCol(DOC_PERIOD);
			// DateFormatPiker(DOC_PERIOD_T);
			// DateFormatPiker(DOC_DATE_T);
			// DateFormatPiker(DOC_PERIOD_T);
			// DateFormatPiker(DOC_DATE_T);
			// DateFormatPiker(DCUSBIRTHDAY);
			/**
			 * ������������� �������� �������-�����������
			 */
			// COUNTRY.setCellValueFactory(cellData ->
			// cellData.getValue().COUNTRY_CODEProperty());
			CLONGNAME.setCellValueFactory(cellData -> cellData.getValue().COUNTRY_NAMEProperty());
			// ==== SINGLE? (CHECH BOX) ===
			OSN.setCellValueFactory(new Callback<CellDataFeatures<CUS_CITIZEN, Boolean>, ObservableValue<Boolean>>() {

				@Override
				public ObservableValue<Boolean> call(CellDataFeatures<CUS_CITIZEN, Boolean> param) {
					CUS_CITIZEN person = param.getValue();

					SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.getOSN());

					// Note: singleCol.setOnEditCommit(): Not work for
					// CheckBoxTableCell.

					// When "Single?" column change.
					booleanProp.addListener(new ChangeListener<Boolean>() {

						@Override
						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
								Boolean newValue) {
							person.setOSN(newValue);
							CUS_CITIZEN.getSelectionModel().select(person);
							CRUDCitizen(person.getCOUNTRY_CODE(), person.getCOUNTRY_NAME(), (newValue) ? "Y" : "N",
									"{ ? = call MJCUS.EDIT_CUS_CITIZEN_TEMP(?,?,?,?)}", "edit");
						}
					});
					return booleanProp;
				}
			});

			OSN.setCellFactory(new Callback<TableColumn<CUS_CITIZEN, Boolean>, //
					TableCell<CUS_CITIZEN, Boolean>>() {
				@Override
				public TableCell<CUS_CITIZEN, Boolean> call(TableColumn<CUS_CITIZEN, Boolean> p) {
					CheckBoxTableCell<CUS_CITIZEN, Boolean> cell = new CheckBoxTableCell<CUS_CITIZEN, Boolean>();
					cell.setAlignment(Pos.CENTER);
					return cell;
				}
			});

			// ������ ��������
			{

				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from COUNTRIES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<COUNTRIES> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					COUNTRIES countryes = new COUNTRIES();
					countryes.setCODE(rs.getInt("CODE"));
					countryes.setNAME(rs.getString("NAME"));
					nationals.add(countryes);
				}
				FilteredList<COUNTRIES> filterednationals = new FilteredList<COUNTRIES>(nationals);
				CombCountry.getEditor().textProperty()
						.addListener(new InputFilter<COUNTRIES>(CombCountry, filterednationals, false));
				CombCountry.setItems(filterednationals);
				rs.close();
				sqlStatement.close();
				CombCountry(CombCountry);

				CombCountry.getSelectionModel().select(0);
			}

			// ������
			{

				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from COUNTRIES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<COUNTRIES> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					COUNTRIES countryes = new COUNTRIES();
					countryes.setCODE(rs.getInt("CODE"));
					countryes.setNAME(rs.getString("NAME"));
					nationals.add(countryes);
				}
				FilteredList<COUNTRIES> filterednationals = new FilteredList<COUNTRIES>(nationals);
				CombCountryAddr.getEditor().textProperty()
						.addListener(new InputFilter<COUNTRIES>(CombCountryAddr, filterednationals, false));
				CombCountryAddr.setItems(filterednationals);
				rs.close();
				sqlStatement.close();
				CombCountry(CombCountryAddr);

				CombCountryAddr.getSelectionModel().select(0);
			}

			/**
			 * ��������������
			 */
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select name from NATIONALITY t order by ID\n";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					nationals.add(rs.getString(1));
				}
				FilteredList<String> filterednationals = new FilteredList<String>(nationals);
				CCUSNATIONALITY.getEditor().textProperty()
						.addListener(new InputFilter<String>(CCUSNATIONALITY, filterednationals, false));
				CCUSNATIONALITY.setItems(filterednationals);
				rs.close();
				sqlStatement.close();
			}

			/**
			 * ������
			 */
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from RAION";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					areas.add(rs.getString(2));
				}
				AREA.setItems(areas);
				rs.close();
				sqlStatement.close();
			}
			/**
			 * ���
			 */
			CCUSSEX.getItems().addAll("�������", "�������");

			/**
			 * ���������
			 */
			{

				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from OTD t order by IOTDNUM\n";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<String> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					OTD tr = new OTD();
					tr.setCOTDNAME(rs.getString("COTDNAME"));
					combolist.add(rs.getString("COTDNAME"));
				}
				ICUSOTD.setItems(combolist);
				rs.close();
				sqlStatement.close();
			}
			/**
			 * �������� � �����������
			 */
			/*
			 * CUS_DOCUM.getSelectionModel().selectedItemProperty().addListener((obs,
			 * oldSelection, newSelection) -> { if (newSelection != null) { CUS_DOCUM cd =
			 * CUS_DOCUM.getSelectionModel().getSelectedItem(); try { Statement sqlStatement
			 * = conn.createStatement(); String readRecordSQL = "select * from VPUD";
			 * ResultSet rs = sqlStatement.executeQuery(readRecordSQL); ObservableList<VPUD>
			 * combolist = FXCollections.observableArrayList(); while (rs.next()) { VPUD pud
			 * = new VPUD(); pud.setCPUDDOC(rs.getString("name"));
			 * pud.setIPUDID(rs.getInt("code")); combolist.add(pud); }
			 * ID_DOC_TP_T.setItems(combolist); convertComboDisplayList(); if
			 * (cd.getID_DOC_TP() != null) { for (VPUD ld : ID_DOC_TP_T.getItems()) { if
			 * (cd.getID_DOC_TP().equals(ld.getCPUDDOC())) {
			 * ID_DOC_TP_T.getSelectionModel().select(ld); break; } } } rs.close(); } catch
			 * (Exception e) { Msg.Message(ExceptionUtils.getStackTrace(e)); Main.logger.error(ExceptionUtils.getStackTrace(e)
			 * + "~" + Thread.currentThread().getName()); String fullClassName =
			 * Thread.currentThread().getStackTrace()[2].getClassName(); String methodName =
			 * Thread.currentThread().getStackTrace()[2].getMethodName(); int lineNumber =
			 * Thread.currentThread().getStackTrace()[2].getLineNumber();
			 * DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName); }
			 * DOC_SER_T.setText(cd.getDOC_SER()); DOC_NUM_T.setText(cd.getDOC_NUM());
			 * DOC_DATE_T.setValue(cd.getDOC_DATE());
			 * DOC_PERIOD_T.setValue(cd.getDOC_PERIOD()); if (cd.getPREF().equals("��"))
			 * PREF_T.setSelected(true); else PREF_T.setSelected(false);
			 * DOC_AGENCY_T.setText(cd.getDOC_AGENCY());
			 * DOC_SUBDIV_T.setText(cd.getDOC_SUBDIV()); } });
			 */
			new ConvConst().FormatDatePiker(DOC_DATE_T);
			new ConvConst().FormatDatePiker(DCUSBIRTHDAY);
			new ConvConst().FormatDatePiker(DOC_PERIOD_T);
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}

	}

	/**
	 * ������� �����������
	 * 
	 * @param event
	 */
	@FXML
	void DelCitizen(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (CUS_CITIZEN.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������� ������ �� �������!");
			} else {
				CUS_CITIZEN cd = CUS_CITIZEN.getSelectionModel().getSelectedItem();
				PreparedStatement delete = conn.prepareStatement("declare " + "pragma autonomous_transaction; begin "
						+ "delete from CUS_CITIZEN_TEMP where id = ?;commit;" + "end;");
				delete.setInt(1, cd.getID());
				delete.executeUpdate();
				delete.close();
				InitCitizen();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ���� ����������
	 * 
	 * @param table
	 */
	public void autoResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column) -> {
			if (column.getText().equals("sess_id")) {
			} else {
				Text t = new Text(column.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					if (column.getCellData(i) != null) {
						t = new Text(column.getCellData(i).toString());
						double calcwidth = t.getLayoutBounds().getWidth();
						if (calcwidth > max) {
							max = calcwidth;
						}
					}
				}
				column.setPrefWidth(max + 10.0d);
			}
		});
	}

	/**
	 * ������� ������ Cus.fxml
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddCus");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * ��������� ������ Cus.fxml
	 */
	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	private BooleanProperty Status;
	private IntegerProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	Connection ForConn;

	public void setConn2(Connection conn) throws SQLException {
		this.ForConn = conn;
		this.ForConn.setAutoCommit(false);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Integer value) {
		this.Id.set(value);
	}

	public Integer getId() {
		return this.Id.get();
	}

	public AddCus() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}

	/**
	 * ������ ����� ���������
	 * 
	 * @param value
	 * @return
	 */
	public static String upperCaseAllFirst(String value) {
		char[] array = value.toCharArray();
		// Uppercase first letter.
		array[0] = Character.toUpperCase(array[0]);
		// Uppercase all letters that follow a whitespace character.
		for (int i = 1; i < array.length; i++) {
			if (Character.isWhitespace(array[i - 1])) {
				array[i] = Character.toUpperCase(array[i]);
			}
		}
		return new String(array);
	}


	// ����� ��-�� ������������, ��������

	@FXML
	void BRN_BIRTH_ACT(ActionEvent event) {

	}

	@FXML
	void PATERN_CERT(ActionEvent event) {

	}

	@FXML
	void MC_MERCER(ActionEvent event) {

	}

	@FXML
	void DIVORCE_CERT(ActionEvent event) {

	}

	@FXML
	void DEATH_CERT(ActionEvent event) {

	}

	@FXML
	void UPD_NAME(ActionEvent event) {

	}

	@FXML
	void UPDATE_ABH_NAME(ActionEvent event) {

	}

	@FXML
	void UPD_NAT(ActionEvent event) {

	}

	@FXML
	void ADOPT(ActionEvent event) {

	}

	@FXML
	void OpenBTN(ActionEvent event) {

	}
}
