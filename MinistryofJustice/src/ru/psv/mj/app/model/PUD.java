package ru.psv.mj.app.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PUD {
	private StringProperty CPUDCODE4;/* Код документа по 308-П. */
	private LongProperty IPUDUNISCODE;/* Код по UNISTREAM */
	private StringProperty CPUDDOCNAME;/* Полное наименование типа документа */
	private StringProperty CPUDCODE3;/* Код документа по TUTDF */
	private LongProperty IPUDCODE2;/* Код документа по 1417-У */
	private StringProperty CPUDCODE;/* Код документа по 207-П */
	private LongProperty IPUDUSE;/* Ограничение по использованию */
	private StringProperty CPUDDOC;/* Краткое наименование (код) типа документа */
	private StringProperty CPUDCODE10;/* Код документа по CAIS */
	private StringProperty CPUDNWU;/* Код по WU для нерезидентов основной документ */
	private StringProperty CPUDDPP;/* Признак: "Y"-документ на право пребывания. */
	private StringProperty CCONTACT;/* Номер документа в Контакте */
	private LongProperty IPUDBLZCODE;/* Код по BLIZKO */
	private StringProperty CPUDCODE9;/* Код документа по 2-НДФЛ. */
	private StringProperty CPUDCODE8;/* Код документа для ГИС ГМП. */
	private StringProperty CPUDLID;/* Код по системе "Лидер" */
	private StringProperty CPUDDUL;/* Признак документа удостоверяющего личность. */
	private LongProperty IPUDINT_CODE;/* Внутренний уникальный тип документа. */
	private StringProperty CPUDCODE7;/* Код документа в RETAIL */
	private LongProperty IPUDID;/* Уникальный идентификатор документа */
	private StringProperty CPUDCNT;/* Страна-эмитент(гражданства). */
	private StringProperty CPUDRESIDENT;/* Признак: "Резидент"- Y; "Нерезидент"-"N"; "Не определено"- null . */
	private StringProperty CPUDWUN;/* Код по WU для нерезидентов */
	private StringProperty CPUDWU;/* Код по WU для резидентов основной документ */
	private StringProperty CPUDCODE6;/* Код документа по инструкции 321-П. */
	private StringProperty CPUDCODE5;/* Код документа для БКИ. */

	public PUD() {
		this.CPUDCODE4 = new SimpleStringProperty();
		this.IPUDUNISCODE = new SimpleLongProperty();
		this.CPUDDOCNAME = new SimpleStringProperty();
		this.CPUDCODE3 = new SimpleStringProperty();
		this.IPUDCODE2 = new SimpleLongProperty();
		this.CPUDCODE = new SimpleStringProperty();
		this.IPUDUSE = new SimpleLongProperty();
		this.CPUDDOC = new SimpleStringProperty();
		this.CPUDCODE10 = new SimpleStringProperty();
		this.CPUDNWU = new SimpleStringProperty();
		this.CPUDDPP = new SimpleStringProperty();
		this.CCONTACT = new SimpleStringProperty();
		this.IPUDBLZCODE = new SimpleLongProperty();
		this.CPUDCODE9 = new SimpleStringProperty();
		this.CPUDCODE8 = new SimpleStringProperty();
		this.CPUDLID = new SimpleStringProperty();
		this.CPUDDUL = new SimpleStringProperty();
		this.IPUDINT_CODE = new SimpleLongProperty();
		this.CPUDCODE7 = new SimpleStringProperty();
		this.IPUDID = new SimpleLongProperty();
		this.CPUDCNT = new SimpleStringProperty();
		this.CPUDRESIDENT = new SimpleStringProperty();
		this.CPUDWUN = new SimpleStringProperty();
		this.CPUDWU = new SimpleStringProperty();
		this.CPUDCODE6 = new SimpleStringProperty();
		this.CPUDCODE5 = new SimpleStringProperty();
	}

	public void setCPUDCODE4(String CPUDCODE4) {
		this.CPUDCODE4.set(CPUDCODE4);
	}

	public void setIPUDUNISCODE(Long IPUDUNISCODE) {
		this.IPUDUNISCODE.set(IPUDUNISCODE);
	}

	public void setCPUDDOCNAME(String CPUDDOCNAME) {
		this.CPUDDOCNAME.set(CPUDDOCNAME);
	}

	public void setCPUDCODE3(String CPUDCODE3) {
		this.CPUDCODE3.set(CPUDCODE3);
	}

	public void setIPUDCODE2(Long IPUDCODE2) {
		this.IPUDCODE2.set(IPUDCODE2);
	}

	public void setCPUDCODE(String CPUDCODE) {
		this.CPUDCODE.set(CPUDCODE);
	}

	public void setIPUDUSE(Long IPUDUSE) {
		this.IPUDUSE.set(IPUDUSE);
	}

	public void setCPUDDOC(String CPUDDOC) {
		this.CPUDDOC.set(CPUDDOC);
	}

	public void setCPUDCODE10(String CPUDCODE10) {
		this.CPUDCODE10.set(CPUDCODE10);
	}

	public void setCPUDNWU(String CPUDNWU) {
		this.CPUDNWU.set(CPUDNWU);
	}

	public void setCPUDDPP(String CPUDDPP) {
		this.CPUDDPP.set(CPUDDPP);
	}

	public void setCCONTACT(String CCONTACT) {
		this.CCONTACT.set(CCONTACT);
	}

	public void setIPUDBLZCODE(Long IPUDBLZCODE) {
		this.IPUDBLZCODE.set(IPUDBLZCODE);
	}

	public void setCPUDCODE9(String CPUDCODE9) {
		this.CPUDCODE9.set(CPUDCODE9);
	}

	public void setCPUDCODE8(String CPUDCODE8) {
		this.CPUDCODE8.set(CPUDCODE8);
	}

	public void setCPUDLID(String CPUDLID) {
		this.CPUDLID.set(CPUDLID);
	}

	public void setCPUDDUL(String CPUDDUL) {
		this.CPUDDUL.set(CPUDDUL);
	}

	public void setIPUDINT_CODE(Long IPUDINT_CODE) {
		this.IPUDINT_CODE.set(IPUDINT_CODE);
	}

	public void setCPUDCODE7(String CPUDCODE7) {
		this.CPUDCODE7.set(CPUDCODE7);
	}

	public void setIPUDID(Long IPUDID) {
		this.IPUDID.set(IPUDID);
	}

	public void setCPUDCNT(String CPUDCNT) {
		this.CPUDCNT.set(CPUDCNT);
	}

	public void setCPUDRESIDENT(String CPUDRESIDENT) {
		this.CPUDRESIDENT.set(CPUDRESIDENT);
	}

	public void setCPUDWUN(String CPUDWUN) {
		this.CPUDWUN.set(CPUDWUN);
	}

	public void setCPUDWU(String CPUDWU) {
		this.CPUDWU.set(CPUDWU);
	}

	public void setCPUDCODE6(String CPUDCODE6) {
		this.CPUDCODE6.set(CPUDCODE6);
	}

	public void setCPUDCODE5(String CPUDCODE5) {
		this.CPUDCODE5.set(CPUDCODE5);
	}

	public String getCPUDCODE4() {
		return CPUDCODE4.get();
	}

	public Long getIPUDUNISCODE() {
		return IPUDUNISCODE.get();
	}

	public String getCPUDDOCNAME() {
		return CPUDDOCNAME.get();
	}

	public String getCPUDCODE3() {
		return CPUDCODE3.get();
	}

	public Long getIPUDCODE2() {
		return IPUDCODE2.get();
	}

	public String getCPUDCODE() {
		return CPUDCODE.get();
	}

	public Long getIPUDUSE() {
		return IPUDUSE.get();
	}

	public String getCPUDDOC() {
		return CPUDDOC.get();
	}

	public String getCPUDCODE10() {
		return CPUDCODE10.get();
	}

	public String getCPUDNWU() {
		return CPUDNWU.get();
	}

	public String getCPUDDPP() {
		return CPUDDPP.get();
	}

	public String getCCONTACT() {
		return CCONTACT.get();
	}

	public Long getIPUDBLZCODE() {
		return IPUDBLZCODE.get();
	}

	public String getCPUDCODE9() {
		return CPUDCODE9.get();
	}

	public String getCPUDCODE8() {
		return CPUDCODE8.get();
	}

	public String getCPUDLID() {
		return CPUDLID.get();
	}

	public String getCPUDDUL() {
		return CPUDDUL.get();
	}

	public Long getIPUDINT_CODE() {
		return IPUDINT_CODE.get();
	}

	public String getCPUDCODE7() {
		return CPUDCODE7.get();
	}

	public Long getIPUDID() {
		return IPUDID.get();
	}

	public String getCPUDCNT() {
		return CPUDCNT.get();
	}

	public String getCPUDRESIDENT() {
		return CPUDRESIDENT.get();
	}

	public String getCPUDWUN() {
		return CPUDWUN.get();
	}

	public String getCPUDWU() {
		return CPUDWU.get();
	}

	public String getCPUDCODE6() {
		return CPUDCODE6.get();
	}

	public String getCPUDCODE5() {
		return CPUDCODE5.get();
	}

	public StringProperty CPUDCODE4Property() {
		return CPUDCODE4;
	}

	public LongProperty IPUDUNISCODEProperty() {
		return IPUDUNISCODE;
	}

	public StringProperty CPUDDOCNAMEProperty() {
		return CPUDDOCNAME;
	}

	public StringProperty CPUDCODE3Property() {
		return CPUDCODE3;
	}

	public LongProperty IPUDCODE2Property() {
		return IPUDCODE2;
	}

	public StringProperty CPUDCODEProperty() {
		return CPUDCODE;
	}

	public LongProperty IPUDUSEProperty() {
		return IPUDUSE;
	}

	public StringProperty CPUDDOCProperty() {
		return CPUDDOC;
	}

	public StringProperty CPUDCODE10Property() {
		return CPUDCODE10;
	}

	public StringProperty CPUDNWUProperty() {
		return CPUDNWU;
	}

	public StringProperty CPUDDPPProperty() {
		return CPUDDPP;
	}

	public StringProperty CCONTACTProperty() {
		return CCONTACT;
	}

	public LongProperty IPUDBLZCODEProperty() {
		return IPUDBLZCODE;
	}

	public StringProperty CPUDCODE9Property() {
		return CPUDCODE9;
	}

	public StringProperty CPUDCODE8Property() {
		return CPUDCODE8;
	}

	public StringProperty CPUDLIDProperty() {
		return CPUDLID;
	}

	public StringProperty CPUDDULProperty() {
		return CPUDDUL;
	}

	public LongProperty IPUDINT_CODEProperty() {
		return IPUDINT_CODE;
	}

	public StringProperty CPUDCODE7Property() {
		return CPUDCODE7;
	}

	public LongProperty IPUDIDProperty() {
		return IPUDID;
	}

	public StringProperty CPUDCNTProperty() {
		return CPUDCNT;
	}

	public StringProperty CPUDRESIDENTProperty() {
		return CPUDRESIDENT;
	}

	public StringProperty CPUDWUNProperty() {
		return CPUDWUN;
	}

	public StringProperty CPUDWUProperty() {
		return CPUDWU;
	}

	public StringProperty CPUDCODE6Property() {
		return CPUDCODE6;
	}

	public StringProperty CPUDCODE5Property() {
		return CPUDCODE5;
	}
}
