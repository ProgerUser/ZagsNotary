package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class SystemLayerItemProvider implements ItemProvider<SystemLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final SystemLayer layer) {
		List<PropertySheet.Item> items = new ArrayList<>();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.visibleProperty());
			}

			public void setValue(Object value) {
				layer.setVisible(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(layer.isVisible());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Visible";
			}

			public String getDescription() {
				return "Controls visibility of the system layer.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.snapToPixelProperty());
			}

			public void setValue(Object value) {
				layer.setSnapToPixel(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(layer.isSnapToPixel());
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
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}