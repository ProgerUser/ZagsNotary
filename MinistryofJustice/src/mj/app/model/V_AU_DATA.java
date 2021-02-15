package mj.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_AU_DATA {
	private IntegerProperty IACTION_ID;
	private StringProperty CFIELD;
	private StringProperty CNEWDATA;
	private StringProperty COLDDATA;

	/* CONSTRUCTOR */
	public V_AU_DATA() {
		this.IACTION_ID = new SimpleIntegerProperty();
		this.CFIELD = new SimpleStringProperty();
		this.CNEWDATA = new SimpleStringProperty();
		this.COLDDATA = new SimpleStringProperty();
	}

	/* COLDDATA */
	public String getCOLDDATA() {
		return COLDDATA.get();
	}

	public void setCOLDDATA(String COLDDATA) {
		this.COLDDATA.set(COLDDATA);
	}

	public StringProperty COLDDATAProperty() {
		return COLDDATA;
	}

	/* CNEWDATA */
	public String getCNEWDATA() {
		return CNEWDATA.get();
	}

	public void setCNEWDATA(String CNEWDATA) {
		this.CNEWDATA.set(CNEWDATA);
	}

	public StringProperty CNEWDATAProperty() {
		return CNEWDATA;
	}

	/* IACTION_ID */
	public Integer getIACTION_ID() {
		return IACTION_ID.get();
	}

	public void setIACTION_ID(Integer IACTION_ID) {
		this.IACTION_ID.set(IACTION_ID);
	}

	public IntegerProperty IACTION_IDProperty() {
		return IACTION_ID;
	}

	/* CFIELD */
	public String getCFIELD() {
		return CFIELD.get();
	}

	public void setCFIELD(String CFIELD) {
		this.CFIELD.set(CFIELD);
	}

	public StringProperty CFIELDProperty() {
		return CFIELD;
	}

}
