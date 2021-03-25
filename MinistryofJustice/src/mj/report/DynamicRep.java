package mj.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import mj.app.model.SqlMap;
import mj.audit.view.AUDIT_REPORT;
import mj.dbutil.DBUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class DynamicRep extends JFrame {
	private static final long serialVersionUID = 1L;

	public void showReport(Integer ID) {
		try {
			Connection conn = DBUtil.conn;
			{
				PreparedStatement prp = conn
						.prepareStatement("with REP_IDS as\r\n" + 
								" (select ? IDS from dual)\r\n" + 
								"select (select prm_value\r\n" + 
								"          from rep_params_tmp\r\n" + 
								"         where rep_id = (select IDS from REP_IDS)\r\n" + 
								"           and prm_id = 1) TBL_NAME,\r\n" + 
								"       (select prm_value\r\n" + 
								"          from rep_params_tmp\r\n" + 
								"         where rep_id = (select IDS from REP_IDS)\r\n" + 
								"           and prm_id = 2) DT1,\r\n" + 
								"       (select prm_value\r\n" + 
								"          from rep_params_tmp\r\n" + 
								"         where rep_id = (select IDS from REP_IDS)\r\n" + 
								"           and prm_id = 3) DT2\r\n" + 
								"  from dual\r\n" + 
								"");
				prp.setInt(1, ID);
				ResultSet rs = prp.executeQuery();
				if (rs.next()) {
					
				}
				prp.close();
				rs.close();
			}
			InputStream input = this.getClass().getResourceAsStream("/audit/Audit.jrxml");
			JasperDesign design = JRXmlLoader.load(input);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			List<AUDIT_REPORT> listItems = new ArrayList<AUDIT_REPORT>();
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("AUDIT_REPORT");
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setString(1, "USR");
			prepStmt.setInt(2, 3247);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				AUDIT_REPORT list = new AUDIT_REPORT();
				list.setCAUDOPERATION(rs.getString("CAUDOPERATION"));
				list.setCAUDPROGRAM(rs.getString("CAUDPROGRAM"));
				list.setCAUDMACHINE(rs.getString("CAUDMACHINE"));
				list.setCTABLENAME(rs.getString("CTABLENAME"));
				list.setCTABLE(rs.getString("CTABLE"));
				list.setDAUDDATE(rs.getTimestamp("DAUDDATE"));
				list.setCOLDDATA(rs.getString("COLDDATA"));
				list.setCNEWDATA(rs.getString("CNEWDATA"));
				list.setCFIELDNAME(rs.getString("CFIELDNAME"));
				list.setCFIELD(rs.getString("CFIELD"));
				list.setIACTIONID(rs.getInt("IACTION_ID"));
				listItems.add(list);
			}
			
			rs.close();
			prepStmt.close();
			
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ItemDataSource", itemsJRBean);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JRViewer viewer = new JRViewer(print);
			viewer.setOpaque(true);
			viewer.setVisible(true);
			viewer.setSize(900, 800);
			this.add(viewer);
			this.setSize(900, 800);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}