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
 *
 * @author Flávio Dias <ee11160@fe.up.pt>
 */
public class RoomTest extends TestCase {
    
    DbSetup obj = new DbSetup();
    /**
     * Constructor of the unit test for the class Room.
     * @param testName The name of the test. 
     */
    public RoomTest(String testName) {
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
            stmt.executeUpdate("CREATE TABLE test.room (maxplayers INTEGER NOT NULL DEFAULT 4, name VARCHAR(50) NOT NULL, players INTEGER NOT NULL DEFAULT 1, owner VARCHAR(50) NOT NULL, playernames text[] DEFAULT '{{NULL,\"Ready\"},{NULL,NULL},{NULL,NULL},{NULL,NULL}}'::text[], id INTEGER PRIMARY KEY);");
            stmt.executeUpdate("INSERT INTO test.room VALUES(3, 'testroom', 2, 'Jeremias', '{{Jeremias, Activo}, {Inacio, Activo}, {NULL, NULL}, {NULL, NULL}}', 666);");
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error creating the room table " +ex);
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
            
            stmt.executeUpdate("DROP TABLE test.room;");
            stmt.executeUpdate("DROP Schema test;");
            con.close();    
        } catch (SQLException ex) {
            System.out.println("Error droping the table room " +ex);
        }
    }
    /**
     * Test of getRooms method, of class Room.
     */
    public void testGetRooms() {
        System.out.println("getRooms");
        Room instance = new Room();
        instance.setTest(true);
        String expResult = "testroom&3&2&Jeremias/";
        String result = instance.getRooms();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getHost method, of class Room.
     */
    public void testGetHost() {
        System.out.println("getOwner");
        String username = "Jeremias";
        Room instance = new Room();
        instance.setTest(true);
        boolean expResult = true;
        boolean result = instance.getHost(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of serverFull method, of class Room.
     */
    public void testServerFull() {
        System.out.println("serverFull");
        Room instance = new Room();
        instance.setTest(true);
        int expResult = 1;
        int result = instance.serverFull();
        assertEquals(expResult, result);
    }

    /**
     * Test of createDBRoom method, of class Room.
     */
    public void testCreateDBRoom() {
        System.out.println("createDBRoom");
        int nPlayers = 4;
        String roomName = "ChineseRoom";
        String owner = "Sun Tzu";
        Room instance = new Room();
        instance.setTest(true);
        int expResult = 2;
        int result = instance.createDBRoom(nPlayers, roomName, owner);
        assertEquals(expResult, result);
    }

    /**
     * Test of isRoomFull method, of class Room.
     */
    public void testIsRoomFull() {
        System.out.println("isRoomFull");
        String room = "testroom";
        Room instance = new Room();
        instance.setTest(true);
        boolean expResult = false;
        boolean result = instance.isRoomFull(room);
        assertEquals(expResult, result);
    }

    /**
     * Test of addPlayerRoom method, of class Room.
     */
    public void testAddPlayerRoom() {
        System.out.println("addPlayerRoom");
        String room = "testroom";
        String username = "Alberto";
        Room instance = new Room();
        instance.setTest(true);
        int expResult = 666;
        int result = instance.addPlayerRoom(room, username);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateRoomState method, of class Room.
     */
    public void testUpdateRoomState() {
        System.out.println("updateRoomState");
        int index = 1;
        String state = " WHAT?";
        String room = "testroom";
        Room instance = new Room();
        instance.setTest(true);
        boolean expResult = true;
        boolean result = instance.updateRoomState(index, state, room);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRoomPlayers method, of class Room.
     */
    public void testGetRoomPlayers() {
        System.out.println("getRoomPlayers");
        String room = "testroom";
        Room instance = new Room();
        instance.setTest(true);
        String expResult = "JeremiasInacio";
        String result = instance.getRoomPlayers(room);
    }

    /**
     * Test of getRoomStatus method, of class Room.
     */
    public void testGetRoomStatus() {
        System.out.println("getRoomStatus");
        String room = "testroom";
        Room instance = new Room();
        instance.setTest(true);
        String expResult = "{{Activo},{Activo},{NULL},{NULL}}";
        String result = instance.getRoomStatus(room);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteRoom method, of class Room.
     */
    public void testDeleteRoom() {
        System.out.println("deleteRoom");
        String username = "Inacio";
        Room instance = new Room();
        instance.setTest(true);
        String expResult = "OK";
        String result = instance.deleteRoom(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of qRoom method, of class Room.
     */
    public void testQRoom() {
        System.out.println("qRoom");
        String[] players = {"Jeremias", "Activo", null, null, null, null, null, null};
        String room = "testroom";
        Room instance = new Room();
        instance.setTest(true);
        String expResult = "OK";
        String result = instance.qRoom(players, room);
        assertEquals(expResult, result);
    }
}
