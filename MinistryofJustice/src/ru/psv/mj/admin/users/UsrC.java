package ru.psv.mj.admin.users;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.access.grp.ODB_GROUP_USR;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.KeyBoard;

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
	private TableView<ODB_GROUP_USR> USR_GRP;
	@FXML
	private TableColumn<ODB_GROUP_USR, Long> GRP_ID;
	@FXML
	private TableColumn<ODB_GROUP_USR, String> GRP_NAME;
	@FXML
	private TextField FIO_SH;
	@FXML
	private TextField FIO_ABH;
	@FXML
	private TextField FIO_ABH_SH;
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
	private XTableView<USR> USRLST;
	@FXML
	private XTableColumn<USR, String> LOGNAME;
	@FXML
	private XTableColumn<USR, String> CUSRNAMEC;
	@FXML
	private ComboBox<NOTARY> NOTARY;
	@FXML
	private ComboBox<ZAGS> ZAGS;
	@FXML
	private XTableColumn<USR, Long> USRID;
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
	void AddGrp(ActionEvent event) {
		try {
			if (USRLST.getSelectionModel().getSelectedItem() != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) IUSRPWD_LENGTH.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/ru/psv/mj/admin/users/AddGrp.fxml"));

				AddGrp controller = new AddGrp();
				controller.SetUsr(USRLST.getSelectionModel().getSelectedItem().getCUSRLOGNAME());
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("����� ������");
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						InitGrp();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
		}
	}

	@FXML
	void DeleteGrp(ActionEvent event) {
		try {
			if (USR_GRP.getSelectionModel().getSelectedItem() != null
					& USRLST.getSelectionModel().getSelectedItem() != null) {
				
				final Alert alert = new Alert(AlertType.CONFIRMATION, "������� ������ "
						+ USR_GRP.getSelectionModel().getSelectedItem().getGRP_NAME() + " ?",
						ButtonType.YES, ButtonType.NO);
				((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
						.add(new Image("/icon.png"));
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
						.orElse(ButtonType.NO) == ButtonType.YES) {
					ODB_GROUP_USR grp_act = USR_GRP.getSelectionModel().getSelectedItem();
					USR usr = USRLST.getSelectionModel().getSelectedItem();
					PreparedStatement prp = conn
							.prepareStatement(
									"declare\n" + 
					                "  usr_id number;\n" + 
									"  pragma autonomous_transaction;\n"
									+ "begin\n" + 
									"  select usr.iusrid into usr_id from usr where usr.cusrlogname = ?;\n"
									+ "  delete from  ODB_GRP_MEMBER where GRP_ID = ? and IUSRID = usr_id;\n"
									+ "  commit;\n" + 
									"end;\n");
					prp.setString(1, usr.getCUSRLOGNAME());
					prp.setLong(2, grp_act.getGRP_ID());
					prp.executeUpdate();
					prp.close();
					// ��������
					InitGrp();
				}
				
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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
	private void OpenKey() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) IUSRPWD_LENGTH.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/widgets/KeyBoard.fxml"));

			KeyBoard controller = new KeyBoard();
			loader.setController(controller);
			controller.setTextField(IUSRPWD_LENGTH.getScene());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("��������� ����������");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {

				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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

			if (DbUtil.Odb_Action(3l) == 0) {
				Msg.Message("��� �������!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) USRLST.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/admin/users/AddUser.fxml"));

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
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Set_UP_Pass(ActionEvent event) {
		try {

			if (USRLST.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������������!");
			} else {

				if (DbUtil.Odb_Action(61l) == 0) {
					Msg.Message("��� �������!");
					return;
				}

				Stage stage = new Stage();
				Stage stage_ = (Stage) USRLST.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/ru/psv/mj/admin/users/Set_Up_Pass.fxml"));

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
			DbUtil.Log_Error(e);
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
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��������� ������
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void UpdateUser(ActionEvent event) {
		try {

			if (DbUtil.Odb_Action(6l) == 0) {
				Msg.Message("��� �������!");
				return;
			}

			if (USRLST.getSelectionModel().getSelectedItem() == null) {
				// Msg.Message("�������� ������������!");
			} else {
				CallableStatement callStmt = conn
						.prepareCall("{ ? = call MJUsers.UpdateUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callStmt.registerOutParameter(1, Types.VARCHAR);

				if (IUSRBRANCH.getSelectionModel().getSelectedItem() != null) {
					callStmt.setLong(2, IUSRBRANCH.getValue().getIOTDNUM());
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
					callStmt.setLong(6, ZAGS.getValue().getZAGS_ID());
				} else {
					callStmt.setNull(6, Types.INTEGER);
				}

				callStmt.setLong(7, Long.valueOf(IUSRPWD_LENGTH.getText()));
				callStmt.setLong(8, Long.valueOf(IUSRCHR_QUANTITY.getText()));
				callStmt.setLong(9, Long.valueOf(IUSRNUM_QUANTITY.getText()));
				callStmt.setLong(10, Long.valueOf(IUSRSPEC_QUANTITY.getText()));
				callStmt.setString(11, (MUST_CHANGE_PASSWORD.isSelected()) ? "Y" : "N");
				USR usr = USRLST.getSelectionModel().getSelectedItem();
				callStmt.setLong(12, usr.getIUSRID());
				callStmt.setString(13, CUSRNAME.getText());

				if (NOTARY.getValue() != null) {
					callStmt.setLong(14, NOTARY.getValue().getNOT_ID());
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

				callStmt.setString(16, FIO_SH.getText());
				callStmt.setString(17, FIO_ABH_SH.getText());
				callStmt.setString(18, FIO_ABH.getText());

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
			DbUtil.Log_Error(e);
		}
	}

	String where = " where DUSRFIRE is null";

	void INIT() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String selectStmt = "select * from usr " + where + " order by IUSRID desc";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<USR> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				USR list = new USR();
				list.setIUSRID(rs.getLong("IUSRID"));
				list.setCUSRLOGNAME(rs.getString("CUSRLOGNAME"));
				list.setCUSRNAME(rs.getString("CUSRNAME"));
				list.setCUSRPOSITION(rs.getString("CUSRPOSITION"));
				list.setDUSRHIRE((rs.getDate("DUSRHIRE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DUSRHIRE")), formatter)
						: null);
				list.setIUSRBRANCH(rs.getLong("IUSRBRANCH"));
				list.setDUSRFIRE((rs.getDate("DUSRFIRE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DUSRFIRE")), formatter)
						: null);
				list.setIUSRPWD_LENGTH(rs.getLong("IUSRPWD_LENGTH"));
				list.setIUSRCHR_QUANTITY(rs.getLong("IUSRCHR_QUANTITY"));
				list.setIUSRNUM_QUANTITY(rs.getLong("IUSRNUM_QUANTITY"));
				list.setIUSREXP_DAYS(rs.getLong("IUSREXP_DAYS"));
				list.setCUSROFFPHONE(rs.getString("CUSROFFPHONE"));
				list.setTWRTSTART((rs.getDate("TWRTSTART") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("TWRTSTART")), formatter)
						: null);
				list.setTWRTEND((rs.getDate("TWRTEND") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("TWRTEND")), formatter)
						: null);
				list.setCEMAIL(rs.getString("CEMAIL"));
				list.setCRESTRICT_TERM(rs.getString("CRESTRICT_TERM"));
				list.setIUSRPWDREUSE(rs.getLong("IUSRPWDREUSE"));
				list.setIUSRSPEC_QUANTITY(rs.getLong("IUSRSPEC_QUANTITY"));
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
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setNOTARY_ID(rs.getLong("NOTARY_ID"));
				list.setACCESS_LEVEL(rs.getString("access_level"));
				list.setFIO_SH(rs.getString("FIO_SH"));
				list.setFIO_ABH_SH(rs.getString("FIO_ABH_SH"));
				list.setFIO_ABH(rs.getString("FIO_ABH"));
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
			DbUtil.Log_Error(e);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
		ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());

		ToggleGroup group = new ToggleGroup();
		zags_w.setToggleGroup(group);
		notary_w.setToggleGroup(group);
		all_w.setToggleGroup(group);

		dbConnect();
		// DbUtil.Run_Process(conn,getClass().getName());
		ZagsCombo();
		NotaryCombo();
		convertComboDisplayList();
		INIT();

		USRID.setCellValueFactory(cellData -> cellData.getValue().IUSRIDProperty().asObject());
		LOGNAME.setCellValueFactory(cellData -> cellData.getValue().CUSRLOGNAMEProperty());
		CUSRNAMEC.setCellValueFactory(cellData -> cellData.getValue().CUSRNAMEProperty());

		USRID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
				TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));
		LOGNAME.setColumnFilter(new PatternColumnFilter<>());
		CUSRNAMEC.setColumnFilter(new PatternColumnFilter<>());

		GRP_ID.setCellValueFactory(cellData -> cellData.getValue().GRP_IDProperty().asObject());
		GRP_NAME.setCellValueFactory(cellData -> cellData.getValue().GRP_NAMEProperty());

		/* Listener */
		USRLST.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				InitFields();
				InitGrp();
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
//						setStyle(
//								"-fx-background-color: rgb(162, 189, 48);-fx-border-color:black;-fx-border-width :  0.5 0.5 0.5 0.5 ");
						// setStyle("-fx-text-fill: rgb(162, 189, 48);");
					} else {
//						setStyle("-fx-background-color: #D24141;");
						setStyle("-fx-text-fill: #D24141;-fx-font-weight: bold;");
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
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	/**
	 * �������� ������� � ��������
	 */
	void InitGrp() {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("SELECT GRP_ID, GRP_NAME, NAME\r\n"
					+ "  FROM ODB_GROUP_USR J\r\n" + " WHERE EXISTS (SELECT NULL\r\n"
					+ "          FROM ODB_GRP_MEMBER G\r\n" + "         WHERE G.IUSRID =\r\n"
					+ "               (SELECT USR.IUSRID FROM USR WHERE USR.CUSRLOGNAME = ?)\r\n"
					+ "           AND J.GRP_ID = G.GRP_ID)\r\n" + "");
			prepStmt.setString(1, USRLST.getSelectionModel().getSelectedItem().getCUSRLOGNAME());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ODB_GROUP_USR> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				ODB_GROUP_USR list = new ODB_GROUP_USR();
				list.setGRP_ID(rs.getLong("GRP_ID"));
				list.setGRP_NAME(rs.getString("GRP_NAME"));
				list.setNAME(rs.getString("NAME"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			USR_GRP.setItems(dlist);

			TableFilter<ODB_GROUP_USR> tableFilter = TableFilter.forTableView(USR_GRP).apply();
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
						list.setIOTDNUM(rs.getLong("IOTDNUM"));
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
						list.setZAGS_ID(rs.getLong("ZAGS_ID"));
						list.setZAGS_OTD(rs.getLong("ZAGS_OTD"));
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
						list.setNOT_ID(rs.getLong("NOT_ID"));
						list.setNOT_OTD(rs.getLong("NOT_OTD"));
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

				FIO_SH.setText(usr.getFIO_SH());
				FIO_ABH.setText(usr.getFIO_ABH());
				FIO_ABH_SH.setText(usr.getFIO_ABH_SH());

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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��� ���������
	 */
	private void convertComboDisplayList() {
		IUSRBRANCH.setConverter(new StringConverter<OTD>() {
			@Override
			public String toString(OTD object) {
				return object != null ? object.getCOTDNAME() : "";
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
			public String toString(ZAGS object) {
				return object != null ? object.getZAGS_NAME() : "";
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
			public String toString(NOTARY object) {
				return object != null ? object.getNOT_NAME() : "";
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
				if (DbUtil.Odb_Action(4l) == 0) {
					Msg.Message("��� �������!");
					return;
				}

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
							callStmt.setLong(2, val.getIUSRID());
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
								DbUtil.Log_Error(e1);
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
