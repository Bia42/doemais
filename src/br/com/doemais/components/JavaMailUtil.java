package br.com.doemais.components;

import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.PreencodedMimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.codec.binary.Base64;

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
		String myAccountEmail = "doemais.contato@gmail.com";
		String password = "Nicom2113$";
		
	    Session session = Session.getInstance(prop,
	  	      new Authenticator() {
	    		@Override
	  	           protected PasswordAuthentication getPasswordAuthentication()
	  	           {
	  	                 return new PasswordAuthentication(myAccountEmail,
	  	                		password);
	  	           }
	  	      });
	    
	    Message message = prepareMessage3(session, myAccountEmail, recepient, corpo, imagem);
	
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
		    MimeMultipart corpo2 = new MimeMultipart("related");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Venha doar Sangue Conosco - Hemocentro Unicamp");
			message.setText(corpo);
			
			String body = imagem.replace("data:image/png;base64","");             
			
			MimeBodyPart filePart = new PreencodedMimeBodyPart("base64");
			filePart.setFileName("screenshot.png");
			//This is Needed if you want to show as an html element in the page
			filePart.setHeader("Content-ID", "<screenshot>");
			filePart.setText(body);
			corpo2.addBodyPart(filePart);
			message.setContent(corpo2);

			return message;
		}catch(Exception ex) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		return null;
	}
 
    
	private static Message prepareMessage2(Session session, String myAccountEmail, String recepient, String corpo, String imagem) {
		try {
		    MimeMultipart corpo2 = new MimeMultipart("related");


			MimeBodyPart mbp1 = new MimeBodyPart();
			
			String body = imagem.replace("data:image/png;base64","");             
			
			MimeBodyPart filePart = new PreencodedMimeBodyPart("base64");
			filePart.setFileName("image.png");
			//This is Needed if you want to show as an html element in the page
			filePart.setHeader("Content-ID", "<image>");
			filePart.setText(body);
			filePart.setDisposition("inline");  
					    
			if(corpo2 == null)
			   corpo2 = new MimeMultipart("alternative");
					    
			corpo2.addBodyPart(filePart); 
					    
			String tag_imagem = "<img alt='Image' src='cid:image' />";

			String htmlText = tag_imagem + "<br><br>" + "Teste" + "<br>"
								+ "teste2";

			mbp1.setContent(htmlText, "text/html");
			corpo2.addBodyPart(filePart); 

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
	private static Message prepareMessage3(Session session, String myAccountEmail, String recepient, String corpo, String imagem) {
		try {
		    MimeMultipart corpo2 = new MimeMultipart("related");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Venha doar Sangue Conosco - Hemocentro Unicamp");
			message.setText(corpo);
			
			String body = imagem.replace("data:image/png;base64","");             
			
			MimeMultipart multipart = new MimeMultipart("related");
	        BodyPart messageBodyPart = new MimeBodyPart();
	        String htmlText = "<p></p> <h4>" + corpo +"</h4>" + "<img src=\"cid:image\">";
	        messageBodyPart.setContent(htmlText, "text/html");
	        multipart.addBodyPart(messageBodyPart);
	        try {
	            messageBodyPart = new MimeBodyPart();
	            byte[] imgBytes = Base64.decodeBase64(body);
	            ByteArrayDataSource dSource = new ByteArrayDataSource(imgBytes, "image/*");
	            messageBodyPart.setDataHandler(new DataHandler(dSource));
	            messageBodyPart.setHeader("Content-ID","<image>");
	            multipart.addBodyPart(messageBodyPart);
	            message.setContent(multipart);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

			return message;
		}catch(Exception ex) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		return null;
	}
}
