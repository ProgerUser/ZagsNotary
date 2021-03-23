package notary.client;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NAS_PUNKT {
	/** Код */
	private IntegerProperty CODE;
	/** Наименование */
	private StringProperty NAME;
	/** Район */
	private StringProperty AREA;

	public NAS_PUNKT() {
		this.CODE = new SimpleIntegerProperty();
		this.NAME = new SimpleStringProperty();
		this.AREA = new SimpleStringProperty();
	}

	public void setCODE(Integer CODE) {
		this.CODE.set(CODE);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setAREA(String AREA) {
		this.AREA.set(AREA);
	}

	public Integer getCODE() {
		return CODE.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public String getAREA() {
		return AREA.get();
	}

	public IntegerProperty CODEProperty() {
		return CODE;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public StringProperty AREAProperty() {
		return AREA;
	}
}
