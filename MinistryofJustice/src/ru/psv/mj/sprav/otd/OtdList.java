package ru.psv.mj.sprav.otd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.tableview2.FilteredTableColumn;
import org.controlsfx.control.tableview2.FilteredTableView;
import org.controlsfx.control.tableview2.cell.TextField2TableCell;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;

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
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;
import ru.psv.mj.admin.users.OTD;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.DbTracer;

public class OtdList {

	public OtdList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private BorderPane BP;

	@FXML
	private FilteredTableColumn<OTD, String> COTDNAME;

	@FXML
	private FilteredTableColumn<OTD, Long> IOTDNUM;

	@FXML
	private FilteredTableColumn<OTD, String> RAION;

	@FXML
	private FilteredTableView<OTD> OTD;

	@FXML
	public void Tbl2Click(MouseEvent event) {
		if (event.getClickCount() == 2) // Checking double click
		{
			if (DbUtil.Odb_Action(123l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			Edit(OTD.getSelectionModel().getSelectedItem().getIOTDNUM(), (Stage) OTD.getScene().getWindow());
		}
	}

	@FXML
	void Add(ActionEvent event) {
		try {
//			Form login = Form.of(Group.of(Field.ofStringType("CHF").label("Currency"),
//					Field.ofSingleSelectionType(Arrays.asList("`12`12", "Bern (BE)"), 1)
//							.label("Capital"))
//			).title("Login");
//			
//			BP.setBottom(new FormRenderer(login));
//			
//			BP.setRight(SCB);

			Notifications.create().darkStyle().title("Title").graphic(new Rectangle(600, 400, Color.AQUA))
					.hideAfter(Duration.seconds(10)).show();

			// проверка доступа
			if (DbUtil.Odb_Action(122l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) OTD.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/mj/otd/IUOtd.fxml"));

			AddOtd controller = new AddOtd();
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
		
		Form loginForm = Form.of(
				Group.of(
					Field.ofStringType("").label("Username"),
					Field.ofStringType("").label("Password").required("This field can’t be empty")
					)
				).title("Login");
		
		Pane root = new Pane();
		root.getChildren().add(new FormRenderer(loginForm));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
//		// проверка доступа
//		if (DbUtil.Odb_Action(123l) == 0) {
//			Msg.Message("Нет доступа!");
//			return;
//		}
//		if (OTD.getSelectionModel().getSelectedItem() == null) {
//			Msg.Message("Выберите строку!");
//		} else {
//			Edit(OTD.getSelectionModel().getSelectedItem().getIOTDNUM(), (Stage) OTD.getScene().getWindow());
//		}

	}

	@FXML
	void Delete(ActionEvent event) {
		// проверка доступа
		if (DbUtil.Odb_Action(124l) == 0) {
			Msg.Message("Нет доступа!");
			return;
		}

		try {
			if (OTD.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {

				Stage stage = (Stage) OTD.getScene().getWindow();
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
											+ " delete from otd where IOTDNUM = ?;" + "commit;" + "end;");
							OTD cl = OTD.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getIOTDNUM());
							delete.executeUpdate();
							delete.close();
							Init();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DbUtil.Log_Error(e1);
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
				PreparedStatement selforupd = conn
						.prepareStatement("select * from otd where  IOTDNUM = ? /*for update nowait*/");
				OTD otd = Init2(docid);
				selforupd.setLong(1, otd.getIOTDNUM());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/mj/otd/IUOtd.fxml"));

						EditOtd controller = new EditOtd();
						controller.setId(otd.getIOTDNUM());
						controller.setConn(conn, otd);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: " + otd.getCOTDNAME());
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
	private Button TEST;

	@FXML
	void F5(KeyEvent ke) {
		try {
			if (ke.getCode().equals(KeyCode.F5)) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) BP.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/mj/widgets/DbTracer.fxml"));

				DbTracer controller = new DbTracer();
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("DB queries tracer");
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {

					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void initialize() {
		try {

//			addIfNotPresent(BP.getStyleClass(), JMetroStyleClass.BACKGROUND);
//			addIfNotPresent(OTD.getStyleClass(), JMetroStyleClass.TABLE_GRID_LINES);
//			addIfNotPresent(OTD.getStyleClass(), JMetroStyleClass.ALTERNATING_ROW_COLORS);
			dbConnect();
			//DbUtil.Run_Process(conn, getClass().getName());
//			Platform.runLater(() -> {
//			});

//			MaterialDesignIconView materialDesignIconView = new MaterialDesignIconView(MaterialDesignIcon.THUMB_UP);
//			materialDesignIconView.setSize("4em");

			COTDNAME.setCellValueFactory(cellData -> cellData.getValue().COTDNAMEProperty());
			IOTDNUM.setCellValueFactory(cellData -> cellData.getValue().IOTDNUMProperty().asObject());
			RAION.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());

			COTDNAME.setCellFactory(TextField2TableCell.forTableColumn());
			IOTDNUM.setCellFactory(TextField2TableCell.forTableColumn(new StringConverter<Long>() {
				@Override
				public String toString(Long object) {
					return String.valueOf(object);
				}

				@Override
				public Long fromString(String string) {
					return Long.parseLong(string);
				}
			}));

			RAION.setCellFactory(TextField2TableCell.forTableColumn());

			OTD.rowHeaderVisibleProperty().set(true);

			SouthFilter<OTD, String> editorCOTDNAME = new SouthFilter<>(COTDNAME, String.class);
			SouthFilter<OTD, Long> editorIOTDNUM = new SouthFilter<>(IOTDNUM, Long.class);
			SouthFilter<OTD, String> editorRAION = new SouthFilter<>(RAION, String.class);

			COTDNAME.setSouthNode(editorCOTDNAME);
			IOTDNUM.setSouthNode(editorIOTDNUM);
			RAION.setSouthNode(editorRAION);

			// двойной щелчок
			OTD.setRowFactory(tv -> {
				TableRow<OTD> row = new TableRow<>();
				row.setOnMouseClicked(event -> {

					if (DbUtil.Odb_Action(123l) == 0) {
						Msg.Message("Нет доступа!");
						return;
					}
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit(OTD.getSelectionModel().getSelectedItem().getIOTDNUM(),
								(Stage) OTD.getScene().getWindow());
					}
				});
				return row;
			});

			Init();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	OTD Init2(Long id) {
		OTD list = null;
		try {
			String selectStmt = "select * from otd_area where IOTDNUM = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				list = new OTD();
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setAREA_ID(rs.getLong("AREA_ID"));
				list.setIOTDNUM(rs.getLong("IOTDNUM"));
				list.setNAME(rs.getString("NAME"));
				list.setCODE(rs.getLong("CODE"));
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
			String selectStmt = "select * from otd_area";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<OTD> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				OTD list = new OTD();
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setAREA_ID(rs.getLong("AREA_ID"));
				list.setIOTDNUM(rs.getLong("IOTDNUM"));
				list.setNAME(rs.getString("NAME"));
				list.setCODE(rs.getLong("CODE"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			FilteredTableView.configureForFiltering(OTD, dlist);
			// OTD.setItems(dlist);

//			TableFilter<OTD> tableFilter = TableFilter.forTableView(OTD).apply();
//			tableFilter.setSearchStrategy((input, target) -> {
//				try {
//					return target.toLowerCase().contains(input.toLowerCase());
//				} catch (Exception e) {
//					return false;
//				}
//			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	Connection conn;

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}
}
