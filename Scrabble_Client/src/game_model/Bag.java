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


public class Bag {
     private List<Letter> bag = new ArrayList<Letter>();
     private Iterator<Letter> iter;
    
    
    public Bag() {
     
        // Add all letters to the bag...
     
     for (Character ch : Letter.letters)
         for (int i=0; i< Letter.getAmountOfLettersinPool(ch);i++)
                    bag.add(new Letter(ch));
                    
         shuffleBag(bag);
         
         iter = bag.iterator();
}
    
    private static void shuffleBag(List<Letter> a) {
          int n= a.toArray().length;
          
          Random random=new Random();
          random.nextInt();
          
          
          for (int i=0;i<n;i++){
              int change = i + random.nextInt(n-i);
              swap (a,i,change);
              }
    }

    private static void swap(List<Letter> a,int i,int change){
            Letter helper =a.get(i);
            a.set(i, a.get(change));
            
            a.set(change,helper);
    
    }
    
    public Letter getNext()
    {
        if(!iter.hasNext())
            return null;
        
        return(iter.next());
    }
    
    public boolean hasNext()
	{
		return(iter.hasNext());
	}
    
    }
    

