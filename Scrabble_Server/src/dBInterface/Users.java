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
import java.util.Arrays;

/**
 *
 * @author HUGUETA
 */
public class Users {
    
    private boolean testConfigured;
    private final DbSetup dbconn;

    /**
     * Initializes the test variable and the connection to the database.
     */
    public Users() {
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
     * Verifies if the provided username already exists on the database.
     * @param user a string that stores the username
     * @return a boolean stating if it exists or not
     */        
    public boolean usernameExist(String user){
        boolean exist = false;
        String[] aux = dbconn.getDB();
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
    /**
     * Inserts the newly approved user onto the database.
     * @param username The unique username that identifies the new user.
     * @param password The encrypted password that secures the user account.
     * @param email An email address unique to the user, so he can retrieve his password.
     * @return A boolean stating if the operation was successful or not
     */
    public boolean insertUser(String username, String password, String email) {
        boolean state = false;
        String sql;
        String[] aux = dbconn.getDB();
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
    /**
     * Retrieves the password of specified user.
     * <p>
     * This operation is necessary whenever the user logs into the server, or 
     * when he needs to change the password to a new one.
     * @param user The user to which the password belongs.
     * @return A string, storing either the password or the reason to why the 
     * operation failed.
     */    
    public String getPassword(String user) {
        String[] aux = dbconn.getDB();
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
    
    /**
     * Changes the password or email of a certain user.
     * @param pass The new password that might be changed.
     * @param email The email that might be changed.
     * @param username The username that indentifies the user.
     * @return A status code reflecting the success of the operation.
     */
    public int changes(String pass, String email, String username){
        String[] aux = dbconn.getDB();
        int ret;
        String updatePassword;
        String updateEmail;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured){
                updatePassword = "UPDATE scrabble.accounts SET password ='" + pass + "' WHERE username ='" + username + "'";
                updateEmail = "UPDATE scrabble.accounts SET email ='" + email + "' WHERE username ='" + username + "'";
            }else{
                updatePassword = "UPDATE test.accounts SET password ='" + pass + "' WHERE username ='" + username + "'";
                updateEmail = "UPDATE test.accounts SET email ='" + email + "' WHERE username ='" + username + "'";
            }
            stmt.executeUpdate(updatePassword);
            stmt.executeUpdate(updateEmail);
            
            ret = 2;
        } catch (SQLException ex) {
            ret = 0;
            System.out.println("userActive() "+ ex);
        }
        return ret;
    }
    
    /**
     * Acknowledges that specified user is either online or offline.
     * @param username The user username that will change is state.
     * @param state The new state of the user, false if offline, true if online.
     * @return A boolean stating if the operation was sucessful or not.
     */
    public boolean userActive(String username, boolean state) {
        String[] aux = dbconn.getDB();
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
    /**
     * Verifies if a user is online.
     * @param user The user that will be verified.
     * @return A boolean indicating if he is online or not.
     */
    public boolean getActive(String user) {
        String[] aux = dbconn.getDB();
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
   /**
     * Returns the state of the specified user.
     * <p>
     * The states recognized are NORMAL, or BAN/KICK when an admin recognizes
     * that this user is prejudicional to the community.
     * @param user The specified user.
     * @return A string if the state of the user.
     */
    public String getState(String user){
        String state = "";
        String[] aux = dbconn.getDB();
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
    /**
     * Changes the state of a user.
     * @param state The new state of the user.
     * @param user The user that the state will be changed.
     * @return A boolean stating if the operation was successful or not.
     */
    public boolean setState (String state, String user) {
        boolean result = false;
        String[] aux = dbconn.getDB();
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
    
    /**
     * Verifies if an user is an admin of the system.
     * @param user the user that will be verified
     * @return A boolean stating either the user is an admin or not.
     */
    public boolean getAdmin(String user){
        boolean admin = false;
        String[] aux = dbconn.getDB();
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
    
    /**
     * Counts the number of registered users on the system.
     * @return The number of registered users.
     */
    public int getRegistedPlayers(){
        String[] aux = dbconn.getDB();
        String query;
        int i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT username FROM scrabble.accounts ORDER BY points DESC;";
            else
                query = "SELECT username FROM test.accounts ORDER BY points DESC;";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                i++;
            }
        }catch (SQLException ex) {
            System.out.println("getRegistedPlayers() " +ex);
            return -1;
        }
        return i;
    }
    
    /**
     * Retrieves the registered users from the database.
     * <p> These users are presented on an array, sorted by their score.
     * @return An array with all the registered users.
     */    
    public String[] getUsername(){
        String[] aux = dbconn.getDB();
        int i = getRegistedPlayers();
        String[] usernames = new String[i];
        String query;
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT username FROM scrabble.accounts ORDER BY points DESC;";
            else
                query = "SELECT username FROM test.accounts ORDER BY points DESC;";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                usernames[i] = rs.getString("username");
                i++;
            }
        } catch (SQLException ex){
            System.out.println("getUsername() " +ex);
            return null;
        }
        return usernames;
    }
    
    /**
     * Retrieves the points of every registered user.
     * <p> This list is sorted according to their position on the ranking.
     * @return An array with all the points sorted.
     */
    public String[] getPoints(){
        String[] aux = dbconn.getDB();
        int i = getRegistedPlayers();
        String[] points = new String[i];
        String query;
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT points FROM scrabble.accounts ORDER BY points DESC;";
            else
                query = "SELECT points FROM test.accounts ORDER BY points DESC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                points[i] = rs.getString("points");
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("getPoints() " +ex);
            return null;
        }
        return points;
    }
    
    /**
     * Retrieves the number of victories of every registered user.
     * <p> This list is sorted downwards.
     * @return An array with all the victories sorted.
     */
    public String[] getWins(){        
        String[] aux = dbconn.getDB();
        int i = getRegistedPlayers();
        String query;
        String[] wins = new String[i];
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT wins FROM scrabble.accounts ORDER BY points DESC;";
            else
                query = "SELECT wins FROM test.accounts ORDER BY points DESC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                wins[i] = rs.getString("wins");
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("getWins() " +ex);
            return null;
        }
        return wins;
    }
     /**
     * Retrieves the number of losses of every registered user.
     * <p> This list is sorted downwards.
     * @return An array with all the losses sorted.
     */
     public String[] getLoses(){
        String[] aux = dbconn.getDB();
        int i = getRegistedPlayers();
        String[] loses = new String[i];
        String query;
        i = 0;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT loses FROM scrabble.accounts ORDER BY points DESC;";
            else
                query = "SELECT loses FROM test.accounts ORDER BY points DESC;";
            ResultSet rs = stmt.executeQuery(query);
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
     
     /**
      * Returns a list with the names of all registered users.
      * @return A list with the users names.
      */    
     public String getUsernameList(){
        String players = "";
        String[] aux = dbconn.getDB();
        String query;
        try {
            Connection con = DriverManager.getConnection(aux[1],aux[2],aux[3]);
            Statement stmt = con.createStatement();
            if(!testConfigured)
                query = "SELECT * FROM scrabble.accounts;";
            else
                query = "SELECT * FROM test.accounts;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                players = players + rs.getString("username")+"/";                
            }
        } catch (SQLException ex) { System.out.println("getUserInfo() " +ex); }
        System.out.println(players);
        return players;
    }
    
}
