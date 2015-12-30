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
public class Chat {
    
    public Chat(){
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
            sql = "INSERT INTO scrabble.chat (remetente, mensagem, data)" + " VALUES ('" + usernameChat + "', '" + messageChat + "', '" + today + "')";
            stmt.executeUpdate(sql);
            con.close();
       } catch (SQLException ex) 
          {
            System.out.println(ex);
          }
    }
}
