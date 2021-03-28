package notary.template.html.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.InputFilter;
import mj.dbutil.DBUtil;
import notary.template.html.model.ALL_TABLE;
import notary.template.html.model.NT_PRM_TYPE;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

public class IUTempParamIU {

	private IntegerProperty ID;
	private StringProperty type;

	@FXML
	private Button OK;
	NT_TEMP_LIST_PARAM cl;
	@FXML
	private ComboBox<NT_PRM_TYPE> PRM_TYPE;

	@FXML
	private TextArea PRM_SQL;

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
				} else {
					PRM_SQL.setEditable(false);
					PRM_SQL.setText("");
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
							"insert into NT_TEMP_LIST_PARAM (PRM_NAME,PRM_TMP_ID,PRM_SQL,PRM_TYPE,PRM_TBL_REF) values (?,?,?,?,?)");
					prp.setString(1, PRM_NAME.getText());
					prp.setInt(2, getID());
					prp.setString(3, PRM_SQL.getText());
					prp.setInt(4, PRM_TYPE.getSelectionModel().getSelectedItem().getTYPE_ID());
					prp.setString(5, alltbl.getTABLE_NAME());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn.prepareStatement(
							"update NT_TEMP_LIST_PARAM set PRM_NAME = ?,PRM_SQL=?,PRM_TYPE=?,PRM_TBL_REF=? where PRM_ID = ?");
					prp.setString(1, PRM_NAME.getText());
					prp.setString(2, PRM_SQL.getText());
					prp.setInt(3, PRM_TYPE.getSelectionModel().getSelectedItem().getTYPE_ID());
					prp.setString(4, alltbl.getTABLE_NAME());
					prp.setInt(5, cl.getPRM_ID());
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

	@FXML
	private void initialize() {
		try {
			dbConnect();
			DBUtil.RunProcess(conn);
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

				FilteredList<ALL_TABLE> filterednationals = new FilteredList<ALL_TABLE>(combolist);
				PRM_TBL_REF.getEditor().textProperty()
						.addListener(new InputFilter<ALL_TABLE>(PRM_TBL_REF, filterednationals, false));
				PRM_TBL_REF.setItems(filterednationals);
				convert_TablrList(PRM_TBL_REF);
			}
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

			if (gettype().equals("U")) {
				PRM_NAME.setText(cl.getPRM_NAME());
				OK.setText("Сохранить");
				PRM_SQL.setText(cl.getPRM_SQL());
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
							break;
						}
					}
				}
			}

			NT_PRM_TYPE type = PRM_TYPE.getSelectionModel().getSelectedItem();
			if (type != null) {
				if (type.getTYPE_ID() == 1) {
					PRM_SQL.setEditable(true);
				} else {
					PRM_SQL.setEditable(false);
					PRM_SQL.setText("");
				}
			}

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
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
}
