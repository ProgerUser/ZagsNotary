/*    */ package com.flexganttfx.extras;
/*    */ 
/*    */ import com.flexganttfx.model.Row;
/*    */ import com.flexganttfx.view.graphics.GraphicsBase;
/*    */ import com.flexganttfx.view.util.FlexGanttFXControl;
/*    */ import impl.com.flexganttfx.extras.skin.LayersViewSkin;
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.beans.property.SimpleObjectProperty;
/*    */ import javafx.scene.control.Skin;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LayersView<R extends Row<?, ?, ?>>
/*    */   extends FlexGanttFXControl
/*    */ {
/*    */   private final ObjectProperty<GraphicsBase<R>> graphics;
/*    */   
/*    */   public LayersView() {
/* 42 */     this.graphics = new SimpleObjectProperty<>(this, "graphics");
/*    */     getStylesheets().add(LayersView.class.getResource("layers-view.css").toExternalForm());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Skin<?> createDefaultSkin() {
/*    */     return (Skin<?>)new LayersViewSkin(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public final ObjectProperty<GraphicsBase<R>> graphicsProperty() {
/* 54 */     return this.graphics;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final GraphicsBase<R> getGraphics() {
/* 64 */     return this.graphics.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void setGraphics(GraphicsBase<R> graphics) {
/* 75 */     graphicsProperty().set(graphics);
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\LayersView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */