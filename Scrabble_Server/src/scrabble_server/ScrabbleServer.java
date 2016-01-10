package scrabble_server;

import java.io.*;
import java.net.*;
import dBInterface.*;
import java.util.Arrays;

/**
 * Starts the server and attends the messages received from the clients.
 * @author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */
public class ScrabbleServer  implements Runnable{
    
     ClientThread clients[];
     ServerSocket server = null;
     Thread thread = null;
     int clientCount = 0;
    DBconnection DBcon = new DBconnection();
    //DBconnection DBcon = DBconnection.getInstance();
    static int port;
    /* FROM: http://stackoverflow.com/questions/1813853/ifdef-ifndef-in-java */
    private static final boolean debug = false;
    
    private static ScrabbleServer instance = null;
    /**
     * Creates a new instance of the class ScrabbleServer.
     * @return The instance of the class ScrabbleServer.
     */
    public static ScrabbleServer getInstance(){
        if (instance == null) {
            getPort();
            instance = new ScrabbleServer(port);
        }
        return instance;
    }
    
    /**
     * Binds the port and configures the limit of supported users.
     * @param port The port to which the socket will be binded.
     */
    public ScrabbleServer(int port){
        try {
            clients = new ClientThread[50];
            System.out.println("[Server][Socket]" + "Binding to port " + port + ", please wait  ...");
            server = new ServerSocket(port);
            //port = server.getLocalPort();
            System.out.println("[Server][Socket]" + "Server started: " + server);
            start();
        } catch (IOException ioe) {
            System.out.println("[Server][Socket]" + ioe);
        }
    }

    public void run(){
        while (thread != null) {
            try {
                System.out.println("[Server][Socket]" + "Waiting for a client ...");
                addThread(server.accept());
            } catch (IOException ie) {
                System.out.println("[Server][Socket]" + "Acceptance Error: " + ie);
            }
        }
    }
    
    /**
     * Start a new thread.
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    /**
     * @deprecated This function should be avoided.
     * Stops the actual thread.
     */
    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }
    
    /**
     * Finds the client through his ID.
     * @param ID The ID that will be verified.
     * @return The position of the client in the array.
     */
    public int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Broadcast a message to all the clients.
     * @param type The type of message.
     * @param sender The sender of the message.
     * @param content The content of the message.
     */
    public void Announce(String type, String sender, String content) {
        //Message msg = new Message(type, sender, content, "All");
        String msg = type + "#" + sender + "#" + content + "#";
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(msg);
        }
        //System.out.println("<< Sending: " + msg);
    }
    
    /**
     * Broadcasts a message to all clients that entered a certain room.
     * @param players The players that are in the room.
     * @param msg The content of the message.
     */
    public void AnnounceRoom(String[] players, String msg){
        int i = 0;
        String ret = "";
        switch(msg){
            case "UPLAYERS":
                ret = "UPLAYERS#" + players[0] + "#" + players[1] + "#" + players[2] + "#" + players[3] + "#"
                    + players[4] + "#" + players[5] + "#" + players[6] + "#" + players[7] + "#";
                break;
            case "DELETE":
                ret = "QUITROOM#OK#";
                break;
            case "STARTGAME":
                ret = "STARTGAME#OK#";
        }
        
        while(i<players.length && !"NULL".equals(players[i])){
            for(int j=0; j<clientCount; j++){
                if(clients[j].username.equals(players[i])){
                    clients[j].send(ret);
                }
            }
            i++;
        }
        System.out.println("<< Sending: " + ret);
    }
    
    /**
     * Handles the messages received from the clients.
     * @param ID The ID of the client.
     * @param msg The content of the message.
     */
    public synchronized void handle(int ID, String msg){
        char[] data = msg.toCharArray();
        String type = findType(data);
        String ret = "";
        int result = 0;       
        
        System.out.println("CLIENT COUNT HANDLE " + clientCount);
        
        switch (type) {
            case "LOGIN":
                String usernameLogin = findMessage(data,6,1);
                clients[findClient(ID)].username = usernameLogin;
                //System.out.println("[Server][Socket]" + "USERNAME do SERVER: " + usernameLogin);
                String passwordLogin = findMessage(data,6,2);
                //System.out.println("[Server][Socket]" + "PW do SERVER: " + passwordLogin);

                result = DBcon.logUser(usernameLogin, passwordLogin);
                String rooms = "";
                switch (result) {
                    case 0: {
                        ret = "LOGIN#FAIL#NORMAL#";
                        break;
                    }
                    case 1: {
                        ret = "LOGIN#OK#NORMAL#" + usernameLogin + "#";
                        rooms = DBcon.receiveRooms();
                        break;
                    }
                    case 2: {
                        ret = "LOGIN#EXIST#";
                        break;
                    }
                    case 3:{
                        ret = "LOGIN#OK#ADMIN#" + usernameLogin + "#";
                        break;
                    }
                    case 4: {
                        ret = "LOGIN#FAIL#BAN#";
                        break;
                    }
                    default: {
                        ret = "LOGIN#ERROR#";
                        break;
                    }

                }
                System.out.println("<< Sending: " + ret);
                //System.out.println("ID : " + ID + " client id : " + findClient(ID));
                clients[findClient(ID)].send(ret);
                
                
                if(!rooms.equals(""))
                    clients[findClient(ID)].send("ROOMS#" + rooms + "#");
                
                break;
            case "LOGOUT":
                String usernameLogout = findMessage(data, 7, 1);
                //System.out.println("[Server][Socket]" + "Username : " + usernameLogout);
                
                result = DBcon.logoutUser(usernameLogout);

                switch (result) {
                    case 0: {
                        ret = "LOGOUT#FAIL#";
                        break;
                    }
                    case 1: {
                        ret = "LOGOUT#OK#";
                        break;
                    }
                    default: {
                        ret = "LOGOUT#ERROR#";
                        break;
                    }
                }
                System.out.println("<< Sending: " + ret);
                clients[findClient(ID)].send(ret);
                if(result == 1) remove(ID);
                
                break;
            case "SIGNUP": {
                String usernameRegist = findMessage(data,7,1);
                //System.out.println("[SIGNUP] usernameRegist: "+usernameRegist);
                String passwordRegist = findMessage(data,7,2);
                //System.out.println("[SIGNUP] passwordRegist: "+passwordRegist);
                String emailRegist = findMessage(data, 7, 3);
                //System.out.println("[SIGNUP] emailRegist: "+emailRegist);
               

                result = DBcon.signUser(usernameRegist, passwordRegist, emailRegist);

                switch (result) {
                    case 0: {
                        ret = "SIGNUP#FAIL#";
                        break;
                    }
                    case 1: {
                        ret = "SIGNUP#OK#";
                        break;
                    }
                    case 2: {
                        ret = "SIGNUP#EMAIL#";
                        break;
                    }
                    default: {
                        ret = "SIGNUP#ERROR#";
                        break;
                    }
                }
                System.out.println("<< Sending: " + ret);
                clients[findClient(ID)].send(ret);
                break;

            }
            case "CHANGE":{
                String actualPass = findMessage(data, 7, 1);
                String newPass = findMessage(data, 7, 2);
                String email = findMessage(data, 7, 3);
                String username = findMessage(data, 7, 4);
                result = DBcon.change(actualPass, newPass, email, username);
                switch(result){
                    case 2:{
                        ret = "CHANGE#OK#";
                        break;
                    }
                    case 1:{
                        ret="CHANGE#FAIL#";     //actualPass wrong
                        break;
                    }
                    case 0:{
                        ret = "CHANGE#ERROR#";
                        break;
                    }
                }
                System.out.println("<< Sending: " + ret);
                clients[findClient(ID)].send(ret);
                break;
            }
            case "CREATEROOM":{
                String owner = findMessage(data, 11,2);
                String roomName = findMessage(data,11, 3);
                int nPlayers = Character.getNumericValue(data[11]);
                String room = DBcon.receiveRooms();
                String users = DBcon.getUserList();
                
                if(debug)
                {
                    System.out.println("ReceiveRooms " +room);
                    System.out.println("Users list " + users);
                }
                //Erro está aqui.
                if(users.contains(roomName) || room.contains(roomName) ) //In order to simplify the program, this behaviour is inhibited.
                    ret = "CREATEROOM#FAIL#";                 
                else
                {
                    room = DBcon.createRoom(nPlayers, owner, roomName);
                    if(debug)
                        System.out.println("Result of create room: " + room);
                    
                    if(room.equals("NO"))
                        ret = "CREATEROOM#FAIL#";
                    else
                    {
                        ret = "CREATEROOM#OK#"+roomName+"#";
                    
                        System.out.println("<< Sending: " + ret);
                        clients[findClient(ID)].send(ret);
                
                        int ansJoin = DBcon.join(clients[findClient(ID)].username, findClient(ID), roomName); 
                
                        switch(ansJoin){
                            case 0:
                                ret = "JOINROOM#FAIL#";
                                break;
                            case 1:
                                ret = "JOINROOM#OWNER#"+roomName+"#";
                                break;
                            default:
                                ret = "JOINROOM#ERROR#";
                                break;
                        }
                        
                        clients[findClient(ID)].send(ret);
                    } 
                }
            System.out.println("<< Sending: " + ret);
            break;
            }
            case "JOINROOM":{
                String room = findMessage(data, 9, 1);
                //System.out.println("JOINROOM room: " + room);
                //System.out.println("USERNAME JOINROOM: " + clients[findClient(ID)].username);
                result = DBcon.join(clients[findClient(ID)].username, findClient(ID), room);
                
                switch(result){
                    case 0:
                        ret = "JOINROOM#FAIL#";
                        break;
                    case 1:
                        ret = "JOINROOM#OK#" + room + "#";
                        break;
                    case 2:
                        ret = "JOINROOM#ADMIN#";
                        break;
                    default:
                        ret = "JOINROOM#ERROR#";
                        break;
                }
                clients[findClient(ID)].send(ret);
                System.out.println("<< Sending: " + ret);
                break;
            }
            
            case "STARTGAME":{
                //Step 1: Retrieve the room name
                String room = findMessage(data, 10, 1);
                if(debug)
                    System.out.println("The room is" + room);
                //Step 2: Retrieve the players on them.
                String players[] = DBcon.receiveRoomPlayers(room);
                if(debug)
                    System.out.println(Arrays.toString(players));

                //Step 3: Send message to every that the game will start.
                AnnounceRoom(players, "STARTGAME");
                break;
            }
            case "VIEWROOMS":{
                rooms = DBcon.receiveRooms();
                clients[findClient(ID)].send("ROOMS#"+ rooms + "#");
                System.out.println("<< Sending: " + "ROOMS#"+ rooms + "#");
                break;
            }
            case "QUITROOM":{
                String username = findMessage(data, 9, 1);
                String room = findMessage(data, 9,2);
                String[] players = DBcon.receiveRoomPlayers(room);                
                String quit = DBcon.quitRoom(username, room);                
                switch(quit){
                    case "OWNER":{
                        AnnounceRoom(players, "DELETE");
                        break;}
                    case "USER":{
                        players = DBcon.receiveRoomPlayers(room);
                        clients[findClient(ID)].send("QUITROOM#OK#");
                        System.out.println("<< Sending: " + "QUITROOM#OK#");
                        AnnounceRoom(players, "UPLAYERS");
                        break;}
                    case "ERROR":{
                        clients[findClient(ID)].send("QUITROOM#ERROR#");
                        System.out.println("<< Sending: " + "QUITROOM#ERROR#");
                        //AnnounceRoom(players, "ERROR");
                        break;}
                }
                break;
            }
            case "CHAT": {
                String usernameChat = findMessage(data,5,1);                
                String messageChat = findMessage(data,5,2);
                
                System.out.println("[CHAT]" + " Username: " + usernameChat+ " Message: " + messageChat);

                DBcon.listChat(usernameChat,messageChat);
                
                
                Announce("CHAT", usernameChat, messageChat);
                break;
            }
            case "UPLAYERS":{
                String[] players = DBcon.receiveRoomPlayers(findMessage(data, 9, 1));
                AnnounceRoom(players, "UPLAYERS");
                break;
            } 
            case "READY":{
                String username = findMessage(data, 6, 1);
                String room = findMessage(data, 6, 2);               
                String[] players = DBcon.roomState(username, room);
                AnnounceRoom(players, "UPLAYERS");
            }
            case "RANKING":{
                 String[] result_s = DBcon.ranking();
                 ret = "RANKING#" + Arrays.toString(result_s) + "#";
                 System.out.println("<< Sending: " + ret);
                 clients[findClient(ID)].send(ret);                 
                 break;
            }
            case "PRIVATE":{                
               String sender = findMessage(data,8,1);
               String receiver = findMessage(data,8,2);
               String txt_msg = findMessage(data,8,3);
               
               //System.out.println("\n\ntext"+txt_msg);
               result = DBcon.sendMessage(sender,receiver,txt_msg);
               
                switch (result) {
                    case 0: {
                        ret = "PRIVATE#FAIL#";
                        break;
                    }
                    case 1: {
                        ret = "PRIVATE#OK#";
                        break;
                    }
                    default: {
                        ret = "PRIVATE#ERROR#";
                        break;
                    }

                }
                System.out.println("<< Sending: " + ret);        
                clients[findClient(ID)].send(ret);
                break;
            
            }
            case "DISPLAY": {
                 ret = "DISPLAY#" + DBcon.getUserList() + "#";
                 System.out.println("<< Sending: " + ret);
                 clients[findClient(ID)].send(ret);
                 break;
            }
            case "MSGLIST":{
                String user=findMessage(data,8,1);
                //System.out.println("Socket Server username detetada: "+user);
                //String Nr_msg=findMessage(data,8,2);
                int nr_msg =0; //Integer.parseInt(Nr_msg);
                String chat = DBcon.PrivMsgList(user);
                ret = "MSGLIST#" + chat + "#";
                System.out.println("<< Sending: " + ret);
                clients[findClient(ID)].send(ret);    
                break;
            }
            case "MSGNR":{
                 int nr;
                 String user_NR=findMessage(data,6,1);
                 
                 nr=DBcon.nrmsg(user_NR);
                 
                 ret="MSGNR#"+nr+"#";
                 clients[findClient(ID)].send(ret);
                 break;
            }
        }
    
    }
    /**
     * Terminates a connection with a client.
     * @param ID The ID of the client.
     */
    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            ClientThread toTerminate = clients[pos];
            System.out.println("[Server][Socket]" + "\nRemoving client thread " + ID + " at " + pos);
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                System.out.println("[Server][Socket]" + "\nError closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }
    
    /**
     * Adds a new client to the system.
     * @param socket The socket to which he is connected.
     * @throws IOException Exception produced by failed or interrupted I/O operations.
     */
    public void addThread(Socket socket) throws IOException {

        if (clientCount < clients.length) {

            System.out.println("[Server][Socket]" + "Client accepted: " + socket);
            clients[clientCount] = new ClientThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;     
               
            } catch (IOException ioe) {
                System.out.println("[Server][Socket]" + "Error opening thread: " + ioe);
            }

        } else {
            System.out.println("[Server][Socket]" + "\nClient refused: maximum " + clients.length + " reached.");
        }
    }
    
    /**
     * Finds what type a message has been received.
     * @param data The message to be analyzed.
     * @return The type of message.
     */
    public String findType(char data[]) {
        int i = 0;
        String type = "";

        while (data[i] != '#') {
            type = type + data[i];
            i++;
        }
        return type;
    }
    
    /**
     * Retrieves part of the message.
     * @param data The complete message.
     * @param i The index of the array where the search will start.
     * @param nrParam The number of parts to retrieve.
     * @return The message found.
     */
    public String findMessage(char data[],int i,int nrParam ){
        String message="";
        int j=1;
        
        if(nrParam>1){
            while(j!=nrParam){
                while (data[i] != '#') {
                    i++;
                }
                j++;
                i++;
            }
        }
        while (data[i] != '#') {
            message = message + data[i];
            i++;
        }
        //System.out.printf("\nPassword : %s\n", password);

        return message;
    }
    
    /**
     * Reads the port from a configuration file.
     */
    static void getPort(){
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
        port = Integer.parseInt(aux[0]);
    }
    
    /**
     * Initializes the database link and awaits for a connection of an new Client 
     * @param args Stores the arguments passed through the command line.
     */
    public static void main(String args[]) {
        DbSetup dbconn = new DbSetup();
        dbconn.setDB();
        ScrabbleServer server = ScrabbleServer.getInstance();       
    }
}
