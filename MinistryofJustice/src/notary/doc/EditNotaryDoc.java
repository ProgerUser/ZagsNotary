package notary.doc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;

public class EditNotaryDoc {

	@FXML
	private ComboBox<V_NT_TEMP_LIST> VAL_NT_VALUE;
	@FXML
	private TableView<V_NT_DOC_PRM_EDIT> nt_temp_param_val;
	@FXML
	private TableColumn<V_NT_DOC_PRM_EDIT, Integer> id;
	@FXML
	private TableColumn<V_NT_DOC_PRM_EDIT, String> name;
	@FXML
	private TableColumn<V_NT_DOC_PRM_EDIT, String> value;
	@FXML
	private TextField DOC_NUM;
	@FXML
	private ScrollPane scroll;
	private BooleanProperty status;

	public void setStatus(Boolean status) {
		this.status.set(status);
	}

	public Boolean getStatus() {
		return status.get();
	}


//	DBUtil.SqlFromProp("notary/doc/SQL.properties", "NtParamVal")
	void Init(Integer id) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_DOC_PRM_EDIT t where t.PRM_TMP_ID = ? and VAL_NT_DOC = ? ");
			prepStmt.setInt(1, id);
			prepStmt.setInt(2, nt_doc.getID());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_DOC_PRM_EDIT> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_DOC_PRM_EDIT list = new V_NT_DOC_PRM_EDIT();
				list.setVAL_NT_VALUE(rs.getString("VAL_NT_VALUE"));
				list.setVAL_PRM_ID(rs.getInt("VAL_PRM_ID"));
				list.setPRM_TYPE(rs.getInt("PRM_TYPE"));
				list.setPRM_NAME(rs.getString("PRM_NAME"));
				list.setVAL_NT_DOC(rs.getInt("VAL_NT_DOC"));
				list.setPRM_ID(rs.getInt("PRM_ID"));
				list.setPRM_TMP_ID(rs.getInt("PRM_TMP_ID"));
				list.setPRM_SQL(rs.getString("PRM_SQL"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();
			nt_temp_param_val.setItems(dlist);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		try {
			setStatus(false);
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void EditParam() {
		try {
			V_NT_DOC_PRM_EDIT val = nt_temp_param_val.getSelectionModel().getSelectedItem();
			if (val != null) {
				Stage stage = new Stage();
				Stage stage_ = (Stage) scroll.getScene().getWindow();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/notary/doc/IUParam.fxml"));

				IUParamEdit controller = new IUParamEdit();
				controller.setConn(conn, val);
				loader.setController(controller);

				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.getIcons().add(new Image("/icon.png"));
				stage.setTitle("Редактировать");
				stage.initOwner(stage_);
				stage.setResizable(true);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent paramT) {
						if (controller.getStatus()) {
							Init(VAL_NT_VALUE.getSelectionModel().getSelectedItem().getID());
						}
					}
				});
				stage.showAndWait();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	
	@FXML
	void EditParam(ActionEvent event) {
		try {
			EditParam();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			conn.commit();
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void VAL_NT_VALUE(ActionEvent event) {
		try {
			V_NT_TEMP_LIST val = VAL_NT_VALUE.getSelectionModel().getSelectedItem();
			if (val != null) {
				Init(val.getID());
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public EditNotaryDoc() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
	}

	@FXML
	private void initialize() {
		try {
			//scroll.setFitToHeight(true);
			//scroll.setFitToWidth(true);
			// Двойной щелчок по строке для открытия документа
			nt_temp_param_val.setRowFactory(tv -> {
				TableRow<V_NT_DOC_PRM_EDIT> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						EditParam();
					}
				});
				return row;
			});
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from V_NT_TEMP_LIST");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<V_NT_TEMP_LIST> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					V_NT_TEMP_LIST list = new V_NT_TEMP_LIST();
					list.setPARENT(rs.getInt("PARENT"));
					list.setNAMES(rs.getString("NAMES"));
					list.setNAME(rs.getString("NAME"));
					list.setFILE_PATH(rs.getString("FILE_PATH"));
					list.setID(rs.getInt("ID"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				FilteredList<V_NT_TEMP_LIST> filterednationals = new FilteredList<V_NT_TEMP_LIST>(combolist);
				VAL_NT_VALUE.getEditor().textProperty()
						.addListener(new InputFilter<V_NT_TEMP_LIST>(VAL_NT_VALUE, filterednationals, false));
				VAL_NT_VALUE.setItems(combolist);
				convert_VAL_NT_VALUE(VAL_NT_VALUE);
				rs.close();
				if (nt_doc.getNT_TYPE() != null) {
					for (V_NT_TEMP_LIST ld : VAL_NT_VALUE.getItems()) {
						if (nt_doc.getNT_TYPE().equals(ld.getID())) {
							VAL_NT_VALUE.getSelectionModel().select(ld);
							break;
						}
					}
				}
			}
			Init(nt_doc.getNT_TYPE());
			id.setCellValueFactory(cellData -> cellData.getValue().PRM_IDProperty().asObject());
			name.setCellValueFactory(cellData -> cellData.getValue().PRM_NAMEProperty());
			value.setCellValueFactory(cellData -> cellData.getValue().VAL_NT_VALUEProperty());
			Main.autoResizeColumns(nt_temp_param_val);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void convert_VAL_NT_VALUE(ComboBox<V_NT_TEMP_LIST> cmbbx) {
		cmbbx.setConverter(new StringConverter<V_NT_TEMP_LIST>() {
			@Override
			public String toString(V_NT_TEMP_LIST product) {
				return product != null ? product.getNAMES() : null;
			}

			@Override
			public V_NT_TEMP_LIST fromString(final String string) {
				return cmbbx.getItems().stream().filter(product -> product.getNAMES().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
	V_NT_DOC nt_doc;
	public void setConn(Connection conn,V_NT_DOC nt_doc) {
		try {
			this.conn = conn;
			this.nt_doc = nt_doc;
			this.conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
	private Connection conn;

	void onclose() {
		Stage stage = (Stage) VAL_NT_VALUE.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
