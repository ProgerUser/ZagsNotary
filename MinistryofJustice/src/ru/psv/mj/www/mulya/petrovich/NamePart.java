package ru.psv.mj.www.mulya.petrovich;

/**
 * ����� �����
 */
public enum NamePart {

	LASTNAME("lastname"),
	FIRSTNAME("firstname"),
	MIDDLENAME("middlename");

	private String value;

	public String getValue() {
		return value;
	}

	NamePart(String value) {
		this.value = value;
	}
}
