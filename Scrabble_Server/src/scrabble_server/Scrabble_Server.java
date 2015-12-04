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

public class Scrabble_Server {

    private static int port= 0;

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
        boolean Connect = DBconnection.connect(aux[1], aux[2], aux[3]);
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