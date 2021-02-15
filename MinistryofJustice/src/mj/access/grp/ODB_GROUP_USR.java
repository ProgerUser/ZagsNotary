package mj.access.grp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ODB_GROUP_USR {
	/** Внешний Id примечаний */
	private IntegerProperty NOTATION_EXTEND_ID;
	/** Наименование группы */
	private StringProperty GRP_NAME;
	/** Идентификатор группы */
	private IntegerProperty GRP_ID;

	public ODB_GROUP_USR() {
		this.NOTATION_EXTEND_ID = new SimpleIntegerProperty();
		this.GRP_NAME = new SimpleStringProperty();
		this.GRP_ID = new SimpleIntegerProperty();
	}

	public void setNOTATION_EXTEND_ID(Integer NOTATION_EXTEND_ID) {
		this.NOTATION_EXTEND_ID.set(NOTATION_EXTEND_ID);
	}

	public void setGRP_NAME(String GRP_NAME) {
		this.GRP_NAME.set(GRP_NAME);
	}

	public void setGRP_ID(Integer GRP_ID) {
		this.GRP_ID.set(GRP_ID);
	}

	public Integer getNOTATION_EXTEND_ID() {
		return NOTATION_EXTEND_ID.get();
	}

	public String getGRP_NAME() {
		return GRP_NAME.get();
	}

	public Integer getGRP_ID() {
		return GRP_ID.get();
	}

	public IntegerProperty NOTATION_EXTEND_IDProperty() {
		return NOTATION_EXTEND_ID;
	}

	public StringProperty GRP_NAMEProperty() {
		return GRP_NAME;
	}

	public IntegerProperty GRP_IDProperty() {
		return GRP_ID;
	}
}
