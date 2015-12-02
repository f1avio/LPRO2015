/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */

public class ClientService {
    
    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password){
        String hostname= "localhost";
        Socket clientSocket = null;
        int port=1513;
        boolean Success = false;
        MD5 hash = new MD5();
        
         try{
            clientSocket = new Socket(hostname, port);
           
            /*Passes the password through the MD5Hash*/
            String hashpass = hash.convert(password);
            /*Delivers the user input to the server*/
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(username + "\n");
            outToServer.writeBytes(hashpass + "\n");
            
            /*Read the response from the server*/
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            if(inFromServer.readLine().equals("ok"))
                Success = true;
            else
                Success = false;
          
             } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
            System.exit(1);
        } finally {
             if(null != clientSocket)
                try {
                    clientSocket.close();
             } catch (IOException ex) {
                 Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
             }
         }    
         return Success;
    }
}
