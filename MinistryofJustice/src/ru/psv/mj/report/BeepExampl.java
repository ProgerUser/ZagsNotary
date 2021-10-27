package ru.psv.mj.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sun.jna.Library;
import com.sun.jna.Native;

import ru.psv.mj.app.model.Connect;
import ru.psv.mj.utils.DbUtil;

/** Simple example of Windows native library declaration and usage. */
public class BeepExampl {

//	
//	Тест
//	
	public interface Kernel32 extends Library {
		// FREQUENCY is expressed in hertz and ranges from 37 to 32767
		// DURATION is expressed in milliseconds
		public boolean Beep(int FREQUENCY, int DURATION);

		public void Sleep(int DURATION);
	}

//
//	Интерфейс к C# dll, вместо использования jnit4net
//	
	public interface ICPU_NAME extends Library {
		public String CPU_NAME();
	};

	public interface IShow extends Library {
		public String Show(String externalRef);
	};

	public interface IHDD_SERIAL extends Library {
		public static String HDD_SERIAL() {
			// TODO Auto-generated method stub
			return null;
		}
	};

//
//	FRREP
//	
	public interface FRREP extends Library {
		public String _SHOWREPORT(
				String DLLOPTIONS,
				String USERNAME,
				String PASSW,
				String CONNECT_STRING,
				String SERVERNAME,
				String SID,
				String Edit_Enable,
				String Generate_Type,
				String Dir,
				String Use_Convertation,
				String File_Name,
				String REPORT_FILE,
				String REPORT_TYPE_ID,
				String REPORT_ID,
				String REP_NAME,
				String REPORT_SORT,
				String P1,
				String P2,
				String P3,
				String P4,
				String P5,
				String P6,
				String P7,
				String P8,
				String P9,
				String P10);
	}

//	
//	Main
//	
	private static String sn = null;
	private static String cpu = null;
	@SuppressWarnings("resource")
	public static final String getSerialNumber() {
		
		List<String> snlist = new ArrayList<String>();
		
		if (sn != null) {
			return sn;
		}

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "wmic", "diskdrive", "get", "serialnumber" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scanner sc = new Scanner(is);
		try {
			while (sc.hasNext()) {
				String next = sc.next();
				if (!"SerialNumber".equals(next)) {
					snlist.add(next.trim());
				}
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		sn = snlist.get(snlist.size() - 1);
		
		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}
		
		return sn.trim();
	}

	@SuppressWarnings("resource")
	public static final String getCpuNumber() {

		if (cpu != null) {
			return cpu;
		}

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "wmic", "CPU", "get", "Name" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scanner sc = new Scanner(is);
		try {
			while (sc.hasNext()) {
				String next = sc.next();
				if (!"Name".equals(next)) {
					cpu = (cpu != null ? cpu : "") + " " + next.trim();
				}
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (cpu == null) {
			throw new RuntimeException("Cannot find CPU NAME");
		}

		return cpu.trim();
	}

	public void exec() {
		ICPU_NAME iCPU_NAME = (ICPU_NAME) Native.loadLibrary("D:/MJ_DLL.dll", ICPU_NAME.class);// call JNA
		System.out.println("Returned: " + iCPU_NAME.CPU_NAME());
	}

	public static String getMotherboardSN() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs", new File(System.getenv("MJ_PATH") + "OutReports"));
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

		    String vbs =
		         "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		        + "Set colItems = objWMIService.ExecQuery _ \n"
		        + "   (\"Select * from Win32_Processor\") \n"
		        + "For Each objItem in colItems \n"
		        + "    Wscript.Echo objItem.Name \n"
		        + "    exit for  ' do the first cpu only! \n"
		        + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}
	
	public static void main(String[] args) throws SQLException {
//		System.out.println(getCpuNumber());
//		System.out.println(getSerialNumber());
		// System.out.println(getMotherboardSN());

		// new BeepExampl().exec();
		// Integer ret = new DotNet().FileName("ADOPTOIN");
		// System.out.println(ret);

		// System.out.println(getSerialNumber());
		// D:\MJ_WORD.dll
		// IShow iShow = (IShow) Native.loadLibrary("D:/MJ_WORD.dll", IShow.class);
		// System.out.println("ret=" +
		// iShow.Show("C:\\MJ_\\OutReports\\ADOPTOIN.docx"));

		{
			Connect.connectionURL = "localhost:1522/orcl";
			Connect.userID = "xxi";
			Connect.userPassword = "";
		}
		
		DbUtil.Db_Connect();
		
//		String sid = "";
//		{
//			Statement statement = DBUtil.conn.createStatement();
//			ResultSet rs = statement
//					.executeQuery("select INSTANCE_NAME, HOST_NAME, userenv('SESSIONID') from SYS.V_$INSTANCE");
//			rs.next();
//			sid = rs.getString(1);
//		}
//		//DBUtil.dbDisconnect();
//
//		FRREP lib = (FRREP) Native.loadLibrary(System.getenv("MJ_PATH") + "bin/FRREP.dll", FRREP.class);
//		String ret = lib._SHOWREPORT("RUN", // DLLOPTIONS
//				"xxi", // USERNAME
//				"", // PASSW
//				"localhost:1522/orcl", // CONNECT_STRING
//				"orcl", // SERVERNAME
//				sid, // SID
//				"Y", // Edit_Enable
//				"F", // Generate_Type
//				"T", // Dir
//				"Y", // Use_Convertation
//				"AUDIT.fr3", // File_Name
//				"AUDIT.fr3", // REPORT_FILE
//				"1", // REPORT_TYPE_ID
//				"", // REPORT_ID
//				"", // REP_NAME
//				"", // REPORT_SORT
//				"0", // P1
//				"", // P2
//				"", // P3
//				"", // P4
//				"", // P5
//				"", // P6
//				"", // P7
//				"", // P8
//				"", // P9
//				"" // P10
//		);
//
//		System.out.println("FRREP=" + ret);
	}
}