public class Board {

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

    public int getNrEmptySquares()
    {
        int nrEmptySquares = 0;

        for(int i = 0; i < boardGrid.length; i++)
            for(int j = 0; j < boardGrid[i].length; j++)
                if(boardGrid[i][j] == EMPTY)
                    nrEmptySquares++;

        return  nrEmptySquares;
    }

    public int getNrBlackSquares()
    {
        int nrBlackSquares = 0;

        for(int i = 0; i < boardGrid.length; i++)
            for(int j = 0; j < boardGrid[i].length; j++)
                if(boardGrid[i][j] == BLACK)
                    nrBlackSquares++;

        return  nrBlackSquares;
    }

    public int getNrWhiteSquares()
    {
        int nrWhiteSquares = 0;

        for(int i = 0; i < boardGrid.length; i++)
            for(int j = 0; j < boardGrid[i].length; j++)
                if(boardGrid[i][j] == WHITE)
                    nrWhiteSquares++;

        return  nrWhiteSquares;
    }

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
        {
            currentPlayer = WHITE;
        }
        else
        {
            currentPlayer = BLACK;
        }
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    //disk flips or placed in an empty square
    public void applyMove(int x, int y)
    {
        if(boardGrid[x][y] == 0 && Logic.checkSquareAllowed(x, y, this, currentPlayer))
        {
            int[][] flippedDisks = Logic.getFlippedDisks(x, y, this, currentPlayer);

            if(currentPlayer == BLACK)
            {
                boardGrid[x][y] = BLACK;
                for(int i = 0; i < flippedDisks.length; i++)
                {
                    boardGrid[flippedDisks[i][0]][flippedDisks[i][1]] = BLACK;
                }
            }
            else
            {
                boardGrid[x][y] = WHITE;
                for(int i = 0; i < flippedDisks.length; i++)
                {
                    boardGrid[flippedDisks[i][0]][flippedDisks[i][1]] = WHITE;
                }
            }
            changePlayer();

//        if(Logic.checkSquareAllowed(x, y, this, turn))
//        {
//            if(boardGrid[x][y] == 0)
//            {
//                if(currentPlayer == BLACK)
//                {
//
//                }
//            }
//            if(boardGrid[xCoord][yCoord] == 0 && currentPlayer == BLACK) boardGrid[xCoord][yCoord] = BLACK; //if the square is empty or white and it's black's turn, put a black disc in the square
//            if(boardGrid[xCoord][yCoord] == 0 && currentPlayer == WHITE) boardGrid[xCoord][yCoord] = WHITE; //if the square is empty or black and it's white's turn, put a white disc in the square
        }
        else
        {
            System.out.println("Move not allowed");
        }
        incrementTurn();
    }
}