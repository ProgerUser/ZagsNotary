package mj.prjmngm.emps;

import java.io.Reader;
import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class RootPmEmpController {
	/**
	 * Конструктор
	 */
	public RootPmEmpController() {
		Main.logger = Logger.getLogger(getClass());
	}

	// <TableView>
	@FXML
	private TableView<VPM_EMP> PM_EMP;
	// </TableView>

	// <TableColumn>
	@FXML
	private TableColumn<VPM_EMP, Long> EMP_ID;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_LASTNAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_FIRSTNAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_MIDDLENAME;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_POSITION;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_TEL;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_EMAIL;
	@FXML
	private TableColumn<VPM_EMP, String> EMP_LOGIN;
	// </TableColumn>

	/**
	 * Добавить
	 * 
	 * @param event
	 */
	@FXML
	private void Add(ActionEvent event) {
		try {

			if (DbUtil.Odb_Action(0l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Удалить
	 * 
	 * @param event
	 */
	@FXML
	private void Delete(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Редактировать
	 * 
	 * @param event
	 */
	@FXML
	private void Edit(ActionEvent event) {
		try {
			// права
			if (DbUtil.Odb_Action(0l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			// выбранная запись
			VPM_EMP sel = PM_EMP.getSelectionModel().getSelectedItem();
			if (sel != null) {
				// удержать
				PreparedStatement selforupd = conn
						.prepareStatement("select * from PM_EMP where EMP_ID = ? for update nowait");
				selforupd.setLong(1, sel.getEMP_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					// add lock row
					String lock = DbUtil.Lock_Row(sel.getEMP_ID(), "PM_EMP", conn);
					if (lock != null) {// if error add row
						Msg.Message(lock);
						conn.rollback();
						return;
					}
					// <FXML>
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/mj/prjmngm/emps/IUPmEmp.fxml"));
					EditPmEmpController controller = new EditPmEmpController();
					controller.SetClass(sel, conn);
					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Редактирование: " + sel.getEMP_ID());
					stage.initOwner((Stage) PM_EMP.getScene().getWindow());
					stage.setResizable(false);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							try {
								if (controller.getStatus()) {
									conn.commit();
									// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
									String lock = DbUtil.Lock_Row_Delete(sel.getEMP_ID(), "PM_EMP", conn);
									if (lock != null) {// if error add row
										Msg.Message(lock);
									}
								} // Если нажали "X" или "Cancel" и до этого что-то меняли
								else if (!controller.getStatus() & CompareBeforeClose(sel.getEMP_ID()) == 1) {
									final Alert alert = new Alert(AlertType.CONFIRMATION,
											"Закрыть форму без сохранения?", ButtonType.YES, ButtonType.NO);
									if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
											.orElse(ButtonType.NO) == ButtonType.YES) {
										conn.rollback();
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(sel.getEMP_ID(), "PM_EMP", conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
									} else if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
											.orElse(ButtonType.NO) == ButtonType.NO) {
										paramT.consume();
									}
								} // Если нажали "X" или "Cancel" и до этого ничего не меняли
								else if (!controller.getStatus() & CompareBeforeClose(sel.getEMP_ID()) == 0) {
									conn.rollback();
									// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
									String lock = DbUtil.Lock_Row_Delete(sel.getEMP_ID(), "PM_EMP", conn);
									if (lock != null) {// if error add row
										Msg.Message(lock);
									}
								}
							} catch (Exception e) {
								DbUtil.Log_Error(e);
							}
						}
					});
					// </FXML>
				} catch (SQLException e) {
					if (e.getErrorCode() == 54) {
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(sel.getEMP_ID(), "PM_EMP"));
						DbUtil.Log_Error(e);
					} else {
						DbUtil.Log_Error(e);
					}
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Сравнение данных
	 * 
	 * @return
	 */
	Long CompareBeforeClose(Long docid) {
		Long ret = 0l;
		try {
			Clob lob = conn.createClob();
			lob.setString(1, RetXml);
			CallableStatement callStmt = conn.prepareCall("{ call Mercer.CompareXmls(?,?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.setClob(2, lob);
			callStmt.registerOutParameter(3, Types.VARCHAR);
			callStmt.registerOutParameter(4, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(3) == null) {
				ret = callStmt.getLong(4);
				System.out.println("ret=" + callStmt.getLong(4));
			} else {
				Msg.Message(callStmt.getString(3));
				Main.logger.error(callStmt.getString(6) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

		return ret;
	}

	String RetXml;

	/**
	 * Возврат XML файлов для сравнения
	 */
	void XmlsForCompare(Long docid) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call Mercer.RetXmls(?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.registerOutParameter(2, Types.VARCHAR);
			callStmt.registerOutParameter(3, Types.CLOB);
			callStmt.execute();
			if (callStmt.getString(2) == null) {
				// clob
				Clob retcl = callStmt.getClob(3);
				// chars
				char char_xmls[] = new char[(int) retcl.length()];
				// read
				Reader r1 = retcl.getCharacterStream();
				r1.read(char_xmls);
				// strings
				RetXml = new String(char_xmls);
				// System.out.println(RetXml);
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Обновить
	 * 
	 * @param event
	 */
	@FXML
	private void Reshresh(ActionEvent event) {
		try {
			LoadTable();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			// init table column
			EMP_EMAIL.setCellValueFactory(cellData -> cellData.getValue().EMP_EMAILProperty());
			EMP_ID.setCellValueFactory(cellData -> cellData.getValue().EMP_IDProperty().asObject());
			EMP_TEL.setCellValueFactory(cellData -> cellData.getValue().EMP_TELProperty());
			EMP_LOGIN.setCellValueFactory(cellData -> cellData.getValue().EMP_LOGINProperty());
			EMP_POSITION.setCellValueFactory(cellData -> cellData.getValue().EMP_POSITIONProperty());
			EMP_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().EMP_FIRSTNAMEProperty());
			EMP_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().EMP_LASTNAMEProperty());
			EMP_MIDDLENAME.setCellValueFactory(cellData -> cellData.getValue().EMP_MIDDLENAMEProperty());
			// connect
			dbConnect();
			// load table
			LoadTable();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Initialize table
	 */
	void LoadTable() {
		try {
			// loop
			String selectStmt = "select * from VPM_EMP t";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_EMP> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_EMP list = new VPM_EMP();
				list.setEMP_EMAIL(rs.getString("EMP_EMAIL"));
				list.setEMP_ID(rs.getLong("EMP_ID"));
				list.setEMP_TEL(rs.getString("EMP_TEL"));
				list.setEMP_LOGIN(rs.getString("EMP_LOGIN"));
				list.setEMP_POSITION(rs.getString("EMP_POSITION"));
				list.setEMP_FIRSTNAME(rs.getString("EMP_FIRSTNAME"));
				list.setEMP_LASTNAME(rs.getString("EMP_LASTNAME"));
				list.setEMP_MIDDLENAME(rs.getString("EMP_MIDDLENAME"));
				obslist.add(list);
			}
			// add data
			PM_EMP.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_EMP> tableFilter = TableFilter.forTableView(PM_EMP).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(PM_EMP);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Авто расширение
	 * 
	 * @param table
	 */
	void ResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column_) -> {
			if (column_.getText().equals("")) {
			} else {
				Text t = new Text(column_.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					if (column_.getCellData(i) != null) {
						if (column_.getCellData(i) != null) {
							t = new Text(column_.getCellData(i).toString());
							double calcwidth = t.getLayoutBounds().getWidth();
							if (calcwidth > max) {
								max = calcwidth;
							}
						}
					}
				}
				column_.setPrefWidth(max + 20.0d);
			}
		});
	}

	// <ORACLE_CONNECT>
	/**
	 * Строка соединения
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.setProperty("password", Connect.userPassword);
			props.setProperty("user", Connect.userID);
			props.put("v$session.osuser", System.getProperty("user.name").toString());
			props.put("v$session.machine", InetAddress.getLocalHost().getHostAddress());
			props.put("v$session.program", getClass().getName());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + Connect.connectionURL, props);
			conn.setAutoCommit(false);
			DbUtil.Run_Process(conn, getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрыть
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
				conn.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	// </ORACLE_CONNECT>
}
