package mj.notary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import mj.dbutil.DBUtil;
import mj.msg.Msg;

public class NotaryList {

	public NotaryList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TableColumn<VNOTARY, String> NOT_NAME;

	@FXML
	private TableColumn<VNOTARY, String> NOT_OTD;

	@FXML
	private TableColumn<VNOTARY, String> NOT_RUK;

	@FXML
	private TableView<VNOTARY> NOTARY;
	
    @FXML
    private TableColumn<VNOTARY, String> ADDRESS;

    @FXML
    private TableColumn<VNOTARY, String> TELEPHONE;

	@FXML
	private TableColumn<VNOTARY, Long> NOT_ID;

	@FXML
	void Add(ActionEvent event) {
		// проверка доступа
		if (DBUtil.OdbAction(126l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}

		try {

			Stage stage = new Stage();
			Stage stage_ = (Stage) NOTARY.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/notary/IUNotary.fxml"));

			AddNotary controller = new AddNotary();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить новую запись");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
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
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		// проверка доступа
		if (DBUtil.OdbAction(127l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}
		if (NOTARY.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите строку!");
		} else {
			Edit(NOTARY.getSelectionModel().getSelectedItem().getNOT_ID(), (Stage) NOTARY.getScene().getWindow());
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		// проверка доступа
		if (DBUtil.OdbAction(128l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}

		try {
			if (NOTARY.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
				Main.logger = Logger.getLogger(getClass());

				Stage stage = (Stage) NOTARY.getScene().getWindow();
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
											+ " delete from NOTARY where NOT_ID = ?;" + "commit;" + "end;");
							VNOTARY cl = NOTARY.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getNOT_ID());
							delete.executeUpdate();
							delete.close();

							Init();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DBUtil.LOG_ERROR(e1);
							}
							DBUtil.LOG_ERROR(e);
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
			DBUtil.LOG_ERROR(e);
		}
	}

	boolean isopen = false;

	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {
				PreparedStatement selforupd = conn
						.prepareStatement("select * from NOTARY where NOT_ID = ? /*for update nowait*/");
				VNOTARY VNOTARY = Init2(docid);
				selforupd.setLong(1, VNOTARY.getNOT_ID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/NOTARY/IUNOTARY.fxml"));

						EditNotary controller = new EditNotary();
						controller.setId(VNOTARY.getNOT_ID());
						controller.setConn(conn, VNOTARY);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: " + VNOTARY.getNOT_NAME());
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.initModality(Modality.WINDOW_MODAL);
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
									DBUtil.LOG_ERROR(e);
								}

							}
						});
						stage.show();
						isopen = true;
					}
				} catch (SQLException e) {
					if (e.getErrorCode() == 54) {
						Msg.Message("Клиент редактируется другим пользователем!");
						DBUtil.LOG_ERROR(e);
					} else {
						DBUtil.LOG_ERROR(e);
					}
				}
			} else {
				Msg.Message("Форма редактирования уже открыта!");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			NOT_NAME.setCellValueFactory(cellData -> cellData.getValue().NOT_NAMEProperty());
			NOT_OTD.setCellValueFactory(cellData -> cellData.getValue().COTDNAMEProperty());
			NOT_RUK.setCellValueFactory(cellData -> cellData.getValue().NOT_RUKProperty());
			NOT_ID.setCellValueFactory(cellData -> cellData.getValue().NOT_IDProperty().asObject());
			ADDRESS.setCellValueFactory(cellData -> cellData.getValue().NOT_ADDRESSProperty());
			TELEPHONE.setCellValueFactory(cellData -> cellData.getValue().NOT_TELEPHONEProperty());

			// двойной щелчок
			NOTARY.setRowFactory(tv -> {
				TableRow<VNOTARY> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (DBUtil.OdbAction(127l) == 0) {
						Msg.Message("Нет доступа!");
						return;
					}
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit(NOTARY.getSelectionModel().getSelectedItem().getNOT_ID(),
								(Stage) NOTARY.getScene().getWindow());
					}
				});
				return row;
			});
			Init();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	VNOTARY Init2(Long id) {
		VNOTARY list = null;
		try {
			String selectStmt = "select * from VNOTARY where NOT_ID = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				list = new VNOTARY();
				list.setNOT_NAME(rs.getString("NOT_NAME"));
				list.setNOT_ADDRESS(rs.getString("NOT_ADDRESS"));
				list.setNOT_TELEPHONE(rs.getString("NOT_TELEPHONE"));
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setNOT_ID(rs.getLong("NOT_ID"));
				list.setNOT_RUK(rs.getString("NOT_RUK"));
			}

			prepStmt.close();
			rs.close();

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return list;
	}

	void Init() {
		try {
			String selectStmt = "select * from VNOTARY";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VNOTARY> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				VNOTARY list = new VNOTARY();
				list.setNOT_NAME(rs.getString("NOT_NAME"));
				list.setNOT_ADDRESS(rs.getString("NOT_ADDRESS"));
				list.setNOT_TELEPHONE(rs.getString("NOT_TELEPHONE"));
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setNOT_ID(rs.getLong("NOT_ID"));
				list.setNOT_RUK(rs.getString("NOT_RUK"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			NOTARY.setItems(dlist);

			TableFilter<VNOTARY> tableFilter = TableFilter.forTableView(NOTARY).apply();
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

	Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "VNOTARYList");
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

}
