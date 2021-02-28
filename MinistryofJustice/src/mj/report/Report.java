package mj.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class Report {

	@FXML
	private ComboBox<AP_REPORT_CAT> ComboList;

	@FXML
	private Button List;

	@FXML
	private Button Design;

	@FXML
	private Button Clone;

	@FXML
	void List(ActionEvent event) {
		ParamRep();
	}

	void onclose() {
		Stage stage = (Stage) List.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void ComboList(ActionEvent event) {
		try {

		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	void Run(ActionEvent event) {
		if (ComboList.getSelectionModel().getSelectedItem() != null) {
			setFrDllOptions(FRREPRunner.FRREPDllOptions.RUN);
			runfr3();
		}
	}

	@FXML
	void Clone(ActionEvent event) {
		if (ComboList.getSelectionModel().getSelectedItem() != null) {
			setFrDllOptions(FRREPRunner.FRREPDllOptions.CLONE);
			runfr3();
		}
	}

	@FXML
	void Design(ActionEvent event) {
		if (ComboList.getSelectionModel().getSelectedItem() != null) {
			setFrDllOptions(FRREPRunner.FRREPDllOptions.DESIGN);
			runfr3();
		}
	}

	public void setFrDllOptions(FRREPRunner.FRREPDllOptions frDllOptions) {
		this.frDllOptions = frDllOptions;
	}

	public FRREPRunner.FRREPDllOptions getFrDllOptions() {
		return this.frDllOptions;
	}

	private FRREPRunner.FRREPDllOptions frDllOptions;

	void runfr3() {
		try {
			FRREPRunner runner = new FRREPRunner();
			dbConnect();
			String port = Connect.connectionURL.substring(Connect.connectionURL.indexOf(":")+1,
					Connect.connectionURL.indexOf("/"));
			//System.out.println(port);
			String sid = "";
			String host = "";
			{
				Statement statement = conn.createStatement();
				ResultSet rs = statement
						.executeQuery("select INSTANCE_NAME, HOST_NAME, userenv('SESSIONID') from SYS.V_$INSTANCE");
				if (rs.next()) {
					sid = rs.getString(1);
					runner.setSid(rs.getString(3));
				}
				statement.close();
				rs.close();
			}
			if (getRecId() != null) {
				runner.setP1(String.valueOf(getRecId()));
			}
			runner.setDllOptions(getFrDllOptions());
			runner.setUserName(Connect.userID);
			runner.setPassw(Connect.userPassword);
			host = Connect.connectionURL.substring(0, Connect.connectionURL.indexOf(":"));
			runner.setConnect_string(host + ":" + port + ":" + sid);
			runner.setServerName(sid);
			runner.setReport_file(ComboList.getSelectionModel().getSelectedItem().getREPORT_UFS());
			runner.setReport_type_id(
					String.valueOf(ComboList.getSelectionModel().getSelectedItem().getREPORT_TYPE_ID()));
			runner.setReport_id(String.valueOf(ComboList.getSelectionModel().getSelectedItem().getREPORT_ID()));

			// runner.setGenerate_type("F");
			// runner.setUse_convertation("N");
			// runner.setEdit_enable("N");
			// runner.setDir("T");
			runner.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void clickItem(MouseEvent event) {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void ParamRep() {
		try {
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			Button Update = new Button();
			Update.setText("Выбрать");
			AnchorPane secondaryLayout = new AnchorPane();
			VBox vb = new VBox();
			//ToolBar toolBar = new ToolBar(Update);
			
			ButtonBar bb = new ButtonBar();
			bb.setPrefHeight(40);
			bb.getButtons().addAll(Update);
			
			XTableView<AP_REPORT_CAT> cusllists = new XTableView<AP_REPORT_CAT>();
			XTableColumn<AP_REPORT_CAT, Integer> REPORT_ID = new XTableColumn<>("Код");
			REPORT_ID.setCellValueFactory(new PropertyValueFactory<>("REPORT_ID"));
			XTableColumn<AP_REPORT_CAT, String> REPORT_NAME = new XTableColumn<>("Наименование");
			REPORT_NAME.setCellValueFactory(new PropertyValueFactory<>("REPORT_NAME"));
			cusllists.getColumns().add(REPORT_ID);
			cusllists.getColumns().add(REPORT_NAME);

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());

			REPORT_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			REPORT_NAME.setColumnFilter(new PatternColumnFilter<>());

			vb.getChildren().add(cusllists);
			vb.getChildren().add(bb);

			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");

			vb.setPadding(new Insets(5, 5, 5, 5));
			/**/
			REPORT_ID.setCellValueFactory(cellData -> cellData.getValue().REPORT_IDProperty().asObject());
			REPORT_NAME.setCellValueFactory(cellData -> cellData.getValue().REPORT_NAMEProperty());

			/* SelData */
			PreparedStatement prepStmt = conn.prepareStatement("select * from v_ap_rep_cat where REPORT_TYPE_ID = ?");
			prepStmt.setInt(1, getId());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<AP_REPORT_CAT> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				AP_REPORT_CAT list = new AP_REPORT_CAT();
				list.setAVAILABLE_SQL(rs.getString("AVAILABLE_SQL"));
				list.setOEM_DATA(rs.getString("OEM_DATA"));
				list.setEDIT_PARAM(rs.getString("EDIT_PARAM"));
				list.setREPORT_COMMENT(rs.getString("REPORT_COMMENT"));
				list.setREPORT_VIEWER(rs.getInt("REPORT_VIEWER"));
				list.setCOPIES(rs.getInt("COPIES"));
				list.setREPORT_UFS(rs.getString("REPORT_UFS"));
				list.setREPORT_NAME(rs.getString("REPORT_NAME"));
				list.setREPORT_TYPE_ID(rs.getInt("REPORT_TYPE_ID"));
				list.setREPORT_ID(rs.getInt("REPORT_ID"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			// двойной щелчок
			cusllists.setRowFactory(tv -> {
				TableRow<AP_REPORT_CAT> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (cusllists.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите данные из таблицы!");
						} else {
							AP_REPORT_CAT qq = cusllists.getSelectionModel().getSelectedItem();
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

			REPORT_ID.setPrefWidth(100);
			REPORT_NAME.setPrefWidth(350);

			TableFilter<AP_REPORT_CAT> CUSFilter = TableFilter.forTableView(cusllists).apply();
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
						AP_REPORT_CAT qq = cusllists.getSelectionModel().getSelectedItem();
						ComboList.getSelectionModel().select(qq);
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) List.getScene().getWindow();

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
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	private void initialize() {
		// ResourceManager.addBundle();
		try {
			dbConnect();
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
			//OctIconView icon = new OctIconView(OctIcon.TOOLS);
			//Text icon = OctIconFactory.get().createIcon(OctIcon.TOOLS);
			
			if (DBUtil.OdbAction(183) == 0) {
				Clone.setVisible(false);
			}
			if (DBUtil.OdbAction(182) == 0) {
				Design.setVisible(false);
			}

//			{
//				InputStream svgFile = getClass().getResourceAsStream("/copy_frrep.svg");
//				SvgLoader loader = new SvgLoader();
//				Group svgImage = loader.loadSvg(svgFile);
//				svgImage.setScaleX(0.04);
//				svgImage.setScaleY(0.04);
//				Group graphic = new Group(svgImage);
//				Clone.setGraphic(graphic);
//			}

//			{
//				InputStream svgFile = getClass().getResourceAsStream("/design_frrep.svg");
//				SvgLoader loader = new SvgLoader();
//				Group svgImage = loader.loadSvg(svgFile);
//				svgImage.setScaleX(0.04);
//				svgImage.setScaleY(0.04);
//				Group graphic = new Group(svgImage);
//				Design.setGraphic(graphic);
//			}

			// отчеты
			{
				PreparedStatement stmt = conn.prepareStatement("select * from v_ap_rep_cat where REPORT_TYPE_ID = ?");
				stmt.setInt(1, getId());
				ResultSet rs = stmt.executeQuery();
				ObservableList<AP_REPORT_CAT> rep = FXCollections.observableArrayList();
				while (rs.next()) {
					AP_REPORT_CAT list = new AP_REPORT_CAT();
					list.setAVAILABLE_SQL(rs.getString("AVAILABLE_SQL"));
					list.setOEM_DATA(rs.getString("OEM_DATA"));
					list.setEDIT_PARAM(rs.getString("EDIT_PARAM"));
					list.setREPORT_COMMENT(rs.getString("REPORT_COMMENT"));
					list.setREPORT_VIEWER(rs.getInt("REPORT_VIEWER"));
					list.setCOPIES(rs.getInt("COPIES"));
					list.setREPORT_UFS(rs.getString("REPORT_UFS"));
					list.setREPORT_NAME(rs.getString("REPORT_NAME"));
					list.setREPORT_TYPE_ID(rs.getInt("REPORT_TYPE_ID"));
					list.setREPORT_ID(rs.getInt("REPORT_ID"));
					rep.add(list);
				}

				stmt.close();
				rs.close();

				ComboList.setItems(rep);
				ComboList.getSelectionModel().select(0);
				CombRep(ComboList);
			}
			
//			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//	        System.out.println("Number of print services: " + printServices.length);
//
//	        for (PrintService printer : printServices)
//	            System.out.println("Printer: " + printer.getName());

		} catch (Exception e) {
			e.printStackTrace();
			Main.logger = Logger.getLogger(getClass());
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	private void CombRep(ComboBox<AP_REPORT_CAT> cmb) {
		cmb.setConverter(new StringConverter<AP_REPORT_CAT>() {
			@Override
			public String toString(AP_REPORT_CAT product) {
				return (product != null) ? product.getREPORT_NAME() : "";
			}

			@Override
			public AP_REPORT_CAT fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getREPORT_NAME().equals(string)).findFirst()
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
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	private BooleanProperty Status;

	private IntegerProperty Id;

	private IntegerProperty RecId;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setRecId(Integer value) {
		this.RecId.set(value);
	}

	public Integer getRecId() {
		return this.RecId.get();
	}

	public void setId(Integer value) {
		this.Id.set(value);
	}

	public Integer getId() {
		return this.Id.get();
	}

	public Report() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.RecId = new SimpleIntegerProperty();
		this.Id = new SimpleIntegerProperty();
	}
}
