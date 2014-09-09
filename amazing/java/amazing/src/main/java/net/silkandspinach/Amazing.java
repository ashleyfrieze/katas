package net.silkandspinach;

/**
 * + The original program is by Jack Hauber, and the source is
 * "Basic Computer Games." Used with permission of David Ahl;
 * see www.SwapMeetDave.com.
 * + This exercise was inspired by Alan Hensel's use of Amazing
 * as a refactoring challenge.
 * + This transliteration to Java was created by Bill Wake, William.Wake@acm.org
 */
import java.util.Random;

public class Amazing {

	
    private static Random random = new Random(0);
    
    
    public static String buildMaze(Random randomGenerator, int horizontal, int vertical) {
    	random = randomGenerator;
    	return constructAndOutputMaze(horizontal, vertical);
    }
    
    public static void main(String[] args) {
    	System.out.println(constructAndOutputMaze(Integer.parseInt(args[0]),Integer.parseInt(args[1])));
    }

    private static int generateRandom(int max) {
        return (int) (max * random.nextFloat()) + 1;
    }
    
    // convert static random method into an object
    private static RandomGenerator createRandomGeneratorObject() {
    	return new RandomGenerator() {
			
			public int generateRandom(int max) {
				return Amazing.generateRandom(max);
			}
		};
    }

    private static String constructAndOutputMaze(int width, int height) {
    	int[][] maze = null;
    	int entrance = 0;
    	
        if (tooSmall(width, height)) {
        	width = 0;
        	height = 0;
        } else {
	        entrance = generateRandom(width);
	        maze = generateMaze(width, height, entrance);
        }

        return new MazeOutputter(maze, width, height, entrance).toString();

    }

	private static int[][] generateMaze(int width, int height, int entrance) {
		MazeGenerator generator = new MazeGenerator(width, height, entrance, createRandomGeneratorObject());
		generator.generate();
		return generator.getMazeRoute();
	}

	static int[][] constructBlankMaze1BasedArray(int width, int height) {
		int[][] array = new int[width + 1][height + 1];
        for (int i = 0; i <= width; i++) {
            array[i] = new int[height + 1];
        }
		return array;
	}

	private static boolean tooSmall(int h, int v) {
		return h <= 1 || v <= 1;
	}

}
