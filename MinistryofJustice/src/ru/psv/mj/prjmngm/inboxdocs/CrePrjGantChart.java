package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.log4j.Logger;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.MutableActivityBase;
import com.flexganttfx.model.layout.GanttLayout;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import com.flexganttfx.view.timeline.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class CrePrjGantChart {

	/**
	 * Конструктор
	 */
	public CrePrjGantChart() {
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
				if (empid.equals("")) {
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
	 * Инициализация
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			// ___________________________
			// Stage = (Stage) PM_DOCS.getScene().getWindow();
//			Stage stage = new Stage();
			// Create the Gantt chart
			gantt = new GanttChart<Employees>(new Employees("Сотрудники"));

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

			// graphics.showEarliestActivities();

			graphics.showAllActivities();

			// graphics.setDisable(true);

			GantBorder.setTop(new GanttChartToolBar(gantt));
			GantBorder.setCenter(gantt);
			GantBorder.setBottom(new GanttChartStatusBar(gantt));
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
