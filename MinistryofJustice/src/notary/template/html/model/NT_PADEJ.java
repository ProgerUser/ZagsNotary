package notary.template.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_PADEJ {
	/** Нет данных */
	private LongProperty PDJ_ID;
	/** Нет данных */
	private StringProperty PDJ_NAME;
	/** Нет данных */
	private StringProperty PDJ_R_NAME;

	public NT_PADEJ() {
		this.PDJ_ID = new SimpleLongProperty();
		this.PDJ_NAME = new SimpleStringProperty();
		this.PDJ_R_NAME = new SimpleStringProperty();
	}

	public void setPDJ_ID(Long PDJ_ID) {
		this.PDJ_ID.set(PDJ_ID);
	}

	public void setPDJ_NAME(String PDJ_NAME) {
		this.PDJ_NAME.set(PDJ_NAME);
	}

	public void setPDJ_R_NAME(String PDJ_R_NAME) {
		this.PDJ_R_NAME.set(PDJ_R_NAME);
	}

	public Long getPDJ_ID() {
		return PDJ_ID.get();
	}

	public String getPDJ_NAME() {
		return PDJ_NAME.get();
	}

	public String getPDJ_R_NAME() {
		return PDJ_R_NAME.get();
	}

	public LongProperty PDJ_IDProperty() {
		return PDJ_ID;
	}

	public StringProperty PDJ_NAMEProperty() {
		return PDJ_NAME;
	}

	public StringProperty PDJ_R_NAMEProperty() {
		return PDJ_R_NAME;
	}
}
