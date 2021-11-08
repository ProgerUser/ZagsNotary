/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.DSTLineLayer;
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
/*     */ 
/*     */ public class DSTLineLayerItemProvider
/*     */   implements ItemProvider<DSTLineLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final DSTLineLayer layer) {
/*  26 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/*  27 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*     */     
/*  29 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  33 */             return Optional.of(layer.strokeProperty());
/*     */           }
/*     */           
/*     */           public void setValue(Object value) {
/*  37 */             layer.setStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  42 */             return layer.getStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  47 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  52 */             return "Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  57 */             return "The color used for the DST line";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  62 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/*  66 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  70 */             return Optional.of(layer.lineWidthProperty());
/*     */           }
/*     */           
/*     */           public void setValue(Object value) {
/*  74 */             layer.setLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  79 */             return Double.valueOf(layer.getLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  84 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  89 */             return "Line Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  94 */             return "The width of the DST line";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  99 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 103 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\DSTLineLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */