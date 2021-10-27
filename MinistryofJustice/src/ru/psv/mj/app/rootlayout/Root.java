package ru.psv.mj.app.rootlayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.app.model.Connect;
import ru.psv.mj.msg.Msg;
import ru.psv.mj.utils.DbUtil;

/**
 * Класс инициализации пунктов меню
 * 
 * @author PSV
 *
 */
public class Root {

	public Root() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private MenuBar menubar;

	@FXML
	void Close(ActionEvent event) {
		try {
			final Alert alert = new Alert(AlertType.CONFIRMATION, "Закрыть программу?", ButtonType.YES, ButtonType.NO);
			if (Msg.setDefaultButton(alert, ButtonType.NO).showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
				Main main = new Main();
				main.Close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	
	/**
	 * Управление проектами
	 */
	@FXML
	void PmEmp(ActionEvent event) {
		try {
			Main.PmEmp();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	//--
	@FXML
	void InBoxDocs(ActionEvent event) {
		try {
			Main.PmInBoxDocs();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
	// --------------------------------------

	/**
	 * Выход
	 * 
	 * @param event
	 */
	@FXML
	void handleExit(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	void Nt_Temp(ActionEvent event) {
		try {
			Main.Nt_Temp();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void grp_acces(ActionEvent event) {
		try {
			Main.grp_acces();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Лог программы
	 * 
	 * @param event
	 */
	@FXML
	void settings(ActionEvent event) {
		try {
			Main.settings();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void Nt_Doc(ActionEvent event) {
		try {
			Main.Nt_Doc();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Лог программы
	 * 
	 * @param event
	 */
	@FXML
	void doc1c(ActionEvent event) {
		try {
			Main.doc1c();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void NtClients(ActionEvent event) {
		try {
			Main.NtClients();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Лог программы
	 * 
	 * @param event
	 */
	@FXML
	void prglog(ActionEvent event) {
		try {
			Main.prglog();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void reports(ActionEvent event) {
		try {
			Main.reports();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Проект
	 * 
	 * @param event
	 */
	@FXML
	void prj(ActionEvent event) {
		try {
			Main.prj();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отделения
	 * 
	 * @param event
	 */
	@FXML
	void OTD(ActionEvent event) {
		try {
			Main.OTD();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void COURTS(ActionEvent event) {
		try {
			Main.COURTS();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * загс
	 * 
	 * @param event
	 */
	@FXML
	void ZAGS(ActionEvent event) {
		try {
			Main.ZAGS();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void NOTARY(ActionEvent event) {
		try {
			Main.NOTARY();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Доступ по действиям
	 * 
	 * @param event
	 */
	@FXML
	void access_3(ActionEvent event) {
		try {
			Main.Access3();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	/**
	 * Доступ к пунктам меню
	 * 
	 * @param event
	 */
	@FXML
	void access_menuitems(ActionEvent event) {
		try {
			Main.Admin_Menu();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	@FXML
	void UPD_NAT(ActionEvent event) {
		try {
			Main.UPD_NAT();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	/**
	 * Перемена имени
	 * 
	 * @param event
	 */
	@FXML
	void UPDATE_NAME(ActionEvent event) {
		try {
			Main.UPDATE_NAME();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	@FXML
	void au_setup(ActionEvent event) {
		try {
			Main.au_setup();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	/**
	 * Усыновление (удочерение)
	 * 
	 * @param event
	 */
	@FXML
	void ADOPTOIN(ActionEvent event) {
		try {
			Main.ADOPTOIN();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	/**
	 * Восстановление абхазской фамилии
	 * 
	 * @param event
	 */
	@FXML
	void UPDATE_ABH_NAME(ActionEvent event) {
		try {
			Main.UPDATE_ABH_NAME();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}

	}

	/**
	 * Список граждан
	 * 
	 * @param event
	 */
	@FXML
	void CUSLIST(ActionEvent event) {
		try {
			Main.CUSLIST();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Рождение
	 * 
	 * @param event
	 */
	@FXML
	void BIRTH_ACT(ActionEvent event) {
		try {
			Main.BIRTH_ACT();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Аудит
	 * 
	 * @param event
	 */
	@FXML
	void auview(ActionEvent event) {
		try {
			Main.AUVIEW();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Пользователи
	 * 
	 * @param event
	 */
	@FXML
	void usradmin(ActionEvent event) {
		try {
			Main.Users();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Проверка прав доступа к меню
	 * 
	 * @param FORM_NAME
	 * @param CUSRLOGNAME
	 * @return
	 */
	public Long chk_menu(Long FORM_NAME, String CUSRLOGNAME) {
		Long ret = 0l;
		Connection conn = DbUtil.conn;
		try {
			// SqlMap sql = new SqlMap().load("/SQL.xml");
			// String readRecordSQL = sql.getSql("acces_menu");
			PreparedStatement prepStmt = conn
					.prepareStatement("SELECT MJUsers.MNU_ACCESS(MNU_ID => ?, USR_LOGIN => ?) CNT FROM DUAL");
			prepStmt.setLong(1, FORM_NAME);
			prepStmt.setString(2, CUSRLOGNAME);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				ret = rs.getLong("CNT");
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	/**
	 * Инициализация
	 */
	@FXML
	private void initialize() {
		try {
			menubar.getMenus().forEach(menu -> {
				if (!menu.getId().equals("exit")) {
					if (chk_menu(Long.valueOf(menu.getId()), Connect.userID) == 1) {
						menu.setVisible(true);
					} else {
						menu.setVisible(false);
					}
					menu.getItems().forEach(menuItem -> {
						if (chk_menu(Long.valueOf(menuItem.getId()), Connect.userID) == 1) {
							menuItem.setVisible(true);
						} else {
							menuItem.setVisible(false);
						}
					});
				}
			});
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Заключение брака
	 * 
	 * @param event
	 */
	@FXML
	void MC_MERCER(ActionEvent event) {
		try {
			Main.MC_MERCER();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Расторжение брака
	 * 
	 * @param event
	 */
	@FXML
	void DIVORCE_CERT(ActionEvent event) {
		try {
			Main.DIVORCE_CERT();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Установление отцовстава
	 * 
	 * @param event
	 */
	@FXML
	void PATERN_CERT(ActionEvent event) {
		try {
			Main.PATERN_CERT();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Свидетельство о смерти
	 * 
	 * @param event
	 */
	@FXML
	void DEATH_CERT(ActionEvent event) {
		try {
			Main.DEATH_CERT();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}
}
