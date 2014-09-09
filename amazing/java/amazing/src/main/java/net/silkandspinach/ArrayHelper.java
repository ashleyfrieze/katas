package net.silkandspinach;

public class ArrayHelper {
	public static int[][] constructBlankMaze1BasedArray(int width, int height) {
		int[][] array = new int[width + 1][height + 1];
        for (int i = 0; i <= width; i++) {
            array[i] = new int[height + 1];
        }
		return array;
	}
}
