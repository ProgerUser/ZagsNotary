package notary.doc.html.model;

import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NT_CUS_LIST_FOR_SEARCH {
	/** Нет данных */
	private LongProperty ICUSNUM;
	/** Нет данных */
	private StringProperty CCUSNAME;
	/** Нет данных */
	private StringProperty CCUSNAME_SH;
	/** Нет данных */
	private SimpleObjectProperty<LocalDate> DCUSBIRTHDAY;
	/** Нет данных */
	private StringProperty CCUSLAST_NAME;
	/** Нет данных */
	private StringProperty CCUSFIRST_NAME;
	/** Нет данных */
	private StringProperty CCUSMIDDLE_NAME;
	/** Нет данных */
	private StringProperty CUS_TYPE;
	/** Нет данных */
	private LongProperty ID;

	public NT_CUS_LIST_FOR_SEARCH() {
		this.ICUSNUM = new SimpleLongProperty();
		this.CCUSNAME = new SimpleStringProperty();
		this.CCUSNAME_SH = new SimpleStringProperty();
		this.DCUSBIRTHDAY = new SimpleObjectProperty<>();
		this.CCUSLAST_NAME = new SimpleStringProperty();
		this.CCUSFIRST_NAME = new SimpleStringProperty();
		this.CCUSMIDDLE_NAME = new SimpleStringProperty();
		this.CUS_TYPE = new SimpleStringProperty();
		this.ID = new SimpleLongProperty();
	}

	public void setICUSNUM(Long ICUSNUM) {
		this.ICUSNUM.set(ICUSNUM);
	}

	public void setCCUSNAME(String CCUSNAME) {
		this.CCUSNAME.set(CCUSNAME);
	}

	public void setCCUSNAME_SH(String CCUSNAME_SH) {
		this.CCUSNAME_SH.set(CCUSNAME_SH);
	}

	public void setDCUSBIRTHDAY(LocalDate DCUSBIRTHDAY) {
		this.DCUSBIRTHDAY.set(DCUSBIRTHDAY);
	}

	public void setCCUSLAST_NAME(String CCUSLAST_NAME) {
		this.CCUSLAST_NAME.set(CCUSLAST_NAME);
	}

	public void setCCUSFIRST_NAME(String CCUSFIRST_NAME) {
		this.CCUSFIRST_NAME.set(CCUSFIRST_NAME);
	}

	public void setCCUSMIDDLE_NAME(String CCUSMIDDLE_NAME) {
		this.CCUSMIDDLE_NAME.set(CCUSMIDDLE_NAME);
	}

	public void setCUS_TYPE(String CUS_TYPE) {
		this.CUS_TYPE.set(CUS_TYPE);
	}

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public Long getICUSNUM() {
		return ICUSNUM.get();
	}

	public String getCCUSNAME() {
		return CCUSNAME.get();
	}

	public String getCCUSNAME_SH() {
		return CCUSNAME_SH.get();
	}

	public LocalDate getDCUSBIRTHDAY() {
		return DCUSBIRTHDAY.get();
	}

	public String getCCUSLAST_NAME() {
		return CCUSLAST_NAME.get();
	}

	public String getCCUSFIRST_NAME() {
		return CCUSFIRST_NAME.get();
	}

	public String getCCUSMIDDLE_NAME() {
		return CCUSMIDDLE_NAME.get();
	}

	public String getCUS_TYPE() {
		return CUS_TYPE.get();
	}

	public Long getID() {
		return ID.get();
	}

	public LongProperty ICUSNUMProperty() {
		return ICUSNUM;
	}

	public StringProperty CCUSNAMEProperty() {
		return CCUSNAME;
	}

	public StringProperty CCUSNAME_SHProperty() {
		return CCUSNAME_SH;
	}

	public SimpleObjectProperty<LocalDate> DCUSBIRTHDAYProperty() {
		return DCUSBIRTHDAY;
	}

	public StringProperty CCUSLAST_NAMEProperty() {
		return CCUSLAST_NAME;
	}

	public StringProperty CCUSFIRST_NAMEProperty() {
		return CCUSFIRST_NAME;
	}

	public StringProperty CCUSMIDDLE_NAMEProperty() {
		return CCUSMIDDLE_NAME;
	}

	public StringProperty CUS_TYPEProperty() {
		return CUS_TYPE;
	}

	public LongProperty IDProperty() {
		return ID;
	}
}
