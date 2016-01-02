/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

/**@author  Adam Kopnicky
 *          Ewa Godlewska
 *          Flavio Dias
 *          Hugo Pereira
 *          Jose Carvalho
 */
public class Letter {
    protected Character Char;
    
    /**
    * Array of all letters, as well as their respective values.
    */
    public static final Character[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final int[] letterValues = { 1,  3,  3,  2,  1,  4,  2,  4,  1,  8,  5,  1,  3,  1,  1,  3,  10, 1,  1,  1,  1,  4,  4,  8,  4, 10};
    private static final int[] amountInPool  = {9,  2,  2,  4,  12, 2,  3,  2,  9,  1,  1,  4,  2,  6,  8,  2,  1,  6,  4,  6,  4,  2,  2,  1,  2,  1};

    /**
    * Uppercases the passed parameter.
    * @param letter The new character to be converted. 
    */
    public Letter(Character letter)
    {
        Char = Character.toUpperCase(letter);
    }
    /**
     * Converts the character to a string.
     * @return The Character converted to a string.
     */
    @Override
    public String toString()
    {
        return Char.toString();
    }
    
    /**
    * Returns amount of points represented by a letter
    * @param l Letter to check
    * @return amount of points
    */
    public static int getValueForLetter(Character l)
    {
        l = Character.toUpperCase(l);
	return letterValues[ getIndexOfLetter(l) ];
    }
    
    /** 
    * Returns amount of points represented by this letter.
    * @return Amount of points.
    */
    public int getValueForLetter()
    {
        return letterValues[ getIndexOfLetter(Char) ];
    }
    
    /**
    * Returns number of letters of given kind in the pool when game begins
    * @param l - Letter
    * @return - total number of letters
    */
    public static int getAmountOfLettersInPool(Character l)
    {
        l = Character.toUpperCase(l);
        return amountInPool[ getIndexOfLetter(l) ];
    }

    /**
    * Returns number of letters of given kind in the pool when game begins
    * @return - total number of letters
    */
    public int getAmountOfLettersInPool()
    {
        return amountInPool[ getIndexOfLetter(Char) ];
    }	
    /**
     * Returns the position of a letter on a array.
     * @param l The letter that will be verified.
     * @return The position of the letter.
     */	
    private static int getIndexOfLetter(Character l)
    {
        l = Character.toUpperCase(l);
        for(int i=0; i<letters.length; i++)
        {
            if(letters[i].equals(l))
            return i;
        }
        return -1;
    }
    /**
     * Returns the Character.
     * @return A Character.
     */
    public Character getChar()
    {
        return Char;
    }
}
