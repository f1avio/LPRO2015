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
public class BagTest {
    
    Bag instance = null;
    
    /**
     * Initiates a ne 'Bag' object.
     */
    public BagTest() {
        instance = new Bag();
    }
    
    /**
     * Test of getNext method, of class Bag.
     */
    @Test
    public void testGetNext() {
        System.out.println("getNext");
        Letter expResult = null;
        Letter result = instance.getNext();
        assertNotEquals(expResult, result);
        //The tile is randomized. There's no way to know what will come from there.
    }

    /**
     * Test of hasNext method, of class Bag.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        boolean expResult = true;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
    }
    
     /**
     * Second Test of hasNext method, of class Bag.
     */
    @Test
    public void testHasNext2() {
        System.out.println("hasNext");

        for(int i = 0; i < 96; i++) //Should be +2 if we count the empty spaces.
            instance.getNext();
            
        boolean expResult = false;
        boolean result = instance.hasNext();
        assertNotEquals(expResult, result);
    }
    
}
