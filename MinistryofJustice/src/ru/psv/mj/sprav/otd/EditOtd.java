package ru.psv.mj.sprav.otd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.admin.users.OTD;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;

public class EditOtd {

	@FXML
	private TextField COTDNAME;

	@FXML
	private TextField IOTDNUM;

    @FXML
    private ComboBox<RAION> RAION;
    
	@FXML
	void Save(ActionEvent event) {
		try {
			CallableStatement call = conn.prepareCall(
					"begin update otd set IOTDNUM = ? , COTDNAME = ?,AREA_ID=? where IOTDNUM = ?; end;");
			call.setLong(1, Long.valueOf(IOTDNUM.getText()));
			call.setString(2, COTDNAME.getText());
			call.setLong(3, Long.valueOf(IOTDNUM.getText()));
			call.setLong(4, RAION.getSelectionModel().getSelectedItem().getCODE());
			call.executeUpdate();
//			try (DbmsOutputCapture capture = new DbmsOutputCapture(conn)) {
//				List<String> lines = capture.execute(call);
//				if (Connect.dbmsOutput) {
//					Main.logger.info(lines);
//				}
//			} catch (Exception e) {
//				DBUtil.LOG_ERROR(e);
//			}
			
			call.close();
//			PreparedStatement oper = conn
//					.prepareStatement("update otd set IOTDNUM = ? , COTDNAME = ? where IOTDNUM = ?");
//			oper.setLong(1, Long.valueOf(IOTDNUM.getText()));
//			oper.setString(2, COTDNAME.getText());
//			oper.setLong(3, Long.valueOf(IOTDNUM.getText()));
//			oper.executeUpdate();
//			oper.close();
			setStatus(true);
			onclose();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
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
			COTDNAME.setText(otd.getCOTDNAME());
			IOTDNUM.setText(String.valueOf(otd.getIOTDNUM()));

			
			// Районы
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from RAION");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<RAION> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					RAION list = new RAION();
					list.setNAME(rs.getString("NAME"));
					list.setCODE(rs.getLong("CODE"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				RAION.setItems(combolist);
				convert_RAION(RAION);
				rs.close();
				if (otd.getAREA_ID() != null) {
					for (RAION ld : RAION.getItems()) {
						if (otd.getAREA_ID().equals(ld.getCODE())) {
							RAION.getSelectionModel().select(ld);
							break;
						}
					}
				}
			}
			
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private void convert_RAION(ComboBox<RAION> cmbbx) {
		cmbbx.setConverter(new StringConverter<RAION>() {
			@Override
			public String toString(RAION product) {
				return product != null ? product.getNAME() : "";
			}

			@Override
			public RAION fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	
	Connection conn;

	OTD otd;
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

	public void setConn(Connection conn, OTD value) throws SQLException {
		this.otd = value;
		this.conn = conn;
		this.conn.setAutoCommit(false);
	}

	public Long getId() {
		return this.Id.get();
	}

	public EditOtd() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}
}
