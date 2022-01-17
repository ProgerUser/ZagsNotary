package ru.psv.mj.prjmngm.emps;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PM_EMP_JBTP {
	/** ID */
	private LongProperty JB_ID;
	/** Наименование */
	private StringProperty JP_NAME;

	public PM_EMP_JBTP() {
		this.JB_ID = new SimpleLongProperty();
		this.JP_NAME = new SimpleStringProperty();
	}

	public void setJB_ID(Long JB_ID) {
		this.JB_ID.set(JB_ID);
	}

	public void setJP_NAME(String JP_NAME) {
		this.JP_NAME.set(JP_NAME);
	}

	public Long getJB_ID() {
		return JB_ID.get();
	}

	public String getJP_NAME() {
		return JP_NAME.get();
	}

	public LongProperty JB_IDProperty() {
		return JB_ID;
	}

	public StringProperty JP_NAMEProperty() {
		return JP_NAME;
	}
}
