package mj.audit.view;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AUDIT_REPORT {
	private StringProperty CAUDOPERATION;/* ��� ������ */
	private StringProperty CAUDPROGRAM;/* ��� ������ */
	private StringProperty CAUDMACHINE;/* ��� ������ */
	private StringProperty CTABLENAME;/* ��� ������ */
	private StringProperty CTABLE;/* ��� ������ */
	private SimpleObjectProperty<Timestamp> DAUDDATE;/* ��� ������ */
	private StringProperty COLDDATA;/* ��� ������ */
	private StringProperty CNEWDATA;/* ��� ������ */
	private StringProperty CFIELDNAME;/* ��� ������ */
	private StringProperty CFIELD;/* ��� ������ */
	private IntegerProperty IACTIONID;/* ��� ������ */

	public AUDIT_REPORT() {
		this.CAUDOPERATION = new SimpleStringProperty();
		this.CAUDPROGRAM = new SimpleStringProperty();
		this.CAUDMACHINE = new SimpleStringProperty();
		this.CTABLENAME = new SimpleStringProperty();
		this.CTABLE = new SimpleStringProperty();
		this.DAUDDATE = new SimpleObjectProperty<>();
		this.COLDDATA = new SimpleStringProperty();
		this.CNEWDATA = new SimpleStringProperty();
		this.CFIELDNAME = new SimpleStringProperty();
		this.CFIELD = new SimpleStringProperty();
		this.IACTIONID = new SimpleIntegerProperty();
	}

	public void setCAUDOPERATION(String CAUDOPERATION) {
		this.CAUDOPERATION.set(CAUDOPERATION);
	}

	public void setCAUDPROGRAM(String CAUDPROGRAM) {
		this.CAUDPROGRAM.set(CAUDPROGRAM);
	}

	public void setCAUDMACHINE(String CAUDMACHINE) {
		this.CAUDMACHINE.set(CAUDMACHINE);
	}

	public void setCTABLENAME(String CTABLENAME) {
		this.CTABLENAME.set(CTABLENAME);
	}

	public void setCTABLE(String CTABLE) {
		this.CTABLE.set(CTABLE);
	}

	public void setDAUDDATE(Timestamp DAUDDATE) {
		this.DAUDDATE.set(DAUDDATE);
	}

	public void setCOLDDATA(String COLDDATA) {
		this.COLDDATA.set(COLDDATA);
	}

	public void setCNEWDATA(String CNEWDATA) {
		this.CNEWDATA.set(CNEWDATA);
	}

	public void setCFIELDNAME(String CFIELDNAME) {
		this.CFIELDNAME.set(CFIELDNAME);
	}

	public void setCFIELD(String CFIELD) {
		this.CFIELD.set(CFIELD);
	}

	public void setIACTIONID(Integer IACTIONID) {
		this.IACTIONID.set(IACTIONID);
	}

	public String getCAUDOPERATION() {
		return CAUDOPERATION.get();
	}

	public String getCAUDPROGRAM() {
		return CAUDPROGRAM.get();
	}

	public String getCAUDMACHINE() {
		return CAUDMACHINE.get();
	}

	public String getCTABLENAME() {
		return CTABLENAME.get();
	}

	public String getCTABLE() {
		return CTABLE.get();
	}

	public Object getDAUDDATE() {
		return DAUDDATE.get();
	}

	public String getCOLDDATA() {
		return COLDDATA.get();
	}

	public String getCNEWDATA() {
		return CNEWDATA.get();
	}

	public String getCFIELDNAME() {
		return CFIELDNAME.get();
	}

	public String getCFIELD() {
		return CFIELD.get();
	}

	public Integer getIACTIONID() {
		return IACTIONID.get();
	}

	public StringProperty CAUDOPERATIONProperty() {
		return CAUDOPERATION;
	}

	public StringProperty CAUDPROGRAMProperty() {
		return CAUDPROGRAM;
	}

	public StringProperty CAUDMACHINEProperty() {
		return CAUDMACHINE;
	}

	public StringProperty CTABLENAMEProperty() {
		return CTABLENAME;
	}

	public StringProperty CTABLEProperty() {
		return CTABLE;
	}

	public SimpleObjectProperty<Timestamp> DAUDDATEProperty() {
		return DAUDDATE;
	}

	public StringProperty COLDDATAProperty() {
		return COLDDATA;
	}

	public StringProperty CNEWDATAProperty() {
		return CNEWDATA;
	}

	public StringProperty CFIELDNAMEProperty() {
		return CFIELDNAME;
	}

	public StringProperty CFIELDProperty() {
		return CFIELD;
	}

	public IntegerProperty IACTIONIDProperty() {
		return IACTIONID;
	}
}
