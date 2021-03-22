package notary.template;

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
import mj.dbutil.DBUtil;

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
    
	@FXML
	void OK(ActionEvent event) {
		try {
			if (!PRM_NAME.getText().equals("")) {
				if (gettype().equals("I")) {
					PreparedStatement prp = conn.prepareStatement(
							"insert into NT_TEMP_LIST_PARAM (PRM_NAME,PRM_TMP_ID,PRM_SQL,PRM_TYPE) values (?,?,?,?)");
					prp.setString(1, PRM_NAME.getText());
					prp.setInt(2, getID());
					prp.setString(3, PRM_SQL.getText());
					prp.setInt(4, PRM_TYPE.getSelectionModel().getSelectedItem().getTYPE_ID());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn.prepareStatement(
							"update NT_TEMP_LIST_PARAM set PRM_NAME = ?,PRM_SQL=?,PRM_TYPE=? where PRM_ID = ?");
					prp.setString(1, PRM_NAME.getText());
					prp.setString(2, PRM_SQL.getText());
					prp.setInt(3, PRM_TYPE.getSelectionModel().getSelectedItem().getTYPE_ID());
					prp.setInt(4, cl.getPRM_ID());
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
				//Выбор типа
				if (cl.getPRM_TYPE() != null) {
					for (NT_PRM_TYPE ld : PRM_TYPE.getItems()) {
						if (cl.getPRM_TYPE().equals(ld.getTYPE_ID())) {
							PRM_TYPE.getSelectionModel().select(ld);
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
}
