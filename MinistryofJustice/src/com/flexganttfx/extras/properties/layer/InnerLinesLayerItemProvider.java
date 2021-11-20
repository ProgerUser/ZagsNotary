package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.InnerLinesLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class InnerLinesLayerItemProvider implements ItemProvider<InnerLinesLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final InnerLinesLayer layer) {
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
				return "The color used for the inner lines";
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
				return "The width of the inner lines";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.drawLastDividerLineProperty());
			}

			public void setValue(Object value) {
				layer.setDrawLastDividerLine(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(layer.isDrawLastDividerLine());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Draw Last Divider Line";
			}

			public String getDescription() {
				return "Controls if a divider line is drawn for the last inner line";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}