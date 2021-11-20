package com.flexganttfx.extras;

import java.text.MessageFormat;
import java.util.Objects;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;

import com.flexganttfx.extras.util.Messages;
import com.flexganttfx.model.Row;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.ListViewGraphics;
import com.flexganttfx.view.timeline.Timeline;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.stage.PopupWindow;
import javafx.util.StringConverter;

public class GanttChartToolBar<R extends Row<?, ?, ?>> extends ToolBar {
	private final ObjectProperty<GanttChartBase<R>> ganttChart;

	private PopOver layerControlsPopOver;

	private PopOver radarPopOver;

	public GanttChartToolBar() {
		this.ganttChart = new SimpleObjectProperty<>(this, "ganttChart");
		setOrientation(Orientation.HORIZONTAL);
		getStylesheets().add(GanttChartToolBar.class.getResource("toolbar.css").toExternalForm());
		ganttChartProperty().addListener(observable -> buildToolBar());
	}

	public GanttChartToolBar(GanttChartBase<R> ganttChart) {
		this();
		setGanttChart(ganttChart);
	}

	public final ObjectProperty<GanttChartBase<R>> ganttChartProperty() {
		return this.ganttChart;
	}

	public final GanttChartBase<R> getGanttChart() {
		return ganttChartProperty().get();
	}

	public final void setGanttChart(GanttChartBase<R> ganttChart) {
		Objects.requireNonNull(ganttChart);
		ganttChartProperty().set(ganttChart);
	}

	@SuppressWarnings("rawtypes")
	private void buildToolBar() {
		getItems().clear();
		if (this.layerControlsPopOver != null) {
			this.layerControlsPopOver.hide();
			this.layerControlsPopOver = null;
		}
		GanttChartBase<R> ganttChart = getGanttChart();
		if (ganttChart != null) {
			Button timeNow = new Button(Messages.getString("GanttChartToolBar.BUTTON_NOW"));
			timeNow.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.ACCESS_POINT));
			timeNow.setOnAction(showTimeNow());
			getItems().add(timeNow);
			Button earliest = new Button(Messages.getString("GanttChartToolBar.BUTTON_EARLIEST"));
			earliest.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.PAGE_FIRST));
			earliest.setOnAction(showEarliestActivities());
			getItems().add(earliest);
			Button latest = new Button(Messages.getString("GanttChartToolBar.BUTTON_LATEST"));
			latest.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.PAGE_LAST));
			latest.setOnAction(showLatestActivities());
			getItems().add(latest);
			Button showAll = new Button(Messages.getString("GanttChartToolBar.BUTTON_ALL"));
			showAll.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.ARROW_COMPRESS_ALL));
			showAll.setOnAction(showAllActivities());
			getItems().add(showAll);
			getItems().add(new Separator());
			Button zoomIn = new Button(Messages.getString("GanttChartToolBar.BUTTON_ZOOM_IN"));
			zoomIn.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.MAGNIFY_PLUS));
			zoomIn.setOnAction(zoomIn());
			getItems().add(zoomIn);
			Button zoomOut = new Button(Messages.getString("GanttChartToolBar.BUTTON_ZOOM_OUT"));
			zoomOut.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.MAGNIFY_MINUS));
			zoomOut.setOnAction(zoomOut());
			getItems().add(zoomOut);
			ComboBox<Timeline.ZoomMode> zoomModeBox = new ComboBox<>();
			zoomModeBox.setConverter(new StringConverter<Timeline.ZoomMode>() {
				public String toString(Timeline.ZoomMode mode) {
					switch (mode) {
					default:
						return "Keep Center";
					case KEEP_START_TIME:
						return "Keep Start";
					case KEEP_END_TIME:
						break;
					}
					return "Keep End";
				}

				public Timeline.ZoomMode fromString(String string) {
					return null;
				}
			});
			zoomModeBox.getItems().setAll(Timeline.ZoomMode.values());
			zoomModeBox.valueProperty().bindBidirectional(getGanttChart().getTimeline().zoomModeProperty());
			getItems().add(zoomModeBox);
			getItems().add(new Separator());
			ToggleButton links = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_LINKS"));
			links.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.VECTOR_LINE));
			links.selectedProperty().bindBidirectional(ganttChart.getGraphics().showLinksProperty());
			getItems().add(links);
			ToggleButton headers = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_SCALE"));
			headers.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.RULER));
			headers.selectedProperty().bindBidirectional(ganttChart.getGraphics().showRowHeadersProperty());
			getItems().add(headers);
			Button layers = new Button(Messages.getString("GanttChartToolBar.BUTTON_LAYERS"));
			layers.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.LAYERS));
			layers.setOnAction(showLayerControls(layers));
			getItems().add(layers);
			Button radar = new Button(Messages.getString("GanttChartToolBar.BUTTON_RADAR"));
			radar.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.RADAR));
			radar.setOnAction(showRadarPopOver(radar));
			getItems().add(radar);
			if (ganttChart instanceof GanttChart) {
				ToggleButton table = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_TABLE"));
				table.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.TABLE));
				table.selectedProperty().bindBidirectional(((GanttChart) ganttChart).showTreeTableProperty());
				getItems().add(table);
			}
			getItems().add(new Separator());
			ListViewGraphics<R> graphics = ganttChart.getGraphics();
			ToggleButton cursor = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_CURSOR"));
			cursor.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CURSOR_TEXT));
			cursor.selectedProperty().bindBidirectional(graphics.showVerticalCursorProperty());
			getItems().add(cursor);
			MenuButton gridLines = new MenuButton(Messages.getString("GanttChartToolBar.BUTTON_GRID"));
			gridLines.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.GRID));
			MenuItem gridOff = new MenuItem(Messages.getString("GanttChartToolBar.MENU_ITEM_GRID_OFF"));
			gridOff.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.GRID_OFF));
			gridOff.setOnAction(hideGridLines());
			gridLines.getItems().add(gridOff);
			for (int i = 1; i <= 2; i++) {
				MenuItem gridOn = new MenuItem(
						MessageFormat.format(Messages.getString("GanttChartToolBar.MENU_ITEM_GRID_LEVELS"),
								new Object[] { Integer.valueOf(i) }));
				gridLines.getItems().add(gridOn);
				gridOn.setOnAction(showGridLines(i));
			}
			getItems().add(gridLines);
			ToggleButton calendars = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_CALENDARS"));
			calendars.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CALENDAR));
			calendars.selectedProperty().bindBidirectional(graphics.showCalendarLayerProperty());
			getItems().add(calendars);
			ToggleButton nowLine = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_NOW_LINE"));
			nowLine.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CLOCK));
			nowLine.selectedProperty().bindBidirectional(graphics.showNowLineLayerProperty());
			getItems().add(nowLine);
			ToggleButton detail = new ToggleButton(Messages.getString("GanttChartToolBar.BUTTON_DETAIL"));
			detail.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.BOOK_OPEN));
			detail.selectedProperty().bindBidirectional(ganttChart.showDetailProperty());
			getItems().add(detail);
			getItems().add(new Separator());
			CustomTextField filterField = new CustomTextField();
			filterField.getStyleClass().add("search-field");
			filterField.setPromptText("Filter");
			filterField.textProperty().addListener(it -> filter(filterField.getText()));
			getItems().add(filterField);
		}
	}

	private void filter(String txt) {
		if (txt.trim().equals("")) {
			getGanttChart().setRowFilter(null);
		} else {
			getGanttChart().setRowFilter(row -> row.getName().toLowerCase().contains(txt.toLowerCase()));
		}
	}

	private EventHandler<ActionEvent> showLayerControls(Button button) {
		return evt -> {
			if (this.layerControlsPopOver == null) {
				LayersView<R> layersView = new LayersView<>();
				layersView.setGraphics((GraphicsBase<R>) getGanttChart().getGraphics());
				this.layerControlsPopOver = new PopOver((Node) layersView);
				this.layerControlsPopOver.setTitle(Messages.getString("GanttChartToolBar.BUTTON_LAYERS"));
				this.layerControlsPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
			}
			Point2D localToScreen = button.localToScreen(0.0D, 0.0D);
			this.layerControlsPopOver.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);
			this.layerControlsPopOver.show(button, localToScreen.getX() + button.getWidth() / 2.0D,
					localToScreen.getY() + button.getHeight() - 2.0D);
		};
	}

	private EventHandler<ActionEvent> showRadarPopOver(Button button) {
		return evt -> {
			if (this.radarPopOver == null) {
				RadarView<R> radarView = new RadarView<>();
				radarView.setGraphics((GraphicsBase<R>) getGanttChart().getGraphics());
				this.radarPopOver = new PopOver((Node) radarView);
				this.radarPopOver.setTitle(Messages.getString("GanttChartToolBar.TITLE_RADAR"));
				this.radarPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
			}
			Point2D localToScreen = button.localToScreen(0.0D, 0.0D);
			this.radarPopOver.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);
			this.radarPopOver.show(button, localToScreen.getX() + button.getWidth() / 2.0D,
					localToScreen.getY() + button.getHeight() - 2.0D);
		};
	}

	private EventHandler<ActionEvent> showGridLines(int level) {
		return evt -> {
			getGanttChart().getGraphics().setMaxGridLevel(level);
			getGanttChart().getGraphics().setShowGridLineLayer(true);
		};
	}

	private EventHandler<ActionEvent> hideGridLines() {
		return evt -> getGanttChart().getGraphics().setShowGridLineLayer(false);
	}

	private EventHandler<ActionEvent> zoomOut() {
		return evt -> getGanttChart().getMasterTimeline().zoomOut();
	}

	private EventHandler<ActionEvent> zoomIn() {
		return evt -> getGanttChart().getMasterTimeline().zoomIn();
	}

	private EventHandler<ActionEvent> showAllActivities() {
		return evt -> getGanttChart().getGraphics().showAllActivities();
	}

	private EventHandler<ActionEvent> showLatestActivities() {
		return evt -> getGanttChart().getGraphics().showLatestActivities();
	}

	private EventHandler<ActionEvent> showEarliestActivities() {
		return evt -> getGanttChart().getGraphics().showEarliestActivities();
	}

	private EventHandler<ActionEvent> showTimeNow() {
		return evt -> getGanttChart().getMasterTimeline().showNow();
	}
}
