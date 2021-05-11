package notary.template.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_TEMPLATE {
	/** Идентификатор шаблона */
	private LongProperty NT_ID;
	/** Идентификатор родительского шаблона */
	private LongProperty NT_PARENT;
	/** Порядковый номер внутри иерархии */
	private LongProperty NT_NPP;
	/** Наименование шаблона */
	private StringProperty NT_NAME;

	public NT_TEMPLATE() {
		this.NT_ID = new SimpleLongProperty();
		this.NT_PARENT = new SimpleLongProperty();
		this.NT_NPP = new SimpleLongProperty();
		this.NT_NAME = new SimpleStringProperty();
	}

	public void setNT_ID(Long NT_ID) {
		this.NT_ID.set(NT_ID);
	}

	public void setNT_PARENT(Long NT_PARENT) {
		this.NT_PARENT.set(NT_PARENT);
	}

	public void setNT_NPP(Long NT_NPP) {
		this.NT_NPP.set(NT_NPP);
	}

	public void setNT_NAME(String NT_NAME) {
		this.NT_NAME.set(NT_NAME);
	}

	public Long getNT_ID() {
		return NT_ID.get();
	}

	public Long getNT_PARENT() {
		return NT_PARENT.get();
	}

	public Long getNT_NPP() {
		return NT_NPP.get();
	}

	public String getNT_NAME() {
		return NT_NAME.get();
	}

	public LongProperty NT_IDProperty() {
		return NT_ID;
	}

	public LongProperty NT_PARENTProperty() {
		return NT_PARENT;
	}

	public LongProperty NT_NPPProperty() {
		return NT_NPP;
	}

	public StringProperty NT_NAMEProperty() {
		return NT_NAME;
	}
}
