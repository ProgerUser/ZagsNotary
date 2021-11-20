package impl.com.flexganttfx.extras.skin;

import com.flexganttfx.extras.LayersView;
import com.flexganttfx.extras.util.Messages;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.view.graphics.GraphicsBase;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LayersViewSkin<R extends Row<?, ?, ?>> extends SkinBase<LayersView<R>> {
	private GridPane gridPane;

	private final InvalidationListener rebuildListener = observable -> buildControls();

	private final WeakInvalidationListener weakRebuildListener = new WeakInvalidationListener(this.rebuildListener);

	public LayersViewSkin(LayersView<R> view) {
		super(view);
		this.gridPane = new GridPane();
		this.gridPane.setAlignment(Pos.TOP_CENTER);
		this.gridPane.getStyleClass().add("layer-controls");
		this.gridPane.setHgap(10.0D);
		this.gridPane.setVgap(10.0D);
		buildControls();
		view.graphicsProperty().addListener((observable, oldGraphics, newGraphics) -> {
			if (oldGraphics != null)
				oldGraphics.getLayers().removeListener(this.weakRebuildListener);
			if (newGraphics != null)
				newGraphics.getLayers().addListener(this.weakRebuildListener);
			buildControls();
		});
		GraphicsBase<R> graphics = view.getGraphics();
		if (graphics != null)
			graphics.getLayers().addListener(this.weakRebuildListener);
		getChildren().add(this.gridPane);
	}

	private void buildControls() {
		this.gridPane.getChildren().clear();
		GraphicsBase<R> graphics = getSkinnable().getGraphics();
		if (graphics != null) {
			ObservableList<Layer> modelLayers = graphics.getLayers();
			int row = modelLayers.size();
			for (int i = 0; i < modelLayers.size(); i++) {
				Layer layer = modelLayers.get(i);
				CheckBox checkBox = new CheckBox();
				Slider slider = new Slider(0.0D, 1.0D, 1.0D);
				GridPane.setHgrow(slider, Priority.ALWAYS);
				checkBox.setText(layer.getName());
				checkBox.setSelected(layer.isVisible());
				slider.setValue(layer.getOpacity());
				Button moveToFront = new Button();
				Button moveToBack = new Button();
				Button moveForward = new Button();
				Button moveBackward = new Button();
				Button delete = new Button();
				this.gridPane.add(checkBox, 0, row);
				this.gridPane.add(slider, 1, row);
				if (i < modelLayers.size() - 1) {
					this.gridPane.add(moveToFront, 2, row);
					this.gridPane.add(moveForward, 3, row);
				}
				if (i > 0) {
					this.gridPane.add(moveBackward, 4, row);
					this.gridPane.add(moveToBack, 5, row);
				}
				this.gridPane.add(delete, 6, row);
				moveToFront.getStyleClass().addAll(new String[] { "layers-navigate-button", "move-to-front" });
				moveToBack.getStyleClass().addAll(new String[] { "layers-navigate-button", "move-to-back" });
				moveForward.getStyleClass().addAll(new String[] { "layers-navigate-button", "move-forward" });
				moveBackward.getStyleClass().addAll(new String[] { "layers-navigate-button", "move-backward" });
				delete.getStyleClass().addAll(new String[] { "layers-navigate-button", "delete" });
				moveToFront.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CHEVRON_DOUBLE_UP));
				moveToBack.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CHEVRON_DOUBLE_DOWN));
				moveForward.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CHEVRON_UP));
				moveBackward.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.CHEVRON_DOWN));
				delete.setGraphic((Node) new MaterialDesignIconView(MaterialDesignIcon.DELETE));
				moveToFront.setTooltip(new Tooltip(Messages.getString("LayersViewSkin.TOOLTIP_MOVE_LAYER_TO_FRONT")));
				moveToBack.setTooltip(new Tooltip(Messages.getString("LayersViewSkin.TOOLTIP_MOVE_LAYER_TO_BACK")));
				moveForward.setTooltip(new Tooltip(Messages.getString("LayersViewSkin.TOOLTIP_MOVE_LAYER_FORWARD")));
				moveBackward.setTooltip(new Tooltip(Messages.getString("LayersViewSkin.TOOLTIP_MOVE_LAYER_BACK")));
				delete.setTooltip(new Tooltip(Messages.getString("LayersViewSkin.TOOLTIP_DELETE_LAYER")));
				moveToFront.setOnAction(moveToFront(layer));
				moveToBack.setOnAction(moveToBack(layer));
				moveForward.setOnAction(moveForward(layer));
				moveBackward.setOnAction(moveBackward(layer));
				delete.setOnAction(delete(layer));
				Bindings.bindBidirectional(checkBox.selectedProperty(), layer.visibleProperty());
				Bindings.bindBidirectional(checkBox.textProperty(), layer.nameProperty());
				Bindings.bindBidirectional(slider.valueProperty(), layer.opacityProperty());
				delete.visibleProperty().bind(layer.deletableProperty());
				row--;
			}
			HBox hBox = new HBox();
			hBox.setSpacing(10.0D);
			hBox.setAlignment(Pos.CENTER_RIGHT);
			Button showAll = new Button(Messages.getString("LayersViewSkin.BUTTON_SHOW_ALL"));
			Button hideAll = new Button(Messages.getString("LayersViewSkin.BUTTON_HIDE_ALL"));
			hBox.getChildren().add(hideAll);
			hBox.getChildren().add(showAll);
			this.gridPane.add(hBox, 0, modelLayers.size() + 1, 7, 1);
			GridPane.setMargin(hBox, new Insets(20.0D, 0.0D, 0.0D, 0.0D));
			showAll.setOnAction(showAll(modelLayers));
			hideAll.setOnAction(hideAll(modelLayers));
		}
		Label headerName = new Label(Messages.getString("LayersViewSkin.HEADER_LAYER_NAME"));
		Label headerOpacity = new Label(Messages.getString("LayersViewSkin.HEADER_OPACITY"));
		Label headerOrder = new Label(Messages.getString("LayersViewSkin.HEADER_ORDER"));
		headerName.setMaxWidth(Double.MAX_VALUE);
		headerOpacity.setMaxWidth(Double.MAX_VALUE);
		headerOrder.setMaxWidth(Double.MAX_VALUE);
		headerName.getStyleClass().add("layers-table-header");
		headerOpacity.getStyleClass().add("layers-table-header");
		headerOrder.getStyleClass().add("layers-table-header");
		GridPane.setHgrow(headerOpacity, Priority.ALWAYS);
		GridPane.setFillWidth(headerName, Boolean.valueOf(true));
		GridPane.setFillWidth(headerOpacity, Boolean.valueOf(true));
		GridPane.setFillWidth(headerOrder, Boolean.valueOf(true));
		this.gridPane.add(headerName, 0, 0);
		this.gridPane.add(headerOpacity, 1, 0);
		this.gridPane.add(headerOrder, 2, 0, 4, 1);
	}

	private EventHandler<ActionEvent> hideAll(ObservableList<Layer> modelLayers) {
		return event -> {
			for (Layer layer : modelLayers)
				layer.setVisible(false);
		};
	}

	private EventHandler<ActionEvent> showAll(ObservableList<Layer> modelLayers) {
		return event -> {
			for (Layer layer : modelLayers)
				layer.setVisible(true);
		};
	}

	private EventHandler<ActionEvent> delete(Layer layer) {
		return evt -> getSkinnable().getGraphics().getLayers().remove(layer);
	}

	private EventHandler<ActionEvent> moveBackward(Layer layer) {
		return evt -> getSkinnable().getGraphics().moveLayerBackward(layer);
	}

	private EventHandler<ActionEvent> moveForward(Layer layer) {
		return evt -> getSkinnable().getGraphics().moveLayerForward(layer);
	}

	private EventHandler<ActionEvent> moveToBack(Layer layer) {
		return evt -> getSkinnable().getGraphics().moveLayerToBack(layer);
	}

	private EventHandler<ActionEvent> moveToFront(Layer layer) {
		return evt -> getSkinnable().getGraphics().moveLayerToFront(layer);
	}
}
