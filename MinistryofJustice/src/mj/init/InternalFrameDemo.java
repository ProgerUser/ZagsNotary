package mj.init;

import java.io.IOException;

import com.jyloo.syntheticafx.DesktopPane;
import com.jyloo.syntheticafx.InternalFrame;
import com.jyloo.syntheticafx.RootPane;
import com.jyloo.syntheticafx.SyntheticaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;

public class InternalFrameDemo extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
		// SyntheticaFX.init("syntheticafx.theme.modena.SyntheticaFXModena");

		Scene scene = new Scene(new RootPane(stage, createContentPane()), 800, 600);
		stage.setScene(scene);
		stage.setTitle(getClass().getSimpleName());

		// String url = getClass().getResource("/resources/icon.png").toExternalForm();
		// stage.getIcons().add(new Image(url));
		stage.show();
	}

	private Parent createContentPane() throws IOException {
		InternalFrame iFrame = new InternalFrame("Frame A", createTabPane(), 400, 300);
		// String url = getClass().getResource("/resources/icon.png").toExternalForm();
		// iFrame.getIcons().add(new Image(url));
		iFrame.relocate(300, 200);
		iFrame.setSelected(true);

		DesktopPane desktopPane = new DesktopPane();
		desktopPane.getChildren().add(iFrame);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/rt/RootLayout.fxml"));
		Connect.connectionURL = "localhost:1521/XE";
		Connect.userID = "xxi";
		Connect.userPassword = "123";
		DBUtil.dbConnect();
		BorderPane content = (BorderPane) loader.load();
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