package notary.client.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CUS_CITIZEN {
	private BooleanProperty OSN;/* Основной */
	private IntegerProperty ICUSNUM;/* Идентификатор контрагента */
	private StringProperty COUNTRY_NAME;/* Полное наименование */
	private IntegerProperty COUNTRY_CODE;/* Страна */
	private IntegerProperty ID;/* ID */

	public CUS_CITIZEN() {
		this.OSN = new SimpleBooleanProperty();
		this.ICUSNUM = new SimpleIntegerProperty();
		this.COUNTRY_NAME = new SimpleStringProperty();
		this.COUNTRY_CODE = new SimpleIntegerProperty();
		this.ID = new SimpleIntegerProperty();
	}

	public void setOSN(Boolean OSN) {
		this.OSN.set(OSN);
	}

	public void setICUSNUM(Integer ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME.set(COUNTRY_NAME);
	}

	public void setCOUNTRY_CODE(Integer COUNTRY_CODE) {
		this.COUNTRY_CODE.set(COUNTRY_CODE);
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public Boolean getOSN() {
		return OSN.get();
	}

	public Integer getICUSNUM() {
		return ICUSNUM.get();
	}

	public String getCOUNTRY_NAME() {
		return COUNTRY_NAME.get();
	}

	public Integer getCOUNTRY_CODE() {
		return COUNTRY_CODE.get();
	}

	public Integer getID() {
		return ID.get();
	}

	public BooleanProperty OSNProperty() {
		return OSN;
	}

	public IntegerProperty ICUSNUMProperty() {
		return ICUSNUM;
	}

	public StringProperty COUNTRY_NAMEProperty() {
		return COUNTRY_NAME;
	}

	public IntegerProperty COUNTRY_CODEProperty() {
		return COUNTRY_CODE;
	}

	public IntegerProperty IDProperty() {
		return ID;
	}
}
