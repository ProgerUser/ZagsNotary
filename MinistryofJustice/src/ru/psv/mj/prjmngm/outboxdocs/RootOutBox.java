package ru.psv.mj.prjmngm.outboxdocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.ActivityBase;
import com.flexganttfx.model.activity.MutableCompletableActivityBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.projects.model.VPM_PROJECTS;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class RootOutBox {

	@FXML
	private TableView<VPM_PROJECTS> prj_tbl;
	@FXML
	private TableColumn<VPM_PROJECTS, Long> doc_id;
	@FXML
	private TableColumn<Object, LocalDate> doc_date;
	@FXML
	private TableColumn<Object, LocalDate> doc_end;
	@FXML
	private TableColumn<VPM_PROJECTS, String> doc_name;
	@FXML
	private TableColumn<VPM_PROJECTS, String> doc_comment;
	@FXML
	private TableColumn<VPM_PROJECTS, String> doc_tp_name;
	@FXML
	private TableColumn<VPM_PROJECTS, String> org_name;
	@FXML
	private TableColumn<VPM_PROJECTS, String> org_ruk;
	@FXML
	private TableColumn<VPM_PROJECTS, String> emp_lastname;
	@FXML
	private TableColumn<VPM_PROJECTS, String> emp_firstname;
	@FXML
	private TableColumn<VPM_PROJECTS, String> emp_middlename;
	@FXML
	private TableColumn<VPM_PROJECTS, String> emp_position;
	@FXML
	private TableColumn<VPM_PROJECTS, String> prj_creusr;
	@FXML
	private TableColumn<Object, LocalDateTime> tm$prj_startdate;
	@FXML
	private TableColumn<VPM_PROJECTS, String> prj_status_char;

	/**
	 * Конструктор
	 */
	public RootOutBox() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private VBox EmpPrjVbox;

	@FXML
	private BorderPane GantBorder;

	@SuppressWarnings("unused")
	private Long docid;

	public void SetClass(Long docid) {
		this.docid = docid;
	}

	void OnClose() {
		Stage stage = (Stage) GantBorder.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Initialize table
	 */
	void LoadTable() {
		try {
			// loop
			String selectStmt = "SELECT * FROM VPM_PROJECTS PRJ WHERE PRJ_STATUS = 4 ORDER BY PRJ_ID DESC";
			// System.out.println(selectStmt);
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_PROJECTS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_PROJECTS list = new VPM_PROJECTS();

				// System.out.println(rs.getString("PRJ_EMP_LOGIN"));

				list.setPRJ_EMP_LOGIN(rs.getString("PRJ_EMP_LOGIN"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
				list.setEMP_TEL(rs.getString("EMP_TEL"));
				list.setPRJ_STATUS(rs.getLong("PRJ_STATUS"));
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setDOC_REF(rs.getLong("DOC_REF"));
				list.setPRJ_STATUS_CHAR(rs.getString("PRJ_STATUS_CHAR"));
				list.setEMP_WORKEND((rs.getDate("EMP_WORKEND") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKEND")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setEMP_ID(rs.getLong("EMP_ID"));
				list.setEMP_POSITION(rs.getString("EMP_POSITION"));
				list.setDOC_COMMENT(rs.getString("DOC_COMMENT"));
				list.setEMP_WORKSTART((rs.getDate("EMP_WORKSTART") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKSTART")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setTM$DOC_START((rs.getDate("TM$DOC_START") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_START")),
						DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
				list.setORG_ID(rs.getLong("ORG_ID"));
				list.setORG_RUK(rs.getString("ORG_RUK"));
				list.setPRJ_ID(rs.getLong("PRJ_ID"));
				list.setEMP_LOGIN(rs.getLong("EMP_LOGIN"));
				list.setPRJ_CREUSR(rs.getString("PRJ_CREUSR"));
				list.setDOC_TP_NAME(rs.getString("DOC_TP_NAME"));
				list.setEMP_MIDDLENAME(rs.getString("EMP_MIDDLENAME"));
				list.setDOC_ISFAST(rs.getString("DOC_ISFAST"));
				list.setPRJ_DOCID(rs.getLong("PRJ_DOCID"));
				list.setEMP_FIRSTNAME(rs.getString("EMP_FIRSTNAME"));
				list.setEMP_BOSS(rs.getLong("EMP_BOSS"));
				list.setEMP_JBTYPE(rs.getLong("EMP_JBTYPE"));
				list.setDOC_ID(rs.getLong("DOC_ID"));
				list.setTM$PRJ_STARTDATE((rs.getDate("TM$PRJ_STARTDATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$PRJ_STARTDATE")),
						DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
				list.setPRJ_EMP(rs.getLong("PRJ_EMP"));
				list.setDOC_USR(rs.getString("DOC_USR"));
				list.setDOC_TP_ID(rs.getLong("DOC_TP_ID"));
				list.setORG_NAME(rs.getString("ORG_NAME"));
				list.setEMP_LASTNAME(rs.getString("EMP_LASTNAME"));
				list.setDOC_END((rs.getDate("DOC_END") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_END")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_NAME(rs.getString("DOC_NAME"));

				obslist.add(list);
			}
			// add data
			prj_tbl.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_PROJECTS> tableFilter = TableFilter.forTableView(prj_tbl).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			// ResizeColumns(prj_tbl);
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
				System.out.println(column_.getText());
				System.out.println(column_.getColumns().size());

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

	class Data {
		Instant start;
		Instant end;
		String name;
		String comment;
		String docnumber;
		String isfast;
		String docname;
		Long empid;
		Long dtdiff;
	}

	Layer layer = new Layer("Activities");
	int shift = 1;

	class ActivityRow extends Row<ActivityRow, ActivityRow, ActivityBase<Data>> {
		Data data;
		int linksIn;
		int linksOut;

		protected MutableCompletableActivityBase<Data> act;

		public ActivityRow(String name, Long empid) {
			data = new Data();
			data.name = name;
			data.empid = empid;
			setExpanded(true);
			if (data != null && name != null) {
				setName(data.name);
			}
		}

		public ActivityRow(String name, ActivityRow parent, Instant start, Instant end, String comment,
				String docnumber, String isfast, String docname, Long dtdiff) {
			data = new Data();
			data.start = start;
			data.end = end;
			data.name = name;
			data.comment = comment;
			data.docnumber = docnumber;
			data.isfast = isfast;
			data.docname = docname;
			data.dtdiff = dtdiff;
			setExpanded(true);
			if (data != null && name != null) {
				createActivity(data);
				setName(data.name);
			}
		}

		public int getLinksIn() {
			return linksIn;
		}

		public void setLinksIn(int linksIn) {
			this.linksIn = linksIn;
		}

		public int getLinksOut() {
			return linksOut;
		}

		public void setLinksOut(int linksOut) {
			this.linksOut = linksOut;
		}

		protected void createActivity(Data data) {
			act = new MutableCompletableActivityBase<>(data.name, data.start, data.end);
			act.setUserObject(data);
			addActivity(layer, act);
		}
	}

	/**
	 * Привязка столбцов к данным
	 */
	void InitTabCol() {
		try {
			doc_date.setCellValueFactory(cellData -> ((VPM_PROJECTS) cellData.getValue()).DOC_DATEProperty());
			prj_status_char.setCellValueFactory(cellData -> cellData.getValue().PRJ_STATUS_CHARProperty());
			emp_position.setCellValueFactory(cellData -> cellData.getValue().EMP_POSITIONProperty());
			doc_comment.setCellValueFactory(cellData -> cellData.getValue().DOC_COMMENTProperty());
			emp_middlename.setCellValueFactory(cellData -> cellData.getValue().EMP_MIDDLENAMEProperty());
			emp_firstname.setCellValueFactory(cellData -> cellData.getValue().EMP_FIRSTNAMEProperty());
			doc_id.setCellValueFactory(cellData -> cellData.getValue().DOC_IDProperty().asObject());
			tm$prj_startdate
					.setCellValueFactory(cellData -> ((VPM_PROJECTS) cellData.getValue()).TM$PRJ_STARTDATEProperty());
			org_name.setCellValueFactory(cellData -> cellData.getValue().ORG_NAMEProperty());
			emp_lastname.setCellValueFactory(cellData -> cellData.getValue().EMP_LASTNAMEProperty());
			doc_end.setCellValueFactory(cellData -> ((VPM_PROJECTS) cellData.getValue()).DOC_ENDProperty());
			doc_name.setCellValueFactory(cellData -> cellData.getValue().DOC_NAMEProperty());
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
			new ConvConst().TableColumnDate(doc_date);
			new ConvConst().TableColumnDate(doc_end);
			new ConvConst().TableColumnDateTime(tm$prj_startdate);
			// ________________________________
			dbConnect();
			InitTabCol();
			LoadTable();
			// CreGant();
			/**
			 * Двойной щелчок по строке
			 */
			prj_tbl.setRowFactory(tv -> {
				TableRow<VPM_PROJECTS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (prj_tbl.getSelectionModel().getSelectedItem() != null) {
							EditPrj(null);
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
	 * Проверка возможности редактирования
	 * 
	 * @param sel
	 * @return
	 * @throws SQLException
	 */
	boolean IsEditable(VPM_PROJECTS sel) throws SQLException {
		boolean ret = false;
		CallableStatement cl = conn.prepareCall("{ ? = call PM_DOC.PRJ_IS_EDITABLE(?,?,?)}");
		cl.registerOutParameter(1, Types.INTEGER);
		cl.setLong(2, sel.getPRJ_ID());
		cl.setLong(3, sel.getEMP_ID());
		cl.setLong(4, sel.getPRJ_STATUS());
		cl.execute();
		if (cl.getInt(1) == 1) {
			ret = true;
		}
		cl.close();
		return ret;
	}

	/**
	 * Редактировать проект/Документ
	 * 
	 * @param event
	 */
	@FXML
	void EditPrj(ActionEvent event) {
		try {
			VPM_PROJECTS sel = prj_tbl.getSelectionModel().getSelectedItem();
			if (sel != null) {
				// System.out.println( sel.getPRJ_EMP_LOGIN());
				// System.out.println( sel.getPRJ_STATUS());
				if (IsEditable(sel)) {

					// удержать
					PreparedStatement selforupd = conn
							.prepareStatement("select * from VPM_PROJECTS where PRJ_ID = ? FOR UPDATE NOWAIT");
					selforupd.setLong(1, sel.getPRJ_ID());
					try {
						selforupd.executeQuery();
						selforupd.close();
						// add lock row
						String lock = DbUtil.Lock_Row(sel.getDOC_ID(), "VPM_PROJECTS", conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// <FXML>---------------------------------------
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/outboxdocs/OpenOutBox.fxml"));
						EditOutBoxDoc controller = new EditOutBoxDoc();
						controller.SetClass(sel, conn);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: " + sel.getDOC_ID());
						stage.initOwner((Stage) prj_tbl.getScene().getWindow());
						stage.setResizable(true);
						stage.initModality(Modality.WINDOW_MODAL);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									if (controller.getStatus()) {
										conn.commit();
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(sel.getDOC_ID(), "VPM_PROJECTS", conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
										// Таблица
										LoadTable();
									}
								} catch (Exception e) {
									DbUtil.Log_Error(e);
								}
							}
						});
						stage.show();
						// </FXML>---------------------------------------
					} catch (SQLException e) {
						if (e.getErrorCode() == 54) {
							Msg.Message(
									"Запись редактируется " + DbUtil.Lock_Row_View(sel.getDOC_ID(), "VPM_PROJECTS"));
						} else {
							DbUtil.Log_Error(e);
						}
					}
				} else {
					Msg.Message("Нет прав на редактирование!");
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	// <ORACLE_CONNECT>
	/**
	 * Строка соединения
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
	 * Закрыть
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
				conn.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	// </ORACLE_CONNECT>

}
