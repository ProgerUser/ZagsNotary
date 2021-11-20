package com.flexganttfx.extras.properties.renderer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import com.flexganttfx.view.graphics.renderer.ActivityRenderer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class ActivityBarRendererItemProvider implements ItemProvider<ActivityBarRenderer> {
	public List<PropertySheet.Item> getPropertySheetItems(final ActivityBarRenderer renderer) {
		ActivityRendererItemProvider support = new ActivityRendererItemProvider();
		List<PropertySheet.Item> items = support.getPropertySheetItems((ActivityRenderer) renderer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.textFillProperty());
			}

			public void setValue(Object value) {
				renderer.setTextFill((Paint) value);
			}

			public Object getValue() {
				return renderer.getTextFill();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Text Fill";
			}

			public String getDescription() {
				return "The color used for the text.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.textFillSelectedProperty());
			}

			public void setValue(Object value) {
				renderer.setTextFillSelected((Paint) value);
			}

			public Object getValue() {
				return renderer.getTextFillSelected();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Text Fill Selected";
			}

			public String getDescription() {
				return "The color used for the text when the activity is selected.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.textFillHighlightProperty());
			}

			public void setValue(Object value) {
				renderer.setTextFillHover((Paint) value);
			}

			public Object getValue() {
				return renderer.getTextFillHover();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Text Fill Hover";
			}

			public String getDescription() {
				return "The color used for the text when the mouse cursor hovers over the activity.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.textFillHighlightProperty());
			}

			public void setValue(Object value) {
				renderer.setTextFillHighlight((Paint) value);
			}

			public Object getValue() {
				return renderer.getTextFillHighlight();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Text Fill Highlight";
			}

			public String getDescription() {
				return "The color used for the text when the activity is highlighted / blinking.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.textFillPressedProperty());
			}

			public void setValue(Object value) {
				renderer.setTextFillPressed((Paint) value);
			}

			public Object getValue() {
				return renderer.getTextFillPressed();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Text Fill Pressed";
			}

			public String getDescription() {
				return "The color used for the text when the activity is pressed.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.textGapProperty());
			}

			public void setValue(Object value) {
				renderer.setTextGap(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(renderer.getTextGap());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Text Gap";
			}

			public String getDescription() {
				return "The gap between the activity and the text.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.glossyProperty());
			}

			public void setValue(Object value) {
				renderer.setGlossy(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(renderer.isGlossy());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Glossy";
			}

			public String getDescription() {
				return "Shows / hides a glossy effect on the activity.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.autoFixTextProperty());
			}

			public void setValue(Object value) {
				renderer.setAutoFixText(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(renderer.isAutoFixText());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Fix Text";
			}

			public String getDescription() {
				return "Controls if text stays within the viewport as long as possible.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.barHeightProperty());
			}

			public void setValue(Object value) {
				renderer.setBarHeight(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(renderer.getBarHeight());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Bar Height";
			}

			public String getDescription() {
				return "The size of the bar representing an activity.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fontProperty());
			}

			public void setValue(Object value) {
				renderer.setFont((Font) value);
			}

			public Object getValue() {
				return renderer.getFont();
			}

			public Class<?> getType() {
				return Font.class;
			}

			public String getName() {
				return "Font";
			}

			public String getDescription() {
				return "The font used for any text.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		return items;
	}
}