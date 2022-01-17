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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
import com.flexganttfx.model.ActivityLink;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.ActivityBase;
import com.flexganttfx.model.activity.MutableCompletableActivityBase;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.graphics.ListViewGraphics;
import com.flexganttfx.view.graphics.renderer.StraightLinkRenderer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.prjmngm.inboxdocs.model.VPM_PROJECTS;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class CrePrjGantChart {

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
	public CrePrjGantChart() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private VBox EmpPrjVbox;

	@FXML
	private BorderPane GantBorder;

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
			ActivityRow treetable = table.getSelectionModel().getSelectedItem().getValue();

			if (treetable != null) {
				Long sel = treetable.data.empid;
				if (sel != null) {
					CallableStatement callStmt = conn.prepareCall("{ call PM_DOC.ADD_PRJ(?,?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					// Ссылка на документ
					if (docid != null) {
						callStmt.setLong(2, docid);
					} else {
						callStmt.setNull(2, java.sql.Types.INTEGER);
					}
					// Ссылка на сотрудника
					callStmt.setLong(3, sel);
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

	TreeTableView<ActivityRow> table;

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected GanttChartBase<?> createGanttChart() throws Exception {

		GanttChart<ActivityRow> gantt = new GanttChart();
		List<ActivityRow> roots = new ArrayList<>();

		// --------------------------------------------------------------
		{
			PreparedStatement prp1 = null;
			ResultSet rs1 = null;
			PreparedStatement prp = conn.prepareStatement("select * from PM_EMP");
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("EMP_LASTNAME"));
				final ActivityRow row = new ActivityRow(rs.getString("EMP_LASTNAME") + " "
						+ rs.getString("EMP_FIRSTNAME") + " " + rs.getString("EMP_MIDDLENAME"), rs.getLong("EMP_ID"));
				roots.add(row);
				// ____________________________
				{
					prp1 = conn.prepareStatement("select * from VPM_PROJECTS where PRJ_EMP = ? order by DOC_END desc");
					prp1.setLong(1, rs.getLong("EMP_ID"));
					rs1 = prp1.executeQuery();
					while (rs1.next()) {
						row.getChildren().add(new ActivityRow("", row,
								LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs1.getDate("DOC_DATE")),
										DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay(ZoneId.systemDefault())
										.toInstant(),
								LocalDate
										.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs1.getDate("DOC_END")),
												DateTimeFormatter.ofPattern("dd.MM.yyyy"))
										.atStartOfDay(ZoneId.systemDefault()).toInstant(),
								rs1.getString("doc_comment"), rs1.getString("DOC_NUMBER"), rs1.getString("doc_isfast"),
								rs1.getString("doc_name"), rs1.getLong("dtdiff")));
					}
				}
				// ____________________________
			}
			//
			prp1.close();
			rs1.close();
			//
			prp.close();
			rs.close();
		}
		// --------------------------------------------------------------
		ActivityRow root = new ActivityRow(null, null);
		root.setExpanded(true);
		root.getChildren().addAll(roots);
		gantt.setRoot(root);
		gantt.getLayers().add(layer);

		// source set ensures that only one link will come "out of" an activity.
		Set<ActivityRow> sourceSet = new HashSet<>();

		ListViewGraphics graphics = gantt.getGraphics();
		graphics.setLinkRenderer(ActivityLink.class, new StraightLinkRenderer<>(graphics, "Straight Link Renderer"));

		table = gantt.getTreeTable();
		table.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
			TreeItem<ActivityRow> item = table.getSelectionModel().getSelectedItem();
			if (item != null && item.getValue().act != null) {
				gantt.getGraphics().getTimeline().showTime(item.getValue().act.getStartTime());
			}
		});

		TreeTableColumn<ActivityRow, Long> empid = new TreeTableColumn<>("ИД сотр.");
		empid.setPrefWidth(75);
		empid.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.empid));

		TreeTableColumn<ActivityRow, Long> dtdiff = new TreeTableColumn<>("Осталось дней");
		dtdiff.setPrefWidth(75);
		dtdiff.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.dtdiff));

		TreeTableColumn<ActivityRow, String> docname = new TreeTableColumn<>("Название документа");
		docname.setPrefWidth(150);
		docname.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.docname));

		TreeTableColumn<ActivityRow, String> docnumber = new TreeTableColumn<>("Номер документа");
		docnumber.setPrefWidth(100);
		docnumber.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.docnumber));

		TreeTableColumn<ActivityRow, String> isfast = new TreeTableColumn<>("Срочный");
		isfast.setPrefWidth(75);
		isfast.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.isfast));

		TreeTableColumn<ActivityRow, String> comment = new TreeTableColumn<>("Док. комментарий");
		comment.setPrefWidth(120);
		comment.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.comment));

		TreeTableColumn<ActivityRow, Instant> docdate = new TreeTableColumn<>("Дата документа");
		docdate.setPrefWidth(100);
		docdate.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.start));

		TreeTableColumn<ActivityRow, Instant> docend = new TreeTableColumn<>("Срок документа");
		docend.setPrefWidth(100);
		docend.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.end));

		// ---------------------------------
		docdate.setCellFactory(column_ -> {

			TreeTableCell<ActivityRow, Instant> cell_ = new TreeTableCell<ActivityRow, Instant>() {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.getDefault())
						.withZone(ZoneId.systemDefault());

				@Override
				protected void updateItem(Instant item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						if (item != null) {
							this.setText(formatter.format(item));
						}
					}
				}
			};

			return cell_;
		});
		docend.setCellFactory(column_ -> {

			TreeTableCell<ActivityRow, Instant> cell_ = new TreeTableCell<ActivityRow, Instant>() {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.getDefault())
						.withZone(ZoneId.systemDefault());

				@Override
				protected void updateItem(Instant item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						if (item != null) {
							this.setText(formatter.format(item));

							LocalDate lt = LocalDate.now();
							LocalDate lcd = item.atZone(ZoneId.systemDefault()).toLocalDate();
							long daysBetween = ChronoUnit.DAYS.between(lt, lcd);
							if (daysBetween >= 20 | daysBetween < 0) {
								setStyle("-fx-text-fill: red;-fx-font-weight: bold");
							} else if (daysBetween <= 20 & daysBetween > 0) {
								setStyle("-fx-text-fill: orange;-fx-font-weight: bold");
							} else {
								setStyle("");
							}
						}
					}
				}
			};

			return cell_;
		});

		// ---------------------------------
		table.getColumns().addAll(empid, docname, docnumber, isfast, comment, docdate, docend, dtdiff);

		links.forEach(link -> gantt.getLinks().add(link));

		gantt.getGraphics().showEarliestActivities();

		Platform.runLater(() -> gantt.getGraphics().showAllActivities());

		return gantt;
	}

	private final ArrayList<ActivityLink<?>> links = new ArrayList<>();

	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void CreGant() {
		try {

			GanttChartBase<?> gant = createGanttChart();

			GantBorder.setTop(new GanttChartToolBar(gant));
			GantBorder.setCenter(gant);
			GantBorder.setBottom(new GanttChartStatusBar(gant));

		} catch (Exception e) {
			DbUtil.Log_Error(e);
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
			LoadTable();
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
	private void RefrGant(ActionEvent event) {
		try {
			CreGant();
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
