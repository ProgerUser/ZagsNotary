package ru.psv.mj.prjmngm.inboxdocs.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPM_DOCS {
	/** ��� ������ */
	private LongProperty DOC_ID;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> DOC_DATE;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> DOC_END;
	/** ��� ������ */
	private StringProperty DOC_USR;
	/** ��� ������ */
	private StringProperty DOC_COMMENT;
	/** ��� ������ */
	private StringProperty DOC_ISFAST;
	/** ��� ������ */
	private StringProperty DOC_NUMBER;
	/** ��� ������ */
	private LongProperty DOC_REF;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDateTime> TM$DOC_START;
	/** ��� ������ */
	private LongProperty DOC_TP_ID;
	/** ��� ������ */
	private StringProperty DOC_TP_NAME;
	/** ��� ������ */
	private StringProperty ORG_NAME;
	/** ��� ������ */
	private LongProperty ORG_ID;
	/** ��� ������ */
	private StringProperty DOC_NAME;
	/** ��� ������ */
	private SimpleObjectProperty<LocalDate> DOC_ISH_DATE;
	/** ��� ������ */
	private StringProperty DOC_ISFH_NUMBER;

	public VPM_DOCS() {
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
		this.DOC_ISH_DATE = new SimpleObjectProperty<>();
		this.DOC_ISFH_NUMBER = new SimpleStringProperty();
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

	public void setDOC_ISH_DATE(LocalDate DOC_ISH_DATE) {
		this.DOC_ISH_DATE.set(DOC_ISH_DATE);
	}

	public void setDOC_ISFH_NUMBER(String DOC_ISFH_NUMBER) {
		this.DOC_ISFH_NUMBER.set(DOC_ISFH_NUMBER);
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

	public LocalDate getDOC_ISH_DATE() {
		return DOC_ISH_DATE.get();
	}

	public String getDOC_ISFH_NUMBER() {
		return DOC_ISFH_NUMBER.get();
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

	public SimpleObjectProperty<LocalDate> DOC_ISH_DATEProperty() {
		return DOC_ISH_DATE;
	}

	public StringProperty DOC_ISFH_NUMBERProperty() {
		return DOC_ISFH_NUMBER;
	}
}
