package ru.psv.mj.prjmngm.projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class RootPmPrjController {
	/**
	 * Конструктор
	 */
	public RootPmPrjController() {
		Main.logger = Logger.getLogger(getClass());
	}

	// <TableView>
	@FXML
	private TableView<VPM_DOCS> PM_DOCS;
	// </TableView>
	// <TableColumn>
	@FXML
	private TableColumn<VPM_DOCS, Long> DOC_ID;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_NUMBER;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_TP_NAME;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_COMMENT;
	@FXML
	private TableColumn<Object, LocalDate> DOC_DATE;
	@FXML
	private TableColumn<Object, LocalDate> DOC_END;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_ISFAST;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_USR;
	@FXML
	private TableColumn<VPM_DOCS, String> ORG_NAME;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_NAME;
	@FXML
	private TableColumn<Object, LocalDateTime> DOC_START;
	// </TableColumn>

	/**
	 * Добавить
	 * 
	 * @param event
	 */
	@FXML
	private void Add(ActionEvent event) {
		try {

			// права
			if (DbUtil.Odb_Action(Long.valueOf(245)) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/inboxdocs/IUPmDoc.fxml"));

			AddPmPrjC controller = new AddPmPrjC();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить: ");
			stage.initOwner((Stage) PM_DOCS.getScene().getWindow());
			stage.setResizable(true);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					try {
						if (controller.getStatus()) {
							controller.dbDisconnect();

							LoadTable();

							System.out.println(controller.getRetId());
							for (VPM_DOCS doc : PM_DOCS.getItems()) {
								System.out.println("loop:" + doc.getDOC_ID());
								if (doc.getDOC_ID().equals(controller.getRetId())) {
									System.out.println("Y");
									Platform.runLater(() -> {
										PM_DOCS.requestFocus();
										PM_DOCS.getSelectionModel().select(doc);
										PM_DOCS.scrollTo(doc);
									});
									break;
								}
							}
							Platform.runLater(() -> {
								Edit(null);
							});

						} else {

						}
					} catch (Exception e) {
						DbUtil.Log_Error(e);
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить
	 * 
	 * @param event
	 */
	@FXML
	private void Delete(ActionEvent event) {
		try {
			// права
			if (DbUtil.Odb_Action(Long.valueOf(244)) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (PM_DOCS.getSelectionModel().getSelectedItem() != null) {
				VPM_DOCS sel = PM_DOCS.getSelectionModel().getSelectedItem();

				final Alert alert = new Alert(AlertType.CONFIRMATION, "Удалить " + sel.getDOC_ID() + "?",
						ButtonType.YES, ButtonType.NO);
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
					PreparedStatement prp = conn.prepareStatement("delete from PM_DOCS where DOC_ID = ?");
					prp.setLong(1, sel.getDOC_ID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					LoadTable();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
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
//	class Project extends MutableActivityBase<VPM_PROJECTS> {
//		public Project(VPM_PROJECTS data) {
//			setUserObject(data);
//			setName(data.getEMP_LASTNAME());
//			setStartTime(data.getDOC_DATE().atStartOfDay(ZoneId.systemDefault()).toInstant());
//			setEndTime(data.getDOC_END().atStartOfDay(ZoneId.systemDefault()).toInstant());
//		}
//	}

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

	/**
	 * Создать проект, привязать к сотруднику
	 * 
	 * @param event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	@FXML
	void CrePrj(ActionEvent event) {
		try {
			// Stage = (Stage) PM_DOCS.getScene().getWindow();
			Stage stage = new Stage();
			// Create the Gantt chart
			GanttChart<Employees> gantt = new GanttChart<Employees>(new Employees("Сотрудники"));

			Layer layer = new Layer("Projects");
			gantt.getLayers().add(layer);

			ObservableList<Employees> obslist = FXCollections.observableArrayList();
			{
				PreparedStatement prp1 = null;
				ResultSet rs1 = null;
				PreparedStatement prp = conn.prepareStatement(
						"select distinct PRJ_EMP,EMP_LASTNAME,EMP_FIRSTNAME,EMP_MIDDLENAME from VPM_PROJECTS");
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					Employees psv = new Employees(rs.getString("EMP_LASTNAME") + " " + rs.getString("EMP_FIRSTNAME")
							+ " " + rs.getString("EMP_MIDDLENAME"));
					// ____________________________
					{
						prp1 = conn.prepareStatement("select * from VPM_PROJECTS where PRJ_EMP = ?");
						prp1.setLong(1, rs.getLong("PRJ_EMP"));
						rs1 = prp1.executeQuery();
						while (rs1.next()) {
							psv.addActivity(layer, new Project(new ProjectData(rs1.getString("EMP_LASTNAME"),
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
			graphics.showEarliestActivities();

			BorderPane borderPane = new BorderPane();
			borderPane.setTop(new GanttChartToolBar(gantt));
			borderPane.setCenter(gantt);
			borderPane.setBottom(new GanttChartStatusBar(gantt));

			Scene scene = new Scene(borderPane);

			stage.setScene(scene);
			stage.sizeToScene();
			stage.centerOnScreen();
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Диаграмма Ганта");
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Редактировать
	 * 
	 * @param event
	 */
	@FXML
	private void Edit(ActionEvent event) {
		try {
			// права
			if (DbUtil.Odb_Action(Long.valueOf(243)) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			// выбранная запись
			VPM_DOCS sel = PM_DOCS.getSelectionModel().getSelectedItem();
			if (sel != null) {
				// удержать
				PreparedStatement selforupd = conn
						.prepareStatement("SELECT * FROM PM_DOCS WHERE DOC_ID = ? FOR UPDATE NOWAIT");
				selforupd.setLong(1, sel.getDOC_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					// add lock row
					String lock = DbUtil.Lock_Row(sel.getDOC_ID(), "PM_DOCS", conn);
					if (lock != null) {// if error add row
						Msg.Message(lock);
						conn.rollback();
						return;
					}
					// <FXML>---------------------------------------
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/inboxdocs/IUPmDoc.fxml"));
					EditPmPrjC controller = new EditPmPrjC();
					controller.SetClass(sel, conn);

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Редактирование: " + sel.getDOC_ID());
					stage.initOwner((Stage) PM_DOCS.getScene().getWindow());
					stage.setResizable(true);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							try {
								if (controller.getStatus()) {
									conn.commit();
									// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
									String lock = DbUtil.Lock_Row_Delete(sel.getDOC_ID(), "PM_DOCS", conn);
									if (lock != null) {// if error add row
										Msg.Message(lock);
									}
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
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(sel.getDOC_ID(), "PM_DOCS"));
					} else {
						DbUtil.Log_Error(e);
					}
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
			new ConvConst().TableColumnDate(DOC_DATE);
			new ConvConst().TableColumnDate(DOC_END);
			new ConvConst().TableColumnDateTime(DOC_START);
			// init table column
			DOC_ID.setCellValueFactory(cellData -> cellData.getValue().DOC_IDProperty().asObject());
			DOC_COMMENT.setCellValueFactory(cellData -> cellData.getValue().DOC_COMMENTProperty());
			DOC_ISFAST.setCellValueFactory(cellData -> cellData.getValue().DOC_ISFASTProperty());
			DOC_DATE.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).DOC_DATEProperty());
			DOC_TP_NAME.setCellValueFactory(cellData -> cellData.getValue().DOC_TP_NAMEProperty());
			DOC_END.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).DOC_ENDProperty());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			DOC_USR.setCellValueFactory(cellData -> cellData.getValue().DOC_USRProperty());
			DOC_START.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).TM$DOC_STARTProperty());
			ORG_NAME.setCellValueFactory(cellData -> cellData.getValue().ORG_NAMEProperty());
			DOC_NAME.setCellValueFactory(cellData -> cellData.getValue().DOC_NAMEProperty());
			dbConnect();
			// load table
			LoadTable();
			/**
			 * Двойной щелчок по строке
			 */
			PM_DOCS.setRowFactory(tv -> {
				TableRow<VPM_DOCS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PM_DOCS.getSelectionModel().getSelectedItem() != null) {
							Edit(null);
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
			String selectStmt = "select * from VPM_DOCS t";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_DOCS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_DOCS list = new VPM_DOCS();

				list.setORG_NAME(rs.getString("ORG_NAME"));
				list.setDOC_ID(rs.getLong("DOC_ID"));
				list.setDOC_COMMENT(rs.getString("DOC_COMMENT"));
				list.setDOC_ISFAST(rs.getString("DOC_ISFAST"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_NAME(rs.getString("DOC_NAME"));
				list.setDOC_REF(rs.getLong("DOC_REF"));
				list.setDOC_TP_ID(rs.getLong("DOC_TP_ID"));
				list.setDOC_TP_NAME(rs.getString("DOC_TP_NAME"));
				list.setORG_ID(rs.getLong("ORG_ID"));
				list.setDOC_END((rs.getDate("DOC_END") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_END")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setTM$DOC_START((rs.getDate("TM$DOC_START") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_START")),
						DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
				list.setDOC_USR(rs.getString("DOC_USR"));

				obslist.add(list);
			}
			// add data
			PM_DOCS.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_DOCS> tableFilter = TableFilter.forTableView(PM_DOCS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(PM_DOCS);
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
			if (column_.getText().equals("Сист.данные")) {
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
