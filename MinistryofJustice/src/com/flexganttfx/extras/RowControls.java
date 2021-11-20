package com.flexganttfx.extras;

import com.flexganttfx.extras.util.Messages;
import com.flexganttfx.model.Row;
import com.flexganttfx.view.graphics.GraphicsBase;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

@SuppressWarnings("unused")
public class RowControls<R extends Row<?, ?, ?>> extends HBox {
	public RowControls(GraphicsBase<R> graphics, R row) {
		setPickOnBounds(false);
		setMinSize(0.0D, 0.0D);
		setAlignment(Pos.TOP_RIGHT);
		setFillHeight(true);

		Button editButton = new Button(Messages.getString("RowControls.BUTTON_EDIT"));
		editButton.getStyleClass().add("row-controls-button");
		editButton.setOnAction(evt -> graphics.startRowEditing(row));
		getChildren().add(editButton);
	}
}