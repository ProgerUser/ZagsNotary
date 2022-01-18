package ru.psv.mj.prjmngm.projects.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPM_PROJECTS {
	/** Нет данных */
	private LongProperty PRJ_ID;
	/** Нет данных */
	private LongProperty PRJ_DOCID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$PRJ_STARTDATE;
	/** Нет данных */
	private StringProperty PRJ_CREUSR;
	/** Нет данных */
	private LongProperty PRJ_EMP;
	/** Нет данных */
	private LongProperty PRJ_STATUS;
	/** Нет данных */
	private StringProperty PRJ_STATUS_CHAR;
	/** Нет данных */
	private LongProperty EMP_ID;
	/** Нет данных */
	private StringProperty EMP_LASTNAME;
	/** Нет данных */
	private StringProperty EMP_FIRSTNAME;
	/** Нет данных */
	private StringProperty EMP_MIDDLENAME;
	/** Нет данных */
	private StringProperty EMP_POSITION;
	/** Нет данных */
	private StringProperty EMP_EMAIL;
	/** Нет данных */
	private LongProperty EMP_LOGIN;
	/** Нет данных */
	private StringProperty EMP_TEL;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> EMP_WORKSTART;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> EMP_WORKEND;
	/** Нет данных */
	private LongProperty EMP_BOSS;
	/** Нет данных */
	private LongProperty EMP_JBTYPE;
	/** Нет данных */
	private LongProperty DOC_ID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DOC_DATE;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DOC_END;
	/** Нет данных */
	private StringProperty DOC_USR;
	/** Нет данных */
	private StringProperty DOC_COMMENT;
	/** Нет данных */
	private StringProperty DOC_ISFAST;
	/** Нет данных */
	private StringProperty DOC_NUMBER;
	/** Нет данных */
	private LongProperty DOC_REF;
	/** Нет данных */
	private SimpleObjectProperty<LocalDateTime> TM$DOC_START;
	/** Нет данных */
	private LongProperty DOC_TP_ID;
	/** Нет данных */
	private StringProperty DOC_TP_NAME;
	/** Нет данных */
	private StringProperty ORG_NAME;
	/** Нет данных */
	private LongProperty ORG_ID;
	/** Нет данных */
	private StringProperty DOC_NAME;
	/** Нет данных */
	private StringProperty ORG_RUK;

	public VPM_PROJECTS() {
		this.PRJ_ID = new SimpleLongProperty();
		this.PRJ_DOCID = new SimpleLongProperty();
		this.TM$PRJ_STARTDATE = new SimpleObjectProperty<>();
		this.PRJ_CREUSR = new SimpleStringProperty();
		this.PRJ_EMP = new SimpleLongProperty();
		this.PRJ_STATUS = new SimpleLongProperty();
		this.PRJ_STATUS_CHAR = new SimpleStringProperty();
		this.EMP_ID = new SimpleLongProperty();
		this.EMP_LASTNAME = new SimpleStringProperty();
		this.EMP_FIRSTNAME = new SimpleStringProperty();
		this.EMP_MIDDLENAME = new SimpleStringProperty();
		this.EMP_POSITION = new SimpleStringProperty();
		this.EMP_EMAIL = new SimpleStringProperty();
		this.EMP_LOGIN = new SimpleLongProperty();
		this.EMP_TEL = new SimpleStringProperty();
		this.EMP_WORKSTART = new SimpleObjectProperty<>();
		this.EMP_WORKEND = new SimpleObjectProperty<>();
		this.EMP_BOSS = new SimpleLongProperty();
		this.EMP_JBTYPE = new SimpleLongProperty();
		this.DOC_ID = new SimpleLongProperty();
		this.DOC_DATE = new SimpleObjectProperty<>();
		this.DOC_END = new SimpleObjectProperty<>();
		this.DOC_USR = new SimpleStringProperty();
		this.DOC_COMMENT = new SimpleStringProperty();
		this.DOC_ISFAST = new SimpleStringProperty();
		this.DOC_NUMBER = new SimpleStringProperty();
		this.DOC_REF = new SimpleLongProperty();
		this.TM$DOC_START = new SimpleObjectProperty<>();
		this.DOC_TP_ID = new SimpleLongProperty();
		this.DOC_TP_NAME = new SimpleStringProperty();
		this.ORG_NAME = new SimpleStringProperty();
		this.ORG_ID = new SimpleLongProperty();
		this.DOC_NAME = new SimpleStringProperty();
		this.ORG_RUK = new SimpleStringProperty();
	}

	public void setPRJ_ID(Long PRJ_ID) {
		this.PRJ_ID.set(PRJ_ID);
	}

	public void setPRJ_DOCID(Long PRJ_DOCID) {
		this.PRJ_DOCID.set(PRJ_DOCID);
	}

	public void setTM$PRJ_STARTDATE(LocalDateTime TM$PRJ_STARTDATE) {
		this.TM$PRJ_STARTDATE.set(TM$PRJ_STARTDATE);
	}

	public void setPRJ_CREUSR(String PRJ_CREUSR) {
		this.PRJ_CREUSR.set(PRJ_CREUSR);
	}

	public void setPRJ_EMP(Long PRJ_EMP) {
		this.PRJ_EMP.set(PRJ_EMP);
	}

	public void setPRJ_STATUS(Long PRJ_STATUS) {
		this.PRJ_STATUS.set(PRJ_STATUS);
	}

	public void setPRJ_STATUS_CHAR(String PRJ_STATUS_CHAR) {
		this.PRJ_STATUS_CHAR.set(PRJ_STATUS_CHAR);
	}

	public void setEMP_ID(Long EMP_ID) {
		this.EMP_ID.set(EMP_ID);
	}

	public void setEMP_LASTNAME(String EMP_LASTNAME) {
		this.EMP_LASTNAME.set(EMP_LASTNAME);
	}

	public void setEMP_FIRSTNAME(String EMP_FIRSTNAME) {
		this.EMP_FIRSTNAME.set(EMP_FIRSTNAME);
	}

	public void setEMP_MIDDLENAME(String EMP_MIDDLENAME) {
		this.EMP_MIDDLENAME.set(EMP_MIDDLENAME);
	}

	public void setEMP_POSITION(String EMP_POSITION) {
		this.EMP_POSITION.set(EMP_POSITION);
	}

	public void setEMP_EMAIL(String EMP_EMAIL) {
		this.EMP_EMAIL.set(EMP_EMAIL);
	}

	public void setEMP_LOGIN(Long EMP_LOGIN) {
		this.EMP_LOGIN.set(EMP_LOGIN);
	}

	public void setEMP_TEL(String EMP_TEL) {
		this.EMP_TEL.set(EMP_TEL);
	}

	public void setEMP_WORKSTART(LocalDate EMP_WORKSTART) {
		this.EMP_WORKSTART.set(EMP_WORKSTART);
	}

	public void setEMP_WORKEND(LocalDate EMP_WORKEND) {
		this.EMP_WORKEND.set(EMP_WORKEND);
	}

	public void setEMP_BOSS(Long EMP_BOSS) {
		this.EMP_BOSS.set(EMP_BOSS);
	}

	public void setEMP_JBTYPE(Long EMP_JBTYPE) {
		this.EMP_JBTYPE.set(EMP_JBTYPE);
	}

	public void setDOC_ID(Long DOC_ID) {
		this.DOC_ID.set(DOC_ID);
	}

	public void setDOC_DATE(LocalDate DOC_DATE) {
		this.DOC_DATE.set(DOC_DATE);
	}

	public void setDOC_END(LocalDate DOC_END) {
		this.DOC_END.set(DOC_END);
	}

	public void setDOC_USR(String DOC_USR) {
		this.DOC_USR.set(DOC_USR);
	}

	public void setDOC_COMMENT(String DOC_COMMENT) {
		this.DOC_COMMENT.set(DOC_COMMENT);
	}

	public void setDOC_ISFAST(String DOC_ISFAST) {
		this.DOC_ISFAST.set(DOC_ISFAST);
	}

	public void setDOC_NUMBER(String DOC_NUMBER) {
		this.DOC_NUMBER.set(DOC_NUMBER);
	}

	public void setDOC_REF(Long DOC_REF) {
		this.DOC_REF.set(DOC_REF);
	}

	public void setTM$DOC_START(LocalDateTime TM$DOC_START) {
		this.TM$DOC_START.set(TM$DOC_START);
	}

	public void setDOC_TP_ID(Long DOC_TP_ID) {
		this.DOC_TP_ID.set(DOC_TP_ID);
	}

	public void setDOC_TP_NAME(String DOC_TP_NAME) {
		this.DOC_TP_NAME.set(DOC_TP_NAME);
	}

	public void setORG_NAME(String ORG_NAME) {
		this.ORG_NAME.set(ORG_NAME);
	}

	public void setORG_ID(Long ORG_ID) {
		this.ORG_ID.set(ORG_ID);
	}

	public void setDOC_NAME(String DOC_NAME) {
		this.DOC_NAME.set(DOC_NAME);
	}

	public void setORG_RUK(String ORG_RUK) {
		this.ORG_RUK.set(ORG_RUK);
	}

	public Long getPRJ_ID() {
		return PRJ_ID.get();
	}

	public Long getPRJ_DOCID() {
		return PRJ_DOCID.get();
	}

	public LocalDateTime getTM$PRJ_STARTDATE() {
		return TM$PRJ_STARTDATE.get();
	}

	public String getPRJ_CREUSR() {
		return PRJ_CREUSR.get();
	}

	public Long getPRJ_EMP() {
		return PRJ_EMP.get();
	}

	public Long getPRJ_STATUS() {
		return PRJ_STATUS.get();
	}

	public String getPRJ_STATUS_CHAR() {
		return PRJ_STATUS_CHAR.get();
	}

	public Long getEMP_ID() {
		return EMP_ID.get();
	}

	public String getEMP_LASTNAME() {
		return EMP_LASTNAME.get();
	}

	public String getEMP_FIRSTNAME() {
		return EMP_FIRSTNAME.get();
	}

	public String getEMP_MIDDLENAME() {
		return EMP_MIDDLENAME.get();
	}

	public String getEMP_POSITION() {
		return EMP_POSITION.get();
	}

	public String getEMP_EMAIL() {
		return EMP_EMAIL.get();
	}

	public Long getEMP_LOGIN() {
		return EMP_LOGIN.get();
	}

	public String getEMP_TEL() {
		return EMP_TEL.get();
	}

	public LocalDate getEMP_WORKSTART() {
		return EMP_WORKSTART.get();
	}

	public LocalDate getEMP_WORKEND() {
		return EMP_WORKEND.get();
	}

	public Long getEMP_BOSS() {
		return EMP_BOSS.get();
	}

	public Long getEMP_JBTYPE() {
		return EMP_JBTYPE.get();
	}

	public Long getDOC_ID() {
		return DOC_ID.get();
	}

	public LocalDate getDOC_DATE() {
		return DOC_DATE.get();
	}

	public LocalDate getDOC_END() {
		return DOC_END.get();
	}

	public String getDOC_USR() {
		return DOC_USR.get();
	}

	public String getDOC_COMMENT() {
		return DOC_COMMENT.get();
	}

	public String getDOC_ISFAST() {
		return DOC_ISFAST.get();
	}

	public String getDOC_NUMBER() {
		return DOC_NUMBER.get();
	}

	public Long getDOC_REF() {
		return DOC_REF.get();
	}

	public LocalDateTime getTM$DOC_START() {
		return TM$DOC_START.get();
	}

	public Long getDOC_TP_ID() {
		return DOC_TP_ID.get();
	}

	public String getDOC_TP_NAME() {
		return DOC_TP_NAME.get();
	}

	public String getORG_NAME() {
		return ORG_NAME.get();
	}

	public Long getORG_ID() {
		return ORG_ID.get();
	}

	public String getDOC_NAME() {
		return DOC_NAME.get();
	}

	public String getORG_RUK() {
		return ORG_RUK.get();
	}

	public LongProperty PRJ_IDProperty() {
		return PRJ_ID;
	}

	public LongProperty PRJ_DOCIDProperty() {
		return PRJ_DOCID;
	}

	public SimpleObjectProperty<LocalDateTime> TM$PRJ_STARTDATEProperty() {
		return TM$PRJ_STARTDATE;
	}

	public StringProperty PRJ_CREUSRProperty() {
		return PRJ_CREUSR;
	}

	public LongProperty PRJ_EMPProperty() {
		return PRJ_EMP;
	}

	public LongProperty PRJ_STATUSProperty() {
		return PRJ_STATUS;
	}

	public StringProperty PRJ_STATUS_CHARProperty() {
		return PRJ_STATUS_CHAR;
	}

	public LongProperty EMP_IDProperty() {
		return EMP_ID;
	}

	public StringProperty EMP_LASTNAMEProperty() {
		return EMP_LASTNAME;
	}

	public StringProperty EMP_FIRSTNAMEProperty() {
		return EMP_FIRSTNAME;
	}

	public StringProperty EMP_MIDDLENAMEProperty() {
		return EMP_MIDDLENAME;
	}

	public StringProperty EMP_POSITIONProperty() {
		return EMP_POSITION;
	}

	public StringProperty EMP_EMAILProperty() {
		return EMP_EMAIL;
	}

	public LongProperty EMP_LOGINProperty() {
		return EMP_LOGIN;
	}

	public StringProperty EMP_TELProperty() {
		return EMP_TEL;
	}

	public SimpleObjectProperty<LocalDate> EMP_WORKSTARTProperty() {
		return EMP_WORKSTART;
	}

	public SimpleObjectProperty<LocalDate> EMP_WORKENDProperty() {
		return EMP_WORKEND;
	}

	public LongProperty EMP_BOSSProperty() {
		return EMP_BOSS;
	}

	public LongProperty EMP_JBTYPEProperty() {
		return EMP_JBTYPE;
	}

	public LongProperty DOC_IDProperty() {
		return DOC_ID;
	}

	public SimpleObjectProperty<LocalDate> DOC_DATEProperty() {
		return DOC_DATE;
	}

	public SimpleObjectProperty<LocalDate> DOC_ENDProperty() {
		return DOC_END;
	}

	public StringProperty DOC_USRProperty() {
		return DOC_USR;
	}

	public StringProperty DOC_COMMENTProperty() {
		return DOC_COMMENT;
	}

	public StringProperty DOC_ISFASTProperty() {
		return DOC_ISFAST;
	}

	public StringProperty DOC_NUMBERProperty() {
		return DOC_NUMBER;
	}

	public LongProperty DOC_REFProperty() {
		return DOC_REF;
	}

	public SimpleObjectProperty<LocalDateTime> TM$DOC_STARTProperty() {
		return TM$DOC_START;
	}

	public LongProperty DOC_TP_IDProperty() {
		return DOC_TP_ID;
	}

	public StringProperty DOC_TP_NAMEProperty() {
		return DOC_TP_NAME;
	}

	public StringProperty ORG_NAMEProperty() {
		return ORG_NAME;
	}

	public LongProperty ORG_IDProperty() {
		return ORG_ID;
	}

	public StringProperty DOC_NAMEProperty() {
		return DOC_NAME;
	}

	public StringProperty ORG_RUKProperty() {
		return ORG_RUK;
	}
}
