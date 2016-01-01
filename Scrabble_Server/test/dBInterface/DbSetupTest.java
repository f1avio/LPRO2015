/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBInterface;

import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author Flávio Dias <ee11160@fe.up.pt>
 */
public class DbSetupTest extends TestCase {
    
    /**
     *Constructor of the unit test of the class DbSetup.
     * @param testName
     */
    public DbSetupTest(String testName) {
        super(testName);
    }
    /**
     * Sets up the class with the parameters read from the file config.
     * @throws Exception Throws any kind of exception.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DbSetup instance = new DbSetup();
        instance.setDB();
    }
    
    /**
     * Test of getParameters method, of class DbSetup.
     */
    public void testGetDB() {
        System.out.println("getDB");
        DbSetup instance = new DbSetup();
        String expResult = "[1513, jdbc:postgresql://vdbm.fe.up.pt/lpro1513, lpro1513, C4bhX7aai, null]";
        String result = Arrays.toString(instance.getDB());
        assertEquals(expResult, result);
    }
    
}
