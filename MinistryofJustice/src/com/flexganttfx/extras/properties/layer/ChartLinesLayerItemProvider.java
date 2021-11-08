/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.ChartLinesLayer;
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
/*     */ public class ChartLinesLayerItemProvider
/*     */   implements ItemProvider<ChartLinesLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final ChartLinesLayer layer) {
/*  26 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/*  27 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*     */     
/*  29 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  33 */             return Optional.of(layer.majorLinesVisibleProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  38 */             layer.setMajorLinesVisible(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  43 */             return Boolean.valueOf(layer.isMajorLinesVisible());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  48 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  53 */             return "Major Lines Visible";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  58 */             return "Determines if major chart lines will be shown.";
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
/*  71 */             return Optional.of(layer.majorLinesStrokeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  76 */             layer.setMajorLinesStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  81 */             return layer.getMajorLinesStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  86 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  91 */             return "Major Lines Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  96 */             return "The color used for the major chart lines.";
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
/* 109 */             return Optional.of(layer.majorLinesLineWidthProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 114 */             layer.setMajorLinesLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 119 */             return Double.valueOf(layer.getMajorLinesLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 124 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 129 */             return "Major Lines Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 134 */             return "The width of the major chart lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 139 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 143 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 147 */             return Optional.of(layer.minorLinesVisibleProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 152 */             layer.setMinorLinesVisible(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 157 */             return Boolean.valueOf(layer.isMinorLinesVisible());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 162 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 167 */             return "Minor Lines Visible";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 172 */             return "Determines if minor chart lines will be shown.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 177 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 181 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 185 */             return Optional.of(layer.minorLinesStrokeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 190 */             layer.setMinorLinesStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 195 */             return layer.getMinorLinesStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 200 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 205 */             return "Minor Lines Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 210 */             return "The color used for the major chart lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 215 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 219 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 223 */             return Optional.of(layer.minorLinesLineWidthProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 228 */             layer.setMinorLinesLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 233 */             return Double.valueOf(layer.getMinorLinesLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 238 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 243 */             return "Minor Lines Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 248 */             return "The width of the minor chart lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 253 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 257 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\ChartLinesLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */