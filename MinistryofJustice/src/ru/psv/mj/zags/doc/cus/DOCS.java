package ru.psv.mj.zags.doc.cus;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DOCS {
	/** Нет данных */
	private LongProperty DOCCNT;
	/** Нет данных */
	private StringProperty DOCNAME;
	/** Нет данных */
	private StringProperty TABLE_NAME;

	public DOCS() {
		this.DOCCNT = new SimpleLongProperty();
		this.DOCNAME = new SimpleStringProperty();
		this.TABLE_NAME = new SimpleStringProperty();
	}

	public void setDOCCNT(Long DOCCNT) {
		this.DOCCNT.set(DOCCNT);
	}

	public void setDOCNAME(String DOCNAME) {
		this.DOCNAME.set(DOCNAME);
	}

	public void setTABLE_NAME(String TABLE_NAME) {
		this.TABLE_NAME.set(TABLE_NAME);
	}

	public Long getDOCCNT() {
		return DOCCNT.get();
	}

	public String getDOCNAME() {
		return DOCNAME.get();
	}

	public String getTABLE_NAME() {
		return TABLE_NAME.get();
	}

	public LongProperty DOCCNTProperty() {
		return DOCCNT;
	}

	public StringProperty DOCNAMEProperty() {
		return DOCNAME;
	}

	public StringProperty TABLE_NAMEProperty() {
		return TABLE_NAME;
	}
}
