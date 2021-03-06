package mj.access.grp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import mj.access.action.ODB_ACTION;
import mj.access.menu.ODB_MNU;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class GrpController {

	public GrpController() {
		Main.logger = Logger.getLogger(getClass());
	}
	
	ODB_ACTION adb_act_act;
	
	@FXML
	private TextField ActionID_ACT;

	@FXML
	private TreeView<ODB_ACTION> Actions;

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
	private TreeView<ODB_MNU> MNU;

	@FXML
	private ContextMenu ContMenu;

	@FXML
	void AddAct(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(162) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			if (grp.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите пользователя");
			} else {
				if (Actions.getSelectionModel().getSelectedItem() == null) {
					Msg.Message("Выберите действие");
				} else {
					ODB_GROUP_USR grp_ = grp.getSelectionModel().getSelectedItem();
					ODB_ACTION odb_act = Actions.getSelectionModel().getSelectedItem().getValue();
					// String acts = Actions.getSelectionModel().getSelectedItem().getValue();
					// Integer act = Integer.valueOf(acts.substring(0, acts.indexOf(":")));
					CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.ODB_ACT_GRP_ADD(?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					callStmt.setInt(2, grp_.getGRP_ID());
					callStmt.setInt(3, odb_act.getACT_ID());
					callStmt.execute();
					String ret = callStmt.getString(1);
					callStmt.close();
					if (ret.equals("ok")) {
						conn.commit();
						fillTreeAct();
					} else {
						conn.rollback();
						Msg.Message(ret);
					}
				}
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				DBUtil.LOG_ERROR(e);
			}
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DeleteAct(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(163) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			
			if (grp.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите пользователя");
			} else {
				if (Actions.getSelectionModel().getSelectedItem() == null) {
					Msg.Message("Выберите действие");
				} else {
					ODB_GROUP_USR grp_ = grp.getSelectionModel().getSelectedItem();
					ODB_ACTION odb_act = Actions.getSelectionModel().getSelectedItem().getValue();
					// String acts = Actions.getSelectionModel().getSelectedItem().getValue();
					// Integer act = Integer.valueOf(acts.substring(0, acts.indexOf(":")));

					CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.ODB_ACT_GRP_DEL(?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					callStmt.setInt(2, grp_.getGRP_ID());
					callStmt.setInt(3, odb_act.getACT_ID());
					callStmt.execute();
					String ret = callStmt.getString(1);
					callStmt.close();
					if (ret.equals("ok")) {
						conn.commit();
						fillTreeAct();
					} else {
						conn.rollback();
						Msg.Message(ret);
					}
				}
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				DBUtil.LOG_ERROR(e);
			}
			DBUtil.LOG_ERROR(e);
		}
	}
	
	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "GrpAccess");
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
	@FXML
	void AddMnu(ActionEvent event) {
		try {
			
			if (DBUtil.OdbAction(156) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			if (grp.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите пользователя");
			} else {
				if (MNU.getSelectionModel().getSelectedItem() == null) {
					Msg.Message("Выберите действие");
				} else {
					ODB_GROUP_USR grp_ = grp.getSelectionModel().getSelectedItem();
					ODB_MNU odb_act = MNU.getSelectionModel().getSelectedItem().getValue();
					// String acts = MNU.getSelectionModel().getSelectedItem().getValue();
					// Integer act = Integer.valueOf(acts.substring(0, acts.indexOf(":")));
					CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuGrpAdd(?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					callStmt.setInt(2, grp_.getGRP_ID());
					callStmt.setInt(3, odb_act.getMNU_ID());
					callStmt.execute();
					String ret = callStmt.getString(1);
					callStmt.close();
					if (ret.equals("ok")) {
						conn.commit();
						fillTreeMnu();
					} else {
						conn.rollback();
						Msg.Message(ret);
					}
				}
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				DBUtil.LOG_ERROR(e1);
			}
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DeleteMnu(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(157) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (grp.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите пользователя");
			} else {
				if (MNU.getSelectionModel().getSelectedItem() == null) {
					Msg.Message("Выберите действие");
				} else {
					ODB_GROUP_USR grp_ = grp.getSelectionModel().getSelectedItem();
					ODB_MNU odb_act = MNU.getSelectionModel().getSelectedItem().getValue();
					// String acts = MNU.getSelectionModel().getSelectedItem().getValue();
					// Integer act = Integer.valueOf(acts.substring(0, acts.indexOf(":")));

					CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuGrpDelete(?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					callStmt.setInt(2, grp_.getGRP_ID());
					callStmt.setInt(3, odb_act.getMNU_ID());
					callStmt.execute();
					String ret = callStmt.getString(1);
					callStmt.close();
					if (ret.equals("ok")) {
						conn.commit();
						fillTreeMnu();
					} else {
						conn.rollback();
						Msg.Message(ret);
					}
				}
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				DBUtil.LOG_ERROR(e1);
			}
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void addusr(ActionEvent event) {
		try {
			if (grp.getSelectionModel().getSelectedItem() != null
					& usrout.getSelectionModel().getSelectedItem() != null) {
				ODB_GROUP_USR grp_act = grp.getSelectionModel().getSelectedItem();
				USR_IN_OUT usr = usrout.getSelectionModel().getSelectedItem();
				PreparedStatement prp = conn
						.prepareStatement(
								"declare\n" + 
								"  pragma autonomous_transaction;\n" + 
								"  usr_id number;\n" + 
								"begin\n" + 
								"  select usr.iusrid into usr_id from usr where usr.cusrlogname = ?;\n" + 
								"  insert into ODB_GRP_MEMBER (GRP_ID, IUSRID) values (?, usr_id);\n" + 
								"  commit;\n" + 
								"end;\n" + 
								"");
				prp.setString(1, usr.getLOGIN());
				prp.setInt(2, grp_act.getGRP_ID());
				prp.executeUpdate();
				prp.close();
				// Обновить
				InitUsrIn(grp_act.getGRP_ID());
				InitUsrOut(grp_act.getGRP_ID());
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void deleteusr(ActionEvent event) {
		try {
			if (grp.getSelectionModel().getSelectedItem() != null
					& usrin.getSelectionModel().getSelectedItem() != null) {
				ODB_GROUP_USR grp_act = grp.getSelectionModel().getSelectedItem();
				USR_IN_OUT usr = usrin.getSelectionModel().getSelectedItem();
				PreparedStatement prp = conn
						.prepareStatement("declare\n" + "  usr_id number;\n" + "  pragma autonomous_transaction;\n"
								+ "begin\n" + "  select usr.iusrid into usr_id from usr where usr.cusrlogname = ?;\n"
								+ "  delete from  ODB_GRP_MEMBER where GRP_ID = ? and IUSRID = usr_id;\n"
								+ "  commit;\n" + "end;\n" + "");
				prp.setString(1, usr.getLOGIN());
				prp.setInt(2, grp_act.getGRP_ID());
				prp.executeUpdate();
				prp.close();
				// Обновить
				InitUsrIn(grp_act.getGRP_ID());
				InitUsrOut(grp_act.getGRP_ID());
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
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
	private TextField ActionID;
	
	@FXML
	private void initialize() {
		try {
			dbConnect();
			
			fillTreeMnu();
			fillTreeAct();
			
			MNU.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<ODB_MNU> act = MNU.getSelectionModel().getSelectedItem();
				if (act != null) {
					ActionID.setText(String.valueOf(act.getValue().getMNU_ID()));
				}
			});
			Actions.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<ODB_ACTION> act = Actions.getSelectionModel().getSelectedItem();
				if (act != null) {
					ActionID_ACT.setText(String.valueOf(act.getValue().getACT_ID()));
				}
			});
			
			Actions.setCellFactory(tv -> {
				TreeCell<ODB_ACTION> cell = new TreeCell<ODB_ACTION>() {
					@Override
					public void updateItem(ODB_ACTION item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText("");
							setGraphic(null);
						} else {
							setText(item.getACT_NAME());
							if (grp.getSelectionModel().getSelectedItem() != null) {
								ODB_GROUP_USR grp_ = grp.getSelectionModel().getSelectedItem();
								Integer act = item.getACT_ID();
								if (DBUtil.ODB_ACT_GRP(grp_.getGRP_ID(), act) == 1) {
									setStyle("-fx-text-fill: green;-fx-font-weight: bold");
								} else {
									setStyle("");
								}
							}
						}
					}
				};
				return cell;
			});
			
			// Меню_________________________--
			MNU.setCellFactory(tv -> {
				TreeCell<ODB_MNU> cell = new TreeCell<ODB_MNU>() {
					@Override
					public void updateItem(ODB_MNU item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText("");
							setGraphic(null);
						} else {
							setText(item.getMNU_NAME());
							if (grp.getSelectionModel().getSelectedItem() != null) {
								ODB_GROUP_USR grp_ = grp.getSelectionModel().getSelectedItem();
								Integer act = item.getMNU_ID();
								if (DBUtil.ODB_MNU_GRP(grp_.getGRP_ID(), act) == 1) {
									setStyle("-fx-text-fill: green;-fx-font-weight: bold");
								} else {
									setStyle("");
								}
							}
						}
					}
				};
				return cell;
			});

			// _____________________________

			name.setCellValueFactory(cellData -> cellData.getValue().GRP_NAMEProperty());
			in_fio.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			out_login.setCellValueFactory(cellData -> cellData.getValue().LOGINProperty());
			out_fio.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			in_login.setCellValueFactory(cellData -> cellData.getValue().LOGINProperty());
			id.setCellValueFactory(cellData -> cellData.getValue().GRP_IDProperty().asObject());
			InitGrp();

			grp.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				// Заполним меню
				fillTreeMnu();
				// заполним функции
				fillTreeAct();
				
				ODB_GROUP_USR grp_act = grp.getSelectionModel().getSelectedItem();
				// Заполнить таблицы
				InitUsrIn(grp_act.getGRP_ID());
				InitUsrOut(grp_act.getGRP_ID());
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	TreeItem<ODB_MNU> root = null;
	TreeItem<ODB_ACTION> root_root = null;
	ODB_MNU adb_act;

	void fillTreeMnu() {
		Map<Integer, TreeItem<ODB_MNU>> itemById = new HashMap<>();
		Map<Integer, Integer> parents = new HashMap<>();
		String query = "select * from ODB_MNU";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				adb_act = new ODB_MNU();
				adb_act.setMNU_ID(rs.getInt("MNU_ID"));
				adb_act.setMNU_NPP(rs.getInt("MNU_NPP"));
				adb_act.setMNU_PARENT(rs.getInt("MNU_PARENT"));
				adb_act.setMNU_NAME(rs.getString("MNU_NAME"));
				itemById.put(rs.getInt("MNU_ID"), new TreeItem<>(adb_act));
				parents.put(rs.getInt("MNU_ID"), rs.getInt("MNU_PARENT"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Map.Entry<Integer, TreeItem<ODB_MNU>> entry : itemById.entrySet()) {
			Integer key = entry.getKey();
			Integer parent = parents.get(key);
			if (parent.equals(key)) {
				// in case the root item points to itself, this is it
				root = entry.getValue();
				root.setExpanded(true);
			} else {
				TreeItem<ODB_MNU> parentItem = itemById.get(parent);
				if (parentItem == null) {
					// in case the root item has no parent in the result set, this is it
					root = entry.getValue();
				} else {
					// add to parent tree item
					parentItem.getChildren().add(entry.getValue());
				}
				// parentItem.setExpanded(true);
			}
		}
		// root.setExpanded(true);
		MNU.setRoot(root);
	}

	
	void fillTreeAct() {
		Map<Integer, TreeItem<ODB_ACTION>> itemById = new HashMap<>();
		Map<Integer, Integer> parents = new HashMap<>();
		String query = "select * from ODB_ACTION";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				adb_act_act = new ODB_ACTION();
				adb_act_act.setACT_ID(rs.getInt("ACT_ID"));
				adb_act_act.setACT_NPP(rs.getInt("ACT_NPP"));
				adb_act_act.setACT_PARENT(rs.getInt("ACT_PARENT"));
				adb_act_act.setACT_NAME(rs.getString("ACT_NAME"));
				itemById.put(rs.getInt("ACT_ID"), new TreeItem<>(adb_act_act));
				parents.put(rs.getInt("ACT_ID"), rs.getInt("ACT_PARENT"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Map.Entry<Integer, TreeItem<ODB_ACTION>> entry : itemById.entrySet()) {
			Integer key = entry.getKey();
			Integer parent = parents.get(key);
			if (parent.equals(key)) {
				// in case the root item points to itself, this is it
				root_root = entry.getValue();
				root_root.setExpanded(true);
			} else {
				TreeItem<ODB_ACTION> parentItem = itemById.get(parent);
				if (parentItem == null) {
					// in case the root item has no parent in the result set, this is it
					root_root = entry.getValue();
				} else {
					// add to parent tree item
					parentItem.getChildren().add(entry.getValue());
				}
				// parentItem.setExpanded(true);
			}
		}
		// root.setExpanded(true);
		Actions.setRoot(root_root);
	}
	
	/**
	 * Пользователи вне группы
	 */
	void InitUsrOut(Integer grp_id) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select usr.cusrlogname, usr.cusrname\n"
					+ "  from usr\n" + " where not exists (select null\n" + "          from ODB_GRP_MEMBER mem\n"
					+ "         where mem.iusrid = usr.iusrid\n" + "           and mem.grp_id = ?)\n");
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
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Пользователи в группе
	 */
	void InitUsrIn(Integer grp_id) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select usr.cusrlogname, usr.cusrname\n"
					+ "  from usr\n" + " where exists (select null\n" + "          from ODB_GRP_MEMBER mem\n"
					+ "         where mem.iusrid = usr.iusrid\n" + "           and mem.grp_id = ?)\n");
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
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Обновить таблицу с группами
	 */
	void InitGrp() {
		try {
			PreparedStatement prepStmt = conn
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
			DBUtil.LOG_ERROR(e);
		}
	}
}
