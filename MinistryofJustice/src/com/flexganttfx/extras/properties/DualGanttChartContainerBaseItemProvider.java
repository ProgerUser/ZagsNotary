/*    */ package com.flexganttfx.extras.properties;
/*    */ 
/*    */ import com.flexganttfx.view.GanttChartBase;
/*    */ import com.flexganttfx.view.container.DualGanttChartContainerBase;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.collections.FXCollections;
/*    */ import javafx.collections.ObservableList;
/*    */ import org.controlsfx.control.PropertySheet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DualGanttChartContainerBaseItemProvider<T extends GanttChartBase<?>>
/*    */   implements ItemProvider<DualGanttChartContainerBase<T>>
/*    */ {
/*    */   private static final String DUAL_GANTT_CHART_CONTAINER_PROPERTIES_CATEGORY = "Control: Dual Gantt Chart Container";
/*    */   
/*    */   public final ObservableList<PropertySheet.Item> getPropertySheetItems(final DualGanttChartContainerBase<T> container) {
/* 30 */     ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
/*    */     
/* 32 */     items.add(new PropertySheet.Item()
/*    */         {
/*    */           public Optional<ObservableValue<?>> getObservableValue()
/*    */           {
/* 36 */             return Optional.of(container.showSecondaryProperty());
/*    */           }
/*    */ 
/*    */           
/*    */           public void setValue(Object value) {
/* 41 */             container.setShowSecondary(((Boolean)value).booleanValue());
/*    */           }
/*    */ 
/*    */           
/*    */           public Object getValue() {
/* 46 */             return Boolean.valueOf(container.isShowSecondary());
/*    */           }
/*    */ 
/*    */           
/*    */           public Class<?> getType() {
/* 51 */             return Boolean.class;
/*    */           }
/*    */ 
/*    */           
/*    */           public String getName() {
/* 56 */             return "Show Secondary";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getDescription() {
/* 61 */             return "Show secondary chart";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getCategory() {
/* 66 */             return "Control: Dual Gantt Chart Container";
/*    */           }
/*    */         });
/*    */     
/* 70 */     return items;
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\DualGanttChartContainerBaseItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */