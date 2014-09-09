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
	
    private static Random random = new Random(0);
    private static StringBuffer result = new StringBuffer();
    
    static class MazeGenerator {
    	private static int nextState = 0;      // where GOTO goes
    	
    	private int[][] wArray;
    	private int[][] vArray;
    	final int mazeSize;
    	
        boolean findNextCursorPointWhenMakingCurrentIntoRoute;
        boolean someOtherDecision;
        
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
	        
	        findNextCursorPointWhenMakingCurrentIntoRoute = false;
	        someOtherDecision = false;
	        
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
	                        wallToTheLeftAndContinue();
	                    else if (x == 2)
	                    	trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
	                    else if (x == 3)
	                        nextState(1020);
	                    else
	                        nextState(350);
	                    break;
	                }
	                
	            	
	                case 390: {
	                    int x = randomChoiceOfFour();
	                    if (x == 1)
	                        wallToTheLeftAndContinue();
	                    else if (x == 2)
	                    	trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
	                    else if (x == 3)
	                        makeCurrentPositionIntoRoute();
	                    else
	                    	decisionPoint410();
	                    break;
	                }

	                case 490: {
	                    int x = randomChoiceOfFour();
	                    if (x == 1)
	                        wallToTheLeftAndContinue();
	                    else if (x == 2)
	                        nextState(1020);
	                    else if (x == 3)
	                    	makeCurrentPositionIntoRoute();
	                    else
	                        nextState(510);
	                    break;
	                }
	                

	                case 680: {
	                    int x = randomChoiceOfFour();
	                    if (x == 1)
	                    	trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
	                    else if (x == 2)
	                        nextState(1020);
	                    else if (x == 3)
	                    	makeCurrentPositionIntoRoute();
	                    else
	                    	decisionPoint700();
	                    break;
	                }
	                
	                
	                case 350:
	                	if ((!onLastRow() && isWarrayCellBelowOccupied())||onLastRow() && someOtherDecision) {
	                		decisionPoint410();
	                	} else {
	                		if (onLastRow()) {
	                            findNextCursorPointWhenMakingCurrentIntoRoute = true;
	                		}
	                		nextState(390);
	                	}
	                    break;

	                case 440:
	                    if (isWarrayCellToRightOccupied()) {
	                        nextState(530);
	                    } else {
	                        if (!onLastRow()) {
	                            if (isWarrayCellBelowOccupied())
	                                nextState(510);
	                            else
	                                nextState(490);
	                        } else {
	                            if (someOtherDecision) {
	                                nextState(510);
	                            } else {
	                                findNextCursorPointWhenMakingCurrentIntoRoute = true;
	                                nextState(490);
	                            }
	                        }
	                    }
	                    break;

	                case 510: {
	                    int x = randomChoiceOfThree();
	                    if (x == 1)
	                        wallToTheLeftAndContinue();
	                    else if (x == 2)
	                        nextState(1020);
	                    else
	                        nextState(530);
	                    break;
	                }
	                case 530:
	                    if (!onLastRow()) {
	                        if (isWarrayCellBelowOccupied())
	                            wallToTheLeftAndContinue();
	                        else
	                        	eitherMakeCurrentPositionIntoRouteOrLoopSomeMore();
	                    } else {
	                        if (someOtherDecision) {
	                            wallToTheLeftAndContinue();
	                        } else {
	                            findNextCursorPointWhenMakingCurrentIntoRoute = true;
	                            eitherMakeCurrentPositionIntoRouteOrLoopSomeMore();
	                        }
	                    }
	                    break;

	                case 720:
	                    if (!onLastRow()) {
	                        if (isWarrayCellBelowOccupied()) {
	                        	trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
	                        } else {
	                        	do1090orWriteCintoWarrayAndProceed();
	                        }
	                    } else {
	                        if (someOtherDecision) {
	                        	trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
	                        } else {
	                            findNextCursorPointWhenMakingCurrentIntoRoute = true;
	                            do1090orWriteCintoWarrayAndProceed();
	                        }
	                    }
	                    break;
	                case 790:
	                    if (onLastColumn() || isWarrayCellToRightOccupied()) {
	                    	if (!onLastRow()) {
							    if (isWarrayCellBelowOccupied())
							    	goToNextCellLocationWithWrappingThenFindNonBlankWarrayLocation();
							    else
							    	makeCurrentPositionIntoRoute();
							} else {
							    if (someOtherDecision) {
							    	goToNextCellLocationWithWrappingThenFindNonBlankWarrayLocation();
							    } else {
							        findNextCursorPointWhenMakingCurrentIntoRoute = true;
							        makeCurrentPositionIntoRoute();
							    }
							}
	                    } else {
                            if (!onLastRow()) {
                                if (isWarrayCellBelowOccupied()) {
                                    nextState(1020);
                                } else {
            	                    if (randomChoiceOfThree() == 2) {
            	                    	makeCurrentPositionIntoRoute();
            	                    } else {
									    nextState(1020);
            	                    }
                                }
                            } else {
                                if (someOtherDecision) {
                                    nextState(1020);
                                } else {
                                    findNextCursorPointWhenMakingCurrentIntoRoute = true;
                        			cellBeingFilled++;
                                    addRouteToMazeAboveCursorAndDecideWhetherToStop();
                                }
                            }
	                        
	                    }
	                    break;
	
	                case 1020:
						trackInWarrayRightOfCurrent();
	                    
	                    if (vArray[currentColumn][currentRow] == 0) {
	                        vArray[currentColumn][currentRow] = 2;
	                    } else {
	                        vArray[currentColumn][currentRow] = 3;
	                    }
	
	                    currentColumn++;
	
	                    if (!hasFilledAllCells()) {
	                    	moveCursorAbout();
	                    }
	                    break;
	

	                default:
	                	throw new RuntimeException("Unknown next state " + nextState);
	
	            }
	
	        }
	        
	        return vArray;
	    }


		private void moveCursor430() {
			if (onLastColumn())
			    nextState(530);
			else
			    nextState(440);
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
			            if (someOtherDecision) {
			            	decisionPoint700();
			            } else {
			                findNextCursorPointWhenMakingCurrentIntoRoute = true;
			                nextState(680);
			            }
			        }
			    }
			}
		}


		private void makeCurrentPositionIntoRoute() {
			if (findNextCursorPointWhenMakingCurrentIntoRoute) {
				findNextCursorPointWhenMakingCurrentIntoRoute = false;
				someOtherDecision= true;
			    if (vArray[currentColumn][currentRow] == 0) {
			        vArray[currentColumn][currentRow] = 1;
			        
			        currentColumn = 1;
			        currentRow = 1;
			        findNonBlankWarrayLocationThenStartLooping();
			    } else {
			        vArray[currentColumn][currentRow] = 3;

			        goToNextCellLocationWithWrappingThenFindNonBlankWarrayLocation();
			    }
			} else {
			    trackInWarrayBelowCurrent();
			    if (vArray[currentColumn][currentRow] == 0) {
			        vArray[currentColumn][currentRow] = 1;
			    } else {
			        vArray[currentColumn][currentRow] = 3;
			    }
			    
			    currentRow++;
			    if (!hasFilledAllCells()) {
			    	// continue;
			        startLooping();
			    }
			}
		}


		private void wallToTheLeftAndContinue() {
			trackInWarrayLeftOfCurrent();
            vArray[currentColumn - 1][currentRow] = 2;
            currentColumn--;
            if (!hasFilledAllCells()) {
                findNextCursorPointWhenMakingCurrentIntoRoute = false;
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
				makeCurrentPositionIntoRoute();
			else
				trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
		}


		private void eitherMakeCurrentPositionIntoRouteOrLoopSomeMore() {
			if (randomChoiceOfThree() == 2)
				makeCurrentPositionIntoRoute();
			else
			    wallToTheLeftAndContinue();
		}


		private void findNonBlankWarrayLocationThenStartLooping() {
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
				trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
			else if (x == 2)
			    nextState(1020);
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
			vArray[currentColumn][currentRow - 1] = 1;
			currentRow--;
			if (!hasFilledAllCells()) {
				findNextCursorPointWhenMakingCurrentIntoRoute = false;
			    // continue
			    startLooping();
			}
		}


		private void trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish() {
			trackCellIdInWArray(currentColumn, currentRow - 1);
			addRouteToMazeAboveCursorAndDecideWhetherToStop();
		}

		private void decisionPoint410() {
			int x = randomChoiceOfThree();
			if (x == 1)
			    wallToTheLeftAndContinue();
			else if (x == 2)
				trackInWarrayAboveCurrentUpdateMazeAndDecideWhetherToFinish();
			else
				moveCursor430();
		}

		private void startLooping() {
			if (currentColumn - 1 == 0 || wArray[currentColumn - 1][currentRow] != 0) {
				moveCursorAbout();
			} else {
		        if (onFirstRow() || isWarrayCellAboveOccupied()) {
					moveCursor430();
		        } else {
	                if (onLastColumn() || isWarrayCellToRightOccupied()) {
	                    nextState(350);
	                } else {
                        nextState(330);
	                }
			    }
			}
		}


		private void goToNextCellLocationWithWrappingThenFindNonBlankWarrayLocation() {
			goToNextCellLocationWithWrapping();
			findNonBlankWarrayLocationThenStartLooping();
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
        printMazeInnards(width, height, generateMaze(width, height, entrance));
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
                if (maze[i][j] >= 2) {
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
		    if (maze[col][row] == 0 || maze[col][row] == 2) {
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
