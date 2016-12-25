package com.application.server.utils.mailer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.application.server.utils.CommonLib;
import com.application.server.utils.exception.ZException;

public class EmailUtil {

	private static volatile EmailUtil sInstance;
	private final String HOST_NAME = "smtp.gmail.com";
	private final int PORT_NUMBER = 587;
	private final String START_TLS_ENABLED = "true";
	private final String AUTH_FLAG = "true";

	/**
	 * Empty constructor to prevent multiple objects in memory
	 */
	private EmailUtil() {
	}

	/**
	 * Implementation of double check'd locking scheme.
	 */
	public static EmailUtil getInstance() {

		if (sInstance == null) {
			synchronized (EmailUtil.class) {
				if (sInstance == null) {
					sInstance = new EmailUtil();
				}
			}
		}
		return sInstance;
	}

	/**
	 * Function - send a mail as per detailed in the model in a separate thread.
	 * 
	 * @param emailModel
	 */
	public void sendEmail(EmailModel emailModel) {

		Runnable runnable = new Runnable() {
			public void run() {
				System.setProperty("jsse.enableSNIExtension", "false");
				
				// Get system properties
				Properties properties = new Properties();

				// Setup mail server
				properties.setProperty("mail.smtp.host", HOST_NAME);
				properties.put("mail.smtp.starttls.enable", START_TLS_ENABLED);
				properties.put("mail.smtp.port", PORT_NUMBER);
				properties.put("mail.smtp.auth", AUTH_FLAG);
				properties.put("mail.smtp.debug", "false");

				// Get the default Session object.
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(CommonLib.ZAPP_ID, CommonLib.ZAPP_PWD);
					}
				});

				// Enable the debugger for any logs
				session.setDebug(false);

				try {
					// Create a MimeMessage object.
					MimeMessage message = new MimeMessage(session);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(emailModel.getFrom()));

					// Set To: header field of the header.
					for (String sender : emailModel.getSenders()) {
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(sender));
					}

					// Set Subject: header field
					message.setSubject(emailModel.getSubject());

					// Set Content: header field
					message.setText(emailModel.getContent());

					// Let's try sending the object
					Transport.send(message);

				} catch (MessagingException mex) {
					mex.printStackTrace();
				} catch (Exception e) {
					try {
						throw new ZException("Error", e);
					} catch (ZException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		Thread newThread = new Thread(runnable);
		newThread.start();
	}

	/**
	 * Function - send a mail as per detailed in the model in a separate thread.
	 * 
	 * @param emailModel
	 */
	public void sendHtmlEmail(EmailModel emailModel) {

		Runnable runnable = new Runnable() {
			public void run() {
				try {
					HtmlEmail newemail = new HtmlEmail();
					newemail.setHostName("smtp.gmail.com");
					newemail.setSmtpPort(465);
					newemail.setAuthenticator(new DefaultAuthenticator(CommonLib.ZAPP_ID, CommonLib.ZAPP_PWD));
					newemail.setSSLOnConnect(true);
					newemail.setFrom(emailModel.getFrom());
					newemail.setSubject(emailModel.getSubject());
					newemail.setHtmlMsg(emailModel.getContent());
					for (String sender : emailModel.getSenders()) {
						newemail.addTo(sender);
					}
					newemail.send();
				} catch (EmailException mex) {
					mex.printStackTrace();
				} catch (Exception e) {
					try {
						throw new ZException("Error", e);
					} catch (ZException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		Thread newThread = new Thread(runnable);
		newThread.start();
	}

	// Content when a merchant sign up
	public static final String MERCHANT_SIGNUP_SUBJECT = "Welcome on Board @ Zapplon";

	public final String getMerchantSignupContent(String userName, String email, String password) {
		return String.format("Hello %s,\nGreetings from Zapplon !\nWelcome aboard.\n"
				+ "Please download the application from the url : https://play.google.com/store/apps/details?id=com.application.zapplonmerchant \n"
				+ "Please use the below credentials to login\n" + "Email: %s" + "Password: %s \n"
				+ "Zapplon is committed to drive your sales and increase revenues by giving you a platform to connect with on demand customers based on yield and demand - supply conditions.\n"
				+ "Zapplon markets the slow hours as innovative events to all the nearby online customers and gives effective visibility on various channels and social media platforms.\n"
				+ "You can also market your new and slow moving products to get more footfalls.\n"
				+ "The power is in your hands, so make maximum use of  Zapplon and drive up your sales in a Zapp !!!\n"
				+ "Regards,\n" + "Zapplon Team", userName, email, password);
	}

	// Content when a merchant deal is booked
	public static final String MERCHANT_BOOK_SUBJECT = "You got a customer lined-up";

	public final String getMerchantBookContent(String userName) {
		return String
				.format("Congrats !!,\n  %s had just bought an experience.\n\nPlease make the experience a great one."
						+ "Regards,\n" + "Zapplon Team", userName);
	}

	// Content when a user books a deal
	public static final String USER_BOOK_SUBJECT = "Experience booked via Zapplon";

	public final String getUserBookContent() {
		return String.format("Helios !!,\n  You just bought an awesome experience.\n\nHope you have a great one."
				+ "Regards,\n" + "Zapplon Team");
	}

	// Content when a user sign up
	public static final String USER_SIGNUP_SUBJECT = "Welcome to Zapplon";
	public static final String USER_BOOKING_SUBJECT = "Someone just made a booking";

	public final String getUserSignupContent(String userName) {
		return String.format("Hey %s,\n\nThanks for Signing up @ Zapplon !!!.\n " + "\nWith Zapplon you can -"
				+ "\n—> Choose among Uber, Ola, Taxi For Sure, Meru, Easy Cabs and more"
				+ "\n—> Book a cab without switching apps."
				+ "\n—> Ride as you wish - in comfort, on a budget, in the city or outstation", userName);
	}

	// Content when a user sends feedback
	public static final String USER_FEEDBACK_SUBJECT = "Splitc Android Application Feedback";
	public static final String USER_CONTACT_SUBJECT = "Splitc Web contact request";

	public final String getFeedbackContent(String user, String message, String log) {
		return String.format("Hi,\n You have a new feedback message from %s !\n\n" + "%s \n\n" + "%s", user, message, log);
	}

}