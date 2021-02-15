package mj.access.action;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ODB_ACTION {
	private StringProperty ACT_NAME;/* Наименование действия */
	private IntegerProperty ACT_NPP;/* Порядковый номер внутри иерархии */
	private IntegerProperty ACT_PARENT;/* Идентификатор родительского действия */
	private IntegerProperty ACT_ID;/* Идентификатор действия */

	public ODB_ACTION() {
		this.ACT_NAME = new SimpleStringProperty();
		this.ACT_NPP = new SimpleIntegerProperty();
		this.ACT_PARENT = new SimpleIntegerProperty();
		this.ACT_ID = new SimpleIntegerProperty();
	}

	public void setACT_NAME(String ACT_NAME) {
		this.ACT_NAME.set(ACT_NAME);
	}

	public void setACT_NPP(Integer ACT_NPP) {
		this.ACT_NPP.set(ACT_NPP);
	}

	public void setACT_PARENT(Integer ACT_PARENT) {
		this.ACT_PARENT.set(ACT_PARENT);
	}

	public void setACT_ID(Integer ACT_ID) {
		this.ACT_ID.set(ACT_ID);
	}

	public String getACT_NAME() {
		return ACT_NAME.get();
	}

	public Integer getACT_NPP() {
		return ACT_NPP.get();
	}

	public Integer getACT_PARENT() {
		return ACT_PARENT.get();
	}

	public Integer getACT_ID() {
		return ACT_ID.get();
	}

	public StringProperty ACT_NAMEProperty() {
		return ACT_NAME;
	}

	public IntegerProperty ACT_NPPProperty() {
		return ACT_NPP;
	}

	public IntegerProperty ACT_PARENTProperty() {
		return ACT_PARENT;
	}

	public IntegerProperty ACT_IDProperty() {
		return ACT_ID;
	}
}
