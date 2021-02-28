package mj.dbutil;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import mj.app.main.Main;
import mj.app.model.Connect;
import mj.app.model.SqlMap;
import mj.msg.Msg;

public class DBUtil {

	public DBUtil() {
		Main.logger = Logger.getLogger(getClass());
	}

	// Declare JDBC Driver
	private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";

	// Connection
	public static Connection conn = null;

	// Connect to DB
	public static void dbConnect() {
		try {
			// Setting Oracle JDBC Driver
			Class.forName(JDBC_DRIVER);
			// Establish the Oracle Connection using Connection String
			Properties props = new Properties();
			props.put("v$session.program", "MJRoot Connections");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
					props);
			conn.setAutoCommit(false);
		}catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	// Close Connection
	public static void dbDisconnect() {
		try {
			Main.logger = Logger.getLogger(DBUtil.class);
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public static boolean CheckConnect() {
		boolean ret = true;
		try {
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL);
			conn.close();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	/**
	 * Запись лога
	 */
	public static void LogToDb(Integer linenumber, String classname, String error, String METHODNAME) {
		try {
			if (linenumber != null & (classname != null && !classname.equals("")) & (error != null && !error.equals(""))
					& (METHODNAME != null && !METHODNAME.equals(""))) {
				Class.forName(JDBC_DRIVER);
				Properties props = new Properties();
				props.put("v$session.program", "LogToDb");
				Connection conn = DriverManager.getConnection(
						"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
						props);
				conn.setAutoCommit(false);
				PreparedStatement stmt = conn.prepareStatement(" declare\n" + "pragma autonomous_transaction; begin \n"
						+ " insert into logs (\n" + "linenumber, \n" + "classname, \n" + "error,METHODNAME) \n"
						+ "values\n" + "(?,?,?,?);\n" + "commit;\n" + "end;");
				stmt.setInt(1, linenumber);
				stmt.setString(2, classname);
				Clob lob = conn.createClob();
				lob.setString(1, error);
				stmt.setClob(3, lob);
				stmt.setString(4, METHODNAME);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Запись "залоченной" строки
	 */
	public static String Lock_Row(Integer Id, String TableName) {
		String ret = null;
		try {
			if (Id != null & (TableName != null && !TableName.equals(""))) {
				CallableStatement callStmt = conn.prepareCall("{ call LOCKS.LOCK_ROW_ADD(?,?,?,?)}");
				/* Ошибка */
				callStmt.registerOutParameter(4, Types.VARCHAR);
				/* первичный ключ */
				callStmt.setInt(3, Id);
				/* Пользователь */ callStmt.setString(2, Connect.userID.toUpperCase());
				callStmt.setString(1, TableName.toUpperCase());
				callStmt.execute();
				ret = callStmt.getString(4);
				callStmt.close();
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	
	public static void LOG_ERROR(Exception e) {
		//Если есть соединение или пока нет
		if(conn != null || CheckConnect()) {
			e.printStackTrace();
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e));
			LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}else {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
	 * Кто залочил
	 */
	public static String Lock_Row_View(Integer Id, String TableName) {
		String ret = null;
		String error = null;
		try {
			if (Id != null & (TableName != null && !TableName.equals(""))) {
				CallableStatement callStmt = conn.prepareCall("{ call LOCKS.LOCK_ROW_VIEW(?,?,?,?)}");
				callStmt.setString(1, TableName.toUpperCase());
				callStmt.setInt(2, Id);
				callStmt.registerOutParameter(3, Types.VARCHAR);
				callStmt.registerOutParameter(4, Types.VARCHAR);
				callStmt.execute();
				ret = callStmt.getString(3);
				error = callStmt.getString(4);
				callStmt.close();
				if (error != null)
					Msg.Message(error);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Удалить инф. о залоченной строке
	 */
	public static String Lock_Row_Delete(Integer Id, String TableName) {
		String ret = null;
		try {
			if (Id != null & (TableName != null && !TableName.equals(""))) {
				CallableStatement callStmt = conn.prepareCall("{ call LOCKS.LOCK_ROW_DEL(?,?,?,?)}");
				callStmt.setString(1, TableName.toUpperCase());
				callStmt.setString(2, Connect.userID.toUpperCase());
				callStmt.setInt(3, Id);
				callStmt.registerOutParameter(4, Types.VARCHAR);
				callStmt.execute();
				ret = callStmt.getString(4);
				callStmt.close();
				// System.out.println("~~~~~~~~~~");
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	// DB Execute Query Operation
	public static ResultSet dbExecuteQuery(String queryStmt) {
		// Declare statement, resultSet and CachedResultSet as null
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			Main.logger = Logger.getLogger(DBUtil.class);
			// Connect to DB (Establish Oracle Connection)
			if (conn == null && !conn.isClosed()) {
				dbConnect();
			}
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(queryStmt);

			stmt.close();
			resultSet.close();
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
		} finally {
			if (resultSet != null) {
				// Close resultSet
				try {
					resultSet.close();
				} catch (SQLException e) {
					Msg.Message(ExceptionUtils.getStackTrace(e));
				}
			}
			if (stmt != null) {
				// Close Statement
				try {
					stmt.close();
				} catch (SQLException e) {
					DBUtil.LOG_ERROR(e);
				}
			}
		}
		// Return CachedRowSet
		return resultSet;
	}

	public static Integer ODB_ACTION(Integer usrid, Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbAccess(?,?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setInt(2, usrid);
			callStmt.setInt(3, actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Учреждение, Загс, Нотариус, Оба...
	 * @return
	 */
	public static String ACC_LEV() {
		String ret = null;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.ACC_LEV(?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, Connect.userID.toUpperCase());
			callStmt.execute();
			ret = callStmt.getString(1);
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	public static Integer ODB_MNU(Integer usrid, Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccess(?,?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setInt(2, usrid);
			callStmt.setInt(3, actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	public static Integer ODB_MNU_GRP(Integer grpid, Integer actid) {
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccessGrp(?,?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setInt(2, grpid);
			callStmt.setInt(3, actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}
	
	public static Integer ODB_MNU2(Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccess(?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setInt(2, actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	public static Integer OdbAction(Integer actid) {
		Main.logger = Logger.getLogger(DBUtil.class);
		Integer ret = 0;
		Connection conn = DBUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbAccess(?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setInt(2, actid);
			callStmt.execute();
			ret = callStmt.getInt(1);
			callStmt.close();
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Проверка прав доступа
	 * 
	 * @param ACT_NAME
	 * @param CUSRLOGNAME
	 * @return
	 */
	public static int chk_accesss(String ACT_NAME, String CUSRLOGNAME) {
		Main.logger = Logger.getLogger(DBUtil.class);
		int ret = 0;
		Connection conn = DBUtil.conn;
		try {
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("acces_act");
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setString(1, ACT_NAME);
			prepStmt.setString(2, CUSRLOGNAME);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				ret = rs.getInt("CNT");
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Откат
	 */
	public static void Rollback() {
		try {
			try {
				conn.rollback();
			} catch (SQLException e) {
				DBUtil.LOG_ERROR(e);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Фиксация
	 */
	public static void Commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	// DB Execute Update (For Update/Insert/Delete) Operation
	public static void dbExecuteUpdate(String sqlStmt) {
		// Declare statement as null
		Statement stmt = null;
		Main.logger = Logger.getLogger(DBUtil.class);
		try {
			// Connect to DB (Establish Oracle Connection)
			if (conn == null && !conn.isClosed()) {
				dbConnect();
			}
			// Create Statement
			stmt = conn.createStatement();
			// Run executeUpdate operation with given sql statement
			stmt.executeUpdate(sqlStmt);
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		} finally {
			if (stmt != null) {
				// Close statement
				try {
					stmt.close();
				} catch (SQLException e) {
					DBUtil.LOG_ERROR(e);
				}
			}
		}
	}
}
