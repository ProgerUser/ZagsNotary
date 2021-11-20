package com.flexganttfx.extras.properties;

import java.util.Optional;

import org.controlsfx.control.PropertySheet;

import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.container.QuadGanttChartContainerBase;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QuadGanttChartContainerBaseItemProvider<T extends GanttChartBase<?>>
		implements ItemProvider<QuadGanttChartContainerBase<T>> {
	@SuppressWarnings("unused")
	private static final String QUAD_GANTT_CHART_CONTAINER_PROPERTIES_CATEGORY = "Control: Quad Gantt Chart Container";

	public final ObservableList<PropertySheet.Item> getPropertySheetItems(
			final QuadGanttChartContainerBase<T> container) {
		ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(container.showLowerProperty());
			}

			public void setValue(Object value) {
				container.setShowLower(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(container.isShowLower());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Lower";
			}

			public String getDescription() {
				return "Show lower charts";
			}

			public String getCategory() {
				return "Control: Quad Gantt Chart Container";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(container.animatedProperty());
			}

			public void setValue(Object value) {
				container.setAnimated(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(container.isAnimated());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Animated";
			}

			public String getDescription() {
				return "Open / close animations";
			}

			public String getCategory() {
				return "Control: Quad Gantt Chart Container";
			}
		});

		return items;
	}
}