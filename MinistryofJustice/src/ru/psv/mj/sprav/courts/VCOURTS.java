package ru.psv.mj.sprav.courts;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VCOURTS {
	/** Нет данных */
	private LongProperty ID;
	/** Нет данных */
	private StringProperty NAME;
	/** Нет данных */
	private LongProperty OTD;
	/** Нет данных */
	private StringProperty ABH_NAME;
	/** Нет данных */
	private StringProperty NAME_ROD;
	/** Нет данных */
	private LongProperty IOTDNUM;
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private LongProperty AREA_ID;

	public VCOURTS() {
		this.ID = new SimpleLongProperty();
		this.NAME = new SimpleStringProperty();
		this.OTD = new SimpleLongProperty();
		this.ABH_NAME = new SimpleStringProperty();
		this.NAME_ROD = new SimpleStringProperty();
		this.IOTDNUM = new SimpleLongProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.AREA_ID = new SimpleLongProperty();
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setOTD(Long OTD) {
		this.OTD.set(OTD);
	}

	public void setABH_NAME(String ABH_NAME) {
		this.ABH_NAME.set(ABH_NAME);
	}

	public void setNAME_ROD(String NAME_ROD) {
		this.NAME_ROD.set(NAME_ROD);
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

	public Long getID() {
		return ID.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public Long getOTD() {
		return OTD.get();
	}

	public String getABH_NAME() {
		return ABH_NAME.get();
	}

	public String getNAME_ROD() {
		return NAME_ROD.get();
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

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public LongProperty OTDProperty() {
		return OTD;
	}

	public StringProperty ABH_NAMEProperty() {
		return ABH_NAME;
	}

	public StringProperty NAME_RODProperty() {
		return NAME_ROD;
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
}
