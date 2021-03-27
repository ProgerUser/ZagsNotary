package notary.client.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import mj.doc.cus.COUNTRIES;
import mj.msg.Msg;
import mj.util.ConvConst;
import notary.client.model.DOC_TYPES;
import notary.client.model.GENDER;
import notary.client.model.NAS_PUNKT;
import notary.client.model.NT_CLI_TYPES;
import notary.client.model.RAION;

public class AddClients {
	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	private BooleanProperty Status;

	public boolean getStatus() {
		return this.Status.get();
	}

	public AddClients() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
	}

    @FXML
    private CheckBox raion_not_list;

    @FXML
    private CheckBox nas_p_not_list;
    
	@FXML
	private ScrollPane scroll;

	@FXML
	private TextField CLI_NAME;
	@FXML
	private TextField CLI_LAST_NAME;
	@FXML
	private TextField CLI_FIRST_NAME;
	@FXML
	private TextField CLI_MIDDLE_NAME;
	@FXML
	private TextField CLI_SH_NAME;
	@FXML
	private TextField CLI_OGRN;
	@FXML
	private TextField CLI_ADR_STREET;
	@FXML
	private TextField CLI_ADR_HOME;
	@FXML
	private TextField CLI_ADR_CORP;
	@FXML
	private TextField CLI_ADR_KV;
	@FXML
	private TextField CLI_DOC_SERIA;
	@FXML
	private TextField CLI_DOC_NUMBER;
	@FXML
	private TextField CLI_DOC_SUBDIV;
	@FXML
	private TextField CLI_DOC_AGENCY;
	@FXML
	private TextField CLI_INN;
	@FXML
	private TextField CLI_KPP;

	@FXML
	private TextField CLI_PLACE_BIRTH;
	@FXML
	private DatePicker CLI_DATE_REG;
	@FXML
	private DatePicker CLI_BR_DATE;
	@FXML
	private DatePicker CLI_DOC_START;
	@FXML
	private DatePicker CLI_DOC_END;

	@FXML
	private ComboBox<GENDER> CLI_GENDER;
	@FXML
	private ComboBox<COUNTRIES> CLI_BIRTH_COUNTRY;
	@FXML
	private ComboBox<RAION> CLI_ADR_RAION;
	@FXML
	private ComboBox<NAS_PUNKT> CLI_ADR_NAS_PUNKT;
	@FXML
	private ComboBox<NT_CLI_TYPES> CLI_TYPE;
	@FXML
	private ComboBox<COUNTRIES> CLI_ADR_COUNTRY;
	@FXML
	private ComboBox<DOC_TYPES> CLI_DOC_TYPE;

    @FXML
    private GridPane L_F_M_NAME;
    
    @FXML
    private VBox OSN_VBox;
    
    @FXML
    private TextField CLI_ADR_NAS_PUNKT_T;
    @FXML
    private TextField CLI_ADR_RAION_T;
    
	@FXML
	void nas_p_not_list(ActionEvent event) {
		try {
			if (nas_p_not_list.isSelected()) {
				CLI_ADR_NAS_PUNKT.setValue(null);
				CLI_ADR_NAS_PUNKT.setVisible(false);

				CLI_ADR_NAS_PUNKT_T.setVisible(true);
			}else {
				CLI_ADR_NAS_PUNKT.setVisible(true);
				CLI_ADR_NAS_PUNKT_T.setVisible(false);
				CLI_ADR_NAS_PUNKT_T.setText("");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void raion_not_list(ActionEvent event) {
		try {
			if (raion_not_list.isSelected()) {
				CLI_ADR_RAION.setValue(null);
				CLI_ADR_RAION.setVisible(false);

				CLI_ADR_RAION_T.setVisible(true);
			}else {
				CLI_ADR_RAION.setVisible(true);
				CLI_ADR_RAION_T.setText("");
				CLI_ADR_RAION_T.setVisible(false);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
    
	@FXML
	void CENCEL(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) CLI_DOC_AGENCY.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void CLI_ADR_RAION(ActionEvent event) {
		try {
			RAION val = CLI_ADR_RAION.getSelectionModel().getSelectedItem();
			if (val != null) {
				// ���. �����
				{
					PreparedStatement prp = conn
							.prepareStatement("select * from NAS_PUNKT where AREA = ? order by NAME");
					prp.setString(1, val.getNAME());
					ResultSet rs = prp.executeQuery();
					ObservableList<NAS_PUNKT> nationals = FXCollections.observableArrayList();
					while (rs.next()) {
						NAS_PUNKT countryes = new NAS_PUNKT();
						countryes.setCODE(rs.getInt("CODE"));
						countryes.setNAME(rs.getString("NAME"));
						nationals.add(countryes);
					}
					rs.close();
					prp.close();
					{
						FilteredList<NAS_PUNKT> filterednationals = new FilteredList<NAS_PUNKT>(nationals);
						CLI_ADR_NAS_PUNKT.getEditor().textProperty()
								.addListener(new InputFilter<NAS_PUNKT>(CLI_ADR_NAS_PUNKT, filterednationals, false));
						CLI_ADR_NAS_PUNKT.setItems(filterednationals);
						CombNasPunkt(CLI_ADR_NAS_PUNKT);
						CLI_ADR_NAS_PUNKT.getSelectionModel().select(0);
					}
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void CLI_TYPE(ActionEvent event) {
		try {
			NT_CLI_TYPES val = CLI_TYPE.getSelectionModel().getSelectedItem();
			if (val != null) {
				if (val.getCODE().equals(2)) {
					L_F_M_NAME.setVisible(false);
					CLI_LAST_NAME.setText("");
					CLI_FIRST_NAME.setText("");
					CLI_MIDDLE_NAME.setText("");
				} else if (val.getCODE().equals(1) || val.getCODE().equals(2)) {
					L_F_M_NAME.setVisible(true);
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			CallableStatement callStmt = conn
					.prepareCall("{ call NT_CLI.ADD_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			// ������
			callStmt.registerOutParameter(1, Types.VARCHAR);
			// �� �������
			callStmt.registerOutParameter(2, Types.INTEGER);
			// ������������
			callStmt.setString(3, CLI_NAME.getText());
			// ������� ���� �����, ��
			callStmt.setString(4, CLI_LAST_NAME.getText());
			// ��� ���� �����, ��
			callStmt.setString(5, CLI_FIRST_NAME.getText());
			// �������� ���� �����, ��
			callStmt.setString(6, CLI_MIDDLE_NAME.getText());
			// ������������ ��������
			callStmt.setString(7, CLI_SH_NAME.getText());
			// ��� ������� 1-��� - 2-�� 3- ��
			if (CLI_TYPE.getValue() != null) {
				callStmt.setInt(8, CLI_TYPE.getSelectionModel().getSelectedItem().getCODE());
			} else {
				callStmt.setNull(8, java.sql.Types.INTEGER);
			}
			// ���, 1=�������, 2=�������
			if (CLI_GENDER.getValue() != null) {
				callStmt.setInt(9, CLI_GENDER.getSelectionModel().getSelectedItem().getID());
			} else {
				callStmt.setNull(9, java.sql.Types.INTEGER);
			}
			// ���
			callStmt.setString(10, CLI_INN.getText());
			// ���
			callStmt.setString(11, CLI_KPP.getText());
			// ����
			callStmt.setString(12, CLI_OGRN.getText());
			// ���� ��������
			callStmt.setDate(13,
					(CLI_BR_DATE.getValue() != null) ? java.sql.Date.valueOf(CLI_BR_DATE.getValue()) : null);
			// ����� ��������
			callStmt.setString(14, CLI_PLACE_BIRTH.getText());
			// ���� ����������� (��)
			callStmt.setDate(15,
					(CLI_DATE_REG.getValue() != null) ? java.sql.Date.valueOf(CLI_DATE_REG.getValue()) : null);
			// �����-������
			if (CLI_ADR_COUNTRY.getValue() != null) {
				callStmt.setInt(16, CLI_ADR_COUNTRY.getSelectionModel().getSelectedItem().getCODE());
			} else {
				callStmt.setNull(16, java.sql.Types.INTEGER);
			}
			// �����-�����
			if (CLI_ADR_RAION.getValue() != null) {
				callStmt.setInt(17, CLI_ADR_RAION.getSelectionModel().getSelectedItem().getCODE());
			} else {
				callStmt.setNull(17, java.sql.Types.INTEGER);
			}
			// �����-���������� �����
			if (CLI_ADR_NAS_PUNKT.getValue() != null) {
				callStmt.setInt(18, CLI_ADR_NAS_PUNKT.getSelectionModel().getSelectedItem().getCODE());
			} else {
				callStmt.setNull(18, java.sql.Types.INTEGER);
			}
			// �����-�����
			callStmt.setString(19, CLI_ADR_STREET.getText());
			// �����-���
			callStmt.setString(20, CLI_ADR_HOME.getText());
			// �����-������
			callStmt.setString(21, CLI_ADR_CORP.getText());
			// �����-��������
			callStmt.setString(22, CLI_ADR_KV.getText());
			// ��������-��� ���������
			if (CLI_DOC_TYPE.getValue() != null) {
				callStmt.setInt(23, CLI_DOC_TYPE.getSelectionModel().getSelectedItem().getCODE());
			} else {
				callStmt.setNull(23, java.sql.Types.INTEGER);
			}
			// ��������-�����
			callStmt.setString(24, CLI_DOC_SERIA.getText());
			// ��������-�����
			callStmt.setString(25, CLI_DOC_NUMBER.getText());
			// ��������-��� �����
			callStmt.setString(26, CLI_DOC_AGENCY.getText());
			// ��������-���� ������
			callStmt.setDate(27,
					(CLI_DOC_START.getValue() != null) ? java.sql.Date.valueOf(CLI_DOC_START.getValue()) : null);
			// ��������-���� ��������
			callStmt.setDate(28,
					(CLI_DOC_END.getValue() != null) ? java.sql.Date.valueOf(CLI_DOC_END.getValue()) : null);
			// ��������-��� �������������
			callStmt.setString(29, CLI_DOC_SUBDIV.getText());
			// ������ ��������
			if (CLI_BIRTH_COUNTRY.getValue() != null) {
				callStmt.setInt(30, CLI_BIRTH_COUNTRY.getSelectionModel().getSelectedItem().getCODE());
			} else {
				callStmt.setNull(30, java.sql.Types.INTEGER);
			}
			//�����-���������� �����/�����
			callStmt.setString(31, CLI_ADR_NAS_PUNKT_T.getText());
			// �����-�����/�����
			callStmt.setString(32, CLI_ADR_RAION_T.getText());
			// �����-����� ���� �����
			callStmt.setInt(33, (raion_not_list.isSelected() ? 2 : 1));
			// �����-���������� ����� ���� �����
			callStmt.setInt(34, (nas_p_not_list.isSelected() ? 2 : 1));

			
			callStmt.execute();
			if (callStmt.getString(1) != null) {
				Msg.Message(callStmt.getString(1));
			} else {
				conn.commit();
				setStatus(true);
				onclose();
			}
			callStmt.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {

			new ConvConst().FormatDatePiker(CLI_DATE_REG);
			new ConvConst().FormatDatePiker(CLI_DOC_START);
			new ConvConst().FormatDatePiker(CLI_DOC_END);
			new ConvConst().FormatDatePiker(CLI_DATE_REG);
			new ConvConst().FormatDatePiker(CLI_BR_DATE);

			scroll.setFitToHeight(true);
			scroll.setFitToWidth(true);

			dbConnect();
			DBUtil.RunProcess(conn);
			// ������ ��������
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from COUNTRIES order by NAME";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<COUNTRIES> nationals = FXCollections.observableArrayList();
				while (rs.next()) {
					COUNTRIES countryes = new COUNTRIES();
					countryes.setCODE(rs.getInt("CODE"));
					countryes.setNAME(rs.getString("NAME"));
					nationals.add(countryes);
				}
				rs.close();
				sqlStatement.close();
				{
					FilteredList<COUNTRIES> filterednationals = new FilteredList<COUNTRIES>(nationals);
					CLI_BIRTH_COUNTRY.getEditor().textProperty()
							.addListener(new InputFilter<COUNTRIES>(CLI_BIRTH_COUNTRY, filterednationals, false));
					CLI_BIRTH_COUNTRY.setItems(filterednationals);
					CombCountry(CLI_BIRTH_COUNTRY);
					CLI_BIRTH_COUNTRY.getSelectionModel().select(0);
				}
				{
					FilteredList<COUNTRIES> filterednationals = new FilteredList<COUNTRIES>(nationals);
					CLI_ADR_COUNTRY.getEditor().textProperty()
							.addListener(new InputFilter<COUNTRIES>(CLI_ADR_COUNTRY, filterednationals, false));
					CLI_ADR_COUNTRY.setItems(filterednationals);
					CombCountry(CLI_ADR_COUNTRY);
					CLI_ADR_COUNTRY.getSelectionModel().select(0);
				}
			}
			// �����
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from RAION";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<RAION> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					RAION list = new RAION();
					list.setCODE(rs.getInt("CODE"));
					list.setNAME(rs.getString("NAME"));
					areas.add(list);
				}
				CLI_ADR_RAION.setItems(areas);
				CombRaion(CLI_ADR_RAION);
				rs.close();
				sqlStatement.close();
			}
			// ���
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from gender";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<GENDER> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					GENDER list = new GENDER();
					list.setID(rs.getInt("ID"));
					list.setSEX(rs.getString("SEX"));
					areas.add(list);
				}
				CLI_GENDER.setItems(areas);
				CombGender(CLI_GENDER);
				rs.close();
				sqlStatement.close();
			}
			// ��� �������
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from NT_CLI_TYPES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<NT_CLI_TYPES> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_CLI_TYPES list = new NT_CLI_TYPES();
					list.setNAME(rs.getString("NAME"));
					list.setCODE(rs.getInt("CODE"));
					areas.add(list);
				}
				CLI_TYPE.setItems(areas);
				CombCliType(CLI_TYPE);
				rs.close();
				sqlStatement.close();
			}
			// ���� ����������
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from DOC_TYPES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<DOC_TYPES> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					DOC_TYPES list = new DOC_TYPES();
					list.setNAME(rs.getString("NAME"));
					list.setCODE(rs.getInt("CODE"));
					areas.add(list);
				}
				CLI_DOC_TYPE.setItems(areas);
				CombDocType(CLI_DOC_TYPE);
				rs.close();
				sqlStatement.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void CombCountry(ComboBox<COUNTRIES> cmb) {
		cmb.setConverter(new StringConverter<COUNTRIES>() {
			@Override
			public String toString(COUNTRIES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public COUNTRIES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void CombRaion(ComboBox<RAION> cmb) {
		cmb.setConverter(new StringConverter<RAION>() {
			@Override
			public String toString(RAION product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public RAION fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void CombGender(ComboBox<GENDER> cmb) {
		cmb.setConverter(new StringConverter<GENDER>() {
			@Override
			public String toString(GENDER product) {
				return (product != null) ? product.getSEX() : "";
			}

			@Override
			public GENDER fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getSEX().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void CombCliType(ComboBox<NT_CLI_TYPES> cmb) {
		cmb.setConverter(new StringConverter<NT_CLI_TYPES>() {
			@Override
			public String toString(NT_CLI_TYPES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public NT_CLI_TYPES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	
	private void CombDocType(ComboBox<DOC_TYPES> cmb) {
		cmb.setConverter(new StringConverter<DOC_TYPES>() {
			@Override
			public String toString(DOC_TYPES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public DOC_TYPES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void CombNasPunkt(ComboBox<NAS_PUNKT> cmb) {
		cmb.setConverter(new StringConverter<NAS_PUNKT>() {
			@Override
			public String toString(NAS_PUNKT product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public NAS_PUNKT fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddClients");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
