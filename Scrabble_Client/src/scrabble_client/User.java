package scrabble_client;

import game_model.Letter;
import java.util.ArrayList;
import java.util.List;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
public class User {  /*It will be used later on to more complicated things*/
    private String name =null;
    private int score;
    private ArrayList<Letter> letters = new ArrayList<Letter>();
    
    
    public User(){
        
    }
    
    /**
     * @param name The username to be stored
     */
    public User(String name)
    {
        this.name= name;
        score=0;
    }
    
    /**
     * Set the name to be used
     * @param name
     */
    public void setName(String name){
        this.name= name;
    }
    /**
     * Retrieves the stored name
     * @return the stored name
     */
    public String getName ()
    {
        return name;
    }
}
