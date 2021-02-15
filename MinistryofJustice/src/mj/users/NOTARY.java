package mj.users;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NOTARY {
	/** Руководитель */
	private StringProperty NOT_RUK;
	/** Наименование ЗАГСа */
	private StringProperty NOT_NAME;
	/** Город */
	private IntegerProperty NOT_OTD;
	/** ИД */
	private IntegerProperty NOT_ID;

	public NOTARY() {
		this.NOT_RUK = new SimpleStringProperty();
		this.NOT_NAME = new SimpleStringProperty();
		this.NOT_OTD = new SimpleIntegerProperty();
		this.NOT_ID = new SimpleIntegerProperty();
	}

	public void setNOT_RUK(String NOT_RUK) {
		this.NOT_RUK.set(NOT_RUK);
	}

	public void setNOT_NAME(String NOT_NAME) {
		this.NOT_NAME.set(NOT_NAME);
	}

	public void setNOT_OTD(Integer NOT_OTD) {
		this.NOT_OTD.set(NOT_OTD);
	}

	public void setNOT_ID(Integer NOT_ID) {
		this.NOT_ID.set(NOT_ID);
	}

	public String getNOT_RUK() {
		return NOT_RUK.get();
	}

	public String getNOT_NAME() {
		return NOT_NAME.get();
	}

	public Integer getNOT_OTD() {
		return NOT_OTD.get();
	}

	public Integer getNOT_ID() {
		return NOT_ID.get();
	}

	public StringProperty NOT_RUKProperty() {
		return NOT_RUK;
	}

	public StringProperty NOT_NAMEProperty() {
		return NOT_NAME;
	}

	public IntegerProperty NOT_OTDProperty() {
		return NOT_OTD;
	}

	public IntegerProperty NOT_IDProperty() {
		return NOT_ID;
	}
}
