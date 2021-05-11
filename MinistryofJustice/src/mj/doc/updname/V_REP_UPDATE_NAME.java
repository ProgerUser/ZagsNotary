package mj.doc.updname;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_REP_UPDATE_NAME {
	/** Нет данных */
	private StringProperty CURDATE;
	/** Нет данных */
	private StringProperty SVID_NUMBER;
	/** Нет данных */
	private StringProperty SVID_SERIA;
	/** Нет данных */
	private StringProperty ADDRESS;
	/** Нет данных */
	private StringProperty NATIONALITY;
	/** Нет данных */
	private StringProperty COUNTRY_NAME;
	/** Нет данных */
	private StringProperty ZAGS_NAME;
	/** Нет данных */
	private StringProperty BR_ACT_DATE;
	/** Нет данных */
	private LongProperty BR_ACT_ID;
	/** Нет данных */
	private StringProperty CCUSPLACE_BIRTH;
	/** Нет данных */
	private StringProperty DCUSBIRTHDAY;
	/** Нет данных */
	private StringProperty NEW_MIDDLNAME;
	/** Нет данных */
	private StringProperty NEW_FIRSTNAME;
	/** Нет данных */
	private StringProperty NEW_LASTNAME;
	/** Нет данных */
	private StringProperty OLD_MIDDLNAME;
	/** Нет данных */
	private StringProperty OLD_FIRSTNAME;
	/** Нет данных */
	private StringProperty OLD_LASTNAME;
	/** Нет данных */
	private LongProperty ID;

	public V_REP_UPDATE_NAME() {
		this.CURDATE = new SimpleStringProperty();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.SVID_SERIA = new SimpleStringProperty();
		this.ADDRESS = new SimpleStringProperty();
		this.NATIONALITY = new SimpleStringProperty();
		this.COUNTRY_NAME = new SimpleStringProperty();
		this.ZAGS_NAME = new SimpleStringProperty();
		this.BR_ACT_DATE = new SimpleStringProperty();
		this.BR_ACT_ID = new SimpleLongProperty();
		this.CCUSPLACE_BIRTH = new SimpleStringProperty();
		this.DCUSBIRTHDAY = new SimpleStringProperty();
		this.NEW_MIDDLNAME = new SimpleStringProperty();
		this.NEW_FIRSTNAME = new SimpleStringProperty();
		this.NEW_LASTNAME = new SimpleStringProperty();
		this.OLD_MIDDLNAME = new SimpleStringProperty();
		this.OLD_FIRSTNAME = new SimpleStringProperty();
		this.OLD_LASTNAME = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
	}

	public void setCURDATE(String CURDATE) {
		this.CURDATE.set(CURDATE);
	}

	public void setSVID_NUMBER(String SVID_NUMBER) {
		this.SVID_NUMBER.set(SVID_NUMBER);
	}

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS.set(ADDRESS);
	}

	public void setNATIONALITY(String NATIONALITY) {
		this.NATIONALITY.set(NATIONALITY);
	}

	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME.set(COUNTRY_NAME);
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public void setBR_ACT_DATE(String BR_ACT_DATE) {
		this.BR_ACT_DATE.set(BR_ACT_DATE);
	}

	public void setBR_ACT_ID(Long BR_ACT_ID) {
		this.BR_ACT_ID.set(BR_ACT_ID);
	}

	public void setCCUSPLACE_BIRTH(String CCUSPLACE_BIRTH) {
		this.CCUSPLACE_BIRTH.set(CCUSPLACE_BIRTH);
	}

	public void setDCUSBIRTHDAY(String DCUSBIRTHDAY) {
		this.DCUSBIRTHDAY.set(DCUSBIRTHDAY);
	}

	public void setNEW_MIDDLNAME(String NEW_MIDDLNAME) {
		this.NEW_MIDDLNAME.set(NEW_MIDDLNAME);
	}

	public void setNEW_FIRSTNAME(String NEW_FIRSTNAME) {
		this.NEW_FIRSTNAME.set(NEW_FIRSTNAME);
	}

	public void setNEW_LASTNAME(String NEW_LASTNAME) {
		this.NEW_LASTNAME.set(NEW_LASTNAME);
	}

	public void setOLD_MIDDLNAME(String OLD_MIDDLNAME) {
		this.OLD_MIDDLNAME.set(OLD_MIDDLNAME);
	}

	public void setOLD_FIRSTNAME(String OLD_FIRSTNAME) {
		this.OLD_FIRSTNAME.set(OLD_FIRSTNAME);
	}

	public void setOLD_LASTNAME(String OLD_LASTNAME) {
		this.OLD_LASTNAME.set(OLD_LASTNAME);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public String getCURDATE() {
		return CURDATE.get();
	}

	public String getSVID_NUMBER() {
		return SVID_NUMBER.get();
	}

	public String getSVID_SERIA() {
		return SVID_SERIA.get();
	}

	public String getADDRESS() {
		return ADDRESS.get();
	}

	public String getNATIONALITY() {
		return NATIONALITY.get();
	}

	public String getCOUNTRY_NAME() {
		return COUNTRY_NAME.get();
	}

	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public String getBR_ACT_DATE() {
		return BR_ACT_DATE.get();
	}

	public Long getBR_ACT_ID() {
		return BR_ACT_ID.get();
	}

	public String getCCUSPLACE_BIRTH() {
		return CCUSPLACE_BIRTH.get();
	}

	public String getDCUSBIRTHDAY() {
		return DCUSBIRTHDAY.get();
	}

	public String getNEW_MIDDLNAME() {
		return NEW_MIDDLNAME.get();
	}

	public String getNEW_FIRSTNAME() {
		return NEW_FIRSTNAME.get();
	}

	public String getNEW_LASTNAME() {
		return NEW_LASTNAME.get();
	}

	public String getOLD_MIDDLNAME() {
		return OLD_MIDDLNAME.get();
	}

	public String getOLD_FIRSTNAME() {
		return OLD_FIRSTNAME.get();
	}

	public String getOLD_LASTNAME() {
		return OLD_LASTNAME.get();
	}

	public Long getID() {
		return ID.get();
	}

	public StringProperty CURDATEProperty() {
		return CURDATE;
	}

	public StringProperty SVID_NUMBERProperty() {
		return SVID_NUMBER;
	}

	public StringProperty SVID_SERIAProperty() {
		return SVID_SERIA;
	}

	public StringProperty ADDRESSProperty() {
		return ADDRESS;
	}

	public StringProperty NATIONALITYProperty() {
		return NATIONALITY;
	}

	public StringProperty COUNTRY_NAMEProperty() {
		return COUNTRY_NAME;
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}

	public StringProperty BR_ACT_DATEProperty() {
		return BR_ACT_DATE;
	}

	public LongProperty BR_ACT_IDProperty() {
		return BR_ACT_ID;
	}

	public StringProperty CCUSPLACE_BIRTHProperty() {
		return CCUSPLACE_BIRTH;
	}

	public StringProperty DCUSBIRTHDAYProperty() {
		return DCUSBIRTHDAY;
	}

	public StringProperty NEW_MIDDLNAMEProperty() {
		return NEW_MIDDLNAME;
	}

	public StringProperty NEW_FIRSTNAMEProperty() {
		return NEW_FIRSTNAME;
	}

	public StringProperty NEW_LASTNAMEProperty() {
		return NEW_LASTNAME;
	}

	public StringProperty OLD_MIDDLNAMEProperty() {
		return OLD_MIDDLNAME;
	}

	public StringProperty OLD_FIRSTNAMEProperty() {
		return OLD_FIRSTNAME;
	}

	public StringProperty OLD_LASTNAMEProperty() {
		return OLD_LASTNAME;
	}

	public LongProperty IDProperty() {
		return ID;
	}
}
