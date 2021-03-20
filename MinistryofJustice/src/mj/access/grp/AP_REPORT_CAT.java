package mj.access.grp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AP_REPORT_CAT {
	/** Идентификатор отчета */
	private IntegerProperty REPORT_ID;
	/** Идентификатор типа отчета */
	private IntegerProperty REPORT_TYPE_ID;
	/** Наименование отчета */
	private StringProperty REPORT_NAME;
	/** UFS отчета */
	private StringProperty REPORT_UFS;
	/** Число копий */
	private IntegerProperty COPIES;
	/** Нет данных */
	private IntegerProperty REPORT_VIEWER;
	/** Комментарий к отчету */
	private StringProperty REPORT_COMMENT;
	/** Редактирование параметров отчета */
	private StringProperty EDIT_PARAM;
	/** Выгрузка данных в кодировке RU8PC866 */
	private StringProperty OEM_DATA;
	/** Возможность выполнения отчета */
	private StringProperty AVAILABLE_SQL;

	public AP_REPORT_CAT() {
		this.REPORT_ID = new SimpleIntegerProperty();
		this.REPORT_TYPE_ID = new SimpleIntegerProperty();
		this.REPORT_NAME = new SimpleStringProperty();
		this.REPORT_UFS = new SimpleStringProperty();
		this.COPIES = new SimpleIntegerProperty();
		this.REPORT_VIEWER = new SimpleIntegerProperty();
		this.REPORT_COMMENT = new SimpleStringProperty();
		this.EDIT_PARAM = new SimpleStringProperty();
		this.OEM_DATA = new SimpleStringProperty();
		this.AVAILABLE_SQL = new SimpleStringProperty();
	}

	public void setREPORT_ID(Integer REPORT_ID) {
		this.REPORT_ID.set(REPORT_ID);
	}

	public void setREPORT_TYPE_ID(Integer REPORT_TYPE_ID) {
		this.REPORT_TYPE_ID.set(REPORT_TYPE_ID);
	}

	public void setREPORT_NAME(String REPORT_NAME) {
		this.REPORT_NAME.set(REPORT_NAME);
	}

	public void setREPORT_UFS(String REPORT_UFS) {
		this.REPORT_UFS.set(REPORT_UFS);
	}

	public void setCOPIES(Integer COPIES) {
		this.COPIES.set(COPIES);
	}

	public void setREPORT_VIEWER(Integer REPORT_VIEWER) {
		this.REPORT_VIEWER.set(REPORT_VIEWER);
	}

	public void setREPORT_COMMENT(String REPORT_COMMENT) {
		this.REPORT_COMMENT.set(REPORT_COMMENT);
	}

	public void setEDIT_PARAM(String EDIT_PARAM) {
		this.EDIT_PARAM.set(EDIT_PARAM);
	}

	public void setOEM_DATA(String OEM_DATA) {
		this.OEM_DATA.set(OEM_DATA);
	}

	public void setAVAILABLE_SQL(String AVAILABLE_SQL) {
		this.AVAILABLE_SQL.set(AVAILABLE_SQL);
	}

	public Integer getREPORT_ID() {
		return REPORT_ID.get();
	}

	public Integer getREPORT_TYPE_ID() {
		return REPORT_TYPE_ID.get();
	}

	public String getREPORT_NAME() {
		return REPORT_NAME.get();
	}

	public String getREPORT_UFS() {
		return REPORT_UFS.get();
	}

	public Integer getCOPIES() {
		return COPIES.get();
	}

	public Integer getREPORT_VIEWER() {
		return REPORT_VIEWER.get();
	}

	public String getREPORT_COMMENT() {
		return REPORT_COMMENT.get();
	}

	public String getEDIT_PARAM() {
		return EDIT_PARAM.get();
	}

	public String getOEM_DATA() {
		return OEM_DATA.get();
	}

	public String getAVAILABLE_SQL() {
		return AVAILABLE_SQL.get();
	}

	public IntegerProperty REPORT_IDProperty() {
		return REPORT_ID;
	}

	public IntegerProperty REPORT_TYPE_IDProperty() {
		return REPORT_TYPE_ID;
	}

	public StringProperty REPORT_NAMEProperty() {
		return REPORT_NAME;
	}

	public StringProperty REPORT_UFSProperty() {
		return REPORT_UFS;
	}

	public IntegerProperty COPIESProperty() {
		return COPIES;
	}

	public IntegerProperty REPORT_VIEWERProperty() {
		return REPORT_VIEWER;
	}

	public StringProperty REPORT_COMMENTProperty() {
		return REPORT_COMMENT;
	}

	public StringProperty EDIT_PARAMProperty() {
		return EDIT_PARAM;
	}

	public StringProperty OEM_DATAProperty() {
		return OEM_DATA;
	}

	public StringProperty AVAILABLE_SQLProperty() {
		return AVAILABLE_SQL;
	}
}
