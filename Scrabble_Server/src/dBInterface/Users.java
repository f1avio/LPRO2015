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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HUGUETA
 */
public class Users {
    
    
    public Users(){
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
            
    public boolean usernameExist(String user){
        boolean exist = false;
        String[] aux = getDB();
        try{
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"accounts\"");
            while(rs.next()){
                if(rs.getString("username").equals(user)){
                    exist = true;
                    break;
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("usernameExist() " +ex);
        }
        return exist;
    }
    
    public boolean insertUser(String username, String password, String email) {
        boolean state = false;
        String sql="";
        String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            sql = "INSERT INTO scrabble.accounts VALUES('"+ username + "', '" + password + "', FALSE ,'" + email + "', 0, FALSE, 'NORMAL');";
            stmt.executeUpdate(sql);
            state = true;

            con.close();

        } catch (SQLException ex) {
            System.out.println("insertUser() " +ex);
        }

        return state;
    }
    
    public String getPassword(String user) {
        String[] aux = getDB();
        String state = "PasswordNotFound";
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"accounts\"");
            while (rs.next()) {
                if (rs.getString("username").equals(user)) {
                    state = rs.getString("password");
                    break;
                }

            }

            con.close();
        } catch (SQLException ex) {
            state = "SQL EXCEPTION";
        }
        return state;
    }
    
    public boolean userActive(String username, boolean state) {
        String[] aux = getDB();
        boolean ret = false;
        
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE \"scrabble\".\"accounts\" SET \"isonline\"='" + state + "' WHERE \"username\"='" + username + "'");
            ret = true;
        } catch (SQLException ex) {
            System.out.println("userActive() "+ ex);
        }
        return ret;
    }
    
    
    public boolean getActive(String user) {
        String[] aux = getDB();
        boolean active = false;

        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"accounts\"");
            while (rs.next()) {

                if (rs.getString("username").equals(user)) {
                    active = rs.getBoolean("isonline");
                    break;
                }

            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("getActive() "+ ex);
        }

        return active;
    }
    
    public String getRoom(){
        String[] aux = getDB();    
        String rooms = "";
        
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\"");
            while (rs.next()) {
                    

                      rooms = rooms + rs.getString("name") + "&"+ rs.getString("maxplayers") + "&" + rs.getString("players") + "&" + rs.getString("owner")+"/";
                }
            con.close();    
            
        } catch (SQLException ex) {
            System.out.println("getRoom() "+ ex);
        }
        
        return rooms;             
        
    }
    
    public String getState(String user){
        String state = "";
        String[] aux = getDB();    
        try { 
            
        Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"accounts\"");
        
        while (rs.next()) {
            if (rs.getString("username").equals(user)) {
                state = rs.getString("state");
                break;
            }
        }
        con.close();    
        } catch (SQLException ex) {
            System.out.println("getState() "+ ex);
        }
        
        return state;
    }
    
    public boolean setState (String state, String user) {
        boolean result = false;
        String[] aux = getDB();
        
        try {    
        Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"accounts\"");
        
        while(rs.next()){
            if(rs.getString("username").equals(user)) {              
                stmt.executeUpdate("UPDATE \"scrabble\".\"accounts\" SET \"state\"=" + "'" + state + "'" + " WHERE \"username\"=" + "'" + user + "'");
                result = true;
            }
            else result = false;
        }
        
        if( getState(user).equals("BAN") || getState(user).equals("KICK") ) {
            result = true;
        }
        else result = false;
        
        con.close();   
         
        } catch (SQLException ex) {
            System.out.println("setState() "+ ex);
        }
        return result;
    }
    
    public boolean getAdmin(String user){
        boolean admin = false;
        String[] aux = getDB();
        try{ 
        Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"accounts\"");
        
        while (rs.next()) {
            if (rs.getString("username").equals(user)) {
                    admin = rs.getBoolean("ADMIN");
                    break;
                }
        }
        con.close();    
            
        } catch (SQLException ex) {
            System.out.println("getAdmin() "+ ex);
        }
        return admin;
    }
    
    public boolean getOwner(String username){
        boolean owner = false;
        String[] aux = getDB();
        try{ 
        Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\"");
        
        while (rs.next()) {
            if (rs.getString("owner").equals(username)){
                    owner = true;
                    break;
                }
        }
        con.close();
        }catch (SQLException ex) {
            System.out.println("getOwner() "+ ex);
        }
        return owner;
    }
    
    public int serverFull(){
        String[] aux = getDB();
        int i=0;
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\"");
            
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
    
    public String createDBRoom(int nPlayers, String roomName, String owner){
        String sql;
        String[] rooms = {"Room1","Room2","Room3","Room4"};
        try{
            String[] aux = getDB();
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            int players = 0 ;
            String Rname = "";
            int count;
            int i=0;
            
            if(roomName.equals("")){
                ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\"");
                while (rs.next()) {
                    Rname = rs.getString("NAME");
                    if(Rname.equals(rooms[i]))
                    {
                        i++;
                    }
                    else
                        break;
                }
                roomName = rooms[i];
            }
            
            sql = "INSERT INTO \"scrabble\".\"room\"" + " (\"maxplayers\",\"name\",\"players\", \"owner\")" + " VALUES (" + nPlayers + ", '" + roomName + "'," + players + ", '" + owner + "');";
            //stmt.executeQuery(sql);
            stmt.executeUpdate(sql);
            con.close();
            return roomName;
            
        } catch (SQLException ex) {
            System.out.println("createDBRoom() "+ex);
            return "ERROR";
        }
    }
    
    
    public boolean isRoomFull(String room){
        int players = 0;
        int maxPlayers = 0;
        String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT players FROM \"scrabble\".\"room\" WHERE \"name\"='" + room + "'");
            
            if (rs.next()) {
                players = rs.getShort("players");
            }
            ResultSet rs1 = stmt.executeQuery("SELECT maxplayers FROM \"scrabble\".\"room\" WHERE \"name\"='" + room + "'");
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
    
    public boolean addPlayerRoom(String room){
        int players = 0;
        String[] aux = getDB();
        try {
            
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\" WHERE \"name\"='" + room + "'");
            if (rs.next()) {
                    players = rs.getShort("players");
             }
            players = players + 1;
            stmt.executeUpdate("UPDATE \"scrabble\".\"room\" SET \"players\"=" + players + " WHERE \"name\"=" + "'" + room + "'");
            
            
        }catch(SQLException ex){
            System.out.println("addPlayerRoom() " +ex);
            return false;
        }
        return true;
    }
    
    public String deleteRoom(String username){
        String[] aux = getDB();
        try {
            
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate("DELETE FROM \"scrabble\".\"room\" WHERE \"owner\"='" + username + "'");
        } catch (SQLException ex) {
            System.out.println("deleteRoom() " +ex);
            return "DELETE#ERROR#";
        }
        return "DELETE#OK#";
    }
    
    public String qRoom(String username){
        String[] aux = getDB();
        int players = 0;
        try {
            
            Connection con = DriverManager.getConnection(aux[1], aux[2], aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\" WHERE \"name\"='" + room + "'");
            if (rs.next()) {
                    players = rs.getShort("players");
             }
            players = players - 1;
            stmt.executeUpdate("UPDATE \"scrabble\".\"room\" SET \"players\"=" + players + " WHERE \"name\"=" + "'" + room + "'");
            
        } catch (SQLException ex) {
            System.out.println("quitRoom() " +ex);
            return "QUIT#ERROR#";
        }
        return "QUIT#OK#";
    }
}
