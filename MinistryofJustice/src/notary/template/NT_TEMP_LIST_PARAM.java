package notary.template;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_TEMP_LIST_PARAM {
	/** ИД */
	private IntegerProperty PRM_ID;
	/** Название */
	private StringProperty PRM_NAME;
	/** Ссылка на шаблоны */
	private IntegerProperty PRM_TMP_ID;
	/** Запрос */
	private StringProperty PRM_SQL;
	/** Тип параметра */
	private IntegerProperty PRM_TYPE;

	public NT_TEMP_LIST_PARAM() {
		this.PRM_ID = new SimpleIntegerProperty();
		this.PRM_NAME = new SimpleStringProperty();
		this.PRM_TMP_ID = new SimpleIntegerProperty();
		this.PRM_SQL = new SimpleStringProperty();
		this.PRM_TYPE = new SimpleIntegerProperty();
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
}
