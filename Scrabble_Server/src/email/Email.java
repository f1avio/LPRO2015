/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * @author Flávio Dias
 * @version 2.0
 */
public class Email {
   
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
    /**
     * Due to security reasons, the user will be stored on a config file
     * that will NEVER UPLOADED TO THE REPOSITORY.
     */
    private static String user = null;
    
    /**
     * Due to security reasons, the password will be stored on a config file
     * that will NEVER UPLOADED TO THE REPOSITORY.
     */
    private static String password = null;
    
    /**
     * Sets sensitive account details, so that the server is able to send emails
     * to the users that attempt to register.
     * @param username The username of the gmail account.
     * @param password The password of the gmail account.
     */
    public static void setAccountDetails(String username, String password)
    {
        Email.user = username;
        Email.password = password;
    }
    
    /**
     * Sets up the gmail server properties.
     */    
    void setupMailServer()
    {
        System.out.println("\n 1st ===> setup Mail Server Properties..");
	mailServerProperties = System.getProperties();
	mailServerProperties.put("mail.smtp.port", "587");
	mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.user", user);
        mailServerProperties.setProperty("mail.smtp.password", password);

	System.out.println("Mail Server Properties have been setup successfully..");
    }
    
    /**
     * Creates a new mail session
     * @param email The email address of the user.
     * @param subject The subject of the email.
     * @param content The content of the email.
     */
    boolean getMailSession(String email, String subject, String content)
    {
        try {
            System.out.println("\n\n 2nd ===> get Mail Session..");
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.setSender(new InternetAddress(user));
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(""));
            generateMailMessage.setSubject(subject);
            //String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
            generateMailMessage.setContent(content, "text/html");
            System.out.println(Arrays.toString(generateMailMessage.getRecipients(Message.RecipientType.TO)));
            System.out.println("Mail Session has been created successfully..");
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Sends the email created on the getMailSession class.
     */
    boolean sendMail(){
        try {
            System.out.println("\n\n 3rd ===> Get Session and Send mail");
            Transport transport = getMailSession.getTransport("smtp");
            
            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", user, password);
            System.out.println("Sending the message!");
            transport.sendMessage(generateMailMessage, generateMailMessage.getRecipients(Message.RecipientType.TO));
            transport.close();
            return true;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }       
    }
}
