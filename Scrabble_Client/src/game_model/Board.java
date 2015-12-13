/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import game_model.Word.Direction;
import java.util.List;

/**
 *
 * @author HUGUETA
 */
public class Board {
    private static Dictionary dic = new Dictionary();
    
    /*
    *Premium colors to the game board
    *NO: no premium color   
    *DL: double letter        
    *TL: triple letter       
    *DW: double word       
    *TW: triple word
    */
    public enum Pcolor{ NO, DL, TL, DW, TW};
    
    /**
    * Layout of Premium Colors (Pcolor) on standard 15x15 Scrabble board.
    */
    private Pcolor[][] premiumColorsLayout = {
			{ Pcolor.TW, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.TW },
			{ Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO },
			{ Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO },
			{ Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.DL },
			{ Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO },
			{ Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO },
			{ Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO },
			{ Pcolor.TW, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.TW },
			{ Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO },
			{ Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO },
			{ Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.NO },
			{ Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.DL },
			{ Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO },
			{ Pcolor.NO, Pcolor.DW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DW, Pcolor.NO },
			{ Pcolor.TW, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.TW, Pcolor.NO, Pcolor.NO, Pcolor.NO, Pcolor.DL, Pcolor.NO, Pcolor.NO, Pcolor.TW }			
    };   
    
    /**
    * Position of letters on standard 15x15 Scrabble board.
    */
    private Letter[][] lettersOnBoard = new Letter[15][15];
    
    /**
    * Gets what letter is currently stored in this cell
    * @param x - X for cell coordinate, must be 0..14 where 0 is left, 14 - right side of the board
    * @param y - Y for cell coordinate, must be 0..14 where 0 is top, 14 - bottom of the board
    * @return A letter
    */
    public Letter getLetter(int x, int y)
    {
        return lettersOnBoard[x][y];
    }
    
    /**
    * Puts a letter to the board.
    * @param letter
    * @param x for cell coordinate, must be [0,14] where 0 is left, 14 - right side of the board
    * @param y for cell coordinate, must be [0,14] where 0 is top, 14 - bottom of the board
    */
    public void setLetter(Letter letter, int x, int y) {
        lettersOnBoard[x][y] = letter;
    }
    
    /*
    * Gets a Premium Color
    * @param x for cell coordinate, must be [0,14] where 0 is left, 14 - right side of the board
    * @param y for cell coordinate, must be [0,14] where 0 is top, 14 - bottom of the board
    * @return - enum Pcolor
    */
    public Pcolor getPremiumColor(int x, int y){
        return premiumColorsLayout[x][y];
    }
    
    public boolean CheckWordValidity(Word word){
        List<BoardLetters> letters = word.getWord();
        /*
        *
        */
        if(word.getValidity() != Word.WordValidity.VALID)
            return false;
        /*
        *
        */
        for(BoardLetters bl : letters)
            if(lettersOnBoard[bl.getX()][bl.getY()] != null)
            {
                word.setValidity(Word.WordValidity.INV_BOARD_OCCUPIED);
                return false;
            }    
        
        /*
        *
        */
        SurroundingLetters(word);
        
        /*
        *
        */
        if(!hasGaps(word))
        {
            word.setValidity(Word.WordValidity.INV_HAS_GAPS);
            return false;
        }
        
        /*
        *
        */
        if(!Solitare(word))
        {
            word.setValidity(Word.WordValidity.INV_SOLITARE);
            return false;
        }
        
        /*
        *
        */
        word.setValidity(Word.WordValidity.VALID);
        return true;
    }

    private boolean hasGaps(Word word)
    {
        int start = word.getStart();
        int end = word.getEnd();
        
        if(word.getDirection() == Word.Direction.HORIZONTAL)
        {
            int y = word.getMaxY();
            
            for(int x=start; x<(end+1); x++)
                if(getLetter(x,y)== null && (word.getLetterFrom(x,y)==null))
                    return false;
        }
        else if(word.getDirection()==Word.Direction.VERTICAL)
        {
            int x=word.getMaxX();
            
            for(int y=start; y<(end+1); y++)
                if(getLetter(x,y)==null && (word.getLetterFrom(x,y)==null))
                    return false;
        }
        return true;
    }
    
    private boolean Solitare(Word word)
    {
        int start = word.getStart();
        int end = word.getEnd();
        
        /*
        *
        */
        if(firstMove())
        {
            return (word.getLetterFrom(7,7) != null);
        }
        
        /*
        *
        */
        if(word.getDirection() == Word.Direction.HORIZONTAL)
        {
            int y=word.getMaxY();
            
            for(int x=start; x<(end+1); x++)
            {
                /*
                *
                */
                if(getLetter(x,y)!= null)
                    return true;
                /*
                *
                */
                if(y-1 >= 0)
                    if(getLetter(x,y-1) != null)
                        return true;
                if(y+1 <= 15)
                    if(getLetter(x,y-1) !=null)
                        return true;
            }
            
        }
        else if(word.getDirection() == Word.Direction.VERTICAL)
        {
            int x = word.getMaxX();
            
            for(int y=start; y<(end+1); y++)
            {
                if(getLetter(x, y) != null)
                    return true;
                if(x-1 >= 0)
                    if(getLetter(x-1, y) != null)
                        return true;	
				
                if(x+1 <=15)
                    if(getLetter(x+1, y) != null)
                        return true;
            }
        }
        return false;
    }
    
    private boolean firstMove()
    {
        for(Letter y[] : lettersOnBoard)
            for(Letter x : y)
                if(x != null)
                    return false;
		
        return true;
    }
    
    private int getHorizontalStart(Word word)
    {
        int startX = word.getMinX();
        int y      = word.getMinY();
		
        while(startX-- > 0)
        {
            if(getLetter(startX, y) == null)
            break;			
        }
		
        return startX+1;
    }
    
    private int getHorizontalStart(BoardLetters bl)
    {
        int startX = bl.getX();
        int y = bl.getY();
		
        while(startX-- > 0)
        {
            if(getLetter(startX, y) == null)
            break;			
        }
		
        return startX+1;
    }
 
    private int getHorizontalEnd(Word word)
    {
	int startX = word.getMaxX();
	int y      = word.getMinY();

	while(startX++ < 15)
	{
            if(getLetter(startX, y) == null)
            break;			
	}
		
        return startX-1;
    }	
	
    private int getHorizontalEnd(BoardLetters bl)
    {
        int startX = bl.getX();
        int y      = bl.getY();
		
        while(startX++ < 15)
        {
            if(getLetter(startX, y) == null)
            break;			
        }
		
        return startX-1;
    }
    
    private int getVerticalStart(Word word)
    {
        int x = word.getMinX();
        int startY = word.getMinY();
		
        while(startY-- > 0)
        {
            if(getLetter(x, startY) == null)
            break;			
        }
		
        return startY+1;
    }
	
    private int getVerticalStart(BoardLetters bl)
    {
        int x = bl.getX();
        int startY = bl.getY();
		
        while(startY-- > 0)
        {
            if(getLetter(x, startY) == null)
            break;			
        }
		
        return startY+1;
    }
	
    private int getVerticalEnd(Word word)
    {
        int x = word.getMinX();
        int startY = word.getMaxY();
		
        while(startY++ < 15)
        {
            if(getLetter(x, startY) == null)
            break;			
        }
		
        return startY-1;
    }		
	
    private int getVerticalEnd(BoardLetters bl)
    {
        int x = bl.getX();
        int startY = bl.getY();
		
        while(startY++ < 15)
        {
            if(getLetter(x, startY) == null)
            break;			
        }
		
        return startY-1;
    }    
    
    /*
    *
    */ 
    public void addWord(Word word)
    {
        List<BoardLetters> letters = word.getWord();
        if(word.getValidity() != Word.WordValidity.VALID)
            return;
        
        for(BoardLetters bl : letters)
            lettersOnBoard[bl.getX()][bl.getY()]= bl;
    }
    
    public void SurroundingLetters(Word word)
    {
        /**
        * 1. Get any letters before and after added word
        */
		
        if(word.getDirection() == Word.Direction.HORIZONTAL)
        {
            /**
            * Find beginning and end of the word (including letters already on the board)
            */
			
            word.setStartOnBoard(getHorizontalStart(word));
            word.setEndOnBoard(getHorizontalEnd(word));
        }
        else if(word.getDirection() == Word.Direction.VERTICAL)
        {
            /**
            * Find beginning and end of the word (including letters already on the board)
            */
			
            word.setStartOnBoard(getVerticalStart(word));
            word.setEndOnBoard(getVerticalEnd(word));
        }
		
            /**
            * 2. Get any _crossing_ words for each newly added letter
            */
		
        if(word.getDirection() == Word.Direction.HORIZONTAL)
	{
            for(BoardLetters bl : word.getWord())
                {
                    bl.setCrossingWordStarts(getVerticalStart(bl));
                    bl.setCrossingWordEnds(getVerticalEnd(bl));
                }
        }
        else if(word.getDirection() == Word.Direction.VERTICAL)
        {
            /**
            * Find beginning and end of the word (including letters already on the board)
            */
			
            for(BoardLetters bl : word.getWord())
            {
                bl.setCrossingWordStarts(getHorizontalStart(bl));
                bl.setCrossingWordEnds(getHorizontalEnd(bl));
            }		
        }	
		
    }
    
    public int CalculatePoints(Word word)
    {
        int Tpoints=0;
        
        /**
        * Check if word has been validated AND is valid
        */
        if(word.getValidity() != Word.WordValidity.VALID)
            return 0;
        
        if(word.getDirection() == Direction.HORIZONTAL)
        {
            /*
            *   1
            */
            {
                int points=0;
                int wordBonus=1;
                int start= word.getStart();
                int end= word.getEnd();
                int y= word.getMaxY();
            
                for(int x=start;x<(end+1);x++)
                {
                    BoardLetters bl = word.getLetterFrom(x,y);
                    if(getLetter(x,y)==null && (bl!=null))
                    {
                        wordBonus*= getWordBonus(bl);
                        points+=bl.getValueForLetter() * getLetterBonus(bl);
                    }
                    else
                    {
                        points+= getLetter(x,y).getValueForLetter();
                    }
                }
            
                points*=wordBonus;
            
                if(dic.checkWord(getHorizontalWord(word,start,end,y)))
                {
                    Tpoints+= points;
                }
                else
                {
                    word.setValidity(Word.WordValidity.INV_NOT_A_WORD);
                    return 0;
                }
            }
            
            /*
            * 2
            */
            {
                for(BoardLetters bl : word.getWord())
                {
                    int points=0;
                    int wordBonus=1;
                    int start=bl.getCrossingWordStarts();
                    int end=bl.getCrossingWordEnds();
                
                    if(start==end)
                        continue;
                
                    int x= bl.getX();
                
                    for(int y=start; y<(end+1); y++)
                    {
                        if(getLetter(x, y) == null)
                        {
                            wordBonus *= getWordBonus(bl);
                            points += bl.getValueForLetter() * getLetterBonus(bl);
                        }
                        else
                        {
                            points += getLetter(x, y).getValueForLetter(); 
                        }
                    }
                
                    points*= wordBonus;
                    
                    if(dic.checkWord(getVerticalWord(word, x, start, end)))
                    {
                        Tpoints+= points;
                    }
                    else
                    {
                        word.setValidity(Word.WordValidity.INV_NOT_A_WORD);
                        return 0;
                    }
                }
            }   
        }
        else if(word.getDirection()== Direction.VERTICAL)
        {
            /**
            * 1. Calculate points from top to bottom
            */
            {
                int points = 0;
                int wordBonus = 1;
                int start = word.getStart();
                int end = word.getEnd();			
                int x = word.getMaxX();

                for(int y=start; y<(end+1); y++)
                {
                    BoardLetters bl = word.getLetterFrom(x, y);
                    if(getLetter(x, y) == null && bl !=null )
                    {
                        // We have new letter over here
                        // Add bonuses
                        wordBonus *= getWordBonus(bl);
                        points += bl.getValueForLetter() * getLetterBonus(bl);
                    }
                    else
                    {
                        // Old letter over here
                        // Do not get bonuses, only letter value
                        points += getLetter(x, y).getValueForLetter(); 
                    }
                }

                points *= wordBonus;

                if(dic.checkWord(getVerticalWord(word, x, start, end)))
                {
                    Tpoints += points;
                }
                else
                {
                    word.setValidity(Word.WordValidity.INV_NOT_A_WORD);
                    return 0;
                }				
            }
			
            /** 
            * 2. Calculate points for all _crossing_ words
            * (combined from old letters from left to right)
            */		
            {
                for(BoardLetters bl : word.getWord())
                {
                    int points = 0;
                    int wordBonus = 1;
                    int start = bl.getCrossingWordStarts();
                    int end = bl.getCrossingWordEnds();
					
                    /**
                    * If there is no word, just single letter
                    * just added (like for P or R) - just skip it
                    */
                    if(start == end)
                        continue;
					
                    int y = bl.getY();
					
                    for(int x=start; x<(end+1); x++)
                    {
			if(getLetter(x, y) == null)
                        {
                            // We have new letter over here
                            // Add bonuses

                            wordBonus *= getWordBonus(bl);
                            points += bl.getValueForLetter() * getLetterBonus(bl);
                        }
                        else
                        {
                            // Old letter over here
                            // Do not get bonuses, only letter value
                            points += getLetter(x,y).getValueForLetter();
                        }
                    }
					
                    points *= wordBonus;

                    if(dic.checkWord(getHorizontalWord(word, start, end, y)))
                    {
			Tpoints += points;
                    }
                    else
                    {
			word.setValidity(Word.WordValidity.INV_NOT_A_WORD);
                        return 0;
                    }				
		}
            }
        }
        
        /*
	* If player uses all 7 letters - add extra bonus 50
	*/
	if(word.getWord().size() == 7)
            Tpoints += 50;
        return Tpoints;
    }
    
    private int getLetterBonus(BoardLetters bl)
    {
        switch(premiumColorsLayout[bl.getX()][bl.getY()])
        {
            case DL :
                    // Double letter - x2
                    return 2;
            case TL :
                    // Tripple letter - x3
                    return 3;		
            default:
                // No bonus - multplayer is equal 1
                return 1;
        }
    }
    
    private int getWordBonus(BoardLetters bl)
    {
        switch(premiumColorsLayout[bl.getX()][bl.getY()])
        {
            case DW :
                // Double word - x2
                return 2;
            case TW :
                // Tripple word - x3
                return 3;		
            default:
                // No bonus - multplayer is equal 1
                return 1;
        }
    }
    
    public String getHorizontalWord(Word word, int startX, int endX, int y)
    {
        StringBuilder wordOut = new StringBuilder();
		
        for(int x=startX; x<(endX+1); x++)
            if(getLetter(x, y) == null)
                wordOut.append(word.getLetterFrom(x, y));				
            else
                wordOut.append(getLetter(x, y));
		
        return wordOut.toString();
    }
    
    public String getVerticalWord(Word word, int x, int startY, int endY)
    {
        StringBuilder wordOut = new StringBuilder();
		
        for(int y=startY; y<(endY+1); y++)
            if(getLetter(x, y) == null)
                wordOut.append(word.getLetterFrom(x, y));				
            else
                wordOut.append(getLetter(x, y));
		
        return wordOut.toString();	
    }
    
    
}