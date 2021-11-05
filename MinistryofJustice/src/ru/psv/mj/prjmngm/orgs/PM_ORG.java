package ru.psv.mj.prjmngm.orgs;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PM_ORG {
	/** ID */
	private LongProperty ORG_ID;
	/** Наименование */
	private StringProperty ORG_NAME;
	/** Руководитель ФИО */
	private StringProperty ORG_RUK;
	/** Руководитель ФИО кратк. */
	private StringProperty ORG_SHNAME;
	/** Должность */
	private StringProperty ORG_DOLJ;
	/** Обращение */
	private StringProperty ORG_OBRASH;
	/** Пол Руководителя */
	private StringProperty ORG_RUK_GENDER;

	public PM_ORG() {
		this.ORG_ID = new SimpleLongProperty();
		this.ORG_NAME = new SimpleStringProperty();
		this.ORG_RUK = new SimpleStringProperty();
		this.ORG_SHNAME = new SimpleStringProperty();
		this.ORG_DOLJ = new SimpleStringProperty();
		this.ORG_OBRASH = new SimpleStringProperty();
		this.ORG_RUK_GENDER = new SimpleStringProperty();
	}

	public void setORG_ID(Long ORG_ID) {
		this.ORG_ID.set(ORG_ID);
	}

	public void setORG_NAME(String ORG_NAME) {
		this.ORG_NAME.set(ORG_NAME);
	}

	public void setORG_RUK(String ORG_RUK) {
		this.ORG_RUK.set(ORG_RUK);
	}

	public void setORG_SHNAME(String ORG_SHNAME) {
		this.ORG_SHNAME.set(ORG_SHNAME);
	}

	public void setORG_DOLJ(String ORG_DOLJ) {
		this.ORG_DOLJ.set(ORG_DOLJ);
	}

	public void setORG_OBRASH(String ORG_OBRASH) {
		this.ORG_OBRASH.set(ORG_OBRASH);
	}

	public void setORG_RUK_GENDER(String ORG_RUK_GENDER) {
		this.ORG_RUK_GENDER.set(ORG_RUK_GENDER);
	}

	public Long getORG_ID() {
		return ORG_ID.get();
	}

	public String getORG_NAME() {
		return ORG_NAME.get();
	}

	public String getORG_RUK() {
		return ORG_RUK.get();
	}

	public String getORG_SHNAME() {
		return ORG_SHNAME.get();
	}

	public String getORG_DOLJ() {
		return ORG_DOLJ.get();
	}

	public String getORG_OBRASH() {
		return ORG_OBRASH.get();
	}

	public String getORG_RUK_GENDER() {
		return ORG_RUK_GENDER.get();
	}

	public LongProperty ORG_IDProperty() {
		return ORG_ID;
	}

	public StringProperty ORG_NAMEProperty() {
		return ORG_NAME;
	}

	public StringProperty ORG_RUKProperty() {
		return ORG_RUK;
	}

	public StringProperty ORG_SHNAMEProperty() {
		return ORG_SHNAME;
	}

	public StringProperty ORG_DOLJProperty() {
		return ORG_DOLJ;
	}

	public StringProperty ORG_OBRASHProperty() {
		return ORG_OBRASH;
	}

	public StringProperty ORG_RUK_GENDERProperty() {
		return ORG_RUK_GENDER;
	}
}
