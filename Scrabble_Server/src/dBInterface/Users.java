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
                   query = "SELECT * FROM scrabble.accounts";
            else
                   query = "SELECT * FROM test.accounts";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
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
                query = "SELECT * FROM scrabble.accounts";
            else
                query = "SELECT * FROM test.accounts";
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
               update = "UPDATE scrabble.accounts SET isonline ='" + state + "' WHERE username ='" + username + "'";
            else
               update = "UPDATE test.accounts SET isonline ='" + state + "' WHERE username ='" + username + "'";     
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
                query = "SELECT * FROM scrabble.accounts";
            else
                query = "SELECT * FROM test.accounts";
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
   
    public String getState(String user){
        String state = "";
        String[] aux = getDB();
        String query;
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) { 
            
        Statement stmt = con.createStatement();

        if(!testConfigured)
            query = "SELECT * FROM scrabble.accounts";
        else
            query = "SELECT * FROM test.accounts";
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
            query = "SELECT * FROM scrabble.accounts";
        else
            query = "SELECT * FROM test.accounts";
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()){
            if(rs.getString("username").equals(user)) {
                if(!testConfigured)
                    update = "UPDATE scrabble.accounts SET state =" + "'" + state + "'" + " WHERE username =" + "'" + user + "'";
                else
                    update = "UPDATE scrabble.test SET state =" + "'" + state + "'" + " WHERE username =" + "'" + user + "'";
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
            query = "SELECT * FROM scrabble.accounts";
        else
            query = "SELECT * FROM test.accounts";
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
    
    public int getRegistedPlayers(){
        String[] aux = getDB();
        int i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM scrabble.accounts ORDER BY points DESC");
            while(rs.next()){
                i++;
            }
        }catch (SQLException ex) {
            System.out.println("getRegistedPlayers() " +ex);
            return -1;
        }
        return i;
    }
        
    public String[] getUsername(){
        String[] aux = getDB();
        int i = getRegistedPlayers();
        String[] usernames = new String[i];
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM scrabble.accounts ORDER BY points DESC");
            while(rs.next()){
                usernames[i] = rs.getString("username");
                i++;
            }
        } catch (SQLException ex){
            System.out.println("getUsername() " +ex);
            return null;
        }         
        System.out.println("getUsername(): " + Arrays.toString(usernames));
        return usernames;
    }
    
    public String[] getPoints(){
        String[] aux = getDB();
        int i = getRegistedPlayers();
        String[] points = new String[i];
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT points FROM scrabble.accounts ORDER BY points DESC");
            while (rs.next()){
                points[i] = rs.getString("points");
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("getPoints() " +ex);
            return null;
        }
        System.out.println("getPoints(): " + Arrays.toString(points));
        return points;
    }
    
    public String[] getWins(){        
        String[] aux = getDB();
        int i = getRegistedPlayers();
        String[] wins = new String[i];
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT wins FROM scrabble.accounts ORDER BY points DESC");
            while (rs.next()){
                wins[i] = rs.getString("wins");
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("getWins() " +ex);
            return null;
        }
        System.out.println("getWins(): " + Arrays.toString(wins));
        return wins;
    }
    
     public String[] getLoses(){
        String[] aux = getDB();
        int i = getRegistedPlayers();
        String[] loses = new String[i];
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT loses FROM scrabble.accounts ORDER BY points DESC");
            while (rs.next()) {
                loses[i] = rs.getString("loses");
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("getLoses() " +ex);
            return null;
        }
        System.out.println("getLoses(): " + Arrays.toString(loses));
        return loses;
    }
}
