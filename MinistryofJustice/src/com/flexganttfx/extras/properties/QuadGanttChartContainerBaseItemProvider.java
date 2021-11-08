/*     */ package com.flexganttfx.extras.properties;
/*     */ 
/*     */ import com.flexganttfx.view.GanttChartBase;
/*     */ import com.flexganttfx.view.container.QuadGanttChartContainerBase;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
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
/*     */ 
/*     */ public class QuadGanttChartContainerBaseItemProvider<T extends GanttChartBase<?>>
/*     */   implements ItemProvider<QuadGanttChartContainerBase<T>>
/*     */ {
/*     */   private static final String QUAD_GANTT_CHART_CONTAINER_PROPERTIES_CATEGORY = "Control: Quad Gantt Chart Container";
/*     */   
/*     */   public final ObservableList<PropertySheet.Item> getPropertySheetItems(final QuadGanttChartContainerBase<T> container) {
/*  30 */     ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
/*     */     
/*  32 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  36 */             return Optional.of(container.showLowerProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  41 */             container.setShowLower(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  46 */             return Boolean.valueOf(container.isShowLower());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  51 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  56 */             return "Show Lower";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  61 */             return "Show lower charts";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  66 */             return "Control: Quad Gantt Chart Container";
/*     */           }
/*     */         });
/*     */     
/*  70 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  74 */             return Optional.of(container.animatedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  79 */             container.setAnimated(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  84 */             return Boolean.valueOf(container.isAnimated());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  89 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  94 */             return "Animated";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  99 */             return "Open / close animations";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 104 */             return "Control: Quad Gantt Chart Container";
/*     */           }
/*     */         });
/*     */     
/* 108 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\QuadGanttChartContainerBaseItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */