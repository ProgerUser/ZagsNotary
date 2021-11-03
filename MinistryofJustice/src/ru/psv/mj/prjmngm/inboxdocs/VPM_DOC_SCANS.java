package ru.psv.mj.prjmngm.inboxdocs;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPM_DOC_SCANS {
	/** Нет данных */
	private LongProperty DS_ID;
	/** Нет данных */
	private StringProperty DS_FILENAME;
	/** Нет данных */
	private StringProperty DS_TYPE;
	/** Нет данных */
	private LongProperty DS_DOCID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DS_DATE;
	/** Нет данных */
	private StringProperty DocScanKb;

	public VPM_DOC_SCANS() {
		this.DS_ID = new SimpleLongProperty();
		this.DS_FILENAME = new SimpleStringProperty();
		this.DS_TYPE = new SimpleStringProperty();
		this.DS_DOCID = new SimpleLongProperty();
		this.TM$DS_DATE = new SimpleObjectProperty<>();
		this.DocScanKb = new SimpleStringProperty();
	}

	public void setDS_ID(Long DS_ID) {
		this.DS_ID.set(DS_ID);
	}

	public void setDS_FILENAME(String DS_FILENAME) {
		this.DS_FILENAME.set(DS_FILENAME);
	}

	public void setDS_TYPE(String DS_TYPE) {
		this.DS_TYPE.set(DS_TYPE);
	}

	public void setDS_DOCID(Long DS_DOCID) {
		this.DS_DOCID.set(DS_DOCID);
	}

	public void setTM$DS_DATE(LocalDateTime TM$DS_DATE) {
		this.TM$DS_DATE.set(TM$DS_DATE);
	}

	public void setDocScanKb(String DocScanKb) {
		this.DocScanKb.set(DocScanKb);
	}

	public Long getDS_ID() {
		return DS_ID.get();
	}

	public String getDS_FILENAME() {
		return DS_FILENAME.get();
	}

	public String getDS_TYPE() {
		return DS_TYPE.get();
	}

	public Long getDS_DOCID() {
		return DS_DOCID.get();
	}

	public LocalDateTime getTM$DS_DATE() {
		return TM$DS_DATE.get();
	}

	public String getDocScanKb() {
		return DocScanKb.get();
	}

	public LongProperty DS_IDProperty() {
		return DS_ID;
	}

	public StringProperty DS_FILENAMEProperty() {
		return DS_FILENAME;
	}

	public StringProperty DS_TYPEProperty() {
		return DS_TYPE;
	}

	public LongProperty DS_DOCIDProperty() {
		return DS_DOCID;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DS_DATEProperty() {
		return TM$DS_DATE;
	}

	public StringProperty DocScanKbProperty() {
		return DocScanKb;
	}
}
