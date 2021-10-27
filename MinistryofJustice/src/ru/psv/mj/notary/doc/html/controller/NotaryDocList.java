package ru.psv.mj.notary.doc.html.controller;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.notary.doc.html.model.NT_CUS_LIST_FOR_SEARCH;
import ru.psv.mj.notary.doc.html.model.NT_DOV_YEAR_LIST_FOR_SEARCH;
import ru.psv.mj.notary.doc.html.model.NT_PRM_SEARCH;
import ru.psv.mj.notary.doc.html.model.V_NT_DOC;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;
import ru.psv.mj.widgets.FxUtilTest;
import ru.psv.mj.www.pl.jsolve.Docx;
import ru.psv.mj.www.pl.jsolve.TextVariable;
import ru.psv.mj.www.pl.jsolve.VariablePattern;
import ru.psv.mj.www.pl.jsolve.Variables;

public class NotaryDocList {

	public NotaryDocList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private BorderPane ROOT;
	@FXML
	private VBox VB;
	@FXML
	private XTableView<V_NT_DOC> NT_DOC;
	@FXML
	private XTableColumn<V_NT_DOC, Long> ID;
	@FXML
	private XTableColumn<V_NT_DOC, String> DOC_NUMBER;
	@FXML
	private XTableColumn<V_NT_DOC, String> OPER;
	@FXML
	private XTableColumn<Object, LocalDate> CR_DATE;
	@FXML
	private XTableColumn<V_NT_DOC, String> CR_TIME;
	@FXML
	private XTableColumn<V_NT_DOC, String> NT_TYPE;
	@FXML
	private ProgressIndicator PB;

	static boolean AddWin = true;
	static boolean EditWin = true;

	@FXML
	private ComboBox<NT_PRM_SEARCH> ParamList;

	@FXML
	private Button Search;

	@FXML
	private ComboBox<Object> Vals;

	@FXML
	private GridPane Grid;

	/**
	 * Выполнить поиск после выбора
	 * 
	 * @param event
	 */
	@FXML
	void Search(ActionEvent event) {
		try {
			NT_PRM_SEARCH val_prm = ParamList.getSelectionModel().getSelectedItem();
			if (val_prm != null) {
				if (val_prm.getSRCH_ID().equals(1l)) {
					NT_CUS_LIST_FOR_SEARCH val_val = (NT_CUS_LIST_FOR_SEARCH) Vals.getSelectionModel()
							.getSelectedItem();
					if(val_val!=null) {
						Init(val_val.getID());
					}
				} else if (val_prm.getSRCH_ID().equals(2l)) {
					NT_DOV_YEAR_LIST_FOR_SEARCH val_val = (NT_DOV_YEAR_LIST_FOR_SEARCH) Vals.getSelectionModel()
							.getSelectedItem();
					if(val_val!=null) {
						Init(val_val.getID());
					}
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Vals(ActionEvent event) {
		try {
			NT_PRM_SEARCH val_prm = ParamList.getSelectionModel().getSelectedItem();
			if (val_prm != null) {
				if (val_prm.getSRCH_ID().equals(1l)) {

				} else if (val_prm.getSRCH_ID().equals(2l)) {

				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Очистить параметр поиска
	 * 
	 * @param event
	 */
	@FXML
	void DelPrm(ActionEvent event) {
		try {
			ParamList.getSelectionModel().select(null);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Очистить значение параметра поиска
	 * 
	 * @param event
	 */
	@FXML
	void DelPrmVal(ActionEvent event) {
		try {
			Vals.getSelectionModel().select(null);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * При выборе параметра для дальнейшего поиска по нему
	 * 
	 * @param event
	 */
	@FXML
	void ParamList(ActionEvent event) {
		try {
			NT_PRM_SEARCH val = ParamList.getSelectionModel().getSelectedItem();
			if (val != null) {
				// если список "Ссылка на клиента"
				if (val.getSRCH_ID().equals(1l)) {

//					Grid.getChildren().remove(Vals);
//					Vals = new ComboBox<NT_CUS_LIST_FOR_SEARCH>();
//					
//					Vals.setPrefWidth(Control.USE_PREF_SIZE);
//					Vals.setPrefHeight(Control.USE_PREF_SIZE);
//					Vals.maxWidth(Double.MAX_VALUE);
//					Vals.maxHeight(Control.USE_PREF_SIZE);
//					
//					Vals.setEditable(true);
//					Grid.add(Vals, 1, 1);

					// параметры для поиска
					{
						PreparedStatement stsmt = conn.prepareStatement("select * from NT_CUS_LIST_FOR_SEARCH");
						ResultSet rs = stsmt.executeQuery();
						ObservableList<Object> combolist = FXCollections.observableArrayList();
						while (rs.next()) {
							NT_CUS_LIST_FOR_SEARCH list = new NT_CUS_LIST_FOR_SEARCH();
							list.setCCUSFIRST_NAME(rs.getString("CCUSFIRST_NAME"));
							list.setDCUSBIRTHDAY((rs.getDate("DCUSBIRTHDAY") != null) ? LocalDate.parse(
									new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DCUSBIRTHDAY")), formatter)
									: null);
							list.setCCUSNAME_SH(rs.getString("CCUSNAME_SH"));
							list.setID(rs.getLong("ID"));
							list.setICUSNUM(rs.getLong("ICUSNUM"));
							list.setCUS_TYPE(rs.getString("CUS_TYPE"));
							list.setCCUSLAST_NAME(rs.getString("CCUSLAST_NAME"));
							list.setCCUSMIDDLE_NAME(rs.getString("CCUSMIDDLE_NAME"));
							list.setCCUSNAME(rs.getString("CCUSNAME"));
							combolist.add(list);
						}
						stsmt.close();
						rs.close();
						// фильтр
//						FilteredList<Object> filteredlogin = new FilteredList<Object>(
//								combolist);
//						Vals.getEditor().textProperty()
//								.addListener(new InputFilter<Object>(Vals, filteredlogin, false));
						Vals.setItems(combolist);

						FxUtilTest.getComboBoxValue(Vals);
						FxUtilTest.autoCompleteComboBoxPlus(Vals,
								(typedText, itemToCompare) -> ((NT_CUS_LIST_FOR_SEARCH) itemToCompare).getCCUSNAME()
										.toLowerCase().contains(typedText.toLowerCase()));
						convert_Vals(Vals);
					}
				} // если список "Срок выдачи доверенности"
				else if (val.getSRCH_ID().equals(2l)) {

					PreparedStatement stsmt = conn.prepareStatement("select * from NT_DOV_YEAR_LIST_FOR_SEARCH");
					ResultSet rs = stsmt.executeQuery();
					ObservableList<Object> combolist = FXCollections.observableArrayList();
					while (rs.next()) {
						NT_DOV_YEAR_LIST_FOR_SEARCH list = new NT_DOV_YEAR_LIST_FOR_SEARCH();
						list.setNUM(rs.getLong("NUM"));
						list.setRUNAME(rs.getString("RUNAME"));
						list.setDOC_ID(rs.getLong("DOC_ID"));
						list.setID(rs.getLong("ID"));
						combolist.add(list);
					}
					stsmt.close();
					rs.close();
					// фильтр
					Vals.setItems(combolist);
					FxUtilTest.getComboBoxValue(Vals);
					FxUtilTest.autoCompleteComboBoxPlus(Vals,
							(typedText, itemToCompare) -> ((NT_DOV_YEAR_LIST_FOR_SEARCH) itemToCompare).getRUNAME()
									.toLowerCase().contains(typedText.toLowerCase()));
					convert_Vals_Srok(Vals);
//					Grid.getChildren().remove(Vals);
//					ComboBox<String> cmbbx = new ComboBox<String>();
//					Grid.add(cmbbx, 1, 1);
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Add(ActionEvent event) {
		try {
			if (AddWin) {
				AddWin = false;
				Stage stage = new Stage();
				Stage stage_ = (Stage) NT_DOC.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/ru/psv/mj/notary/doc/html/view/IUHtmlDoc.fxml"));

				AddDoc controller = new AddDoc();
				loader.setController(controller);

				Parent root = loader.load();
				
				Scene scene = new Scene(root);
//				Style startingStyle = Style.LIGHT;
//				JMetro jMetro = new JMetro(startingStyle);
//				System.setProperty("prism.lcdtext", "false");
//				jMetro.setScene(scene);
				
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Добавить новую запись");
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						AddWin = true;
						if (controller.getStatus()) {
							Init();
							controller.dbDisconnect();
							NT_DOC.getSelectionModel().select(controller.NT_DOC);
							Edit();
						}
					}
				});
				stage.show();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void Edit() {
		try {
			if (EditWin) {
				V_NT_DOC val = NT_DOC.getSelectionModel().getSelectedItem();
				if (val != null) {
					EditWin = false;
					Stage stage = new Stage();
					Stage stage_ = (Stage) NT_DOC.getScene().getWindow();

					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ru/psv/mj/notary/doc/html/view/IUHtmlDoc.fxml"));

					EditDoc controller = new EditDoc();
					controller.setConn(conn, val);
					loader.setController(controller);

					Parent root = loader.load();
					
					Scene scene = new Scene(root);
					
//					Style startingStyle = Style.LIGHT;
//					JMetro jMetro = new JMetro(startingStyle);
//					System.setProperty("prism.lcdtext", "false");
//					jMetro.setScene(scene);
					
					stage.setScene(scene);
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Редактировать: " + val.getDOC_NUMBER());
					stage.initOwner(stage_);
					stage.setResizable(true);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							try {
								EditWin = true;
								if (controller.getStatus()) {
									Init();
									conn.commit();
								} else {
									conn.rollback();
								}
							} catch (Exception e) {
								DbUtil.Log_Error(e);
							}
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
	void Delete(ActionEvent event) {
		try {
			if (NT_DOC.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите строку!");
			} else {
//				if (DBUtil.OdbAction(106) == 0) {
//					Msg.Message("Нет доступа!");
//					return;
//				}

				Stage stage = (Stage) NT_DOC.getScene().getWindow();
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
											+ " delete from NT_DOC where ID = ?;" + "commit;" + "end;");
							V_NT_DOC cl = NT_DOC.getSelectionModel().getSelectedItem();
							delete.setLong(1, cl.getID());
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

	@FXML
	void Edit(ActionEvent event) {
		try {
			// HtmlEditor((Stage) NT_DOC.getScene().getWindow());
			Edit();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
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

	@SuppressWarnings("unused")
	@FXML
	void Print(ActionEvent event) {
		try {
			V_NT_DOC val = NT_DOC.getSelectionModel().getSelectedItem();
			if (val != null) {
				String path = null;
				String sql = null;
				{
					PreparedStatement prp = conn
							.prepareStatement("select DOCX_PATH,REP_QUERY from NT_TEMP_LIST t where t.id = ? ");
					prp.setLong(1, val.getNT_TYPE());
					ResultSet rs = prp.executeQuery();
					if (rs.next()) {
						path = rs.getString("DOCX_PATH");
						sql = rs.getString("REP_QUERY");
						if (rs.getClob("REP_QUERY") != null) {
							sql = getClobString(rs.getClob("REP_QUERY"));
						}
					}
					rs.close();
					prp.close();
				}
				// Вызов
				Docx docx = new Docx(System.getenv("MJ_PATH") + path);
				docx.setVariablePattern(new VariablePattern("#{", "}"));
				// preparing variables
				Variables variables = new Variables();
				PreparedStatement prepStmt = DbUtil.conn.prepareStatement(
						DbUtil.Sql_From_Prop("/ru/psv/mj/notary/doc/html/controller/Sql.properties", "PrintNtDocPrmVals"));
				prepStmt.setLong(1, val.getID());
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					variables.addTextVariable(new TextVariable("#{" + rs.getString("NAME").toLowerCase() + "}",
							(rs.getString("VALUE") == null || rs.getString("VALUE").length() < 2 ? "ПУСТО!"
									: rs.getString("VALUE"))));
				}
				rs.close();
				prepStmt.close();

				// fill template
				docx.fillTemplate(variables);
				File tempFile = File.createTempFile("Документ", ".docx",
						new File(System.getenv("MJ_PATH") + "OutReports"));
				FileOutputStream str = new FileOutputStream(tempFile);
				docx.save(str);
				str.close();
				tempFile.deleteOnExit();
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(tempFile);
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Refresh(ActionEvent event) {
		try {
			Init();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private void convert_Vals(ComboBox<Object> cmbbx) {
		cmbbx.setConverter(new StringConverter<Object>() {
			@Override
			public String toString(Object object) {
				return object != null
						? ((NT_CUS_LIST_FOR_SEARCH) object).getICUSNUM() + ". "
								+ ((NT_CUS_LIST_FOR_SEARCH) object).getCCUSNAME()
						: "";
			}

			@Override
			public Object fromString(String string) {
				return cmbbx.getItems().stream()
						.filter(object -> (((NT_CUS_LIST_FOR_SEARCH) object).getICUSNUM() + ". "
								+ ((NT_CUS_LIST_FOR_SEARCH) object).getCCUSNAME()).equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	private void convert_Vals_Srok(ComboBox<Object> cmbbx) {
		cmbbx.setConverter(new StringConverter<Object>() {
			@Override
			public String toString(Object object) {
				return object != null
						? ((NT_DOV_YEAR_LIST_FOR_SEARCH) object).getID() + ". "
								+ ((NT_DOV_YEAR_LIST_FOR_SEARCH) object).getRUNAME() + " Док. "
								+ ((NT_DOV_YEAR_LIST_FOR_SEARCH) object).getDOC_ID()
						: "";
			}

			@Override
			public Object fromString(String string) {
				return cmbbx.getItems().stream()
						.filter(object -> (((NT_DOV_YEAR_LIST_FOR_SEARCH) object).getID() + ". "
								+ ((NT_DOV_YEAR_LIST_FOR_SEARCH) object).getRUNAME() + " Док. "
								+ ((NT_DOV_YEAR_LIST_FOR_SEARCH) object).getDOC_ID()).equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	private void convert_ParamList(ComboBox<NT_PRM_SEARCH> cmbbx) {
		cmbbx.setConverter(new StringConverter<NT_PRM_SEARCH>() {
			@Override
			public String toString(NT_PRM_SEARCH object) {
				return object != null ? object.getSRCH_ID() + ". " + object.getSRCH_NAME() : "";
			}

			@Override
			public NT_PRM_SEARCH fromString(String string) {
				return cmbbx.getItems().stream()
						.filter(object -> (object.getSRCH_ID() + ". " + object.getSRCH_NAME()).equals(string))
						.findFirst().orElse(null);
			}

		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			ROOT.setBottom(createOptionPane(NT_DOC));
			ID.setCellValueFactory(cellData -> cellData.getValue().IDProperty().asObject());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			OPER.setCellValueFactory(cellData -> cellData.getValue().OPERProperty());
			CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
			NT_TYPE.setCellValueFactory(cellData -> cellData.getValue().TYPE_NODEProperty());
			CR_DATE.setCellValueFactory(cellData -> ((V_NT_DOC) cellData.getValue()).CR_DATEProperty());
			// Фильтр
			SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
			ObservableList rules = FXCollections.observableArrayList(ComparisonType.values());
			ID.setColumnFilter(new ComparableColumnFilter(new ComparableFilterModel(rules),
					TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));
			DOC_NUMBER.setColumnFilter(new PatternColumnFilter<>());
			CR_DATE.setColumnFilter(new DateColumnFilter<>());
			CR_TIME.setColumnFilter(new PatternColumnFilter<>());
			NT_TYPE.setColumnFilter(new PatternColumnFilter<>());
			OPER.setColumnFilter(new PatternColumnFilter<>());
			// Двойной щелчок по строке для открытия документа
			NT_DOC.setRowFactory(tv -> {
				TableRow<V_NT_DOC> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						Edit();
					}
				});
				return row;
			});
			new ConvConst().TableColumnDate(CR_DATE);
			Init();

			// параметры для поиска
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from NT_PRM_SEARCH where SRCH_ID not in (2)");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<NT_PRM_SEARCH> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_PRM_SEARCH list = new NT_PRM_SEARCH();
					list.setSRCH_ID(rs.getLong("SRCH_ID"));
					list.setSRCH_NAME(rs.getString("SRCH_NAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				ParamList.setItems(combolist);

				FxUtilTest.getComboBoxValue(ParamList);
				FxUtilTest.autoCompleteComboBoxPlus(ParamList, (typedText, itemToCompare) -> itemToCompare
						.getSRCH_NAME().toLowerCase().contains(typedText.toLowerCase()));
				convert_ParamList(ParamList);
			}

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
		Stage stage = (Stage) NT_DOC.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void Init() {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_DOC");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_DOC> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_DOC list = new V_NT_DOC();
				if (rs.getClob("HTML_DOCUMENT") != null) {
					list.setHTML_DOCUMENT(new ConvConst().ClobToString(rs.getClob("HTML_DOCUMENT")));
				}
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setID(rs.getLong("ID"));
				list.setOPER(rs.getString("OPER"));
				list.setNOTARY(rs.getLong("NOTARY"));
				list.setNT_TYPE(rs.getLong("NT_TYPE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setTYPE_NAME(rs.getString("TYPE_NAME"));
				list.setTYPE_NODE(rs.getString("TYPE_NODE"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			NT_DOC.setItems(dlist);

//			// enable filter support
//			FilteredList filtered = new FilteredList(dlist);
//			// data should be sortable too
//			SortedList sorted = new SortedList(filtered);
//			sorted.comparatorProperty().bind(NT_DOC.comparatorProperty());
//			NT_DOC.setEditable(true);

			TableFilter<V_NT_DOC> tableFilter = TableFilter.forTableView(NT_DOC).apply();
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

	void Init(Long val) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_DOC where id = ?");
			prepStmt.setLong(1, val);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_DOC> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_DOC list = new V_NT_DOC();
				if (rs.getClob("HTML_DOCUMENT") != null) {
					list.setHTML_DOCUMENT(new ConvConst().ClobToString(rs.getClob("HTML_DOCUMENT")));
				}
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setID(rs.getLong("ID"));
				list.setOPER(rs.getString("OPER"));
				list.setNOTARY(rs.getLong("NOTARY"));
				list.setNT_TYPE(rs.getLong("NT_TYPE"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setTYPE_NAME(rs.getString("TYPE_NAME"));
				list.setTYPE_NODE(rs.getString("TYPE_NODE"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			NT_DOC.setItems(dlist);
			TableFilter<V_NT_DOC> tableFilter = TableFilter.forTableView(NT_DOC).apply();
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

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");
		CheckBox filterVisible = new CheckBox("Показать фильтр");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());
		CheckBox menuButtonVisible = new CheckBox("Показать кнопку меню");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());
		CheckBox firstFilterable = new CheckBox("Фильтруемый первый столбец");
		firstFilterable.selectedProperty().bindBidirectional(ID.filterableProperty());
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
}
