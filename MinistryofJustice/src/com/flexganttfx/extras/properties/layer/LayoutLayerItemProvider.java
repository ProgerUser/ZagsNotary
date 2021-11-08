/*    */ package com.flexganttfx.extras.properties.layer;
/*    */ 
/*    */ import com.flexganttfx.extras.properties.ItemProvider;
/*    */ import com.flexganttfx.view.graphics.layer.LayoutLayer;
/*    */ import com.flexganttfx.view.graphics.layer.SystemLayer;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.scene.paint.Color;
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
/*    */ public class LayoutLayerItemProvider
/*    */   implements ItemProvider<LayoutLayer>
/*    */ {
/*    */   public List<PropertySheet.Item> getPropertySheetItems(final LayoutLayer layer) {
/* 25 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/* 26 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*    */     
/* 28 */     items.add(new PropertySheet.Item()
/*    */         {
/*    */           public Optional<ObservableValue<?>> getObservableValue()
/*    */           {
/* 32 */             return Optional.of(layer.paddingFillProperty());
/*    */           }
/*    */           
/*    */           public void setValue(Object value) {
/* 36 */             layer.setPaddingFill((Color)value);
/*    */           }
/*    */ 
/*    */           
/*    */           public Object getValue() {
/* 41 */             return layer.getPaddingFill();
/*    */           }
/*    */ 
/*    */           
/*    */           public Class<?> getType() {
/* 46 */             return Color.class;
/*    */           }
/*    */ 
/*    */           
/*    */           public String getName() {
/* 51 */             return "Padding Fill";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getDescription() {
/* 56 */             return "The color used for filling the background of the padding area.";
/*    */           }
/*    */ 
/*    */           
/*    */           public String getCategory() {
/* 61 */             return "System Layer: " + layer.getName();
/*    */           }
/*    */         });
/*    */     
/* 65 */     return items;
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\LayoutLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */