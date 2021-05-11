package notary.template.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_PRM_TYPE {
	/** ИД */
	private LongProperty TYPE_ID;
	/** Название */
	private StringProperty TYPE_NAME;

	public NT_PRM_TYPE() {
		this.TYPE_ID = new SimpleLongProperty();
		this.TYPE_NAME = new SimpleStringProperty();
	}

	public void setTYPE_ID(Long TYPE_ID) {
		this.TYPE_ID.set(TYPE_ID);
	}

	public void setTYPE_NAME(String TYPE_NAME) {
		this.TYPE_NAME.set(TYPE_NAME);
	}

	public Long getTYPE_ID() {
		return TYPE_ID.get();
	}

	public String getTYPE_NAME() {
		return TYPE_NAME.get();
	}

	public LongProperty TYPE_IDProperty() {
		return TYPE_ID;
	}

	public StringProperty TYPE_NAMEProperty() {
		return TYPE_NAME;
	}
}
