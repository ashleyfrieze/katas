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

	private static final String GAP_THEN_BORDER = "  I";
	private static final String GAP = "   ";
	private static final String LEFT_BORDER = "I";
	private static final String CLOSED_WALL = ":--";
	private static final String OPENING = ":  ";
	private static final String RIGHT_WALL = ":";
	
	private static final int DEAD_END = 0;
	private static final int UP_OR_DOWN = 1;
	private static final int PASSAGE = 2;
	private static final int CORNER = 3;
	
    private static Random random = new Random(0);
    private static StringBuffer result = new StringBuffer();
    
    static class MazeGenerator {
    	private static int nextState = 0;      // where GOTO goes
    	
    	private int[][] wArray;
    	private int[][] vArray;
    	final int mazeSize;
    	
        boolean routeWantsToGoUp;
        boolean routeWantsToGoDown;
        
        int cellBeingFilled;

        int currentColumn;
        int currentRow;
        
        int width;
        int height;
    	
    	MazeGenerator(int width, int height, int entrance) {
    		this.width = width;
    		this.height = height;
    		
	        wArray = constructBlankMaze1BasedArray(width, height);
	        vArray = constructBlankMaze1BasedArray(width, height);
	        
	        mazeSize = width * height;
	        
	        routeWantsToGoUp = false;
	        routeWantsToGoDown = false;
	        
	        cellBeingFilled = 1;
	        wArray[entrance][1] = cellBeingFilled;
	        cellBeingFilled++;
	
	        currentColumn = entrance;
	        currentRow = 1;

    	}

        
        int[][] generate() {
	        startLooping();
	        while (!hasFilledAllCells()) {
	
				switch (nextState) {
	                case 330: {
	                    int x = randomChoiceOfFour();
	
	                    if (x == 1)
	                        routeGoesLeft();
	                    else if (x == 2)
	                    	routeGoesUp();
	                    else if (x == 3)
	                    	routeGoesRight();
	                    else
	                        nextState(350);
	                    break;
	                }
	                
	            	
	                case 390: {
	                    int x = randomChoiceOfFour();
	                    if (x == 1)
	                        routeGoesLeft();
	                    else if (x == 2)
	                    	routeGoesUp();
	                    else if (x == 3)
	                        goDownAndForceDownwardRoute();
	                    else
	                    	selectLeftUpOrDown();
	                    break;
	                }
	                

	                case 680: {
	                    int x = randomChoiceOfFour();
	                    if (x == 1)
	                    	routeGoesUp();
	                    else if (x == 2)
	                    	routeGoesRight();
	                    else if (x == 3)
	                    	goDownAndForceDownwardRoute();
	                    else
	                    	decisionPoint700();
	                    break;
	                }
	                
	                
	                case 350:
	                	if ((!onLastRow() && isWarrayCellBelowOccupied())||onLastRow() && routeWantsToGoDown) {
	                		selectLeftUpOrDown();
	                	} else {
	                		if (onLastRow()) {
	                            routeWantsToGoUp = true;
	                		}
	                		nextState(390);
	                	}
	                    break;


	                case 720:
	                    if (!onLastRow()) {
	                        if (isWarrayCellBelowOccupied()) {
	                        	routeGoesUp();
	                        } else {
	                        	do1090orWriteCintoWarrayAndProceed();
	                        }
	                    } else {
	                        if (routeWantsToGoDown) {
	                        	routeGoesUp();
	                        } else {
	                            routeWantsToGoUp = true;
	                            do1090orWriteCintoWarrayAndProceed();
	                        }
	                    }
	                    break;
	                case 790:
	                    if (onLastColumn() || isWarrayCellToRightOccupied()) {
	                    	if ((!onLastRow() && isWarrayCellBelowOccupied()) || (onLastRow() && routeWantsToGoDown)) {
								goToNextCellLocationWithWrapping();
								findPointOnRouteThenStartLooping();
							} else {
							    goDownAndForceDownwardRoute();
							} 
	                    } else {
                            if (!onLastRow()) {
                                if (isWarrayCellBelowOccupied()) {
                                	routeGoesRight();
                                } else {
            	                    selectDownOrRight();
                                }
                            } else {
                                if (routeWantsToGoDown) {
                                	routeGoesRight();
                                } else {
                                    routeWantsToGoUp = true;
                        			cellBeingFilled++;
                                    addRouteToMazeAboveCursorAndDecideWhetherToStop();
                                }
                            }
	                        
	                    }
	                    break;


	                default:
	                	throw new RuntimeException("Unknown next state " + nextState);
	
	            }
	
	        }
	        
	        return vArray;
	    }



		private void selectDownOrRight() {
			if (randomChoiceOfThree() == 2) {
				goDownAndForceDownwardRoute();
			} else {
				routeGoesRight();
			}
		}

		private void selectLeftRightOrDownOrFaceDown() {
			int x = randomChoiceOfFour();
			if (x == 1)
			    routeGoesLeft();
			else if (x == 2)
				routeGoesRight();
			else if (x == 3)
				goDownAndForceDownwardRoute();
			else
				selectLeftRightOrDown();
		}


		private void selectLeftRightOrDown() {
			int x = randomChoiceOfThree();
			if (x == 1)
			    routeGoesLeft();
			else if (x == 2)
				routeGoesRight();
			else
				goDownOrLeft();
		}

		private void selectLeftUpOrDown() {
			int x = randomChoiceOfThree();
			if (x == 1)
			    routeGoesLeft();
			else if (x == 2)
				routeGoesUp();
			else
				workOutNextStepsWhileFacingDownwards();
		}
		
		private void goDownOrLeft() {
			if (!onLastRow()) {
			    if (isWarrayCellBelowOccupied())
			        routeGoesLeft();
			    else
			    	eitherMakeCurrentPositionIntoRouteOrLoopSomeMore();
			} else {
			    if (routeWantsToGoDown) {
			        routeGoesLeft();
			    } else {
			        routeWantsToGoUp = true;
			        eitherMakeCurrentPositionIntoRouteOrLoopSomeMore();
			    }
			}
		}


		private void routeGoesRight() {
			trackInWarrayRightOfCurrent();
			
			if (vArray[currentColumn][currentRow] == DEAD_END) {
			    vArray[currentColumn][currentRow] = PASSAGE;
			} else {
			    vArray[currentColumn][currentRow] = CORNER;
			}

			currentColumn++;

			if (!hasFilledAllCells()) {
				moveCursorAbout();
			}
		}


		private void workOutNextStepsWhileFacingDownwards() {
			if (onLastColumn() || isWarrayCellToRightOccupied()) {
				goDownOrLeft();
			} else {
                if (!onLastRow()) {
                    if (isWarrayCellBelowOccupied())
                    	selectLeftRightOrDown();
                    else
                    	selectLeftRightOrDownOrFaceDown();
                } else {
                    if (routeWantsToGoDown) {
                    	selectLeftRightOrDown();
                    } else {
                        routeWantsToGoUp = true;
                        selectLeftRightOrDownOrFaceDown();
                    }
                }
			}
		}


		private int randomChoiceOfFour() {
			return generateRandom(3);
		}


		private int randomChoiceOfThree() {
			return generateRandom(2);
		}


		private void moveCursorAbout() {
			if (onFirstRow() || isWarrayCellAboveOccupied()) {
			    nextState(790);
			} else {
			    if (onLastColumn() || isWarrayCellToRightOccupied()) {
			        nextState(720);
			    } else {
			        if (!onLastRow()) {
			            if (isWarrayCellBelowOccupied())
			            	decisionPoint700();
			            else
			                nextState(680);
			        } else {
			            if (routeWantsToGoDown) {
			            	decisionPoint700();
			            } else {
			                routeWantsToGoUp = true;
			                nextState(680);
			            }
			        }
			    }
			}
		}


		private void goDownAndForceDownwardRoute() {
			if (routeWantsToGoUp) {
				forceDownwardRoute();
			} else {
			    
				routeGoesDown();
			    

			    if (!hasFilledAllCells()) {
			    	// continue;
			        startLooping();
			    }
			}
		}


		private void forceDownwardRoute() {
			routeWantsToGoUp = false;
			routeWantsToGoDown= true;
			if (vArray[currentColumn][currentRow] == DEAD_END) {
			    vArray[currentColumn][currentRow] = UP_OR_DOWN;
			    
			    currentColumn = 1;
			    currentRow = 1;
			    findPointOnRouteThenStartLooping();
			} else {
			    vArray[currentColumn][currentRow] = CORNER;

			    goToNextCellLocationWithWrapping();
				findPointOnRouteThenStartLooping();
			}
		}

		private void routeGoesDown() {
			trackInWarrayBelowCurrent();
			if (vArray[currentColumn][currentRow] == DEAD_END) {
			    vArray[currentColumn][currentRow] = UP_OR_DOWN;
			} else {
			    vArray[currentColumn][currentRow] = CORNER;
			}
		    currentRow++;
		}


		private void routeGoesLeft() {
			trackInWarrayLeftOfCurrent();
            vArray[currentColumn - 1][currentRow] = PASSAGE;
            currentColumn--;
            if (!hasFilledAllCells()) {
                routeWantsToGoUp = false;
                // continue
    	        startLooping();
            }
		}


		private boolean isWarrayCellToRightOccupied() {
			return wArray[currentColumn + 1][currentRow] != 0;
		}


		private boolean onLastColumn() {
			return currentColumn == width;
		}


		private boolean isWarrayCellAboveOccupied() {
			return wArray[currentColumn][currentRow - 1] != 0;
		}


		private boolean onFirstRow() {
			return currentRow - 1 == 0;
		}


		private boolean hasFilledAllCells() {
			return cellBeingFilled > mazeSize;
		}


		private boolean isWarrayCellBelowOccupied() {
			return wArray[currentColumn][currentRow + 1] != 0;
		}


		private boolean onLastRow() {
			return currentRow == height;
		}


		private void do1090orWriteCintoWarrayAndProceed() {
			if (randomChoiceOfThree() == 2)
				goDownAndForceDownwardRoute();
			else
				routeGoesUp();
		}


		private void eitherMakeCurrentPositionIntoRouteOrLoopSomeMore() {
			if (randomChoiceOfThree() == 2)
				goDownAndForceDownwardRoute();
			else
			    routeGoesLeft();
		}


		private void findPointOnRouteThenStartLooping() {
			while (isWarrayCurrentCellBlank()) {
				goToNextCellLocationWithWrapping();
			} 

		    startLooping();
		}


		private boolean isWarrayCurrentCellBlank() {
			return wArray[currentColumn][currentRow] == 0;
		}


		private void decisionPoint700() {
			int x = randomChoiceOfThree();
			if (x == 1)
				routeGoesUp();
			else if (x == 2)
				routeGoesRight();
			else
			    nextState(720);
		}


		private void trackInWarrayBelowCurrent() {
			trackCellIdInWArray(currentColumn, currentRow+1);
		}


		private void trackInWarrayRightOfCurrent() {
			trackCellIdInWArray(currentColumn+1, currentRow);
		}


		private void trackInWarrayLeftOfCurrent() {
			trackCellIdInWArray(currentColumn-1, currentRow);
		}
		
		private void trackCellIdInWArray(int column, int row) {
			wArray[column][row] = cellBeingFilled;
			cellBeingFilled++;
		}


		private void addRouteToMazeAboveCursorAndDecideWhetherToStop() {
			vArray[currentColumn][currentRow - 1] = UP_OR_DOWN;
			currentRow--;
			if (!hasFilledAllCells()) {
				routeWantsToGoUp = false;
			    // continue
			    startLooping();
			}
		}


		private void routeGoesUp() {
			trackCellIdInWArray(currentColumn, currentRow - 1);
			addRouteToMazeAboveCursorAndDecideWhetherToStop();
		}

		private void startLooping() {
			if (currentColumn - 1 == 0 || wArray[currentColumn - 1][currentRow] != 0) {
				moveCursorAbout();
			} else {
		        if (onFirstRow() || isWarrayCellAboveOccupied()) {
					workOutNextStepsWhileFacingDownwards();
		        } else {
	                if (onLastColumn() || isWarrayCellToRightOccupied()) {
	                    nextState(350);
	                } else {
                        nextState(330);
	                }
			    }
			}
		}


		private void goToNextCellLocationWithWrapping() {
			if (notAtRightMostColumn()) {
				currentColumn++;
			} else {
			    currentColumn = 1;
			    if (!onLastRow()) {
			        currentRow++;
			    } else {
			        currentRow = 1;
			    }
			}
		}


		private boolean notAtRightMostColumn() {
			return currentColumn != width;
		}


		public static void nextState(int lineno) {
	        nextState = lineno;
	    }
    }
    
    public static String buildMaze(Random randomGenerator, int horizontal, int vertical) {
    	random = randomGenerator;
    	constructAndOutputMaze(horizontal, vertical);
    	return result.toString();
    }
    
    public static void main(String[] args) {
        constructAndOutputMaze(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println(result);
    }

    private static void clear() {
        result.setLength(0);
    }

    private static void printNewline() {
        result.append("\n");
    }

    public static void print(String text) {
        result.append(text);
    }

    public static int generateRandom(int count) {
        return (int) (count * random.nextFloat()) + 1;
    }

    private static void constructAndOutputMaze(int width, int height) {
        clear();
        print("Amazing - Copyright by Creative Computing, Morristown, NJ");
        printNewline();

        if (tooSmall(width, height)) {
        	return;
        }

        int entrance = generateRandom(width);

        printTopLine(width, entrance);
        int[][] maze = generateMaze(width, height, entrance);
        printMazeInnards(width, height, maze);
       
        for(int row=1; row<=height; row++) {
	        for(int col=1; col<=width; col++) {
	        	System.out.print(maze[col][row]);
	        }
	        System.out.println();
        }
    }

	private static int[][] generateMaze(int width, int height, int entrance) {
		MazeGenerator generator = new MazeGenerator(width, height, entrance);
		return generator.generate();

	}

	private static int[][] constructBlankMaze1BasedArray(int width, int height) {
		int[][] array = new int[width + 1][height + 1];
        for (int i = 0; i <= width; i++) {
            array[i] = new int[height + 1];
        }
		return array;
	}

	private static boolean tooSmall(int h, int v) {
		return h <= 1 || v <= 1;
	}

	private static void printMazeInnards(int height, int width, int[][] maze) {
		for (int j = 1; j <= width; j++) {
            print(LEFT_BORDER);

            for (int i = 1; i <= height; i++) {
                if (maze[i][j] == PASSAGE || maze[i][j] == CORNER) {
                    print(GAP);
                } else {
                    print(GAP_THEN_BORDER);
                }
            }
            printNewline();

            printSeparatorLine(height, maze, j);
        }
	}

	private static void printSeparatorLine(int width, int[][] maze, int row) {
		for (int col = 1; col <= width; col++) {
		    if (maze[col][row] == DEAD_END || maze[col][row] == PASSAGE) {
		        print(CLOSED_WALL);
		    } else {
		        print(OPENING);
		    }
		}

		print(RIGHT_WALL);    // 1360
		printNewline();
	}

	private static void printTopLine(int width, int entrance) {
        for (int i = 1; i <= width; i++) {
            if (i == entrance)
                print(OPENING);
            else
                print(CLOSED_WALL);
        }
        
        print(RIGHT_WALL);
        printNewline();
	}
}
