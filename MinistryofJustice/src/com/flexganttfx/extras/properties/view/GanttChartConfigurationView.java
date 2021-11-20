package com.flexganttfx.extras.properties.view;

import com.flexganttfx.view.GanttChartBase;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

@SuppressWarnings("rawtypes")
public class GanttChartConfigurationView extends TabPane {
	private GanttChartPropertySheet controlsSheet = new GanttChartPropertySheet<>();
	private GanttChartPropertySheet backgroundLayersSheet = new GanttChartPropertySheet<>();
	private GanttChartPropertySheet foregroundLayersSheet = new GanttChartPropertySheet<>();
	private GanttChartPropertySheet renderersSheet = new GanttChartPropertySheet<>();

	private final ObjectProperty<GanttChartBase> ganttChart;

	public GanttChartConfigurationView(GanttChartBase<?> ganttChart) {
		this();
		setGanttChart(ganttChart);
	}

	@SuppressWarnings("unchecked")
	public void update() {
		GanttChartBase ganttChart = getGanttChart();
		this.controlsSheet.getTargets().setAll(new Object[] { ganttChart });
		this.backgroundLayersSheet.getTargets().setAll(ganttChart.getGraphics().getBackgroundSystemLayers());
		this.foregroundLayersSheet.getTargets().setAll(ganttChart.getGraphics().getForegroundSystemLayers());
		this.renderersSheet.getTargets().setAll(ganttChart.getGraphics().getAllActivityRenderers());
	}

	public GanttChartConfigurationView() {
		this.ganttChart = new SimpleObjectProperty<>(this, "ganttChart");
		setSide(Side.RIGHT);
		Tab controlsTab = new Tab("Controls", (Node) this.controlsSheet);
		Tab backgroundLayersTab = new Tab("Background Layers", (Node) this.backgroundLayersSheet);
		Tab foregroundLayersTab = new Tab("Foreground Layers", (Node) this.foregroundLayersSheet);
		Tab renderersTab = new Tab("Renderers", (Node) this.renderersSheet);
		getTabs().setAll(new Tab[] { controlsTab, backgroundLayersTab, foregroundLayersTab, renderersTab });
		this.ganttChart.addListener(it -> update());
	}

	public final ObjectProperty<GanttChartBase> ganttChartProperty() {
		return this.ganttChart;
	}

	public final GanttChartBase getGanttChart() {
		return this.ganttChart.get();
	}

	public final void setGanttChart(GanttChartBase ganttChart) {
		this.ganttChart.set(ganttChart);
	}
}
