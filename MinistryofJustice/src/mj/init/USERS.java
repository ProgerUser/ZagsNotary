package mj.init;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class USERS {
	/** Нет данных */
	private IntegerProperty ID;
	/** Нет данных */
	private StringProperty USR_NAME;

	public USERS() {
		this.ID = new SimpleIntegerProperty();
		this.USR_NAME = new SimpleStringProperty();
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME.set(USR_NAME);
	}

	public Integer getID() {
		return ID.get();
	}

	public String getUSR_NAME() {
		return USR_NAME.get();
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public StringProperty USR_NAMEProperty() {
		return USR_NAME;
	}
}
