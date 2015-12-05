package scrabble_server;

import dBInterface.DBconnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
class ClientThread extends Thread{
    private Socket connectionSocket = null;
    private final DBconnection conn = new DBconnection();
    
    /**
     * ServiceClient constructor that stores the parameter socket
     * @param socket the socket that needs to be stored
     */
   public ClientThread(Socket socket)
    {
        connectionSocket = socket;
        System.out.println("A new user has connected.");
    }

   /**
    * Method called when a new ClientThread object is started
    */
    @Override public void run()
    {
        String operation;
        int status;
        
        String username, password, email;
        
        while(true)
        {
            try {
                /*Instructs the client to insert his name*/
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                
                /*Reads what the client has to say*/
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                operation = inFromClient.readLine(); /*Dictates the operation that will be applied*/
                   
                switch(operation)
                {  
                    case "login":       username = inFromClient.readLine();
                                        password = inFromClient.readLine();
                                        status = conn.logUser("accounts", username, password);
                                        outToClient.writeBytes(Integer.toString(status)+ '\n');
                                        break;
                        
                    case "register":    username = inFromClient.readLine();
                                        password = inFromClient.readLine();
                                        email = inFromClient.readLine();
                                        status = conn.signUser("accounts", username, password, email);
                                        outToClient.writeBytes(Integer.toString(status)+ '\n');
                                        break;
                        
                    case "logout":      username = inFromClient.readLine();
                                        status = conn.logoutUser("accounts", username);
                                        outToClient.writeBytes(Integer.toString(status)+'\n');
                                        break;
                        
                    default:            break;     
                }
                
        } catch (IOException e) {
            System.out.println("An IOException has occurred.");
        } 
        
    }
  }
    

}
