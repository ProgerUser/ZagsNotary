package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.DSTLineLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class DSTLineLayerItemProvider implements ItemProvider<DSTLineLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final DSTLineLayer layer) {
		SystemLayerItemProvider provider = new SystemLayerItemProvider();
		List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer) layer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.strokeProperty());
			}

			public void setValue(Object value) {
				layer.setStroke((Color) value);
			}

			public Object getValue() {
				return layer.getStroke();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Stroke";
			}

			public String getDescription() {
				return "The color used for the DST line";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineWidthProperty());
			}

			public void setValue(Object value) {
				layer.setLineWidth(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(layer.getLineWidth());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Line Width";
			}

			public String getDescription() {
				return "The width of the DST line";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}