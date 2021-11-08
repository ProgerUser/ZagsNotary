package com.flexganttfx.extras.properties.view;

import com.flexganttfx.extras.properties.ItemFactory;
import com.flexganttfx.model.Row;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

public class GanttChartPropertySheet<R extends Row<?, ?, ?>> extends PropertySheet {
	private final ObservableList<Object> targets;

	public GanttChartPropertySheet() {
		this.targets = FXCollections.observableArrayList();
		setMode(PropertySheet.Mode.CATEGORY);
		//this.targets.addListener(it -> update());
	}

	public GanttChartPropertySheet(Object target) {
		this();
		getTargets().add(target);
	}

	public final ObservableList<Object> getTargets() {
		return this.targets;
	}

	private void update() {
		ItemFactory itemFactory = new ItemFactory();
		List<PropertySheet.Item> targetItems = new ArrayList<>();
		for (Object target : this.targets)
			targetItems.addAll(itemFactory.getItems(target));
		getItems().setAll(targetItems);
	}
}
