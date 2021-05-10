package notary.doc.html.model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class V_NT_DOC {
	/** Нет данных */
	private StringProperty HTML_DOCUMENT;
	/** Нет данных */
	private IntegerProperty ID;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> CR_DATE;
	/** Нет данных */
	private StringProperty CR_TIME;
	/** Нет данных */
	private StringProperty OPER;
	/** Нет данных */
	private IntegerProperty NOTARY;
	/** Нет данных */
	private IntegerProperty NT_TYPE;
	/** Нет данных */
	private StringProperty DOC_NUMBER;
	/** Нет данных */
	private StringProperty TYPE_NAME;
	private StringProperty TYPE_NODE;

	public V_NT_DOC() {
		this.TYPE_NODE = new SimpleStringProperty();
		this.HTML_DOCUMENT = new SimpleStringProperty();
		this.ID = new SimpleIntegerProperty();
		this.CR_DATE = new SimpleObjectProperty<>();
		this.CR_TIME = new SimpleStringProperty();
		this.OPER = new SimpleStringProperty();
		this.NOTARY = new SimpleIntegerProperty();
		this.NT_TYPE = new SimpleIntegerProperty();
		this.DOC_NUMBER = new SimpleStringProperty();
		this.TYPE_NAME = new SimpleStringProperty();
	}

	public void setTYPE_NODE(String TYPE_NODE) {
		this.TYPE_NODE.set(TYPE_NODE);
	}
	
	public void setHTML_DOCUMENT(String HTML_DOCUMENT) {
		this.HTML_DOCUMENT.set(HTML_DOCUMENT);
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public void setCR_DATE(LocalDate CR_DATE) {
		this.CR_DATE.set(CR_DATE);
	}

	public void setCR_TIME(String CR_TIME) {
		this.CR_TIME.set(CR_TIME);
	}

	public void setOPER(String OPER) {
		this.OPER.set(OPER);
	}

	public void setNOTARY(Integer NOTARY) {
		this.NOTARY.set(NOTARY);
	}

	public void setNT_TYPE(Integer NT_TYPE) {
		this.NT_TYPE.set(NT_TYPE);
	}

	public void setDOC_NUMBER(String DOC_NUMBER) {
		this.DOC_NUMBER.set(DOC_NUMBER);
	}

	public void setTYPE_NAME(String TYPE_NAME) {
		this.TYPE_NAME.set(TYPE_NAME);
	}

	public String getTYPE_NODE() {
		return TYPE_NODE.get();
	}
	
	public String getHTML_DOCUMENT() {
		return HTML_DOCUMENT.get();
	}

	public Integer getID() {
		return ID.get();
	}

	public LocalDate getCR_DATE() {
		return CR_DATE.get();
	}

	public String getCR_TIME() {
		return CR_TIME.get();
	}

	public String getOPER() {
		return OPER.get();
	}

	public Integer getNOTARY() {
		return NOTARY.get();
	}

	public Integer getNT_TYPE() {
		return NT_TYPE.get();
	}

	public String getDOC_NUMBER() {
		return DOC_NUMBER.get();
	}

	public String getTYPE_NAME() {
		return TYPE_NAME.get();
	}

	public StringProperty TYPE_NODEProperty() {
		return TYPE_NODE;
	}
	
	public StringProperty HTML_DOCUMENTProperty() {
		return HTML_DOCUMENT;
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public SimpleObjectProperty<LocalDate> CR_DATEProperty() {
		return CR_DATE;
	}

	public StringProperty CR_TIMEProperty() {
		return CR_TIME;
	}

	public StringProperty OPERProperty() {
		return OPER;
	}

	public IntegerProperty NOTARYProperty() {
		return NOTARY;
	}

	public IntegerProperty NT_TYPEProperty() {
		return NT_TYPE;
	}

	public StringProperty DOC_NUMBERProperty() {
		return DOC_NUMBER;
	}

	public StringProperty TYPE_NAMEProperty() {
		return TYPE_NAME;
	}
}
