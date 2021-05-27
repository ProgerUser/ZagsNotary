package com.mulya;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.fasterxml.jackson.jr.ob.JSON;
import com.mulya.beans.NameBean;
import com.mulya.beans.RootBean;
import com.mulya.beans.RuleBean;
import com.mulya.enums.Case;
import com.mulya.enums.Gender;
import com.mulya.enums.NamePart;

/**
 * User: mulya Date: 28/09/2014
 */
public class PetrovichDeclinationMaker {

	private static final String DEFAULT_PATH_TO_RULES_FILE = "/rules.json";
	private static final String MODS_KEEP_IT_ALL_SYMBOL = ".";
	private static final String MODS_REMOVE_LETTER_SYMBOL = "-";

	private RootBean rootRulesBean;

	public GenderCurryedMaker male = new GenderCurryedMaker(Gender.MALE);
	public GenderCurryedMaker female = new GenderCurryedMaker(Gender.FEMALE);
	public GenderCurryedMaker androgynous = new GenderCurryedMaker(Gender.ANDROGYNOUS);

	private PetrovichDeclinationMaker(String pathToRulesFile) throws IOException {

		InputStream is = getClass().getResourceAsStream(DEFAULT_PATH_TO_RULES_FILE);
		// rootRulesBean = JSON.std.beanFrom(RootBean.class, new
		// String(Files.readAllBytes(Paths.get(pathToRulesFile))));
		// rootRulesBean = JSON.std.beanFrom(RootBean.class, IOUtils.toString(is,
		// StandardCharsets.UTF_8));
		rootRulesBean = JSON.std.beanFrom(RootBean.class, jsons);

	}

	public static PetrovichDeclinationMaker getInstance() throws IOException {
		return getInstance(DEFAULT_PATH_TO_RULES_FILE);
	}

	public static PetrovichDeclinationMaker getInstance(String pathToRulesFile) throws IOException {
		return new PetrovichDeclinationMaker(pathToRulesFile);
	}

	public String make(NamePart namePart, Gender gender, Case caseToUse, String originalName) {
		String result = originalName;
		NameBean nameBean;

//		switch (namePart) {
//		case FIRSTNAME:
//			nameBean = rootRulesBean.getFirstname();
//			break;
//		case LASTNAME:
//			nameBean = rootRulesBean.getLastname();
//			break;
//		case MIDDLENAME:
//			nameBean = rootRulesBean.getMiddlename();
//			break;
//		default:
//			nameBean = rootRulesBean.getMiddlename();
//			break;
//		}

		if (namePart == NamePart.FIRSTNAME) {
			nameBean = rootRulesBean.getFirstname();
		} else if (namePart == NamePart.LASTNAME) {
			nameBean = rootRulesBean.getLastname();
		} else if (namePart == NamePart.MIDDLENAME) {
			nameBean = rootRulesBean.getMiddlename();
		} else {
			nameBean = rootRulesBean.getMiddlename();
		}

		RuleBean ruleToUse = null;
		RuleBean exceptionRuleBean = findInRuleBeanList(nameBean.getExceptions(), gender, originalName);
		RuleBean suffixRuleBean = findInRuleBeanList(nameBean.getSuffixes(), gender, originalName);
		if (exceptionRuleBean != null && exceptionRuleBean.getGender().equals(gender.getValue())) {
			ruleToUse = exceptionRuleBean;
		} else if (suffixRuleBean != null && suffixRuleBean.getGender().equals(gender.getValue())) {
			ruleToUse = suffixRuleBean;
		} else {
			ruleToUse = exceptionRuleBean != null ? exceptionRuleBean : suffixRuleBean;
		}

		if (ruleToUse != null) {
			String modToApply = ruleToUse.getMods().get(caseToUse.getValue());
			result = applyModToName(modToApply, originalName);
		}

		return result;
	}

	protected String applyModToName(String modToApply, String name) {
		String result = name;
		if (!modToApply.equals(MODS_KEEP_IT_ALL_SYMBOL)) {
			if (modToApply.contains(MODS_REMOVE_LETTER_SYMBOL)) {
				for (int i = 0; i < modToApply.length(); i++) {
					if (Character.toString(modToApply.charAt(i)).equals(MODS_REMOVE_LETTER_SYMBOL)) {
						result = result.substring(0, result.length() - 1);
					} else {
						result += modToApply.substring(i);
						break;
					}
				}
			} else {
				result = name + modToApply;
			}
		}
		return result;
	}

	protected RuleBean findInRuleBeanList(List<RuleBean> ruleBeanList, Gender gender, String originalName) {
		RuleBean result = null;
		if (ruleBeanList != null) {
			out: for (RuleBean ruleBean : ruleBeanList) {
				for (String test : ruleBean.getTest()) {
					if (originalName.endsWith(test)) {
						if (ruleBean.getGender().equals(Gender.ANDROGYNOUS.getValue())) {
							result = ruleBean;
							break out;
						} else if ((ruleBean.getGender().equals(gender.getValue()))) {
							result = ruleBean;
							break out;
						}
					}
				}
			}
		}

		return result;
	}

	protected class GenderCurryedMaker {
		private Gender gender;

		protected GenderCurryedMaker(Gender gender) {
			this.gender = gender;
		}

		public GenderAndNamePartCurryedMaker firstname() {
			return new GenderAndNamePartCurryedMaker(gender, NamePart.FIRSTNAME);
		}

		public GenderAndNamePartCurryedMaker lastname() {
			return new GenderAndNamePartCurryedMaker(gender, NamePart.LASTNAME);
		}

		public GenderAndNamePartCurryedMaker middlename() {
			return new GenderAndNamePartCurryedMaker(gender, NamePart.MIDDLENAME);
		}
	}

	protected class GenderAndNamePartCurryedMaker {
		private NamePart namePart;
		private Gender gender;

		protected GenderAndNamePartCurryedMaker(Gender gender, NamePart namePart) {
			this.gender = gender;
			this.namePart = namePart;
		}

		public String toGenitive(String name) {
			return make(namePart, gender, Case.GENITIVE, name);
		}

		public String toDative(String name) {
			return make(namePart, gender, Case.DATIVE, name);
		}

		public String toAccusative(String name) {
			return make(namePart, gender, Case.ACCUSATIVE, name);
		}

		public String toInstrumental(String name) {
			return make(namePart, gender, Case.INSTRUMENTAL, name);
		}

		public String toPrepositional(String name) {
			return make(namePart, gender, Case.PREPOSITIONAL, name);
		}
	}

	private static final String jsons = "{\n" + "  \"lastname\": {\n" + " \"exceptions\": [\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"бонч\",\n" + "     \"абдул\",\n"
			+ "     \"белиц\",\n" + "     \"гасан\",\n" + "     \"дюссар\",\n" + "     \"дюмон\",\n"
			+ "     \"книппер\",\n" + "     \"корвин\",\n" + "     \"ван\",\n" + "     \"шолом\",\n" + "     \"тер\",\n"
			+ "     \"призван\",\n" + "     \"мелик\",\n" + "     \"вар\",\n" + "     \"фон\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ],\n" + "   \"tags\": [\n" + "     \"first_word\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"дюма\",\n"
			+ "     \"тома\",\n" + "     \"дега\",\n" + "     \"люка\",\n" + "     \"ферма\",\n" + "     \"гамарра\",\n"
			+ "     \"петипа\",\n" + "     \"шандра\",\n" + "     \"скал€\",\n" + "     \"каруана\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"гусь\",\n" + "     \"ремень\",\n" + "     \"камень\",\n" + "     \"онук\",\n"
			+ "     \"богода\",\n" + "     \"нечипас\",\n" + "     \"долгопалец\",\n" + "     \"маненок\",\n"
			+ "     \"рева\",\n" + "     \"кива\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"вий\",\n" + "     \"сой\",\n"
			+ "     \"цой\",\n" + "     \"хой\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-€\",\n"
			+ "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"€\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   }\n" + " ],\n" + " \"suffixes\": [\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"б\",\n" + "     \"в\",\n" + "     \"г\",\n"
			+ "     \"д\",\n" + "     \"ж\",\n" + "     \"з\",\n" + "     \"й\",\n" + "     \"к\",\n" + "     \"л\",\n"
			+ "     \"м\",\n" + "     \"н\",\n" + "     \"п\",\n" + "     \"р\",\n" + "     \"с\",\n" + "     \"т\",\n"
			+ "     \"ф\",\n" + "     \"х\",\n" + "     \"ц\",\n" + "     \"ч\",\n" + "     \"ш\",\n" + "     \"щ\",\n"
			+ "     \"ъ\",\n" + "     \"ь\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"гава\",\n" + "     \"орота\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n"
			+ "     \"ска\",\n" + "     \"цка\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-ой\",\n"
			+ "     \"-ой\",\n" + "     \"-ую\",\n" + "     \"-ой\",\n" + "     \"-ой\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"цка€\",\n" + "     \"ска€\",\n"
			+ "     \"на€\",\n" + "     \"а€\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--ой\",\n"
			+ "     \"--ой\",\n" + "     \"--ую\",\n" + "     \"--ой\",\n" + "     \"--ой\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"€€\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--ей\",\n" + "     \"--ей\",\n" + "     \"--юю\",\n" + "     \"--ей\",\n"
			+ "     \"--ей\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n"
			+ "     \"на\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-ой\",\n" + "     \"-ой\",\n"
			+ "     \"-у\",\n" + "     \"-ой\",\n" + "     \"-ой\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"иной\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n" + "     \"-е\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"уй\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"ца\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-ы\",\n"
			+ "     \"-е\",\n" + "     \"-у\",\n" + "     \"-ей\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"рих\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n" + "     \"а\",\n" + "     \"ом\",\n"
			+ "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"и€\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"иа\",\n" + "     \"аа\",\n"
			+ "     \"оа\",\n" + "     \"уа\",\n" + "     \"ыа\",\n" + "     \"еа\",\n" + "     \"юа\",\n"
			+ "     \"эа\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"их\",\n" + "     \"ых\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"о\",\n" + "     \"е\",\n"
			+ "     \"э\",\n" + "     \"и\",\n" + "     \"ы\",\n" + "     \"у\",\n" + "     \"ю\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n"
			+ "     \"ова\",\n" + "     \"ева\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-ой\",\n"
			+ "     \"-ой\",\n" + "     \"-у\",\n" + "     \"-ой\",\n" + "     \"-ой\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"га\",\n" + "     \"ка\",\n"
			+ "     \"ха\",\n" + "     \"ча\",\n" + "     \"ща\",\n" + "     \"жа\",\n" + "     \"ша\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-е\",\n" + "     \"-у\",\n" + "     \"-ой\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"а\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-ы\",\n" + "     \"-е\",\n"
			+ "     \"-у\",\n" + "     \"-ой\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ь\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n" + "     \"-е\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"и€\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-и\",\n" + "     \"-ю\",\n"
			+ "     \"-ей\",\n" + "     \"-и\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"€\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-и\",\n" + "     \"-е\",\n" + "     \"-ю\",\n" + "     \"-ей\",\n" + "     \"-е\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ей\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"€н\",\n" + "     \"ан\",\n" + "     \"йн\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"а\",\n"
			+ "     \"у\",\n" + "     \"а\",\n" + "     \"ом\",\n" + "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ынец\",\n" + "     \"обец\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--ца\",\n" + "     \"--цу\",\n" + "     \"--ца\",\n" + "     \"--цем\",\n"
			+ "     \"--це\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"онец\",\n" + "     \"овец\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--ца\",\n"
			+ "     \"--цу\",\n" + "     \"--ца\",\n" + "     \"--цом\",\n" + "     \"--це\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ай\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"кой\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-го\",\n" + "     \"-му\",\n"
			+ "     \"-го\",\n" + "     \"--им\",\n" + "     \"-м\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"гой\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-го\",\n" + "     \"-му\",\n" + "     \"-го\",\n" + "     \"--им\",\n" + "     \"-м\"\n"
			+ "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ой\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"-го\",\n" + "     \"-му\",\n" + "     \"-го\",\n"
			+ "     \"--ым\",\n" + "     \"-м\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"ах\",\n" + "     \"ив\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"а\",\n"
			+ "     \"у\",\n" + "     \"а\",\n" + "     \"ом\",\n" + "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ший\",\n" + "     \"щий\",\n"
			+ "     \"жий\",\n" + "     \"ний\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--его\",\n"
			+ "     \"--ему\",\n" + "     \"--его\",\n" + "     \"-м\",\n" + "     \"--ем\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ый\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--ого\",\n" + "     \"--ому\",\n" + "     \"--ого\",\n" + "     \"-м\",\n"
			+ "     \"--ом\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"кий\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--ого\",\n" + "     \"--ому\",\n"
			+ "     \"--ого\",\n" + "     \"-м\",\n" + "     \"--ом\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ий\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n" + "     \"-и\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ок\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--ка\",\n" + "     \"--ку\",\n" + "     \"--ка\",\n" + "     \"--ком\",\n"
			+ "     \"--ке\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"ец\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--ца\",\n" + "     \"--цу\",\n"
			+ "     \"--ца\",\n" + "     \"--цом\",\n" + "     \"--це\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ц\",\n" + "     \"ч\",\n" + "     \"ш\",\n"
			+ "     \"щ\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n" + "     \"а\",\n"
			+ "     \"ем\",\n" + "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"ен\",\n" + "     \"нн\",\n" + "     \"он\",\n" + "     \"ун\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n" + "     \"а\",\n" + "     \"ом\",\n"
			+ "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"в\",\n" + "     \"н\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n"
			+ "     \"а\",\n" + "     \"ым\",\n" + "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"б\",\n" + "     \"г\",\n" + "     \"д\",\n"
			+ "     \"ж\",\n" + "     \"з\",\n" + "     \"к\",\n" + "     \"л\",\n" + "     \"м\",\n" + "     \"п\",\n"
			+ "     \"р\",\n" + "     \"с\",\n" + "     \"т\",\n" + "     \"ф\",\n" + "     \"х\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n" + "     \"а\",\n" + "     \"ом\",\n"
			+ "     \"е\"\n" + "   ]\n" + "   }\n" + " ]\n" + "  },\n" + "  \"firstname\": {\n" + " \"exceptions\": [\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"лев\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--ьва\",\n" + "     \"--ьву\",\n" + "     \"--ьва\",\n"
			+ "     \"--ьвом\",\n" + "     \"--ьве\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"пЄтр\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"---етра\",\n"
			+ "     \"---етру\",\n" + "     \"---етра\",\n" + "     \"---етром\",\n" + "     \"---етре\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"павел\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--ла\",\n" + "     \"--лу\",\n" + "     \"--ла\",\n" + "     \"--лом\",\n"
			+ "     \"--ле\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"€ша\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-е\",\n" + "     \"-у\",\n"
			+ "     \"-ей\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"шота\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"рашель\",\n" + "     \"нинель\",\n"
			+ "     \"николь\",\n" + "     \"габриэль\",\n" + "     \"даниэль\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n"
			+ "   }\n" + " ],\n" + " \"suffixes\": [\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"е\",\n" + "     \"Є\",\n" + "     \"и\",\n" + "     \"о\",\n"
			+ "     \"у\",\n" + "     \"ы\",\n" + "     \"э\",\n" + "     \"ю\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"б\",\n"
			+ "     \"в\",\n" + "     \"г\",\n" + "     \"д\",\n" + "     \"ж\",\n" + "     \"з\",\n" + "     \"й\",\n"
			+ "     \"к\",\n" + "     \"л\",\n" + "     \"м\",\n" + "     \"н\",\n" + "     \"п\",\n" + "     \"р\",\n"
			+ "     \"с\",\n" + "     \"т\",\n" + "     \"ф\",\n" + "     \"х\",\n" + "     \"ц\",\n" + "     \"ч\",\n"
			+ "     \"ш\",\n" + "     \"щ\",\n" + "     \"ъ\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"ь\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-и\",\n" + "     \"-и\",\n" + "     \".\",\n" + "     \"ю\",\n" + "     \"-и\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ь\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"га\",\n" + "     \"ка\",\n" + "     \"ха\",\n" + "     \"ча\",\n"
			+ "     \"ща\",\n" + "     \"жа\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-е\",\n"
			+ "     \"-у\",\n" + "     \"-ой\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"ша\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-и\",\n" + "     \"-е\",\n" + "     \"-у\",\n" + "     \"-ей\",\n" + "     \"-е\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"test\": [\n" + "     \"а\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"-ы\",\n" + "     \"-е\",\n" + "     \"-у\",\n"
			+ "     \"-ой\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n"
			+ "   \"test\": [\n" + "     \"и€\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-и\",\n"
			+ "     \"-и\",\n" + "     \"-ю\",\n" + "     \"-ей\",\n" + "     \"-и\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n" + "     \"а\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-ы\",\n" + "     \"-е\",\n" + "     \"-у\",\n" + "     \"-ой\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n"
			+ "     \"€\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-е\",\n" + "     \"-ю\",\n"
			+ "     \"-ей\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"и€\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-и\",\n"
			+ "     \"-и\",\n" + "     \"-ю\",\n" + "     \"-ей\",\n" + "     \"-и\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"€\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-е\",\n" + "     \"-ю\",\n" + "     \"-ей\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"ей\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n"
			+ "     \"-ем\",\n" + "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"test\": [\n" + "     \"ий\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-€\",\n"
			+ "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n" + "     \"-и\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"й\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-€\",\n" + "     \"-ю\",\n" + "     \"-€\",\n" + "     \"-ем\",\n"
			+ "     \"-е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n"
			+ "     \"б\",\n" + "     \"в\",\n" + "     \"г\",\n" + "     \"д\",\n" + "     \"ж\",\n" + "     \"з\",\n"
			+ "     \"к\",\n" + "     \"л\",\n" + "     \"м\",\n" + "     \"н\",\n" + "     \"п\",\n" + "     \"р\",\n"
			+ "     \"с\",\n" + "     \"т\",\n" + "     \"ф\",\n" + "     \"х\",\n" + "     \"ц\",\n" + "     \"ч\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n" + "     \"а\",\n" + "     \"ом\",\n"
			+ "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"test\": [\n" + "     \"ни€\",\n" + "     \"ри€\",\n" + "     \"ви€\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-и\",\n" + "     \"-и\",\n" + "     \"-ю\",\n" + "     \"-ем\",\n"
			+ "     \"-ем\"\n" + "   ]\n" + "   }\n" + " ]\n" + "  },\n" + "  \"middlename\": {\n"
			+ " \"suffixes\": [\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"test\": [\n" + "     \"ич\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"а\",\n" + "     \"у\",\n" + "     \"а\",\n" + "     \"ем\",\n"
			+ "     \"е\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"test\": [\n"
			+ "     \"на\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-ы\",\n" + "     \"-е\",\n" + "     \"-у\",\n"
			+ "     \"-ой\",\n" + "     \"-е\"\n" + "   ]\n" + "   }\n" + " ]\n" + "  }\n" + "}";
}
