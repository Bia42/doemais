package br.com.doemais.components;

import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.util.MailSSLSocketFactory;

public class JavaMailUtil {
	public static void sendMail(String recepient, String corpo, String imagem) throws GeneralSecurityException {
		
		System.out.println("Preparing to sendo email");
		Properties prop = new Properties();
		
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		  MailSSLSocketFactory sf = new MailSSLSocketFactory();
		  sf.setTrustAllHosts(true);
		  prop.put("mail.smtp.ssl.socketFactory", sf);
		String myAccountEmail = "beatrizo237@gmail.com";
		String password = "nicom2113$";
		
	    Session session = Session.getInstance(prop,
	  	      new Authenticator() {
	    		@Override
	  	           protected PasswordAuthentication getPasswordAuthentication()
	  	           {
	  	                 return new PasswordAuthentication(myAccountEmail,
	  	                		password);
	  	           }
	  	      });
	    
	    Message message = prepareMessage(session, myAccountEmail, recepient, corpo, imagem);
	
	    try {
			Transport.send(message);
			System.out.println("Message sent!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String corpo, String imagem) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("My first email");
			message.setText(corpo);
			return message;
		}catch(Exception ex) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		return null;
	}
	
	private static Message prepareMessage2(Session session, String myAccountEmail, String recepient, String corpo, String imagem) {
		try {
		    MimeMultipart corpo2 = new MimeMultipart("alternative");

			MimeBodyPart mbp1 = new MimeBodyPart();

			byte[] bytes = new String(imagem.substring(imagem.indexOf(",") + 1)).getBytes("UTF-8");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
			baos.write(bytes, 0, bytes.length);
						
			MimeBodyPart imagePart = new MimeBodyPart();   
			// Loading the image   
			DataSource ds = new ByteArrayDataSource(baos.toByteArray(), "image/png");   
			imagePart.setDataHandler(new DataHandler(ds));   
			imagePart.setFileName("selo.png");   
			// Setting the header   
			imagePart.setHeader("Content-ID", "<image>");   
			imagePart.setDisposition("inline");  
					    
			if(corpo2 == null)
			   corpo2 = new MimeMultipart("alternative");
					    
			corpo2.addBodyPart(imagePart); 
					    
			String tag_imagem = "<img alt='Selo' src='cid:image' />";

			String htmlText = tag_imagem + "<br><br>" + "Teste" + "<br>"
								+ "teste2";

			mbp1.setContent(htmlText, "text/html");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("My first email");
			message.setContent(corpo2);
			return message;
		}catch(Exception ex) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		return null;
	}
}
