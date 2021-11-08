/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.InnerLinesLayer;
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
/*     */ public class InnerLinesLayerItemProvider
/*     */   implements ItemProvider<InnerLinesLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final InnerLinesLayer layer) {
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
/*     */           
/*     */           public void setValue(Object value) {
/*  38 */             layer.setStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  43 */             return layer.getStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  48 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  53 */             return "Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  58 */             return "The color used for the inner lines";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  63 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/*  67 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  71 */             return Optional.of(layer.lineWidthProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  76 */             layer.setLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  81 */             return Double.valueOf(layer.getLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  86 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  91 */             return "Line Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  96 */             return "The width of the inner lines";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 101 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 105 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 109 */             return Optional.of(layer.drawLastDividerLineProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 114 */             layer.setDrawLastDividerLine(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 119 */             return Boolean.valueOf(layer.isDrawLastDividerLine());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 124 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 129 */             return "Draw Last Divider Line";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 134 */             return "Controls if a divider line is drawn for the last inner line";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 139 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 143 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\InnerLinesLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */