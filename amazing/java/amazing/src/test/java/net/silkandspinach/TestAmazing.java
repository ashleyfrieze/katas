package net.silkandspinach;

// Copyright 2003, William C. Wake. All rights reserved.

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class TestAmazing {
	// test data for a small maze
	private static final String fourByFiveWithRandom100Seed = 
            "Amazing - Copyright by Creative Computing, Morristown, NJ\n" +
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
                "Amazing - Copyright by Creative Computing, Morristown, NJ\n" +
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
}
