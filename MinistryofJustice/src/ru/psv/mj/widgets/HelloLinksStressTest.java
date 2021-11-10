package ru.psv.mj.widgets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
import com.flexganttfx.model.ActivityLink;
import com.flexganttfx.model.ActivityRef;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.ActivityBase;
import com.flexganttfx.model.activity.CompletableActivity;
import com.flexganttfx.model.activity.MutableCompletableActivityBase;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.graphics.ActivityBounds;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.ListViewGraphics;
import com.flexganttfx.view.graphics.renderer.CompletableActivityRenderer;
import com.flexganttfx.view.graphics.renderer.StraightLinkRenderer;
import com.flexganttfx.view.util.Position;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.psv.mj.app.model.Connect;
import ru.psv.mj.utils.DbUtil;

public class HelloLinksStressTest extends Application {

	private final ArrayList<ActivityLink<?>> links = new ArrayList<>();

	public HelloLinksStressTest() {

	}

	/**
	 * Главная точка входа
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Locale.setDefault(Locale.ENGLISH);
		launch(args);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	protected GanttChartBase<?> createGanttChart() throws Exception {
		Connect.connectionURL = "localhost:1522/orcl";
		Connect.userID = "xxi";
		Connect.userPassword = "";
//		Connect.userID = "psv";
//		Connect.userPassword = "ipman165";
		DbUtil.Db_Connect();

		GanttChart<ActivityRow> gantt = new GanttChart();
		List<ActivityRow> roots = new ArrayList<>();

		// int TOTAL = 10;

//		for (int i = 0; i < TOTAL; i++) {
//			final ActivityRow row = new ActivityRow("row " + i, i);
//			roots.add(row);
//			for (int j = 0; j < 5; j++) {
//				row.getChildren().add(new ActivityRow("sub Row" + i + " : " + j, row));
//			}
//		}
		// --------------------------------------------------------------
		{
			PreparedStatement prp1 = null;
			ResultSet rs1 = null;
			PreparedStatement prp = DbUtil.conn.prepareStatement("select * from PM_EMP");
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("EMP_LASTNAME"));
				final ActivityRow row = new ActivityRow(rs.getString("EMP_LASTNAME") + " "
						+ rs.getString("EMP_FIRSTNAME") + " " + rs.getString("EMP_MIDDLENAME"), rs.getLong("EMP_ID"));
				roots.add(row);
				// ____________________________
				{
					prp1 = DbUtil.conn
							.prepareStatement("select * from VPM_PROJECTS where PRJ_EMP = ? order by DOC_END desc");
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
								rs1.getString("doc_name")));
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

//		for (int i = 0; i < 100000; i++) {
//			int s = -1, e = -1;
//			while (s >= e) {
//				s = (int) (Math.random() * TOTAL);
//				e = Math.min(TOTAL - 1, s + (int) (Math.random() * 5));
//			}
//
//			ActivityRow rsChild = roots.get(s);
//			ActivityRow reChild = roots.get(e);
//
//			ActivityRow predecessor = rsChild.getChildren().get((int) (Math.random() * rsChild.getChildren().size()));
//			ActivityRow successor = reChild.getChildren().get((int) (Math.random() * reChild.getChildren().size()));
//
//			if (!sourceSet.contains(predecessor)) {
//				sourceSet.add(predecessor);
//				links.add(new ActivityLink<>(new ActivityRef<>(predecessor, layer, predecessor.act),
//						new ActivityRef<>(successor, layer, successor.act)));
//
//				predecessor.setLinksOut(predecessor.getLinksOut() + 1);
//				successor.setLinksIn(successor.getLinksOut() + 1);
//			}
//		}

		ListViewGraphics graphics = gantt.getGraphics();
		graphics.setLinkRenderer(ActivityLink.class, new StraightLinkRenderer<>(graphics, "Straight Link Renderer"));

		TreeTableView<ActivityRow> table = gantt.getTreeTable();
		table.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
			TreeItem<ActivityRow> item = table.getSelectionModel().getSelectedItem();
			if (item != null && item.getValue().act != null) {
				gantt.getGraphics().getTimeline().showTime(item.getValue().act.getStartTime());
			}
		});

//		TreeTableColumn<ActivityRow, Integer> columnA = new TreeTableColumn<>("in");
//		columnA.setMinWidth(100);
//		columnA.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getLinksIn()));
//
//		TreeTableColumn<ActivityRow, Integer> columnB = new TreeTableColumn<>("out");
//		columnB.setMinWidth(100);
//		columnB.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getLinksOut()));

		TreeTableColumn<ActivityRow, Long> empid = new TreeTableColumn<>("ИД сотр.");
		empid.setPrefWidth(75);
		empid.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().data.empid));

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
							if (daysBetween >= 20) {
								setStyle("-fx-text-fill: red;-fx-font-weight: bold");
							} else {
								setStyle("");
							}
						}
					}
				}
			};

			return cell_;
		});
//		docend.setCellFactory(col -> {
//			TreeTableCell<ActivityRow, Instant> cell = new TreeTableCell<ActivityRow, Instant>() {
//				@Override
//				public void updateItem(Instant item, boolean empty) {
//					super.updateItem(item, empty);
//					if (empty) {
//						setText(null);
//					} else {
//						setText(item);
//						if (table.getSelectionModel().getSelectedItem() != null) {
////							ActivityRow treetable = table.getSelectionModel().getSelectedItem().getValue();
//							Instant docd = item;
//							LocalDate lt = LocalDate.now();
//							LocalDate lcd = docd.atZone(ZoneId.systemDefault()).toLocalDate();
//							long daysBetween = ChronoUnit.DAYS.between(lt, lcd);
////							Instant sel = treetable.data.end;
//
//							if (daysBetween >= 20) {
//								setStyle("-fx-text-fill: green;-fx-font-weight: bold");
//							} else {
//								setStyle("");
//							}
//						}
//					}
//				}
//			};
//			cell.setAlignment(Pos.CENTER);
//			return cell;
//		});
		// ---------------------------------
		table.getColumns().addAll(empid, docname, docnumber, isfast, comment, docdate, docend);

		links.forEach(link -> gantt.getLinks().add(link));

		gantt.getGraphics().showEarliestActivities();

		Platform.runLater(() -> gantt.getGraphics().showAllActivities());

		return gantt;
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
//			data.start = start;
//			data.end = end;
			data.name = name;
			data.empid = empid;
			setExpanded(true);
			if (data != null && name != null) {
				// createActivity(data);
				setName(data.name);
			}
		}

		public ActivityRow(String name, ActivityRow parent, Instant start, Instant end, String comment,
				String docnumber, String isfast, String docname) {
			data = new Data();
			data.start = start;
			data.end = end;
			data.name = name;
			data.comment = comment;
			data.docnumber = docnumber;
			data.isfast = isfast;
			data.docname = docname;
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
//            System.out.println("st: " + data.start + ", et: " + data.end);
			act = new MutableCompletableActivityBase<>(data.name, data.start, data.end);
			act.setUserObject(data);
			addActivity(layer, act);
		}
	}

	Instant generateRandomInstant(int startYear, int endYear) {
		LocalDate startDate = LocalDate.of(startYear, 1, 1); // start date
		long start = startDate.toEpochDay();
		LocalDate endDate = LocalDate.of(endYear, 1, 1); // end date
		long end = endDate.toEpochDay();
		long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
		return LocalDate.ofEpochDay(randomEpochDay).atStartOfDay().toInstant(ZoneOffset.UTC);
	}

	class CompletableActivityRendererBase<A extends CompletableActivity> extends CompletableActivityRenderer<A> {

		@SuppressWarnings("unused")
		private double widthPerChar = -1;

		public CompletableActivityRendererBase(GraphicsBase<?> graphics, String name) {
			super(graphics, name);
			setGlossy(false);
			setBarHeight(40);
			setCornerRadius(5);
			setCornersRounded(true);
			setFont(new Font("Roboto", 15));
			setFillCompletion(Color.valueOf("#2899B0"));
			setFill(Color.valueOf("#3CB5CE"));
			setTextFillHover(Color.rgb(255, 255, 255, .87));
			setTextFillPressed(getTextFillHover());
			setTextFillHighlight(getTextFillHover());
			setTextFillSelected(getTextFillHover());
		}

		@Override
		public void drawCompletion(ActivityRef<A> activityRef, GraphicsContext gc, double x, double y, double w,
				double h, boolean selected, boolean hover, boolean highlighted, boolean pressed) {
			super.drawCompletion(activityRef, gc, x, y, w, h, selected, hover, highlighted, pressed);
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void drawBorder(ActivityRef activityRef, Position position, GraphicsContext gc, double x, double y,
				double w, double h, boolean selected, boolean hover, boolean highlighted, boolean pressed) {
			// do nothing
		}

		@Override
		public ActivityBounds drawActivity(ActivityRef<A> path, Position position, GraphicsContext gc, double x,
				double y, double w, double h, boolean selected, boolean hover, boolean highlighted, boolean pressed) {
			return super.drawActivity(path, position, gc, x, y, w, h, selected, hover, highlighted, pressed);
		}

		@Override
		public void drawBackground(ActivityRef<A> activityRef, Position position, GraphicsContext gc, double x,
				double y, double w, double h, boolean selected, boolean hover, boolean highlighted, boolean pressed) {
			super.drawBackground(activityRef, position, gc, x, y, w, h, selected, hover, highlighted, pressed);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {

		GanttChartBase<?> gant = createGanttChart();
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(new GanttChartToolBar(gant));
		borderPane.setCenter(gant);
		borderPane.setBottom(new GanttChartStatusBar(gant));
		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

}