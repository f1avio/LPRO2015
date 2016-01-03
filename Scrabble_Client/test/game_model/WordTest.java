/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_model;

import game_model.Word.Direction;
import java.util.ArrayList;
import java.util.List;
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
public class WordTest {
    
    Word instance = null;
    Letter o;
    Letter l;
    Letter a;
    Word aux;

    
    public WordTest() {
        
        this.a = new Letter('a');
        this.l = new Letter('l');
        this.o = new Letter('o');
        instance = new Word();
        this.aux = new Word();
        
        aux.addLetter(o, 7, 7);
        aux.addLetter(l, 8, 7);
        aux.addLetter(a, 9, 7);
        
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
     * Test of toString method, of class Word.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "[O(7,7)(1) L(8,7)(1) A(9,7)(1) ]";
        String result = aux.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of addLetter method, of class Word.
     */
    @Test
    public void testAddLetter() {
        System.out.println("addLetter");
        int x = 5;
        int y = 5;
        String expResult = "[O(5,5)(1) ]";
        instance.addLetter(o, x, y);
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getWord method, of class Word.
     */
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        List<BoardLetters> expResult = new ArrayList<>();
        expResult.add(new BoardLetters(o, 7, 7));
        expResult.add(new BoardLetters(l, 8, 7));
        expResult.add(new BoardLetters(a, 9, 7));
        List<BoardLetters> result = aux.getWord();
        assertEquals(expResult.toString(), result.toString()); //It can't compare array lists.
    }

    /**
     * Test of getMinX method, of class Word.
     */
    @Test
    public void testGetMinX() {
        System.out.println("getMinX");
        int expResult = 7;
        int result = aux.getMinX();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxX method, of class Word.
     */
    @Test
    public void testGetMaxX() {
        System.out.println("getMaxX");
        int expResult = 9;
        int result = aux.getMaxX();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinY method, of class Word.
     */
    @Test
    public void testGetMinY() {
        System.out.println("getMinY");
        int expResult = 7;
        int result = aux.getMinY();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxY method, of class Word.
     */
    @Test
    public void testGetMaxY() {
        System.out.println("getMaxY");
        int expResult = 7;
        int result = aux.getMaxY();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLetterFrom method, of class Word.
     */
    @Test
    public void testGetLetterFrom() {
        System.out.println("getLetterFrom");
        int x = 8;
        int y = 7;
        BoardLetters expResult = new BoardLetters(l, 8, 7);
        BoardLetters result = aux.getLetterFrom(x, y);
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of getDirection method, of class Word.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        Word.Direction expResult = Direction.HORIZONTAL;
        Word.Direction result = aux.getDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValidity method, of class Word.
     */
    @Test
    public void testGetValidity() {
        System.out.println("getValidity");
        aux.setValidity(Word.WordValidity.VALID);
        Word.WordValidity expResult = Word.WordValidity.VALID;
        Word.WordValidity result = aux.getValidity();
        assertEquals(expResult, result);
    }

    /**
     * Test of setValidity method, of class Word.
     */
    @Test
    public void testSetValidity() {
        System.out.println("setValidity");
        Word.WordValidity validity = Word.WordValidity.INV_SYNTAX;
        aux.setValidity(validity);
        Word.WordValidity expResult = Word.WordValidity.INV_SYNTAX;
        Word.WordValidity result = aux.getValidity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStart method, of class Word.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        int expResult = 4;
        aux.setStartOnBoard(4);
        int result = aux.getStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnd method, of class Word.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        int expResult = 9;
        instance.setEndOnBoard(9);
        int result = instance.getEnd();
        assertEquals(expResult, result);
    }
    
}
