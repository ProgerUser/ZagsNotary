package com.flexganttfx.extras.properties;

import com.flexganttfx.model.Row;
import com.flexganttfx.model.dateline.VirtualGrid;
import com.flexganttfx.view.graphics.GraphicsBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

public class GraphicsBaseItemProvider<R extends Row<?, ?, ?>> implements ItemProvider<GraphicsBase<R>> {
	@SuppressWarnings("unused")
	private static final String GRAPHICS_VIEW_PROPERTIES = "Control: Graphics";

	public List<PropertySheet.Item> getPropertySheetItems(final GraphicsBase<R> graphics) {
		List<PropertySheet.Item> items = new ArrayList<>();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showRowHeadersProperty());
			}

			public void setValue(Object value) {
				graphics.setShowRowHeaders(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowRowHeaders());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Row Headers";
			}

			public String getDescription() {
				return "Show / hide row headers";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.rowHeadersWidthProperty());
			}

			public void setValue(Object value) {
				graphics.setRowHeadersWidth(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(graphics.getRowHeadersWidth());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Row Header Width";
			}

			public String getDescription() {
				return "The width of the row headers";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.canvasBufferProperty());
			}

			public void setValue(Object value) {
				graphics.setCanvasBuffer(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(graphics.getCanvasBuffer());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Canvas Buffer";
			}

			public String getDescription() {
				return "Increases the canvas widths to reduce redraws";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.safeRenderingProperty());
			}

			public void setValue(Object value) {
				graphics.setSafeRendering(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isSafeRendering());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Safe Rendering";
			}

			public String getDescription() {
				return "Enables / disables safe rendering mode.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showZoneIdProperty());
			}

			public void setValue(Object value) {
				graphics.setShowZoneId(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowZoneId());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Zone Id";
			}

			public String getDescription() {
				return "Shows / hides the zone IDs used by the rows.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.rowEditingModeProperty());
			}

			public void setValue(Object value) {
				graphics.setRowEditingMode((GraphicsBase.RowEditingMode) value);
			}

			public Object getValue() {
				return graphics.getRowEditingMode();
			}

			public Class<?> getType() {
				return GraphicsBase.RowEditingMode.class;
			}

			public String getName() {
				return "Row Editing Mode";
			}

			public String getDescription() {
				return "Single or multi row editing.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.animateRowEditorProperty());
			}

			public void setValue(Object value) {
				graphics.setAnimateRowEditor(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isAnimateRowEditor());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Animate Row Editor";
			}

			public String getDescription() {
				return "Use flip animation to show / hide the row editor.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showVerticalCursorProperty());
			}

			public void setValue(Object value) {
				graphics.setShowVerticalCursor(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowVerticalCursor());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Vertical Cursor";
			}

			public String getDescription() {
				return "Enables / disables the vertical cursor line.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showHorizontalCursorProperty());
			}

			public void setValue(Object value) {
				graphics.setShowHorizontalCursor(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowHorizontalCursor());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Horizontal Cursor";
			}

			public String getDescription() {
				return "Enables / disables the horizontal cursor line.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.maxGridLevelProperty());
			}

			public void setValue(Object value) {
				graphics.setMaxGridLevel(((Integer) value).intValue());
			}

			public Object getValue() {
				return Integer.valueOf(graphics.getMaxGridLevel());
			}

			public Class<?> getType() {
				return Integer.class;
			}

			public String getName() {
				return "Max Grid Level";
			}

			public String getDescription() {
				return "Determines the number of grid levels shown by the grid layer.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.virtualGridProperty());
			}

			@SuppressWarnings("rawtypes")
			public void setValue(Object value) {
				graphics.setVirtualGrid((VirtualGrid) value);
			}

			public Object getValue() {
				return graphics.getVirtualGrid();
			}

			public Class<?> getType() {
				return VirtualGrid.class;
			}

			public String getName() {
				return "Virtual Grid";
			}

			public String getDescription() {
				return "Sets a virtual grid.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.fadeInOutVisibilityChangesProperty());
			}

			public void setValue(Object value) {
				graphics.setFadeInOutVisibilityChanges(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isFadeInOutVisibilityChanges());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Fade in/out";
			}

			public String getDescription() {
				return "Controls whether visibility changes will be animated with a quick fade in/out.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.fadeInOutVisibilityChangesDurationProperty());
			}

			public void setValue(Object value) {
				graphics.setFadeInOutVisibilityChangesDuration(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(graphics.getFadeInOutVisibilityChangesDuration());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Fade in/out duration";
			}

			public String getDescription() {
				return "The duration in millis of the visibility change animation.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.selectionModeProperty());
			}

			public void setValue(Object value) {
				graphics.setSelectionMode((GraphicsBase.SelectionMode) value);
			}

			public Object getValue() {
				return graphics.getSelectionMode();
			}

			public Class<?> getType() {
				return GraphicsBase.SelectionMode.class;
			}

			public String getName() {
				return "Selection Mode";
			}

			public String getDescription() {
				return "Sets a selection mode (single, multiple, none).";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.debugModeProperty());
			}

			public void setValue(Object value) {
				graphics.setDebugMode(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isDebugMode());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Debug Mode";
			}

			public String getDescription() {
				return "Adds debug information to the rendered activities (bounds).";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.lassoSelectionBehaviourProperty());
			}

			public void setValue(Object value) {
				graphics.setLassoSelectionBehaviour((GraphicsBase.LassoSelectionBehaviour) value);
			}

			public Object getValue() {
				return graphics.getLassoSelectionBehaviour();
			}

			public Class<?> getType() {
				return GraphicsBase.LassoSelectionBehaviour.class;
			}

			public String getName() {
				return "Selection Behaviour";
			}

			public String getDescription() {
				return "Controls whether activities are selected by the lasso.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.highlightDelayProperty());
			}

			public void setValue(Object value) {
				graphics.setHighlightDelay(((Long) value).longValue());
			}

			public Object getValue() {
				return Long.valueOf(graphics.getHighlightDelay());
			}

			public Class<?> getType() {
				return Long.class;
			}

			public String getName() {
				return "Highlight Delay";
			}

			public String getDescription() {
				return "Controls the blinking speed of highlighted activities.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showHoverTimeIntervalLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowHoverTimeIntervalLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowHoverTimeIntervalLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Hover Interval";
			}

			public String getDescription() {
				return "Hide or show the time interval over which the mouse cursor hovers in the dateline.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showSelectedTimeIntervalsLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowSelectedTimeIntervalsLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowSelectedTimeIntervalsLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Selected Intervals";
			}

			public String getDescription() {
				return "Hide or show the time interval selections made in the dateline.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showGridLineLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowGridLineLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowGridLineLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Grid Lines";
			}

			public String getDescription() {
				return "Enables / disables grid lines";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showInnerLinesLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowInnerLinesLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowInnerLinesLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Inner Lines";
			}

			public String getDescription() {
				return "Enables / disables display of divider lines for inner lines";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showAgendaLinesLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowAgendaLinesLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowAgendaLinesLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Agenda Lines";
			}

			public String getDescription() {
				return "Enables / disables display of agenda lines (hours, minutes)";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showNowLineLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowNowLineLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowNowLineLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Now Line";
			}

			public String getDescription() {
				return "Enables / disables display of now line";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showDSTLineLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowDSTLineLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowDSTLineLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show DST Line";
			}

			public String getDescription() {
				return "Enables / disables display of DST line";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showRowLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowRowLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowRowLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Row Backgrounds";
			}

			public String getDescription() {
				return "Enables / disables display of individual row backgrounds";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showChartLinesLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowChartLinesLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowChartLinesLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Chart Lines";
			}

			public String getDescription() {
				return "Enables / disables display of chart lines.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showCalendarLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowCalendarLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowCalendarLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Calendars";
			}

			public String getDescription() {
				return "Enables / disables display of calendars";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showLayoutLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowLayoutLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowLayoutLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Layout Decoration";
			}

			public String getDescription() {
				return "Enables / disables display of layout specific decorations.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showZoomTimeIntervalLayerProperty());
			}

			public void setValue(Object value) {
				graphics.setShowZoomTimeIntervalLayer(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowZoomTimeIntervalLayer());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Zoom Interval";
			}

			public String getDescription() {
				return "Hide or show the time interval when zooming inside the dateline.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.showMarkedTimeIntervalProperty());
			}

			public void setValue(Object value) {
				graphics.setShowMarkedTimeInterval(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isShowMarkedTimeInterval());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Marked Interval";
			}

			public String getDescription() {
				return "Hide or show the time interval currently marked (e.g. while dragging).";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.dragAndDropFeedbackProperty());
			}

			public void setValue(Object value) {
				graphics.setDragAndDropFeedback((GraphicsBase.DragAndDropFeedback) value);
			}

			public Object getValue() {
				return graphics.getDragAndDropFeedback();
			}

			public Class<?> getType() {
				return GraphicsBase.DragAndDropFeedback.class;
			}

			public String getName() {
				return "Drag & Drop Feedback";
			}

			public String getDescription() {
				return "Control visual drag feedback";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.autoGridEnabledProperty());
			}

			public void setValue(Object value) {
				graphics.setAutoGridEnabled(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isAutoGridEnabled());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Autogrid";
			}

			public String getDescription() {
				return "Enable or disable the autogrid feature.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.lassoEnabledProperty());
			}

			public void setValue(Object value) {
				graphics.setLassoEnabled(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isLassoEnabled());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Lasso Enabled";
			}

			public String getDescription() {
				return "Enable or disable the lasso.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.lassoSnapsToGridProperty());
			}

			public void setValue(Object value) {
				graphics.setLassoSnapsToGrid(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isLassoSnapsToGrid());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Lasso Grid";
			}

			public String getDescription() {
				return "Enable or disable the lasso snaps to grid feature.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.horizontalDragEnabledProperty());
			}

			public void setValue(Object value) {
				graphics.setHorizontalDragEnabled(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isHorizontalDragEnabled());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Horizontal Drag";
			}

			public String getDescription() {
				return "Enable or disable horizontal dragging";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(graphics.autoMarkedTimeIntervalProperty());
			}

			public void setValue(Object value) {
				graphics.setAutoMarkedTimeInterval(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(graphics.isAutoMarkedTimeInterval());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Auto Marked Interval";
			}

			public String getDescription() {
				return "Enable or disable the automatic update of the marked time interval.";
			}

			public String getCategory() {
				return "Control: Graphics";
			}
		});

		return items;
	}
}
