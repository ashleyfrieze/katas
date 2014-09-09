package net.silkandspinach;

public class MazeGenerator {
	// package scope so they can be used by the maze outputter
	static final int DEAD_END = 0;
	static final int UP_OR_DOWN = 1;
	static final int PASSAGE = 2;
	static final int CORNER = 3;
	
	private int[][] mazeRoute;
	private final int entrance;
	
	private boolean routeWantsToGoUp;
	private boolean routeWantsToGoDown;
    
	private PathTracker pathTracker;
	private RandomGenerator randomGenerator;
    
	public MazeGenerator(int width, int height, RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
		entrance = randomGenerator.generateRandom(width);
		
		pathTracker = new PathTracker(width, height, entrance);
		
        mazeRoute = ArrayHelper.constructBlankMaze1BasedArray(width, height);

        routeWantsToGoUp = false;
        routeWantsToGoDown = false;
	}

    public int[][] getMazeRoute() {
    	return mazeRoute;
    }
    
    public int getEntrance() {
    	return entrance;
    }
	
    public void generate() {
        while (!pathTracker.hasFilledAllCells()) {
            if (pathTracker.cannotGoLeft()) {
				moveCursorAbout();
				continue;
			} 

		    if (pathTracker.cannotGoUp()) {
				workOutNextStepsWhileFacingDownwards();
				continue;
		    } 

	        if (pathTracker.cannotGoRight()) {
	        	goAnyDirectionPossible();
	        	continue;
	        } 

            if (!selectedLeftUpOrRight()) {
            	goAnyDirectionPossible();
            }
        }
    }

	private void goAnyDirectionPossible() {
		if ((!pathTracker.onLastRow() && pathTracker.isGoingDownImpossible())
				||pathTracker.onLastRow() && routeWantsToGoDown) {
			selectLeftUpOrDown();
		} else {
			if (pathTracker.onLastRow()) {
		        routeWantsToGoUp = true;
			}
		    selectAnyDirectionButRight();
		}
	}

	private void goRightOrDown() {
		if (pathTracker.cannotGoRight()) {
			if ((!pathTracker.onLastRow() && pathTracker.isGoingDownImpossible()) 
					|| (pathTracker.onLastRow() && routeWantsToGoDown)) {
				pathTracker.goToNextCellLocationWithWrapping();
				pathTracker.moveToNextPointOnRoute();
			} else {
			    goDownForcingDownwardRoute();
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
					routeGoesUp(false);
		        }
		    }
		    
		}
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
			goDownForcingDownwardRoute();
		} else {
			routeGoesRight();
		}
	}

	private void selectLeftRightOrDownOrFaceDown() {
		switch(randomChoiceOfFour()) {
			case 1:
				routeGoesLeft();
				break;
			case 2:
				routeGoesRight();
				break;
			case 3:
				goDownForcingDownwardRoute();
				break;
			default:
				selectLeftRightOrDown();
				break;
		}
	}


	private void selectLeftRightOrDown() {
		switch(randomChoiceOfThree()) {
			case 1:
				routeGoesLeft();
				break;
			case 2:
				routeGoesRight();
				break;
			default:
				goDownOrLeft();
				break;
		}
	}


	private void selectAnyDirectionButRight() {
		switch(randomChoiceOfFour()) {
			case 1:
			    routeGoesLeft();
			    break;
			case 2:
				routeGoesUp();
				break;
			case 3:
				goDownForcingDownwardRoute();
				break;
			default:
				selectLeftUpOrDown();
				break;
		}
	}

	private boolean selectedLeftUpOrRight() {
		switch(randomChoiceOfFour()) {
			case 1:
			    routeGoesLeft();
			    break;
			case 2:
				routeGoesUp();
				break;
			case 3:
				routeGoesRight();
				break;
			default:
				return false;
		}
		
		return true;
	}


	private void selectAnyDirectionButLeft() {
		switch(randomChoiceOfFour()) {
			case 1:
				routeGoesUp();
				break;
			case 2:
				routeGoesRight();
				break;
			case 3:
				goDownForcingDownwardRoute();
				break;
			default:
				selectUpRightDownOrReturnUp();
		}
	}
	
	
	private void selectLeftUpOrDown() {
		switch (randomChoiceOfThree()) {
			case 1:
				routeGoesLeft();
				break;
			case 2:
				routeGoesUp();
				break;
			default:
				workOutNextStepsWhileFacingDownwards();
				break;
		}
	}
	


	private void selectUpOrDown() {
		if (randomChoiceOfThree() == 2) {
			goDownForcingDownwardRoute();
		} else {
			routeGoesUp();
		}
	}


	private void selectDownOrLeft() {
		if (randomChoiceOfThree() == 2) {
			goDownForcingDownwardRoute();
		} else {
		    routeGoesLeft();
		}
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
			goRightOrDown();
			return;
		} 

	    if (pathTracker.cannotGoRight()) {
	    	moveDownIfPossibleOrReverse();
	    	return;
	    } 

        if (!pathTracker.onLastRow()) {
            if (pathTracker.isGoingDownImpossible()) {
            	selectUpRightDownOrReturnUp();
            } else {
            	selectAnyDirectionButLeft();
            }
        } else {
            if (routeWantsToGoDown) {
            	selectUpRightDownOrReturnUp();
            } else {
                routeWantsToGoUp = true;
                selectAnyDirectionButLeft();
            }
        }
	}


	private void goDownForcingDownwardRoute() {
		if (routeWantsToGoUp) {
			forceDownwardRoute();
		} else {
			routeGoesDown();
		}
	}


	private void forceDownwardRoute() {
		routeWantsToGoUp = false;
		routeWantsToGoDown= true;
		if (pathTracker.getCurrentCellValue(mazeRoute) == DEAD_END) {
			pathTracker.setCurrentCellValue(mazeRoute, UP_OR_DOWN);
		    
		    pathTracker.moveBackToFirstCell();
		} else {
			pathTracker.setCurrentCellValue(mazeRoute, CORNER);
		    pathTracker.goToNextCellLocationWithWrapping();
		}
		
	    pathTracker.moveToNextPointOnRoute();
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
        }
	}


	private void routeGoesUp() {
		routeGoesUp(true);
	}

	/**
	 * Route goes up and we're either tracking it, or skipping the tracking
	 * @param track
	 */
	private void routeGoesUp(boolean track) {
		if (track) { 
			pathTracker.trackAboveCurrent();
		} else {
			pathTracker.incrementCell();
		}
		
		pathTracker.decrementRow();
		pathTracker.setCurrentCellValue(mazeRoute, UP_OR_DOWN);
		routeWantsToGoUp = false;
	}	

}

