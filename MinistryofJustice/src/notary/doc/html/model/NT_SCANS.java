package notary.doc.html.model;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_SCANS {
	/** Ссылка на документ */
	private LongProperty SC_DOC;
	/** Название файла */
	private StringProperty SC_FILE_NAME;
	/** Дата добавления */
	private SimpleObjectProperty<LocalDateTime> SC_DATE;
	/** Пользователь */
	private StringProperty SC_OPER;
	/** ИД файла */
	private LongProperty SC_ID;
	/** Тип документа (возможно что только PDF) */
	private StringProperty SC_TYPE;

	public NT_SCANS() {
		this.SC_DOC = new SimpleLongProperty();
		this.SC_FILE_NAME = new SimpleStringProperty();
		this.SC_DATE = new SimpleObjectProperty<>();
		this.SC_OPER = new SimpleStringProperty();
		this.SC_ID = new SimpleLongProperty();
		this.SC_TYPE = new SimpleStringProperty();
	}

	public void setSC_DOC(Long SC_DOC) {
		this.SC_DOC.set(SC_DOC);
	}

	public void setSC_FILE_NAME(String SC_FILE_NAME) {
		this.SC_FILE_NAME.set(SC_FILE_NAME);
	}

	public void setSC_DATE(LocalDateTime SC_DATE) {
		this.SC_DATE.set(SC_DATE);
	}

	public void setSC_OPER(String SC_OPER) {
		this.SC_OPER.set(SC_OPER);
	}

	public void setSC_ID(Long SC_ID) {
		this.SC_ID.set(SC_ID);
	}

	public void setSC_TYPE(String SC_TYPE) {
		this.SC_TYPE.set(SC_TYPE);
	}

	public Long getSC_DOC() {
		return SC_DOC.get();
	}

	public String getSC_FILE_NAME() {
		return SC_FILE_NAME.get();
	}

	public LocalDateTime getSC_DATE() {
		return SC_DATE.get();
	}

	public String getSC_OPER() {
		return SC_OPER.get();
	}

	public Long getSC_ID() {
		return SC_ID.get();
	}

	public String getSC_TYPE() {
		return SC_TYPE.get();
	}

	public LongProperty SC_DOCProperty() {
		return SC_DOC;
	}

	public StringProperty SC_FILE_NAMEProperty() {
		return SC_FILE_NAME;
	}

	public SimpleObjectProperty<LocalDateTime> SC_DATEProperty() {
		return SC_DATE;
	}

	public StringProperty SC_OPERProperty() {
		return SC_OPER;
	}

	public LongProperty SC_IDProperty() {
		return SC_ID;
	}

	public StringProperty SC_TYPEProperty() {
		return SC_TYPE;
	}
}
