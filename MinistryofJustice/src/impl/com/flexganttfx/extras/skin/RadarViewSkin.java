package impl.com.flexganttfx.extras.skin;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;

import com.flexganttfx.extras.RadarView;
import com.flexganttfx.model.Activity;
import com.flexganttfx.model.ActivityRepository;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.timeline.TimelineModel;
import com.flexganttfx.model.util.TimeInterval;
import com.flexganttfx.view.graphics.ActivityEvent;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.timeline.Dateline;
import com.flexganttfx.view.timeline.Timeline;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SkinBase;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RadarViewSkin<R extends Row<?, ?, ?>> extends SkinBase<RadarView<R>> {
	private final Canvas activitiesCanvas;
	private final Canvas visibleTimeIntervalCanvas;
	private final InvalidationListener redrawTimeIntervalListener = evt -> drawVisibleTimeInterval();
	private final InvalidationListener weakRedrawTimeIntervalListener = new WeakInvalidationListener(
			this.redrawTimeIntervalListener);
	private Rectangle2D visibleBounds;
	private final ObjectProperty<Paint> visibleTimeIntervalColor;

	public RadarViewSkin(RadarView<R> view) {
		super(view);

		this.visibleTimeIntervalColor = new SimpleObjectProperty<>(this, "visibleTimeIntervalColor",
				Color.GREEN.deriveColor(0.0D, 1.0D, 1.0D, 0.3D));
		StackPane stackPane = new StackPane();
		stackPane.getStyleClass().add("radar");
		this.activitiesCanvas = new Canvas();
		this.activitiesCanvas.widthProperty().bind(view.radarWidthProperty());
		this.activitiesCanvas.heightProperty().bind(view.radarHeightProperty());
		stackPane.getChildren().add(this.activitiesCanvas);
		this.visibleTimeIntervalCanvas = new Canvas();
		this.visibleTimeIntervalCanvas.widthProperty().bind(view.radarWidthProperty());
		this.visibleTimeIntervalCanvas.heightProperty().bind(view.radarHeightProperty());
		stackPane.getChildren().add(this.visibleTimeIntervalCanvas);
		getChildren().add(stackPane);
		GraphicsBase<?> graphics = view.getGraphics();
		EventHandler<InputEvent> activityEventHandler = evt -> drawActivities();
		if (graphics != null) {
			graphics.getTimeline().visibleTimeIntervalProperty().addListener(this.weakRedrawTimeIntervalListener);
			graphics.getRows().addListener(this.weakRedrawTimeIntervalListener);
			graphics.addEventHandler(ActivityEvent.ANY, activityEventHandler);
		}
		view.graphicsProperty().addListener((observable, oldGraphics, newGraphics) -> {
			if (oldGraphics != null) {
				graphics.getTimeline().visibleTimeIntervalProperty()
						.removeListener(this.weakRedrawTimeIntervalListener);
				oldGraphics.getRows().removeListener(this.weakRedrawTimeIntervalListener);
				oldGraphics.removeEventHandler(ActivityEvent.ANY, activityEventHandler);
			}
			if (newGraphics != null) {
				graphics.getTimeline().visibleTimeIntervalProperty().addListener(this.weakRedrawTimeIntervalListener);
				newGraphics.getRows().addListener(this.weakRedrawTimeIntervalListener);
				newGraphics.addEventHandler(ActivityEvent.ANY, activityEventHandler);
			}
		});
		drawAll();
		this.visibleTimeIntervalCanvas.setOnMousePressed(this::mousePressed);
		this.visibleTimeIntervalCanvas.setOnMouseDragged(this::mouseDragged);
		this.visibleTimeIntervalCanvas.setOnMouseClicked(this::mouseClicked);
	}

	private void mousePressed(MouseEvent e) {
	}

	private void mouseDragged(MouseEvent e) {
		showLocation(e.getX() - this.visibleBounds.getWidth() / 2.0D);
	}

	private void mouseClicked(MouseEvent e) {
		showLocation(e.getX() - this.visibleBounds.getWidth() / 2.0D);
	}

	public final ObjectProperty<Paint> visibleTimeIntervalColorProperty() {
		return this.visibleTimeIntervalColor;
	}

	private void showLocation(double location) {
		double x = Math.min(this.activitiesCanvas.getWidth() - this.visibleBounds.getWidth(), Math.max(0.0D, location));
		Instant time = calculateTimeAt(x);
		Timeline timeline = getSkinnable().getGraphics().getTimeline();
		TimelineModel<?> timelineModel = timeline.getModel();
		timelineModel.setStartTime(time);
	}

	private void drawAll() {
		drawActivities();
		drawVisibleTimeInterval();
	}

	private void drawActivities() {
		GraphicsContext gc = this.activitiesCanvas.getGraphicsContext2D();
		double width = this.activitiesCanvas.getWidth();
		double height = this.activitiesCanvas.getHeight();
		gc.clearRect(0.0D, 0.0D, width, height);
		GraphicsBase<R> graphics = getSkinnable().getGraphics();
		if (graphics != null) {
			Dateline dateline = graphics.getTimeline().getDateline();
			TemporalUnit temporalUnit = dateline.getPrimaryTemporalUnit();
			ZoneId zoneId = dateline.getZoneId();
			gc.setStroke(Color.RED);
			gc.setLineWidth(0.5D);
			Instant earliestTimeUsed = graphics.getEarliestTimeUsed();
			Instant latestTimeUsed = graphics.getLatestTimeUsed();
			ObservableList<?> rows = graphics.getRows();
			int numberOfRows = rows.size();
			for (int i = 0; i < numberOfRows; i++) {
				Row<?, ?, ?> row = (Row<?, ?, ?>) rows.get(i);
				ActivityRepository<?> repository = row.getRepository();
				Instant earliestTimeUsedInRow = repository.getEarliestTimeUsed();
				Instant latestTimeUsedInRow = repository.getLatestTimeUsed();
				if (earliestTimeUsedInRow != null && latestTimeUsedInRow != null)
					for (Layer layer : graphics.getLayers()) {
						Iterator<?> activities = repository.getActivities(layer, earliestTimeUsedInRow,
								latestTimeUsedInRow, temporalUnit, zoneId);
						while (activities.hasNext()) {
							Activity activity = (Activity) activities.next();
							double x1 = calculateX(activity.getStartTime(), width, earliestTimeUsed, latestTimeUsed);
							double x2 = calculateX(activity.getEndTime(), width, earliestTimeUsed, latestTimeUsed);
							double y = calculateY(i, numberOfRows, height);
							gc.strokeLine(x1, y, x2, y);
						}
					}
			}
		}
	}

	private void drawVisibleTimeInterval() {
		GraphicsContext gc = this.visibleTimeIntervalCanvas.getGraphicsContext2D();
		double width = this.visibleTimeIntervalCanvas.getWidth();
		double height = this.visibleTimeIntervalCanvas.getHeight();
		gc.clearRect(0.0D, 0.0D, width, height);
		GraphicsBase<R> graphics = getSkinnable().getGraphics();
		if (graphics != null) {
			Instant earliestTimeUsed = graphics.getEarliestTimeUsed();
			Instant latestTimeUsed = graphics.getLatestTimeUsed();
			Timeline timeline = graphics.getTimeline();
			TimeInterval visibleTimeInterval = timeline.getVisibleTimeInterval();
			Instant visibleStartTime = visibleTimeInterval.getStartTime();
			Instant visibleEndTime = visibleTimeInterval.getEndTime();
			if (earliestTimeUsed != null && latestTimeUsed != null) {
				if (visibleStartTime.isBefore(earliestTimeUsed))
					visibleStartTime = earliestTimeUsed;
				if (visibleEndTime.isAfter(latestTimeUsed))
					visibleEndTime = latestTimeUsed;
				double x1 = calculateX(visibleStartTime, width, earliestTimeUsed, latestTimeUsed);
				double x2 = calculateX(visibleEndTime, width, earliestTimeUsed, latestTimeUsed);
				gc.setFill(getVisibleTimeIntervalColor());
				gc.fillRect(x1, 0.0D, x2 - x1, height);
				this.visibleBounds = new Rectangle2D(x1, 0.0D, Math.max(0.0D, x2 - x1), Math.max(0.0D, height));
			} else {
				this.visibleBounds = null;
			}
		}
	}

	public final Paint getVisibleTimeIntervalColor() {
		return this.visibleTimeIntervalColor.get();
	}

	public final void setVisibleTimeIntervalColor(Paint visibleTimeIntervalColor) {
		this.visibleTimeIntervalColor.set(visibleTimeIntervalColor);
	}

	private double calculateY(int rowIndex, int totalNumberOfRows, double canvasHeight) {
		return (int) (canvasHeight / totalNumberOfRows * rowIndex) + 0.5D;
	}

	private double calculateX(Instant time, double width, Instant earliestTimeUsed, Instant latestTimeUsed) {
		double mpp = (latestTimeUsed.toEpochMilli() - earliestTimeUsed.toEpochMilli()) / width;
		return (time.toEpochMilli() - earliestTimeUsed.toEpochMilli()) / mpp;
	}

	private Instant calculateTimeAt(double x) {
		GraphicsBase<R> graphics = getSkinnable().getGraphics();
		Instant earliestTimeUsed = graphics.getEarliestTimeUsed();
		Instant latestTimeUsed = graphics.getLatestTimeUsed();

		double mpp = (latestTimeUsed.toEpochMilli() - earliestTimeUsed.toEpochMilli())
				/ this.activitiesCanvas.getWidth();
		long millis = (long) (mpp * x);
		return Instant.ofEpochMilli(earliestTimeUsed.toEpochMilli() + millis);
	}
}
