/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_server;

import java.net.Socket;
import junit.framework.TestCase;
import static org.junit.Assert.assertNotEquals;

/**@author Adam Kopnicky 
 * @author Ewa Godlewska 
 * @author Flavio Dias 
 * @author Hugo Pereira
 * @author Jose Carvalho
 */
public class ScrabbleServerTest extends TestCase {
    
    public ScrabbleServerTest(String testName) {
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
     * Test of getInstance method, of class ScrabbleServer.
     */
    public void testGetInstance() {
        System.out.println("getInstance");
        ScrabbleServer expResult = null;
        ScrabbleServer result = ScrabbleServer.getInstance();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of findClient method, of class ScrabbleServer.
     */
    public void testFindClient() {
        System.out.println("findClient");
        int ID = 0;
        ScrabbleServer instance = null;
        int expResult = 0;
        int result = instance.findClient(ID);
        assertEquals(expResult, result);

    }

    /**
     * Test of handle method, of class ScrabbleServer.
     */
    public void testHandle() {
        System.out.println("handle");
        int ID = 0;
        String msg = "";
        ScrabbleServer instance = null;
        instance.handle(ID, msg);

    }

    /**
     * Test of remove method, of class ScrabbleServer.
     */
    public void testRemove() {
        System.out.println("remove");
        int ID = 0;
        ScrabbleServer instance = null;
        instance.remove(ID);

    }

    /**
     * Test of addThread method, of class ScrabbleServer.
     */
    public void testAddThread() throws Exception {
        System.out.println("addThread");
        Socket socket = null;
        ScrabbleServer instance = null;
        instance.addThread(socket);

    }

    /**
     * Test of findType method, of class ScrabbleServer.
     */
    public void testFindType() {
        System.out.println("findType");
        char[] Array = null;
        ScrabbleServer instance = null;
        String expResult = "";
        String result = instance.findType(Array);
        assertEquals(expResult, result);

    }

    /**
     * Test of findMessage method, of class ScrabbleServer.
     */
    public void testFindMessage() {
        System.out.println("findMessage");
        char[] Array = null;
        int i = 0;
        int nrParam = 0;
        ScrabbleServer instance = null;
        String expResult = "";
        String result = instance.findMessage(Array, i, nrParam);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPort method, of class ScrabbleServer.
     */
    public void testGetPort() {
        System.out.println("getPort");
        ScrabbleServer.getPort();

    }

    /**
     * Test of main method, of class ScrabbleServer.
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ScrabbleServer.main(args);

    }
    
}
