package com.flexganttfx.extras.properties;

import com.flexganttfx.extras.properties.timeline.DatelineItemProvider;
import com.flexganttfx.extras.properties.timeline.EventlineItemProvider;
import com.flexganttfx.extras.properties.timeline.TimelineItemProvider;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.view.GanttChartBase;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.ListViewGraphics;
import com.flexganttfx.view.timeline.Timeline;
import com.flexganttfx.view.util.Position;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

public class GanttChartBaseItemProvider<R extends Row<?, ?, ?>> implements ItemProvider<GanttChartBase<R>> {
	@SuppressWarnings("unused")
	private static final String GANTT_CHART_BASE_PROPERTIES_CATEGORY = "Control: Gantt Chart Base";

	@SuppressWarnings("unchecked")
	public List<PropertySheet.Item> getPropertySheetItems(final GanttChartBase<R> gc) {
		ObservableList<PropertySheet.Item> items = FXCollections.observableArrayList();

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(gc.fixedCellSizeProperty());
			}

			public void setValue(Object value) {
				gc.setFixedCellSize(((Double) value).doubleValue());
			}

			public Object getValue() {
				return Double.valueOf(gc.getFixedCellSize());
			}

			public Class<?> getType() {
				return Double.class;
			}

			public String getName() {
				return "Fixed Cell Size";
			}

			public String getDescription() {
				return "Controls whether cells have a fixed or varying row height.";
			}

			public String getCategory() {
				return "Control: Gantt Chart Base";
			}
		});

		items.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(gc.positionProperty());
			}

			public void setValue(Object value) {
				gc.setPosition((Position) value);
			}

			public Object getValue() {
				return gc.getPosition();
			}

			public Class<?> getType() {
				return Position.class;
			}

			public String getName() {
				return "Position";
			}

			public String getDescription() {
				return "The position of the Gantt chart within a dual / multi Gantt chart container.";
			}

			public String getCategory() {
				return "Control: Gantt Chart Base";
			}
		});

		Timeline timeline = gc.getTimeline();
		TimelineItemProvider timelineItemProvider = new TimelineItemProvider();
		items.addAll(timelineItemProvider.getPropertySheetItems(timeline));

		DatelineItemProvider datelineItemProvider = new DatelineItemProvider();
		items.addAll(datelineItemProvider.getPropertySheetItems(timeline.getDateline()));

		EventlineItemProvider eventlineItemProvider = new EventlineItemProvider();
		items.addAll(eventlineItemProvider.getPropertySheetItems(timeline.getEventline()));

		ListViewGraphics<R> graphics = gc.getGraphics();
		GraphicsBaseItemProvider<Row<?, ?, ?>> graphicsBasePropertySheetSupport = new GraphicsBaseItemProvider<>();
		items.addAll(graphicsBasePropertySheetSupport.getPropertySheetItems((GraphicsBase<Row<?, ?, ?>>) graphics));

		for (Layer layer : graphics.getLayers()) {
			items.add(new PropertySheet.Item() {
				public Optional<ObservableValue<?>> getObservableValue() {
					return Optional.of(layer.visibleProperty());
				}

				public void setValue(Object value) {
					layer.setVisible(((Boolean) value).booleanValue());
				}

				public Object getValue() {
					return Boolean.valueOf(layer.isVisible());
				}

				public Class<?> getType() {
					return Boolean.class;
				}

				public String getName() {
					return "Visible";
				}

				public String getDescription() {
					return "Show / hide the model layer (its activities)";
				}

				public String getCategory() {
					return "Model Layer: " + layer.getName();
				}
			});

			items.add(new PropertySheet.Item() {
				public Optional<ObservableValue<?>> getObservableValue() {
					return Optional.of(layer.deletableProperty());
				}

				public void setValue(Object value) {
					layer.setDeletable(((Boolean) value).booleanValue());
				}

				public Object getValue() {
					return Boolean.valueOf(layer.isDeletable());
				}

				public Class<?> getType() {
					return Boolean.class;
				}

				public String getName() {
					return "Deletable";
				}

				public String getDescription() {
					return "Determines if the layer can be deleted by the user.";
				}

				public String getCategory() {
					return "Model Layer: " + layer.getName();
				}
			});

			items.add(new PropertySheet.Item() {
				public Optional<ObservableValue<?>> getObservableValue() {
					return Optional.of(layer.nameProperty());
				}

				public void setValue(Object value) {
					layer.setName((String) value);
				}

				public Object getValue() {
					return layer.getName();
				}

				public Class<?> getType() {
					return String.class;
				}

				public String getName() {
					return "Name";
				}

				public String getDescription() {
					return "The name of the model layer";
				}

				public String getCategory() {
					return "Model Layer: " + layer.getName();
				}
			});

			items.add(new PropertySheet.Item() {
				public Optional<ObservableValue<?>> getObservableValue() {
					return Optional.of(layer.opacityProperty());
				}

				public void setValue(Object value) {
					layer.setOpacity(((Double) value).doubleValue());
				}

				public Object getValue() {
					return Double.valueOf(layer.getOpacity());
				}

				public Class<?> getType() {
					return Double.class;
				}

				public String getName() {
					return "Opacity";
				}

				public String getDescription() {
					return "Layer opacity / transparency.";
				}

				public String getCategory() {
					return "Model Layer: " + layer.getName();
				}
			});
		}

		return items;
	}
}
