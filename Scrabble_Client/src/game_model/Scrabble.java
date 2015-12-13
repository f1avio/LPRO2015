/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import java.util.ArrayList;

/**
 *
 * @author HUGUETA
 */
public class Scrabble {
    public final Board board = new Board();
    public ArrayList<Player> players;
    public Bag bag = new Bag();
    
    public Scrabble(int playersNumber)
    {
        players = new ArrayList<Player>(playersNumber);
    }
    
    public void run(){
        
    }
    
    public void firstLetters()
    {
        for(Player p : players)
            for(int i=0; i<7;i++)
                p.addLetter(bag.getNext());
    }
    
}
