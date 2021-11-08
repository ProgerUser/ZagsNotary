/*     */ package com.flexganttfx.extras;
/*     */ 
/*     */ import com.flexganttfx.model.Row;
/*     */ import com.flexganttfx.view.graphics.GraphicsBase;
/*     */ import com.flexganttfx.view.util.FlexGanttFXControl;
/*     */ import impl.com.flexganttfx.extras.skin.RadarViewSkin;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
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
/*     */ public class RadarView<R extends Row<?, ?, ?>>
/*     */   extends FlexGanttFXControl
/*     */ {
/*     */   private final ObjectProperty<GraphicsBase<R>> graphics;
/*     */   private final DoubleProperty radarWidth;
/*     */   private final DoubleProperty radarHeight;
/*     */   
/*     */   protected Skin<?> createDefaultSkin() {
/*     */     return (Skin<?>)new RadarViewSkin(this);
/*     */   }
/*     */   
/*     */   public final ObjectProperty<GraphicsBase<R>> graphicsProperty() {
/*     */     return this.graphics;
/*     */   }
/*     */   
/*     */   public RadarView() {
/*  45 */     this.graphics = new SimpleObjectProperty<>(this, "graphics");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.radarWidth = new SimpleDoubleProperty(this, "width", 300.0D);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.radarHeight = new SimpleDoubleProperty(this, "height", 200.0D);
/*     */     getStylesheets().add(RadarView.class.getResource("radar-view.css").toExternalForm());
/*     */   }
/*     */ 
/*     */   
/*     */   public final GraphicsBase<R> getGraphics() {
/*     */     return this.graphics.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleProperty radarHeightProperty() {
/* 137 */     return this.radarHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setGraphics(GraphicsBase<R> graphics) {
/*     */     graphicsProperty().set(graphics);
/*     */   }
/*     */ 
/*     */   
/*     */   public final double getRadarHeight() {
/* 147 */     return this.radarHeight.get();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleProperty radarWidthProperty() {
/*     */     return this.radarWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setRadarHeight(double height) {
/* 158 */     if (height <= 0.0D) {
/* 159 */       throw new IllegalArgumentException("height must be larger than 0 but was " + height);
/*     */     }
/*     */     
/* 162 */     this.radarHeight.set(height);
/*     */   }
/*     */   
/*     */   public final double getRadarWidth() {
/*     */     return this.radarWidth.get();
/*     */   }
/*     */   
/*     */   public final void setRadarWidth(double width) {
/*     */     if (width <= 0.0D)
/*     */       throw new IllegalArgumentException("width must be larger than 0 but was " + width); 
/*     */     this.radarWidth.set(width);
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\RadarView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */