package notary.doc;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LIST {
	/** ��� */
	private IntegerProperty CODE;
	/** ������������ */
	private StringProperty NAME;

	public LIST() {
		this.CODE = new SimpleIntegerProperty();
		this.NAME = new SimpleStringProperty();
	}

	public void setCODE(Integer CODE) {
		this.CODE.set(CODE);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public Integer getCODE() {
		return CODE.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public IntegerProperty CODEProperty() {
		return CODE;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}
}
