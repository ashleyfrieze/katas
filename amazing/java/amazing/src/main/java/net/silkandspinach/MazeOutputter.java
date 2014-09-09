package net.silkandspinach;

public class MazeOutputter {
	private static final String GAP_THEN_BORDER = "  I";
	private static final String GAP = "   ";
	private static final String LEFT_BORDER = "I";
	private static final String CLOSED_WALL = ":--";
	private static final String OPENING = ":  ";
	private static final String RIGHT_WALL = ":";
	
    private StringBuffer result = new StringBuffer();
	
	public MazeOutputter(int[][] maze, int width, int height, int entrance) {
        print("Amazing - Copyright by Creative Computing, Morristown, NJ");
        printNewline();
        if (width>0 && height>0) {
	        printTopLine(width, entrance);
	        printMazeInnards(width, height, maze);
        }
	}
	

	private void printMazeInnards(int height, int width, int[][] maze) {
		for (int j = 1; j <= width; j++) {
            print(LEFT_BORDER);

            for (int i = 1; i <= height; i++) {
                if (maze[i][j] == Amazing.PASSAGE || maze[i][j] == Amazing.CORNER) {
                    print(GAP);
                } else {
                    print(GAP_THEN_BORDER);
                }
            }
            printNewline();

            printSeparatorLine(height, maze, j);
        }
	}

	private void printSeparatorLine(int width, int[][] maze, int row) {
		for (int col = 1; col <= width; col++) {
		    if (maze[col][row] == Amazing.DEAD_END || maze[col][row] == Amazing.PASSAGE) {
		        print(CLOSED_WALL);
		    } else {
		        print(OPENING);
		    }
		}

		print(RIGHT_WALL);    // 1360
		printNewline();
	}

	private void printTopLine(int width, int entrance) {
        for (int i = 1; i <= width; i++) {
            if (i == entrance)
                print(OPENING);
            else
                print(CLOSED_WALL);
        }
        
        print(RIGHT_WALL);
        printNewline();
	}
	

    private void printNewline() {
        result.append("\n");
    }

    private void print(String text) {
        result.append(text);
    }
    
    @Override
    public String toString() {
    	return result.toString();
    }

}
