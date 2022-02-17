package ru.psv.mj.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.TimerTask;

public class ScheduledTask2 extends TimerTask {
	Connection conn = null;
	String ClassName = null;

	public void run() {
		if (conn != null) {
			try {
				PreparedStatement prp = conn.prepareStatement(
						"declare\n"
						+ "  pragma autonomous_transaction;\n"
						+ "  sessid_ number := sys_context('USERENV', 'SESSIONID');\n"
						+ "  sescnt_ number := 0;\n"
						+ "begin\n"
						+ "\n"
						+ "  select count(*)\n"
						+ "    into sescnt_\n"
						+ "    from cli_db_con\n"
						+ "   where cli_db_con.usrlogname = user\n"
						+ "     and cli_db_con.sessid = sessid_;\n"
						+ "\n"
						+ "  if sescnt_ > 0 then\n"
						+ "    update cli_db_con\n"
						+ "       set lsttm = sysdate\n"
						+ "     where cli_db_con.usrlogname = user\n"
						+ "       and cli_db_con.sessid = sessid_;\n"
						+ "  else\n"
						+ "    insert into cli_db_con\n"
						+ "      (usrlogname, sessid, lsttm)\n"
						+ "    values\n"
						+ "      (user, sys_context('USERENV', 'SESSIONID'), sysdate);\n"
						+ "  end if;\n"
						+ "  \n"
						+ "  commit;\n"
						+ "  \n"
						+ "end;");
				System.out.println("Exec="+prp.executeUpdate());
				prp.close();
			} catch (SQLException e) {
				e.printStackTrace();
				this.cancel();
			}
		}
	}

	void setConn(Connection conn, String ClassName) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.ClassName = ClassName;
	}
}