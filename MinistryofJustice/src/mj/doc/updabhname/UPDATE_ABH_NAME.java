package mj.doc.updabhname;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UPDATE_ABH_NAME {
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty SVID_SERIA;
	/** Нет данных */
	private StringProperty SVID_NUMBER;
	/** Нет данных */
	private IntegerProperty CUSID;
	/** Нет данных */
	private IntegerProperty ZAGS_ID;
	/** Нет данных */
	private StringProperty OPER;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DOC_DATE;
	/** Нет данных */
	private IntegerProperty BRN_ACT_ID;
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
	private IntegerProperty ID;
	/** Нет данных */
	private StringProperty FIO;
	/** Номер документа */
	private StringProperty DOC_NUMBER;

	public UPDATE_ABH_NAME() {
		this.DOC_NUMBER = new SimpleStringProperty();
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.SVID_SERIA = new SimpleStringProperty();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.CUSID = new SimpleIntegerProperty();
		this.ZAGS_ID = new SimpleIntegerProperty();
		this.OPER = new SimpleStringProperty();
		this.TM$DOC_DATE = new SimpleObjectProperty<>();
		this.BRN_ACT_ID = new SimpleIntegerProperty();
		this.NEW_MIDDLNAME = new SimpleStringProperty();
		this.NEW_FIRSTNAME = new SimpleStringProperty();
		this.NEW_LASTNAME = new SimpleStringProperty();
		this.OLD_MIDDLNAME = new SimpleStringProperty();
		this.OLD_FIRSTNAME = new SimpleStringProperty();
		this.OLD_LASTNAME = new SimpleStringProperty();
		this.ID = new SimpleIntegerProperty();
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

	public void setCUSID(Integer CUSID) {
		this.CUSID.set(CUSID);
	}

	public void setZAGS_ID(Integer ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setTM$DOC_DATE(LocalDateTime TM$DOC_DATE) {
		this.TM$DOC_DATE.set(TM$DOC_DATE);
	}

	public void setBRN_ACT_ID(Integer BRN_ACT_ID) {
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

	public void setID(Integer ID) {
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

	public Integer getCUSID() {
		return CUSID.get();
	}

	public Integer getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public LocalDateTime getTM$DOC_DATE() {
		return TM$DOC_DATE.get();
	}

	public Integer getBRN_ACT_ID() {
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

	public Integer getID() {
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

	public IntegerProperty CUSIDProperty() {
		return CUSID;
	}

	public IntegerProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DOC_DATEProperty() {
		return TM$DOC_DATE;
	}

	public IntegerProperty BRN_ACT_IDProperty() {
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

	public IntegerProperty IDProperty() {
		return ID;
	}

	public StringProperty FIOProperty() {
		return FIO;
	}
}
