package com.flexganttfx.extras.properties;

import com.flexganttfx.model.Row;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.GanttChartBase;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

public class GanttChartItemProvider<R extends Row<?, ?, ?>> implements ItemProvider<GanttChart<R>> {
	@SuppressWarnings("unused")
	private static final String GANTT_CHART_PROPERTIES_CATEGORY = "Control: Gantt Chart";

	public List<PropertySheet.Item> getPropertySheetItems(final GanttChart<R> gc) {
		GanttChartBaseItemProvider<R> baseItems = new GanttChartBaseItemProvider<>();
		List<PropertySheet.Item> items = baseItems.getPropertySheetItems((GanttChartBase<R>) gc);

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(gc.displayModeProperty());
			}

			public void setValue(Object value) {
				gc.setDisplayMode((GanttChart.DisplayMode) value);
			}

			public Object getValue() {
				return gc.getDisplayMode();
			}

			public Class<?> getType() {
				return GanttChart.DisplayMode.class;
			}

			public String getName() {
				return "Display Mode";
			}

			public String getDescription() {
				return "Standard Gantt, Table Only, Graphics Only";
			}

			public String getCategory() {
				return "Control: Gantt Chart";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(gc.showTreeTableProperty());
			}

			public void setValue(Object value) {
				gc.setShowTreeTable(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(gc.isShowTreeTable());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Tree Table";
			}

			public String getDescription() {
				return "Enables / disables display of the tree table view";
			}

			public String getCategory() {
				return "Control: Gantt Chart";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(gc.rowHeaderTypeProperty());
			}

			public void setValue(Object value) {
				gc.setRowHeaderType((GanttChart.RowHeaderType) value);
			}

			public Object getValue() {
				return gc.getRowHeaderType();
			}

			public Class<?> getType() {
				return GanttChart.RowHeaderType.class;
			}

			public String getName() {
				return "Row Header Mode";
			}

			public String getDescription() {
				return "The type of content shown by the row header.";
			}

			public String getCategory() {
				return "Control: Gantt Chart";
			}
		});

		return items;
	}
}