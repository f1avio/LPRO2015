package dBInterface;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**@author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */

public class DBconnection {
    
    private static boolean SuccessfulConn = true;
    private static Connection db = null;
    
    /**
     * Initializes the JDBC driver and attempts to connect to the PostgreSQL
     * database. 
     * @param url gives the location of the database
     * @param username the database username necessary to log in
     * @param password the database password necessary to log in 
     * @return true if it manages to connect; false if it fails to connect
     */
     public static boolean connect(String url, String username, String password) 
    {  
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            SuccessfulConn = false;
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            db = DriverManager.getConnection( url,username,password);
            SuccessfulConn = true;
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Verifies if the server is still connected to the database.
     * @return true if connected; false if disconnected
     */
    public static boolean  initialized()
    {
        return SuccessfulConn;
    }
    
    /**
     * Closes the connection to the PostgreSQL database. 
     * @return true if successful; false if unsuccessful
     */
   public static boolean dBClose()
    {
        try {
            db.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * Searches for a specified user on the PostgreSQL database. 
     * @param table the table where the user data is stored
     * @param username the user's name that will be searched
     * @return true if he exists; false if he doesn't exist
     */
    public boolean searchUser(String table, String username) 
    {
        boolean success = false;
        
        try {
            
            PreparedStatement st;
            
            String stmnt = "SELECT * FROM "+table+" WHERE username = '"+username+"';";
            st = db.prepareStatement(stmnt);
            ResultSet rs;
            rs = st.executeQuery();
            
            if(false != rs.next())
                success = true;
            
            rs.close();
            st.close();
            

        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        return success;
    }
    
    /**
     * Searches and compares the password of the specified user
     * @param table the table where the user data is stored
     * @param username the user's name that will be searched
     * @param password the user's password to be compared
     * @return true if the password is correct; false if the password doesn't exist 
     */
    public boolean searchPassword(String table, String username, String password)
    {
         boolean success = false;
         
        try {
            PreparedStatement st;
            
            String stmnt = "SELECT password FROM "+table+" WHERE username = '"+username+"';";
            st = db.prepareStatement(stmnt);
            
            ResultSet rs;
            rs = st.executeQuery();
            rs.next(); //So it can positionate the resultset
            if(rs.getString("password").equals(password))
                success = true;
            
            rs.close();
            st.close();
            

        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    /**
     * Logs the user to the system, activating his flag "isOnline"
     * @param table the table where the user data is stored
     * @param username the user's name that needs to log in
     * @param password the user's password
     * @return true if the operation was successful; false if it is unsuccessful
     */
    public int logUser(String table, String username, String password)
    {
            if(!searchUser(table, username))
                return -1; //The player doesn't exist!
            
            if(!searchPassword(table, username, password))
                return -2; //The password is incorrect!
            
            if(isOnline(table, username))
                return -3; //the user is already online
            
            //Everything checks out, activate isOnline flag
        try {    
            PreparedStatement st;
            String stmnt = "UPDATE "+table+" SET isOnline = TRUE WHERE  username = '"+username+"';";
            st = db.prepareStatement(stmnt);
            
            int aux = 0;
            aux = st.executeUpdate();
            
            st.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
            return -4;
        }
        
        return 0;
    }
    
    public int signUser(String table, String username, String password, String email)
    {        
            if(searchUser(table, username))
                return -2; //The user already exists
            
            //Everything checks out, activate isOnline flag
        try {    
            PreparedStatement st;
            String stmnt = "INSERT INTO "+table+" VALUES ('"+username+"', '"+password+"', FALSE, '"+email+"');";
            st = db.prepareStatement(stmnt);
            st.executeUpdate();
            st.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        return 0;
    }
    
    /**
     * Verifies if a user is online
     * @param table the table where the user data is stored
     * @param username the user's name that will be verified
     * @return true if it is online: false if it isn't online
     */
    public boolean isOnline(String table, String username)
    {
        boolean success = false;
        
        try {     
            PreparedStatement st;
            
            String stmnt = "SELECT isOnline FROM "+table+" WHERE username = '"+username+"';";
            st = db.prepareStatement(stmnt);
            ResultSet rs;
            rs = st.executeQuery();
            
            if(false != rs.next())
                success = rs.getBoolean("isOnline");
            
            rs.close();
            st.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
            
        }
        
        return success;
    }
    
    /**
     * Logs out the user from the system, deactivating his flag "isOnline"
     * @param table the table where the user data is stored
     * @param username the user's name that needs to log out
     * @return true if the operation was successful; false if it's unsuccessful
     */
    public boolean logoutUser(String table, String username)
    {
        if(searchUser(table, username) && isOnline(table, username)) //o utilizador tem de existir e estar online
        {
            try {
                PreparedStatement st;
                String stmnt = "UPDATE "+table+" SET isOnline = FALSE WHERE  username = '"+username+"';";
                st = db.prepareStatement(stmnt);
                
                int aux = st.executeUpdate();
                
                st.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(DBconnection.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
            return true;
        }
        return false;
    }
}
