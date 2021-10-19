package mj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.TimerTask;
import mj.app.model.Connect;

public class ScheduledTask_ extends TimerTask {
	Connection conn = null;
	String ClassName = null;

	public void run() {
		String sessionid = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		if (conn != null) {
			try {
				PreparedStatement prp = conn.prepareStatement("select sys_context('USERENV', 'SESSIONID') from dual");
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					sessionid = rs.getString(1);
					System.out.println("<" + rs.getString(1) + ">" + "<SelectFromDual><" + dtf.format(now) + ">");
				}
				rs.close();
				prp.close();
			} catch (Exception e) {
				System.out.println("ERROR<" + sessionid + ">");
				this.cancel();
			}
		} else {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				Properties props = new Properties();
				props.put("v$session.program",getClass().getName());
				Connection conn = DriverManager.getConnection(
						"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
						props);
				conn.setAutoCommit(false);
				this.conn = conn;
				System.out.println("ERROR_NEW_SESS");
			} catch (Exception e) {
				
			}
			// this.cancel();
		}
	}

	void setConn(Connection conn, String ClassName) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.ClassName = ClassName;
	}
}