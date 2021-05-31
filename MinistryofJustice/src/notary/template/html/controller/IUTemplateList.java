package notary.template.html.controller;

import java.io.File;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.log4j.Logger;

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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
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

	@FXML
	private TextArea REP_QUERY;
	@FXML
	private ComboBox<NOTARY> NOTARY;

    @FXML
    private TextField PARENT_ID;
    
	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	/**
	 * Для нотариуса
	 */
	private void NotaryConvert() {
		NOTARY.setConverter(new StringConverter<NOTARY>() {
			@Override
			public String toString(NOTARY product) {
				return product.getNOT_ID()+". "+ product.getNOT_NAME();
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
					PreparedStatement prp = conn
							.prepareStatement("update NT_TEMP_LIST set NAME = ?,REP_QUERY=?,DOCX_PATH=?,NOTARY=?,PARENT=? where ID = ?");
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
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "AddTemplateList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
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
	private void initialize() {
		try {
			dbConnect();
			DbUtil.Run_Process(conn);
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
				REP_QUERY.setText(val_list.getREP_QUERY());
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
