/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.GridLinesLayer;
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
/*     */ public class GridLinesLayerItemProvider
/*     */   implements ItemProvider<GridLinesLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final GridLinesLayer layer) {
/*  26 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/*  27 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*     */     
/*  29 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  33 */             return Optional.of(layer.lineStroke1Property());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  38 */             layer.setLineStroke1((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  43 */             return layer.getLineStroke1();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  48 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  53 */             return "Line Stroke 1";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  58 */             return "The color used for the first grid line level";
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
/*  71 */             return Optional.of(layer.lineWidth1Property());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  76 */             layer.setLineWidth1(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  81 */             return Double.valueOf(layer.getLineWidth1());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  86 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  91 */             return "Line Width 1";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  96 */             return "The line width used for the first grid line level";
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
/* 109 */             return Optional.of(layer.lineStroke2Property());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 114 */             layer.setLineStroke2((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 119 */             return layer.getLineStroke2();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 124 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 129 */             return "Line Stroke 2";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 134 */             return "The color used for the first grid line level";
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
/* 147 */             return Optional.of(layer.lineWidth2Property());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 152 */             layer.setLineWidth2(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 157 */             return Double.valueOf(layer.getLineWidth2());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 162 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 167 */             return "Line Width 2";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 172 */             return "The line width used for the second grid line level";
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
/* 185 */             return Optional.of(layer.lineStroke3Property());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 190 */             layer.setLineStroke3((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 195 */             return layer.getLineStroke3();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 200 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 205 */             return "Line Stroke 3";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 210 */             return "The color used for the third grid line level";
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
/* 223 */             return Optional.of(layer.lineWidth3Property());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 228 */             layer.setLineWidth3(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 233 */             return Double.valueOf(layer.getLineWidth3());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 238 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 243 */             return "Line Width 3";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 248 */             return "The line width used for the third grid line level";
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


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\GridLinesLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */