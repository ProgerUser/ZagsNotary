package notary.doc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;

public class IUParamEdit {

	public IUParamEdit() {
		Main.logger = Logger.getLogger(getClass());
		this.status = new SimpleBooleanProperty();
	}

	@FXML
	private VBox VBOX_ROOT;

	private BooleanProperty status;

	public void setStatus(Boolean status) {
		this.status.set(status);
	}

	public Boolean getStatus() {
		return status.get();
	}

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
			setStatus(false);
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) OK.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	void IF_LIST_FIELD_B(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) IF_LIST_FIELD_B.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/doc/ParamList.fxml"));

			ParamList controller = new ParamList();
			controller.setQuery(prm.getPRM_SQL());
			controller.setConn(conn);
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Список");
			stage.initOwner(stage_);
			stage.setResizable(true);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					if (controller.getStatus()) {
						IF_LIST_FIELD_ID.setText(controller.getCode_s());
						IF_LIST_FIELD_NAME.setText(controller.getName_s());
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void OK(ActionEvent event) {
		try {
			if (prm.getPRM_TYPE() == 1) {
				// Список
				if (!IF_LIST_FIELD_ID.getText().equals("") & !IF_LIST_FIELD_NAME.getText().equals("")) {
					CallableStatement prp = conn
							.prepareCall(DBUtil.SqlFromProp("/notary/doc/SQL.properties", "UpdateParamValue"));
					prp.setInt(1, prm.getVAL_PRM_ID());
					prp.setInt(2, prm.getVAL_NT_DOC());
					prp.setString(3, IF_LIST_FIELD_ID.getText());
					prp.execute();
					prp.close();
					setStatus(true);
					onclose();
				}
			} else if (prm.getPRM_TYPE() == 2) {
				// Поле
				
			} else if (prm.getPRM_TYPE() == 3) {
				// Дата
				
			} else if (prm.getPRM_TYPE() == 4) {
				// Расширенное поле
				
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				DBUtil.LOG_ERROR(e1);
			}
			DBUtil.LOG_ERROR(e);
		}
	}

	V_NT_DOC_PRM_EDIT prm;
	Connection conn;

	public void setConn(Connection conn, V_NT_DOC_PRM_EDIT prm) {
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
				PreparedStatement prp = conn.prepareStatement(prm.getPRM_SQL() + " where code = ?");
				prp.setInt(1,Integer.valueOf(prm.getVAL_NT_VALUE()));
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					IF_LIST_FIELD_ID.setText(String.valueOf(rs.getInt("code")));
					IF_LIST_FIELD_NAME.setText(rs.getString("name"));
				}
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
