package dBInterface;

/**@author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */
public class DBconnection {
    
    Thread t1;
    Thread t2;
    Thread t3;
    int count = 0;
    
    private static DBconnection instance = null;
    
    public static DBconnection getInstance(){
        if (instance == null) {
            instance = new DBconnection();
        }
        return instance;
    }
    
    public int logUser(String user, String password)
    {
        Users database = new Users();
        boolean exist = database.usernameExist(user);
        boolean admin;
        
        if(exist){
            if(database.getPassword(user).equals(password)){
                if(database.getActive(user)){
                    return 2;
                }else{
                    String state = database.getState(user);
                    System.out.println("[Server][Service]" + "State: " + state);
                    admin = database.getAdmin(user);
                    if(admin){
                        database.userActive(user, true);
                        return 3;
                    }else
                        if(state.equals("NORMAL") || state.equals("KICK")){
                            database.userActive(user, true);
                            return 1;
                        }
                        else return 4;
                }
            } else return 0;
        }else return 0;
    }
    /**
     * Attempts to signup a new user creating a new line on the specified database    
     * @param user
     * @param password The password needed to login into the system
     * @param email The user email, for administrative tasks
     * @return  a standardized status value, notifying the degree of success of the implementation
     */
    public int signUser(String user, String password, String email)
    {
        int state = 0;
        Users database = new Users();
        boolean userExist;
        boolean signupRet;
        
        userExist = database.usernameExist(user);
        
        if (userExist) {
            state = 0;
        } else {
            signupRet = database.insertUser(user, password, email);
            if (signupRet) {
                state = 1;
            }
        }

        return state;
    }
    
    
    /**
     * Logs out the user from the system, deactivating his flag "isOnline"
     * @param table the table where the user data is stored
     * @param username the user's name that needs to log out
     * @return a standardized status value, notifying the degree of success of the implementation
     */
    public int logoutUser(String username)
    {
        int state;
        Users database = new Users();
        boolean userExist;
        boolean active;
        
        userExist = database.usernameExist(username);
        active = database.getActive(username);
        
            if (userExist && active) {
                database.userActive(username, false);
                state = 1;
            } else {
                state = 0;
            }
        System.out.println("[Server][Service]" + "RETURN LOGOUT = " + state);
        return state;
    }
    
    public String receiveTables(){
        String tables ="";
        Users database = new Users();
        
        tables = database.getRoom();
        
        return tables;
    }
}
