package mj.doc.cus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class Auth1c {

	@SuppressWarnings("resource")
	public static final String getSerialNumber() {
		String sn = null;
		List<String> snlist = new ArrayList<String>();
		try {
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
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return sn.trim();
	}

	@SuppressWarnings("resource")
	public static final String getCpuNumber() {
		String cpu = null;
		try {

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
						cpu = (cpu != null ? cpu : "") + " " + next;
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
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return cpu.trim();
	}

	/**
	 * Конструктор, инициализируем подключения к DLL
	 */
	public Auth1c() {
		Main.logger = Logger.getLogger(getClass());
//		try {
//			// Bridge.setVerbose(true);
//			if (System.getProperty("sun.arch.data.model").equals("64")) {
//				Bridge.init(new File(System.getenv("MJ_PATH") + "jni4net.n.w64.v40-0.8.8.0.dll"));
//			} else {
//				Bridge.init(new File(System.getenv("MJ_PATH") + "jni4net.n.w32.v40-0.8.8.0.dll"));
//			}
//			File dll = new File(System.getenv("MJ_PATH") + "MJ.j4n.dll");
//
//			Bridge.LoadAndRegisterAssemblyFrom(dll);
//		} catch (Exception e) {
//			Msg.Message(ExceptionUtils.getStackTrace(e));
//			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
//			e.printStackTrace();
//		}
	}

	/**
	 * Получить название базы
	 * 
	 * @return
	 */
	public String DB_NAME() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select value from props where name = 'DbName'");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить LOGIN
	 * 
	 * @return
	 */
	public String LOGIN() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select value from props where name = 'Login'");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить PASSWORD
	 * 
	 * @return
	 */
	public String PASSWORD() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select value from props where name = 'Password'");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить PersonalDbNumber
	 * 
	 * @return
	 */
	public String PersonalDbNumber() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select value from props where name = 'PersonalDbNumber'");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить PersonalDbNumber
	 * 
	 * @return
	 */
	public String FullAddress() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select FullAddress from PropsFor1c");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("FullAddress");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить ID
	 * 
	 * @return
	 */
	public String ID() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select value from props where name = 'ID'");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить дату последней авторизации в 1С
	 * 
	 * @return
	 */
	public String LAST_AUTH() {
		String ret = null;
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("select value from props where name = 'last_connect'");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString("value");
			}
			stmt.close();
			rs.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Авторизация в 1с http сервис
	 * 
	 * @param db_name
	 * @param last_auth
	 * @param encode
	 * @return
	 */
	public String AUTH_1C_DATE(String db_name, String last_auth, String encode) {
		String ret = "";
		try {
			final String username = "User";
			final String password = "User";

			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password.toCharArray());
				}
			});
			String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<Контейнер>\r\n"
					+ "<ДанныеАвторизации ИмяБазы=\"" + db_name + "\" ДатаПоследнейАвторизации=\"" + last_auth
					+ "\"/>\r\n" + "<ДанныеДляАвторизации КодДоступа=\"" + encode + "\"/>\r\n" + "</Контейнер>\r\n";
			URL url = new URL("https://62.182.8.67/PD/hs/Authorization");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Set timeout as per needs
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			// Set DoOutput to true if you want to use URLConnection for output.
			// Default is false
			connection.setDoOutput(true);

			connection.setUseCaches(true);
			connection.setRequestMethod("POST");

			// Set Headers
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");

			// Write XML
			OutputStream outputStream = connection.getOutputStream();
			byte[] b = request.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();

			// Read XML
			InputStream inputStream = connection.getInputStream();
			String theString = IOUtils.toString(inputStream, "UTF-8");
			ret = theString;
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	public String Call1cHttpService(String xml, String username, String password, URL url) {
		String ret = "";
		try {
			Main.logger.info(xml);
			Main.logger.info(username);
			Main.logger.info(password);
			Main.logger.info(url);
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password.toCharArray());
				}
			});

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Set timeout as per needs
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			// Set DoOutput to true if you want to use URLConnection for output.
			// Default is false
			connection.setDoOutput(true);

			connection.setUseCaches(true);
			connection.setRequestMethod("POST");

			// Set Headers
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");

			// Write XML
			OutputStream outputStream = connection.getOutputStream();
			byte[] b = xml.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();

			// Read XML
			InputStream inputStream = connection.getInputStream();
			String theString = IOUtils.toString(inputStream, "UTF-8");
			ret = theString;
		} catch (Exception e) {
			//DBUtil.LOG_ERROR(e);
			Main.logger.error(e);
		}
		return ret;
	}

	/**
	 * Обновить данные последней авторизаций
	 * 
	 * @param date
	 */
	public void SAVE_AUTH_1C_DATE(String date) {
		try {
			dbConnect();
			PreparedStatement stmt = conn.prepareStatement("update props set value = ? where name = 'last_connect'");
			stmt.setString(1, date);
			stmt.executeUpdate();
			stmt.close();
			dbDisconnect();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * разобрать xml, дата последней авторизации
	 * 
	 * @param xml
	 * @return
	 */
	public String XML(String xml) {
		String ret = "";
		try {
			StringBuilder xmlStringBuilder = new StringBuilder();
			xmlStringBuilder.append(xml);
			ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Ответ");
			// System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				// System.out.println("\nОтвет :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// System.out.println("ОтветНаПопыткуАвторизации : " +
					// eElement.getAttribute("ОтветНаПопыткуАвторизации"));
					ret = eElement.getAttribute("ОтветНаПопыткуАвторизации");
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * разобрать xml, ответ на поппытку регистрации
	 * 
	 * @param xml
	 * @return
	 */
	public String ParseXmlRegRet(String xml) {
		String ret = "";
		try {
			StringBuilder xmlStringBuilder = new StringBuilder();
			xmlStringBuilder.append(xml);
			ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			// System.out.println("Root element :" +
			// doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Ответ");
			// System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				// System.out.println("\nОтвет :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// System.out.println("ОтветНаПопыткуАвторизации : " +
					// eElement.getAttribute("ОтветНаПопыткуАвторизации"));
					ret = eElement.getAttribute("Результат");
				}
			}
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Сессия
	 */
	private Connection conn;

	/**
	 * Открыть сессию
	 */
	private void dbConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			conn = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * Отключить сессию
	 */
	public void dbDisconnect() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	/**
	 * получить данные о гражданине
	 * 
	 * @param request
	 * @return
	 */
	public String SENDFIO_1C(String request) {
		String ret = "";
		try {
			final String username = "User";
			final String password = "User";

			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password.toCharArray());
				}
			});

			URL url = new URL("https://62.182.8.67/PD/hs/GetData/FIO");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Set timeout as per needs
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			// Set DoOutput to true if you want to use URLConnection for output.
			// Default is false
			connection.setDoOutput(true);

			connection.setUseCaches(true);
			connection.setRequestMethod("POST");

			// Set Headers
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");

			// Write XML
			OutputStream outputStream = connection.getOutputStream();
			byte[] b = request.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();

			// Read XML
			InputStream inputStream = connection.getInputStream();
			String theString = IOUtils.toString(inputStream, "UTF-8");

			ret = theString;
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * получить данные о гражданине
	 * 
	 * @param db_name
	 * @param last_auth
	 * @param encode
	 * @return
	 */
	public String SAVEFIO_1C(String request) {
		String ret = "";
		try {
			final String username = "User";
			final String password = "User";

			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password.toCharArray());
				}
			});

			URL url = new URL("https://62.182.8.67/PD/hs/Sync/ClientPriority");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Set timeout as per needs
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			// Set DoOutput to true if you want to use URLConnection for output.
			// Default is false
			connection.setDoOutput(true);

			connection.setUseCaches(true);
			connection.setRequestMethod("POST");

			// Set Headers
			connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");

			// Write XML
			OutputStream outputStream = connection.getOutputStream();
			byte[] b = request.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();

			// Read XML
			InputStream inputStream = connection.getInputStream();
			String theString = IOUtils.toString(inputStream, "UTF-8");

			ret = theString;
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Зашифровать
	 * 
	 * @return
	 */
	public String ENCRYPT(String db_name, String hdd_serial, String cpu_name) {
		String ret = null;
		try {
			// исходная строка
			String concat = hdd_serial + "/////" + cpu_name + "/////" + db_name;
			// System.out.println(concat);
			Random r = new Random();
			// от
			int low = 1000;
			// до
			int high = 9999;
			// случайное число в промежутке
			int random_int = r.nextInt(high - low) + low;
			// длина
			int concat_length = concat.length();
			// зашифрованная строка
			String encrypt_str = "";
			// Шифруем данные
			// Вычисляем код каждого символа исходной строки, прибавляем к нему случайное
			// число и дополняем сзади нулями до пяти символов
			for (int i = 0; i < concat_length; i++) {
				int rec_code = Character.codePointAt(concat, i);
				int ch_rec_code = rec_code + random_int;
				String prefix = "";
				if (ch_rec_code < 10) {
					prefix = "0000";
				} else if (ch_rec_code < 100) {
					prefix = "000";
				} else if (ch_rec_code < 1000) {
					prefix = "00";
				} else if (ch_rec_code < 10000) {
					prefix = "0";
				}
				String ch_rec_code_string = String.valueOf(ch_rec_code);
				encrypt_str = encrypt_str + prefix + ch_rec_code_string;
			}
			// После шифрования к началу строки и концу строки присоединяем соответственно
			// конец и начало (наоборот) случайного числа, которое использовали для
			// шифрования
			String random_int_string = String.valueOf(random_int);
			String start2 = random_int_string.substring(0, 2);
			String end2 = random_int_string.substring(random_int_string.length() - 2);
			encrypt_str = end2 + encrypt_str + start2;
			ret = encrypt_str;
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return ret;
	}

	/**
	 * Получить серийный номер жесткого диска power shall
	 * 
	 * @return
	 */
	public String HDD_SERIAL() {
		String HDD_SERIAL = "";
		try {
			// Bridge.setVerbose(true);
			/*
			 * if (System.getProperty("sun.arch.data.model").equals("64")) { Bridge.init(new
			 * File(System.getenv("MJ_PATH") + "jni4net.n.w64.v40-0.8.8.0.dll")); } else {
			 * Bridge.init(new File(System.getenv("MJ_PATH") +
			 * "jni4net.n.w32.v40-0.8.8.0.dll")); } File dll = new
			 * File(System.getenv("MJ_PATH") + "MJ.j4n.dll");
			 * 
			 * Bridge.LoadAndRegisterAssemblyFrom(dll);
			 */
//			// вызвать dll c#
//			HardWhere csdll = new HardWhere();
//			// получить серийный номер жесткого диска
//
//			String[] cs_dll_hdd_serial = csdll.HDD_SERIAL();
//			for (String var : cs_dll_hdd_serial) {
//				if (!var.trim().equals("None")) {
//					HDD_SERIAL = var.trim();
//				}
//			}
			HDD_SERIAL = getSerialNumber();

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return HDD_SERIAL;
	}

	/**
	 * Получить модель процессора power shall
	 * 
	 * @return
	 */
	public String CPU_NAME() {
		String CPU_NAME = null;
		try {
			// Bridge.setVerbose(true);
			/*
			 * if (System.getProperty("sun.arch.data.model").equals("64")) { Bridge.init(new
			 * File(System.getenv("MJ_PATH") + "jni4net.n.w64.v40-0.8.8.0.dll")); } else {
			 * Bridge.init(new File(System.getenv("MJ_PATH") +
			 * "jni4net.n.w32.v40-0.8.8.0.dll")); } File dll = new
			 * File(System.getenv("MJ_PATH") + "MJ.j4n.dll");
			 * 
			 * Bridge.LoadAndRegisterAssemblyFrom(dll);
			 */
//			// вызвать dll c#
//			HardWhere csdll = new HardWhere();
//			// получить модель процессора
//			String[] cs_dll_cpu_name = csdll.CPU_NAME();
//			for (String var : cs_dll_cpu_name) {
//				if (!var.trim().equals("None")) {
//					CPU_NAME = var.trim();
//				}
//			}
			CPU_NAME = getCpuNumber();
			System.out.println("CPU_NAME:" + CPU_NAME);
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
		return CPU_NAME;
	}
}
