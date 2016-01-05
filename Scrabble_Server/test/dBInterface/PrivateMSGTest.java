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
 * @author Flávio Dias
 */
public class PrivateMSGTest extends TestCase {
    
    PrivateMSG instance = null;
    DbSetup obj = null;
    
    public PrivateMSGTest(String testName) {
        super(testName);
        
        instance = new PrivateMSG();
        instance.setTest(true);
        
        obj = new DbSetup();
        obj.setDB();
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        String aux[] = obj.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE SCHEMA test;");
            stmt.executeUpdate("CREATE TABLE test.private (message character varying NOT NULL, sender character varying NOT NULL,\n" +
                               "    receiver character varying NOT NULL, date character varying NOT NULL);");
            
            stmt.executeUpdate("INSERT INTO test.private VALUES ('ola', 'Jeremias', 'admin', '05/01/2016');");
            stmt.executeUpdate("INSERT INTO test.private VALUES ('123456789', 'admin', 'admin', '03/01/2016');");
            
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating a table " +ex);
        }    
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        
        String aux[] = obj.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate("DROP TABLE test.private;");
            stmt.executeUpdate("DROP Schema test;");
            con.close();    
        } catch (SQLException ex) {
            System.out.println("Error droping a table " +ex);
        }
    }

    /**
     * Test of addPrivate_MSG method, of class PrivateMSG.
     */
    public void testAddPrivate_MSG() {
        System.out.println("addPrivate_MSG");
        String user_msg = "Maslow";
        String friend_msg = "Grupo13 - LPRO";
        String texto = "if all you have is a hammer, everything looks like a nail";
        int expResult = 1;
        int result = instance.addPrivate_MSG(user_msg, friend_msg, texto);
        assertEquals(expResult, result);
    }

    /**
     * Test of PrivMsgList method, of class PrivateMSG.
     */
    public void testPrivMsgList() {
        System.out.println("PrivMsgList");
        String user = "admin";
        String expResult = "Jeremias£ola«admin£123456789«";
        String result = instance.PrivMsgList(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of PrivMsgListDelete method, of class PrivateMSG.
     */
    public void testPrivMsgListDelete() {
        System.out.println("PrivMsgListDelete");
        String user = "admin";
        boolean expResult = true;
        boolean result = instance.PrivMsgListDelete(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNrMsg method, of class PrivateMSG.
     */
    public void testGetNrMsg() {
        System.out.println("getNrMsg");
        String user = "admin";
        int expResult = 2;
        int result = instance.getNrMsg(user);
        assertEquals(expResult, result);
    }
    
}
