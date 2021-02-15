package mj.doc.death;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DEATH_CERT {
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private IntegerProperty DC_ZAGS;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DC_OPEN;
	/** Нет данных */
	private StringProperty DC_USR;
	/** Нет данных */
	private StringProperty DC_NUMBER;
	/** Нет данных */
	private StringProperty DC_SERIA;
	/** Нет данных */
	private StringProperty DC_FADREG_ADR;
	/** Нет данных */
	private StringProperty DC_FADORG_NAME;
	/** Нет данных */
	private StringProperty DC_FADLOCATION;
	/** Нет данных */
	private StringProperty DC_FADMIDDLE_NAME;
	/** Нет данных */
	private StringProperty DC_FADLAST_NAME;
	/** Нет данных */
	private StringProperty DC_FADFIRST_NAME;
	/** Нет данных */
	private StringProperty DC_ZTP;
	/** Нет данных */
	private StringProperty DC_LLOC;
	/** Нет данных */
	private StringProperty DC_NRNAME;
	/** Нет данных */
	private StringProperty DC_RCNAME;
	/** Нет данных */
	private StringProperty DC_FMON;
	/** Нет данных */
	private StringProperty DC_FTYPE;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DC_FD;
	/** Нет данных */
	private StringProperty DC_FNUM;
	/** Нет данных */
	private StringProperty DC_CD;
	/** Нет данных */
	private StringProperty DC_DPL;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DC_DD;
	/** Нет данных */
	private IntegerProperty DC_CUS;
	/** Нет данных */
	private IntegerProperty DC_ID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DBDATE;
	/** Нет данных */
	private StringProperty DFIO;

	public DEATH_CERT() {
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.DC_ZAGS = new SimpleIntegerProperty();
		this.TM$DC_OPEN = new SimpleObjectProperty<>();
		this.DC_USR = new SimpleStringProperty();
		this.DC_NUMBER = new SimpleStringProperty();
		this.DC_SERIA = new SimpleStringProperty();
		this.DC_FADREG_ADR = new SimpleStringProperty();
		this.DC_FADORG_NAME = new SimpleStringProperty();
		this.DC_FADLOCATION = new SimpleStringProperty();
		this.DC_FADMIDDLE_NAME = new SimpleStringProperty();
		this.DC_FADLAST_NAME = new SimpleStringProperty();
		this.DC_FADFIRST_NAME = new SimpleStringProperty();
		this.DC_ZTP = new SimpleStringProperty();
		this.DC_LLOC = new SimpleStringProperty();
		this.DC_NRNAME = new SimpleStringProperty();
		this.DC_RCNAME = new SimpleStringProperty();
		this.DC_FMON = new SimpleStringProperty();
		this.DC_FTYPE = new SimpleStringProperty();
		this.DC_FD = new SimpleObjectProperty<>();
		this.DC_FNUM = new SimpleStringProperty();
		this.DC_CD = new SimpleStringProperty();
		this.DC_DPL = new SimpleStringProperty();
		this.DC_DD = new SimpleObjectProperty<>();
		this.DC_CUS = new SimpleIntegerProperty();
		this.DC_ID = new SimpleIntegerProperty();
		this.DBDATE = new SimpleObjectProperty<>();
		this.DFIO = new SimpleStringProperty();
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setDC_ZAGS(Integer DC_ZAGS) {
		this.DC_ZAGS.set(DC_ZAGS);
	}

	public void setTM$DC_OPEN(LocalDateTime TM$DC_OPEN) {
		this.TM$DC_OPEN.set(TM$DC_OPEN);
	}

	public void setDC_USR(String DC_USR) {
		this.DC_USR.set(DC_USR);
	}

	public void setDC_NUMBER(String DC_NUMBER) {
		this.DC_NUMBER.set(DC_NUMBER);
	}

	public void setDC_SERIA(String DC_SERIA) {
		this.DC_SERIA.set(DC_SERIA);
	}

	public void setDC_FADREG_ADR(String DC_FADREG_ADR) {
		this.DC_FADREG_ADR.set(DC_FADREG_ADR);
	}

	public void setDC_FADORG_NAME(String DC_FADORG_NAME) {
		this.DC_FADORG_NAME.set(DC_FADORG_NAME);
	}

	public void setDC_FADLOCATION(String DC_FADLOCATION) {
		this.DC_FADLOCATION.set(DC_FADLOCATION);
	}

	public void setDC_FADMIDDLE_NAME(String DC_FADMIDDLE_NAME) {
		this.DC_FADMIDDLE_NAME.set(DC_FADMIDDLE_NAME);
	}

	public void setDC_FADLAST_NAME(String DC_FADLAST_NAME) {
		this.DC_FADLAST_NAME.set(DC_FADLAST_NAME);
	}

	public void setDC_FADFIRST_NAME(String DC_FADFIRST_NAME) {
		this.DC_FADFIRST_NAME.set(DC_FADFIRST_NAME);
	}

	public void setDC_ZTP(String DC_ZTP) {
		this.DC_ZTP.set(DC_ZTP);
	}

	public void setDC_LLOC(String DC_LLOC) {
		this.DC_LLOC.set(DC_LLOC);
	}

	public void setDC_NRNAME(String DC_NRNAME) {
		this.DC_NRNAME.set(DC_NRNAME);
	}

	public void setDC_RCNAME(String DC_RCNAME) {
		this.DC_RCNAME.set(DC_RCNAME);
	}

	public void setDC_FMON(String DC_FMON) {
		this.DC_FMON.set(DC_FMON);
	}

	public void setDC_FTYPE(String DC_FTYPE) {
		this.DC_FTYPE.set(DC_FTYPE);
	}

	public void setDC_FD(LocalDate DC_FD) {
		this.DC_FD.set(DC_FD);
	}

	public void setDC_FNUM(String DC_FNUM) {
		this.DC_FNUM.set(DC_FNUM);
	}

	public void setDC_CD(String DC_CD) {
		this.DC_CD.set(DC_CD);
	}

	public void setDC_DPL(String DC_DPL) {
		this.DC_DPL.set(DC_DPL);
	}

	public void setDC_DD(LocalDate DC_DD) {
		this.DC_DD.set(DC_DD);
	}

	public void setDC_CUS(Integer DC_CUS) {
		this.DC_CUS.set(DC_CUS);
	}

	public void setDC_ID(Integer DC_ID) {
		this.DC_ID.set(DC_ID);
	}

	public void setDBDATE(LocalDate DBDATE) {
		this.DBDATE.set(DBDATE);
	}

	public void setDFIO(String DFIO) {
		this.DFIO.set(DFIO);
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public Integer getDC_ZAGS() {
		return DC_ZAGS.get();
	}

	public LocalDateTime getTM$DC_OPEN() {
		return TM$DC_OPEN.get();
	}

	public String getDC_USR() {
		return DC_USR.get();
	}

	public String getDC_NUMBER() {
		return DC_NUMBER.get();
	}

	public String getDC_SERIA() {
		return DC_SERIA.get();
	}

	public String getDC_FADREG_ADR() {
		return DC_FADREG_ADR.get();
	}

	public String getDC_FADORG_NAME() {
		return DC_FADORG_NAME.get();
	}

	public String getDC_FADLOCATION() {
		return DC_FADLOCATION.get();
	}

	public String getDC_FADMIDDLE_NAME() {
		return DC_FADMIDDLE_NAME.get();
	}

	public String getDC_FADLAST_NAME() {
		return DC_FADLAST_NAME.get();
	}

	public String getDC_FADFIRST_NAME() {
		return DC_FADFIRST_NAME.get();
	}

	public String getDC_ZTP() {
		return DC_ZTP.get();
	}

	public String getDC_LLOC() {
		return DC_LLOC.get();
	}

	public String getDC_NRNAME() {
		return DC_NRNAME.get();
	}

	public String getDC_RCNAME() {
		return DC_RCNAME.get();
	}

	public String getDC_FMON() {
		return DC_FMON.get();
	}

	public String getDC_FTYPE() {
		return DC_FTYPE.get();
	}

	public LocalDate getDC_FD() {
		return DC_FD.get();
	}

	public String getDC_FNUM() {
		return DC_FNUM.get();
	}

	public String getDC_CD() {
		return DC_CD.get();
	}

	public String getDC_DPL() {
		return DC_DPL.get();
	}

	public LocalDate getDC_DD() {
		return DC_DD.get();
	}

	public Integer getDC_CUS() {
		return DC_CUS.get();
	}

	public Integer getDC_ID() {
		return DC_ID.get();
	}

	public LocalDate getDBDATE() {
		return DBDATE.get();
	}

	public String getDFIO() {
		return DFIO.get();
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public IntegerProperty DC_ZAGSProperty() {
		return DC_ZAGS;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DC_OPENProperty() {
		return TM$DC_OPEN;
	}

	public StringProperty DC_USRProperty() {
		return DC_USR;
	}

	public StringProperty DC_NUMBERProperty() {
		return DC_NUMBER;
	}

	public StringProperty DC_SERIAProperty() {
		return DC_SERIA;
	}

	public StringProperty DC_FADREG_ADRProperty() {
		return DC_FADREG_ADR;
	}

	public StringProperty DC_FADORG_NAMEProperty() {
		return DC_FADORG_NAME;
	}

	public StringProperty DC_FADLOCATIONProperty() {
		return DC_FADLOCATION;
	}

	public StringProperty DC_FADMIDDLE_NAMEProperty() {
		return DC_FADMIDDLE_NAME;
	}

	public StringProperty DC_FADLAST_NAMEProperty() {
		return DC_FADLAST_NAME;
	}

	public StringProperty DC_FADFIRST_NAMEProperty() {
		return DC_FADFIRST_NAME;
	}

	public StringProperty DC_ZTPProperty() {
		return DC_ZTP;
	}

	public StringProperty DC_LLOCProperty() {
		return DC_LLOC;
	}

	public StringProperty DC_NRNAMEProperty() {
		return DC_NRNAME;
	}

	public StringProperty DC_RCNAMEProperty() {
		return DC_RCNAME;
	}

	public StringProperty DC_FMONProperty() {
		return DC_FMON;
	}

	public StringProperty DC_FTYPEProperty() {
		return DC_FTYPE;
	}

	public SimpleObjectProperty<LocalDate> DC_FDProperty() {
		return DC_FD;
	}

	public StringProperty DC_FNUMProperty() {
		return DC_FNUM;
	}

	public StringProperty DC_CDProperty() {
		return DC_CD;
	}

	public StringProperty DC_DPLProperty() {
		return DC_DPL;
	}

	public SimpleObjectProperty<LocalDate> DC_DDProperty() {
		return DC_DD;
	}

	public IntegerProperty DC_CUSProperty() {
		return DC_CUS;
	}

	public IntegerProperty DC_IDProperty() {
		return DC_ID;
	}

	public SimpleObjectProperty<LocalDate> DBDATEProperty() {
		return DBDATE;
	}

	public StringProperty DFIOProperty() {
		return DFIO;
	}
}
