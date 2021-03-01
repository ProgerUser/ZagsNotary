package mj.users;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;

/**
 * ����������������� �������������
 * 
 * @author Said
 *
 */

public class UsrC {

	public UsrC() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private RadioButton zags_w;

	@FXML
	private RadioButton notary_w;

	@FXML
	private RadioButton all_w;

	@FXML
	private Text LOG;

	@FXML
	private CheckBox ViewFire;

	@FXML
	private TextField IUSRNUM_QUANTITY;

	@FXML
	private Button refreshusrs;

	@FXML
	private TextField CUSRNAME;

	@FXML
	private DatePicker DUSRHIRE;

	@FXML
	private DatePicker DUSRFIRE;

	@FXML
	private Button CHUSERS;

	@FXML
	private TextField IUSRCHR_QUANTITY;

	@FXML
	private TextField IUSRSPEC_QUANTITY;

	@FXML
	private TableView<USR> USRLST;

	@FXML
	private TableColumn<USR, String> LOGNAME;

	@FXML
	private TableColumn<USR, String> CUSRNAMEC;

	@FXML
	private ComboBox<NOTARY> NOTARY;

	@FXML
	private ComboBox<ZAGS> ZAGS;

	@FXML
	private TableColumn<USR, Integer> USRID;

	@FXML
	private TextField CUSRPOSITION;

	@FXML
	private TextField IUSRPWD_LENGTH;

	@FXML
	private ComboBox<OTD> IUSRBRANCH;

	@FXML
	private CheckBox MUST_CHANGE_PASSWORD;

	@FXML
	private Button ADD_USR;

	@FXML
	void ClearZags(ActionEvent event) {
		try {
			ZAGS.getSelectionModel().select(null);
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
		}
	}

	@FXML
	void ClearNotary(ActionEvent event) {
		try {

			NOTARY.getSelectionModel().select(null);
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
		}
	}

	@FXML
	void ClearOtd(ActionEvent event) {
		try {
			IUSRBRANCH.getSelectionModel().select(null);
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
		}
	}

	void Add() {
		try {
			Main.logger = Logger.getLogger(getClass());

			if (DBUtil.OdbAction(3) == 0) {
				Msg.Message("��� �������!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) USRLST.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/users/AddUser.fxml"));

			AddUser controller = new AddUser();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("�������� ������������");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						INIT();
					}
					controller.dbDisconnect();
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

	@FXML
	void Set_UP_Pass(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());

			if (USRLST.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������������!");
			} else {

				if (DBUtil.OdbAction(61) == 0) {
					Msg.Message("��� �������!");
					return;
				}

				Stage stage = new Stage();
				Stage stage_ = (Stage) USRLST.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/mj/users/Set_Up_Pass.fxml"));

				Set_Up_Pass controller = new Set_Up_Pass();
				controller.setUsr(USRLST.getSelectionModel().getSelectedItem().getCUSRLOGNAME());
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("�������� ������");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						if (controller.getStatus()) {
							INIT();
							InitFields();
						}
						controller.dbDisconnect();
					}
				});
				stage.show();
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

	@FXML
	void AddUser(ActionEvent event) {
		Add();
	}

	/**
	 * ������
	 */
	private Connection conn;

	/**
	 * ������� ������
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "USR");
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
	 * ��������� ������
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

	@FXML
	void UpdateUser(ActionEvent event) {
		try {

			if (DBUtil.OdbAction(6) == 0) {
				Msg.Message("��� �������!");
				return;
			}

			if (USRLST.getSelectionModel().getSelectedItem() == null) {
				// Msg.Message("�������� ������������!");
			} else {
				CallableStatement callStmt = conn
						.prepareCall("{ ? = call MJUsers.UpdateUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callStmt.registerOutParameter(1, Types.VARCHAR);

				if (IUSRBRANCH.getSelectionModel().getSelectedItem() != null) {
					callStmt.setInt(2, IUSRBRANCH.getValue().getIOTDNUM());
				} else {
					callStmt.setNull(2, java.sql.Types.INTEGER);
				}

				callStmt.setString(3, CUSRPOSITION.getText());

				if (DUSRHIRE.getValue() == null) {
					callStmt.setDate(4, null);
				} else {
					callStmt.setDate(4, java.sql.Date.valueOf(DUSRHIRE.getValue()));
				}

				if (DUSRFIRE.getValue() == null) {
					callStmt.setDate(5, null);
				} else {
					callStmt.setDate(5, java.sql.Date.valueOf(DUSRFIRE.getValue()));
				}
				if (ZAGS.getValue() != null) {
					callStmt.setInt(6, ZAGS.getValue().getZAGS_ID());
				} else {
					callStmt.setNull(6, Types.INTEGER);
				}

				callStmt.setInt(7, Integer.valueOf(IUSRPWD_LENGTH.getText()));
				callStmt.setInt(8, Integer.valueOf(IUSRCHR_QUANTITY.getText()));
				callStmt.setInt(9, Integer.valueOf(IUSRNUM_QUANTITY.getText()));
				callStmt.setInt(10, Integer.valueOf(IUSRSPEC_QUANTITY.getText()));
				callStmt.setString(11, (MUST_CHANGE_PASSWORD.isSelected()) ? "Y" : "N");
				USR usr = USRLST.getSelectionModel().getSelectedItem();
				callStmt.setInt(12, usr.getIUSRID());
				callStmt.setString(13, CUSRNAME.getText());

				if (NOTARY.getValue() != null) {
					callStmt.setInt(14, NOTARY.getValue().getNOT_ID());
				} else {
					callStmt.setNull(14, Types.INTEGER);
				}

				if (zags_w.isSelected())
					callStmt.setString(15, "ZAG");
				else if (notary_w.isSelected())
					callStmt.setString(15, "NOT");
				else if (all_w.isSelected())
					callStmt.setString(15, "ADM");
				else
					callStmt.setString(15, null);

				callStmt.execute();
				String ret = callStmt.getString(1);
				callStmt.close();
				if (ret.equals("ok")) {
					conn.commit();
					INIT();
					LOG.setText("OK");
				} else {
					conn.rollback();
					Stage stage_ = (Stage) IUSRPWD_LENGTH.getScene().getWindow();
					Msg.ErrorView(stage_, "UpdateUser", conn);
				}
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

	String where = " where DUSRFIRE is null";

	void INIT() {
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String selectStmt = "select * from usr " + where + " order by CUSRLOGNAME";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<USR> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				USR list = new USR();
				list.setIUSRID(rs.getInt("IUSRID"));
				list.setCUSRLOGNAME(rs.getString("CUSRLOGNAME"));
				list.setCUSRNAME(rs.getString("CUSRNAME"));
				list.setCUSRPOSITION(rs.getString("CUSRPOSITION"));
				list.setDUSRHIRE((rs.getDate("DUSRHIRE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DUSRHIRE")), formatter)
						: null);
				list.setIUSRBRANCH(rs.getInt("IUSRBRANCH"));
				list.setDUSRFIRE((rs.getDate("DUSRFIRE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DUSRFIRE")), formatter)
						: null);
				list.setIUSRPWD_LENGTH(rs.getInt("IUSRPWD_LENGTH"));
				list.setIUSRCHR_QUANTITY(rs.getInt("IUSRCHR_QUANTITY"));
				list.setIUSRNUM_QUANTITY(rs.getInt("IUSRNUM_QUANTITY"));
				list.setIUSREXP_DAYS(rs.getInt("IUSREXP_DAYS"));
				list.setCUSROFFPHONE(rs.getString("CUSROFFPHONE"));
				list.setTWRTSTART((rs.getDate("TWRTSTART") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("TWRTSTART")), formatter)
						: null);
				list.setTWRTEND((rs.getDate("TWRTEND") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("TWRTEND")), formatter)
						: null);
				list.setCEMAIL(rs.getString("CEMAIL"));
				list.setCRESTRICT_TERM(rs.getString("CRESTRICT_TERM"));
				list.setIUSRPWDREUSE(rs.getInt("IUSRPWDREUSE"));
				list.setIUSRSPEC_QUANTITY(rs.getInt("IUSRSPEC_QUANTITY"));
				list.setWELCOME_MESSAGE(rs.getString("WELCOME_MESSAGE"));
				list.setSHORT_NAME(rs.getString("SHORT_NAME"));
				list.setLOCK_DATE_TIME((rs.getDate("LOCK_DATE_TIME") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("LOCK_DATE_TIME")), formatter) : null);
				list.setLOCK_INFO(rs.getString("LOCK_INFO"));
				list.setMUST_CHANGE_PASSWORD(rs.getString("MUST_CHANGE_PASSWORD"));
				list.setSHORT_POSITION(rs.getString("SHORT_POSITION"));
				list.setWORKDAY_TIME_END((rs.getDate("WORKDAY_TIME_END") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("WORKDAY_TIME_END")), formatter) : null);
				list.setWORKDAY_TIME_BEGIN((rs.getDate("WORKDAY_TIME_BEGIN") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("WORKDAY_TIME_BEGIN")), formatter) : null);
				list.setZAGS_ID(rs.getInt("ZAGS_ID"));
				list.setNOTARY_ID(rs.getInt("NOTARY_ID"));
				list.setACCESS_LEVEL(rs.getString("access_level"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			USRLST.setItems(dlist);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<USR> tableFilter = TableFilter.forTableView(USRLST).apply();
					tableFilter.setSearchStrategy((input, target) -> {
						try {
							return target.toLowerCase().contains(input.toLowerCase());
						} catch (Exception e) {
							return false;
						}
					});
				}
			});
			// autoResizeColumns(USRLST);
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

	public static void autoResizeColumns(TableView<?> table) {
		// Set the right policy
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column) -> {
			// System.out.println(column.getText());
			if (column.getText().equals("sess_id")) {

			} else {
				// Minimal width = columnheader
				Text t = new Text(column.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					// cell must not be empty
					if (column.getCellData(i) != null) {
						t = new Text(column.getCellData(i).toString());
						double calcwidth = t.getLayoutBounds().getWidth();
						// remember new max-width
						if (calcwidth > max) {
							max = calcwidth;
						}
					}
				}
				// set the new max-widht with some extra space
				column.setPrefWidth(max + 10.0d);
			}
		});
	}

	@FXML
	void Refresh(ActionEvent event) {
		INIT();
		InitFields();
	}

	@FXML
	void RefreshFromItem(ActionEvent event) {
		INIT();
		InitFields();
	}

	@FXML
	void AddFromItem(ActionEvent event) {
		Add();
	}

	@FXML
	private void initialize() {
		ToggleGroup group = new ToggleGroup();
		zags_w.setToggleGroup(group);
		notary_w.setToggleGroup(group);
		all_w.setToggleGroup(group);

		dbConnect();
		ZagsCombo();
		NotaryCombo();
		convertComboDisplayList();
		INIT();

		USRID.setCellValueFactory(cellData -> cellData.getValue().IUSRIDProperty().asObject());
		LOGNAME.setCellValueFactory(cellData -> cellData.getValue().CUSRLOGNAMEProperty());
		CUSRNAMEC.setCellValueFactory(cellData -> cellData.getValue().CUSRNAMEProperty());

		/* Listener */
		USRLST.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				InitFields();
				LOG.setText("");
			}
		});

		// F10 ��������� ���������
//		USRLST.setOnKeyReleased((KeyEvent t) -> {
//			KeyCode key = t.getCode();
//			if (key == KeyCode.F10) {
//				int pos = USRLST.getSelectionModel().getSelectedIndex();
//				System.out.println("INDEX:" + pos);
//			}
//		});

		LOGNAME.setCellFactory(col -> new TextFieldTableCell<USR, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
					setStyle("");
				} else {
					setText(item.toString());
					if (userfire(item.toString())) {
						setStyle(
								"-fx-background-color: rgb(162, 189, 48);-fx-border-color:black;-fx-border-width :  0.5 0.5 0.5 0.5 ");
					} else {
						setStyle("-fx-background-color: #D24141;");
					}
				}
			}
		});

		new ConvConst().FormatDatePiker(DUSRHIRE);
		new ConvConst().FormatDatePiker(DUSRFIRE);
	}

	boolean userfire(String user) {
		boolean ret = true;
		try {
			PreparedStatement prepStmt = conn
					.prepareStatement("select null from usr where CUSRLOGNAME = ? and DUSRFIRE is not null");
			prepStmt.setString(1, user);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				ret = false;
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	public void InitFields() {
		try {
			USR usr = USRLST.getSelectionModel().getSelectedItem();
			if (usr != null) {
				CUSRNAME.setText(usr.getCUSRNAME());
				// ���������
				{
					PreparedStatement stsmt = conn.prepareStatement("select * from otd");
					ResultSet rs = stsmt.executeQuery();
					ObservableList<OTD> combolist = FXCollections.observableArrayList();
					while (rs.next()) {
						OTD list = new OTD();
						list.setIOTDNUM(rs.getInt("IOTDNUM"));
						list.setCOTDNAME(rs.getString("COTDNAME"));
						combolist.add(list);
					}
					stsmt.close();
					rs.close();
					IUSRBRANCH.setItems(combolist);
					// System.out.println("IUSRBRANCH=" + usr.getIUSRBRANCH());
					for (OTD ld : IUSRBRANCH.getItems()) {
						if (usr.getIUSRBRANCH().equals(ld.getIOTDNUM())) {
							IUSRBRANCH.getSelectionModel().select(ld);
							break;
						}
					}
					rs.close();
				}
				// ����
				{
					PreparedStatement stsmt = conn.prepareStatement("select * from zags");
					ResultSet rs = stsmt.executeQuery();
					ObservableList<ZAGS> combolist = FXCollections.observableArrayList();
					while (rs.next()) {
						ZAGS list = new ZAGS();
						list.setZAGS_ID(rs.getInt("ZAGS_ID"));
						list.setZAGS_OTD(rs.getInt("ZAGS_OTD"));
						list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
						list.setZAGS_RUK(rs.getString("ZAGS_RUK"));
						combolist.add(list);
					}
					stsmt.close();
					rs.close();
					ZAGS.setItems(combolist);
					// System.out.println("ZAGS_ID=" + usr.getZAGS_ID());
					if (usr.getZAGS_ID() != null) {
						for (ZAGS ld : ZAGS.getItems()) {
							if (usr.getZAGS_ID().equals(ld.getZAGS_ID())) {
								ZAGS.getSelectionModel().select(ld);
								break;
							}
						}
					}
					rs.close();
				}
				// ��������
				{
					PreparedStatement stsmt = conn.prepareStatement("select * from notary");
					ResultSet rs = stsmt.executeQuery();
					ObservableList<NOTARY> combolist = FXCollections.observableArrayList();
					while (rs.next()) {
						NOTARY list = new NOTARY();
						list.setNOT_ID(rs.getInt("NOT_ID"));
						list.setNOT_OTD(rs.getInt("NOT_OTD"));
						list.setNOT_NAME(rs.getString("NOT_NAME"));
						list.setNOT_RUK(rs.getString("NOT_RUK"));
						combolist.add(list);
					}
					stsmt.close();
					rs.close();
					NOTARY.setItems(combolist);
					// System.out.println("ZAGS_ID=" + usr.getNOTARY_ID());
					if (usr.getNOTARY_ID() != null) {
						for (NOTARY ld : NOTARY.getItems()) {
							if (usr.getNOTARY_ID().equals(ld.getNOT_ID())) {
								NOTARY.getSelectionModel().select(ld);
								break;
							}
						}
					}
					rs.close();
				}
				DUSRHIRE.setValue(usr.getDUSRHIRE());
				DUSRFIRE.setValue(usr.getDUSRFIRE());
				CUSRPOSITION.setText(usr.getCUSRPOSITION());
				IUSRPWD_LENGTH.setText(String.valueOf(usr.getIUSRPWD_LENGTH().toString()));
				IUSRCHR_QUANTITY.setText(String.valueOf(usr.getIUSRCHR_QUANTITY().toString()));
				IUSRNUM_QUANTITY.setText(String.valueOf(usr.getIUSRNUM_QUANTITY()));
				IUSRSPEC_QUANTITY.setText(String.valueOf(usr.getIUSRSPEC_QUANTITY()));
				MUST_CHANGE_PASSWORD.setSelected((usr.getMUST_CHANGE_PASSWORD().equals("Y")) ? true : false);

				if (usr.getACCESS_LEVEL() != null) {
					if (usr.getACCESS_LEVEL().equals("ZAG"))
						zags_w.setSelected(true);
					else if (usr.getACCESS_LEVEL().equals("NOT"))
						notary_w.setSelected(true);
					else if (usr.getACCESS_LEVEL().equals("ADM"))
						all_w.setSelected(true);
				} else {
					zags_w.setSelected(false);
					notary_w.setSelected(false);
					all_w.setSelected(false);
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * ��� ���������
	 */
	private void convertComboDisplayList() {
		IUSRBRANCH.setConverter(new StringConverter<OTD>() {
			@Override
			public String toString(OTD product) {
				return product.getCOTDNAME();
			}

			@Override
			public OTD fromString(final String string) {
				return IUSRBRANCH.getItems().stream().filter(product -> product.getCOTDNAME().equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	/**
	 * ��� �����
	 */
	private void ZagsCombo() {
		ZAGS.setConverter(new StringConverter<ZAGS>() {
			@Override
			public String toString(ZAGS product) {
				return product.getZAGS_NAME();
			}

			@Override
			public ZAGS fromString(final String string) {
				return ZAGS.getItems().stream().filter(product -> product.getZAGS_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	/**
	 * ��� ���������
	 */
	private void NotaryCombo() {
		NOTARY.setConverter(new StringConverter<NOTARY>() {
			@Override
			public String toString(NOTARY product) {
				return product.getNOT_NAME();
			}

			@Override
			public NOTARY fromString(final String string) {
				return NOTARY.getItems().stream().filter(product -> product.getNOT_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	void DeleteUser(ActionEvent event) {
		try {
			if (USRLST.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������������!");
			} else {
				if (DBUtil.OdbAction(4) == 0) {
					Msg.Message("��� �������!");
					return;
				}
				Main.logger = Logger.getLogger(getClass());

				Stage stage = (Stage) USRLST.getScene().getWindow();
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
							USR val = USRLST.getSelectionModel().getSelectedItem();
							CallableStatement callStmt = conn.prepareCall("{ call MJUsers.DeleteUser(?,?)}");
							callStmt.registerOutParameter(1, Types.VARCHAR);
							callStmt.setInt(2, val.getIUSRID());
							callStmt.execute();
							if (callStmt.getString(1) == null) {
								Msg.Message("������������: " + val.getCUSRNAME() + " ������");
								INIT();
							} else {
								Msg.Message(callStmt.getString(1));
								Main.logger.error(callStmt.getString(1) + "~" + Thread.currentThread().getName());
							}
							callStmt.close();
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
				newWindow_yn.setTitle("��������");
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

	@FXML
	void ViewFire(ActionEvent event) {
		if (ViewFire.isSelected()) {
			where = "";
			INIT();
		} else {
			where = " where DUSRFIRE is null";
			INIT();
		}
	}
}
