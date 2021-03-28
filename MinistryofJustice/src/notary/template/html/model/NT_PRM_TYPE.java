package notary.template.html.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_PRM_TYPE {
	/** ИД */
	private IntegerProperty TYPE_ID;
	/** Название */
	private StringProperty TYPE_NAME;

	public NT_PRM_TYPE() {
		this.TYPE_ID = new SimpleIntegerProperty();
		this.TYPE_NAME = new SimpleStringProperty();
	}

	public void setTYPE_ID(Integer TYPE_ID) {
		this.TYPE_ID.set(TYPE_ID);
	}

	public void setTYPE_NAME(String TYPE_NAME) {
		this.TYPE_NAME.set(TYPE_NAME);
	}

	public Integer getTYPE_ID() {
		return TYPE_ID.get();
	}

	public String getTYPE_NAME() {
		return TYPE_NAME.get();
	}

	public IntegerProperty TYPE_IDProperty() {
		return TYPE_ID;
	}

	public StringProperty TYPE_NAMEProperty() {
		return TYPE_NAME;
	}
}
