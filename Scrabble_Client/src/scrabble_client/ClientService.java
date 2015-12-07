package scrabble_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


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
    JOptionPane frame;
    
    
    public ClientService(){
    }
        /**
        * Constructor that reads the config.txt file to set parameters to the connection
        */
        public static void readServer(){
            /*Attempts to read a configuration file */
            /*Step 0: Initialize the files*/
            BufferedReader inputStream = null;
            int i = 0;
            String aux[] = new String[3];
            String file = "config.txt";
            final JPanel frame = new JPanel();
            try {
                inputStream = new BufferedReader(new FileReader(file));
                while ((aux[i] = inputStream.readLine()) != null) {
                    i++;
                }
            
                port = Integer.parseInt(aux[0]);
                hostname = aux[1];
                clientSocket = new Socket(hostname, port);
                
                //System.out.println("hostname:"+ hostname + "\nport:"+port);
            
            }catch (FileNotFoundException f){
                System.err.println("Caught FileNotFoundException: " + f.getMessage());
            } 
       
            catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
                JOptionPane.showMessageDialog(frame, "Error: Server or Port");
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
    
    
    /**Passes the command and the necessary arguments to the server to perform a sign up
     * @param username The user username within the game
     * @param password The password needed to login into the system
     * @param email The user email, for administrative tasks
     * @return  a standardized status value, notifying the degree of success of the implementation
     */
    public int register(String username, String password, String email)
    {
        int status;
        DataOutputStream outToServer;
        
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
     * Passes the command and the necessary arguments to the server to perform a log in
     * @param username the user's name that needs to log in
     * @param password the user's password
     * @return a standardized status value, notifying the degree of success of the implementation
     */
    public int login(String username, String password){
        MD5 hash = new MD5();
        int aux = 0;
        
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
            aux = Integer.parseInt(inFromServer.readLine());
            
             } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
            System.exit(1);
        }   
         return aux;
    }
    
    /** Verifies if the email address is a valid one
     * @param email the email address to be verified
     * @return a boolean indicating the email's validaty
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
    
    /**
     * Passes the command and the necessary arguments to the server to perform a logout
     * @param username the user's name that needs to log out
     * @return a standardized status value, notifying the degree of success of the implementation
     */
    public int logout(String username) 
    {
        int status = -2;
        DataOutputStream outToServer;
        BufferedReader inFromServer;
        
        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("logout\n"); //Command
            outToServer.writeBytes(username+"\n");
            
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            status = Integer.parseInt(inFromServer.readLine());
        } catch (IOException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }
}
