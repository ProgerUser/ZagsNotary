package ru.psv.mj.prjmngm.projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.emps.VPM_EMP;
import ru.psv.mj.prjmngm.projects.model.VPM_PROJECTS;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class SelEmpPrj {
	/**
	 * Конструктор
	 */
	public SelEmpPrj() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

	private BooleanProperty Status;

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	// <TableView>
	@FXML
	private TableView<VPM_EMP> PM_EMP;
	// </TableView>

	// <TableColumn>
	@FXML
	private TableColumn<VPM_EMP, Long> EMP_ID;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_LASTNAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_FIRSTNAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_MIDDLENAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_POSITION;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_TEL;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_EMAIL;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_LOGIN;
	@FXML
	private TableColumn<Object, LocalDate> EMP_WORKSTART;
	@FXML
	private TableColumn<Object, LocalDate> EMP_WORKEND;
	// </TableColumn>

	void OnClose() {
		Stage stage = (Stage) PM_EMP.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private VPM_EMP class_emp_;

	public VPM_EMP getEmp() {
		return this.class_emp_;
	}

	/**
	 * Отмена
	 * 
	 * @param event
	 */
	@FXML
	void Cancel(ActionEvent event) {
		try {
			setStatus(false);
			OnClose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			VPM_EMP sel = PM_EMP.getSelectionModel().getSelectedItem();
			if (sel != null) {
				final Alert alert = new Alert(AlertType.CONFIRMATION, "Выбрать сотрудника " + sel.getEMP_ID() + "?",
						ButtonType.YES, ButtonType.NO);
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
					PreparedStatement prp = conn.prepareStatement(
							"DECLARE\r\n"
							+ "  STATUS_ NUMBER := ?;\r\n"
							+ "  PRJID_  NUMBER := ?;\r\n"
							+ "  EMP_    NUMBER := ?;\r\n"
							+ "BEGIN\r\n"
							+ "  INSERT INTO PM_PRJ_STAT_HIST\r\n"
							+ "    (STH_PRJ, STH_STAT, STH_EMP)\r\n"
							+ "  VALUES\r\n"
							+ "    (PRJID_, STATUS_, EMP_);\r\n"
							+ "  \r\n"
							+ "  UPDATE PM_PROJECTS SET PRJ_EMP = EMP_ WHERE PRJ_ID = PRJID_;\r\n"
							+ "END;");
					prp.setLong(1, class_.getPRJ_STATUS());
					prp.setLong(2, class_.getPRJ_ID());
					prp.setLong(3, sel.getEMP_ID());
					
					prp.executeUpdate();
					prp.close();
					conn.commit();
					class_emp_ = sel;
					setStatus(true);
					OnClose();	
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Обновить
	 * 
	 * @param event
	 */
	@FXML
	private void Reshresh(ActionEvent event) {
		try {
			LoadTable();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			new ConvConst().TableColumnDate(EMP_WORKEND);
			new ConvConst().TableColumnDate(EMP_WORKSTART);
			// init table column
			EMP_EMAIL.setCellValueFactory(cellData -> cellData.getValue().EMP_EMAILProperty());
			EMP_ID.setCellValueFactory(cellData -> cellData.getValue().EMP_IDProperty().asObject());
			EMP_TEL.setCellValueFactory(cellData -> cellData.getValue().EMP_TELProperty());
			EMP_LOGIN.setCellValueFactory(cellData -> cellData.getValue().EMP_LOGINProperty());
			EMP_POSITION.setCellValueFactory(cellData -> cellData.getValue().EMP_POSITIONProperty());
			EMP_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().EMP_FIRSTNAMEProperty());
			EMP_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().EMP_LASTNAMEProperty());
			EMP_MIDDLENAME.setCellValueFactory(cellData -> cellData.getValue().EMP_MIDDLENAMEProperty());
			EMP_WORKSTART.setCellValueFactory(cellData -> ((VPM_EMP) cellData.getValue()).EMP_WORKSTARTProperty());
			EMP_WORKEND.setCellValueFactory(cellData -> ((VPM_EMP) cellData.getValue()).EMP_WORKENDProperty());
			// connect
			//dbConnect();
			// load table
			LoadTable();
			/**
			 * Двойной щелчок по строке
			 */
			PM_EMP.setRowFactory(tv -> {
				TableRow<VPM_EMP> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PM_EMP.getSelectionModel().getSelectedItem() != null) {

						}
					}
				});
				return row;
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Initialize table
	 */
	void LoadTable() {
		try {
			// loop
			String selectStmt = "select * from vpm_emp t where EMP_ID <> ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, class_.getEMP_ID());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_EMP> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_EMP list = new VPM_EMP();
				list.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
				list.setEMP_JBTYPE(rs.getLong("EMP_JBTYPE"));
				list.setEMP_ID(rs.getLong("EMP_ID"));
				list.setEMP_WORKSTART((rs.getDate("EMP_WORKSTART") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKSTART")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setEMP_JB_YPE2(rs.getLong("EMP_JB_YPE2"));
				list.setEMP_TEL(rs.getString("EMP_TEL"));
				list.setEMP_LOGIN(rs.getString("EMP_LOGIN"));
				list.setEMP_LOGIN_L(rs.getLong("EMP_LOGIN_L"));
				list.setEMP_POSITION(rs.getString("EMP_POSITION"));
				list.setEMP_FIRSTNAME(rs.getString("EMP_FIRSTNAME"));
				list.setEMP_BOSS(rs.getLong("EMP_BOSS"));
				list.setEMP_LASTNAME(rs.getString("EMP_LASTNAME"));
				list.setEMP_MIDDLENAME(rs.getString("EMP_MIDDLENAME"));
				list.setEMP_WORKEND((rs.getDate("EMP_WORKEND") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKEND")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);

				obslist.add(list);
			}
			// add data
			PM_EMP.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_EMP> tableFilter = TableFilter.forTableView(PM_EMP).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(PM_EMP);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Авто расширение
	 * 
	 * @param table
	 */
	void ResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column_) -> {
			if (column_.getText().equals("")) {
			} else {
				Text t = new Text(column_.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					if (column_.getCellData(i) != null) {
						if (column_.getCellData(i) != null) {
							t = new Text(column_.getCellData(i).toString());
							double calcwidth = t.getLayoutBounds().getWidth();
							if (calcwidth > max) {
								max = calcwidth;
							}
						}
					}
				}
				column_.setPrefWidth(max + 20.0d);
			}
		});
	}

	// <ORACLE_CONNECT>
	/**
	 * Строка соединения
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
//	private void dbConnect() {
//		try {
//			conn = DbUtil.GetConnect(getClass().getName());
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}

	VPM_PROJECTS class_;

	public void SetClass(VPM_PROJECTS class_, Connection conn) {
		this.class_ = class_;
		this.conn = conn;
	}
	
//	/**
//	 * Закрыть
//	 */
//	public void dbDisconnect() {
//		try {
//			if (conn != null && !conn.isClosed()) {
//				conn.rollback();
//				conn.close();
//			}
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}
	// </ORACLE_CONNECT>
}
