package mj.init;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class USERS {
	/** Нет данных */
	private LongProperty ID;
	/** Нет данных */
	private StringProperty USR_NAME;

	public USERS() {
		this.ID = new SimpleLongProperty();
		this.USR_NAME = new SimpleStringProperty();
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME.set(USR_NAME);
	}

	public Long getID() {
		return ID.get();
	}

	public String getUSR_NAME() {
		return USR_NAME.get();
	}

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty USR_NAMEProperty() {
		return USR_NAME;
	}
}
