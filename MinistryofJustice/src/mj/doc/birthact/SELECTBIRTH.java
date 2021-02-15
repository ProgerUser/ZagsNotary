package mj.doc.birthact;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SELECTBIRTH {
	/** Нет данных */
	private StringProperty BR_ACT_USER;
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private IntegerProperty BR_ACT_M;
	/** Нет данных */
	private IntegerProperty BR_ACT_F;
	/** Нет данных */
	private IntegerProperty BR_ACT_CH;
	/** Нет данных */
	private StringProperty MFIO;
	/** Нет данных */
	private StringProperty FFIO;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$_DOC_DATE;
	/** Нет данных */
	private StringProperty ZAGS_NAME;
	/** Нет данных */
	private StringProperty CHILDREN_FIO;
	/** Нет данных */
	private StringProperty LIVE_DEAD;
	/** Нет данных */
	private IntegerProperty BRN_AC_ID;

	public SELECTBIRTH() {
		this.BR_ACT_USER = new SimpleStringProperty();
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.BR_ACT_M = new SimpleIntegerProperty();
		this.BR_ACT_F = new SimpleIntegerProperty();
		this.BR_ACT_CH = new SimpleIntegerProperty();
		this.MFIO = new SimpleStringProperty();
		this.FFIO = new SimpleStringProperty();
		this.TM$_DOC_DATE = new SimpleObjectProperty<>();
		this.ZAGS_NAME = new SimpleStringProperty();
		this.CHILDREN_FIO = new SimpleStringProperty();
		this.LIVE_DEAD = new SimpleStringProperty();
		this.BRN_AC_ID = new SimpleIntegerProperty();
	}

	public void setBR_ACT_USER(String BR_ACT_USER) {
		this.BR_ACT_USER.set(BR_ACT_USER);
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
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

	public void setMFIO(String MFIO) {
		this.MFIO.set(MFIO);
	}

	public void setFFIO(String FFIO) {
		this.FFIO.set(FFIO);
	}

	public void setTM$_DOC_DATE(LocalDateTime TM$_DOC_DATE) {
		this.TM$_DOC_DATE.set(TM$_DOC_DATE);
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public void setCHILDREN_FIO(String CHILDREN_FIO) {
		this.CHILDREN_FIO.set(CHILDREN_FIO);
	}

	public void setLIVE_DEAD(String LIVE_DEAD) {
		this.LIVE_DEAD.set(LIVE_DEAD);
	}

	public void setBRN_AC_ID(Integer BRN_AC_ID) {
		this.BRN_AC_ID.set(BRN_AC_ID);
	}

	public String getBR_ACT_USER() {
		return BR_ACT_USER.get();
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
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

	public String getMFIO() {
		return MFIO.get();
	}

	public String getFFIO() {
		return FFIO.get();
	}

	public LocalDateTime getTM$_DOC_DATE() {
		return TM$_DOC_DATE.get();
	}

	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public String getCHILDREN_FIO() {
		return CHILDREN_FIO.get();
	}

	public String getLIVE_DEAD() {
		return LIVE_DEAD.get();
	}

	public Integer getBRN_AC_ID() {
		return BRN_AC_ID.get();
	}

	public StringProperty BR_ACT_USERProperty() {
		return BR_ACT_USER;
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
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

	public StringProperty MFIOProperty() {
		return MFIO;
	}

	public StringProperty FFIOProperty() {
		return FFIO;
	}

	public SimpleObjectProperty<LocalDateTime> TM$_DOC_DATEProperty() {
		return TM$_DOC_DATE;
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}

	public StringProperty CHILDREN_FIOProperty() {
		return CHILDREN_FIO;
	}

	public StringProperty LIVE_DEADProperty() {
		return LIVE_DEAD;
	}

	public IntegerProperty BRN_AC_IDProperty() {
		return BRN_AC_ID;
	}
}
