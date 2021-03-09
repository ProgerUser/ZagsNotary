package mj.courts;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VCOURTS {
	/** Нет данных */
	private IntegerProperty ID;
	/** Нет данных */
	private StringProperty NAME;
	/** Нет данных */
	private IntegerProperty OTD;
	/** Нет данных */
	private StringProperty ABH_NAME;
	/** Нет данных */
	private StringProperty NAME_ROD;
	/** Нет данных */
	private IntegerProperty IOTDNUM;
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private IntegerProperty AREA_ID;

	public VCOURTS() {
		this.ID = new SimpleIntegerProperty();
		this.NAME = new SimpleStringProperty();
		this.OTD = new SimpleIntegerProperty();
		this.ABH_NAME = new SimpleStringProperty();
		this.NAME_ROD = new SimpleStringProperty();
		this.IOTDNUM = new SimpleIntegerProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.AREA_ID = new SimpleIntegerProperty();
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setOTD(Integer OTD) {
		this.OTD.set(OTD);
	}

	public void setABH_NAME(String ABH_NAME) {
		this.ABH_NAME.set(ABH_NAME);
	}

	public void setNAME_ROD(String NAME_ROD) {
		this.NAME_ROD.set(NAME_ROD);
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

	public Integer getID() {
		return ID.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public Integer getOTD() {
		return OTD.get();
	}

	public String getABH_NAME() {
		return ABH_NAME.get();
	}

	public String getNAME_ROD() {
		return NAME_ROD.get();
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

	public IntegerProperty IDProperty() {
		return ID;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public IntegerProperty OTDProperty() {
		return OTD;
	}

	public StringProperty ABH_NAMEProperty() {
		return ABH_NAME;
	}

	public StringProperty NAME_RODProperty() {
		return NAME_ROD;
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
}
