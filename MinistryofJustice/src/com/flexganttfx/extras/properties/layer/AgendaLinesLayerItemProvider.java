/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.AgendaLinesLayer;
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
/*     */ public class AgendaLinesLayerItemProvider
/*     */   implements ItemProvider<AgendaLinesLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final AgendaLinesLayer layer) {
/*  24 */     SystemLayerItemProvider provider = new SystemLayerItemProvider();
/*  25 */     List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer)layer);
/*     */     
/*  27 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  31 */             return Optional.of(layer.majorLinesVisibleProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  36 */             layer.setMajorLinesVisible(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  41 */             return Boolean.valueOf(layer.isMajorLinesVisible());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  46 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  51 */             return "Major Lines Visible";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  56 */             return "Determines if major agenda lines will be shown.";
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
/*  69 */             return Optional.of(layer.majorLinesStrokeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  74 */             layer.setMajorLinesStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  79 */             return layer.getMajorLinesStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  84 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  89 */             return "Major Lines Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  94 */             return "The color used for the major agenda lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  99 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 103 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 107 */             return Optional.of(layer.majorLinesLineWidthProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 112 */             layer.setMajorLinesLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 117 */             return Double.valueOf(layer.getMajorLinesLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 122 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 127 */             return "Major Lines Line Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 132 */             return "The width of the major agenda lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 137 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 141 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 145 */             return Optional.of(layer.minorLinesVisibleProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 150 */             layer.setMinorLinesVisible(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 155 */             return Boolean.valueOf(layer.isMinorLinesVisible());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 160 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 165 */             return "Minor Lines Visible";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 170 */             return "Determines if minor agenda lines will be shown.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 175 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 179 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 183 */             return Optional.of(layer.minorLinesStrokeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 188 */             layer.setMinorLinesStroke((Color)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 193 */             return layer.getMinorLinesStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 198 */             return Color.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 203 */             return "Minor Lines Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 208 */             return "The color used for the major agenda lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 213 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 217 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 221 */             return Optional.of(layer.minorLinesLineWidthProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 226 */             layer.setMinorLinesLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 231 */             return Double.valueOf(layer.getMinorLinesLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 236 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 241 */             return "Minor Lines Line Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 246 */             return "The width of the minor agenda lines.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 251 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/* 254 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\AgendaLinesLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */