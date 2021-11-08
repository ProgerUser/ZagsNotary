/*     */ package com.flexganttfx.extras.properties.timeline;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.timeline.Eventline;
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
/*     */ public class EventlineItemProvider
/*     */   implements ItemProvider<Eventline>
/*     */ {
/*     */   private static final String EVENTLINE_PROPERTIES_CATEGORY = "Control: Eventline";
/*     */   
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final Eventline target) {
/*  23 */     List<PropertySheet.Item> items = new ArrayList<>();
/*     */     
/*  25 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  29 */             return Optional.of(target.showTimeCursorProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  34 */             target.setShowTimeCursor(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  39 */             return Boolean.valueOf(target.isShowTimeCursor());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  44 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  49 */             return "Show Time Cursor";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  54 */             return "Enables / disables the display of the time cursor.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  59 */             return "Control: Eventline";
/*     */           }
/*     */         });
/*     */     
/*  63 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  67 */             return Optional.of(target.showMarkedTimeIntervalProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  72 */             target.setShowMarkedTimeInterval(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  77 */             return Boolean.valueOf(target.isShowMarkedTimeInterval());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  82 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  87 */             return "Show Marked Time Intervals";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  92 */             return "Enables / disables the display of a marked time interval.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  97 */             return "Control: Eventline";
/*     */           }
/*     */         });
/*     */     
/* 101 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\timeline\EventlineItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */