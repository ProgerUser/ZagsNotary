package mj.doc.birthact;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import mj.app.main.Main;
import mj.app.model.SqlMap;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class PrintReport extends JFrame {

	public PrintReport() {
		Main.logger = Logger.getLogger(getClass());
	}

	private static final long serialVersionUID = 1L;

	public void showReport(Integer actid) {
		try {
			Main.logger = Logger.getLogger(getClass());
			InputStream input = this.getClass().getResourceAsStream("/br_act/BrAct.jrxml");
			JasperDesign design = JRXmlLoader.load(input);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			Connection conn = DBUtil.conn;
			SqlMap sql = new SqlMap().load("/SqlBurn.xml");
			String readRecordSQL = sql.getSql("ForReport");
			PreparedStatement prepStmt = conn.prepareStatement(readRecordSQL);
			prepStmt.setInt(1, actid);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				parameters.put("BR_ACT_ID", rs.getString("br_act_id"));
				parameters.put("LNAME", rs.getString("LNAME"));
				parameters.put("FNAME", rs.getString("FNAME"));
				parameters.put("CHBDATE", rs.getString("dcusbirthday"));
				parameters.put("CHSEX", rs.getString("SEX"));
				parameters.put("MNAME", rs.getString("MNAME"));
				parameters.put("CHBRNPLACE", rs.getString("ccusplace_birth"));
				parameters.put("CHCNT", rs.getString("br_act_brchcnt"));
				parameters.put("LD", rs.getString("br_act_ld"));
				parameters.put("NDOCA", rs.getString("BR_ACT_NDOCA"));
				parameters.put("DATEDOCA", rs.getString("BR_ACT_DATEDOCA"));
				parameters.put("MEDORGA", rs.getString("BR_ACT_MEDORGA"));
				parameters.put("FIOB", rs.getString("BR_ACT_FIOB"));
				parameters.put("DATEDOCB", rs.getString("BR_ACT_DATEDOCB"));
				parameters.put("NAMECOURT", rs.getString("BR_ACT_NAMECOURT"));
				parameters.put("DESCCOURT", rs.getString("BR_ACT_DESCCOURT"));
				parameters.put("DCOURT", rs.getString("BR_ACT_DCOURT"));
			}
			rs.close();
			prepStmt.close();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			list.add(parameters);

			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
			JRViewer viewer = new JRViewer(print);
			viewer.setOpaque(true);
			viewer.setVisible(true);
			this.add(viewer);
			this.setSize(700, 500);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			DBUtil.LogToDb(lineNumber, fullClassName, ExceptionUtils.getStackTrace(e), methodName);
		}
	}
}
