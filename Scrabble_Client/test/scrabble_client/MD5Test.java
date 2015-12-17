/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabble_client;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Flávio
 */
public class MD5Test {
    
    public MD5Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of convert method, of class MD5.
     */
    @Test
    public void testConvert() {
        System.out.println("convert");
        String password = "dictionary";
        MD5 instance = new MD5();
        String expResult = "abc20d7bde1df257f890e152af2e3470";
        String result = instance.convert(password);
        assertEquals(expResult, result);
    }
    
}
