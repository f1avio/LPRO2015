package scrabble_server;

import java.io.*;
import java.net.*;
import dBInterface.*;

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
    
    
    public synchronized void handle(int ID, String msg){
        char[] Array = msg.toCharArray();
        String type = findType(Array);
        String ret = "";
        int result = 0;
        
        System.out.println("CLIENT COUNT HANDLE " + clientCount);
        
        switch (type) {
            case "LOGIN":
                String usernameLogin = findMessage(Array,6,1);
                clients[findClient(ID)].username = usernameLogin;
                System.out.println("[Server][Socket]" + "USERNAME do SERVER: " + usernameLogin);
                String passwordLogin = findMessage(Array,6,2);
                System.out.println("[Server][Socket]" + "PW do SERVER: " + passwordLogin);

                result = DBcon.logUser(usernameLogin, passwordLogin);
                String tables = "";
                switch (result) {
                    case 0: {
                        ret = "LOGIN#FAIL#NORMAL#";
                        break;
                    }
                    case 1: {
                        ret = "LOGIN#OK#NORMAL#" + usernameLogin + "#";
                        tables = DBcon.receiveTables();
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
                
                
                if(!tables.equals("")) //Announce("TABLES", " ", tables);
                    clients[findClient(ID)].send("TABLES#" + tables + "#");
                
                break;
            case "LOGOUT":
                String usernameLogout = findMessage(Array, 7, 1);//findLogoutUsername(Array);
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
                String usernameRegist = findMessage(Array,7,1);
                //System.out.println("[SIGNUP] usernameRegist: "+usernameRegist);
                String passwordRegist = findMessage(Array,7,2);
                //System.out.println("[SIGNUP] passwordRegist: "+passwordRegist);
                String emailRegist = findMessage(Array, 7, 3);
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
    
    public String findType(char Array[]) {
        int i = 0;
        String type = "";

        while (Array[i] != '#') {
            type = type + Array[i];
            i++;
        }
        return type;
    }
    
    public String findMessage(char Array[],int i,int nrParam ){
        String message="";
        int j=1;
        
        if(nrParam>1){
        while(j!=nrParam){
        while (Array[i] != '#') {
            i++;
        }
        j++;
        i++;
        }
        }
        while (Array[i] != '#') {
            message = message + Array[i];
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