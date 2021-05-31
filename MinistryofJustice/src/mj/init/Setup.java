package mj.init;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.doc.cus.Auth1c;
import mj.msg.Msg;
import mj.utils.DbUtil;

public class Setup {

    @FXML
    private TextField HDD_SERIAL;
    
    @FXML
    private TextField CPU_NAME;
    
	private static String FullAddress = null;

	@FXML
	private TextField PersonalDbNumber;

	@FXML
	private TextField Port;

	@FXML
	private TextField DbName;

	@FXML
	private TextField Login;

	@FXML
	private TextField ID;

	@FXML
	private TextField EMail;

	@FXML
	private TextField HostAddress;

	@FXML
	private TextField Resource;

	@FXML
	private CheckBox IsSsl;

	@FXML
	private PasswordField Password;

	/**
	 * Выполнить регистрацию
	 * 
	 * @param event
	 */
	@FXML
	void Register(ActionEvent event) {
		try {
			if (DbUtil.Odb_Action(143l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (!DbName.getText().equals("") & !EMail.getText().equals("") & FullAddress != null
					& !Login.getText().equals("") & !Password.getText().equals("")) {
				// разрешить любые сертификаты
				new HttpsTrustManager().allowAllSSL();

				Auth1c exdb = new Auth1c();
				String CPU_NAME = exdb.CPU_NAME();
				String HDD_SERIAL = exdb.HDD_SERIAL();
				String ENCRYPT = exdb.ENCRYPT(DbName.getText(), HDD_SERIAL, CPU_NAME);

				// сохранить
				{
					SQLIETEConnect();
					String sql1 = "update props set value = '" + DbName.getText() + "' where name = 'DbName';";
					String sql2 = "update props set value = '" + EMail.getText() + "' where name = 'EMail';";
					String sql3 = "update props set value = '" + ENCRYPT + "' where name = 'PersonalDbNumber';";
					String[] statements = new String[] { sql1, sql2, sql3 };
					for (String sql : statements) {
						PreparedStatement stsmt = SQLIETE.prepareStatement(sql);
						stsmt.execute();
						stsmt.close();
					}
					SQLIETEDisconnect();
					Init();
				}
				// Обращение к сервису
				String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<Контейнер>\r\n"
						+ "<ДанныеДляРегистрации ПерсональныйНомерБазы=\"" + ENCRYPT
						+ "\" ЭлектронныйАдресРегистратора=\"" + EMail.getText() + "\"/>\r\n" + "</Контейнер>\r\n";
				URL url = new URL(FullAddress + "/Registration");
				String RegistrReturn = exdb.Call1cHttpService(request, Login.getText(), Password.getText(), url);
				// System.out.println(RegistrReturn);
				Msg.Message(exdb.ParseXmlRegRet(RegistrReturn));
			} else {
				Msg.Message("Заполните поля!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Сохранить параметры подключения
	 */
	public void SaveConnParam() {
		try {
			SQLIETEConnect();
			String sql1 = "update props set value = '" + ((IsSsl.isSelected()) ? "Y" : "N") + "' where name = 'IsSsl';";
			String sql2 = "update props set value = '" + HostAddress.getText() + "' where name = 'HostAddress';";
			String sql3 = "update props set value = '" + Port.getText() + "' where name = 'Port';";
			String sql4 = "update props set value = '" + Resource.getText() + "' where name = 'Resource';";
			String sql5 = "update props set value = '" + Login.getText() + "' where name = 'Login';";
			String sql6 = "update props set value = '" + Password.getText() + "' where name = 'Password';";
			String[] statements = new String[] { sql1, sql2, sql3, sql4, sql5, sql6 };
			for (String sql : statements) {
				PreparedStatement stsmt = SQLIETE.prepareStatement(sql);
				stsmt.execute();
				stsmt.close();
			}
			SQLIETEDisconnect();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Сохранить параметры регистрации
	 */
	public void SaveRegParam() {
		try {
			SQLIETEConnect();
			String sql1 = "update props set value = '" + DbName.getText() + "' where name = 'DbName';";
			String sql2 = "update props set value = '" + EMail.getText() + "' where name = 'EMail';";
			String sql3 = "update props set value = '" + PersonalDbNumber.getText()
					+ "' where name = 'PersonalDbNumber';";
			String[] statements = new String[] { sql1, sql2, sql3 };
			for (String sql : statements) {
				PreparedStatement stsmt = SQLIETE.prepareStatement(sql);
				stsmt.execute();
				stsmt.close();
			}
			SQLIETEDisconnect();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	@FXML
	void TestConnect(ActionEvent event) {
		try {
			if (DbUtil.Odb_Action(142l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (!Port.getText().equals("") & !Resource.getText().equals("") & !Login.getText().equals("")
					& !Password.getText().equals("") & !ID.getText().equals("")) {
				SaveConnParam();
				Init();

				// разрешить любые сертификаты
				new HttpsTrustManager().allowAllSSL();

				Auth1c exdb = new Auth1c();
				// Обращение к сервису
				String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<Контейнер>\r\n"
						+ "<РодительскийЭлемент>\r\n <ТестовыеДанные Привет=\"Привет\"/>\r\n"
						+ "\r\n</РодительскийЭлемент>\r\n</Контейнер>\r\n";
				URL url = new URL(FullAddress + "/TestConnection");
				String RegistrReturn = exdb.Call1cHttpService(request, Login.getText(), Password.getText(), url);
				Msg.Message(RegistrReturn);
			} else {
				Msg.Message("Заполните поля!");
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Проверить авторизацию
	 * 
	 * @param event
	 */
	@FXML
	void CheckAuth(ActionEvent event) {
		try {
			if (DbUtil.Odb_Action(144l) == 0) {
				Msg.Message("Нет доступа!");
				return;
			}
			if (!ID.getText().equals("")) {
				// сохраним
				{
					SQLIETEConnect();
					String sql1 = "update props set value = '" + ID.getText() + "' where name = 'ID';";
					PreparedStatement stsmt = SQLIETE.prepareStatement(sql1);
					stsmt.execute();
					stsmt.close();
					SQLIETEDisconnect();
				}
				// разрешить любые сертификаты
				new HttpsTrustManager().allowAllSSL();

				Auth1c exdb = new Auth1c();
				// Обращение к сервису
				String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<Контейнер>\r\n"
						+ "<ДанныеДляАвторизации КодДоступа=\"" + PersonalDbNumber.getText() + "\" IDБазы=\""
						+ ID.getText() + "\"/>\r\n" + "</Контейнер>\r\n";
				URL url = new URL(FullAddress + "/Authorization");
				String RegistrReturn = exdb.Call1cHttpService(request, Login.getText(), Password.getText(), url);
				String xml_last_auth = exdb.XML(RegistrReturn);
				exdb.SAVE_AUTH_1C_DATE(xml_last_auth);
				Msg.Message(xml_last_auth);

			} else {
				Msg.Message("Заполните поле!");
				ID.requestFocus();
			}
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Закрытие формы
	 */
	void onclose() {
		Stage stage = (Stage) IsSsl.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Сессия
	 */
	private Connection SQLIETE;

	/**
	 * Открыть сессию
	 */
	void SQLIETEConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Main.logger = Logger.getLogger(getClass());
			String url = "jdbc:sqlite:" + System.getenv("MJ_PATH") + "SqlLite\\log.db";
			SQLIETE = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отключить сессию
	 */
	void SQLIETEDisconnect() {
		try {
			Main.logger = Logger.getLogger(getClass());
			if (SQLIETE != null && !SQLIETE.isClosed()) {
				SQLIETE.close();
			}
		} catch (SQLException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Отправить письмо
	 * 
	 * @param posid
	 * @param daylast
	 */
	void sendmail(String to, String uuid) {
		Main.logger = Logger.getLogger(getClass());
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.rambler.ru");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.trust", "smtp.rambler.ru");
		properties.put("mail.smtp.user", "abh_mj@rambler.ru");
		properties.put("mail.smtp.password", "O3GmogtK$kBBjda$8GRk");
		properties.put("mail.store.protocol", "imap");
		properties.put("mail.imap.host", "imap.rambler.ru");
		properties.put("mail.imap.port", "993");
		properties.put("mail.imap.auth", "true");
		properties.put("mail.imap.ssl.enable", "false");
		properties.put("mail.imap.ssl.trust", "imap.rambler.ru");
		properties.put("mail.imap.user", "abh_mj@rambler.ru");
		properties.put("mail.imap.password", "O3GmogtK$kBBjda$8GRk");
		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("abh_mj@rambler.ru", "O3GmogtK$kBBjda$8GRk");
			}
		});

		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			message.setContent(message, "text/plain; charset=UTF-8");
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			// Set From: header field of the header.
			message.setFrom(new InternetAddress("abh_mj@rambler.ru"));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Новая регистрация Министерство юстиции", "UTF-8");

			// Now set the actual message
			message.setText("", "UTF-8");

			// Send message
			Transport.send(message);

			// update property sqlitedb
			setStatus(true);
		} catch (MessagingException e) {
			DbUtil.Log_Error(e);
		}
	}

	/**
	 * Получить все поля
	 */
	void Init() {
		try {
			Main.logger = Logger.getLogger(getClass());
			SQLIETEConnect();
			PreparedStatement stsmt = SQLIETE.prepareStatement("select * from PropsFor1c");
			ResultSet rs = stsmt.executeQuery();
			if (rs.next()) {
				HostAddress.setText(rs.getString("HostAddress"));
				Port.setText(rs.getString("Port"));
				if (rs.getString("IsSsl").equals("Y")) {
					IsSsl.setSelected(true);
				} else if (rs.getString("IsSsl").equals("N")) {
					IsSsl.setSelected(false);
				}
				Resource.setText(rs.getString("Resource"));
				Login.setText(rs.getString("Login"));
				Password.setText(rs.getString("Password"));
				DbName.setText(rs.getString("DbName"));
				EMail.setText(rs.getString("EMail"));
				PersonalDbNumber.setText(rs.getString("PersonalDbNumber"));
				FullAddress = rs.getString("FullAddress");
				ID.setText(rs.getString("ID"));
			}
			
			stsmt.close();
			rs.close();

			SQLIETEDisconnect();
		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	// инициализация
	@FXML
	private void initialize() {
		try {

			Auth1c exdb = new Auth1c();
			
		    CPU_NAME.setText(exdb.CPU_NAME());
		    HDD_SERIAL.setText(exdb.HDD_SERIAL());
		    
			Init();

		} catch (Exception e) {
			DbUtil.Log_Error(e);
		}
	}

	private BooleanProperty Status;
	private StringProperty UUID_;

	public void setStatus(Boolean value) {
		this.Status.set(value);
	}

	public void setUUID(String value) {
		this.UUID_.set(value);
	}

	public boolean getStatus() {
		return this.Status.get();
	}

	public String getUUID() {
		return this.UUID_.get();
	}

	public Setup() {
		Main.logger = Logger.getLogger(getClass());
		this.Status = new SimpleBooleanProperty();
		this.UUID_ = new SimpleStringProperty();
	}
}
