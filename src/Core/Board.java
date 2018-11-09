package Core;

import java.io.Serializable;

public class Board implements Serializable {

    private int[][] boardGrid;
    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = -1;
    private int size;
    private int turn;
    private int currentPlayer;

    public Board() {
        this(8);
    }

    /**
     * Create board with starting discs in the middle
     * @param size height & width of board
     */
    public Board(int size) {
        size = (size < 4) ? 4: size; //enforces minimum size of 4
        size = ((size % 2) == 0) ? size : (size-1); //makes sure board is even
        this.size = size;
        boardGrid = new int[size][size];
        boardGrid[(size/2)-1][(size/2)-1] = BLACK;
        boardGrid[(size/2)-1][(size/2)] = WHITE;
        boardGrid[(size/2)][(size/2)-1] = WHITE;
        boardGrid[(size/2)][(size/2)] = BLACK;
        turn = 0;
        currentPlayer = BLACK;
    }

    public Board(Board board) { // properly "deep" copy a board
            this.size = board.getSize();
            this.turn = board.getTurn();
            this.currentPlayer = board.getCurrentPlayer();
            this.boardGrid = new int[size][size];
            for(int r = 0; r < size; r++) {
                for(int c = 0; c < size; c++) {
                    this.boardGrid[r][c] = board.getBoardGrid()[r][c];
                }
            }
    }

    /**
     * Prints out the board for diagnostic purposes
     */
    public void displayBoardGrid() {
        for(int r = 0; r < size; r++) {
            for(int c = 0; c < size; c++) {
                System.out.print(boardGrid[r][c] + " ");
                System.out.println();
            }
        }
    }

    public int[][] getBoardGrid()
    {
        return boardGrid;
    }

    public void setBoardGrid(int[][] boardGrid)
    {
        this.boardGrid = boardGrid;
    }

    public int getSize()
    {
        return size;
    }

    /**
     * Checks the state of integer array representing the board, if cell equals int state then increment counter
     * @return Number of counted squares
     */
    public int getNrSquares(int state)
    {
        int nrSquares = 0;

        for(int i = 0; i < boardGrid.length; i++)
            for(int j = 0; j < boardGrid[i].length; j++)
                if(boardGrid[i][j] == state)
                    nrSquares++;

        return  nrSquares;
    }

//    /**
//     * Checks the state of integer array representing the board, if cell contains 0 then this is an empty cell
//     * @return Number of squares that are empty
//     */
//    public int getNrEmptySquares()
//    {
//        int nrEmptySquares = 0;
//
//        for(int i = 0; i < boardGrid.length; i++)
//            for(int j = 0; j < boardGrid[i].length; j++)
//                if(boardGrid[i][j] == EMPTY)
//                    nrEmptySquares++;
//
//        return  nrEmptySquares;
//    }
//
//    /**
//     * Checks the state of integer array representing the board, if cell contains 1 then this is a black cell
//     * @return Number of squares that are black
//     */
//    public int getNrBlackSquares()
//    {
//        int nrBlackSquares = 0;
//
//        for(int i = 0; i < boardGrid.length; i++)
//            for(int j = 0; j < boardGrid[i].length; j++)
//                if(boardGrid[i][j] == BLACK)
//                    nrBlackSquares++;
//
//        return  nrBlackSquares;
//    }
//
//    /**
//     * Checks the state of integer array representing the board, if cell contains -1 then this is an white cell
//     * @return Number of squares that are white
//     */
//    public int getNrWhiteSquares()
//    {
//        int nrWhiteSquares = 0;
//
//        for(int i = 0; i < boardGrid.length; i++)
//            for(int j = 0; j < boardGrid[i].length; j++)
//                if(boardGrid[i][j] == WHITE)
//                    nrWhiteSquares++;
//
//        return  nrWhiteSquares;
//    }

    public void incrementTurn()
    {
        turn++;
    }

    public int getTurn()
    {
        return turn;
    }

    public void changePlayer()
    {
        if (currentPlayer == BLACK)
            currentPlayer = WHITE;
        else
            currentPlayer = BLACK;
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * Updates a specific cell based on the current status of the game, top left is 0, 0
     * @param row specifies row of cell we want to update
     * @param col specifies column of cell we want to update
     */
    public void applyMove(int row, int col) {
        int[][] flippedDisks = Logic.getFlippedDisks(row, col, this);
        boardGrid[row][col] = currentPlayer;
        for (int[] flippedDisk : flippedDisks)
            boardGrid[flippedDisk[0]][flippedDisk[1]] = currentPlayer;
    }

    public boolean checkTile(int r, int c, int state) {
        return (boardGrid[r][c] == state);
    }
}