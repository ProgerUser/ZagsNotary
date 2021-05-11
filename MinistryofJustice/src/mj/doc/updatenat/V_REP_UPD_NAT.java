package mj.doc.updatenat;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_REP_UPD_NAT {
	/** Нет данных */
	private LongProperty ID;
	/** Нет данных */
	private StringProperty DCUSBIRTHDAY;
	/** Нет данных */
	private StringProperty OLD_NAT;
	/** Нет данных */
	private StringProperty NEW_NAT;
	/** Нет данных */
	private StringProperty CCUSPLACE_BIRTH;
	/** Нет данных */
	private LongProperty BR_ACT_ID;
	/** Нет данных */
	private StringProperty BR_ACT_DATE;
	/** Нет данных */
	private StringProperty ZAGS_NAME;
	/** Нет данных */
	private StringProperty COUNTRY_NAME;
	/** Нет данных */
	private StringProperty NATIONALITY;
	/** Нет данных */
	private StringProperty ADDRESS;
	/** Нет данных */
	private StringProperty SVID_SERIA;
	/** Нет данных */
	private StringProperty SVID_NUMBER;
	/** Нет данных */
	private StringProperty CURDATE;
	/** Нет данных */
	private StringProperty LASTNAME;
	/** Нет данных */
	private StringProperty FIRSTNAME;
	/** Нет данных */
	private StringProperty MIDDLNAME;

	public V_REP_UPD_NAT() {
		this.ID = new SimpleLongProperty();
		this.DCUSBIRTHDAY = new SimpleStringProperty();
		this.OLD_NAT = new SimpleStringProperty();
		this.NEW_NAT = new SimpleStringProperty();
		this.CCUSPLACE_BIRTH = new SimpleStringProperty();
		this.BR_ACT_ID = new SimpleLongProperty();
		this.BR_ACT_DATE = new SimpleStringProperty();
		this.ZAGS_NAME = new SimpleStringProperty();
		this.COUNTRY_NAME = new SimpleStringProperty();
		this.NATIONALITY = new SimpleStringProperty();
		this.ADDRESS = new SimpleStringProperty();
		this.SVID_SERIA = new SimpleStringProperty();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.CURDATE = new SimpleStringProperty();
		this.LASTNAME = new SimpleStringProperty();
		this.FIRSTNAME = new SimpleStringProperty();
		this.MIDDLNAME = new SimpleStringProperty();
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setDCUSBIRTHDAY(String DCUSBIRTHDAY) {
		this.DCUSBIRTHDAY.set(DCUSBIRTHDAY);
	}

	public void setOLD_NAT(String OLD_NAT) {
		this.OLD_NAT.set(OLD_NAT);
	}

	public void setNEW_NAT(String NEW_NAT) {
		this.NEW_NAT.set(NEW_NAT);
	}

	public void setCCUSPLACE_BIRTH(String CCUSPLACE_BIRTH) {
		this.CCUSPLACE_BIRTH.set(CCUSPLACE_BIRTH);
	}

	public void setBR_ACT_ID(Long BR_ACT_ID) {
		this.BR_ACT_ID.set(BR_ACT_ID);
	}

	public void setBR_ACT_DATE(String BR_ACT_DATE) {
		this.BR_ACT_DATE.set(BR_ACT_DATE);
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME.set(COUNTRY_NAME);
	}

	public void setNATIONALITY(String NATIONALITY) {
		this.NATIONALITY.set(NATIONALITY);
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS.set(ADDRESS);
	}

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setSVID_NUMBER(String SVID_NUMBER) {
		this.SVID_NUMBER.set(SVID_NUMBER);
	}

	public void setCURDATE(String CURDATE) {
		this.CURDATE.set(CURDATE);
	}

	public void setLASTNAME(String LASTNAME) {
		this.LASTNAME.set(LASTNAME);
	}

	public void setFIRSTNAME(String FIRSTNAME) {
		this.FIRSTNAME.set(FIRSTNAME);
	}

	public void setMIDDLNAME(String MIDDLNAME) {
		this.MIDDLNAME.set(MIDDLNAME);
	}

	public Long getID() {
		return ID.get();
	}

	public String getDCUSBIRTHDAY() {
		return DCUSBIRTHDAY.get();
	}

	public String getOLD_NAT() {
		return OLD_NAT.get();
	}

	public String getNEW_NAT() {
		return NEW_NAT.get();
	}

	public String getCCUSPLACE_BIRTH() {
		return CCUSPLACE_BIRTH.get();
	}

	public Long getBR_ACT_ID() {
		return BR_ACT_ID.get();
	}

	public String getBR_ACT_DATE() {
		return BR_ACT_DATE.get();
	}

	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public String getCOUNTRY_NAME() {
		return COUNTRY_NAME.get();
	}

	public String getNATIONALITY() {
		return NATIONALITY.get();
	}

	public String getADDRESS() {
		return ADDRESS.get();
	}

	public String getSVID_SERIA() {
		return SVID_SERIA.get();
	}

	public String getSVID_NUMBER() {
		return SVID_NUMBER.get();
	}

	public String getCURDATE() {
		return CURDATE.get();
	}

	public String getLASTNAME() {
		return LASTNAME.get();
	}

	public String getFIRSTNAME() {
		return FIRSTNAME.get();
	}

	public String getMIDDLNAME() {
		return MIDDLNAME.get();
	}

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty DCUSBIRTHDAYProperty() {
		return DCUSBIRTHDAY;
	}

	public StringProperty OLD_NATProperty() {
		return OLD_NAT;
	}

	public StringProperty NEW_NATProperty() {
		return NEW_NAT;
	}

	public StringProperty CCUSPLACE_BIRTHProperty() {
		return CCUSPLACE_BIRTH;
	}

	public LongProperty BR_ACT_IDProperty() {
		return BR_ACT_ID;
	}

	public StringProperty BR_ACT_DATEProperty() {
		return BR_ACT_DATE;
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}

	public StringProperty COUNTRY_NAMEProperty() {
		return COUNTRY_NAME;
	}

	public StringProperty NATIONALITYProperty() {
		return NATIONALITY;
	}

	public StringProperty ADDRESSProperty() {
		return ADDRESS;
	}

	public StringProperty SVID_SERIAProperty() {
		return SVID_SERIA;
	}

	public StringProperty SVID_NUMBERProperty() {
		return SVID_NUMBER;
	}

	public StringProperty CURDATEProperty() {
		return CURDATE;
	}

	public StringProperty LASTNAMEProperty() {
		return LASTNAME;
	}

	public StringProperty FIRSTNAMEProperty() {
		return FIRSTNAME;
	}

	public StringProperty MIDDLNAMEProperty() {
		return MIDDLNAME;
	}
}
