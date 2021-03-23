package notary.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.Connect;
import mj.dbutil.DBUtil;
import mj.util.ConvConst;

public class ClientsList {

	public ClientsList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private XTableView<V_NT_CLI> NT_CLIENTS;

	@FXML
	private XTableColumn<V_NT_CLI, Integer> CLI_ID;

	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_OPER;

	@FXML
	private XTableColumn<Object, LocalDate> CR_DATE;

	@FXML
	private XTableColumn<V_NT_CLI, String> CR_TIME;

	@FXML
	private XTableColumn<V_NT_CLI, String> CLI_NAME;

	@FXML
	void Add(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Stage stage_ = (Stage) NT_CLIENTS.getScene().getWindow();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/client/IUClients.fxml"));

			AddClients controller = new AddClients();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("Добавить новую запись");
			stage.initOwner(stage_);
			stage.setResizable(true);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {
					controller.dbDisconnect();
					if(controller.getStatus()) {
						Init();
					}
				}
			});
			stage.showAndWait();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Delete(ActionEvent event) {
		try {
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Edit(ActionEvent event) {
		try {
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void Refresh(ActionEvent event) {
		try {
			Init();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {
			dbConnect();
			
			CLI_ID.setCellValueFactory(cellData -> cellData.getValue().CLI_IDProperty().asObject());
			CLI_OPER.setCellValueFactory(cellData -> cellData.getValue().CLI_OPERProperty());
			CR_DATE.setCellValueFactory(cellData -> ((V_NT_CLI) cellData.getValue()).CR_DATEProperty());
			CR_TIME.setCellValueFactory(cellData -> cellData.getValue().CR_TIMEProperty());
			CLI_NAME.setCellValueFactory(cellData -> cellData.getValue().CLI_NAMEProperty());
			
			new ConvConst().TableColumnDate(CR_DATE);
			
			Init();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private Connection conn;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Properties props = new Properties();
			props.put("v$session.program", "ClientsList");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	void Init() {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from V_NT_CLI t");
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<V_NT_CLI> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				V_NT_CLI list = new V_NT_CLI();
				list.setCLI_OPER(rs.getString("CLI_OPER"));
				list.setCLI_DATE_REG((rs.getDate("CLI_DATE_REG") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DATE_REG")), formatter) : null);
				list.setCLI_ADR_STREET(rs.getString("CLI_ADR_STREET"));
				list.setCLI_DOC_TYPE(rs.getInt("CLI_DOC_TYPE"));
				list.setCLI_FIRST_NAME(rs.getString("CLI_FIRST_NAME"));
				list.setCLI_ADR_COUNTRY(rs.getInt("CLI_ADR_COUNTRY"));
				list.setCLI_ADR_RAION(rs.getInt("CLI_ADR_RAION"));
				list.setCLI_DOC_AGENCY(rs.getString("CLI_DOC_AGENCY"));
				list.setCLI_DOC_START((rs.getDate("CLI_DOC_START") != null) ? LocalDate.parse(
						new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DOC_START")), formatter) : null);
				list.setCLI_DOC_END((rs.getDate("CLI_DOC_END") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DOC_END")), formatter) : null);
				list.setCLI_PLACE_BIRTH(rs.getString("CLI_PLACE_BIRTH"));
				list.setCLI_ADR_NAS_PUNKT(rs.getInt("CLI_ADR_NAS_PUNKT"));
				list.setCLI_DOC_SERIA(rs.getString("CLI_DOC_SERIA"));
				list.setCR_DATE((rs.getDate("CR_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CR_DATE")), formatter)
						: null);
				list.setCLI_ADR_HOME(rs.getString("CLI_ADR_HOME"));
				list.setCLI_LAST_NAME(rs.getString("CLI_LAST_NAME"));
				list.setCLI_KPP(rs.getString("CLI_KPP"));
				list.setCLI_DOC_SUBDIV(rs.getString("CLI_DOC_SUBDIV"));
				list.setCR_TIME(rs.getString("CR_TIME"));
				list.setCLI_NAME(rs.getString("CLI_NAME"));
				list.setCLI_MIDDLE_NAME(rs.getString("CLI_MIDDLE_NAME"));
				list.setCLI_NOTARY(rs.getInt("CLI_NOTARY"));
				list.setCLI_ADR_CORP(rs.getString("CLI_ADR_CORP"));
				list.setCLI_DATE((rs.getDate("CLI_DATE") != null)
						? LocalDate.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_DATE")), formatter)
						: null);
				list.setCLI_BR_DATE((rs.getDate("CLI_BR_DATE") != null) ? LocalDate
						.parse(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("CLI_BR_DATE")), formatter) : null);
				list.setCLI_ADR_KV(rs.getString("CLI_ADR_KV"));
				list.setCLI_SH_NAME(rs.getString("CLI_SH_NAME"));
				list.setCLI_GENDER(rs.getInt("CLI_GENDER"));
				list.setCLI_INN(rs.getString("CLI_INN"));
				list.setCLI_TYPE(rs.getInt("CLI_TYPE"));
				list.setCLI_DOC_NUMBER(rs.getString("CLI_DOC_NUMBER"));
				list.setCLI_BIRTH_COUNTRY(rs.getInt("CLI_BIRTH_COUNTRY"));
				list.setCLI_ID(rs.getInt("CLI_ID"));
				list.setCLI_OGRN(rs.getString("CLI_OGRN"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			NT_CLIENTS.setItems(dlist);

			TableFilter<V_NT_CLI> tableFilter = TableFilter.forTableView(NT_CLIENTS).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	void onclose() {
		Stage stage = (Stage) NT_CLIENTS.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
