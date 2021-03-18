package mj.otd;

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

public class AddOtd {

    @FXML
    private ComboBox<RAION> RAION;
    
	@FXML
	private TextField COTDNAME;

	@FXML
	private TextField IOTDNUM;

	@FXML
	void Save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			PreparedStatement oper = conn.prepareStatement("insert into  otd (IOTDNUM, COTDNAME,AREA_ID) values (?,?,?)");
			oper.setInt(1, Integer.valueOf(IOTDNUM.getText()));
			oper.setString(2, COTDNAME.getText());
			oper.setInt(3, RAION.getSelectionModel().getSelectedItem().getCODE());
			oper.executeUpdate();
			oper.close();
			conn.commit();
			setStatus(true);
			onclose();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
		setStatus(false);
	}

	void onclose() {
		Stage stage = (Stage) COTDNAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();

			// ������
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from RAION");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<RAION> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					RAION list = new RAION();
					list.setCODE(rs.getInt("CODE"));
					list.setNAME(rs.getString("NAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				RAION.setItems(combolist);
				convert_RAION(RAION);
				rs.close();
			}

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	private void convert_RAION(ComboBox<RAION> cmbbx) {
		cmbbx.setConverter(new StringConverter<RAION>() {
			@Override
			public String toString(RAION product) {
				return product != null ? product.getNAME() : null;
			}

			@Override
			public RAION fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AssOtd");
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

	public AddOtd() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}
}
