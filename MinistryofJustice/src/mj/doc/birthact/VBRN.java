package mj.doc.birthact;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VBRN {
	/** ��� ������ */
	private StringProperty MOTHERNAME;
	/** ��� ������ */
	private StringProperty FATHERNAME;
	/** ��� ������ */
	private StringProperty CHILDRENNAME;
	/** ��� ������ */
	private IntegerProperty BR_ACT_PATCER;
	/** ��� ������ */
	private StringProperty BR_ACT_SERIA;
	/** ��� ������ */
	private StringProperty BR_ACT_NUM;
	/** ��� ������ */
	private IntegerProperty BR_ACT_MERCER_ID;
	/** ��� ������ */
	private StringProperty BR_ACT_FADREG_ADR;
	/** ��� ������ */
	private StringProperty BR_ACT_FADORG_NAME;
	/** ��� ������ */
	private StringProperty BR_ACT_FADLOCATION;
	/** ��� ������ */
	private StringProperty BR_ACT_FADMIDDLE_NAME;
	/** ��� ������ */
	private StringProperty BR_ACT_FADLAST_NAME;
	/** ��� ������ */
	private StringProperty BR_ACT_FADFIRST_NAME;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> BR_ACT_DCOURT;
	/** ��� ������ */
	private StringProperty BR_ACT_DESCCOURT;
	/** ��� ������ */
	private StringProperty BR_ACT_NAMECOURT;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> BR_ACT_DATEDOCB;
	/** ��� ������ */
	private StringProperty BR_ACT_FIOB;
	/** ��� ������ */
	private StringProperty BR_ACT_NDOCA;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> BR_ACT_DATEDOCA;
	/** ��� ������ */
	private StringProperty BR_ACT_MEDORGA;
	/** ��� ������ */
	private IntegerProperty BR_ACT_M;
	/** ��� ������ */
	private IntegerProperty BR_ACT_F;
	/** ��� ������ */
	private IntegerProperty BR_ACT_CH;
	/** ��� ������ */
	private StringProperty BR_ACT_DBF;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> BR_ACT_MZDATE;
	/** ��� ������ */
	private StringProperty BR_ACT_TGRABF;
	/** ��� ������ */
	private StringProperty BR_ACT_USER;
	/** ��� ������ */
	private IntegerProperty BR_ACT_ZGID;
	/** ��� ������ */
	private StringProperty BR_ACT_LD;
	/** ��� ������ */
	private IntegerProperty BR_ACT_BRCHCNT;
	/** ��� ������ */
	private StringProperty BR_ACT_ZTP;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> BR_ACT_DATE;
	/** ��� ������ */
	private IntegerProperty BR_ACT_ID;

	public VBRN() {
		this.MOTHERNAME = new SimpleStringProperty();
		this.FATHERNAME = new SimpleStringProperty();
		this.CHILDRENNAME = new SimpleStringProperty();
		this.BR_ACT_PATCER = new SimpleIntegerProperty();
		this.BR_ACT_SERIA = new SimpleStringProperty();
		this.BR_ACT_NUM = new SimpleStringProperty();
		this.BR_ACT_MERCER_ID = new SimpleIntegerProperty();
		this.BR_ACT_FADREG_ADR = new SimpleStringProperty();
		this.BR_ACT_FADORG_NAME = new SimpleStringProperty();
		this.BR_ACT_FADLOCATION = new SimpleStringProperty();
		this.BR_ACT_FADMIDDLE_NAME = new SimpleStringProperty();
		this.BR_ACT_FADLAST_NAME = new SimpleStringProperty();
		this.BR_ACT_FADFIRST_NAME = new SimpleStringProperty();
		this.BR_ACT_DCOURT = new SimpleObjectProperty<>();
		this.BR_ACT_DESCCOURT = new SimpleStringProperty();
		this.BR_ACT_NAMECOURT = new SimpleStringProperty();
		this.BR_ACT_DATEDOCB = new SimpleObjectProperty<>();
		this.BR_ACT_FIOB = new SimpleStringProperty();
		this.BR_ACT_NDOCA = new SimpleStringProperty();
		this.BR_ACT_DATEDOCA = new SimpleObjectProperty<>();
		this.BR_ACT_MEDORGA = new SimpleStringProperty();
		this.BR_ACT_M = new SimpleIntegerProperty();
		this.BR_ACT_F = new SimpleIntegerProperty();
		this.BR_ACT_CH = new SimpleIntegerProperty();
		this.BR_ACT_DBF = new SimpleStringProperty();
		this.BR_ACT_MZDATE = new SimpleObjectProperty<>();
		this.BR_ACT_TGRABF = new SimpleStringProperty();
		this.BR_ACT_USER = new SimpleStringProperty();
		this.BR_ACT_ZGID = new SimpleIntegerProperty();
		this.BR_ACT_LD = new SimpleStringProperty();
		this.BR_ACT_BRCHCNT = new SimpleIntegerProperty();
		this.BR_ACT_ZTP = new SimpleStringProperty();
		this.BR_ACT_DATE = new SimpleObjectProperty<>();
		this.BR_ACT_ID = new SimpleIntegerProperty();
	}

	public void setMOTHERNAME(String MOTHERNAME) {
		this.MOTHERNAME.set(MOTHERNAME);
	}

	public void setFATHERNAME(String FATHERNAME) {
		this.FATHERNAME.set(FATHERNAME);
	}

	public void setCHILDRENNAME(String CHILDRENNAME) {
		this.CHILDRENNAME.set(CHILDRENNAME);
	}

	public void setBR_ACT_PATCER(Integer BR_ACT_PATCER) {
		this.BR_ACT_PATCER.set(BR_ACT_PATCER);
	}

	public void setBR_ACT_SERIA(String BR_ACT_SERIA) {
		this.BR_ACT_SERIA.set(BR_ACT_SERIA);
	}

	public void setBR_ACT_NUM(String BR_ACT_NUM) {
		this.BR_ACT_NUM.set(BR_ACT_NUM);
	}

	public void setBR_ACT_MERCER_ID(Integer BR_ACT_MERCER_ID) {
		this.BR_ACT_MERCER_ID.set(BR_ACT_MERCER_ID);
	}

	public void setBR_ACT_FADREG_ADR(String BR_ACT_FADREG_ADR) {
		this.BR_ACT_FADREG_ADR.set(BR_ACT_FADREG_ADR);
	}

	public void setBR_ACT_FADORG_NAME(String BR_ACT_FADORG_NAME) {
		this.BR_ACT_FADORG_NAME.set(BR_ACT_FADORG_NAME);
	}

	public void setBR_ACT_FADLOCATION(String BR_ACT_FADLOCATION) {
		this.BR_ACT_FADLOCATION.set(BR_ACT_FADLOCATION);
	}

	public void setBR_ACT_FADMIDDLE_NAME(String BR_ACT_FADMIDDLE_NAME) {
		this.BR_ACT_FADMIDDLE_NAME.set(BR_ACT_FADMIDDLE_NAME);
	}

	public void setBR_ACT_FADLAST_NAME(String BR_ACT_FADLAST_NAME) {
		this.BR_ACT_FADLAST_NAME.set(BR_ACT_FADLAST_NAME);
	}

	public void setBR_ACT_FADFIRST_NAME(String BR_ACT_FADFIRST_NAME) {
		this.BR_ACT_FADFIRST_NAME.set(BR_ACT_FADFIRST_NAME);
	}

	public void setBR_ACT_DCOURT(LocalDate BR_ACT_DCOURT) {
		this.BR_ACT_DCOURT.set(BR_ACT_DCOURT);
	}

	public void setBR_ACT_DESCCOURT(String BR_ACT_DESCCOURT) {
		this.BR_ACT_DESCCOURT.set(BR_ACT_DESCCOURT);
	}

	public void setBR_ACT_NAMECOURT(String BR_ACT_NAMECOURT) {
		this.BR_ACT_NAMECOURT.set(BR_ACT_NAMECOURT);
	}

	public void setBR_ACT_DATEDOCB(LocalDate BR_ACT_DATEDOCB) {
		this.BR_ACT_DATEDOCB.set(BR_ACT_DATEDOCB);
	}

	public void setBR_ACT_FIOB(String BR_ACT_FIOB) {
		this.BR_ACT_FIOB.set(BR_ACT_FIOB);
	}

	public void setBR_ACT_NDOCA(String BR_ACT_NDOCA) {
		this.BR_ACT_NDOCA.set(BR_ACT_NDOCA);
	}

	public void setBR_ACT_DATEDOCA(LocalDate BR_ACT_DATEDOCA) {
		this.BR_ACT_DATEDOCA.set(BR_ACT_DATEDOCA);
	}

	public void setBR_ACT_MEDORGA(String BR_ACT_MEDORGA) {
		this.BR_ACT_MEDORGA.set(BR_ACT_MEDORGA);
	}

	public void setBR_ACT_M(Integer BR_ACT_M) {
		this.BR_ACT_M.set(BR_ACT_M);
	}

	public void setBR_ACT_F(Integer BR_ACT_F) {
		this.BR_ACT_F.set(BR_ACT_F);
	}

	public void setBR_ACT_CH(Integer BR_ACT_CH) {
		this.BR_ACT_CH.set(BR_ACT_CH);
	}

	public void setBR_ACT_DBF(String BR_ACT_DBF) {
		this.BR_ACT_DBF.set(BR_ACT_DBF);
	}

	public void setBR_ACT_MZDATE(LocalDate BR_ACT_MZDATE) {
		this.BR_ACT_MZDATE.set(BR_ACT_MZDATE);
	}

	public void setBR_ACT_TGRABF(String BR_ACT_TGRABF) {
		this.BR_ACT_TGRABF.set(BR_ACT_TGRABF);
	}

	public void setBR_ACT_USER(String BR_ACT_USER) {
		this.BR_ACT_USER.set(BR_ACT_USER);
	}

	public void setBR_ACT_ZGID(Integer BR_ACT_ZGID) {
		this.BR_ACT_ZGID.set(BR_ACT_ZGID);
	}

	public void setBR_ACT_LD(String BR_ACT_LD) {
		this.BR_ACT_LD.set(BR_ACT_LD);
	}

	public void setBR_ACT_BRCHCNT(Integer BR_ACT_BRCHCNT) {
		this.BR_ACT_BRCHCNT.set(BR_ACT_BRCHCNT);
	}

	public void setBR_ACT_ZTP(String BR_ACT_ZTP) {
		this.BR_ACT_ZTP.set(BR_ACT_ZTP);
	}

	public void setBR_ACT_DATE(LocalDate BR_ACT_DATE) {
		this.BR_ACT_DATE.set(BR_ACT_DATE);
	}

	public void setBR_ACT_ID(Integer BR_ACT_ID) {
		this.BR_ACT_ID.set(BR_ACT_ID);
	}

	public String getMOTHERNAME() {
		return MOTHERNAME.get();
	}

	public String getFATHERNAME() {
		return FATHERNAME.get();
	}

	public String getCHILDRENNAME() {
		return CHILDRENNAME.get();
	}

	public Integer getBR_ACT_PATCER() {
		return BR_ACT_PATCER.get();
	}

	public String getBR_ACT_SERIA() {
		return BR_ACT_SERIA.get();
	}

	public String getBR_ACT_NUM() {
		return BR_ACT_NUM.get();
	}

	public Integer getBR_ACT_MERCER_ID() {
		return BR_ACT_MERCER_ID.get();
	}

	public String getBR_ACT_FADREG_ADR() {
		return BR_ACT_FADREG_ADR.get();
	}

	public String getBR_ACT_FADORG_NAME() {
		return BR_ACT_FADORG_NAME.get();
	}

	public String getBR_ACT_FADLOCATION() {
		return BR_ACT_FADLOCATION.get();
	}

	public String getBR_ACT_FADMIDDLE_NAME() {
		return BR_ACT_FADMIDDLE_NAME.get();
	}

	public String getBR_ACT_FADLAST_NAME() {
		return BR_ACT_FADLAST_NAME.get();
	}

	public String getBR_ACT_FADFIRST_NAME() {
		return BR_ACT_FADFIRST_NAME.get();
	}

	public LocalDate getBR_ACT_DCOURT() {
		return BR_ACT_DCOURT.get();
	}

	public String getBR_ACT_DESCCOURT() {
		return BR_ACT_DESCCOURT.get();
	}

	public String getBR_ACT_NAMECOURT() {
		return BR_ACT_NAMECOURT.get();
	}

	public LocalDate getBR_ACT_DATEDOCB() {
		return BR_ACT_DATEDOCB.get();
	}

	public String getBR_ACT_FIOB() {
		return BR_ACT_FIOB.get();
	}

	public String getBR_ACT_NDOCA() {
		return BR_ACT_NDOCA.get();
	}

	public LocalDate getBR_ACT_DATEDOCA() {
		return BR_ACT_DATEDOCA.get();
	}

	public String getBR_ACT_MEDORGA() {
		return BR_ACT_MEDORGA.get();
	}

	public Integer getBR_ACT_M() {
		return BR_ACT_M.get();
	}

	public Integer getBR_ACT_F() {
		return BR_ACT_F.get();
	}

	public Integer getBR_ACT_CH() {
		return BR_ACT_CH.get();
	}

	public String getBR_ACT_DBF() {
		return BR_ACT_DBF.get();
	}

	public LocalDate getBR_ACT_MZDATE() {
		return BR_ACT_MZDATE.get();
	}

	public String getBR_ACT_TGRABF() {
		return BR_ACT_TGRABF.get();
	}

	public String getBR_ACT_USER() {
		return BR_ACT_USER.get();
	}

	public Integer getBR_ACT_ZGID() {
		return BR_ACT_ZGID.get();
	}

	public String getBR_ACT_LD() {
		return BR_ACT_LD.get();
	}

	public Integer getBR_ACT_BRCHCNT() {
		return BR_ACT_BRCHCNT.get();
	}

	public String getBR_ACT_ZTP() {
		return BR_ACT_ZTP.get();
	}

	public LocalDate getBR_ACT_DATE() {
		return BR_ACT_DATE.get();
	}

	public Integer getBR_ACT_ID() {
		return BR_ACT_ID.get();
	}

	public StringProperty MOTHERNAMEProperty() {
		return MOTHERNAME;
	}

	public StringProperty FATHERNAMEProperty() {
		return FATHERNAME;
	}

	public StringProperty CHILDRENNAMEProperty() {
		return CHILDRENNAME;
	}

	public IntegerProperty BR_ACT_PATCERProperty() {
		return BR_ACT_PATCER;
	}

	public StringProperty BR_ACT_SERIAProperty() {
		return BR_ACT_SERIA;
	}

	public StringProperty BR_ACT_NUMProperty() {
		return BR_ACT_NUM;
	}

	public IntegerProperty BR_ACT_MERCER_IDProperty() {
		return BR_ACT_MERCER_ID;
	}

	public StringProperty BR_ACT_FADREG_ADRProperty() {
		return BR_ACT_FADREG_ADR;
	}

	public StringProperty BR_ACT_FADORG_NAMEProperty() {
		return BR_ACT_FADORG_NAME;
	}

	public StringProperty BR_ACT_FADLOCATIONProperty() {
		return BR_ACT_FADLOCATION;
	}

	public StringProperty BR_ACT_FADMIDDLE_NAMEProperty() {
		return BR_ACT_FADMIDDLE_NAME;
	}

	public StringProperty BR_ACT_FADLAST_NAMEProperty() {
		return BR_ACT_FADLAST_NAME;
	}

	public StringProperty BR_ACT_FADFIRST_NAMEProperty() {
		return BR_ACT_FADFIRST_NAME;
	}

	public SimpleObjectProperty<LocalDate> BR_ACT_DCOURTProperty() {
		return BR_ACT_DCOURT;
	}

	public StringProperty BR_ACT_DESCCOURTProperty() {
		return BR_ACT_DESCCOURT;
	}

	public StringProperty BR_ACT_NAMECOURTProperty() {
		return BR_ACT_NAMECOURT;
	}

	public SimpleObjectProperty<LocalDate> BR_ACT_DATEDOCBProperty() {
		return BR_ACT_DATEDOCB;
	}

	public StringProperty BR_ACT_FIOBProperty() {
		return BR_ACT_FIOB;
	}

	public StringProperty BR_ACT_NDOCAProperty() {
		return BR_ACT_NDOCA;
	}

	public SimpleObjectProperty<LocalDate> BR_ACT_DATEDOCAProperty() {
		return BR_ACT_DATEDOCA;
	}

	public StringProperty BR_ACT_MEDORGAProperty() {
		return BR_ACT_MEDORGA;
	}

	public IntegerProperty BR_ACT_MProperty() {
		return BR_ACT_M;
	}

	public IntegerProperty BR_ACT_FProperty() {
		return BR_ACT_F;
	}

	public IntegerProperty BR_ACT_CHProperty() {
		return BR_ACT_CH;
	}

	public StringProperty BR_ACT_DBFProperty() {
		return BR_ACT_DBF;
	}

	public SimpleObjectProperty<LocalDate> BR_ACT_MZDATEProperty() {
		return BR_ACT_MZDATE;
	}

	public StringProperty BR_ACT_TGRABFProperty() {
		return BR_ACT_TGRABF;
	}

	public StringProperty BR_ACT_USERProperty() {
		return BR_ACT_USER;
	}

	public IntegerProperty BR_ACT_ZGIDProperty() {
		return BR_ACT_ZGID;
	}

	public StringProperty BR_ACT_LDProperty() {
		return BR_ACT_LD;
	}

	public IntegerProperty BR_ACT_BRCHCNTProperty() {
		return BR_ACT_BRCHCNT;
	}

	public StringProperty BR_ACT_ZTPProperty() {
		return BR_ACT_ZTP;
	}

	public SimpleObjectProperty<LocalDate> BR_ACT_DATEProperty() {
		return BR_ACT_DATE;
	}

	public IntegerProperty BR_ACT_IDProperty() {
		return BR_ACT_ID;
	}
}
