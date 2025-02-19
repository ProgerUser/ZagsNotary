package ru.psv.mj.www.mulya.petrovich;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.jr.ob.JSON;

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

//		InputStream is = getClass().getResourceAsStream(DEFAULT_PATH_TO_RULES_FILE);
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
			+ "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"����\",\n" + "     \"�����\",\n"
			+ "     \"�����\",\n" + "     \"�����\",\n" + "     \"������\",\n" + "     \"�����\",\n"
			+ "     \"�������\",\n" + "     \"������\",\n" + "     \"���\",\n" + "     \"�����\",\n" + "     \"���\",\n"
			+ "     \"�������\",\n" + "     \"�����\",\n" + "     \"���\",\n" + "     \"���\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ],\n" + "   \"tags\": [\n" + "     \"first_word\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"����\",\n"
			+ "     \"����\",\n" + "     \"����\",\n" + "     \"����\",\n" + "     \"�����\",\n" + "     \"�������\",\n"
			+ "     \"������\",\n" + "     \"������\",\n" + "     \"�����\",\n" + "     \"�������\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"����\",\n" + "     \"������\",\n" + "     \"������\",\n" + "     \"����\",\n"
			+ "     \"������\",\n" + "     \"�������\",\n" + "     \"����������\",\n" + "     \"�������\",\n"
			+ "     \"����\",\n" + "     \"����\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"���\",\n" + "     \"���\",\n"
			+ "     \"���\",\n" + "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   }\n" + " ],\n" + " \"suffixes\": [\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"����\",\n" + "     \"�����\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"���\",\n" + "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-��\",\n"
			+ "     \"-��\",\n" + "     \"-��\",\n" + "     \"-��\",\n" + "     \"-��\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"����\",\n" + "     \"����\",\n"
			+ "     \"���\",\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--��\",\n"
			+ "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\",\n"
			+ "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-��\",\n" + "     \"-��\",\n"
			+ "     \"-�\",\n" + "     \"-��\",\n" + "     \"-��\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"����\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"���\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n"
			+ "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\",\n" + "     \"��\",\n"
			+ "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n"
			+ "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\",\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n"
			+ "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"���\",\n" + "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-��\",\n"
			+ "     \"-��\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-��\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\",\n" + "     \"��\",\n"
			+ "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"��\",\n" + "     \"��\",\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n" + "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"����\",\n" + "     \"����\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--���\",\n"
			+ "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"����\",\n" + "     \"����\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--��\",\n"
			+ "     \"--��\",\n" + "     \"--��\",\n" + "     \"--���\",\n" + "     \"--��\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-��\",\n" + "     \"-��\",\n"
			+ "     \"-��\",\n" + "     \"--��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-��\",\n" + "     \"-��\",\n" + "     \"-��\",\n" + "     \"--��\",\n" + "     \"-�\"\n"
			+ "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"-��\",\n" + "     \"-��\",\n" + "     \"-��\",\n"
			+ "     \"--��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\",\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n" + "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"���\",\n" + "     \"���\",\n"
			+ "     \"���\",\n" + "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--���\",\n"
			+ "     \"--���\",\n" + "     \"--���\",\n" + "     \"-�\",\n" + "     \"--��\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--���\",\n" + "     \"--���\",\n" + "     \"--���\",\n" + "     \"-�\",\n"
			+ "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--���\",\n" + "     \"--���\",\n"
			+ "     \"--���\",\n" + "     \"-�\",\n" + "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--���\",\n"
			+ "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"--��\",\n" + "     \"--��\",\n"
			+ "     \"--��\",\n" + "     \"--���\",\n" + "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"��\",\n" + "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n"
			+ "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"�\",\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"��\",\n" + "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n"
			+ "     \"�\"\n" + "   ]\n" + "   }\n" + " ]\n" + "  },\n" + "  \"firstname\": {\n" + " \"exceptions\": [\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"���\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--���\",\n" + "     \"--���\",\n" + "     \"--���\",\n"
			+ "     \"--����\",\n" + "     \"--���\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"---����\",\n"
			+ "     \"---����\",\n" + "     \"---����\",\n" + "     \"---�����\",\n" + "     \"---����\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�����\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--��\",\n" + "     \"--���\",\n"
			+ "     \"--��\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"���\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"����\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"������\",\n" + "     \"������\",\n"
			+ "     \"������\",\n" + "     \"��������\",\n" + "     \"�������\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n"
			+ "   }\n" + " ],\n" + " \"suffixes\": [\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \".\",\n"
			+ "     \".\",\n" + "     \".\",\n" + "     \".\",\n" + "     \".\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \".\",\n" + "     \"�\",\n" + "     \"-�\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n" + "     \"��\",\n"
			+ "     \"��\",\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n"
			+ "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n"
			+ "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"�\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n"
			+ "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   },\n"
			+ "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"�\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n"
			+ "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n"
			+ "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"androgynous\",\n"
			+ "   \"ru.psv.mj.test\": [\n" + "     \"���\",\n" + "     \"���\",\n" + "     \"���\"\n" + "   ],\n"
			+ "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-��\",\n"
			+ "     \"-��\"\n" + "   ]\n" + "   }\n" + " ]\n" + "  },\n" + "  \"middlename\": {\n"
			+ " \"suffixes\": [\n" + "   {\n" + "   \"gender\": \"male\",\n" + "   \"ru.psv.mj.test\": [\n" + "     \"��\"\n"
			+ "   ],\n" + "   \"mods\": [\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"�\",\n" + "     \"��\",\n"
			+ "     \"�\"\n" + "   ]\n" + "   },\n" + "   {\n" + "   \"gender\": \"female\",\n" + "   \"ru.psv.mj.test\": [\n"
			+ "     \"��\"\n" + "   ],\n" + "   \"mods\": [\n" + "     \"-�\",\n" + "     \"-�\",\n" + "     \"-�\",\n"
			+ "     \"-��\",\n" + "     \"-�\"\n" + "   ]\n" + "   }\n" + " ]\n" + "  }\n" + "}";
}
