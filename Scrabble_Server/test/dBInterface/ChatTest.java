/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import junit.framework.TestCase;

/**
 * @author Flávio Dias <ee11160@fe.up.pt>
 */
public class ChatTest extends TestCase {
        DbSetup obj = new DbSetup();
        
    /**
     * Constructor of the unit test for the class Chat.
     * @param testName The name of the test. 
     */
    public ChatTest(String testName) {
        super(testName);
        obj.setDB();
    }
    
    /**
     * Creates a test table on the database and fills in some tuples.
     * @throws Exception Throws any kind of exception.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String aux[] = obj.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE Schema test");
            stmt.executeUpdate("CREATE TABLE test.chat (remetente character varying(50) NOT NULL, mensagem character varying(50) NOT NULL, data character varying(50) NOT NULL);");
           
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating the chat table " +ex);
        }
    }
    
    /**
     * Drops the the table and the schema after the test has finished.
     * @throws Exception Throws any kind of exception. 
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        String aux[] = obj.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate("DROP TABLE test.chat;");
            stmt.executeUpdate("DROP Schema test;");
            con.close();    
        } catch (SQLException ex) {
            System.out.println("Error droping the table chat " +ex);
        }
    }

    /**
     * Test of addChat_MSG method, of class Chat.
     */
    public void testAddChat_MSG() {
        System.out.println("addChat_MSG");
        String usernameChat = "Inacio";
        String messageChat = "something something";
        Chat instance = new Chat();
        instance.setTest(true);
        boolean expResult = true;
        boolean result = instance.addChat_MSG(usernameChat, messageChat);
        assertEquals(expResult, result);
    }
    
}
