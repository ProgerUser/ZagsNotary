package ru.psv.mj.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class INFR_TYPE {
	private StringProperty NAME;
	private StringProperty SHORT_NAME;

	/* CONSTRUCTOR */
	public INFR_TYPE() {
		this.NAME = new SimpleStringProperty();
		this.SHORT_NAME = new SimpleStringProperty();
	}

	/* NAME */
	public String getNAME() {
		return NAME.get();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	/* SHORT_NAME */
	public String getSHORT_NAME() {
		return SHORT_NAME.get();
	}

	public void setSHORT_NAME(String SHORT_NAME) {
		this.SHORT_NAME.set(SHORT_NAME);
	}

	public StringProperty SHORT_NAMEProperty() {
		return SHORT_NAME;
	}

}
