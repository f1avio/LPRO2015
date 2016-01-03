/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Flávio
 */
public class BoardTest {
    
    Board instance = null;
    Letter letter = null;
    
    int x = 7;
    int y = 7;
    
    public BoardTest() {
        
       letter = new Letter('X');
       instance = new Board();
    }
    
    /**
     * Test of getLetter method, of class Board.
     */
    @Test
    public void testGetLetter() {
        System.out.println("getLetter");
        Letter expResult = new Letter('X');
        instance.setLetter(letter, x, y);
        Letter result = instance.getLetter(x, y);
        assertEquals(expResult.Char, result.Char);
    }

    /**
     * Test of getPremiumColor method, of class Board.
     */
    @Test
    public void testGetPremiumColor() {
        System.out.println("getPremiumColor");
        Board.Pcolor expResult = Board.Pcolor.DW;
        Board.Pcolor result = instance.getPremiumColor(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of CheckWordValidity method, of class Board.
     */
    @Test
    public void testCheckWordValidity() {
        System.out.println("CheckWordValidity");
        Word word = new Word();
        word.addLetter(letter, x, y);
        boolean expResult = true;
        boolean result = instance.CheckWordValidity(word);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of CalculatePoints method, of class Board.
     */
    @Test
    public void testCalculatePoints() {
        System.out.println("CalculatePoints");
        Word word = new Word();
        word.addLetter(new Letter('a'), x, y);
        word.addLetter(new Letter('c'), x+1, y);
        word.addLetter(new Letter('e'), x+2, y);
        word.addLetter(new Letter('d'), x+3, y);
        Word.WordValidity intResult=word.getValidity();
        if(intResult != Word.WordValidity.VALID)
            fail("The word is not even valid!");
        
        Word.Direction intResult2 = word.getDirection();
        if( intResult2 != Word.Direction.HORIZONTAL)
            fail("The direction is quite wrong!");
        
        word.setStartOnBoard(x);
        word.setEndOnBoard(x+3);
        int expResult = 14;
        int result = instance.CalculatePoints(word);
        assertEquals(expResult, result);
    }

    /**
     * Test of getHorizontalWord method, of class Board.
     */
    @Test
    public void testGetHorizontalWord() {
        System.out.println("getHorizontalWord");
        Word word = new Word();
        word.addLetter(letter, x, y);
        word.addLetter(letter, x+1, y);
        int startX = x;
        int endX = x+1;
        String expResult = "XX";
        String result = instance.getHorizontalWord(word, startX, endX, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVerticalWord method, of class Board.
     */
    @Test
    public void testGetVerticalWord() {
        System.out.println("getVerticalWord");
        Word word = new Word();
        word.addLetter(letter, x, y);
        word.addLetter(letter, x, y+1);
        word.addLetter(letter, x, y+2);
        int startY = y;
        int endY = y+2;
        String expResult = "XXX";
        String result = instance.getVerticalWord(word, x, startY, endY);
        assertEquals(expResult, result);
    }
    
}
