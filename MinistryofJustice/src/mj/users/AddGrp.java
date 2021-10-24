package mj.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.controlsfx.control.table.TableFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.access.grp.ODB_GROUP_USR;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class AddGrp {
	@FXML
	private TableView<ODB_GROUP_USR> ODB_GROUP_USR;
	@FXML
	private TableColumn<ODB_GROUP_USR, Long> GRP_ID;
	@FXML
	private TableColumn<ODB_GROUP_USR, String> GRP_NAME;
	@FXML
	private TableColumn<ODB_GROUP_USR, String> NAME;

	public static void autoResizeColumns(TableView<?> table) {
		// Set the right policy
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().stream().forEach((column) -> {
			// System.out.println(column.getText());
			if (column.getText().equals("")) {

			} else {
				// Minimal width = columnheader
				Text t = new Text(column.getText());
				double max = t.getLayoutBounds().getWidth();
				for (int i = 0; i < table.getItems().size(); i++) {
					// cell must not be empty
					if (column.getCellData(i) != null) {
						t = new Text(column.getCellData(i).toString());
						double calcwidth = t.getLayoutBounds().getWidth();
						// remember new max-width
						if (calcwidth > max) {
							max = calcwidth;
						}
					}
				}
				// set the new max-widht with some extra space
				column.setPrefWidth(max + 20.0d);
			}
		});
	}

	void onclose() {
		Stage stage = (Stage) ODB_GROUP_USR.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Обновить таблицу с группами
	 */
	void InitGrp() {
		try {
			PreparedStatement prepStmt = DbUtil.conn.prepareStatement("SELECT GRP_ID, GRP_NAME, NAME\r\n"
					+ "  FROM ODB_GROUP_USR J\r\n" + " WHERE NOT EXISTS (SELECT NULL\r\n"
					+ "          FROM ODB_GRP_MEMBER G\r\n" + "         WHERE G.IUSRID =\r\n"
					+ "               (SELECT USR.IUSRID FROM USR WHERE USR.CUSRLOGNAME = ?)\r\n"
					+ "           AND J.GRP_ID = G.GRP_ID)\r\n" + "");
			prepStmt.setString(1, UserLogin);
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<ODB_GROUP_USR> dlist = FXCollections.observableArrayList();
			while (rs.next()) {
				ODB_GROUP_USR list = new ODB_GROUP_USR();
				list.setGRP_ID(rs.getLong("GRP_ID"));
				list.setGRP_NAME(rs.getString("GRP_NAME"));
				list.setNAME(rs.getString("NAME"));
				dlist.add(list);
			}
			prepStmt.close();
			rs.close();

			ODB_GROUP_USR.setItems(dlist);

			TableFilter<ODB_GROUP_USR> tableFilter = TableFilter.forTableView(ODB_GROUP_USR).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
			autoResizeColumns(ODB_GROUP_USR);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private String UserLogin;

	public void SetUsr(String UserLogin) {
		this.UserLogin = UserLogin;
	}

	@FXML
	private void initialize() {
		try {
			GRP_ID.setCellValueFactory(cellData -> cellData.getValue().GRP_IDProperty().asObject());
			GRP_NAME.setCellValueFactory(cellData -> cellData.getValue().GRP_NAMEProperty());
			NAME.setCellValueFactory(cellData -> cellData.getValue().NAMEProperty());

			/**
			 * Двойной щелчок по строке
			 */
			ODB_GROUP_USR.setRowFactory(tv -> {
				TableRow<ODB_GROUP_USR> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						if (ODB_GROUP_USR.getSelectionModel().getSelectedItem() != null) {
							try {
								final Alert alert = new Alert(AlertType.CONFIRMATION, "Добавить группу "
										+ ODB_GROUP_USR.getSelectionModel().getSelectedItem().getGRP_NAME() + " ?",
										ButtonType.YES, ButtonType.NO);
								((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
										.add(new Image("/icon.png"));
								if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait()
										.orElse(ButtonType.NO) == ButtonType.YES) {
									PreparedStatement prp = DbUtil.conn.prepareStatement("declare\n"
											+ "  pragma autonomous_transaction;\n" + "  usr_id number;\n" + "begin\n"
											+ "  select usr.iusrid into usr_id from usr where usr.cusrlogname = ?;\n"
											+ "  insert into ODB_GRP_MEMBER (GRP_ID, IUSRID) values (?, usr_id);\n"
											+ "  commit;\n" + "end;\n" + "");
									prp.setString(1, UserLogin);
									prp.setLong(2, ODB_GROUP_USR.getSelectionModel().getSelectedItem().getGRP_ID());
									prp.executeUpdate();
									prp.close();
									onclose();
								}
							} catch (Exception e) {
								DbUtil.Log_Error(e);
							}
						}
					}
				});
				return row;
			});

			InitGrp();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
