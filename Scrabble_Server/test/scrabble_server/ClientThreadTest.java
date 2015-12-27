/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

import junit.framework.TestCase;

/**@author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */
public class ClientThreadTest extends TestCase {
    
    public ClientThreadTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of send method, of class ClientThread.
     */
    public void testSend() {
        System.out.println("send");
        String msg = "";
        ClientThread instance = null;
        instance.send(msg);
    }

    /**
     * Test of getID method, of class ClientThread.
     */
    public void testGetID() {
        System.out.println("getID");
        ClientThread instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of open method, of class ClientThread.
     */
    public void testOpen() throws Exception {
        System.out.println("open");
        ClientThread instance = null;
        instance.open();
    }

    /**
     * Test of close method, of class ClientThread.
     */
    public void testClose() throws Exception {
        System.out.println("close");
        ClientThread instance = null;
        instance.close();

    }
    
}
