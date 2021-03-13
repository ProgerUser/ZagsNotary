package mj.widgets;

import com.jyloo.syntheticafx.DesktopPane;
import com.jyloo.syntheticafx.InternalFrame;
import com.jyloo.syntheticafx.RootPane;
import com.jyloo.syntheticafx.SyntheticaFX;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InternalFrameDemo extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
		// SyntheticaFX.init("syntheticafx.theme.modena.SyntheticaFXModena");

		Scene scene = new Scene(new RootPane(stage, createContentPane()), 800, 600);
		stage.setScene(scene);
		stage.setTitle(getClass().getSimpleName());

		// String url = getClass().getResource("/resources/icon.png").toExternalForm();
		// stage.getIcons().add(new Image(url));
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private Parent createContentPane() {
		InternalFrame iFrame = new InternalFrame("Frame A", createTabPane(), 400, 300);
		// String url = getClass().getResource("/resources/icon.png").toExternalForm();
		// iFrame.getIcons().add(new Image(url));
		iFrame.relocate(300, 200);
		iFrame.setSelected(true);

		StackedAreaChart.Series<Number, Number> series = new StackedAreaChart.Series<>("Series 1",
				FXCollections.observableArrayList(new StackedAreaChart.Data<>(0, 2), new StackedAreaChart.Data<>(2, 5),
						new StackedAreaChart.Data<>(4, 3), new StackedAreaChart.Data<>(6, 6),
						new StackedAreaChart.Data<>(8, 7), new StackedAreaChart.Data<>(10, 6)));
		StackedAreaChart.Series<Number, Number> series2 = new StackedAreaChart.Series<>("Series 2",
				FXCollections.observableArrayList(new StackedAreaChart.Data<>(0, 3), new StackedAreaChart.Data<>(1, 6),
						new StackedAreaChart.Data<>(3, 1), new StackedAreaChart.Data<>(5, 2),
						new StackedAreaChart.Data<>(7, 3), new StackedAreaChart.Data<>(9, 2),
						new StackedAreaChart.Data<>(10, 1)));
		ObservableList<StackedAreaChart.Series<Number, Number>> chartData = FXCollections.observableArrayList(series,
				series2);

		NumberAxis xAxis = new NumberAxis("X Values", 0.0d, 10.0d, 1.0d);
		NumberAxis yAxis = new NumberAxis("Y Values", 0.0d, 10.0d, 1.0d);
		InternalFrame iFrame2 = new InternalFrame("Frame B",
				new StackedAreaChart<Number, Number>(xAxis, yAxis, chartData));
		iFrame2.relocate(10, 10);

		DesktopPane desktopPane = new DesktopPane();
		desktopPane.getChildren().add(iFrame2);
		desktopPane.getChildren().add(iFrame);

		BorderPane content = new BorderPane();
		content.setCenter(desktopPane);
		return content;
	}

	private TabPane createTabPane() {
		TabPane tabPane = new TabPane();
		tabPane.getTabs().add(createTab("Tab1", new Label("Content 1")));
		tabPane.getTabs().add(createTab("Tab2", new Label("Content 2")));
		tabPane.getTabs().add(createTab("Tab3", new Label("Content 3")));
		return tabPane;
	}

	private Tab createTab(String tabLabel, Node content) {
		Tab tab = new Tab();
		tab.setText(tabLabel);
		tab.setContent(content);
		return tab;
	}
}