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
    
    public void setTest(boolean testConfigured)
    {
        this.testConfigured = testConfigured;
    }
    
    public Room(){
        this.testConfigured = false;
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            System.out.println("Erro: Users()");
        }        
    }
    
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
    
    public String getRooms(){
        String[] aux = getDB();    
        String rooms = "";
        
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM scrabble.room");
            while (rs.next()) {
                rooms = rooms + rs.getString("name") + "&"+ rs.getString("maxplayers") + "&" + rs.getString("players") + "&" + rs.getString("owner")+"/";
            }
            con.close();          
        } catch (SQLException ex) {
            System.out.println("getRoom() "+ ex);
        }        
        return rooms;   
    }
    
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
    
    public int createDBRoom(int nPlayers, String roomName, String owner){
        String sql;
        int roomID = serverFull() + 1;
        try{
            String[] aux = getDB();
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            sql = "INSERT INTO scrabble.room (maxplayers, name, players, owner, id) VALUES (" 
                    + nPlayers + ", '" + roomName + "', 0, '" + owner + "', "+roomID+");";
            
            stmt.executeUpdate(sql);
            con.close();
            return roomID;
            
        } catch (SQLException ex) {
            System.out.println("createDBRoom() "+ex);
            return -1;
        }
    }
    
    
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
            
            if(players < maxPlayers)
                return false;
            else
                return true;
        }
        catch(SQLException ex)
        {
            System.out.println("isRoomFull() " +ex);
            return false;
        }  
    }
    
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
