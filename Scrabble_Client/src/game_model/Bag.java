/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Fills in the bag with the necessary letters, with their positions randomized.
 * @author Flávio
 */
public class Bag {
     private List<Letter> bag;
     private Iterator<Letter> iter;
    
    /**
     * Fills in the bag with all the letters, randomly.
     */
    public Bag() {
        
        this.bag = new ArrayList<>();
        
        // Add all letters to the bag...
     
    for (Character ch : Letter.letters)
        for (int i=0; i< Letter.getAmountOfLettersInPool(ch);i++)
            bag.add(new Letter(ch));
                    
    shuffleBag(bag);
         
    iter = bag.iterator();
    }
    /**
     * Shuffles the contents of the bag.
     * @param a The bag that will be shuffled.
     */
    private static void shuffleBag(List<Letter> a) {
        int n= a.toArray().length;
          
        Random random=new Random();
        random.nextInt();
          
          
        for (int i=0;i<n;i++){
            int change = i + random.nextInt(n-i);
            swap (a,i,change);
        }
    }
    /**
     * Changes the position of two tile inside the bag.
     * @param a The bag's identifier.
     * @param i The position of one of the tiles.
     * @param change The other position of another tile.
     */
    private static void swap(List<Letter> a,int i,int change){
        Letter helper =a.get(i);
        a.set(i, a.get(change));
        a.set(change,helper);
    }
    /**
     * Takes one tile from the bag.
     * @return If it exists, a tile.
     */
    public Letter getNext()
    {
        if(!iter.hasNext())
            return null;
        
        return(iter.next());
    }
    /**
     * Verifies if there are still tiles in the bag.
     * @return A boolean stating if there are tiles in the bag.
     */
    public boolean hasNext()
    {
        return(iter.hasNext());
    }
    
}
    

