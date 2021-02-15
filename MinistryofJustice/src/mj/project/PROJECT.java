package mj.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PROJECT {
	/** Идентификатор */
	private IntegerProperty PRJ_ID;
	/** Идентификатор родительской папки */
	private IntegerProperty PRJ_PARENT;
	/** Размер файла */
	private IntegerProperty BYTES;
	/** Наименование */
	private StringProperty PRJ_NAME;
	/** Папка или нет */
	private StringProperty IS_FOLDER;
	/** Версия */
	private IntegerProperty VERSION;

	public PROJECT() {
		this.PRJ_ID = new SimpleIntegerProperty();
		this.PRJ_PARENT = new SimpleIntegerProperty();
		this.BYTES = new SimpleIntegerProperty();
		this.PRJ_NAME = new SimpleStringProperty();
		this.IS_FOLDER = new SimpleStringProperty();
		this.VERSION = new SimpleIntegerProperty();
	}

	public void setPRJ_ID(Integer PRJ_ID) {
		this.PRJ_ID.set(PRJ_ID);
	}

	public void setPRJ_PARENT(Integer PRJ_PARENT) {
		this.PRJ_PARENT.set(PRJ_PARENT);
	}

	public void setBYTES(Integer BYTES) {
		this.BYTES.set(BYTES);
	}

	public void setPRJ_NAME(String PRJ_NAME) {
		this.PRJ_NAME.set(PRJ_NAME);
	}

	public void setIS_FOLDER(String IS_FOLDER) {
		this.IS_FOLDER.set(IS_FOLDER);
	}

	public void setVERSION(Integer VERSION) {
		this.VERSION.set(VERSION);
	}

	public Integer getPRJ_ID() {
		return PRJ_ID.get();
	}

	public Integer getPRJ_PARENT() {
		return PRJ_PARENT.get();
	}

	public Integer getBYTES() {
		return BYTES.get();
	}

	public String getPRJ_NAME() {
		return PRJ_NAME.get();
	}

	public String getIS_FOLDER() {
		return IS_FOLDER.get();
	}

	public Integer getVERSION() {
		return VERSION.get();
	}

	public IntegerProperty PRJ_IDProperty() {
		return PRJ_ID;
	}

	public IntegerProperty PRJ_PARENTProperty() {
		return PRJ_PARENT;
	}

	public IntegerProperty BYTESProperty() {
		return BYTES;
	}

	public StringProperty PRJ_NAMEProperty() {
		return PRJ_NAME;
	}

	public StringProperty IS_FOLDERProperty() {
		return IS_FOLDER;
	}

	public IntegerProperty VERSIONProperty() {
		return VERSION;
	}
}
