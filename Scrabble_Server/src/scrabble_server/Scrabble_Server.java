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

    private static int port= 0;
    private ServerSocket sk = null;
    
    
    /**
     * Initializes the database link and awaits for a connection of an new Client 
     * @param args Stores the arguments passed through the terminal
     */
    public static void main(String args[]) {
        
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
    
        /*Step One: Initialize database*/
        boolean Connect = DBInterface.connect(aux[1], aux[2], aux[3]);
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