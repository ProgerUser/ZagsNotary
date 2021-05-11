package mj.doc.updatenat;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NATIONALITY {
	/** Название */
	private StringProperty NAME;
	/** ID */
	private LongProperty ID;

	public NATIONALITY() {
		this.NAME = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public String getNAME() {
		return NAME.get();
	}

	public Long getID() {
		return ID.get();
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public LongProperty IDProperty() {
		return ID;
	}
}
