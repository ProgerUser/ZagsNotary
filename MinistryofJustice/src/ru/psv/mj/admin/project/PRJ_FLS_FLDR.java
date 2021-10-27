package ru.psv.mj.admin.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

public class PRJ_FLS_FLDR {

	public PRJ_FLS_FLDR() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private TableView<PRJ_FL_VER_HIST> Hist;
	@FXML
	private TableColumn<PRJ_FL_VER_HIST, Long> VERISION;
	@FXML
	private TableColumn<PRJ_FL_VER_HIST, LocalDateTime> DT;
//	@FXML
//	private TreeView<PROJECT> PROJECT;
//	@FXML
//	private TextField PRJ_ID;
	@FXML
	private TextField NameFind;
	@FXML
	private TextField ID_FIND;
	
	@FXML
	private ContextMenu ContMenu;
	//-----------------------------------
	@FXML
    private TreeTableView<PROJECT> PROJECT;
    @FXML
    private TreeTableColumn<PROJECT, String> PRJ_NAME;
    @FXML
    private TreeTableColumn<PROJECT, String> IS_FOLDER;
    @FXML
    private TreeTableColumn<PROJECT, Long> VERSION;
    @FXML
    private TreeTableColumn<PROJECT, Long> BYTES;
    @FXML
    private TreeTableColumn<PROJECT, Long> PRJ_IDS;

	@FXML
	void Add(ActionEvent event) {
		try {
			if (DbUtil.Odb_Action(148l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (PROJECT.getSelectionModel().getSelectedItem().getValue().getPRJ_ID() != null
					&& !PROJECT.getSelectionModel().getSelectedItem().getValue().getIS_FOLDER().equals("N")) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) Hist.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/mj/project/IUPrj.fxml"));

				PRJ_ADD controller = new PRJ_ADD();
				loader.setController(controller);
				controller.setId(PROJECT.getSelectionModel().getSelectedItem().getValue().getPRJ_ID());

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
							FillTree();
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									PROJECT.requestFocus();
									PROJECT.getSelectionModel().select(SelTbl);
									PROJECT.scrollTo(SelTbl);
								}
							});
						}
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		try {
			if (DbUtil.Odb_Action(149l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (PROJECT.getSelectionModel().getSelectedItem().getValue().getPRJ_ID() != null
					&& !PROJECT.getSelectionModel().getSelectedItem().getValue().getIS_FOLDER().equals("F")) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) Hist.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/mj/project/IUPrj.fxml"));

				PRJ_UPDATE controller = new PRJ_UPDATE();
				loader.setController(controller);
				controller.setPROJECT(PROJECT.getSelectionModel().getSelectedItem().getValue());
				controller.setId(PROJECT.getSelectionModel().getSelectedItem().getValue().getPRJ_ID());

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle(
						"Редактировать: " + PROJECT.getSelectionModel().getSelectedItem().getValue().getPRJ_NAME());
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						if (controller.getStatus()) {
							FillTree();
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									PROJECT.requestFocus();
									PROJECT.getSelectionModel().select(SelTbl);
									PROJECT.scrollTo(SelTbl);
								}
							});
						}
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		try {
			if (DbUtil.Odb_Action(150l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (PROJECT.getSelectionModel().getSelectedItem() != null) {
				// delete
				PreparedStatement prp = conn.prepareStatement("delete from PROJECT where PRJ_ID = ?");
				prp.setLong(1, PROJECT.getSelectionModel().getSelectedItem().getValue().getPRJ_ID());
				prp.executeUpdate();
				conn.commit();
				prp.close();
				// refresh
				FillTree();
			} else {
				Msg.Message("Выберите строку!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	TreeItem<PROJECT> root = null;

	PROJECT prj;

	void FillTree() {
		Map<Long, TreeItem<PROJECT>> itemById = new HashMap<>();
		Map<Long, Long> parents = new HashMap<>();
		String query = "select * from PROJECT";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				prj = new PROJECT();
				prj.setPRJ_ID(rs.getLong("PRJ_ID"));
				prj.setPRJ_PARENT(rs.getLong("PRJ_PARENT"));
				prj.setPRJ_NAME(rs.getString("PRJ_NAME"));
				prj.setIS_FOLDER(rs.getString("IS_FOLDER"));
				prj.setVERSION(rs.getLong("VERSION"));
				prj.setBYTES(rs.getLong("BYTES"));
				itemById.put(rs.getLong("PRJ_ID"), new TreeItem<>(prj));
				parents.put(rs.getLong("PRJ_ID"), rs.getLong("PRJ_PARENT"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Map.Entry<Long, TreeItem<PROJECT>> entry : itemById.entrySet()) {
			Long key = entry.getKey();
			Long parent = parents.get(key);
			if (parent.equals(key)) {
				// in case the root item points to itself, this is it
				root = entry.getValue();
			} else {
				TreeItem<PROJECT> parentItem = itemById.get(parent);
				if (parentItem == null) {
					// in case the root item has no parent in the result set, this is it
					root = entry.getValue();
				} else {
					// add to parent tree item
					parentItem.getChildren().add(entry.getValue());
				}
				parentItem.setExpanded(false);
			}
		}
		root.setExpanded(true);
		PROJECT.setRoot(root);
	}
	
	@FXML
	void FIND(ActionEvent event) {
		try {
			if (!ID_FIND.getText().equals("")) {
				findNode(root, Integer.valueOf(ID_FIND.getText()));
			}
			if (!NameFind.getText().equals("")) {
				findNodeName(root, NameFind.getText());
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private void findNode(TreeItem<PROJECT> treeNode, int id) {
	       if (treeNode.getChildren().isEmpty()) {
	           // Do nothing node is empty.
	       } else {
	           // Loop through each child node.
	           for (TreeItem<PROJECT> node : treeNode.getChildren()) {
	               if (node.getValue().getPRJ_ID() == id) {
	                   node.setExpanded(true);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								PROJECT.requestFocus();
								PROJECT.getSelectionModel().select(node);
								PROJECT.scrollTo(PROJECT.getSelectionModel().getSelectedIndex());
							}
						});
						
	               } else {
	                   node.setExpanded(true);
	               }
	               // If the current node has children then check them.
	               if (!treeNode.getChildren().isEmpty()) {
	                   findNode(node, id);
	               }
	           }
	       }
	   }
	private void findNodeName(TreeItem<PROJECT> treeNode, String id) {
	       if (treeNode.getChildren().isEmpty()) {
	           // Do nothing node is empty.
	       } else {
	           // Loop through each child node.
	           for (TreeItem<PROJECT> node : treeNode.getChildren()) {
	               if (node.getValue().getPRJ_NAME().toLowerCase().contains(id.toLowerCase())) {
	                   node.setExpanded(true);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								PROJECT.requestFocus();
								PROJECT.getSelectionModel().select(node);
								PROJECT.scrollTo(PROJECT.getSelectionModel().getSelectedIndex());
							}
						});
						
	               } else {
	                   node.setExpanded(true);
	               }
	               // If the current node has children then check them.
	               if (!treeNode.getChildren().isEmpty()) {
	            	   findNodeName(node, id);
	               }
	           }
	       }
	   }
	
	private Connection conn;

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
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

	int SelTbl;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());

			CellDateFormatDT(DT);

//			PROJECT.setCellFactory(tv -> {
//
//				TreeCell<PROJECT> cell = new TreeCell<PROJECT>() {
//
//					@Override
//					public void updateItem(PROJECT item, boolean empty) {
//						super.updateItem(item, empty);
//						if (empty) {
//							setText("");
//							setGraphic(null);
//						} else {
//							setText(item.getPRJ_NAME());
//							if (PROJECT.getSelectionModel().getSelectedItem() != null) {
//
//							}
//						}
//					}
//
//				};
//				return cell;
//			});

			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			FillTree();

			PROJECT.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<PROJECT> prj = PROJECT.getSelectionModel().getSelectedItem();
				if (prj != null) {
					SelTbl = PROJECT.getSelectionModel().getSelectedIndex();
					//PRJ_ID.setText(String.valueOf(prj.getValue().getPRJ_ID()));
					//IsFolder.setText(prj.getValue().getIS_FOLDER());

					InitHist(prj.getValue().getPRJ_ID());
					if (prj.getValue().getIS_FOLDER().equals("N")) {
						//FILE_LG.setText(String.format("%,d байт", prj.getValue().getBYTES()));
					} else {
						//FILE_LG.setText("");
					}
				}
			});

			VERISION.setCellValueFactory(cellData -> cellData.getValue().VERISIONProperty().asObject());
			DT.setCellValueFactory(cellData -> cellData.getValue().DTProperty());

			//---------------------
			PRJ_IDS.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof PROJECT) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRJ_ID());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			PRJ_NAME.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof PROJECT) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRJ_NAME());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			IS_FOLDER.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof PROJECT) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getIS_FOLDER());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			VERSION.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof PROJECT) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getVERSION());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			BYTES.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof PROJECT) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getBYTES());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void CellDateFormatDT(TableColumn<PRJ_FL_VER_HIST, LocalDateTime> tc) {
		tc.setCellFactory(column -> {
			TableCell<PRJ_FL_VER_HIST, LocalDateTime> cell = new TableCell<PRJ_FL_VER_HIST, LocalDateTime>() {
				private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(format.format(item));
					}
				}
			};
			return cell;
		});
	}

	void InitHist(Long ID) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			PreparedStatement prepStmt = conn
					.prepareStatement("select * from PRJ_FL_VER_HIST where PRJ_ID = ? order by DT desc");
			prepStmt.setLong(1, ID);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<PRJ_FL_VER_HIST> usr_list = FXCollections.observableArrayList();
			while (rs.next()) {
				PRJ_FL_VER_HIST list = new PRJ_FL_VER_HIST();
				list.setPRJ_ID(rs.getLong("PRJ_ID"));
				list.setVERISION(rs.getLong("VERISION"));
				list.setDT(
						(rs.getDate("DT") != null)
								? LocalDateTime.parse(
										new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("DT")), formatter)
								: null);
				usr_list.add(list);
			}
			prepStmt.close();
			rs.close();
			Hist.setItems(usr_list);

			TableFilter<PRJ_FL_VER_HIST> tableFilter = TableFilter.forTableView(Hist).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

}
