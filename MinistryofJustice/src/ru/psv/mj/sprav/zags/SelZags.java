package ru.psv.mj.sprav.zags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.utils.DbUtil;

public class SelZags {

	/**
	 * Конструктор
	 */
	public SelZags() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.zagsid = new SimpleLongProperty();
	}

	@FXML
	private TableColumn<VZAGS, String> ZAGS_NAME;
	@FXML
	private TableColumn<VZAGS, String> ZAGS_OTD;
	@FXML
	private TableColumn<VZAGS, String> ZAGS_RUK;
	@FXML
	private TableView<VZAGS> ZAGS;
	@FXML
	private TableColumn<VZAGS, Long> ZAGS_ID;

	@FXML
	private void initialize() {
		try {
			dbConnect();
			// DbUtil.Run_Process(conn,getClass().getName());
			ZAGS_NAME.setCellValueFactory(cellData -> cellData.getValue().ZAGS_NAMEProperty());
			ZAGS_OTD.setCellValueFactory(cellData -> cellData.getValue().COTDNAMEProperty());
			ZAGS_RUK.setCellValueFactory(cellData -> cellData.getValue().ZAGS_RUKProperty());
			ZAGS_ID.setCellValueFactory(cellData -> cellData.getValue().ZAGS_IDProperty().asObject());

			// двойной щелчок
			ZAGS.setRowFactory(tv -> {
				TableRow<VZAGS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Ok(null);
					}
				});
				return row;
			});
			Init();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отмена
	 * 
	 * @param event
	 */
	@FXML
	void Cancel(ActionEvent event) {
		try {
			setStatus(false);
			OnClose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private LongProperty zagsid;

	public Long getZagsId() {
		return this.zagsid.get();
	}

	private BooleanProperty Status;

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setZagsId(Long value) {
		this.zagsid.set(value);
	}
	
	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	/**
	 * ОК
	 * 
	 * @param event
	 */
	@FXML
	void Ok(ActionEvent event) {
		try {
			if (ZAGS.getSelectionModel().getSelectedItem() != null) {
				VZAGS sel = ZAGS.getSelectionModel().getSelectedItem();
				setZagsId(sel.getZAGS_ID());
				setStatus(true);
				OnClose();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void OnClose() {
		Stage stage = (Stage) ZAGS.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	void Init() {
		try {
			String selectStmt = "select * from VZAGS where ZAGS_ID <> 5 order by ZAGS_ID desc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VZAGS> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				VZAGS list = new VZAGS();
				list.setZAGS_RUK(rs.getString("ZAGS_RUK"));
				list.setZAGS_CITY_ABH(rs.getString("ZAGS_CITY_ABH"));
				list.setZAGS_ADR(rs.getString("ZAGS_ADR"));
				list.setZAGS_ID(rs.getLong("ZAGS_ID"));
				list.setADDR(rs.getString("ADDR"));
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setZAGS_RUK_ABH(rs.getString("ZAGS_RUK_ABH"));
				list.setZAGS_ADR_ABH(rs.getString("ZAGS_ADR_ABH"));
				list.setZAGS_OTD(rs.getLong("ZAGS_OTD"));
				list.setADDR_ABH(rs.getString("ADDR_ABH"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			ZAGS.setItems(dlist);

			TableFilter<VZAGS> tableFilter = TableFilter.forTableView(ZAGS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
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

}
