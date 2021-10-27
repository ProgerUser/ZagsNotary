package ru.psv.mj.sprav.zags;

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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.admin.users.OTD;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.KeyBoard;

public class EditZags {

	@FXML
	private TextField ZAGS_NAME;

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
    
    @FXML
    private TextField ZAGS_ADR;
    
    @FXML
    private TextField ADDR_ABH;
    
    @FXML
	private void OpenKey() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) ZAGS_CITY_ABH.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/widgets/KeyBoard.fxml"));

			KeyBoard controller = new KeyBoard();
			loader.setController(controller);
			controller.setTextField(ZAGS_CITY_ABH.getScene());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Абхазская клавиатура");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {

				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
    
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
			PreparedStatement oper = conn.prepareStatement(
					"update zags "
					+"set \n" + 
					"ZAGS_ID = ?,\n" + 
					"ZAGS_OTD = ?,\n" + 
					"ZAGS_NAME = ?,\n" + 
					"ZAGS_RUK = ?,\n" + 
					"ZAGS_ADR = ?,\n" + 
					"ZAGS_CITY_ABH = ?,\n" + 
					"ZAGS_ADR_ABH = ?,\n" + 
					"ZAGS_RUK_ABH = ?,\n" + 
					"ADDR = ?,"+ 
					"ADDR_ABH = ?"
					+ " where ZAGS_ID = ?");
			oper.setLong(1, Long.valueOf(ZAGS_ID.getText()));
			oper.setLong(2, ZAGS_OTD.getValue().getIOTDNUM());
			oper.setString(3, ZAGS_NAME.getText());
			oper.setString(4, ZAGS_RUK.getText());
			oper.setString(5, ZAGS_ADR.getText());
			oper.setString(6, ZAGS_CITY_ABH.getText());
			oper.setString(7, ZAGS_ADR_ABH.getText());
			oper.setString(8, ZAGS_RUK_ABH.getText());
			oper.setString(9, ADDR.getText());
			oper.setString(10, ADDR_ABH.getText());
			oper.setLong(11, Long.valueOf(ZAGS_ID.getText()));
			oper.executeUpdate();
			oper.close();
			
			setStatus(true);
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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
			ZAGS_ID.setEditable(false);
			ZAGS_RUK_ABH.setText(zags.getZAGS_RUK_ABH());
			ADDR.setText(zags.getADDR());
			ADDR_ABH.setText(zags.getADDR_ABH());
			ZAGS_ADR_ABH.setText(zags.getZAGS_ADR_ABH());
			ZAGS_CITY_ABH.setText(zags.getZAGS_CITY_ABH());
			ZAGS_ADR.setText(zags.getZAGS_ADR());
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
					list.setIOTDNUM(rs.getLong("IOTDNUM"));
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
			DbUtil.Log_Error(e);
		}
	}

	Connection conn;

	VZAGS zags;

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

	public void setConn(Connection conn, VZAGS value) throws SQLException {
		this.zags = value;
		this.conn = conn;
		this.conn.setAutoCommit(false);
	}

	public Long getId() {
		return this.Id.get();
	}

	public EditZags() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}
}
