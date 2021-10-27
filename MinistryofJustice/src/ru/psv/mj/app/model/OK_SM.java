package ru.psv.mj.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OK_SM {
	private StringProperty CDIGITAL;
	private StringProperty CALFA_2;
	private StringProperty CSHORTNAME;
	private StringProperty CLONGNAME;

	/* CONSTRUCTOR */
	public OK_SM() {
		this.CDIGITAL = new SimpleStringProperty();
		this.CALFA_2 = new SimpleStringProperty();
		this.CSHORTNAME = new SimpleStringProperty();
		this.CLONGNAME = new SimpleStringProperty();
	}

	/* CLONGNAME */
	public String getCLONGNAME() {
		return CLONGNAME.get();
	}

	public void setCLONGNAME(String CLONGNAME) {
		this.CLONGNAME.set(CLONGNAME);
	}

	public StringProperty CLONGNAMEProperty() {
		return CLONGNAME;
	}

	/* CDIGITAL */
	public String getCDIGITAL() {
		return CDIGITAL.get();
	}

	public void setCDIGITAL(String CDIGITAL) {
		this.CDIGITAL.set(CDIGITAL);
	}

	public StringProperty CDIGITALProperty() {
		return CDIGITAL;
	}

	/* CALFA_2 */
	public String getCALFA_2() {
		return CALFA_2.get();
	}

	public void setCALFA_2(String CALFA_2) {
		this.CALFA_2.set(CALFA_2);
	}

	public StringProperty CALFA_2Property() {
		return CALFA_2;
	}

	/* CSHORTNAME */
	public String getCSHORTNAME() {
		return CSHORTNAME.get();
	}

	public void setCSHORTNAME(String CSHORTNAME) {
		this.CSHORTNAME.set(CSHORTNAME);
	}

	public StringProperty CSHORTNAMEProperty() {
		return CSHORTNAME;
	}
}
