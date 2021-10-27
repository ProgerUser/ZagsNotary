package ru.psv.mj.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.inet.jortho.LanguageBundle;
import com.inet.jortho.SpellChecker;

public class Checker {

	private static Map<String, Method> methods;

	public static void main(String[] args)
			throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
		SpellChecker.registerDictionaries(Checker.class.getResource("/dictionary_ru.ortho"), "ru");

		methods = new HashMap<>();

		setAccessibleMethod(LanguageBundle.class, "get", Locale.class);
		setAccessibleMethod(LanguageBundle.class, "existInDictionary", String.class,
				Checker.class.getClassLoader().loadClass("com.inet.jortho.Dictionary"),
				com.inet.jortho.SpellCheckerOptions.class, boolean.class);
		setAccessibleMethod(SpellChecker.class, "getCurrentDictionary");

		while (SpellChecker.getCurrentLocale() == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Object dictionary = invokeMethod(SpellChecker.class, "getCurrentDictionary", null);

		LanguageBundle bundle = (LanguageBundle) invokeMethod(LanguageBundle.class, "get", null,
				SpellChecker.getCurrentLocale());

		Set<String> errors = new HashSet<>();
		StringTokenizer st = new StringTokenizer("Приходить не надо фыв фыв фы в");
		boolean newSentence = true;
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			boolean b = true;
			boolean nextNewSentence = false;
			if (word.length() > 1) {
				if ('.' == word.charAt(word.length() - 1)) {
					nextNewSentence = true;
					word = word.substring(0, word.length() - 1);
				}
				b = (Boolean) invokeMethod(LanguageBundle.class, "existInDictionary", bundle, word, dictionary,
						SpellChecker.getOptions(), newSentence);
			}
			if (!b)
				errors.add(word);
			newSentence = nextNewSentence;
		}
		System.out.println(StringUtils.join(errors, " , "));
	}

	private static void setAccessibleMethod(Class<?> cls, String name, Class<?>... parameterTypes)
			throws NoSuchMethodException {
		Method method = cls.getDeclaredMethod(name, parameterTypes);
		method.setAccessible(true);
		methods.put(cls.getName() + "." + name, method);
	}

	private static Object invokeMethod(Class<?> cls, String name, Object obj, Object... args)
			throws InvocationTargetException, IllegalAccessException {
		return methods.get(cls.getName() + "." + name).invoke(obj, args);
	}

}