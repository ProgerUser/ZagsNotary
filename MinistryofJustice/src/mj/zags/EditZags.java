package mj.zags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.users.OTD;

public class EditZags {

	@FXML
	private TextField ZAGS_NAME;

	@FXML
	private ComboBox<OTD> ZAGS_OTD;

	@FXML
	private TextField ZAGS_RUK;

	@FXML
	private TextField ZAGS_ID;

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
			Main.logger = Logger.getLogger(getClass());
			PreparedStatement oper = conn.prepareStatement(
					"update zags set ZAGS_ID = ? , ZAGS_OTD = ?,ZAGS_NAME = ?, ZAGS_RUK = ? where ZAGS_ID = ?");
			oper.setInt(1, Integer.valueOf(ZAGS_ID.getText()));
			oper.setInt(2, ZAGS_OTD.getValue().getIOTDNUM());
			oper.setString(3, ZAGS_NAME.getText());
			oper.setString(4, ZAGS_RUK.getText());
			oper.setInt(5, Integer.valueOf(ZAGS_ID.getText()));
			oper.executeUpdate();
			oper.close();
			
			setStatus(true);
			onclose();
		} catch (SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
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
			Main.logger = Logger.getLogger(getClass());

			ZAGS_NAME.setText(zags.getZAGS_NAME());
			ZAGS_RUK.setText(zags.getZAGS_RUK());
			ZAGS_ID.setText(String.valueOf(zags.getZAGS_ID()));

			// Отделение
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
				if (zags.getCOTDNAME() != null) {
					for (OTD ld : ZAGS_OTD.getItems()) {
						if (zags.getCOTDNAME().equals(ld.getCOTDNAME())) {
							ZAGS_OTD.getSelectionModel().select(ld);
							break;
						}
					}
				}
				rs.close();
			}

			convertComboDisplayList();

		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	Connection conn;

	VZAGS zags;

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

	public void setConn(Connection conn, VZAGS value) {
		this.zags = value;
		this.conn = conn;
	}

	public Integer getId() {
		return this.Id.get();
	}

	public EditZags() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleIntegerProperty();
	}
}
