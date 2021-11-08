/*    */ package com.flexganttfx.extras.util;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class Messages
/*    */ {
/*    */   private static final String BUNDLE_NAME = "com.flexganttfx.extras.util.messages";
/* 21 */   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("com.flexganttfx.extras.util.messages");
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
/*    */   public static String getString(String key) {
/*    */     try {
/* 35 */       return RESOURCE_BUNDLE.getString(key);
/* 36 */     } catch (MissingResourceException e) {
/* 37 */       return '!' + key + '!';
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extra\\util\Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */