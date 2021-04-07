package notary.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class COUNTRIES {
	/** Название */
	private StringProperty NAME;
	/** Код */
	private IntegerProperty CODE;

	public COUNTRIES() {
		this.NAME = new SimpleStringProperty();
		this.CODE = new SimpleIntegerProperty();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setCODE(Integer CODE) {
		this.CODE.set(CODE);
	}

	public String getNAME() {
		return NAME.get();
	}

	public Integer getCODE() {
		return CODE.get();
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public IntegerProperty CODEProperty() {
		return CODE;
	}
}
