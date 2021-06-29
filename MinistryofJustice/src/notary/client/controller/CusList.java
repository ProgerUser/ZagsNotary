package notary.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
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
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.ComparableColumnFilter;
import com.jyloo.syntheticafx.DateColumnFilter;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.TitledBorderPane;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.ComparableFilterModel;
import com.jyloo.syntheticafx.filter.ComparisonType;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.msg.Msg;
import mj.util.ConvConst;
import mj.utils.DbUtil;
import notary.client.model.VCUS;

/**
 * Контроллер формы "Граждане", позволяет добавлять, редактировать граждан <br>
 * возможные доработки:<br>
 * вывод таблицы со всеми документами и переход к ним...<br>
 * Отредактирован 28.10.2020 <br>
 * 24.11.2020 изменен метод сопоставления выпад. списка для отслеживания
 * изменении при закрытии <br>
 * 04.12.2020 <br>
 * Синхронизация с удаленной базой 1С
 * 
 * @author Said
 *
 */
public class CusList {

	public CusList() {
		Main.logger = Logger.getLogger(getClass());
		dbConnect();
	}

	@FXML
	private BorderPane CUS_BORDER;

	@FXML
	private XTableColumn<VCUS, String> COUNTRY_NAME;

	@FXML
	private XTableColumn<VCUS, String> NAME;

	@FXML
	private XTableColumn<VCUS, String> CCUSIDOPEN;

	@FXML
	private XTableColumn<VCUS, String> CR_TIME;

	@FXML
	private XTableColumn<Object, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<VCUS, String> CUS_TYPE;
	@FXML
	private XTableColumn<VCUS, String> CCUSNAME;
	@FXML
	private XTableColumn<VCUS, String> CCUSNAME_SH;
	
	/*
	 * @FXML private XTableColumn<VCUS, LocalDateTime> DCUSOPEN;
	 */
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
	 * Таблица Граждан
	 */
	@FXML
	private XTableView<VCUS> CUSLIST;

	/**
	 * Серия документа
	 */
	@FXML
	private XTableColumn<VCUS, String> DOC_SER;

	/**
	 * Отчество
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSMIDDLE_NAMET;

	/**
	 * Фамилия
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSLAST_NAMET;

	/**
	 * Дата рождения
	 */
	@FXML
	private XTableColumn<Object, LocalDate> DCUSBIRTHDAYT;

	/**
	 * Район
	 */
	@FXML
	private XTableColumn<VCUS, String> AREA;

	/**
	 * Страна
	 */
	@FXML
	private XTableColumn<VCUS, String> COUNTRY;

	/**
	 * Тип документа
	 */
	@FXML
	private XTableColumn<VCUS, String> ID_DOC_TP;

	/**
	 * Пол
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSSEX;

	/**
	 * Дом
	 */
	@FXML
	private XTableColumn<VCUS, String> DOM;

	/**
	 * Квартира
	 */
	@FXML
	private XTableColumn<VCUS, String> KV;

	/**
	 * Номер документа
	 */
	@FXML
	private XTableColumn<VCUS, String> DOC_NUM;

	/**
	 * Город
	 */
	// @FXML
	// private XTableColumn<VCUS, String> CITY;

	/**
	 * Орган выдавший документ
	 */
	@FXML
	private XTableColumn<VCUS, String> DOC_SUBDIV;

	/**
	 * Название инфраструктуры-улицы
	 */
	@FXML
	private XTableColumn<VCUS, String> INFR_NAME;

	/**
	 * Дата окончания документа
	 */
	@FXML
	private XTableColumn<Object, LocalDate> DOC_PERIOD;

	/**
	 * Дата выдачи документа
	 */
	@FXML
	private XTableColumn<Object, LocalDate> DOC_DATE;

	/**
	 * Имя фильтр
	 */
	@FXML
	private TextField CCUSFIRST_NAME;

	/**
	 * Фамилия фильтр
	 */
	@FXML
	private TextField CCUSLAST_NAME;

	/**
	 * Отчество фильтр
	 */
	@FXML
	private TextField CCUSMIDDLE_NAME;

	/**
	 * Имя таблица
	 */
	@FXML
	private XTableColumn<VCUS, String> CCUSFIRST_NAMET;

	/**
	 * ИД документа
	 */
	@FXML
	private XTableColumn<VCUS, Long> ICUSNUM;

	@FXML
	private XTableColumn<VCUS, String> NAS_PUNCT;

	/**
	 * Добавить Гражданина = Контекстное меню
	 * 
	 * @param event
	 */
	@FXML
	void CmAdd(ActionEvent event) {
		Add();
	}

	/**
	 * Удалить Гражданина = Контекстное меню
	 * 
	 * @param event
	 */
	@FXML
	void CmDelete(ActionEvent event) {
		Delete();
	}

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");

		CheckBox filterVisible = new CheckBox("Показать фильтр");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());

		CheckBox menuButtonVisible = new CheckBox("Показать кнопку меню");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());

		CheckBox firstFilterable = new CheckBox("Фильтруемый первый столбец");
		// XTableColumn<VCUS, Long> firstColumn = (XTableColumn<VCUS, Long>)
		// table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(ICUSNUM.filterableProperty());

		CheckBox includeHidden = new CheckBox("Включить скрытые столбцы");
		includeHidden.selectedProperty().bindBidirectional(table.getFilterController().includeHiddenProperty());

		CheckBox andFilters = new CheckBox("Используйте операцию \"И\" для многоколоночного фильтра");
		andFilters.selectedProperty().bindBidirectional(table.getFilterController().andFiltersProperty());

		pane.getChildren().addAll(filterVisible, menuButtonVisible, firstFilterable, includeHidden, andFilters);

		TitledBorderPane p = new TitledBorderPane("Настройки", pane);
		p.getStyleClass().add("top-border-only");
		p.setStyle("-fx-border-insets: 10 0 0 0");
		return p;
	}

	/**
	 * Редактировать Гражданина = Контекстное меню
	 * 
	 * @param event
	 */
	@FXML
	void CmEdit(ActionEvent event) {
		if (CUSLIST.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите документ!");
		} else {
			Edit(CUSLIST.getSelectionModel().getSelectedItem().getICUSNUM(),
					(Stage) CUSLIST.getScene().getWindow()/*
															 * , conn
															 */);
		}
	}

	/**
	 * Удалить Гражданина <Ссылка>
	 * 
	 * @param event
	 */
	@FXML
	void BtDelete(ActionEvent event) {
		Delete();
	}

	/**
	 * Удалить гражданина - вызов из контекстного меню и из кнопок
	 */
	void Delete() {
		try {
			if (CUSLIST.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите троку!");
			} else {
				if (DbUtil.Odb_Action(29l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}

				PreparedStatement check = conn
						.prepareStatement(DbUtil.Sql_From_Prop("/notary/client/controller/SQL.properties", "DocCnt"));
				check.setLong(1, CUSLIST.getSelectionModel().getSelectedItem().getICUSNUM());
				ResultSet rs = check.executeQuery();
				String txt = "Удалить запись?";
				int sc_wdth = 200;
				if (rs.next()) {
					if (rs.getLong(1) > 0) {
						txt = "Найдены связанные документы, продолжить удаление?";
						sc_wdth = 350;
					}
				}
				check.close();
				rs.close();

				Stage stage = (Stage) CUSLIST.getScene().getWindow();
				Label alert = new Label(txt);
				alert.setLayoutX(10.0);
				alert.setLayoutY(10.0);
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
				Scene ynScene = new Scene(yn, sc_wdth, 100);
				Stage newWindow_yn = new Stage();
				no.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						newWindow_yn.close();
					}
				});
				yes.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							VCUS cl = CUSLIST.getSelectionModel().getSelectedItem();
							CallableStatement callStmt = conn.prepareCall("{call MJCUS.DelCus(?,?)}");
							callStmt.registerOutParameter(1, Types.VARCHAR);
							callStmt.setLong(2, cl.getICUSNUM());
							callStmt.execute();
							if (callStmt.getString(1) != null) {
								Msg.Message(callStmt.getString(1));
							}
							callStmt.close();
							/*
							 * PreparedStatement delete = conn .prepareStatement("declare " +
							 * "pragma autonomous_transaction;" + "begin " +
							 * " delete from CUS where ICUSNUM = ?;" + "commit;" + "end;"); VCUS cl =
							 * CUSLIST.getSelectionModel().getSelectedItem(); delete.setLong(1,
							 * cl.getICUSNUM()); delete.executeUpdate();
							 */
							InitVCus(null, null, null, null, null, "null", null);
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
			/*
			 * DialogFactory.showError(CUSLIST.getScene().getWindow(),
			 * ExceptionUtils.getStackTrace(e), null, "Ошибка");
			 */
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Добавить Гражданина - кнопка
	 * 
	 * @param event
	 */
	@FXML
	void BtAdd(ActionEvent event) {
		Add();
	}

	/**
	 * Обновление Таблицы Граждан
	 * 
	 * @param event
	 */
	@FXML
	void CmRefresh(ActionEvent event) {
		InitVCus(null, null, null, null, null, "null", null);
	}

	/**
	 * Индикатор открытия формы редактирования
	 */
	boolean isopen = false;

	/**
	 * CUS xml
	 */
	String CusXml;
	/**
	 * CusAddr xml
	 */
	String CusAddrXml;
	/**
	 * CusDocum xml
	 */
	String CusDocumXml;
	/**
	 * CusCitiz xml
	 */
	String CusCitizXml;

	/**
	 * Сравнение данных
	 * 
	 * @return
	 */
	Long CompareBeforeClose(Long docid, Connection conn) {
		Long ret = 0l;
		try {
			Clob cus = conn.createClob();
			cus.setString(1, CusXml);
			Clob cusaddr = conn.createClob();
			cusaddr.setString(1, CusAddrXml);
			Clob cusdocum = conn.createClob();
			cusdocum.setString(1, CusDocumXml);
			Clob cuscit = conn.createClob();
			cuscit.setString(1, CusCitizXml);

			CallableStatement callStmt = conn.prepareCall("{ call MJCUS.CompareXmls(?,?,?,?,?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.setClob(2, cus);
			callStmt.setClob(3, cusaddr);
			callStmt.setClob(4, cuscit);
			callStmt.setClob(5, cusdocum);
			callStmt.registerOutParameter(6, Types.VARCHAR);
			callStmt.registerOutParameter(7, Types.INTEGER);
			callStmt.execute();
			if (callStmt.getString(6) == null) {
				ret = callStmt.getLong(7);
				System.out.println("ret=" + callStmt.getLong(7));
			} else {
				Msg.Message(callStmt.getString(6));
				Main.logger.error(callStmt.getString(6) + "~" + Thread.currentThread().getName());
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
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

	/**
	 * Возврат XML файлов для сравнения
	 */
	void XmlsForCompare(Long docid, Connection conn) {
		try {
			CallableStatement callStmt = conn.prepareCall("{ call MJCUS.RetXmls(?,?,?,?,?,?)}");
			callStmt.setLong(1, docid);
			callStmt.registerOutParameter(2, Types.VARCHAR);
			callStmt.registerOutParameter(3, Types.CLOB);
			callStmt.registerOutParameter(4, Types.CLOB);
			callStmt.registerOutParameter(5, Types.CLOB);
			callStmt.registerOutParameter(6, Types.CLOB);
			callStmt.execute();
			if (callStmt.getString(2) == null) {
				// clob
				Clob cus = callStmt.getClob(3);
				Clob cus_addr = callStmt.getClob(4);
				Clob cus_citizen = callStmt.getClob(5);
				Clob cus_docum = callStmt.getClob(6);
				// chars
				char cus_xmls[] = new char[(int) cus.length()];
				char cus_addr_xmls[] = new char[(int) cus_addr.length()];
				char cus_cit_xmls[] = new char[(int) cus_citizen.length()];
				char cus_docum_xmls[] = new char[(int) cus_docum.length()];
				// read

				Reader r1 = cus.getCharacterStream();
				r1.read(cus_xmls);

				Reader r2 = cus_addr.getCharacterStream();
				r2.read(cus_addr_xmls);

				Reader r3 = cus_citizen.getCharacterStream();
				r3.read(cus_cit_xmls);

				Reader r4 = cus_docum.getCharacterStream();
				r4.read(cus_docum_xmls);

				// strings
				CusXml = new String(cus_xmls);
				CusAddrXml = new String(cus_addr_xmls);
				CusDocumXml = new String(cus_docum_xmls);
				CusCitizXml = new String(cus_cit_xmls);

//				System.out.println(CusXml);
//				System.out.println(CusAddrXml);
//				System.out.println(CusDocumXml);
//				System.out.println(CusCitizXml);
			} else {
				Msg.Message(callStmt.getString(2));
			}
			callStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	String formname = "norm";

	public void setConn(Connection conn, String formname) throws SQLException {
		this.conn = conn;this.conn.setAutoCommit(false);
		this.formname = formname;
		this.conn.setAutoCommit(false);
	}

	/**
	 * Вызов формы редактирования
	 * 
	 * @param event
	 */
	public void Edit(Long docid, Stage stage_/* , Connection conn */) {
		try {
			if (isopen == false) {
				
				if (DbUtil.Odb_Action(28l) == 0) {
					Msg.Message("Нет доступа!");
					return;
				}
				
				PreparedStatement selforupd = conn
						.prepareStatement("select * from cus where  ICUSNUM = ? for update nowait");
				VCUS cl = Init2(docid, conn);
				selforupd.setLong(1, cl.getICUSNUM());
				try {
					selforupd.executeQuery();
					selforupd.close();
					{
						// add lock row
						String lock = DbUtil.Lock_Row(docid, "CUS", conn);
						if (lock != null) {// if error add row
							Msg.Message(lock);
							conn.rollback();
							return;
						}
						// заполняем xml
						XmlsForCompare(docid, conn);
						// -------------------
						Stage stage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/notary/client/view/IUCus.fxml"));

						EditCus controller = new EditCus();
						controller.setId(cl.getICUSNUM());
						controller.setConn(conn);
						loader.setController(controller);

						Parent root = loader.load();

						Scene scene = new Scene(root);
//						Style startingStyle = Style.LIGHT;
//						JMetro jMetro = new JMetro(startingStyle);
//						System.setProperty("prism.lcdtext", "false");
//						jMetro.setScene(scene);

						stage.setScene(scene);
						stage.getIcons().add(new Image("/icon.png"));
						stage.setTitle("Редактирование: " + cl.getCCUSNAME());
						stage.initOwner(stage_);
						stage.setResizable(false);
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent paramT) {
								try {
									// Если нажали сохранить
									// обновление без сохранения
									controller.CallSaveToCompare();
									// заполняем xml
									//XmlsForCompare(docid, conn);
									if (controller.getStatus()) {
										if (formname.equals("norm")) {
											InitVCus(null, null, null, null, null, "edit", controller.getId());
										}
										conn.commit();
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(docid, "CUS", conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
										isopen = false;
									} // Если нажали "X" или "Cancel" и до этого что-то меняли
									else if (!controller.getStatus() & CompareBeforeClose(docid, conn) == 1) {
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
												isopen = false;
												// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
												String lock = DbUtil.Lock_Row_Delete(docid, "CUS", conn);
												if (lock != null) {// if error add row
													Msg.Message(lock);
												}
											}
										});
										newWindow_yn.setTitle("Внимание");
										newWindow_yn.setScene(ynScene);
										newWindow_yn.initModality(Modality.WINDOW_MODAL);
										newWindow_yn.initOwner(stage);
										newWindow_yn.setResizable(false);
										newWindow_yn.getIcons().add(new Image("/icon.png"));
										newWindow_yn.showAndWait();
									} // Если нажали "X" или "Cancel" и до этого ничего не меняли
									else if (!controller.getStatus() & CompareBeforeClose(docid, conn) == 0) {
										conn.rollback();
										isopen = false;
										// УДАЛИТЬ ЗАПИСЬ О "ЛОЧКЕ"=
										String lock = DbUtil.Lock_Row_Delete(docid, "CUS", conn);
										if (lock != null) {// if error add row
											Msg.Message(lock);
										}
									}
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
						Msg.Message("Запись редактируется " + DbUtil.Lock_Row_View(docid, "CUS"));
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

	/**
	 * Вызов формы добавления
	 * 
	 * @param event
	 */
	void Add() {
		try {
			if (DbUtil.Odb_Action(27l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}

			Stage stage = new Stage();
			Stage stage_ = (Stage) CUSLIST.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/client/view/IUCus.fxml"));

			AddCus controller = new AddCus();
			loader.setController(controller);

			// controller.setConn2(conn);
			// controller.setConn(conn);

			controller.setStage(CCUSFIRST_NAME);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Создание записи");
			stage.initOwner(stage_);
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						InitVCus(null, null, null, null, null, /* "crud" */"", controller.getId());
					}
					controller.dbDisconnect();
				}
			});
			stage.show();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Форма редактирования
	 * 
	 * @param event
	 */
	@FXML
	void BtEdit(ActionEvent event) {
		if (CUSLIST.getSelectionModel().getSelectedItem() == null) {
			Msg.Message("Выберите документ!");
		} else {
			Edit(CUSLIST.getSelectionModel().getSelectedItem().getICUSNUM(),
					(Stage) CUSLIST.getScene().getWindow()/*
															 * , conn
															 */);
		}
	}

	/**
	 * Таблица внутри
	 */
	@FXML
	private StackPane StackPane;

	/**
	 * Для выполнения в другом потоке, позволяет не <br>
	 * "замораживать" интерфейс при долгом выполнении запроса
	 * 
	 * @param lname
	 * @param fname
	 * @param mname
	 * @param dt1
	 * @param dt2
	 */
	void Progress(String lname, String fname, String mname, LocalDate dt1, LocalDate dt2) {
		try {
			// Если многопоточность
			StackPane.setDisable(true);
			PB.setVisible(true);
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {
					InitVCus(lname, fname, mname, dt1, dt2, "filter", null);
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

	/**
	 * Результат выполнения процесса
	 */
	void BlockMain() {
		StackPane.setDisable(false);
		PB.setVisible(false);
	}

	/**
	 * Индикатор прогресса
	 */
	@FXML
	private ProgressIndicator PB;

	/**
	 * Для фильтра
	 * 
	 * @param lname Фамилия
	 * @param fname Имя
	 * @param mname Отчество
	 * @param dt1   Дата с
	 * @param dt2   Дата по
	 * @param ID    Клиент
	 */
	private void InitVCus(String lname, String fname, String mname, LocalDate dt1, LocalDate dt2, String type,
			Long ID) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String wlname = "";
			String wfname = "";
			String wmname = "";
			String wdt1 = "";
			String wdt2 = "";
			String retid = "";
			String order = "";

			if (type.equals("filter")) {
				if (!lname.equals("")) {
					wlname = "and upper(VCUS.CCUSLAST_NAME) like '" + lname + "%'\r\n";
				}

				if (!fname.equals("")) {
					wfname = "and upper(VCUS.CCUSFIRST_NAME) like '" + fname + "%'\r\n";
				}

				if (!mname.equals("")) {
					wmname = "and upper(VCUS.CCUSMIDDLE_NAME) like '" + mname + "%'\r\n";
				}

				if (!mname.equals("")) {
					wmname = "and upper(VCUS.CCUSMIDDLE_NAME) like '" + mname + "%'\r\n";
				}

				if (dt1 != null) {
					wdt1 = "and VCUS.DCUSBIRTHDAY >= to_date('" + dt1.format(formatter) + "','dd.mm.yyyy') \r\n";
				}
				if (dt2 != null) {
					wdt2 = "and VCUS.DCUSBIRTHDAY <= to_date('" + dt2.format(formatter) + "','dd.mm.yyyy') \r\n";
				}
			} else if (type.equals("crud")) {
				retid = " and VCUS.ICUSNUM = " + ID;
			} else if (type.equals("edit")) {
				order = " order by case ICUSNUM when " + ID + " then 1 else 2 end\r\n";
			} else {
				order = " order by ICUSNUM desc\r\n";
			}

			String selectStmt = "select * from vcusnt VCUS where 1=1 \r\n" + wlname + wfname + wmname + wdt1 + wdt2 + retid
					+ order;
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VCUS> cus_list = FXCollections.observableArrayList();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			while (rs.next()) {
				VCUS list = new VCUS();

				list.setCOUNTRY(rs.getString("COUNTRY"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setID_DOC_TP(rs.getString("ID_DOC_TP"));
				list.setDOC_SER(rs.getString("DOC_SER"));
				list.setAREA(rs.getString("AREA"));
				list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
				list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				list.setPUNCT_NAME(rs.getString("PUNCT_NAME"));
				list.setTM$DCUSOPEN((rs.getDate("TM$DCUSOPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DCUSOPEN")), dateFormatter)
						: null);
				list.setINFR_NAME(rs.getString("INFR_NAME"));
				list.setDOM(rs.getString("DOM"));
				list.setKV(rs.getString("KV"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setICUSNUM(rs.getLong("ICUSNUM"));
				list.setCCUSIDOPEN(rs.getString("CCUSIDOPEN"));
				list.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
				list.setDOC_PERIOD((rs.getDate("DOC_PERIOD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_PERIOD")), formatter) : null);
				list.setCCUSSEX(rs.getString("CCUSSEX"));
				list.setNAME(rs.getString("NAME"));
				list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
				list.setCITY(rs.getString("CITY"));
				list.setDOC_SUBDIV(rs.getString("DOC_SUBDIV"));
				list.setDOC_NUM(rs.getString("DOC_NUM"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
				
				list.setCCUSNAME(rs.getString("CCUSNAME"));
				list.setCCUSNAME_SH(rs.getString("CCUSNAME_SH"));
				list.setCUS_TYPE(rs.getString("CUS_TYPE"));

				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();

			if (CUSLIST != null) {
				CUSLIST.setItems(cus_list);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						TableFilter<VCUS> tableFilter = TableFilter.forTableView(CUSLIST).apply();
						tableFilter.setSearchStrategy((input, target) -> {
							try {
								return target.toLowerCase().contains(input.toLowerCase());
							} catch (Exception e) {
								return false;
							}
						});
					}
				});
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Возврат для инициализации
	 * 
	 * @return {@link VCUS}
	 */
	VCUS Init2(Long cusid, Connection conn) {
		VCUS list = null;
		try {
			Main.logger = Logger.getLogger(getClass());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			String selectStmt = "select * from vcusnt where vcusnt.ICUSNUM = ?  \r\n";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1, cusid);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VCUS> cus_list = FXCollections.observableArrayList();
			DateTimeFormatter formatterwt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			while (rs.next()) {
				list = new VCUS();

				list.setCOUNTRY(rs.getString("COUNTRY"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")), formatter)
						: null);
				list.setID_DOC_TP(rs.getString("ID_DOC_TP"));
				list.setDOC_SER(rs.getString("DOC_SER"));
				list.setAREA(rs.getString("AREA"));
				list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
				list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter) : null);
				list.setPUNCT_NAME(rs.getString("PUNCT_NAME"));
				list.setTM$DCUSOPEN((rs.getDate("TM$DCUSOPEN") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DCUSOPEN")), formatterwt)
						: null);
				list.setINFR_NAME(rs.getString("INFR_NAME"));
				list.setDOM(rs.getString("DOM"));
				list.setKV(rs.getString("KV"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setICUSNUM(rs.getLong("ICUSNUM"));
				list.setCCUSIDOPEN(rs.getString("CCUSIDOPEN"));
				list.setCOUNTRY_NAME(rs.getString("COUNTRY_NAME"));
				list.setDOC_PERIOD((rs.getDate("DOC_PERIOD") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_PERIOD")), formatter) : null);
				list.setCCUSSEX(rs.getString("CCUSSEX"));
				list.setNAME(rs.getString("NAME"));
				list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
				list.setCITY(rs.getString("CITY"));
				list.setDOC_SUBDIV(rs.getString("DOC_SUBDIV"));
				list.setDOC_NUM(rs.getString("DOC_NUM"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
				
				list.setCCUSNAME(rs.getString("CCUSNAME"));
				list.setCCUSNAME_SH(rs.getString("CCUSNAME_SH"));
				list.setCUS_TYPE(rs.getString("CUS_TYPE"));

				cus_list.add(list);
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return list;
	}

	/**
	 * Сессия
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program",getClass().getName());
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отключить сессию
	 */
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

	/**
	 * Для многопоточности
	 */
	private Executor exec;

	/**
	 * Поле С фильтра
	 * 
	 * @param event
	 */
	@FXML
	void DT1(ActionEvent event) {
		try {
			Main.logger = Logger.getLogger(getClass());
			Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(), DT1.getValue(),
					DT2.getValue());
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
			Main.logger = Logger.getLogger(getClass());
			Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(), DT1.getValue(),
					DT2.getValue());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Очистить поля
	 * 
	 * @param event
	 */
	@FXML
	void Clear(ActionEvent event) {
		CCUSLAST_NAME.setText("");
		CCUSFIRST_NAME.setText("");
		CCUSMIDDLE_NAME.setText("");
		DT1.setValue(null);
		DT2.setValue(null);
	}

	/**
	 * Инициализация
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {

//			addIfNotPresent(CUS_BORDER.getStyleClass(), JMetroStyleClass.BACKGROUND);
//			addIfNotPresent(CUS_BORDER.getStyleClass(), JMetroStyleClass.LIGHT_BUTTONS);
//			addIfNotPresent(CUSLIST.getStyleClass(), JMetroStyleClass.TABLE_GRID_LINES);
//			addIfNotPresent(CUSLIST.getStyleClass(), JMetroStyleClass.ALTERNATING_ROW_COLORS);

			// ______Установка фильтра____________
			CUSLIST.filterRowVisibleProperty();

			CUS_BORDER.setBottom(createOptionPane(CUSLIST));

			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");

			CCUSIDOPEN.setColumnFilter(new PatternColumnFilter<>());

			CR_DATE.setColumnFilter(new DateColumnFilter<>());

			CR_TIME.setColumnFilter(new PatternColumnFilter<>());

			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			ICUSNUM.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));

			CUS_TYPE.setColumnFilter(new PatternColumnFilter<>());
			CCUSNAME.setColumnFilter(new PatternColumnFilter<>());
			CCUSNAME_SH.setColumnFilter(new PatternColumnFilter<>());
			
			CCUSLAST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSFIRST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSMIDDLE_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSLAST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSLAST_NAMET.setColumnFilter(new PatternColumnFilter<>());
			CCUSSEX.setColumnFilter(new PatternColumnFilter<>());
			NAME.setColumnFilter(new PatternColumnFilter<>());
			DCUSBIRTHDAYT.setColumnFilter(new DateColumnFilter<>());
			COUNTRY_NAME.setColumnFilter(new PatternColumnFilter<>());
			ID_DOC_TP.setColumnFilter(new PatternColumnFilter<>());
			DOC_SER.setColumnFilter(new PatternColumnFilter<>());
			DOC_NUM.setColumnFilter(new PatternColumnFilter<>());
			DOC_DATE.setColumnFilter(new DateColumnFilter<>());
			DOC_PERIOD.setColumnFilter(new DateColumnFilter<>());
			DOC_SUBDIV.setColumnFilter(new PatternColumnFilter<>());
			COUNTRY.setColumnFilter(new PatternColumnFilter<>());
			AREA.setColumnFilter(new PatternColumnFilter<>());
			// CITY.setColumnFilter(new PatternColumnFilter<>());
			NAS_PUNCT.setColumnFilter(new PatternColumnFilter<>());
			INFR_NAME.setColumnFilter(new PatternColumnFilter<>());
			DOM.setColumnFilter(new PatternColumnFilter<>());
			KV.setColumnFilter(new PatternColumnFilter<>());
			// _______________

			// DateAutoComma(DT1);
			// DateAutoComma(DT2);
			/**
			 * Для создания многопоточности
			 */
			exec = Executors.newCachedThreadPool((runnable) -> {
				Thread t = new Thread(runnable);
				t.setDaemon(true);
				return t;
			});

			/**
			 * Только буквы в фильтре
			 */
			// OnlyAlpha(CCUSLAST_NAME);
			// OnlyAlpha(CCUSFIRST_NAME);
			// OnlyAlpha(CCUSMIDDLE_NAME);
			new ConvConst().OnlyAlpha(CCUSLAST_NAME);
			new ConvConst().OnlyAlpha(CCUSFIRST_NAME);
			new ConvConst().OnlyAlpha(CCUSMIDDLE_NAME);

			/*
			 * DT1.getEditor().textProperty().addListener(new ChangeListener<String>() {
			 * 
			 * @Override public void changed(ObservableValue<? extends String> observable,
			 * String oldValue, String newValue) { if (newValue.length() == 2) {
			 * System.out.println(newValue); newValue = newValue+"."; } newValue =
			 * newValue+"."; } });
			 */

			// DT1.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			// System.out.println(DT1.getEditor().getText());
			/*
			 * if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB ||
			 * event.getCode() == KeyCode.DOWN) { DT2.requestFocus(); event.consume(); } if
			 * (event.getCode() == KeyCode.UP) { // do something event.consume(); }
			 */
			// });

			new ConvConst().FormatDatePiker(DT1);
			new ConvConst().FormatDatePiker(DT2);

			/**
			 * Двойной щелчок по строке
			 */
			CUSLIST.setRowFactory(tv -> {
				TableRow<VCUS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (CUSLIST.getSelectionModel().getSelectedItem() == null) {
							Msg.Message("Выберите документ!");
						} else {
							Edit(CUSLIST.getSelectionModel().getSelectedItem().getICUSNUM(),
									(Stage) CUSLIST.getScene().getWindow()/* , conn */);
						}
					}
				});
				return row;
			});

			/**
			 * Создать сессию
			 */
			dbConnect();
			DbUtil.Run_Process(conn,getClass().getName());
			/**
			 * Инициализация столбцов таблицы
			 */
			{
				new ConvConst().TableColumnDate(CR_DATE);

				CCUSIDOPEN.setCellValueFactory(cellData -> cellData.getValue().CCUSIDOPENProperty());

				CR_DATE.setCellValueFactory(cellData -> ((VCUS) cellData.getValue()).CR_DATEProperty());
				CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());

				CUS_TYPE.setCellValueFactory(cellData -> cellData.getValue().CUS_TYPEProperty());
				CCUSNAME.setCellValueFactory(cellData -> cellData.getValue().CCUSNAMEProperty());
				CCUSNAME_SH.setCellValueFactory(cellData -> cellData.getValue().CCUSNAME_SHProperty());
				
				ICUSNUM.setCellValueFactory(cellData -> cellData.getValue().ICUSNUMProperty().asObject());
				CCUSLAST_NAMET.setCellValueFactory(cellData -> cellData.getValue().CCUSLAST_NAMEProperty());
				CCUSFIRST_NAMET.setCellValueFactory(cellData -> cellData.getValue().CCUSFIRST_NAMEProperty());
				CCUSMIDDLE_NAMET.setCellValueFactory(cellData -> cellData.getValue().CCUSMIDDLE_NAMEProperty());
				DCUSBIRTHDAYT.setCellValueFactory(cellData -> ((VCUS) cellData.getValue()).DCUSBIRTHDAYProperty());
				ID_DOC_TP.setCellValueFactory(cellData -> cellData.getValue().ID_DOC_TPProperty());
				CCUSSEX.setCellValueFactory(cellData -> cellData.getValue().CCUSSEXProperty());
				DOC_SER.setCellValueFactory(cellData -> cellData.getValue().DOC_SERProperty());
				DOC_NUM.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMProperty());
				DOC_DATE.setCellValueFactory(cellData -> ((VCUS) cellData.getValue()).DOC_DATEProperty());
				DOC_PERIOD.setCellValueFactory(cellData -> ((VCUS) cellData.getValue()).DOC_PERIODProperty());
				DOC_SUBDIV.setCellValueFactory(cellData -> cellData.getValue().DOC_SUBDIVProperty());
				COUNTRY.setCellValueFactory(cellData -> cellData.getValue().COUNTRYProperty());
				AREA.setCellValueFactory(cellData -> cellData.getValue().AREAProperty());
				NAS_PUNCT.setCellValueFactory(cellData -> cellData.getValue().PUNCT_NAMEProperty());
				INFR_NAME.setCellValueFactory(cellData -> cellData.getValue().INFR_NAMEProperty());
				DOM.setCellValueFactory(cellData -> cellData.getValue().DOMProperty());
				KV.setCellValueFactory(cellData -> cellData.getValue().KVProperty());
				COUNTRY_NAME.setCellValueFactory(cellData -> cellData.getValue().COUNTRY_NAMEProperty());
				NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());
			}

			/**
			 * Фильтр поля в верхнем регистре
			 */
			{
				new ConvConst().UpperCase(CCUSLAST_NAME);
				new ConvConst().UpperCase(CCUSFIRST_NAME);
				new ConvConst().UpperCase(CCUSMIDDLE_NAME);
				// UpperCase(CCUSLAST_NAME);
				// UpperCase(CCUSFIRST_NAME);
				// UpperCase(CCUSMIDDLE_NAME);
			}
			/**
			 * Форматирование Столбца "Дата рождения"
			 */
			{
				new ConvConst().TableColumnDate(DCUSBIRTHDAYT);
				new ConvConst().TableColumnDate(DOC_DATE);
				new ConvConst().TableColumnDate(DOC_PERIOD);
				// TableColumnLocalDate(DCUSBIRTHDAYT);
				// TableColumnLocalDate(DOC_DATE);
				// TableColumnLocalDate(DOC_PERIOD);
			}

			ICUSNUM.setOnEditCommit(new EventHandler<CellEditEvent<VCUS, Long>>() {
				@Override
				public void handle(CellEditEvent<VCUS, Long> t) {
					((VCUS) t.getTableView().getItems().get(t.getTablePosition().getRow())).setICUSNUM(t.getNewValue());
				}
			});

			/**
			 * Listener при нажатии на строку клиентов
			 */
			CUSLIST.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {

				}
			});

			/**
			 * Поиск при вводе
			 */
			{
				CCUSLAST_NAME.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.length() > 0) {
							Progress(newValue, CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(), DT1.getValue(),
									DT2.getValue());
						} else {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(),
									DT1.getValue(), DT2.getValue());
						}
					}
				});

				CCUSFIRST_NAME.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.length() > 0) {
							Progress(CCUSLAST_NAME.getText(), newValue, CCUSMIDDLE_NAME.getText(), DT1.getValue(),
									DT2.getValue());
						} else {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(),
									DT1.getValue(), DT2.getValue());
						}
					}
				});
				CCUSMIDDLE_NAME.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.length() > 0) {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), newValue, DT1.getValue(),
									DT2.getValue());
						} else {
							Progress(CCUSLAST_NAME.getText(), CCUSFIRST_NAME.getText(), CCUSMIDDLE_NAME.getText(),
									DT1.getValue(), DT2.getValue());
						}
					}
				});

			}
			/**
			 * Заполнение данными таблицу
			 */
			InitVCus(null, null, null, null, null, "null", null);

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
