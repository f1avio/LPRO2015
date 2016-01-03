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
 * @author Flávio Dias.
 */
public class BoardLettersTest {
    
    BoardLetters instance = null;
    
    public BoardLettersTest() {
        Letter aux = new Letter('a');
        instance = new BoardLetters(aux, 7, 7);
    }
    
    /**
     * Test of getX method, of class BoardLetters.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        int expResult = 7;
        int result = instance.getX();
        assertEquals(expResult, result);
    }

    /**
     * Test of getY method, of class BoardLetters.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        int expResult = 7;
        int result = instance.getY();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCrossingWordStarts method, of class BoardLetters.
     */
    @Test
    public void testGetCrossingWordStarts() {
        System.out.println("getCrossingWordStarts");
        int expResult = 8;
        instance.setCrossingWordStarts(8);
        int result = instance.getCrossingWordStarts();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCrossingWordEnds method, of class BoardLetters.
     */
    @Test
    public void testGetCrossingWordEnds() {
        System.out.println("getCrossingWordEnds");
        int expResult = 10;
        instance.setCrossingWordEnds(10);
        int result = instance.getCrossingWordEnds();
        assertEquals(expResult, result);
    }
    
}
