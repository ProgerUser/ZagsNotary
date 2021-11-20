package com.flexganttfx.extras.properties.renderer;

import com.flexganttfx.extras.properties.ItemProvider;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import com.flexganttfx.view.graphics.renderer.CompletableActivityRenderer;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import org.controlsfx.control.PropertySheet;

@SuppressWarnings("rawtypes")
public class CompletableActivityRendererItemProvider implements ItemProvider<CompletableActivityRenderer> {
	public List<PropertySheet.Item> getPropertySheetItems(final CompletableActivityRenderer renderer) {
		ActivityBarRendererItemProvider support = new ActivityBarRendererItemProvider();

		List<PropertySheet.Item> list = support.getPropertySheetItems((ActivityBarRenderer) renderer);

		list.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillCompletionProperty());
			}

			public void setValue(Object value) {
				renderer.setFillCompletion((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillCompletion();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Completion";
			}

			public String getDescription() {
				return "The paint used for drawing that segment of the activity that represents the completed part.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		list.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillCompletionHoverProperty());
			}

			public void setValue(Object value) {
				renderer.setFillCompletionHover((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillCompletionHover();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Completion Hover";
			}

			public String getDescription() {
				return "The paint used for drawing that segment of the activity that represents the completed part.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		list.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillCompletionPressedProperty());
			}

			public void setValue(Object value) {
				renderer.setFillCompletionPressed((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillCompletionPressed();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Completion Pressed";
			}

			public String getDescription() {
				return "The paint used for drawing that segment of the activity that represents the completed part.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		list.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillCompletionSelectedProperty());
			}

			public void setValue(Object value) {
				renderer.setFillCompletionSelected((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillCompletionSelected();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Completion Selected";
			}

			public String getDescription() {
				return "The paint used for drawing that segment of the activity that represents the completed part.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});

		list.add(new PropertySheet.Item() {
			public Optional<ObservableValue<?>> getObservableValue() {
				return Optional.of(renderer.fillCompletionHighlightProperty());
			}

			public void setValue(Object value) {
				renderer.setFillCompletionHighlight((Paint) value);
			}

			public Object getValue() {
				return renderer.getFillCompletionHighlight();
			}

			public Class<?> getType() {
				return Paint.class;
			}

			public String getName() {
				return "Completion Highlight";
			}

			public String getDescription() {
				return "The paint used for drawing that segment of the activity that represents the completed part.";
			}

			public String getCategory() {
				return "Renderer: " + renderer.getName();
			}
		});
		return list;
	}
}