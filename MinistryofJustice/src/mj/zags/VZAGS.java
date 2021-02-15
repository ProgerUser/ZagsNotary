package mj.zags;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VZAGS {
	/** Нет данных */
	private StringProperty ZAGS_RUK;
	/** Нет данных */
	private StringProperty ZAGS_NAME;
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private IntegerProperty ZAGS_ID;

	public VZAGS() {
		this.ZAGS_RUK = new SimpleStringProperty();
		this.ZAGS_NAME = new SimpleStringProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.ZAGS_ID = new SimpleIntegerProperty();
	}

	public void setZAGS_RUK(String ZAGS_RUK) {
		this.ZAGS_RUK.set(ZAGS_RUK);
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public void setZAGS_ID(Integer ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public String getZAGS_RUK() {
		return ZAGS_RUK.get();
	}

	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public Integer getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public StringProperty ZAGS_RUKProperty() {
		return ZAGS_RUK;
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

	public IntegerProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}
}
