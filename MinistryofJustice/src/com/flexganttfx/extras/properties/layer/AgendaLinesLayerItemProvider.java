package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.AgendaLinesLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class AgendaLinesLayerItemProvider implements ItemProvider<AgendaLinesLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final AgendaLinesLayer layer) {
		SystemLayerItemProvider provider = new SystemLayerItemProvider();
		List<PropertySheet.Item> items = provider.getPropertySheetItems((SystemLayer) layer);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.majorLinesVisibleProperty());
			}

			public void setValue(Object value) {
				layer.setMajorLinesVisible(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(layer.isMajorLinesVisible());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Major Lines Visible";
			}

			public String getDescription() {
				return "Determines if major agenda lines will be shown.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.majorLinesStrokeProperty());
			}

			public void setValue(Object value) {
				layer.setMajorLinesStroke((Color) value);
			}

			public Object getValue() {
				return layer.getMajorLinesStroke();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Major Lines Stroke";
			}

			public String getDescription() {
				return "The color used for the major agenda lines.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.majorLinesLineWidthProperty());
			}

			public void setValue(Object value) {
				layer.setMajorLinesLineWidth(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(layer.getMajorLinesLineWidth());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Major Lines Line Width";
			}

			public String getDescription() {
				return "The width of the major agenda lines.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.minorLinesVisibleProperty());
			}

			public void setValue(Object value) {
				layer.setMinorLinesVisible(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(layer.isMinorLinesVisible());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Minor Lines Visible";
			}

			public String getDescription() {
				return "Determines if minor agenda lines will be shown.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.minorLinesStrokeProperty());
			}

			public void setValue(Object value) {
				layer.setMinorLinesStroke((Color) value);
			}

			public Object getValue() {
				return layer.getMinorLinesStroke();
			}

			public Class<?> getType() {
				return Color.class;
			}

			public String getName() {
				return "Minor Lines Stroke";
			}

			public String getDescription() {
				return "The color used for the major agenda lines.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(layer.minorLinesLineWidthProperty());
			}

			public void setValue(Object value) {
				layer.setMinorLinesLineWidth(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(layer.getMinorLinesLineWidth());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Minor Lines Line Width";
			}

			public String getDescription() {
				return "The width of the minor agenda lines.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});
		return items;
	}
}
