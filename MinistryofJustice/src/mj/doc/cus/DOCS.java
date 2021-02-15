package mj.doc.cus;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DOCS {
	/** Нет данных */
	private IntegerProperty DOCCNT;
	/** Нет данных */
	private StringProperty DOCNAME;
	/** Нет данных */
	private StringProperty TABLE_NAME;

	public DOCS() {
		this.DOCCNT = new SimpleIntegerProperty();
		this.DOCNAME = new SimpleStringProperty();
		this.TABLE_NAME = new SimpleStringProperty();
	}

	public void setDOCCNT(Integer DOCCNT) {
		this.DOCCNT.set(DOCCNT);
	}

	public void setDOCNAME(String DOCNAME) {
		this.DOCNAME.set(DOCNAME);
	}

	public void setTABLE_NAME(String TABLE_NAME) {
		this.TABLE_NAME.set(TABLE_NAME);
	}

	public Integer getDOCCNT() {
		return DOCCNT.get();
	}

	public String getDOCNAME() {
		return DOCNAME.get();
	}

	public String getTABLE_NAME() {
		return TABLE_NAME.get();
	}

	public IntegerProperty DOCCNTProperty() {
		return DOCCNT;
	}

	public StringProperty DOCNAMEProperty() {
		return DOCNAME;
	}

	public StringProperty TABLE_NAMEProperty() {
		return TABLE_NAME;
	}
}
