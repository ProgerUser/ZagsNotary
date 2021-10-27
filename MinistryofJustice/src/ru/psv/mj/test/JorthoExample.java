package ru.psv.mj.test;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;

import javax.swing.*;

public class JorthoExample {

	public static void main(String[] args) {

		SpellCheckExampleUi ui = new SpellCheckExampleUi();
		ui.getTextComponent().setText(null);
		
		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());

		SpellChecker.registerDictionaries(JorthoExample.class.getResource("/dictionary_ru.ortho"), "ru");
		SpellChecker.register(ui.getTextComponent());

		SpellCheckerOptions sco = new SpellCheckerOptions();
		sco.setCaseSensitive(true);
		sco.setSuggestionsLimitMenu(15);

		JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
		ui.getTextComponent().setComponentPopupMenu(popup);

		ui.showUI();

	}
}