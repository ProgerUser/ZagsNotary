package mj.doc.updatenat;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UPD_NAT {
	/** Серия */
	private StringProperty SVID_SERIA;
	/** Номер */
	private StringProperty SVID_NUMBER;
	/** ИД */
	private IntegerProperty ID;
	/** Ссылка на клиента */
	private IntegerProperty CUSID;
	/** Пользователь */
	private StringProperty OPER;
	/** Дата заведения */
	private SimpleObjectProperty<LocalDateTime> DOC_DATE;
	/** Ссылка на загс */
	private IntegerProperty ZAGS_ID;
	/** Ссылка на свидетельство о рождении */
	private IntegerProperty BRN_ACT_ID;
	/** Старая национальность */
	private IntegerProperty OLD_NAT;
	/** Новая национальность */
	private IntegerProperty NEW_NAT;
	/** ФИО */
	private StringProperty FIO;

	public UPD_NAT() {
		this.SVID_SERIA = new SimpleStringProperty();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.ID = new SimpleIntegerProperty();
		this.CUSID = new SimpleIntegerProperty();
		this.OPER = new SimpleStringProperty();
		this.DOC_DATE = new SimpleObjectProperty<>();
		this.ZAGS_ID = new SimpleIntegerProperty();
		this.BRN_ACT_ID = new SimpleIntegerProperty();
		this.OLD_NAT = new SimpleIntegerProperty();
		this.NEW_NAT = new SimpleIntegerProperty();
		this.FIO = new SimpleStringProperty();
	}

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setSVID_NUMBER(String SVID_NUMBER) {
		this.SVID_NUMBER.set(SVID_NUMBER);
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setCUSID(Integer CUSID) {
		this.CUSID.set(CUSID);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setDOC_DATE(LocalDateTime DOC_DATE) {
		this.DOC_DATE.set(DOC_DATE);
	}

	public void setZAGS_ID(Integer ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setBRN_ACT_ID(Integer BRN_ACT_ID) {
		this.BRN_ACT_ID.set(BRN_ACT_ID);
	}

	public void setOLD_NAT(Integer OLD_NAT) {
		this.OLD_NAT.set(OLD_NAT);
	}

	public void setNEW_NAT(Integer NEW_NAT) {
		this.NEW_NAT.set(NEW_NAT);
	}

	public void setFIO(String FIO) {
		this.FIO.set(FIO);
	}

	public String getSVID_SERIA() {
		return SVID_SERIA.get();
	}

	public String getSVID_NUMBER() {
		return SVID_NUMBER.get();
	}

	public Integer getID() {
		return ID.get();
	}

	public Integer getCUSID() {
		return CUSID.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public LocalDateTime getDOC_DATE() {
		return DOC_DATE.get();
	}

	public Integer getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public Integer getBRN_ACT_ID() {
		return BRN_ACT_ID.get();
	}

	public Integer getOLD_NAT() {
		return OLD_NAT.get();
	}

	public Integer getNEW_NAT() {
		return NEW_NAT.get();
	}

	public String getFIO() {
		return FIO.get();
	}

	public StringProperty SVID_SERIAProperty() {
		return SVID_SERIA;
	}

	public StringProperty SVID_NUMBERProperty() {
		return SVID_NUMBER;
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public IntegerProperty CUSIDProperty() {
		return CUSID;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public SimpleObjectProperty<LocalDateTime> DOC_DATEProperty() {
		return DOC_DATE;
	}

	public IntegerProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public IntegerProperty BRN_ACT_IDProperty() {
		return BRN_ACT_ID;
	}

	public IntegerProperty OLD_NATProperty() {
		return OLD_NAT;
	}

	public IntegerProperty NEW_NATProperty() {
		return NEW_NAT;
	}

	public StringProperty FIOProperty() {
		return FIO;
	}
}
