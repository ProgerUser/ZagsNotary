package mj.init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class Settings {
	
	public Settings() {
		Main.logger = Logger.getLogger(getClass());
	}
	

	@FXML
	private XTableColumn<USERS, String> USR_NAME;

	@FXML
	private XTableColumn<USERS, Long> ID;

	@FXML
	private XTableView<USERS> USERS;

	@FXML
	private VBox PANE;

	@FXML
	private TextField URL;

	@FXML
	void Save(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(146l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (!URL.getText().equals("")) {
				PreparedStatement prepStmt = SQLIETE.prepareStatement("update props set value = ? where name = 'url'");
				prepStmt.setString(1, URL.getText());
				prepStmt.executeUpdate();
				prepStmt.close();
			} else {
				Msg.Message("Адрес сервера должен быть заполнен!");
			}
			SQLIETE.commit();
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) USERS.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void Delete(ActionEvent event) {
		try {
			if (USERS.getSelectionModel().getSelectedItem() != null) {
				USERS usr = USERS.getSelectionModel().getSelectedItem();
				PreparedStatement prepStmt = SQLIETE.prepareStatement("delete from users where ID = ?");
				prepStmt.setLong(1, usr.getID());
				prepStmt.executeUpdate();
				prepStmt.close();
				Refresh();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void Refresh() {
		try {
			PreparedStatement prepStmt = SQLIETE.prepareStatement("select * from USERS order by ID");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<USERS> usr_list = FXCollections.observableArrayList();
			while (rs.next()) {
				USERS list = new USERS();
				list.setID(rs.getLong("ID"));
				list.setUSR_NAME(rs.getString("USR_NAME"));
				usr_list.add(list);
			}
			
			prepStmt.close();
			rs.close();
			
			USERS.setItems(usr_list);
			TableFilter<USERS> tableFilter = TableFilter.forTableView(USERS).apply();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");

			SQLIETEConnect();
			PreparedStatement prp = SQLIETE.prepareStatement("select value from props where name = 'url'");
			ResultSet rs = prp.executeQuery();
			if (rs.next()) {
				URL.setText(rs.getString("value"));
			}
			
			prp.close();
			rs.close();
			
			USERS.setTableMenuButtonVisible(true);
			
			ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
			USR_NAME.setCellValueFactory(cellData -> cellData.getValue().USR_NAMEProperty());

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			
			USR_NAME.setColumnFilter(new PatternColumnFilter<>());
			
			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.INTEGER_TEXTFORMATTER_FACTORY));

			Refresh();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Сессия
	 */
	private Connection SQLIETE;

	/**
	 * Открыть сессию
	 */
	private void SQLIETEConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Main.logger = Logger.getLogger(getClass());
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			SQLIETE = DriverManager.getConnection(url);
			SQLIETE.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Отключить сессию
	 */
	public void SQLIETEDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());

			if (SQLIETE != null && !SQLIETE.isClosed()) {
				SQLIETE.close();
			}

		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
