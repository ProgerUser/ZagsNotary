package notary.doc.html.controller;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

public class AddParam {

	public AddParam() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
		this.Input = new SimpleStringProperty();
	}

	public void setInput(String Input) {
		this.Input.set(Input);
	}

	public String getInput() {
		return Input.get();
	}

	@FXML
	private TreeTableView<NT_TEMP_LIST_PARAM> param;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, Long> id;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> name;

	@FXML
	private TreeTableColumn<NT_TEMP_LIST_PARAM, String> req;
	private BooleanProperty status;
	Connection conn;
	String json;

	public void setConn(Connection conn, String json, V_NT_TEMP_LIST vals) {
		this.conn = conn;
		this.vals = vals;
		this.json = json;
	}

	private StringProperty Input;

	@FXML
	void cenc(ActionEvent event) {
		try {
			setStatus(false);
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void setStatus(Boolean status) {
		this.status.set(status);
	}

	public Boolean getStatus() {
		return status.get();
	}

	@FXML
	void ok(ActionEvent event) {
		try {
			NT_TEMP_LIST_PARAM val = param.getSelectionModel().getSelectedItem().getValue();
			if (val != null) {
				prm_ret = val;
				setStatus(true);
				onclose();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public NT_TEMP_LIST_PARAM prm;
	public NT_TEMP_LIST_PARAM prm_ret;
	
	@SuppressWarnings("rawtypes")
	TreeItem root = new TreeItem<>("Root");
	
	V_NT_TEMP_LIST vals;

	@SuppressWarnings("unchecked")
	void fillTree() {
		root = new TreeItem<>("Root");
		Map<Long, TreeItem<NT_TEMP_LIST_PARAM>> itemById = new HashMap<>();
		Map<Long, Long> parents = new HashMap<>();
		try {
			PreparedStatement prp = conn.prepareStatement(
					DBUtil.SqlFromProp("/notary/doc/html/controller/Sql.properties", "AddParamForDoc"));
			Clob clob = conn.createClob();
			clob.setString(1, json);
			prp.setLong(1, vals.getID());
			prp.setClob(2, clob);
			ResultSet rs = prp.executeQuery();
			while (rs.next()) {
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
			prp.close();
			rs.close();

			for (Map.Entry<Long, TreeItem<NT_TEMP_LIST_PARAM>> entry : itemById.entrySet()) {
				Long key = entry.getKey();
				Long parent = parents.get(key);
				if (parent.equals(key)) {
					root = entry.getValue();
					root.setExpanded(true);
				} else {
					TreeItem<NT_TEMP_LIST_PARAM> parentItem = itemById.get(parent);
					if (parentItem == null) {
						root.getChildren().add(entry.getValue());
						root.setExpanded(true);
					} else {
						parentItem.getChildren().add(entry.getValue());
					}
				}
			}
			root.setExpanded(true);
			param.setRoot(root);
			param.setShowRoot(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onclose() {
		Stage stage = (Stage) param.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	private void initialize() {
		try {
			id.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_ID());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			req.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getREQUIRED());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			name.setCellValueFactory(cellData -> {
				if (cellData.getValue().getValue() instanceof NT_TEMP_LIST_PARAM) {
					return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getPRM_R_NAME());
				}
				return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
			});
			fillTree();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
