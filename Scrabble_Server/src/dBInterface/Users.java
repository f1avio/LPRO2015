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
        //System.out.println("getDB() "+Arrays.toString(aux));
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
        String tables = "";
        
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM \"scrabble\".\"room\"");
            while (rs.next()) {
                    

                      tables = tables + rs.getString("name") + "&"+ rs.getString("maxplayers") + "&" + rs.getString("players") + "/";
                }
            con.close();    
            
        } catch (SQLException ex) {
            System.out.println("getRoom() "+ ex);
        }
        
        return tables;             
        
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
}
