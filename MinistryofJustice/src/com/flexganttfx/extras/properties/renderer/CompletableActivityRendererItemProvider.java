/*     */ package com.flexganttfx.extras.properties.renderer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
/*     */ import com.flexganttfx.view.graphics.renderer.CompletableActivityRenderer;
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
/*     */ public class CompletableActivityRendererItemProvider
/*     */   implements ItemProvider<CompletableActivityRenderer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final CompletableActivityRenderer renderer) {
/*  25 */     ActivityBarRendererItemProvider support = new ActivityBarRendererItemProvider();
/*     */     
/*  27 */     List<PropertySheet.Item> list = support.getPropertySheetItems((ActivityBarRenderer)renderer);
/*     */     
/*  29 */     list.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  33 */             return Optional.of(renderer.fillCompletionProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  38 */             renderer.setFillCompletion((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  43 */             return renderer.getFillCompletion();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  48 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  53 */             return "Completion";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  58 */             return "The paint used for drawing that segment of the activity that represents the completed part.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  63 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/*  67 */     list.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  71 */             return Optional.of(renderer.fillCompletionHoverProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  76 */             renderer.setFillCompletionHover((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  81 */             return renderer.getFillCompletionHover();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  86 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  91 */             return "Completion Hover";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  96 */             return "The paint used for drawing that segment of the activity that represents the completed part.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 101 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/* 105 */     list.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 109 */             return Optional.of(renderer.fillCompletionPressedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 114 */             renderer.setFillCompletionPressed((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 119 */             return renderer.getFillCompletionPressed();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 124 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 129 */             return "Completion Pressed";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 134 */             return "The paint used for drawing that segment of the activity that represents the completed part.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 139 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/* 143 */     list.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 147 */             return Optional.of(renderer.fillCompletionSelectedProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 152 */             renderer.setFillCompletionSelected((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 157 */             return renderer.getFillCompletionSelected();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 162 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 167 */             return "Completion Selected";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 172 */             return "The paint used for drawing that segment of the activity that represents the completed part.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 177 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/*     */     
/* 181 */     list.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 185 */             return Optional.of(renderer.fillCompletionHighlightProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 190 */             renderer.setFillCompletionHighlight((Paint)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 195 */             return renderer.getFillCompletionHighlight();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 200 */             return Paint.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 205 */             return "Completion Highlight";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 210 */             return "The paint used for drawing that segment of the activity that represents the completed part.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 215 */             return "Renderer: " + renderer.getName();
/*     */           }
/*     */         });
/* 218 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\renderer\CompletableActivityRendererItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */