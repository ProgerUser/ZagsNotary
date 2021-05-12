package mj.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	Connection conn = null;

	public void run() {
		if (conn != null) {
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				PreparedStatement prp = conn.prepareStatement("select 1 from dual");
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					System.out.println("<SelectFromDual><" + dtf.format(now) + ">");
				}
				rs.close();
				prp.close();
			} catch (Exception e) {
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