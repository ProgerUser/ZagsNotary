package ru.psv.mj.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OTD {
	private StringProperty IOTDNUM;
	private StringProperty COTDNAME;

	/* CONSTRUCTOR */
	public OTD() {
		this.IOTDNUM = new SimpleStringProperty();
		this.COTDNAME = new SimpleStringProperty();
	}

	/* IOTDNUM */
	public String getIOTDNUM() {
		return IOTDNUM.get();
	}

	public void setIOTDNUM(String IOTDNUM) {
		this.IOTDNUM.set(IOTDNUM);
	}

	public StringProperty IOTDNUMProperty() {
		return IOTDNUM;
	}

	/* COTDNAME */
	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

}
