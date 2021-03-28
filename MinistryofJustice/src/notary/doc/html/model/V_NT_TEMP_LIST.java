package notary.doc.html.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_NT_TEMP_LIST {
	/** Нет данных */
	private StringProperty HTML_TEMP;
	/** Нет данных */
	private StringProperty NAMES;
	/** Нет данных */
	private IntegerProperty ID;
	/** Нет данных */
	private StringProperty NAME;
	/** Нет данных */
	private IntegerProperty PARENT;

	public V_NT_TEMP_LIST() {
		this.HTML_TEMP = new SimpleStringProperty();
		this.NAMES = new SimpleStringProperty();
		this.ID = new SimpleIntegerProperty();
		this.NAME = new SimpleStringProperty();
		this.PARENT = new SimpleIntegerProperty();
	}

	public void setHTML_TEMP(String HTML_TEMP) {
		this.HTML_TEMP.set(HTML_TEMP);
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

	public String getHTML_TEMP() {
		return HTML_TEMP.get();
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

	public StringProperty HTML_TEMPProperty() {
		return HTML_TEMP;
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
}
