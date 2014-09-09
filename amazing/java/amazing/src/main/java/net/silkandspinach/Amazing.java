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
    	
    	private int[][] mazeRoute;
    	
        boolean routeWantsToGoUp;
        boolean routeWantsToGoDown;
        
        PathTracker pathTracker;
        
    	MazeGenerator(int width, int height, int entrance) {
    		pathTracker = new PathTracker(width, height, entrance);
    		
	        mazeRoute = constructBlankMaze1BasedArray(width, height);

	        
	        routeWantsToGoUp = false;
	        routeWantsToGoDown = false;
	        

    	}

        
        int[][] generate() {
	        startLooping();
	        while (!pathTracker.hasFilledAllCells()) {
	
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
	                
	                case 350:
	                	if ((!pathTracker.onLastRow() && pathTracker.isGoingDownImpossible())
	                			||pathTracker.onLastRow() && routeWantsToGoDown) {
	                		selectLeftUpOrDown();
	                	} else {
	                		if (pathTracker.onLastRow()) {
	                            routeWantsToGoUp = true;
	                		}
		                    int x = randomChoiceOfFour();
		                    if (x == 1)
		                        routeGoesLeft();
		                    else if (x == 2)
		                    	routeGoesUp();
		                    else if (x == 3)
		                        goDownAndForceDownwardRoute();
		                    else
		                    	selectLeftUpOrDown();
	                	}
	                    break;


	                case 790:
	                    if (pathTracker.cannotGoRight()) {
	                    	if ((!pathTracker.onLastRow() && pathTracker.isGoingDownImpossible()) 
	                    			|| (pathTracker.onLastRow() && routeWantsToGoDown)) {
								pathTracker.goToNextCellLocationWithWrapping();
								findPointOnRouteThenStartLooping();
							} else {
							    goDownAndForceDownwardRoute();
							} 
	                    } else {
                            if (!pathTracker.onLastRow()) {
                                if (pathTracker.isGoingDownImpossible()) {
                                	routeGoesRight();
                                } else {
            	                    selectDownOrRight();
                                }
                            } else {
                                if (routeWantsToGoDown) {
                                	routeGoesRight();
                                } else {
                        			pathTracker.incrementCell();
                                    addRouteToMazeAboveCursorAndKeepLooping();
                                }
                            }
	                        
	                    }
	                    break;


	                default:
	                	throw new RuntimeException("Unknown next state " + nextState);
	
	            }
	
	        }
	        
	        return mazeRoute;
	    }


		private void selectAnyDirection() {
			int x = randomChoiceOfFour();
			if (x == 1)
				routeGoesUp();
			else if (x == 2)
				routeGoesRight();
			else if (x == 3)
				goDownAndForceDownwardRoute();
			else
				selectUpRightDownOrReturnUp();
		}


		private void moveDownIfPossibleOrReverse() {
			if (pathTracker.onLastRow()) {
			    if (routeWantsToGoDown) {
			    	routeGoesUp();
			    } else {
			        routeWantsToGoUp = true;
			        selectUpOrDown();
			    }

			} else {
			    if (pathTracker.isGoingDownImpossible()) {
			    	routeGoesUp();
			    } else {
			    	selectUpOrDown();
			    }
			}
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
			if (!pathTracker.onLastRow()) {
			    if (pathTracker.isGoingDownImpossible())
			        routeGoesLeft();
			    else
			    	selectDownOrLeft();
			} else {
			    if (routeWantsToGoDown) {
			        routeGoesLeft();
			    } else {
			        routeWantsToGoUp = true;
			        selectDownOrLeft();
			    }
			}
		}


		private void routeGoesRight() {
			pathTracker.trackRightOfCurrent();
			
			if (pathTracker.getCurrentCellValue(mazeRoute) == DEAD_END) {
				pathTracker.setCurrentCellValue(mazeRoute, PASSAGE);
			} else {
				pathTracker.setCurrentCellValue(mazeRoute, CORNER);
			}

			pathTracker.incrementColumn();

			if (!pathTracker.hasFilledAllCells()) {
				moveCursorAbout();
			}
		}


		private void workOutNextStepsWhileFacingDownwards() {
			if (pathTracker.cannotGoRight()) {
				goDownOrLeft();
			} else {
                if (!pathTracker.onLastRow()) {
                    if (pathTracker.isGoingDownImpossible())
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
			if (pathTracker.cannotGoUp()) {
			    nextState(790);
			} else {
			    if (pathTracker.cannotGoRight()) {
			    	moveDownIfPossibleOrReverse();
			    } else {
			        if (!pathTracker.onLastRow()) {
			            if (pathTracker.isGoingDownImpossible())
			            	selectUpRightDownOrReturnUp();
			            else
			            	selectAnyDirection();
			        } else {
			            if (routeWantsToGoDown) {
			            	selectUpRightDownOrReturnUp();
			            } else {
			                routeWantsToGoUp = true;
			                selectAnyDirection();
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
			    

			    if (!pathTracker.hasFilledAllCells()) {
			    	// continue;
			        startLooping();
			    }
			}
		}


		private void forceDownwardRoute() {
			routeWantsToGoUp = false;
			routeWantsToGoDown= true;
			if (pathTracker.getCurrentCellValue(mazeRoute) == DEAD_END) {
				pathTracker.setCurrentCellValue(mazeRoute, UP_OR_DOWN);
			    
			    pathTracker.moveBackToFirstCell();
			    
			    findPointOnRouteThenStartLooping();
			} else {
				pathTracker.setCurrentCellValue(mazeRoute, CORNER);

			    pathTracker.goToNextCellLocationWithWrapping();
				findPointOnRouteThenStartLooping();
			}
		}

		private void routeGoesDown() {
			pathTracker.trackBelowCurrent();
			if (pathTracker.getCurrentCellValue(mazeRoute) == DEAD_END) {
				pathTracker.setCurrentCellValue(mazeRoute, UP_OR_DOWN);
			} else {
				pathTracker.setCurrentCellValue(mazeRoute,  CORNER);
			}
			pathTracker.incrementRow();
		}


		private void routeGoesLeft() {
			pathTracker.trackLeftOfCurrent();
			pathTracker.decrementColumn();
			pathTracker.setCurrentCellValue(mazeRoute, PASSAGE);

            if (!pathTracker.hasFilledAllCells()) {
                routeWantsToGoUp = false;
                // continue
    	        startLooping();
            }
		}



		private void selectUpOrDown() {
			if (randomChoiceOfThree() == 2)
				goDownAndForceDownwardRoute();
			else
				routeGoesUp();
		}


		private void selectDownOrLeft() {
			if (randomChoiceOfThree() == 2)
				goDownAndForceDownwardRoute();
			else
			    routeGoesLeft();
		}


		private void findPointOnRouteThenStartLooping() {
			pathTracker.moveToNextPointOnRoute();

		    startLooping();
		}


		private void selectUpRightDownOrReturnUp() {
			int x = randomChoiceOfThree();
			if (x == 1)
				routeGoesUp();
			else if (x == 2)
				routeGoesRight();
			else
				moveDownIfPossibleOrReverse();
		}

		private void addRouteToMazeAboveCursorAndKeepLooping() {
			pathTracker.decrementRow();
			pathTracker.setCurrentCellValue(mazeRoute, UP_OR_DOWN);
			routeWantsToGoUp = false;
		    startLooping();
		}


		private void routeGoesUp() {
			pathTracker.trackAboveCurrent();
			
			addRouteToMazeAboveCursorAndKeepLooping();
		}

		private void startLooping() {
			if (pathTracker.hasFilledAllCells()) {
				return;
			}
			if (pathTracker.cannotGoLeft()) {
				moveCursorAbout();
			} else {
		        if (pathTracker.cannotGoUp()) {
					workOutNextStepsWhileFacingDownwards();
		        } else {
	                if (pathTracker.cannotGoRight()) {
	                    nextState(350);
	                } else {
                        nextState(330);
	                }
			    }
			}
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
