/*    */ package com.flexganttfx.extras.properties.layer;
/*    */ 
/*    */ import com.flexganttfx.extras.properties.ItemProvider;
/*    */ import com.flexganttfx.view.graphics.layer.SystemLayer;
/*    */ import com.flexganttfx.view.graphics.layer.ZoomTimeIntervalLayer;
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
/*    */ 
/*    */ public class ZoomIntervalLayerItemProvider
/*    */   implements ItemProvider<ZoomTimeIntervalLayer>
/*    */ {
/*    */   public List<PropertySheet.Item> getPropertySheetItems(final ZoomTimeIntervalLayer layer) {
/* 26 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/* 27 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*    */     
/* 29 */     items.add(new PropertySheet.Item()
/*    */         {
/*    */           public Optional<ObservableValue<?>> getObservableValue()
/*    */           {
/* 33 */             return Optional.of(layer.zoomTimeIntervalFillProperty());
/*    */           }
/*    */ 
/*    */           
/*    */           public void setValue(Object value) {
/* 38 */             layer.setZoomTimeIntervalFill((Color)value);
/*    */           }
/*    */ 
/*    */           
/*    */           public Object getValue() {
/* 43 */             return layer.getZoomTimeIntervalFill();
/*    */           }
/*    */ 
/*    */           
/*    */           public Class<?> getType() {
/* 48 */             return Paint.class;
/*    */           }
/*    */ 
/*    */           
/*    */           public String getName() {
/* 53 */             return "Focused Time Fill";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getDescription() {
/* 58 */             return "The color used for visualizing the focused time interval of the dateline.";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getCategory() {
/* 63 */             return "System Layer: " + layer.getName();
/*    */           }
/*    */         });
/*    */     
/* 67 */     return items;
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\ZoomIntervalLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */