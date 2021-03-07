package mj.zags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.users.OTD;

public class AddZags {
    @FXML
    private TextField ADDR_ABH;
    
	@FXML
	private TextField ZAGS_NAME;

    @FXML
    private TextField ZAGS_ADR;
    
	@FXML
	private ComboBox<OTD> ZAGS_OTD;

	@FXML
	private TextField ZAGS_RUK;

	@FXML
	private TextField ZAGS_ID;

    @FXML
    private TextField ZAGS_RUK_ABH;

    @FXML
    private TextField ADDR;

    @FXML
    private TextField ZAGS_ADR_ABH;

    @FXML
    private TextField ZAGS_CITY_ABH;
    
	/**
	 * Для отделения
	 */
	private void convertComboDisplayList() {
		ZAGS_OTD.setConverter(new StringConverter<OTD>() {
			@Override
			public String toString(OTD product) {
				return product.getCOTDNAME();
			}

			@Override
			public OTD fromString(final String string) {
				return ZAGS_OTD.getItems().stream().filter(product -> product.getCOTDNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	void Save(ActionEvent event) {
		try {
			PreparedStatement oper = conn
					.prepareStatement("insert into zags (ZAGS_ID, ZAGS_OTD,ZAGS_NAME,ZAGS_RUK,ZAGS_ADR,ZAGS_CITY_ABH,ZAGS_ADR_ABH,ZAGS_RUK_ABH,ADDR,ADDR_ABH) values (?,?,?,?,?,?,?,?,?,?)");
			oper.setInt(1, Integer.valueOf(ZAGS_ID.getText()));
			oper.setInt(2, ZAGS_OTD.getValue().getIOTDNUM());
			oper.setString(3, ZAGS_NAME.getText());
			oper.setString(4, ZAGS_RUK.getText());
			oper.setString(5, ZAGS_ADR.getText());
			oper.setString(6, ZAGS_CITY_ABH.getText());
			oper.setString(7, ZAGS_ADR_ABH.getText());
			oper.setString(8, ZAGS_RUK_ABH.getText());
			oper.setString(9, ADDR.getText());
			oper.setString(10, ADDR_ABH.getText());
			oper.executeUpdate();
			oper.close();
			
			conn.commit();
			setStatus(true);
			onclose();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				DBUtil.LOG_ERROR(e1);
			}
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
		setStatus(false);
	}

	void onclose() {
		Stage stage = (Stage) ZAGS_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();

			/* Отделение */
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from otd");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<OTD> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					OTD list = new OTD();
					list.setIOTDNUM(rs.getInt("IOTDNUM"));
					list.setCOTDNAME(rs.getString("COTDNAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				
				ZAGS_OTD.setItems(combolist);
				rs.close();
			}
			convertComboDisplayList();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddZags");
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

	public AddZags() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}
}
