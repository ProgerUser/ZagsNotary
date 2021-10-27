package ru.psv.mj.notary.doc.html.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableModel {

	private StringProperty RowCnt;

	private StringProperty ColumnCnt;

	public TableModel() {
		this.RowCnt = new SimpleStringProperty();
		this.ColumnCnt = new SimpleStringProperty();
	}

	public void setRowCnt(String RowCnt) {
		this.RowCnt.set(RowCnt);
	}

	public void setColumnCnt(String NAME) {
		this.ColumnCnt.set(NAME);
	}

	public String getRowCnt() {
		return RowCnt.get();
	}

	public String getColumnCnt() {
		return ColumnCnt.get();
	}

	public StringProperty RowCntProperty() {
		return RowCnt;
	}

	public StringProperty ColumnCntProperty() {
		return ColumnCnt;
	}
}
