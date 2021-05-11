package mj.doc.cus;

import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CUS_DOCUM {
	private StringProperty DOC_SUBDIV;/* Код подразделение, выдавшего документ */
	private SimpleObjectProperty<LocalDate> DOC_PERIOD;/* Период действия */
	private StringProperty DOC_AGENCY;/* Выдавший орган */
	private SimpleObjectProperty<LocalDate> DOC_DATE;/* Дата выдачи */
	private StringProperty DOC_SER;/* Серия документа */
	private StringProperty DOC_NUM;/* Номер документа */
	private StringProperty ID_DOC_TP;/* ID типа документа */
	private StringProperty PREF;/* Признак основного документа-"Y" */
	private LongProperty ICUSNUM;/* Идентификатор контрагента */
	private LongProperty ID_DOC;/* ID документа */

	public CUS_DOCUM() {
		this.DOC_SUBDIV = new SimpleStringProperty();
		this.DOC_PERIOD = new SimpleObjectProperty<>();
		this.DOC_AGENCY = new SimpleStringProperty();
		this.DOC_DATE = new SimpleObjectProperty<>();
		this.DOC_SER = new SimpleStringProperty();
		this.DOC_NUM = new SimpleStringProperty();
		this.ID_DOC_TP = new SimpleStringProperty();
		this.PREF = new SimpleStringProperty();
		this.ICUSNUM = new SimpleLongProperty();
		this.ID_DOC = new SimpleLongProperty();
	}

	public void setDOC_SUBDIV(String DOC_SUBDIV) {
		this.DOC_SUBDIV.set(DOC_SUBDIV);
	}

	public void setDOC_PERIOD(LocalDate DOC_PERIOD) {
		this.DOC_PERIOD.set(DOC_PERIOD);
	}

	public void setDOC_AGENCY(String DOC_AGENCY) {
		this.DOC_AGENCY.set(DOC_AGENCY);
	}

	public void setDOC_DATE(LocalDate DOC_DATE) {
		this.DOC_DATE.set(DOC_DATE);
	}

	public void setDOC_SER(String DOC_SER) {
		this.DOC_SER.set(DOC_SER);
	}

	public void setDOC_NUM(String DOC_NUM) {
		this.DOC_NUM.set(DOC_NUM);
	}

	public void setID_DOC_TP(String ID_DOC_TP) {
		this.ID_DOC_TP.set(ID_DOC_TP);
	}

	public void setPREF(String PREF) {
		this.PREF.set(PREF);
	}

	public void setICUSNUM(Long ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public void setID_DOC(Long ID_DOC) {
		this.ID_DOC.set(ID_DOC);
	}

	public String getDOC_SUBDIV() {
		return DOC_SUBDIV.get();
	}

	public LocalDate getDOC_PERIOD() {
		return DOC_PERIOD.get();
	}

	public String getDOC_AGENCY() {
		return DOC_AGENCY.get();
	}

	public LocalDate getDOC_DATE() {
		return DOC_DATE.get();
	}

	public String getDOC_SER() {
		return DOC_SER.get();
	}

	public String getDOC_NUM() {
		return DOC_NUM.get();
	}

	public String getID_DOC_TP() {
		return ID_DOC_TP.get();
	}

	public String getPREF() {
		return PREF.get();
	}

	public Long getICUSNUM() {
		return ICUSNUM.get();
	}

	public Long getID_DOC() {
		return ID_DOC.get();
	}

	public StringProperty DOC_SUBDIVProperty() {
		return DOC_SUBDIV;
	}

	public SimpleObjectProperty<LocalDate> DOC_PERIODProperty() {
		return DOC_PERIOD;
	}

	public StringProperty DOC_AGENCYProperty() {
		return DOC_AGENCY;
	}

	public SimpleObjectProperty<LocalDate> DOC_DATEProperty() {
		return DOC_DATE;
	}

	public StringProperty DOC_SERProperty() {
		return DOC_SER;
	}

	public StringProperty DOC_NUMProperty() {
		return DOC_NUM;
	}

	public StringProperty ID_DOC_TPProperty() {
		return ID_DOC_TP;
	}

	public StringProperty PREFProperty() {
		return PREF;
	}

	public LongProperty ICUSNUMProperty() {
		return ICUSNUM;
	}

	public LongProperty ID_DOCProperty() {
		return ID_DOC;
	}
}
