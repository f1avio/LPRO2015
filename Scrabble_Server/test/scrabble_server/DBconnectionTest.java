/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

import dBInterface.DbSetup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import junit.framework.TestCase;

/**
 * @author Flávio Dias
 */
public class DBconnectionTest extends TestCase {
    
    DBconnection instance = new DBconnection();
    DbSetup objecto = new DbSetup();
    /**
     * Constructor to the unit test of class DBconnection.
     * <p> Creates a new object of this class, and sets him up to operate under
     * test conditions.
     * @param testName The name of the class.
     */
    public DBconnectionTest(String testName) {
        super(testName);
        
        instance.setTest(true);
        
        objecto.setDB();
    }
    
    /**
     * Creates the necessary tables to perform the test.
     * <p>These tables are replicas from the ones used on the unit test for 
     * the classes belonging to the package dBInterface.
     * @throws Exception Throws any kind of exception.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
         String aux[] = objecto.getDB();
         
         //Creates the accounts table and fills it with some users.
         try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE SCHEMA test;");
            stmt.executeUpdate("CREATE TABLE test.accounts (username character varying(30) NOT NULL,"
                    + " password character varying(64) NOT NULL, isonline boolean NOT NULL, email character varying(64) NOT NULL,"
                    + " points integer DEFAULT 0 NOT NULL, admin boolean NOT NULL, state character varying(50) DEFAULT 'NORMAL'::character varying NOT NULL,"
                    + " wins integer DEFAULT 0 NOT NULL, loses integer DEFAULT 0 NOT NULL);");
            stmt.executeUpdate("INSERT INTO test.accounts VALUES ('Jeremias', 'a269d2326633e4ed0249bc440824c3d4', false, 'saldos@continente.pt', 10, false, 'NORMAL', 3, 4);");
            stmt.executeUpdate("INSERT INTO test.accounts VALUES ('Inacio', 'f2e3b1053b0515cb3e302901de0af857', TRUE, '400m@nos.pt', 12, false, 'NORMAL', 3, 6);");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating a table " +ex);
        }
         
        //Creates the room table and fills it with some rooms. 
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE test.room (maxplayers INTEGER NOT NULL DEFAULT 4, name VARCHAR(50) NOT NULL, players INTEGER NOT NULL DEFAULT 1, owner VARCHAR(50) NOT NULL, playernames text[] DEFAULT '{{NULL,NULL},{NULL,NULL},{NULL,NULL},{NULL,NULL}}'::text[], id INTEGER PRIMARY KEY);");
            stmt.executeUpdate("INSERT INTO test.room VALUES(3, 'testroom', 2, 'Jeremias', '{{Jeremias, Wait}, {Inacio, Wait}, {NULL, NULL}, {NULL, NULL}}', 1);");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating the room table " +ex);
        }
        
        //Creates the chat table and fills it with some chat messages.
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE test.chat (remetente character varying(50) NOT NULL, mensagem character varying(50) NOT NULL, data character varying(50) NOT NULL);");
           
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating the chat table " +ex);
        }
    }
    
    /**
     * Drops all previously created tables after the test is completed.
     * @throws Exception Exception Throws any kind of exception.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        
        String aux[] = objecto.getDB();
        
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate("DROP TABLE test.accounts;");
            stmt.executeUpdate("DROP TABLE test.room;");
            stmt.executeUpdate("DROP TABLE test.chat;");
            stmt.executeUpdate("DROP Schema test;");
            con.close();    
        } catch (SQLException ex) {
            System.out.println("Error droping a table " +ex);
        }
    }
    
    /**
     * Test of logUser method, of class DBconnection.
     */
    public void testLogUser() {
        System.out.println("logUser");
        String user = "Jeremias";
        String password = "a269d2326633e4ed0249bc440824c3d4";
        int expResult = 1;
        int result = instance.logUser(user, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of signUser method, of class DBconnection.
     */
    public void testSignUser() {
        System.out.println("signUser");
        String user = "Marcelo";
        String password = "BES";
        String email = "ferias@casaveraoricardosalgado.pt";
        int expResult = 1;
        int result = instance.signUser(user, password, email);
        assertEquals(expResult, result);
    }

    /**
     * Test of logoutUser method, of class DBconnection.
     */
    public void testLogoutUser() {
        System.out.println("logoutUser");
        String username = "Inacio";
        int expResult = 1;
        int result = instance.logoutUser(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of receiveRooms method, of class DBconnection.
     */
    public void testReceiveRooms() {
        System.out.println("receiveRooms");
        String expResult = "testroom&3&2&Jeremias/";
        String result = instance.receiveRooms();
        assertEquals(expResult, result);
    }

    /**
     * Test of receiveRoomPlayers method, of class DBconnection.
     */
    public void testReceiveRoomPlayers() {
        System.out.println("receiveRoomPlayers");
        String room = "testroom";
        String expResult = "[Jeremias, Inacio, NULL, NULL, Wait, Wait, NULL, NULL]";
        String result = Arrays.toString(instance.receiveRoomPlayers(room));
        assertEquals(expResult, result);
    }

    /**
     * Test of isHost method, of class DBconnection.
     */
    public void testIsHost() {
        System.out.println("isHost");
        String username = "Jeremias";
        boolean expResult = true;
        boolean result = instance.isHost(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of listChat method, of class DBconnection.
     */
    public void testListChat() {
        System.out.println("listChat");
        String usernameChat = "Inacio";
        String messageChat = "hello";
        boolean expResult = true;
        boolean result = instance.listChat(usernameChat, messageChat);
        assertEquals(expResult, result);
    }

    /**
     * Test of createRoom method, of class DBconnection.
     */
    public void testCreateRoom() {
        System.out.println("createRoom");
        int nPlayers = 4;
        String owner = "Marcelo";
        String roomName = "Patrocinado pela TVI";
        String expResult = "Patrocinado pela TVI";
        String result = instance.createRoom(nPlayers, owner, roomName);
        assertEquals(expResult, result);
    }

    /**
     * Test of join method, of class DBconnection.
     */
    public void testJoin() {
        System.out.println("join");
        String username = "Inacio";
        int ID = 666;
        String roomName = "testroom";
        int expResult = 1;
        int result = instance.join(username, ID, roomName);
        assertEquals(expResult, result);
    }

    /**
     * Test of quitRoom method, of class DBconnection.
     */
    public void testQuitRoom() {
        System.out.println("quitRoom");
        String username = "Inacio";
        String room = "testroom";
        String expResult = "USER";
        String result = instance.quitRoom(username, room);
        assertEquals(expResult, result);
    }

    /**
     * Test of roomState method, of class DBconnection.
     */
    public void testRoomState() {
        System.out.println("roomState");
        String username = "Jeremias";
        String room = "testroom";
        String expResult = "[Jeremias, Inacio, NULL, NULL, Ready, Wait, NULL, NULL]";
        String result = Arrays.toString(instance.roomState(username, room));
        assertEquals(expResult, result);
    }

    /**
     * Test of ranking method, of class DBconnection.
     */
    public void testRanking() {
        System.out.println("ranking");
        String expResult = "[2, 1/Inacio/12/3/6, 2/Jeremias/10/3/4]";
        String result = Arrays.toString(instance.ranking());
        assertEquals(expResult, result);
    }
}
