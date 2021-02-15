package mj.access.grp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class GrpController {

	public GrpController() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TableColumn<USR_IN_OUT, String> out_fio;

	@FXML
	private TableColumn<USR_IN_OUT, String> out_login;

	@FXML
	private TableView<ODB_GROUP_USR> grp;

	@FXML
	private TableColumn<USR_IN_OUT, String> in_login;

	@FXML
	private TableColumn<USR_IN_OUT, String> in_fio;

	@FXML
	private TableColumn<ODB_GROUP_USR, String> name;

	@FXML
	private TableColumn<ODB_GROUP_USR, Integer> id;

	@FXML
	private TableView<USR_IN_OUT> usrout;

	@FXML
	private TableView<USR_IN_OUT> usrin;

	@FXML
	void addusr(ActionEvent event) {
		try {
			if(grp.getSelectionModel().getSelectedItem() != null &
					usrout.getSelectionModel().getSelectedItem() != null) {
				ODB_GROUP_USR grp_act = grp.getSelectionModel().getSelectedItem();
				USR_IN_OUT usr = usrout.getSelectionModel().getSelectedItem();
				PreparedStatement prp = DBUtil.conn.prepareStatement("declare\n" + 
						"  usr_id number;\n" + 
						"  pragma autonomous_transaction;\n" + 
						"begin\n" + 
						"  select usr.iusrid into usr_id from usr where usr.cusrlogname = ?;\n" + 
						"  insert into ODB_GRP_MEMBER (GRP_ID, IUSRID) values (?, usr_id);\n" + 
						"  commit;\n" + 
						"end;\n" + 
						"");
				prp.setString(1,  usr.getLOGIN());
				prp.setInt(2,  grp_act.getGRP_ID());
				prp.executeUpdate();
				prp.close();
				//Обновить
				InitUsrIn(grp_act.getGRP_ID());
				InitUsrOut(grp_act.getGRP_ID());
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void deleteusr(ActionEvent event) {
		try {
			if(grp.getSelectionModel().getSelectedItem() != null &
					usrin.getSelectionModel().getSelectedItem() != null) {
				ODB_GROUP_USR grp_act = grp.getSelectionModel().getSelectedItem();
				USR_IN_OUT usr = usrin.getSelectionModel().getSelectedItem();
				PreparedStatement prp = DBUtil.conn.prepareStatement("declare\n" + 
						"  usr_id number;\n" + 
						"  pragma autonomous_transaction;\n" + 
						"begin\n" + 
						"  select usr.iusrid into usr_id from usr where usr.cusrlogname = ?;\n" + 
						"  delete from  ODB_GRP_MEMBER where GRP_ID = ? and IUSRID = usr_id;\n" + 
						"  commit;\n" + 
						"end;\n" + 
						"");
				prp.setString(1,  usr.getLOGIN());
				prp.setInt(2,  grp_act.getGRP_ID());
				prp.executeUpdate();
				prp.close();
				//Обновить
				InitUsrIn(grp_act.getGRP_ID());
				InitUsrOut(grp_act.getGRP_ID());
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void add(ActionEvent event) {

	}

	@FXML
	void adit(ActionEvent event) {

	}

	@FXML
	void delete(ActionEvent event) {

	}

	@FXML
	private void initialize() {
		try {
			name.setCellValueFactory(cellData -> cellData.getValue().GRP_NAMEProperty());
			in_fio.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			out_login.setCellValueFactory(cellData -> cellData.getValue().LOGINProperty());
			out_fio.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			in_login.setCellValueFactory(cellData -> cellData.getValue().LOGINProperty());
			id.setCellValueFactory(cellData -> cellData.getValue().GRP_IDProperty().asObject());
			InitGrp();

			grp.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				ODB_GROUP_USR grp_act = grp.getSelectionModel().getSelectedItem();
				//Заполнить таблицы
				InitUsrIn(grp_act.getGRP_ID());
				InitUsrOut(grp_act.getGRP_ID());
			});
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Пользователи вне группы
	 */
	void InitUsrOut(Integer grp_id) {
		try {
			PreparedStatement prepStmt = DBUtil.conn
					.prepareStatement("select usr.cusrlogname, usr.cusrname\n" + 
							"  from usr\n" + 
							" where not exists (select null\n" + 
							"          from ODB_GRP_MEMBER mem\n" + 
							"         where mem.iusrid = usr.iusrid\n" + 
							"           and mem.grp_id = ?)\n");
			prepStmt.setInt(1, grp_id);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<USR_IN_OUT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				USR_IN_OUT list = new USR_IN_OUT();
				list.setLOGIN(rs.getString("cusrlogname"));
				list.setNAME(rs.getString("cusrname"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			usrout.setItems(dlist);
			TableFilter<USR_IN_OUT> tableFilter = TableFilter.forTableView(usrout).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Пользователи в группе
	 */
	void InitUsrIn(Integer grp_id) {
		try {
			PreparedStatement prepStmt = DBUtil.conn
					.prepareStatement("select usr.cusrlogname, usr.cusrname\n" + 
							"  from usr\n" + 
							" where exists (select null\n" + 
							"          from ODB_GRP_MEMBER mem\n" + 
							"         where mem.iusrid = usr.iusrid\n" + 
							"           and mem.grp_id = ?)\n");
			prepStmt.setInt(1, grp_id);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<USR_IN_OUT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				USR_IN_OUT list = new USR_IN_OUT();
				list.setLOGIN(rs.getString("cusrlogname"));
				list.setNAME(rs.getString("cusrname"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			usrin.setItems(dlist);
			TableFilter<USR_IN_OUT> tableFilter = TableFilter.forTableView(usrin).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Обновить таблицу с группами
	 */
	void InitGrp() {
		try {
			PreparedStatement prepStmt = DBUtil.conn
					.prepareStatement("select grp_id, grp_name, notation_extend_id from ODB_GROUP_USR t\n" + "");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ODB_GROUP_USR> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				ODB_GROUP_USR list = new ODB_GROUP_USR();
				list.setGRP_ID(rs.getInt("GRP_ID"));
				list.setGRP_NAME(rs.getString("GRP_NAME"));
				list.setNOTATION_EXTEND_ID(rs.getInt("NOTATION_EXTEND_ID"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			grp.setItems(dlist);

			TableFilter<ODB_GROUP_USR> tableFilter = TableFilter.forTableView(grp).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}
}
