package notary.doc.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_PRM_SEARCH {
	/** ИД */
	private LongProperty SRCH_ID;
	/** Название параметра */
	private StringProperty SRCH_NAME;

	public NT_PRM_SEARCH() {
		this.SRCH_ID = new SimpleLongProperty();
		this.SRCH_NAME = new SimpleStringProperty();
	}

	public void setSRCH_ID(Long SRCH_ID) {
		this.SRCH_ID.set(SRCH_ID);
	}

	public void setSRCH_NAME(String SRCH_NAME) {
		this.SRCH_NAME.set(SRCH_NAME);
	}

	public Long getSRCH_ID() {
		return SRCH_ID.get();
	}

	public String getSRCH_NAME() {
		return SRCH_NAME.get();
	}

	public LongProperty SRCH_IDProperty() {
		return SRCH_ID;
	}

	public StringProperty SRCH_NAMEProperty() {
		return SRCH_NAME;
	}
}
