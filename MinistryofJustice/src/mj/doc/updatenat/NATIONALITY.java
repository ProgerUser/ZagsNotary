package mj.doc.updatenat;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NATIONALITY {
	/** Название */
	private StringProperty NAME;
	/** ID */
	private IntegerProperty ID;

	public NATIONALITY() {
		this.NAME = new SimpleStringProperty();
		this.ID = new SimpleIntegerProperty();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public String getNAME() {
		return NAME.get();
	}

	public Integer getID() {
		return ID.get();
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public IntegerProperty IDProperty() {
		return ID;
	}
}
