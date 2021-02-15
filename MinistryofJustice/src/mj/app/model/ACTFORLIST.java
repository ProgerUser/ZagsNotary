package mj.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ACTFORLIST {
	private SimpleObjectProperty<LocalDate> DCUSBIRTHDAY;/* Нет данных */
	private SimpleObjectProperty<LocalDateTime> BR_ACT_DATE;/* Нет данных */
	private StringProperty CCUSNAME;/* Нет данных */
	private IntegerProperty BR_ACT_ID;/* Нет данных */

	public ACTFORLIST() {
		this.DCUSBIRTHDAY = new SimpleObjectProperty<>();
		this.BR_ACT_DATE = new SimpleObjectProperty<>();
		this.CCUSNAME = new SimpleStringProperty();
		this.BR_ACT_ID = new SimpleIntegerProperty();
	}

	public void setDCUSBIRTHDAY(LocalDate DCUSBIRTHDAY) {
		this.DCUSBIRTHDAY.set(DCUSBIRTHDAY);
	}

	public void setBR_ACT_DATE(LocalDateTime BR_ACT_DATE) {
		this.BR_ACT_DATE.set(BR_ACT_DATE);
	}

	public void setCCUSNAME(String CCUSNAME) {
		this.CCUSNAME.set(CCUSNAME);
	}

	public void setBR_ACT_ID(Integer BR_ACT_ID) {
		this.BR_ACT_ID.set(BR_ACT_ID);
	}

	public Object getDCUSBIRTHDAY() {
		return DCUSBIRTHDAY.get();
	}

	public Object getBR_ACT_DATE() {
		return BR_ACT_DATE.get();
	}

	public String getCCUSNAME() {
		return CCUSNAME.get();
	}

	public Integer getBR_ACT_ID() {
		return BR_ACT_ID.get();
	}

	public SimpleObjectProperty<LocalDate> DCUSBIRTHDAYProperty() {
		return DCUSBIRTHDAY;
	}

	public SimpleObjectProperty<LocalDateTime> BR_ACT_DATEProperty() {
		return BR_ACT_DATE;
	}

	public StringProperty CCUSNAMEProperty() {
		return CCUSNAME;
	}

	public IntegerProperty BR_ACT_IDProperty() {
		return BR_ACT_ID;
	}
}
