/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Eduardo
 */
public class Word {
    
    public static enum Direction {UNKNOWN, HORIZONTAL, VERTICAL};

    /* 
    * VALID: Word is valid
    * INV_SYNTAX: Word is invalid 
    * INV_BOARD_ALREADY_OCCUPIED: Adding new letters to occupied cells
    * INV_HAS_GAPS: New word has gaps
    * INV_SOLITARE: 1st word not passing or not connected to current crossword
    * INV_NOT_A_WORD: not found in dictionary
    * INV_OTHER
    */
    public static enum WordValidity {VALID, INV_SYNTAX, INV_BOARD_OCCUPIED, INV_HAS_GAPS, INV_SOLITARE, INV_NOT_A_WORD, INV_OTHER }
   
    private List<BoardLetters> letters = new ArrayList<>();
    private Direction wordDirection;
   
    private int startOnBoard;  //Describes where the new word starts
    private int endOnBoard;    //Describes where the new word ends
    
   
    private WordValidity validity;
 
    /**
     * Appends the position and its value to a tile positioned on the board,
     * and converts it to string.
     * @return A string with the letter plus important information. 
     */
    @Override
    public String toString()
    {
        StringBuilder sb= new StringBuilder();
       
        sb.append("[");
       
        letters.stream().forEach((BoardLetters bl) -> {
            sb.append(bl).append("(").append(bl.getX()).append(",").append(bl.getY()).append(")(").append(bl.getValueForLetter()).append(") ");
        });
       
        sb.append("]");
       
        return sb.toString();
    }
   
    /**
     * Adds a new letter/tile to the board.
     * @param l The letter/tile to be added.
     * @param x Its position on the X-axis.
     * @param y Its position on the Y-axis.
     */
    public void addLetter(Letter l, int x, int y)
    {
        BoardLetters bl = new BoardLetters(l, x, y);
       
        for(BoardLetters l_check : letters)
            if(l_check.getX() == x && l_check.getY() == y)
            {
                validity = WordValidity.INV_SYNTAX;
                return;
            }
           
        int width = 0;
        int height = 0;
       
        for (BoardLetters l_check : letters)
        {
            width = Math.max(width, Math.abs(l_check.getX()-x));
            height = Math.max(height,Math.abs(l_check.getY()-y));
        }
       
        if (width>0 && height > 0)
        {
            validity = WordValidity.INV_SYNTAX;
            return;
        }
       
        validity = WordValidity.VALID;
        letters.add(bl);
    }
    /**
     * @return The word that was formed. 
     */
    public List<BoardLetters> getWord()
    {
        return letters;
    }
    
   /**
   * @return The position where the word starts on the X-axis. 
   */
    public int getMinX() 
    {
        int min=15;
        for(BoardLetters bl : letters)
            min = Math.min(min, bl.getX());
        return min;
    }
   /**
    * @return The position where the word ends on the X-axis.
    */
    public int getMaxX()  
    {
        int max=0;
		
        for(BoardLetters bl : letters)
            max = Math.max(max, bl.getX());
		
        return max;
    }
    
  /**
   * @return The position where the word starts on the Y-axis. 
   */
    public int getMinY()  //Check where word begins
    {
        int min=15;
		
        for(BoardLetters bl : letters)
            min = Math.min(min, bl.getY());
		
        return min;
    }
    
    /**
    * @return The position where the word ends on the X-axis.
    */
    public int getMaxY()  //Check where word ends
    {
        int max=0;
		
        for(BoardLetters bl : letters)
            max = Math.max(max, bl.getY());
		
        return max;
    }
   /**
    * Retur the letter/tile from a specified position.
    * @param x The x coordinate.
    * @param y The y coordinate.
    * @return  The letter on the provided position.
    */
    public BoardLetters getLetterFrom(int x, int y)  
    {
        for(BoardLetters bl : letters)
            if(bl.getX() == x && bl.getY() == y)
                return bl;
		
        return null;
    }
   /** 
    * @return The direction of the word.
    */
    public Direction getDirection()
    {
        if(wordDirection != null)
            return wordDirection;
		
        if(validity != WordValidity.VALID)
	{
            wordDirection = Direction.UNKNOWN;
            return Direction.UNKNOWN;
        }

        int width  = getMaxX() - getMinX();
        int height = getMaxY() - getMinY();

	if(width > 0 && height == 0)
            wordDirection = Direction.HORIZONTAL;
        else if(width == 0 && height > 0)
            wordDirection = Direction.VERTICAL;
        else
            wordDirection = Direction.UNKNOWN;		
			
        return wordDirection;				
    }

    /**
    * @return the validity of a word.
    */
    public WordValidity getValidity() {
        return validity;
    }

    /**
    * @param validity Sets the validity of a word.
    */
    public void setValidity(WordValidity validity) {
        this.validity = validity;
    }

    /**
    * @return the position where the word starts.
    */
    public int getStart() {
        return startOnBoard;
    }

    /**
    * @param startOnBoard sets the position of where the word starts.
    */
    public void setStartOnBoard(int startOnBoard) {
        this.startOnBoard = startOnBoard;
    }

    /**
    * @return The position where the word ends.
    */
    public int getEnd() {
        return endOnBoard;
    }

    /**
    * @param endOnBoard sets the position of where the word ends.
    */
    public void setEndOnBoard(int endOnBoard) {
        this.endOnBoard = endOnBoard;
    }
}
