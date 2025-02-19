package ru.psv.mj.zags.doc.updname;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UPDATE_NAME {
	/** ��� ������ */
	private StringProperty CR_TIME;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** ��� ������ */
	private StringProperty SVID_SERIA;
	/** ��� ������ */
	private StringProperty SVID_NUMBER;
	/** ��� ������ */
	private LongProperty CUSID;
	/** ��� ������ */
	private LongProperty ZAGS_ID;
	/** ��� ������ */
	private StringProperty OPER;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDateTime> TM$DOC_DATE;
	/** ��� ������ */
	private LongProperty BRN_ACT_ID;
	/** ��� ������ */
	private StringProperty NEW_MIDDLNAME;
	/** ��� ������ */
	private StringProperty NEW_FIRSTNAME;
	/** ��� ������ */
	private StringProperty NEW_LASTNAME;
	/** ��� ������ */
	private StringProperty OLD_MIDDLNAME;
	/** ��� ������ */
	private StringProperty OLD_FIRSTNAME;
	/** ��� ������ */
	private StringProperty OLD_LASTNAME;
	/** ��� ������ */
	private LongProperty ID;
	/** ��� ������ */
	private StringProperty FIO;
	/** ������� �� �������� ��� */
	private StringProperty OLD_LASTNAME_AB;
	/** ��� �� �������� ��� */
	private StringProperty OLD_FIRSTNAME_AB;
	/** �������� �� �������� ��� */
	private StringProperty OLD_MIDDLNAME_AB;
	/** ������� ����� �������� ��� */
	private StringProperty NEW_LASTNAME_AB;
	/** ��� ����� �������� ��� */
	private StringProperty NEW_FIRSTNAME_AB;
	/** �������� ����� �������� ��� */
	private StringProperty NEW_MIDDLNAME_AB;
	/** ����� ��������� */
	private StringProperty DOC_NUMBER;

	public UPDATE_NAME() {
		this.DOC_NUMBER = new SimpleStringProperty();

		this.OLD_LASTNAME_AB = new SimpleStringProperty();
		this.OLD_FIRSTNAME_AB = new SimpleStringProperty();
		this.OLD_MIDDLNAME_AB = new SimpleStringProperty();
		this.NEW_LASTNAME_AB = new SimpleStringProperty();
		this.NEW_FIRSTNAME_AB = new SimpleStringProperty();
		this.NEW_MIDDLNAME_AB = new SimpleStringProperty();

		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.SVID_SERIA = new SimpleStringProperty();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.CUSID = new SimpleLongProperty();
		this.ZAGS_ID = new SimpleLongProperty();
		this.OPER = new SimpleStringProperty();
		this.TM$DOC_DATE = new SimpleObjectProperty<>();
		this.BRN_ACT_ID = new SimpleLongProperty();
		this.NEW_MIDDLNAME = new SimpleStringProperty();
		this.NEW_FIRSTNAME = new SimpleStringProperty();
		this.NEW_LASTNAME = new SimpleStringProperty();
		this.OLD_MIDDLNAME = new SimpleStringProperty();
		this.OLD_FIRSTNAME = new SimpleStringProperty();
		this.OLD_LASTNAME = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
		this.FIO = new SimpleStringProperty();
	}

	public void setDOC_NUMBER(String DOC_NUMBER) {
		this.DOC_NUMBER.set(DOC_NUMBER);
	}

	public String getDOC_NUMBER() {
		return DOC_NUMBER.get();
	}

	public StringProperty DOC_NUMBERProperty() {
		return DOC_NUMBER;
	}

	public void setOLD_LASTNAME_AB(String OLD_LASTNAME_AB) {
		this.OLD_LASTNAME_AB.set(OLD_LASTNAME_AB);
	}

	public void setOLD_FIRSTNAME_AB(String OLD_FIRSTNAME_AB) {
		this.OLD_FIRSTNAME_AB.set(OLD_FIRSTNAME_AB);
	}

	public void setOLD_MIDDLNAME_AB(String OLD_MIDDLNAME_AB) {
		this.OLD_MIDDLNAME_AB.set(OLD_MIDDLNAME_AB);
	}

	public void setNEW_LASTNAME_AB(String NEW_LASTNAME_AB) {
		this.NEW_LASTNAME_AB.set(NEW_LASTNAME_AB);
	}

	public void setNEW_FIRSTNAME_AB(String NEW_FIRSTNAME_AB) {
		this.NEW_FIRSTNAME_AB.set(NEW_FIRSTNAME_AB);
	}

	public void setNEW_MIDDLNAME_AB(String NEW_MIDDLNAME_AB) {
		this.NEW_MIDDLNAME_AB.set(NEW_MIDDLNAME_AB);
	}

	public String getOLD_LASTNAME_AB() {
		return OLD_LASTNAME_AB.get();
	}

	public String getOLD_FIRSTNAME_AB() {
		return OLD_FIRSTNAME_AB.get();
	}

	public String getOLD_MIDDLNAME_AB() {
		return OLD_MIDDLNAME_AB.get();
	}

	public String getNEW_LASTNAME_AB() {
		return NEW_LASTNAME_AB.get();
	}

	public String getNEW_FIRSTNAME_AB() {
		return NEW_FIRSTNAME_AB.get();
	}

	public String getNEW_MIDDLNAME_AB() {
		return NEW_MIDDLNAME_AB.get();
	}

	public StringProperty OLD_LASTNAME_ABProperty() {
		return OLD_LASTNAME_AB;
	}

	public StringProperty OLD_FIRSTNAME_ABProperty() {
		return OLD_FIRSTNAME_AB;
	}

	public StringProperty OLD_MIDDLNAME_ABProperty() {
		return OLD_MIDDLNAME_AB;
	}

	public StringProperty NEW_LASTNAME_ABProperty() {
		return NEW_LASTNAME_AB;
	}

	public StringProperty NEW_FIRSTNAME_ABProperty() {
		return NEW_FIRSTNAME_AB;
	}

	public StringProperty NEW_MIDDLNAME_ABProperty() {
		return NEW_MIDDLNAME_AB;
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setSVID_NUMBER(String SVID_NUMBER) {
		this.SVID_NUMBER.set(SVID_NUMBER);
	}

	public void setCUSID(Long CUSID) {
		this.CUSID.set(CUSID);
	}

	public void setZAGS_ID(Long ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setTM$DOC_DATE(LocalDateTime TM$DOC_DATE) {
		this.TM$DOC_DATE.set(TM$DOC_DATE);
	}

	public void setBRN_ACT_ID(Long BRN_ACT_ID) {
		this.BRN_ACT_ID.set(BRN_ACT_ID);
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

	public void setFIO(String FIO) {
		this.FIO.set(FIO);
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public String getSVID_SERIA() {
		return SVID_SERIA.get();
	}

	public String getSVID_NUMBER() {
		return SVID_NUMBER.get();
	}

	public Long getCUSID() {
		return CUSID.get();
	}

	public Long getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public LocalDateTime getTM$DOC_DATE() {
		return TM$DOC_DATE.get();
	}

	public Long getBRN_ACT_ID() {
		return BRN_ACT_ID.get();
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

	public String getFIO() {
		return FIO.get();
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public StringProperty SVID_SERIAProperty() {
		return SVID_SERIA;
	}

	public StringProperty SVID_NUMBERProperty() {
		return SVID_NUMBER;
	}

	public LongProperty CUSIDProperty() {
		return CUSID;
	}

	public LongProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DOC_DATEProperty() {
		return TM$DOC_DATE;
	}

	public LongProperty BRN_ACT_IDProperty() {
		return BRN_ACT_ID;
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

	public StringProperty FIOProperty() {
		return FIO;
	}
}
