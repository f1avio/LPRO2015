/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

import dBInterface.DBconnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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
    
   public int logClient(String username, String password)
   {
    int  status = conn.logUser("accounts", username, password);
                if((-1 == status) || (-2 == status) || (-3 == status) || (-4 == status))
                {
                    switch (status)
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
                /*else 
                {
                    if(true == conn.isOnline("accounts", username))
                    {   
                        System.out.println("The client sucessfully logged in. Since this is a test. We will now logout.");
                        conn.logoutUser("accounts", username);
                    }
                    else
                    {
                        System.out.print("Something went wrong. Client didn't logged in");
                        status = -5;
                    }
                }*/
       
       return status;
   }
   
   
   public int signClient(String username, String password, String email)
   {
       int status = conn.signUser("accounts", username, password, email);
       
       return status;
   }
   
   public int logoutClient(String username)
   {
       int status = conn.logoutUser("accounts", username);
       return status;
   }
   
    @Override public void run()
    {
        String operation;
        int aux;
        
        String username, password, email;
        
        while(true)
        {
            try {
                /*Instructs the client to insert his name*/
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                
                /*Reads what the client has to say*/
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                operation = inFromClient.readLine(); //Dictates the operation that will be applied
                   
                switch(operation)
                {  
                    case "login":       username = inFromClient.readLine();
                                        password = inFromClient.readLine();
                                        aux = logClient(username, password);
                                        outToClient.writeBytes(Integer.toString(aux)+ '\n');
                                        break;
                        
                    case "register":    username = inFromClient.readLine();
                                        password = inFromClient.readLine();
                                        email = inFromClient.readLine();
                                        aux = signClient(username, password, email);
                                        outToClient.writeBytes(Integer.toString(aux)+ '\n');
                                        break;
                        
                    case "logout":      username = inFromClient.readLine();
                                        aux = logoutClient(username);
                                        outToClient.writeBytes(Integer.toString(aux)+'\n');
                                        break;
                        
                    default:            break;     
                }
                
        } catch (IOException e) {
        } 
        
    }
  }
    
    
    
}
