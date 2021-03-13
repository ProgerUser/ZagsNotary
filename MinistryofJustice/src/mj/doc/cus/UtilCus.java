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
			if (DBUtil.OdbAction(27) == 0) {
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
							sttmt.setInt(1, controller.getId());
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
			XTableColumn<MC_MERCER, Integer> MERCER_ID = new XTableColumn<>("Номер");
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
					+ "  from MC_MERCER t\n";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			ObservableList<MC_MERCER> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				MC_MERCER list = new MC_MERCER();

				list.setSHEFIO(rs.getString("SheFio"));
				list.setHEFIO(rs.getString("HeFio"));
				list.setMERCER_ID(rs.getInt("MERCER_ID"));
				list.setMERCER_HE(rs.getInt("MERCER_HE"));
				list.setMERCER_SHE(rs.getInt("MERCER_SHE"));
				list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
				list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
				list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
				list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
				list.setMERCER_HEAGE(rs.getInt("MERCER_HEAGE"));
				list.setMERCER_SHEAGE(rs.getInt("MERCER_SHEAGE"));
				list.setTM$MERCER_DATE((rs.getDate("MERCER_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("MERCER_DATE")), formatterdt)
						: null);
				list.setMERCER_USR(rs.getString("MERCER_USR"));
				list.setMERCER_ZAGS(rs.getInt("MERCER_ZAGS"));
				list.setMERCER_DIVSHE(rs.getInt("MERCER_DIVSHE"));
				list.setMERCER_DIVHE(rs.getInt("MERCER_DIVHE"));
				list.setMERCER_DSPMT_HE(rs.getString("MERCER_DSPMT_HE"));
				list.setMERCER_NUM(rs.getString("MERCER_NUM"));
				list.setMERCER_SERIA(rs.getString("MERCER_SERIA"));
				list.setMERCER_DIESHE(rs.getInt("MERCER_DIESHE"));
				list.setMERCER_DIEHE(rs.getInt("MERCER_DIEHE"));
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
			
			XTableColumn<PATERN_CERT, Integer> PC_ID = new XTableColumn<>("Номер");
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
			String selectStmt = "select * from v_patern_cert";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			ObservableList<PATERN_CERT> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				PATERN_CERT list = new PATERN_CERT();

				list.setPС_M(rs.getInt("PС_M"));
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
				list.setPС_CH(rs.getInt("PС_CH"));
				list.setCHILDFIO(rs.getString("CHILDFIO"));
				list.setPС_FZ((rs.getDate("PС_FZ") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_FZ")), formatter)
						: null);
				list.setPC_ID(rs.getInt("PC_ID"));
				list.setPC_ACT_ID(rs.getInt("PC_ACT_ID"));
				list.setPC_ZMNAME(rs.getString("PC_ZMNAME"));
				list.setCHILDRENBIRTH((rs.getDate("CHILDRENBIRTH") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CHILDRENBIRTH")), formatter) : null);
				list.setPС_TYPE(rs.getString("PС_TYPE"));
				list.setPС_AFT_MNAME(rs.getString("PС_AFT_MNAME"));
				list.setPС_CRDATE((rs.getDate("PС_CRDATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("PС_CRDATE")), formatter)
						: null);
				list.setPС_F(rs.getInt("PС_F"));
				list.setPС_ZAGS(rs.getInt("PС_ZAGS"));
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
