package ru.psv.mj.update.root.dbutil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import oracle.jdbc.pool.OracleDataSource;
import ru.psv.mj.update.root.Main;
import ru.psv.mj.update.root.alert.Msg;
import ru.psv.mj.utils.DbUtil;

public class DBUtil {
	@SuppressWarnings("unused")
	private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	public static Connection conn = null;
	public static String DB;

	public static void dbConnect() {
		try {

			System.setProperty("oracle.net.tns_admin", System.getenv("MJ_PATH") + "OraCli/network/admin");
			OracleDataSource ods = new OracleDataSource();
			ods.setTNSEntryName("mj_orcl");
			ods.setDriverType("thin");
			ods.setUser("UPDATES");
			ods.setPassword("upd_prj");
			ods.setLoginTimeout(2);
			Properties cp = new Properties();
			cp.setProperty("SetBigStringTryClob", "true");
			cp.put("v$session.program", DbUtil.class.getName());
			ods.setConnectionProperties(cp);
			conn = ods.getConnection();
//
//			Class.forName(JDBC_DRIVER);
//			Main.logger = Logger.getLogger(DBUtil.class);
//			Properties props = new Properties();
//			props.put("v$session.program", "MJUpdate");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:UPDATES/upd_prj@" + DB, props);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, className, ExceptionUtils.getStackTrace(e), methodName);
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error("error=" + ExceptionUtils.getStackTrace(e) + ";LineNumber = " + lineNumber + ";className="
					+ className + ";methodName=" + methodName);
		}
	}

	public static void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(DBUtil.class);
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	public static void LogToDb(Integer linenumber, String classname, String error, String METHODNAME) {
		try {
			if (linenumber != null & !classname.equals("") & !error.equals("") & !METHODNAME.equals("")) {
				PreparedStatement stmt = conn.prepareStatement(
						" declare\npragma autonomous_transaction; begin \n insert into logs (\nlinenumber, \nclassname, \nerror,METHODNAME) \nvalues\n(?,?,?,?);\ncommit;\nend;");
				stmt.setInt(1, linenumber);
				stmt.setString(2, classname);
				stmt.setString(3, error);
				stmt.setString(4, METHODNAME);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	public static ResultSet dbExecuteQuery(String queryStmt) {
		ResultSet resultSet;
		block21: {
			Statement stmt = null;
			resultSet = null;
			try {
				try {
					Main.logger = Logger.getLogger(DBUtil.class);
					if (conn == null && !conn.isClosed()) {
						DBUtil.dbConnect();
					}
					stmt = conn.createStatement();
					resultSet = stmt.executeQuery(queryStmt);
				} catch (Exception e) {
					Msg.Message(ExceptionUtils.getStackTrace(e));
					if (resultSet != null) {
						try {
							resultSet.close();
						} catch (SQLException e2) {
							Msg.Message(ExceptionUtils.getStackTrace(e2));
						}
					}
					if (stmt == null)
						break block21;
					try {
						stmt.close();
					} catch (SQLException e3) {
						Msg.Message(ExceptionUtils.getStackTrace(e3));
						Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e3)) + "~"
								+ Thread.currentThread().getName());
						String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
						String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
						int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e3), methodName);
					}
				}
			} finally {
				if (resultSet != null) {
					try {
						resultSet.close();
					} catch (SQLException e) {
						Msg.Message(ExceptionUtils.getStackTrace(e));
					}
				}
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						Msg.Message(ExceptionUtils.getStackTrace(e));
						Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~"
								+ Thread.currentThread().getName());
						String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
						String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
						int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
					}
				}
			}
		}
		return resultSet;
	}

	public static Integer ODB_ACTION(Integer usrid, Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbAccess(?,?)}");
			callStmt.registerOutParameter(1, 4);
			callStmt.setInt(2, (int) usrid);
			callStmt.setInt(3, (int) actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
		return ret;
	}

	public static Integer ODB_MNU(Integer usrid, Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccess(?,?)}");
			callStmt.registerOutParameter(1, 4);
			callStmt.setInt(2, (int) usrid);
			callStmt.setInt(3, (int) actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
		return ret;
	}

	public static Integer ODB_MNU2(Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccess(?)}");
			callStmt.registerOutParameter(1, 4);
			callStmt.setInt(2, (int) actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
		return ret;
	}

	public static Integer OdbAction(Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbAccess(?)}");
			callStmt.registerOutParameter(1, 4);
			callStmt.setInt(2, (int) actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
		return ret;
	}

	public static void Rollback() {
		try {
			try {
				conn.rollback();
			} catch (SQLException e) {
				Msg.Message(ExceptionUtils.getStackTrace(e));
				Main.logger.error(
						String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
				String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	public static void Commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}

	public static void dbExecuteUpdate(String sqlStmt) {
		block13: {
			Statement stmt = null;
			Main.logger = Logger.getLogger(DBUtil.class);
			try {
				try {
					if (conn == null && !conn.isClosed()) {
						DBUtil.dbConnect();
					}
					stmt = conn.createStatement();
					stmt.executeUpdate(sqlStmt);
					conn.commit();
				} catch (Exception e) {
					e.printStackTrace();
					Msg.Message(ExceptionUtils.getStackTrace(e));
					Main.logger.error(
							String.valueOf(ExceptionUtils.getStackTrace(e)) + "~" + Thread.currentThread().getName());
					String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
					String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
					int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
					if (stmt == null)
						break block13;
					try {
						stmt.close();
					} catch (SQLException e2) {
						Msg.Message(ExceptionUtils.getStackTrace(e2));
						Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e2)) + "~"
								+ Thread.currentThread().getName());
						String fullClassName2 = Thread.currentThread().getStackTrace()[2].getClassName();
						String methodName2 = Thread.currentThread().getStackTrace()[2].getMethodName();
						int lineNumber2 = Thread.currentThread().getStackTrace()[2].getLineNumber();
						DBUtil.LogToDb(lineNumber2, fullClassName2, ExceptionUtils.getStackTrace(e2), methodName2);
					}
				}
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						Msg.Message(ExceptionUtils.getStackTrace(e));
						Main.logger.error(String.valueOf(ExceptionUtils.getStackTrace(e)) + "~"
								+ Thread.currentThread().getName());
						String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
						String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
						int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
					}
				}
			}
		}
	}
}
