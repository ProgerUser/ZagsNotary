package mj.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	Connection conn = null;

	public void run() {
		String sessionid = null;
		if (conn != null) {
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				PreparedStatement prp = conn.prepareStatement("select sys_context('USERENV', 'SESSIONID') from dual");
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					sessionid = rs.getString(1);
					System.out.println("<" + rs.getString(1) + ">" + "<SelectFromDual><" + dtf.format(now) + ">");
				}
				rs.close();
				prp.close();
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("ERROR<" + sessionid + ">");
				this.cancel();
			}
		} else {
			this.cancel();
		}
	}

	void setConn(Connection conn) {
		this.conn = conn;
	}
}