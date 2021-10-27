package ru.psv.mj.sprav.notary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

public class AddNotary {

	@FXML
	private TextField NOT_NAME;

	@FXML
	private ComboBox<OTD> NOT_OTD;

	@FXML
	private TextField NOT_RUK;

	@FXML
	private TextField NOT_ID;

	@FXML
    private TextField ADDRESS;

    @FXML
    private TextField TELEPHONE;
    
	/**
	 * Для отделения
	 */
	private void convertComboDisplayList() {
		NOT_OTD.setConverter(new StringConverter<OTD>() {
			@Override
			public String toString(OTD object) {
				return object != null ? object.getCOTDNAME() : "";
			}
			
			@Override
			public OTD fromString(final String string) {
				return NOT_OTD.getItems().stream().filter(product -> product.getCOTDNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	void Save(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			PreparedStatement oper = conn
					.prepareStatement("insert into NOTARY "
							+ "(NOT_ID, NOT_OTD,NOT_NAME,NOT_RUK,NOT_ADDRESS,NOT_TELEPHONE)"
							+ " values (?,?,?,?,?,?)");
			oper.setLong(1, Long.valueOf(NOT_ID.getText()));
			oper.setLong(2, NOT_OTD.getValue().getIOTDNUM());
			oper.setString(3, NOT_NAME.getText());
			oper.setString(4, NOT_RUK.getText());
			oper.setString(5, ADDRESS.getText());
			oper.setString(6, TELEPHONE.getText());
			oper.executeUpdate();
			oper.close();

			conn.commit();
			setStatus(true);
			onclose();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				Main.logger.error(ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
			}
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
		setStatus(false);
	}

	void onclose() {
		Stage stage = (Stage) NOT_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			/* Отделение */
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from otd");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<OTD> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					OTD list = new OTD();
					list.setIOTDNUM(rs.getLong("IOTDNUM"));
					list.setCOTDNAME(rs.getString("COTDNAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();

				NOT_OTD.setItems(combolist);
				rs.close();
			}
			convertComboDisplayList();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	Connection conn;

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
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

	public AddNotary() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}
}
