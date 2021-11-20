package com.flexganttfx.extras.properties.timeline;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.timeline.Timeline;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import org.controlsfx.control.PropertySheet;

public class TimelineItemProvider implements ItemProvider<Timeline> {
	@SuppressWarnings("unused")
	private static final String TIMELINE_PROPERTIES_CATEGORY = "Control: Timeline";

	public List<PropertySheet.Item> getPropertySheetItems(final Timeline target) {
		List<PropertySheet.Item> items = new ArrayList<>();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.zoomAnimatedProperty());
			}

			public void setValue(Object value) {
				target.setZoomAnimated(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(target.isZoomAnimated());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Animated Zoom";
			}

			public String getDescription() {
				return "Zoom operations can be performed with or without animation.";
			}

			public String getCategory() {
				return "Control: Timeline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.zoomFactorProperty());
			}

			public void setValue(Object value) {
				target.setZoomFactor(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(target.getZoomFactor());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Zoom Factor";
			}

			public String getDescription() {
				return "The factor used for zooming in or out, default = .5";
			}

			public String getCategory() {
				return "Control: Timeline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.zoomModeProperty());
			}

			public void setValue(Object value) {
				target.setZoomMode((Timeline.ZoomMode) value);
			}

			public Object getValue() {
				return target.getZoomMode();
			}

			public Class<?> getType() {
				return Timeline.ZoomMode.class;
			}

			public String getName() {
				return "Zoom Mode";
			}

			public String getDescription() {
				return "The method of zooming in (keep start, keep end, keep center time).";
			}

			public String getCategory() {
				return "Control: Timeline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.zoomDurationProperty());
			}

			public void setValue(Object value) {
				target.setZoomDuration((Duration) value);
			}

			public Object getValue() {
				return target.getZoomDuration();
			}

			public Class<?> getType() {
				return Duration.class;
			}

			public String getName() {
				return "Zoom Duration";
			}

			public String getDescription() {
				return "The duration of the zoom animation.";
			}

			public String getCategory() {
				return "Control: Timeline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.moveAnimatedProperty());
			}

			public void setValue(Object value) {
				target.setMoveAnimated(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(target.isMoveAnimated());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Animated Move";
			}

			public String getDescription() {
				return "Move operations can be performed with or without animation.";
			}

			public String getCategory() {
				return "Control: Timeline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.moveDurationProperty());
			}

			public void setValue(Object value) {
				target.setMoveDuration((Duration) value);
			}

			public Object getValue() {
				return target.getMoveDuration();
			}

			public Class<?> getType() {
				return Duration.class;
			}

			public String getName() {
				return "Move Duration";
			}

			public String getDescription() {
				return "The duration of the move animation.";
			}

			public String getCategory() {
				return "Control: Timeline";
			}
		});

		return items;
	}
}
