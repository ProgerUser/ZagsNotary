package com.flexganttfx.extras.properties.timeline;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.timeline.Eventline;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

public class EventlineItemProvider implements ItemProvider<Eventline> {
	@SuppressWarnings("unused")
	private static final String EVENTLINE_PROPERTIES_CATEGORY = "Control: Eventline";

	public List<PropertySheet.Item> getPropertySheetItems(final Eventline target) {
		List<PropertySheet.Item> items = new ArrayList<>();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.showTimeCursorProperty());
			}

			public void setValue(Object value) {
				target.setShowTimeCursor(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(target.isShowTimeCursor());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Time Cursor";
			}

			public String getDescription() {
				return "Enables / disables the display of the time cursor.";
			}

			public String getCategory() {
				return "Control: Eventline";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(target.showMarkedTimeIntervalProperty());
			}

			public void setValue(Object value) {
				target.setShowMarkedTimeInterval(((Boolean) value).booleanValue());
			}

			public Object getValue() {
				return Boolean.valueOf(target.isShowMarkedTimeInterval());
			}

			public Class<?> getType() {
				return Boolean.class;
			}

			public String getName() {
				return "Show Marked Time Intervals";
			}

			public String getDescription() {
				return "Enables / disables the display of a marked time interval.";
			}

			public String getCategory() {
				return "Control: Eventline";
			}
		});

		return items;
	}
}
