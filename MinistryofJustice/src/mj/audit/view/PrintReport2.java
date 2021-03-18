package mj.audit.view;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import mj.app.main.Main;
import mj.app.model.SqlMap;
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

public class PrintReport2 extends JFrame {

	private static final long serialVersionUID = 1L;

	public void showReport(String tablename, Integer actid) {
		try {
			Main.logger = Logger.getLogger(getClass());
			InputStream input = this.getClass().getResourceAsStream("/mj/audit/view/Audit.jrxml");
			JasperDesign design = JRXmlLoader.load(input);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			/* List to hold Items */
			List<AUDIT_REPORT> listItems = new ArrayList<AUDIT_REPORT>();
			/* Create Items */

			Connection conn = DBUtil.conn;
			SqlMap sql = new SqlMap().load("/SQL.xml");
			String readRecordSQL = sql.getSql("AUDIT_REPORT");
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setString(1, tablename);
			prepStmt.setInt(2, actid);
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
			
			//JasperExportManager.exportReportToPdfFile(print, "AUDIT.rtf");

			/*
			File destFile = new File(System.getenv("MJ_PATH")+"/reports/", print.getName() + ".xls");
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			
			//Открытие выгруженного файла
			try {
			    Runtime.getRuntime().exec("cmd /c start excel.exe \""+System.getenv("MJ_PATH")+"/reports/"+print.getName() + ".xls\"");
			} catch (Exception e) {
			    e.printStackTrace();
			}
			*/

			
			JRViewer viewer = new JRViewer(print);
			viewer.setOpaque(true);
			viewer.setVisible(true);
			viewer.setSize(900, 800);
			this.add(viewer);
			this.setSize(900, 800);
			this.setVisible(true);
			
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
