package notary.client;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GENDER {
	/** ID */
	private IntegerProperty ID;
	/** Пол */
	private StringProperty SEX;

	public GENDER() {
		this.ID = new SimpleIntegerProperty();
		this.SEX = new SimpleStringProperty();
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setSEX(String SEX) {
		this.SEX.set(SEX);
	}

	public Integer getID() {
		return ID.get();
	}

	public String getSEX() {
		return SEX.get();
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public StringProperty SEXProperty() {
		return SEX;
	}
}
