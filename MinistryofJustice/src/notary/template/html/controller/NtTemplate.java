package notary.template.html.controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;
import mj.widgets.DbmsOutputCapture;
import notary.template.html.model.NT_TEMPLATE;
import notary.template.html.model.NT_TEMP_LIST;

public class NtTemplate {

	public NtTemplate() {
		Main.logger = Logger.getLogger(getClass());
	}

	NT_TEMPLATE nt_template;

//	@FXML
//	private TreeView<NT_TEMPLATE> NT_TEMPLATE;
	@FXML
	private TableView<NT_TEMP_LIST> NT_TEMP_LIST;
	@FXML
	private TableColumn<NT_TEMP_LIST, Long> ID;
	@FXML
	private TableColumn<NT_TEMP_LIST, String> NAME;
//	@FXML
//	private TextField TextToSearch;
	@FXML
	private TableColumn<NT_TEMP_LIST, Long> NOTARY;

	// TableView
	@FXML
	private TreeTableView<NT_TEMPLATE> NT_TEMPLATE;

	@FXML
	private TreeTableColumn<NT_TEMPLATE, Long> NT_ID;

	@FXML
	private TreeTableColumn<NT_TEMPLATE, String> NT_NAME;
	// ----------------------

	TreeItem<NT_TEMPLATE> root = null;

	int seltemp;

	int SelTbl;

	static boolean openHtmlEditor = true;

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

						NT_TEMPLATE.getSelectionModel().select(seltemp);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMPLATE.requestFocus();
								NT_TEMPLATE.getSelectionModel().select(seltemp);
								NT_TEMPLATE.scrollTo(seltemp);
							}
						});

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMP_LIST.requestFocus();
								NT_TEMP_LIST.getSelectionModel().select(SelTbl);
								NT_TEMP_LIST.scrollTo(SelTbl);
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
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						controller.dbDisconnect();
						fillTreeNtTemp();

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMPLATE.requestFocus();
								NT_TEMPLATE.getSelectionModel().select(seltemp);
								NT_TEMPLATE.scrollTo(seltemp);
							}
						});

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMP_LIST.requestFocus();
								NT_TEMP_LIST.getSelectionModel().select(SelTbl);
								NT_TEMP_LIST.scrollTo(SelTbl);
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

	/**
	 * Clob в строку
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String ClobToString(Clob clob) throws SQLException, IOException {
		BufferedReader stringReader = new BufferedReader(clob.getCharacterStream());
		String singleLine = null;
		StringBuffer strBuff = new StringBuffer();
		while ((singleLine = stringReader.readLine()) != null) {
			strBuff.append(singleLine + "\r\n");
		}
		return strBuff.toString();
	}

	void Init(Long id) {
		try {
			String selectStmt = "select * from nt_temp_list where PARENT = ? order by ID asc";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<NT_TEMP_LIST> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				NT_TEMP_LIST list = new NT_TEMP_LIST();
				list.setID(rs.getLong("ID"));
				list.setNAME(rs.getString("NAME"));
				list.setPARENT(rs.getLong("PARENT"));
				if (rs.getClob("REP_QUERY") != null) {
					list.setREP_QUERY(new ConvConst().ClobToString(rs.getClob("REP_QUERY")));
				}
				if (rs.getClob("HTML_TEMP") != null) {
					list.setHTML_TEMP(new ConvConst().ClobToString(rs.getClob("HTML_TEMP")));
				}
				list.setDOCX_PATH(rs.getString("DOCX_PATH"));
				list.setNOTARY(rs.getLong("NOTARY"));

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
			DbUtil.Log_Error(e);
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

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMPLATE.requestFocus();
								NT_TEMPLATE.getSelectionModel().select(seltemp);
								NT_TEMPLATE.scrollTo(seltemp);
							}
						});

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMP_LIST.requestFocus();
								NT_TEMP_LIST.getSelectionModel().select(SelTbl);
								NT_TEMP_LIST.scrollTo(SelTbl);
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
							delete.setLong(1, tmp.getValue().getNT_ID());
							delete.executeUpdate();
							delete.close();
							conn.commit();
							fillTreeNtTemp();
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
							delete.setLong(1, tmp.getID());
							delete.executeUpdate();
							delete.close();
							conn.commit();
							TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
							if (tmp != null) {
								Init(tmp.getValue().getNT_ID());
							}
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
	void EditTemp(ActionEvent event) {
		try {
			TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
			if (tmp != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/template/html/view/IUNtTemplate.fxml"));

				IUTemplate controller = new IUTemplate();
				controller.setID(tmp.getValue().getNT_ID(), tmp.getValue());
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

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMPLATE.requestFocus();
								NT_TEMPLATE.getSelectionModel().select(seltemp);
								NT_TEMPLATE.scrollTo(seltemp);
							}
						});

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMP_LIST.requestFocus();
								NT_TEMP_LIST.getSelectionModel().select(SelTbl);
								NT_TEMP_LIST.scrollTo(SelTbl);
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

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMPLATE.requestFocus();
								NT_TEMPLATE.getSelectionModel().select(seltemp);
								NT_TEMPLATE.scrollTo(seltemp);
							}
						});

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								NT_TEMP_LIST.requestFocus();
								NT_TEMP_LIST.getSelectionModel().select(SelTbl);
								NT_TEMP_LIST.scrollTo(SelTbl);
							}
						});
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void EditTempList(ActionEvent event) {
		try {
			EditTempList();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Html(ActionEvent event) {
		try {
			if (openHtmlEditor) {
				NT_TEMP_LIST tmp = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
				if (tmp != null) {
					openHtmlEditor = false;
					Stage stage = new Stage();
					Stage stage_ = (Stage) NT_TEMPLATE.getScene().getWindow();

					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/notary/template/html/view/HtmlEditor.fxml"));

					HtmlEditor controller = new HtmlEditor();
					controller.setConn(conn, tmp);
					loader.setController(controller);

					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Шаблон HTML: " + tmp.getNAME());
					stage.initOwner(stage_);
					stage.setResizable(true);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							openHtmlEditor = true;
							Init(tmp.getPARENT());

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									NT_TEMPLATE.requestFocus();
									NT_TEMPLATE.getSelectionModel().select(seltemp);
									NT_TEMPLATE.scrollTo(seltemp);
								}
							});

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									NT_TEMP_LIST.requestFocus();
									NT_TEMP_LIST.getSelectionModel().select(SelTbl);
									NT_TEMP_LIST.scrollTo(SelTbl);
								}
							});
						}
					});
					stage.show();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void TextToSearch(ActionEvent event) {
		try {

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void fillTreeNtTemp() {
		Map<Long, TreeItem<NT_TEMPLATE>> itemById = new HashMap<>();
		Map<Long, Long> parents = new HashMap<>();
		String query = "select * from NT_TEMPLATE";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nt_template = new NT_TEMPLATE();
				nt_template.setNT_NAME(rs.getString("NT_NAME"));
				nt_template.setNT_PARENT(rs.getLong("NT_PARENT"));
				nt_template.setNT_ID(rs.getLong("NT_ID"));
				nt_template.setNT_NPP(rs.getLong("NT_NPP"));
				itemById.put(rs.getLong("NT_ID"), new TreeItem<>(nt_template));
				parents.put(rs.getLong("NT_ID"), rs.getLong("NT_PARENT"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Map.Entry<Long, TreeItem<NT_TEMPLATE>> entry : itemById.entrySet()) {
			Long key = entry.getKey();
			Long parent = parents.get(key);
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
					parentItem.setExpanded(true);
				}
			}
		}
		root.setExpanded(true);
		NT_TEMPLATE.setRoot(root);
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
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void OpenWord(ActionEvent event) {
		try {
			NT_TEMP_LIST val = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
			if (val != null) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(new File(System.getenv("MJ_PATH") + val.getDOCX_PATH()));
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Клонировать шаблон
	 * 
	 * @param event
	 */
	@FXML
	void CloneTempList(ActionEvent event) {
		try {
			NT_TEMP_LIST val = NT_TEMP_LIST.getSelectionModel().getSelectedItem();
			if (val != null) {
				//
				final Alert alert = new Alert(AlertType.CONFIRMATION, "Клонировать?", ButtonType.YES, ButtonType.NO);
				if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
					// User selected the non-default yes button
					CallableStatement cls = conn.prepareCall("{call NT_PKG.CLONE_TEMP_LIST(?,?)}");
					cls.registerOutParameter(1, Types.VARCHAR);
					cls.setLong(2, val.getID());
					// DbmsOutput
					try (DbmsOutputCapture capture = new DbmsOutputCapture(conn)) {
						List<String> lines = capture.execute(cls);
						System.out.println(lines);
					} catch (Exception e) {
						DbUtil.Log_Error(e);
					}
					// --------------
					if (cls.getString(1) == null) {
						conn.commit();
						Init(val.getPARENT());
					} else {
						conn.rollback();
						Msg.Message(cls.getString(1));
					}
					cls.close();
				} else {
					return;
				}
			} else {
				Msg.Message("Выберите шаблон!");
			}
			NT_TEMPLATE.getSelectionModel().select(seltemp);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	boolean isExsists(Long ids) {
		boolean ret = true;
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select count(*) cnt\n"
					+ "  from NT_TEMPLATE j\n"
					+ " where exists (select null from NT_TEMP_LIST l where l.parent = j.nt_id)\n"
					+ "   and j.nt_id = ?\n"
					+ "");
			prepStmt.setLong(1, ids);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cnt") > 0) {
					ret = false;
				}
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());

			NT_ID.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMPLATE) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getNT_ID());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			NT_NAME.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMPLATE) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getNT_NAME());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});

//			NT_TEMPLATE.setCellFactory(tv -> {
//				TreeCell<NT_TEMPLATE> cell = new TreeCell<NT_TEMPLATE>() {
//					@Override
//					public void updateItem(NT_TEMPLATE item, boolean empty) {
//						super.updateItem(item, empty);
//						if (empty) {
//							setText("");
//							setGraphic(null);
//						} else {
//							setText(item.getNT_NAME());
//						}
//					}
//				};
//				return cell;
//			});

			NT_ID.setCellFactory(col -> {
				TreeTableCell<NT_TEMPLATE, Long> cell = new TreeTableCell<NT_TEMPLATE, Long>() {
					@Override
					public void updateItem(Long item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(item.toString());
							// ---
							//System.out.println(item);
							if (isExsists(item) == false) {
								setStyle("-fx-text-fill: #D24141;-fx-font-weight: bold;");
							} else {

							}
						}
					}
				};
				cell.setAlignment(Pos.CENTER);
				return cell;
			});

			NT_TEMPLATE.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				TreeItem<NT_TEMPLATE> tmp = NT_TEMPLATE.getSelectionModel().getSelectedItem();
				if (tmp != null) {
					// TextToSearch.setText(String.valueOf(tmp.getValue().getNT_ID()));
					Init(tmp.getValue().getNT_ID());
				}
			});

			NOTARY.setCellValueFactory(cellData -> cellData.getValue().NOTARYProperty().asObject());
			ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
			NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			fillTreeNtTemp();
			// Двойной щелчок по строке для открытия документа
			NT_TEMP_LIST.setRowFactory(tv -> {
				TableRow<NT_TEMP_LIST> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						// EditTempList();
						Html(null);
					}
				});
				return row;
			});

			NT_TEMPLATE.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getClickCount() == 2) {
						EditTemp(null);
					}
				}
			});

			NT_TEMPLATE.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					seltemp = NT_TEMPLATE.getSelectionModel().getSelectedIndex();
				}
			});

			NT_TEMP_LIST.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					SelTbl = NT_TEMP_LIST.getSelectionModel().getSelectedIndex();
				}
			});

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
