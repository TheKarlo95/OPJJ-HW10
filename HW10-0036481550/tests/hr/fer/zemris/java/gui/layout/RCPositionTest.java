package hr.fer.zemris.java.gui.layout;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.gui.layouts.RCPosition;

@SuppressWarnings("javadoc")
public class RCPositionTest {
    
    // tests for: valueOf(int, int): RCPosition

    @Test
    public void testValueOfInt_ValidInput() {
        assertEquals(1, 1, RCPosition.valueOf(1, 1));
        assertEquals(1, 6, RCPosition.valueOf(1, 6));
        assertEquals(1, 7, RCPosition.valueOf(1, 7));
        
        for(int row = 2; row <= 5; row++) {
            for(int col = 1; col <= 7; col++) {
                assertEquals(row, col, RCPosition.valueOf(row, col));
            }
        }       
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInt_Invalid_0_0() {
        RCPosition.valueOf(0, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInt_Invalid_1_2() {
        RCPosition.valueOf(1, 2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInt_Invalid_1_3() {
        RCPosition.valueOf(1, 3);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInt_Invalid_1_5() {
        RCPosition.valueOf(1, 5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInt_Invalid_OverMax() {
        RCPosition.valueOf(RCPosition.MAX_ROWS + 1, RCPosition.MAX_COLS + 1);
    }
    
    // tests for: valueOf(String): RCPosition
    
    @Test
    public void testValueOfString_ValidInput() {
        assertEquals(1, 1, RCPosition.valueOf("1, 1"));
        assertEquals(1, 6, RCPosition.valueOf("1, 6"));
        assertEquals(1, 7, RCPosition.valueOf("1, 7"));
        
        for(int row = 2; row <= 5; row++) {
            for(int col = 1; col <= 7; col++) {
                assertEquals(row, col, RCPosition.valueOf(row + ", " + col));
            }
        }       
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfString_Invalid_0_0() {
        RCPosition.valueOf("0, 0");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfString_Invalid_1_2() {
        RCPosition.valueOf("1, 2");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfString_Invalid_1_3() {
        RCPosition.valueOf("1, 3");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfString_Invalid_1_5() {
        RCPosition.valueOf("1, 5");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValueOfString_Invalid_OverMax() {
        RCPosition.valueOf((RCPosition.MAX_ROWS + 1) + ", " + (RCPosition.MAX_COLS + 1));
    }
    
    // tests for: toString(): String
    
    @Test
    public void testToString() {
        Assert.assertEquals(1 + ", " + 1, RCPosition.valueOf("1, 1").toString());
        Assert.assertEquals(1 + ", " + 6, RCPosition.valueOf("1, 6").toString());
        Assert.assertEquals(1 + ", " + 7, RCPosition.valueOf("1, 7").toString());
        
        for(int row = 2; row <= 5; row++) {
            for(int col = 1; col <= 7; col++) {
                Assert.assertEquals(row + ", " + col, RCPosition.valueOf(row, col).toString());
            }
        }       
    }
    
    // tests for: clone(): RCPosition
    
    @Test
    public void testClone() {
        RCPosition position = RCPosition.valueOf(3, 4);
        RCPosition clone = (RCPosition) position.clone();
        
        Assert.assertEquals(position, clone);
        Assert.assertTrue(position != clone);       
    }
    
    // tests for: hashCode(): RCPosition
    
    @Test
    public void testHashcode() {
        List<RCPosition> positions = getAll();
        
        for(int i = 2; i <= 5; i++) {
            for(int j = 1; j <= 7; j++) {
                if(positions.get(i).equals(positions.get(j))) {
                    assertTrue(positions.get(i).hashCode() == positions.get(j).hashCode());
                } else {
                    assertFalse(positions.get(i).hashCode() == positions.get(j).hashCode());
                }
            }
        }
    }
    
    
    private List<RCPosition> getAll() {
        List<RCPosition> positions = new ArrayList<>();
        
        positions.add(RCPosition.valueOf("1, 1"));
        positions.add(RCPosition.valueOf("1, 6"));
        positions.add(RCPosition.valueOf("1, 7"));
        
        for(int row = 2; row <= 5; row++) {
            for(int col = 1; col <= 7; col++) {
                positions.add(RCPosition.valueOf(row, col));
            }
        }
        
        return positions;
    }
    
    
    private void assertEquals(int expectedRow, int expectedCol, RCPosition actual) {
        try {
            assertTrue(expectedRow == actual.getRow());
            assertTrue(expectedCol == actual.getCol());
        } catch (AssertionError e) {
            throw new AssertionError(String.format(
                    "Expected: %d, %d but was: %d, %d.",
                    expectedRow,
                    expectedCol,
                    actual.getRow(),
                    actual.getCol()));
        }
    }

}
