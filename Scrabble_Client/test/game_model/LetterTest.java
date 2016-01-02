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
 * @author Flávio Dias
 */
public class LetterTest {
    Letter instance = new Letter('a');
    
    /**
     * Test of toString method, of class Letter.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "A";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValueForLetter method, of class Letter.
     */
    @Test
    public void testGetValueForLetter_Character() {
        System.out.println("getValueForLetter");
        Character l = 'Z';
        int expResult = 10;
        int result = Letter.getValueForLetter(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValueForLetter method, of class Letter.
     */
    @Test
    public void testGetValueForLetter_0args() {
        System.out.println("getValueForLetter");
        int expResult = 1;
        int result = instance.getValueForLetter();
        assertEquals(expResult, result);

    }

    /**
     * Test of getAmountOfLettersInPool method, of class Letter.
     */
    @Test
    public void testGetAmountOfLettersInPool_Character() {
        System.out.println("getAmountOfLettersInPool");
        Character l = 'e';
        int expResult = 12;
        int result = Letter.getAmountOfLettersInPool(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAmountOfLettersInPool method, of class Letter.
     */
    @Test
    public void testGetAmountOfLettersInPool_0args() {
        System.out.println("getAmountOfLettersInPool");
        int expResult = 9;
        int result = instance.getAmountOfLettersInPool();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChar method, of class Letter.
     */
    @Test
    public void testGetChar() {
        System.out.println("getChar");
        Character expResult = 'A';
        Character result = instance.getChar();
        assertEquals(expResult, result);
    }
    
}
