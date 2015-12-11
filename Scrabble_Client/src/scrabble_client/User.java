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
    
    
    /**
     * @param name The username to be stored
     */
    public User(String name)
    {
        this.name= name;
        score=0;
    }
    
    /**
     * Stores the username of the newly logged in user
     * Constructor which adds initial letters
     * @param name The username to be stored
     * @param firstLetters First letters of the player
     */
    public User(String name, ArrayList<Letter> firstLetters)
    {
     this.name = name;
     letters.addAll(firstLetters);
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
    
    
    /**
	 * Adds points to player's score
	 * 
	 * @param addS
	 * @return Amount of points after adding
	 */
    public int addScore(int addS){
        score+=addS;
        return score;
    }
    
    /**
	 * Gets current player's score
	 * @return Current amount of points
	 */
	public int getScore() {
		return score;
	}
        
        /**
	 * Gets all player's letters (available in his rack)
	 * @return List of player's letters
	 */
	public List<Letter> getAllLetters() {
		return letters;
	}
        
        /**
	 * Adds a letter to player's rack
	 * 
	 * @param l Letter to add
	 */
	public void addLetter(Letter l) {
		letters.add(l);
	}

	/**
	 * Removes a letter from player's rack
	 * 
	 * @param l Letter to remove
	 */
	public void useLetter(Letter l) {
	}
}
