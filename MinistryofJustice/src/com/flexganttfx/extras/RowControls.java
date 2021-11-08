/*    */ package com.flexganttfx.extras;
/*    */ 
/*    */ import com.flexganttfx.extras.util.Messages;
/*    */ import com.flexganttfx.model.Row;
/*    */ import com.flexganttfx.view.graphics.GraphicsBase;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.geometry.Pos;
/*    */ import javafx.scene.control.Button;
/*    */ import javafx.scene.layout.HBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RowControls<R extends Row<?, ?, ?>>
/*    */   extends HBox
/*    */ {
/*    */   public RowControls(GraphicsBase<R> graphics, R row) {
/* 33 */     setPickOnBounds(false);
/* 34 */     setMinSize(0.0D, 0.0D);
/* 35 */     setAlignment(Pos.TOP_RIGHT);
/* 36 */     setFillHeight(true);
/*    */     
/* 38 */     Button editButton = new Button(Messages.getString("RowControls.BUTTON_EDIT"));
/* 39 */     editButton.getStyleClass().add("row-controls-button");
/* 40 */     editButton.setOnAction(evt -> graphics.startRowEditing(row));
/* 41 */     getChildren().add(editButton);
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\RowControls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */