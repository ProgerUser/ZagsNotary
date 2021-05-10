package notary.doc.html.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import notary.client.model.NT_CLI_TYPES;
import notary.doc.html.model.V_NT_DOC;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_PADEJ;

public class DocParamAdd {

	public DocParamAdd() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private ComboBox<NT_PADEJ> PRM_PADEJ;

	@FXML
	private TextField PRM_R_NAME;

	@FXML
	private Button OK;

	private Connection conn;

	@FXML
	private ComboBox<NT_CLI_TYPES> CUS_TYPE;

	public void setConn(Connection conn, V_NT_TEMP_LIST val, V_NT_DOC DOC) {
		this.conn = conn;
		this.NT_DOC = val;
		this.DOC = DOC;
	}

	V_NT_DOC DOC;

	V_NT_TEMP_LIST NT_DOC;

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

	@FXML
	void Cencel(ActionEvent event) {
		onclose();
	}

	@FXML
	private void DelPadej() {
		try {
			PRM_PADEJ.setValue(null);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!PRM_R_NAME.getText().equals("") & PRM_R_NAME.getText().length() > 5) {
				
				NT_CLI_TYPES val = CUS_TYPE.getSelectionModel().getSelectedItem();
				if (val == null) {
					Msg.Message("Выберите тип клиента!");
					return;
				}
				
				CallableStatement cls = conn.prepareCall("{call NT_PKG.ADD_DOC_PARAM(?,?,?,?,?,?)}");
				cls.registerOutParameter(1, Types.VARCHAR);
				cls.setInt(2, NT_DOC.getID());
				if (PRM_PADEJ.getSelectionModel().getSelectedItem() != null) {
					cls.setInt(3, PRM_PADEJ.getSelectionModel().getSelectedItem().getPDJ_ID());
				} else {
					cls.setNull(3, Types.INTEGER);
				}
				cls.setString(4, PRM_R_NAME.getText());
				cls.setInt(5, DOC.getID());
				
				cls.setInt(6, val.getCODE());
				
				cls.execute();
				// --------------
				if (cls.getString(1) == null) {
					conn.commit();
					onclose();
				} else {
					conn.rollback();
					Msg.Message(cls.getString(1));
				}
				cls.close();
			} else {
				Msg.Message("Введите название параметра больше 5 букв!");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) PRM_R_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
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

			// тип клиента
			{
				Statement sqlStatement = conn.createStatement();
				String readRecordSQL = "select * from NT_CLI_TYPES";
				ResultSet rs = sqlStatement.executeQuery(readRecordSQL);
				ObservableList<NT_CLI_TYPES> areas = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_CLI_TYPES list = new NT_CLI_TYPES();
					list.setNAME(rs.getString("NAME"));
					list.setCODE(rs.getInt("CODE"));
					areas.add(list);
				}
				CUS_TYPE.setItems(areas);
				CombCusType(CUS_TYPE);
				rs.close();
				sqlStatement.close();
			}
			
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private void CombCusType(ComboBox<NT_CLI_TYPES> cmb) {
		cmb.setConverter(new StringConverter<NT_CLI_TYPES>() {
			@Override
			public String toString(NT_CLI_TYPES product) {
				return (product != null) ? product.getNAME() : "";
			}

			@Override
			public NT_CLI_TYPES fromString(final String string) {
				return cmb.getItems().stream().filter(product -> product.getNAME().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}
}
