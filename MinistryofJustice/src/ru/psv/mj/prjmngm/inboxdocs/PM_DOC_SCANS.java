package ru.psv.mj.prjmngm.inboxdocs;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PM_DOC_SCANS {
	/** Дата добавления */
	private SimpleObjectProperty<LocalDateTime> DS_DATE;
	/** ID */
	private LongProperty DS_ID;
	/** Название файла */
	private StringProperty DS_FILENAME;
	/** Тип файла */
	private StringProperty DS_TYPE;
	/** Ссылка на документ */
	private LongProperty DS_DOCID;

	public PM_DOC_SCANS() {
		this.DS_DATE = new SimpleObjectProperty<>();
		this.DS_ID = new SimpleLongProperty();
		this.DS_FILENAME = new SimpleStringProperty();
		this.DS_TYPE = new SimpleStringProperty();
		this.DS_DOCID = new SimpleLongProperty();
	}

	public void setDS_DATE(LocalDateTime DS_DATE) {
		this.DS_DATE.set(DS_DATE);
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

	public LocalDateTime getDS_DATE() {
		return DS_DATE.get();
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

	public SimpleObjectProperty<LocalDateTime> DS_DATEProperty() {
		return DS_DATE;
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
}
