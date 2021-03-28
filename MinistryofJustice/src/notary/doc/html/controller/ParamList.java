package notary.doc.html.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import notary.doc.old.model.LIST;

public class ParamList {

	public ParamList() {
		Main.logger = Logger.getLogger(getClass());
		this.query = new SimpleStringProperty();
		this.code_s = new SimpleStringProperty();
		this.name_s = new SimpleStringProperty();
		this.status = new SimpleBooleanProperty();
	}

	private StringProperty query;
	private StringProperty code_s;
	private StringProperty name_s;
	private BooleanProperty status;

	public void setStatus(Boolean status) {
		this.status.set(status);
	}

	public Boolean getStatus() {
		return status.get();
	}
	
	public void setName_S(String name_s) {
		this.name_s.set(name_s);
	}

	public String getName_s() {
		return name_s.get();
	}
	public void setCode_S(String code_s) {
		this.code_s.set(code_s);
	}

	public String getCode_s() {
		return code_s.get();
	}
	
	public void setQuery(String query) {
		this.query.set(query);
	}

	public String getQuery() {
		return query.get();
	}

	public void setConn(Connection conn) {
		try {
			this.conn = conn;
			this.conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) search.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private Connection conn;

	@FXML
	private TextField search;

	@FXML
	private TableView<LIST> list;

	@FXML
	private TableColumn<LIST, Integer> code;

	@FXML
	private TableColumn<LIST, String> name;

	@FXML
	void cencel(ActionEvent event) {
		try {
			setStatus(false);
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void init(String where) {
		try {
			PreparedStatement prp = conn.prepareStatement(getQuery() + where);
			ResultSet rs = prp.executeQuery();
			ObservableList<LIST> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				LIST list = new LIST();
				list.setCODE(rs.getInt(1));
				list.setNAME(rs.getString(2));
				dlist.add(list);
			}
			prp.close();
			rs.close();
			list.setItems(dlist);
			TableFilter<LIST> tableFilter = TableFilter.forTableView(list).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void search(ActionEvent event) {
		try {
			String where = " where name like '" + search.getText() + "'";
			init(where);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	@FXML
	void select(ActionEvent event) {
		try {
			LIST val = list.getSelectionModel().getSelectedItem();
			if (val != null) {
				setCode_S(String.valueOf(val.getCODE()));
				setName_S(val.getNAME());
				setStatus(true);
				onclose();
			} 
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			code.setCellValueFactory(cellData -> cellData.getValue().CODEProperty().asObject());
			name.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			init("");
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
