package notary.doc.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_NT_TEMP_LIST {
	/** Нет данных */
	private StringProperty NAMES;
	/** Нет данных */
	private IntegerProperty ID;
	/** Нет данных */
	private StringProperty NAME;
	/** Нет данных */
	private IntegerProperty PARENT;
	/** Нет данных */
	private StringProperty FILE_PATH;

	public V_NT_TEMP_LIST() {
		this.NAMES = new SimpleStringProperty();
		this.ID = new SimpleIntegerProperty();
		this.NAME = new SimpleStringProperty();
		this.PARENT = new SimpleIntegerProperty();
		this.FILE_PATH = new SimpleStringProperty();
	}

	public void setNAMES(String NAMES) {
		this.NAMES.set(NAMES);
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setPARENT(Integer PARENT) {
		this.PARENT.set(PARENT);
	}

	public void setFILE_PATH(String FILE_PATH) {
		this.FILE_PATH.set(FILE_PATH);
	}

	public String getNAMES() {
		return NAMES.get();
	}

	public Integer getID() {
		return ID.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public Integer getPARENT() {
		return PARENT.get();
	}

	public String getFILE_PATH() {
		return FILE_PATH.get();
	}

	public StringProperty NAMESProperty() {
		return NAMES;
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public IntegerProperty PARENTProperty() {
		return PARENT;
	}

	public StringProperty FILE_PATHProperty() {
		return FILE_PATH;
	}
}
