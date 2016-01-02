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
public class ClientThreadTest extends TestCase {
    
    ClientThread instance = null;
    
    public ClientThreadTest(String testName) {
        super(testName);
        
        //instance = new ClientThread();
      
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
        instance.send(msg);
        /*What I might need to do:
            1. Create a new client object
            2. Send this message
            3.Receive it on the client's end.
            4.Compare both of them.
        */
    }

    /**
     * Test of getID method, of class ClientThread.
     */
    public void testGetID() {
        System.out.println("getID");
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of open method, of class ClientThread.
     * @throws java.lang.Exception
     */
    public void testOpen() throws Exception {
        System.out.println("open");
        instance.open();
        /*What I might need to do:
            1. Open the datastream
            2. Compare it with null;
            3.Repeat it for the other stream
        */
    }

    /**
     * Test of close method, of class ClientThread.
     * @throws java.lang.Exception
     */
    public void testClose() throws Exception {
        System.out.println("close");
        instance.close();
        /*What I need to do:
            1. Open the socket
            2. Close the socket.
            3.Check if it is null.
         */
    }
    
}
