package notary.client.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CUS_CITIZEN {
	private BooleanProperty OSN;/* Основной */
	private LongProperty ICUSNUM;/* Идентификатор контрагента */
	private StringProperty COUNTRY_NAME;/* Полное наименование */
	private LongProperty COUNTRY_CODE;/* Страна */
	private LongProperty ID;/* ID */

	public CUS_CITIZEN() {
		this.OSN = new SimpleBooleanProperty();
		this.ICUSNUM = new SimpleLongProperty();
		this.COUNTRY_NAME = new SimpleStringProperty();
		this.COUNTRY_CODE = new SimpleLongProperty();
		this.ID = new SimpleLongProperty();
	}

	public void setOSN(Boolean OSN) {
		this.OSN.set(OSN);
	}

	public void setICUSNUM(Long ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME.set(COUNTRY_NAME);
	}

	public void setCOUNTRY_CODE(Long COUNTRY_CODE) {
		this.COUNTRY_CODE.set(COUNTRY_CODE);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public Boolean getOSN() {
		return OSN.get();
	}

	public Long getICUSNUM() {
		return ICUSNUM.get();
	}

	public String getCOUNTRY_NAME() {
		return COUNTRY_NAME.get();
	}

	public Long getCOUNTRY_CODE() {
		return COUNTRY_CODE.get();
	}

	public Long getID() {
		return ID.get();
	}

	public BooleanProperty OSNProperty() {
		return OSN;
	}

	public LongProperty ICUSNUMProperty() {
		return ICUSNUM;
	}

	public StringProperty COUNTRY_NAMEProperty() {
		return COUNTRY_NAME;
	}

	public LongProperty COUNTRY_CODEProperty() {
		return COUNTRY_CODE;
	}

	public LongProperty IDProperty() {
		return ID;
	}
}
