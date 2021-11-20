package com.flexganttfx.extras.properties.layer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.layer.ChartLinesLayer;
import com.flexganttfx.view.graphics.layer.SystemLayer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class ChartLinesLayerItemProvider implements ItemProvider<ChartLinesLayer> {
	public List<PropertySheet.Item> getPropertySheetItems(final ChartLinesLayer layer) {
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
				return "Determines if major chart lines will be shown.";
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
				return "The color used for the major chart lines.";
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
				return "Major Lines Width";
			}

			public String getDescription() {
				return "The width of the major chart lines.";
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
				return "Determines if minor chart lines will be shown.";
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
				return "The color used for the major chart lines.";
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
				return "Minor Lines Width";
			}

			public String getDescription() {
				return "The width of the minor chart lines.";
			}

			public String getCategory() {
				return "System Layer: " + layer.getName();
			}
		});

		return items;
	}
}