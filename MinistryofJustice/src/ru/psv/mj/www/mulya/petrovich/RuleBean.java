package ru.psv.mj.www.mulya.petrovich;

import java.util.List;

/**
 * User: mulya
 * Date: 25/09/2014
 */
public class RuleBean {
	/**
	 * ���
	 */
	private String gender;
	/**
	 * ������� ���������
	 */
	private List<String> mods;
	/**
	 * ������� ����������
	 */
	private List<String> test;

	public RuleBean() {
	}

	public RuleBean(String gender, List<String> mods, List<String> test) {
		this.gender = gender;
		this.mods = mods;
		this.test = test;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getMods() {
		return mods;
	}

	public void setMods(List<String> mods) {
		this.mods = mods;
	}

	public List<String> getTest() {
		return test;
	}

	public void setTest(List<String> test) {
		this.test = test;
	}
}
