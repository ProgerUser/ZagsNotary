package impl.com.flexganttfx.extras.skin;

import com.flexganttfx.extras.VirtualGridControl;
import com.flexganttfx.model.dateline.VirtualGrid;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.control.SkinBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class VirtualGridControlSkin extends SkinBase<VirtualGridControl> {
	private VBox vbox;

	public VirtualGridControlSkin(VirtualGridControl control) {
		super(control);
		this.vbox = new VBox();
		this.vbox.setFillWidth(true);
		getChildren().add(this.vbox);
		control.showNoGridOptionProperty().addListener(it -> updatePanel());
		//control.getGrids().addListener(evt -> updatePanel());
		updatePanel();
	}

	private void updatePanel() {
		this.vbox.getChildren().clear();
		ToggleGroup toggleGroup = new ToggleGroup();
		if (getSkinnable().isShowNoGridOption()) {
			ToggleButton noGrid = createButton(getSkinnable().getNoGridText());
			noGrid.setOnAction(evt -> getSkinnable().setValue(null));
			this.vbox.getChildren().add(noGrid);
			toggleGroup.getToggles().add(noGrid);
		}
		for (VirtualGrid<?> grid : (Iterable<VirtualGrid<?>>) getSkinnable().getGrids()) {
			ToggleButton button = createButton(grid.getName());
			button.setFocusTraversable(false);
			if (getSkinnable().getValue() == grid)
				button.setSelected(true);
			button.setOnAction(evt -> getSkinnable().setValue(grid));
			toggleGroup.getToggles().add(button);
			this.vbox.getChildren().add(button);
		}
	}

	private ToggleButton createButton(String name) {
		ToggleButton button = new ToggleButton(name);
		button.getStyleClass().add("grid-button");
		button.setMaxWidth(Double.MAX_VALUE);
		return button;
	}
}
