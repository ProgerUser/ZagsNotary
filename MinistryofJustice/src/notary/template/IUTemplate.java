package notary.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;

public class IUTemplate {
	
	private IntegerProperty NT_PARENT;
	private IntegerProperty ID;
	private StringProperty type;
	private StringProperty NAME;
	
    @FXML
    private Button OK;
    
	public IUTemplate() {
		Main.logger = Logger.getLogger(getClass());
		this.NT_PARENT = new SimpleIntegerProperty();
		this.ID = new SimpleIntegerProperty();
		this.type = new SimpleStringProperty();
		this.NAME = new SimpleStringProperty();
	}
	public void setNAME(String NAME) {
		this.NAME.set(NAME);
	}
	public String getNAME() {
		return NAME.get();
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
	
	public void setNT_PARENT(Integer NT_PARENT) {
		this.NT_PARENT.set(NT_PARENT);
	}
	public Integer getNT_PARENT() {
		return NT_PARENT.get();
	}

	private Connection conn;
    @FXML
    private TextField NT_NAME;

    @FXML
    void Cencel(ActionEvent event) {
    	onclose();
    }

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!NT_NAME.getText().equals("")) {
				if (gettype().equals("I")) {
					PreparedStatement prp = conn
							.prepareStatement("insert into NT_TEMPLATE (NT_PARENT,NT_NAME) values (?,?)");
					prp.setInt(1, getNT_PARENT());
					prp.setString(2, NT_NAME.getText());
					prp.executeUpdate();
					prp.close();
					conn.commit();
					onclose();
				} else if (gettype().equals("U")) {
					PreparedStatement prp = conn.prepareStatement("update NT_TEMPLATE set NT_NAME = ? where NT_ID = ?");
					prp.setInt(2, getID());
					prp.setString(1, NT_NAME.getText());
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
			props.put("v$session.program", "AddTemplate");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) NT_NAME.getScene().getWindow();
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
			if (gettype().equals("U")) {
				NT_NAME.setText(getNAME());
				OK.setText("Сохранить");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
