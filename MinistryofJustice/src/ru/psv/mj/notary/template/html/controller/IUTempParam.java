package ru.psv.mj.notary.template.html.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.notary.template.html.model.NT_TEMP_LIST_PARAM;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class IUTempParam {

	public IUTempParam() {
		Main.logger = Logger.getLogger(getClass());
		this.ID = new SimpleLongProperty();
	}

	private LongProperty ID;

	public void setID(Long ID) {
		this.ID.set(ID);
	}

	public Long getID() {
		return ID.get();
	}

	@FXML
	private TreeTableView<NT_TEMP_LIST_PARAM> NT_TEMP_LIST_PARAM;
	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, Long> PRM_ID;
	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> PRM_NAME;
	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> PRM_TYPE;
	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> PRM_R_NAME;
	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> REQUIRED;

	int SelTbl;
	int FSelTbl;
	
	@FXML
	void Add(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) NT_TEMP_LIST_PARAM.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ru/psv/mj/notary/template/html/view/IUTempParamIU.fxml"));

			IUTempParamIU controller = new IUTempParamIU();
			controller.setID(getID());
			controller.settype("I");
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					controller.dbDisconnect();
					NT_TEMP_LIST_PARAM.setRoot(null);
					fillTree(-1);
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							NT_TEMP_LIST_PARAM.requestFocus();
							NT_TEMP_LIST_PARAM.getSelectionModel().select(SelTbl);
							//NT_TEMP_LIST_PARAM.getFocusModel().focus(FSelTbl);
							NT_TEMP_LIST_PARAM.scrollTo(SelTbl);
						}
					});
				}
			});
			stage.showAndWait();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		try {
			NT_TEMP_LIST_PARAM tmp = NT_TEMP_LIST_PARAM.getSelectionModel().getSelectedItem().getValue();
			if (tmp != null) {
				Stage stage = (Stage) NT_TEMP_LIST_PARAM.getScene().getWindow();
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
									.prepareStatement("delete from NT_TEMP_LIST_PARAM where PRM_ID = ?");
							delete.setLong(1, tmp.getPRM_ID());
							delete.executeUpdate();
							delete.close();
							conn.commit();
							fillTree(-1);
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

	@FXML
	void OK(ActionEvent event) {
		try {
			conn.commit();
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void Edit() {
		try {
			NT_TEMP_LIST_PARAM tmp = NT_TEMP_LIST_PARAM.getSelectionModel().getSelectedItem().getValue();
			int getind = NT_TEMP_LIST_PARAM.getSelectionModel().getSelectedIndex();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMP_LIST_PARAM.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/ru/psv/mj/notary/template/html/view/IUTempParamIU.fxml"));

				IUTempParamIU controller = new IUTempParamIU();
				controller.setcl(tmp);
				controller.settype("U");
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать: " + tmp.getPRM_R_NAME());
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						fillTree(getind);
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMP_LIST_PARAM.requestFocus();
								NT_TEMP_LIST_PARAM.getSelectionModel().select(SelTbl);
								//NT_TEMP_LIST_PARAM.getFocusModel().focus(FSelTbl);
								NT_TEMP_LIST_PARAM.scrollTo(SelTbl);
							}
						});
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		try {
			Edit();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Root Node
	 */
	// TreeItem<NT_TEMP_LIST_PARAM> root = null;

	NT_TEMP_LIST_PARAM prm;
	@SuppressWarnings("rawtypes")
	TreeItem root = new TreeItem<>("Root");

	@SuppressWarnings("unchecked")
	void fillTree(int val) {
		root = new TreeItem<>("Root");
		Map<Long, TreeItem<NT_TEMP_LIST_PARAM>> itemById = new HashMap<>();
		Map<Long, Long> parents = new HashMap<>();
		try {
			PreparedStatement prepStmt = conn
					.prepareStatement("select * from VNT_TEMP_LIST_PARAM where PRM_TMP_ID = ? order by PRM_NAME");
			prepStmt.setLong(1, getID());
			// System.out.println("`````" + getID());
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				// System.out.println("PRM_ID=" + rs.getLong("PRM_ID") + ";" + "PARENTS=" +
				// rs.getLong("PARENTS"));
				prm = new NT_TEMP_LIST_PARAM();
				prm.setPRM_ID(rs.getLong("PRM_ID"));
				prm.setPRM_NAME(rs.getString("PRM_NAME"));
				prm.setPRM_R_NAME(rs.getString("PRM_R_NAME"));
				prm.setPRM_TMP_ID(rs.getLong("PRM_TMP_ID"));
				prm.setPRM_SQL(rs.getString("PRM_SQL"));
				prm.setPRM_TYPE(rs.getLong("PRM_TYPE"));
				prm.setPRM_PADEJ(rs.getLong("PRM_PADEJ"));
				prm.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
				if (rs.getClob("PRM_FOR_PRM_SQL") != null) {
					prm.setPRM_FOR_PRM_SQL(new ConvConst().ClobToString(rs.getClob("PRM_FOR_PRM_SQL")));
				}
				prm.setTYPE_NAME(rs.getString("TYPE_NAME"));
				prm.setREQUIRED(rs.getString("REQUIRED"));
				prm.setPARENTS(rs.getLong("PARENTS"));
				prm.setHTML_CODE(rs.getString("HTML_CODE"));
				itemById.put(rs.getLong("PRM_ID"), new TreeItem<>(prm));
				parents.put(rs.getLong("PRM_ID"), rs.getLong("PARENTS"));
			}
			prepStmt.close();
			rs.close();

			for (Map.Entry<Long, TreeItem<NT_TEMP_LIST_PARAM>> entry : itemById.entrySet()) {
				Long key = entry.getKey();
				Long parent = parents.get(key);
				// System.out.println("parent=" + parent + ";" + "key=" + key);
				if (parent.equals(key)) {
					// System.out.println("(parent.equals(key))=" +
					// entry.getValue().getValue().getPRM_ID());
					// in case the root item points to itself, this is it
					root = entry.getValue();
				} else {
					TreeItem<NT_TEMP_LIST_PARAM> parentItem = itemById.get(parent);
					if (parentItem == null) {
						// System.out.println("(parentItem == null)=" +
						// entry.getValue().getValue().getPRM_ID());
						// in case the root item has no parent in the resultset, this is it
						root.getChildren().add(entry.getValue());
					} else {
						// System.out.println("else=" + entry.getValue().getValue().getPRM_ID());
						// add to parent treeitem
						parentItem.getChildren().add(entry.getValue());
						// root.getChildren().add(parentItem);
					}
				}
			}

			NT_TEMP_LIST_PARAM.setRoot(root);
			NT_TEMP_LIST_PARAM.setShowRoot(false);
			expandTreeView(root);

			if (val >= 0) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						NT_TEMP_LIST_PARAM.requestFocus();
						NT_TEMP_LIST_PARAM.getSelectionModel().select(val);
						NT_TEMP_LIST_PARAM.getFocusModel().focus(val);
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void expandTreeView(TreeItem<NT_TEMP_LIST_PARAM> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(true);
			for (TreeItem<NT_TEMP_LIST_PARAM> child : item.getChildren()) {
				expandTreeView(child);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
//			PRM_ID.setCellValueFactory(cellData -> cellData.getValue().getValue().PRM_IDProperty().asObject());
//			PRM_NAME.setCellValueFactory(cellData -> cellData.getValue().getValue().PRM_NAMEProperty());
//			PRM_TYPE.setCellValueFactory(cellData -> cellData.getValue().getValue().TYPE_NAMEProperty());
//			PRM_R_NAME.setCellValueFactory(cellData -> cellData.getValue().getValue().PRM_R_NAMEProperty());
//			REQUIRED.setCellValueFactory(cellData -> cellData.getValue().getValue().REQUIREDProperty());

			PRM_ID.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_ID());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			PRM_NAME.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_NAME());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			PRM_R_NAME.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_R_NAME());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			REQUIRED.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getREQUIRED());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			PRM_TYPE.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_TYPE());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});

//			Platform.runLater(() -> {
//				
//			});
			fillTree(-1);
			// Двойной щелчок по строке для открытия документа
			NT_TEMP_LIST_PARAM.setRowFactory(tv -> {
				TreeTableRow<NT_TEMP_LIST_PARAM> row = new TreeTableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit();
					}
				});
				return row;
			});
			
			NT_TEMP_LIST_PARAM.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					SelTbl = NT_TEMP_LIST_PARAM.getSelectionModel().getSelectedIndex();
					FSelTbl = NT_TEMP_LIST_PARAM.getSelectionModel().getFocusedIndex();
				}
			});

		} catch (Exception e) {
			DbUtil.Log_Error(e);
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

	void onclose() {
		Stage stage = (Stage) NT_TEMP_LIST_PARAM.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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
