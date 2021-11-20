package com.flexganttfx.extras.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	@SuppressWarnings("unused")
	private static final String BUNDLE_NAME = "com.flexganttfx.extras.util.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle("com.flexganttfx.extras.util.messages");

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
