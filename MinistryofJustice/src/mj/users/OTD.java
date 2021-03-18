package mj.users;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OTD {
	/** Нет данных */
	private IntegerProperty IOTDNUM;
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private IntegerProperty AREA_ID;
	/** Нет данных */
	private IntegerProperty CODE;
	/** Нет данных */
	private StringProperty NAME;

	public OTD() {
		this.IOTDNUM = new SimpleIntegerProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.AREA_ID = new SimpleIntegerProperty();
		this.CODE = new SimpleIntegerProperty();
		this.NAME = new SimpleStringProperty();
	}

	public void setIOTDNUM(Integer IOTDNUM) {
		this.IOTDNUM.set(IOTDNUM);
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public void setAREA_ID(Integer AREA_ID) {
		this.AREA_ID.set(AREA_ID);
	}

	public void setCODE(Integer CODE) {
		this.CODE.set(CODE);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public Integer getIOTDNUM() {
		return IOTDNUM.get();
	}

	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public Integer getAREA_ID() {
		return AREA_ID.get();
	}

	public Integer getCODE() {
		return CODE.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public IntegerProperty IOTDNUMProperty() {
		return IOTDNUM;
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

	public IntegerProperty AREA_IDProperty() {
		return AREA_ID;
	}

	public IntegerProperty CODEProperty() {
		return CODE;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}
}
