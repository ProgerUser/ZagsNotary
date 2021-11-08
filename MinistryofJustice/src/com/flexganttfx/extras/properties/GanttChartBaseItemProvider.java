/*     */ package com.flexganttfx.extras.properties;
/*     */ 
/*     */ import com.flexganttfx.extras.properties.timeline.DatelineItemProvider;
/*     */ import com.flexganttfx.extras.properties.timeline.EventlineItemProvider;
/*     */ import com.flexganttfx.extras.properties.timeline.TimelineItemProvider;
/*     */ import com.flexganttfx.model.Layer;
/*     */ import com.flexganttfx.model.Row;
/*     */ import com.flexganttfx.view.GanttChartBase;
/*     */ import com.flexganttfx.view.graphics.GraphicsBase;
/*     */ import com.flexganttfx.view.graphics.ListViewGraphics;
/*     */ import com.flexganttfx.view.timeline.Timeline;
/*     */ import com.flexganttfx.view.util.Position;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
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
/*     */ public class GanttChartBaseItemProvider<R extends Row<?, ?, ?>>
/*     */   implements ItemProvider<GanttChartBase<R>>
/*     */ {
/*     */   private static final String GANTT_CHART_BASE_PROPERTIES_CATEGORY = "Control: Gantt Chart Base";
/*     */   
/*     */   public List<PropertySheet.Item> getPropertySheetItems(final GanttChartBase<R> gc) {
/*  36 */     ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();
/*     */     
/*  38 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  42 */             return Optional.of(gc.fixedCellSizeProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  47 */             gc.setFixedCellSize(((Double)value).doubleValue());
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  52 */             return Double.valueOf(gc.getFixedCellSize());
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  57 */             return Double.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/*  62 */             return "Fixed Cell Size";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/*  67 */             return "Controls whether cells have a fixed or varying row height.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/*  72 */             return "Control: Gantt Chart Base";
/*     */           }
/*     */         });
/*     */     
/*  76 */     items.add(new PropertySheet.Item()
/*     */         {
/*     */           public Optional<ObservableValue<?>> getObservableValue()
/*     */           {
/*  80 */             return Optional.of(gc.positionProperty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(Object value) {
/*  85 */             gc.setPosition((Position)value);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getValue() {
/*  90 */             return gc.getPosition();
/*     */           }
/*     */ 
/*     */           
/*     */           public Class<?> getType() {
/*  95 */             return Position.class;
/*     */           }
/*     */ 
/*     */           
/*     */           public String getName() {
/* 100 */             return "Position";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getDescription() {
/* 105 */             return "The position of the Gantt chart within a dual / multi Gantt chart container.";
/*     */           }
/*     */ 
/*     */           
/*     */           public String getCategory() {
/* 110 */             return "Control: Gantt Chart Base";
/*     */           }
/*     */         });
/*     */     
/* 114 */     Timeline timeline = gc.getTimeline();
/* 115 */     TimelineItemProvider timelineItemProvider = new TimelineItemProvider();
/* 116 */     items.addAll(timelineItemProvider.getPropertySheetItems(timeline));
/*     */     
/* 118 */     DatelineItemProvider datelineItemProvider = new DatelineItemProvider();
/* 119 */     items.addAll(datelineItemProvider.getPropertySheetItems(timeline.getDateline()));
/*     */     
/* 121 */     EventlineItemProvider eventlineItemProvider = new EventlineItemProvider();
/* 122 */     items.addAll(eventlineItemProvider.getPropertySheetItems(timeline.getEventline()));
/*     */     
/* 124 */     ListViewGraphics<R> graphics = gc.getGraphics();
/* 125 */     GraphicsBaseItemProvider<Row<?, ?, ?>> graphicsBasePropertySheetSupport = new GraphicsBaseItemProvider<>();
/* 126 */     items.addAll(graphicsBasePropertySheetSupport.getPropertySheetItems((GraphicsBase<Row<?, ?, ?>>)graphics));
/*     */     
/* 128 */     for (Layer layer : graphics.getLayers()) {
/* 129 */       items.add(new PropertySheet.Item()
/*     */           {
/*     */             public Optional<ObservableValue<?>> getObservableValue()
/*     */             {
/* 133 */               return Optional.of(layer.visibleProperty());
/*     */             }
/*     */ 
/*     */             
/*     */             public void setValue(Object value) {
/* 138 */               layer.setVisible(((Boolean)value).booleanValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getValue() {
/* 143 */               return Boolean.valueOf(layer.isVisible());
/*     */             }
/*     */ 
/*     */             
/*     */             public Class<?> getType() {
/* 148 */               return Boolean.class;
/*     */             }
/*     */ 
/*     */             
/*     */             public String getName() {
/* 153 */               return "Visible";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getDescription() {
/* 158 */               return "Show / hide the model layer (its activities)";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getCategory() {
/* 163 */               return "Model Layer: " + layer.getName();
/*     */             }
/*     */           });
/*     */       
/* 167 */       items.add(new PropertySheet.Item()
/*     */           {
/*     */             public Optional<ObservableValue<?>> getObservableValue()
/*     */             {
/* 171 */               return Optional.of(layer.deletableProperty());
/*     */             }
/*     */ 
/*     */             
/*     */             public void setValue(Object value) {
/* 176 */               layer.setDeletable(((Boolean)value).booleanValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getValue() {
/* 181 */               return Boolean.valueOf(layer.isDeletable());
/*     */             }
/*     */ 
/*     */             
/*     */             public Class<?> getType() {
/* 186 */               return Boolean.class;
/*     */             }
/*     */ 
/*     */             
/*     */             public String getName() {
/* 191 */               return "Deletable";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getDescription() {
/* 196 */               return "Determines if the layer can be deleted by the user.";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getCategory() {
/* 201 */               return "Model Layer: " + layer.getName();
/*     */             }
/*     */           });
/*     */       
/* 205 */       items.add(new PropertySheet.Item()
/*     */           {
/*     */             public Optional<ObservableValue<?>> getObservableValue()
/*     */             {
/* 209 */               return Optional.of(layer.nameProperty());
/*     */             }
/*     */ 
/*     */             
/*     */             public void setValue(Object value) {
/* 214 */               layer.setName((String)value);
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getValue() {
/* 219 */               return layer.getName();
/*     */             }
/*     */ 
/*     */             
/*     */             public Class<?> getType() {
/* 224 */               return String.class;
/*     */             }
/*     */ 
/*     */             
/*     */             public String getName() {
/* 229 */               return "Name";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getDescription() {
/* 234 */               return "The name of the model layer";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getCategory() {
/* 239 */               return "Model Layer: " + layer.getName();
/*     */             }
/*     */           });
/*     */       
/* 243 */       items.add(new PropertySheet.Item()
/*     */           {
/*     */             public Optional<ObservableValue<?>> getObservableValue()
/*     */             {
/* 247 */               return Optional.of(layer.opacityProperty());
/*     */             }
/*     */ 
/*     */             
/*     */             public void setValue(Object value) {
/* 252 */               layer.setOpacity(((Double)value).doubleValue());
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getValue() {
/* 257 */               return Double.valueOf(layer.getOpacity());
/*     */             }
/*     */ 
/*     */             
/*     */             public Class<?> getType() {
/* 262 */               return Double.class;
/*     */             }
/*     */ 
/*     */             
/*     */             public String getName() {
/* 267 */               return "Opacity";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getDescription() {
/* 272 */               return "Layer opacity / transparency.";
/*     */             }
/*     */ 
/*     */             
/*     */             public String getCategory() {
/* 277 */               return "Model Layer: " + layer.getName();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 282 */     return items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\GanttChartBaseItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */