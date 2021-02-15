package mj.notary;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VNOTARY {
	/** Нет данных */
	private StringProperty NOT_RUK;
	/** Нет данных */
	private StringProperty NOT_NAME;
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private IntegerProperty NOT_ID;

	public VNOTARY() {
		this.NOT_RUK = new SimpleStringProperty();
		this.NOT_NAME = new SimpleStringProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.NOT_ID = new SimpleIntegerProperty();
	}

	public void setNOT_RUK(String NOT_RUK) {
		this.NOT_RUK.set(NOT_RUK);
	}

	public void setNOT_NAME(String NOT_NAME) {
		this.NOT_NAME.set(NOT_NAME);
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
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

	public String getCOTDNAME() {
		return COTDNAME.get();
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

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

	public IntegerProperty NOT_IDProperty() {
		return NOT_ID;
	}
}
