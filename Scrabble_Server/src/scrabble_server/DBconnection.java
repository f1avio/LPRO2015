package scrabble_server;

import dBInterface.Chat;
import dBInterface.Room;
import dBInterface.Users;
import dBInterface.PrivateMSG;
import java.util.Arrays;

/**
 * Provides the classes necessary to interface the server with the database.
 * @author Adam Kopnicky 
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
    
    Users database = new Users();
    Room db = new Room();
    Chat missive= new Chat();
    PrivateMSG pmsg = new PrivateMSG();
    Player p;
    int count = 0;
    
    /**
     * Configures some classes to operate under test conditions.
     *<p> List of configured classes: Chat, Room, Users and PrivateMSG.
     * @param y_n (de)activates the test conditions.
     */
    public void setTest(boolean y_n)
    {
        database.setTest(y_n);
        db.setTest(y_n);
        missive.setTest(y_n);
        pmsg.setTest(y_n);
    }
    
    /**
     * Verifies the provided info and if possible, logs the visitor as a user.
     * <p> First it attempts to find the provided username on the database.
     * If it exists, it compares the provided password, previously encrypted, to
     * the one stored on the database.
     * <p> At last, it verifies if the user hasn't been expelled of the system.
     * @param user The username that the visitor is attempting to use as login.
     * @param password The password provided by the visitor.
     * @return An integer, whose value depends of the method's results.
     */
    public int logUser(String user, String password)
    {
        //Users database = new Users();
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
     * @param user The username that will be registered on the system.
     * @param password The password needed to login into the system.
     * @param email The user email, for administrative tasks.
     * @return  a standardized status value, notifying the degree of success of the implementation.
     */
    public int signUser(String user, String password, String email)
    {
        int state = 0;
        //Users database = new Users();
        boolean userExist;
        boolean signupRet;
        boolean emailExist;
        userExist = database.usernameExist(user);
        emailExist = database.emailExist(email);
        
        if (userExist) {
            state = 0;
        }
        else if(emailExist)
        {
            state = 2;
        }
        else {
            signupRet = database.insertUser(user, password, email);
            if (signupRet) {
                state = 1;
            }
        }
        return state;
    }
    
    /**
     * Logs out the user from the system, deactivating his flag "isOnline".
     * @param username the user that needs to log out.
     * @return a standardized status value, notifying the degree of success of the implementation.
     */
    public int logoutUser(String username)
    {
        int state;
        //Users database = new Users();
        boolean userExist;
        boolean active;
        boolean ishost;
        userExist = database.usernameExist(username);
        active = database.getActive(username);
        ishost = db.getHost(username);
        
        //An additional step is necessary to remove him from the game.
        if(ishost)
            db.deleteRoom(username);
            
        if (userExist && active) {
                database.userActive(username, false);
                state = 1;
            } else {
                state = 0;
            }
        return state;
    }
    
    /**
     * Changes the password and email of a user
     * @param actualPass The user's actual password.
     * @param newPass The new pass that will be stored.
     * @param email The new email of the user.
     * @param username The user whose parameters will be changed.
     * @return A standardized status value, notifying the degree of success of the implementation.
     */
    public int change(String actualPass, String newPass, String email, String username){
        int state=0;
        if(!actualPass.equals(database.getPassword(username))){
            return 1;
        }
        state = database.changes(newPass, email, username);
        return state;
    }
    
    /**
     * Returns a list of all the rooms on the system.
     * <p> Acts as an intermediate between lower level and the clientThread.
     * @return A string with all the rooms listed.
     */
    public String receiveRooms(){
        //Room database = new Room();
        String rooms = db.getRooms();
        return rooms;
    }
    /**
     * Returns a list of all the players and their status on a certain room.
     * @param room The room where this information will be checked.
     * @return A string with all the players listed.
     */
    public String[] receiveRoomPlayers(String room){
        //Room database = new Room();
        int i = 0;
        int j = 0;
        char[] roomplayers = db.getRoomPlayers(room).toCharArray();
        char[] roomstatus = db.getRoomStatus(room).toCharArray();
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
     * Returns if a user is currently an host of a room.
     * @param username The user that will ber verified.
     * @return A boolean stating if the user is really a owner of a room.
     */
    public boolean isHost(String username){
        //Room database = new Room();
        boolean ans = db.getHost(username);
        return ans;
    }
    
    /**
     * Sends a new message to the general chat.
     * @param usernameChat The user that sent the message.
     * @param messageChat The content of the message.
     * @return A boolean stating if the operation was successful or not.
     */
    public boolean listChat(String usernameChat, String messageChat) {
        //Chat missive= new Chat(); 
        boolean addChat_MSG = missive.addChat_MSG(usernameChat,messageChat);
        return addChat_MSG;
    }
    
    /**
     * Creates a new room in the system.
     * @param nPlayers The maximum number of players allowed in the room.
     * @param owner The user that creates the room.
     * @param roomName The designed room's name.
     * @return The room's name as a confirmation.
     */
    public String createRoom(int nPlayers, String owner, String roomName){
        int ans;
        String rooms;
        if(db.serverFull()>3) return "NO";

        ans = db.createDBRoom(nPlayers, roomName, owner);
        if(-1 != ans)
            return roomName;
        else
            return "NO";
    }
    
    /**
     * Joins a user as a player to a certain room.
     * <p>It starts by verifying if the user is allowed to enter the room, and
     * if allowed, verifies if the room is already full.
     * @param username The user that will be registered.
     * @param ID the user's identification number.
     * @param roomName The room where he will enter.
     * @return An integer that stores the status of the operation.
     */
    public int join(String username, int ID, String roomName){
        //Room databaseR = new Room();
        //Users databaseU = new Users();
        boolean ansFull;
        int roomID;
        
        if(database.getState(username).equals("KICK"))
            return 2;
        ansFull = db.isRoomFull(roomName);
        if(ansFull){
            return 0;
        }
        
        p = new Player(username, ID);
        
        roomID = db.addPlayerRoom(roomName, username);      
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
        //Room database = new Room();
        String ret="";
        int i = 0;
        boolean owner = isHost(username);
        if(owner){
            if("OK".equals(db.deleteRoom(username))){    
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
            
            if("OK".equals(db.qRoom(players, room))){
                ret = "USER";
            } else{
                ret = "ERROR";
            }
        }
        return ret;
    }
    
    /**
     * Changes the state of a player on the room.
     * <p> The player can switch between two states, "Wait", where he isn't fully
     * commited to start a game, so he simply waits for more players, or "Ready"
     * where he signals the host that he is ready to play.
     * @param username The user that intends to change his state.
     * @param room The room where the player is.
     * @return An update list of players and their states.
     */
    public String[] roomState(String username, String room){
        //Room database = new Room();
        String[] players = receiveRoomPlayers(room);
        //String ret = "";
        int i = 0;
        while(!players[i].equals(username)){
            i++;
        }
        
        switch(players[i+4]){
            case "Ready":
                if(db.updateRoomState(i+1, "Wait", room))
                    players[i+4] = "Wait";
                break;
            case "Wait":
                if(db.updateRoomState(i+1, "Ready", room))
                    players[i+4] = "Ready";
                break;
        }
        //System.out.println("Roomstate: "+Arrays.toString(players));
        return players;
    }
    
    /**
     *Retrieves the global rankings of the system. 
     * @return An array of strings with the complete information.
     */
    public String[] ranking(){  
        //Users database = new Users();
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
        //System.out.println("Ranking: " + Arrays.toString(rank_s));
        return rank_s;
    }
    
    /**
     * Sends a private message a specific user.
     * @param sender The sender of the message.
     * @param receiver The receiver of the message.
     * @param text_msg The content of the message.
     * @return The status of the operation.
     */
    public int sendMessage(String sender, String receiver, String text_msg){    
        int ret = 0;
        ret=pmsg.addPrivate_MSG(sender, receiver, text_msg);
        return ret;  
    }
    
    /**
     * Retrieves a list with all the registered users.
     * @return A list with the names.
     */
    public String getUserList(){
       
        String infos = database.getUsernameList();
       
        return infos;
   }
    
    /**
     * Returns the private messages stored on the database
     * @param user The receiver of the message.
     * @return The private messages stored on a string.
     */
    public String PrivMsgList(String user){              
        String Msg=pmsg.PrivMsgList(user);
        return Msg;
   
    }
    
    /**
     * Counts the number of pending messages.
     * @param userNR The receiver of those messages.
     * @return The number of messages.
     */
    int nrmsg(String userNR) {

        int nr=pmsg.getNrMsg(userNR);
        return nr;
    }
}
