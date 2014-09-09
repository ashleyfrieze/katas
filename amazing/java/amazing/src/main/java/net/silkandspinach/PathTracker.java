package net.silkandspinach;

/**
 * Track where the path has been
 */
public class PathTracker {


	private final int width;
	private final int height;
	private final int mazeSize;
	
	private int[][] pathTaken;
	
	private int currentRow;
	private int currentColumn;
	
    int cellBeingFilled;

	public PathTracker(int width, int height, int entrance) {
        pathTaken = Amazing.constructBlankMaze1BasedArray(width, height);
		
		cellBeingFilled = 1;
        pathTaken[entrance][1] = cellBeingFilled;
        cellBeingFilled++;

        currentColumn = entrance;
        currentRow = 1;
        
        this.width = width;
        this.height = height;
        mazeSize = width * height;
	}
	
	public int getCurrentCellValue(int[][] data) {
		return data[currentColumn][currentRow];
	}
	
	public void setCurrentCellValue(int[][] data, int value) {
		data[currentColumn][currentRow] = value;
	}

	public void incrementCell() {
		cellBeingFilled++;
	}

	public void moveBackToFirstCell() {
		currentColumn = 1;
	    currentRow = 1;
	}

	public void decrementColumn() {
        currentColumn--;
	}

	public void incrementColumn() {
		currentColumn++;
	}

	public void decrementRow() {
		currentRow--;
	}
	
	public void incrementRow() {
		currentRow++;
	}
	
	public void trackAboveCurrent() {
		trackCellIdInWArray(currentColumn, currentRow - 1);
	}
	
	public void trackBelowCurrent() {
		trackCellIdInWArray(currentColumn, currentRow+1);
	}


	public void trackRightOfCurrent() {
		trackCellIdInWArray(currentColumn+1, currentRow);
	}


	public void trackLeftOfCurrent() {
		trackCellIdInWArray(currentColumn-1, currentRow);
	}
	
	private void trackCellIdInWArray(int column, int row) {
		pathTaken[column][row] = cellBeingFilled;
		cellBeingFilled++;
	}

	
	public void moveToNextPointOnRoute() {
		while (isCurrentCellBlank()) {
			goToNextCellLocationWithWrapping();
		} 
	}    
    


	public void goToNextCellLocationWithWrapping() {
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

	public boolean notAtRightMostColumn() {
		return currentColumn != width;
	}

	private boolean isCurrentCellBlank() {
		return pathTaken[currentColumn][currentRow] == 0;
	}

	public boolean cannotGoLeft() {
		return currentColumn - 1 == 0 || pathTaken[currentColumn - 1][currentRow] != 0;
	}

	public boolean isCellToRightOccupied() {
		return pathTaken[currentColumn + 1][currentRow] != 0;
	}


	public boolean onLastColumn() {
		return currentColumn == width;
	}


	public boolean isCellAboveOccupied() {
		return pathTaken[currentColumn][currentRow - 1] != 0;
	}


	public boolean onFirstRow() {
		return currentRow - 1 == 0;
	}


	public boolean hasFilledAllCells() {
		return cellBeingFilled > mazeSize;
	}


	public boolean isGoingDownImpossible() {
		return onLastRow() || pathTaken[currentColumn][currentRow + 1] != 0;
	}

	public boolean cannotGoRight() {
		return onLastColumn() || isCellToRightOccupied();
	}
	
	public boolean onLastRow() {
		return currentRow == height;
	}

	public boolean cannotGoUp() {
		return onFirstRow() || isCellAboveOccupied();
	}

}
