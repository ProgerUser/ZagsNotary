package mj.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ZAGSLIST {
	private StringProperty ZAGS_NAME;

	/* CONSTRUCTOR */
	public ZAGSLIST() {
		this.ZAGS_NAME = new SimpleStringProperty();
	}

	/* ZAGS_NAME */
	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}
}
