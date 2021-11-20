package com.flexganttfx.extras.properties.renderer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.renderer.Renderer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;
import org.controlsfx.control.PropertySheet;

public class RendererItemProvider implements ItemProvider<Renderer> {
	public List<PropertySheet.Item> getPropertySheetItems(final Renderer renderer) {
		ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.enabledProperty());
			}

			public void setValue(Object value) {
				renderer.setEnabled(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(renderer.isEnabled());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Enabled";
			}

			public String getDescription() {
				return "Enables / disables the renderer (if disabled activities using this renderer will not be shown at all).";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.snapToPixelProperty());
			}

			public void setValue(Object value) {
				renderer.setSnapToPixel(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(renderer.isSnapToPixel());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Snap To Pixel";
			}

			public String getDescription() {
				return "Enables / disables the snap to pixel feature.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.paddingProperty());
			}

			public void setValue(Object value) {
				renderer.setPadding((Insets) value);
			}

			public Object getValue() {
				return renderer.getPadding();
			}

			public Class<?> getType() {
				return Insets.class;
			}

			public String getName() {
				return "Padding";
			}

			public String getDescription() {
				return "Specifies a padding to be applied (not applicable for all renderers).";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillProperty());
			}

			public void setValue(Object value) {
				renderer.setFill((Paint) value);
			}

			public Object getValue() {
				return renderer.getFill();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Fill";
			}

			public String getDescription() {
				return "The color used for filling the activity background.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillHighlightProperty());
			}

			public void setValue(Object value) {
				renderer.setFillHighlight((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillHighlight();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Fill Highlight";
			}

			public String getDescription() {
				return "The color used for filling the activity background when the activity is currently drawn highlighted.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillHoverProperty());
			}

			public void setValue(Object value) {
				renderer.setFillHover((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillHover();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Fill Hover";
			}

			public String getDescription() {
				return "The color used for filling the activity background when the mouse cursor hovers over the activity.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillSelectedProperty());
			}

			public void setValue(Object value) {
				renderer.setFillSelected((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillSelected();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Fill Selected";
			}

			public String getDescription() {
				return "The color used for filling the activity background when the activity is selected.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillPressedProperty());
			}

			public void setValue(Object value) {
				renderer.setFillPressed((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillPressed();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Fill Pressed";
			}

			public String getDescription() {
				return "The color used for filling the activity background when the user presses on it.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.alphaProperty());
			}

			public void setValue(Object value) {
				renderer.setAlpha(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(renderer.getAlpha());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Opacity / Alpha";
			}

			public String getDescription() {
				return "The alpha value used when drawing the activity (opaque, transparent).";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		return items;
	}
}
