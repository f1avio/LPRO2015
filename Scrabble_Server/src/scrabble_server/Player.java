/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

/**
 * Stores the information of an user when he becomes a player.
 * @author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */
public class Player {
    
    String username;
    int ID;
    public int points=0;
    
    /**
     * Creates a new player instance.
     * @param name The name of the player.
     * @param ID An internal ID of the player.
     */
    public Player(String name, int ID){
        this.username = name;
        this.ID = ID;
    }
    
    /**
     * Returns the player username.
     * @return The username.
     */
    public String getUsername(){
        return this.username;
    }
    /**
     * Returns the ID of the player.
     * @return The ID of the player.
     */
    public int getID(){
        return this.ID;
    }
    
    /**
     * Returns the number of points accumulated.
     * @return The number of points.
     */
    public int getPoints(){
        return this.points;
    }
    
    
}
