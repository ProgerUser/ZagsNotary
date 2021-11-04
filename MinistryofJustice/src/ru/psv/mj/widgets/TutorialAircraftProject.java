package ru.psv.mj.widgets;

/**
 * Copyright (C) 2014 - 2020 DLSC Software & Consulting GmbH (dlsc.com)
 *
 * This file is part of FlexGanttFX.
 */

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
import com.flexganttfx.model.Layer;
import com.flexganttfx.model.Row;
import com.flexganttfx.model.activity.MutableActivityBase;
import com.flexganttfx.model.layout.GanttLayout;
import com.flexganttfx.view.GanttChart;
import com.flexganttfx.view.graphics.GraphicsBase;
import com.flexganttfx.view.graphics.renderer.ActivityBarRenderer;
import com.flexganttfx.view.timeline.Timeline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TutorialAircraftProject extends Application {

	/**
	 * Обычный объект данных, хранящий фиктивную информацию о проектах.
	 * 
	 * @author saidp
	 *
	 */
	class ProjectData {

		String PrjName;
		String Fio;
		Instant departureTime = Instant.now();
		Instant arrivalTime = Instant.now().plus(Duration.ofDays(6));

		public ProjectData(String ProjectNo, int day, String Fio) {
			this.PrjName = ProjectNo;
			this.Fio = Fio;
			departureTime = departureTime.plus(Duration.ofDays(day));
			arrivalTime = arrivalTime.plus(Duration.ofDays(day));
		}
	}

	/**
	 * Деятельность, представляющая полет. Этот объект будет отображаться как полоса
	 * в графическом представлении диаграммы Ганта. Полет изменчив, поэтому
	 * пользователь сможет с ним взаимодействовать.
	 * 
	 * @author saidp
	 *
	 */
	class Project extends MutableActivityBase<ProjectData> {
		public Project(ProjectData data) {
			setUserObject(data);
			setName(data.Fio);
			setStartTime(data.departureTime);
			setEndTime(data.arrivalTime);
		}
	}

	/**
	 * Каждая строка представляет собой самолет в этом примере. Мероприятия,
	 * показанные на строка относится к типу Project.
	 * 
	 * @author saidp
	 *
	 */
	class Employees extends Row<Employees, Employees, Project> {
		public Employees(String name) {
			super(name);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(Stage stage) {

		// Create the Gantt chart
		GanttChart<Employees> gantt = new GanttChart<Employees>(new Employees("Сотрудники"));

		Layer layer = new Layer("Projects");
		gantt.getLayers().add(layer);

		Employees psv = null;
		psv = new Employees("Пачулия Саид Викторович");
		psv.addActivity(layer, new Project(new ProjectData("Дело 1", 1, "PSV")));
		psv.addActivity(layer, new Project(new ProjectData("Дело 2", 2, "PSV")));

	    psv = new Employees("Пачулия Саид Викторович1");
		psv.addActivity(layer, new Project(new ProjectData("Дело 1", 1, "PSV")));
		psv.addActivity(layer, new Project(new ProjectData("Дело 2", 2, "PSV")));

		gantt.getRoot().getChildren().setAll(psv);

		Timeline timeline = gantt.getTimeline();
		timeline.showTemporalUnit(ChronoUnit.DAYS, 10);

		GraphicsBase<Employees> graphics = gantt.getGraphics();
		graphics.setActivityRenderer(Project.class, GanttLayout.class,
				new ActivityBarRenderer<>(graphics, "Project Renderer"));
		graphics.showEarliestActivities();

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(new GanttChartToolBar(gantt));
		borderPane.setCenter(gantt);
		borderPane.setBottom(new GanttChartStatusBar(gantt));
		Scene scene = new Scene(borderPane);

		// Scene scene = new Scene(gantt);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}