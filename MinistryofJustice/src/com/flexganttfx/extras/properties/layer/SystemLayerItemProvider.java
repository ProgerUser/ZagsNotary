/*     */ package com.flexganttfx.extras.properties.layer;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.graphics.layer.SystemLayer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
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
/*     */ public class SystemLayerItemProvider
/*     */   implements ItemProvider<SystemLayer>
/*     */ {
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final SystemLayer layer) {
/*  25 */     List<PropertySheet.Item> items = new ArrayList<>();
/*     */     
/*  27 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  31 */             return Optional.of(layer.visibleProperty());
/*     */           }
/*     */           
/*     */           public void setValue(Object value) {
/*  35 */             layer.setVisible(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  40 */             return Boolean.valueOf(layer.isVisible());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  45 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  50 */             return "Visible";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  55 */             return "Controls visibility of the system layer.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  60 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/*  64 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  68 */             return Optional.of(layer.snapToPixelProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  73 */             layer.setSnapToPixel(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  78 */             return Boolean.valueOf(layer.isSnapToPixel());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  83 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  88 */             return "Snap To Pixel";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  93 */             return "Enables / disables the snap to pixel feature.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  98 */             return "System Layer: " + layer.getName();
/*     */           }
/*     */         });
/*     */     
/* 102 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\layer\SystemLayerItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */