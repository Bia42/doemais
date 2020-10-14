package br.com.doemais.components;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class JavaMailApp {
	public static void main(String[] args) throws GeneralSecurityException {
	    Properties props = new Properties();
	    
	    /** Parâmetros de conexão com servidor Gmail */
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class",
	    "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	     props.put("mail.smtps.ssl.trust", "*");
	     MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
	     socketFactory.setTrustAllHosts(true);
	     props.put("mail.smtp.ssl.socketFactory", socketFactory);
	    
	    Session session = Session.getDefaultInstance(props,
	      new javax.mail.Authenticator() {
	           protected PasswordAuthentication getPasswordAuthentication()
	           {
	                 return new PasswordAuthentication("beatrizo237@gmail.com",
	                 "nicom2113$");
	           }
	      });

	    /** Ativa Debug para sessão */
	    session.setDebug(true);

	    try {

	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("beatrizo237@gmail.com"));
	      //Remetente

	      Address[] toUser = InternetAddress.parse("beatrizo237@gmail.com");

	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject("Enviando email com JavaMail");//Assunto
	      message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
	      /**Método para enviar a mensagem criada*/
	      Transport.send(message);

	      System.out.println("Feito!!!");

	     } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	  }
}
