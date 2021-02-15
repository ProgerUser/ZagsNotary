package mj.project;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PRJ_FL_VER_HIST {
	/** Ссылка на файл */
	private IntegerProperty PRJ_ID;
	/** Версия */
	private IntegerProperty VERISION;
	/** Дата */
	private SimpleObjectProperty<LocalDateTime> DT;

	public PRJ_FL_VER_HIST() {
		this.PRJ_ID = new SimpleIntegerProperty();
		this.VERISION = new SimpleIntegerProperty();
		this.DT = new SimpleObjectProperty<>();
	}

	public void setPRJ_ID(Integer PRJ_ID) {
		this.PRJ_ID.set(PRJ_ID);
	}

	public void setVERISION(Integer VERISION) {
		this.VERISION.set(VERISION);
	}

	public void setDT(LocalDateTime DT) {
		this.DT.set(DT);
	}

	public Integer getPRJ_ID() {
		return PRJ_ID.get();
	}

	public Integer getVERISION() {
		return VERISION.get();
	}

	public LocalDateTime getDT() {
		return DT.get();
	}

	public IntegerProperty PRJ_IDProperty() {
		return PRJ_ID;
	}

	public IntegerProperty VERISIONProperty() {
		return VERISION;
	}

	public SimpleObjectProperty<LocalDateTime> DTProperty() {
		return DT;
	}
}
