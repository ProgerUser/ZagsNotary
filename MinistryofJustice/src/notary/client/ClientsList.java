package notary.client;

import org.apache.log4j.Logger;

import com.jyloo.syntheticafx.XTableColumn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class ClientsList {

	public ClientsList() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private XTableColumn<?, ?> CLI_ID;

	@FXML
	private XTableColumn<?, ?> CLI_OPER;

	@FXML
	private XTableColumn<?, ?> CR_DATE;

	@FXML
	private XTableColumn<?, ?> CR_TIME;

	@FXML
	private XTableColumn<?, ?> CLI_NAME;

	@FXML
	void Add(ActionEvent event) {

	}

	@FXML
	void Delete(ActionEvent event) {

	}

	@FXML
	void Edit(ActionEvent event) {

	}

	@FXML
	void Refresh(ActionEvent event) {

	}

	@FXML
	private void initialize() {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
