package mj.zags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class ZagsList {

	public ZagsList() {
		Main.logger = Logger.getLogger(getClass());
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
	void Add(ActionEvent event) {
		// проверка доступа
		if (DbUtil.Odb_Action(126l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}

		try {
			Main.logger = Logger.getLogger(getClass());
			Stage stage = new Stage();
			Stage stage_ = (Stage) ZAGS.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/zags/IUZags.fxml"));

			AddZags controller = new AddZags();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить новую запись");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						Init();
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		// проверка доступа
		if (DbUtil.Odb_Action(127l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}
		if (ZAGS.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(ZAGS.getSelectionModel().getSelectedItem().getZAGS_ID(), (Stage) ZAGS.getScene().getWindow());
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		// проверка доступа
		if (DbUtil.Odb_Action(128l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}

		try {
			if (ZAGS.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				Main.logger = Logger.getLogger(getClass());

				Stage stage = (Stage) ZAGS.getScene().getWindow();
				Label alert = new Label("Удалить запись?");
				alert.setLayoutX(75.0);
				alert.setLayoutY(11.0);
				alert.setPrefHeight(17.0);

				Button no = new Button();
				no.setText("Нет");
				no.setLayoutX(111.0);
				no.setLayoutY(56.0);
				no.setPrefWidth(72.0);
				no.setPrefHeight(21.0);

				Button yes = new Button();
				yes.setText("Да");
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
							PreparedStatement delete = conn
									.prepareStatement("declare " + "pragma autonomous_transaction;" + "begin "
											+ " delete from ZAGS where ZAGS_ID = ?;" + "commit;" + "end;");
							VZAGS cl = ZAGS.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getZAGS_ID());
							delete.executeUpdate();
							delete.close();

							Init();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								Msg.Message(ExceptionUtils.getStackTrace(e1));
								Main.logger.error(
										ExceptionUtils.getStackTrace(e1) + "~" + Thread.currentThread().getName());
							}
							DbUtil.Log_Error(e);
						}
						newWindow_yn.close();
					}
				});
				newWindow_yn.setTitle("Внимание");
				newWindow_yn.setScene(ynScene);
				newWindow_yn.initModality(Modality.WINDOW_MODAL);
				newWindow_yn.initOwner(stage);
				newWindow_yn.setResizable(false);
				newWindow_yn.getIcons().add(new Image("/icon.png"));
				newWindow_yn.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	boolean isopen = false;

	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {
				Main.logger = Logger.getLogger(getClass());
				PreparedStatement selforupd = conn
						.prepareStatement("select * from ZAGS where ZAGS_ID = ? /*for update nowait*/");
				VZAGS VZAGS = Init2(docid);
				selforupd.setLong(1, VZAGS.getZAGS_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/zags/IUZags.fxml"));

						EditZags controller = new EditZags();
						controller.setId(VZAGS.getZAGS_ID());
						controller.setConn(conn, VZAGS);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: " + VZAGS.getZAGS_NAME());
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									if (controller.getStatus()) {
										conn.commit();
										Init();
									} else
										conn.rollback();
									isopen = false;
								} catch (SQLException e) {
									DbUtil.Log_Error(e);
								}

							}
						});
						stage.show();
						isopen = true;
					}
				} catch (SQLException e) {
					if (e.getErrorCode() == 54) {
						Msg.Message("Клиент редактируется другим пользователем!");
						DbUtil.Log_Error(e);
					} else {
						DbUtil.Log_Error(e);
					}
				}

			} else {
				Msg.Message("Форма редактирования уже открыта!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DbUtil.Run_Process(conn);
			ZAGS_NAME.setCellValueFactory(cellData -> cellData.getValue().ZAGS_NAMEProperty());
			ZAGS_OTD.setCellValueFactory(cellData -> cellData.getValue().COTDNAMEProperty());
			ZAGS_RUK.setCellValueFactory(cellData -> cellData.getValue().ZAGS_RUKProperty());
			ZAGS_ID.setCellValueFactory(cellData -> cellData.getValue().ZAGS_IDProperty().asObject());

			// двойной щелчок
			ZAGS.setRowFactory(tv -> {
				TableRow<VZAGS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (DbUtil.Odb_Action(127l) == 0) {
						Msg.Message("Нет доступа!");
						return;
					}
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit(ZAGS.getSelectionModel().getSelectedItem().getZAGS_ID(),
								(Stage) ZAGS.getScene().getWindow());
					}
				});
				return row;
			});
			Init();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	VZAGS Init2(Long id) {
		VZAGS list = null;
		try {
			Main.logger = Logger.getLogger(getClass());
			String selectStmt = "select * from VZAGS where ZAGS_ID = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				list = new VZAGS();
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
			}
			prepStmt.close();
			rs.close();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return list;
	}

	void Init() {
		try {
			String selectStmt = "select * from VZAGS order by ZAGS_ID desc";
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
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "VZAGSList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
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
