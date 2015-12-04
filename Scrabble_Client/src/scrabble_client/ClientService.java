/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */

public class ClientService {
    
    static String hostname= "localhost";
    static Socket clientSocket = null;
    static int port=1513;
   
    public ClientService(){
        /*Attempts to read a configuration file */
        /*Step 0: Initialize the files*/
        BufferedReader inputStream = null;
        int i = 0;
        String aux[] = new String[3];
        String file = "config.txt";
        
        try {
            inputStream = new BufferedReader(new FileReader(file));
            while ((aux[i] = inputStream.readLine()) != null) {
                i++;
            }
            
            port = Integer.parseInt(aux[0]);
            hostname = aux[1];
            clientSocket = new Socket(hostname, port);  
            
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
    }
    /**
     * 
     * @param username
     * @param password
     * @param email
     * @return 
     */
    public int register(String username, String password, String email)
    {
        int status = 0;
        DataOutputStream outToServer = null;
        
        try {
            MD5 hash = new MD5();
            /*Passes the password through the MD5Hash*/
            String hashpass = hash.convert(password);
            /*Delivers the user input to the server*/
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("register\n"); //Command
            outToServer.writeBytes(username + "\n");
            outToServer.writeBytes(hashpass + "\n");
            outToServer.writeBytes(email + "\n");
            
            /*Read the response from the server*/
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            status = Integer.parseInt(inFromServer.readLine());
         
        } catch (IOException ex) {
            status = -4; //Failed to transmit data to the server
        } catch (NumberFormatException e) {
            status = -3; /*Failed to read the response from the server. Contact the administrator
                           for more information */
        }   
        
    return status;
    }
    /**
     * @param username 
     * @param password 
     * @return
     */
    public boolean login(String username, String password){
        boolean Success = false;
        MD5 hash = new MD5();
        
         try{
            /*Passes the password through the MD5Hash*/
            String hashpass = hash.convert(password);
            /*Delivers the user input to the server*/
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("login\n"); //Command
            outToServer.writeBytes(username + "\n");
            outToServer.writeBytes(hashpass + "\n");
            
            /*Read the response from the server*/
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            int aux = Integer.parseInt(inFromServer.readLine());
            if(0 == aux)
                Success = true;
            else
                Success = false;
          
             } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
            System.exit(1);
        }   
         return Success;
    }
    
    /**
     * @param email
     * @return 
     */
    public static boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
   return result;
}
    
    
}
