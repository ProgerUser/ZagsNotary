package mj.access.menu;

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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.users.USR;

public class OdbMNU {

	public OdbMNU() {
		Main.logger = Logger.getLogger(getClass());
	}
	
	@FXML
	private TextField ActionID;

	@FXML
	private TreeView<ODB_MNU> MNU;

	TreeItem<ODB_MNU> root = null;

	ODB_MNU adb_act;

	@FXML
	private TableView<USR> Users;

	@FXML
	private TableColumn<USR, String> Login;

	@FXML
	private TableColumn<USR, String> Fio;

	@FXML
	private ContextMenu ContMenu;

	@FXML
	private MenuItem Add;

	@FXML
	private MenuItem Delete;

	@FXML
	void Add(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(156) == 0) {
				Msg.Message("��� �������!");
				return;
			}
			
			if (Users.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������������");
			} else {
				if (MNU.getSelectionModel().getSelectedItem() == null) {
					Msg.Message("�������� ��������");
				} else {
					USR usr = Users.getSelectionModel().getSelectedItem();
					ODB_MNU odb_act = MNU.getSelectionModel().getSelectedItem().getValue();
					// String acts = MNU.getSelectionModel().getSelectedItem().getValue();
					// Integer act = Integer.valueOf(acts.substring(0, acts.indexOf(":")));
					CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuUsrAdd(?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					callStmt.setInt(2, usr.getIUSRID());
					callStmt.setInt(3, odb_act.getMNU_ID());
					callStmt.execute();
					String ret = callStmt.getString(1);
					
					callStmt.close();
					
					if (ret.equals("ok")) {
						conn.commit();
						fillTree();
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
				e.printStackTrace();
				Msg.Message(ExceptionUtils.getStackTrace(e1));
				Main.logger.error(ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(157) == 0) {
				Msg.Message("��� �������!");
				return;
			}
			Main.logger = Logger.getLogger(getClass());
			if (Users.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������������");
			} else {
				if (MNU.getSelectionModel().getSelectedItem() == null) {
					Msg.Message("�������� ��������");
				} else {
					USR usr = Users.getSelectionModel().getSelectedItem();
					ODB_MNU odb_act = MNU.getSelectionModel().getSelectedItem().getValue();
					// String acts = MNU.getSelectionModel().getSelectedItem().getValue();
					// Integer act = Integer.valueOf(acts.substring(0, acts.indexOf(":")));

					CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuUsrDelete(?,?)}");
					callStmt.registerOutParameter(1, Types.VARCHAR);
					callStmt.setInt(2, usr.getIUSRID());
					callStmt.setInt(3, odb_act.getMNU_ID());
					callStmt.execute();
					String ret = callStmt.getString(1);
					callStmt.close();
					if (ret.equals("ok")) {
						conn.commit();
						fillTree();
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
				e.printStackTrace();
				Msg.Message(ExceptionUtils.getStackTrace(e));
				Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
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
	 * ������� ��������
	 * 
	 * @param event
	 */
	@FXML
	void DeleteAction(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(160) == 0) {
				Msg.Message("��� �������!");
				return;
			}
			Main.logger = Logger.getLogger(getClass());
			Stage stage = (Stage) Users.getScene().getWindow();
			Label alert = new Label("������� ������?");
			alert.setLayoutX(75.0);
			alert.setLayoutY(11.0);
			alert.setPrefHeight(17.0);

			Button no = new Button();
			no.setText("���");
			no.setLayoutX(111.0);
			no.setLayoutY(56.0);
			no.setPrefWidth(72.0);
			no.setPrefHeight(21.0);

			Button yes = new Button();
			yes.setText("��");
			yes.setLayoutX(14.0);
			yes.setLayoutY(56.0);
			yes.setPrefWidth(72.0);
			yes.setPrefHeight(21.0);

			AnchorPane yn = new AnchorPane();
			yn.getChildren().add(alert);
			yn.getChildren().add(no);
			yn.getChildren().add(yes);
			Scene ynScene = new Scene(yn, 250, 100);
			Stage newWindow_yn = new Stage();
			no.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					newWindow_yn.close();
				}
			});
			yes.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					try {
						PreparedStatement delete = conn.prepareStatement("declare " + "pragma autonomous_transaction;"
								+ "begin " + " delete from odb_mnu where MNU_ID = ?;" + "commit;" + "end;");

						delete.setInt(1, MNU.getSelectionModel().getSelectedItem().getValue().getMNU_ID());
						delete.executeUpdate();
						delete.close();
						fillTree();
					} catch (SQLException e) {
						try {
							conn.rollback();
						} catch (SQLException e1) {
							Msg.Message(ExceptionUtils.getStackTrace(e1));
							Main.logger.error(ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
						}
						Msg.Message(ExceptionUtils.getStackTrace(e));
						Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
						String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
						String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
						int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
					}
					newWindow_yn.close();
				}
			});
			newWindow_yn.setTitle("��������");
			newWindow_yn.setScene(ynScene);
			newWindow_yn.initModality(Modality.WINDOW_MODAL);
			newWindow_yn.initOwner(stage);
			newWindow_yn.setResizable(false);
			newWindow_yn.getIcons().add(new Image("/icon.png"));
			newWindow_yn.show();

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
	 * �������������
	 * 
	 * @param event
	 */
	@FXML
	void EdtAction(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(159) == 0) {
				Msg.Message("��� �������!");
				return;
			}
			Main.logger = Logger.getLogger(getClass());
			Stage stage = new Stage();
			Stage stage_ = (Stage) Users.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/access/menu/IUMnu.fxml"));

			EditMnu controller = new EditMnu();
			loader.setController(controller);
			controller.setParantid(MNU.getSelectionModel().getSelectedItem().getValue().getMNU_ID(),
					MNU.getSelectionModel().getSelectedItem().getValue().getMNU_NAME());

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("�������������: " + MNU.getSelectionModel().getSelectedItem().getValue().getMNU_NAME());
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						fillTree();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
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
	 * �������� ��������
	 * 
	 * @param event
	 */
	@FXML
	void AddChildAction(ActionEvent event) {
		try {
			if (DBUtil.OdbAction(158) == 0) {
				Msg.Message("��� �������!");
				return;
			}
			Main.logger = Logger.getLogger(getClass());
			Stage stage = new Stage();
			Stage stage_ = (Stage) Users.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/access/menu/IUMnu.fxml"));

			// System.out.println(MNU.getSelectionModel().getSelectedItem().getValue().getMNU_ID());

			AddMnu controller = new AddMnu();
			controller.setParantid(MNU.getSelectionModel().getSelectedItem().getValue().getMNU_ID());
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("�������� ����� ������");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						fillTree();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
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

	@FXML
	private void initialize() {
		try {
			
			Login.setCellValueFactory(cellData -> cellData.getValue().CUSRLOGNAMEProperty());
			Fio.setCellValueFactory(cellData -> cellData.getValue().CUSRNAMEProperty());

			// MNU.setCellFactory((TreeView<String> p) -> new TextFieldTreeCellImpl());

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
							if (Users.getSelectionModel().getSelectedItem() != null) {

								USR usr = Users.getSelectionModel().getSelectedItem();

								Integer act = item.getMNU_ID();
								// Integer act = Integer.valueOf(item.substring(0, item.indexOf(":")));

								if (DBUtil.ODB_MNU(usr.getIUSRID(), act) == 1) {
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

			dbConnect();

			InitUsrs();

			fillTree();

			Users.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				fillTree();
				// Clear(root);
				// USR usr = Users.getSelectionModel().getSelectedItem();
				// printChildren(root, usr.getIUSRID());
			});

			MNU.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<ODB_MNU> act = MNU.getSelectionModel().getSelectedItem();
				if (act != null) {
					ActionID.setText(String.valueOf(act.getValue().getMNU_ID()));
				}
			});

			TableFilter<USR> tableFilter = TableFilter.forTableView(Users).apply();
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

	void InitUsrs() {
		try {
			Main.logger = Logger.getLogger(getClass());
			PreparedStatement prepStmt = conn.prepareStatement("select * from usr");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<USR> usr_list = FXCollections.observableArrayList();
			while (rs.next()) {
				USR usr = new USR();
				usr.setCUSRLOGNAME(rs.getString("CUSRLOGNAME"));
				usr.setIUSRID(rs.getInt("IUSRID"));
				usr.setCUSRNAME(rs.getString("CUSRNAME"));
				usr_list.add(usr);
			}
			prepStmt.close();
			rs.close();
			Users.setItems(usr_list);
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/*
	 * void fillTree2() { Map<Integer, TreeItem<String>> itemById = new HashMap<>();
	 * Map<Integer, Integer> parents = new HashMap<>(); String query =
	 * "select * from ODB_MNU"; try { PreparedStatement pstmt =
	 * conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery(); while
	 * (rs.next()) { adb_act = new ODB_MNU();
	 * adb_act.setMNU_ID(rs.getInt("MNU_ID"));
	 * adb_act.setMNU_NPP(rs.getInt("MNU_NPP"));
	 * adb_act.setMNU_PARENT(rs.getInt("MNU_PARENT"));
	 * adb_act.setMNU_NAME(rs.getString("MNU_NAME"));
	 * itemById.put(rs.getInt("MNU_ID"), new
	 * TreeItem<>(String.valueOf(rs.getInt("MNU_ID")) + ":" +
	 * rs.getString("MNU_NAME"))); parents.put(rs.getInt("MNU_ID"),
	 * rs.getInt("MNU_PARENT")); } pstmt.close(); rs.close(); } catch (SQLException
	 * e) { e.printStackTrace(); }
	 * 
	 * for (Map.Entry<Integer, TreeItem<String>> entry : itemById.entrySet()) {
	 * Integer key = entry.getKey(); Integer parent = parents.get(key); if
	 * (parent.equals(key)) { // in case the root item points to itself, this is it
	 * root = entry.getValue(); } else { TreeItem<String> parentItem =
	 * itemById.get(parent); if (parentItem == null) { // in case the root item has
	 * no parent in the result set, this is it root = entry.getValue(); } else { //
	 * add to parent tree item parentItem.getChildren().add(entry.getValue()); }
	 * parentItem.setExpanded(true); } } root.setExpanded(true); MNU.setRoot(root);
	 * 
	 * }
	 */
	void fillTree() {
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

	public boolean setTreeItem(ODB_MNU emp, TreeItem<ODB_MNU> root_tree) {
		if (emp.equals(root_tree.getValue())) {
			root_tree.setExpanded(false);
			return true;
		} else {
			for (TreeItem<ODB_MNU> emps : root_tree.getChildren()) {
				if (setTreeItem(emp, emps)) {
					// expand & return true, if item is a descendant of the current item
					root_tree.setExpanded(true);
					return true;
				}
			}
			return false; // set expanded to false before returning here?
		}
	}

	void Clear(TreeItem<String> root) {
		for (TreeItem<String> child : root.getChildren()) {
			child.setValue(child.getValue()
					.replace(String.valueOf(Character.toChars(Integer.parseUnsignedInt("2713", 16))), "")
					.replace(String.valueOf(Character.toChars(Integer.parseUnsignedInt("2718", 16))), ""));
			if (child.getChildren().isEmpty()) {

			} else {
				Clear(child);
			}
		}
	}

	void printChildren(TreeItem<String> root, Integer usrid) {
		for (TreeItem<String> child : root.getChildren()) {
			Integer act = Integer.valueOf(child.getValue().substring(0, child.getValue().indexOf(":")));
			if (DBUtil.ODB_MNU(usrid, act) == 1) {
				child.setValue(
						child.getValue() + String.valueOf(Character.toChars(Integer.parseUnsignedInt("2713", 16))));
			} else {
				child.setValue(
						child.getValue() + String.valueOf(Character.toChars(Integer.parseUnsignedInt("2718", 16))));
			}
			if (child.getChildren().isEmpty()) {

			} else {
				printChildren(child, usrid);
			}
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AccessMNU");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	public void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
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
	void AddAction(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (MNU.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ��������");
			} else {

				// TreeItem<String> selectedItem =
				// MNU.getSelectionModel().getSelectedItem();
				/*
				 * int index = selectedItem.getParent().getChildren().indexOf(selectedItem);
				 */
				// selectedItem.getChildren().add(new
				// TreeItem<>(String.valueOf(selectedItem.getChildren()) + ":"));

				// selectedItem.getParent().getChildren().add(index + 1, new
				// TreeItem<>(String.valueOf(index + 2) + ":"));

				// TreeItem newEmployee = new TreeItem<>("New Employee");
				// getTreeItem().getChildren().add(newEmployee);
			}
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

	@SuppressWarnings("unused")
	private final class TextFieldTreeCellImpl extends TreeCell<String> {

		private TextField textField;

		@Override
		public void startEdit() {
			super.startEdit();

			if (textField == null) {
				createTextField();
			}
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText((String) getItem());
			setGraphic(getTreeItem().getGraphic());
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (Users.getSelectionModel().getSelectedItem() != null) {
					USR usr = Users.getSelectionModel().getSelectedItem();
					Integer act = Integer.valueOf(item.substring(0, item.indexOf(":")));
					if (DBUtil.ODB_MNU(usr.getIUSRID(), act) == 1) {
						setStyle("-fx-text-fill: green;-fx-font-weight: bold");
					} else {
						setStyle("");
					}
				}

				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(getTreeItem().getGraphic());
				}
			}
		}

		String NewAction(String str) {
			String ret = null;
			try {
				Main.logger = Logger.getLogger(getClass());
				TreeItem<ODB_MNU> selectedItem = MNU.getSelectionModel().getSelectedItem();

				ODB_MNU partext = selectedItem.getParent().getValue();

				Integer pact = partext.getMNU_ID();
				// Integer pact = Integer.valueOf(partext.substring(0, partext.indexOf(":")));
				String text = partext.getMNU_NAME();
				// str.substring(str.indexOf(":"));
				Integer act = Integer.valueOf(str.substring(0, str.indexOf(":")));

				String generatedColumns[] = { "MNU_ID" };
				PreparedStatement pstmt = conn.prepareStatement(
						"insert into ODB_MNU (MNU_PARENT,MNU_NPP,MNU_NAME) values (?,?,?) ", generatedColumns);
				pstmt.setInt(1, pact);
				pstmt.setInt(2, act);
				pstmt.setString(3, text);
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					ret = String.valueOf(rs.getInt(1)) + ":" + text;
					conn.commit();
				}
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				Msg.Message(ExceptionUtils.getStackTrace(e));
				Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
			return ret;
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyReleased((KeyEvent t) -> {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(NewAction(textField.getText()));
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			});

		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}
}
