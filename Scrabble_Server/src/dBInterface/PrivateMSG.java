/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

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
 * Sends private messsages to other registered users.
 * @author Flávio Dias
 * @author Hugo Pereira
 * @author José Carvalho
 */
public class PrivateMSG {
    
    
    private boolean testConfigured;
    private final DbSetup dbconn; 

    /**
     * Initializes the test variable and the connection to the database.
     */
    public PrivateMSG() {
        this.testConfigured = false;
        this.dbconn = new DbSetup();
    }
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
     * Sends a message to a specific user.
     * @param user_msg The sender of the message.
     * @param friend_msg The receiver of the message.
     * @param texto The content of the message.
     * @return A status code reflecting the success of the operation.
     */
    public int addPrivate_MSG(String user_msg, String friend_msg,String texto){

        String sql;
        int ret;
        String[] aux = dbconn.getDB();
        try (Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3])) {
            Statement stmt = con.createStatement();
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(date);
            //System.out.println("Today : " + today);
            if(!testConfigured)
                sql = "INSERT INTO scrabble.private" + " (sender, message, receiver ,date)" + " VALUES ('" + user_msg + "', '" + texto + "','" + friend_msg + "','" + today + "')";
            else
                sql = "INSERT INTO test.private" + " (sender, message, receiver ,date)" + " VALUES ('" + user_msg + "', '" + texto + "','" + friend_msg + "','" + today + "')";
            stmt.executeUpdate(sql);
            ret = 1;

        } catch (SQLException ex) {
            System.out.println(ex);
            ret=0;
        }
        return ret;
    }
        /**
         * Retrieves the pending messages to the user.
         * @param user The user with pending messages.
         * @return The pending messages.
         */
        public String PrivMsgList(String user) {
        String[] aux = dbconn.getDB();
        String query;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            
            String msgList="";
            if(!testConfigured)
                query = "SELECT * FROM scrabble.private WHERE receiver = '" + user+ "';";
            else
                query = "SELECT * FROM test.private WHERE receiver = '" + user+ "';";
            ResultSet rs = stmt.executeQuery(query);            
            while(rs.next())
            {
                msgList = msgList + rs.getString("sender")+ "£" + rs.getString("message")+"«";
            }
            return msgList;
          
        } catch (SQLException ex) {
            System.out.println("PrivMsgList() "+ex);
            return "ERRO";}
    }
    /**
     * Deletes the list of messages to a certain user.
     * @param user The user whose list of messages will be deleted.
     * @return A status code representing the success of the operation.
     */
    boolean PrivMsgListDelete(String user) {
        
        String[] aux = dbconn.getDB();
        String update;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
           
            String msg="";
            if(!testConfigured)
                update = "DELETE FROM scrabble.private WHERE \"receiver\"='"+user+"';";
            else
                update = "DELETE FROM test.private WHERE \"receiver\"='"+user+"';";
            stmt.executeUpdate(update);
                
                //stmt.executeUpdate("INSERT INTO USERS" + " (FRIENDS)" + " VALUES ('" + friend + "')");
        } catch (SQLException ex) 
        {
            System.out.println("PrivMsgListDelete() " +ex);
            return false;
        }
        return true;
    }
    /**
     * Counts the number of pending messages.
     * @param user The user whose inbox will be counted.
     * @return The number of messages.
     */
    public int getNrMsg(String user) {
        String[] aux = dbconn.getDB();
        String query;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            int nr_msg = 0;

            if(!testConfigured)
                query = "SELECT * FROM scrabble.private WHERE receiver = '" + user+ "';";
            else
                query = "SELECT * FROM test.private WHERE receiver = '" + user+ "';"; 
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                nr_msg++;
            } 
            return nr_msg;
          
        } catch (SQLException ex) {
            System.out.println("getNrMsg() "+ex);
            return -1;}
         
        }
}
