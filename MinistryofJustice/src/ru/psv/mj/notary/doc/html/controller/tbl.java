package ru.psv.mj.notary.doc.html.controller;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.notary.doc.html.model.TableModel;
import ru.psv.mj.util.ConvConst;
import ru.psv.mj.utils.DbUtil;

public class tbl {

	public tbl() {
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
			DbUtil.Log_Error(e);
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
							Msg.Message("���-�� ����� ������ ���� ������ 0");
						}
					} else {
						Msg.Message("������� ���-�� �����");
					}
					///////////
				} else {
					Msg.Message("���-�� �������� ������ ���� ������ 0");
				}
			} else {
				Msg.Message("������� ���-�� ��������");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	private void initialize() {
		try {

			new ConvConst().OnlyNumber(columns);
			new ConvConst().OnlyNumber(rows);

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
