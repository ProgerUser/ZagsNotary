package mj.doc.divorce;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DIVORCE_CERT {
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty DIVC_ZPLACE;
	/** Нет данных */
	private StringProperty DIVC_ZMNAME;
	/** Нет данных */
	private StringProperty DIVC_ZАNAME;
	/** Нет данных */
	private StringProperty DIVC_ZLNAME;
	/** Нет данных */
	private LongProperty DIVC_ZAGS;
	/** Нет данных */
	private StringProperty DIVC_SERIA;
	/** Нет данных */
	private StringProperty DIVC_NUM;
	/** Нет данных */
	private LongProperty DIVC_MC_MERCER;
	/** Нет данных */
	private LongProperty DIVC_ZOSPRISON;
	/** Нет данных */
	private StringProperty DIVC_ZOSFIO2;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DIVC_ZOSCD2;
	/** Нет данных */
	private LongProperty DIVC_ZOSCN2;
	/** Нет данных */
	private StringProperty DIVC_ZOSFIO;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DIVC_ZOSCD;
	/** Нет данных */
	private LongProperty DIVC_ZOSCN;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DIVC_CAD;
	/** Нет данных */
	private LongProperty DIVC_CAN;
	/** Нет данных */
	private StringProperty DIVC_TCHNUM;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DIVC_TCHD;
	/** Нет данных */
	private StringProperty DIVC_TYPE;
	/** Нет данных */
	private StringProperty DIVC_USR;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DIVC_DT;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DIVC_DATE;
	/** Нет данных */
	private StringProperty DIVC_SHE_LNAFT;
	/** Нет данных */
	private StringProperty DIVC_SHE_LNBEF;
	/** Нет данных */
	private StringProperty DIVC_HE_LNAFT;
	/** Нет данных */
	private StringProperty DIVC_HE_LNBEF;
	/** Нет данных */
	private LongProperty DIVC_SHE;
	/** Нет данных */
	private LongProperty DIVC_HE;
	/** Нет данных */
	private LongProperty DIVC_ID;
	/** Нет данных */
	private StringProperty SHEFIO;
	/** Нет данных */
	private StringProperty HEFIO;
	/** Номер документа */
	private StringProperty DOC_NUMBER;

	public DIVORCE_CERT() {
		this.DOC_NUMBER = new SimpleStringProperty();
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.DIVC_ZPLACE = new SimpleStringProperty();
		this.DIVC_ZMNAME = new SimpleStringProperty();
		this.DIVC_ZАNAME = new SimpleStringProperty();
		this.DIVC_ZLNAME = new SimpleStringProperty();
		this.DIVC_ZAGS = new SimpleLongProperty();
		this.DIVC_SERIA = new SimpleStringProperty();
		this.DIVC_NUM = new SimpleStringProperty();
		this.DIVC_MC_MERCER = new SimpleLongProperty();
		this.DIVC_ZOSPRISON = new SimpleLongProperty();
		this.DIVC_ZOSFIO2 = new SimpleStringProperty();
		this.DIVC_ZOSCD2 = new SimpleObjectProperty<>();
		this.DIVC_ZOSCN2 = new SimpleLongProperty();
		this.DIVC_ZOSFIO = new SimpleStringProperty();
		this.DIVC_ZOSCD = new SimpleObjectProperty<>();
		this.DIVC_ZOSCN = new SimpleLongProperty();
		this.DIVC_CAD = new SimpleObjectProperty<>();
		this.DIVC_CAN = new SimpleLongProperty();
		this.DIVC_TCHNUM = new SimpleStringProperty();
		this.DIVC_TCHD = new SimpleObjectProperty<>();
		this.DIVC_TYPE = new SimpleStringProperty();
		this.DIVC_USR = new SimpleStringProperty();
		this.DIVC_DT = new SimpleObjectProperty<>();
		this.TM$DIVC_DATE = new SimpleObjectProperty<>();
		this.DIVC_SHE_LNAFT = new SimpleStringProperty();
		this.DIVC_SHE_LNBEF = new SimpleStringProperty();
		this.DIVC_HE_LNAFT = new SimpleStringProperty();
		this.DIVC_HE_LNBEF = new SimpleStringProperty();
		this.DIVC_SHE = new SimpleLongProperty();
		this.DIVC_HE = new SimpleLongProperty();
		this.DIVC_ID = new SimpleLongProperty();
		this.SHEFIO = new SimpleStringProperty();
		this.HEFIO = new SimpleStringProperty();
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

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setDIVC_ZPLACE(String DIVC_ZPLACE) {
		this.DIVC_ZPLACE.set(DIVC_ZPLACE);
	}

	public void setDIVC_ZMNAME(String DIVC_ZMNAME) {
		this.DIVC_ZMNAME.set(DIVC_ZMNAME);
	}

	public void setDIVC_ZАNAME(String DIVC_ZАNAME) {
		this.DIVC_ZАNAME.set(DIVC_ZАNAME);
	}

	public void setDIVC_ZLNAME(String DIVC_ZLNAME) {
		this.DIVC_ZLNAME.set(DIVC_ZLNAME);
	}

	public void setDIVC_ZAGS(Long DIVC_ZAGS) {
		this.DIVC_ZAGS.set(DIVC_ZAGS);
	}

	public void setDIVC_SERIA(String DIVC_SERIA) {
		this.DIVC_SERIA.set(DIVC_SERIA);
	}

	public void setDIVC_NUM(String DIVC_NUM) {
		this.DIVC_NUM.set(DIVC_NUM);
	}

	public void setDIVC_MC_MERCER(Long DIVC_MC_MERCER) {
		this.DIVC_MC_MERCER.set(DIVC_MC_MERCER);
	}

	public void setDIVC_ZOSPRISON(Long DIVC_ZOSPRISON) {
		this.DIVC_ZOSPRISON.set(DIVC_ZOSPRISON);
	}

	public void setDIVC_ZOSFIO2(String DIVC_ZOSFIO2) {
		this.DIVC_ZOSFIO2.set(DIVC_ZOSFIO2);
	}

	public void setDIVC_ZOSCD2(LocalDate DIVC_ZOSCD2) {
		this.DIVC_ZOSCD2.set(DIVC_ZOSCD2);
	}

	public void setDIVC_ZOSCN2(Long DIVC_ZOSCN2) {
		this.DIVC_ZOSCN2.set(DIVC_ZOSCN2);
	}

	public void setDIVC_ZOSFIO(String DIVC_ZOSFIO) {
		this.DIVC_ZOSFIO.set(DIVC_ZOSFIO);
	}

	public void setDIVC_ZOSCD(LocalDate DIVC_ZOSCD) {
		this.DIVC_ZOSCD.set(DIVC_ZOSCD);
	}

	public void setDIVC_ZOSCN(Long DIVC_ZOSCN) {
		this.DIVC_ZOSCN.set(DIVC_ZOSCN);
	}

	public void setDIVC_CAD(LocalDate DIVC_CAD) {
		this.DIVC_CAD.set(DIVC_CAD);
	}

	public void setDIVC_CAN(Long DIVC_CAN) {
		this.DIVC_CAN.set(DIVC_CAN);
	}

	public void setDIVC_TCHNUM(String DIVC_TCHNUM) {
		this.DIVC_TCHNUM.set(DIVC_TCHNUM);
	}

	public void setDIVC_TCHD(LocalDate DIVC_TCHD) {
		this.DIVC_TCHD.set(DIVC_TCHD);
	}

	public void setDIVC_TYPE(String DIVC_TYPE) {
		this.DIVC_TYPE.set(DIVC_TYPE);
	}

	public void setDIVC_USR(String DIVC_USR) {
		this.DIVC_USR.set(DIVC_USR);
	}

	public void setDIVC_DT(LocalDate DIVC_DT) {
		this.DIVC_DT.set(DIVC_DT);
	}

	public void setTM$DIVC_DATE(LocalDateTime TM$DIVC_DATE) {
		this.TM$DIVC_DATE.set(TM$DIVC_DATE);
	}

	public void setDIVC_SHE_LNAFT(String DIVC_SHE_LNAFT) {
		this.DIVC_SHE_LNAFT.set(DIVC_SHE_LNAFT);
	}

	public void setDIVC_SHE_LNBEF(String DIVC_SHE_LNBEF) {
		this.DIVC_SHE_LNBEF.set(DIVC_SHE_LNBEF);
	}

	public void setDIVC_HE_LNAFT(String DIVC_HE_LNAFT) {
		this.DIVC_HE_LNAFT.set(DIVC_HE_LNAFT);
	}

	public void setDIVC_HE_LNBEF(String DIVC_HE_LNBEF) {
		this.DIVC_HE_LNBEF.set(DIVC_HE_LNBEF);
	}

	public void setDIVC_SHE(Long DIVC_SHE) {
		this.DIVC_SHE.set(DIVC_SHE);
	}

	public void setDIVC_HE(Long DIVC_HE) {
		this.DIVC_HE.set(DIVC_HE);
	}

	public void setDIVC_ID(Long DIVC_ID) {
		this.DIVC_ID.set(DIVC_ID);
	}

	public void setSHEFIO(String SHEFIO) {
		this.SHEFIO.set(SHEFIO);
	}

	public void setHEFIO(String HEFIO) {
		this.HEFIO.set(HEFIO);
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public String getDIVC_ZPLACE() {
		return DIVC_ZPLACE.get();
	}

	public String getDIVC_ZMNAME() {
		return DIVC_ZMNAME.get();
	}

	public String getDIVC_ZАNAME() {
		return DIVC_ZАNAME.get();
	}

	public String getDIVC_ZLNAME() {
		return DIVC_ZLNAME.get();
	}

	public Long getDIVC_ZAGS() {
		return DIVC_ZAGS.get();
	}

	public String getDIVC_SERIA() {
		return DIVC_SERIA.get();
	}

	public String getDIVC_NUM() {
		return DIVC_NUM.get();
	}

	public Long getDIVC_MC_MERCER() {
		return DIVC_MC_MERCER.get();
	}

	public Long getDIVC_ZOSPRISON() {
		return DIVC_ZOSPRISON.get();
	}

	public String getDIVC_ZOSFIO2() {
		return DIVC_ZOSFIO2.get();
	}

	public LocalDate getDIVC_ZOSCD2() {
		return DIVC_ZOSCD2.get();
	}

	public Long getDIVC_ZOSCN2() {
		return DIVC_ZOSCN2.get();
	}

	public String getDIVC_ZOSFIO() {
		return DIVC_ZOSFIO.get();
	}

	public LocalDate getDIVC_ZOSCD() {
		return DIVC_ZOSCD.get();
	}

	public Long getDIVC_ZOSCN() {
		return DIVC_ZOSCN.get();
	}

	public LocalDate getDIVC_CAD() {
		return DIVC_CAD.get();
	}

	public Long getDIVC_CAN() {
		return DIVC_CAN.get();
	}

	public String getDIVC_TCHNUM() {
		return DIVC_TCHNUM.get();
	}

	public LocalDate getDIVC_TCHD() {
		return DIVC_TCHD.get();
	}

	public String getDIVC_TYPE() {
		return DIVC_TYPE.get();
	}

	public String getDIVC_USR() {
		return DIVC_USR.get();
	}

	public LocalDate getDIVC_DT() {
		return DIVC_DT.get();
	}

	public LocalDateTime getTM$DIVC_DATE() {
		return TM$DIVC_DATE.get();
	}

	public String getDIVC_SHE_LNAFT() {
		return DIVC_SHE_LNAFT.get();
	}

	public String getDIVC_SHE_LNBEF() {
		return DIVC_SHE_LNBEF.get();
	}

	public String getDIVC_HE_LNAFT() {
		return DIVC_HE_LNAFT.get();
	}

	public String getDIVC_HE_LNBEF() {
		return DIVC_HE_LNBEF.get();
	}

	public Long getDIVC_SHE() {
		return DIVC_SHE.get();
	}

	public Long getDIVC_HE() {
		return DIVC_HE.get();
	}

	public Long getDIVC_ID() {
		return DIVC_ID.get();
	}

	public String getSHEFIO() {
		return SHEFIO.get();
	}

	public String getHEFIO() {
		return HEFIO.get();
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public StringProperty DIVC_ZPLACEProperty() {
		return DIVC_ZPLACE;
	}

	public StringProperty DIVC_ZMNAMEProperty() {
		return DIVC_ZMNAME;
	}

	public StringProperty DIVC_ZАNAMEProperty() {
		return DIVC_ZАNAME;
	}

	public StringProperty DIVC_ZLNAMEProperty() {
		return DIVC_ZLNAME;
	}

	public LongProperty DIVC_ZAGSProperty() {
		return DIVC_ZAGS;
	}

	public StringProperty DIVC_SERIAProperty() {
		return DIVC_SERIA;
	}

	public StringProperty DIVC_NUMProperty() {
		return DIVC_NUM;
	}

	public LongProperty DIVC_MC_MERCERProperty() {
		return DIVC_MC_MERCER;
	}

	public LongProperty DIVC_ZOSPRISONProperty() {
		return DIVC_ZOSPRISON;
	}

	public StringProperty DIVC_ZOSFIO2Property() {
		return DIVC_ZOSFIO2;
	}

	public SimpleObjectProperty<LocalDate> DIVC_ZOSCD2Property() {
		return DIVC_ZOSCD2;
	}

	public LongProperty DIVC_ZOSCN2Property() {
		return DIVC_ZOSCN2;
	}

	public StringProperty DIVC_ZOSFIOProperty() {
		return DIVC_ZOSFIO;
	}

	public SimpleObjectProperty<LocalDate> DIVC_ZOSCDProperty() {
		return DIVC_ZOSCD;
	}

	public LongProperty DIVC_ZOSCNProperty() {
		return DIVC_ZOSCN;
	}

	public SimpleObjectProperty<LocalDate> DIVC_CADProperty() {
		return DIVC_CAD;
	}

	public LongProperty DIVC_CANProperty() {
		return DIVC_CAN;
	}

	public StringProperty DIVC_TCHNUMProperty() {
		return DIVC_TCHNUM;
	}

	public SimpleObjectProperty<LocalDate> DIVC_TCHDProperty() {
		return DIVC_TCHD;
	}

	public StringProperty DIVC_TYPEProperty() {
		return DIVC_TYPE;
	}

	public StringProperty DIVC_USRProperty() {
		return DIVC_USR;
	}

	public SimpleObjectProperty<LocalDate> DIVC_DTProperty() {
		return DIVC_DT;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DIVC_DATEProperty() {
		return TM$DIVC_DATE;
	}

	public StringProperty DIVC_SHE_LNAFTProperty() {
		return DIVC_SHE_LNAFT;
	}

	public StringProperty DIVC_SHE_LNBEFProperty() {
		return DIVC_SHE_LNBEF;
	}

	public StringProperty DIVC_HE_LNAFTProperty() {
		return DIVC_HE_LNAFT;
	}

	public StringProperty DIVC_HE_LNBEFProperty() {
		return DIVC_HE_LNBEF;
	}

	public LongProperty DIVC_SHEProperty() {
		return DIVC_SHE;
	}

	public LongProperty DIVC_HEProperty() {
		return DIVC_HE;
	}

	public LongProperty DIVC_IDProperty() {
		return DIVC_ID;
	}

	public StringProperty SHEFIOProperty() {
		return SHEFIO;
	}

	public StringProperty HEFIOProperty() {
		return HEFIO;
	}
}
