package mj.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class Report_ {

	@FXML
	private ComboBox<REPORTS> ComboList;

	@FXML
	private Button List;

	@FXML
	private TableView<REP_PARAMS> PARAMS;

	@FXML
	private TableColumn<REP_PARAMS, Long> PRM_ID;

	@FXML
	private TableColumn<REP_PARAMS, String> VALUE;

	@FXML
	private TableColumn<REP_PARAMS, String> NAME;

	@FXML
	void List(ActionEvent event) {
		ParamRep();
	}

	void onclose() {
		Stage stage = (Stage) PARAMS.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void ComboList(ActionEvent event) {
		try {
			// Параметры
			PreparedStatement prp = conn.prepareStatement("select * from VREP_PARAMS where REP_ID = ?");
			prp.setLong(1, ComboList.getSelectionModel().getSelectedItem().getREP_ID());
			ResultSet rs = prp.executeQuery();
			ObservableList<REP_PARAMS> rep = FXCollections.observableArrayList();
			while (rs.next()) {
				REP_PARAMS list = new REP_PARAMS();
				list.setPRM_NAME(rs.getString("PRM_NAME"));
				list.setIS_LIST(rs.getString("IS_LIST"));
				list.setPRM_ID(rs.getLong("PRM_ID"));
				list.setLIST_QUERY(rs.getString("LIST_QUERY"));
				list.setREP_ID(rs.getLong("REP_ID"));
				list.setPRM_DEF_VALUE(rs.getString("PRM_DEF_VALUE"));
				rep.add(list);
			}
			prp.close();
			rs.close();

			PARAMS.setItems(rep);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	public static String clobStringConversion(Clob clb) throws IOException, SQLException {
		if (clb == null)
			return "";

		StringBuffer str = new StringBuffer();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null)
			str.append(strng);

		return str.toString();
	}

	@FXML
	void Run(ActionEvent event) {
		runfr3();
	}

	void runfr3() {
		try {
			FRREPRunner runner = new FRREPRunner();

			dbConnect();
			DBUtil.RunProcess(conn);
			String port = "1521";
			String sid = "";
			String host = "";
			{
				Statement statement = conn.createStatement();
				ResultSet rs = statement
						.executeQuery("select INSTANCE_NAME, HOST_NAME, userenv('SESSIONID') from SYS.V_$INSTANCE");
				if (rs.next()) {
					sid = rs.getString(1);
					host = rs.getString(2);
					runner.setSid(rs.getString(3));
				}
				statement.close();
				rs.close();
			}

			runner.setDllOptions(FRREPRunner.FRREPDllOptions.DESIGN);
			runner.setUserName(Connect.userID);
			runner.setPassw(Connect.userPassword);
			runner.setConnect_string(host + ":" + port + ":" + sid);
			runner.setServerName("xe");
			runner.setReport_file("terminal_amra.fr3");
			runner.setReport_type_id("666");
			runner.setReport_id("1");
			runner.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void Run1(ActionEvent event) {
		try {
			if (ComboList.getValue() == null) {
				return;
			}

			// for (int i = 0; i < PARAMS.getItems().size(); i++) {
			// for (int j = 0; j < PARAMS.getColumns().size(); j++) {
			// if (PARAMS.getColumns().get(j).getCellData(i) != null) {
			// if (j == 2) {
			// PARAMS.getColumns().get(j).getCellData(i);
			// }
			// } else {
			//
			// }
			// }
			// }

			try {
				PreparedStatement prp = conn.prepareStatement(
						"begin delete from REP_PARAMS_TMP; commit; insert into REP_PARAMS_TMP (REP_ID,PRM_ID,PRM_VALUE) values (?,?,?); end;");
				prp.setLong(1, PARAMS.getSelectionModel().getSelectedItem().getREP_ID());
				prp.setLong(2, PARAMS.getSelectionModel().getSelectedItem().getPRM_ID());
				prp.setString(3, PARAMS.getSelectionModel().getSelectedItem().getPRM_DEF_VALUE());
				prp.executeUpdate();
				prp.close();
			} catch (Exception e) {
				DBUtil.LOG_ERROR(e);
			}

			// Call DB, get clob via java code
			PreparedStatement prp = conn
					.prepareStatement("select rep_id, rep_name, rep_type, rep_class from REPORTS where rep_id = ?");
			prp.setLong(1, ComboList.getValue().getREP_ID());
			ResultSet rs = prp.executeQuery();
			String clob_to_tring = null;
			if (rs.next()) {
				clob_to_tring = clobStringConversion(rs.getClob("rep_class"));
			}
			prp.close();
			rs.close();

			final String className = "DynamicRep";
			// A temporary directory where the java code and class will be located
			Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);
			Files.createDirectories(temp);
			// Creation of the java source file
			// You could also extends the SimpleJavaFileObject object as shown in the doc.
			// See SimpleJavaFileObject at
			// https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html
			Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
			System.out.println("The java source file is loacted at " + javaSourceFile);
			Files.write(javaSourceFile, clob_to_tring.getBytes());
			// The compile part
			// Definition of the files to compile
			File[] files1 = { javaSourceFile.toFile() };

			// compile the source file
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(files1));
			compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
			fileManager.close();

			// load the compiled class
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { temp.toUri().toURL() });
			Class<?> helloClass = classLoader.loadClass(className);

			// call a method on the loaded class
			Method helloMethod = helloClass.getDeclaredMethod("showReport");
			helloMethod.invoke(helloClass.newInstance());

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	public void clickItem(MouseEvent event) {
		if (event.getClickCount() == 2) // Checking double click
		{
			System.out.println(PARAMS.getSelectionModel().getSelectedItem().getIS_LIST());
		}
	}

	void ParamList() {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();
			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<REP_PARAMS_QUERY> cusllists = new TableView<REP_PARAMS_QUERY>();
			TableColumn<REP_PARAMS_QUERY, String> Vals = new TableColumn<>("Код");
			Vals.setCellValueFactory(new PropertyValueFactory<>("Vals"));
			TableColumn<REP_PARAMS_QUERY, String> Names = new TableColumn<>("Names");
			Names.setCellValueFactory(new PropertyValueFactory<>("Names"));
			cusllists.getColumns().add(Vals);
			cusllists.getColumns().add(Names);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);

			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");

			vb.setPadding(new Insets(10, 10, 10, 10));
			/**/
			Vals.setCellValueFactory(cellData -> cellData.getValue().ValsProperty());
			Names.setCellValueFactory(cellData -> cellData.getValue().NamesProperty());

			/* SelData */
			PreparedStatement prepStmt = conn
					.prepareStatement(PARAMS.getSelectionModel().getSelectedItem().getLIST_QUERY());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<REP_PARAMS_QUERY> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				REP_PARAMS_QUERY cus = new REP_PARAMS_QUERY();
				cus.setVals(rs.getString(1));
				cus.setNames(rs.getString(2));
				cuslist.add(cus);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			// двойной щелчок
			cusllists.setRowFactory(tv -> {
				TableRow<REP_PARAMS_QUERY> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (cusllists.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите данные из таблицы!");
						} else {
							REP_PARAMS_QUERY qq = cusllists.getSelectionModel().getSelectedItem();
							System.out.println(qq.getVals());

							VALUE.setCellFactory(TextFieldTableCell.forTableColumn());
							ObservableList<REP_PARAMS> rep = FXCollections.observableArrayList();
							REP_PARAMS prms = PARAMS.getSelectionModel().getSelectedItem();
							prms.setPRM_DEF_VALUE(qq.getVals());
							rep.add(prms);
							// PARAMS.setItems(rep);
							((Node) (event.getSource())).getScene().getWindow().hide();
						}
					}
				});
				return row;
			});

			Vals.setPrefWidth(100);
			Names.setPrefWidth(250);

			TableFilter<REP_PARAMS_QUERY> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			/**/
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите данные из таблицы!");
					} else {
						REP_PARAMS_QUERY qq = cusllists.getSelectionModel().getSelectedItem();
						System.out.println(qq.getVals());

						VALUE.setCellFactory(TextFieldTableCell.forTableColumn());
						ObservableList<REP_PARAMS> rep = FXCollections.observableArrayList();
						REP_PARAMS prms = PARAMS.getSelectionModel().getSelectedItem();
						prms.setPRM_DEF_VALUE(qq.getVals());
						rep.add(prms);
						// PARAMS.setItems(rep);
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) PARAMS.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle(PARAMS.getSelectionModel().getSelectedItem().getPRM_NAME());
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			// Specifies the modality for new window.
			newWindow.initModality(Modality.WINDOW_MODAL);
			// Specifies the owner Window (parent) for new window
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void ParamRep() {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();
			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<REPORTS> cusllists = new TableView<REPORTS>();
			TableColumn<REPORTS, Long> REP_ID = new TableColumn<>("Код");
			REP_ID.setCellValueFactory(new PropertyValueFactory<>("Vals"));
			TableColumn<REPORTS, String> Names = new TableColumn<>("Наименование");
			Names.setCellValueFactory(new PropertyValueFactory<>("Names"));
			cusllists.getColumns().add(REP_ID);
			cusllists.getColumns().add(Names);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);

			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");

			vb.setPadding(new Insets(10, 10, 10, 10));
			/**/
			REP_ID.setCellValueFactory(cellData -> cellData.getValue().REP_IDProperty().asObject());
			Names.setCellValueFactory(cellData -> cellData.getValue().REP_NAMEProperty());

			/* SelData */
			PreparedStatement prepStmt = conn.prepareStatement("select * from REPORTS");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<REPORTS> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				REPORTS list = new REPORTS();
				list.setREP_ID(rs.getLong("REP_ID"));
				list.setREP_NAME(rs.getString("REP_NAME"));
				list.setREP_TYPE(rs.getString("REP_TYPE"));
				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			// двойной щелчок
			cusllists.setRowFactory(tv -> {
				TableRow<REPORTS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (cusllists.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите данные из таблицы!");
						} else {
							REPORTS qq = cusllists.getSelectionModel().getSelectedItem();
							ComboList.getSelectionModel().select(qq);
							((Node) (event.getSource())).getScene().getWindow().hide();
						}
					}
				});
				return row;
			});

			cusllists.setItems(cuslist);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			REP_ID.setPrefWidth(100);
			Names.setPrefWidth(350);

			TableFilter<REPORTS> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			/**/
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите данные из таблицы!");
					} else {
						REPORTS qq = cusllists.getSelectionModel().getSelectedItem();
						ComboList.getSelectionModel().select(qq);
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) PARAMS.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			// Specifies the modality for new window.
			newWindow.initModality(Modality.WINDOW_MODAL);
			// Specifies the owner Window (parent) for new window
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			VALUE.setOnEditCommit(new EventHandler<CellEditEvent<REP_PARAMS, String>>() {
				@Override
				public void handle(CellEditEvent<REP_PARAMS, String> t) {
					((REP_PARAMS) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setPRM_DEF_VALUE(t.getNewValue());
				}
			});

			// new FRREPRunner().run();

			PARAMS.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				REP_PARAMS prm = PARAMS.getSelectionModel().getSelectedItem();
				if (prm != null && prm.getIS_LIST().equals("Y")) {
					VALUE.setCellFactory(col -> {
						Button editButton = new Button("Открыть список");
						TableCell<REP_PARAMS, String> cell = new TableCell<REP_PARAMS, String>() {

							@Override
							public void updateItem(String person, boolean empty) {
								super.updateItem(person, empty);
								if (empty) {
									setGraphic(null);
								} else {
									setGraphic(editButton);
								}
							}
						};
						editButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								ParamList();
							}
						});
						return cell;
					});

				} else if (prm != null && prm.getIS_LIST().equals("N")) {
					VALUE.setCellFactory(TextFieldTableCell.forTableColumn());
				}
			});

			// установить svg как иконку для кнопки
//			{
//				InputStream svgFile = getClass().getResourceAsStream("/table2.svg");
//				SvgLoader loader = new SvgLoader();
//				Group svgImage = loader.loadSvg(svgFile);
//				svgImage.setScaleX(0.060);
//				svgImage.setScaleY(0.060);
//				Group graphic = new Group(svgImage);
//				List.setGraphic(graphic);
//			}

			// отчеты
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from REPORTS";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<REPORTS> rep = FXCollections.observableArrayList();
				while (rs.next()) {
					REPORTS list = new REPORTS();
					list.setREP_NAME(rs.getString("REP_NAME"));
					list.setREP_ID(rs.getLong("REP_ID"));
					list.setREP_TYPE(rs.getString("REP_TYPE"));
					rep.add(list);
				}
				sqlStatement.close();
				rs.close();
				
				ComboList.setItems(rep);
				CombRep(ComboList);
			}

			PRM_ID.setCellValueFactory(cellData -> cellData.getValue().PRM_IDProperty().asObject());
			VALUE.setCellValueFactory(cellData -> cellData.getValue().PRM_DEF_VALUEProperty());
			NAME.setCellValueFactory(cellData -> cellData.getValue().PRM_NAMEProperty());

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void CombRep(ComboBox<REPORTS> cmb) {
		cmb.setConverter(new StringConverter<REPORTS>() {
			@Override
			public String toString(REPORTS product) {
				return (product != null) ? product.getREP_NAME() : "";
			}

			@Override
			public REPORTS fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getREP_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	/**
	 * Строка соединения
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "Report");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Закрыть
	 */
	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private BooleanProperty Status;

	private LongProperty Id;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Long value) {
		this.Id.set(value);
	}

	public Long getId() {
		return this.Id.get();
	}

	public Report_() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}
}
