package com.flexganttfx.extras;

import java.text.MessageFormat;
import java.util.Objects;

import org.controlsfx.control.StatusBar;

import com.flexganttfx.extras.util.Messages;
import com.flexganttfx.model.Activity;
import com.flexganttfx.model.ActivityRef;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.dateline.VirtualGrid;
import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.graphics.ListViewGraphics;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class GanttChartStatusBar<R extends Row<?, ?, ?>> extends StatusBar {
	private Label gridLabel;
	private final InvalidationListener focusedActivityListener;
	private WeakInvalidationListener weakFocusedActivityListener;
	private final InvalidationListener virtualGridListener;
	private final WeakInvalidationListener weakVirtualGridListener;
	private final ObjectProperty<GanttChartBase<R>> ganttChart;

	public GanttChartStatusBar(GanttChartBase<R> ganttChart) {
		this();
		setGanttChart(ganttChart);
	}

	@SuppressWarnings("rawtypes")
	public GanttChartStatusBar() {
		this.focusedActivityListener = (observable -> {
			ListViewGraphics listViewGraphics = getGanttChart().getGraphics();

			ActivityRef<?> focusedActivity = listViewGraphics.getHoverActivity();
			if (focusedActivity != null) {
				Activity activity = focusedActivity.getActivity();
				setText(activity.getName());
			}
		});
		this.weakFocusedActivityListener = new WeakInvalidationListener(this.focusedActivityListener);

		this.virtualGridListener = (observable -> updateGridLabel());

		this.weakVirtualGridListener = new WeakInvalidationListener(this.virtualGridListener);

		this.ganttChart = new SimpleObjectProperty<>(this, "ganttChart");
		getStylesheets().add(GanttChartStatusBar.class.getResource("statusbar.css").toExternalForm());
		getStyleClass().add("gantt-chart-status-bar");
		this.gridLabel = new Label();
		this.gridLabel.getStyleClass().add("grid-label");
		getRightItems().add(this.gridLabel);
		updateGridLabel();
		ganttChartProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				ListViewGraphics listViewGraphics = oldValue.getGraphics();
				listViewGraphics.hoverActivityProperty().removeListener(this.weakFocusedActivityListener);
				listViewGraphics.virtualGridProperty().removeListener(this.weakVirtualGridListener);
			}
			if (newValue != null) {
				ListViewGraphics listViewGraphics = newValue.getGraphics();
				listViewGraphics.hoverActivityProperty().addListener(this.weakFocusedActivityListener);
				listViewGraphics.virtualGridProperty().addListener(this.weakVirtualGridListener);
			}
			updateGridLabel();
		});
	}

	public final ObjectProperty<GanttChartBase<R>> ganttChartProperty() {
		return this.ganttChart;
	}

	@SuppressWarnings("rawtypes")
	private void updateGridLabel() {
		GanttChartBase<R> gc = getGanttChart();
		if (gc == null)
			return;
		ListViewGraphics listViewGraphics = gc.getGraphics();
		VirtualGrid<?> grid = listViewGraphics.getVirtualGrid();
		if (grid != null) {
			this.gridLabel.setText(MessageFormat.format(Messages.getString("GanttChartStatusBar.MESSAGE_GRID_NAME"),
					new Object[] { grid.getName() }));
		} else {
			this.gridLabel.setText(Messages.getString("GanttChartStatusBar.MESSAGE_GRID_OFF"));
		}
	}

	public final GanttChartBase<R> getGanttChart() {
		return ganttChartProperty().get();
	}

	public final void setGanttChart(GanttChartBase<R> ganttChart) {
		Objects.requireNonNull(ganttChart);
		ganttChartProperty().set(ganttChart);
	}
}
