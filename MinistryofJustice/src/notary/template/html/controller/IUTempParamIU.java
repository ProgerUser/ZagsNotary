package notary.template.html.controller;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;
import mj.widgets.FxUtilTest;
import notary.template.html.model.ALL_TABLE;
import notary.template.html.model.NT_PADEJ;
import notary.template.html.model.NT_PRM_TYPE;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

public class IUTempParamIU {

	private static final String[] KEYWORDS = new String[] { "abstract", "assert", "boolean", "break", "byte", "case",
			"catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends",
			"final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
			"volatile", "while" };

	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	private static final String PAREN_PATTERN = "\\(|\\)";
	private static final String BRACE_PATTERN = "\\{|\\}";
	private static final String BRACKET_PATTERN = "\\[|\\]";
	private static final String SEMICOLON_PATTERN = "\\;";
	private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private static final Pattern PATTERN = Pattern.compile(
			"(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN
					+ ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
					+ "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");

	private ExecutorService executor;

	@FXML
	private VBox vb_js;
	@FXML
	private VBox vb_sql_param;
	@FXML
	private VBox vb_sql_list;

	private IntegerProperty ID;
	private StringProperty type;

	@FXML
	private Button OK;
	NT_TEMP_LIST_PARAM cl;
	@FXML
	private ComboBox<NT_PRM_TYPE> PRM_TYPE;

	private CodeArea PRM_SQL;

	private CodeArea HTML_CODE;

	public IUTempParamIU() {
		Main.logger = Logger.getLogger(getClass());
		this.ID = new SimpleIntegerProperty();
		this.type = new SimpleStringProperty();
	}

	public void setcl(NT_TEMP_LIST_PARAM cl) {
		this.cl = cl;
	}

	public void settype(String type) {
		this.type.set(type);
	}

	public String gettype() {
		return type.get();
	}

	public void setID(Integer ID) {
		this.ID.set(ID);
	}

	public Integer getID() {
		return ID.get();
	}

	private Connection conn;
	@FXML
	private TextField PRM_NAME;
	@FXML
	private ComboBox<ALL_TABLE> PRM_TBL_REF;

	private CodeArea PRM_FOR_PRM_SQL;

	@FXML
	private ComboBox<NT_PADEJ> PRM_PADEJ;

	@FXML
	private CheckBox REQUIRED;

	@FXML
	private TextField PRM_R_NAME;

	@FXML
	private ComboBox<NT_TEMP_LIST_PARAM> PARENTS;

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	void PRM_TYPE(ActionEvent event) {
		try {
			NT_PRM_TYPE type = PRM_TYPE.getSelectionModel().getSelectedItem();
			if (type != null) {
				if (type.getTYPE_ID() == 1) {
					PRM_SQL.setEditable(true);
					PRM_FOR_PRM_SQL.setEditable(true);
					// PRM_PADEJ.setDisable(false);
				} else {
					// PRM_PADEJ.getSelectionModel().select(null);
					// PRM_PADEJ.setDisable(true);
					PRM_FOR_PRM_SQL.setEditable(false);

					PRM_FOR_PRM_SQL.replaceText(0, 0, "");

					PRM_SQL.setEditable(false);

					PRM_SQL.replaceText(0, 0, "");

				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private ALL_TABLE alltbl;

	@FXML
	void PRM_TBL_REF(ActionEvent event) {
		try {
			if (PRM_TBL_REF.getSelectionModel().getSelectedItem() != null) {
				alltbl = PRM_TBL_REF.getSelectionModel().getSelectedItem();
				PRM_TBL_REF.getSelectionModel().select(alltbl);
				System.out.println(alltbl.getTABLE_NAME());
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!PRM_NAME.getText().equals("")) {
				if (gettype().equals("I")) {
					PreparedStatement prp = conn.prepareStatement(
							"insert into NT_TEMP_LIST_PARAM " + "(PRM_NAME," + "PRM_TMP_ID," + "PRM_SQL," + "PRM_TYPE,"
									+ "PRM_TBL_REF," + "PRM_FOR_PRM_SQL," + "PRM_PADEJ," + "REQUIRED,"
									+ "PRM_R_NAME,HTML_CODE,PARENTS)" + " values" + " (?,?,?,?,?,?,?,?,?,?,?)");
					prp.setString(1, PRM_NAME.getText());
					prp.setInt(2, getID());
					prp.setString(3, PRM_SQL.getText());
					prp.setInt(4, PRM_TYPE.getSelectionModel().getSelectedItem().getTYPE_ID());
					if (alltbl != null) {
						prp.setString(5, alltbl.getTABLE_NAME());
					} else {
						prp.setString(5, null);
					}
					Clob clob = conn.createClob();
					clob.setString(1, PRM_FOR_PRM_SQL.getText());
					prp.setClob(6, clob);
					if (PRM_PADEJ.getSelectionModel().getSelectedItem() != null) {
						prp.setInt(7, PRM_PADEJ.getSelectionModel().getSelectedItem().getPDJ_ID());
					} else {
						prp.setNull(7, Types.INTEGER);
					}
					prp.setString(8, (REQUIRED.isSelected() ? "Y" : "N"));
					prp.setString(9, PRM_R_NAME.getText());
					prp.setString(10, HTML_CODE.getText());

					if (PARENTS.getSelectionModel().getSelectedItem() != null) {
						prp.setInt(11, PARENTS.getSelectionModel().getSelectedItem().getPRM_ID());
					} else {
						prp.setNull(11, Types.INTEGER);
					}
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn.prepareStatement("update NT_TEMP_LIST_PARAM " + "set PRM_NAME = ?,"
							+ "PRM_SQL=?," + "PRM_TYPE=?," + "PRM_TBL_REF=?," + "PRM_FOR_PRM_SQL=?," + "PRM_PADEJ=?, "
							+ "REQUIRED=?, " + "PRM_R_NAME=?, " + "HTML_CODE=?,PARENTS=? where PRM_ID = ?");
					prp.setString(1, PRM_NAME.getText());
					prp.setString(2, PRM_SQL.getText());
					prp.setInt(3, PRM_TYPE.getSelectionModel().getSelectedItem().getTYPE_ID());
					if (alltbl != null) {
						prp.setString(4, alltbl.getTABLE_NAME());
					} else {
						prp.setString(4, null);
					}
					Clob clob = conn.createClob();
					clob.setString(1, PRM_FOR_PRM_SQL.getText());
					prp.setClob(5, clob);
					if (PRM_PADEJ.getSelectionModel().getSelectedItem() != null) {
						prp.setInt(6, PRM_PADEJ.getSelectionModel().getSelectedItem().getPDJ_ID());
					} else {
						prp.setNull(6, Types.INTEGER);
					}
					prp.setString(7, (REQUIRED.isSelected() ? "Y" : "N"));
					prp.setString(8, PRM_R_NAME.getText());
					prp.setString(9, HTML_CODE.getText());
					if (PARENTS.getSelectionModel().getSelectedItem() != null) {
						prp.setInt(10, PARENTS.getSelectionModel().getSelectedItem().getPRM_ID());
					} else {
						prp.setNull(10, Types.INTEGER);
					}
					prp.setInt(11, cl.getPRM_ID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "IUTempParamIU");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) PRM_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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

	@SuppressWarnings("unused")
	@FXML
	private void initialize() {
		try {

			HTML_CODE = new CodeArea();
			PRM_FOR_PRM_SQL = new CodeArea();
			PRM_SQL = new CodeArea();

			vb_js.getChildren().add(new StackPane(new VirtualizedScrollPane<>(HTML_CODE)));
			vb_sql_param.getChildren().add(new StackPane(new VirtualizedScrollPane<>(PRM_FOR_PRM_SQL)));
			vb_sql_list.getChildren().add(new StackPane(new VirtualizedScrollPane<>(PRM_SQL)));

			PRM_SQL.setParagraphGraphicFactory(LineNumberFactory.get(PRM_SQL));
			PRM_FOR_PRM_SQL.setParagraphGraphicFactory(LineNumberFactory.get(PRM_FOR_PRM_SQL));
			HTML_CODE.setParagraphGraphicFactory(LineNumberFactory.get(HTML_CODE));

			vb_js.getStyleClass().add("mylistview");
			vb_js.getStylesheets().add("/ScrPane.css");

			vb_sql_param.getStyleClass().add("mylistview");
			vb_sql_param.getStylesheets().add("/ScrPane.css");

			vb_sql_list.getStyleClass().add("mylistview");
			vb_sql_list.getStylesheets().add("/ScrPane.css");

			{
				// Region spacer = new Region();
				HTML_CODE.setPrefHeight(1000);
				PRM_FOR_PRM_SQL.setPrefHeight(1000);
				PRM_SQL.setPrefHeight(1000);

				HTML_CODE.setMaxHeight(Double.MAX_VALUE);
				HTML_CODE.setMaxWidth(Double.MAX_VALUE);

				executor = Executors.newSingleThreadExecutor();
				Subscription cleanupWhenDone = HTML_CODE.multiPlainChanges().successionEnds(Duration.ofMillis(500))
						.supplyTask(this::computeHighlightingAsync).awaitLatest(HTML_CODE.multiPlainChanges())
						.filterMap(t -> {
							if (t.isSuccess()) {
								return Optional.of(t.get());
							} else {
								t.getFailure().printStackTrace();
								return Optional.empty();
							}
						}).subscribe(this::applyHighlighting);
				Subscription cleanupWhenDone1 = PRM_FOR_PRM_SQL.multiPlainChanges()
						.successionEnds(Duration.ofMillis(500)).supplyTask(this::computeHighlightingAsync1)
						.awaitLatest(PRM_FOR_PRM_SQL.multiPlainChanges()).filterMap(t -> {
							if (t.isSuccess()) {
								return Optional.of(t.get());
							} else {
								t.getFailure().printStackTrace();
								return Optional.empty();
							}
						}).subscribe(this::applyHighlighting1);
				Subscription cleanupWhenDone2 = PRM_SQL.multiPlainChanges().successionEnds(Duration.ofMillis(500))
						.supplyTask(this::computeHighlightingAsync2).awaitLatest(HTML_CODE.multiPlainChanges())
						.filterMap(t -> {
							if (t.isSuccess()) {
								return Optional.of(t.get());
							} else {
								t.getFailure().printStackTrace();
								return Optional.empty();
							}
						}).subscribe(this::applyHighlighting2);
				HTML_CODE.getStylesheets().add(
						getClass().getResource("/notary/template/html/controller/java-keywords.css").toExternalForm());
				PRM_FOR_PRM_SQL.getStylesheets().add(
						getClass().getResource("/notary/template/html/controller/java-keywords.css").toExternalForm());
				PRM_SQL.getStylesheets().add(
						getClass().getResource("/notary/template/html/controller/java-keywords.css").toExternalForm());
			}
			dbConnect();
			DBUtil.RunProcess(conn);
			// Таблицы
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from ALL_TABLE order by TABLE_NAME asc");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<ALL_TABLE> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					ALL_TABLE list = new ALL_TABLE();
					list.setTABLE_NAME(rs.getString("TABLE_NAME"));
					list.setTABLECOMMENT(rs.getString("TABLECOMMENT"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();

//				FilteredList<ALL_TABLE> filterednationals = new FilteredList<ALL_TABLE>(combolist);
//				PRM_TBL_REF.getEditor().textProperty()
//						.addListener(new InputFilter<ALL_TABLE>(PRM_TBL_REF, filterednationals, false));

				PRM_TBL_REF.setItems(combolist);

				FxUtilTest.getComboBoxValue(PRM_TBL_REF);
				FxUtilTest.autoCompleteComboBoxPlus(PRM_TBL_REF, (typedText, itemToCompare) -> itemToCompare
						.getTABLE_NAME().toLowerCase().contains(typedText.toLowerCase()));

				convert_TablrList(PRM_TBL_REF);
			}
			// Параметры
			{
				PreparedStatement stsmt = conn.prepareStatement(
						DBUtil.SqlFromProp("/notary/doc/html/controller/Sql.properties", "PrmForAddParents"));
				stsmt.setInt(1, getID());
				ResultSet rs = stsmt.executeQuery();
				ObservableList<NT_TEMP_LIST_PARAM> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_TEMP_LIST_PARAM list = new NT_TEMP_LIST_PARAM();
					list.setPRM_ID(rs.getInt("PRM_ID"));
					list.setPRM_NAME(rs.getString("PRM_NAME"));
					list.setPRM_TMP_ID(rs.getInt("PRM_TMP_ID"));
					list.setPRM_SQL(rs.getString("PRM_SQL"));
					list.setPRM_TYPE(rs.getInt("PRM_TYPE"));
					list.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
					if (rs.getClob("PRM_FOR_PRM_SQL") != null) {
						list.setPRM_FOR_PRM_SQL(new ConvConst().ClobToString(rs.getClob("PRM_FOR_PRM_SQL")));
					}
					list.setPRM_PADEJ(rs.getInt("PRM_PADEJ"));
					list.setREQUIRED(rs.getString("REQUIRED"));
					list.setPRM_R_NAME(rs.getString("PRM_R_NAME"));
					list.setHTML_CODE(rs.getString("HTML_CODE"));
					list.setPARENTS(rs.getInt("PARENTS"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();

//				FilteredList<NT_TEMP_LIST_PARAM> filterednationals = new FilteredList<NT_TEMP_LIST_PARAM>(combolist);
//				PARENTS.getEditor().textProperty()
//						.addListener(new InputFilter<NT_TEMP_LIST_PARAM>(PARENTS, filterednationals, false));
				
				PARENTS.setItems(combolist);
				
				FxUtilTest.getComboBoxValue(PRM_TBL_REF);
				FxUtilTest.autoCompleteComboBoxPlus(PRM_TBL_REF, (typedText, itemToCompare) -> itemToCompare
						.getTABLE_NAME().toLowerCase().contains(typedText.toLowerCase()));
				
				convert_PARENTS(PARENTS);
			}
			// Тип параметра
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from NT_PRM_TYPE order by TYPE_ID asc");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<NT_PRM_TYPE> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_PRM_TYPE list = new NT_PRM_TYPE();
					list.setTYPE_ID(rs.getInt("TYPE_ID"));
					list.setTYPE_NAME(rs.getString("TYPE_NAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				PRM_TYPE.setItems(combolist);
				convert_PRM_TYPE(PRM_TYPE);
				rs.close();
			}
			// Падежи
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from NT_PADEJ order by PDJ_ID asc");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<NT_PADEJ> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_PADEJ list = new NT_PADEJ();
					list.setPDJ_ID(rs.getInt("PDJ_ID"));
					list.setPDJ_NAME(rs.getString("PDJ_NAME"));
					list.setPDJ_R_NAME(rs.getString("PDJ_R_NAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				PRM_PADEJ.setItems(combolist);
				convert_PRM_PADEJ(PRM_PADEJ);
			}

			// Если редактирование
			if (gettype().equals("U")) {
				// Параметры
				{
					PreparedStatement stsmt = conn.prepareStatement(
							DBUtil.SqlFromProp("/notary/doc/html/controller/Sql.properties", "PrmForAddParents"));
					stsmt.setInt(1, cl.getPRM_ID());
					ResultSet rs = stsmt.executeQuery();
					ObservableList<NT_TEMP_LIST_PARAM> combolist = FXCollections.observableArrayList();
					while (rs.next()) {
						NT_TEMP_LIST_PARAM list = new NT_TEMP_LIST_PARAM();
						list.setPRM_ID(rs.getInt("PRM_ID"));
						list.setPRM_NAME(rs.getString("PRM_NAME"));
						list.setPRM_TMP_ID(rs.getInt("PRM_TMP_ID"));
						list.setPRM_SQL(rs.getString("PRM_SQL"));
						list.setPRM_TYPE(rs.getInt("PRM_TYPE"));
						list.setPRM_TBL_REF(rs.getString("PRM_TBL_REF"));
						if (rs.getClob("PRM_FOR_PRM_SQL") != null) {
							list.setPRM_FOR_PRM_SQL(new ConvConst().ClobToString(rs.getClob("PRM_FOR_PRM_SQL")));
						}
						list.setPRM_PADEJ(rs.getInt("PRM_PADEJ"));
						list.setREQUIRED(rs.getString("REQUIRED"));
						list.setPRM_R_NAME(rs.getString("PRM_R_NAME"));
						list.setHTML_CODE(rs.getString("HTML_CODE"));
						list.setPARENTS(rs.getInt("PARENTS"));
						combolist.add(list);
					}
					stsmt.close();
					rs.close();

//					FilteredList<NT_TEMP_LIST_PARAM> filterednationals = new FilteredList<NT_TEMP_LIST_PARAM>(
//							combolist);
//					PARENTS.getEditor().textProperty()
//							.addListener(new InputFilter<NT_TEMP_LIST_PARAM>(PARENTS, filterednationals, false));
					
					PARENTS.setItems(combolist);
					
					FxUtilTest.getComboBoxValue(PRM_TBL_REF);
					FxUtilTest.autoCompleteComboBoxPlus(PRM_TBL_REF, (typedText, itemToCompare) -> itemToCompare
							.getTABLE_NAME().toLowerCase().contains(typedText.toLowerCase()));
					
					
					convert_PARENTS(PARENTS);
				}
				if (cl.getPRM_FOR_PRM_SQL() != null) {
					PRM_FOR_PRM_SQL.replaceText(0, 0, cl.getPRM_FOR_PRM_SQL());
				}
				PRM_NAME.setText(cl.getPRM_NAME());
				OK.setText("Сохранить");

				if (cl.getPRM_SQL() != null) {
					PRM_SQL.replaceText(0, 0, cl.getPRM_SQL());
				}

				PRM_R_NAME.setText(cl.getPRM_R_NAME());

				if (cl.getHTML_CODE() != null) {
					HTML_CODE.replaceText(0, 0, cl.getHTML_CODE());
				}
				// Признак необходимости
				if (cl.getREQUIRED() != null) {
					if (cl.getREQUIRED().equals("Y")) {
						REQUIRED.setSelected(true);
					} else if (cl.getREQUIRED().equals("N")) {
						REQUIRED.setSelected(false);
					}
				}
				// Выбор типа
				if (cl.getPRM_TYPE() != null) {
					for (NT_PRM_TYPE ld : PRM_TYPE.getItems()) {
						if (cl.getPRM_TYPE().equals(ld.getTYPE_ID())) {
							PRM_TYPE.getSelectionModel().select(ld);
							break;
						}
					}
				}
				// Ссылка на таблицу
				if (cl.getPRM_TBL_REF() != null) {
					for (ALL_TABLE ld : PRM_TBL_REF.getItems()) {
						if (cl.getPRM_TBL_REF().equals(ld.getTABLE_NAME())) {
							PRM_TBL_REF.getSelectionModel().select(ld);
							alltbl = ld;
							break;
						}
					}
				}
				// Ссылка параметр
				if (cl.getPARENTS() != null) {
					for (NT_TEMP_LIST_PARAM ld : PARENTS.getItems()) {
						// System.out.println("PRM_ID=" + ld.getPRM_ID() + "; PARENTS=" +
						// cl.getPARENTS());
						if (cl.getPARENTS().equals(ld.getPRM_ID())) {
							PARENTS.getSelectionModel().select(ld);
						}
					}
				}
				// Падеж
				if (cl.getPRM_PADEJ() != null) {
					for (NT_PADEJ ld : PRM_PADEJ.getItems()) {
						if (cl.getPRM_PADEJ().equals(ld.getPDJ_ID())) {
							PRM_PADEJ.getSelectionModel().select(ld);
							break;
						}
					}
				}
			}

			NT_PRM_TYPE type = PRM_TYPE.getSelectionModel().getSelectedItem();
			if (type != null) {
				if (type.getTYPE_ID() == 1) {
					PRM_SQL.setEditable(true);
					PRM_FOR_PRM_SQL.setEditable(true);
					// PRM_PADEJ.setDisable(false);
				} else {
					PRM_SQL.setEditable(false);
					PRM_FOR_PRM_SQL.setEditable(false);

					PRM_SQL.replaceText(0, 0, "");
					// PRM_PADEJ.setDisable(true);
					// PRM_PADEJ.getSelectionModel().select(null);
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void convert_PRM_PADEJ(ComboBox<NT_PADEJ> cmbbx) {
		cmbbx.setConverter(new StringConverter<NT_PADEJ>() {
			@Override
			public String toString(NT_PADEJ product) {
				return product != null ? product.getPDJ_R_NAME() : null;
			}

			@Override
			public NT_PADEJ fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getPDJ_R_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void convert_PARENTS(ComboBox<NT_TEMP_LIST_PARAM> cmbbx) {
		cmbbx.setConverter(new StringConverter<NT_TEMP_LIST_PARAM>() {
			@Override
			public String toString(NT_TEMP_LIST_PARAM product) {
				return product != null ? product.getPRM_NAME() : null;
			}

			@Override
			public NT_TEMP_LIST_PARAM fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getPRM_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void convert_PRM_TYPE(ComboBox<NT_PRM_TYPE> cmbbx) {
		cmbbx.setConverter(new StringConverter<NT_PRM_TYPE>() {
			@Override
			public String toString(NT_PRM_TYPE product) {
				return product != null ? product.getTYPE_NAME() : null;
			}

			@Override
			public NT_PRM_TYPE fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getTYPE_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private void convert_TablrList(ComboBox<ALL_TABLE> cmbbx) {
		cmbbx.setConverter(new StringConverter<ALL_TABLE>() {
			@Override
			public String toString(ALL_TABLE product) {
				return product != null ? product.getTABLE_NAME() : null;
			}

			@Override
			public ALL_TABLE fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getTABLE_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
		String text = HTML_CODE.getText();
		Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
			@Override
			protected StyleSpans<Collection<String>> call() throws Exception {
				return computeHighlighting(text);
			}
		};
		executor.execute(task);
		return task;
	}

	private Task<StyleSpans<Collection<String>>> computeHighlightingAsync1() {
		String text = PRM_FOR_PRM_SQL.getText();
		Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
			@Override
			protected StyleSpans<Collection<String>> call() throws Exception {
				return computeHighlighting(text);
			}
		};
		executor.execute(task);
		return task;
	}

	private Task<StyleSpans<Collection<String>>> computeHighlightingAsync2() {
		String text = PRM_SQL.getText();
		Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
			@Override
			protected StyleSpans<Collection<String>> call() throws Exception {
				return computeHighlighting(text);
			}
		};
		executor.execute(task);
		return task;
	}

	private void applyHighlighting1(StyleSpans<Collection<String>> highlighting) {
		PRM_FOR_PRM_SQL.setStyleSpans(0, highlighting);
	}

	private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
		HTML_CODE.setStyleSpans(0, highlighting);
	}

	private void applyHighlighting2(StyleSpans<Collection<String>> highlighting) {
		PRM_SQL.setStyleSpans(0, highlighting);
	}

	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
					: matcher.group("PAREN") != null ? "paren"
							: matcher.group("BRACE") != null ? "brace"
									: matcher.group("BRACKET") != null ? "bracket"
											: matcher.group("SEMICOLON") != null ? "semicolon"
													: matcher.group("STRING") != null ? "string"
															: matcher.group("COMMENT") != null ? "comment" : null;
			/* never happens */ assert styleClass != null;
			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

}
