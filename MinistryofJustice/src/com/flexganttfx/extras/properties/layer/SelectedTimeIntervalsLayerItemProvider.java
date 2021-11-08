/*    */ package com.flexganttfx.extras.properties.layer;
/*    */ 
/*    */ import com.flexganttfx.extras.properties.ItemProvider;
/*    */ import com.flexganttfx.view.graphics.layer.SelectedTimeIntervalsLayer;
/*    */ import com.flexganttfx.view.graphics.layer.SystemLayer;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.paint.Paint;
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
/*    */ public class SelectedTimeIntervalsLayerItemProvider
/*    */   implements ItemProvider<SelectedTimeIntervalsLayer>
/*    */ {
/*    */   public List<PropertySheet.Item> getPropertySheetItems(final SelectedTimeIntervalsLayer layer) {
/* 25 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/* 26 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*    */     
/* 28 */     items.add(new PropertySheet.Item()
/*    */         {
/*    */           public Optional<ObservableValue<?>> getObservableValue()
/*    */           {
/* 32 */             return Optional.of(layer.selectedTimeIntervalFillProperty());
/*    */           }
/*    */ 
/*    */           
/*    */           public void setValue(Object value) {
/* 37 */             layer.setSelectedTimeIntervalFill((Color)value);
/*    */           }
/*    */ 
/*    */           
/*    */           public Object getValue() {
/* 42 */             return layer.getSelectedTimeIntervalFill();
/*    */           }
/*    */ 
/*    */           
/*    */           public Class<?> getType() {
/* 47 */             return Paint.class;
/*    */           }
/*    */ 
/*    */           
/*    */           public String getName() {
/* 52 */             return "Selected Time Fill";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getDescription() {
/* 57 */             return "The color used for visualizing the selected time intervals of the dateline.";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getCategory() {
/* 62 */             return "System Layer: " + layer.getName();
/*    */           }
/*    */         });
/*    */     
/* 66 */     return items;
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\SelectedTimeIntervalsLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */