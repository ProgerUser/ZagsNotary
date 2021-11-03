package ru.psv.mj.prjmngm.inboxdocs;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPM_DOC_WORD {
	/** Нет данных */
	private LongProperty DW_ID;
	/** Нет данных */
	private StringProperty DW_FILENAME;
	/** Нет данных */
	private StringProperty DW_TYPE;
	/** Нет данных */
	private LongProperty DW_DOCID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DW_DATE;
	/** Нет данных */
	private StringProperty DocWordKb;

	public VPM_DOC_WORD() {
		this.DW_ID = new SimpleLongProperty();
		this.DW_FILENAME = new SimpleStringProperty();
		this.DW_TYPE = new SimpleStringProperty();
		this.DW_DOCID = new SimpleLongProperty();
		this.TM$DW_DATE = new SimpleObjectProperty<>();
		this.DocWordKb = new SimpleStringProperty();
	}

	public void setDW_ID(Long DW_ID) {
		this.DW_ID.set(DW_ID);
	}

	public void setDW_FILENAME(String DW_FILENAME) {
		this.DW_FILENAME.set(DW_FILENAME);
	}

	public void setDW_TYPE(String DW_TYPE) {
		this.DW_TYPE.set(DW_TYPE);
	}

	public void setDW_DOCID(Long DW_DOCID) {
		this.DW_DOCID.set(DW_DOCID);
	}

	public void setTM$DW_DATE(LocalDateTime TM$DW_DATE) {
		this.TM$DW_DATE.set(TM$DW_DATE);
	}

	public void setDocWordKb(String DocWordKb) {
		this.DocWordKb.set(DocWordKb);
	}

	public Long getDW_ID() {
		return DW_ID.get();
	}

	public String getDW_FILENAME() {
		return DW_FILENAME.get();
	}

	public String getDW_TYPE() {
		return DW_TYPE.get();
	}

	public Long getDW_DOCID() {
		return DW_DOCID.get();
	}

	public LocalDateTime getTM$DW_DATE() {
		return TM$DW_DATE.get();
	}

	public String getDocWordKb() {
		return DocWordKb.get();
	}

	public LongProperty DW_IDProperty() {
		return DW_ID;
	}

	public StringProperty DW_FILENAMEProperty() {
		return DW_FILENAME;
	}

	public StringProperty DW_TYPEProperty() {
		return DW_TYPE;
	}

	public LongProperty DW_DOCIDProperty() {
		return DW_DOCID;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DW_DATEProperty() {
		return TM$DW_DATE;
	}

	public StringProperty DocWordKbProperty() {
		return DocWordKb;
	}
}
