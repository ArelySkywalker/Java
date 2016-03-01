import java.util.*;
/**
 * This class represents the underlying data and functionality of
 * a collapse game.
 * 
 * @author Arely Miramontes
 *
 */
public class CollapseGame
{
	private CollapsePiece[][] tileBoard;
	private char[][] characterBoard;
	private CollapsePiece[][] savedTileBoard;
	private char[][] savedCharacterBoard;
	private int boardSize;
	private int numMoves;

	/**
	 * Constructor for objects of class CollapseGame
	 * 
	 * @param dimension the size of the baord
	 * @param boardNumber the desired board number
	 */
	public CollapseGame(int dimension, int boardNumber)
	{
		this.boardSize = dimension;
		this.tileBoard = new CollapsePiece[boardSize][boardSize];
		this.characterBoard = new char[boardSize][boardSize];
		this.savedTileBoard = new CollapsePiece[boardSize][boardSize];
		this.savedCharacterBoard = new char[boardSize][boardSize];
		this.numMoves = 0;

		this.generateBoard(boardNumber);
	}

	/**
	 * Accessor method to get the game board.
	 * 
	 * @return the game board object.
	 */
	public CollapsePiece[][] getTileBoard()
	{
		return this.tileBoard;
	}

	/**
	 * Accessor method to get the game board.
	 * 
	 * @return the game board object.
	 */
	public char[][] getCharacterBoard()
	{
		return this.characterBoard.clone();
	}

	/**
	 * Private method which generates a board from a given number, representing
	 * the random seed.
	 * 
	 * @param boardNum the seed which the random
	 * generator will use to generate the board.
	 */
	private void generateBoard(int boardNum)
	{

		CollapsePiece[] pieces = {CollapsePiece.green, CollapsePiece.purple,
				CollapsePiece.red};
		java.util.Random generator = new java.util.Random(boardNum);

//		for(int i = 0; i < 10; i++)  {
//			tileBoard[i][i] = null;


			/*Iterates through each row on the board*/
			for (int row = 0; row < 10; row++)
			{
				/*Iterates through each column on the board*/
				for (int col = 0; col < tileBoard[0].length; col++)
				{
					// Generate one of three kinds of tiles
					tileBoard[row][col] = pieces[generator.nextInt(pieces.length)];

					/*If the current tile is green, then it sets the char to '+'*/
					if(tileBoard[row][col] == CollapsePiece.green)
					{
						characterBoard[row][col] = '+';
					}
					/*If the current tile is purple, then it sets the char to 'x'*/
					if(tileBoard[row][col] == CollapsePiece.purple)
					{
						characterBoard[row][col] = 'x';
					}
					/*If the current tile is red, then it sets the char to 'o'*/
					if(tileBoard[row][col] == CollapsePiece.red)
					{
						characterBoard[row][col] = 'o';
					}
				}
			}
//		}
	}
	


	/**
	 * Determines if the player has won the game.
	 * 
	 * @return returns true if the player has one, false otherwise.
	 */
	protected boolean isGameOver()
	{
		boolean gameIsOver = true;

		/*Iterates through each row on the board*/
		for (int rowIter = 0; rowIter < characterBoard.length && gameIsOver; rowIter++)
		{
			/*Iterates through each column on the board*/
			for(int colIter = 0; colIter < characterBoard[0].length &&
					gameIsOver; colIter++)
			{
				/*Determines if the current tile is empty*/
				if(characterBoard[rowIter][colIter] != ' ')
				{
					gameIsOver = false;
				}
			}
		}

		return gameIsOver;
	}

	/**
	 * A turn is taken on the board.
	 * 
	 * @param rowPos the row that the user has chosen for their turn
	 * @param colPos the column that the user has chosen for their turn
	 * 
	 * @return boolean: true if move was a valid spot on the board
	 */
	public boolean takeTurn(int rowPos, int colPos)
	{   
		boolean validTurn = false;
		/*Determines if the current spot is empty or not*/
		if(rowPos >= 0 && rowPos < tileBoard.length && colPos >= 0 &&
				colPos < tileBoard.length)
		{
			/*Checks to see if current position is not empty*/
			if(tileBoard[rowPos][colPos] != CollapsePiece.empty)
			{
				/*Determines if the tile chosen has a(n) adjacent tile(s)*/
				if(hasAdjacentTiles(rowPos, colPos))
				{
					//Removes cell and all adjacent tiles of the same color
					removeSelection(rowPos, colPos);
					//Shifts the necesssary cells downwards to fill in blank spots
					shiftCellsDownwards();
					//Shifts the columns to the center if necessary
					shiftColumnsToCenter();
					//Adds new columns
					
				}

				numMoves++;
				validTurn = true;
			}
		}

		return validTurn;
	}

	/**
	 * Getter method for the number of tiles left the player needs to clear
	 * 
	 * @return the number of tiles left the player needs to clear
	 */
	public int getTilesLeft()
	{
		int tilesLeft = 0;

		/*Iterates through all of the rows on the board*/
		for (int rowIter = 0; rowIter < tileBoard.length; rowIter++)
		{
			/*Iterates through all of the columns on the board*/
			for(int colIter = 0; colIter < tileBoard[0].length; colIter++)
			{
				/*Detrmines if the current column is not empty*/
				if(tileBoard[rowIter][colIter] != CollapsePiece.empty)
				{
					tilesLeft++;
				}
			}
		}
		return tilesLeft;
	}

	/**
	 * Getter method for the number of moves the player has made
	 * 
	 * @return the number of moves the player has made
	 */
	public int getNumberOfMoves()
	{
		return this.numMoves;
	}

	/**
	 * Checks to see if any of the adjacent tiles are of the same type
	 */
	protected boolean hasAdjacentTiles(int row, int col)
	{
		boolean hasAdjacentTiles = false;
		CollapsePiece color = tileBoard[row][col];

		/*Only checks for adjacent tiles if tile is not empty*/
		if(color != CollapsePiece.empty)
		{
			/*Looks above the current tile to see if it there is the same tile type*/
			if(row > 0 && tileBoard[row - 1][col] == color)
			{
				hasAdjacentTiles = true;
			}
			/*Looks below the current tile to see if it there is the same tile type*/
			if(row < tileBoard.length - 1 && tileBoard[row + 1][col] == color)
			{
				hasAdjacentTiles = true;
			}
			/*Looks right of the current tile to see if it there is the same tile type*/
			if(col > 0 && tileBoard[row][col - 1] == color)
			{
				hasAdjacentTiles = true;   
			}
			/*Looks left of the current tile to see if it there is the same tile type*/
			if(col < tileBoard[0].length - 1 && tileBoard[row][col + 1] == color)
			{
				hasAdjacentTiles = true;
			}
		}

		return hasAdjacentTiles;
	}



	/**
	 * Removes the block clicked along with any adjacent pieces.
	 */
	private void removeSelection(int rowPos, int colPos)
	{
		CollapsePiece curColor = tileBoard[rowPos][colPos];
		TileCoordinates curSpot;
		LinkedList<TileCoordinates> queue = new LinkedList<TileCoordinates>();
		queue.add(new TileCoordinates(rowPos, colPos));

		/*Keeps finding all of the adjacent tiles while there are unchecked tiles*/
		while(!queue.isEmpty())
		{
			curSpot = queue.remove();

			/*Add the below tile to the queue if it is the same color*/
			if(curSpot.getRowCoordinate() < tileBoard.length - 1 && tileBoard[
			                                                                  curSpot.getRowCoordinate() + 1][curSpot.getColumnCoordinate()]
			                                                                		  == curColor)
			{
				queue.add(new TileCoordinates(curSpot.getRowCoordinate() + 1,
						curSpot.getColumnCoordinate()));
			}
			/*Add the above tile to the queue if it is the same color*/
			if(curSpot.getRowCoordinate() > 0 && tileBoard[curSpot.getRowCoordinate()
			                                               - 1][curSpot.getColumnCoordinate()] == curColor)
			{
				queue.add(new TileCoordinates(curSpot.getRowCoordinate() - 1,
						curSpot.getColumnCoordinate()));
			}
			/*Add the tile to the right to the queue if it is the same color*/
			if(curSpot.getColumnCoordinate() < tileBoard[0].length - 1 &&
					tileBoard[curSpot.getRowCoordinate()][curSpot.getColumnCoordinate() + 1]
							== curColor)
			{
				queue.add(new TileCoordinates(curSpot.getRowCoordinate(),
						curSpot.getColumnCoordinate() + 1));
			}
			/*Add the tile to the left to the queue if it is the same color*/
			if(curSpot.getColumnCoordinate() > 0 && tileBoard[curSpot.getRowCoordinate()]
					[curSpot.getColumnCoordinate() - 1] == curColor)
			{
				queue.add(new TileCoordinates(curSpot.getRowCoordinate(),
						curSpot.getColumnCoordinate() - 1));
			}

			tileBoard[curSpot.getRowCoordinate()][curSpot.getColumnCoordinate()] =
					CollapsePiece.empty;
			characterBoard[curSpot.getRowCoordinate()][curSpot.getColumnCoordinate()] =
					' ';
		}
	}



	/**
	 * Shifts the remaining cells downwards if there is an open spot below them
	 */
	private void shiftCellsDownwards()
	{
		/*Iterates through each row on the board*/
		for(int rowIter = tileBoard.length - 2; rowIter >= 0; rowIter--)
		{
			/*Iterates through each column on the board*/
			for(int colIter = 0; colIter < tileBoard[0].length; colIter++)
			{
				//If the current position is not empty and has an empty spot
				//below it, then slide the tile down to correct position
				if(tileBoard[rowIter][colIter] != CollapsePiece.empty &&
						tileBoard[rowIter + 1][colIter] == CollapsePiece.empty)
				{
					int curSpot = rowIter + 1;

					/*Slides the tile down until it is above the tile or on the bottom*/
					while(curSpot < tileBoard.length - 1 && tileBoard
							[curSpot + 1][colIter] == CollapsePiece.empty)
					{
						curSpot++;
					}

					tileBoard[curSpot][colIter] = tileBoard[rowIter][colIter];
					tileBoard[rowIter][colIter] = CollapsePiece.empty;

					characterBoard[curSpot][colIter] = characterBoard[rowIter][colIter];
					characterBoard[rowIter][colIter] = ' ';
				}

			}
		}
	}
	private void shiftCellUp()
	{
		/*Iterates through each row on the board*/
		for(int rowIter = tileBoard.length - 2; rowIter >= 0; rowIter--)
		{
			/*Iterates through each column on the board*/
			for(int colIter = 0; colIter < tileBoard[0].length; colIter++)
			{
				//If the current position is not empty and has an empty spot
				//below it, then slide the tile down to correct position
				if(tileBoard[rowIter][colIter] != CollapsePiece.empty &&
						tileBoard[rowIter + 1][colIter] == CollapsePiece.empty)
				{
					int curSpot = rowIter - 1;

					/*Slides the tile down until it is above the tile or on the bottom*/
					while(curSpot < tileBoard.length - 1 && tileBoard
							[curSpot + 1][colIter] == CollapsePiece.empty)
					{
						curSpot--;
					}

					tileBoard[curSpot][colIter] = tileBoard[rowIter][colIter];
					tileBoard[rowIter][colIter] = CollapsePiece.empty;

					characterBoard[curSpot][colIter] = characterBoard[rowIter][colIter];
					characterBoard[rowIter][colIter] = ' ';
				}

			}
		}
	}

	/**
	 * Shfits the columns towards the center if there are any empty columns
	 */
	private void shiftColumnsToCenter()
	{
		int centerCol = boardSize / 2;

		/*Iterates through all of the columns left of the center*/
		for(int ndx = centerCol; ndx < boardSize - 1; ndx++)
		{
			shift(ndx, 1);
		}

		/*If the board is even sized the move the center left one*/
		if(boardSize % 2 == 0)
		{
			centerCol--;
		}

		//Shift the cells left of the center to the center, if needed
		for(int ndx = centerCol; ndx > 0; ndx--)
		{
			shift(ndx, -1);
		}
	}

	/**
	 * Helper method to shiftColumnsToCenter, shifts
	 * the current column in the specified direction
	 */
	private void shift(int ndx, int dir)
	{
		/*Determines if current column is empty*/
		if(columnIsEmpty(ndx))
		{
			int curCol = ndx + dir;

			/*Searches for closes non-empty row to center*/
			while(curCol >= 0 && curCol < boardSize && columnIsEmpty(curCol))
			{
				curCol = curCol + dir;
			}

			/*If there is a non-empty row, shift it*/ 
			if(curCol >= 0 && curCol < boardSize)
			{
				/*Shifts the column and makes the old spot empty*/
				for(int rowIter = 0; rowIter < tileBoard.length; rowIter++)
				{
					tileBoard[rowIter][ndx] = tileBoard[rowIter][curCol];
					tileBoard[rowIter][curCol] = CollapsePiece.empty;

					characterBoard[rowIter][ndx] = characterBoard[rowIter][curCol];
					characterBoard[rowIter][curCol] = ' ';
				}
			}
		}
	}


	/**
	 * Checks to see if a column is empty.
	 * 
	 * @param col the current column being checked for being empty
	 */
	private boolean columnIsEmpty(int column)
	{
		boolean isEmpty = true;

		/*Iterates through all of the rows*/
		for(int rowIter = 0; rowIter < tileBoard.length && isEmpty; rowIter++)
		{
			/*Determines if the current position is not empty*/
			if(tileBoard[rowIter][column] != CollapsePiece.empty)
			{
				isEmpty = false;
			}
		}

		return isEmpty;
	}

	/**
	 * Allows the player to cheat by removing the majority of the board
	 */
	public void cheat()
	{
		/*Iterates through all of the rows of the current board*/
		for (int rowIter = 0; rowIter < tileBoard.length; rowIter++)
		{
			/*Iterates through all of the columns of the current board*/
			for(int colIter = 0; colIter < tileBoard[0].length; colIter++)
			{          
				tileBoard[rowIter][colIter] = CollapsePiece.empty;
				characterBoard[rowIter][colIter] = ' ';
			}

			tileBoard[0][0] = CollapsePiece.green;
			characterBoard[0][0] = '+';
			tileBoard[0][1] = CollapsePiece.green;
			characterBoard[0][1] = '+';   
		}
	}
}