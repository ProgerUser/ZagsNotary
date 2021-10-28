package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class RootPmDocController {
	/**
	 * Конструктор
	 */
	public RootPmDocController() {
		Main.logger = Logger.getLogger(getClass());
	}

	// <TableView>
	@FXML
	private TableView<VPM_DOCS> PM_DOCS;
	// </TableView>
	// <TableColumn>
	@FXML
	private TableColumn<VPM_DOCS, Long> DOC_ID;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_NUMBER;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_TP_NAME;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_COMMENT;
	@FXML
	private TableColumn<Object, LocalDate> DOC_DATE;
	@FXML
	private TableColumn<Object, LocalDate> DOC_END;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_ISFAST;
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
			stage.initOwner((Stage) PM_DOCS.getScene().getWindow());
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
			VPM_DOCS sel = PM_DOCS.getSelectionModel().getSelectedItem();
			if (sel != null) {
				// удержать
				PreparedStatement selforupd = conn
						.prepareStatement("SELECT * FROM PM_DOCS WHERE DOC_ID = ? FOR UPDATE NOWAIT");
				selforupd.setLong(1, sel.getDOC_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					// add lock row
					String lock = DbUtil.Lock_Row(sel.getDOC_ID(), "PM_DOCS", conn);
					if (lock != null) {// if error add row
						Msg.Message(lock);
						conn.rollback();
						return;
					}
					// <FXML>---------------------------------------
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/emps/IUPmEmp.fxml"));
					EditPmDocC controller = new EditPmDocC();
					// controller.SetClass(sel, conn);

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Редактирование: " + sel.getDOC_ID());
					stage.initOwner((Stage) PM_DOCS.getScene().getWindow());
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
									String lock = DbUtil.Lock_Row_Delete(sel.getDOC_ID(), "PM_DOCS", conn);
									if (lock != null) {// if error add row
										Msg.Message(lock);
									}
									LoadTable();
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
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(sel.getDOC_ID(), "PM_DOCS"));
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
			new ConvConst().TableColumnDate(DOC_DATE);
			new ConvConst().TableColumnDate(DOC_END);
			// init table column
			DOC_ID.setCellValueFactory(cellData -> cellData.getValue().DOC_IDProperty().asObject());
			DOC_COMMENT.setCellValueFactory(cellData -> cellData.getValue().DOC_COMMENTProperty());
			DOC_ISFAST.setCellValueFactory(cellData -> cellData.getValue().DOC_ISFASTProperty());
			DOC_DATE.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).DOC_DATEProperty());
			DOC_TP_NAME.setCellValueFactory(cellData -> cellData.getValue().DOC_TP_NAMEProperty());
			DOC_END.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).DOC_ENDProperty());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			dbConnect();
			// load table
			LoadTable();
			/**
			 * Двойной щелчок по строке
			 */
			PM_DOCS.setRowFactory(tv -> {
				TableRow<VPM_DOCS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PM_DOCS.getSelectionModel().getSelectedItem() != null) {
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
			String selectStmt = "select * from VPM_DOCS t";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_DOCS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_DOCS list = new VPM_DOCS();
				list.setDOC_ID(rs.getLong("DOC_ID"));
				list.setDOC_COMMENT(rs.getString("DOC_COMMENT"));
				list.setDOC_ISFAST(rs.getString("DOC_ISFAST"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_REF(rs.getLong("DOC_REF"));
				list.setDOC_TP_NAME(rs.getString("DOC_TP_NAME"));
				list.setDOC_END((rs.getDate("DOC_END") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_END")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setDOC_TP_ID(rs.getLong("DOC_TP_ID"));
				list.setDOC_USR(rs.getString("DOC_USR"));
				obslist.add(list);
			}
			// add data
			PM_DOCS.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_DOCS> tableFilter = TableFilter.forTableView(PM_DOCS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(PM_DOCS);
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
