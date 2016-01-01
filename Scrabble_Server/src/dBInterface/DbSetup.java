/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Flávio Dias 
 */
public class DbSetup {
    /**
     * Array of strings that stores the values needed to connect to the database.
     * {@code parameters[0] = port;}
     * {@code parameters[1] = url;}
     * {@code parameters[2] = username;}
     * {@code parameters[3] = password;}
     */
    private static String parameters[] = new String[5];
    
    /**
     * Construtor that configures the PostgreSql Driver.
     */
    public DbSetup(){
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            System.out.println("Erro: DbSetup()");
        }

        
    }
    /**
     * Seeks the necessary parameters to connect to the database.
     * <p>
     * These parameters reside on a document file named config.txt that provides
     * the port, the url and username and password.
     */
    public void setDB(){
        /*Step 0: Initialize the files*/
        BufferedReader inputStream = null;
        int i = 0;
        String file = "config.txt";
        try {
            inputStream = new BufferedReader(new FileReader(file));
            while ((parameters[i] = inputStream.readLine()) != null) {
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
    }
    /**
     * Retrieves the necessary parameters to the connection of the database.
     * @return A string with the mentioned parameters.
     */
    public String[] getDB()
    {
        return parameters;
    }
    
}
