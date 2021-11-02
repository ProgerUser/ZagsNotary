package ru.psv.mj.prjmngm.doc.type;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PM_DOC_TYPES {
	/** ИД типа документа */
	private LongProperty DOC_TP_ID;
	/** Название */
	private StringProperty DOC_TP_NAME;
	/** Запрос */
	private StringProperty DOC_TP_SQL;

	public PM_DOC_TYPES() {
		this.DOC_TP_ID = new SimpleLongProperty();
		this.DOC_TP_NAME = new SimpleStringProperty();
		this.DOC_TP_SQL = new SimpleStringProperty();
	}

	public void setDOC_TP_ID(Long DOC_TP_ID) {
		this.DOC_TP_ID.set(DOC_TP_ID);
	}

	public void setDOC_TP_NAME(String DOC_TP_NAME) {
		this.DOC_TP_NAME.set(DOC_TP_NAME);
	}

	public void setDOC_TP_SQL(String DOC_TP_SQL) {
		this.DOC_TP_SQL.set(DOC_TP_SQL);
	}

	public Long getDOC_TP_ID() {
		return DOC_TP_ID.get();
	}

	public String getDOC_TP_NAME() {
		return DOC_TP_NAME.get();
	}

	public String getDOC_TP_SQL() {
		return DOC_TP_SQL.get();
	}

	public LongProperty DOC_TP_IDProperty() {
		return DOC_TP_ID;
	}

	public StringProperty DOC_TP_NAMEProperty() {
		return DOC_TP_NAME;
	}

	public StringProperty DOC_TP_SQLProperty() {
		return DOC_TP_SQL;
	}
}
