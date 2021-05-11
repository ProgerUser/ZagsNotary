package mj.report;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class REPORTS {
	/** ид отчета */
	private LongProperty REP_ID;
	/** название отчета */
	private StringProperty REP_NAME;
	/** тип отчета */
	private StringProperty REP_TYPE;

	public REPORTS() {
		this.REP_ID = new SimpleLongProperty();
		this.REP_NAME = new SimpleStringProperty();
		this.REP_TYPE = new SimpleStringProperty();
	}

	public void setREP_ID(Long REP_ID) {
		this.REP_ID.set(REP_ID);
	}

	public void setREP_NAME(String REP_NAME) {
		this.REP_NAME.set(REP_NAME);
	}

	public void setREP_TYPE(String REP_TYPE) {
		this.REP_TYPE.set(REP_TYPE);
	}

	public Long getREP_ID() {
		return REP_ID.get();
	}

	public String getREP_NAME() {
		return REP_NAME.get();
	}

	public String getREP_TYPE() {
		return REP_TYPE.get();
	}

	public LongProperty REP_IDProperty() {
		return REP_ID;
	}

	public StringProperty REP_NAMEProperty() {
		return REP_NAME;
	}

	public StringProperty REP_TYPEProperty() {
		return REP_TYPE;
	}
}
