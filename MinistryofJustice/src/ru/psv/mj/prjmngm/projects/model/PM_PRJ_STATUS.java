package ru.psv.mj.prjmngm.projects.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PM_PRJ_STATUS {
	/** ID */
	private LongProperty PJST_ID;
	/** Наименование */
	private StringProperty PJST_NAME;

	public PM_PRJ_STATUS() {
		this.PJST_ID = new SimpleLongProperty();
		this.PJST_NAME = new SimpleStringProperty();
	}

	public void setPJST_ID(Long PJST_ID) {
		this.PJST_ID.set(PJST_ID);
	}

	public void setPJST_NAME(String PJST_NAME) {
		this.PJST_NAME.set(PJST_NAME);
	}

	public Long getPJST_ID() {
		return PJST_ID.get();
	}

	public String getPJST_NAME() {
		return PJST_NAME.get();
	}

	public LongProperty PJST_IDProperty() {
		return PJST_ID;
	}

	public StringProperty PJST_NAMEProperty() {
		return PJST_NAME;
	}
}
