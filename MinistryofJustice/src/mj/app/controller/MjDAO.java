package mj.app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mj.app.main.Main;
import mj.app.model.OK_SM;
import mj.app.model.SqlMap;
import mj.app.model.User_in;
import mj.app.model.User_out;
import mj.users.USR;
import mj.utils.DbUtil;

public class MjDAO {

	public MjDAO() {
		Main.logger = Logger.getLogger(getClass());
	}

	// *******************************
	// SELECT User_in
	// *******************************
	public static ObservableList<OK_SM> OK_SM() {
		ObservableList<OK_SM> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			String selectStmt = "select * from COUNTRIES t order by CODE asc";

			Connection conn = DbUtil.conn;
			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = OK_SM(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	private static ObservableList<OK_SM> OK_SM(ResultSet rs) {
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			ObservableList<OK_SM> user_o_list = FXCollections.observableArrayList();
			while (rs.next()) {
				OK_SM user_o = new OK_SM();
				user_o.setCDIGITAL(rs.getString("CDIGITAL"));
				user_o.setCALFA_2(rs.getString("CALFA_2"));
				user_o.setCSHORTNAME(rs.getString("CSHORTNAME"));
				user_o.setCLONGNAME(rs.getString("CLONGNAME"));
				user_o_list.add(user_o);
			}
			return user_o_list;
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return null;
	}

	// *******************************
	// SELECT User_in
	// *******************************
	public static ObservableList<User_in> User_in(Long form_name) {
		ObservableList<User_in> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			Connection conn = DbUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("user_in");

			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, form_name);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr_in(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT User_in2
	// *******************************
	public static ObservableList<User_in> User_in2(Long form_name) {
		ObservableList<User_in> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			Connection conn = DbUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("user_in2");

			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, form_name);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr_in(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT User_out_menu
	// *******************************
	public static ObservableList<User_out> User_out_menu(Long form_id) {
		ObservableList<User_out> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			Connection conn = DbUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("User_out_menu");

			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, form_id);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr_out(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT User_out2
	// *******************************
	public static ObservableList<User_out> User_out2(Long form_name) {
		ObservableList<User_out> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			Connection conn = DbUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("user_out2");

			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, form_name);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr_out(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT User_out
	// *******************************
	public static ObservableList<User_out> User_out(Long form_name) {
		ObservableList<User_out> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			Connection conn = DbUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("user_out");

			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, form_name);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr_out(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT User_in_Menu
	// *******************************
	public static ObservableList<User_in> User_in_menu(Long form_name) {
		ObservableList<User_in> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			Connection conn = DbUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("User_in_menu");

			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setLong(1, form_name);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr_in(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT get_usr_in
	// *******************************
	private static ObservableList<User_in> get_usr_in(ResultSet rs) {
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			ObservableList<User_in> user_in_list = FXCollections.observableArrayList();
			while (rs.next()) {
				User_in user_in = new User_in();
				user_in.set_FIO_I(rs.getString("CUSRNAME"));
				user_in.set_USR_ID_I(rs.getString("CUSRLOGNAME"));
				user_in.set_TYPE_ACCESS_I(rs.getString("T_NAME"));
				user_in_list.add(user_in);
			}
			return user_in_list;
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return null;
	}

	// *******************************
	// SELECT USR
	// *******************************
	public static ObservableList<USR> USR() {
		ObservableList<USR> Forms_lst = null;
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			String selectStmt = "select * from usr ORDER BY CUSRLOGNAME";
			Connection conn = DbUtil.conn;

			PreparedStatement prepStmt = conn.prepareStatement(selectStmt);
			ResultSet rsEmps = prepStmt.executeQuery();
			Forms_lst = get_usr(rsEmps);
			rsEmps.close();
			prepStmt.close();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return Forms_lst;
	}

	// *******************************
	// SELECT get_usr
	// *******************************
	private static ObservableList<USR> get_usr(ResultSet rs) {
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			ObservableList<USR> forms_list = FXCollections.observableArrayList();
			while (rs.next()) {
				USR frms = new USR();
				frms.setIUSRID(rs.getLong("IUSRID"));
				frms.setCUSRLOGNAME(rs.getString("CUSRLOGNAME"));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

				if (rs.getDate("DUSRHIRE") != null) {
					String DUSRHIRE = new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DUSRHIRE"));
					frms.setDUSRHIRE(LocalDate.parse(DUSRHIRE, formatter));
				}
				if (rs.getDate("DUSRFIRE") != null) {
					String DUSRFIRE = new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("DUSRFIRE"));
					frms.setDUSRFIRE(LocalDate.parse(DUSRFIRE, formatter));
				}

				frms.setCUSROFFPHONE(rs.getString("CUSROFFPHONE"));
				frms.setSHORT_NAME(rs.getString("SHORT_NAME"));
				frms.setCUSRNAME(rs.getString("CUSRNAME"));

				frms.setCUSRPOSITION(rs.getString("CUSRPOSITION"));
				frms.setSHORT_POSITION(rs.getString("SHORT_POSITION"));
				frms.setMUST_CHANGE_PASSWORD(rs.getString("MUST_CHANGE_PASSWORD"));
				frms.setCRESTRICT_TERM(rs.getString("CRESTRICT_TERM"));

				frms.setIUSRPWD_LENGTH(rs.getLong("IUSRPWD_LENGTH"));
				frms.setIUSRBRANCH(rs.getLong("IUSRBRANCH"));
				frms.setIUSRCHR_QUANTITY(rs.getLong("IUSRCHR_QUANTITY"));
				frms.setIUSRNUM_QUANTITY(rs.getLong("IUSRNUM_QUANTITY"));
				frms.setIUSRSPEC_QUANTITY(rs.getLong("IUSRSPEC_QUANTITY"));

				forms_list.add(frms);
			}
			return forms_list;
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return null;
	}

	// *******************************
	// SELECT get_usr_out
	// *******************************
	private static ObservableList<User_out> get_usr_out(ResultSet rs) {
		try {
			Main.logger = Logger.getLogger(MjDAO.class);
			ObservableList<User_out> user_o_list = FXCollections.observableArrayList();
			while (rs.next()) {
				User_out user_o = new User_out();
				user_o.set_FIO_O(rs.getString("CUSRNAME"));
				user_o.set_USR_ID_O(rs.getString("CUSRLOGNAME"));
				user_o_list.add(user_o);
			}
			return user_o_list;
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
		return null;
	}
}
