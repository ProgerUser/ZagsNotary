package mj.doc.death;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import mj.app.model.Connect;
import mj.courts.VCOURTS;
import mj.doc.cus.CUS;
import mj.doc.cus.UtilCus;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;

public class AddDeath {

	@FXML
	private TextField DOC_NUMBER;
	/**
	 * ������������ ����
	 */
	@FXML
	private TextField DC_NRNAME;

	/**
	 * ������� ���� �� ������������
	 */
	@FXML
	private GridPane D_B;

	/**
	 * �������� ������������� ����� � ������
	 */
	@FXML
	private GridPane D_A;

	/**
	 * ��������� ����� ����������
	 */
	@FXML
	private TextField DC_LLOC;

	/**
	 * �������� � ����� ������ ����, ������������� �����������������
	 */
	@FXML
	private GridPane D_V;

	/**
	 * ���� ������
	 */
	@FXML
	private DatePicker DC_DD;

	/**
	 * A-���. ���������. ���� ������-���. ��������. ����� � ������ ����
	 */
	@FXML
	private DatePicker DC_FD;

	/**
	 * ������� ������
	 */
	@FXML
	private TextField DC_DPL;

	/**
	 * ����� ��� ����
	 */
	@FXML
	private GridPane FIZIK;

	/**
	 * ����� ����������
	 */
	@FXML
	private TextField DC_FADLOCATION;

	/**
	 * ��������, ���� �����
	 */
	@FXML
	private TextField DC_FADMIDDLE_NAME;

	/**
	 * ����� ��� ����
	 */
	@FXML
	private ComboBox<String> DC_FTYPE;

	/**
	 * �������� �������������� ���� ������ ���
	 */
	@FXML
	private ComboBox<String> DC_ZTP;

	/**
	 * ������������ ��. �����������
	 */
	@FXML
	private TextField DC_FADORG_NAME;

	/**
	 * �����
	 */
	@FXML
	private TextField DC_NUMBER;

	/**
	 * ������������ ����
	 */
	@FXML
	private ComboBox<VCOURTS> DC_RCNAME;

	/**
	 * ����
	 */
	@FXML
	private GridPane JURIK;

	/**
	 * ��� ��������
	 */
	@FXML
	private TextField DFIO;

	/**
	 * �����
	 */
	@FXML
	private TextField DC_FNUM;

	/**
	 * �����
	 */
	@FXML
	private TextField DC_SERIA;

	/**
	 * ������� ���� �����
	 */
	@FXML
	private TextField DC_FADLAST_NAME;

	/**
	 * ��. ����� ����������� ���������
	 */
	@FXML
	private TextField DC_FADREG_ADR;

	/**
	 * ������� ������
	 */
	@FXML
	private TextField DC_CD;

	/**
	 * ���, ���� ����� ���������
	 */
	@FXML
	private TextField DC_FADFIRST_NAME;

	/**
	 * ID ����������
	 */
	@FXML
	private TextField DC_CUS;

	/**
	 * ����. ���. ���. ���. ��������
	 */
	@FXML
	private TextField DC_FMON;

	@FXML
	void FindClient(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Find_Cus(DFIO, DC_CUS, (Stage) DC_CUS.getScene().getWindow());
	}

	@FXML
	void AddClient(ActionEvent event) {
		UtilCus cus = new UtilCus();
		cus.Add_Cus(DFIO, DC_CUS, (Stage) DC_CUS.getScene().getWindow(), conn);
	}

	/**
	 * ����� ����������
	 * 
	 * @param event
	 * @param num
	 * @param name
	 */
	void CusList(TextField num, TextField name) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Button Update = new Button();
			Update.setText("�������");
			AnchorPane secondaryLayout = new AnchorPane();

			VBox vb = new VBox();
			ToolBar toolBar = new ToolBar(Update);
			TableView<CUS> cusllists = new TableView<CUS>();
			TableColumn<CUS, Long> ICUSNUM = new TableColumn<>("�����");
			ICUSNUM.setCellValueFactory(new PropertyValueFactory<>("ICUSNUM"));
			TableColumn<CUS, String> CCUSNAME = new TableColumn<>("���");
			CCUSNAME.setCellValueFactory(new PropertyValueFactory<>("CCUSNAME"));
			TableColumn<CUS, LocalDate> DCUSBIRTHDAY = new TableColumn<>("���� ��������");
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
				cus.setICUSNUM(rs.getLong("ICUSNUM"));
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
						Msg.Message("�������� ������ �� �������!");
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
			Stage stage = (Stage) DC_CD.getScene().getWindow();

			Stage newWindow = new Stage();
			newWindow.setTitle("������ �������");
			newWindow.setScene(secondScene);
			newWindow.setResizable(false);
			newWindow.initModality(Modality.WINDOW_MODAL);
			newWindow.initOwner(stage);
			newWindow.getIcons().add(new Image("/icon.png"));
			newWindow.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��� ��������� �������������� ���� � ������
	 * 
	 * @param event
	 */
	@FXML
	void DC_FTYPE(ActionEvent event) {
		if (DC_FTYPE.getValue().equals("�������� ������������� ����� � ������")) {
			D_B.setVisible(false);
			D_V.setVisible(false);
			D_A.setVisible(true);
			// �������� �����
			DC_FNUM.setText("");
			DC_FD.setValue(null);
			DC_FMON.setText("");
			DC_RCNAME.setValue(null);
			DC_NRNAME.setText("");
		} else if (DC_FTYPE.getValue().equals("������� ���� �� ������������ ����� � ������")
				| DC_FTYPE.getValue().equals("������� ���� �� ������������ ���� �������")) {
			D_A.setVisible(false);
			D_V.setVisible(false);
			D_B.setVisible(true);
			// �������� �����
			DC_FNUM.setText("");
			DC_FD.setValue(null);
			DC_FMON.setText("");
			DC_RCNAME.setValue(null);
			DC_NRNAME.setText("");
		} else if (DC_FTYPE.getValue().equals("�������� � ����� ������ ����, ������������� �����������������")) {
			D_A.setVisible(false);
			D_B.setVisible(false);
			D_V.setVisible(true);
			// �������� �����
			DC_FNUM.setText("");
			DC_FD.setValue(null);
			DC_FMON.setText("");
			DC_RCNAME.setValue(null);
			DC_NRNAME.setText("");
		}
	}

	/**
	 * ��� ���������
	 * 
	 * @param event
	 */
	@FXML
	void DC_ZTP(ActionEvent event) {
		if (DC_ZTP.getValue().equals("���. ����")) {
			JURIK.setVisible(false);
			FIZIK.setVisible(true);

			if (DC_ZTP.getValue().equals("���. ����")) {
				JURIK.setVisible(false);
				FIZIK.setVisible(true);

				DC_FADLAST_NAME.setText("");
				DC_FADFIRST_NAME.setText("");
				DC_FADMIDDLE_NAME.setText("");
				DC_FADLOCATION.setText("");
				DC_FADORG_NAME.setText("");
				DC_FADREG_ADR.setText("");
			} else if (DC_ZTP.getValue().equals("��. ����")) {
				FIZIK.setVisible(false);
				JURIK.setVisible(true);

				DC_FADLAST_NAME.setText("");
				DC_FADFIRST_NAME.setText("");
				DC_FADMIDDLE_NAME.setText("");
				DC_FADLOCATION.setText("");
				DC_FADORG_NAME.setText("");
				DC_FADREG_ADR.setText("");
			}
		} else if (DC_ZTP.getValue().equals("��. ����")) {
			FIZIK.setVisible(false);
			JURIK.setVisible(true);

			if (DC_ZTP.getValue().equals("���. ����")) {
				JURIK.setVisible(false);
				FIZIK.setVisible(true);

				DC_FADLAST_NAME.setText("");
				DC_FADFIRST_NAME.setText("");
				DC_FADMIDDLE_NAME.setText("");
				DC_FADLOCATION.setText("");
				DC_FADORG_NAME.setText("");
				DC_FADREG_ADR.setText("");
			} else if (DC_ZTP.getValue().equals("��. ����")) {
				FIZIK.setVisible(false);
				JURIK.setVisible(true);

				DC_FADLAST_NAME.setText("");
				DC_FADFIRST_NAME.setText("");
				DC_FADMIDDLE_NAME.setText("");
				DC_FADLOCATION.setText("");
				DC_FADORG_NAME.setText("");
				DC_FADREG_ADR.setText("");
			}
		}

	}

	/**
	 * ���������
	 * 
	 * @param event
	 */
	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ ? = call Deatch.AddDeath(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, DC_NUMBER.getText());
			callStmt.setString(3, DC_SERIA.getText());
			callStmt.setString(4, DC_FADREG_ADR.getText());
			callStmt.setString(5, DC_FADORG_NAME.getText());
			callStmt.setString(6, DC_FADLOCATION.getText());
			callStmt.setString(7, DC_FADMIDDLE_NAME.getText());
			callStmt.setString(8, DC_FADLAST_NAME.getText());
			callStmt.setString(9, DC_FADFIRST_NAME.getText());
			callStmt.setString(10, DC_ZTP.getValue());
			callStmt.setString(11, DC_LLOC.getText());
			callStmt.setString(12, DC_NRNAME.getText());
			if (DC_RCNAME.getSelectionModel().getSelectedItem() != null) {
				callStmt.setLong(13, DC_RCNAME.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(13, java.sql.Types.INTEGER);
			}
			callStmt.setString(14, DC_FMON.getText());
			callStmt.setString(15, DC_FTYPE.getValue());
			callStmt.setDate(16, (DC_FD.getValue() != null) ? java.sql.Date.valueOf(DC_FD.getValue()) : null);
			callStmt.setString(17, DC_FNUM.getText());
			callStmt.setString(18, DC_CD.getText());
			callStmt.setString(19, DC_DPL.getText());
			callStmt.setDate(20, (DC_DD.getValue() != null) ? java.sql.Date.valueOf(DC_DD.getValue()) : null);
			if (!DC_CUS.getText().equals("")) {
				callStmt.setLong(21, Long.valueOf(DC_CUS.getText()));
			} else {
				callStmt.setNull(21, java.sql.Types.INTEGER);
			}
			callStmt.registerOutParameter(22, Types.INTEGER);
			callStmt.setString(23, DOC_NUMBER.getText());
			callStmt.execute();
			String ret = callStmt.getString(1);
			Long id = callStmt.getLong(22);
			if (ret.equals("ok")) {
				conn.commit();
				setStatus(true);
				setId(id);
				callStmt.close();
				onclose();
			} else {
				conn.rollback();
				setStatus(false);
				Stage stage_ = (Stage) DC_ZTP.getScene().getWindow();
				Msg.ErrorView(stage_, "AddDeath", conn);
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��� ��������
	 */
	void onclose() {
		Stage stage = (Stage) DC_ZTP.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * ������
	 * 
	 * @param event
	 */
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
	
	private void convert_DC_RCNAME() {
		DC_RCNAME.setConverter(new StringConverter<VCOURTS>() {
			@Override
			public String toString(VCOURTS product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public VCOURTS fromString(final String string) {
				return DC_RCNAME.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	
	/**
	 * �������������
	 */
	@FXML
	private void initialize() {
		try {

			DFIO.setText(getCusFio());
			DC_CUS.setText(getCusId() != null & getCusId() != 0 ? String.valueOf(getCusId()) : null);
				
			Pane1.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane2.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane3.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			Pane4.heightProperty().addListener(
					(observable, oldValue, newValue) -> MainScroll.vvalueProperty().set(newValue.doubleValue()));
			
			dbConnect();
			DbUtil.Run_Process(conn);
			
			// ����
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from VCOURTS");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<VCOURTS> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					VCOURTS list = new VCOURTS();
					list.setCOTDNAME(rs.getString("COTDNAME"));
					list.setID(rs.getLong("ID"));
					list.setABH_NAME(rs.getString("ABH_NAME"));
					list.setNAME_ROD(rs.getString("NAME_ROD"));
					list.setNAME(rs.getString("NAME"));
					list.setAREA_ID(rs.getLong("AREA_ID"));
					list.setOTD(rs.getLong("OTD"));
					list.setIOTDNUM(rs.getLong("IOTDNUM"));
					combolist.add(list);
				}

				stsmt.close();
				rs.close();

				DC_RCNAME.setItems(combolist);
				convert_DC_RCNAME();
			}
			/**
			 * ��� ���������
			 */
			DC_ZTP.getItems().addAll("���. ����", "��. ����");
			/**
			 * ������������� ����� � ������
			 */
			DC_FTYPE.getItems().addAll("�������� ������������� ����� � ������",
					"������� ���� �� ������������ ����� � ������", "������� ���� �� ������������ ���� �������",
					"�������� � ����� ������ ����, ������������� �����������������");
			new ConvConst().FormatDatePiker(DC_DD);
			new ConvConst().FormatDatePiker(DC_FD);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ������ ����������
	 */
	private Connection conn;

	/**
	 * ������� ������
	 */
	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddDeath");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * �������
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
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

	//
	// ���� ������ �� ����������
	//
	private LongProperty CusId;
	private LongProperty CusGen;
	private StringProperty CusFio;

	public void setCusGen(Long value) {
		this.CusGen.set(value);
	}
	
	public Long getCusGen() {
		return this.CusGen.get();
	}
	
	public void setCusId(Long value) {
		this.CusId.set(value);
	}

	public void setCusFio(String value) {
		this.CusFio.set(value);
	}

	public Long getCusId() {
		return this.CusId.get();
	}

	public String getCusFio() {
		return this.CusFio.get();
	}

	//
	// ---------------------------------------
	//

	
	public AddDeath() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
		
		this.CusId = new SimpleLongProperty();
		this.CusFio = new SimpleStringProperty();
		this.CusGen = new SimpleLongProperty();
	}

}
