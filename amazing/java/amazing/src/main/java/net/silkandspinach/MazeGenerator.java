package net.silkandspinach;

public class MazeGenerator {
	// package scope so they can be used by the maze outputter
	static final int DEAD_END = 0;
	static final int UP_OR_DOWN = 1;
	static final int PASSAGE = 2;
	static final int CORNER = 3;
	
	private static int nextState = 0;      // where GOTO goes
	
	private int[][] mazeRoute;
	
	private boolean routeWantsToGoUp;
	private boolean routeWantsToGoDown;
    
	private PathTracker pathTracker;
	private RandomGenerator randomGenerator;
    
	public MazeGenerator(int width, int height, int entrance, RandomGenerator randomGenerator) {
		pathTracker = new PathTracker(width, height, entrance);
		this.randomGenerator = randomGenerator;
		
        mazeRoute = Amazing.constructBlankMaze1BasedArray(width, height);

        routeWantsToGoUp = false;
        routeWantsToGoDown = false;
	}

    public int[][] getMazeRoute() {
    	return mazeRoute;
    }
	
    public void generate() {
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
		return randomGenerator.generateRandom(3);
	}


	private int randomChoiceOfThree() {
		return randomGenerator.generateRandom(2);
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

