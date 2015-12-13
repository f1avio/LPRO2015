/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Flávio
 */
public class DBconnectionTest {
    
    public DBconnectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("connect");
        String url = "jdbc:postgresql://vdbm.fe.up.pt/lpro1513";
        String username = "lpro1513";
        String password = "C4bhX7aai";
        boolean expResult = true;
        boolean result = DBconnection.connect(url, username, password);
        assertEquals(expResult, result);
    }
    
    @After
    public void tearDown() {
        System.out.println("dBClose");
        boolean expResult = true;
        boolean result = DBconnection.dBClose();
        assertEquals(expResult, result);
    }

    /**
     * Test of initialized method, of class DBconnection.
     */
    @Test
    public void testInitialized() {
        System.out.println("initialized");
        boolean expResult = true;
        boolean result = DBconnection.initialized();
        assertEquals(expResult, result);
    }

    /**
     * Test of searchUser method, of class DBconnection.
     */
    @Test
    public void testSearchUser() {
        System.out.println("searchUser");
        String table = "accounts";
        String username = "Jeremias";
        DBconnection instance = new DBconnection();
        boolean expResult = true;
        boolean result = instance.searchUser(table, username);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of searchPassword method, of class DBconnection.
     */
    @Test
    public void testSearchPassword() {
        System.out.println("searchPassword");
        String table = "accounts";
        String username = "Inacio";
        String password = "f2e3b1053b0515cb3e302901de0af857";
        DBconnection instance = new DBconnection();
        boolean expResult = true;
        boolean result = instance.searchPassword(table, username, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of logUser method, of class DBconnection.
     */
    @Test
    public void testLogUser() {
        System.out.println("logUser");
        String table = "accounts";
        String username = "Jeremias";
        String password = "a269d2326633e4ed0249bc440824c3d4";
        DBconnection instance = new DBconnection();
        int expResult = 0;
        int result = instance.logUser(table, username, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of signUser method, of class DBconnection.
     */
    @Test
    public void testSignUser() {
        System.out.println("signUser");
        String table = "accounts";
        String username = "Bulbasaur";
        String password = "Ivysaur";
        String email = "Venosaur@pkmn.com";
        DBconnection instance = new DBconnection();
        int expResult = 0;
        int result = instance.signUser(table, username, password, email);
        assertEquals(expResult, result);
    }

    /**
     * Test of isOnline method, of class DBconnection.
     */
    @Test
    public void testIsOnline() {
        System.out.println("isOnline");
        String table = "accounts";
        String username = "Jeremias";
        DBconnection instance = new DBconnection();
        boolean expResult = true;
        boolean result = instance.isOnline(table, username);
        assertEquals(expResult, result);
    }

    /**
     * Test of logoutUser method, of class DBconnection.
     */
    @Test
    public void testLogoutUser() {
        System.out.println("logoutUser");
        String table = "accounts";
        String username = "Jeremias";
        DBconnection instance = new DBconnection();
        int expResult = 0;
        int result = instance.logoutUser(table, username);
        assertEquals(expResult, result);
    }
        
}
