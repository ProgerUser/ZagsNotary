package ru.psv.mj.sprav.courts;

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

public class EditCourt {

	@FXML
	private TextField ID;

	@FXML
	private TextField NAME;

	@FXML
	private TextField ABH_NAME;

	@FXML
	private TextField NAME_ROD;

	@FXML
	private ComboBox<OTD> OTD;

	@FXML
	private void OpenKey() {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) ID.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/widgets/KeyBoard.fxml"));

			KeyBoard controller = new KeyBoard();
			loader.setController(controller);
			controller.setTextField(ABH_NAME.getScene());

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

	@FXML
	void Save(ActionEvent event) {
		try {
			PreparedStatement oper = conn
					.prepareStatement("update courts set NAME = ? , OTD = ?, ABH_NAME = ?,NAME_ROD=? where ID = ?");
			oper.setString(1, NAME.getText());
			oper.setLong(2, OTD.getSelectionModel().getSelectedItem().getIOTDNUM());
			oper.setString(3, ABH_NAME.getText());
			oper.setString(4, NAME_ROD.getText());
			oper.setLong(5, courts.getID());
			oper.executeUpdate();
			oper.close();
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
		Stage stage = (Stage) ID.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Для отделения
	 */
	private void convertComboDisplayList() {
		OTD.setConverter(new StringConverter<OTD>() {
			@Override
			public String toString(OTD object) {
				return object != null ? object.getCOTDNAME() : "";
			}

			@Override
			public OTD fromString(final String string) {
				return OTD.getItems().stream().filter(product -> product.getCOTDNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	private void initialize() {
		try {
			ID.setEditable(false);

			ID.setText(String.valueOf(courts.getID()));
			NAME.setText(courts.getNAME());
			ABH_NAME.setText(courts.getABH_NAME());
			NAME_ROD.setText(courts.getNAME_ROD());

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

				OTD.setItems(combolist);
				if (courts.getCOTDNAME() != null) {
					for (OTD ld : OTD.getItems()) {
						if (courts.getCOTDNAME().equals(ld.getCOTDNAME())) {
							OTD.getSelectionModel().select(ld);
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

	VCOURTS courts;
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

	public void setConn(Connection conn, VCOURTS value) throws SQLException {
		this.courts = value;
		this.conn = conn;
		this.conn.setAutoCommit(false);
	}

	public Long getId() {
		return this.Id.get();
	}

	public EditCourt() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Id = new SimpleLongProperty();
	}
}
