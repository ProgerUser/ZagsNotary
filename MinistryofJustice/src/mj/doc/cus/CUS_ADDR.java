package mj.doc.cus;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CUS_ADDR {
	private IntegerProperty ADDR_TYPE;/* Тип адреса */
	private IntegerProperty ICUSNUM;/* Идентификатор контрагента */
	private IntegerProperty ID_ADDR;/* ID адреса */
	private IntegerProperty STROY_TYPE;/* Признак строения */
	private StringProperty ADDRESS_INLINE;/* Адрес для нерезидентов(не по КЛАДР) в одну строку. */
	private StringProperty OKSM_CODE;/* Код ОКСМ территории */
	private StringProperty PORCH;/* Подъезд */
	private StringProperty OFFICE;/* Офис */
	private StringProperty KV;/* Квартира */
	private StringProperty INFR_TYPE;/* Тип инфраструктуры */
	private StringProperty STROY;/* Строение */
	private StringProperty KORP;/* Корпус */
	private StringProperty PUNCT_TYPE;/* Тип НП */
	private StringProperty DOM;/* Дом */
	private StringProperty INFR_NAME;/* Инфраструктура */
	private StringProperty AREA_TYPE;/* Тип района */
	private StringProperty CITY_TYPE;/* Тип города */
	private StringProperty PUNCT_NAME;/* Нас. пункт */
	private StringProperty CITY;/* Город */
	private StringProperty REG_NAME;/* Наименование региона */
	private StringProperty AREA;/* Район */
	private IntegerProperty REG_NUM;/* Номер региона */
	private StringProperty POST_INDEX;/* Почтовый индекс */
	private StringProperty COUNTRY;/* Страна */
	private StringProperty CODE;/* Код адреса */
	private StringProperty CLONGNAMET;
	

	public CUS_ADDR() {
		this.CLONGNAMET = new SimpleStringProperty();
		this.ADDR_TYPE = new SimpleIntegerProperty();
		this.ICUSNUM = new SimpleIntegerProperty();
		this.ID_ADDR = new SimpleIntegerProperty();
		this.STROY_TYPE = new SimpleIntegerProperty();
		this.ADDRESS_INLINE = new SimpleStringProperty();
		this.OKSM_CODE = new SimpleStringProperty();
		this.PORCH = new SimpleStringProperty();
		this.OFFICE = new SimpleStringProperty();
		this.KV = new SimpleStringProperty();
		this.INFR_TYPE = new SimpleStringProperty();
		this.STROY = new SimpleStringProperty();
		this.KORP = new SimpleStringProperty();
		this.PUNCT_TYPE = new SimpleStringProperty();
		this.DOM = new SimpleStringProperty();
		this.INFR_NAME = new SimpleStringProperty();
		this.AREA_TYPE = new SimpleStringProperty();
		this.CITY_TYPE = new SimpleStringProperty();
		this.PUNCT_NAME = new SimpleStringProperty();
		this.CITY = new SimpleStringProperty();
		this.REG_NAME = new SimpleStringProperty();
		this.AREA = new SimpleStringProperty();
		this.REG_NUM = new SimpleIntegerProperty();
		this.POST_INDEX = new SimpleStringProperty();
		this.COUNTRY = new SimpleStringProperty();
		this.CODE = new SimpleStringProperty();
	}

	public void setCLONGNAMET(String CLONGNAMET) {
		this.CLONGNAMET.set(CLONGNAMET);
	}
	
	public void setADDR_TYPE(Integer ADDR_TYPE) {
		this.ADDR_TYPE.set(ADDR_TYPE);
	}

	public void setICUSNUM(Integer ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public void setID_ADDR(Integer ID_ADDR) {
		this.ID_ADDR.set(ID_ADDR);
	}

	public void setSTROY_TYPE(Integer STROY_TYPE) {
		this.STROY_TYPE.set(STROY_TYPE);
	}

	public void setADDRESS_INLINE(String ADDRESS_INLINE) {
		this.ADDRESS_INLINE.set(ADDRESS_INLINE);
	}

	public void setOKSM_CODE(String OKSM_CODE) {
		this.OKSM_CODE.set(OKSM_CODE);
	}

	public void setPORCH(String PORCH) {
		this.PORCH.set(PORCH);
	}

	public void setOFFICE(String OFFICE) {
		this.OFFICE.set(OFFICE);
	}

	public void setKV(String KV) {
		this.KV.set(KV);
	}

	public void setINFR_TYPE(String INFR_TYPE) {
		this.INFR_TYPE.set(INFR_TYPE);
	}

	public void setSTROY(String STROY) {
		this.STROY.set(STROY);
	}

	public void setKORP(String KORP) {
		this.KORP.set(KORP);
	}

	public void setPUNCT_TYPE(String PUNCT_TYPE) {
		this.PUNCT_TYPE.set(PUNCT_TYPE);
	}

	public void setDOM(String DOM) {
		this.DOM.set(DOM);
	}

	public void setINFR_NAME(String INFR_NAME) {
		this.INFR_NAME.set(INFR_NAME);
	}

	public void setAREA_TYPE(String AREA_TYPE) {
		this.AREA_TYPE.set(AREA_TYPE);
	}

	public void setCITY_TYPE(String CITY_TYPE) {
		this.CITY_TYPE.set(CITY_TYPE);
	}

	public void setPUNCT_NAME(String PUNCT_NAME) {
		this.PUNCT_NAME.set(PUNCT_NAME);
	}

	public void setCITY(String CITY) {
		this.CITY.set(CITY);
	}

	public void setREG_NAME(String REG_NAME) {
		this.REG_NAME.set(REG_NAME);
	}

	public void setAREA(String AREA) {
		this.AREA.set(AREA);
	}

	public void setREG_NUM(Integer REG_NUM) {
		this.REG_NUM.set(REG_NUM);
	}

	public void setPOST_INDEX(String POST_INDEX) {
		this.POST_INDEX.set(POST_INDEX);
	}

	public void setCOUNTRY(String COUNTRY) {
		this.COUNTRY.set(COUNTRY);
	}

	public void setCODE(String CODE) {
		this.CODE.set(CODE);
	}

	public Integer getADDR_TYPE() {
		return ADDR_TYPE.get();
	}

	public Integer getICUSNUM() {
		return ICUSNUM.get();
	}

	public Integer getID_ADDR() {
		return ID_ADDR.get();
	}

	public Integer getSTROY_TYPE() {
		return STROY_TYPE.get();
	}

	public String getADDRESS_INLINE() {
		return ADDRESS_INLINE.get();
	}

	public String getOKSM_CODE() {
		return OKSM_CODE.get();
	}

	public String getPORCH() {
		return PORCH.get();
	}

	public String getOFFICE() {
		return OFFICE.get();
	}

	public String getKV() {
		return KV.get();
	}

	public String getINFR_TYPE() {
		return INFR_TYPE.get();
	}

	public String getSTROY() {
		return STROY.get();
	}

	public String getKORP() {
		return KORP.get();
	}
	
	public String getPUNCT_TYPE() {
		return PUNCT_TYPE.get();
	}
	
	public String getCLONGNAMET() {
		return CLONGNAMET.get();
	}

	public String getDOM() {
		return DOM.get();
	}

	public String getINFR_NAME() {
		return INFR_NAME.get();
	}

	public String getAREA_TYPE() {
		return AREA_TYPE.get();
	}

	public String getCITY_TYPE() {
		return CITY_TYPE.get();
	}

	public String getPUNCT_NAME() {
		return PUNCT_NAME.get();
	}

	public String getCITY() {
		return CITY.get();
	}

	public String getREG_NAME() {
		return REG_NAME.get();
	}

	public String getAREA() {
		return AREA.get();
	}

	public Integer getREG_NUM() {
		return REG_NUM.get();
	}

	public String getPOST_INDEX() {
		return POST_INDEX.get();
	}

	public String getCOUNTRY() {
		return COUNTRY.get();
	}

	public String getCODE() {
		return CODE.get();
	}

	public IntegerProperty ADDR_TYPEProperty() {
		return ADDR_TYPE;
	}

	public IntegerProperty ICUSNUMProperty() {
		return ICUSNUM;
	}

	public IntegerProperty ID_ADDRProperty() {
		return ID_ADDR;
	}

	public IntegerProperty STROY_TYPEProperty() {
		return STROY_TYPE;
	}

	public StringProperty ADDRESS_INLINEProperty() {
		return ADDRESS_INLINE;
	}

	public StringProperty OKSM_CODEProperty() {
		return OKSM_CODE;
	}

	public StringProperty PORCHProperty() {
		return PORCH;
	}

	public StringProperty OFFICEProperty() {
		return OFFICE;
	}
	
	public StringProperty CLONGNAMETProperty() {
		return CLONGNAMET;
	}
	

	public StringProperty KVProperty() {
		return KV;
	}

	public StringProperty INFR_TYPEProperty() {
		return INFR_TYPE;
	}

	public StringProperty STROYProperty() {
		return STROY;
	}

	public StringProperty KORPProperty() {
		return KORP;
	}

	public StringProperty PUNCT_TYPEProperty() {
		return PUNCT_TYPE;
	}

	public StringProperty DOMProperty() {
		return DOM;
	}

	public StringProperty INFR_NAMEProperty() {
		return INFR_NAME;
	}

	public StringProperty AREA_TYPEProperty() {
		return AREA_TYPE;
	}

	public StringProperty CITY_TYPEProperty() {
		return CITY_TYPE;
	}

	public StringProperty PUNCT_NAMEProperty() {
		return PUNCT_NAME;
	}

	public StringProperty CITYProperty() {
		return CITY;
	}

	public StringProperty REG_NAMEProperty() {
		return REG_NAME;
	}

	public StringProperty AREAProperty() {
		return AREA;
	}

	public IntegerProperty REG_NUMProperty() {
		return REG_NUM;
	}

	public StringProperty POST_INDEXProperty() {
		return POST_INDEX;
	}

	public StringProperty COUNTRYProperty() {
		return COUNTRY;
	}

	public StringProperty CODEProperty() {
		return CODE;
	}
}
