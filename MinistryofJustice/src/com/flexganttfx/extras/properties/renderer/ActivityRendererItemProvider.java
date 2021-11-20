package com.flexganttfx.extras.properties.renderer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.renderer.ActivityRenderer;
import com.flexganttfx.view.graphics.renderer.Renderer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class ActivityRendererItemProvider implements ItemProvider<ActivityRenderer> {
	public List<PropertySheet.Item> getPropertySheetItems(final ActivityRenderer renderer) {
		RendererItemProvider support = new RendererItemProvider();
		List<PropertySheet.Item> items = support.getPropertySheetItems((Renderer) renderer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.strokeProperty());
			}

			public void setValue(Object value) {
				renderer.setStroke((Paint) value);
			}

			public Object getValue() {
				return renderer.getStroke();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Stroke";
			}

			public String getDescription() {
				return "The color used for drawing the activity border.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.strokeHighlightProperty());
			}

			public void setValue(Object value) {
				renderer.setStrokeHighlight((Paint) value);
			}

			public Object getValue() {
				return renderer.getStrokeHighlight();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Stroke Highlight";
			}

			public String getDescription() {
				return "The color used for drawing the activity border when the activity is currently drawn highlighted.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.strokeHoverProperty());
			}

			public void setValue(Object value) {
				renderer.setStrokeHover((Paint) value);
			}

			public Object getValue() {
				return renderer.getStrokeHover();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Stroke Hover";
			}

			public String getDescription() {
				return "The color used for filling the activity border when the mouse cursor hovers over the activity.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.strokeSelectedProperty());
			}

			public void setValue(Object value) {
				renderer.setStrokeSelected((Paint) value);
			}

			public Object getValue() {
				return renderer.getStrokeSelected();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Stroke Selected";
			}

			public String getDescription() {
				return "The color used for drawing the activity border when the activity is currently selected.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.cornersRoundedProperty());
			}

			public void setValue(Object value) {
				renderer.setCornersRounded(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(renderer.isCornersRounded());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Corners Rounded";
			}

			public String getDescription() {
				return "Determines if the corners of the activity will be drawn rounded or not.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.cornerRadiusProperty());
			}

			public void setValue(Object value) {
				renderer.setCornerRadius(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(renderer.getCornerRadius());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Corner Radius";
			}

			public String getDescription() {
				return "The radius used for the activity corners when rounded corners are used.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.lineWidthProperty());
			}

			public void setValue(Object value) {
				renderer.setLineWidth(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(renderer.getLineWidth());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Line Width";
			}

			public String getDescription() {
				return "The line width used for the border.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		return items;
	}
}
