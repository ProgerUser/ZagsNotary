package ru.psv.mj.notary.template.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_TEMP_LIST_PARAM {
	/** Запрос для параметров */
	private StringProperty PRM_FOR_PRM_SQL;
	/** ИД */
	private LongProperty PRM_ID;
	/** Название */
	private StringProperty PRM_NAME;
	/** Ссылка на шаблоны */
	private LongProperty PRM_TMP_ID;
	/** Запрос */
	private StringProperty PRM_SQL;
	/** Тип параметра */
	private LongProperty PRM_TYPE;
	/** Ссылка на таблицу */
	private StringProperty PRM_TBL_REF;
	/** Нет данных */
	private LongProperty PRM_PADEJ;
	/** Нет данных */
	private StringProperty TYPE_NAME;
	/** Нет данных */
	private StringProperty REQUIRED;
	/** Нет данных */
	private StringProperty PRM_R_NAME;
	/** Нет данных */
	private StringProperty PDJ_NAME;
	/** Разметка в Html */
	private StringProperty HTML_CODE;
	/** Ссылка на род. парам. */
	private LongProperty PARENTS;
	private StringProperty IS_LOC;
	
	public NT_TEMP_LIST_PARAM() {
		this.IS_LOC = new SimpleStringProperty();
		this.PARENTS = new SimpleLongProperty();
		this.PDJ_NAME = new SimpleStringProperty();
		this.HTML_CODE = new SimpleStringProperty();
		this.PRM_R_NAME = new SimpleStringProperty();
		this.REQUIRED = new SimpleStringProperty();
		this.PRM_PADEJ = new SimpleLongProperty();
		this.TYPE_NAME = new SimpleStringProperty();
		this.PRM_FOR_PRM_SQL = new SimpleStringProperty();
		this.PRM_ID = new SimpleLongProperty();
		this.PRM_NAME = new SimpleStringProperty();
		this.PRM_TMP_ID = new SimpleLongProperty();
		this.PRM_SQL = new SimpleStringProperty();
		this.PRM_TYPE = new SimpleLongProperty();
		this.PRM_TBL_REF = new SimpleStringProperty();
	}

	public void setIS_LOC(String IS_LOC) {
		this.IS_LOC.set(IS_LOC);
	}

	public String getIS_LOC() {
		return IS_LOC.get();
	}

	public StringProperty IS_LOCProperty() {
		return IS_LOC;
	}
	
	public void setPARENTS(Long PARENTS) {
		this.PARENTS.set(PARENTS);
	}

	public Long getPARENTS() {
		return PARENTS.get();
	}

	public LongProperty PARENTSProperty() {
		return PARENTS;
	}

	public void setHTML_CODE(String HTML_CODE) {
		this.HTML_CODE.set(HTML_CODE);
	}

	public String getHTML_CODE() {
		return HTML_CODE.get();
	}

	public StringProperty HTML_CODEProperty() {
		return HTML_CODE;
	}

	public void setPDJ_NAME(String PDJ_NAME) {
		this.PDJ_NAME.set(PDJ_NAME);
	}

	public String getPDJ_NAME() {
		return PDJ_NAME.get();
	}

	public StringProperty PDJ_NAMEProperty() {
		return PDJ_NAME;
	}

	public void setPRM_R_NAME(String PRM_R_NAME) {
		this.PRM_R_NAME.set(PRM_R_NAME);
	}

	public String getPRM_R_NAME() {
		return PRM_R_NAME.get();
	}

	public StringProperty PRM_R_NAMEProperty() {
		return PRM_R_NAME;
	}

	public void setREQUIRED(String REQUIRED) {
		this.REQUIRED.set(REQUIRED);
	}

	public String getREQUIRED() {
		return REQUIRED.get();
	}

	public StringProperty REQUIREDProperty() {
		return REQUIRED;
	}

	public void setTYPE_NAME(String TYPE_NAME) {
		this.TYPE_NAME.set(TYPE_NAME);
	}

	public String getTYPE_NAME() {
		return TYPE_NAME.get();
	}

	public StringProperty TYPE_NAMEProperty() {
		return TYPE_NAME;
	}

	public void setPRM_PADEJ(Long PRM_PADEJ) {
		this.PRM_PADEJ.set(PRM_PADEJ);
	}

	public Long getPRM_PADEJ() {
		return PRM_PADEJ.get();
	}

	public LongProperty PRM_PADEJProperty() {
		return PRM_PADEJ;
	}

	public void setPRM_FOR_PRM_SQL(String PRM_FOR_PRM_SQL) {
		this.PRM_FOR_PRM_SQL.set(PRM_FOR_PRM_SQL);
	}

	public void setPRM_ID(Long PRM_ID) {
		this.PRM_ID.set(PRM_ID);
	}

	public void setPRM_NAME(String PRM_NAME) {
		this.PRM_NAME.set(PRM_NAME);
	}

	public void setPRM_TMP_ID(Long PRM_TMP_ID) {
		this.PRM_TMP_ID.set(PRM_TMP_ID);
	}

	public void setPRM_SQL(String PRM_SQL) {
		this.PRM_SQL.set(PRM_SQL);
	}

	public void setPRM_TYPE(Long PRM_TYPE) {
		this.PRM_TYPE.set(PRM_TYPE);
	}

	public void setPRM_TBL_REF(String PRM_TBL_REF) {
		this.PRM_TBL_REF.set(PRM_TBL_REF);
	}

	public String getPRM_FOR_PRM_SQL() {
		return PRM_FOR_PRM_SQL.get();
	}

	public Long getPRM_ID() {
		return PRM_ID.get();
	}

	public String getPRM_NAME() {
		return PRM_NAME.get();
	}

	public Long getPRM_TMP_ID() {
		return PRM_TMP_ID.get();
	}

	public String getPRM_SQL() {
		return PRM_SQL.get();
	}

	public Long getPRM_TYPE() {
		return PRM_TYPE.get();
	}

	public String getPRM_TBL_REF() {
		return PRM_TBL_REF.get();
	}

	public StringProperty PRM_FOR_PRM_SQLProperty() {
		return PRM_FOR_PRM_SQL;
	}

	public LongProperty PRM_IDProperty() {
		return PRM_ID;
	}

	public StringProperty PRM_NAMEProperty() {
		return PRM_NAME;
	}

	public LongProperty PRM_TMP_IDProperty() {
		return PRM_TMP_ID;
	}

	public StringProperty PRM_SQLProperty() {
		return PRM_SQL;
	}

	public LongProperty PRM_TYPEProperty() {
		return PRM_TYPE;
	}

	public StringProperty PRM_TBL_REFProperty() {
		return PRM_TBL_REF;
	}
}
