/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

import junit.framework.TestCase;

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
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getDB method, of class Users.
     */
    public void testGetDB() {
        System.out.println("getDB");
        Users instance = new Users();
        String[] expResult = null;
        String[] result = instance.getDB();
        assertEquals(expResult, result);
    }

    /**
     * Test of usernameExist method, of class Users.
     */
    public void testUsernameExist() {
        System.out.println("usernameExist");
        String user = "Jeremias";
        Users instance = new Users();
        boolean expResult = false;
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
        boolean expResult = false;
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
        boolean expResult = false;
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
        boolean expResult = false;
        boolean result = instance.getActive(user);
        assertEquals(expResult, result);

    }
    /**
     * Test of getTables method, of class Users.
     */
    public void testGetTables() {
        System.out.println("getTables");
        Users instance = new Users();
        String expResult = "algo";
        String result = instance.getRoom();
        assertEquals(expResult, result);

    }

    /**
     * Test of getState method, of class Users.
     */
    public void testGetState() {
        System.out.println("getState");
        String user = "Jeremias";
        Users instance = new Users();
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
        boolean expResult = false;
        boolean result = instance.getAdmin(user);
        assertEquals(expResult, result);

    }
    
}
