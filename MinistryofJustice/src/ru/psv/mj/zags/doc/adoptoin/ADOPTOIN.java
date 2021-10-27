package ru.psv.mj.zags.doc.adoptoin;

import java.time.LocalDate;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ADOPTOIN {
	/** Нет данных */
	private StringProperty BRN_OBL_RESP;
	/** Нет данных */
	private StringProperty BRN_AREA;
	/** Нет данных */
	private StringProperty BRN_CITY;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> OLD_BRTH;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> NEW_BRTH;
	/** Нет данных */
	private StringProperty ZAP_NUMBER;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> ZAP_DATE;
	/** Нет данных */
	private StringProperty ZAP_SOVET_DEP_TRUD;
	/** Нет данных */
	private StringProperty ZAP_ISPOLKOM_RESH;
	/** Нет данных */
	private StringProperty ADOPT_PARENTS;
	/** Нет данных */
	private LongProperty CUSID_F_AD;
	/** Нет данных */
	private LongProperty CUSID_M_AD;
	/** Нет данных */
	private StringProperty SVID_NOMER;
	/** Нет данных */
	private StringProperty SVID_SERIA;
	/** Нет данных */
	private LongProperty BRNACT;
	/** Нет данных */
	private LongProperty CUSID_F;
	/** Нет данных */
	private LongProperty CUSID_M;
	/** Нет данных */
	private StringProperty OPER;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DOC_DATE;
	/** Нет данных */
	private LongProperty CUSID_CH;
	/** Нет данных */
	private StringProperty NEW_MIDDLNAME;
	/** Нет данных */
	private StringProperty NEW_FIRSTNAME;
	/** Нет данных */
	private StringProperty NEW_LASTNAME;
	/** Нет данных */
	private StringProperty OLD_MIDDLNAME;
	/** Нет данных */
	private StringProperty OLD_FIRSTNAME;
	/** Нет данных */
	private StringProperty OLD_LASTNAME;
	/** Нет данных */
	private LongProperty ZAGS_ID;
	/** Нет данных */
	private LongProperty ID;
	/** Нет данных */
	private StringProperty ZAGS_NAME;
	/** Нет данных */
	private StringProperty ADFATHERFIO;
	/** Нет данных */
	private StringProperty ADMOTHERFIO;
	/** Нет данных */
	private StringProperty CHILDRENFIO;
	/** Нет данных */
	private StringProperty MOTHERFIO;
	/** Нет данных */
	private StringProperty FATHERFIO;
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Номер документа */
	private StringProperty DOC_NUMBER;
	/** Фамилия до перемены АБХ */
	private StringProperty OLD_LASTNAME_AB;
	/** Имя до перемены АБХ */
	private StringProperty OLD_FIRSTNAME_AB;
	/** Отчество до перемены АБХ */
	private StringProperty OLD_MIDDLNAME_AB;
	/** Фамилия после перемены АБХ */
	private StringProperty NEW_LASTNAME_AB;
	/** Имя после перемены АБХ */
	private StringProperty NEW_FIRSTNAME_AB;
	/** Отчество после перемены АБХ */
	private StringProperty NEW_MIDDLNAME_AB;

	/** Основание записи об усыновлении */
	private StringProperty GR_ADOPT;
	/** Решение суда дата */
	private SimpleObjectProperty<LocalDate> GR_COURT_DATE;
	/** Решение суда ссылка на суд */
	private LongProperty GR_COURT;

	public ADOPTOIN() {

		this.GR_ADOPT = new SimpleStringProperty();
		this.GR_COURT_DATE = new SimpleObjectProperty<>();
		this.GR_COURT = new SimpleLongProperty();

		this.OLD_LASTNAME_AB = new SimpleStringProperty();
		this.OLD_FIRSTNAME_AB = new SimpleStringProperty();
		this.OLD_MIDDLNAME_AB = new SimpleStringProperty();
		this.NEW_LASTNAME_AB = new SimpleStringProperty();
		this.NEW_FIRSTNAME_AB = new SimpleStringProperty();
		this.NEW_MIDDLNAME_AB = new SimpleStringProperty();

		this.DOC_NUMBER = new SimpleStringProperty();
		this.BRN_OBL_RESP = new SimpleStringProperty();
		this.BRN_AREA = new SimpleStringProperty();
		this.BRN_CITY = new SimpleStringProperty();
		this.OLD_BRTH = new SimpleObjectProperty<>();
		this.NEW_BRTH = new SimpleObjectProperty<>();
		this.ZAP_NUMBER = new SimpleStringProperty();
		this.ZAP_DATE = new SimpleObjectProperty<>();
		this.ZAP_SOVET_DEP_TRUD = new SimpleStringProperty();
		this.ZAP_ISPOLKOM_RESH = new SimpleStringProperty();
		this.ADOPT_PARENTS = new SimpleStringProperty();
		this.CUSID_F_AD = new SimpleLongProperty();
		this.CUSID_M_AD = new SimpleLongProperty();
		this.SVID_NOMER = new SimpleStringProperty();
		this.SVID_SERIA = new SimpleStringProperty();
		this.BRNACT = new SimpleLongProperty();
		this.CUSID_F = new SimpleLongProperty();
		this.CUSID_M = new SimpleLongProperty();
		this.OPER = new SimpleStringProperty();
		this.DOC_DATE = new SimpleObjectProperty<>();
		this.CUSID_CH = new SimpleLongProperty();
		this.NEW_MIDDLNAME = new SimpleStringProperty();
		this.NEW_FIRSTNAME = new SimpleStringProperty();
		this.NEW_LASTNAME = new SimpleStringProperty();
		this.OLD_MIDDLNAME = new SimpleStringProperty();
		this.OLD_FIRSTNAME = new SimpleStringProperty();
		this.OLD_LASTNAME = new SimpleStringProperty();
		this.ZAGS_ID = new SimpleLongProperty();
		this.ID = new SimpleLongProperty();
		this.ZAGS_NAME = new SimpleStringProperty();
		this.ADFATHERFIO = new SimpleStringProperty();
		this.ADMOTHERFIO = new SimpleStringProperty();
		this.CHILDRENFIO = new SimpleStringProperty();
		this.MOTHERFIO = new SimpleStringProperty();
		this.FATHERFIO = new SimpleStringProperty();
		this.CR_TIME = new SimpleStringProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
	}

	public void setGR_ADOPT(String GR_ADOPT) {
		this.GR_ADOPT.set(GR_ADOPT);
	}

	public void setGR_COURT_DATE(LocalDate GR_COURT_DATE) {
		this.GR_COURT_DATE.set(GR_COURT_DATE);
	}

	public void setGR_COURT(Long GR_COURT) {
		this.GR_COURT.set(GR_COURT);
	}

	public String getGR_ADOPT() {
		return GR_ADOPT.get();
	}

	public LocalDate getGR_COURT_DATE() {
		return GR_COURT_DATE.get();
	}

	public Long getGR_COURT() {
		return GR_COURT.get();
	}

	public StringProperty GR_ADOPTProperty() {
		return GR_ADOPT;
	}

	public SimpleObjectProperty<LocalDate> GR_COURT_DATEProperty() {
		return GR_COURT_DATE;
	}

	public LongProperty GR_COURTProperty() {
		return GR_COURT;
	}

	public void setOLD_LASTNAME_AB(String OLD_LASTNAME_AB) {
		this.OLD_LASTNAME_AB.set(OLD_LASTNAME_AB);
	}

	public void setOLD_FIRSTNAME_AB(String OLD_FIRSTNAME_AB) {
		this.OLD_FIRSTNAME_AB.set(OLD_FIRSTNAME_AB);
	}

	public void setOLD_MIDDLNAME_AB(String OLD_MIDDLNAME_AB) {
		this.OLD_MIDDLNAME_AB.set(OLD_MIDDLNAME_AB);
	}

	public void setNEW_LASTNAME_AB(String NEW_LASTNAME_AB) {
		this.NEW_LASTNAME_AB.set(NEW_LASTNAME_AB);
	}

	public void setNEW_FIRSTNAME_AB(String NEW_FIRSTNAME_AB) {
		this.NEW_FIRSTNAME_AB.set(NEW_FIRSTNAME_AB);
	}

	public void setNEW_MIDDLNAME_AB(String NEW_MIDDLNAME_AB) {
		this.NEW_MIDDLNAME_AB.set(NEW_MIDDLNAME_AB);
	}

	public String getOLD_LASTNAME_AB() {
		return OLD_LASTNAME_AB.get();
	}

	public String getOLD_FIRSTNAME_AB() {
		return OLD_FIRSTNAME_AB.get();
	}

	public String getOLD_MIDDLNAME_AB() {
		return OLD_MIDDLNAME_AB.get();
	}

	public String getNEW_LASTNAME_AB() {
		return NEW_LASTNAME_AB.get();
	}

	public String getNEW_FIRSTNAME_AB() {
		return NEW_FIRSTNAME_AB.get();
	}

	public String getNEW_MIDDLNAME_AB() {
		return NEW_MIDDLNAME_AB.get();
	}

	public StringProperty OLD_LASTNAME_ABProperty() {
		return OLD_LASTNAME_AB;
	}

	public StringProperty OLD_FIRSTNAME_ABProperty() {
		return OLD_FIRSTNAME_AB;
	}

	public StringProperty OLD_MIDDLNAME_ABProperty() {
		return OLD_MIDDLNAME_AB;
	}

	public StringProperty NEW_LASTNAME_ABProperty() {
		return NEW_LASTNAME_AB;
	}

	public StringProperty NEW_FIRSTNAME_ABProperty() {
		return NEW_FIRSTNAME_AB;
	}

	public StringProperty NEW_MIDDLNAME_ABProperty() {
		return NEW_MIDDLNAME_AB;
	}

	public void setDOC_NUMBER(String DOC_NUMBER) {
		this.DOC_NUMBER.set(DOC_NUMBER);
	}

	public String getDOC_NUMBER() {
		return DOC_NUMBER.get();
	}

	public StringProperty DOC_NUMBERProperty() {
		return DOC_NUMBER;
	}

	public void setBRN_OBL_RESP(String BRN_OBL_RESP) {
		this.BRN_OBL_RESP.set(BRN_OBL_RESP);
	}

	public void setBRN_AREA(String BRN_AREA) {
		this.BRN_AREA.set(BRN_AREA);
	}

	public void setBRN_CITY(String BRN_CITY) {
		this.BRN_CITY.set(BRN_CITY);
	}

	public void setOLD_BRTH(LocalDate OLD_BRTH) {
		this.OLD_BRTH.set(OLD_BRTH);
	}

	public void setNEW_BRTH(LocalDate NEW_BRTH) {
		this.NEW_BRTH.set(NEW_BRTH);
	}

	public void setZAP_NUMBER(String ZAP_NUMBER) {
		this.ZAP_NUMBER.set(ZAP_NUMBER);
	}

	public void setZAP_DATE(LocalDate ZAP_DATE) {
		this.ZAP_DATE.set(ZAP_DATE);
	}

	public void setZAP_SOVET_DEP_TRUD(String ZAP_SOVET_DEP_TRUD) {
		this.ZAP_SOVET_DEP_TRUD.set(ZAP_SOVET_DEP_TRUD);
	}

	public void setZAP_ISPOLKOM_RESH(String ZAP_ISPOLKOM_RESH) {
		this.ZAP_ISPOLKOM_RESH.set(ZAP_ISPOLKOM_RESH);
	}

	public void setADOPT_PARENTS(String ADOPT_PARENTS) {
		this.ADOPT_PARENTS.set(ADOPT_PARENTS);
	}

	public void setCUSID_F_AD(Long CUSID_F_AD) {
		this.CUSID_F_AD.set(CUSID_F_AD);
	}

	public void setCUSID_M_AD(Long CUSID_M_AD) {
		this.CUSID_M_AD.set(CUSID_M_AD);
	}

	public void setSVID_NOMER(String SVID_NOMER) {
		this.SVID_NOMER.set(SVID_NOMER);
	}

	public void setSVID_SERIA(String SVID_SERIA) {
		this.SVID_SERIA.set(SVID_SERIA);
	}

	public void setBRNACT(Long BRNACT) {
		this.BRNACT.set(BRNACT);
	}

	public void setCUSID_F(Long CUSID_F) {
		this.CUSID_F.set(CUSID_F);
	}

	public void setCUSID_M(Long CUSID_M) {
		this.CUSID_M.set(CUSID_M);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setDOC_DATE(LocalDate DOC_DATE) {
		this.DOC_DATE.set(DOC_DATE);
	}

	public void setCUSID_CH(Long CUSID_CH) {
		this.CUSID_CH.set(CUSID_CH);
	}

	public void setNEW_MIDDLNAME(String NEW_MIDDLNAME) {
		this.NEW_MIDDLNAME.set(NEW_MIDDLNAME);
	}

	public void setNEW_FIRSTNAME(String NEW_FIRSTNAME) {
		this.NEW_FIRSTNAME.set(NEW_FIRSTNAME);
	}

	public void setNEW_LASTNAME(String NEW_LASTNAME) {
		this.NEW_LASTNAME.set(NEW_LASTNAME);
	}

	public void setOLD_MIDDLNAME(String OLD_MIDDLNAME) {
		this.OLD_MIDDLNAME.set(OLD_MIDDLNAME);
	}

	public void setOLD_FIRSTNAME(String OLD_FIRSTNAME) {
		this.OLD_FIRSTNAME.set(OLD_FIRSTNAME);
	}

	public void setOLD_LASTNAME(String OLD_LASTNAME) {
		this.OLD_LASTNAME.set(OLD_LASTNAME);
	}

	public void setZAGS_ID(Long ZAGS_ID) {
		this.ZAGS_ID.set(ZAGS_ID);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setZAGS_NAME(String ZAGS_NAME) {
		this.ZAGS_NAME.set(ZAGS_NAME);
	}

	public void setADFATHERFIO(String ADFATHERFIO) {
		this.ADFATHERFIO.set(ADFATHERFIO);
	}

	public void setADMOTHERFIO(String ADMOTHERFIO) {
		this.ADMOTHERFIO.set(ADMOTHERFIO);
	}

	public void setCHILDRENFIO(String CHILDRENFIO) {
		this.CHILDRENFIO.set(CHILDRENFIO);
	}

	public void setMOTHERFIO(String MOTHERFIO) {
		this.MOTHERFIO.set(MOTHERFIO);
	}

	public void setFATHERFIO(String FATHERFIO) {
		this.FATHERFIO.set(FATHERFIO);
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public String getBRN_OBL_RESP() {
		return BRN_OBL_RESP.get();
	}

	public String getBRN_AREA() {
		return BRN_AREA.get();
	}

	public String getBRN_CITY() {
		return BRN_CITY.get();
	}

	public LocalDate getOLD_BRTH() {
		return OLD_BRTH.get();
	}

	public LocalDate getNEW_BRTH() {
		return NEW_BRTH.get();
	}

	public String getZAP_NUMBER() {
		return ZAP_NUMBER.get();
	}

	public LocalDate getZAP_DATE() {
		return ZAP_DATE.get();
	}

	public String getZAP_SOVET_DEP_TRUD() {
		return ZAP_SOVET_DEP_TRUD.get();
	}

	public String getZAP_ISPOLKOM_RESH() {
		return ZAP_ISPOLKOM_RESH.get();
	}

	public String getADOPT_PARENTS() {
		return ADOPT_PARENTS.get();
	}

	public Long getCUSID_F_AD() {
		return CUSID_F_AD.get();
	}

	public Long getCUSID_M_AD() {
		return CUSID_M_AD.get();
	}

	public String getSVID_NOMER() {
		return SVID_NOMER.get();
	}

	public String getSVID_SERIA() {
		return SVID_SERIA.get();
	}

	public Long getBRNACT() {
		return BRNACT.get();
	}

	public Long getCUSID_F() {
		return CUSID_F.get();
	}

	public Long getCUSID_M() {
		return CUSID_M.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public LocalDate getDOC_DATE() {
		return DOC_DATE.get();
	}

	public Long getCUSID_CH() {
		return CUSID_CH.get();
	}

	public String getNEW_MIDDLNAME() {
		return NEW_MIDDLNAME.get();
	}

	public String getNEW_FIRSTNAME() {
		return NEW_FIRSTNAME.get();
	}

	public String getNEW_LASTNAME() {
		return NEW_LASTNAME.get();
	}

	public String getOLD_MIDDLNAME() {
		return OLD_MIDDLNAME.get();
	}

	public String getOLD_FIRSTNAME() {
		return OLD_FIRSTNAME.get();
	}

	public String getOLD_LASTNAME() {
		return OLD_LASTNAME.get();
	}

	public Long getZAGS_ID() {
		return ZAGS_ID.get();
	}

	public Long getID() {
		return ID.get();
	}

	public String getZAGS_NAME() {
		return ZAGS_NAME.get();
	}

	public String getADFATHERFIO() {
		return ADFATHERFIO.get();
	}

	public String getADMOTHERFIO() {
		return ADMOTHERFIO.get();
	}

	public String getCHILDRENFIO() {
		return CHILDRENFIO.get();
	}

	public String getMOTHERFIO() {
		return MOTHERFIO.get();
	}

	public String getFATHERFIO() {
		return FATHERFIO.get();
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public StringProperty BRN_OBL_RESPProperty() {
		return BRN_OBL_RESP;
	}

	public StringProperty BRN_AREAProperty() {
		return BRN_AREA;
	}

	public StringProperty BRN_CITYProperty() {
		return BRN_CITY;
	}

	public SimpleObjectProperty<LocalDate> OLD_BRTHProperty() {
		return OLD_BRTH;
	}

	public SimpleObjectProperty<LocalDate> NEW_BRTHProperty() {
		return NEW_BRTH;
	}

	public StringProperty ZAP_NUMBERProperty() {
		return ZAP_NUMBER;
	}

	public SimpleObjectProperty<LocalDate> ZAP_DATEProperty() {
		return ZAP_DATE;
	}

	public StringProperty ZAP_SOVET_DEP_TRUDProperty() {
		return ZAP_SOVET_DEP_TRUD;
	}

	public StringProperty ZAP_ISPOLKOM_RESHProperty() {
		return ZAP_ISPOLKOM_RESH;
	}

	public StringProperty ADOPT_PARENTSProperty() {
		return ADOPT_PARENTS;
	}

	public LongProperty CUSID_F_ADProperty() {
		return CUSID_F_AD;
	}

	public LongProperty CUSID_M_ADProperty() {
		return CUSID_M_AD;
	}

	public StringProperty SVID_NOMERProperty() {
		return SVID_NOMER;
	}

	public StringProperty SVID_SERIAProperty() {
		return SVID_SERIA;
	}

	public LongProperty BRNACTProperty() {
		return BRNACT;
	}

	public LongProperty CUSID_FProperty() {
		return CUSID_F;
	}

	public LongProperty CUSID_MProperty() {
		return CUSID_M;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public SimpleObjectProperty<LocalDate> DOC_DATEProperty() {
		return DOC_DATE;
	}

	public LongProperty CUSID_CHProperty() {
		return CUSID_CH;
	}

	public StringProperty NEW_MIDDLNAMEProperty() {
		return NEW_MIDDLNAME;
	}

	public StringProperty NEW_FIRSTNAMEProperty() {
		return NEW_FIRSTNAME;
	}

	public StringProperty NEW_LASTNAMEProperty() {
		return NEW_LASTNAME;
	}

	public StringProperty OLD_MIDDLNAMEProperty() {
		return OLD_MIDDLNAME;
	}

	public StringProperty OLD_FIRSTNAMEProperty() {
		return OLD_FIRSTNAME;
	}

	public StringProperty OLD_LASTNAMEProperty() {
		return OLD_LASTNAME;
	}

	public LongProperty ZAGS_IDProperty() {
		return ZAGS_ID;
	}

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty ZAGS_NAMEProperty() {
		return ZAGS_NAME;
	}

	public StringProperty ADFATHERFIOProperty() {
		return ADFATHERFIO;
	}

	public StringProperty ADMOTHERFIOProperty() {
		return ADMOTHERFIO;
	}

	public StringProperty CHILDRENFIOProperty() {
		return CHILDRENFIO;
	}

	public StringProperty MOTHERFIOProperty() {
		return MOTHERFIO;
	}

	public StringProperty FATHERFIOProperty() {
		return FATHERFIO;
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}
}
