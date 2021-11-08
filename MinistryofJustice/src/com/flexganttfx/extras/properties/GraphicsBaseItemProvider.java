/*      */ package com.flexganttfx.extras.properties;
/*      */ 
/*      */ import com.flexganttfx.model.Row;
/*      */ import com.flexganttfx.model.dateline.VirtualGrid;
/*      */ import com.flexganttfx.view.graphics.GraphicsBase;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Optional;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import org.controlsfx.control.PropertySheet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GraphicsBaseItemProvider<R extends Row<?, ?, ?>>
/*      */   implements ItemProvider<GraphicsBase<R>>
/*      */ {
/*      */   private static final String GRAPHICS_VIEW_PROPERTIES = "Control: Graphics";
/*      */   
/*      */   public List<PropertySheet.Item> getPropertySheetItems(final GraphicsBase<R> graphics) {
/*   30 */     List<PropertySheet.Item> items = new ArrayList<>();
/*      */     
/*   32 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*   36 */             return Optional.of(graphics.showRowHeadersProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*   41 */             graphics.setShowRowHeaders(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*   46 */             return Boolean.valueOf(graphics.isShowRowHeaders());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*   51 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*   56 */             return "Show Row Headers";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*   61 */             return "Show / hide row headers";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*   66 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*   70 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*   74 */             return Optional.of(graphics.rowHeadersWidthProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*   79 */             graphics.setRowHeadersWidth(((Double)value).doubleValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*   84 */             return Double.valueOf(graphics.getRowHeadersWidth());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*   89 */             return Double.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*   94 */             return "Row Header Width";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*   99 */             return "The width of the row headers";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  104 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  108 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  112 */             return Optional.of(graphics.canvasBufferProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  117 */             graphics.setCanvasBuffer(((Double)value).doubleValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  122 */             return Double.valueOf(graphics.getCanvasBuffer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  127 */             return Double.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  132 */             return "Canvas Buffer";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  137 */             return "Increases the canvas widths to reduce redraws";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  142 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  146 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  150 */             return Optional.of(graphics.safeRenderingProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  155 */             graphics.setSafeRendering(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  160 */             return Boolean.valueOf(graphics.isSafeRendering());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  165 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  170 */             return "Safe Rendering";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  175 */             return "Enables / disables safe rendering mode.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  180 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  184 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  188 */             return Optional.of(graphics.showZoneIdProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  193 */             graphics.setShowZoneId(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  198 */             return Boolean.valueOf(graphics.isShowZoneId());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  203 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  208 */             return "Show Zone Id";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  213 */             return "Shows / hides the zone IDs used by the rows.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  218 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  222 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  226 */             return Optional.of(graphics.rowEditingModeProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  231 */             graphics.setRowEditingMode((GraphicsBase.RowEditingMode)value);
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  236 */             return graphics.getRowEditingMode();
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  241 */             return GraphicsBase.RowEditingMode.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  246 */             return "Row Editing Mode";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  251 */             return "Single or multi row editing.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  256 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  260 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  264 */             return Optional.of(graphics.animateRowEditorProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  269 */             graphics.setAnimateRowEditor(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  274 */             return Boolean.valueOf(graphics.isAnimateRowEditor());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  279 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  284 */             return "Animate Row Editor";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  289 */             return "Use flip animation to show / hide the row editor.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  294 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  298 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  302 */             return Optional.of(graphics.showVerticalCursorProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  307 */             graphics.setShowVerticalCursor(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  312 */             return Boolean.valueOf(graphics.isShowVerticalCursor());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  317 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  322 */             return "Show Vertical Cursor";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  327 */             return "Enables / disables the vertical cursor line.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  332 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  336 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  340 */             return Optional.of(graphics.showHorizontalCursorProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  345 */             graphics.setShowHorizontalCursor(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  350 */             return Boolean.valueOf(graphics.isShowHorizontalCursor());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  355 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  360 */             return "Show Horizontal Cursor";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  365 */             return "Enables / disables the horizontal cursor line.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  370 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  374 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  378 */             return Optional.of(graphics.maxGridLevelProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  383 */             graphics.setMaxGridLevel(((Integer)value).intValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  388 */             return Integer.valueOf(graphics.getMaxGridLevel());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  393 */             return Integer.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  398 */             return "Max Grid Level";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  403 */             return "Determines the number of grid levels shown by the grid layer.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  408 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  412 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  416 */             return Optional.of(graphics.virtualGridProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  421 */             graphics.setVirtualGrid((VirtualGrid)value);
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  426 */             return graphics.getVirtualGrid();
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  431 */             return VirtualGrid.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  436 */             return "Virtual Grid";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  441 */             return "Sets a virtual grid.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  446 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  450 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  454 */             return Optional.of(graphics.fadeInOutVisibilityChangesProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  459 */             graphics.setFadeInOutVisibilityChanges(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  464 */             return Boolean.valueOf(graphics.isFadeInOutVisibilityChanges());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  469 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  474 */             return "Fade in/out";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  479 */             return "Controls whether visibility changes will be animated with a quick fade in/out.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  484 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  488 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  492 */             return 
/*  493 */               Optional.of(graphics.fadeInOutVisibilityChangesDurationProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  498 */             graphics.setFadeInOutVisibilityChangesDuration(((Double)value).doubleValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  503 */             return Double.valueOf(graphics.getFadeInOutVisibilityChangesDuration());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  508 */             return Double.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  513 */             return "Fade in/out duration";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  518 */             return "The duration in millis of the visibility change animation.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  523 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  527 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  531 */             return Optional.of(graphics.selectionModeProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  536 */             graphics.setSelectionMode((GraphicsBase.SelectionMode)value);
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  541 */             return graphics.getSelectionMode();
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  546 */             return GraphicsBase.SelectionMode.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  551 */             return "Selection Mode";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  556 */             return "Sets a selection mode (single, multiple, none).";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  561 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  565 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  569 */             return Optional.of(graphics.debugModeProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  574 */             graphics.setDebugMode(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  579 */             return Boolean.valueOf(graphics.isDebugMode());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  584 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  589 */             return "Debug Mode";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  594 */             return "Adds debug information to the rendered activities (bounds).";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  599 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  603 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  607 */             return Optional.of(graphics.lassoSelectionBehaviourProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  612 */             graphics.setLassoSelectionBehaviour((GraphicsBase.LassoSelectionBehaviour)value);
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  617 */             return graphics.getLassoSelectionBehaviour();
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  622 */             return GraphicsBase.LassoSelectionBehaviour.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  627 */             return "Selection Behaviour";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  632 */             return "Controls whether activities are selected by the lasso.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  637 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  641 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  645 */             return Optional.of(graphics.highlightDelayProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  650 */             graphics.setHighlightDelay(((Long)value).longValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  655 */             return Long.valueOf(graphics.getHighlightDelay());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  660 */             return Long.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  665 */             return "Highlight Delay";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  670 */             return "Controls the blinking speed of highlighted activities.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  675 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  679 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  683 */             return Optional.of(graphics.showHoverTimeIntervalLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  688 */             graphics.setShowHoverTimeIntervalLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  693 */             return Boolean.valueOf(graphics.isShowHoverTimeIntervalLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  698 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  703 */             return "Show Hover Interval";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  708 */             return "Hide or show the time interval over which the mouse cursor hovers in the dateline.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  713 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  717 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  721 */             return Optional.of(graphics.showSelectedTimeIntervalsLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  726 */             graphics.setShowSelectedTimeIntervalsLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  731 */             return Boolean.valueOf(graphics.isShowSelectedTimeIntervalsLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  736 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  741 */             return "Show Selected Intervals";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  746 */             return "Hide or show the time interval selections made in the dateline.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  751 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  755 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  759 */             return Optional.of(graphics.showGridLineLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  764 */             graphics.setShowGridLineLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  769 */             return Boolean.valueOf(graphics.isShowGridLineLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  774 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  779 */             return "Show Grid Lines";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  784 */             return "Enables / disables grid lines";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  789 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  793 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  797 */             return Optional.of(graphics.showInnerLinesLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  802 */             graphics.setShowInnerLinesLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  807 */             return Boolean.valueOf(graphics.isShowInnerLinesLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  812 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  817 */             return "Show Inner Lines";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  822 */             return "Enables / disables display of divider lines for inner lines";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  827 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  831 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  835 */             return Optional.of(graphics.showAgendaLinesLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  840 */             graphics.setShowAgendaLinesLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  845 */             return Boolean.valueOf(graphics.isShowAgendaLinesLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  850 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  855 */             return "Show Agenda Lines";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  860 */             return "Enables / disables display of agenda lines (hours, minutes)";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  865 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  869 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  873 */             return Optional.of(graphics.showNowLineLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  878 */             graphics.setShowNowLineLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  883 */             return Boolean.valueOf(graphics.isShowNowLineLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  888 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  893 */             return "Show Now Line";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  898 */             return "Enables / disables display of now line";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  903 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  907 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  911 */             return Optional.of(graphics.showDSTLineLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  916 */             graphics.setShowDSTLineLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  921 */             return Boolean.valueOf(graphics.isShowDSTLineLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  926 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  931 */             return "Show DST Line";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  936 */             return "Enables / disables display of DST line";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  941 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  945 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  949 */             return Optional.of(graphics.showRowLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  954 */             graphics.setShowRowLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  959 */             return Boolean.valueOf(graphics.isShowRowLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/*  964 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/*  969 */             return "Show Row Backgrounds";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/*  974 */             return "Enables / disables display of individual row backgrounds";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/*  979 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/*  983 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/*  987 */             return Optional.of(graphics.showChartLinesLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/*  992 */             graphics.setShowChartLinesLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/*  997 */             return Boolean.valueOf(graphics.isShowChartLinesLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1002 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1007 */             return "Show Chart Lines";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1012 */             return "Enables / disables display of chart lines.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1017 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1021 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1025 */             return Optional.of(graphics.showCalendarLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1030 */             graphics.setShowCalendarLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1035 */             return Boolean.valueOf(graphics.isShowCalendarLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1040 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1045 */             return "Show Calendars";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1050 */             return "Enables / disables display of calendars";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1055 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1059 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1063 */             return Optional.of(graphics.showLayoutLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1068 */             graphics.setShowLayoutLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1073 */             return Boolean.valueOf(graphics.isShowLayoutLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1078 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1083 */             return "Show Layout Decoration";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1088 */             return "Enables / disables display of layout specific decorations.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1093 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1097 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1101 */             return Optional.of(graphics.showZoomTimeIntervalLayerProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1106 */             graphics.setShowZoomTimeIntervalLayer(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1111 */             return Boolean.valueOf(graphics.isShowZoomTimeIntervalLayer());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1116 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1121 */             return "Show Zoom Interval";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1126 */             return "Hide or show the time interval when zooming inside the dateline.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1131 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1135 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1139 */             return Optional.of(graphics.showMarkedTimeIntervalProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1144 */             graphics.setShowMarkedTimeInterval(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1149 */             return Boolean.valueOf(graphics.isShowMarkedTimeInterval());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1154 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1159 */             return "Show Marked Interval";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1164 */             return "Hide or show the time interval currently marked (e.g. while dragging).";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1169 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1173 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1177 */             return Optional.of(graphics.dragAndDropFeedbackProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1182 */             graphics.setDragAndDropFeedback((GraphicsBase.DragAndDropFeedback)value);
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1187 */             return graphics.getDragAndDropFeedback();
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1192 */             return GraphicsBase.DragAndDropFeedback.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1197 */             return "Drag & Drop Feedback";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1202 */             return "Control visual drag feedback";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1207 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1211 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1215 */             return Optional.of(graphics.autoGridEnabledProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1220 */             graphics.setAutoGridEnabled(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1225 */             return Boolean.valueOf(graphics.isAutoGridEnabled());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1230 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1235 */             return "Autogrid";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1240 */             return "Enable or disable the autogrid feature.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1245 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1249 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1253 */             return Optional.of(graphics.lassoEnabledProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1258 */             graphics.setLassoEnabled(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1263 */             return Boolean.valueOf(graphics.isLassoEnabled());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1268 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1273 */             return "Lasso Enabled";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1278 */             return "Enable or disable the lasso.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1283 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1287 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1291 */             return Optional.of(graphics.lassoSnapsToGridProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1296 */             graphics.setLassoSnapsToGrid(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1301 */             return Boolean.valueOf(graphics.isLassoSnapsToGrid());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1306 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1311 */             return "Lasso Grid";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1316 */             return "Enable or disable the lasso snaps to grid feature.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1321 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1325 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1329 */             return Optional.of(graphics.horizontalDragEnabledProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1334 */             graphics.setHorizontalDragEnabled(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1339 */             return Boolean.valueOf(graphics.isHorizontalDragEnabled());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1344 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1349 */             return "Horizontal Drag";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1354 */             return "Enable or disable horizontal dragging";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1359 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1363 */     items.add(new PropertySheet.Item()
/*      */         {
/*      */           public Optional<ObservableValue<?>> getObservableValue()
/*      */           {
/* 1367 */             return Optional.of(graphics.autoMarkedTimeIntervalProperty());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(Object value) {
/* 1372 */             graphics.setAutoMarkedTimeInterval(((Boolean)value).booleanValue());
/*      */           }
/*      */ 
/*      */           
/*      */           public Object getValue() {
/* 1377 */             return Boolean.valueOf(graphics.isAutoMarkedTimeInterval());
/*      */           }
/*      */ 
/*      */           
/*      */           public Class<?> getType() {
/* 1382 */             return Boolean.class;
/*      */           }
/*      */ 
/*      */           
/*      */           public String getName() {
/* 1387 */             return "Auto Marked Interval";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getDescription() {
/* 1392 */             return "Enable or disable the automatic update of the marked time interval.";
/*      */           }
/*      */ 
/*      */           
/*      */           public String getCategory() {
/* 1397 */             return "Control: Graphics";
/*      */           }
/*      */         });
/*      */     
/* 1401 */     return items;
/*      */   }
/*      */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\GraphicsBaseItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */