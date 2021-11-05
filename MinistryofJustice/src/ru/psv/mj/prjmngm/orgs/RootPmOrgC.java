package ru.psv.mj.prjmngm.orgs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import ru.psv.mj.utils.DbUtil;

public class RootPmOrgC {
	/**
	 * Конструктор
	 */
	public RootPmOrgC() {
		Main.logger = Logger.getLogger(getClass());
	}

	// <TableView>
	@FXML
	private TableView<PM_ORG> PM_ORG;
	// </TableView>
	// <TableColumn>
	@FXML
	private TableColumn<PM_ORG, Long> ORG_ID;
	@FXML
	private TableColumn<PM_ORG, String> ORG_NAME;
	@FXML
	private TableColumn<PM_ORG, String> ORG_RUK;
	@FXML
	private TableColumn<PM_ORG, String> ORG_SHNAME;
	@FXML
	private TableColumn<PM_ORG, String> ORG_DOLJ;
	@FXML
	private TableColumn<PM_ORG, String> ORG_OBRASH;
	// </TableColumn>

	/**
	 * Добавить
	 * 
	 * @param event
	 */
	@FXML
	private void Add(ActionEvent event) {
		try {

//			// права
//			if (DbUtil.Odb_Action(Long.valueOf(245)) == 0) {
//				Msg.Message("Нет доступа!");
//				return;
//			}

			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/orgs/IUPmOrg.fxml"));

			AddPmOrgC controller = new AddPmOrgC();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить: ");
			stage.initOwner((Stage) PM_ORG.getScene().getWindow());
			stage.setResizable(true);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					try {
						controller.dbDisconnect();
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
//			if (DbUtil.Odb_Action(Long.valueOf(243)) == 0) {
//				Msg.Message("Нет доступа!");
//				return;
//			}
			// выбранная запись
			PM_ORG sel = PM_ORG.getSelectionModel().getSelectedItem();
			if (sel != null) {
				// удержать
				PreparedStatement selforupd = conn
						.prepareStatement("SELECT * FROM PM_DOC_TYPES WHERE DOC_TP_ID = ? FOR UPDATE NOWAIT");
				selforupd.setLong(1, sel.getORG_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					// add lock row
					String lock = DbUtil.Lock_Row(sel.getORG_ID(), "PM_DOC_TYPES", conn);
					if (lock != null) {// if error add row
						Msg.Message(lock);
						conn.rollback();
						return;
					}
					// <FXML>---------------------------------------
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ru/psv/mj/prjmngm/orgs/IUPmOrg.fxml"));

					EditPmOrgC controller = new EditPmOrgC();
					controller.SetClass(sel, conn);

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Редактирование: " + sel.getORG_ID());
					stage.initOwner((Stage) PM_ORG.getScene().getWindow());
					stage.setResizable(true);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							try {
								if (controller.getStatus()) {
									conn.commit();
									// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
									String lock = DbUtil.Lock_Row_Delete(sel.getORG_ID(), "PM_ORG", conn);
									if (lock != null) {// if error add row
										Msg.Message(lock);
									}
									LoadTable();
								} else {
									conn.rollback();
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
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(sel.getORG_ID(), "PM_ORG"));
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
//			new ConvConst().TableColumnDate(EMP_WORKEND);
//			new ConvConst().TableColumnDate(EMP_WORKSTART);
			// init table column
			ORG_ID.setCellValueFactory(cellData -> cellData.getValue().ORG_IDProperty().asObject());
			ORG_NAME.setCellValueFactory(cellData -> cellData.getValue().ORG_NAMEProperty());
			ORG_RUK.setCellValueFactory(cellData -> cellData.getValue().ORG_RUKProperty());
			ORG_SHNAME.setCellValueFactory(cellData -> cellData.getValue().ORG_SHNAMEProperty());
			ORG_DOLJ.setCellValueFactory(cellData -> cellData.getValue().ORG_DOLJProperty());
			ORG_OBRASH.setCellValueFactory(cellData -> cellData.getValue().ORG_OBRASHProperty());
			// connect
			dbConnect();
			// load table
			LoadTable();
			/**
			 * Двойной щелчок по строке
			 */
			PM_ORG.setRowFactory(tv -> {
				TableRow<PM_ORG> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PM_ORG.getSelectionModel().getSelectedItem() != null) {
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
			String selectStmt = "select * from PM_ORG t";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<PM_ORG> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				PM_ORG list = new PM_ORG();
				list.setORG_ID(rs.getLong("ORG_ID"));
				list.setORG_NAME(rs.getString("ORG_NAME"));
				list.setORG_RUK(rs.getString("ORG_RUK"));
				list.setORG_SHNAME(rs.getString("ORG_SHNAME"));
				list.setORG_DOLJ(rs.getString("ORG_DOLJ"));
				list.setORG_OBRASH(rs.getString("ORG_OBRASH"));
				list.setORG_RUK_GENDER(rs.getString("ORG_RUK_GENDER"));
				obslist.add(list);
			}
			// add data
			PM_ORG.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<PM_ORG> tableFilter = TableFilter.forTableView(PM_ORG).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(PM_ORG);
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
