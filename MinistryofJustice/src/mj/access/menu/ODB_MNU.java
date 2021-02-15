package mj.access.menu;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ODB_MNU {
	/** ������������� �������� */
	private IntegerProperty MNU_ID;
	/** ������������� ������������� �������� */
	private IntegerProperty MNU_PARENT;
	/** ���������� ����� ������ �������� */
	private IntegerProperty MNU_NPP;
	/** ������������ �������� */
	private StringProperty MNU_NAME;

	public ODB_MNU() {
		this.MNU_ID = new SimpleIntegerProperty();
		this.MNU_PARENT = new SimpleIntegerProperty();
		this.MNU_NPP = new SimpleIntegerProperty();
		this.MNU_NAME = new SimpleStringProperty();
	}

	public void setMNU_ID(Integer MNU_ID) {
		this.MNU_ID.set(MNU_ID);
	}

	public void setMNU_PARENT(Integer MNU_PARENT) {
		this.MNU_PARENT.set(MNU_PARENT);
	}

	public void setMNU_NPP(Integer MNU_NPP) {
		this.MNU_NPP.set(MNU_NPP);
	}

	public void setMNU_NAME(String MNU_NAME) {
		this.MNU_NAME.set(MNU_NAME);
	}

	public Integer getMNU_ID() {
		return MNU_ID.get();
	}

	public Integer getMNU_PARENT() {
		return MNU_PARENT.get();
	}

	public Integer getMNU_NPP() {
		return MNU_NPP.get();
	}

	public String getMNU_NAME() {
		return MNU_NAME.get();
	}

	public IntegerProperty MNU_IDProperty() {
		return MNU_ID;
	}

	public IntegerProperty MNU_PARENTProperty() {
		return MNU_PARENT;
	}

	public IntegerProperty MNU_NPPProperty() {
		return MNU_NPP;
	}

	public StringProperty MNU_NAMEProperty() {
		return MNU_NAME;
	}
}
