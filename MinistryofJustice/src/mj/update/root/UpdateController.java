package mj.update.root;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.update.root.alert.Msg;
import mj.update.root.dbutil.DBUtil;

public class UpdateController {
	@FXML
	private ProgressIndicator PI;
	@FXML
	private VBox Box;
	private BooleanProperty Status = new SimpleBooleanProperty();
	private Connection SQLIETE;
	private Executor exec;

	@FXML
	void Yes(ActionEvent event) {
		try {
			// ----------------------------------
			PI.setVisible(true);
			Box.setDisable(true);
			Task<Object> task = new Task<Object>() {
				@Override
				public Object call() throws Exception {
					try {
						// --------------------------------------
						// kill
						killProcess();

						// папки сначала
						{
							// resultset for into loop
							PreparedStatement stmt = DBUtil.conn.prepareStatement(
									"select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
											+ "  from (select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
											+ "          from project where PRJ_NAME = 'MJ_UPDATES.exe'\r\n"
											+ "        minus\r\n"
											+ "        select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
											+ "          from project_temp)" 
											+ "where is_folder = 'Y'");
							ResultSet rs = stmt.executeQuery();
							while (rs.next()) {
								PreparedStatement fldr = DBUtil.conn
										.prepareStatement("select * from PRJ_FOLDERS where PRJ_ID = ?");
								fldr.setInt(1, rs.getInt("prj_id"));
								ResultSet rs2 = fldr.executeQuery();
								while (rs2.next()) {
									File file = new File(
											String.valueOf(System.getenv("MJ_PATH")) + rs2.getString("PATH"));
									if (file.mkdirs()) {
										Main.logger.info("Directory is created! " + rs2.getString("PATH") + "~"
												+ Thread.currentThread().getName());
									} else if (!file.mkdirs()) {
										Main.logger.error("Failed to create directory! " + rs2.getString("PATH") + "~"
												+ Thread.currentThread().getName());
									}
								}
								fldr.close();
								rs2.close();
							}
							stmt.close();
							rs.close();
						}

						// затем файлы
						{
							// resultset for into loop
							PreparedStatement stmt = DBUtil.conn.prepareStatement(
									"select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
											+ "  from (select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
											+ "          from project where PRJ_NAME = 'MJ_UPDATES.exe'\r\n"
											+ "        minus\r\n"
											+ "        select prj_id, prj_parent, BYTES, prj_name, is_folder, version\r\n"
											+ "          from project_temp)"
											+ "where is_folder = 'N'");
							ResultSet rs = stmt.executeQuery();
							while (rs.next()) {
								PreparedStatement blb = DBUtil.conn
										.prepareStatement("select * from PRJ_FLS where PRJ_ID = ?");
								blb.setInt(1, rs.getInt("prj_id"));
								ResultSet rs2 = blb.executeQuery();
								while (rs2.next()) {
									Blob blob = rs2.getBlob("BLB_FILE");
									int blobLength = (int) blob.length();
									byte[] blobAsBytes = blob.getBytes(1L, blobLength);
									blob.free();
									FileUtils.writeByteArrayToFile(
											new File(String.valueOf(System.getenv("MJ_PATH")) + rs2.getString("PATH")),
											blobAsBytes);
								}
								blb.close();
								rs2.close();
							}
							stmt.close();
							rs.close();
						}

						{
							SQLIETEConnect();
							PreparedStatement stmt = SQLIETE.prepareStatement("delete from PROJECT");
							stmt.executeUpdate();
							stmt.close();
							SQLIETE.commit();

							PreparedStatement quer = DBUtil.conn.prepareStatement(
									"select prj_id, prj_parent, BYTES, prj_name, is_folder, version from PROJECT where PRJ_NAME = 'MJ_UPDATES.exe'");
							ResultSet rs2 = quer.executeQuery();
							while (rs2.next()) {
								PreparedStatement insert = SQLIETE.prepareStatement(
										"insert into PROJECT (prj_id, prj_parent, BYTES, prj_name, is_folder, version) values (?,?,?,?,?,?)");
								if (rs2.getString("prj_id") != null) {
									insert.setInt(1, Integer.valueOf(rs2.getString("prj_id")));
								} else {
									insert.setNull(1, 4);
								}
								if (rs2.getString("prj_parent") != null) {
									insert.setInt(2, Integer.valueOf(rs2.getString("prj_parent")));
								} else {
									insert.setNull(2, 4);
								}
								if (rs2.getString("BYTES") != null) {
									insert.setInt(3, Integer.valueOf(rs2.getString("BYTES")));
								} else {
									insert.setNull(3, 4);
								}
								insert.setString(4, rs2.getString("prj_name"));
								insert.setString(5, rs2.getString("is_folder"));
								if (rs2.getString("version") != null) {
									insert.setInt(6, Integer.valueOf(rs2.getString("version")));
								} else {
									insert.setNull(6, 4);
								}
								insert.executeUpdate();
								SQLIETE.commit();
								insert.close();
							}
							rs2.close();
							quer.close();

							// disconnect
							SQLIETEDisconnect();

							// status
							setStatus(true);

							// on_close
							Platform.runLater(() -> {
								onclose();
							});
						}

					} catch (Exception e) {
						Main.logger = Logger.getLogger(this.getClass());
						ShowMes(ExceptionUtils.getStackTrace(e));
						Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~"
								+ Thread.currentThread().getName());
					}
					// ----------------------------------
					return null;
				}
			};
			task.setOnFailed(e -> ShowMes(ExceptionUtils.getStackTrace(task.getException())));
			task.setOnSucceeded(e -> {
				PI.setVisible(false);
				Box.setDisable(false);
			});
			exec.execute(task);
			// ---------------
		} catch (Exception e) {
			Main.logger = Logger.getLogger(this.getClass());
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
	}

	/**
	 * Error message in new thread
	 * 
	 * @param error
	 */
	void ShowMes(String error) {
		Platform.runLater(() -> {
			Msg.Message(error);
		});
	}

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public void killProcess() throws Exception {
		String line;
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
				"wmic Path win32_process Where \"CommandLine Like '%mj_updates.exe%'\" Call Terminate");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
		while ((line = r.readLine()) != null) {
			System.out.println(line);
		}
	}

	@FXML
	void Cencel(ActionEvent event) {
		this.onclose();
	}

	void onclose() {
		Stage stage = (Stage) this.Box.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	private void initialize() {
		exec = Executors.newCachedThreadPool((runnable) -> {
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});
	}

	private void SQLIETEConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Main.logger = Logger.getLogger(this.getClass());
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			this.SQLIETE = DriverManager.getConnection(url);
			SQLIETE.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			Main.logger = Logger.getLogger(this.getClass());
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
	}

	public void SQLIETEDisconnect() {
		try {
			Main.logger = Logger.getLogger(this.getClass());
			if (this.SQLIETE != null && !this.SQLIETE.isClosed()) {
				this.SQLIETE.close();
			}
		} catch (SQLException e) {
			Main.logger = Logger.getLogger(this.getClass());
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
		}
	}
}
