package ru.psv.mj.zags.doc.cus;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VCUS {
	/** Наименование национальности */
	private StringProperty NAME;
	/** Нет данных */
	private StringProperty COUNTRY_NAME;
	/** Нет данных */
	private StringProperty KV;
	/** Нет данных */
	private StringProperty DOM;
	/** Нет данных */
	private StringProperty PUNCT_NAME;
	/** Нет данных */
	private StringProperty INFR_NAME;
	/** Нет данных */
	private StringProperty CITY;
	/** Нет данных */
	private StringProperty AREA;
	/** Нет данных */
	private StringProperty COUNTRY;
	/** Нет данных */
	private StringProperty DOC_SUBDIV;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DOC_PERIOD;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DOC_DATE;
	/** Нет данных */
	private StringProperty DOC_NUM;
	/** Серия */
	private StringProperty DOC_SER;
	/** Тип документа */
	private StringProperty ID_DOC_TP;
	/** Пол */
	private StringProperty CCUSSEX;
	/** Отчество */
	private StringProperty CCUSMIDDLE_NAME;
	/** Имя */
	private StringProperty CCUSFIRST_NAME;
	/** Фамилия */
	private StringProperty CCUSLAST_NAME;
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Дата рождения */
	private SimpleObjectProperty<LocalDate> DCUSBIRTHDAY;
	/** Пользователь */
	private StringProperty CCUSIDOPEN;
	/** Дата заведения */
	private SimpleObjectProperty<LocalDateTime> TM$DCUSOPEN;
	/** ID документа */
	private LongProperty ICUSNUM;

	public VCUS() {
		this.NAME = new SimpleStringProperty();
		this.COUNTRY_NAME = new SimpleStringProperty();
		this.KV = new SimpleStringProperty();
		this.DOM = new SimpleStringProperty();
		this.PUNCT_NAME = new SimpleStringProperty();
		this.INFR_NAME = new SimpleStringProperty();
		this.CITY = new SimpleStringProperty();
		this.AREA = new SimpleStringProperty();
		this.COUNTRY = new SimpleStringProperty();
		this.DOC_SUBDIV = new SimpleStringProperty();
		this.DOC_PERIOD = new SimpleObjectProperty<>();
		this.DOC_DATE = new SimpleObjectProperty<>();
		this.DOC_NUM = new SimpleStringProperty();
		this.DOC_SER = new SimpleStringProperty();
		this.ID_DOC_TP = new SimpleStringProperty();
		this.CCUSSEX = new SimpleStringProperty();
		this.CCUSMIDDLE_NAME = new SimpleStringProperty();
		this.CCUSFIRST_NAME = new SimpleStringProperty();
		this.CCUSLAST_NAME = new SimpleStringProperty();
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.DCUSBIRTHDAY = new SimpleObjectProperty<>();
		this.CCUSIDOPEN = new SimpleStringProperty();
		this.TM$DCUSOPEN = new SimpleObjectProperty<>();
		this.ICUSNUM = new SimpleLongProperty();
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME.set(COUNTRY_NAME);
	}

	public void setKV(String KV) {
		this.KV.set(KV);
	}

	public void setDOM(String DOM) {
		this.DOM.set(DOM);
	}

	public void setPUNCT_NAME(String PUNCT_NAME) {
		this.PUNCT_NAME.set(PUNCT_NAME);
	}

	public void setINFR_NAME(String INFR_NAME) {
		this.INFR_NAME.set(INFR_NAME);
	}

	public void setCITY(String CITY) {
		this.CITY.set(CITY);
	}

	public void setAREA(String AREA) {
		this.AREA.set(AREA);
	}

	public void setCOUNTRY(String COUNTRY) {
		this.COUNTRY.set(COUNTRY);
	}

	public void setDOC_SUBDIV(String DOC_SUBDIV) {
		this.DOC_SUBDIV.set(DOC_SUBDIV);
	}

	public void setDOC_PERIOD(LocalDate DOC_PERIOD) {
		this.DOC_PERIOD.set(DOC_PERIOD);
	}

	public void setDOC_DATE(LocalDate DOC_DATE) {
		this.DOC_DATE.set(DOC_DATE);
	}

	public void setDOC_NUM(String DOC_NUM) {
		this.DOC_NUM.set(DOC_NUM);
	}

	public void setDOC_SER(String DOC_SER) {
		this.DOC_SER.set(DOC_SER);
	}

	public void setID_DOC_TP(String ID_DOC_TP) {
		this.ID_DOC_TP.set(ID_DOC_TP);
	}

	public void setCCUSSEX(String CCUSSEX) {
		this.CCUSSEX.set(CCUSSEX);
	}

	public void setCCUSMIDDLE_NAME(String CCUSMIDDLE_NAME) {
		this.CCUSMIDDLE_NAME.set(CCUSMIDDLE_NAME);
	}

	public void setCCUSFIRST_NAME(String CCUSFIRST_NAME) {
		this.CCUSFIRST_NAME.set(CCUSFIRST_NAME);
	}

	public void setCCUSLAST_NAME(String CCUSLAST_NAME) {
		this.CCUSLAST_NAME.set(CCUSLAST_NAME);
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setDCUSBIRTHDAY(LocalDate DCUSBIRTHDAY) {
		this.DCUSBIRTHDAY.set(DCUSBIRTHDAY);
	}

	public void setCCUSIDOPEN(String CCUSIDOPEN) {
		this.CCUSIDOPEN.set(CCUSIDOPEN);
	}

	public void setTM$DCUSOPEN(LocalDateTime TM$DCUSOPEN) {
		this.TM$DCUSOPEN.set(TM$DCUSOPEN);
	}

	public void setICUSNUM(Long ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public String getNAME() {
		return NAME.get();
	}

	public String getCOUNTRY_NAME() {
		return COUNTRY_NAME.get();
	}

	public String getKV() {
		return KV.get();
	}

	public String getDOM() {
		return DOM.get();
	}

	public String getPUNCT_NAME() {
		return PUNCT_NAME.get();
	}

	public String getINFR_NAME() {
		return INFR_NAME.get();
	}

	public String getCITY() {
		return CITY.get();
	}

	public String getAREA() {
		return AREA.get();
	}

	public String getCOUNTRY() {
		return COUNTRY.get();
	}

	public String getDOC_SUBDIV() {
		return DOC_SUBDIV.get();
	}

	public LocalDate getDOC_PERIOD() {
		return DOC_PERIOD.get();
	}

	public LocalDate getDOC_DATE() {
		return DOC_DATE.get();
	}

	public String getDOC_NUM() {
		return DOC_NUM.get();
	}

	public String getDOC_SER() {
		return DOC_SER.get();
	}

	public String getID_DOC_TP() {
		return ID_DOC_TP.get();
	}

	public String getCCUSSEX() {
		return CCUSSEX.get();
	}

	public String getCCUSMIDDLE_NAME() {
		return CCUSMIDDLE_NAME.get();
	}

	public String getCCUSFIRST_NAME() {
		return CCUSFIRST_NAME.get();
	}

	public String getCCUSLAST_NAME() {
		return CCUSLAST_NAME.get();
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public LocalDate getDCUSBIRTHDAY() {
		return DCUSBIRTHDAY.get();
	}

	public String getCCUSIDOPEN() {
		return CCUSIDOPEN.get();
	}

	public LocalDateTime getTM$DCUSOPEN() {
		return TM$DCUSOPEN.get();
	}

	public Long getICUSNUM() {
		return ICUSNUM.get();
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public StringProperty COUNTRY_NAMEProperty() {
		return COUNTRY_NAME;
	}

	public StringProperty KVProperty() {
		return KV;
	}

	public StringProperty DOMProperty() {
		return DOM;
	}

	public StringProperty PUNCT_NAMEProperty() {
		return PUNCT_NAME;
	}

	public StringProperty INFR_NAMEProperty() {
		return INFR_NAME;
	}

	public StringProperty CITYProperty() {
		return CITY;
	}

	public StringProperty AREAProperty() {
		return AREA;
	}

	public StringProperty COUNTRYProperty() {
		return COUNTRY;
	}

	public StringProperty DOC_SUBDIVProperty() {
		return DOC_SUBDIV;
	}

	public SimpleObjectProperty<LocalDate> DOC_PERIODProperty() {
		return DOC_PERIOD;
	}

	public SimpleObjectProperty<LocalDate> DOC_DATEProperty() {
		return DOC_DATE;
	}

	public StringProperty DOC_NUMProperty() {
		return DOC_NUM;
	}

	public StringProperty DOC_SERProperty() {
		return DOC_SER;
	}

	public StringProperty ID_DOC_TPProperty() {
		return ID_DOC_TP;
	}

	public StringProperty CCUSSEXProperty() {
		return CCUSSEX;
	}

	public StringProperty CCUSMIDDLE_NAMEProperty() {
		return CCUSMIDDLE_NAME;
	}

	public StringProperty CCUSFIRST_NAMEProperty() {
		return CCUSFIRST_NAME;
	}

	public StringProperty CCUSLAST_NAMEProperty() {
		return CCUSLAST_NAME;
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public SimpleObjectProperty<LocalDate> DCUSBIRTHDAYProperty() {
		return DCUSBIRTHDAY;
	}

	public StringProperty CCUSIDOPENProperty() {
		return CCUSIDOPEN;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DCUSOPENProperty() {
		return TM$DCUSOPEN;
	}

	public LongProperty ICUSNUMProperty() {
		return ICUSNUM;
	}
}
