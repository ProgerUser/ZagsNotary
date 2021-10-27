package ru.psv.mj.notary.client.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class COUNTRIES {
	/** Название */
	private StringProperty NAME;
	/** Код */
	private LongProperty CODE;

	public COUNTRIES() {
		this.NAME = new SimpleStringProperty();
		this.CODE = new SimpleLongProperty();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setCODE(Long CODE) {
		this.CODE.set(CODE);
	}

	public String getNAME() {
		return NAME.get();
	}

	public Long getCODE() {
		return CODE.get();
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public LongProperty CODEProperty() {
		return CODE;
	}
}
