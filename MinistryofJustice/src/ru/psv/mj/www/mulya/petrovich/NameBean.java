package ru.psv.mj.www.mulya.petrovich;

import java.util.List;

/**
 * User: mulya Date: 25/09/2014
 */
public class NameBean {
	/**
	 * ����������
	 */
	private List<RuleBean> exceptions;
	/**
	 * �������
	 */
	private List<RuleBean> suffixes;

	public List<RuleBean> getExceptions() {
		return exceptions;
	}

	public NameBean() {
	}

	public NameBean(List<RuleBean> exceptions, List<RuleBean> suffixes) {
		this.exceptions = exceptions;
		this.suffixes = suffixes;
	}

	public void setExceptions(List<RuleBean> exceptions) {
		this.exceptions = exceptions;
	}

	public List<RuleBean> getSuffixes() {
		return suffixes;
	}

	public void setSuffixes(List<RuleBean> suffixes) {
		this.suffixes = suffixes;
	}
}
