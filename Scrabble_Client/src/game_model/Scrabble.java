/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import java.util.ArrayList;

/**
 * Initiates a game of scrabble, distributing the letters among them.
 * @author HUGUETA
 */
public class Scrabble {

    public final Board board = new Board();
    public ArrayList<Player> players;
    public Bag bag = new Bag();
    /**
     * Constructs an empty list with the specified initial capacity.
     * @param playersNumber The specified initial capacity.
     */
    public Scrabble(int playersNumber)
    {
        players = new ArrayList<>(playersNumber);
    }
    //
    //public void run(){
    //    
    //}
    
    /**
     * Gives to each player seven tiles from the bag.
     */
    public void firstLetters()
    {
        for(Player p : players)
            for(int i=0; i<7;i++)
                p.addLetter(bag.getNext());
    }
    
}
