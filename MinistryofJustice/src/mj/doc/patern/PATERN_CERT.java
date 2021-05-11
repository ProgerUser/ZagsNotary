package mj.doc.patern;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PATERN_CERT {
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty PC_ZPLACE;
	/** Нет данных */
	private StringProperty PC_ZLNAME;
	/** Нет данных */
	private StringProperty PC_ZFNAME;
	/** Нет данных */
	private StringProperty PC_ZMNAME;
	/** Нет данных */
	private LongProperty PС_ZAGS;
	/** Нет данных */
	private StringProperty PС_USER;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$PС_DATE;
	/** Нет данных */
	private StringProperty PС_NUMBER;
	/** Нет данных */
	private StringProperty PС_SERIA;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> PС_CRDATE;
	/** Нет данных */
	private StringProperty PС_CRNAME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> PС_FZ;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> PС_TRZ;
	/** Нет данных */
	private StringProperty PС_TYPE;
	/** Нет данных */
	private LongProperty PС_M;
	/** Нет данных */
	private LongProperty PС_F;
	/** Нет данных */
	private LongProperty PС_CH;
	/** Нет данных */
	private StringProperty PС_AFT_MNAME;
	/** Нет данных */
	private StringProperty PС_AFT_FNAME;
	/** Нет данных */
	private StringProperty PС_AFT_LNAME;
	/** Нет данных */
	private LongProperty PC_ACT_ID;
	/** Нет данных */
	private LongProperty PC_ID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CHILDRENBIRTH;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> MOTHERBIRTHDATE;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> FATHERBIRTHDATE;
	/** Нет данных */
	private StringProperty CHILDFIO;
	/** Нет данных */
	private StringProperty MOTHERFIO;
	/** Нет данных */
	private StringProperty FATHERFIO;
	/** Имя до уст. отц */
	private StringProperty BEF_FNAME;
	/** Отчество до уст. отц */
	private StringProperty BEF_MNAME;
	/** Фамилия до уст. отц */
	private StringProperty BEF_LNAME;
	/** Номер документа (проставляется пользователем) */
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
		this.PС_ZAGS = new SimpleLongProperty();
		this.PС_USER = new SimpleStringProperty();
		this.TM$PС_DATE = new SimpleObjectProperty<>();
		this.PС_NUMBER = new SimpleStringProperty();
		this.PС_SERIA = new SimpleStringProperty();
		this.PС_CRDATE = new SimpleObjectProperty<>();
		this.PС_CRNAME = new SimpleStringProperty();
		this.PС_FZ = new SimpleObjectProperty<>();
		this.PС_TRZ = new SimpleObjectProperty<>();
		this.PС_TYPE = new SimpleStringProperty();
		this.PС_M = new SimpleLongProperty();
		this.PС_F = new SimpleLongProperty();
		this.PС_CH = new SimpleLongProperty();
		this.PС_AFT_MNAME = new SimpleStringProperty();
		this.PС_AFT_FNAME = new SimpleStringProperty();
		this.PС_AFT_LNAME = new SimpleStringProperty();
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

	public void setPС_ZAGS(Long PС_ZAGS) {
		this.PС_ZAGS.set(PС_ZAGS);
	}

	public void setPС_USER(String PС_USER) {
		this.PС_USER.set(PС_USER);
	}

	public void setTM$PС_DATE(LocalDateTime TM$PС_DATE) {
		this.TM$PС_DATE.set(TM$PС_DATE);
	}

	public void setPС_NUMBER(String PС_NUMBER) {
		this.PС_NUMBER.set(PС_NUMBER);
	}

	public void setPС_SERIA(String PС_SERIA) {
		this.PС_SERIA.set(PС_SERIA);
	}

	public void setPС_CRDATE(LocalDate PС_CRDATE) {
		this.PС_CRDATE.set(PС_CRDATE);
	}

	public void setPС_CRNAME(String PС_CRNAME) {
		this.PС_CRNAME.set(PС_CRNAME);
	}

	public void setPС_FZ(LocalDate PС_FZ) {
		this.PС_FZ.set(PС_FZ);
	}

	public void setPС_TRZ(LocalDate PС_TRZ) {
		this.PС_TRZ.set(PС_TRZ);
	}

	public void setPС_TYPE(String PС_TYPE) {
		this.PС_TYPE.set(PС_TYPE);
	}

	public void setPС_M(Long PС_M) {
		this.PС_M.set(PС_M);
	}

	public void setPС_F(Long PС_F) {
		this.PС_F.set(PС_F);
	}

	public void setPС_CH(Long PС_CH) {
		this.PС_CH.set(PС_CH);
	}

	public void setPС_AFT_MNAME(String PС_AFT_MNAME) {
		this.PС_AFT_MNAME.set(PС_AFT_MNAME);
	}

	public void setPС_AFT_FNAME(String PС_AFT_FNAME) {
		this.PС_AFT_FNAME.set(PС_AFT_FNAME);
	}

	public void setPС_AFT_LNAME(String PС_AFT_LNAME) {
		this.PС_AFT_LNAME.set(PС_AFT_LNAME);
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

	public Long getPС_ZAGS() {
		return PС_ZAGS.get();
	}

	public String getPС_USER() {
		return PС_USER.get();
	}

	public LocalDateTime getTM$PС_DATE() {
		return TM$PС_DATE.get();
	}

	public String getPС_NUMBER() {
		return PС_NUMBER.get();
	}

	public String getPС_SERIA() {
		return PС_SERIA.get();
	}

	public LocalDate getPС_CRDATE() {
		return PС_CRDATE.get();
	}

	public String getPС_CRNAME() {
		return PС_CRNAME.get();
	}

	public LocalDate getPС_FZ() {
		return PС_FZ.get();
	}

	public LocalDate getPС_TRZ() {
		return PС_TRZ.get();
	}

	public String getPС_TYPE() {
		return PС_TYPE.get();
	}

	public Long getPС_M() {
		return PС_M.get();
	}

	public Long getPС_F() {
		return PС_F.get();
	}

	public Long getPС_CH() {
		return PС_CH.get();
	}

	public String getPС_AFT_MNAME() {
		return PС_AFT_MNAME.get();
	}

	public String getPС_AFT_FNAME() {
		return PС_AFT_FNAME.get();
	}

	public String getPС_AFT_LNAME() {
		return PС_AFT_LNAME.get();
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

	public LongProperty PС_ZAGSProperty() {
		return PС_ZAGS;
	}

	public StringProperty PС_USERProperty() {
		return PС_USER;
	}

	public SimpleObjectProperty<LocalDateTime> TM$PС_DATEProperty() {
		return TM$PС_DATE;
	}

	public StringProperty PС_NUMBERProperty() {
		return PС_NUMBER;
	}

	public StringProperty PС_SERIAProperty() {
		return PС_SERIA;
	}

	public SimpleObjectProperty<LocalDate> PС_CRDATEProperty() {
		return PС_CRDATE;
	}

	public StringProperty PС_CRNAMEProperty() {
		return PС_CRNAME;
	}

	public SimpleObjectProperty<LocalDate> PС_FZProperty() {
		return PС_FZ;
	}

	public SimpleObjectProperty<LocalDate> PС_TRZProperty() {
		return PС_TRZ;
	}

	public StringProperty PС_TYPEProperty() {
		return PС_TYPE;
	}

	public LongProperty PС_MProperty() {
		return PС_M;
	}

	public LongProperty PС_FProperty() {
		return PС_F;
	}

	public LongProperty PС_CHProperty() {
		return PС_CH;
	}

	public StringProperty PС_AFT_MNAMEProperty() {
		return PС_AFT_MNAME;
	}

	public StringProperty PС_AFT_FNAMEProperty() {
		return PС_AFT_FNAME;
	}

	public StringProperty PС_AFT_LNAMEProperty() {
		return PС_AFT_LNAME;
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
