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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author HUGUETA
 */
public class Users {
    
    private boolean testConfigured;
    
    public void setTest(boolean testConfigured)
    {
        this.testConfigured = testConfigured;
    }
    
    public Users(){
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
            
    public boolean usernameExist(String user){
        boolean exist = false;
        String[] aux = getDB();
        String query;
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            if(!testConfigured)
                   query = "SELECT * FROM \"scrabble\".\"accounts\"";
            else
                   query = "SELECT * FROM \"test\".\"accounts\"";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                if(rs.getString("username").equals(user)){
                    exist = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            System.out.println("usernameExist() " +ex);
        }
        return exist;
    }
    
    public boolean insertUser(String username, String password, String email) {
        boolean state = false;
        String sql="";
        String[] aux = getDB();
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            if(!testConfigured)
                sql = "INSERT INTO scrabble.accounts VALUES('"+ username + "', '" + password + "', FALSE ,'" + email + "', 0, FALSE, 'NORMAL');";
            else
                sql = "INSERT INTO test.accounts VALUES('"+ username + "', '" + password + "', FALSE ,'" + email + "', 0, FALSE, 'NORMAL');";
            stmt.executeUpdate(sql);
            state = true;


        } catch (SQLException ex) {
            System.out.println("insertUser() " +ex);
        }

        return state;
    }
    
    public String getPassword(String user) {
        String[] aux = getDB();
        String state = "PasswordNotFound";
        String query;
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            
            if(!testConfigured)
                query = "SELECT * FROM \"scrabble\".\"accounts\"";
            else
                query = "SELECT * FROM \"test\".\"accounts\"";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("username").equals(user)) {
                    state = rs.getString("password");
                    break;
                }

            }

        } catch (SQLException ex) {
            state = "SQL EXCEPTION";
        }
        return state;
    }
    
    public boolean userActive(String username, boolean state) {
        String[] aux = getDB();
        boolean ret = false;
        String update;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
               update = "UPDATE \"scrabble\".\"accounts\" SET \"isonline\"='" + state + "' WHERE \"username\"='" + username + "'";
            else
               update = "UPDATE \"test\".\"accounts\" SET \"isonline\"='" + state + "' WHERE \"username\"='" + username + "'";     
            stmt.executeUpdate(update);
            ret = true;
        } catch (SQLException ex) {
            System.out.println("userActive() "+ ex);
        }
        return ret;
    }
    
    
    public boolean getActive(String user) {
        String[] aux = getDB();
        boolean active = false;
        String query;
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT * FROM \"scrabble\".\"accounts\"";
            else
                query = "SELECT * FROM \"test\".\"accounts\"";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                if (rs.getString("username").equals(user)) {
                    active = rs.getBoolean("isonline");
                    break;
                }

            }
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
        String query;
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) { 
            
        Statement stmt = con.createStatement();

        if(!testConfigured)
            query = "SELECT * FROM \"scrabble\".\"accounts\"";
        else
            query = "SELECT * FROM \"test\".\"accounts\"";
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            if (rs.getString("username").equals(user)) {
                state = rs.getString("state");
                break;
            }
        }
        } catch (SQLException ex) {
            System.out.println("getState() "+ ex);
        }
        
        return state;
    }
    
    public boolean setState (String state, String user) {
        boolean result = false;
        String[] aux = getDB();
        String query;
        String update;
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
        Statement stmt = con.createStatement();
        if(!testConfigured)
            query = "SELECT * FROM \"scrabble\".\"accounts\"";
        else
            query = "SELECT * FROM \"test\".\"accounts\"";
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()){
            if(rs.getString("username").equals(user)) {
                if(!testConfigured)
                    update = "UPDATE \"scrabble\".\"accounts\" SET \"state\"=" + "'" + state + "'" + " WHERE \"username\"=" + "'" + user + "'";
                else
                    update = "UPDATE \"scrabble\".\"test\" SET \"state\"=" + "'" + state + "'" + " WHERE \"username\"=" + "'" + user + "'";
                stmt.executeUpdate(update);
                result = true;
            }
            else result = false;
        }
        
        if( getState(user).equals("BAN") || getState(user).equals("KICK") ) {
            result = true;
        }
        else result = false;
        
         
        } catch (SQLException ex) {
            System.out.println("setState() "+ ex);
        }
        return result;
    }
    
    public boolean getAdmin(String user){
        boolean admin = false;
        String[] aux = getDB();
        String query;
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
        Statement stmt = con.createStatement();
        if(!testConfigured)
            query = "SELECT * FROM \"scrabble\".\"accounts\"";
        else
            query = "SELECT * FROM \"test\".\"accounts\"";
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            if (rs.getString("username").equals(user)) {
                    admin = rs.getBoolean("ADMIN");
                    break;
                }
        }
            
        } catch (SQLException ex) {
            System.out.println("getAdmin() "+ ex);
        }
        return admin;
    }
    
    public boolean getOwner(String username){
        boolean owner = false;
        String[] aux = getDB();
        String query;
        try(Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
        Statement stmt = con.createStatement();
        if(!testConfigured)
            query = "SELECT * FROM \"scrabble\".\"room\"";
        else
            query = "SELECT * FROM \"test\".\"room\"";
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
               query = "SELECT * FROM \"scrabble\".\"room\"";
            else
                query = "SELECT * FROM \"test\".\"room\"";
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
            stmt.executeUpdate("UPDATE scrabble.room SET playernames["+players+"][2] = 'wait' WHERE name = '" + room + "'");
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
            System.out.println("deleteRoom() antes:  "+serverFull());
            stmt.executeUpdate("DELETE FROM scrabble.room WHERE owner = '" + username + "'");
        } catch (SQLException ex) {
            System.out.println("deleteRoom() " +ex);
            return "ERROR";
        }
        System.out.println("deleteRoom() depois:  "+serverFull());
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\" WHERE \"name\"='" + room + "'");
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
    
    void addChat_MSG(String usernameChat, String messageChat) {
                 String sql;
                 String[] aux = getDB();
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(date);
            System.out.println("Today : " + today);
            sql = "INSERT INTO \"scrabble\".\"chat\"" + " (\"remetente\", \"mensagem\",\"data\")" + " VALUES ('" + usernameChat + "', '" + messageChat + "', '" + today + "')";
            stmt.executeUpdate(sql);
     
            con.close();
            
       } catch (SQLException ex) 
          {
            System.out.println(ex);
    
          }

    }
}
