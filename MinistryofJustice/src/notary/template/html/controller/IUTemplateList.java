package notary.template.html.controller;

import java.io.File;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.users.NOTARY;
import mj.utils.DbUtil;
import notary.template.html.model.NT_TEMPLATE;
import notary.template.html.model.NT_TEMP_LIST;

public class IUTemplateList {

	private StringProperty type;

	@FXML
	private Button OK;

	public IUTemplateList() {
		Main.logger = Logger.getLogger(getClass());
		this.type = new SimpleStringProperty();
	}

	public void settype(String type) {
		this.type.set(type);
	}

	NT_TEMPLATE val;
	NT_TEMP_LIST val_list;

	public void setVal(NT_TEMPLATE val) {
		this.val = val;
	}

	public void setVal(NT_TEMP_LIST val_list) {
		this.val_list = val_list;
	}

	public String gettype() {
		return type.get();
	}

	private Connection conn;
	@FXML
	private TextField NAME;

	@FXML
	private TextField DOCX_PATH;

//	@FXML
//	private TextArea REP_QUERY;

	@FXML
	private ComboBox<NOTARY> NOTARY;

	@FXML
	private TextField PARENT_ID;

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	private CodeArea REP_QUERY;

	/**
	 * Для нотариуса
	 */
	private void NotaryConvert() {
		NOTARY.setConverter(new StringConverter<NOTARY>() {
			@Override
			public String toString(NOTARY product) {
				return product.getNOT_ID() + ". " + product.getNOT_NAME();
			}

			@Override
			public NOTARY fromString(final String string) {
				return NOTARY.getItems().stream().filter(product -> product.getNOT_NAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!NAME.getText().equals("")) {
				if (gettype().equals("I")) {
					PreparedStatement prp = conn.prepareStatement(
							"insert into NT_TEMP_LIST (NAME,PARENT,REP_QUERY,DOCX_PATH,NOTARY) values (?,?,?,?,?)");
					prp.setString(1, NAME.getText());
					prp.setLong(2, val.getNT_ID());
					Clob clob = conn.createClob();
					clob.setString(1, REP_QUERY.getText());
					prp.setClob(3, clob);
					prp.setString(4, DOCX_PATH.getText());
					if (NOTARY.getSelectionModel().getSelectedItem() != null) {
						prp.setLong(5, NOTARY.getSelectionModel().getSelectedItem().getNOT_ID());
					} else {
						prp.setNull(5, Types.INTEGER);
					}
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn.prepareStatement(
							"update NT_TEMP_LIST set NAME = ?,REP_QUERY=?,DOCX_PATH=?,NOTARY=?,PARENT=? where ID = ?");
					prp.setString(1, NAME.getText());
					Clob clob = conn.createClob();
					clob.setString(1, REP_QUERY.getText());
					prp.setClob(2, clob);
					prp.setString(3, DOCX_PATH.getText());
					if (NOTARY.getSelectionModel().getSelectedItem() != null) {
						prp.setLong(4, NOTARY.getSelectionModel().getSelectedItem().getNOT_ID());
					} else {
						prp.setNull(4, Types.INTEGER);
					}
					prp.setLong(5, Long.valueOf(PARENT_ID.getText()));
					prp.setLong(6, val_list.getID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private void dbConnect() {
		try {
			conn = DbUtil.GetConnect(getClass().getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) OK.getScene().getWindow();
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

	@FXML
	void AddPath(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбрать файл");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ALL Others", "*.*"),
					new ExtensionFilter("Exe file", "*.exe"), new ExtensionFilter("Jar file", "*.jar"),
					new ExtensionFilter("DLL file", "*.dll"), new ExtensionFilter("SQLITE file", "*.db"),
					new ExtensionFilter("WORD file", "*.doc*"));
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				DOCX_PATH.setText(file.getAbsolutePath().substring(
						file.getAbsolutePath().indexOf(System.getenv("MJ_PATH")) + System.getenv("MJ_PATH").length()));
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private VBox sql_vb;

	private static final String[] KEYWORDS = new String[] { "TO_CHAR", "TO_NUMBER", "PIVOT", "UNPIVOT", "ACCESS", "ADD",
			"ALL", "ALTER", "AND", "ANY", "AS", "ASC", "AUDIT", "BETWEEN", "BY", "CHAR", "CHECK", "CLUSTER", "COLUMN",
			"COLUMN_VALUE", "COMMENT", "COMPRESS", "CONNECT", "CREATE", "CURRENT", "DATE", "DECIMAL", "DEFAULT",
			"DELETE", "DESC", "DISTINCT", "DROP", "ELSE", "EXCLUSIVE", "EXISTS", "FILE", "FLOAT", "FOR", "FROM",
			"GRANT", "GROUP", "HAVING", "IDENTIFIED", "IMMEDIATE", "IN", "INCREMENT", "INDEX", "INITIAL", "INSERT",
			"INTEGER", "INTERSECT", "INTO", "IS", "LEVEL", "LIKE", "LOCK", "LONG", "MAXEXTENTS", "MINUS", "MLSLABEL",
			"MODE", "MODIFY", "NESTED_TABLE_ID", "NOAUDIT", "NOCOMPRESS", "NOT", "NOWAIT", "NULL", "NUMBER", "OF",
			"OFFLINE", "ON", "ONLINE", "OPTION", "OR", "ORDER", "PCTFREE", "PRIOR", "PUBLIC", "RAW", "RENAME",
			"RESOURCE", "REVOKE", "ROW", "ROWID", "ROWNUM", "ROWS", "SELECT", "SESSION", "SET", "SHARE", "SIZE",
			"SMALLINT", "START", "SUCCESSFUL", "SYNONYM", "SYSDATE", "TABLE", "THEN", "TO", "TRIGGER", "UID", "UNION",
			"UNIQUE", "UPDATE", "USER", "VALIDATE", "VALUES", "VARCHAR", "VARCHAR2", "VIEW", "WHENEVER", "WHERE",
			"WITH", "TRIM", "CASE", "await", "break", "case", "catch", "class", "const", "continue", "debugger",
			"default", "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for", "function", "if",
			"implements", "import", "in", "instanceof", "interface", "let", "new", "null", "package", "private",
			"protected", "public", "return", "super", "switch", "static", "this", "throw", "try", "True", "typeof",
			"var", "void", "while", "with", "yield", "to_char", "to_number", "pivot", "unpivot", "access", "add", "all",
			"alter", "and", "any", "as", "asc", "audit", "between", "by", "char", "check", "cluster", "column",
			"column_value", "comment", "compress", "connect", "create", "current", "date", "decimal", "default",
			"delete", "desc", "distinct", "drop", "else", "exclusive", "exists", "file", "float", "for", "from",
			"grant", "group", "having", "identified", "immediate", "in", "increment", "index", "initial", "insert",
			"integer", "intersect", "into", "is", "level", "like", "lock", "long", "maxextents", "minus", "mlslabel",
			"mode", "modify", "nested_table_id", "noaudit", "nocompress", "not", "nowait", "null", "number", "of",
			"offline", "on", "online", "option", "or", "order", "pctfree", "prior", "public", "raw", "rename",
			"resource", "revoke", "row", "rowid", "rownum", "rows", "select", "session", "set", "share", "size",
			"smallint", "start", "successful", "synonym", "sysdate", "table", "then", "to", "trigger", "uid", "union",
			"unique", "update", "user", "validate", "values", "varchar", "varchar2", "view", "whenever", "where",
			"with", "trim", "case", };

	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	private static final String PAREN_PATTERN = "\\(|\\)";
	private static final String BRACE_PATTERN = "\\{|\\}";
	private static final String BRACKET_PATTERN = "\\[|\\]";
	private static final String SEMICOLON_PATTERN = "\\;";
	private static final String STRING_PATTERN = "'([^'\\\\]|\\\\.)*'";
	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private static final Pattern PATTERN = Pattern.compile(
			"(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN
					+ ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
					+ "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");

	private ExecutorService executor;

	private Task<StyleSpans<Collection<String>>> computeHighlightingAsync1() {
		String text = REP_QUERY.getText();
		Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
			@Override
			protected StyleSpans<Collection<String>> call() throws Exception {
				return computeHighlighting(text);
			}
		};
		executor.execute(task);
		return task;
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

	private void applyHighlighting1(StyleSpans<Collection<String>> highlighting) {
		REP_QUERY.setStyleSpans(0, highlighting);
	}

	@SuppressWarnings("unused")
	@FXML
	private void initialize() {
		try {
			REP_QUERY = new CodeArea();
			sql_vb.getChildren().add(new StackPane(new VirtualizedScrollPane<>(REP_QUERY)));
			REP_QUERY.setParagraphGraphicFactory(LineNumberFactory.get(REP_QUERY));

			sql_vb.getStyleClass().add("mylistview");
			sql_vb.getStylesheets().add("/ScrPane.css");

			REP_QUERY.setPrefHeight(500);
			REP_QUERY.setMaxHeight(Double.MAX_VALUE);
			REP_QUERY.setMaxWidth(Double.MAX_VALUE);

			executor = Executors.newSingleThreadExecutor();
			
			Subscription cleanupWhenDone1 = REP_QUERY.multiPlainChanges().successionEnds(Duration.ofMillis(500))
					.supplyTask(this::computeHighlightingAsync1).awaitLatest(REP_QUERY.multiPlainChanges())
					.filterMap(t -> {
						if (t.isSuccess()) {
							return Optional.of(t.get());
						} else {
							t.getFailure().printStackTrace();
							return Optional.empty();
						}
					}).subscribe(this::applyHighlighting1);

			REP_QUERY.getStylesheets()
					.add(getClass().getResource("/notary/template/html/controller/java-keywords.css").toExternalForm());

			dbConnect();
			//DbUtil.Run_Process(conn,getClass().getName());
			// Нотариус
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from notary");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<NOTARY> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					NOTARY list = new NOTARY();
					list.setNOT_ID(rs.getLong("NOT_ID"));
					list.setNOT_OTD(rs.getLong("NOT_OTD"));
					list.setNOT_NAME(rs.getString("NOT_NAME"));
					list.setNOT_RUK(rs.getString("NOT_RUK"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				NOTARY.setItems(combolist);
				NotaryConvert();
			}
			if (gettype().equals("U")) {
				NAME.setText(val_list.getNAME());

				REP_QUERY.replaceText(0, 0, val_list.getREP_QUERY());

				OK.setText("Сохранить");
				DOCX_PATH.setText(val_list.getDOCX_PATH());
				PARENT_ID.setText(String.valueOf(val_list.getPARENT()));
				if (val_list.getNOTARY() != null) {
					for (NOTARY ld : NOTARY.getItems()) {
						if (val_list.getNOTARY().equals(ld.getNOT_ID())) {
							NOTARY.getSelectionModel().select(ld);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
