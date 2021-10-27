package ru.psv.mj.report;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class REP_PARAMS_QUERY {
	/** Значение */
	private StringProperty Vals;
	/** Название */
	private StringProperty Names;

	public REP_PARAMS_QUERY() {
		this.Vals = new SimpleStringProperty();
		this.Names = new SimpleStringProperty();
	}

	public void setVals(String Vals) {
		this.Vals.set(Vals);
	}

	public void setNames(String Names) {
		this.Names.set(Names);
	}

	public String getVals() {
		return Vals.get();
	}

	public String getNames() {
		return Names.get();
	}

	public StringProperty ValsProperty() {
		return Vals;
	}

	public StringProperty NamesProperty() {
		return Names;
	}

}
