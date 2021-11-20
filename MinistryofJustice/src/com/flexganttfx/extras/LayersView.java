package com.flexganttfx.extras;

import com.flexganttfx.model.Row;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.util.FlexGanttFXControl;
import impl.com.flexganttfx.extras.skin.LayersViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;

public class LayersView<R extends Row<?, ?, ?>> extends FlexGanttFXControl {
	private final ObjectProperty<GraphicsBase<R>> graphics;

	public LayersView() {
		this.graphics = new SimpleObjectProperty<>(this, "graphics");
		getStylesheets().add(LayersView.class.getResource("layers-view.css").toExternalForm());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Skin<?> createDefaultSkin() {
		return (Skin<?>) new LayersViewSkin(this);
	}

	public final ObjectProperty<GraphicsBase<R>> graphicsProperty() {
		return this.graphics;
	}

	public final GraphicsBase<R> getGraphics() {
		return this.graphics.get();
	}

	public final void setGraphics(GraphicsBase<R> graphics) {
		graphicsProperty().set(graphics);
	}
}