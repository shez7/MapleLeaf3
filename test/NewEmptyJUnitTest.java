

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.Computer;
import uno.Card;
import uno.Uno;
import uno.deal;

/**
 *
 * @author Shez
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
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
     * Test of hasDrawFour method, of class Uno.
     */
    @Test
    public void testHasDrawFourGood() {
        System.out.println("validateHasDrawFourGood");
        int value = 15; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasDrawFour(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    @Test
    public void testHasDrawFourBad() {
        System.out.println("validateHasDrawFourBad");
        int value = 13; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasDrawFour(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    @Test
    public void testHasDrawFourBoundary() {
        System.out.println("validateHasDrawFourBoundary");
        int value = 14; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasDrawFour(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    /**
     * Test of hasDrawTwo method, of class Uno.
     */
    @Test
    public void testHasDrawTwoGood() {
        System.out.println("validateHasDrawTwoGood");
        int value = 12; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasDrawTwo(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    @Test
    public void testHasDrawTwoBad() {
        System.out.println("validateHasDrawTwoBad");
        int value = 10; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasDrawTwo(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    @Test
    public void testHasDrawTwoBoundary() {
        System.out.println("validateHasDrawTwoBoundary");
        int value = 11; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasDrawTwo(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    /**
     * Test of hasReverse method, of class Uno.
     */
    @Test
    public void testHasReverseGood() {
        System.out.println("validateHasReverseGood");
        int value = 13; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasReverse(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    @Test
    public void testHasReverseBad() {
        System.out.println("validateHasReverseBad");
        int value = 11; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasReverse(card, dealx, value);
        assertEquals(expResult, result);
     }
    
    @Test
    public void testHasReverseBoundary() {
        System.out.println("validateHasReverseBoundary");
        int value = 12; 
        deal dealx = new deal();
        Card card = new Card(5, 'b');
        boolean expResult = false;
        boolean result = Uno.hasReverse(card, dealx, value);
        assertEquals(expResult, result);
     }
    
}


