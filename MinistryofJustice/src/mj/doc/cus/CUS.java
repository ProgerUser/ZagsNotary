package mj.doc.cus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CUS {
	private StringProperty CCUSMIDDLE_NAME;/* Отчество */
	private StringProperty CCUSFIRST_NAME;/* Имя */
	private StringProperty CCUSLAST_NAME;/* Фамилия */
	private SimpleObjectProperty<LocalDate> DCUSBIRTHDAY;/* Дата рождения (для физ.лиц) */
	private StringProperty CCUSNAME_SH;/* Краткое наименование клиента */
	private StringProperty CCUSCOUNTRY1;/* Страна местонахождения/Гражданство */
	private StringProperty CCUSNAME;/* Название */
	private StringProperty CCUSIDOPEN;/* Кто завел */
	private SimpleObjectProperty<LocalDate> DCUSEDIT;/* Дата окончания срока действия документов */
	private SimpleObjectProperty<LocalDateTime> DCUSOPEN;/* Дата заведения */
	private IntegerProperty ICUSNUM;/* Уникальный номер клиента */
	private StringProperty CCUS_OK_SM;/* Код страны рождения */
	private StringProperty BurthCountry;/* Страна рождения */
	private IntegerProperty ICUSOTD;/* ссылка на номер отделения */
	private StringProperty CCUSPLACE_BIRTH;/* Место рождения */
	private IntegerProperty CCUSSEX;/* Пол */
	private StringProperty CCUSNATIONALITY;/* Национальность */
	private StringProperty OTD_NAME;/* Нет данных */
	private StringProperty CITY;/* Нет данных */
	private StringProperty AREA;/* Нет данных */
	private StringProperty INF;/* Нет данных */
	private StringProperty Country;/* Нет данных */

	public CUS() {
		this.BurthCountry = new SimpleStringProperty();
		this.Country = new SimpleStringProperty();
		this.CCUSMIDDLE_NAME = new SimpleStringProperty();
		this.CCUSFIRST_NAME = new SimpleStringProperty();
		this.CCUSLAST_NAME = new SimpleStringProperty();
		this.DCUSBIRTHDAY = new SimpleObjectProperty<>();
		this.CCUSNAME_SH = new SimpleStringProperty();
		this.CCUSCOUNTRY1 = new SimpleStringProperty();
		this.CCUSNAME = new SimpleStringProperty();
		this.CCUSIDOPEN = new SimpleStringProperty();
		this.DCUSEDIT = new SimpleObjectProperty<>();
		this.DCUSOPEN = new SimpleObjectProperty<>();
		this.ICUSNUM = new SimpleIntegerProperty();
		this.CCUS_OK_SM = new SimpleStringProperty();
		this.ICUSOTD = new SimpleIntegerProperty();
		this.CCUSPLACE_BIRTH = new SimpleStringProperty();
		this.CCUSSEX = new SimpleIntegerProperty();
		this.CCUSNATIONALITY = new SimpleStringProperty();
		this.OTD_NAME = new SimpleStringProperty();
		this.CITY = new SimpleStringProperty();
		this.AREA = new SimpleStringProperty();
		this.INF = new SimpleStringProperty();
	}

	public void setCountry(String Country) {
		this.Country.set(Country);
	}

	public String getCountry() {
		return Country.get();
	}
	
	public StringProperty CountryProperty() {
		return Country;
	}
	
	public void setINF(String INF) {
		this.INF.set(INF);
	}

	public void setBurthCountry(String BurthCountry) {
		this.BurthCountry.set(BurthCountry);
	}

	public void setAREA(String AREA) {
		this.AREA.set(AREA);
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

	public void setDCUSBIRTHDAY(LocalDate DCUSBIRTHDAY) {
		this.DCUSBIRTHDAY.set(DCUSBIRTHDAY);
	}

	public void setCCUSNAME_SH(String CCUSNAME_SH) {
		this.CCUSNAME_SH.set(CCUSNAME_SH);
	}

	public void setCCUSCOUNTRY1(String CCUSCOUNTRY1) {
		this.CCUSCOUNTRY1.set(CCUSCOUNTRY1);
	}

	public void setCCUSNAME(String CCUSNAME) {
		this.CCUSNAME.set(CCUSNAME);
	}

	public void setCCUSIDOPEN(String CCUSIDOPEN) {
		this.CCUSIDOPEN.set(CCUSIDOPEN);
	}

	public void setDCUSEDIT(LocalDate DCUSEDIT) {
		this.DCUSEDIT.set(DCUSEDIT);
	}

	public void setDCUSOPEN(LocalDateTime DCUSOPEN) {
		this.DCUSOPEN.set(DCUSOPEN);
	}

	public void setICUSNUM(Integer ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public void setCCUS_OK_SM(String CCUS_OK_SM) {
		this.CCUS_OK_SM.set(CCUS_OK_SM);
	}

	public void setICUSOTD(Integer ICUSOTD) {
		this.ICUSOTD.set(ICUSOTD);
	}

	public void setCCUSPLACE_BIRTH(String CCUSPLACE_BIRTH) {
		this.CCUSPLACE_BIRTH.set(CCUSPLACE_BIRTH);
	}

	public void setCCUSSEX(Integer CCUSSEX) {
		this.CCUSSEX.set(CCUSSEX);
	}

	public void setCCUSNATIONALITY(String CCUSNATIONALITY) {
		this.CCUSNATIONALITY.set(CCUSNATIONALITY);
	}

	public void setOTD_NAME(String OTD_NAME) {
		this.OTD_NAME.set(OTD_NAME);
	}

	public void setCITY(String CITY) {
		this.CITY.set(CITY);
	}

	public String getINF() {
		return INF.get();
	}

	public String getBurthCountry() {
		return BurthCountry.get();
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

	public LocalDate getDCUSBIRTHDAY() {
		return DCUSBIRTHDAY.get();
	}

	public String getAREA() {
		return AREA.get();
	}

	public String getCCUSNAME_SH() {
		return CCUSNAME_SH.get();
	}

	public String getCCUSCOUNTRY1() {
		return CCUSCOUNTRY1.get();
	}

	public String getCCUSNAME() {
		return CCUSNAME.get();
	}

	public String getCCUSIDOPEN() {
		return CCUSIDOPEN.get();
	}

	public Object getDCUSEDIT() {
		return DCUSEDIT.get();
	}

	public LocalDateTime getDCUSOPEN() {
		return DCUSOPEN.get();
	}

	public Integer getICUSNUM() {
		return ICUSNUM.get();
	}

	public String getCCUS_OK_SM() {
		return CCUS_OK_SM.get();
	}

	public Integer getICUSOTD() {
		return ICUSOTD.get();
	}

	public String getCCUSPLACE_BIRTH() {
		return CCUSPLACE_BIRTH.get();
	}

	public Integer getCCUSSEX() {
		return CCUSSEX.get();
	}

	public String getCCUSNATIONALITY() {
		return CCUSNATIONALITY.get();
	}

	public String getOTD_NAME() {
		return OTD_NAME.get();
	}

	public String getCITY() {
		return CITY.get();
	}

	public StringProperty AREAProperty() {
		return AREA;
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

	public StringProperty BurthCountryProperty() {
		return BurthCountry;
	}

	public SimpleObjectProperty<LocalDate> DCUSBIRTHDAYProperty() {
		return DCUSBIRTHDAY;
	}

	public StringProperty CCUSNAME_SHProperty() {
		return CCUSNAME_SH;
	}

	public StringProperty INFProperty() {
		return INF;
	}

	public StringProperty CCUSCOUNTRY1Property() {
		return CCUSCOUNTRY1;
	}

	public StringProperty CCUSNAMEProperty() {
		return CCUSNAME;
	}

	public StringProperty CCUSIDOPENProperty() {
		return CCUSIDOPEN;
	}

	public SimpleObjectProperty<LocalDate> DCUSEDITProperty() {
		return DCUSEDIT;
	}

	public SimpleObjectProperty<LocalDateTime> DCUSOPENProperty() {
		return DCUSOPEN;
	}

	public IntegerProperty ICUSNUMProperty() {
		return ICUSNUM;
	}

	public StringProperty CCUS_OK_SMProperty() {
		return CCUS_OK_SM;
	}

	public IntegerProperty ICUSOTDProperty() {
		return ICUSOTD;
	}

	public StringProperty CCUSPLACE_BIRTHProperty() {
		return CCUSPLACE_BIRTH;
	}

	public IntegerProperty CCUSSEXProperty() {
		return CCUSSEX;
	}

	public StringProperty CCUSNATIONALITYProperty() {
		return CCUSNATIONALITY;
	}

	public StringProperty OTD_NAMEProperty() {
		return OTD_NAME;
	}

	public StringProperty CITYProperty() {
		return CITY;
	}
}
