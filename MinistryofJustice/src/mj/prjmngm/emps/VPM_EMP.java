package mj.prjmngm.emps;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPM_EMP {
	/** ИД сотрудника */
	private LongProperty EMP_ID;
	/** Фамилия */
	private StringProperty EMP_LASTNAME;
	/** Имя */
	private StringProperty EMP_FIRSTNAME;
	/** Отчество */
	private StringProperty EMP_MIDDLENAME;
	/** Должность */
	private StringProperty EMP_POSITION;
	/** Email */
	private StringProperty EMP_EMAIL;
	/** Телефон */
	private StringProperty EMP_TEL;
	/** Логин в программе */
	private StringProperty EMP_LOGIN;

	public VPM_EMP() {
		this.EMP_ID = new SimpleLongProperty();
		this.EMP_LASTNAME = new SimpleStringProperty();
		this.EMP_FIRSTNAME = new SimpleStringProperty();
		this.EMP_MIDDLENAME = new SimpleStringProperty();
		this.EMP_POSITION = new SimpleStringProperty();
		this.EMP_EMAIL = new SimpleStringProperty();
		this.EMP_TEL = new SimpleStringProperty();
		this.EMP_LOGIN = new SimpleStringProperty();
	}

	public void setEMP_ID(Long EMP_ID) {
		this.EMP_ID.set(EMP_ID);
	}

	public void setEMP_LASTNAME(String EMP_LASTNAME) {
		this.EMP_LASTNAME.set(EMP_LASTNAME);
	}

	public void setEMP_FIRSTNAME(String EMP_FIRSTNAME) {
		this.EMP_FIRSTNAME.set(EMP_FIRSTNAME);
	}

	public void setEMP_MIDDLENAME(String EMP_MIDDLENAME) {
		this.EMP_MIDDLENAME.set(EMP_MIDDLENAME);
	}

	public void setEMP_POSITION(String EMP_POSITION) {
		this.EMP_POSITION.set(EMP_POSITION);
	}

	public void setEMP_EMAIL(String EMP_EMAIL) {
		this.EMP_EMAIL.set(EMP_EMAIL);
	}

	public void setEMP_TEL(String EMP_TEL) {
		this.EMP_TEL.set(EMP_TEL);
	}

	public void setEMP_LOGIN(String EMP_LOGIN) {
		this.EMP_LOGIN.set(EMP_LOGIN);
	}

	public Long getEMP_ID() {
		return EMP_ID.get();
	}

	public String getEMP_LASTNAME() {
		return EMP_LASTNAME.get();
	}

	public String getEMP_FIRSTNAME() {
		return EMP_FIRSTNAME.get();
	}

	public String getEMP_MIDDLENAME() {
		return EMP_MIDDLENAME.get();
	}

	public String getEMP_POSITION() {
		return EMP_POSITION.get();
	}

	public String getEMP_EMAIL() {
		return EMP_EMAIL.get();
	}

	public String getEMP_TEL() {
		return EMP_TEL.get();
	}

	public String getEMP_LOGIN() {
		return EMP_LOGIN.get();
	}

	public LongProperty EMP_IDProperty() {
		return EMP_ID;
	}

	public StringProperty EMP_LASTNAMEProperty() {
		return EMP_LASTNAME;
	}

	public StringProperty EMP_FIRSTNAMEProperty() {
		return EMP_FIRSTNAME;
	}

	public StringProperty EMP_MIDDLENAMEProperty() {
		return EMP_MIDDLENAME;
	}

	public StringProperty EMP_POSITIONProperty() {
		return EMP_POSITION;
	}

	public StringProperty EMP_EMAILProperty() {
		return EMP_EMAIL;
	}

	public StringProperty EMP_TELProperty() {
		return EMP_TEL;
	}

	public StringProperty EMP_LOGINProperty() {
		return EMP_LOGIN;
	}
}
