package com.flexganttfx.extras.properties;

import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.container.DualGanttChartContainerBase;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("unused")
public class DualGanttChartContainerBaseItemProvider<T extends GanttChartBase<?>>
		implements ItemProvider<DualGanttChartContainerBase<T>> {
	private static final String DUAL_GANTT_CHART_CONTAINER_PROPERTIES_CATEGORY = "Control: Dual Gantt Chart Container";

	public final ObservableList<PropertySheet.Item> getPropertySheetItems(
			final DualGanttChartContainerBase<T> container) {
		ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(container.showSecondaryProperty());
			}

			public void setValue(Object value) {
				container.setShowSecondary(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(container.isShowSecondary());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Secondary";
			}

			public String getDescription() {
				return "Show secondary chart";
			}

			public String getCategory() {
				return "Control: Dual Gantt Chart Container";
			}
		});

		return items;
	}
}
