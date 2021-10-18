package mj.update.root;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;
import mj.update.root.alert.Msg;
import mj.update.root.dbutil.DBUtil;

public class Main {

	/**
	 * Logger
	 */
	public static Logger logger = Logger.getLogger(Main.class);
	/**
	 * SQLITE
	 */
	private Connection SQLIETE;

	
	
	boolean is_update = false;
	public boolean start() {
		try {
			// log--------------------------------------
			DOMConfigurator.configure(this.getClass().getResource("/log4j.xml"));

			// <sqlite>--------------------------------------
			{
				SQLIETEConnect();
				PreparedStatement stmt = SQLIETE.prepareStatement("select * from props where name = 'url'");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					DBUtil.DB = rs.getString("VALUE");
				}
				stmt.close();
				rs.close();
			}
			// </sqlite>--------------------------------------

			// <oracle connect>--------------------------------------
			DBUtil.dbConnect();
			// </oracle connect>--------------------------------------

			// <insert table>--------------------------------------
			{
				PreparedStatement stmt = SQLIETE.prepareStatement(
						"select prj_id, prj_parent, BYTES, prj_name, is_folder, version from project where prj_name = 'MJ_UPDATES.exe'");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					PreparedStatement tmp = DBUtil.conn.prepareStatement(
							"insert into project_temp (prj_id, prj_parent, BYTES, prj_name, is_folder, version) values (?,?,?,?,?,?)");
					if (rs.getString("prj_id") != null) {
						tmp.setInt(1, Integer.valueOf(rs.getString("prj_id")));
					} else {
						tmp.setNull(1, 4);
					}
					if (rs.getString("prj_parent") != null) {
						tmp.setInt(2, Integer.valueOf(rs.getString("prj_parent")));
					} else {
						tmp.setNull(2, 4);
					}
					if (rs.getString("BYTES") != null) {
						tmp.setInt(3, Integer.valueOf(rs.getString("BYTES")));
					} else {
						tmp.setNull(3, 4);
					}
					tmp.setString(4, rs.getString("prj_name"));
					tmp.setString(5, rs.getString("is_folder"));
					if (rs.getString("version") != null) {
						tmp.setInt(6, Integer.valueOf(rs.getString("version")));
					} else {
						tmp.setNull(6, 4);
					}
					System.out.print(rs.getString("prj_id"));
					System.out.print(";" + rs.getString("prj_parent"));
					System.out.print(";" + rs.getString("BYTES"));
					System.out.print(";" + rs.getString("prj_name"));
					System.out.print(";" + rs.getString("is_folder"));
					System.out.println(";" + rs.getString("version"));
					tmp.execute();
					tmp.close();
				}
				rs.close();
				stmt.close();
				DBUtil.conn.commit();
			}
			// </insert table>--------------------------------------

			// <setdef_upd_stat>--------------------------------------
			//boolean is_update = false;
			// </setdef_upd_stat>--------------------------------------

			// <first_compare>--------------------------------------
			{
				PreparedStatement cnt = DBUtil.conn.prepareStatement("select count(*) CNT\r\n"
						+ "  from (select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
						+ "          from project where PRJ_NAME = 'MJ_UPDATES.exe'\r\n"
						+ "        minus\r\n"
						+ "        select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
						+ "          from project_temp)");
				ResultSet rs = cnt.executeQuery();
				if (rs.next() && rs.getInt("CNT") > 0) {
					is_update = true;
					System.out.println("!f.exists()=" + rs.getInt("CNT"));
				}
				cnt.close();
				rs.close();
			}
			// </first_compare>--------------------------------------

			// <loop folder>--------------------------------------
			{
				PreparedStatement prp = DBUtil.conn.prepareStatement(
						"select level_,path,max_,prj_id,prj_parent,bytes,prj_name,is_folder,version from PRJ_FILES t where t.path = 'MJ_UPDATES.exe'");
				ResultSet rs = prp.executeQuery();
				while (rs.next()) {
					PreparedStatement stmt = null;
					File f = new File(String.valueOf(System.getenv("MJ_PATH")) + rs.getString("PATH"));
					if ((!f.exists()) || (f.length() != rs.getInt("bytes"))) {
						// out
						System.out.print("(!f.exists()) || (f.length() != rs.getInt(\"bytes\"))=");
						System.out.println(rs.getString("prj_id"));
						System.out.println("path=" + f.getAbsolutePath());
						System.out.println("bytes=" + rs.getInt("bytes"));
						System.out.println("f.length=" + f.length());
						// -------------
						stmt = SQLIETE.prepareStatement("delete from project where lower(prj_name) = ?");
						stmt.setString(1, rs.getString("prj_name").toLowerCase());
						stmt.execute();
						SQLIETE.commit();
						stmt.close();
						is_update = true;
					}
				}
				rs.close();
				prp.close();
			}
			// </loop folder>--------------------------------------

			// -------------------
			System.out.println("is_update=" + is_update);
			// -------------------

			// open if ok
			if (!is_update) {
				mj.app.main.Main main = new mj.app.main.Main();
				main.Enter();	
//				this.OpenMJ();
//				DBUtil.dbDisconnect();
//				SQLIETEDisconnect();
//				Platform.exit();
//				System.exit(0);
			}else
			// ----------------------
			// <update_form>--------------------------------------
			{
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Update.fxml"));
				UpdateController controller = new UpdateController();
				controller.setStatus(false);
				loader.setController(controller);
				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
				mj.app.main.Main.primaryStage.getIcons().add(new Image("/icon.png"));
				// scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
				mj.app.main.Main.primaryStage.setScene(scene);
				mj.app.main.Main.primaryStage.setTitle("Обновление");
				mj.app.main.Main.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent paramT) {
						if (controller.getStatus()) {
							try {
//								Main.this.OpenMJ();
								mj.app.main.Main main = new mj.app.main.Main();
								main.Enter();	
							} catch (Exception e) {
								Msg.Message(ExceptionUtils.getStackTrace(e));
								Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~"
										+ Thread.currentThread().getName());
							}
						} else {
							mj.app.main.Main main = new mj.app.main.Main();
							main.Enter();
						}
					}
				});
				mj.app.main.Main.primaryStage.show();
			}
			// <//update_form>--------------------------------------
			SQLIETEDisconnect();
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
		return is_update;
	}

	public void OpenMJ() throws Exception {
		try {
			System.out.println("cmd /c \"" + System.getenv("MJ_PATH") + "MJ.exe\"");
			Runtime.getRuntime().exec("cmd /c \"" + System.getenv("MJ_PATH") + "MJ.exe\"");
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
	}

	private void SQLIETEConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			logger = Logger.getLogger(this.getClass());
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			SQLIETE = DriverManager.getConnection(url);
			SQLIETE.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
	}

	public void SQLIETEDisconnect() {
		try {
			logger = Logger.getLogger(this.getClass());
			if (SQLIETE != null && !SQLIETE.isClosed()) {
				SQLIETE.close();
			}
		} catch (SQLException e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
	}
}
