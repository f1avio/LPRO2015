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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author HUGUETA
 */
public class Chat {
    private boolean testConfigured;
    private final DbSetup dbconn;
    
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
     * Initializes the test variable and the connection to the database.
     */
        public Chat() {
        this.testConfigured = false;
        this.dbconn = new DbSetup();
    }
        
    /**
     * Adds a message to the chat with a certain user.
     * @param usernameChat The user that receives the message.
     * @param messageChat The content of the message.
     * @return A boolean stating if the operation was successful or not.
     */
    public boolean addChat_MSG(String usernameChat, String messageChat) {
        String sql;
        String[] aux = dbconn.getDB();
        boolean success;
        
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        //System.out.println("Today : " + today);
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
           if(!testConfigured)
                sql = "INSERT INTO scrabble.chat (remetente, mensagem, data)" + " VALUES ('" + usernameChat + "', '" + messageChat + "', '" + today + "')";
           else
               sql = "INSERT INTO test.chat (remetente, mensagem, data)" + " VALUES ('" + usernameChat + "', '" + messageChat + "', '" + today + "')";
            stmt.executeUpdate(sql);
            success = true;
       } catch (SQLException ex) 
          {
            System.out.println(ex);
            success = false;
          }
        
        return success;
    }
}
