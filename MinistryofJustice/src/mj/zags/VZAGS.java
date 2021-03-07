package mj.zags;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VZAGS {
	/** Нет данных */
	private StringProperty COTDNAME;
	/** Нет данных */
	private IntegerProperty ZAGS_ID;
	/** Нет данных */
	private IntegerProperty ZAGS_OTD;
	/** Нет данных */
	private StringProperty ZAGS_NAME;
	/** Нет данных */
	private StringProperty ZAGS_RUK;
	/** Нет данных */
	private StringProperty ZAGS_ADR;
	/** Нет данных */
	private StringProperty ZAGS_CITY_ABH;
	/** Нет данных */
	private StringProperty ZAGS_ADR_ABH;
	/** Нет данных */
	private StringProperty ZAGS_RUK_ABH;
	/** Нет данных */
	private StringProperty ADDR;
	/** Нет данных */
	private StringProperty ADDR_ABH;

	public VZAGS() {
		this.ADDR_ABH = new SimpleStringProperty();
		this.COTDNAME = new SimpleStringProperty();
		this.ZAGS_ID = new SimpleIntegerProperty();
		this.ZAGS_OTD = new SimpleIntegerProperty();
		this.ZAGS_NAME = new SimpleStringProperty();
		this.ZAGS_RUK = new SimpleStringProperty();
		this.ZAGS_ADR = new SimpleStringProperty();
		this.ZAGS_CITY_ABH = new SimpleStringProperty();
		this.ZAGS_ADR_ABH = new SimpleStringProperty();
		this.ZAGS_RUK_ABH = new SimpleStringProperty();
		this.ADDR = new SimpleStringProperty();
	}

	public void setADDR_ABH(String ADDR_ABH) {
		this.ADDR_ABH.set(ADDR_ABH);
	}

	public String getADDR_ABH() {
		return ADDR_ABH.get();
	}

	public StringProperty ADDR_ABHProperty() {
		return ADDR_ABH;
	}

	public void setCOTDNAME(String COTDNAME) {
		this.COTDNAME.set(COTDNAME);
	}

	public void setZAGS_ID(Integer ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setZAGS_OTD(Integer ZAGS_OTD) {
		this.ZAGS_OTD.set(ZAGS_OTD);
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public void setZAGS_RUK(String ZAGS_RUK) {
		this.ZAGS_RUK.set(ZAGS_RUK);
	}

	public void setZAGS_ADR(String ZAGS_ADR) {
		this.ZAGS_ADR.set(ZAGS_ADR);
	}

	public void setZAGS_CITY_ABH(String ZAGS_CITY_ABH) {
		this.ZAGS_CITY_ABH.set(ZAGS_CITY_ABH);
	}

	public void setZAGS_ADR_ABH(String ZAGS_ADR_ABH) {
		this.ZAGS_ADR_ABH.set(ZAGS_ADR_ABH);
	}

	public void setZAGS_RUK_ABH(String ZAGS_RUK_ABH) {
		this.ZAGS_RUK_ABH.set(ZAGS_RUK_ABH);
	}

	public void setADDR(String ADDR) {
		this.ADDR.set(ADDR);
	}

	public String getCOTDNAME() {
		return COTDNAME.get();
	}

	public Integer getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public Integer getZAGS_OTD() {
		return ZAGS_OTD.get();
	}

	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public String getZAGS_RUK() {
		return ZAGS_RUK.get();
	}

	public String getZAGS_ADR() {
		return ZAGS_ADR.get();
	}

	public String getZAGS_CITY_ABH() {
		return ZAGS_CITY_ABH.get();
	}

	public String getZAGS_ADR_ABH() {
		return ZAGS_ADR_ABH.get();
	}

	public String getZAGS_RUK_ABH() {
		return ZAGS_RUK_ABH.get();
	}

	public String getADDR() {
		return ADDR.get();
	}

	public StringProperty COTDNAMEProperty() {
		return COTDNAME;
	}

	public IntegerProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public IntegerProperty ZAGS_OTDProperty() {
		return ZAGS_OTD;
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}

	public StringProperty ZAGS_RUKProperty() {
		return ZAGS_RUK;
	}

	public StringProperty ZAGS_ADRProperty() {
		return ZAGS_ADR;
	}

	public StringProperty ZAGS_CITY_ABHProperty() {
		return ZAGS_CITY_ABH;
	}

	public StringProperty ZAGS_ADR_ABHProperty() {
		return ZAGS_ADR_ABH;
	}

	public StringProperty ZAGS_RUK_ABHProperty() {
		return ZAGS_RUK_ABH;
	}

	public StringProperty ADDRProperty() {
		return ADDR;
	}
}
