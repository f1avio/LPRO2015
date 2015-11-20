package scrabble_server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */


class ClientThread extends Thread{
    private Socket connectionSocket = null;
    private final DBInterface conn = new DBInterface();
    
    /**
     * ServiceClient constructor that stores the parameter socket
     * @param socket the socket that needs to be stored
     */
   public ClientThread(Socket socket)
    {
        connectionSocket = socket;
        System.out.println("A new user has connected.");
    }
    
    @Override public void run()
    {
        try {
           
                /*Instructs the client to insert his name*/
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                
                /*Reads what the client has to say*/
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String username = inFromClient.readLine();
                String password = inFromClient.readLine();
                //System.out.println("Username is: " + username +" Password : "+password);

                int aux = conn.logUser("accounts", username, password);
                if((-1 == aux) || (-2 == aux) || (-3 == aux) || (-4 == aux))
                {
                    outToClient.writeBytes("not ok\n");
                    switch (aux)
                            {
                        case -1:System.out.println("The username provided doesn't exist.");
                                break;
                        case -2:System.out.println("The password is incorrect.");
                                break;
                        case -3:System.out.println("The user is already online.");
                                break;
                        case -4:System.out.println("The connection to the database was failed");
                                break;
                        default:System.out.println("Something went wrong!");
                                break;
                    }
                }
                else
                {
                    if(true == conn.isOnline("accounts", username))
                    {   
                        outToClient.writeBytes("ok\n");
                        System.out.println("The client sucessfully logged in. Since this is a test. We will now logout.");
                        conn.logoutUser("accounts", username);
                    }
                    else
                    {
                        System.out.print("Something went wrong. Client didn't logged in");
                        outToClient.writeBytes("not ok\n");
                    }
                }
        } catch (IOException e) {
        } finally {
            if(null != connectionSocket)
                try {
                    connectionSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Scrabble_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Scrabble_Server {

    private static int port= 1513;
    private ServerSocket sk = null;
    
    
    /**
     * Initializes the database link and awaits for a connection of an new Client 
     * @param args Stores the arguments passed through the terminal
     */
    public static void main(String args[]) {
        
        /*Step One: Initialize database*/
        boolean Connect = DBInterface.connect("jdbc:postgresql://vdbm.fe.up.pt/lpro1513", "lpro1513", "C4bh X7aai");
        if(false == Connect)
        {
            System.out.println("Failed to connect to the database. Exiting now.");
            return;
        }
        
        /*Step Two: Start accepting new connections*/
        try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (true) 
            {
	        new ClientThread(serverSocket.accept()).start();
	    }
            
	} catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }  
            
    }
}