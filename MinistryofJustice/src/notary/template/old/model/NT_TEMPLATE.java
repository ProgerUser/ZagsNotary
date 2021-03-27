package notary.template.old.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_TEMPLATE {
	/** Идентификатор шаблона */
	private IntegerProperty NT_ID;
	/** Идентификатор родительского шаблона */
	private IntegerProperty NT_PARENT;
	/** Порядковый номер внутри иерархии */
	private IntegerProperty NT_NPP;
	/** Наименование шаблона */
	private StringProperty NT_NAME;

	public NT_TEMPLATE() {
		this.NT_ID = new SimpleIntegerProperty();
		this.NT_PARENT = new SimpleIntegerProperty();
		this.NT_NPP = new SimpleIntegerProperty();
		this.NT_NAME = new SimpleStringProperty();
	}

	public void setNT_ID(Integer NT_ID) {
		this.NT_ID.set(NT_ID);
	}

	public void setNT_PARENT(Integer NT_PARENT) {
		this.NT_PARENT.set(NT_PARENT);
	}

	public void setNT_NPP(Integer NT_NPP) {
		this.NT_NPP.set(NT_NPP);
	}

	public void setNT_NAME(String NT_NAME) {
		this.NT_NAME.set(NT_NAME);
	}

	public Integer getNT_ID() {
		return NT_ID.get();
	}

	public Integer getNT_PARENT() {
		return NT_PARENT.get();
	}

	public Integer getNT_NPP() {
		return NT_NPP.get();
	}

	public String getNT_NAME() {
		return NT_NAME.get();
	}

	public IntegerProperty NT_IDProperty() {
		return NT_ID;
	}

	public IntegerProperty NT_PARENTProperty() {
		return NT_PARENT;
	}

	public IntegerProperty NT_NPPProperty() {
		return NT_NPP;
	}

	public StringProperty NT_NAMEProperty() {
		return NT_NAME;
	}
}
