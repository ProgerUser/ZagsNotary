package notary.doc.html.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import mj.msg.Msg;
import mj.utils.DbUtil;
import notary.doc.html.model.V_NT_DOC;
import notary.doc.html.model.V_NT_TEMP_LIST;
import notary.template.html.model.NT_PADEJ;
import notary.template.html.model.NT_TEMP_LIST_PARAM;

public class DocParamEdit {

	public DocParamEdit() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private ComboBox<NT_PADEJ> PRM_PADEJ;

	@FXML
	private TextField PRM_R_NAME;

	@FXML
	private Button OK;

	private Connection conn;

	public void setConn(Connection conn, V_NT_TEMP_LIST val, V_NT_DOC DOC,NT_TEMP_LIST_PARAM PRM) {
		this.conn = conn;
		this.NT_DOC = val;
		this.DOC = DOC;
		this.PRM = PRM;
	}

	V_NT_DOC DOC;

	V_NT_TEMP_LIST NT_DOC;
	
	NT_TEMP_LIST_PARAM PRM;

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
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (!PRM_R_NAME.getText().equals("") & PRM_R_NAME.getText().length() > 5) {
				CallableStatement cls = conn.prepareCall("{call NT_PKG.EDIT_DOC_PARAM(?,?,?,?,?,?)}");
				cls.registerOutParameter(1, Types.VARCHAR);
				cls.setLong(2, NT_DOC.getID());
				if (PRM_PADEJ.getSelectionModel().getSelectedItem() != null) {
					cls.setLong(3, PRM_PADEJ.getSelectionModel().getSelectedItem().getPDJ_ID());
				} else {
					cls.setNull(3, Types.INTEGER);
				}
				cls.setString(4, PRM_R_NAME.getText());
				cls.setLong(5, DOC.getID());
				cls.setLong(6, PRM.getPRM_ID());
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
			DbUtil.Log_Error(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) PRM_R_NAME.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		try {
			OK.setText("Сохранить");
			// Падежи
			{
				PreparedStatement stsmt = conn.prepareStatement("select * from NT_PADEJ order by PDJ_ID asc");
				ResultSet rs = stsmt.executeQuery();
				ObservableList<NT_PADEJ> combolist = FXCollections.observableArrayList();
				while (rs.next()) {
					NT_PADEJ list = new NT_PADEJ();
					list.setPDJ_ID(rs.getLong("PDJ_ID"));
					list.setPDJ_NAME(rs.getString("PDJ_NAME"));
					list.setPDJ_R_NAME(rs.getString("PDJ_R_NAME"));
					combolist.add(list);
				}
				stsmt.close();
				rs.close();
				PRM_PADEJ.setItems(combolist);
				convert_PRM_PADEJ(PRM_PADEJ);
			}
			PRM_R_NAME.setText(PRM.getPRM_R_NAME());
			// Падеж
			if (PRM.getPRM_PADEJ() != null) {
				for (NT_PADEJ ld : PRM_PADEJ.getItems()) {
					if (PRM.getPRM_PADEJ().equals(ld.getPDJ_ID())) {
						PRM_PADEJ.getSelectionModel().select(ld);
						break;
					}
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
