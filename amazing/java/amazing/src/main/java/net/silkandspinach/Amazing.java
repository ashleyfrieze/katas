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

    // STATES
    private static final int END_OF_LOOP = 940;
    private static final int STATE_FINISHED = 1200;
    
    static class MazeGenerator {
    	private static int nextState = 0;      // where GOTO goes
    	
    	private int[][] wArray;
    	private int[][] vArray;
    	final int mazeSize;
    	
        boolean someDecision;
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
	        
	        someDecision = false;
	        someOtherDecision = false;
	        
	        cellBeingFilled = 1;
	        wArray[entrance][1] = cellBeingFilled;
	        cellBeingFilled++;
	
	        currentColumn = entrance;
	        currentRow = 1;

    	}

        
        int[][] generate() {
	        startLooping();
	        while (nextState != STATE_FINISHED) {
	
				switch (nextState) {
	                case 330: {
	                    int x = generateRandom(3);
	
	                    if (x == 1)
	                        nextState(END_OF_LOOP);
	                    else if (x == 2)
	                    	writeCintoWarrayAndProceed();
	                    else if (x == 3)
	                        nextState(1020);
	                    else
	                        nextState(350);
	                    break;
	                }
	                case 350:
	                    if (notOnLastRow()) {
	                        if (isWarrayOccupiedBelowCurrentPosition())
	                        	decisionPoint410();
	                        else
	                            nextState(390);
	                    } else {
	                        if (someOtherDecision) {
	                        	decisionPoint410();
	                        } else {
	                            someDecision = true;
	                            nextState(390);
	                        }
	                    }
	                    break;
	
	                case 390: {
	                    int x = generateRandom(3);
	                    if (x == 1)
	                        nextState(END_OF_LOOP);
	                    else if (x == 2)
	                    	writeCintoWarrayAndProceed();
	                    else if (x == 3)
	                        nextState(1090);
	                    else
	                    	decisionPoint410();
	                    break;
	                }

	                case 430:
	                    if (currentColumn == width)
	                        nextState(530);
	                    else
	                        nextState(440);
	                    break;
	                case 440:
	                    if (wArray[currentColumn + 1][currentRow] != 0) {
	                        nextState(530);
	                    } else {
	                        if (notOnLastRow()) {
	                            if (isWarrayOccupiedBelowCurrentPosition())
	                                nextState(510);
	                            else
	                                nextState(490);
	                        } else {
	                            if (someOtherDecision) {
	                                nextState(510);
	                            } else {
	                                someDecision = true;
	                                nextState(490);
	                            }
	                        }
	                    }
	                    break;
	                case 490: {
	                    int x = generateRandom(3);
	                    if (x == 1)
	                        nextState(END_OF_LOOP);
	                    else if (x == 2)
	                        nextState(1020);
	                    else if (x == 3)
	                        nextState(1090);
	                    else
	                        nextState(510);
	                    break;
	                }
	                case 510: {
	                    int x = generateRandom(2);
	                    if (x == 1)
	                        nextState(END_OF_LOOP);
	                    else if (x == 2)
	                        nextState(1020);
	                    else
	                        nextState(530);
	                    break;
	                }
	                case 530:
	                    if (notOnLastRow()) {
	                        if (isWarrayOccupiedBelowCurrentPosition())
	                            nextState(END_OF_LOOP);
	                        else
	                        	do1090orEndLoop();
	                    } else {
	                        if (someOtherDecision) {
	                            nextState(END_OF_LOOP);
	                        } else {
	                            someDecision = true;
	                            do1090orEndLoop();
	                        }
	                    }
	                    break;

	                case 600:
	                    if (currentRow - 1 == 0 || wArray[currentColumn][currentRow - 1] != 0) {
	                        nextState(790);
	                    } else {
		                    if (currentColumn == width || wArray[currentColumn + 1][currentRow] != 0) {
		                        nextState(720);
		                    } else {
	                            if (notOnLastRow()) {
	        	                    if (isWarrayOccupiedBelowCurrentPosition())
	        	                    	decisionPoint700();
	        	                    else
	        	                        nextState(680);
	                            } else {
	                                if (someOtherDecision) {
	                                	decisionPoint700();
	                                } else {
	                                    someDecision = true;
	                                    nextState(680);
	                                }
	                            }
		                    }
	                    }
	                    break;

	                case 680: {
	                    int x = generateRandom(3);
	                    if (x == 1)
	                    	writeCintoWarrayAndProceed();
	                    else if (x == 2)
	                        nextState(1020);
	                    else if (x == 3)
	                        nextState(1090);
	                    else
	                    	decisionPoint700();
	                    break;
	                }
	                case 720:
	                    if (notOnLastRow()) {
	                        if (isWarrayOccupiedBelowCurrentPosition()) {
	                        	writeCintoWarrayAndProceed();
	                        } else {
	                        	do1090orWriteCintoWarrayAndProceed();
	                        }
	                    } else {
	                        if (someOtherDecision) {
	                        	writeCintoWarrayAndProceed();
	                        } else {
	                            someDecision = true;
	                            do1090orWriteCintoWarrayAndProceed();
	                        }
	                    }
	                    break;
	                case 790:
	                    if (currentColumn == width || wArray[currentColumn + 1][currentRow] != 0) {
	                    	if (notOnLastRow()) {
							    if (isWarrayOccupiedBelowCurrentPosition())
							    	incrementRandSandProgress();
							    else
							        nextState(1090);
							} else {
							    if (someOtherDecision) {
							    	incrementRandSandProgress();
							    } else {
							        someDecision = true;
							        nextState(1090);
							    }
							}
	                    } else {
                            if (notOnLastRow()) {
                                if (isWarrayOccupiedBelowCurrentPosition()) {
                                    nextState(1020);
                                } else {
            	                    if (generateRandom(2) == 2) {
									    nextState(1090);
            	                    } else {
									    nextState(1020);
            	                    }
                                }
                            } else {
                                if (someOtherDecision) {
                                    nextState(1020);
                                } else {
                                    someDecision = true;
                                    incrementCstore1inMazeAndDecideIfToFinish();
                                }
                            }
	                        
	                    }
	                    break;
	
	                case END_OF_LOOP:
						storeCinWarrayRmin1SAndIncrementC();
	                    vArray[currentColumn - 1][currentRow] = 2;
	                    currentColumn--;
	                    if (hasFilledAllCells()) {
	                        nextState(STATE_FINISHED);
	                    } else {
		                    someDecision = false;
		                    // continue
		        	        startLooping();
	                    }
	                    break;
	                case 1020:
						storeCinWarrayRpls1SandIncrementC();
	                    
	                    if (vArray[currentColumn][currentRow] == 0) {
	                        vArray[currentColumn][currentRow] = 2;
	                    } else {
	                        vArray[currentColumn][currentRow] = 3;
	                    }
	
	                    currentColumn++;
	
	                    if (hasFilledAllCells())
	                        nextState(STATE_FINISHED);
	                    else
	                        nextState(600);
	                    break;
	
	                case 1090:
	                    if (someDecision) {
	                    	someDecision = false;
	                    	someOtherDecision= true;
	                        if (vArray[currentColumn][currentRow] == 0) {
	                            vArray[currentColumn][currentRow] = 1;
	                            
	                            currentColumn = 1;
	                            currentRow = 1;
	                            findNonBlankWarrayLocation();
	                        } else {
	                            vArray[currentColumn][currentRow] = 3;

	                            incrementRandSandProgress();
	                        }
	                    } else {
	                        storeCinWarrayRSpls1andIncrementC();
	                        if (vArray[currentColumn][currentRow] == 0) {
	                            vArray[currentColumn][currentRow] = 1;
	                        } else {
	                            vArray[currentColumn][currentRow] = 3;
	                        }
	                        
	                        currentRow++;
	                        if (hasFilledAllCells()) {
	                            nextState(STATE_FINISHED);
	                        } else {
	                        	// continue;
	                	        startLooping();
	                        }
	                    }
	                    break;
	
	                default:
	                	throw new RuntimeException("Unknown next state " + nextState);
	
	            }
	
	        }
	        
	        return vArray;
	    }


		private boolean hasFilledAllCells() {
			return cellBeingFilled > mazeSize;
		}


		private boolean isWarrayOccupiedBelowCurrentPosition() {
			return wArray[currentColumn][currentRow + 1] != 0;
		}


		private boolean notOnLastRow() {
			return currentRow != height;
		}


		private void do1090orWriteCintoWarrayAndProceed() {
			if (generateRandom(2) == 2)
			    nextState(1090);
			else
				writeCintoWarrayAndProceed();
		}


		private void do1090orEndLoop() {
			int x = generateRandom(2);
			if (x == 2)
			    nextState(1090);
			else
			    nextState(END_OF_LOOP);
		}


		private void findNonBlankWarrayLocation() {
			if (wArray[currentColumn][currentRow] == 0) {
				incrementRandSandProgress();
			} else {
			    // continue
			    startLooping();
			}
		}


		private void decisionPoint700() {
			int x = generateRandom(2);
			if (x == 1)
				writeCintoWarrayAndProceed();
			else if (x == 2)
			    nextState(1020);
			else
			    nextState(720);
		}


		private void storeCinWarrayRSpls1andIncrementC() {
			wArray[currentColumn][currentRow + 1] = cellBeingFilled;
			cellBeingFilled++;
		}


		private void storeCinWarrayRpls1SandIncrementC() {
			wArray[currentColumn + 1][currentRow] = cellBeingFilled;
			cellBeingFilled++;
		}


		private void storeCinWarrayRmin1SAndIncrementC() {
			wArray[currentColumn - 1][currentRow] = cellBeingFilled;
			cellBeingFilled++;
		}


		private void incrementCstore1inMazeAndDecideIfToFinish() {
			cellBeingFilled++;
			vArray[currentColumn][currentRow - 1] = 1;
			currentRow--;
			if (hasFilledAllCells()) {
			    nextState(STATE_FINISHED);
			} else {
				someDecision = false;
			    // continue
			    startLooping();
			}
		}


		private void writeCintoWarrayAndProceed() {
			wArray[currentColumn][currentRow - 1] = cellBeingFilled;
			incrementCstore1inMazeAndDecideIfToFinish();
		}

		private void decisionPoint410() {
			int x = generateRandom(2);
			if (x == 1)
			    nextState(END_OF_LOOP);
			else if (x == 2)
				writeCintoWarrayAndProceed();
			else
			    nextState(430);
		}

		private void startLooping() {
			if (currentColumn - 1 == 0 || wArray[currentColumn - 1][currentRow] != 0) {
			    nextState(600);
			} else {
		        if (currentRow - 1 == 0 || wArray[currentColumn][currentRow - 1] != 0) {
		            nextState(430);
		        } else {
	                if (currentColumn == width || wArray[currentColumn + 1][currentRow] != 0) {
	                    nextState(350);
	                } else {
                        nextState(330);
	                }
			    }
			}
		}


		private void incrementRandSandProgress() {
			if (currentColumn != width) {
				currentColumn++;
			} else {
			    currentColumn = 1;
			    if (notOnLastRow()) {
			        currentRow++;
			    } else {
			        currentRow = 1;
			    }
			}
			findNonBlankWarrayLocation();
		}


		public static void nextState(int lineno) {
	        nextState = lineno;
	    }
    }
    
    public static String buildMaze(Random randomGenerator, int horizontal, int vertical) {
    	random = randomGenerator;
    	doit(horizontal, vertical);
    	return result.toString();
    }
    
    public static void main(String[] args) {
        doit(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println(result);
    }

    private static void clear() {
        result.setLength(0);
    }

    private static void println() {
        result.append("\n");
    }

    public static void print(String text) {
        result.append(text);
    }

    public static int generateRandom(int count) {
        return (int) (count * random.nextFloat()) + 1;
    }

    private static void doit(int width, int height) {
        clear();
        print("Amazing - Copyright by Creative Computing, Morristown, NJ");
        println();

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
            println();

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
		println();
	}

	private static void printTopLine(int width, int entrance) {
        for (int i = 1; i <= width; i++) {
            if (i == entrance)
                print(OPENING);
            else
                print(CLOSED_WALL);
        }
        
        print(RIGHT_WALL);
        println();
	}
}
