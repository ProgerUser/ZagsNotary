package mj.access.grp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ODB_GROUP_USR {
	/** Идентификатор группы */
	private IntegerProperty GRP_ID;
	/** Наименование группы */
	private StringProperty GRP_NAME;
	/** Название */
	private StringProperty NAME;

	public ODB_GROUP_USR() {
		this.GRP_ID = new SimpleIntegerProperty();
		this.GRP_NAME = new SimpleStringProperty();
		this.NAME = new SimpleStringProperty();
	}

	public void setGRP_ID(Integer GRP_ID) {
		this.GRP_ID.set(GRP_ID);
	}

	public void setGRP_NAME(String GRP_NAME) {
		this.GRP_NAME.set(GRP_NAME);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public Integer getGRP_ID() {
		return GRP_ID.get();
	}

	public String getGRP_NAME() {
		return GRP_NAME.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public IntegerProperty GRP_IDProperty() {
		return GRP_ID;
	}

	public StringProperty GRP_NAMEProperty() {
		return GRP_NAME;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}
}
