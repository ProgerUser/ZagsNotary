package ru.psv.mj.sprav.courts;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class CourtList {

	public CourtList() {
		Main.logger = Logger.getLogger(getClass());
	}
	
	@FXML
	private BorderPane BP;
	
	  @FXML
	    private TableView<VCOURTS> COURTS;

	    @FXML
	    private TableColumn<VCOURTS, Long> ID;

	    @FXML
	    private TableColumn<VCOURTS, String> NAME;

	    @FXML
	    private TableColumn<VCOURTS, String> ABH_NAME;

	    @FXML
	    private TableColumn<VCOURTS, String> OTD;

	@FXML
	void Add(ActionEvent event) {
		// �������� �������
		if (DbUtil.Odb_Action(202l) == 0) {
			Msg.Message("��� �������!");
			return;
		}

		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) COURTS.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/sprav/courts/IUCourt.fxml"));

			AddCourt controller = new AddCourt();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("�������� ���");
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
		// �������� �������
		if (DbUtil.Odb_Action(123l) == 0) {
			Msg.Message("��� �������!");
			return;
		}
		if (COURTS.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("�������� ������!");
		} else {
			Edit(COURTS.getSelectionModel().getSelectedItem().getID(), (Stage) COURTS.getScene().getWindow());
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		// �������� �������
		if (DbUtil.Odb_Action(204l) == 0) {
			Msg.Message("��� �������!");
			return;
		}

		try {
			if (COURTS.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("�������� ������!");
			} else {
				Stage stage = (Stage) COURTS.getScene().getWindow();
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
							PreparedStatement delete = conn
									.prepareStatement("declare " + "pragma autonomous_transaction;" + "begin "
											+ " delete from COURTS where ID = ?;" + "commit;" + "end;");
							VCOURTS cl = COURTS.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getID());
							delete.executeUpdate();
							delete.close();
							Init();
						} catch (SQLException e) {
							try {
								conn.rollback();
							} catch (SQLException e1) {
								DbUtil.Log_Error(e);
							}
							DbUtil.Log_Error(e);
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
						.prepareStatement("select * from VCOURTS where  ID = ? /*for update nowait*/");
				VCOURTS courts = Init2(docid);
				selforupd.setLong(1, courts.getID());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/ru/psv/mj/sprav/courts/IUCourt.fxml"));

						EditCourt controller = new EditCourt();
						controller.setId(courts.getID());
						controller.setConn(conn, courts);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("��������������: " + courts.getNAME());
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
						Msg.Message("������ ������������� ������ �������������!");
						DbUtil.Log_Error(e);
					} else {
						DbUtil.Log_Error(e);
					}
				}

			} else {
				Msg.Message("����� �������������� ��� �������!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private Button TEST;

	 
	@FXML
	private void initialize() {
		try {
			
			
//			addIfNotPresent(BP.getStyleClass(), JMetroStyleClass.BACKGROUND);
//			addIfNotPresent(OTD.getStyleClass(), JMetroStyleClass.TABLE_GRID_LINES);
//			addIfNotPresent(OTD.getStyleClass(), JMetroStyleClass.ALTERNATING_ROW_COLORS);
			dbConnect();
//			Platform.runLater(() -> {
//			});
		
//			MaterialDesignIconView materialDesignIconView = new MaterialDesignIconView(MaterialDesignIcon.THUMB_UP);
//			materialDesignIconView.setSize("4em");
		    ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
		    NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
		    ABH_NAME.setCellValueFactory(cellData -> cellData.getValue().ABH_NAMEProperty());
		    OTD.setCellValueFactory(cellData -> cellData.getValue().COTDNAMEProperty());
		    
			// ������� ������
			COURTS.setRowFactory(tv -> {
				TableRow<VCOURTS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (DbUtil.Odb_Action(203l) == 0) {
						Msg.Message("��� �������!");
						return;
					}
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit(COURTS.getSelectionModel().getSelectedItem().getID(),
								(Stage) COURTS.getScene().getWindow());
					}
				});
				return row;
			});
			Init();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	VCOURTS Init2(Long id) {
		VCOURTS list = null;
		try {
			String selectStmt = "select * from VCOURTS where ID = ? ";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				list = new VCOURTS();
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setID(rs.getLong("ID"));
				list.setABH_NAME(rs.getString("ABH_NAME"));
				list.setNAME_ROD(rs.getString("NAME_ROD"));
				list.setNAME(rs.getString("NAME"));
				list.setAREA_ID(rs.getLong("AREA_ID"));
				list.setOTD(rs.getLong("OTD"));
				list.setIOTDNUM(rs.getLong("IOTDNUM"));
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
			String selectStmt = "select * from VCOURTS";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VCOURTS> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				VCOURTS list = new VCOURTS();
				list.setCOTDNAME(rs.getString("COTDNAME"));
				list.setID(rs.getLong("ID"));
				list.setABH_NAME(rs.getString("ABH_NAME"));
				list.setNAME_ROD(rs.getString("NAME_ROD"));
				list.setNAME(rs.getString("NAME"));
				list.setAREA_ID(rs.getLong("AREA_ID"));
				list.setOTD(rs.getLong("OTD"));
				list.setIOTDNUM(rs.getLong("IOTDNUM"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			COURTS.setItems(dlist);

			TableFilter<VCOURTS> tableFilter = TableFilter.forTableView(COURTS).apply();
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
