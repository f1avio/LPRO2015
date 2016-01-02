/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Flávio Dias
 */
public class DictionaryTest {
    
    /**
     * Test of checkWord method, of class Dictionary.
     */
    @Test
    public void testCheckWord() {
        System.out.println("checkWord");
        String w = "HOME";
        Dictionary instance = new Dictionary();
        boolean expResult = true;
        boolean result = instance.checkWord(w);
        assertEquals(expResult, result);
    }
    
    /**
     * Second test of checkWord method, of class Dictionary.
     */
    @Test
    public void testCheckWord2() {
        System.out.println("checkWord");
        String w = "home";
        Dictionary instance = new Dictionary();
        boolean expResult = false;
        boolean result = instance.checkWord(w);
        assertEquals(expResult, result);
    }
    
}
