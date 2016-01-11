/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import junit.framework.TestCase;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import scrabble_server.ScrabbleServer;

/**
 * @author Flávio Dias
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailTest extends TestCase {
    
    Email instance = null;
    
    public EmailTest(String testName) {
        super(testName);
        
        //We need to copy here the setEmailCredentials to here.
        ScrabbleServer.setEmailCredentials();
        
        instance =  new Email();
        instance.setupMailServer();
    }

    /**
     * Test of getMailSession method, of class Email.
     */
    public void testGetMailSession() {
        System.out.println("getMailSession");
        String email = "flavio_dias@live.com.pt";
        String subject = "Teste do Java";
        String content = "Test email by Crunchify.com JavaMail API example. \" + \"<br><br> Regards, <br>Crunchify Admin";
        Boolean expResult = true;
        Boolean result = instance.getMailSession(email, subject, content);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendMail method, of class Email.
     */
    public void testSendMail() {
        System.out.println("sendMail");
        Boolean expResult = true;
        Boolean result = instance.sendMail();
        assertEquals(expResult, result);
    }
    
}
