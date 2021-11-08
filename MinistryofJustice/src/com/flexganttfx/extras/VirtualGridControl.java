/*     */ package com.flexganttfx.extras;
/*     */ 
/*     */ import com.flexganttfx.extras.util.Messages;
/*     */ import com.flexganttfx.model.dateline.VirtualGrid;
/*     */ import com.flexganttfx.view.util.FlexGanttFXControl;
/*     */ import impl.com.flexganttfx.extras.skin.VirtualGridControlSkin;
/*     */ import java.util.Objects;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.Skin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VirtualGridControl
/*     */   extends FlexGanttFXControl
/*     */ {
/*     */   private final StringProperty noGridText;
/*     */   private final BooleanProperty showNoGridOption;
/*     */   private final ObjectProperty<VirtualGrid<?>> value;
/*     */   private ObservableList<VirtualGrid<?>> grids;
/*     */   
/*     */   public VirtualGridControl() {
/*  52 */     this
/*  53 */       .noGridText = new SimpleStringProperty(this, "noGridText", Messages.getString("VirtualGridControl.NO_GRID"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.showNoGridOption = new SimpleBooleanProperty(this, "showNoGridOption", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.value = new SimpleObjectProperty<>(this, "value");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     this
/* 158 */       .grids = FXCollections.observableArrayList();
/*     */     getStyleClass().add("virtual-grid-control");
/*     */   }
/*     */   
/*     */   protected Skin<?> createDefaultSkin() {
/*     */     return (Skin<?>)new VirtualGridControlSkin(this);
/*     */   }
/*     */   
/*     */   public final ObservableList<VirtualGrid<?>> getGrids() {
/* 167 */     return this.grids;
/*     */   }
/*     */   
/*     */   public String getUserAgentStylesheet() {
/*     */     return getUserAgentStylesheet(VirtualGridControl.class, "virtualgrid.css");
/*     */   }
/*     */   
/*     */   public final StringProperty noGridTextProperty() {
/*     */     return this.noGridText;
/*     */   }
/*     */   
/*     */   public final void setNoGridText(String text) {
/*     */     Objects.requireNonNull(text);
/*     */     this.noGridText.set(text);
/*     */   }
/*     */   
/*     */   public final String getNoGridText() {
/*     */     return this.noGridText.get();
/*     */   }
/*     */   
/*     */   public final BooleanProperty showNoGridOptionProperty() {
/*     */     return this.showNoGridOption;
/*     */   }
/*     */   
/*     */   public final boolean isShowNoGridOption() {
/*     */     return this.showNoGridOption.get();
/*     */   }
/*     */   
/*     */   public final void setShowNoGridOption(boolean show) {
/*     */     this.showNoGridOption.set(show);
/*     */   }
/*     */   
/*     */   public final ObjectProperty<VirtualGrid<?>> valueProperty() {
/*     */     return this.value;
/*     */   }
/*     */   
/*     */   public final VirtualGrid<?> getValue() {
/*     */     return valueProperty().get();
/*     */   }
/*     */   
/*     */   public final void setValue(VirtualGrid<?> grid) {
/*     */     valueProperty().set(grid);
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\VirtualGridControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */