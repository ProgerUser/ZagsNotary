package mj.doc.cus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.DateColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.doc.birthact.AddBirthAct;
import mj.doc.birthact.FindBirth;
import mj.doc.death.DEATH_CERT;
import mj.doc.divorce.DIVORCE_CERT;
import mj.doc.mercer.MC_MERCER;
import mj.doc.patern.PATERN_CERT;
import mj.msg.Msg;

public class UtilCus {

	public UtilCus() {
		Main.logger = Logger.getLogger(getClass());
	}

	/**
	 * Создать гражданина
	 * 
	 * @param event
	 */
	public void Add_Cus(TextField fio, TextField id, Stage stage_, Connection conn) {
		try {

			// проверка доступа
			if (DBUtil.OdbAction(27l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/cus/IUCus.fxml"));

			AddCus controller = new AddCus();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Создание записи");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						try {
							PreparedStatement sttmt = conn
									.prepareStatement("select CCUSNAME from cus where ICUSNUM = ?");
							sttmt.setLong(1, controller.getId());
							ResultSet rs = sttmt.executeQuery();
							if (rs.next()) {
								fio.setText(rs.getString("CCUSNAME"));
								id.setText(String.valueOf(controller.getId()));
							}
							sttmt.close();
							rs.close();
						} catch (SQLException e) {
							DBUtil.LOG_ERROR(e);
						}
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Найти гражданина
	 * 
	 * @param fio
	 * @param id
	 */
	public void Find_Cus(TextField fio, TextField id, Stage stage_) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/cus/FIND_CUS.fxml"));

			FIND_CUS controller = new FIND_CUS();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Список граждан");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						fio.setText(controller.getFio());
						id.setText(String.valueOf(controller.getId()));
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Найти акт о рождении
	 */
	public void Find_Brn(TextField id, Stage stage_) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/birthact/FindBirth.fxml"));

			FindBirth controller = new FindBirth();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Список Свидетельств о рождении");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						id.setText(String.valueOf(controller.getId()));
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Найти заключения брака
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void FindMercer(TextField ID,Stage stage,Connection conn) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			// Update.setLayoutX(30.0);
			// Update.setLayoutY(450.0);
			BorderPane secondaryLayout = new BorderPane();
			
			//Region spacer = new Region();
			//VBox.setVgrow(spacer, Priority.ALWAYS);
			VBox vb = new VBox();
			
			ButtonBar buttonBar = new ButtonBar();
			buttonBar.setPadding(new Insets(10,0,0,0));
			ButtonBar.setButtonData(Update, ButtonData.APPLY);
			buttonBar.getButtons().addAll(Update);
			//ToolBar toolBar = new ToolBar(Update);
			
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			
			XTableView<MC_MERCER> cusllists = new XTableView<MC_MERCER>();
			
			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");
			
			cusllists.setMaxWidth(Double.MAX_VALUE);
			cusllists.setMaxHeight(Double.MAX_VALUE);
			XTableColumn<MC_MERCER, Long> MERCER_ID = new XTableColumn<>("Номер");
			MERCER_ID.setCellValueFactory(new PropertyValueFactory<>("MERCER_ID"));

			
			MERCER_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			
			
			XTableColumn<MC_MERCER, String> HE = new XTableColumn<>("Он");
			XTableColumn<MC_MERCER, String> HeFio = new XTableColumn<>("ФИО");
			HeFio.setCellValueFactory(new PropertyValueFactory<>("HeFio"));
			HE.getColumns().add(HeFio);

			XTableColumn<MC_MERCER, String> SHE = new XTableColumn<>("Она");
			XTableColumn<MC_MERCER, String> SheFio = new XTableColumn<>("ФИО");
			SheFio.setCellValueFactory(new PropertyValueFactory<>("SheFio"));
			SHE.getColumns().add(SheFio);

			XTableColumn<MC_MERCER, LocalDateTime> MERCER_DATE = new XTableColumn<>("Дата документа");
			MERCER_DATE.setCellValueFactory(new PropertyValueFactory<>("MERCER_DATE"));

			SheFio.setColumnFilter(new PatternColumnFilter<>());
			SHE.setColumnFilter(new PatternColumnFilter<>());
			HE.setColumnFilter(new PatternColumnFilter<>());
			HeFio.setColumnFilter(new PatternColumnFilter<>());
			
			cusllists.getColumns().add(MERCER_ID);
			cusllists.getColumns().add(HE);
			cusllists.getColumns().add(SHE);
			cusllists.getColumns().add(MERCER_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(buttonBar);

			vb.setPadding(new Insets(10, 10, 10, 10));
			
			/**/
			MERCER_ID.setCellValueFactory(cellData -> cellData.getValue().MERCER_IDProperty().asObject());
			SheFio.setCellValueFactory(cellData -> cellData.getValue().SHEFIOProperty());
			HeFio.setCellValueFactory(cellData -> cellData.getValue().HEFIOProperty());
			MERCER_DATE.setCellValueFactory(cellData -> cellData.getValue().TM$MERCER_DATEProperty());

			MERCER_DATE.setCellFactory(column -> {
				TableCell<MC_MERCER, LocalDateTime> cell = new TableCell<MC_MERCER, LocalDateTime>() {
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
			/* SelData */
			String selectStmt = "select (select g.ccusname from cus g where g.icusnum = t.mercer_he) HeFio,\n"
					+ "       (select g.ccusname from cus g where g.icusnum = t.mercer_she) SheFio,\n" + "       t.*\n"
					+ "  from MC_MERCER t order by MERCER_ID desc\n";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			ObservableList<MC_MERCER> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				MC_MERCER list = new MC_MERCER();

				list.setSHEFIO(rs.getString("SheFio"));
				list.setHEFIO(rs.getString("HeFio"));
				list.setMERCER_ID(rs.getLong("MERCER_ID"));
				list.setMERCER_HE(rs.getLong("MERCER_HE"));
				list.setMERCER_SHE(rs.getLong("MERCER_SHE"));
				list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
				list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
				list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
				list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
				list.setMERCER_HEAGE(rs.getLong("MERCER_HEAGE"));
				list.setMERCER_SHEAGE(rs.getLong("MERCER_SHEAGE"));
				list.setTM$MERCER_DATE((rs.getDate("MERCER_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("MERCER_DATE")), formatterdt)
						: null);
				list.setMERCER_USR(rs.getString("MERCER_USR"));
				list.setMERCER_ZAGS(rs.getLong("MERCER_ZAGS"));
				list.setMERCER_DIVSHE(rs.getLong("MERCER_DIVSHE"));
				list.setMERCER_DIVHE(rs.getLong("MERCER_DIVHE"));
				list.setMERCER_DSPMT_HE(rs.getString("MERCER_DSPMT_HE"));
				list.setMERCER_NUM(rs.getString("MERCER_NUM"));
				list.setMERCER_SERIA(rs.getString("MERCER_SERIA"));
				list.setMERCER_DIESHE(rs.getLong("MERCER_DIESHE"));
				list.setMERCER_DIEHE(rs.getLong("MERCER_DIEHE"));
				list.setMERCER_OTHER(rs.getString("MERCER_OTHER"));
				list.setMERCER_DSPMT_SHE(rs.getString("MERCER_DSPMT_SHE"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			// Main.autoResizeColumns(cusllists);

			cusllists.setPrefWidth(700);
			cusllists.setPrefHeight(400);

			// ICUSNUM.setPrefWidth(100);
			// DCUSBIRTHDAY.setPrefWidth(120);
			// DCUSBIRTHDAY.setPrefWidth(120);

			TableFilter<MC_MERCER> CUSFilter = TableFilter.forTableView(cusllists).apply();
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
						MC_MERCER country = cusllists.getSelectionModel().getSelectedItem();
						// ID.setText(country.getMERCER_ID());
						ID.setText(String.valueOf(country.getMERCER_ID()));

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			//secondaryLayout.getChildren().add();
			secondaryLayout.setCenter(vb);
			// secondaryLayout.getChildren().add(cusllists);

			// VBox vbox = new VBox(debtinfo);
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE,
					Control.USE_COMPUTED_SIZE);/* Control.USE_COMPUTED_SIZE */
			//Stage stage = (Stage) SAVE.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Акты о заключении брака");
			newWindow.setScene(secondScene);
			newWindow.setResizable(true);
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
	
	
	/**
	 * Найти установление отцовства
	 * 
	 * @param ID
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void FindPatern(TextField ID,Stage stage,Connection conn) {
		try {
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			
			
			Button Update = new Button();
			Update.setText("Выбрать");
			// Update.setLayoutX(30.0);
			// Update.setLayoutY(450.0);
			BorderPane secondaryLayout = new BorderPane();

			VBox vb = new VBox();
			
			ButtonBar buttonBar = new ButtonBar();
			buttonBar.setPadding(new Insets(10,0,0,0));
			ButtonBar.setButtonData(Update, ButtonData.APPLY);
			buttonBar.getButtons().addAll(Update);
			
			XTableView<PATERN_CERT> cusllists = new XTableView<PATERN_CERT>();
			
			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");
			cusllists.setMaxWidth(Double.MAX_VALUE);
			cusllists.setMaxHeight(Double.MAX_VALUE);
			
			XTableColumn<PATERN_CERT, Long> PC_ID = new XTableColumn<>("Номер");
			PC_ID.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
			
			PC_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			
			XTableColumn<PATERN_CERT, String> Father = new XTableColumn<>("Отец");
			XTableColumn<PATERN_CERT, String> FatherFio = new XTableColumn<>("ФИО");
			FatherFio.setCellValueFactory(new PropertyValueFactory<>("FatherFiO"));
			Father.getColumns().add(FatherFio);

			FatherFio.setColumnFilter(new PatternColumnFilter<>());
			
			XTableColumn<PATERN_CERT, String> Mother = new XTableColumn<>("Мать");
			XTableColumn<PATERN_CERT, String> MotherFio = new XTableColumn<>("ФИО");
			MotherFio.setCellValueFactory(new PropertyValueFactory<>("MotherFio"));
			Mother.getColumns().add(MotherFio);

			MotherFio.setColumnFilter(new PatternColumnFilter<>());
			
			XTableColumn<PATERN_CERT, String> Child = new XTableColumn<>("Ребенок");
			XTableColumn<PATERN_CERT, String> ChildFio = new XTableColumn<>("ФИО");
			ChildFio.setCellValueFactory(new PropertyValueFactory<>("ChildFio"));
			Child.getColumns().add(ChildFio);

			ChildFio.setColumnFilter(new PatternColumnFilter<>());
			
			XTableColumn<PATERN_CERT, LocalDateTime> PС_DATE = new XTableColumn<>("Дата документа");
			PС_DATE.setCellValueFactory(new PropertyValueFactory<>("PС_DATE"));

			cusllists.getColumns().add(PC_ID);
			cusllists.getColumns().add(Father);
			cusllists.getColumns().add(Mother);
			cusllists.getColumns().add(Child);
			cusllists.getColumns().add(PС_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(buttonBar);

			vb.setPadding(new Insets(10, 10, 10, 10));
			/**/
			PC_ID.setCellValueFactory(cellData -> cellData.getValue().PC_IDProperty().asObject());
			FatherFio.setCellValueFactory(cellData -> cellData.getValue().FATHERFIOProperty());
			ChildFio.setCellValueFactory(cellData -> cellData.getValue().CHILDFIOProperty());
			MotherFio.setCellValueFactory(cellData -> cellData.getValue().MOTHERFIOProperty());
			PС_DATE.setCellValueFactory(cellData -> cellData.getValue().TM$PС_DATEProperty());

			PС_DATE.setCellFactory(column -> {
				TableCell<PATERN_CERT, LocalDateTime> cell = new TableCell<PATERN_CERT, LocalDateTime>() {
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
			/* SelData */
			String selectStmt = "select * from v_patern_cert order by PC_ID desc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			ObservableList<PATERN_CERT> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				PATERN_CERT list = new PATERN_CERT();

				list.setPС_M(rs.getLong("PС_M"));
				list.setFATHERFIO(rs.getString("FATHERFIO"));
				list.setPС_AFT_FNAME(rs.getString("PС_AFT_FNAME"));
				list.setPC_ZPLACE(rs.getString("PC_ZPLACE"));
				list.setPС_NUMBER(rs.getString("PС_NUMBER"));
				list.setPС_TRZ((rs.getDate("PС_TRZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_TRZ")), formatter)
						: null);
				list.setMOTHERBIRTHDATE((rs.getDate("MOTHERBIRTHDATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("MOTHERBIRTHDATE")), formatter) : null);
				list.setPC_ZLNAME(rs.getString("PC_ZLNAME"));
				list.setMOTHERFIO(rs.getString("MOTHERFIO"));
				list.setPС_CH(rs.getLong("PС_CH"));
				list.setCHILDFIO(rs.getString("CHILDFIO"));
				list.setPС_FZ((rs.getDate("PС_FZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_FZ")), formatter)
						: null);
				list.setPC_ID(rs.getLong("PC_ID"));
				list.setPC_ACT_ID(rs.getLong("PC_ACT_ID"));
				list.setPC_ZMNAME(rs.getString("PC_ZMNAME"));
				list.setCHILDRENBIRTH((rs.getDate("CHILDRENBIRTH") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CHILDRENBIRTH")), formatter) : null);
				list.setPС_TYPE(rs.getString("PС_TYPE"));
				list.setPС_AFT_MNAME(rs.getString("PС_AFT_MNAME"));
				list.setPС_CRDATE((rs.getDate("PС_CRDATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_CRDATE")), formatter)
						: null);
				list.setPС_F(rs.getLong("PС_F"));
				list.setPС_ZAGS(rs.getLong("PС_ZAGS"));
				list.setPС_SERIA(rs.getString("PС_SERIA"));
				list.setPC_ZFNAME(rs.getString("PC_ZFNAME"));
				list.setFATHERBIRTHDATE((rs.getDate("FATHERBIRTHDATE") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("FATHERBIRTHDATE")), formatter) : null);
				list.setPС_USER(rs.getString("PС_USER"));
				list.setPС_AFT_LNAME(rs.getString("PС_AFT_LNAME"));
				list.setTM$PС_DATE((rs.getDate("TM$PС_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$PС_DATE")), formatterdt)
						: null);
				list.setPС_CRNAME(rs.getString("PС_CRNAME"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			// Main.autoResizeColumns(cusllists);

			cusllists.setPrefWidth(700);
			cusllists.setPrefHeight(400);

			// ICUSNUM.setPrefWidth(100);
			// DCUSBIRTHDAY.setPrefWidth(120);
			// DCUSBIRTHDAY.setPrefWidth(120);

			TableFilter<PATERN_CERT> CUSFilter = TableFilter.forTableView(cusllists).apply();
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
						PATERN_CERT country = cusllists.getSelectionModel().getSelectedItem();
						// ID.setText(country.getMERCER_ID());
						ID.setText(String.valueOf(country.getPC_ID()));

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

//			secondaryLayout.getChildren().add(vb);
			secondaryLayout.setCenter(vb);
			// secondaryLayout.getChildren().add(cusllists);

			// VBox vbox = new VBox(debtinfo);
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE,
					Control.USE_COMPUTED_SIZE);/* Control.USE_COMPUTED_SIZE */

			Stage newWindow = new Stage();
			newWindow.setTitle("Акты об установлении отцовства");
			newWindow.setScene(secondScene);
			newWindow.setResizable(true);
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
	
	/**
	 * Список умерших
	 * @param ID
	 * @param stage
	 * @param conn
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void FindDeath(TextField ID ,Stage stage,Connection conn) {
		try {
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			
			Button Update = new Button();
			Update.setText("Выбрать");
			BorderPane secondaryLayout = new BorderPane();

			VBox vb = new VBox();
			
			ButtonBar buttonBar = new ButtonBar();
			buttonBar.setPadding(new Insets(10,0,0,0));
			ButtonBar.setButtonData(Update, ButtonData.APPLY);
			buttonBar.getButtons().addAll(Update);
			
			
			XTableView<DEATH_CERT> cusllists = new XTableView<DEATH_CERT>();
			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");
			
			cusllists.setMaxWidth(Double.MAX_VALUE);
			cusllists.setMaxHeight(Double.MAX_VALUE);
			
			XTableColumn<DEATH_CERT, Long> DC_ID = new XTableColumn<>("Номер");
			DC_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			
			DC_ID.setCellValueFactory(new PropertyValueFactory<>("DC_ID"));
			XTableColumn<DEATH_CERT, String> DieFio = new XTableColumn<>("ФИО");
			
			DieFio.setCellValueFactory(new PropertyValueFactory<>("DieFio"));
			XTableColumn<DEATH_CERT, LocalDate> DC_DD = new XTableColumn<>("Дата смерти");
			
			DC_DD.setCellValueFactory(new PropertyValueFactory<>("DC_DD"));
			cusllists.getColumns().add(DC_ID);
			cusllists.getColumns().add(DieFio);
			cusllists.getColumns().add(DC_DD);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(buttonBar);
			vb.setPadding(new Insets(10, 10, 10, 10));

			DieFio.setColumnFilter(new PatternColumnFilter<>());
			DC_DD.setColumnFilter(new DateColumnFilter<>());
			
			DC_ID.setCellValueFactory(cellData -> cellData.getValue().DC_IDProperty().asObject());
			DieFio.setCellValueFactory(cellData -> cellData.getValue().DFIOProperty());
			DC_DD.setCellValueFactory(cellData -> cellData.getValue().DC_DDProperty());

			DC_DD.setCellFactory(column -> {
				TableCell<DEATH_CERT, LocalDate> cell = new TableCell<DEATH_CERT, LocalDate>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

					@Override
					protected void updateItem(LocalDate item, boolean empty) {
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
			String selectStmt = "select t.*, (select c.ccusname from CUS c where c.icusnum = t.dc_cus) DFIO \n"
					+ "  from DEATH_CERT t order by DC_ID desc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DEATH_CERT> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				DEATH_CERT list = new DEATH_CERT();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				//DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				list.setDFIO(rs.getString("DFIO"));
				list.setDC_ID(rs.getLong("DC_ID"));
				list.setDC_CUS(rs.getLong("DC_CUS"));
				list.setDC_DD((rs.getDate("DC_DD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_DD")), formatter)
						: null);
				list.setDC_DPL(rs.getString("DC_DPL"));
				list.setDC_CD(rs.getString("DC_CD"));
				list.setDC_FNUM(rs.getString("DC_FNUM"));
				list.setDC_FD((rs.getDate("DC_FD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DC_FD")), formatter)
						: null);
				list.setDC_FTYPE(rs.getString("DC_FTYPE"));
				list.setDC_FMON(rs.getString("DC_FMON"));
				list.setDC_RCNAME(rs.getLong("DC_RCNAME"));
				list.setDC_NRNAME(rs.getString("DC_NRNAME"));
				list.setDC_LLOC(rs.getString("DC_LLOC"));
				list.setDC_ZTP(rs.getString("DC_ZTP"));
				list.setDC_FADFIRST_NAME(rs.getString("DC_FADFIRST_NAME"));
				list.setDC_FADLAST_NAME(rs.getString("DC_FADLAST_NAME"));
				list.setDC_FADMIDDLE_NAME(rs.getString("DC_FADMIDDLE_NAME"));
				list.setDC_FADLOCATION(rs.getString("DC_FADLOCATION"));
				list.setDC_FADORG_NAME(rs.getString("DC_FADORG_NAME"));
				list.setDC_FADREG_ADR(rs.getString("DC_FADREG_ADR"));
				list.setDC_SERIA(rs.getString("DC_SERIA"));
				list.setDC_NUMBER(rs.getString("DC_NUMBER"));
				list.setDC_USR(rs.getString("DC_USR"));
				/*list.setTM$DC_OPEN((rs.getDate("TM$DC_OPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DC_OPEN")), formatterdt) : null);*/
				list.setDC_ZAGS(rs.getLong("DC_ZAGS"));

				cuslist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			cusllists.setPrefWidth(700);
			cusllists.setPrefHeight(400);

//			DC_ID.setPrefWidth(100);
//			DieFio.setPrefWidth(120);
//			DC_DD.setPrefWidth(120);

			TableFilter<DEATH_CERT> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите данные из таблицы!");
					} else {
						DEATH_CERT country = cusllists.getSelectionModel().getSelectedItem();
						// name.setText(country.getCCUSNAME());
						ID.setText(String.valueOf(country.getDC_ID()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

//			secondaryLayout.getChildren().add(vb);
			
			secondaryLayout.setCenter(vb);
			
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);

			Stage newWindow = new Stage();
			newWindow.setTitle("Свидетельства о смерти");
			newWindow.setScene(secondScene);
			newWindow.setResizable(true);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	/**
	 * Выбор списка Свидетельство о расторжении брака
	 * @param number
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void FindDivorce(TextField ID ,Stage stage,Connection conn) {
		try {
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			Button Update = new Button();
			Update.setText("Выбрать");
			BorderPane secondaryLayout = new BorderPane();

			VBox vb = new VBox();
			ButtonBar buttonBar = new ButtonBar();
			buttonBar.setPadding(new Insets(10,0,0,0));
			ButtonBar.setButtonData(Update, ButtonData.APPLY);
			buttonBar.getButtons().addAll(Update);
			
			XTableView<DIVORCE_CERT> cusllists = new XTableView<DIVORCE_CERT>();
			cusllists.getStyleClass().add("mylistview");
			cusllists.getStylesheets().add("/ScrPane.css");
			
			cusllists.setMaxWidth(Double.MAX_VALUE);
			cusllists.setMaxHeight(Double.MAX_VALUE);
			
			XTableColumn<DIVORCE_CERT, Long> DIVC_ID = new XTableColumn<>("Номер");
			DIVC_ID.setCellValueFactory(new PropertyValueFactory<>("DIVC_ID"));
			
			DIVC_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));
			
			XTableColumn<DIVORCE_CERT, String> SheFio = new XTableColumn<>("Ее ФИО");
			SheFio.setCellValueFactory(new PropertyValueFactory<>("SheFio"));
			
			SheFio.setColumnFilter(new PatternColumnFilter<>());
			
			XTableColumn<DIVORCE_CERT, String> HeFio = new XTableColumn<>("Его ФИО");
			HeFio.setCellValueFactory(new PropertyValueFactory<>("HeFio"));
			
			HeFio.setColumnFilter(new PatternColumnFilter<>());
			
			XTableColumn<DIVORCE_CERT, LocalDateTime> DIVC_DATE = new XTableColumn<>("Дата документа");
			DIVC_DATE.setCellValueFactory(new PropertyValueFactory<>("DIVC_DATE"));
			
			cusllists.getColumns().add(DIVC_ID);
			cusllists.getColumns().add(SheFio);
			cusllists.getColumns().add(HeFio);
			cusllists.getColumns().add(DIVC_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(buttonBar);
			vb.setPadding(new Insets(10, 10, 10, 10));

			DIVC_ID.setCellValueFactory(cellData -> cellData.getValue().DIVC_IDProperty().asObject());
			SheFio.setCellValueFactory(cellData -> cellData.getValue().SHEFIOProperty());
			HeFio.setCellValueFactory(cellData -> cellData.getValue().HEFIOProperty());
			DIVC_DATE.setCellValueFactory(cellData -> cellData.getValue().TM$DIVC_DATEProperty());

			DIVC_DATE.setCellFactory(column -> {
				TableCell<DIVORCE_CERT, LocalDateTime> cell = new TableCell<DIVORCE_CERT, LocalDateTime>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

			String selectStmt = "select (select g.ccusname from cus g where g.icusnum = t.divc_he) HeFio,\r\n"
					+ "       (select g.ccusname from cus g where g.icusnum = t.divc_she) SheFio,\r\n"
					+ "       t.*\r\n" + "  from DIVORCE_CERT t order by DIVC_ID desc";

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DIVORCE_CERT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				DIVORCE_CERT list = new DIVORCE_CERT();

				list.setHEFIO(rs.getString("HeFio"));
				list.setSHEFIO(rs.getString("SheFio"));
				list.setDIVC_ID(rs.getLong("DIVC_ID"));
				list.setDIVC_HE(rs.getLong("DIVC_HE"));
				list.setDIVC_SHE(rs.getLong("DIVC_SHE"));
				list.setDIVC_HE_LNBEF(rs.getString("DIVC_HE_LNBEF"));
				list.setDIVC_HE_LNAFT(rs.getString("DIVC_HE_LNAFT"));
				list.setDIVC_SHE_LNBEF(rs.getString("DIVC_SHE_LNBEF"));
				list.setDIVC_SHE_LNAFT(rs.getString("DIVC_SHE_LNAFT"));
				list.setTM$DIVC_DATE((rs.getDate("DIVC_DATE") != null) ? LocalDateTime
						.parse(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DIVC_DATE")), formatterdt)
						: null);
				list.setDIVC_DT((rs.getDate("DIVC_DT") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_DT")), formatter)
						: null);
				list.setDIVC_USR(rs.getString("DIVC_USR"));
				list.setDIVC_TYPE(rs.getString("DIVC_TYPE"));
				list.setDIVC_TCHD((rs.getDate("DIVC_TCHD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_TCHD")), formatter)
						: null);
				list.setDIVC_TCHNUM(rs.getString("DIVC_TCHNUM"));
				list.setDIVC_CAN(rs.getLong("DIVC_CAN"));
				list.setDIVC_CAD((rs.getDate("DIVC_CAD") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_CAD")), formatter)
						: null);
				list.setDIVC_ZOSCN(rs.getLong("DIVC_ZOSCN"));
				list.setDIVC_ZOSCD((rs.getDate("DIVC_ZOSCD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD")), formatter) : null);
				list.setDIVC_ZOSFIO(rs.getString("DIVC_ZOSFIO"));
				list.setDIVC_ZOSCN2(rs.getLong("DIVC_ZOSCN2"));
				list.setDIVC_ZOSCD2((rs.getDate("DIVC_ZOSCD2") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DIVC_ZOSCD2")), formatter) : null);
				list.setDIVC_ZOSFIO2(rs.getString("DIVC_ZOSFIO2"));
				list.setDIVC_ZOSPRISON(rs.getLong("DIVC_ZOSPRISON"));
				list.setDIVC_MC_MERCER(rs.getLong("DIVC_MC_MERCER"));
				list.setDIVC_NUM(rs.getString("DIVC_NUM"));
				list.setDIVC_SERIA(rs.getString("DIVC_SERIA"));
				list.setDIVC_ZAGS(rs.getLong("DIVC_ZAGS"));
				list.setDIVC_ZMNAME(rs.getString("DIVC_ZMNAME"));
				list.setDIVC_ZLNAME(rs.getString("DIVC_ZLNAME"));
				list.setDIVC_ZPLACE(rs.getString("DIVC_ZPLACE"));
				list.setDIVC_ZАNAME(rs.getString("DIVC_ZАNAME"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(dlist);

			cusllists.setPrefWidth(700);
			cusllists.setPrefHeight(400);

//			DIVC_ID.setPrefWidth(100);
//			SheFio.setPrefWidth(120);
//			HeFio.setPrefWidth(120);
//			DIVC_DATE.setPrefWidth(120);

			TableFilter<DIVORCE_CERT> CUSFilter = TableFilter.forTableView(cusllists).apply();
			CUSFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			Update.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (cusllists.getSelectionModel().getSelectedItem() == null) {
						Msg.Message("Выберите данные из таблицы!");
					} else {
						DIVORCE_CERT country = cusllists.getSelectionModel().getSelectedItem();
						// num.setText(country.getCCUSNAME());
						ID.setText(String.valueOf(country.getDIVC_ID()));
						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

//			secondaryLayout.getChildren().add(vb);
			
			secondaryLayout.setCenter(vb);
			
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);

			Stage newWindow = new Stage();
			newWindow.setTitle("Свидетельство о расторжении брака");
			newWindow.setScene(secondScene);
			newWindow.setResizable(true);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	/**
	 * Создать свид. о рождении
	 * 
	 * @param event
	 */
	public void Add_Brn(TextField id, Stage stage_, Connection conn) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/doc/birthact/IUBirthAct.fxml"));

			AddBirthAct controller = new AddBirthAct();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Создание записи");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						id.setText(String.valueOf(controller.getId()));
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
