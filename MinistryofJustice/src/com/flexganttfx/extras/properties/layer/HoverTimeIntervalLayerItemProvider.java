package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.HoverTimeIntervalLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class HoverTimeIntervalLayerItemProvider implements ItemProvider<HoverTimeIntervalLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final HoverTimeIntervalLayer layer) {
		SystemLayerItemProvider provider = new SystemLayerItemProvider();
		List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer) layer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.hoverTimeIntervalFillProperty());
			}

			public void setValue(Object value) {
				layer.setHoverTimeIntervalFill((Color) value);
			}

			public Object getValue() {
				return layer.getHoverTimeIntervalFill();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Focused Time Fill";
			}

			public String getDescription() {
				return "The color used for visualizing the hover time interval of the dateline.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}
