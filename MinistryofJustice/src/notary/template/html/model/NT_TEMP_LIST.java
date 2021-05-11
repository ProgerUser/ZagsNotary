package notary.template.html.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_TEMP_LIST {
	/** Ссылка на нотариус */
	private LongProperty NOTARY;
	/** Путь к файлу */
	private StringProperty DOCX_PATH;
	/** ИД */
	private LongProperty ID;
	/** Наименование */
	private StringProperty NAME;
	/** Ссылка на id родитель */
	private LongProperty PARENT;
	/** Запрос */
	private StringProperty REP_QUERY;
	/** шаблон html */
	private StringProperty HTML_TEMP;

	public NT_TEMP_LIST() {
		this.NOTARY = new SimpleLongProperty();
		this.DOCX_PATH = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
		this.NAME = new SimpleStringProperty();
		this.PARENT = new SimpleLongProperty();
		this.REP_QUERY = new SimpleStringProperty();
		this.HTML_TEMP = new SimpleStringProperty();
	}

	public void setNOTARY(Long NOTARY) {
		this.NOTARY.set(NOTARY);
	}

	public void setDOCX_PATH(String DOCX_PATH) {
		this.DOCX_PATH.set(DOCX_PATH);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}

	public void setPARENT(Long PARENT) {
		this.PARENT.set(PARENT);
	}

	public void setREP_QUERY(String REP_QUERY) {
		this.REP_QUERY.set(REP_QUERY);
	}

	public void setHTML_TEMP(String HTML_TEMP) {
		this.HTML_TEMP.set(HTML_TEMP);
	}

	public Long getNOTARY() {
		return NOTARY.get();
	}

	public String getDOCX_PATH() {
		return DOCX_PATH.get();
	}

	public Long getID() {
		return ID.get();
	}

	public String getNAME() {
		return NAME.get();
	}

	public Long getPARENT() {
		return PARENT.get();
	}

	public String getREP_QUERY() {
		return REP_QUERY.get();
	}

	public String getHTML_TEMP() {
		return HTML_TEMP.get();
	}

	public LongProperty NOTARYProperty() {
		return NOTARY;
	}

	public StringProperty DOCX_PATHProperty() {
		return DOCX_PATH;
	}

	public LongProperty IDProperty() {
		return ID;
	}

	public StringProperty NAMEProperty() {
		return NAME;
	}

	public LongProperty PARENTProperty() {
		return PARENT;
	}

	public StringProperty REP_QUERYProperty() {
		return REP_QUERY;
	}

	public StringProperty HTML_TEMPProperty() {
		return HTML_TEMP;
	}
}
