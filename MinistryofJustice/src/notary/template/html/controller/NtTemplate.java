package notary.template.html.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import notary.template.html.model.NT_TEMPLATE;
import notary.template.html.model.NT_TEMP_LIST;

public class NtTemplate {

	public NtTemplate() {
		Main.logger = Logger.getLogger(getClass());
	}

	NT_TEMPLATE nt_template;

	@FXML
	private TreeView<NT_TEMPLATE> NT_TEMPLATE;
	@FXML
	private TableView<NT_TEMP_LIST> NT_TEMP_LIST;
	@FXML
	private TableColumn<NT_TEMP_LIST, Integer> ID;
	@FXML
	private TableColumn<NT_TEMP_LIST, String> NAME;
	@FXML
	private TextField TextToSearch;

	TreeItem<NT_TEMPLATE> root = null;

	@FXML
	void AddTemp(ActionEvent event) {
		try {
			TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/IUNtTemplate.fxml"));

				IUTemplate controller = new IUTemplate();
				controller.setNT_PARENT(tmp.getValue().getNT_ID());
				controller.settype("I");
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
						controller.dbDisconnect();
						fillTreeNtTemp();
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Param(ActionEvent event) {
		try {
			NT_TEMP_LIST tmp = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/IUTempParam.fxml"));

				IUTempParam controller = new IUTempParam();
				controller.setID(tmp.getID());
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Параметры " + tmp.getNAME());
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						fillTreeNtTemp();
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public static String getClobString(Clob clob) throws SQLException, IOException {
		BufferedReader stringReader = new BufferedReader(clob.getCharacterStream());
		String singleLine = null;
		StringBuffer strBuff = new StringBuffer();
		while ((singleLine = stringReader.readLine()) != null) {
			strBuff.append(singleLine + "\r\n");
		}
		return strBuff.toString();
	}

	void Init(Integer id) {
		try {
			String selectStmt = "select * from nt_temp_list where PARENT = ? order by ID asc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<NT_TEMP_LIST> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				NT_TEMP_LIST list = new NT_TEMP_LIST();
				list.setID(rs.getInt("ID"));
				list.setNAME(rs.getString("NAME"));
				list.setPARENT(rs.getInt("PARENT"));
				if (rs.getClob("REP_QUERY") != null) {
					list.setREP_QUERY(getClobString(rs.getClob("REP_QUERY")));
				}
				if (rs.getClob("REP_QUERY") != null) {
					list.setHTML_TEMP(getClobString(rs.getClob("HTML_TEMP")));
				}
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			NT_TEMP_LIST.setItems(dlist);

			TableFilter<NT_TEMP_LIST> tableFilter = TableFilter.forTableView(NT_TEMP_LIST).apply();
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

	@FXML
	void AddTempList(ActionEvent event) {
		try {
			TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/IUTempList.fxml"));

				IUTemplateList controller = new IUTemplateList();
				controller.setVal(tmp.getValue());
				controller.settype("I");
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
						controller.dbDisconnect();
						fillTreeNtTemp();
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void DeleteTemp(ActionEvent event) {
		try {
			TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = (Stage) NT_TEMPLATE.getScene().getWindow();
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
							PreparedStatement delete = conn.prepareStatement("delete from NT_TEMPLATE where NT_ID = ?");
							delete.setInt(1, tmp.getValue().getNT_ID());
							delete.executeUpdate();
							delete.close();
							conn.commit();
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

	@FXML
	void DeleteTempList(ActionEvent event) {
		try {
			NT_TEMP_LIST tmp = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = (Stage) NT_TEMP_LIST.getScene().getWindow();
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
							PreparedStatement delete = conn.prepareStatement("delete from NT_TEMP_LIST where ID = ?");
							delete.setInt(1, tmp.getID());
							delete.executeUpdate();
							delete.close();
							conn.commit();
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

	@FXML
	void EditTemp(ActionEvent event) {
		try {
			TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/IUNtTemplate.fxml"));

				IUTemplate controller = new IUTemplate();
				controller.setID(tmp.getValue().getNT_ID());
				controller.setNAME(tmp.getValue().getNT_NAME());
				controller.settype("U");
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						fillTreeNtTemp();
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void EditTempList() {
		try {
			NT_TEMP_LIST tmp = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/IUTempList.fxml"));

				IUTemplateList controller = new IUTemplateList();
				controller.setVal(tmp);
				controller.settype("U");
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать");
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						fillTreeNtTemp();
						Init(tmp.getPARENT());
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void EditTempList(ActionEvent event) {
		try {
			EditTempList();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Html(ActionEvent event) {
		try {
			NT_TEMP_LIST tmp = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/HtmlEditor.fxml"));

				IUTemplateList controller = new IUTemplateList();
				controller.setVal(tmp);
				controller.settype("U");
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Шаблон HTML: " + tmp.getNAME());
				stage.initOwner(stage_);
				stage.setResizable(false);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void TextToSearch(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void fillTreeNtTemp() {
		Map<Integer, TreeItem<NT_TEMPLATE>> itemById = new HashMap<>();
		Map<Integer, Integer> parents = new HashMap<>();
		String query = "select * from NT_TEMPLATE";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nt_template = new NT_TEMPLATE();
				nt_template.setNT_NAME(rs.getString("NT_NAME"));
				nt_template.setNT_PARENT(rs.getInt("NT_PARENT"));
				nt_template.setNT_ID(rs.getInt("NT_ID"));
				nt_template.setNT_NPP(rs.getInt("NT_NPP"));
				itemById.put(rs.getInt("NT_ID"), new TreeItem<>(nt_template));
				parents.put(rs.getInt("NT_ID"), rs.getInt("NT_PARENT"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Map.Entry<Integer, TreeItem<NT_TEMPLATE>> entry : itemById.entrySet()) {
			Integer key = entry.getKey();
			Integer parent = parents.get(key);
			if (parent.equals(key)) {
				// in case the root item points to itself, this is it
				root = entry.getValue();
				root.setExpanded(true);
			} else {
				TreeItem<NT_TEMPLATE> parentItem = itemById.get(parent);
				if (parentItem == null) {
					// in case the root item has no parent in the result set, this is it
					root = entry.getValue();
				} else {
					// add to parent tree item
					parentItem.getChildren().add(entry.getValue());
				}
				// parentItem.setExpanded(true);
			}
		}
		root.setExpanded(true);
		NT_TEMPLATE.setRoot(root);
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "NtTemplate");
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

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
			NT_TEMPLATE.setCellFactory(tv -> {
				TreeCell<NT_TEMPLATE> cell = new TreeCell<NT_TEMPLATE>() {
					@Override
					public void updateItem(NT_TEMPLATE item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText("");
							setGraphic(null);
						} else {
							setText(item.getNT_NAME());
						}
					}
				};
				return cell;
			});

			NT_TEMPLATE.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
				if (tmp != null) {
					TextToSearch.setText(String.valueOf(tmp.getValue().getNT_ID()));
					Init(tmp.getValue().getNT_ID());
				}
			});

			ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
			NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			fillTreeNtTemp();
			// Двойной щелчок по строке для открытия документа
			NT_TEMP_LIST.setRowFactory(tv -> {
				TableRow<NT_TEMP_LIST> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						EditTempList();
					}
				});
				return row;
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
