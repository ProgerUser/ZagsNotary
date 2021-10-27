package ru.psv.mj.notary.doc.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LIST {

	private LongProperty CODE;

	private StringProperty NAME;

	public LIST() {
		this.CODE = new SimpleLongProperty();
		this.NAME = new SimpleStringProperty();
	}

	public void setCODE(Long CODE) {
		this.CODE.set(CODE);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public Long getCODE() {
		return CODE.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public LongProperty CODEProperty() {
		return CODE;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}
}
