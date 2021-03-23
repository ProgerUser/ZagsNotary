package notary.doc;

import java.sql.Connection;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;

public class AddParam {

	public AddParam() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private VBox VBOX_ROOT;

	@FXML
	private TitledPane T_F;
	@FXML
	private TitledPane L_F;
	@FXML
	private TitledPane T_A;
	@FXML
	private TitledPane D_F;

	@FXML
	private TextField IF_TEXT_FIELD;
	@FXML
	private TextArea IF_TEXT_AREA;
	@FXML
	private TextField IF_LIST_FIELD_NAME;
	@FXML
	private TextField IF_LIST_FIELD_ID;
	@FXML
	private DatePicker IF_DATE_FIELD;

	@FXML
	private Button OK;
	@FXML
	private Button IF_LIST_FIELD_B;

	@FXML
	void Cencel(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void IF_LIST_FIELD_B(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	V_NT_DOC_PRM prm;
	Connection conn;

	public void setConn(Connection conn, V_NT_DOC_PRM prm) {
		try {
			this.conn = conn;
			this.prm = prm;
			this.conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			if (prm.getPRM_TYPE() == 1) {
				// Список
				VBOX_ROOT.getChildren().remove(T_F);
				VBOX_ROOT.getChildren().remove(T_A);
				VBOX_ROOT.getChildren().remove(D_F);
				L_F.setText(prm.getPRM_NAME());
			} else if (prm.getPRM_TYPE() == 2) {
				// Поле
				VBOX_ROOT.getChildren().remove(L_F);
				VBOX_ROOT.getChildren().remove(T_A);
				VBOX_ROOT.getChildren().remove(D_F);
			} else if (prm.getPRM_TYPE() == 3) {
				// Дата
				VBOX_ROOT.getChildren().remove(L_F);
				VBOX_ROOT.getChildren().remove(T_A);
				VBOX_ROOT.getChildren().remove(T_F);
				new ConvConst().FormatDatePiker(IF_DATE_FIELD);
			} else if (prm.getPRM_TYPE() == 4) {
				// Расширенное поле
				VBOX_ROOT.getChildren().remove(L_F);
				VBOX_ROOT.getChildren().remove(D_F);
				VBOX_ROOT.getChildren().remove(T_F);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
