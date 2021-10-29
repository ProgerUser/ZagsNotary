package ru.psv.mj.prjmngm.inboxdocs;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PM_DOC_WORD {
	/** ID */
	private LongProperty DW_ID;
	/** Название файла */
	private StringProperty DW_FILENAME;
	/** Тип файла */
	private StringProperty DW_TYPE;
	/** Ссылка на документ */
	private LongProperty DW_DOCID;
	/** Дата добавления */
	private SimpleObjectProperty<LocalDateTime> DW_DATE;

	public PM_DOC_WORD() {
		this.DW_ID = new SimpleLongProperty();
		this.DW_FILENAME = new SimpleStringProperty();
		this.DW_TYPE = new SimpleStringProperty();
		this.DW_DOCID = new SimpleLongProperty();
		this.DW_DATE = new SimpleObjectProperty<>();
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

	public void setDW_DATE(LocalDateTime DW_DATE) {
		this.DW_DATE.set(DW_DATE);
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

	public LocalDateTime getDW_DATE() {
		return DW_DATE.get();
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

	public SimpleObjectProperty<LocalDateTime> DW_DATEProperty() {
		return DW_DATE;
	}
}
