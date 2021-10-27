package ru.psv.mj.test;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import oracle.jdbc.driver.OracleDriver;

public class OraFolder {

	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
  
  public static String passArray(){
  String ret = "ok";
  PreparedStatement prp = null;
  try {
    Connection conn = new OracleDriver().defaultConnection();
    prp = conn.prepareStatement("begin dbms_output.put_line(?); end;");
    File files = new File("/u01/app/oracle/admin/orcl/dpdump/");
    for (File file : files.listFiles()) {
         prp.setString(1,file.getAbsolutePath()); ;
      }
          prp.close();
    } catch (Exception e) {
      ret = getStackTrace(e);
    }

    return ret;
  }
}