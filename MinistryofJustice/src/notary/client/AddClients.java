package notary.client;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class AddClients {

	public AddClients() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private ScrollPane scroll;
	@FXML
	private TextField CLI_NAME;
	@FXML
	private TextField CLI_LAST_NAME;
	@FXML
	private TextField CLI_FIRST_NAME;
	@FXML
	private TextField CLI_MIDDLE_NAME;
	@FXML
	private TextField CLI_SH_NAME;
	@FXML
	private ComboBox<?> CLI_TYPE;
	@FXML
	private TextField CLI_OGRN;
	@FXML
	private DatePicker CLI_DATE_REG;
	@FXML
	private TextField CLI_INN;
	@FXML
	private TextField CLI_KPP;
	@FXML
	private TextField CLI_PLACE_BIRTH;
	@FXML
	private ComboBox<?> CLI_GENDER;
	@FXML
	private ComboBox<?> CLI_BIRTH_COUNTRY;
	@FXML
	private ComboBox<?> CLI_ADR_RAION;
	@FXML
	private ComboBox<?> CLI_ADR_NAS_PUNKT;
	@FXML
	private TextField CLI_ADR_STREET;
	@FXML
	private ComboBox<?> CLI_ADR_COUNTRY;
	@FXML
	private TextField CLI_ADR_HOME;
	@FXML
	private TextField CLI_ADR_CORP;
	@FXML
	private TextField CLI_ADR_KV;
	@FXML
	private ComboBox<?> CLI_DOC_TYPE;
	@FXML
	private TextField CLI_DOC_SERIA;
	@FXML
	private TextField CLI_DOC_NUMBER;
	@FXML
	private DatePicker CLI_DOC_START;
	@FXML
	private DatePicker CLI_DOC_END;
	@FXML
	private TextField CLI_DOC_SUBDIV;
	@FXML
	private TextField CLI_DOC_AGENCY;
	
	@FXML
	void CENCEL(ActionEvent event) {

	}

	@FXML
	void CLI_ADR_RAION(ActionEvent event) {

	}

	@FXML
	void CLI_TYPE(ActionEvent event) {

	}

	@FXML
	void OK(ActionEvent event) {

	}

	@FXML
	private void initialize() {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
