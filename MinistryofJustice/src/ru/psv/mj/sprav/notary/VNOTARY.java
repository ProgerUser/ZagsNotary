package ru.psv.mj.sprav.notary;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VNOTARY {
	/** Нет данных */
	private LongProperty NOT_ID;
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private StringProperty NOT_NAME;
	/** Нет данных */
	private StringProperty NOT_RUK;
	/** Нет данных */
	private StringProperty NOT_ADDRESS;
	/** Нет данных */
	private StringProperty NOT_TELEPHONE;

	public VNOTARY() {
		this.NOT_ID = new SimpleLongProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.NOT_NAME = new SimpleStringProperty();
		this.NOT_RUK = new SimpleStringProperty();
		this.NOT_ADDRESS = new SimpleStringProperty();
		this.NOT_TELEPHONE = new SimpleStringProperty();
	}

	public void setNOT_ID(Long NOT_ID) {
		this.NOT_ID.set(NOT_ID);
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public void setNOT_NAME(String NOT_NAME) {
		this.NOT_NAME.set(NOT_NAME);
	}

	public void setNOT_RUK(String NOT_RUK) {
		this.NOT_RUK.set(NOT_RUK);
	}

	public void setNOT_ADDRESS(String NOT_ADDRESS) {
		this.NOT_ADDRESS.set(NOT_ADDRESS);
	}

	public void setNOT_TELEPHONE(String NOT_TELEPHONE) {
		this.NOT_TELEPHONE.set(NOT_TELEPHONE);
	}

	public Long getNOT_ID() {
		return NOT_ID.get();
	}

	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public String getNOT_NAME() {
		return NOT_NAME.get();
	}

	public String getNOT_RUK() {
		return NOT_RUK.get();
	}

	public String getNOT_ADDRESS() {
		return NOT_ADDRESS.get();
	}

	public String getNOT_TELEPHONE() {
		return NOT_TELEPHONE.get();
	}

	public LongProperty NOT_IDProperty() {
		return NOT_ID;
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

	public StringProperty NOT_NAMEProperty() {
		return NOT_NAME;
	}

	public StringProperty NOT_RUKProperty() {
		return NOT_RUK;
	}

	public StringProperty NOT_ADDRESSProperty() {
		return NOT_ADDRESS;
	}

	public StringProperty NOT_TELEPHONEProperty() {
		return NOT_TELEPHONE;
	}
}
