package ru.psv.mj.zags.doc.mercer;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MC_MERCER {
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty MERCER_DSPMT_SHE;
	/** Нет данных */
	private StringProperty MERCER_OTHER;
	/** Нет данных */
	private LongProperty MERCER_DIEHE;
	/** Нет данных */
	private LongProperty MERCER_DIESHE;
	/** Нет данных */
	private StringProperty MERCER_SERIA;
	/** Нет данных */
	private StringProperty MERCER_NUM;
	/** Нет данных */
	private StringProperty MERCER_DSPMT_HE;
	/** Нет данных */
	private LongProperty MERCER_DIVHE;
	/** Нет данных */
	private LongProperty MERCER_DIVSHE;
	/** Нет данных */
	private LongProperty MERCER_ZAGS;
	/** Нет данных */
	private StringProperty MERCER_USR;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$MERCER_DATE;
	/** Нет данных */
	private LongProperty MERCER_SHEAGE;
	/** Нет данных */
	private LongProperty MERCER_HEAGE;
	/** Нет данных */
	private StringProperty MERCER_SHE_LNBAFT;
	/** Нет данных */
	private StringProperty MERCER_SHE_LNBEF;
	/** Нет данных */
	private StringProperty MERCER_HE_LNAFT;
	/** Нет данных */
	private StringProperty MERCER_HE_LNBEF;
	/** Нет данных */
	private LongProperty MERCER_SHE;
	/** Нет данных */
	private LongProperty MERCER_HE;
	/** Нет данных */
	private LongProperty MERCER_ID;
	/** Нет данных */
	private StringProperty SHEFIO;
	/** Нет данных */
	private StringProperty HEFIO;
	/** Дата заключения брака */
	private SimpleObjectProperty<LocalDate> MC_DATE;
	/** Номер документа */
	private StringProperty DOC_NUMBER;

	public MC_MERCER() {
		this.DOC_NUMBER = new SimpleStringProperty();
		this.MC_DATE = new SimpleObjectProperty<>();
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.MERCER_DSPMT_SHE = new SimpleStringProperty();
		this.MERCER_OTHER = new SimpleStringProperty();
		this.MERCER_DIEHE = new SimpleLongProperty();
		this.MERCER_DIESHE = new SimpleLongProperty();
		this.MERCER_SERIA = new SimpleStringProperty();
		this.MERCER_NUM = new SimpleStringProperty();
		this.MERCER_DSPMT_HE = new SimpleStringProperty();
		this.MERCER_DIVHE = new SimpleLongProperty();
		this.MERCER_DIVSHE = new SimpleLongProperty();
		this.MERCER_ZAGS = new SimpleLongProperty();
		this.MERCER_USR = new SimpleStringProperty();
		this.TM$MERCER_DATE = new SimpleObjectProperty<>();
		this.MERCER_SHEAGE = new SimpleLongProperty();
		this.MERCER_HEAGE = new SimpleLongProperty();
		this.MERCER_SHE_LNBAFT = new SimpleStringProperty();
		this.MERCER_SHE_LNBEF = new SimpleStringProperty();
		this.MERCER_HE_LNAFT = new SimpleStringProperty();
		this.MERCER_HE_LNBEF = new SimpleStringProperty();
		this.MERCER_SHE = new SimpleLongProperty();
		this.MERCER_HE = new SimpleLongProperty();
		this.MERCER_ID = new SimpleLongProperty();
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

	public void setMC_DATE(LocalDate MC_DATE) {
		this.MC_DATE.set(MC_DATE);
	}

	public LocalDate getMC_DATE() {
		return MC_DATE.get();
	}

	public SimpleObjectProperty<LocalDate> MC_DATEProperty() {
		return MC_DATE;
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setMERCER_DSPMT_SHE(String MERCER_DSPMT_SHE) {
		this.MERCER_DSPMT_SHE.set(MERCER_DSPMT_SHE);
	}

	public void setMERCER_OTHER(String MERCER_OTHER) {
		this.MERCER_OTHER.set(MERCER_OTHER);
	}

	public void setMERCER_DIEHE(Long MERCER_DIEHE) {
		this.MERCER_DIEHE.set(MERCER_DIEHE);
	}

	public void setMERCER_DIESHE(Long MERCER_DIESHE) {
		this.MERCER_DIESHE.set(MERCER_DIESHE);
	}

	public void setMERCER_SERIA(String MERCER_SERIA) {
		this.MERCER_SERIA.set(MERCER_SERIA);
	}

	public void setMERCER_NUM(String MERCER_NUM) {
		this.MERCER_NUM.set(MERCER_NUM);
	}

	public void setMERCER_DSPMT_HE(String MERCER_DSPMT_HE) {
		this.MERCER_DSPMT_HE.set(MERCER_DSPMT_HE);
	}

	public void setMERCER_DIVHE(Long MERCER_DIVHE) {
		this.MERCER_DIVHE.set(MERCER_DIVHE);
	}

	public void setMERCER_DIVSHE(Long MERCER_DIVSHE) {
		this.MERCER_DIVSHE.set(MERCER_DIVSHE);
	}

	public void setMERCER_ZAGS(Long MERCER_ZAGS) {
		this.MERCER_ZAGS.set(MERCER_ZAGS);
	}

	public void setMERCER_USR(String MERCER_USR) {
		this.MERCER_USR.set(MERCER_USR);
	}

	public void setTM$MERCER_DATE(LocalDateTime TM$MERCER_DATE) {
		this.TM$MERCER_DATE.set(TM$MERCER_DATE);
	}

	public void setMERCER_SHEAGE(Long MERCER_SHEAGE) {
		this.MERCER_SHEAGE.set(MERCER_SHEAGE);
	}

	public void setMERCER_HEAGE(Long MERCER_HEAGE) {
		this.MERCER_HEAGE.set(MERCER_HEAGE);
	}

	public void setMERCER_SHE_LNBAFT(String MERCER_SHE_LNBAFT) {
		this.MERCER_SHE_LNBAFT.set(MERCER_SHE_LNBAFT);
	}

	public void setMERCER_SHE_LNBEF(String MERCER_SHE_LNBEF) {
		this.MERCER_SHE_LNBEF.set(MERCER_SHE_LNBEF);
	}

	public void setMERCER_HE_LNAFT(String MERCER_HE_LNAFT) {
		this.MERCER_HE_LNAFT.set(MERCER_HE_LNAFT);
	}

	public void setMERCER_HE_LNBEF(String MERCER_HE_LNBEF) {
		this.MERCER_HE_LNBEF.set(MERCER_HE_LNBEF);
	}

	public void setMERCER_SHE(Long MERCER_SHE) {
		this.MERCER_SHE.set(MERCER_SHE);
	}

	public void setMERCER_HE(Long MERCER_HE) {
		this.MERCER_HE.set(MERCER_HE);
	}

	public void setMERCER_ID(Long MERCER_ID) {
		this.MERCER_ID.set(MERCER_ID);
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

	public String getMERCER_DSPMT_SHE() {
		return MERCER_DSPMT_SHE.get();
	}

	public String getMERCER_OTHER() {
		return MERCER_OTHER.get();
	}

	public Long getMERCER_DIEHE() {
		return MERCER_DIEHE.get();
	}

	public Long getMERCER_DIESHE() {
		return MERCER_DIESHE.get();
	}

	public String getMERCER_SERIA() {
		return MERCER_SERIA.get();
	}

	public String getMERCER_NUM() {
		return MERCER_NUM.get();
	}

	public String getMERCER_DSPMT_HE() {
		return MERCER_DSPMT_HE.get();
	}

	public Long getMERCER_DIVHE() {
		return MERCER_DIVHE.get();
	}

	public Long getMERCER_DIVSHE() {
		return MERCER_DIVSHE.get();
	}

	public Long getMERCER_ZAGS() {
		return MERCER_ZAGS.get();
	}

	public String getMERCER_USR() {
		return MERCER_USR.get();
	}

	public LocalDateTime getTM$MERCER_DATE() {
		return TM$MERCER_DATE.get();
	}

	public Long getMERCER_SHEAGE() {
		return MERCER_SHEAGE.get();
	}

	public Long getMERCER_HEAGE() {
		return MERCER_HEAGE.get();
	}

	public String getMERCER_SHE_LNBAFT() {
		return MERCER_SHE_LNBAFT.get();
	}

	public String getMERCER_SHE_LNBEF() {
		return MERCER_SHE_LNBEF.get();
	}

	public String getMERCER_HE_LNAFT() {
		return MERCER_HE_LNAFT.get();
	}

	public String getMERCER_HE_LNBEF() {
		return MERCER_HE_LNBEF.get();
	}

	public Long getMERCER_SHE() {
		return MERCER_SHE.get();
	}

	public Long getMERCER_HE() {
		return MERCER_HE.get();
	}

	public Long getMERCER_ID() {
		return MERCER_ID.get();
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

	public StringProperty MERCER_DSPMT_SHEProperty() {
		return MERCER_DSPMT_SHE;
	}

	public StringProperty MERCER_OTHERProperty() {
		return MERCER_OTHER;
	}

	public LongProperty MERCER_DIEHEProperty() {
		return MERCER_DIEHE;
	}

	public LongProperty MERCER_DIESHEProperty() {
		return MERCER_DIESHE;
	}

	public StringProperty MERCER_SERIAProperty() {
		return MERCER_SERIA;
	}

	public StringProperty MERCER_NUMProperty() {
		return MERCER_NUM;
	}

	public StringProperty MERCER_DSPMT_HEProperty() {
		return MERCER_DSPMT_HE;
	}

	public LongProperty MERCER_DIVHEProperty() {
		return MERCER_DIVHE;
	}

	public LongProperty MERCER_DIVSHEProperty() {
		return MERCER_DIVSHE;
	}

	public LongProperty MERCER_ZAGSProperty() {
		return MERCER_ZAGS;
	}

	public StringProperty MERCER_USRProperty() {
		return MERCER_USR;
	}

	public SimpleObjectProperty<LocalDateTime> TM$MERCER_DATEProperty() {
		return TM$MERCER_DATE;
	}

	public LongProperty MERCER_SHEAGEProperty() {
		return MERCER_SHEAGE;
	}

	public LongProperty MERCER_HEAGEProperty() {
		return MERCER_HEAGE;
	}

	public StringProperty MERCER_SHE_LNBAFTProperty() {
		return MERCER_SHE_LNBAFT;
	}

	public StringProperty MERCER_SHE_LNBEFProperty() {
		return MERCER_SHE_LNBEF;
	}

	public StringProperty MERCER_HE_LNAFTProperty() {
		return MERCER_HE_LNAFT;
	}

	public StringProperty MERCER_HE_LNBEFProperty() {
		return MERCER_HE_LNBEF;
	}

	public LongProperty MERCER_SHEProperty() {
		return MERCER_SHE;
	}

	public LongProperty MERCER_HEProperty() {
		return MERCER_HE;
	}

	public LongProperty MERCER_IDProperty() {
		return MERCER_ID;
	}

	public StringProperty SHEFIOProperty() {
		return SHEFIO;
	}

	public StringProperty HEFIOProperty() {
		return HEFIO;
	}
}
