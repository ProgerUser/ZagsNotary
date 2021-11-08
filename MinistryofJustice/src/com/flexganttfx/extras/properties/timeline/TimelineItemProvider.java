/*     */ package com.flexganttfx.extras.properties.timeline;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.timeline.Timeline;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.util.Duration;
/*     */ import org.controlsfx.control.PropertySheet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimelineItemProvider
/*     */   implements ItemProvider<Timeline>
/*     */ {
/*     */   private static final String TIMELINE_PROPERTIES_CATEGORY = "Control: Timeline";
/*     */   
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final Timeline target) {
/*  25 */     List<PropertySheet.Item> items = new ArrayList<>();
/*     */     
/*  27 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  31 */             return Optional.of(target.zoomAnimatedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  36 */             target.setZoomAnimated(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  41 */             return Boolean.valueOf(target.isZoomAnimated());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  46 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  51 */             return "Animated Zoom";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  56 */             return "Zoom operations can be performed with or without animation.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  61 */             return "Control: Timeline";
/*     */           }
/*     */         });
/*     */     
/*  65 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  69 */             return Optional.of(target.zoomFactorProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  74 */             target.setZoomFactor(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  79 */             return Double.valueOf(target.getZoomFactor());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  84 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  89 */             return "Zoom Factor";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  94 */             return "The factor used for zooming in or out, default = .5";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  99 */             return "Control: Timeline";
/*     */           }
/*     */         });
/*     */     
/* 103 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 107 */             return Optional.of(target.zoomModeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 112 */             target.setZoomMode((Timeline.ZoomMode)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 117 */             return target.getZoomMode();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 122 */             return Timeline.ZoomMode.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 127 */             return "Zoom Mode";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 132 */             return "The method of zooming in (keep start, keep end, keep center time).";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 137 */             return "Control: Timeline";
/*     */           }
/*     */         });
/*     */     
/* 141 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 145 */             return Optional.of(target.zoomDurationProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 150 */             target.setZoomDuration((Duration)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 155 */             return target.getZoomDuration();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 160 */             return Duration.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 165 */             return "Zoom Duration";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 170 */             return "The duration of the zoom animation.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 175 */             return "Control: Timeline";
/*     */           }
/*     */         });
/*     */     
/* 179 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 183 */             return Optional.of(target.moveAnimatedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 188 */             target.setMoveAnimated(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 193 */             return Boolean.valueOf(target.isMoveAnimated());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 198 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 203 */             return "Animated Move";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 208 */             return "Move operations can be performed with or without animation.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 213 */             return "Control: Timeline";
/*     */           }
/*     */         });
/*     */     
/* 217 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 221 */             return Optional.of(target.moveDurationProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 226 */             target.setMoveDuration((Duration)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 231 */             return target.getMoveDuration();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 236 */             return Duration.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 241 */             return "Move Duration";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 246 */             return "The duration of the move animation.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 251 */             return "Control: Timeline";
/*     */           }
/*     */         });
/*     */     
/* 255 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\timeline\TimelineItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */