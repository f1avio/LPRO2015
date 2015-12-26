/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

/**
 *
 * @author HUGUETA
 */
public class Player {
    
    String username;
    int ID;
    public int points=0;
    
    public Player(String name, int ID){
        this.username = name;
        this.ID = ID;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public int getPoints(){
        return this.points;
    }
    
    
}
