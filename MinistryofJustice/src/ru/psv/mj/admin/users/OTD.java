package ru.psv.mj.admin.users;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OTD {
	/** ��� ������ */
	private LongProperty IOTDNUM;
	/** ��� ������ */
	private StringProperty COTDNAME;
	/** ��� ������ */
	private LongProperty AREA_ID;
	/** ��� ������ */
	private LongProperty CODE;
	/** ��� ������ */
	private StringProperty NAME;

	public OTD() {
		this.IOTDNUM = new SimpleLongProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.AREA_ID = new SimpleLongProperty();
		this.CODE = new SimpleLongProperty();
		this.NAME = new SimpleStringProperty();
	}

	public void setIOTDNUM(Long IOTDNUM) {
		this.IOTDNUM.set(IOTDNUM);
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public void setAREA_ID(Long AREA_ID) {
		this.AREA_ID.set(AREA_ID);
	}

	public void setCODE(Long CODE) {
		this.CODE.set(CODE);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public Long getIOTDNUM() {
		return IOTDNUM.get();
	}

	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public Long getAREA_ID() {
		return AREA_ID.get();
	}

	public Long getCODE() {
		return CODE.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public LongProperty IOTDNUMProperty() {
		return IOTDNUM;
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

	public LongProperty AREA_IDProperty() {
		return AREA_ID;
	}

	public LongProperty CODEProperty() {
		return CODE;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}
}
