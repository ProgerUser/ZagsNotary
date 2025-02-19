package ru.psv.mj.utils;

import java.io.IOException;
import java.io.InputStream;
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
import java.util.Timer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import oracle.jdbc.pool.OracleDataSource;
//import oracle.ucp.jdbc.PoolDataSource;
import ru.psv.mj.app.main.Main;
import ru.psv.mj.app.model.Connect;
import ru.psv.mj.app.model.SqlMap;
import ru.psv.mj.msg.Msg;

/**
 * ����� ��� ������ � �� <br>
 * ������...
 * 
 * @author Said
 *
 */
public class DbUtil {

	public DbUtil() {
		Main.logger = Logger.getLogger(getClass());
	}

	// Declare JDBC Driver
	// private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";

	// Connection
	public static Connection conn = null;

//	// Connect to DB
//	public static void Db_Connect() {
//		try {
////			System.setProperty("oracle.net.tns_admin", System.getenv("MJ_PATH") + "oracle/NETWORK/ADMIN");
////			OracleDataSource ods = new OracleDataSource();
////			ods.setTNSEntryName("orcl");
////			ods.setDriverType("thin");
////			ods.setUser("scott");
////			ods.setPassword("tiger");
////			ods.setLoginTimeout(2);
////			Properties cp = new Properties();
////			cp.setProperty("SetBigStringTryClob", "true");
////			ods.setConnectionProperties(cp);
//
//			// Setting Oracle JDBC Driver
//			Class.forName(JDBC_DRIVER);
//			// Establish the Oracle Connection using Connection String
//			Properties props = new Properties();
//			props.put("v$session.program", DbUtil.class.getName());
//			conn = DriverManager.getConnection(
//					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
//					props);
//			conn.setAutoCommit(false);
//			DbUtil.Run_Process(conn, DbUtil.class.getName());
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}

//	// Connect to DB
//	@SuppressWarnings("resource")
//	public static void Db_Connect1() {
//		try {
//
//			Properties props = new Properties();
//			props.setProperty("dataSourceClassName", "oracle.jdbc.pool.OracleDataSource");
//			props.setProperty("dataSource.user", Connect.userID);
//			props.setProperty("dataSource.password", Connect.userPassword);
//			// props.setProperty("keepaliveTime", "30000");
//			props.setProperty("dataSource.url", "jdbc:oracle:thin:@" + Connect.connectionURL);
//			// props.put("data-source-properties.v$session.program",
//			// DbUtil.class.getName());
//			props.put("dataSource.logWriter", new PrintWriter("D:/log.txt"));
//
//			HikariConfig config = new HikariConfig(props);
//			HikariDataSource ds = new HikariDataSource(config);
//
//			conn = ds.getConnection();
//
//			conn.setAutoCommit(false);
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}

	/**
	 * ��� �����������
	 */
	public static OracleDataSource ods;
	// Connect to DB
//	public static void Db_Connect() {
//		try {
//			//Use connection descriptors
//			String DB_URL="jdbc:oracle:thin:@  (DESCRIPTION =(ENABLE=broken)\n"
//					+ "    (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1522))\n"
//					+ "    (CONNECT_DATA =\n"
//					+ "      (SID = orcl)\n"
//					+ "    )\n"
//					+ "    )\n"
//					+ "  )"; 
//			// Get the PoolDataSource for UCP
//			PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
//			//Set the connection factory first before all other properties
//			pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
//			pds.setURL(DB_URL);
//			pds.setUser(Connect.userID);
//			pds.setPassword(Connect.userPassword);
//			//Set the pool level properties
//			pds.setConnectionPoolName("JDBC_UCP_POOL");
//			//pds.setInitialPoolSize(5);
//			pds.setMinPoolSize(5);
//			pds.setMaxPoolSize(100);
//			Connection conn = pds.getConnection();
//			// System.out.println(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT);
//			if (ods == null) {
//				System.setProperty("oracle.net.tns_admin", System.getenv("MJ_PATH") + "OraCli/network/admin");
//				ods = new OracleDataSource();
//				ods.setTNSEntryName("mj_orcl");
//				ods.setDriverType("thin");
//				ods.setUser(Connect.userID);
//				ods.setPassword(Connect.userPassword);
//				ods.setLoginTimeout(2);
//
//				Properties cp = new Properties();
//				cp.setProperty("SetBigStringTryClob", "true");
//				cp.put("Oracle.net.CONNECT_TIMEOUT", 0);
//				cp.put("Oracle.net.READ_TIMEOUT", 0);
//				cp.put("Oracle.jdbc.ReadTimeout", 0);
//				cp.put("Oracle.net.tcpKeepAlive", "true");
//				cp.put("v$session.program", DbUtil.class.getName());
//
//				ods.setConnectionProperties(cp);
//			}
//			conn = ods.getConnection();
//
//			conn.setAutoCommit(false);
//
//			Run_Proc(conn, DbUtil.class.getName());
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}
	//public static PoolDataSource pds;

	// Use connection descriptors
	final static String DB_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=localhost)(PORT=1522)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=orcl)))";
//	final static String DB_URL ="(DESCRIPTION=\n"
//	+ "(CONNECT_TIMEOUT=15)(RETRY_COUNT=20) (RETRY_DELAY=3)\n"
//	+ "(ADDRESS_LIST =\n"
//	+ "(LOAD_BALANCE=ON)\n"
//	+ "(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1522)))\n"
//	+ "(CONNECT_DATA=(SERVICE_NAME=orcl)))";

//	public static void Db_Connect() {
//		try {
//			String cln = "";
//			if (pds == null) {
//				pds = PoolDataSourceFactory.getPoolDataSource();
//				// Set the connection factory first before all other properties
//				pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
//				pds.setURL(DB_URL);
//				pds.setUser(Connect.userID);
//				pds.setPassword(Connect.userPassword);
//				// Set the pool level properties
//				pds.setConnectionPoolName("JDBC_UCP_POOL");
//				// pds.setInitialPoolSize(5);
//				pds.setMinPoolSize(5);
//				pds.setMaxPoolSize(1000000);
//				pds.setValidateConnectionOnBorrow(true);
//				pds.setSQLForValidateConnection("select user from dual");
//				conn = pds.getConnection();
//			} else {
//				conn = pds.getConnection();
//			}
//			System.out.println(conn.isValid(3));
//			try {
//				if (DbUtil.class.getName() != null) {
//					if (DbUtil.class.getName().length() < 30) {
//						cln = DbUtil.class.getName();
//					} else if (DbUtil.class.getName().length() >= 30) {
//						cln = DbUtil.class.getName().substring(0, 29);
//					}
//				}
//				CallableStatement cstmt = conn.prepareCall("{call dbms_application_info.set_module(?,?)}");
//				cstmt.setString(1, cln);
//				cstmt.setString(2, cln);
//				cstmt.execute();
//				cstmt.close();
//			} catch (Exception e) {
//
//			}
//
////			if (sessions == null) {
////				sessions = new HashMap<Long, String>();
////				sessions.put(GetSession(conn), cln);
////			} else {
////				sessions.put(GetSession(conn), cln);
////			}
//			Run_Proc(conn, DbUtil.class.getName());
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//	}

	public static void Db_Connect() {
		try {
			String cln = "";
			// Setting Oracle JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL);
			conn.setAutoCommit(false);
			try {
				if (DbUtil.class.getName() != null) {
					if (DbUtil.class.getName().length() < 30) {
						cln = DbUtil.class.getName();
					} else if (DbUtil.class.getName().length() >= 30) {
						cln = DbUtil.class.getName().substring(0, 29);
					}
				}
				CallableStatement cstmt = conn.prepareCall("{call dbms_application_info.set_module(?,?)}");
				cstmt.setString(1, cln);
				cstmt.setString(2, cln);
				cstmt.execute();
				cstmt.close();
			} catch (Exception e) {

			}
			Run_Proc(conn, DbUtil.class.getName());
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * �������� ������...
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	static Long GetSession(Connection conn) throws SQLException {
		Long ret = 0l;
		PreparedStatement prp = conn.prepareStatement("select sys_context('USERENV', 'SESSIONID') from dual");
		ResultSet rs = prp.executeQuery();
		if (rs.next()) {
			ret = rs.getLong(1);
		}
		return ret;
	}

//	public static Connection GetConnect(String ClassName) {
//		Connection conn = null;
//		try {
//			if (ods == null) {
//				System.setProperty("oracle.net.tns_admin", System.getenv("MJ_PATH") + "OraCli/network/admin");
//				ods = new OracleDataSource();
//				ods.setTNSEntryName("mj_orcl");
//				ods.setDriverType("thin");
//				ods.setUser(Connect.userID);
//				ods.setPassword(Connect.userPassword);
//				ods.setLoginTimeout(2);
//
//				Properties cp = new Properties();
//				cp.setProperty("SetBigStringTryClob", "true");
//				cp.put("Oracle.net.CONNECT_TIMEOUT", 0);
//				cp.put("Oracle.net.READ_TIMEOUT", 0);
//				cp.put("Oracle.jdbc.ReadTimeout", 0);
//				cp.put("Oracle.net.tcpKeepAlive", "true");
//
//				if (ClassName != null) {
//					if (ClassName.length() < 30) {
//						cp.put("v$session.program", ClassName);
//					} else if (ClassName.length() > 30) {
//						cp.put("v$session.program", ClassName.substring(0, 29));
//					}
//				}
//
//				ods.setConnectionProperties(cp);
//			}
//
//			conn = ods.getConnection();
//			conn.setAutoCommit(false);
//
//			Run_Proc(conn, ClassName);
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//		return conn;
//	}

	public static Connection GetConnect(String ClassName) {
		Connection conn = null;
		String cln = "~~~~~~";
		try {
			// Setting Oracle JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL);
			conn.setAutoCommit(false);
			// ----------------
			// Sess name
			try {
				if (ClassName != null) {
					if (ClassName.length() < 30) {
						cln = ClassName;
					} else if (ClassName.length() >= 30) {
						cln = ClassName.substring(0, 29);
					}
				}
				CallableStatement cstmt = conn.prepareCall("{call dbms_application_info.set_module(?,?)}");
				cstmt.setString(1, cln);
				cstmt.setString(2, cln);
				cstmt.execute();
				cstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// --------------------
			Run_Proc(conn, ClassName);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return conn;
	}
	
//	public static Connection GetConnect(String ClassName) {
//		Connection conn = null;
//		String cln = "~~~~~~";
//		System.out.println("ClassName="+ClassName);
//		try {
//			//Conn
//			if (pds != null) {
//				conn = pds.getConnection();
//			} else {
//				pds = PoolDataSourceFactory.getPoolDataSource();
//				// Set the connection factory first before all other properties
//				pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
//				pds.setURL(DB_URL);
//				pds.setUser(Connect.userID);
//				pds.setPassword(Connect.userPassword);
//				// Set the pool level properties
//				pds.setConnectionPoolName("JDBC_UCP_POOL");
//				// pds.setInitialPoolSize(5);
//				//pds.setMinPoolSize(5);
//				//pds.setMaxPoolSize(1000000);
//				pds.setValidateConnectionOnBorrow(true);
//				pds.setSQLForValidateConnection("select user from dual");
//			}
//			//----------------
//			//Sess name
//			try {
//				if (ClassName != null) {
//					if (ClassName.length() < 30) {
//						cln = ClassName;
//					} else if (ClassName.length() >= 30) {
//						cln = ClassName.substring(0, 29);
//					}
//				}
//				CallableStatement cstmt = conn.prepareCall("{call dbms_application_info.set_module(?,?)}");
//				cstmt.setString(1, cln);
//				cstmt.setString(2, cln);
//				cstmt.execute();
//				cstmt.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//--------------------
//			conn.setAutoCommit(false);
//			Run_Proc(conn, ClassName);
//		} catch (Exception e) {
//			DbUtil.Log_Error(e);
//		}
//		return conn;
//	}

	// Close Connection
	public static void Db_Disconnect() {
		try {
			Main.logger = Logger.getLogger(DbUtil.class);
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	public static String Sql_From_Prop(String path, String prpname) {
		String ret = null;
		try {
			InputStream is = DbUtil.class.getResourceAsStream(path);
			Properties props = new Properties();
			props.load(is);
			ret = props.getProperty(prpname);
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	public static boolean Check_Connect() {
		boolean ret = true;
		try {
//			Class.forName(JDBC_DRIVER);
			Connection conn;
//					DriverManager.getConnection(
//					"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL);
			System.setProperty("oracle.net.tns_admin", System.getenv("MJ_PATH") + "OraCli/network/admin");
			OracleDataSource ods = new OracleDataSource();
			ods.setTNSEntryName("mj_orcl");
			ods.setDriverType("thin");
			ods.setUser(Connect.userID);
			ods.setPassword(Connect.userPassword);
			ods.setLoginTimeout(2);
			Properties cp = new Properties();
			cp.setProperty("SetBigStringTryClob", "true");
			cp.put("v$session.program", DbUtil.class.getName());
			ods.setConnectionProperties(cp);
			conn = ods.getConnection();
			conn.close();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	// public static Map<Long, String> sessions;

	public static void Run_Proc(Connection conn, String ClassName) {
		try {
			Timer time = new Timer(); // Instantiate Timer Object
			ScheduledTask2 st = new ScheduledTask2(); // Instantiate SheduledTask class
			st.setConn(conn, ClassName);
			time.schedule(st, 0, 60000); // Create task repeating every 1 min = 60 000
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ������ ����
	 */
	public static void Log_To_Db(Long linenumber, String classname, String error, String METHODNAME) {
		try {
			if (linenumber != null & (classname != null && !classname.equals("")) & (error != null && !error.equals(""))
					& (METHODNAME != null && !METHODNAME.equals(""))) {
//				Class.forName(JDBC_DRIVER);

//				Properties props = new Properties();
//				props.put("v$session.program", DbUtil.class.getName());
				Connection conn;
				System.setProperty("oracle.net.tns_admin", System.getenv("MJ_PATH") + "OraCli/network/admin");
				OracleDataSource ods = new OracleDataSource();
				ods.setTNSEntryName("mj_orcl");
				ods.setDriverType("thin");
				ods.setUser(Connect.userID);
				ods.setPassword(Connect.userPassword);
				ods.setLoginTimeout(2);
				Properties cp = new Properties();
				cp.setProperty("SetBigStringTryClob", "true");
				cp.put("v$session.program", DbUtil.class.getName());
				ods.setConnectionProperties(cp);
				conn = ods.getConnection();
//						DriverManager.getConnection(
//						"jdbc:oracle:thin:" + Connect.userID + "/" + Connect.userPassword + "@" + Connect.connectionURL,
//						props);
				conn.setAutoCommit(false);
				PreparedStatement stmt = conn.prepareStatement(" declare\n" + "pragma autonomous_transaction; begin \n"
						+ " insert into logs (\n" + "linenumber, \n" + "classname, \n" + "error,METHODNAME) \n"
						+ "values\n" + "(?,?,?,?);\n" + "commit;\n" + "end;");
				stmt.setLong(1, linenumber);
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
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ������ "����������" ������
	 */
	public static String Lock_Row(Long Id, String TableName, Connection conn) {
		String ret = null;
		try {
			if (Id != null & (TableName != null && !TableName.equals(""))) {
				CallableStatement callStmt = conn.prepareCall("{ call LOCKS.LOCK_ROW_ADD(?,?,?,?)}");
				/* ������ */
				callStmt.registerOutParameter(4, Types.VARCHAR);
				/* ��������� ���� */
				callStmt.setLong(3, Id);
				/* ������������ */ callStmt.setString(2, Connect.userID.toUpperCase());
				callStmt.setString(1, TableName.toUpperCase());
				callStmt.execute();
				ret = callStmt.getString(4);
				callStmt.close();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	public static void Log_Error(Exception e) {
		// ���� ���� ���������� ��� ���� ���
		if (conn != null || Check_Connect()) {
			e.printStackTrace();
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			Long lineNumber = (long) Thread.currentThread().getStackTrace()[2].getLineNumber();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e));
			if (!Connect.userID.toLowerCase().equals("xxi")) {
				Log_To_Db(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
			}
		} else {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * ��� �������
	 */
	public static String Lock_Row_View(Long Id, String TableName) {
		String ret = null;
		String error = null;
		try {
			if (Id != null & (TableName != null && !TableName.equals(""))) {
				CallableStatement callStmt = conn.prepareCall("{ call LOCKS.LOCK_ROW_VIEW(?,?,?,?)}");
				callStmt.setString(1, TableName.toUpperCase());
				callStmt.setLong(2, Id);
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
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	/**
	 * ������� ���. � ���������� ������
	 */
	public static String Lock_Row_Delete(Long Id, String TableName, Connection conn) {
		String ret = null;
		try {
			if (Id != null & (TableName != null && !TableName.equals(""))) {
				CallableStatement callStmt = conn.prepareCall("{ call LOCKS.LOCK_ROW_DEL(?,?,?,?)}");
				callStmt.setString(1, TableName.toUpperCase());
				callStmt.setString(2, Connect.userID.toUpperCase());
				callStmt.setLong(3, Id);
				callStmt.registerOutParameter(4, Types.VARCHAR);
				callStmt.execute();
				ret = callStmt.getString(4);
				callStmt.close();
				// System.out.println("~~~~~~~~~~");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	// DB Execute Query Operation
	public static ResultSet Db_Execute_Query(String queryStmt) {
		// Declare statement, resultSet and CachedResultSet as null
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			Main.logger = Logger.getLogger(DbUtil.class);
			// Connect to DB (Establish Oracle Connection)
			if (conn == null && !conn.isClosed()) {
				Db_Connect();
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
					DbUtil.Log_Error(e);
				}
			}
		}
		// Return CachedRowSet
		return resultSet;
	}

	public static Long OdbActionByLogin(Long usrid, Long actid) {
		Long ret = 0l;
		try {
			try {
				CallableStatement callStmt = DbUtil.conn.prepareCall("{ ? = call MJUsers.OdbAccess(?,?)}");
				callStmt.registerOutParameter(1, Types.INTEGER);
				callStmt.setLong(2, usrid);
				callStmt.setLong(3, actid);
				callStmt.execute();
				ret = callStmt.getLong(1);
				callStmt.close();
			} catch (Exception e) {
				try {
					Db_Disconnect();
					Db_Connect();
					CallableStatement callStmt = DbUtil.conn.prepareCall("{ ? = call MJUsers.OdbAccess(?,?)}");
					callStmt.registerOutParameter(1, Types.INTEGER);
					callStmt.setLong(2, usrid);
					callStmt.setLong(3, actid);
					callStmt.execute();
					ret = callStmt.getLong(1);
					callStmt.close();
				} catch (Exception e1) {
					DbUtil.Log_Error(e1);
				}
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	/**
	 * ����������, ����, ��������, ���...
	 * 
	 * @return
	 */
	public static String Access_Level() {
		String ret = null;
		Connection conn = DbUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.ACC_LEV(?)}");
			callStmt.registerOutParameter(1, Types.VARCHAR);
			callStmt.setString(2, Connect.userID.toUpperCase());
			callStmt.execute();
			ret = callStmt.getString(1);
			callStmt.close();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	public static Long Odb_Mnu(Long usrid, Long actid) {
		Main.logger = Logger.getLogger(DbUtil.class);
		Long ret = 0l;
		Connection conn = DbUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccess(?,?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setLong(2, usrid);
			callStmt.setLong(3, actid);
			callStmt.execute();
			ret = callStmt.getLong(1);
			callStmt.close();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	public static Long Odb_Mnu_Grp(Long grpid, Long actid) {
		Long ret = 0l;
		Connection conn = DbUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccessGrp(?,?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setLong(2, grpid);
			callStmt.setLong(3, actid);
			callStmt.execute();
			ret = callStmt.getLong(1);
			callStmt.close();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	public static Long Odb_Act_Grp(Long grpid, Long actid) {
		Long ret = 0l;
		Connection conn = DbUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.ODB_ACT_ACCESS_GRP(?,?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setLong(2, grpid);
			callStmt.setLong(3, actid);
			callStmt.execute();
			ret = callStmt.getLong(1);
			callStmt.close();
		} catch (Exception e) {
			try {
				CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.ODB_ACT_ACCESS_GRP(?,?)}");
				callStmt.registerOutParameter(1, Types.INTEGER);
				callStmt.setLong(2, grpid);
				callStmt.setLong(3, actid);
				callStmt.execute();
				ret = callStmt.getLong(1);
				callStmt.close();
			} catch (Exception e1) {
				DbUtil.Log_Error(e);
			}
		}
		return ret;
	}

	public static Long Odb_Mnu2(Long actid) {
		Main.logger = Logger.getLogger(DbUtil.class);
		Long ret = 0l;
		Connection conn = DbUtil.conn;
		try {
			CallableStatement callStmt = conn.prepareCall("{ ? = call MJUsers.OdbMnuAccess(?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setLong(2, actid);
			callStmt.execute();
			ret = callStmt.getLong(1);
			callStmt.close();
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	/**
	 * �������� ������� � ��������
	 * 
	 * @param actid
	 * @return
	 */
	public static Long Odb_Action(Long actid) {
		Long ret = 0l;
		try {
			CallableStatement callStmt = DbUtil.conn.prepareCall("{ ? = call MJUsers.ACT_ACCESS(?)}");
			callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.setLong(2, actid);
			callStmt.execute();
			ret = callStmt.getLong(1);
			callStmt.close();
		} catch (Exception e) {
			try {
				Db_Disconnect();
				Db_Connect();
				CallableStatement callStmt = DbUtil.conn.prepareCall("{ ? = call MJUsers.ACT_ACCESS(?)}");
				callStmt.registerOutParameter(1, Types.INTEGER);
				callStmt.setLong(2, actid);
				callStmt.execute();
				ret = callStmt.getLong(1);
				callStmt.close();
			} catch (Exception e1) {
				DbUtil.Log_Error(e1);
			}
		}
		return ret;
	}

	/**
	 * �������� ���� �������
	 * 
	 * @param ACT_NAME
	 * @param CUSRLOGNAME
	 * @return
	 */
	public static Long Chk_Accesss(String ACT_NAME, String CUSRLOGNAME) {
		Main.logger = Logger.getLogger(DbUtil.class);
		Long ret = 0l;
		Connection conn = DbUtil.conn;
		try {
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("acces_act");
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setString(1, ACT_NAME);
			prepStmt.setString(2, CUSRLOGNAME);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				ret = rs.getLong("CNT");
			}
			prepStmt.close();
			rs.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return ret;
	}

	/**
	 * �����
	 */
	public static void Rollback() {
		try {
			try {
				conn.rollback();
			} catch (SQLException e) {
				DbUtil.Log_Error(e);
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * ��������
	 */
	public static void Commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	// DB Execute Update (For Update/Insert/Delete) Operation
	public static void Db_Execute_Update(String sqlStmt) {
		// Declare statement as null
		Statement stmt = null;
		Main.logger = Logger.getLogger(DbUtil.class);
		try {
			// Connect to DB (Establish Oracle Connection)
			if (conn == null && !conn.isClosed()) {
				Db_Connect();
			}
			// Create Statement
			stmt = conn.createStatement();
			// Run executeUpdate operation with given sql statement
			stmt.executeUpdate(sqlStmt);
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		} finally {
			if (stmt != null) {
				// Close statement
				try {
					stmt.close();
				} catch (SQLException e) {
					DbUtil.Log_Error(e);
				}
			}
		}
	}

	public static String getResource(final String path) {
		final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		try {
			return IOUtils.toString(stream, "UTF-8");
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}

}
