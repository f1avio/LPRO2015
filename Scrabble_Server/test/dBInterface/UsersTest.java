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

/**
 * @author Flávio Dias <ee11160@fe.up.pt>
 */
public class UsersTest extends TestCase {
    
        DbSetup obj = new DbSetup();
    /**
     * Constructor of the unit test for the class Users.
     * @param testName The name of the test. 
     */    
    public UsersTest(String testName) {
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
            stmt.executeUpdate("CREATE SCHEMA test;");
            stmt.executeUpdate("CREATE TABLE test.accounts (username character varying(30) NOT NULL,"
                    + " password character varying(64) NOT NULL, isonline boolean NOT NULL, email character varying(64) NOT NULL,"
                    + " points integer DEFAULT 0 NOT NULL, admin boolean NOT NULL, state character varying(50) DEFAULT 'NORMAL'::character varying NOT NULL,"
                    + " wins integer DEFAULT 0 NOT NULL, loses integer DEFAULT 0 NOT NULL);");
            
            stmt.executeUpdate("INSERT INTO test.accounts VALUES ('Jeremias', 'a269d2326633e4ed0249bc440824c3d4', false,"
                    + " 'saldos@continente.pt', 10, false, 'NORMAL', 3, 4);");
            
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating a table " +ex);
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
            
            stmt.executeUpdate("DROP TABLE test.accounts;");
            stmt.executeUpdate("DROP Schema test;");
            con.close();    
        } catch (SQLException ex) {
            System.out.println("Error droping a table " +ex);
        }
        
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

    /**
     * Test of getRegistedPlayers method, of class Users.
     */
    public void testGetRegistedPlayers() {
        System.out.println("getRegistedPlayers");
        Users instance = new Users();
        instance.setTest(true);
        int expResult = 1;
        int result = instance.getRegistedPlayers();
        assertEquals(expResult, result);

    }

    /**
     * Test of getUsername method, of class Users.
     */
    public void testGetUsername() {
        System.out.println("getUsername");
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "[Jeremias]";
        String result = Arrays.toString(instance.getUsername());
        assertEquals(expResult, result);
    }

    /**
     * Test of getPoints method, of class Users.
     */
    public void testGetPoints() {
        System.out.println("getPoints");
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "[10]";
        String result = Arrays.toString(instance.getPoints());
        assertEquals(expResult, result);
    }

    /**
     * Test of getWins method, of class Users.
     */
    public void testGetWins() {
        System.out.println("getWins");
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "[3]";
        String result = Arrays.toString(instance.getWins());
        assertEquals(expResult, result);
    }

    /**
     * Test of getLoses method, of class Users.
     */
    public void testGetLoses() {
        System.out.println("getLoses");
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "[4]";
        String result = Arrays.toString(instance.getLoses());
        assertEquals(expResult, result);
    }

    /**
     * Test of changes method, of class Users.
     */
    public void testChanges() {
        System.out.println("changes");
        String pass = "password";
        String email = "charliebrown@depressed.com";
        String username = "Jeremias";
        Users instance = new Users();
        instance.setTest(true);
        int expResult = 2;
        int result = instance.changes(pass, email, username);
        assertEquals(expResult, result);

    }

    /**
     * Test of getUsernameList method, of class Users.
     */
    public void testGetUsernameList() {
        System.out.println("getUsernameList");
        Users instance = new Users();
        instance.setTest(true);
        String expResult = "Jeremias/";
        String result = instance.getUsernameList();
        assertEquals(expResult, result);
    }
}
