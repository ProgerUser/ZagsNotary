package ru.psv.mj.prjmngm.inboxdocs;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class RootPmDoc {
	/**
	 * Конструктор
	 */
	public RootPmDoc() {
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
	@FXML
	private TableColumn<Object, LocalDate> EMP_WORKSTART;
	@FXML
	private TableColumn<Object, LocalDate> EMP_WORKEND;
	// </TableColumn>

	/**
	 * Добавить
	 * 
	 * @param event
	 */
	@FXML
	private void Add(ActionEvent event) {
		try {

			// права
			if (DbUtil.Odb_Action(Long.valueOf(245)) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/emps/IUPmEmp.fxml"));

			AddPmDocC controller = new AddPmDocC();
			controller.SetConn(conn);
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить: ");
			stage.initOwner((Stage) PM_EMP.getScene().getWindow());
			stage.setResizable(true);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					try {
						LoadTable();
					} catch (Exception e) {
						DbUtil.Log_Error(e);
					}
				}
			});
			stage.show();
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
			// права
			if (DbUtil.Odb_Action(Long.valueOf(244)) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
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
			if (DbUtil.Odb_Action(Long.valueOf(243)) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			// выбранная запись
			VPM_EMP sel = PM_EMP.getSelectionModel().getSelectedItem();
			if (sel != null) {
				// удержать
				PreparedStatement selforupd = conn
						.prepareStatement("SELECT * FROM PM_EMP WHERE EMP_ID = ? FOR UPDATE NOWAIT");
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
					// xml
					XmlsForCompare(sel.getEMP_ID());
					// <FXML>---------------------------------------
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/emps/IUPmEmp.fxml"));
					EditPmDocC controller = new EditPmDocC();
					controller.SetClass(sel, conn);
					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Редактирование: " + sel.getEMP_ID());
					stage.initOwner((Stage) PM_EMP.getScene().getWindow());
					stage.setResizable(true);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							try {
								// сохрнаить для сравнения
								controller.SaveCompare();
								if (controller.getStatus()) {
									conn.commit();
									// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
									String lock = DbUtil.Lock_Row_Delete(sel.getEMP_ID(), "PM_EMP", conn);
									if (lock != null) {// if error add row
										Msg.Message(lock);
									}
									LoadTable();
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
										LoadTable();
									} else {
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
					stage.show();
					// </FXML>---------------------------------------
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
			CallableStatement callStmt = conn.prepareCall("{ call PM_EMP_PKG.CompareXmls(?,?,?,?)}");
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
			CallableStatement callStmt = conn.prepareCall("{ call PM_EMP_PKG.RetXmls(?,?,?)}");
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
			new ConvConst().TableColumnDate(EMP_WORKEND);
			new ConvConst().TableColumnDate(EMP_WORKSTART);
			// init table column
			EMP_EMAIL.setCellValueFactory(cellData -> cellData.getValue().EMP_EMAILProperty());
			EMP_ID.setCellValueFactory(cellData -> cellData.getValue().EMP_IDProperty().asObject());
			EMP_TEL.setCellValueFactory(cellData -> cellData.getValue().EMP_TELProperty());
			EMP_LOGIN.setCellValueFactory(cellData -> cellData.getValue().EMP_LOGINProperty());
			EMP_POSITION.setCellValueFactory(cellData -> cellData.getValue().EMP_POSITIONProperty());
			EMP_FIRSTNAME.setCellValueFactory(cellData -> cellData.getValue().EMP_FIRSTNAMEProperty());
			EMP_LASTNAME.setCellValueFactory(cellData -> cellData.getValue().EMP_LASTNAMEProperty());
			EMP_MIDDLENAME.setCellValueFactory(cellData -> cellData.getValue().EMP_MIDDLENAMEProperty());
			EMP_WORKSTART.setCellValueFactory(cellData -> ((VPM_EMP)cellData.getValue()).EMP_WORKSTARTProperty());
			EMP_WORKEND.setCellValueFactory(cellData -> ((VPM_EMP)cellData.getValue()).EMP_WORKENDProperty());
			// connect
			dbConnect();
			// load table
			LoadTable();
			/**
			 * Двойной щелчок по строке
			 */
			PM_EMP.setRowFactory(tv -> {
				TableRow<VPM_EMP> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PM_EMP.getSelectionModel().getSelectedItem() != null) {
							Edit(null);
						}
					}
				});
				return row;
			});
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
				list.setEMP_WORKSTART((rs.getDate("EMP_WORKSTART") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKSTART")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setEMP_TEL(rs.getString("EMP_TEL"));
				list.setEMP_LOGIN(rs.getString("EMP_LOGIN"));
				list.setEMP_LOGIN_L(rs.getLong("EMP_LOGIN_L"));
				list.setEMP_POSITION(rs.getString("EMP_POSITION"));
				list.setEMP_FIRSTNAME(rs.getString("EMP_FIRSTNAME"));
				list.setEMP_LASTNAME(rs.getString("EMP_LASTNAME"));
				list.setEMP_MIDDLENAME(rs.getString("EMP_MIDDLENAME"));
				list.setEMP_WORKEND((rs.getDate("EMP_WORKEND") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("EMP_WORKEND")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);

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
			conn = DbUtil.GetConnect(getClass().getName());
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
