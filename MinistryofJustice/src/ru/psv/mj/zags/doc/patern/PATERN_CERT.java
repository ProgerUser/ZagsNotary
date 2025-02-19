package ru.psv.mj.zags.doc.patern;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PATERN_CERT {
	/** ��� ������ */
	private StringProperty CR_TIME;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** ��� ������ */
	private StringProperty PC_ZPLACE;
	/** ��� ������ */
	private StringProperty PC_ZLNAME;
	/** ��� ������ */
	private StringProperty PC_ZFNAME;
	/** ��� ������ */
	private StringProperty PC_ZMNAME;
	/** ��� ������ */
	private LongProperty P�_ZAGS;
	/** ��� ������ */
	private StringProperty P�_USER;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDateTime> TM$P�_DATE;
	/** ��� ������ */
	private StringProperty P�_NUMBER;
	/** ��� ������ */
	private StringProperty P�_SERIA;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> P�_CRDATE;
	/** ��� ������ */
	private StringProperty P�_CRNAME;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> P�_FZ;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> P�_TRZ;
	/** ��� ������ */
	private StringProperty P�_TYPE;
	/** ��� ������ */
	private LongProperty P�_M;
	/** ��� ������ */
	private LongProperty P�_F;
	/** ��� ������ */
	private LongProperty P�_CH;
	/** ��� ������ */
	private StringProperty P�_AFT_MNAME;
	/** ��� ������ */
	private StringProperty P�_AFT_FNAME;
	/** ��� ������ */
	private StringProperty P�_AFT_LNAME;
	/** ��� ������ */
	private LongProperty PC_ACT_ID;
	/** ��� ������ */
	private LongProperty PC_ID;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> CHILDRENBIRTH;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> MOTHERBIRTHDATE;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> FATHERBIRTHDATE;
	/** ��� ������ */
	private StringProperty CHILDFIO;
	/** ��� ������ */
	private StringProperty MOTHERFIO;
	/** ��� ������ */
	private StringProperty FATHERFIO;
	/** ��� �� ���. ��� */
	private StringProperty BEF_FNAME;
	/** �������� �� ���. ��� */
	private StringProperty BEF_MNAME;
	/** ������� �� ���. ��� */
	private StringProperty BEF_LNAME;
	/** ����� ��������� (������������� �������������) */
	private StringProperty DOC_NUMBER;

	public PATERN_CERT() {
		this.DOC_NUMBER = new SimpleStringProperty();
		this.BEF_FNAME = new SimpleStringProperty();
		this.BEF_MNAME = new SimpleStringProperty();
		this.BEF_LNAME = new SimpleStringProperty();

		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.PC_ZPLACE = new SimpleStringProperty();
		this.PC_ZLNAME = new SimpleStringProperty();
		this.PC_ZFNAME = new SimpleStringProperty();
		this.PC_ZMNAME = new SimpleStringProperty();
		this.P�_ZAGS = new SimpleLongProperty();
		this.P�_USER = new SimpleStringProperty();
		this.TM$P�_DATE = new SimpleObjectProperty<>();
		this.P�_NUMBER = new SimpleStringProperty();
		this.P�_SERIA = new SimpleStringProperty();
		this.P�_CRDATE = new SimpleObjectProperty<>();
		this.P�_CRNAME = new SimpleStringProperty();
		this.P�_FZ = new SimpleObjectProperty<>();
		this.P�_TRZ = new SimpleObjectProperty<>();
		this.P�_TYPE = new SimpleStringProperty();
		this.P�_M = new SimpleLongProperty();
		this.P�_F = new SimpleLongProperty();
		this.P�_CH = new SimpleLongProperty();
		this.P�_AFT_MNAME = new SimpleStringProperty();
		this.P�_AFT_FNAME = new SimpleStringProperty();
		this.P�_AFT_LNAME = new SimpleStringProperty();
		this.PC_ACT_ID = new SimpleLongProperty();
		this.PC_ID = new SimpleLongProperty();
		this.CHILDRENBIRTH = new SimpleObjectProperty<>();
		this.MOTHERBIRTHDATE = new SimpleObjectProperty<>();
		this.FATHERBIRTHDATE = new SimpleObjectProperty<>();
		this.CHILDFIO = new SimpleStringProperty();
		this.MOTHERFIO = new SimpleStringProperty();
		this.FATHERFIO = new SimpleStringProperty();
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

	public void setBEF_FNAME(String BEF_FNAME) {
		this.BEF_FNAME.set(BEF_FNAME);
	}

	public void setBEF_MNAME(String BEF_MNAME) {
		this.BEF_MNAME.set(BEF_MNAME);
	}

	public void setBEF_LNAME(String BEF_LNAME) {
		this.BEF_LNAME.set(BEF_LNAME);
	}

	public String getBEF_FNAME() {
		return BEF_FNAME.get();
	}

	public String getBEF_MNAME() {
		return BEF_MNAME.get();
	}

	public String getBEF_LNAME() {
		return BEF_LNAME.get();
	}

	public StringProperty BEF_FNAMEProperty() {
		return BEF_FNAME;
	}

	public StringProperty BEF_MNAMEProperty() {
		return BEF_MNAME;
	}

	public StringProperty BEF_LNAMEProperty() {
		return BEF_LNAME;
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setPC_ZPLACE(String PC_ZPLACE) {
		this.PC_ZPLACE.set(PC_ZPLACE);
	}

	public void setPC_ZLNAME(String PC_ZLNAME) {
		this.PC_ZLNAME.set(PC_ZLNAME);
	}

	public void setPC_ZFNAME(String PC_ZFNAME) {
		this.PC_ZFNAME.set(PC_ZFNAME);
	}

	public void setPC_ZMNAME(String PC_ZMNAME) {
		this.PC_ZMNAME.set(PC_ZMNAME);
	}

	public void setP�_ZAGS(Long P�_ZAGS) {
		this.P�_ZAGS.set(P�_ZAGS);
	}

	public void setP�_USER(String P�_USER) {
		this.P�_USER.set(P�_USER);
	}

	public void setTM$P�_DATE(LocalDateTime TM$P�_DATE) {
		this.TM$P�_DATE.set(TM$P�_DATE);
	}

	public void setP�_NUMBER(String P�_NUMBER) {
		this.P�_NUMBER.set(P�_NUMBER);
	}

	public void setP�_SERIA(String P�_SERIA) {
		this.P�_SERIA.set(P�_SERIA);
	}

	public void setP�_CRDATE(LocalDate P�_CRDATE) {
		this.P�_CRDATE.set(P�_CRDATE);
	}

	public void setP�_CRNAME(String P�_CRNAME) {
		this.P�_CRNAME.set(P�_CRNAME);
	}

	public void setP�_FZ(LocalDate P�_FZ) {
		this.P�_FZ.set(P�_FZ);
	}

	public void setP�_TRZ(LocalDate P�_TRZ) {
		this.P�_TRZ.set(P�_TRZ);
	}

	public void setP�_TYPE(String P�_TYPE) {
		this.P�_TYPE.set(P�_TYPE);
	}

	public void setP�_M(Long P�_M) {
		this.P�_M.set(P�_M);
	}

	public void setP�_F(Long P�_F) {
		this.P�_F.set(P�_F);
	}

	public void setP�_CH(Long P�_CH) {
		this.P�_CH.set(P�_CH);
	}

	public void setP�_AFT_MNAME(String P�_AFT_MNAME) {
		this.P�_AFT_MNAME.set(P�_AFT_MNAME);
	}

	public void setP�_AFT_FNAME(String P�_AFT_FNAME) {
		this.P�_AFT_FNAME.set(P�_AFT_FNAME);
	}

	public void setP�_AFT_LNAME(String P�_AFT_LNAME) {
		this.P�_AFT_LNAME.set(P�_AFT_LNAME);
	}

	public void setPC_ACT_ID(Long PC_ACT_ID) {
		this.PC_ACT_ID.set(PC_ACT_ID);
	}

	public void setPC_ID(Long PC_ID) {
		this.PC_ID.set(PC_ID);
	}

	public void setCHILDRENBIRTH(LocalDate CHILDRENBIRTH) {
		this.CHILDRENBIRTH.set(CHILDRENBIRTH);
	}

	public void setMOTHERBIRTHDATE(LocalDate MOTHERBIRTHDATE) {
		this.MOTHERBIRTHDATE.set(MOTHERBIRTHDATE);
	}

	public void setFATHERBIRTHDATE(LocalDate FATHERBIRTHDATE) {
		this.FATHERBIRTHDATE.set(FATHERBIRTHDATE);
	}

	public void setCHILDFIO(String CHILDFIO) {
		this.CHILDFIO.set(CHILDFIO);
	}

	public void setMOTHERFIO(String MOTHERFIO) {
		this.MOTHERFIO.set(MOTHERFIO);
	}

	public void setFATHERFIO(String FATHERFIO) {
		this.FATHERFIO.set(FATHERFIO);
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public String getPC_ZPLACE() {
		return PC_ZPLACE.get();
	}

	public String getPC_ZLNAME() {
		return PC_ZLNAME.get();
	}

	public String getPC_ZFNAME() {
		return PC_ZFNAME.get();
	}

	public String getPC_ZMNAME() {
		return PC_ZMNAME.get();
	}

	public Long getP�_ZAGS() {
		return P�_ZAGS.get();
	}

	public String getP�_USER() {
		return P�_USER.get();
	}

	public LocalDateTime getTM$P�_DATE() {
		return TM$P�_DATE.get();
	}

	public String getP�_NUMBER() {
		return P�_NUMBER.get();
	}

	public String getP�_SERIA() {
		return P�_SERIA.get();
	}

	public LocalDate getP�_CRDATE() {
		return P�_CRDATE.get();
	}

	public String getP�_CRNAME() {
		return P�_CRNAME.get();
	}

	public LocalDate getP�_FZ() {
		return P�_FZ.get();
	}

	public LocalDate getP�_TRZ() {
		return P�_TRZ.get();
	}

	public String getP�_TYPE() {
		return P�_TYPE.get();
	}

	public Long getP�_M() {
		return P�_M.get();
	}

	public Long getP�_F() {
		return P�_F.get();
	}

	public Long getP�_CH() {
		return P�_CH.get();
	}

	public String getP�_AFT_MNAME() {
		return P�_AFT_MNAME.get();
	}

	public String getP�_AFT_FNAME() {
		return P�_AFT_FNAME.get();
	}

	public String getP�_AFT_LNAME() {
		return P�_AFT_LNAME.get();
	}

	public Long getPC_ACT_ID() {
		return PC_ACT_ID.get();
	}

	public Long getPC_ID() {
		return PC_ID.get();
	}

	public LocalDate getCHILDRENBIRTH() {
		return CHILDRENBIRTH.get();
	}

	public LocalDate getMOTHERBIRTHDATE() {
		return MOTHERBIRTHDATE.get();
	}

	public LocalDate getFATHERBIRTHDATE() {
		return FATHERBIRTHDATE.get();
	}

	public String getCHILDFIO() {
		return CHILDFIO.get();
	}

	public String getMOTHERFIO() {
		return MOTHERFIO.get();
	}

	public String getFATHERFIO() {
		return FATHERFIO.get();
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public StringProperty PC_ZPLACEProperty() {
		return PC_ZPLACE;
	}

	public StringProperty PC_ZLNAMEProperty() {
		return PC_ZLNAME;
	}

	public StringProperty PC_ZFNAMEProperty() {
		return PC_ZFNAME;
	}

	public StringProperty PC_ZMNAMEProperty() {
		return PC_ZMNAME;
	}

	public LongProperty P�_ZAGSProperty() {
		return P�_ZAGS;
	}

	public StringProperty P�_USERProperty() {
		return P�_USER;
	}

	public SimpleObjectProperty<LocalDateTime> TM$P�_DATEProperty() {
		return TM$P�_DATE;
	}

	public StringProperty P�_NUMBERProperty() {
		return P�_NUMBER;
	}

	public StringProperty P�_SERIAProperty() {
		return P�_SERIA;
	}

	public SimpleObjectProperty<LocalDate> P�_CRDATEProperty() {
		return P�_CRDATE;
	}

	public StringProperty P�_CRNAMEProperty() {
		return P�_CRNAME;
	}

	public SimpleObjectProperty<LocalDate> P�_FZProperty() {
		return P�_FZ;
	}

	public SimpleObjectProperty<LocalDate> P�_TRZProperty() {
		return P�_TRZ;
	}

	public StringProperty P�_TYPEProperty() {
		return P�_TYPE;
	}

	public LongProperty P�_MProperty() {
		return P�_M;
	}

	public LongProperty P�_FProperty() {
		return P�_F;
	}

	public LongProperty P�_CHProperty() {
		return P�_CH;
	}

	public StringProperty P�_AFT_MNAMEProperty() {
		return P�_AFT_MNAME;
	}

	public StringProperty P�_AFT_FNAMEProperty() {
		return P�_AFT_FNAME;
	}

	public StringProperty P�_AFT_LNAMEProperty() {
		return P�_AFT_LNAME;
	}

	public LongProperty PC_ACT_IDProperty() {
		return PC_ACT_ID;
	}

	public LongProperty PC_IDProperty() {
		return PC_ID;
	}

	public SimpleObjectProperty<LocalDate> CHILDRENBIRTHProperty() {
		return CHILDRENBIRTH;
	}

	public SimpleObjectProperty<LocalDate> MOTHERBIRTHDATEProperty() {
		return MOTHERBIRTHDATE;
	}

	public SimpleObjectProperty<LocalDate> FATHERBIRTHDATEProperty() {
		return FATHERBIRTHDATE;
	}

	public StringProperty CHILDFIOProperty() {
		return CHILDFIO;
	}

	public StringProperty MOTHERFIOProperty() {
		return MOTHERFIO;
	}

	public StringProperty FATHERFIOProperty() {
		return FATHERFIO;
	}
}
