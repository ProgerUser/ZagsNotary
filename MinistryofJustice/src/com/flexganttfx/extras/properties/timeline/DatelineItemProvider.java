/*     */ package com.flexganttfx.extras.properties.timeline;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.ItemProvider;
/*     */ import com.flexganttfx.view.timeline.Dateline;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.ZoneId;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.control.SelectionMode;
/*     */ import org.controlsfx.control.PropertySheet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatelineItemProvider
/*     */   implements ItemProvider<Dateline>
/*     */ {
/*     */   private static final String DATELINE_PROPERTIES_CATEGORY = "Control: Dateline";
/*     */   
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final Dateline target) {
/*  26 */     List<PropertySheet.Item> items = new ArrayList<>();
/*     */     
/*  28 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  32 */             return Optional.of(target.datelineBufferProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  37 */             target.setDatelineBuffer(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  42 */             return Double.valueOf(target.getDatelineBuffer());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  47 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  52 */             return "Dateline Buffer";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  57 */             return "Increases the dateline width to reduce redraws";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  62 */             return "Control: Dateline";
/*     */           }
/*     */         });
/*     */     
/*  66 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  70 */             return Optional.of(target.selectionModeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  75 */             target.setSelectionMode((SelectionMode)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  80 */             return target.getSelectionMode();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  85 */             return SelectionMode.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  90 */             return "Selection Mode";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  95 */             return "Single or multiple selections of dateline cells / time intervals.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 100 */             return "Control: Dateline";
/*     */           }
/*     */         });
/*     */     
/* 104 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 108 */             return Optional.of(target.zoneIdProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 113 */             target.setZoneId((ZoneId)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 118 */             return target.getZoneId();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 123 */             return ZoneId.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 128 */             return "Timezone";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 133 */             return "The timezone that will be displayed by the dateline.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 138 */             return "Control: Dateline";
/*     */           }
/*     */         });
/*     */     
/* 142 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 146 */             return Optional.of(target.firstDayOfWeekProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 151 */             target.setFirstDayOfWeek((DayOfWeek)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 156 */             return target.getFirstDayOfWeek();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 161 */             return DayOfWeek.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 166 */             return "First Day of Week";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 171 */             return "The day representing the beginning of the week.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 176 */             return "Control: Dateline";
/*     */           }
/*     */         });
/*     */     
/* 180 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/* 184 */             return Optional.of(target.zoomLassoEnabledProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/* 189 */             target.setZoomLassoEnabled(((Boolean)value).booleanValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/* 194 */             return Boolean.valueOf(target.isZoomLassoEnabled());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/* 199 */             return Boolean.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 204 */             return "Support Zoom Lasso";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 209 */             return "If enabled the user can perform a zoom by selecting a time range with a lasso.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 214 */             return "Control: Dateline";
/*     */           }
/*     */         });
/*     */     
/* 218 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\timeline\DatelineItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */