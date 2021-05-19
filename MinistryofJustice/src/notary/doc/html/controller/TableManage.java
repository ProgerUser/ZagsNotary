package notary.doc.html.controller;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import mj.util.ConvConst;
import notary.doc.html.model.TableModel;

public class TableManage {

	public TableManage() {
		Main.logger = Logger.getLogger(getClass());
	}

	void onclose() {
		Stage stage = (Stage) columns.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	private TableModel tbl = null;

	public TableModel getTbl() {
		return tbl;
	}

	@FXML
	private TextField columns;

	@FXML
	private TextField rows;

	@FXML
	void cencel(ActionEvent event) {
		try {
			onclose();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	Integer column;
	Integer row;

	@FXML
	void ok(ActionEvent event) {
		try {
			if (!columns.getText().equals("")) {
				column = Integer.valueOf(columns.getText());
				if (column > 0) {
					///////////////////
					if (!rows.getText().equals("")) {
						row = Integer.valueOf(rows.getText());
						if (row > 0) {
							tbl = new TableModel();
							tbl.setColumnCnt(columns.getText());
							tbl.setRowCnt(rows.getText());
							onclose();
						} else {
							Msg.Message("Кол-во строк должно быть больше 0");
						}
					} else {
						Msg.Message("Введите кол-во строк");
					}
					///////////
				} else {
					Msg.Message("Кол-во столбцов должно быть больше 0");
				}
			} else {
				Msg.Message("Введите кол-во столбцов");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	private void initialize() {
		try {

			new ConvConst().OnlyNumber(columns);
			new ConvConst().OnlyNumber(rows);

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
