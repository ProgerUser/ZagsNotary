/*     */ package mj.init;
/*     */ 
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.Tooltip;
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
/*     */ public class Decorator
/*     */ {
/*     */   private Node decorator;
/*     */   private Pos position;
/*     */   private double xOffset;
/*     */   private double yOffset;
/*     */   
/*     */   public Decorator(Node decorator, Pos position) {
/*  42 */     this(decorator, position, 0.0D, 0.0D);
/*     */   }
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
/*     */   public Decorator(Node decorator, Pos position, double xOffset, double yOffset) {
/*  55 */     setNode(decorator);
/*  56 */     setPosition(position);
/*  57 */     setXOffset(xOffset);
/*  58 */     setYOffset(yOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode() {
/*  68 */     return this.decorator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNode(Node decorator) {
/*  78 */     this.decorator = decorator;
/*     */   }
/*     */ 
/*     */   
/*     */   public Pos getPosition() {
/*  83 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(Pos position) {
/*  88 */     this.position = position;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getXOffset() {
/*  93 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXOffset(double xOffset) {
/*  98 */     this.xOffset = xOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 103 */     return this.yOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYOffset(double yOffset) {
/* 108 */     this.yOffset = yOffset;
/*     */   }
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
/*     */   public static Node createWarningDecorator(String tooltipText) {
/* 121 */     Label n = new Label(tooltipText);
/* 122 */     n.getStyleClass().add("validation-warning");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     n.setTooltip(new Tooltip(n.getText()));
/* 128 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Decorator createWarningDecorator(String tooltipText, Pos position, double xOffset, double yOffset) {
/* 133 */     return new Decorator(createWarningDecorator(tooltipText), position, xOffset, yOffset);
/*     */   }
/*     */ }


/* Location:              C:\Users\Said.000\Desktop\syntheticaFX_0.8.2_eval\syntheticaFX.jar!\com\jyloo\syntheticafx\validation\Decorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */