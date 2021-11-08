/*     */ package com.flexganttfx.extras.properties.renderer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.renderer.ActivityRenderer;
/*     */ import com.flexganttfx.view.graphics.renderer.Renderer;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.paint.Paint;
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
/*     */ 
/*     */ public class ActivityRendererItemProvider
/*     */   implements ItemProvider<ActivityRenderer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final ActivityRenderer renderer) {
/*  27 */     RendererItemProvider support = new RendererItemProvider();
/*  28 */     List<PropertySheet.Item> items = support.getPropertySheetItems((Renderer)renderer);
/*     */ 
/*     */ 
/*     */     
/*  32 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  36 */             return Optional.of(renderer.strokeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  41 */             renderer.setStroke((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  46 */             return renderer.getStroke();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  51 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  56 */             return "Stroke";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  61 */             return "The color used for drawing the activity border.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  66 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  72 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  76 */             return Optional.of(renderer.strokeHighlightProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  81 */             renderer.setStrokeHighlight((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  86 */             return renderer.getStrokeHighlight();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  91 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  96 */             return "Stroke Highlight";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 101 */             return "The color used for drawing the activity border when the activity is currently drawn highlighted.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 106 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 112 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 116 */             return Optional.of(renderer.strokeHoverProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 121 */             renderer.setStrokeHover((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 126 */             return renderer.getStrokeHover();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 131 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 136 */             return "Stroke Hover";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 141 */             return "The color used for filling the activity border when the mouse cursor hovers over the activity.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 146 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 152 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 156 */             return Optional.of(renderer.strokeSelectedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 161 */             renderer.setStrokeSelected((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 166 */             return renderer.getStrokeSelected();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 171 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 176 */             return "Stroke Selected";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 181 */             return "The color used for drawing the activity border when the activity is currently selected.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 186 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 192 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 196 */             return Optional.of(renderer.cornersRoundedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 201 */             renderer.setCornersRounded(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 206 */             return Boolean.valueOf(renderer.isCornersRounded());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 211 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 216 */             return "Corners Rounded";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 221 */             return "Determines if the corners of the activity will be drawn rounded or not.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 226 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 232 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 236 */             return Optional.of(renderer.cornerRadiusProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 241 */             renderer.setCornerRadius(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 246 */             return Double.valueOf(renderer.getCornerRadius());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 251 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 256 */             return "Corner Radius";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 261 */             return "The radius used for the activity corners when rounded corners are used.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 266 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 272 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 276 */             return Optional.of(renderer.lineWidthProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 281 */             renderer.setLineWidth(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 286 */             return Double.valueOf(renderer.getLineWidth());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 291 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 296 */             return "Line Width";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 301 */             return "The line width used for the border.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 306 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/* 310 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\renderer\ActivityRendererItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */