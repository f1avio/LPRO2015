package scrabble_server;

import dBInterface.Chat;
import dBInterface.Room;
import dBInterface.Users;
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
    /**
     * Verifies the provided info and if possible, logs the visitor as a user.
     * <p> First it attempts to find the provided username on the database.
     * If it exists, it compares the provided password, previously encrypted, to
     * the one stored on the database.
     * <p> At last, verifies if the user hasn't been expelled of the system.
     * @param user The username that the visitor is attempting to use as login.
     * @param password The password provided by the visitor.
     * @return An integer, whose value depends of the method's results.
     */
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
     * Attempts to signup a new user creating a new tuple on the specified database    
     * @param user The user's username that will be registered on the system.
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
    /**
     * Returns a list of all the rooms on the system.
     * <p> Acts as an intermediate between lower level and the clientThread.
     * @return A string with all the rooms listed.
     */
    public String receiveRooms(){
        Room database = new Room();
        String rooms = database.getRooms();
        return rooms;
    }
    /**
     * Returns a list of all the player on a certain room.
     * @param room The room name where this information will be checked.
     * @return A string with all the players listed.
     */
    public String[] receiveRoomPlayers(String room){
        Room database = new Room();
        int i = 0;
        int j = 0;
        char[] roomplayers = database.getRoomPlayers(room).toCharArray();
        char[] roomstatus = database.getRoomStatus(room).toCharArray();
        String aux = "";
        String[] players = new String[8];
        
        while(i < 4){
            while(roomplayers[j]!='}' && roomplayers[j]!='{' && roomplayers[j]!=','){
                aux = aux + roomplayers[j];
                j++;
            }
            if(!"".equals(aux)){
                players[i]=aux;
                aux = "";
                i++;
            }
            j++;
        }
        j = 0;
        while(i < 8){
            while(roomstatus[j]!='}' && roomstatus[j]!='{' && roomstatus[j]!=','){
                aux = aux + roomstatus[j];
                j++;
            }
            if(!"".equals(aux)){
                players[i]=aux;
                aux = "";
                i++;
            }
            j++;
        }
        //System.out.println("receiveRoomPlayers() "+Arrays.toString(players));
        return players;
    }
    /**
     * Return if a user is currently an host of a room.
     * @param username The user's username that will ber verified.
     * @return A boolean stating if the user is really a owner
     */
    public boolean isHost(String username){
        Room database = new Room();
        boolean ans = database.getHost(username);
        return ans;
    }
    /**
     * Adds a message to a certain user.
     * @param usernameChat The user that will receive the message.
     * @param messageChat The content of the message.
     */
    public void listChat(String usernameChat, String messageChat) {

        Chat missive= new Chat();
       
        boolean addChat_MSG = missive.addChat_MSG(usernameChat,messageChat);
    }
    /**
     * Creates a new room in the system.
     * @param nPlayers The maximum number of players allowed in the room.
     * @param owner The user that creates the room.
     * @param roomName The designed room's name.
     * @return The room's name as a confirmation.
     */
    public String createRoom(int nPlayers, String owner, String roomName){
        int ans = 0;
        Room database = new Room();
        
        if(database.serverFull()>3) return "";
        
        ans = database.createDBRoom(nPlayers, roomName, owner);
        switch(ans){
            case 1:
                /*game1 = new GameManager("Room1");
                t1 = new Thread(game1);
                t1.start();*/
                return roomName;
            case 2:
                /*game2 = new GameManager("Room2");
                t2 = new Thread(game2);
                t2.start();*/
                return roomName;
            case 3:
                /*game3 = new GameManager("Room3");
                t3 = new Thread(game3);
                t3.start();*/
                return roomName;
            case 4:
                /*game4 = new GameManager("Room4");
                t4 = new Thread(game4);
                t4.start();*/
                return roomName;  
            default:
                return "";
        }
    }
    /**
     * Joins a user as a player to a certain room.
     * <p>It starts by verifying if the user is allowed to enter the room, and
     * if allowed, verifies if the room is already full.
     * @param username The user's username that will be registered.
     * @param ID the user's identification number.
     * @param roomName The room where he will enter.
     * @return An integer that stores the status of the operation.
     */
    public int join(String username, int ID, String roomName){
        Room databaseR = new Room();
        Users databaseU = new Users();
        boolean ansFull;
        int roomID;
        
        if(databaseU.getState(username).equals("KICK"))
            return 2;
        ansFull = databaseR.isRoomFull(roomName);
        if(ansFull){
            return 0;
        }
        
        p = new Player(username, ID);
        
        roomID = databaseR.addPlayerRoom(roomName, username);      
        switch(roomID){
           case 1:{
               System.out.println("INSERTING PLAYER ON Room: "+roomName);
               //game1.joinPlayer(p);
               return 1;
           }
           case 2:{
               System.out.println("INSERTING PLAYER ON Room: "+roomName);
               //game2.joinPlayer(p);
               return 1;
           }
           case 3:{
               System.out.println("INSERTING PLAYER ON Room: "+roomName);
               //game3.joinPlayer(p);
               return 1;
           }
           case 4:{
               System.out.println("INSERTING PLAYER ON Room: "+roomName);
               //game4.joinPlayer(p);
               return 1;              
           }
           default:
               return -1;
       }             
    }
    /**
     * Allows a player to quit the room he is in.
     * <p>If the player that is leaving is also the host, the room will also be
     * closed.
     * @param username The user that will leave.
     * @param room The room which the user wants to leave.
     * @return The status of the operation.
     */
    public String quitRoom(String username, String room){
        Room database = new Room();
        String ret="";
        int i = 0;
        boolean owner = isHost(username);
        if(owner){
            if("OK".equals(database.deleteRoom(username))){    
                ret = "OWNER";
            } else{
                ret = "ERROR";
            }     
        }
        else{
            String[] players = receiveRoomPlayers(room);
            while(i<4 && !username.equals(players[i])){
                i++;
            }
            while(i<3){
                players[i]=players[i+1];
                players[i+4]=players[i+5];
                i++;
            }
            players[3] = "NULL";
            players[7] = "NULL";
            
            if("OK".equals(database.qRoom(players, room))){
                ret = "USER";
            } else{
                ret = "ERROR";
            }
        }
        return ret;
    }
    /**
     * Changes the state of a player on the room.
     * <p> The player can switch between two states, "Wait" where he isn't fully
     * commited to start a game, so he simply waits for more players, or "Ready"
     * where he signals the host that he is ready to play.
     * @param username The user that intends to change his state.
     * @param room The room where the player is.
     * @return An update list of players and their states.
     */
    public String[] roomState(String username, String room){
        Room database = new Room();
        String[] players = receiveRoomPlayers(room);
        //String ret = "";
        int i = 0;
        while(!players[i].equals(username)){
            i++;
        }
        
        switch(players[i+4]){
            case "Ready":
                if(database.updateRoomState(i+1, "Wait", room))
                    players[i+4] = "Wait";
                break;
            case "Wait":
                if(database.updateRoomState(i+1, "Ready", room))
                    players[i+4] = "Ready";
                break;
        }
        return players;
    }
    /**
     *Retrieves the global rankings of the system. 
     * @return An array of strings with the complete information.
     */
    public String[] ranking(){  
        Users database = new Users();
        int totalPlayers = database.getRegistedPlayers();
        String[] rank_s = new String[totalPlayers+1];
        String[] user = database.getUsername();
        String[] points = database.getPoints();
        String[] w = database.getWins();
        String[] l = database.getLoses();
        for(int i = 1; i< totalPlayers+1; i++ ){            
            rank_s[i] = i + "/" +user[i-1] + "/" + points[i-1] + "/" + w[i-1]+ "/" +l[i-1];
        }
        rank_s[0] = Integer.toString(totalPlayers);
        return rank_s;
    }
}