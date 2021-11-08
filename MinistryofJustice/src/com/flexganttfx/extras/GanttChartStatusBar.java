/*     */ package com.flexganttfx.extras;
/*     */ 
/*     */ import com.flexganttfx.extras.util.Messages;
/*     */ import com.flexganttfx.model.Activity;
/*     */ import com.flexganttfx.model.ActivityRef;
/*     */ import com.flexganttfx.model.Row;
/*     */ import com.flexganttfx.model.dateline.VirtualGrid;
/*     */ import com.flexganttfx.view.GanttChartBase;
/*     */ import com.flexganttfx.view.graphics.ListViewGraphics;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Objects;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.control.Label;
/*     */ import org.controlsfx.control.StatusBar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GanttChartStatusBar<R extends Row<?, ?, ?>>
/*     */   extends StatusBar
/*     */ {
/*     */   private Label gridLabel;
/*     */   private final InvalidationListener focusedActivityListener;
/*     */   private WeakInvalidationListener weakFocusedActivityListener;
/*     */   private final InvalidationListener virtualGridListener;
/*     */   private final WeakInvalidationListener weakVirtualGridListener;
/*     */   private final ObjectProperty<GanttChartBase<R>> ganttChart;
/*     */   
/*     */   public GanttChartStatusBar(GanttChartBase<R> ganttChart) {
/*     */     this();
/*     */     setGanttChart(ganttChart);
/*     */   }
/*     */   
/*     */   public GanttChartStatusBar() {
/*  91 */     this.focusedActivityListener = (observable -> {
/*     */         ListViewGraphics listViewGraphics = getGanttChart().getGraphics();
/*     */         
/*     */         ActivityRef<?> focusedActivity = listViewGraphics.getHoverActivity();
/*     */         if (focusedActivity != null) {
/*     */           Activity activity = focusedActivity.getActivity();
/*     */           setText(activity.getName());
/*     */         } 
/*     */       });
/* 100 */     this.weakFocusedActivityListener = new WeakInvalidationListener(this.focusedActivityListener);
/*     */     
/* 102 */     this.virtualGridListener = (observable -> updateGridLabel());
/*     */     
/* 104 */     this.weakVirtualGridListener = new WeakInvalidationListener(this.virtualGridListener);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.ganttChart = new SimpleObjectProperty<>(this, "ganttChart"); getStylesheets().add(GanttChartStatusBar.class.getResource("statusbar.css").toExternalForm()); getStyleClass().add("gantt-chart-status-bar"); this.gridLabel = new Label(); this.gridLabel.getStyleClass().add("grid-label"); getRightItems().add(this.gridLabel); updateGridLabel(); ganttChartProperty().addListener((observable, oldValue, newValue) -> {
/*     */           if (oldValue != null) {
/*     */             ListViewGraphics listViewGraphics = oldValue.getGraphics(); listViewGraphics.hoverActivityProperty().removeListener(this.weakFocusedActivityListener); listViewGraphics.virtualGridProperty().removeListener(this.weakVirtualGridListener);
/*     */           } 
/*     */           if (newValue != null) {
/*     */             ListViewGraphics listViewGraphics = newValue.getGraphics();
/*     */             listViewGraphics.hoverActivityProperty().addListener(this.weakFocusedActivityListener);
/*     */             listViewGraphics.virtualGridProperty().addListener(this.weakVirtualGridListener);
/*     */           } 
/*     */           updateGridLabel();
/* 130 */         }); } public final ObjectProperty<GanttChartBase<R>> ganttChartProperty() { return this.ganttChart; } private void updateGridLabel() { GanttChartBase<R> gc = getGanttChart();
/*     */     if (gc == null)
/*     */       return; 
/*     */     ListViewGraphics listViewGraphics = gc.getGraphics();
/*     */     VirtualGrid<?> grid = listViewGraphics.getVirtualGrid();
/*     */     if (grid != null) {
/*     */       this.gridLabel.setText(MessageFormat.format(Messages.getString("GanttChartStatusBar.MESSAGE_GRID_NAME"), new Object[] { grid.getName() }));
/*     */     } else {
/*     */       this.gridLabel.setText(Messages.getString("GanttChartStatusBar.MESSAGE_GRID_OFF"));
/*     */     }  }
/* 140 */   public final GanttChartBase<R> getGanttChart() { return ganttChartProperty().get(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setGanttChart(GanttChartBase<R> ganttChart) {
/* 151 */     Objects.requireNonNull(ganttChart);
/* 152 */     ganttChartProperty().set(ganttChart);
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\GanttChartStatusBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */