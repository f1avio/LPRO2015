package dBInterface;

import scrabble_server.Player;

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
    Thread t4;
    //GameManager game1;
    //GameManager game2;
    //GameManager game3;
    //GameManager game4;
    
    Player p;
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
    
    public String receiveRooms(){
        String rooms ="";
        Users database = new Users();
        
        rooms = database.getRoom();
        
        return rooms;
    }
    
    public boolean isOwner(String username){
        Users database = new Users();
        boolean ans = database.getOwner(username);
        return ans;
    }
    
    public String createRoom(int nPlayers, String owner){
        String ans = "";
        String roomName;
        Users database = new Users();
        
        int nRooms = database.serverFull();
        System.out.println("#Rooms: "+nRooms);
        //if(nRooms >= 4) return "";
        
        switch(nRooms){
            case 0:
                roomName = "Room1";
                break;
            case 1:
                roomName = "Room2";
                break;
            case 2:
                roomName = "Room3";
                break;
            case 3:
                roomName = "Room4";
                break;
            default:
                return "";
        }
        ans = database.createDBRoom(nPlayers, roomName, owner);
        switch(ans){
            case "Room1":
                /*game1 = new GameManager("Room1");
                t1 = new Thread(game1);
                t1.start();*/
                return "Room1";
            case "Room2":
                /*game2 = new GameManager("Room2");
                t2 = new Thread(game2);
                t2.start();*/
                return "Room2";
            case "Room3":
                /*game3 = new GameManager("Room3");
                t3 = new Thread(game3);
                t3.start();*/
                return "Room3";
            case "Room4":
                /*game4 = new GameManager("Room4");
                t4 = new Thread(game4);
                t4.start();*/
                return "Room4";  
            default:
                return "";
        }
    }
    
    public int join(String username, int ID, String roomName){
        Users database = new Users();
        boolean ansFull = false;
        boolean ansSuccess = false;
        int ret = 0;
        
        if(database.getState(username).equals("KICK"))
            return 2;
        ansFull = database.isRoomFull(roomName);
        if(ansFull){
            return ret;
        }
        
        p = new Player(username, ID);
        
        ansSuccess = database.addPlayerRoom(roomName);
        if(ansSuccess) ret = 1;
        
        switch(roomName){
           case "Room1" : {
               System.out.println("INSERTING PLAYER ON Room1");
               //game1.joinPlayer(p);
               break;
           }
           case "Room2" : {
               System.out.println("INSERTING PLAYER ON Room2");
               //game2.joinPlayer(p);
               break;
           }
           case "Room3" : {
               System.out.println("INSERTING PLAYER ON Room3");
               //game3.joinPlayer(p);
               break;
           }
           case "Room4" : {
               System.out.println("INSERTING PLAYER ON Room4");
               //game4.joinPlayer(p);
               break;
           }
       }
       
       return ret;
        
    }
    
    public String quitRoom(String username, boolean owner){
        Users database = new Users();
        String aux = "";
        String ret = "";
        if(owner){
            aux = database.deleteRoom(username);
        } else{
            aux = database.qRoom(username);
        }
        switch(aux){
            
        }
        
    }
}
