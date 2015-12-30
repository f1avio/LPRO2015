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
import java.util.Arrays;
import junit.framework.TestCase;
import static org.junit.Assert.assertNotEquals;
/**
 *
 * @author Flávio
 */
public class UsersTest extends TestCase {
    
    public UsersTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Users instance = new Users();
        String aux[] = instance.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate("CREATE TABLE test.accounts (username VARCHAR(30) PRIMARY KEY NOT NULL, password VARCHAR(64) NOT NULL, isonline BOOLEAN NOT NULL, email VARCHAR(50) NOT NULL, Points INTEGER DEFAULT 0 NOT NULL, admin BOOLEAN NOT NULL, state VARCHAR(50) DEFAULT 'NORMAL' NOT NULL);");
            stmt.executeUpdate("INSERT INTO test.accounts VALUES('Jeremias', 'a269d2326633e4ed0249bc440824c3d4', FALSE, 'saldos@continente', 10, FALSE, 'NORMAL');");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating a table " +ex);
        }
        
    }      
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Users instance = new Users();
        String aux[] = instance.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate("DROP TABLE test.accounts;");
            con.close();    
        } catch (SQLException ex) {
            System.out.println("Error droping a table " +ex);
        }
        
    }

    /**
     * Test of getDB method, of class Users.
     */
    public void testGetDB() {
        System.out.println("getDB");
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "[1513, jdbc:postgresql://vdbm.fe.up.pt/lpro1513, lpro1513, C4bhX7aai, null]";
        String result = Arrays.toString(instance.getDB());
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of usernameExist method, of class Users.
     */
    public void testUsernameExist() {
        System.out.println("usernameExist");
        String user = "Jeremias";
        Users instance = new Users();
        instance.setTest(true);
        boolean expResult = true;
        boolean result = instance.usernameExist(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertUser method, of class Users.
     */
    public void testInsertUser() {
        System.out.println("insertUser");
        String username = "Tom";
        String password = "Cruise";
        String mail = "shorty@short.com";
        Users instance = new Users();
        instance.setTest(true);
        boolean expResult = true;
        boolean result = instance.insertUser(username, password, mail);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class Users.
     */
    public void testGetPassword() {
        System.out.println("getPassword");
        String user = "Jeremias";
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "a269d2326633e4ed0249bc440824c3d4";
        String result = instance.getPassword(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of userActive method, of class Users.
     */
    public void testUserActive() {
        System.out.println("userActive");
        String username = "Inacio";
        boolean state = false;
        Users instance = new Users();
        instance.setTest(true);
        boolean expResult = true;
        boolean result = instance.userActive(username, state);
        assertEquals(expResult, result);
    }

    /**
     * Test of getActive method, of class Users.
     */
    public void testGetActive() {
        System.out.println("getActive");
        String user = "Jeremias";
        Users instance = new Users();
        instance.setTest(true);
        boolean expResult = false;
        boolean result = instance.getActive(user);
        assertEquals(expResult, result);

    }

    /**
     * Test of getState method, of class Users.
     */
    public void testGetState() {
        System.out.println("getState");
        String user = "Jeremias";
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "NORMAL";
        String result = instance.getState(user);
        assertEquals(expResult, result);

    }

    /**
     * Test of setState method, of class Users.
     */
    public void testSetState() {
        System.out.println("setState");
        String state = "Inacio";
        String user = "A-MAZING!";
        Users instance = new Users();
        instance.setTest(true);
        boolean expResult = false;
        boolean result = instance.setState(state, user);
        assertEquals(expResult, result);

    }

    /**
     * Test of getAdmin method, of class Users.
     */
    public void testGetAdmin() {
        System.out.println("getAdmin");
        String user = "admin";
        Users instance = new Users();
        instance.setTest(true);
        boolean expResult = false;
        boolean result = instance.getAdmin(user);
        assertEquals(expResult, result);

    }
    
}
