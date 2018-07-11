package com.jiajie.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.poifs.nio.DataSource;

import com.sun.mail.util.MailSSLSocketFactory;

public class EmailUtil {
	private static final String user = "371645807@qq.com";
	private static final String code = "dkoteixwskwocafb";
	private static final String sendServer = "smtp.qq.com";
	private static final int port = 465;
	private static Address[] adds = null;

	static {
		System.out.println("===========================");
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = ExamZjInfo.class.getResourceAsStream("/constant.properties");
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String mail = prop.getProperty("mail");
		String[] mails = mail.split("#");
		adds = new Address[mails.length];
		for (int i = 0; i < mails.length; i++) {
			try {
				adds[i] = new InternetAddress(mails[i]);
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}
	}

	public static Session getSession() throws GeneralSecurityException {
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");// 必须 普通客户端
		props.setProperty("mail.transport.protocol", "smtp");// 必须选择协议
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		return Session.getDefaultInstance(props);

	}

	public static void SendMail(Session ss, Message ms)
			throws AddressException, MessagingException {
		ms.setFrom(new InternetAddress(user));
		ms.setSentDate(new Date());
		Transport transport = ss.getTransport();// 发送信息的工具
		transport.connect(sendServer, port, user, code);
		transport.sendMessage(ms, adds);// 对方的地址
		transport.close();
	}

	public MimeBodyPart createAttachment(String fileName) throws Exception {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(fileName);
		attachmentPart.setDataHandler(new DataHandler(fds));
		attachmentPart.setFileName(fds.getName());
		return attachmentPart;
	}
	
	public static boolean emailFormat(String email){
        boolean tag = true;
        final String pattern1 = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
	
	public static boolean isPhone(String phone) {
	    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(phone);
	    boolean isMatch = m.matches();
	    return isMatch;
	}
}
