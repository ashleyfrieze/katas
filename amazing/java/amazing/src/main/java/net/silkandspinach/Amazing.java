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
    	final int endPointOfMaze;
    	
        int q;
        int z;
        
        int c;

        int r;
        int s;
        
        int width;
        int height;
    	
    	MazeGenerator(int width, int height, int entrance) {
    		this.width = width;
    		this.height = height;
    		
	        wArray = constructBlankMaze1BasedArray(width, height);
	        vArray = constructBlankMaze1BasedArray(width, height);
	        
	        endPointOfMaze = width * height + 1;
	        
	        q = 0;
	        z = 0;
	        
	        c = 1;
	        wArray[entrance][1] = c;
	        c++;
	
	        r = entrance;
	        s = 1;

    	}

        
        int[][] generate() {
	        startLooping();
	        while (nextState != STATE_FINISHED) {
	
				switch (nextState) {
	                case 260:
	                    if (wArray[r][s] == 0) {
	                    	incrementRandSandProgress();
	                    } else {
	                        // continue
	            	        startLooping();
	                    }
	                    break;
	
	                case 330: {
	                    int x = generateRandomMazeElement();
	
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
	                    if (s != height) {
	                        if (wArray[r][s + 1] != 0)
	                        	decisionPoint410();
	                        else
	                            nextState(390);
	                    } else {
	                        if (z == 1) {
	                        	decisionPoint410();
	                        } else {
	                            q = 1;
	                            nextState(390);
	                        }
	                    }
	                    break;
	
	                case 390: {
	                    int x = generateRandomMazeElement();
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
	                    if (r == width)
	                        nextState(530);
	                    else
	                        nextState(440);
	                    break;
	                case 440:
	                    if (wArray[r + 1][s] != 0) {
	                        nextState(530);
	                    } else {
	                        if (s != height) {
	                            if (wArray[r][s + 1] != 0)
	                                nextState(510);
	                            else
	                                nextState(490);
	                        } else {
	                            if (z == 1) {
	                                nextState(510);
	                            } else {
	                                q = 1;
	                                nextState(490);
	                            }
	                        }
	                    }
	                    break;
	                case 490: {
	                    int x = generateRandomMazeElement();
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
	                    if (s != height) {
	                        if (wArray[r][s + 1] != 0)
	                            nextState(END_OF_LOOP);
	                        else
	                            nextState(570);
	                    } else {
	                        if (z == 1) {
	                            nextState(END_OF_LOOP);
	                        } else {
	                            q = 1;
	                            nextState(570);
	                        }
	                    }
	                    break;
	                case 570: {
	                    int x = generateRandom(2);
	                    if (x == 2)
	                        nextState(1090);
	                    else
	                        nextState(END_OF_LOOP);
	                    break;
	                }
	                case 600:
	                    if (s - 1 == 0) {
	                        nextState(790);
	                    } else {
		                    if (wArray[r][s - 1] != 0)
		                        nextState(790);
		                    else
		                        nextState(620);
	                    }
	                    break;
	                case 620:
	                    if (r == width) {
	                        nextState(720);
	                    } else {
	                        if (wArray[r + 1][s] != 0) {
	                            nextState(720);
	                        } else {
	                            if (s != height) {
	                                nextState(670);
	                            } else {
	                                if (z == 1) {
	                                    nextState(700);
	                                } else {
	                                    q = 1;
	                                    nextState(680);
	                                }
	                            }
	                        }
	                    }
	                    break;
	                case 670:
	                    if (wArray[r][s + 1] != 0)
	                        nextState(700);
	                    else
	                        nextState(680);
	                    break;
	                case 680: {
	                    int x = generateRandomMazeElement();
	                    if (x == 1)
	                    	writeCintoWarrayAndProceed();
	                    else if (x == 2)
	                        nextState(1020);
	                    else if (x == 3)
	                        nextState(1090);
	                    else
	                        nextState(700);
	                    break;
	                }
	                case 700: {
	                    int x = generateRandom(2);
	                    if (x == 1)
	                    	writeCintoWarrayAndProceed();
	                    else if (x == 2)
	                        nextState(1020);
	                    else
	                        nextState(720);
	                    break;
	                }
	                case 720:
	                    if (s != height) {
	                        if (wArray[r][s + 1] != 0)
	                        	writeCintoWarrayAndProceed();
	                        else
	                            nextState(760);
	                    } else {
	                        if (z == 1) {
	                        	writeCintoWarrayAndProceed();
	                        } else {
	                            q = 1;
	                            nextState(760);
	                        }
	                    }
	                    break;
	                case 760: {
	                    int x = generateRandom(2);
	                    if (x == 2)
	                        nextState(1090);
	                    else
	                    	writeCintoWarrayAndProceed();
	                    break;
	                }
	                case 790:
	                    if (r == width || wArray[r + 1][s] != 0) {
	                    	if (s != height) {
							    if (wArray[r][s + 1] != 0)
							    	incrementRandSandProgress();
							    else
							        nextState(1090);
							} else {
							    if (z == 1) {
							    	incrementRandSandProgress();
							    } else {
							        q = 1;
							        nextState(1090);
							    }
							}
	                    } else {
                            if (s != height) {
                                if (wArray[r][s + 1] != 0)
                                    nextState(1020);
                                else
            	                    decisionPoint850();
                            } else {
                                if (z == 1) {
                                    nextState(1020);
                                } else {
                                    q = 1;
                                    incrementCstore1inMazeAndDecideIfToFinish();
                                }
                            }
	                        
	                    }
	                    break;
	
	                case END_OF_LOOP:
						storeCinWarrayRmin1SAndIncrementC();
	                    vArray[r - 1][s] = 2;
	                    r--;
	                    if (c == endPointOfMaze) {
	                        nextState(STATE_FINISHED);
	                    } else {
		                    q = 0;
		                    // continue
		        	        startLooping();
	                    }
	                    break;
	                case 1020:
						storeCinWarrayRpls1SandIncrementC();
	                    
	                    if (vArray[r][s] == 0) {
	                        vArray[r][s] = 2;
	                    } else {
	                        vArray[r][s] = 3;
	                    }
	
	                    r++;
	
	                    if (c == endPointOfMaze)
	                        nextState(STATE_FINISHED);
	                    else
	                        nextState(600);
	                    break;
	
	                case 1090:
	                    if (q == 1) {
	                        z = 1;
	                        if (vArray[r][s] == 0) {
	                            vArray[r][s] = 1;
	                            q = 0;
	                            r = 1;
	                            s = 1;
	                            nextState(260);
	                        } else {
	                            vArray[r][s] = 3;
	                            q = 0;
	                            incrementRandSandProgress();
	                        }
	                    } else {
	                        storeCinWarrayRSpls1andIncrementC();
	                        if (vArray[r][s] == 0) {
	                            vArray[r][s] = 1;
	                        } else {
	                            vArray[r][s] = 3;
	                        }
	                        
	                        s++;
	                        if (c == endPointOfMaze) {
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


		private void storeCinWarrayRSpls1andIncrementC() {
			wArray[r][s + 1] = c;
			c++;
		}


		private void storeCinWarrayRpls1SandIncrementC() {
			wArray[r + 1][s] = c;
			c++;
		}


		private void storeCinWarrayRmin1SAndIncrementC() {
			wArray[r - 1][s] = c;
			c++;
		}


		private void incrementCstore1inMazeAndDecideIfToFinish() {
			c++;
			vArray[r][s - 1] = 1;
			s--;
			if (c == endPointOfMaze) {
			    nextState(STATE_FINISHED);
			} else {
			    q = 0;
			    // continue
			    startLooping();
			}
		}


		private void writeCintoWarrayAndProceed() {
			wArray[r][s - 1] = c;
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
			if (r - 1 == 0 || wArray[r - 1][s] != 0) {
			    nextState(600);
			} else {
		        if (s - 1 == 0 || wArray[r][s - 1] != 0) {
		            nextState(430);
		        } else {
	                if (r == width || wArray[r + 1][s] != 0) {
	                    nextState(350);
	                } else {
                        nextState(330);
	                }
			    }
			}
		}


		private void incrementRandSandProgress() {
			if (r != width) {
				r++;
			} else {
			    r = 1;
			    if (s != height) {
			        s++;
			    } else {
			        s = 1;
			    }
			}
			nextState(260);
		}


		private void decisionPoint850() {
			int x = generateRandom(2);
			if (x == 1)
			    nextState(1020);
			else if (x == 2)
			    nextState(1090);
			else
			    nextState(1020);
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

	private static int generateRandomMazeElement() {
		return generateRandom(3);
	}

	private static int[][] constructBlankMaze1BasedArray(int width, int height) {
		int[][] wArray = new int[width + 1][height + 1];
        for (int i = 0; i <= width; i++) {
            wArray[i] = new int[height + 1];
        }
		return wArray;
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
