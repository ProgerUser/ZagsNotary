package ru.psv.mj.www.mulya.petrovich;

/**
 * �����
 */
public enum Case {

	GENITIVE(0),
	DATIVE(1),
	ACCUSATIVE(2),
	INSTRUMENTAL(3),
	PREPOSITIONAL(4);

	private int value;

	public int getValue() {
		return value;
	}

	Case(int value) {
		this.value = value;
	}
}
