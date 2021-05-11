package mj.doc.updatenat;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UPD_NAT {
	/** Серия */
	private StringProperty SVID_SERIA;
	/** Номер */
	private StringProperty SVID_NUMBER;
	/** ИД */
	private LongProperty ID;
	/** Ссылка на клиента */
	private LongProperty CUSID;
	/** Пользователь */
	private StringProperty OPER;
	/** Дата заведения */
	private SimpleObjectProperty<LocalDateTime> DOC_DATE;
	/** Ссылка на загс */
	private LongProperty ZAGS_ID;
	/** Ссылка на свидетельство о рождении */
	private LongProperty BRN_ACT_ID;
	/** Старая национальность */
	private LongProperty OLD_NAT;
	/** Новая национальность */
	private LongProperty NEW_NAT;
	/** ФИО */
	private StringProperty FIO;
	/** Номер документа */
	private StringProperty DOC_NUMBER;

	public UPD_NAT() {
		this.DOC_NUMBER = new SimpleStringProperty();
		this.SVID_SERIA = new SimpleStringProperty();
		this.SVID_NUMBER = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
		this.CUSID = new SimpleLongProperty();
		this.OPER = new SimpleStringProperty();
		this.DOC_DATE = new SimpleObjectProperty<>();
		this.ZAGS_ID = new SimpleLongProperty();
		this.BRN_ACT_ID = new SimpleLongProperty();
		this.OLD_NAT = new SimpleLongProperty();
		this.NEW_NAT = new SimpleLongProperty();
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

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setSVID_NUMBER(String SVID_NUMBER) {
		this.SVID_NUMBER.set(SVID_NUMBER);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setCUSID(Long CUSID) {
		this.CUSID.set(CUSID);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setDOC_DATE(LocalDateTime DOC_DATE) {
		this.DOC_DATE.set(DOC_DATE);
	}

	public void setZAGS_ID(Long ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setBRN_ACT_ID(Long BRN_ACT_ID) {
		this.BRN_ACT_ID.set(BRN_ACT_ID);
	}

	public void setOLD_NAT(Long OLD_NAT) {
		this.OLD_NAT.set(OLD_NAT);
	}

	public void setNEW_NAT(Long NEW_NAT) {
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

	public Long getID() {
		return ID.get();
	}

	public Long getCUSID() {
		return CUSID.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public LocalDateTime getDOC_DATE() {
		return DOC_DATE.get();
	}

	public Long getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public Long getBRN_ACT_ID() {
		return BRN_ACT_ID.get();
	}

	public Long getOLD_NAT() {
		return OLD_NAT.get();
	}

	public Long getNEW_NAT() {
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

	public LongProperty IDProperty() {
		return ID;
	}

	public LongProperty CUSIDProperty() {
		return CUSID;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public SimpleObjectProperty<LocalDateTime> DOC_DATEProperty() {
		return DOC_DATE;
	}

	public LongProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public LongProperty BRN_ACT_IDProperty() {
		return BRN_ACT_ID;
	}

	public LongProperty OLD_NATProperty() {
		return OLD_NAT;
	}

	public LongProperty NEW_NATProperty() {
		return NEW_NAT;
	}

	public StringProperty FIOProperty() {
		return FIO;
	}
}
