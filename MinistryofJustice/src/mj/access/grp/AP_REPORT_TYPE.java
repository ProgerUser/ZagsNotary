package mj.access.grp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AP_REPORT_TYPE {
	/** Идентификатор типа отчета */
	private IntegerProperty REPORT_TYPE_ID;
	/** Наименование типа отчета */
	private StringProperty REPORT_TYPE_NAME;

	public AP_REPORT_TYPE() {
		this.REPORT_TYPE_ID = new SimpleIntegerProperty();
		this.REPORT_TYPE_NAME = new SimpleStringProperty();
	}

	public void setREPORT_TYPE_ID(Integer REPORT_TYPE_ID) {
		this.REPORT_TYPE_ID.set(REPORT_TYPE_ID);
	}

	public void setREPORT_TYPE_NAME(String REPORT_TYPE_NAME) {
		this.REPORT_TYPE_NAME.set(REPORT_TYPE_NAME);
	}

	public Integer getREPORT_TYPE_ID() {
		return REPORT_TYPE_ID.get();
	}

	public String getREPORT_TYPE_NAME() {
		return REPORT_TYPE_NAME.get();
	}

	public IntegerProperty REPORT_TYPE_IDProperty() {
		return REPORT_TYPE_ID;
	}

	public StringProperty REPORT_TYPE_NAMEProperty() {
		return REPORT_TYPE_NAME;
	}
}
