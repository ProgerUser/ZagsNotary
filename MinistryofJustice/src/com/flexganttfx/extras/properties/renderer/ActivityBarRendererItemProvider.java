/*     */ package com.flexganttfx.extras.properties.renderer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
/*     */ import com.flexganttfx.view.graphics.renderer.ActivityRenderer;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.text.Font;
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
/*     */ public class ActivityBarRendererItemProvider
/*     */   implements ItemProvider<ActivityBarRenderer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final ActivityBarRenderer renderer) {
/*  26 */     ActivityRendererItemProvider support = new ActivityRendererItemProvider();
/*  27 */     List<PropertySheet.Item> items = support.getPropertySheetItems((ActivityRenderer)renderer);
/*     */ 
/*     */ 
/*     */     
/*  31 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  35 */             return Optional.of(renderer.textFillProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  40 */             renderer.setTextFill((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  45 */             return renderer.getTextFill();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  50 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  55 */             return "Text Fill";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  60 */             return "The color used for the text.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  65 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  71 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  75 */             return Optional.of(renderer.textFillSelectedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  80 */             renderer.setTextFillSelected((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  85 */             return renderer.getTextFillSelected();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  90 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  95 */             return "Text Fill Selected";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 100 */             return "The color used for the text when the activity is selected.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 105 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 111 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 115 */             return Optional.of(renderer.textFillHighlightProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 120 */             renderer.setTextFillHover((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 125 */             return renderer.getTextFillHover();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 130 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 135 */             return "Text Fill Hover";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 140 */             return "The color used for the text when the mouse cursor hovers over the activity.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 145 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 151 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 155 */             return Optional.of(renderer.textFillHighlightProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 160 */             renderer.setTextFillHighlight((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 165 */             return renderer.getTextFillHighlight();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 170 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 175 */             return "Text Fill Highlight";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 180 */             return "The color used for the text when the activity is highlighted / blinking.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 185 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 191 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 195 */             return Optional.of(renderer.textFillPressedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 200 */             renderer.setTextFillPressed((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 205 */             return renderer.getTextFillPressed();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 210 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 215 */             return "Text Fill Pressed";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 220 */             return "The color used for the text when the activity is pressed.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 225 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 231 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 235 */             return Optional.of(renderer.textGapProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 240 */             renderer.setTextGap(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 245 */             return Double.valueOf(renderer.getTextGap());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 250 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 255 */             return "Text Gap";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 260 */             return "The gap between the activity and the text.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 265 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 271 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 275 */             return Optional.of(renderer.glossyProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 280 */             renderer.setGlossy(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 285 */             return Boolean.valueOf(renderer.isGlossy());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 290 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 295 */             return "Glossy";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 300 */             return "Shows / hides a glossy effect on the activity.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 305 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 311 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 315 */             return Optional.of(renderer.autoFixTextProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 320 */             renderer.setAutoFixText(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 325 */             return Boolean.valueOf(renderer.isAutoFixText());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 330 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 335 */             return "Fix Text";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 340 */             return "Controls if text stays within the viewport as long as possible.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 345 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 351 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 355 */             return Optional.of(renderer.barHeightProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 360 */             renderer.setBarHeight(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 365 */             return Double.valueOf(renderer.getBarHeight());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 370 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 375 */             return "Bar Height";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 380 */             return "The size of the bar representing an activity.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 385 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 391 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 395 */             return Optional.of(renderer.fontProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 400 */             renderer.setFont((Font)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 405 */             return renderer.getFont();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 410 */             return Font.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 415 */             return "Font";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 420 */             return "The font used for any text.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 425 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/* 429 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\renderer\ActivityBarRendererItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */