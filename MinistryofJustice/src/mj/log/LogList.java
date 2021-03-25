package mj.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.TitledBorderPane;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;

public class LogList {

	public LogList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private XTableColumn<LOGS, String> METHODNAME;

	@FXML
	private XTableColumn<LOGS, LocalDateTime> LOGDATED;

	@FXML
	private XTableView<LOGS> LOGS;

	@FXML
	private XTableColumn<LOGS, String> ERROR;

	@FXML
	private XTableColumn<LOGS, Integer> ID;

	@FXML
	private XTableColumn<LOGS, String> OPER;

	@FXML
	private XTableColumn<LOGS, Integer> LINENUMBER;

	@FXML
	private XTableColumn<LOGS, String> CLASSNAME;

	@FXML
	private DatePicker DT1;

	@FXML
	private DatePicker DT2;

	/**
	 * Форматирование столбцов
	 * 
	 * @param TC
	 */
	void DateFormatCol(TableColumn<LOGS, LocalDateTime> TC) {
		TC.setCellFactory(column -> {
			TableCell<LOGS, LocalDateTime> cell = new TableCell<LOGS, LocalDateTime>() {
				private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(format.format(item));
					}
				}
			};
			return cell;
		});
	}

	@FXML
	private BorderPane ROOT;

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");

		CheckBox filterVisible = new CheckBox("Показать фильтр");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());

		CheckBox menuButtonVisible = new CheckBox("Показать кнопку меню");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());

		CheckBox firstFilterable = new CheckBox("Фильтруемый первый столбец");
		// XTableColumn<VCUS, Integer> firstColumn = (XTableColumn<VCUS, Integer>)
		// table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(ID.filterableProperty());

		CheckBox includeHidden = new CheckBox("Включить скрытые столбцы");
		includeHidden.selectedProperty().bindBidirectional(table.getFilterController().includeHiddenProperty());

		CheckBox andFilters = new CheckBox("Используйте операцию \"И\" для многоколоночного фильтра");
		andFilters.selectedProperty().bindBidirectional(table.getFilterController().andFiltersProperty());

		pane.getChildren().addAll(filterVisible, menuButtonVisible, firstFilterable, includeHidden, andFilters);

		TitledBorderPane p = new TitledBorderPane("Настройки", pane);
		p.getStyleClass().add("top-border-only");
		p.setStyle("-fx-border-insets: 10 0 0 0");
		return p;
	}

	@FXML
	private void DT1() {
		Init();
	}

	@FXML
	private void DT2() {
		Init();
	}

	@FXML
	private void Clear() {
		DT1.setValue(null);
		DT2.setValue(null);
		Init();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			ROOT.setBottom(createOptionPane(LOGS));

			METHODNAME.setCellValueFactory(cellData -> cellData.getValue().METHODNAMEProperty());
			LOGDATED.setCellValueFactory(cellData -> cellData.getValue().LOGDATEProperty());
			CLASSNAME.setCellValueFactory(cellData -> cellData.getValue().CLASSNAMEProperty());
			OPER.setCellValueFactory(cellData -> cellData.getValue().OPERProperty());
			ERROR.setCellValueFactory(cellData -> cellData.getValue().ERRORProperty());
			ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
			LINENUMBER.setCellValueFactory(cellData -> cellData.getValue().LINENUMBERProperty().asObject());

			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());

			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			LINENUMBER.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			CLASSNAME.setColumnFilter(new PatternColumnFilter<>());
			OPER.setColumnFilter(new PatternColumnFilter<>());
			ERROR.setColumnFilter(new PatternColumnFilter<>());
			METHODNAME.setColumnFilter(new PatternColumnFilter<>());

			DateFormatCol(LOGDATED);
			Init();
			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);
//
//			
//			
			ID.setCellFactory(TextFieldTableCell.<LOGS, Integer>forTableColumn(new IntegerStringConverter()));
			ID.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, Integer>>() {
				@Override
				public void handle(CellEditEvent<LOGS, Integer> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow())).setID(t.getNewValue());
				}
			});
//			
//			
//			
			LOGDATED.setCellFactory(
					TextFieldTableCell.<LOGS, LocalDateTime>forTableColumn(new LocalDateTimeStringConverter()));
			LOGDATED.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, LocalDateTime>>() {
				@Override
				public void handle(CellEditEvent<LOGS, LocalDateTime> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLOGDATE(t.getNewValue());
				}
			});
//
//			
//			
			LINENUMBER.setCellFactory(TextFieldTableCell.<LOGS, Integer>forTableColumn(new IntegerStringConverter()));
			LINENUMBER.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, Integer>>() {
				@Override
				public void handle(CellEditEvent<LOGS, Integer> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setLINENUMBER(t.getNewValue());
				}
			});
//
//			
//			
			CLASSNAME.setCellFactory(TextFieldTableCell.forTableColumn());
			CLASSNAME.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, String>>() {
				@Override
				public void handle(CellEditEvent<LOGS, String> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setCLASSNAME(t.getNewValue());
				}
			});
//
//			
//			
			OPER.setCellFactory(TextFieldTableCell.forTableColumn());
			OPER.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, String>>() {
				@Override
				public void handle(CellEditEvent<LOGS, String> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOPER(t.getNewValue());
				}
			});
//			
//			
//			
			ERROR.setCellFactory(TextFieldTableCell.forTableColumn());
			ERROR.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, String>>() {
				@Override
				public void handle(CellEditEvent<LOGS, String> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow())).setERROR(t.getNewValue());
				}
			});
//			
//			
//			
			METHODNAME.setCellFactory(TextFieldTableCell.forTableColumn());
			METHODNAME.setOnEditCommit(new EventHandler<CellEditEvent<LOGS, String>>() {
				@Override
				public void handle(CellEditEvent<LOGS, String> t) {
					((LOGS) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setMETHODNAME(t.getNewValue());
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void Init() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			DateTimeFormatter formatterd = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String dt1 = "";
			String dt2 = "";

			if (DT1.getValue() != null) {
				dt1 = " and LOGDATE >= to_date('" + DT1.getValue().format(formatterd) + "','dd.mm.yyyy') \r\n";
			}
			if (DT2.getValue() != null) {
				dt2 = " and LOGDATE <= to_date('" + DT2.getValue().format(formatterd) + "','dd.mm.yyyy') \r\n";
			}

			String selectStmt = "select * from LOGS where 1=1 " + dt1 + dt2 + " order by LOGDATE desc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<LOGS> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				LOGS list = new LOGS();
				list.setLOGDATE((rs.getDate("LOGDATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("LOGDATE")), formatter) : null);
				list.setOPER(rs.getString("OPER"));
				list.setLINENUMBER(rs.getInt("LINENUMBER"));
				list.setMETHODNAME(rs.getString("METHODNAME"));
				list.setCLASSNAME(rs.getString("CLASSNAME"));
				//list.setERROR(rs.getString("ERROR"));
				list.setID(rs.getInt("ID"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			LOGS.setItems(dlist);

			TableFilter<LOGS> tableFilter = TableFilter.forTableView(LOGS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "LogList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

}
