/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.NowLineLayer;
/*     */ import com.flexganttfx.view.graphics.layer.SystemLayer;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.paint.Color;
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
/*     */ public class NowLineLayerItemProvider
/*     */   implements ItemProvider<NowLineLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final NowLineLayer layer) {
/*  25 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/*  26 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*     */     
/*  28 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  32 */             return Optional.of(layer.strokeProperty());
/*     */           }
/*     */           
/*     */           public void setValue(Object value) {
/*  36 */             layer.setStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  41 */             return layer.getStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  46 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  51 */             return "Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  56 */             return "The color used for the now line";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  61 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/*  65 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  69 */             return Optional.of(layer.lineWidthProperty());
/*     */           }
/*     */           
/*     */           public void setValue(Object value) {
/*  73 */             layer.setLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  78 */             return Double.valueOf(layer.getLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  83 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  88 */             return "Line Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  93 */             return "The width of the now line";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  98 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 102 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\NowLineLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */