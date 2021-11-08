/*     */ package com.flexganttfx.extras.properties.renderer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.renderer.Renderer;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
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
/*     */ public class RendererItemProvider
/*     */   implements ItemProvider<Renderer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final Renderer renderer) {
/*  28 */     ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
/*     */ 
/*     */ 
/*     */     
/*  32 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  36 */             return Optional.of(renderer.enabledProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  41 */             renderer.setEnabled(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  46 */             return Boolean.valueOf(renderer.isEnabled());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  51 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  56 */             return "Enabled";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  61 */             return "Enables / disables the renderer (if disabled activities using this renderer will not be shown at all).";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  66 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/*  70 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  74 */             return Optional.of(renderer.snapToPixelProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  79 */             renderer.setSnapToPixel(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  84 */             return Boolean.valueOf(renderer.isSnapToPixel());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  89 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  94 */             return "Snap To Pixel";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  99 */             return "Enables / disables the snap to pixel feature.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 104 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 109 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 113 */             return Optional.of(renderer.paddingProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 118 */             renderer.setPadding((Insets)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 123 */             return renderer.getPadding();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 128 */             return Insets.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 133 */             return "Padding";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 138 */             return "Specifies a padding to be applied (not applicable for all renderers).";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 143 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 149 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 153 */             return Optional.of(renderer.fillProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 158 */             renderer.setFill((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 163 */             return renderer.getFill();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 168 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 173 */             return "Fill";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 178 */             return "The color used for filling the activity background.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 183 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 189 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 193 */             return Optional.of(renderer.fillHighlightProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 198 */             renderer.setFillHighlight((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 203 */             return renderer.getFillHighlight();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 208 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 213 */             return "Fill Highlight";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 218 */             return "The color used for filling the activity background when the activity is currently drawn highlighted.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 223 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 229 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 233 */             return Optional.of(renderer.fillHoverProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 238 */             renderer.setFillHover((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 243 */             return renderer.getFillHover();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 248 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 253 */             return "Fill Hover";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 258 */             return "The color used for filling the activity background when the mouse cursor hovers over the activity.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 263 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 269 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 273 */             return Optional.of(renderer.fillSelectedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 278 */             renderer.setFillSelected((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 283 */             return renderer.getFillSelected();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 288 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 293 */             return "Fill Selected";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 298 */             return "The color used for filling the activity background when the activity is selected.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 303 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 309 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 313 */             return Optional.of(renderer.fillPressedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 318 */             renderer.setFillPressed((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 323 */             return renderer.getFillPressed();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 328 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 333 */             return "Fill Pressed";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 338 */             return "The color used for filling the activity background when the user presses on it.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 343 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 349 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 353 */             return Optional.of(renderer.alphaProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 358 */             renderer.setAlpha(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 363 */             return Double.valueOf(renderer.getAlpha());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 368 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 373 */             return "Opacity / Alpha";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 378 */             return "The alpha value used when drawing the activity (opaque, transparent).";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 383 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/* 387 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\renderer\RendererItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */