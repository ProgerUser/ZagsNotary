package ru.psv.mj.notary.client.model;

import java.time.LocalDateTime;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ALL_DOCS {
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DOC_DATE;
	/** Нет данных */
	private LongProperty DOC_ID;
	/** Нет данных */
	private StringProperty DOCNAME;
	/** Нет данных */
	private StringProperty TABLE_NAME;
	private StringProperty TYPE_DOC;

	public ALL_DOCS() {
		this.TYPE_DOC = new SimpleStringProperty();
		this.TM$DOC_DATE = new SimpleObjectProperty<>();
		this.DOC_ID = new SimpleLongProperty();
		this.DOCNAME = new SimpleStringProperty();
		this.TABLE_NAME = new SimpleStringProperty();
	}

	public StringProperty TYPE_DOCProperty() {
		return TYPE_DOC;
	}

	public String getTYPE_DOC() {
		return TYPE_DOC.get();
	}

	public void setTYPE_DOC(String TYPE_DOC) {
		this.TYPE_DOC.set(TYPE_DOC);
	}

	public void setTM$DOC_DATE(LocalDateTime TM$DOC_DATE) {
		this.TM$DOC_DATE.set(TM$DOC_DATE);
	}

	public void setDOC_ID(Long DOC_ID) {
		this.DOC_ID.set(DOC_ID);
	}

	public void setDOCNAME(String DOCNAME) {
		this.DOCNAME.set(DOCNAME);
	}

	public void setTABLE_NAME(String TABLE_NAME) {
		this.TABLE_NAME.set(TABLE_NAME);
	}

	public LocalDateTime getTM$DOC_DATE() {
		return TM$DOC_DATE.get();
	}

	public Long getDOC_ID() {
		return DOC_ID.get();
	}

	public String getDOCNAME() {
		return DOCNAME.get();
	}

	public String getTABLE_NAME() {
		return TABLE_NAME.get();
	}

	public SimpleObjectProperty<LocalDateTime> TM$DOC_DATEProperty() {
		return TM$DOC_DATE;
	}

	public LongProperty DOC_IDProperty() {
		return DOC_ID;
	}

	public StringProperty DOCNAMEProperty() {
		return DOCNAME;
	}

	public StringProperty TABLE_NAMEProperty() {
		return TABLE_NAME;
	}
}
