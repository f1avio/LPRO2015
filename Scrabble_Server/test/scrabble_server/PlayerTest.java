/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

import junit.framework.TestCase;

/**
 * @author Flávio Dias
 */
public class PlayerTest extends TestCase {
    
    /**
     * Test of getUsername method, of class Player.
     */
    public void testGetUsername() {
        System.out.println("getUsername");
        Player instance = new Player("Freddy", 49);
        String expResult = "Freddy";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getID method, of class Player.
     */
    public void testGetID() {
        System.out.println("getID");
        Player instance = new Player("Jason", 11);
        int expResult = 11;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPoints method, of class Player.
     */
    public void testGetPoints() {
        System.out.println("getPoints");
        Player instance = new Player("Generic Horror Movie Character", 2016);
        int expResult = 0;
        int result = instance.getPoints();
        assertEquals(expResult, result);
    }
    
}
