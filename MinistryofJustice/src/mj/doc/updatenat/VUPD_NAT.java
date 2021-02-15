package mj.doc.updatenat;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VUPD_NAT {
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty SVID_NUMBER;
	/** Нет данных */
	private StringProperty SVID_SERIA;
	/** Нет данных */
	private IntegerProperty BRN_ACT_ID;
	/** Нет данных */
	private IntegerProperty ZAGS_ID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DOC_DATE;
	/** Нет данных */
	private StringProperty OPER;
	/** Нет данных */
	private IntegerProperty CUSID;
	/** Нет данных */
	private IntegerProperty ID;
	/** Нет данных */
	private StringProperty NEW_NAT;
	/** Нет данных */
	private StringProperty OLD_NAT;
	/** Нет данных */
	private StringProperty FIO;

	public VUPD_NAT() {
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.SVID_SERIA = new SimpleStringProperty();
		this.BRN_ACT_ID = new SimpleIntegerProperty();
		this.ZAGS_ID = new SimpleIntegerProperty();
		this.TM$DOC_DATE = new SimpleObjectProperty<>();
		this.OPER = new SimpleStringProperty();
		this.CUSID = new SimpleIntegerProperty();
		this.ID = new SimpleIntegerProperty();
		this.NEW_NAT = new SimpleStringProperty();
		this.OLD_NAT = new SimpleStringProperty();
		this.FIO = new SimpleStringProperty();
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setSVID_NUMBER(String SVID_NUMBER) {
		this.SVID_NUMBER.set(SVID_NUMBER);
	}

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setBRN_ACT_ID(Integer BRN_ACT_ID) {
		this.BRN_ACT_ID.set(BRN_ACT_ID);
	}

	public void setZAGS_ID(Integer ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setTM$DOC_DATE(LocalDateTime TM$DOC_DATE) {
		this.TM$DOC_DATE.set(TM$DOC_DATE);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setCUSID(Integer CUSID) {
		this.CUSID.set(CUSID);
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setNEW_NAT(String NEW_NAT) {
		this.NEW_NAT.set(NEW_NAT);
	}

	public void setOLD_NAT(String OLD_NAT) {
		this.OLD_NAT.set(OLD_NAT);
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

	public String getSVID_NUMBER() {
		return SVID_NUMBER.get();
	}

	public String getSVID_SERIA() {
		return SVID_SERIA.get();
	}

	public Integer getBRN_ACT_ID() {
		return BRN_ACT_ID.get();
	}

	public Integer getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public LocalDateTime getTM$DOC_DATE() {
		return TM$DOC_DATE.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public Integer getCUSID() {
		return CUSID.get();
	}

	public Integer getID() {
		return ID.get();
	}

	public String getNEW_NAT() {
		return NEW_NAT.get();
	}

	public String getOLD_NAT() {
		return OLD_NAT.get();
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

	public StringProperty SVID_NUMBERProperty() {
		return SVID_NUMBER;
	}

	public StringProperty SVID_SERIAProperty() {
		return SVID_SERIA;
	}

	public IntegerProperty BRN_ACT_IDProperty() {
		return BRN_ACT_ID;
	}

	public IntegerProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DOC_DATEProperty() {
		return TM$DOC_DATE;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public IntegerProperty CUSIDProperty() {
		return CUSID;
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public StringProperty NEW_NATProperty() {
		return NEW_NAT;
	}

	public StringProperty OLD_NATProperty() {
		return OLD_NAT;
	}

	public StringProperty FIOProperty() {
		return FIO;
	}
}
