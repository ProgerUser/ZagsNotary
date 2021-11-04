package ru.psv.mj.prjmngm.inboxdocs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class SelRootPmDocController {

	void OnClose() {
		Stage stage = (Stage) PM_DOCS.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Конструктор
	 */
	public SelRootPmDocController() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.RetId = new SimpleLongProperty();
	}

	private BooleanProperty Status;
	private LongProperty RetId;

	public boolean getStatus() {
		return this.Status.get();
	}

	public Long getRetId() {
		return this.RetId.get();
	}

	public void setStatus(Boolean value, Long RetId) {
		this.Status.set(value);
		this.RetId.set(RetId);
	}
	VPM_DOCS class_;
	Connection conn;
	public void SetClass(VPM_DOCS class_, Connection conn) {
		this.class_ = class_;
		this.conn = conn;
	}

	// <TableView>
	@FXML
	private TableView<VPM_DOCS> PM_DOCS;
	// </TableView>
	// <TableColumn>
	@FXML
	private TableColumn<VPM_DOCS, Long> DOC_ID;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_NUMBER;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_TP_NAME;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_COMMENT;
	@FXML
	private TableColumn<Object, LocalDate> DOC_DATE;
	@FXML
	private TableColumn<Object, LocalDate> DOC_END;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_ISFAST;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_USR;
	@FXML
	private TableColumn<VPM_DOCS, String> ORG_NAME;
	@FXML
	private TableColumn<VPM_DOCS, String> DOC_NAME;
	@FXML
	private TableColumn<Object, LocalDateTime> DOC_START;
	// </TableColumn>

	@FXML
	void Cancel(ActionEvent event) {
		try {
			OnClose();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Select(ActionEvent event) {
		try {
			if (PM_DOCS.getSelectionModel().getSelectedItem() != null) {
				VPM_DOCS sel = PM_DOCS.getSelectionModel().getSelectedItem();
				setStatus(true, sel.getDOC_ID());
				OnClose();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Обновить
	 * 
	 * @param event
	 */
	@FXML
	private void Reshresh(ActionEvent event) {
		try {
			LoadTable();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			new ConvConst().TableColumnDate(DOC_DATE);
			new ConvConst().TableColumnDate(DOC_END);
			new ConvConst().TableColumnDateTime(DOC_START);
			// init table column
			DOC_ID.setCellValueFactory(cellData -> cellData.getValue().DOC_IDProperty().asObject());
			DOC_COMMENT.setCellValueFactory(cellData -> cellData.getValue().DOC_COMMENTProperty());
			DOC_ISFAST.setCellValueFactory(cellData -> cellData.getValue().DOC_ISFASTProperty());
			DOC_DATE.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).DOC_DATEProperty());
			DOC_TP_NAME.setCellValueFactory(cellData -> cellData.getValue().DOC_TP_NAMEProperty());
			DOC_END.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).DOC_ENDProperty());
			DOC_NUMBER.setCellValueFactory(cellData -> cellData.getValue().DOC_NUMBERProperty());
			DOC_USR.setCellValueFactory(cellData -> cellData.getValue().DOC_USRProperty());
			DOC_START.setCellValueFactory(cellData -> ((VPM_DOCS) cellData.getValue()).TM$DOC_STARTProperty());
			ORG_NAME.setCellValueFactory(cellData -> cellData.getValue().ORG_NAMEProperty());
			DOC_NAME.setCellValueFactory(cellData -> cellData.getValue().DOC_NAMEProperty());

			// load table
			LoadTable();
			/**
			 * Двойной щелчок по строке
			 */
			PM_DOCS.setRowFactory(tv -> {
				TableRow<VPM_DOCS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (PM_DOCS.getSelectionModel().getSelectedItem() != null) {
							VPM_DOCS sel = PM_DOCS.getSelectionModel().getSelectedItem();
							setStatus(true, sel.getDOC_ID());
							OnClose();
						}
					}
				});
				return row;
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Initialize table
	 */
	void LoadTable() {
		try {
			// loop
			String selectStmt = "select * from vselpm_docs where doc_id <> ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			prepStmt.setLong(1,class_.getDOC_ID());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<VPM_DOCS> obslist = FXCollections.observableArrayList();
			while (rs.next()) {
				VPM_DOCS list = new VPM_DOCS();

				list.setORG_NAME(rs.getString("ORG_NAME"));
				list.setDOC_ID(rs.getLong("DOC_ID"));
				list.setDOC_COMMENT(rs.getString("DOC_COMMENT"));
				list.setDOC_ISFAST(rs.getString("DOC_ISFAST"));
				list.setDOC_DATE((rs.getDate("DOC_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_DATE")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_NAME(rs.getString("DOC_NAME"));
				list.setDOC_REF(rs.getLong("DOC_REF"));
				list.setDOC_TP_ID(rs.getLong("DOC_TP_ID"));
				list.setDOC_TP_NAME(rs.getString("DOC_TP_NAME"));
				list.setORG_ID(rs.getLong("ORG_ID"));
				list.setDOC_END((rs.getDate("DOC_END") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DOC_END")),
								DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: null);
				list.setDOC_NUMBER(rs.getString("DOC_NUMBER"));
				list.setTM$DOC_START((rs.getDate("TM$DOC_START") != null) ? LocalDateTime.parse(
						new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rs.getDate("TM$DOC_START")),
						DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) : null);
				list.setDOC_USR(rs.getString("DOC_USR"));

				obslist.add(list);
			}
			// add data
			PM_DOCS.setItems(obslist);
			// close
			prepStmt.close();
			rs.close();
			// add filter
			TableFilter<VPM_DOCS> tableFilter = TableFilter.forTableView(PM_DOCS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			// resize
			ResizeColumns(PM_DOCS);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Авто расширение
	 * 
	 * @param table
	 */
	void ResizeColumns(TableView<?> table) {
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column_) -> {
			if (column_.getText().equals("Сист.данные")) {
			} else {
				Text t = new Text(column_.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					if (column_.getCellData(i) != null) {
						if (column_.getCellData(i) != null) {
							t = new Text(column_.getCellData(i).toString());
							double calcwidth = t.getLayoutBounds().getWidth();
							if (calcwidth > max) {
								max = calcwidth;
							}
						}
					}
				}
				column_.setPrefWidth(max + 20.0d);
			}
		});
	}

	
}
