package mj.doc.cus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.table.TableFilter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.app.model.SqlMap;
import mj.dbutil.DBUtil;
import mj.doc.adoptoin.AdoptList;
import mj.doc.birthact.BirthList;
import mj.doc.death.DeathList;
import mj.doc.divorce.DivorceList;
import mj.doc.mercer.MercerList;
import mj.doc.patern.PaternList;
import mj.doc.updabhname.UpdAbhNameList;
import mj.doc.updatenat.UpdNatList;
import mj.doc.updname.UpdNameList;
import mj.msg.Msg;

/**
 * Список всех 9 документов и переход к ним, <br>
 * можно открыть документы и создать, если есть хоть один документ
 * 
 * @author Said
 *
 */
//TODO добавить возможность создать документ 

public class DocList {

	@FXML
	private TableColumn<DOCS, Integer> DocCnt;

	@FXML
	private TableView<DOCS> Docs;
	
	@FXML
	private Button OpenDocs;
	
	@FXML
	private TableColumn<DOCS, String> DocType;

	@FXML
	void OpenDocs(ActionEvent event) {
		try {
			OpenDoc();
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void RefershDocList(ActionEvent event) {
		refresh();
	}

	/**
	 * Открыть документа
	 */
	void OpenDoc() {
		try {
			if (Docs.getSelectionModel().getSelectedItem() == null) {
				Msg.Message("Выберите документ!");
			} else if (Docs.getSelectionModel().getSelectedItem().getDOCCNT() == 0) {
				Msg.Message("Нет документов!");
			} else {
				String tablename = Docs.getSelectionModel().getSelectedItem().getTABLE_NAME();
				if (tablename.equals("BRN_BIRTH_ACT")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/br_act/BirthList.fxml"));

					BirthList controller = new BirthList();
					controller.setWhere("  where (br_act_ch = " + getId() + " or br_act_f = " + getId() + " or\n"
							+ "                 br_act_m = " + getId() + ")");
					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Акты о рождении");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("PATERN_CERT")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/patern/PaternList.fxml"));

					PaternList controller = new PaternList();
					controller.setWhere("  where (pс_ch = " + getId() + " or pс_f = " + getId() + " or\n"
							+ "                 pс_m = " + getId() + ")");
					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Установление отцовства");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("MC_MERCER")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/mercer/MercerList.fxml"));

					MercerList controller = new MercerList();
					controller.setWhere("  where (MERCER_HE = " + getId() + " or MERCER_SHE = " + getId() + ")");
					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Заключение брака");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("DEATH_CERT")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/divorce/DivorceList.fxml"));

					DeathList controller = new DeathList();

					controller.setWhere("  where (DC_CUS = " + getId() + ") ");

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Свидетельство о смерти");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("DIVORCE_CERT")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/death/DeathList.fxml"));

					DivorceList controller = new DivorceList();

					controller.setWhere("  where (DIVC_HE = " + getId() + " or DIVC_SHE = " + getId() + ")");

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Расторжение брака");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("UPD_NAME")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/updname/UpdNameList.fxml"));

					UpdNameList controller = new UpdNameList();

					controller.setWhere("  where (CUSID = " + getId() + ")");

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Перемена имени");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("UPDATE_ABH_NAME")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/updabhname/UpdAbhNameList.fxml"));

					UpdAbhNameList controller = new UpdAbhNameList();

					controller.setWhere("  where (CUSID = " + getId() + ")");

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Восстановление абхазской фамилии");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("UPD_NAT")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();

					loader.setLocation(getClass().getResource("/upd_nat/UpdNatList.fxml"));

					UpdNatList controller = new UpdNatList();

					controller.setWhere("  where (CUSID = " + getId() + ")");

					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Перемена национальной принадлежности");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				} else if (tablename.equals("ADOPT")) {
					Stage stage = new Stage();
					Stage stage_ = (Stage) Docs.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/adopt/AdoptList.fxml"));
					AdoptList controller = new AdoptList();
					controller.setWhere("  where (CUSID_CH = " + getId() + " or CUSID_F = " + getId()
							+ " or CUSID_F_AD = " + getId() + " or CUSID_M = " + getId() + " or CUSID_M_AD = " + ")");
					loader.setController(controller);
					Parent root = loader.load();
					stage.setScene(new Scene(root));
					stage.getIcons().add(new Image("/icon.png"));
					stage.setTitle("Усыновление (удочерение)");
					stage.initOwner(stage_);
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent paramT) {
							controller.dbDisconnect();
						}
					});
					stage.show();
				}
			}
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	void OpenDocList(ActionEvent event) {
		try {
			OpenDoc();
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	/**
	 * Заполнение данными
	 */
	void refresh() {
		try {
			SqlMap sql = new SqlMap().load("/SQLCUS.xml");
			String readRecordSQL = sql.getSql("DocsList");
			Connection conn = DBUtil.conn;
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setInt(1, getId());
			ResultSet rs = prepStmt.executeQuery();
			ObservableList<DOCS> Docs = FXCollections.observableArrayList();
			while (rs.next()) {
				DOCS list = new DOCS();
				list.setDOCCNT(rs.getInt("DOCCNT"));
				list.setTABLE_NAME(rs.getString("TABLE_NAME"));
				list.setDOCNAME(rs.getString("DOCNAME"));
				Docs.add(list);
			}
			prepStmt.close();
			rs.close();

			this.Docs.setItems(Docs);

			Main.autoResizeColumns(this.Docs);

			TableFilter<DOCS> tableFilter = TableFilter.forTableView(this.Docs).apply();
			tableFilter.setSearchStrategy((input, target) -> {
				try {
					return target.toLowerCase().contains(input.toLowerCase());
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
			Main.logger = Logger.getLogger(getClass());
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	@FXML
	private void initialize() {
		try {
			Main.logger = Logger.getLogger(getClass());

			// Двойной щелчок по строке
			Docs.setRowFactory(tv -> {
				TableRow<DOCS> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						OpenDoc();
					}
				});
				return row;
			});

			DocType.setCellValueFactory(cellData -> cellData.getValue().DOCNAMEProperty());
			DocCnt.setCellValueFactory(cellData -> cellData.getValue().DOCCNTProperty().asObject());
			refresh();
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	private IntegerProperty Id;

	public void setId(Integer value) {
		this.Id.set(value);
	}

	public Integer getId() {
		return this.Id.get();
	}

	public DocList() {
		Main.logger = Logger.getLogger(getClass());
		this.Id = new SimpleIntegerProperty();
	}

}
