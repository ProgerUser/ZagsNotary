package notary.doc.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_NT_DOC_PRM {
	/** Нет данных */
	private IntegerProperty PRM_ID;
	/** Нет данных */
	private StringProperty PRM_NAME;
	/** Нет данных */
	private IntegerProperty PRM_TMP_ID;
	/** Нет данных */
	private StringProperty PRM_SQL;
	/** Нет данных */
	private IntegerProperty PRM_TYPE;
	/** Нет данных */
	private IntegerProperty VAL_PRM_ID;
	/** Нет данных */
	private StringProperty VAL_NT_VALUE;

	public V_NT_DOC_PRM() {
		this.PRM_ID = new SimpleIntegerProperty();
		this.PRM_NAME = new SimpleStringProperty();
		this.PRM_TMP_ID = new SimpleIntegerProperty();
		this.PRM_SQL = new SimpleStringProperty();
		this.PRM_TYPE = new SimpleIntegerProperty();
		this.VAL_PRM_ID = new SimpleIntegerProperty();
		this.VAL_NT_VALUE = new SimpleStringProperty();
	}

	public void setPRM_ID(Integer PRM_ID) {
		this.PRM_ID.set(PRM_ID);
	}

	public void setPRM_NAME(String PRM_NAME) {
		this.PRM_NAME.set(PRM_NAME);
	}

	public void setPRM_TMP_ID(Integer PRM_TMP_ID) {
		this.PRM_TMP_ID.set(PRM_TMP_ID);
	}

	public void setPRM_SQL(String PRM_SQL) {
		this.PRM_SQL.set(PRM_SQL);
	}

	public void setPRM_TYPE(Integer PRM_TYPE) {
		this.PRM_TYPE.set(PRM_TYPE);
	}

	public void setVAL_PRM_ID(Integer VAL_PRM_ID) {
		this.VAL_PRM_ID.set(VAL_PRM_ID);
	}

	public void setVAL_NT_VALUE(String VAL_NT_VALUE) {
		this.VAL_NT_VALUE.set(VAL_NT_VALUE);
	}

	public Integer getPRM_ID() {
		return PRM_ID.get();
	}

	public String getPRM_NAME() {
		return PRM_NAME.get();
	}

	public Integer getPRM_TMP_ID() {
		return PRM_TMP_ID.get();
	}

	public String getPRM_SQL() {
		return PRM_SQL.get();
	}

	public Integer getPRM_TYPE() {
		return PRM_TYPE.get();
	}

	public Integer getVAL_PRM_ID() {
		return VAL_PRM_ID.get();
	}

	public String getVAL_NT_VALUE() {
		return VAL_NT_VALUE.get();
	}

	public IntegerProperty PRM_IDProperty() {
		return PRM_ID;
	}

	public StringProperty PRM_NAMEProperty() {
		return PRM_NAME;
	}

	public IntegerProperty PRM_TMP_IDProperty() {
		return PRM_TMP_ID;
	}

	public StringProperty PRM_SQLProperty() {
		return PRM_SQL;
	}

	public IntegerProperty PRM_TYPEProperty() {
		return PRM_TYPE;
	}

	public IntegerProperty VAL_PRM_IDProperty() {
		return VAL_PRM_ID;
	}

	public StringProperty VAL_NT_VALUEProperty() {
		return VAL_NT_VALUE;
	}
}
