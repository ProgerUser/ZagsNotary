package mj.report;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class REP_PARAMS {
	/** Ссылка на отчет */
	private LongProperty REP_ID;
	/** Название параметра */
	private StringProperty PRM_NAME;
	/** Значение по умолчанию для параметра */
	private StringProperty PRM_DEF_VALUE;
	/** Номер параметра */
	private LongProperty PRM_ID;
	/** Список? */
	private StringProperty IS_LIST;
	/** Запрос списка */
	private StringProperty LIST_QUERY;
	
	private StringProperty PARAMS;

	public REP_PARAMS() {
		this.PARAMS = new SimpleStringProperty();
		this.REP_ID = new SimpleLongProperty();
		this.PRM_NAME = new SimpleStringProperty();
		this.PRM_DEF_VALUE = new SimpleStringProperty();
		this.PRM_ID = new SimpleLongProperty();
		this.IS_LIST = new SimpleStringProperty();
		this.LIST_QUERY = new SimpleStringProperty();
	}

	public void setPARAMS(String PARAMS) {
		this.PARAMS.set(PARAMS);
	}
	
	public StringProperty PARAMSProperty() {
		return PARAMS;
	}
	
	public String getPARAMS() {
		return PARAMS.get();
	}
	
	public void setREP_ID(Long REP_ID) {
		this.REP_ID.set(REP_ID);
	}

	public void setPRM_NAME(String PRM_NAME) {
		this.PRM_NAME.set(PRM_NAME);
	}

	public void setPRM_DEF_VALUE(String PRM_DEF_VALUE) {
		this.PRM_DEF_VALUE.set(PRM_DEF_VALUE);
	}

	public void setPRM_ID(Long PRM_ID) {
		this.PRM_ID.set(PRM_ID);
	}

	public void setIS_LIST(String IS_LIST) {
		this.IS_LIST.set(IS_LIST);
	}

	public void setLIST_QUERY(String LIST_QUERY) {
		this.LIST_QUERY.set(LIST_QUERY);
	}

	public Long getREP_ID() {
		return REP_ID.get();
	}

	public String getPRM_NAME() {
		return PRM_NAME.get();
	}

	public String getPRM_DEF_VALUE() {
		return PRM_DEF_VALUE.get();
	}

	public Long getPRM_ID() {
		return PRM_ID.get();
	}

	public String getIS_LIST() {
		return IS_LIST.get();
	}

	public String getLIST_QUERY() {
		return LIST_QUERY.get();
	}

	public LongProperty REP_IDProperty() {
		return REP_ID;
	}

	public StringProperty PRM_NAMEProperty() {
		return PRM_NAME;
	}

	public StringProperty PRM_DEF_VALUEProperty() {
		return PRM_DEF_VALUE;
	}

	public LongProperty PRM_IDProperty() {
		return PRM_ID;
	}

	public StringProperty IS_LISTProperty() {
		return IS_LIST;
	}

	public StringProperty LIST_QUERYProperty() {
		return LIST_QUERY;
	}
}
