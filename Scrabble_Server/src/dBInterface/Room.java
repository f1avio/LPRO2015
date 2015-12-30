/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HUGUETA
 */
public class Room {
    
    private boolean testConfigured;
    /**
     * Configures the object to work with a specified schema.
     * <p>
     * Besides it's real use, the object can be used to apply some 
     * tests. To do so, a boolean variable is modified and passed to this
     * method.
     * @param testConfigured Specifies if it is a test situation or not 
     */
    public void setTest(boolean testConfigured)
    {
        this.testConfigured = testConfigured;
    }
     /**
     * Construtor that configures the PostgreSql Driver.
     */
    public Room(){
        this.testConfigured = false;
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            System.out.println("Erro: Room()");
        }        
    }
    /**
     * Seeks the necessary parameters to connect to the database.
     * <p>
     * These parameters reside on a document file named config.txt that provides
     * the port, the path and username and password.
     * @return an array of strings with the parameters mentioned on the description
     * stored.
     */
    public String[] getDB(){
        /*Step 0: Initialize the files*/
        BufferedReader inputStream = null;
        int i = 0;
        String aux[] = new String[5];
        String file = "config.txt";
        try {
            inputStream = new BufferedReader(new FileReader(file));
            while ((aux[i] = inputStream.readLine()) != null) {
                i++;
            }
        }catch (FileNotFoundException f)
              {
            System.err.println("Caught FileNotFoundException: " + f.getMessage());
            } 
       
        catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            }  
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    System.err.println("Caught IOException: " + ex.getMessage());
                }
            }
        }
        return aux;
    }
    /**
     * Retrieves all the tuples of the "Rooms" table. 
     * @return A string if all the tuples specified.
     */
    public String getRooms(){
        String[] aux = getDB();    
        String rooms = "";
        
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM scrabble.room");
            while (rs.next()) {
                rooms = rooms + rs.getString("name") + "&"+ rs.getString("maxplayers") + "&" + rs.getString("players") + "&" + rs.getString("owner")+"/";
            }
        } catch (SQLException ex) {
            System.out.println("getRoom() "+ ex);
        }        
        return rooms;   
    }
    /**
     * Verifies if the specified user is the owner of a room.
     * @param username The user that will be verified.
     * @return A boolean stating either the user is a room owner or not.
     */
    public boolean getOwner(String username){
        boolean owner = false;
        String[] aux = getDB();
        String query;
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
        Statement stmt = con.createStatement();
        if(!testConfigured)
            query = "SELECT * FROM scrabble.room";
        else
            query = "SELECT * FROM test.room";
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            if (rs.getString("owner").equals(username)){
                    owner = true;
                    break;
                }
        }
        }catch (SQLException ex) {
            System.out.println("getOwner() "+ ex);
        }
        return owner;
    }
    /**
     * Counts the amount of rooms on the server
     * @return The amount of rooms
     */
    public int serverFull(){
        String[] aux = getDB();
        int i=0;
        String query;
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
               query = "SELECT * FROM scrabble.room";
            else
                query = "SELECT * FROM test.room";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                i++;
            }
            //System.out.println("serverFull() " + "#Rooms: " + i);
        }
        catch(SQLException ex)
        {
            System.out.println("[Server][Users]" +ex);
        }
        
        return i ;  
    }
    /**
     * Creates a new room on the database.
     * @param nPlayers The number of players that can participate.
     * @param roomName The room's name.
     * @param owner The user that takes the initiative to create the room.
     * @return the ID of the new room.
     */
    public int createDBRoom(int nPlayers, String roomName, String owner){
        String sql;
        int roomID = serverFull() + 1;
        String[] aux = getDB(); //Changed this position
        try(Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3])) {
            Statement stmt = con.createStatement();
            sql = "INSERT INTO scrabble.room (maxplayers, name, players, owner, id) VALUES (" 
                    + nPlayers + ", '" + roomName + "', 0, '" + owner + "', "+roomID+");";
            
            stmt.executeUpdate(sql);
            
        } catch (SQLException ex) {
            System.out.println("createDBRoom() "+ex);
            return -1;
        }
        return roomID;
    }
    
    /**
     * Checks if the specified room is full.
     * @param room The room's name that will be verified.
     * @return A boolean stating either the room is full or not.
     */
    public boolean isRoomFull(String room){
        int players = 0;
        int maxPlayers = 0;
        String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT players FROM scrabble.room WHERE name ='" + room + "'");
            
            if (rs.next()) {
                players = rs.getShort("players");
            }
            ResultSet rs1 = stmt.executeQuery("SELECT maxplayers FROM scrabble.room WHERE name ='" + room + "'");
            if (rs1.next()) {
                maxPlayers= rs1.getInt("maxplayers");
            }
            
            return players >= maxPlayers;
        }
        catch(SQLException ex)
        {
            System.out.println("isRoomFull() " +ex);
            return false;
        }  
    }
    /**
     * Adds a player to a room.
     * @param room The room that the new player will be added.
     * @param username The user username's that will be added.
     * @return the ID of the room if successful. -1 if not.
     */
    public int addPlayerRoom(String room, String username){
        int players = 0;
        int roomID = 0;
        String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM scrabble.room WHERE name = '" + room + "'");
            if(rs.next()){
                players = rs.getShort("players");
            }
            players = players + 1;
            stmt.executeUpdate("UPDATE scrabble.room SET players = " + players + " WHERE name = " + "'" + room + "'");
            stmt.executeUpdate("UPDATE scrabble.room SET playernames["+players+"][1] = '"+ username +"' WHERE name = '" + room + "'");
            stmt.executeUpdate("UPDATE scrabble.room SET playernames["+players+"][2] = 'Wait' WHERE name = '" + room + "'");
            rs = stmt.executeQuery("SELECT * FROM scrabble.room WHERE name = '" + room + "'");
            if(rs.next()){
                roomID = rs.getInt("id");
            }
        }catch(SQLException ex){
            System.out.println("addPlayerRoom() " +ex);
            return -1;
        }
        return roomID;
    }
    /**
     * Updates the state of a player at specified room.
     * @param index the position of the player whose statel will be changed.
     * @param state the new state of the player.
     * @param room The room where the player is.
     * @return A boolean stating if the operation was successful or not.
     */
    public boolean updateRoomState(int index, String state, String room){
        String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE scrabble.room SET playernames["+index+"][2] = '"+state+"' WHERE name = '" + room + "'");
        }catch(SQLException ex){
            System.out.println("updateRoomState() " +ex);
            return false;
        }
        return true;
    }
    /**
     * Retrieves the players names on a certain Room.
     * @param room The room where the player names will be retrieved.
     * @return A string if a all the players on the room.
     */
    public String getRoomPlayers(String room){
        String[] aux = getDB();
        String players = "";
        
        try {
            
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT playernames[1:4][1:1] FROM scrabble.room WHERE name = '"+room+"'");
            
            while(rs.next()){
                players = players + rs.getString(1);
            }      
        }catch(SQLException ex){
            System.out.println("getRoomPlayers() " +ex);
        }
        //System.out.println("getRoomPlayers(): "+players);
        return players;  
    }
    /**
     * Retrieves the status of all the participating players of a certain room.
     * @param room The room where all players are on.
     * @return A string with the status of all the players.
     */
    public String getRoomStatus(String room){
        String[] aux = getDB();
        String status = "";
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT playernames[1:4][2:2] FROM scrabble.room WHERE name = '"+room+"'");
            while(rs.next()){
                status = status + rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.println("getRoomStatus() " +ex);
        }
        //System.out.println("getRoomStatus(): "+status);
        return status;
    }
    /**
     * Deletes a room from a database.
     * @param username The owner's username.
     * @return either the operation was succesfful or not.
     */
    public String deleteRoom(String username){
        String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM scrabble.room WHERE owner = '" + username + "'");
        } catch (SQLException ex) {
            System.out.println("deleteRoom() " +ex);
            return "ERROR";
        }
        return "OK";
    }
    //Don't have a clue
    public String qRoom(String[] players, String room){
        String[] aux = getDB();
        String msg = "'{{"+players[0]+","+players[4]+"},{"+players[1]+","+players[5]+"},{"+players[2]+","+players[6]+"},{"
                + players[3]+","+players[7]+"}}'";
        String sql = "UPDATE scrabble.room SET playernames[1:4][1:2] =" + msg + "WHERE name = '" + room + "'";
        int numplayers = 0;
        
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery("SELECT * FROM scrabble.room WHERE name ='" + room + "'");
            if (rs.next()) {
                    numplayers = rs.getShort("players");
            }
            numplayers = numplayers - 1;
            stmt.executeUpdate("UPDATE scrabble.room SET players =" + numplayers + " WHERE name =" + "'" + room + "'");
            
        } catch (SQLException ex) {
            System.out.println("qRoom() " +ex);
            return "ERROR";
        }
        return "OK";
    }
    
    
}
