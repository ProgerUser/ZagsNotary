package mj.users;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class USR {
	/** ID ЗАГСА */
	private IntegerProperty ZAGS_ID;
	/** Начало рабочего времени */
	private SimpleObjectProperty<LocalDate> WORKDAY_TIME_BEGIN;
	/** Окончание рабочего времени */
	private SimpleObjectProperty<LocalDate> WORKDAY_TIME_END;
	/** Сокращенная должность */
	private StringProperty SHORT_POSITION;
	/** Пользователь должен сменить пароль при входе (Y/N) */
	private StringProperty MUST_CHANGE_PASSWORD;
	/** Информация блокировки */
	private StringProperty LOCK_INFO;
	/** Дата/время блокировки */
	private SimpleObjectProperty<LocalDate> LOCK_DATE_TIME;
	/** Сокращенное ФИО */
	private StringProperty SHORT_NAME;
	/** Приветственное сообщение */
	private StringProperty WELCOME_MESSAGE;
	/** Минимальное количество специальных символов в пароле */
	private IntegerProperty IUSRSPEC_QUANTITY;
	/** Использовать старый пароль после N новых */
	private IntegerProperty IUSRPWDREUSE;
	/** Y если пользователь может открывать сессии только с одного терминала */
	private StringProperty CRESTRICT_TERM;
	/** Адрес электронной почты */
	private StringProperty CEMAIL;
	/** Окончание разрешения коннекта */
	private SimpleObjectProperty<LocalDate> TWRTEND;
	/** Начало разрешения коннекта */
	private SimpleObjectProperty<LocalDate> TWRTSTART;
	/** Номер внутреннего телефона */
	private StringProperty CUSROFFPHONE;
	/** Срок действия пароля в днях */
	private IntegerProperty IUSREXP_DAYS;
	/** Минимальное количество цифровых символов в пароле */
	private IntegerProperty IUSRNUM_QUANTITY;
	/** Минимальное количество буквенных символов в пароле */
	private IntegerProperty IUSRCHR_QUANTITY;
	/** Минимальная длина пароля */
	private IntegerProperty IUSRPWD_LENGTH;
	/** Дата увольнения */
	private SimpleObjectProperty<LocalDate> DUSRFIRE;
	/** Филиал ( ссылка на iOTDnum ) */
	private IntegerProperty IUSRBRANCH;
	/** Дата поступления на работу */
	private SimpleObjectProperty<LocalDate> DUSRHIRE;
	/** Должность */
	private StringProperty CUSRPOSITION;
	/** ФИО */
	private StringProperty CUSRNAME;
	/** LogName */
	private StringProperty CUSRLOGNAME;
	/** Идентификатор пользователя */
	private IntegerProperty IUSRID;
	/** Учреждение, NOT, ZAG,ADM */
	private StringProperty ACCESS_LEVEL;
	/** ID Нотариуса */
	private IntegerProperty NOTARY_ID;

	public USR() {
		this.ZAGS_ID = new SimpleIntegerProperty();
		this.WORKDAY_TIME_BEGIN = new SimpleObjectProperty<>();
		this.WORKDAY_TIME_END = new SimpleObjectProperty<>();
		this.SHORT_POSITION = new SimpleStringProperty();
		this.MUST_CHANGE_PASSWORD = new SimpleStringProperty();
		this.LOCK_INFO = new SimpleStringProperty();
		this.LOCK_DATE_TIME = new SimpleObjectProperty<>();
		this.SHORT_NAME = new SimpleStringProperty();
		this.WELCOME_MESSAGE = new SimpleStringProperty();
		this.IUSRSPEC_QUANTITY = new SimpleIntegerProperty();
		this.IUSRPWDREUSE = new SimpleIntegerProperty();
		this.CRESTRICT_TERM = new SimpleStringProperty();
		this.CEMAIL = new SimpleStringProperty();
		this.TWRTEND = new SimpleObjectProperty<>();
		this.TWRTSTART = new SimpleObjectProperty<>();
		this.CUSROFFPHONE = new SimpleStringProperty();
		this.IUSREXP_DAYS = new SimpleIntegerProperty();
		this.IUSRNUM_QUANTITY = new SimpleIntegerProperty();
		this.IUSRCHR_QUANTITY = new SimpleIntegerProperty();
		this.IUSRPWD_LENGTH = new SimpleIntegerProperty();
		this.DUSRFIRE = new SimpleObjectProperty<>();
		this.IUSRBRANCH = new SimpleIntegerProperty();
		this.DUSRHIRE = new SimpleObjectProperty<>();
		this.CUSRPOSITION = new SimpleStringProperty();
		this.CUSRNAME = new SimpleStringProperty();
		this.CUSRLOGNAME = new SimpleStringProperty();
		this.IUSRID = new SimpleIntegerProperty();
		this.ACCESS_LEVEL = new SimpleStringProperty();
		this.NOTARY_ID = new SimpleIntegerProperty();
	}

	public void setZAGS_ID(Integer ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setWORKDAY_TIME_BEGIN(LocalDate WORKDAY_TIME_BEGIN) {
		this.WORKDAY_TIME_BEGIN.set(WORKDAY_TIME_BEGIN);
	}

	public void setWORKDAY_TIME_END(LocalDate WORKDAY_TIME_END) {
		this.WORKDAY_TIME_END.set(WORKDAY_TIME_END);
	}

	public void setSHORT_POSITION(String SHORT_POSITION) {
		this.SHORT_POSITION.set(SHORT_POSITION);
	}

	public void setMUST_CHANGE_PASSWORD(String MUST_CHANGE_PASSWORD) {
		this.MUST_CHANGE_PASSWORD.set(MUST_CHANGE_PASSWORD);
	}

	public void setLOCK_INFO(String LOCK_INFO) {
		this.LOCK_INFO.set(LOCK_INFO);
	}

	public void setLOCK_DATE_TIME(LocalDate LOCK_DATE_TIME) {
		this.LOCK_DATE_TIME.set(LOCK_DATE_TIME);
	}

	public void setSHORT_NAME(String SHORT_NAME) {
		this.SHORT_NAME.set(SHORT_NAME);
	}

	public void setWELCOME_MESSAGE(String WELCOME_MESSAGE) {
		this.WELCOME_MESSAGE.set(WELCOME_MESSAGE);
	}

	public void setIUSRSPEC_QUANTITY(Integer IUSRSPEC_QUANTITY) {
		this.IUSRSPEC_QUANTITY.set(IUSRSPEC_QUANTITY);
	}

	public void setIUSRPWDREUSE(Integer IUSRPWDREUSE) {
		this.IUSRPWDREUSE.set(IUSRPWDREUSE);
	}

	public void setCRESTRICT_TERM(String CRESTRICT_TERM) {
		this.CRESTRICT_TERM.set(CRESTRICT_TERM);
	}

	public void setCEMAIL(String CEMAIL) {
		this.CEMAIL.set(CEMAIL);
	}

	public void setTWRTEND(LocalDate TWRTEND) {
		this.TWRTEND.set(TWRTEND);
	}

	public void setTWRTSTART(LocalDate TWRTSTART) {
		this.TWRTSTART.set(TWRTSTART);
	}

	public void setCUSROFFPHONE(String CUSROFFPHONE) {
		this.CUSROFFPHONE.set(CUSROFFPHONE);
	}

	public void setIUSREXP_DAYS(Integer IUSREXP_DAYS) {
		this.IUSREXP_DAYS.set(IUSREXP_DAYS);
	}

	public void setIUSRNUM_QUANTITY(Integer IUSRNUM_QUANTITY) {
		this.IUSRNUM_QUANTITY.set(IUSRNUM_QUANTITY);
	}

	public void setIUSRCHR_QUANTITY(Integer IUSRCHR_QUANTITY) {
		this.IUSRCHR_QUANTITY.set(IUSRCHR_QUANTITY);
	}

	public void setIUSRPWD_LENGTH(Integer IUSRPWD_LENGTH) {
		this.IUSRPWD_LENGTH.set(IUSRPWD_LENGTH);
	}

	public void setDUSRFIRE(LocalDate DUSRFIRE) {
		this.DUSRFIRE.set(DUSRFIRE);
	}

	public void setIUSRBRANCH(Integer IUSRBRANCH) {
		this.IUSRBRANCH.set(IUSRBRANCH);
	}

	public void setDUSRHIRE(LocalDate DUSRHIRE) {
		this.DUSRHIRE.set(DUSRHIRE);
	}

	public void setCUSRPOSITION(String CUSRPOSITION) {
		this.CUSRPOSITION.set(CUSRPOSITION);
	}

	public void setCUSRNAME(String CUSRNAME) {
		this.CUSRNAME.set(CUSRNAME);
	}

	public void setCUSRLOGNAME(String CUSRLOGNAME) {
		this.CUSRLOGNAME.set(CUSRLOGNAME);
	}

	public void setIUSRID(Integer IUSRID) {
		this.IUSRID.set(IUSRID);
	}

	public void setACCESS_LEVEL(String ACCESS_LEVEL) {
		this.ACCESS_LEVEL.set(ACCESS_LEVEL);
	}

	public void setNOTARY_ID(Integer NOTARY_ID) {
		this.NOTARY_ID.set(NOTARY_ID);
	}

	public Integer getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public LocalDate getWORKDAY_TIME_BEGIN() {
		return WORKDAY_TIME_BEGIN.get();
	}

	public LocalDate getWORKDAY_TIME_END() {
		return WORKDAY_TIME_END.get();
	}

	public String getSHORT_POSITION() {
		return SHORT_POSITION.get();
	}

	public String getMUST_CHANGE_PASSWORD() {
		return MUST_CHANGE_PASSWORD.get();
	}

	public String getLOCK_INFO() {
		return LOCK_INFO.get();
	}

	public LocalDate getLOCK_DATE_TIME() {
		return LOCK_DATE_TIME.get();
	}

	public String getSHORT_NAME() {
		return SHORT_NAME.get();
	}

	public String getWELCOME_MESSAGE() {
		return WELCOME_MESSAGE.get();
	}

	public Integer getIUSRSPEC_QUANTITY() {
		return IUSRSPEC_QUANTITY.get();
	}

	public Integer getIUSRPWDREUSE() {
		return IUSRPWDREUSE.get();
	}

	public String getCRESTRICT_TERM() {
		return CRESTRICT_TERM.get();
	}

	public String getCEMAIL() {
		return CEMAIL.get();
	}

	public LocalDate getTWRTEND() {
		return TWRTEND.get();
	}

	public LocalDate getTWRTSTART() {
		return TWRTSTART.get();
	}

	public String getCUSROFFPHONE() {
		return CUSROFFPHONE.get();
	}

	public Integer getIUSREXP_DAYS() {
		return IUSREXP_DAYS.get();
	}

	public Integer getIUSRNUM_QUANTITY() {
		return IUSRNUM_QUANTITY.get();
	}

	public Integer getIUSRCHR_QUANTITY() {
		return IUSRCHR_QUANTITY.get();
	}

	public Integer getIUSRPWD_LENGTH() {
		return IUSRPWD_LENGTH.get();
	}

	public LocalDate getDUSRFIRE() {
		return DUSRFIRE.get();
	}

	public Integer getIUSRBRANCH() {
		return IUSRBRANCH.get();
	}

	public LocalDate getDUSRHIRE() {
		return DUSRHIRE.get();
	}

	public String getCUSRPOSITION() {
		return CUSRPOSITION.get();
	}

	public String getCUSRNAME() {
		return CUSRNAME.get();
	}

	public String getCUSRLOGNAME() {
		return CUSRLOGNAME.get();
	}

	public Integer getIUSRID() {
		return IUSRID.get();
	}

	public String getACCESS_LEVEL() {
		return ACCESS_LEVEL.get();
	}

	public Integer getNOTARY_ID() {
		return NOTARY_ID.get();
	}

	public IntegerProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public SimpleObjectProperty<LocalDate> WORKDAY_TIME_BEGINProperty() {
		return WORKDAY_TIME_BEGIN;
	}

	public SimpleObjectProperty<LocalDate> WORKDAY_TIME_ENDProperty() {
		return WORKDAY_TIME_END;
	}

	public StringProperty SHORT_POSITIONProperty() {
		return SHORT_POSITION;
	}

	public StringProperty MUST_CHANGE_PASSWORDProperty() {
		return MUST_CHANGE_PASSWORD;
	}

	public StringProperty LOCK_INFOProperty() {
		return LOCK_INFO;
	}

	public SimpleObjectProperty<LocalDate> LOCK_DATE_TIMEProperty() {
		return LOCK_DATE_TIME;
	}

	public StringProperty SHORT_NAMEProperty() {
		return SHORT_NAME;
	}

	public StringProperty WELCOME_MESSAGEProperty() {
		return WELCOME_MESSAGE;
	}

	public IntegerProperty IUSRSPEC_QUANTITYProperty() {
		return IUSRSPEC_QUANTITY;
	}

	public IntegerProperty IUSRPWDREUSEProperty() {
		return IUSRPWDREUSE;
	}

	public StringProperty CRESTRICT_TERMProperty() {
		return CRESTRICT_TERM;
	}

	public StringProperty CEMAILProperty() {
		return CEMAIL;
	}

	public SimpleObjectProperty<LocalDate> TWRTENDProperty() {
		return TWRTEND;
	}

	public SimpleObjectProperty<LocalDate> TWRTSTARTProperty() {
		return TWRTSTART;
	}

	public StringProperty CUSROFFPHONEProperty() {
		return CUSROFFPHONE;
	}

	public IntegerProperty IUSREXP_DAYSProperty() {
		return IUSREXP_DAYS;
	}

	public IntegerProperty IUSRNUM_QUANTITYProperty() {
		return IUSRNUM_QUANTITY;
	}

	public IntegerProperty IUSRCHR_QUANTITYProperty() {
		return IUSRCHR_QUANTITY;
	}

	public IntegerProperty IUSRPWD_LENGTHProperty() {
		return IUSRPWD_LENGTH;
	}

	public SimpleObjectProperty<LocalDate> DUSRFIREProperty() {
		return DUSRFIRE;
	}

	public IntegerProperty IUSRBRANCHProperty() {
		return IUSRBRANCH;
	}

	public SimpleObjectProperty<LocalDate> DUSRHIREProperty() {
		return DUSRHIRE;
	}

	public StringProperty CUSRPOSITIONProperty() {
		return CUSRPOSITION;
	}

	public StringProperty CUSRNAMEProperty() {
		return CUSRNAME;
	}

	public StringProperty CUSRLOGNAMEProperty() {
		return CUSRLOGNAME;
	}

	public IntegerProperty IUSRIDProperty() {
		return IUSRID;
	}

	public StringProperty ACCESS_LEVELProperty() {
		return ACCESS_LEVEL;
	}

	public IntegerProperty NOTARY_IDProperty() {
		return NOTARY_ID;
	}
}
