package ru.psv.mj.notary.doc.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_NT_TEMP_LIST {
	/** Нет данных */
	private StringProperty NAMES;
	/** Нет данных */
	private LongProperty ID;
	/** Нет данных */
	private StringProperty NAME;
	/** Нет данных */
	private LongProperty PARENT;
	/** Нет данных */
	private StringProperty HTML_TEMP;
	/** Нет данных */
	private StringProperty REP_QUERY;

	public V_NT_TEMP_LIST() {
		this.NAMES = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
		this.NAME = new SimpleStringProperty();
		this.PARENT = new SimpleLongProperty();
		this.HTML_TEMP = new SimpleStringProperty();
		this.REP_QUERY = new SimpleStringProperty();
	}

	public void setNAMES(String NAMES) {
		this.NAMES.set(NAMES);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setPARENT(Long PARENT) {
		this.PARENT.set(PARENT);
	}

	public void setHTML_TEMP(String HTML_TEMP) {
		this.HTML_TEMP.set(HTML_TEMP);
	}

	public void setREP_QUERY(String REP_QUERY) {
		this.REP_QUERY.set(REP_QUERY);
	}

	public String getNAMES() {
		return NAMES.get();
	}

	public Long getID() {
		return ID.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public Long getPARENT() {
		return PARENT.get();
	}

	public String getHTML_TEMP() {
		return HTML_TEMP.get();
	}

	public String getREP_QUERY() {
		return REP_QUERY.get();
	}

	public StringProperty NAMESProperty() {
		return NAMES;
	}

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public LongProperty PARENTProperty() {
		return PARENT;
	}

	public StringProperty HTML_TEMPProperty() {
		return HTML_TEMP;
	}

	public StringProperty REP_QUERYProperty() {
		return REP_QUERY;
	}
}
