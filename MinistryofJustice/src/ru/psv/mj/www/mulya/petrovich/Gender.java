package ru.psv.mj.www.mulya.petrovich;

/**
 * ���
 */
public enum Gender {
	MALE("male"),
	FEMALE("female"),
	ANDROGYNOUS("androgynous");

	private String value;

	public String getValue() {
		return value;
	}

	Gender(String value) {
		this.value = value;
	}
}
