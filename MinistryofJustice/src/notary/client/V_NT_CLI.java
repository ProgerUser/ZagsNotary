package notary.client;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_NT_CLI {
	/** Нет данных */
	private IntegerProperty CLI_ID;
	/** Нет данных */
	private StringProperty CLI_NAME;
	/** Нет данных */
	private StringProperty CLI_LAST_NAME;
	/** Нет данных */
	private StringProperty CLI_FIRST_NAME;
	/** Нет данных */
	private StringProperty CLI_MIDDLE_NAME;
	/** Нет данных */
	private StringProperty CLI_SH_NAME;
	/** Нет данных */
	private IntegerProperty CLI_TYPE;
	/** Нет данных */
	private IntegerProperty CLI_GENDER;
	/** Нет данных */
	private IntegerProperty CLI_NOTARY;
	/** Нет данных */
	private StringProperty CLI_INN;
	/** Нет данных */
	private StringProperty CLI_KPP;
	/** Нет данных */
	private StringProperty CLI_OGRN;
	/** Нет данных */
	private StringProperty CLI_OPER;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CLI_DATE;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CLI_BR_DATE;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CLI_DATE_REG;
	/** Нет данных */
	private IntegerProperty CLI_ADR_COUNTRY;
	/** Нет данных */
	private IntegerProperty CLI_ADR_RAION;
	/** Нет данных */
	private IntegerProperty CLI_ADR_NAS_PUNKT;
	/** Нет данных */
	private StringProperty CLI_ADR_STREET;
	/** Нет данных */
	private StringProperty CLI_ADR_HOME;
	/** Нет данных */
	private StringProperty CLI_ADR_CORP;
	/** Нет данных */
	private StringProperty CLI_ADR_KV;
	/** Нет данных */
	private IntegerProperty CLI_DOC_TYPE;
	/** Нет данных */
	private StringProperty CLI_DOC_SERIA;
	/** Нет данных */
	private StringProperty CLI_DOC_NUMBER;
	/** Нет данных */
	private StringProperty CLI_DOC_AGENCY;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CLI_DOC_START;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CLI_DOC_END;
	/** Нет данных */
	private StringProperty CLI_DOC_SUBDIV;
	/** Нет данных */
	private StringProperty CLI_PLACE_BIRTH;
	/** Нет данных */
	private IntegerProperty CLI_BIRTH_COUNTRY;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty CR_TIME;

	public V_NT_CLI() {
		this.CLI_ID = new SimpleIntegerProperty();
		this.CLI_NAME = new SimpleStringProperty();
		this.CLI_LAST_NAME = new SimpleStringProperty();
		this.CLI_FIRST_NAME = new SimpleStringProperty();
		this.CLI_MIDDLE_NAME = new SimpleStringProperty();
		this.CLI_SH_NAME = new SimpleStringProperty();
		this.CLI_TYPE = new SimpleIntegerProperty();
		this.CLI_GENDER = new SimpleIntegerProperty();
		this.CLI_NOTARY = new SimpleIntegerProperty();
		this.CLI_INN = new SimpleStringProperty();
		this.CLI_KPP = new SimpleStringProperty();
		this.CLI_OGRN = new SimpleStringProperty();
		this.CLI_OPER = new SimpleStringProperty();
		this.CLI_DATE = new SimpleObjectProperty<>();
		this.CLI_BR_DATE = new SimpleObjectProperty<>();
		this.CLI_DATE_REG = new SimpleObjectProperty<>();
		this.CLI_ADR_COUNTRY = new SimpleIntegerProperty();
		this.CLI_ADR_RAION = new SimpleIntegerProperty();
		this.CLI_ADR_NAS_PUNKT = new SimpleIntegerProperty();
		this.CLI_ADR_STREET = new SimpleStringProperty();
		this.CLI_ADR_HOME = new SimpleStringProperty();
		this.CLI_ADR_CORP = new SimpleStringProperty();
		this.CLI_ADR_KV = new SimpleStringProperty();
		this.CLI_DOC_TYPE = new SimpleIntegerProperty();
		this.CLI_DOC_SERIA = new SimpleStringProperty();
		this.CLI_DOC_NUMBER = new SimpleStringProperty();
		this.CLI_DOC_AGENCY = new SimpleStringProperty();
		this.CLI_DOC_START = new SimpleObjectProperty<>();
		this.CLI_DOC_END = new SimpleObjectProperty<>();
		this.CLI_DOC_SUBDIV = new SimpleStringProperty();
		this.CLI_PLACE_BIRTH = new SimpleStringProperty();
		this.CLI_BIRTH_COUNTRY = new SimpleIntegerProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.CR_TIME = new SimpleStringProperty();
	}

	public void setCLI_ID(Integer CLI_ID) {
		this.CLI_ID.set(CLI_ID);
	}

	public void setCLI_NAME(String CLI_NAME) {
		this.CLI_NAME.set(CLI_NAME);
	}

	public void setCLI_LAST_NAME(String CLI_LAST_NAME) {
		this.CLI_LAST_NAME.set(CLI_LAST_NAME);
	}

	public void setCLI_FIRST_NAME(String CLI_FIRST_NAME) {
		this.CLI_FIRST_NAME.set(CLI_FIRST_NAME);
	}

	public void setCLI_MIDDLE_NAME(String CLI_MIDDLE_NAME) {
		this.CLI_MIDDLE_NAME.set(CLI_MIDDLE_NAME);
	}

	public void setCLI_SH_NAME(String CLI_SH_NAME) {
		this.CLI_SH_NAME.set(CLI_SH_NAME);
	}

	public void setCLI_TYPE(Integer CLI_TYPE) {
		this.CLI_TYPE.set(CLI_TYPE);
	}

	public void setCLI_GENDER(Integer CLI_GENDER) {
		this.CLI_GENDER.set(CLI_GENDER);
	}

	public void setCLI_NOTARY(Integer CLI_NOTARY) {
		this.CLI_NOTARY.set(CLI_NOTARY);
	}

	public void setCLI_INN(String CLI_INN) {
		this.CLI_INN.set(CLI_INN);
	}

	public void setCLI_KPP(String CLI_KPP) {
		this.CLI_KPP.set(CLI_KPP);
	}

	public void setCLI_OGRN(String CLI_OGRN) {
		this.CLI_OGRN.set(CLI_OGRN);
	}

	public void setCLI_OPER(String CLI_OPER) {
		this.CLI_OPER.set(CLI_OPER);
	}

	public void setCLI_DATE(LocalDate CLI_DATE) {
		this.CLI_DATE.set(CLI_DATE);
	}

	public void setCLI_BR_DATE(LocalDate CLI_BR_DATE) {
		this.CLI_BR_DATE.set(CLI_BR_DATE);
	}

	public void setCLI_DATE_REG(LocalDate CLI_DATE_REG) {
		this.CLI_DATE_REG.set(CLI_DATE_REG);
	}

	public void setCLI_ADR_COUNTRY(Integer CLI_ADR_COUNTRY) {
		this.CLI_ADR_COUNTRY.set(CLI_ADR_COUNTRY);
	}

	public void setCLI_ADR_RAION(Integer CLI_ADR_RAION) {
		this.CLI_ADR_RAION.set(CLI_ADR_RAION);
	}

	public void setCLI_ADR_NAS_PUNKT(Integer CLI_ADR_NAS_PUNKT) {
		this.CLI_ADR_NAS_PUNKT.set(CLI_ADR_NAS_PUNKT);
	}

	public void setCLI_ADR_STREET(String CLI_ADR_STREET) {
		this.CLI_ADR_STREET.set(CLI_ADR_STREET);
	}

	public void setCLI_ADR_HOME(String CLI_ADR_HOME) {
		this.CLI_ADR_HOME.set(CLI_ADR_HOME);
	}

	public void setCLI_ADR_CORP(String CLI_ADR_CORP) {
		this.CLI_ADR_CORP.set(CLI_ADR_CORP);
	}

	public void setCLI_ADR_KV(String CLI_ADR_KV) {
		this.CLI_ADR_KV.set(CLI_ADR_KV);
	}

	public void setCLI_DOC_TYPE(Integer CLI_DOC_TYPE) {
		this.CLI_DOC_TYPE.set(CLI_DOC_TYPE);
	}

	public void setCLI_DOC_SERIA(String CLI_DOC_SERIA) {
		this.CLI_DOC_SERIA.set(CLI_DOC_SERIA);
	}

	public void setCLI_DOC_NUMBER(String CLI_DOC_NUMBER) {
		this.CLI_DOC_NUMBER.set(CLI_DOC_NUMBER);
	}

	public void setCLI_DOC_AGENCY(String CLI_DOC_AGENCY) {
		this.CLI_DOC_AGENCY.set(CLI_DOC_AGENCY);
	}

	public void setCLI_DOC_START(LocalDate CLI_DOC_START) {
		this.CLI_DOC_START.set(CLI_DOC_START);
	}

	public void setCLI_DOC_END(LocalDate CLI_DOC_END) {
		this.CLI_DOC_END.set(CLI_DOC_END);
	}

	public void setCLI_DOC_SUBDIV(String CLI_DOC_SUBDIV) {
		this.CLI_DOC_SUBDIV.set(CLI_DOC_SUBDIV);
	}

	public void setCLI_PLACE_BIRTH(String CLI_PLACE_BIRTH) {
		this.CLI_PLACE_BIRTH.set(CLI_PLACE_BIRTH);
	}

	public void setCLI_BIRTH_COUNTRY(Integer CLI_BIRTH_COUNTRY) {
		this.CLI_BIRTH_COUNTRY.set(CLI_BIRTH_COUNTRY);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public Integer getCLI_ID() {
		return CLI_ID.get();
	}

	public String getCLI_NAME() {
		return CLI_NAME.get();
	}

	public String getCLI_LAST_NAME() {
		return CLI_LAST_NAME.get();
	}

	public String getCLI_FIRST_NAME() {
		return CLI_FIRST_NAME.get();
	}

	public String getCLI_MIDDLE_NAME() {
		return CLI_MIDDLE_NAME.get();
	}

	public String getCLI_SH_NAME() {
		return CLI_SH_NAME.get();
	}

	public Integer getCLI_TYPE() {
		return CLI_TYPE.get();
	}

	public Integer getCLI_GENDER() {
		return CLI_GENDER.get();
	}

	public Integer getCLI_NOTARY() {
		return CLI_NOTARY.get();
	}

	public String getCLI_INN() {
		return CLI_INN.get();
	}

	public String getCLI_KPP() {
		return CLI_KPP.get();
	}

	public String getCLI_OGRN() {
		return CLI_OGRN.get();
	}

	public String getCLI_OPER() {
		return CLI_OPER.get();
	}

	public LocalDate getCLI_DATE() {
		return CLI_DATE.get();
	}

	public LocalDate getCLI_BR_DATE() {
		return CLI_BR_DATE.get();
	}

	public LocalDate getCLI_DATE_REG() {
		return CLI_DATE_REG.get();
	}

	public Integer getCLI_ADR_COUNTRY() {
		return CLI_ADR_COUNTRY.get();
	}

	public Integer getCLI_ADR_RAION() {
		return CLI_ADR_RAION.get();
	}

	public Integer getCLI_ADR_NAS_PUNKT() {
		return CLI_ADR_NAS_PUNKT.get();
	}

	public String getCLI_ADR_STREET() {
		return CLI_ADR_STREET.get();
	}

	public String getCLI_ADR_HOME() {
		return CLI_ADR_HOME.get();
	}

	public String getCLI_ADR_CORP() {
		return CLI_ADR_CORP.get();
	}

	public String getCLI_ADR_KV() {
		return CLI_ADR_KV.get();
	}

	public Integer getCLI_DOC_TYPE() {
		return CLI_DOC_TYPE.get();
	}

	public String getCLI_DOC_SERIA() {
		return CLI_DOC_SERIA.get();
	}

	public String getCLI_DOC_NUMBER() {
		return CLI_DOC_NUMBER.get();
	}

	public String getCLI_DOC_AGENCY() {
		return CLI_DOC_AGENCY.get();
	}

	public LocalDate getCLI_DOC_START() {
		return CLI_DOC_START.get();
	}

	public LocalDate getCLI_DOC_END() {
		return CLI_DOC_END.get();
	}

	public String getCLI_DOC_SUBDIV() {
		return CLI_DOC_SUBDIV.get();
	}

	public String getCLI_PLACE_BIRTH() {
		return CLI_PLACE_BIRTH.get();
	}

	public Integer getCLI_BIRTH_COUNTRY() {
		return CLI_BIRTH_COUNTRY.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public IntegerProperty CLI_IDProperty() {
		return CLI_ID;
	}

	public StringProperty CLI_NAMEProperty() {
		return CLI_NAME;
	}

	public StringProperty CLI_LAST_NAMEProperty() {
		return CLI_LAST_NAME;
	}

	public StringProperty CLI_FIRST_NAMEProperty() {
		return CLI_FIRST_NAME;
	}

	public StringProperty CLI_MIDDLE_NAMEProperty() {
		return CLI_MIDDLE_NAME;
	}

	public StringProperty CLI_SH_NAMEProperty() {
		return CLI_SH_NAME;
	}

	public IntegerProperty CLI_TYPEProperty() {
		return CLI_TYPE;
	}

	public IntegerProperty CLI_GENDERProperty() {
		return CLI_GENDER;
	}

	public IntegerProperty CLI_NOTARYProperty() {
		return CLI_NOTARY;
	}

	public StringProperty CLI_INNProperty() {
		return CLI_INN;
	}

	public StringProperty CLI_KPPProperty() {
		return CLI_KPP;
	}

	public StringProperty CLI_OGRNProperty() {
		return CLI_OGRN;
	}

	public StringProperty CLI_OPERProperty() {
		return CLI_OPER;
	}

	public SimpleObjectProperty<LocalDate> CLI_DATEProperty() {
		return CLI_DATE;
	}

	public SimpleObjectProperty<LocalDate> CLI_BR_DATEProperty() {
		return CLI_BR_DATE;
	}

	public SimpleObjectProperty<LocalDate> CLI_DATE_REGProperty() {
		return CLI_DATE_REG;
	}

	public IntegerProperty CLI_ADR_COUNTRYProperty() {
		return CLI_ADR_COUNTRY;
	}

	public IntegerProperty CLI_ADR_RAIONProperty() {
		return CLI_ADR_RAION;
	}

	public IntegerProperty CLI_ADR_NAS_PUNKTProperty() {
		return CLI_ADR_NAS_PUNKT;
	}

	public StringProperty CLI_ADR_STREETProperty() {
		return CLI_ADR_STREET;
	}

	public StringProperty CLI_ADR_HOMEProperty() {
		return CLI_ADR_HOME;
	}

	public StringProperty CLI_ADR_CORPProperty() {
		return CLI_ADR_CORP;
	}

	public StringProperty CLI_ADR_KVProperty() {
		return CLI_ADR_KV;
	}

	public IntegerProperty CLI_DOC_TYPEProperty() {
		return CLI_DOC_TYPE;
	}

	public StringProperty CLI_DOC_SERIAProperty() {
		return CLI_DOC_SERIA;
	}

	public StringProperty CLI_DOC_NUMBERProperty() {
		return CLI_DOC_NUMBER;
	}

	public StringProperty CLI_DOC_AGENCYProperty() {
		return CLI_DOC_AGENCY;
	}

	public SimpleObjectProperty<LocalDate> CLI_DOC_STARTProperty() {
		return CLI_DOC_START;
	}

	public SimpleObjectProperty<LocalDate> CLI_DOC_ENDProperty() {
		return CLI_DOC_END;
	}

	public StringProperty CLI_DOC_SUBDIVProperty() {
		return CLI_DOC_SUBDIV;
	}

	public StringProperty CLI_PLACE_BIRTHProperty() {
		return CLI_PLACE_BIRTH;
	}

	public IntegerProperty CLI_BIRTH_COUNTRYProperty() {
		return CLI_BIRTH_COUNTRY;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}
}
