package mj.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	Connection conn = null;

	public void run() {
		if (conn != null) {
			try {
				PreparedStatement prp = conn.prepareStatement("select 1 from dual");
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					System.out.println(rs.getString(1));
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