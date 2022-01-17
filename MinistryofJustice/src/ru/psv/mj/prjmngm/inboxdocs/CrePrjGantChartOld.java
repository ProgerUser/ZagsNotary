package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.log4j.Logger;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.table.TableFilter;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
import com.flexganttfx.model.ActivityRef;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.MutableActivityBase;
import com.flexganttfx.model.layout.GanttLayout;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.ListViewGraphics;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import com.flexganttfx.view.graphics.renderer.RowRenderer;
import com.flexganttfx.view.timeline.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.inboxdocs.model.VPM_PROJECTS;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import static org.controlsfx.control.PopOver.ArrowLocation.TOP_CENTER;

public class CrePrjGantChartOld {

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
	public CrePrjGantChartOld() {
		Main.logger = Logger.getLogger(getClass());
	}

	/**
	 * Обычный объект данных, хранящий фиктивную информацию о проектах.
	 * 
	 * @author saidp
	 *
	 */
	class ProjectData {

		String PrjName;
		String Fio;
		Integer CountPrj;
		Instant start;
		Instant end;

		public ProjectData(String PrjName, String Fio, Instant start, Instant end) {
			this.PrjName = PrjName;
			this.Fio = Fio;
			this.start = start;
			this.end = end;
		}
	}

	public class EmployeesRowRenderer extends RowRenderer<Employees> {

		public EmployeesRowRenderer(GraphicsBase<?> graphics) {
			super(graphics, "Aircraft Row Renderer");
		}

		protected void drawRow(Employees row, GraphicsContext gc, double w, double h, boolean selected, boolean hover,
				boolean highlighted, boolean pressed) {
			gc.setFill(Color.ORANGE);
			gc.fillRect(0, 0, w, h);
		}
	}

	/**
	 * Деятельность, представляющая полет. Этот объект будет отображаться как полоса
	 * в графическом представлении диаграммы Ганта. Полет изменчив, поэтому
	 * пользователь сможет с ним взаимодействовать.
	 * 
	 * @author saidp
	 *
	 */
	class Project extends MutableActivityBase<ProjectData> {
		public Project(ProjectData data) {
			setUserObject(data);
			setName(data.Fio);
			setStartTime(data.start);
			setEndTime(data.end);
		}
	}

	/**
	 * Каждая строка представляет собой самолет в этом примере. Мероприятия,
	 * показанные на строка относится к типу Project.
	 * 
	 * @author saidp
	 *
	 */
	class Employees extends Row<Employees, Employees, Project> {
		public Employees(String name) {
			super(name);
		}
	}

	@FXML
	private VBox EmpPrjVbox;

	@FXML
	private BorderPane GantBorder;

	GanttChart<Employees> gantt = null;

	/**
	 * Отмена
	 * 
	 * @param event
	 */
	@FXML
	void Cancel(ActionEvent event) {
		try {
			OnClose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private Long docid;

	public void SetClass(Long docid) {
		this.docid = docid;
	}

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@SuppressWarnings("null")
	@FXML
	void Ok(ActionEvent event) {
		try {
			String sel = gantt.getTreeTable().getSelectionModel().getSelectedItem().getValue().getName();
			System.out.println(sel);
			if (sel.contains("ФИО=\"")) {
				String empid = sel.substring(sel.indexOf("ID="), sel.length());
				empid = empid.replace("ID=\"", "").replace("\";", "");
				System.out.println(empid);
				CallableStatement callStmt = conn.prepareCall("{ call PM_DOC.ADD_PRJ(?,?,?)}");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				// Ссылка на документ
				if (docid != null) {
					callStmt.setLong(2, docid);
				} else {
					callStmt.setNull(2, java.sql.Types.INTEGER);
				}
				// Ссылка на сотрудника
				if (empid != null || !empid.trim().isEmpty()) {
					callStmt.setLong(3, Integer.valueOf(empid));
				} else {
					callStmt.setNull(3, java.sql.Types.INTEGER);
				}
				// выполнение
				callStmt.execute();
				if (callStmt.getString(1) == null) {
					conn.commit();
					callStmt.close();
					OnClose();
				} else {
					conn.rollback();
					Msg.Message(callStmt.getString(1));
					callStmt.close();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
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
			String selectStmt = "select * from VPM_PROJECTS t";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_PROJECTS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_PROJECTS list = new VPM_PROJECTS();

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
			ResizeColumns(prj_tbl);
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

	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void CreGant() {
		try {
			Employees emps = new Employees("Сотрудники");
			emps.setExpanded(true);
			gantt = new GanttChart<Employees>(emps);

			Layer layer = new Layer("Projects");
			gantt.getLayers().add(layer);

			ObservableList<Employees> obslist = FXCollections.observableArrayList();
			{
				PreparedStatement prp1 = null;
				ResultSet rs1 = null;
				PreparedStatement prp = conn.prepareStatement("select * from PM_EMP");
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					Employees psv = new Employees(
							"ФИО=\"" + (rs.getString("EMP_LASTNAME") + " " + rs.getString("EMP_FIRSTNAME") + " "
									+ rs.getString("EMP_MIDDLENAME")) + "\";ID=\"" + rs.getLong("EMP_ID") + "\";");
					// ____________________________
					{
						prp1 = conn.prepareStatement("select * from VPM_PROJECTS where PRJ_EMP = ?");
						prp1.setLong(1, rs.getLong("EMP_ID"));
						rs1 = prp1.executeQuery();
						while (rs1.next()) {
							Employees pr = new Employees("Наз.пр.=\"" + rs1.getString("DOC_NAME") + "\";" + "Срочн.=\""
									+ rs1.getString("doc_isfast") + "\";");
							psv.getChildren().add(pr);
							pr.addActivity(layer, new Project(new ProjectData(rs1.getString("EMP_LASTNAME"),
									rs1.getString("EMP_LASTNAME") + " " + rs.getString("EMP_FIRSTNAME") + " "
											+ rs1.getString("EMP_MIDDLENAME"),
									LocalDate
											.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs1.getDate("DOC_DATE")),
													DateTimeFormatter.ofPattern("dd.MM.yyyy"))
											.atStartOfDay(ZoneId.systemDefault()).toInstant(),
									LocalDate
											.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs1.getDate("DOC_END")),
													DateTimeFormatter.ofPattern("dd.MM.yyyy"))
											.atStartOfDay(ZoneId.systemDefault()).toInstant())));

						}
					}
					// ____________________________
					obslist.add(psv);
					psv.setExpanded(true);
				}
				//
				prp1.close();
				rs1.close();
				//
				prp.close();
				rs.close();
			}

			gantt.getRoot().getChildren().setAll(obslist);

			Timeline timeline = gantt.getTimeline();
			timeline.showTemporalUnit(ChronoUnit.DAYS, 10);

			GraphicsBase<Employees> graphics = gantt.getGraphics();
			graphics.setActivityRenderer(Project.class, GanttLayout.class,
					new ActivityBarRenderer<>(graphics, "Project Renderer"));

			graphics.showAllActivities();

			GantBorder.setTop(new GanttChartToolBar(gantt));
			GantBorder.setCenter(gantt);
			GantBorder.setBottom(new GanttChartStatusBar(gantt));

			ListViewGraphics<Employees> gr = gantt.getGraphics();
			gr.getListView().addEventHandler(MouseEvent.MOUSE_MOVED, evt -> mouseMoved(evt));

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private PopOver popOver;

	private void mouseMoved(MouseEvent evt) {
		ActivityRef<?> ref = gantt.getGraphics().getActivityRefAt(evt.getX(), evt.getY());
		if (ref != null) {
			if (popOver == null || popOver.isDetached()) {
				popOver = new PopOver();
				popOver.setArrowLocation(TOP_CENTER);
				popOver.getContentNode().setMouseTransparent(true);
			}

			double x = evt.getScreenX();
			double y = evt.getScreenY();

			if (!popOver.isShowing()) {
				popOver.setTitle(ref.getActivity().getName());
				popOver.show(gantt.getGraphics(), x, y, Duration.ONE);
			}
		} else {
			if (popOver != null && !popOver.isDetached()) {
				popOver.hide();
			}
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
			org_ruk.setCellValueFactory(cellData -> cellData.getValue().ORG_RUKProperty());
			prj_creusr.setCellValueFactory(cellData -> cellData.getValue().PRJ_CREUSRProperty());
			doc_tp_name.setCellValueFactory(cellData -> cellData.getValue().DOC_TP_NAMEProperty());
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
			CreGant();
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
