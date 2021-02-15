package mj.users;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OTD {
	/** Номер отделения */
	private IntegerProperty IOTDNUM;
	/** Название */
	private StringProperty COTDNAME;

	public OTD() {
		this.IOTDNUM = new SimpleIntegerProperty();
		this.COTDNAME = new SimpleStringProperty();
	}

	public void setIOTDNUM(Integer IOTDNUM) {
		this.IOTDNUM.set(IOTDNUM);
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public Integer getIOTDNUM() {
		return IOTDNUM.get();
	}

	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public IntegerProperty IOTDNUMProperty() {
		return IOTDNUM;
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}
}
