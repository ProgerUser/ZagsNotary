package mj.doc.birthact;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ActionType;
import com.jyloo.syntheticafx.Dialog;
import com.jyloo.syntheticafx.DialogFactory;
import com.jyloo.syntheticafx.SyntheticaFX;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.doc.mercer.MC_MERCER;
import mj.doc.patern.PATERN_CERT;
import mj.init.Decorator;
import mj.init.Validation;
import mj.init.Validator;
import mj.init.Validity;
import mj.msg.Msg;
import mj.util.ConvConst;

public class AddBirthAct {

	@FXML
	void MotherAlone(ActionEvent event) {
		try {
			if (MotherAlone.isSelected()) {
				IfMNotAlone.setVisible(false);
				IFMAL_F_LAST_NAME.setVisible(true);

				FatherName.setText("");
				FatherCusId.setText("");
			} else {
				IfMNotAlone.setVisible(true);
				IFMAL_F_LAST_NAME.setVisible(false);

				IFMAL_F_LAST_NAME.setText("");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
    
    @FXML
    private TextField IFMAL_F_LAST_NAME;

    @FXML
    private GridPane IfMNotAlone;
    
    @FXML
    private CheckBox MotherAlone;
    
    @FXML
    private TextField DOC_NUMBER;
    
    
	@FXML
	private TextField FADFIRST_NAME;

	@FXML
	private DatePicker BIRTH_ACT_ZM;

	@FXML
	private TitledPane osnvosst;

	@FXML
	private TitledPane father;

	@FXML
	private TextField ChildCnt;

	@FXML
	private TextField MEDORG_A;

	@FXML
	private TextField NAME_COURT;

	@FXML
	private ComboBox<String> FatherType;

	@FXML
	private ComboBox<String> BR_ACT_DBF;

	@FXML
	private DatePicker DATEDOCB_B;

	@FXML
	private DatePicker DATEDOCB_A;

	@FXML
	private TextField FADLOCATION;

	@FXML
	private TitledPane printdoc;

	@FXML
	private TextField FADORG_NAME;

	@FXML
	private TitledPane mother;

	@FXML
	private TextField FIO_B;

	@FXML
	private TitledPane children;

	@FXML
	private TextField MotherName;

	@FXML
	private Button SAVE;

	@FXML
	private TitledPane osndoc;

	@FXML
	private TextField ChildName;

	@FXML
	private GridPane FIZIK;

	@FXML
	private TextField FADMIDDLE_NAME;

	@FXML
	private TitledPane sved_thaver;

	@FXML
	private TextField MARR_CER_ID;

	@FXML
	private TextField NUM;

	@FXML
	private TextField DESC_C;

	@FXML
	private TextField SERIA;

	@FXML
	private TextField PAT_CER_ID;

	@FXML
	private TextField FADREG_ADR;

	@FXML
	private TextField ChildCusId;

	@FXML
	private TextField NDOC_A;

	@FXML
	private GridPane JURIK;

	@FXML
	private GridPane IF2;

	@FXML
	private TextField FADLAST_NAME;

	@FXML
	private GridPane IF1;

	@FXML
	private GridPane IF3;

	@FXML
	private ComboBox<String> BIRTH_ACT_TYPE;

	@FXML
	private ComboBox<String> LD;

	@FXML
	private DatePicker DCOURT;

	@FXML
	private TextField MotherCusId;

	@FXML
	private TextField FatherCusId;

	@FXML
	private TextField FatherName;

	@FXML
	private TitledPane fiozaiav;

	@FXML
	private GridPane FIOLPVR;

	@FXML
	private GridPane DUFOR;

	/**
	 * Метод {@link BR_ACT_DBF} для изменения видимости {@link FIOLPVR} и
	 * {@link DUFOR}
	 */
	@FXML
	void BR_ACT_DBF(ActionEvent event) {
		if (BR_ACT_DBF.getValue().equals("Документ установленной формы о рождении")) {

			FIOLPVR.setVisible(false);
			DUFOR.setVisible(true);

			FIO_B.setText("");
			DATEDOCB_B.setValue(null);
			NDOC_A.setText("");
			DATEDOCB_A.setValue(null);
			MEDORG_A.setText("");

		} else if (BR_ACT_DBF.getValue().equals("Заявление")) {

			FIO_B.setText("");
			DATEDOCB_B.setValue(null);
			NDOC_A.setText("");
			DATEDOCB_A.setValue(null);
			MEDORG_A.setText("");

			FIOLPVR.setVisible(true);
			DUFOR.setVisible(false);

		}
	}

	@FXML
	void FindChild(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(ChildName, ChildCusId, (Stage) ChildCusId.getScene().getWindow());
	}

	@FXML
	void AddChild(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(ChildName, ChildCusId, (Stage) FatherCusId.getScene().getWindow(), conn);
	}

	@FXML
	void FindFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(FatherName, FatherCusId, (Stage) ChildCusId.getScene().getWindow());
	}

	@FXML
	void FindMerCert(ActionEvent event) {
		//Mercer(MARR_CER_ID);
		UtilCus cus = new UtilCus();
		cus.FindMercer(MARR_CER_ID, (Stage) MARR_CER_ID.getScene().getWindow(), conn);
	}

	@FXML
	void AddMerCert(ActionEvent event) {

	}

	@FXML
	void FindPat(ActionEvent event) {
//		Patern(PAT_CER_ID);
		UtilCus cus = new UtilCus();
		cus.FindPatern(PAT_CER_ID, (Stage) PAT_CER_ID.getScene().getWindow(), conn);
	}

	@FXML
	void AddPat(ActionEvent event) {

	}

	/**
	 * Найти установление отцовства
	 * 
	 * @param ID
	 */
	void Patern(TextField ID) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			// Update.setLayoutX(30.0);
			// Update.setLayoutY(450.0);
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<PATERN_CERT> cusllists = new TableView<PATERN_CERT>();
			TableColumn<PATERN_CERT, Integer> PC_ID = new TableColumn<>("Номер");
			PC_ID.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));

			TableColumn<PATERN_CERT, String> Father = new TableColumn<>("Отец");
			TableColumn<PATERN_CERT, String> FatherFio = new TableColumn<>("ФИО");
			FatherFio.setCellValueFactory(new PropertyValueFactory<>("FatherFiO"));
			Father.getColumns().add(FatherFio);

			TableColumn<PATERN_CERT, String> Mother = new TableColumn<>("Мать");
			TableColumn<PATERN_CERT, String> MotherFio = new TableColumn<>("ФИО");
			MotherFio.setCellValueFactory(new PropertyValueFactory<>("MotherFio"));
			Mother.getColumns().add(MotherFio);

			TableColumn<PATERN_CERT, String> Child = new TableColumn<>("Ребенок");
			TableColumn<PATERN_CERT, String> ChildFio = new TableColumn<>("ФИО");
			ChildFio.setCellValueFactory(new PropertyValueFactory<>("ChildFio"));
			Child.getColumns().add(ChildFio);

			TableColumn<PATERN_CERT, LocalDateTime> PС_DATE = new TableColumn<>("Дата документа");
			PС_DATE.setCellValueFactory(new PropertyValueFactory<>("PС_DATE"));

			cusllists.getColumns().add(PC_ID);
			cusllists.getColumns().add(Father);
			cusllists.getColumns().add(Mother);
			cusllists.getColumns().add(Child);
			cusllists.getColumns().add(PС_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);

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

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

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

			secondaryLayout.getChildren().add(vb);
			// secondaryLayout.getChildren().add(cusllists);

			// VBox vbox = new VBox(debtinfo);
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE,
					Control.USE_COMPUTED_SIZE);/* Control.USE_COMPUTED_SIZE */
			Stage stage = (Stage) SAVE.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Акты об установлении отцовства");
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

	/**
	 * Найти заключения брака
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void Mercer(TextField ID) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			// Update.setLayoutX(30.0);
			// Update.setLayoutY(450.0);
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<MC_MERCER> cusllists = new TableView<MC_MERCER>();
			TableColumn<MC_MERCER, Integer> MERCER_ID = new TableColumn<>("Номер");
			MERCER_ID.setCellValueFactory(new PropertyValueFactory<>("MERCER_ID"));

			TableColumn<MC_MERCER, String> HE = new TableColumn<>("Он");
			TableColumn<MC_MERCER, String> HeFio = new TableColumn<>("ФИО");
			HeFio.setCellValueFactory(new PropertyValueFactory<>("HeFio"));
			HE.getColumns().add(HeFio);

			TableColumn<MC_MERCER, String> SHE = new TableColumn<>("Она");
			TableColumn<MC_MERCER, String> SheFio = new TableColumn<>("ФИО");
			SheFio.setCellValueFactory(new PropertyValueFactory<>("SheFio"));
			SHE.getColumns().add(SheFio);

			TableColumn<MC_MERCER, LocalDateTime> MERCER_DATE = new TableColumn<>("Дата документа");
			MERCER_DATE.setCellValueFactory(new PropertyValueFactory<>("MERCER_DATE"));

			cusllists.getColumns().add(MERCER_ID);
			cusllists.getColumns().add(HE);
			cusllists.getColumns().add(SHE);
			cusllists.getColumns().add(MERCER_DATE);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);

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

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

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

			secondaryLayout.getChildren().add(vb);
			// secondaryLayout.getChildren().add(cusllists);

			// VBox vbox = new VBox(debtinfo);
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE,
					Control.USE_COMPUTED_SIZE);/* Control.USE_COMPUTED_SIZE */
			Stage stage = (Stage) SAVE.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Акты о заключении брака");
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

	/**
	 * Найти гражданина
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void CusList(ActionEvent event, TextField num, TextField name) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
			// Update.setLayoutX(30.0);
			// Update.setLayoutY(450.0);
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Integer> ICUSNUM = new TableColumn<>("Номер");
			ICUSNUM.setCellValueFactory(new PropertyValueFactory<>("ICUSNUM"));
			TableColumn<CUS, String> CCUSNAME = new TableColumn<>("ФИО");
			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));
			TableColumn<CUS, LocalDate> DCUSBIRTHDAY = new TableColumn<>("Дата рождения");
			DCUSBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("DCUSBIRTHDAY"));
			cusllists.getColumns().add(ICUSNUM);
			cusllists.getColumns().add(CCUSNAME);
			cusllists.getColumns().add(DCUSBIRTHDAY);

			vb.getChildren().add(cusllists);
			vb.getChildren().add(toolBar);

			vb.setPadding(new Insets(10, 10, 10, 10));
			/**/
			ICUSNUM.setCellValueFactory(cellData -> cellData.getValue().ICUSNUMProperty().asObject());
			CCUSNAME.setCellValueFactory(cellData -> cellData.getValue().CCUSNAMEProperty());
			DCUSBIRTHDAY.setCellValueFactory(cellData -> cellData.getValue().DCUSBIRTHDAYProperty());
			DCUSBIRTHDAY.setCellFactory(column -> {
				TableCell<CUS, LocalDate> cell = new TableCell<CUS, LocalDate>() {
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
			/* SelData */
			String selectStmt = "select * from cus";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<CUS> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				CUS cus = new CUS();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				String DCUSBIRTHDAYt = new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY"));
				cus.setICUSNUM(rs.getInt("ICUSNUM"));
				cus.setCCUSNAME(rs.getString("CCUSNAME"));
				cus.setDCUSBIRTHDAY(LocalDate.parse(DCUSBIRTHDAYt, formatter));
				cuslist.add(cus);
			}
			prepStmt.close();
			rs.close();

			cusllists.setItems(cuslist);

			// Main.autoResizeColumns(cusllists);

			cusllists.setPrefWidth(500);
			cusllists.setPrefHeight(350);

			ICUSNUM.setPrefWidth(100);
			DCUSBIRTHDAY.setPrefWidth(120);
			DCUSBIRTHDAY.setPrefWidth(120);

			TableFilter<CUS> CUSFilter = TableFilter.forTableView(cusllists).apply();
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
						CUS country = cusllists.getSelectionModel().getSelectedItem();
						num.setText(country.getCCUSNAME());
						name.setText(String.valueOf(country.getICUSNUM()));

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}

			});

			secondaryLayout.getChildren().add(vb);
			// secondaryLayout.getChildren().add(cusllists);

			// VBox vbox = new VBox(debtinfo);
			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE,
					Control.USE_COMPUTED_SIZE);/* Control.USE_COMPUTED_SIZE */
			Stage stage = (Stage) SAVE.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список граждан");
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

	/**
	 * Добавить отца
	 * 
	 * @param event
	 */
	@FXML
	void AddFather(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(FatherName, FatherCusId, (Stage) FatherCusId.getScene().getWindow(), conn);
	}

	/**
	 * Найти мать
	 * 
	 * @param event
	 */
	@FXML
	void FindMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(MotherName, MotherCusId, (Stage) ChildCusId.getScene().getWindow());
	}

	/**
	 * Добавить мать
	 * 
	 * @param event
	 */
	@FXML
	void AddMother(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(MotherName, MotherCusId, (Stage) FatherCusId.getScene().getWindow(), conn);
	}

	/********************/

	/**
	 * Сохранить
	 * 
	 * @param event
	 */
	@FXML
	void SAVE(ActionEvent event) {
		try {
			// ре - валидация 
			ch_cnt_val.revalidate();
			ld_val.revalidate();
			ch_cus_id_val.revalidate();
			m_cus_id_val.revalidate();
			
			if(!MotherAlone.isSelected()) {
				f_cus_id_val.revalidate();
			}
			
			BR_ACT_DBF_VAL.revalidate();
			SERIA_VAL.revalidate();
			NUM_VAL.revalidate();
			FatherType_val.revalidate();
			// проверка
			if (!NUM_VAL.isValid() | !SERIA_VAL.isValid() | !BR_ACT_DBF_VAL.isValid() | !ch_cnt_val.isValid()
					| !ld_val.isValid() | !ch_cus_id_val.isValid() | !m_cus_id_val.isValid()
					| !f_cus_id_val.isValid()|!FatherType_val.isValid()) {
				// показать диалог с ошибкой
				Dialog<ActionType> d = DialogFactory.createError(null, "Не все поля заполнены!", null, "Ошибка");
				d.showAndWait();
				return;
			}
			//___________________________________________
			
			/* Вызов функции CRE_BURN из пакета BIRN_UTIL */
			{
				CallableStatement callStmt = conn.prepareCall(
						"{ ? = call BURN_UTIL.CRE_BURN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				callStmt.registerOutParameter(1, Types.VARCHAR);
				/* Тип заявителя.F-физик.-J-юрик */
				if (BIRTH_ACT_TYPE.getValue() != null) {
					if (BIRTH_ACT_TYPE.getValue().equals("Физ. лицо")) {
						callStmt.setString(2, "F");
					} else if (BIRTH_ACT_TYPE.getValue().equals("Организация")) {
						callStmt.setString(2, "J");
					}
				} else {
					callStmt.setString(2, null);
				}
				/* Количество родившихся детей */
				if (!ChildCnt.getText().equals("")) {
					callStmt.setInt(3, Integer.valueOf(ChildCnt.getText()));
				} else {
					callStmt.setNull(3, java.sql.Types.INTEGER);
				}
				/* Живорожденный или мертворожденный */
				if (LD.getValue() != null) {
					if (LD.getValue().equals("Живорожденный")) {
						callStmt.setString(4, "L");
					} else if (LD.getValue().equals("Мертворожденный")) {
						callStmt.setString(4, "D");
					}
				} else {
					callStmt.setString(4, null);
				}
				// Тип основании сведении об отце
				if (FatherType.getValue() != null) {
					if (FatherType.getValue().equals("Свидетельства о заключении брака")) {
						callStmt.setString(5, "A");
					} else if (FatherType.getValue().equals("Свидетельства об установлении отцовства")) {
						callStmt.setString(5, "B");
					} else if (FatherType.getValue().equals("Заявления матери")) {
						callStmt.setString(5, "V");
					}
				} else {
					callStmt.setString(5, null);
				}

				/* Дата заявления от матери, если (FatherType = V) */
				callStmt.setDate(6,
						(BIRTH_ACT_ZM.getValue() != null) ? java.sql.Date.valueOf(BIRTH_ACT_ZM.getValue()) : null);
				/* Документ подтверждающий факт о рождении ребенка, тип */
				if (BR_ACT_DBF.getValue() != null) {
					if (BR_ACT_DBF.getValue().equals("Документ установленной формы о рождении")) {
						callStmt.setString(7, "A");
					} else if (BR_ACT_DBF.getValue().equals("Заявление")) {
						callStmt.setString(7, "B");
					}
				} else {
					callStmt.setString(7, null);
				}
				/* Ссылка на ребенка */
				if (!ChildCusId.getText().equals("")) {
					callStmt.setInt(8, Integer.valueOf(ChildCusId.getText()));
				} else {
					callStmt.setNull(8, java.sql.Types.INTEGER);
				}
				/* Ссылка на отца */
				if (!FatherCusId.getText().equals("")) {
					callStmt.setInt(9, Integer.valueOf(FatherCusId.getText()));
				} else {
					callStmt.setNull(9, java.sql.Types.INTEGER);
				}
				/* Ссылка на мать */
				if (!MotherCusId.getText().equals("")) {
					callStmt.setInt(10, Integer.valueOf(MotherCusId.getText()));
				} else {
					callStmt.setNull(10, java.sql.Types.INTEGER);
				}
				/* Наименование мед. организации выдавший документ (A-Док. уст. формы) */
				callStmt.setString(11, MEDORG_A.getText());
				/* Дата документа (A-Док. уст. формы) */
				callStmt.setDate(12,
						(DATEDOCB_A.getValue() != null) ? java.sql.Date.valueOf(DATEDOCB_A.getValue()) : null);
				/* Номер документа (A-Док. уст. формы) */
				callStmt.setString(13, NDOC_A.getText());
				/* ФИО лица присутствовавшего во время родов (Б-Заявление) */
				callStmt.setString(14, FIO_B.getText());
				/* Дата документа (Б-Заявление) */
				callStmt.setDate(15,
						(DATEDOCB_B.getValue() != null) ? java.sql.Date.valueOf(DATEDOCB_B.getValue()) : null);
				/* Наименование суда */
				callStmt.setString(16, NAME_COURT.getText());
				/* Решение суда № */
				callStmt.setString(17, DESC_C.getText());
				/* Дата решения суда */
				callStmt.setDate(18, (DCOURT.getValue() != null) ? java.sql.Date.valueOf(DCOURT.getValue()) : null);
				/* Имя (Заявитель о рождении) */
				callStmt.setString(19, FADFIRST_NAME.getText());
				/* Фамилия (Заявитель о рождении) */
				callStmt.setString(20, FADLAST_NAME.getText());
				/* Отчество (Заявитель о рождении) */
				callStmt.setString(21, FADMIDDLE_NAME.getText());
				/* Место жительства (Заявитель о рождении) */
				callStmt.setString(22, FADLOCATION.getText());
				/* Наименование организации (Заявитель о рождении) */
				callStmt.setString(23, FADORG_NAME.getText());
				/* Адрес регистрации (Заявитель о рождении) */
				callStmt.setString(24, FADREG_ADR.getText());
				/* Ссылка на свидетельство о заключении брака */
				if (!MARR_CER_ID.getText().equals("")) {
					callStmt.setInt(25, Integer.valueOf(MARR_CER_ID.getText()));
				} else {
					callStmt.setNull(25, java.sql.Types.INTEGER);
				}
				/* Номер (печать ЗАГСа) */
				callStmt.setString(26, NUM.getText());
				/* Серия (печать ЗАГСа) */
				callStmt.setString(27, SERIA.getText());
				/* Ссылка на установлении отцовства */
				if (!PAT_CER_ID.getText().equals("")) {
					callStmt.setInt(28, Integer.valueOf(PAT_CER_ID.getText()));
				} else {
					callStmt.setNull(28, java.sql.Types.INTEGER);
				}
				callStmt.registerOutParameter(29, Types.INTEGER);

				callStmt.setString(30, DOC_NUMBER.getText());
				callStmt.setString(31, (MotherAlone.isSelected() ? "Y" : "N"));
				callStmt.setString(32, IFMAL_F_LAST_NAME.getText());
				/* Выполнить */
				callStmt.execute();
				String ret = callStmt.getString(1);
				Integer id = callStmt.getInt(29);
				if (ret.equals("ok")) {
					conn.commit();
					setStatus(true);
					setId(id);
					callStmt.close();
					onclose();
				} else {
					conn.rollback();
					setStatus(false);
					Stage stage_ = (Stage) SERIA.getScene().getWindow();
					Msg.ErrorView(stage_, "AddBrnAct", conn);
					callStmt.close();
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void LDACT(ActionEvent event) {

	}

	/**
	 * Форматирование DatePiker
	 * 
	 * @param DtPik
	 */
	void DateFormat(DatePicker DtPik) {
		DtPik.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * Только число
	 * 
	 * @param TxtFld
	 */
	void OnlyDigits(TextField TxtFld) {
		TxtFld.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					TxtFld.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	/**
	 * Первая буква заглавная
	 * 
	 * @param TxtFld
	 */
	void FirstWUpp(TextField TxtFld) {
		TxtFld.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue.length() > 0) {
				TxtFld.setText(upperCaseAllFirst(newValue));
			}
		});
	}

	/**
	 * Сессия {@link AddBirthAct}
	 */
	Connection conn = null;

	/**
	 * Открыть сессию
	 */
	void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddBirth");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Быстрый способ заполнения даты
	 * 
	 * @param dp
	 */
	void DateAutoComma(DatePicker dp) {
		DateTimeFormatter fastFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		dp.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate object) {
				return (object != null) ? object.format(defaultFormatter) : null;
			}

			@Override
			public LocalDate fromString(String string) {
				try {
					return LocalDate.parse(string, fastFormatter);
				} catch (DateTimeParseException dtp) {

				}

				return LocalDate.parse(string, defaultFormatter);
			}
		});
	}

	/**
	 * Отключить сессию
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private ScrollPane MainScroll;

	@SuppressWarnings("rawtypes")
	Validity ch_cnt_val;
	@SuppressWarnings("rawtypes")
	Validity ld_val;
	@SuppressWarnings("rawtypes")
	Validity ch_cus_id_val;
	@SuppressWarnings("rawtypes")
	Validity m_cus_id_val;
	@SuppressWarnings("rawtypes")
	Validity f_cus_id_val;

	@SuppressWarnings("rawtypes")
	Validity BR_ACT_DBF_VAL;

	@SuppressWarnings("rawtypes")
	Validity SERIA_VAL;
	@SuppressWarnings("rawtypes")
	Validity NUM_VAL;
	@SuppressWarnings("rawtypes")
	Validity FatherType_val;
	

	@FXML
	private void initialize() {
		try {
			//если открыт от формы гражданина
			
			ChildName.setText(getCusFio());
			//System.out.print(getCusId());
			ChildCusId.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);
			// Валидация
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");

			ch_cnt_val = Validation.installValidator(ChildCnt, Validator.createEmptyValidator(ChildCnt),
					Validation.Type.EAGER, Decorator.createWarningDecorator("Поле не может быть пустым!"));

			ld_val = Validation.installValidator(LD, Validator.createEmptyValidator(LD), Validation.Type.EAGER,
					Decorator.createWarningDecorator("Выберите тип!"));

			ch_cus_id_val = Validation.installValidator(ChildCusId, Validator.createEmptyValidator(ChildCusId),
					Validation.Type.EAGER, Decorator.createWarningDecorator("Выберите ИД ребенка!"));

			m_cus_id_val = Validation.installValidator(MotherCusId, Validator.createEmptyValidator(MotherCusId),
					Validation.Type.EAGER, Decorator.createWarningDecorator("Выберите ИД метери!"));

			f_cus_id_val = Validation.installValidator(FatherCusId, Validator.createEmptyValidator(FatherCusId),
					Validation.Type.EAGER, Decorator.createWarningDecorator("Выберите ИД отца!"));

			BR_ACT_DBF_VAL = Validation.installValidator(BR_ACT_DBF, Validator.createEmptyValidator(BR_ACT_DBF),
					Validation.Type.EAGER, Decorator.createWarningDecorator("Выберите тип!"));
			SERIA_VAL = Validation.installValidator(SERIA, Validator.createEmptyValidator(SERIA), Validation.Type.EAGER,
					Decorator.createWarningDecorator("Поле не может быть пустым!"));
			NUM_VAL = Validation.installValidator(NUM, Validator.createEmptyValidator(NUM), Validation.Type.EAGER,
					Decorator.createWarningDecorator("Поле не может быть пустым!"));
			
			FatherType_val = Validation.installValidator(FatherType, Validator.createEmptyValidator(FatherType), Validation.Type.EAGER,
					Decorator.createWarningDecorator("Выберите тип!"));
			// button.disableProperty().bind(v1.and(v2).not());
			// button.setOnAction(e -> {
			// boolean allValid = v1.and(v2).get();
			// System.out.println(allValid);
			// });
			// ch_cnt_val.revalidate();
			// SAVE.disableProperty().bind(ch_cnt_val.not());
			// ---------------
			DateAutoComma(BIRTH_ACT_ZM);
			DateAutoComma(DATEDOCB_B);
			DateAutoComma(DATEDOCB_A);
			DateAutoComma(DCOURT);
			/*
			 * children.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * father.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * mother.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * osndoc.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * osnvosst.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * sved_thaver.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * printdoc.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 * fiozaiav.heightProperty().addListener( (observable, oldValue, newValue) ->
			 * MainScroll.vvalueProperty().set(newValue.doubleValue()));
			 */

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MainScroll.setVvalue(0D);
				}
			});

			dbConnect();
			{
				OnlyDigits(ChildCnt);
				OnlyDigits(NDOC_A);
				OnlyDigits(DESC_C);
				OnlyDigits(MARR_CER_ID);
				OnlyDigits(PAT_CER_ID);
			}
			{
				BIRTH_ACT_TYPE.getItems().addAll("Физ. лицо", "Организация");
				LD.getItems().addAll("Живорожденный", "Мертворожденный");
			}
			{
				DateFormat(BIRTH_ACT_ZM);
				DateFormat(DATEDOCB_B);
				DateFormat(DCOURT);
				DateFormat(DATEDOCB_A);
			}

			{
				FirstWUpp(MEDORG_A);
				FirstWUpp(FIO_B);
				FirstWUpp(MEDORG_A);
				FirstWUpp(FADLAST_NAME);
				FirstWUpp(FADFIRST_NAME);
				FirstWUpp(FADMIDDLE_NAME);
				FirstWUpp(FADLOCATION);
				FirstWUpp(FADORG_NAME);
				FirstWUpp(FADREG_ADR);
			}
			FatherType.getItems().addAll("Свидетельства о заключении брака", "Свидетельства об установлении отцовства",
					"Заявления матери");
			BR_ACT_DBF.getItems().addAll("Документ установленной формы о рождении", "Заявление");
			
			new ConvConst().FormatDatePiker(BIRTH_ACT_ZM);
			new ConvConst().FormatDatePiker(DATEDOCB_B);
			new ConvConst().FormatDatePiker(DATEDOCB_A);
			new ConvConst().FormatDatePiker(DCOURT);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void chngcolour(ActionEvent event) {
	}

	public static String upperCaseAllFirst(String value) {
		char[] array = value.toCharArray();
		// Uppercase first letter.
		array[0] = Character.toUpperCase(array[0]);
		// Uppercase all letters that follow a whitespace character.
		for (int i = 1; i < array.length; i++) {
			if (Character.isWhitespace(array[i - 1])) {
				array[i] = Character.toUpperCase(array[i]);
			}
		}
		return new String(array);
	}

	@FXML
	void BIRTH_ACT_TYPE(ActionEvent event) {
		if (BIRTH_ACT_TYPE.getValue().equals("Физ. лицо")) {

			JURIK.setVisible(false);
			FIZIK.setVisible(true);

			FADLAST_NAME.setText("");
			FADFIRST_NAME.setText("");
			FADMIDDLE_NAME.setText("");
			FADLOCATION.setText("");
			FADORG_NAME.setText("");
			FADREG_ADR.setText("");

		} else if (BIRTH_ACT_TYPE.getValue().equals("Организация")) {

			FIZIK.setVisible(false);
			JURIK.setVisible(true);

			FADLAST_NAME.setText("");
			FADFIRST_NAME.setText("");
			FADMIDDLE_NAME.setText("");
			FADLOCATION.setText("");
			FADORG_NAME.setText("");
			FADREG_ADR.setText("");
		}
	}

	@FXML
	void FatherType(ActionEvent event) {
		if (FatherType.getValue().equals("Свидетельства о заключении брака")) {

			IF2.setVisible(false);
			IF3.setVisible(false);
			IF1.setVisible(true);

			MARR_CER_ID.setText("");
			PAT_CER_ID.setText("");
			BIRTH_ACT_ZM.setValue(null);

		} else if (FatherType.getValue().equals("Свидетельства об установлении отцовства")) {

			IF1.setVisible(false);
			IF3.setVisible(false);
			IF2.setVisible(true);

			MARR_CER_ID.setText("");
			PAT_CER_ID.setText("");
			BIRTH_ACT_ZM.setValue(null);

		} else if (FatherType.getValue().equals("Заявления матери")) {

			IF1.setVisible(false);
			IF2.setVisible(false);
			IF3.setVisible(true);

			MARR_CER_ID.setText("");
			PAT_CER_ID.setText("");
			BIRTH_ACT_ZM.setValue(null);

		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	void onclose() {
		/* ForClose */
		{
			Stage stage = (Stage) IF3.getScene().getWindow();
			// do what you have to do
			// stage.close();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}

	private BooleanProperty Status;
	private IntegerProperty Id;
	
//
//если открыт от гражданина
//
	private IntegerProperty CusId;
	private StringProperty CusFio;

	public void setCusId(Integer value) {
		this.CusId.set(value);
	}

	public void setCusFio(String value) {
		this.CusFio.set(value);
	}
	
	public Integer getCusId() {
		return this.CusId.get();
	}
	
	public String getCusFio() {
		return this.CusFio.get();
	}
//
// ---------------------------------------
//
	
	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Integer value) {
		this.Id.set(value);
	}

	public Integer getId() {
		return this.Id.get();
	}

	public AddBirthAct() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
		this.CusId = new SimpleIntegerProperty();
		this.CusFio = new SimpleStringProperty();
	}
}
