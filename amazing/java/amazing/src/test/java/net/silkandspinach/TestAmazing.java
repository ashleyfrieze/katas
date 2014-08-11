package net.silkandspinach;

// Copyright 2003, William C. Wake. All rights reserved.

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Random;

import org.junit.Test;

public class TestAmazing {
	private static final String copyRightMessage = "Amazing - Copyright by Creative Computing, Morristown, NJ\n";
	// test data for a small maze
	private static final String fourByFiveWithRandom100Seed = 
			copyRightMessage +
            ":--:--:  :--:\n" +
            "I     I     I\n" +
            ":  :--:  :  :\n" +
            "I  I     I  I\n" +
            ":  :  :--:  :\n" +
            "I  I  I     I\n" +
            ":  :  :  :  :\n" +
            "I  I  I  I  I\n" +
            ":  :--:  :  :\n" +
            "I  I  I  I  I\n" +
            ":--:--:  :--:\n";
	
    @Test
	public void testSeed0size15x20() {
        String expected =
        		copyRightMessage +
                ":--:--:--:--:--:--:--:--:--:--:  :--:--:--:--:\n" +
                "I  I           I        I     I     I        I\n" +
                ":  :  :  :  :  :  :--:  :  :  :  :--:  :--:  :\n" +
                "I     I  I  I  I     I     I     I     I  I  I\n" +
                ":--:--:  :  :--:  :  :--:--:--:--:  :  :  :  :\n" +
                "I     I  I        I  I              I  I     I\n" +
                ":  :  :  :--:--:--:  :  :  :--:--:--:--:--:--:\n" +
                "I  I     I        I     I  I     I        I  I\n" +
                ":--:--:  :--:  :--:  :--:  :  :  :  :--:  :  :\n" +
                "I     I        I        I  I  I     I     I  I\n" +
                ":  :  :--:--:--:  :--:  :  :  :--:--:  :--:  :\n" +
                "I  I              I     I  I  I     I  I     I\n" +
                ":  :--:--:--:--:--:--:  :  :  :  :--:  :--:  :\n" +
                "I        I           I  I  I  I     I  I     I\n" +
                ":  :--:  :--:  :  :  :  :  :  :--:  :  :  :--:\n" +
                "I  I     I     I  I  I  I  I     I  I  I  I  I\n" +
                ":  :  :--:  :--:  :  :  :  :--:  :  :  :  :  :\n" +
                "I  I        I     I  I  I        I  I  I  I  I\n" +
                ":  :--:  :--:  :--:  :  :--:--:--:  :  :  :  :\n" +
                "I     I     I  I  I  I  I     I        I     I\n" +
                ":--:  :--:  :  :  :  :--:  :  :  :--:--:  :--:\n" +
                "I  I     I  I     I     I  I  I     I  I     I\n" +
                ":  :--:  :--:--:--:  :  :  :  :--:  :  :  :  :\n" +
                "I     I     I     I  I  I  I     I  I  I  I  I\n" +
                ":  :  :--:  :  :  :--:  :  :--:  :  :  :  :  :\n" +
                "I  I        I  I     I  I     I     I  I  I  I\n" +
                ":  :--:--:  :--:  :  :  :--:  :--:--:  :  :--:\n" +
                "I  I     I        I  I     I     I     I     I\n" +
                ":  :  :--:--:--:--:  :  :--:  :  :  :--:--:  :\n" +
                "I     I           I  I     I  I  I  I     I  I\n" +
                ":  :--:  :--:--:--:  :--:  :  :  :  :  :  :  :\n" +
                "I  I                 I     I  I  I     I  I  I\n" +
                ":  :  :--:--:--:--:--:  :--:  :--:  :--:  :  :\n" +
                "I  I  I  I           I     I     I  I     I  I\n" +
                ":  :  :  :  :--:  :  :--:  :--:  :  :  :--:  :\n" +
                "I  I  I     I     I  I  I     I     I  I     I\n" +
                ":--:  :--:--:  :  :  :  :  :  :--:--:  :--:  :\n" +
                "I     I        I  I  I  I  I  I     I        I\n" +
                ":  :--:  :--:--:  :  :  :  :  :--:  :  :--:--:\n" +
                "I        I        I     I  I        I        I\n" +
                ":--:--:--:--:--:--:--:--:  :--:--:--:--:--:--:\n";

        String result = Amazing.buildMaze(new Random(0), 15, 20);

        assertEquals("Should have the maze that was expected", expected, result.toString());
    }

    @Test
    public void testSeed100size4x5() {
        String result = Amazing.buildMaze(new Random(100), 4, 5);
        assertEquals("Should have the maze that was expected", fourByFiveWithRandom100Seed, result.toString());
    }
    
    @Test
    public void testGenerateTwoConsecutiveMazes() {
        String result = Amazing.buildMaze(new Random(100), 4, 5);
        assertEquals("Should have the maze that was expected", fourByFiveWithRandom100Seed, result.toString());
        
        result = Amazing.buildMaze(new Random(100), 4, 5);
        assertEquals("Should have the maze that was expected", fourByFiveWithRandom100Seed, result.toString());
    }
    
    @Test
    public void whenSizeIsTooSmall() {
    	assertNotBuilt(1, 1);
    	assertNotBuilt(1, 0);
    	assertNotBuilt(0, 1);
    	assertNotBuilt(1, 2);
    	assertNotBuilt(2, 1);
    	assertNotBuilt(-1, -1);
    }

	private void assertNotBuilt(int h, int v) {
		assertThat(Amazing.buildMaze(new Random(), h,  v).toString(), is(copyRightMessage));
	}
}
