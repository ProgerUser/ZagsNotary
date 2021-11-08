/*     */ package com.flexganttfx.extras.properties;
/*     */ 
/*     */ import com.flexganttfx.model.Row;
/*     */ import com.flexganttfx.view.GanttChart;
/*     */ import com.flexganttfx.view.GanttChartBase;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import org.controlsfx.control.PropertySheet;
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
/*     */ public class GanttChartItemProvider<R extends Row<?, ?, ?>>
/*     */   implements ItemProvider<GanttChart<R>>
/*     */ {
/*     */   private static final String GANTT_CHART_PROPERTIES_CATEGORY = "Control: Gantt Chart";
/*     */   
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final GanttChart<R> gc) {
/*  28 */     GanttChartBaseItemProvider<R> baseItems = new GanttChartBaseItemProvider<>();
/*  29 */     List<PropertySheet.Item> items = baseItems.getPropertySheetItems((GanttChartBase<R>)gc);
/*     */     
/*  31 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  35 */             return Optional.of(gc.displayModeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  40 */             gc.setDisplayMode((GanttChart.DisplayMode)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  45 */             return gc.getDisplayMode();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  50 */             return GanttChart.DisplayMode.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  55 */             return "Display Mode";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  60 */             return "Standard Gantt, Table Only, Graphics Only";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  65 */             return "Control: Gantt Chart";
/*     */           }
/*     */         });
/*     */     
/*  69 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  73 */             return Optional.of(gc.showTreeTableProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  78 */             gc.setShowTreeTable(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  83 */             return Boolean.valueOf(gc.isShowTreeTable());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  88 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  93 */             return "Show Tree Table";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  98 */             return "Enables / disables display of the tree table view";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 103 */             return "Control: Gantt Chart";
/*     */           }
/*     */         });
/*     */     
/* 107 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 111 */             return Optional.of(gc.rowHeaderTypeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 116 */             gc.setRowHeaderType((GanttChart.RowHeaderType)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 121 */             return gc.getRowHeaderType();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 126 */             return GanttChart.RowHeaderType.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 131 */             return "Row Header Mode";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 136 */             return "The type of content shown by the row header.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 141 */             return "Control: Gantt Chart";
/*     */           }
/*     */         });
/*     */     
/* 145 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\GanttChartItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */