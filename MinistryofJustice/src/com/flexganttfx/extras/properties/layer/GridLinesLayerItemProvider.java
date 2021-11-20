package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.GridLinesLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class GridLinesLayerItemProvider implements ItemProvider<GridLinesLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final GridLinesLayer layer) {
		SystemLayerItemProvider provider = new SystemLayerItemProvider();
		List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer) layer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineStroke1Property());
			}

			public void setValue(Object value) {
				layer.setLineStroke1((Color) value);
			}

			public Object getValue() {
				return layer.getLineStroke1();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Line Stroke 1";
			}

			public String getDescription() {
				return "The color used for the first grid line level";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineWidth1Property());
			}

			public void setValue(Object value) {
				layer.setLineWidth1(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(layer.getLineWidth1());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Line Width 1";
			}

			public String getDescription() {
				return "The line width used for the first grid line level";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineStroke2Property());
			}

			public void setValue(Object value) {
				layer.setLineStroke2((Color) value);
			}

			public Object getValue() {
				return layer.getLineStroke2();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Line Stroke 2";
			}

			public String getDescription() {
				return "The color used for the first grid line level";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineWidth2Property());
			}

			public void setValue(Object value) {
				layer.setLineWidth2(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(layer.getLineWidth2());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Line Width 2";
			}

			public String getDescription() {
				return "The line width used for the second grid line level";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineStroke3Property());
			}

			public void setValue(Object value) {
				layer.setLineStroke3((Color) value);
			}

			public Object getValue() {
				return layer.getLineStroke3();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Line Stroke 3";
			}

			public String getDescription() {
				return "The color used for the third grid line level";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.lineWidth3Property());
			}

			public void setValue(Object value) {
				layer.setLineWidth3(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(layer.getLineWidth3());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Line Width 3";
			}

			public String getDescription() {
				return "The line width used for the third grid line level";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}