package notary.doc.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_DOV_YEAR_LIST_FOR_SEARCH {
	/** Нет данных */
	private LongProperty ID;
	/** Нет данных */
	private StringProperty RUNAME;
	/** Нет данных */
	private LongProperty NUM;
	/** Нет данных */
	private LongProperty DOC_ID;

	public NT_DOV_YEAR_LIST_FOR_SEARCH() {
		this.ID = new SimpleLongProperty();
		this.RUNAME = new SimpleStringProperty();
		this.NUM = new SimpleLongProperty();
		this.DOC_ID = new SimpleLongProperty();
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setRUNAME(String RUNAME) {
		this.RUNAME.set(RUNAME);
	}

	public void setNUM(Long NUM) {
		this.NUM.set(NUM);
	}

	public void setDOC_ID(Long DOC_ID) {
		this.DOC_ID.set(DOC_ID);
	}

	public Long getID() {
		return ID.get();
	}

	public String getRUNAME() {
		return RUNAME.get();
	}

	public Long getNUM() {
		return NUM.get();
	}

	public Long getDOC_ID() {
		return DOC_ID.get();
	}

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty RUNAMEProperty() {
		return RUNAME;
	}

	public LongProperty NUMProperty() {
		return NUM;
	}

	public LongProperty DOC_IDProperty() {
		return DOC_ID;
	}
}
