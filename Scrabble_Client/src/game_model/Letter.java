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
    * Array of all letters
    */
    public static final Character[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final int[] letterValues = { 1,  3,  3,  2,  1,  4,  2,  4,  1,  8,  5,  1,  3,  1,  1,  3,  10, 1,  1,  1,  1,  4,  4,  8,  4, 10};
    private static final int[] amountInPool  = {9,  2,  2,  4,  12, 2,  3,  2,  9,  1,  1,  4,  2,  6,  8,  2,  1,  6,  4,  6,  4,  2,  2,  1,  2,  1};

    /**
    * 
    * @param letter
    */
    public Letter(Character letter)
    {
        Char = new Character(Character.toUpperCase(letter));
    }
    
    public String toString()
    {
        return Char.toString();
    }
    
    /**
    * @param letter Letter to check
    * @return amount of points
    */
    public static int GetLetterValue(Character letter)
    {
        return letter = Character.toUpperCase(letter);
    }
    
    public Character getChar()
    {
        return Char;
    }
}
