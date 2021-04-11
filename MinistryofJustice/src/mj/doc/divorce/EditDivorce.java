package mj.doc.divorce;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

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
import mj.courts.VCOURTS;
import mj.dbutil.DBUtil;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.doc.mercer.MC_MERCER;
import mj.msg.Msg;
import mj.util.ConvConst;

public class EditDivorce {

	@FXML
	private TextField DOC_NUMBER;
	
	@FXML
	private DatePicker DIVC_ZOSCD2;

	@FXML
	private TextField DIVC_HE_LNAFT;

	@FXML
	private TextField DIVC_SHE_LNAFT;

	@FXML
	private TextField HeFio;

	@FXML
	private DatePicker DIVC_CAD;

	@FXML
	private TextField DIVC_ZOSFIO2;

	@FXML
	private TextField DIVC_NUM;

	@FXML
	private TextField DIVC_TCHNUM;

	@FXML
	private TextField DIVC_ZPLACE;

	@FXML
	private GridPane DIV_A;

	@FXML
	private GridPane DIV_B;

	@FXML
	private ComboBox<String> DIVC_TYPE;

	@FXML
	private TextField SheFio;

	@FXML
	private TextField DIVC_ZOSPRISON;

	@FXML
	private TextField DIVC_SHE;

	@FXML
	private GridPane DIV_V2;

	@FXML
	private TextField DIVC_ZLNAME;

	@FXML
	private GridPane DIV_V1;

	@FXML
	private DatePicker DIVC_DT;

	@FXML
	private DatePicker DIVC_TCHD;

	@FXML
	private TextField DIVC_MC_MERCER;

	@FXML
	private TextField DIVC_ZMNAME;

	@FXML
	private TextField DIVC_ZАNAME;

	@FXML
	private ComboBox<VCOURTS> DIVC_CAN;

	@FXML
	private DatePicker DIVC_ZOSCD;

	@FXML
	private TextField DIVC_SERIA;

	@FXML
	private TextField DIVC_HE_LNBEF;

	@FXML
	private ComboBox<VCOURTS> DIVC_ZOSCN2;

	@FXML
	private TextField DIVC_SHE_LNBEF;

	@FXML
	private TextField DIVC_ZOSFIO;

	@FXML
	private ComboBox<VCOURTS> DIVC_ZOSCN;

	@FXML
	private TextField DIVC_HE;

	
	private void convert_DIVC_ZOSCN2() {
		DIVC_ZOSCN2.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DIVC_ZOSCN2.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	
	private void convert_DIVC_ZOSCN() {
		DIVC_ZOSCN.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DIVC_ZOSCN.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	private void convert_DIVC_CAN() {
		DIVC_CAN.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DIVC_CAN.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	// _______________Методы______________________________
	@FXML
	void FindHe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(HeFio, DIVC_HE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void FindShe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(SheFio, DIVC_SHE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void AddHe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(HeFio, DIVC_HE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void AddShe(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(SheFio, DIVC_SHE, (Stage) DIVC_HE.getScene().getWindow());
	}

	@FXML
	void FindMercer(ActionEvent event) {
//		Mercer(DIVC_MC_MERCER);
		UtilCus cus = new UtilCus();
		cus.FindMercer(DIVC_MC_MERCER, (Stage) DIVC_MC_MERCER.getScene().getWindow(),conn);
	}

	void CusList(TextField num, TextField name) {
		try {
			Button Update = new Button();
			Update.setText("Выбрать");
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

			Scene secondScene = new Scene(secondaryLayout, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			Stage stage = (Stage) DIVC_DT.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("Список граждан");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DIVC_TYPE(ActionEvent event) {
		if (DIVC_TYPE.getValue()
				.equals("Совместное заявление супругов, не имеющих общих детей, не достигших совершеннолетия")) {
			DIV_A.setVisible(true);
			DIV_B.setVisible(false);
			DIV_V1.setVisible(false);
			DIV_V2.setVisible(false);
			// значения полей
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		} else if (DIVC_TYPE.getValue().equals("Решение суда о расторжении брака")) {
			DIV_A.setVisible(false);
			DIV_B.setVisible(true);
			DIV_V1.setVisible(false);
			DIV_V2.setVisible(false);
			// значения полей
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		} else if (DIVC_TYPE.getValue()
				.equals("Заявление одного из супругов и решение суда о признании безвестно отсутствующим")
				| DIVC_TYPE.getValue()
						.equals("Заявление одного из супругов и решение суда о признании недееспособным")) {
			DIV_A.setVisible(false);
			DIV_B.setVisible(false);
			DIV_V1.setVisible(true);
			DIV_V2.setVisible(false);
			// значения полей
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		} else if (DIVC_TYPE.getValue().equals("Приговор суда об осуждении и лишении свободы")) {
			DIV_A.setVisible(false);
			DIV_B.setVisible(false);
			DIV_V1.setVisible(false);
			DIV_V2.setVisible(true);
			// значения полей
			DIVC_TCHNUM.setText("");
			DIVC_TCHD.setValue(null);
			DIVC_CAD.setValue(null);
			DIVC_CAN.setValue(null);
			DIVC_ZOSCD.setValue(null);
			DIVC_ZOSFIO.setText("");
			DIVC_ZOSCN.setValue(null);
			DIVC_ZOSCN2.setValue(null);
			DIVC_ZOSFIO2.setText("");
			DIVC_ZOSPRISON.setText("");
			DIVC_ZOSCD2.setValue(null);
		}
	}

	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement callStmt = conn.prepareCall(
					"{ call Divorce.EditDivorce(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, DIVC_SERIA.getText());
			callStmt.setString(3, DIVC_NUM.getText());
			if (!DIVC_MC_MERCER.getText().equals("")) {
				callStmt.setInt(4, Integer.valueOf(DIVC_MC_MERCER.getText()));
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}
			if (!DIVC_ZOSPRISON.getText().equals("")) {
				callStmt.setInt(5, Integer.valueOf(DIVC_ZOSPRISON.getText()));
			} else {
				callStmt.setNull(5, java.sql.Types.INTEGER);
			}
			callStmt.setString(6, DIVC_ZOSFIO2.getText());
			callStmt.setDate(7,
					(DIVC_ZOSCD2.getValue() != null) ? java.sql.Date.valueOf(DIVC_ZOSCD2.getValue()) : null);
			
			if (DIVC_ZOSCN2.getSelectionModel().getSelectedItem() != null) {
				callStmt.setInt(8, DIVC_ZOSCN2.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(8, java.sql.Types.INTEGER);
			}
			
			callStmt.setString(9, DIVC_ZOSFIO.getText());
			callStmt.setDate(10, (DIVC_ZOSCD.getValue() != null) ? java.sql.Date.valueOf(DIVC_ZOSCD.getValue()) : null);
			
			if (DIVC_ZOSCN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setInt(11, DIVC_ZOSCN.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			
			callStmt.setDate(12, (DIVC_CAD.getValue() != null) ? java.sql.Date.valueOf(DIVC_CAD.getValue()) : null);
			
			
			if (DIVC_CAN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setInt(13, DIVC_CAN.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(13, java.sql.Types.INTEGER);
			}

			callStmt.setString(14, DIVC_TCHNUM.getText());
			callStmt.setDate(15, (DIVC_TCHD.getValue() != null) ? java.sql.Date.valueOf(DIVC_TCHD.getValue()) : null);
			callStmt.setString(16, DIVC_TYPE.getValue());
			callStmt.setDate(17, (DIVC_DT.getValue() != null) ? java.sql.Date.valueOf(DIVC_DT.getValue()) : null);
			callStmt.setString(18, DIVC_SHE_LNAFT.getText());
			callStmt.setString(19, DIVC_SHE_LNBEF.getText());
			callStmt.setString(20, DIVC_HE_LNAFT.getText());
			callStmt.setString(21, DIVC_HE_LNBEF.getText());
			if (!DIVC_SHE.getText().equals("")) {
				callStmt.setInt(22, Integer.valueOf(DIVC_SHE.getText()));
			} else {
				callStmt.setNull(22, java.sql.Types.INTEGER);
			}
			if (!DIVC_HE.getText().equals("")) {
				callStmt.setInt(23, Integer.valueOf(DIVC_HE.getText()));
			} else {
				callStmt.setNull(23, java.sql.Types.INTEGER);
			}
			callStmt.setString(24, DIVC_ZPLACE.getText());
			callStmt.setString(25, DIVC_ZMNAME.getText());
			callStmt.setString(26, DIVC_ZАNAME.getText());
			callStmt.setString(27, DIVC_ZLNAME.getText());
			callStmt.setInt(28, div_cer.getDIVC_ID());
			callStmt.setString(29,  DOC_NUMBER.getText());
			callStmt.execute();
			if (callStmt.getString(1) == null) {
				conn.commit();
				setStatus(true);
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) DIVC_HE_LNBEF.getScene().getWindow();
				Msg.MessageBox(callStmt.getString(1), stage_);
				callStmt.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void SaveTocompare() {
		try {
			CallableStatement callStmt = conn.prepareCall(
					"{ call Divorce.EditDivorce(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, DIVC_SERIA.getText());
			callStmt.setString(3, DIVC_NUM.getText());
			if (!DIVC_MC_MERCER.getText().equals("")) {
				callStmt.setInt(4, Integer.valueOf(DIVC_MC_MERCER.getText()));
			} else {
				callStmt.setNull(4, java.sql.Types.INTEGER);
			}
			if (!DIVC_ZOSPRISON.getText().equals("")) {
				callStmt.setInt(5, Integer.valueOf(DIVC_ZOSPRISON.getText()));
			} else {
				callStmt.setNull(5, java.sql.Types.INTEGER);
			}
			callStmt.setString(6, DIVC_ZOSFIO2.getText());
			callStmt.setDate(7,
					(DIVC_ZOSCD2.getValue() != null) ? java.sql.Date.valueOf(DIVC_ZOSCD2.getValue()) : null);
			if (DIVC_ZOSCN2.getSelectionModel().getSelectedItem() != null) {
				callStmt.setInt(8, DIVC_ZOSCN2.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(8, java.sql.Types.INTEGER);
			}
			callStmt.setString(9, DIVC_ZOSFIO.getText());
			callStmt.setDate(10, (DIVC_ZOSCD.getValue() != null) ? java.sql.Date.valueOf(DIVC_ZOSCD.getValue()) : null);
			if (DIVC_ZOSCN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setInt(11, DIVC_ZOSCN.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(11, java.sql.Types.INTEGER);
			}
			callStmt.setDate(12, (DIVC_CAD.getValue() != null) ? java.sql.Date.valueOf(DIVC_CAD.getValue()) : null);
			if (DIVC_CAN.getSelectionModel().getSelectedItem() != null) {
				callStmt.setInt(13, DIVC_CAN.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(13, java.sql.Types.INTEGER);
			}
			callStmt.setString(14, DIVC_TCHNUM.getText());
			callStmt.setDate(15, (DIVC_TCHD.getValue() != null) ? java.sql.Date.valueOf(DIVC_TCHD.getValue()) : null);
			callStmt.setString(16, DIVC_TYPE.getValue());
			callStmt.setDate(17, (DIVC_DT.getValue() != null) ? java.sql.Date.valueOf(DIVC_DT.getValue()) : null);
			callStmt.setString(18, DIVC_SHE_LNAFT.getText());
			callStmt.setString(19, DIVC_SHE_LNBEF.getText());
			callStmt.setString(20, DIVC_HE_LNAFT.getText());
			callStmt.setString(21, DIVC_HE_LNBEF.getText());
			if (!DIVC_SHE.getText().equals("")) {
				callStmt.setInt(22, Integer.valueOf(DIVC_SHE.getText()));
			} else {
				callStmt.setNull(22, java.sql.Types.INTEGER);
			}
			if (!DIVC_HE.getText().equals("")) {
				callStmt.setInt(23, Integer.valueOf(DIVC_HE.getText()));
			} else {
				callStmt.setNull(23, java.sql.Types.INTEGER);
			}
			callStmt.setString(24, DIVC_ZPLACE.getText());
			callStmt.setString(25, DIVC_ZMNAME.getText());
			callStmt.setString(26, DIVC_ZАNAME.getText());
			callStmt.setString(27, DIVC_ZLNAME.getText());
			callStmt.setInt(28, div_cer.getDIVC_ID());
			callStmt.setString(29,  DOC_NUMBER.getText());
			callStmt.execute();
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) DIVC_HE_LNBEF.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	private ScrollPane MainScroll;

	@FXML
	private TitledPane Pane1;

	@FXML
	private TitledPane Pane2;

	@FXML
	private TitledPane Pane3;

	@FXML
	private TitledPane Pane4;

	@FXML
	private TitledPane Pane5;

	@FXML
	private TitledPane Pane6;

	@FXML
	private TitledPane Pane7;

	@FXML
	private void initialize() {
		try {
			
			// Суды
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from VCOURTS");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<VCOURTS> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					VCOURTS list = new VCOURTS();
					list.setCOTDNAME(rs.getString("COTDNAME"));
					list.setID(rs.getInt("ID"));
					list.setABH_NAME(rs.getString("ABH_NAME"));
					list.setNAME_ROD(rs.getString("NAME_ROD"));
					list.setNAME(rs.getString("NAME"));
					list.setAREA_ID(rs.getInt("AREA_ID"));
					list.setOTD(rs.getInt("OTD"));
					list.setIOTDNUM(rs.getInt("IOTDNUM"));
					combolist.add(list);
				}

				stsmt.close();
				rs.close();

				DIVC_CAN.setItems(combolist);
				DIVC_ZOSCN2.setItems(combolist);
				DIVC_ZOSCN.setItems(combolist);
				
				if (div_cer.getDIVC_CAN() != null) {
					for (VCOURTS ld : DIVC_ZOSCN2.getItems()) {
						if (div_cer.getDIVC_ZOSCN2().equals(ld.getID())) {
							DIVC_ZOSCN2.getSelectionModel().select(ld);
							break;
						}
					}
				}
				
				if (div_cer.getDIVC_CAN() != null) {
					for (VCOURTS ld : DIVC_ZOSCN.getItems()) {
						if (div_cer.getDIVC_ZOSCN().equals(ld.getID())) {
							DIVC_ZOSCN.getSelectionModel().select(ld);
							break;
						}
					}
				}
				
				if (div_cer.getDIVC_CAN() != null) {
					for (VCOURTS ld : DIVC_CAN.getItems()) {
						if (div_cer.getDIVC_CAN().equals(ld.getID())) {
							DIVC_CAN.getSelectionModel().select(ld);
							break;
						}
					}
				}
				
				//НАИМЕНОВАНИЯ СУДА, ЕСЛИ ЕСТЬ
				convert_DIVC_CAN();
				convert_DIVC_ZOSCN2();
				convert_DIVC_ZOSCN();
				
				rs.close();
			}
						
			Pane1.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane2.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane3.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane4.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane5.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane6.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane7.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));

			DIVC_TYPE.getItems().addAll(
					"Совместное заявление супругов, не имеющих общих детей, не достигших совершеннолетия",
					"Решение суда о расторжении брака",
					"Заявление одного из супругов и решение суда о признании безвестно отсутствующим",
					"Заявление одного из супругов и решение суда о признании недееспособным",
					"Приговор суда об осуждении и лишении свободы");
			// Он
			if (div_cer.getDIVC_HE() != 0) {
				DIVC_HE.setText(String.valueOf(div_cer.getDIVC_HE()));
			}
			HeFio.setText(div_cer.getHEFIO());
			DIVC_HE_LNBEF.setText(div_cer.getDIVC_HE_LNBEF());
			DIVC_HE_LNAFT.setText(div_cer.getDIVC_HE_LNAFT());
			// Она
			if (div_cer.getDIVC_SHE() != 0) {
				DIVC_SHE.setText(String.valueOf(div_cer.getDIVC_SHE()));
			}
			SheFio.setText(div_cer.getSHEFIO());
			DIVC_SHE_LNBEF.setText(div_cer.getDIVC_SHE_LNBEF());
			DIVC_SHE_LNAFT.setText(div_cer.getDIVC_SHE_LNAFT());
			// Дата прекращения брака
			DIVC_DT.setValue(div_cer.getDIVC_DT());
			// Основание для государственной регистрации расторжения брака
			if (div_cer.getDIVC_TYPE() != null) {
				if (div_cer.getDIVC_TYPE().equals("A")) {
					DIVC_TYPE.getSelectionModel().select(
							"Совместное заявление супругов, не имеющих общих детей, не достигших совершеннолетия");
					DIV_A.setVisible(true);

					DIVC_TCHD.setValue(div_cer.getDIVC_TCHD());
					DIVC_TCHNUM.setText(div_cer.getDIVC_TCHNUM());
				} else if (div_cer.getDIVC_TYPE().equals("B")) {
					DIVC_TYPE.getSelectionModel().select("Решение суда о расторжении брака");
					DIV_B.setVisible(true);

					DIVC_CAD.setValue(div_cer.getDIVC_CAD());
					//DIVC_CAN.setValue(div_cer.getDIVC_CAN());

				} else if (div_cer.getDIVC_TYPE().equals("V1")) {
					DIVC_TYPE.getSelectionModel()
							.select("Заявление одного из супругов и решение суда о признании безвестно отсутствующим");
					DIV_V1.setVisible(true);

					DIVC_ZOSCD.setValue(div_cer.getDIVC_ZOSCD());
					//DIVC_ZOSCN.setText(div_cer.getDIVC_ZOSCN());
					DIVC_ZOSFIO.setText(div_cer.getDIVC_ZOSFIO());

				} else if (div_cer.getDIVC_TYPE().equals("V2")) {
					DIVC_TYPE.getSelectionModel()
							.select("Заявление одного из супругов и решение суда о признании недееспособным");

					DIV_V1.setVisible(true);

					DIVC_ZOSCD.setValue(div_cer.getDIVC_ZOSCD());
					//DIVC_ZOSCN.setText(div_cer.getDIVC_ZOSCN());
					DIVC_ZOSFIO.setText(div_cer.getDIVC_ZOSFIO());

				} else if (div_cer.getDIVC_TYPE().equals("V3")) {
					DIVC_TYPE.getSelectionModel().select("Приговор суда об осуждении и лишении свободы");
					DIV_V2.setVisible(true);

					//DIVC_ZOSCN2.setText(div_cer.getDIVC_ZOSCN2());
					DIVC_ZOSFIO2.setText(div_cer.getDIVC_ZOSFIO2());
					if (div_cer.getDIVC_ZOSPRISON() != 0) {
						DIVC_ZOSPRISON.setText(String.valueOf(div_cer.getDIVC_ZOSPRISON()));
					}
					DIVC_ZOSCD2.setValue(div_cer.getDIVC_ZOSCD2());
				}
			}
			// Акт о заключении брака
			if (div_cer.getDIVC_MC_MERCER() != 0) {
				DIVC_MC_MERCER.setText(String.valueOf(div_cer.getDIVC_MC_MERCER()));
			}
			// Заявитель
			DIVC_ZLNAME.setText(div_cer.getDIVC_ZLNAME());
			DIVC_ZАNAME.setText(div_cer.getDIVC_ZАNAME());
			DIVC_ZMNAME.setText(div_cer.getDIVC_ZMNAME());
			DIVC_ZPLACE.setText(div_cer.getDIVC_ZPLACE());
			// Выдано свидетельство
			DIVC_SERIA.setText(div_cer.getDIVC_SERIA());
			DIVC_NUM.setText(div_cer.getDIVC_NUM());
			
			DOC_NUMBER.setText(div_cer.getDOC_NUMBER());
			
			new ConvConst().FormatDatePiker(DIVC_ZOSCD2);
			new ConvConst().FormatDatePiker(DIVC_CAD);
			new ConvConst().FormatDatePiker(DIVC_DT);
			new ConvConst().FormatDatePiker(DIVC_TCHD);
			new ConvConst().FormatDatePiker(DIVC_ZOSCD);
			
			
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private BooleanProperty Status;

	private IntegerProperty Id;

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

	public EditDivorce() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}

	DIVORCE_CERT div_cer;

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
			String selectStmt = "select * from VMC_MERCER t\n";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			DateTimeFormatter formatterdt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			ObservableList<MC_MERCER> cuslist = FXCollections.observableArrayList();
			while (rs.next()) {
				MC_MERCER list = new MC_MERCER();

				list.setSHEFIO(rs.getString("SHEFIO"));
				list.setHEFIO(rs.getString("HEFIO"));
				list.setMERCER_ID(rs.getInt("MERCER_ID"));
				list.setMERCER_HE(rs.getInt("MERCER_HE"));
				list.setMERCER_SHE(rs.getInt("MERCER_SHE"));
				list.setMERCER_HE_LNBEF(rs.getString("MERCER_HE_LNBEF"));
				list.setMERCER_HE_LNAFT(rs.getString("MERCER_HE_LNAFT"));
				list.setMERCER_SHE_LNBEF(rs.getString("MERCER_SHE_LNBEF"));
				list.setMERCER_SHE_LNBAFT(rs.getString("MERCER_SHE_LNBAFT"));
				list.setMERCER_HEAGE(rs.getInt("MERCER_HEAGE"));
				list.setMERCER_SHEAGE(rs.getInt("MERCER_SHEAGE"));
				list.setTM$MERCER_DATE((rs.getDate("TM$MERCER_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$MERCER_DATE")), formatterdt)
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
			Stage stage = (Stage) DIVC_TYPE.getScene().getWindow();

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

	public void setConn(Connection conn, DIVORCE_CERT dvc) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.div_cer = dvc;
	}

}
