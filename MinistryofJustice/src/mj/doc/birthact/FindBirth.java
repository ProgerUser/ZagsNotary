package mj.doc.birthact;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;

public class FindBirth {

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_LD;

	@FXML
	private XTableView<SELECTBIRTH> BIRTH_ACT;

	@FXML
	private Button BIRTH_ACT_FILTER;

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_CH_FNAME;

	@FXML
	private Button BIRTH_ACT_PRINT;

	@FXML
	private XTableColumn<SELECTBIRTH, Long> BIRTH_ACT_ID;

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_CH_MNAME;

	@FXML
	private BorderPane ap;

	@FXML
	private XTableColumn<SELECTBIRTH, LocalDateTime> BIRTH_ACT_DATE;
	@FXML
	private XTableColumn<SELECTBIRTH, String> FFIO;
	@FXML
	private XTableColumn<SELECTBIRTH, String> MFIO;

	@FXML
	private XTableColumn<SELECTBIRTH, String> BIRTH_ACT_ZAGS;

	@FXML
	private Button BIRTH_ACT_ADD;

	@FXML
	private XTableColumn<SELECTBIRTH, String> ChFio;

	@FXML
	private Button BIRTH_ACT_DELETE;

	@FXML
	private Button BIRTH_ACT_EDIT;

	@FXML
	void BIRTH_ACT_FILTER(ActionEvent event) {
		try {
			/* LOG */
			Main.logger = Logger.getLogger(getClass());
			/*******/

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void BIRTH_ACT_PRINT(ActionEvent event) {

	}

	public void Add(Stage stage_) {
		try {
			Logger.getLogger(getClass());
			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/br_act/IUBirthAct.fxml"));

			AddBirthAct controller = new AddBirthAct();

			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить акт о рождении");
			stage.setResizable(false);
			stage.initOwner(stage_);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						if (from == null) {
							AfterAdd(controller.getId());
						}
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
	void BIRTH_ACT_ADD(ActionEvent event) {
		Add((Stage) BIRTH_ACT.getScene().getWindow());
	}

	@FXML
	void BIRTH_ACT_EDIT(ActionEvent event) {
		if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите документ!");
		} else {
			Edit(BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID(),
					(Stage) BIRTH_ACT.getScene().getWindow());
		}
	}

	@FXML
	void BIRTH_ACT_DELETE(ActionEvent event) {
	}

	@FXML
	void AddBirth(ActionEvent event) {
		Add((Stage) BIRTH_ACT.getScene().getWindow());
	}

	boolean isopen = false;

	public void Edit(Long docid, Stage stage_) {
		try {
			if (isopen == false) {
				/* LOG */
				PreparedStatement selforupd = conn
						.prepareStatement("select * from brn_birth_act where  BR_ACT_ID = ? /*for update nowait*/");
				selforupd.setLong(1, docid);
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// xml
						XmlsForCompare(docid);
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();

						loader.setLocation(getClass().getResource("/br_act/IUBirthAct.fxml"));

						EditBirthAct controller = new EditBirthAct();
						controller.setId(docid);
						controller.setConn(conn);

						loader.setController(controller);
						Parent root = loader.load();
						stage.setScene(new Scene(root));
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование:");
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									// обновить без сохранения
									controller.SAVECOMPARE();
									if (controller.getStatus()) {
										if (from == null) {
											AfterAdd(controller.getId());
										}
										conn.commit();
									} else if (CompareBeforeClose(docid) == 1) {
										/**
										 * Проверка выхода без сохранения
										 */
										Stage stage = stage_;
										Label alert = new Label("Закрыть форму без сохранения?");
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
										Scene ynScene = new Scene(yn, 300, 100);
										Stage newWindow_yn = new Stage();
										no.setOnAction(new EventHandler<ActionEvent>() {
											public void handle(ActionEvent event) {
												newWindow_yn.close();
												paramT.consume();
											}
										});
										yes.setOnAction(new EventHandler<ActionEvent>() {
											public void handle(ActionEvent event) {
												try {
													conn.rollback();
												} catch (SQLException e) {
													e.printStackTrace();
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
										newWindow_yn.showAndWait();
									}
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
						Msg.Message("Документ редактируется другим пользователем!");
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
	void EditBirth(ActionEvent event) {
		if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите документ!");
		} else {
			Edit(BIRTH_ACT.getSelectionModel().getSelectedItem().getBRN_AC_ID(),
					(Stage) BIRTH_ACT.getScene().getWindow());
		}
	}

	private void InitBirths() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			// SqlMap sql = new SqlMap().load("/BirthSql.xml");
			// String readRecordSQL = sql.getSql("SelectBirth");
			PreparedStatement prepStmt = conn
					.prepareStatement("select * from SelectBirth t " + ((getWhere() != null) ? getWhere() : " order by BRN_AC_ID desc"));
			// System.out.println("select * from SelectBirth t " + getWhere());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<SELECTBIRTH> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				SELECTBIRTH list = new SELECTBIRTH();
				list.setTM$_DOC_DATE((rs.getDate("TM$_DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$_DOC_DATE")), formatter)
						: null);
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setLIVE_DEAD(rs.getString("LIVE_DEAD"));
				list.setFFIO(rs.getString("FFIO"));
				list.setBRN_AC_ID(rs.getLong("BRN_AC_ID"));
				list.setCHILDREN_FIO(rs.getString("CHILDREN_FIO"));
				list.setMFIO(rs.getString("MFIO"));
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			BIRTH_ACT.setItems(cus_list);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {  
					TableFilter<SELECTBIRTH> tableFilter = TableFilter.forTableView(BIRTH_ACT).apply();
					tableFilter.setSearchStrategy((input, target) -> {
						try {
							return target.toLowerCase().contains(input.toLowerCase());
						} catch (Exception e) {
							return false;
						}
					});
				}
			});

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private void AfterAdd(Long id) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			// SqlMap sql = new SqlMap().load("/BirthSql.xml");
			// String readRecordSQL = sql.getSql("SelectBirth");
			PreparedStatement prepStmt = conn.prepareStatement("select * from SelectBirth t");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<SELECTBIRTH> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				SELECTBIRTH list = new SELECTBIRTH();
				list.setTM$_DOC_DATE((rs.getDate("TM$_DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$_DOC_DATE")), formatter)
						: null);
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setLIVE_DEAD(rs.getString("LIVE_DEAD"));
				list.setFFIO(rs.getString("FFIO"));
				list.setBRN_AC_ID(rs.getLong("BRN_AC_ID"));
				list.setCHILDREN_FIO(rs.getString("CHILDREN_FIO"));
				list.setMFIO(rs.getString("MFIO"));
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			BIRTH_ACT.setItems(cus_list);
			TableFilter<SELECTBIRTH> tableFilter = TableFilter.forTableView(BIRTH_ACT).apply();
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
	void SelectDocument(ActionEvent event) {
		try {
			if(BIRTH_ACT.getSelectionModel().getSelectedItem() != null) {
				SELECTBIRTH elem = BIRTH_ACT.getSelectionModel().getSelectedItem();
				setStatus(true);
				setId(elem.getBRN_AC_ID());
				onclose();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Close(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	
	@FXML
	void RefreshBirthList(ActionEvent event) {
		try {
			InitBirths();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Сессия BirtchList.fxml
	 */
	Connection conn = null;

	/**
	 * Открыть сессию BirtchList.fxml
	 */
	void dbConnect() {
		try {
			Properties props = new Properties();
			props.put("v$session.program",getClass().getName());

			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отключить сессию BirtchList.fxml
	 */
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
	void DeleteCont(ActionEvent event) {
		try {
			if (BIRTH_ACT.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите документ!");
			} else {

				Stage stage = (Stage) BIRTH_ACT.getScene().getWindow();
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
							SELECTBIRTH id = BIRTH_ACT.getSelectionModel().getSelectedItem();
							PreparedStatement prst = conn
									.prepareStatement(" " + "declare " + "pragma autonomous_transaction;" + "begin "
											+ "delete from brn_birth_act where br_act_id = ?;" + "commit;" + "end;");
							prst.setLong(1, id.getBRN_AC_ID());
							prst.executeUpdate();
							prst.close();
							InitBirths();
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
				// Specifies the modality for new window.
				newWindow_yn.initModality(Modality.WINDOW_MODAL);
				// Specifies the owner Window (parent) for new window
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
	private ProgressIndicator PB;
	@FXML
	private BorderPane ROOT;

	void BlockMain() {
		ROOT.setDisable(false);
		PB.setVisible(false);
	}

	private Executor exec;

	@FXML
	void Print(ActionEvent event) {
		try {
			ROOT.setDisable(true);
			PB.setVisible(true);
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {
					SELECTBIRTH auact = BIRTH_ACT.getSelectionModel().getSelectedItem();
					new PrintReport().showReport(auact.getBRN_AC_ID());
					return null;
				}
			};
			task.setOnFailed(e -> Msg.Message(task.getException().getMessage()));
			task.setOnSucceeded(e -> BlockMain());
			exec.execute(task);

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	Integer from = null;

	public void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		this.from = 1;
		this.conn.setAutoCommit(false);
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) BIRTH_ACT.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Быстрый способ заполнения даты
	 * 
	 * @param dp
	 */
	void DateAutoComma(DatePicker dp) {
		DateTimeFormatter fastFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		dp.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate object) {
				return (object != null) ? object.format(defaultFormatter) : null;
			}

			@Override
			public LocalDate fromString(String string) {
				try {
					return LocalDate.parse(string, fastFormatter);
				} catch (DateTimeParseException dtp) {

				}

				return LocalDate.parse(string, defaultFormatter);
			}
		});
	}

	/**
	 * Поле С
	 */
	@FXML
	private DatePicker DT1;

	/**
	 * Поле ПО
	 */
	@FXML
	private DatePicker DT2;

	/**
	 * Поле С фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT1(ActionEvent event) {
		try {
			InitBirths2();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void ClearFilterDates(ActionEvent event) {
		DT1.setValue(null);
		DT2.setValue(null);
	}

	private void InitBirths2() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			// SqlMap sql = new SqlMap().load("/BirthSql.xml");
			// String readRecordSQL = sql.getSql("SelectBirth");
			PreparedStatement prepStmt = conn.prepareStatement("select * from SelectBirth t where 1=1 "
					+ ((DT1.getValue() != null)
							? "  and to_date(TM$_DOC_DATE,'DD.MM.RRRR') >= to_date('"
									+ DT1.getValue().format(formatter2) + "','dd.mm.yyyy')"
							: "")
					+ ((DT2.getValue() != null)
							? "  and to_date(TM$_DOC_DATE,'DD.MM.RRRR') <= to_date('"
									+ DT2.getValue().format(formatter2) + "','DD.MM.RRRR')"
							: ""));
			// System.out.println("select * from SelectBirth t " + getWhere());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<SELECTBIRTH> cus_list = FXCollections.observableArrayList();
			while (rs.next()) {
				SELECTBIRTH list = new SELECTBIRTH();
				list.setTM$_DOC_DATE((rs.getDate("TM$_DOC_DATE") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$_DOC_DATE")), formatter)
						: null);
				list.setZAGS_NAME(rs.getString("ZAGS_NAME"));
				list.setLIVE_DEAD(rs.getString("LIVE_DEAD"));
				list.setFFIO(rs.getString("FFIO"));
				list.setBRN_AC_ID(rs.getLong("BRN_AC_ID"));
				list.setCHILDREN_FIO(rs.getString("CHILDREN_FIO"));
				list.setMFIO(rs.getString("MFIO"));
				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
			BIRTH_ACT.setItems(cus_list);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TableFilter<SELECTBIRTH> tableFilter = TableFilter.forTableView(BIRTH_ACT).apply();
					tableFilter.setSearchStrategy((input, target) -> {
						try {
							return target.toLowerCase().contains(input.toLowerCase());
						} catch (Exception e) {
							return false;
						}
					});
				}
			});

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Поле ПО фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT2(ActionEvent event) {
		try {
			InitBirths2();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			DbUtil.Run_Process(conn,getClass().getName());
			DateAutoComma(DT1);
			DateAutoComma(DT2);

			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});
			InitBirths();
			// двойной щелчок
			BIRTH_ACT.setRowFactory(tv -> {
				TableRow<SELECTBIRTH> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						SELECTBIRTH elem = BIRTH_ACT.getSelectionModel().getSelectedItem();
						setStatus(true);
						setId(elem.getBRN_AC_ID());
						onclose();
					}
				});
				return row;
			});
			
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			
			BIRTH_ACT_LD.setColumnFilter(new PatternColumnFilter<>());
			BIRTH_ACT_ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));
			BIRTH_ACT_ZAGS.setColumnFilter(new PatternColumnFilter<>());
			MFIO.setColumnFilter(new PatternColumnFilter<>());
			FFIO.setColumnFilter(new PatternColumnFilter<>());
			ChFio.setColumnFilter(new PatternColumnFilter<>());
			
			BIRTH_ACT_LD.setCellValueFactory(cellData -> cellData.getValue().LIVE_DEADProperty());
			BIRTH_ACT_ID.setCellValueFactory(cellData -> cellData.getValue().BRN_AC_IDProperty().asObject());
			BIRTH_ACT_DATE.setCellValueFactory(cellData -> cellData.getValue().TM$_DOC_DATEProperty());
			ChFio.setCellValueFactory(cellData -> cellData.getValue().CHILDREN_FIOProperty());
			BIRTH_ACT_ZAGS.setCellValueFactory(cellData -> cellData.getValue().ZAGS_NAMEProperty());
			MFIO.setCellValueFactory(cellData -> cellData.getValue().MFIOProperty());
			FFIO.setCellValueFactory(cellData -> cellData.getValue().FFIOProperty());
			/*
			 * BIRTH_ACT_ID.setCellFactory( TextFieldTableCell.<BIRTH_ACT,
			 * Long>forTableColumn(new IntegerStringConverter()));
			 * BIRTH_ACT_DATE.setCellFactory( TextFieldTableCell.<BIRTH_ACT,
			 * LocalDateTime>forTableColumn(new LocalDateTimeStringConverter()));
			 */
			BIRTH_ACT_ID.setOnEditCommit(new EventHandler<CellEditEvent<SELECTBIRTH, Long>>() {
				@Override
				public void handle(CellEditEvent<SELECTBIRTH, Long> t) {
					((SELECTBIRTH) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setBRN_AC_ID(t.getNewValue());
				}
			});

			BIRTH_ACT_DATE.setCellFactory(column -> {
				TableCell<SELECTBIRTH, LocalDateTime> cell = new TableCell<SELECTBIRTH, LocalDateTime>() {
					private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:MM:SS");

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
			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private StringProperty Where;

	public void setWhere(String value) {
		this.Where.set(value);
	}

	public String getWhere() {
		return this.Where.get();
	}

	/**
	 * Сравнение данных
	 * 
	 * @return
	 */
	Long CompareBeforeClose(Long docid) {
		Long ret = 0l;
		try {
			Clob lob = conn.createClob();
			lob.setString(1, RetXml);
			CallableStatement callStmt = conn.prepareCall("{ call BURN_UTIL.CompareXmls(?,?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.setClob(2, lob);
			callStmt.registerOutParameter(3, Types.VARCHAR);
			callStmt.registerOutParameter(4, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(3) == null) {
				ret = callStmt.getLong(4);
				System.out.println("ret=" + callStmt.getLong(4));
				callStmt.close();
			} else {
				Msg.Message(callStmt.getString(3));
				Main.logger.error(callStmt.getString(6) + "~" + Thread.currentThread().getName());
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

		return ret;
	}

	String RetXml;

	private StringProperty Fio;
	private BooleanProperty Status;
	private LongProperty Id;

	public String getFio() {
		return this.Fio.get();
	}

	public void setFio(String value) {
		this.Fio.set(value);
	}

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void setId(Long value) {
		this.Id.set(value);
	}

	public Long getId() {
		return this.Id.get();
	}

	public FindBirth() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.Fio = new SimpleStringProperty();
		this.Id = new SimpleLongProperty();
		this.Where = new SimpleStringProperty();
	}

	/**
	 * Возврат XML файлов для сравнения
	 */
	void XmlsForCompare(Long docid) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call BURN_UTIL.RetXmls(?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.registerOutParameter(2, Types.VARCHAR);
			callStmt.registerOutParameter(3, Types.CLOB);
			callStmt.execute();
			if (callStmt.getString(2) == null) {
				// clob
				Clob retcl = callStmt.getClob(3);
				// chars
				char char_xmls[] = new char[(int) retcl.length()];
				// read
				Reader r1 = retcl.getCharacterStream();
				r1.read(char_xmls);
				// strings
				RetXml = new String(char_xmls);
				// System.out.println(RetXml);
				callStmt.close();
			} else {
				Msg.Message(callStmt.getString(2));
				Main.logger.error(callStmt.getString(2) + "~" + Thread.currentThread().getName());
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

}
