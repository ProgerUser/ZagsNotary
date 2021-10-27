package ru.psv.mj.zags.doc.cus;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VPUD {
	private LongProperty IPUDID;/* Нет данных */
	private StringProperty CPUDDOC;/* Нет данных */

	public VPUD() {
		this.IPUDID = new SimpleLongProperty();
		this.CPUDDOC = new SimpleStringProperty();
	}

	public void setIPUDID(Long IPUDID) {
		this.IPUDID.set(IPUDID);
	}

	public void setCPUDDOC(String CPUDDOC) {
		this.CPUDDOC.set(CPUDDOC);
	}

	public Long getIPUDID() {
		return IPUDID.get();
	}

	public String getCPUDDOC() {
		return CPUDDOC.get();
	}

	public LongProperty IPUDIDProperty() {
		return IPUDID;
	}

	public StringProperty CPUDDOCProperty() {
		return CPUDDOC;
	}
}
