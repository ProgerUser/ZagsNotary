/*    */ package com.flexganttfx.extras.properties.view;
/*    */ 
/*    */ import com.flexganttfx.model.Row;
/*    */ import com.flexganttfx.view.GanttChartBase;
/*    */ import javafx.beans.Observable;
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.beans.property.SimpleObjectProperty;
/*    */ import javafx.geometry.Side;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.Tab;
/*    */ import javafx.scene.control.TabPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GanttChartConfigurationView
/*    */   extends TabPane
/*    */ {
/* 21 */   private GanttChartPropertySheet controlsSheet = new GanttChartPropertySheet<>();
/* 22 */   private GanttChartPropertySheet backgroundLayersSheet = new GanttChartPropertySheet<>();
/* 23 */   private GanttChartPropertySheet foregroundLayersSheet = new GanttChartPropertySheet<>();
/* 24 */   private GanttChartPropertySheet renderersSheet = new GanttChartPropertySheet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final ObjectProperty<GanttChartBase> ganttChart;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GanttChartConfigurationView(GanttChartBase<?> ganttChart) {
/* 46 */     this();
/* 47 */     setGanttChart(ganttChart);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 54 */     GanttChartBase ganttChart = getGanttChart();
/* 55 */     this.controlsSheet.getTargets().setAll(new Object[] { ganttChart });
/* 56 */     this.backgroundLayersSheet.getTargets().setAll(ganttChart.getGraphics().getBackgroundSystemLayers());
/* 57 */     this.foregroundLayersSheet.getTargets().setAll(ganttChart.getGraphics().getForegroundSystemLayers());
/* 58 */     this.renderersSheet.getTargets().setAll(ganttChart.getGraphics().getAllActivityRenderers());
/*    */   }
/*    */   
/* 61 */   public GanttChartConfigurationView() { this.ganttChart = new SimpleObjectProperty<>(this, "ganttChart");
/*    */     setSide(Side.RIGHT);
/*    */     Tab controlsTab = new Tab("Controls", (Node)this.controlsSheet);
/*    */     Tab backgroundLayersTab = new Tab("Background Layers", (Node)this.backgroundLayersSheet);
/*    */     Tab foregroundLayersTab = new Tab("Foreground Layers", (Node)this.foregroundLayersSheet);
/*    */     Tab renderersTab = new Tab("Renderers", (Node)this.renderersSheet);
/*    */     getTabs().setAll(new Tab[] { controlsTab, backgroundLayersTab, foregroundLayersTab, renderersTab });
/*    */     this.ganttChart.addListener(it -> update()); } public final ObjectProperty<GanttChartBase> ganttChartProperty() {
/* 69 */     return this.ganttChart;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final GanttChartBase getGanttChart() {
/* 78 */     return this.ganttChart.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void setGanttChart(GanttChartBase ganttChart) {
/* 87 */     this.ganttChart.set(ganttChart);
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\view\GanttChartConfigurationView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */