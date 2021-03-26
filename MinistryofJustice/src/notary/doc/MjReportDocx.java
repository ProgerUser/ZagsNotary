package notary.doc;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class MjReportDocx {

	public MjReportDocx() {
		Main.logger = Logger.getLogger(getClass());
	}

	public static void run(String filepath, String sql) {
		try {
			// Вызов
			Docx docx = new Docx(System.getenv("MJ_PATH") + filepath);
			docx.setVariablePattern(new VariablePattern("#{", "}"));
			// preparing variables
			Variables variables = new Variables();
			PreparedStatement prepStmt = DBUtil.conn.prepareStatement(sql);
			prepStmt.setInt(1, 1);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				variables.addTextVariable(new TextVariable("#{DOC_NUMBER}", rs.getString("DOC_NUMBER")));
			}
			rs.close();
			prepStmt.close();

			// fill template
			docx.fillTemplate(variables);
			File tempFile = File.createTempFile("Документ", ".docx", new File(System.getenv("MJ_PATH") + "OutReports"));
			FileOutputStream str = new FileOutputStream(tempFile);
			docx.save(str);
			str.close();
			tempFile.deleteOnExit();
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(tempFile);
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}
}
