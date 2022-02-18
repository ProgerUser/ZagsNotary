package ru.psv.mj.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	Connection conn = null;
	String ClassName = null;

	@SuppressWarnings("unused")
	public void run() {
		
//		String sessionid = null;
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
//		LocalDateTime now = LocalDateTime.now();
//		if (conn != null) {
//			try {
//				PreparedStatement prp = conn.prepareStatement("select sys_context('USERENV', 'SESSIONID') from dual");
//				ResultSet rs = prp.executeQuery();
//				if (rs.next()) {
//				    sessionid = rs.getString(1);
////					System.out.println("<SessionId=\"" + rs.getString(1) + "\">" + "<Time=\"" + dtf.format(now) + "\">"
////							+ "<Class=\"" + ClassName + "\">");
//				}
//				rs.close();
//				prp.close();
//			} catch (Exception e) {
//				try {
//					conn = DbUtil.pds.getConnection();
//					System.out.println("Reconnect!");
//				} catch (SQLException e1) {
//					System.out.println("<ErrorNewCon=\"" + e1.getMessage() + "\">" + "<Time=\"" + dtf.format(now) + "\">"
//							+ "<Class=\"" + ClassName + "\">"+ "<ClassId=\"" + ClassName + "\">");
//				}
//				System.out.println("<Error=\"" + e.getMessage() + "\">" + "<Time=\"" + dtf.format(now) + "\">"
//						+ "<Class=\"" + ClassName + "\">"+ "<ClassId=\"" + ClassName + "\">");
//				//this.cancel();
//			}
//		} else {
//			try {
//				conn = DbUtil.pds.getConnection();
//				System.out.println("Reconnect1!");
//			} catch (SQLException e) {
//				System.out.println("<ErrorNewCon1=\"" + e.getMessage() + "\">" + "<Time=\"" + dtf.format(now) + "\">"
//						+ "<Class=\"" + ClassName + "\">"+ "<ClassId=\"" + ClassName + "\">");
//			}
////			try {
////				Class.forName("oracle.jdbc.OracleDriver");
////				Properties props = new Properties();
////				props.put("v$session.program",getClass().getName());
////				Connection conn = DriverManager.getConnection(
////						"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
////						props);
////				conn.setAutoCommit(false);
////				this.conn = conn;
////				System.out.println("ERROR_NEW_SESS");
////			} catch (Exception e) {
////				
////			}
//			// this.cancel();
//		}
	}

	void setConn(Connection conn, String ClassName) throws SQLException {
		this.conn = conn;
		this.conn.setAutoCommit(false);
		this.ClassName = ClassName;
	}
}