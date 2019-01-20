package Core;

import java.io.Serializable;

public class Board implements Serializable {

    public int[][] boardGrid;
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
        this.boardGrid = new int[size][size];
        this.boardGrid[(size/2)-1][(size/2)-1] = BLACK;
        this.boardGrid[(size/2)-1][(size/2)] = WHITE;
        this.boardGrid[(size/2)][(size/2)-1] = WHITE;
        this.boardGrid[(size/2)][(size/2)] = BLACK;
        this.turn = 0;
        this.currentPlayer = BLACK;
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
                System.out.printf("%3d", boardGrid[r][c]);
            }
            System.out.println();
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
        for(int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[i].length; j++) {
                if (boardGrid[i][j] == state)
                    nrSquares++;
            }
        }

        return  nrSquares;
    }

    /**
     * Count amount of corners that have the value of state
     * @param state e.g. Black or White
     * @return amount of corners
     */
    public int getCorners(int state)
    {
        int nrCorners = 0;
        for (int i = 0; i < boardGrid.length; i += boardGrid.length- 1) {
            for (int j = 0; j < boardGrid.length; j += boardGrid.length - 1) {
                if (boardGrid[i][j] == state)
                    nrCorners++;
            }
        }

        return nrCorners;
    }

    public void incrementTurn()
    {
        turn++;
    }

    public int getTurn()
    {
        return turn;
    }

    public void changePlayer() {
        if (currentPlayer == BLACK)
            currentPlayer = WHITE;
        else
            currentPlayer = BLACK;
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(int player){
        currentPlayer = player;
    }

    public void setTurn(int turn) {turn = turn;}

    /**
     * Updates a specific cell based on the current status of the game, top left is 0, 0
     * @param row specifies row of cell we want to update
     * @param col specifies column of cell we want to update
     */
    public void applyMove(int row, int col) {
//        EvalFunc_FixedTerr evaluator = new EvalFunc_FixedTerr(this, getBoardGrid());
//        System.out.println(evaluator.evaluate());
        int[][] flippedDisks = Logic.getFlippedDisks(row, col, this);
        boardGrid[row][col] = currentPlayer;
        for (int[] flippedDisk : flippedDisks)
            boardGrid[flippedDisk[0]][flippedDisk[1]] = currentPlayer;
        applyMove();
    }

    /**
     * Adapter for new way of doing moves
     * @param move that needs to be applied to this board
     */
    public void applyMove(int[] move) {
        applyMove(move[0], move[1]);
    }

    public void applyMove()
    {
        incrementTurn();
        changePlayer();
    }

    /**
     * Checks if all values in board matrix and current players are the same
     * @param parent board that this board needs to be compared with
     * @return if this and parent board sre same
     */
    public boolean isSameBoard(Board parent) {
        boolean sameBoard = true;
        int[][] parentBoardGrid = parent.getBoardGrid();
        int[][] currentBoardGrid = this.getBoardGrid();
        if(currentPlayer != parent.getCurrentPlayer()) {
            sameBoard = false;
        } else {
            for(int r = 0; r < parentBoardGrid.length; r++) {
                for(int c = 0; c < parentBoardGrid.length; c++) {
                    if(parentBoardGrid[r][c] != currentBoardGrid[r][c]) {
                        sameBoard = false;
                        break;
                    }
                }
            }
        }
        return sameBoard;
    }

    /**
     * Finds different in board with regards to row ccmpared to parent
     * @return row, where board and parent's board differ
     */
    public int getRow(Board parent) {
        int row = -1; //makes sure we throw an error if we have not updated our coordinate
        int[][] parentBoardGrid = parent.getBoardGrid();
        int[][] currentBoardGrid = this.getBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == EMPTY && currentBoardGrid[r][c] != EMPTY) row = r;
            }
        }
        return row;
    }

    /**
     * Finds different in board with regards to column ccmpared to parent
     * @return column, where board and parent's board differ
     */
    public int getColumn(Board parent) {
        int column = -1; //makes sure we throw an error if we have not updated our coordinate
        int[][] parentBoardGrid = parent.getBoardGrid();
        int[][] currentBoardGrid = this.getBoardGrid();
        for(int r = 0; r < parentBoardGrid.length; r++) {
            for(int c = 0; c < parentBoardGrid.length; c++) {
                if(parentBoardGrid[r][c] == EMPTY && currentBoardGrid[r][c] != EMPTY) column = c;
            }
        }
        return column;
    }



    public boolean checkTile(int r, int c, int state) {
        return (boardGrid[r][c] == state);
    }
}