package scrabble_server;

import java.io.*;
import java.net.*;
import dBInterface.*;
import java.util.Arrays;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
public class ScrabbleServer  implements Runnable{
    
    public ClientThread clients[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int clientCount = 0;
    DBconnection DBcon = DBconnection.getInstance();
    static int port;
    
    private static ScrabbleServer instance = null;
    
    public static ScrabbleServer getInstance(){
        if (instance == null) {
            getPort();
            instance = new ScrabbleServer(port);
        }
        return instance;
    }
    
    
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
    

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }
    
    public int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }
    
    public void Announce(String type, String sender, String content) {
        //Message msg = new Message(type, sender, content, "All");
        String msg = type + "#" + sender + "#" + content + "#";
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(msg);
        }
    }
    
    public void AnnounceRoom(String username, String[] players, String msg){
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
        }

        while(i<players.length && !"NULL".equals(players[i])){
            for(int j=0; j<clientCount; j++){
                if(clients[j].username.equals(players[i])){
                    clients[j].send(ret);
                }
                if(clients[j].username.equals(username)){
                    clients[j].send("QUITROOM#OK#");
                }
            }
            i++;
        }
    }
    
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
                System.out.println("[Server][Socket]" + "USERNAME do SERVER: " + usernameLogin);
                String passwordLogin = findMessage(data,6,2);
                System.out.println("[Server][Socket]" + "PW do SERVER: " + passwordLogin);

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
                System.out.println("[Server][Socket] Sending: " + ret);
                System.out.println("ID : " + ID + "client id : " + findClient(ID));
                clients[findClient(ID)].send(ret);
                
                
                if(!rooms.equals(""))
                    clients[findClient(ID)].send("ROOMS#" + rooms + "#");
                
                break;
            case "LOGOUT":
                String usernameLogout = findMessage(data, 7, 1);
                System.out.println("[Server][Socket]" + "Username : " + usernameLogout);
                
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
                    default: {
                        ret = "SIGNUP#ERROR#";
                        break;
                    }

                }

                clients[findClient(ID)].send(ret);
                break;

            }
            case "CREATEROOM":{
                String owner = findMessage(data, 11,2);
                String roomName = findMessage(data,11, 3);
                int nPlayers = Character.getNumericValue(data[11]);
                
                String room = DBcon.createRoom(nPlayers, owner, roomName);
                
                switch(room){
                    case "":
                        ret = "CREATEROOM#FAIL#";
                        break;
                    default:
                        ret = "CREATEROOM#OK#"+room+"#";
                        break;
                }
                clients[findClient(ID)].send(ret);
                
                int ansJoin = DBcon.join(clients[findClient(ID)].username, findClient(ID), room); 
                
                switch(ansJoin){
                    case 0:
                        ret = "JOINROOM#FAIL#";
                        break;
                    case 1:
                        ret = "JOINROOM#OWNER#"+room+"#";
                        break;
                    default:
                        ret = "JOINROOM#ERROR#";
                        break;
                }
                clients[findClient(ID)].send(ret);
                break;
            }
            case "JOINROOM":{
                String room = findMessage(data, 9, 1);
                System.out.println("JOINROOM room: " + room);
                System.out.println("USERNAME JOINROOM: " + clients[findClient(ID)].username);
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
                System.out.println("[JOINROOM] ret: "+ret);
                break;
            }
            case "VIEWROOMS":{
                rooms = DBcon.receiveRooms();
                clients[findClient(ID)].send("ROOMS#"+ rooms + "#");
                break;
            }
            case "QUITROOM":{
                String username = findMessage(data, 9, 1);
                String room = findMessage(data, 9,2);
                String quit = DBcon.quitRoom(username, room);
                switch(quit){
                    case "OWNER":
                        AnnounceRoom(username, DBcon.receiveRoomPlayers(room), "DELETE");
                        break;
                    case "USER":
                        AnnounceRoom(username, DBcon.receiveRoomPlayers(room), "UPLAYERS");
                        break;
                    case "ERROR":
                        AnnounceRoom(username, DBcon.receiveRoomPlayers(room), "ERROR");
                        break;
                }
                break;
            }
            case "CHAT": {
                String usernameChat = findMessage(data,5,1);
                
                String messageChat = findMessage(data,5,2);
                
                System.out.println("[Server][Socket]" + " Username : " + usernameChat);
                System.out.println("[Server][Socket]" + " Message : " + messageChat);

                DBcon.listChat(usernameChat,messageChat);
                
                
                Announce("CHAT", usernameChat, messageChat);
                break;
            }
            case "UPLAYERS":{
                String[] players = DBcon.receiveRoomPlayers(findMessage(data, 9, 1));
                AnnounceRoom("NULL", players, "UPLAYERS");
                break;
            }   
        }
    
    }
    
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
    
    public String findType(char data[]) {
        int i = 0;
        String type = "";

        while (data[i] != '#') {
            type = type + data[i];
            i++;
        }
        return type;
    }
    
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
     * @param args Stores the arguments passed through the terminal
     */
    public static void main(String args[]) {
        ScrabbleServer server = ScrabbleServer.getInstance();       
    }
}