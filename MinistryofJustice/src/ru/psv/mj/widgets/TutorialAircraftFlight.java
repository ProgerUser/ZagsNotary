package ru.psv.mj.widgets;

import com.flexganttfx.extras.GanttChartStatusBar;
import com.flexganttfx.extras.GanttChartToolBar;
/**
 * Copyright (C) 2014 - 2020 DLSC Software & Consulting GmbH (dlsc.com)
 *
 * This file is part of FlexGanttFX.
 */
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

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
	
public class TutorialAircraftFlight extends Application {

	/*
	 * Обычный объект данных, хранящий фиктивную информацию о полете.
	 */
	class FlightData {

		String flightNo;
		Instant departureTime = Instant.now();
		Instant arrivalTime = Instant.now().plus(Duration.ofHours(6));

		public FlightData(String flightNo, int day) {
			this.flightNo = flightNo;
			departureTime = departureTime.plus(Duration.ofDays(day));
			arrivalTime = arrivalTime.plus(Duration.ofDays(day));
		}
	}

	/*
	 * Деятельность, представляющая полет. Этот объект будет отображаться как полоса
     * в графическом представлении диаграммы Ганта. Полет изменчив, поэтому пользователь
     * сможет с ним взаимодействовать.
	 */
	class Flight extends MutableActivityBase<FlightData> {
		public Flight(FlightData data) {
			setUserObject(data);
			setName(data.flightNo);
			setStartTime(data.departureTime);
			setEndTime(data.arrivalTime);
		}
	}

	/*
	* Каждая строка представляет собой самолет в этом примере. Мероприятия, показанные на
	* строка относится к типу Flight.
	*/
	class Aircraft extends Row<Aircraft, Aircraft, Flight> {
		public Aircraft(String name) {
			super(name);
		}
	}

	public void start(Stage stage) {

		// Create the Gantt chart
		GanttChart<Aircraft> gantt = new GanttChart<Aircraft>(new Aircraft("Самолеты"));

	
		Layer layer = new Layer("Flights");
		gantt.getLayers().add(layer);

		Aircraft b747 = new Aircraft("B747");
		b747.addActivity(layer, new Flight(new FlightData("полет1", 1)));
		b747.addActivity(layer, new Flight(new FlightData("полет2", 2)));
		b747.addActivity(layer, new Flight(new FlightData("полет3", 3)));
		b747.addActivity(layer, new Flight(new FlightData("полет4", 10)));

		Aircraft a380 = new Aircraft("A380");
		a380.addActivity(layer, new Flight(new FlightData("flight1", 1)));
		a380.addActivity(layer, new Flight(new FlightData("flight2", 2)));
		a380.addActivity(layer, new Flight(new FlightData("flight3", 3)));

		gantt.getRoot().getChildren().setAll(b747, a380);

		Timeline timeline = gantt.getTimeline();
		timeline.showTemporalUnit(ChronoUnit.DAYS, 10);

		GraphicsBase<Aircraft> graphics = gantt.getGraphics();
		graphics.setActivityRenderer(Flight.class, GanttLayout.class,
				new ActivityBarRenderer<>(graphics, "Flight Renderer"));
		graphics.showEarliestActivities();

		BorderPane borderPane = new BorderPane(); 
		borderPane.setTop(new GanttChartToolBar(gantt)); 
		borderPane.setCenter(gantt); 
		borderPane.setBottom(new GanttChartStatusBar(gantt)); 
		Scene scene = new Scene(borderPane);
		
		//Scene scene = new Scene(gantt);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}