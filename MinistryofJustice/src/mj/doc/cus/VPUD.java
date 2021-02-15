package mj.doc.cus;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPUD {
	private IntegerProperty IPUDID;/* Нет данных */
	private StringProperty CPUDDOC;/* Нет данных */

	public VPUD() {
		this.IPUDID = new SimpleIntegerProperty();
		this.CPUDDOC = new SimpleStringProperty();
	}

	public void setIPUDID(Integer IPUDID) {
		this.IPUDID.set(IPUDID);
	}

	public void setCPUDDOC(String CPUDDOC) {
		this.CPUDDOC.set(CPUDDOC);
	}

	public Integer getIPUDID() {
		return IPUDID.get();
	}

	public String getCPUDDOC() {
		return CPUDDOC.get();
	}

	public IntegerProperty IPUDIDProperty() {
		return IPUDID;
	}

	public StringProperty CPUDDOCProperty() {
		return CPUDDOC;
	}
}
