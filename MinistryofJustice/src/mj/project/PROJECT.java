package mj.project;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PROJECT {
	/** Идентификатор */
	private LongProperty PRJ_ID;
	/** Идентификатор родительской папки */
	private LongProperty PRJ_PARENT;
	/** Размер файла */
	private LongProperty BYTES;
	/** Наименование */
	private StringProperty PRJ_NAME;
	/** Папка или нет */
	private StringProperty IS_FOLDER;
	/** Версия */
	private LongProperty VERSION;

	public PROJECT() {
		this.PRJ_ID = new SimpleLongProperty();
		this.PRJ_PARENT = new SimpleLongProperty();
		this.BYTES = new SimpleLongProperty();
		this.PRJ_NAME = new SimpleStringProperty();
		this.IS_FOLDER = new SimpleStringProperty();
		this.VERSION = new SimpleLongProperty();
	}

	public void setPRJ_ID(Long PRJ_ID) {
		this.PRJ_ID.set(PRJ_ID);
	}

	public void setPRJ_PARENT(Long PRJ_PARENT) {
		this.PRJ_PARENT.set(PRJ_PARENT);
	}

	public void setBYTES(Long BYTES) {
		this.BYTES.set(BYTES);
	}

	public void setPRJ_NAME(String PRJ_NAME) {
		this.PRJ_NAME.set(PRJ_NAME);
	}

	public void setIS_FOLDER(String IS_FOLDER) {
		this.IS_FOLDER.set(IS_FOLDER);
	}

	public void setVERSION(Long VERSION) {
		this.VERSION.set(VERSION);
	}

	public Long getPRJ_ID() {
		return PRJ_ID.get();
	}

	public Long getPRJ_PARENT() {
		return PRJ_PARENT.get();
	}

	public Long getBYTES() {
		return BYTES.get();
	}

	public String getPRJ_NAME() {
		return PRJ_NAME.get();
	}

	public String getIS_FOLDER() {
		return IS_FOLDER.get();
	}

	public Long getVERSION() {
		return VERSION.get();
	}

	public LongProperty PRJ_IDProperty() {
		return PRJ_ID;
	}

	public LongProperty PRJ_PARENTProperty() {
		return PRJ_PARENT;
	}

	public LongProperty BYTESProperty() {
		return BYTES;
	}

	public StringProperty PRJ_NAMEProperty() {
		return PRJ_NAME;
	}

	public StringProperty IS_FOLDERProperty() {
		return IS_FOLDER;
	}

	public LongProperty VERSIONProperty() {
		return VERSION;
	}
}
