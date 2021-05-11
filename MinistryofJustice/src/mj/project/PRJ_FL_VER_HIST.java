package mj.project;

import java.time.LocalDateTime;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PRJ_FL_VER_HIST {
	/** Ссылка на файл */
	private LongProperty PRJ_ID;
	/** Версия */
	private LongProperty VERISION;
	/** Дата */
	private SimpleObjectProperty<LocalDateTime> DT;

	public PRJ_FL_VER_HIST() {
		this.PRJ_ID = new SimpleLongProperty();
		this.VERISION = new SimpleLongProperty();
		this.DT = new SimpleObjectProperty<>();
	}

	public void setPRJ_ID(Long PRJ_ID) {
		this.PRJ_ID.set(PRJ_ID);
	}

	public void setVERISION(Long VERISION) {
		this.VERISION.set(VERISION);
	}

	public void setDT(LocalDateTime DT) {
		this.DT.set(DT);
	}

	public Long getPRJ_ID() {
		return PRJ_ID.get();
	}

	public Long getVERISION() {
		return VERISION.get();
	}

	public LocalDateTime getDT() {
		return DT.get();
	}

	public LongProperty PRJ_IDProperty() {
		return PRJ_ID;
	}

	public LongProperty VERISIONProperty() {
		return VERISION;
	}

	public SimpleObjectProperty<LocalDateTime> DTProperty() {
		return DT;
	}
}
