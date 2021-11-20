package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.LayoutLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class LayoutLayerItemProvider implements ItemProvider<LayoutLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final LayoutLayer layer) {
		SystemLayerItemProvider provider = new SystemLayerItemProvider();
		List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer) layer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.paddingFillProperty());
			}

			public void setValue(Object value) {
				layer.setPaddingFill((Color) value);
			}

			public Object getValue() {
				return layer.getPaddingFill();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Padding Fill";
			}

			public String getDescription() {
				return "The color used for filling the background of the padding area.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}