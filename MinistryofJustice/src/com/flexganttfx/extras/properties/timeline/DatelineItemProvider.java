package com.flexganttfx.extras.properties.timeline;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.timeline.Dateline;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SelectionMode;
import org.controlsfx.control.PropertySheet;

public class DatelineItemProvider implements ItemProvider<Dateline> {
	@SuppressWarnings("unused")
	private static final String DATELINE_PROPERTIES_CATEGORY = "Control: Dateline";

	public List<PropertySheet.Item> getPropertySheetItems(final Dateline target) {
		List<PropertySheet.Item> items = new ArrayList<>();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.datelineBufferProperty());
			}

			public void setValue(Object value) {
				target.setDatelineBuffer(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(target.getDatelineBuffer());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Dateline Buffer";
			}

			public String getDescription() {
				return "Increases the dateline width to reduce redraws";
			}

			public String getCategory() {
				return "Control: Dateline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.selectionModeProperty());
			}

			public void setValue(Object value) {
				target.setSelectionMode((SelectionMode) value);
			}

			public Object getValue() {
				return target.getSelectionMode();
			}

			public Class<?> getType() {
				return SelectionMode.class;
			}

			public String getName() {
				return "Selection Mode";
			}

			public String getDescription() {
				return "Single or multiple selections of dateline cells / time intervals.";
			}

			public String getCategory() {
				return "Control: Dateline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.zoneIdProperty());
			}

			public void setValue(Object value) {
				target.setZoneId((ZoneId) value);
			}

			public Object getValue() {
				return target.getZoneId();
			}

			public Class<?> getType() {
				return ZoneId.class;
			}

			public String getName() {
				return "Timezone";
			}

			public String getDescription() {
				return "The timezone that will be displayed by the dateline.";
			}

			public String getCategory() {
				return "Control: Dateline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.firstDayOfWeekProperty());
			}

			public void setValue(Object value) {
				target.setFirstDayOfWeek((DayOfWeek) value);
			}

			public Object getValue() {
				return target.getFirstDayOfWeek();
			}

			public Class<?> getType() {
				return DayOfWeek.class;
			}

			public String getName() {
				return "First Day of Week";
			}

			public String getDescription() {
				return "The day representing the beginning of the week.";
			}

			public String getCategory() {
				return "Control: Dateline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.zoomLassoEnabledProperty());
			}

			public void setValue(Object value) {
				target.setZoomLassoEnabled(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(target.isZoomLassoEnabled());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Support Zoom Lasso";
			}

			public String getDescription() {
				return "If enabled the user can perform a zoom by selecting a time range with a lasso.";
			}

			public String getCategory() {
				return "Control: Dateline";
			}
		});

		return items;
	}
}
