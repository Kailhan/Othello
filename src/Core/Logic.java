package Core;

public class Logic {

    static int north = 0;
    static int northEast = 1;
    static int east = 2;
    static int southEast = 3;
    static int south = 4;
    static int southWest = 5;
    static int west = 6;
    static int northWest = 7;

    public static boolean checkSquareAllowed(int r, int c, Board board) //Returns true if the position clicked would result in disks being flipped
    {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();

        if (boardGrid[r][c] == 0) //If the square is empty
        {
            if (r > 1  && r < boardSize - 2 && c > 1 && c < boardSize - 2) { //middle
                return getFlippedDisksDirection(r, c, board, east).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southEast).length > 0 ||
                        getFlippedDisksDirection(r, c, board, south).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, west).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, north).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northEast).length > 0;
            }
            else if (r <= 1 && c > 1 && c < boardSize - 2) { //left edge
                return getFlippedDisksDirection(r, c, board, east).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southEast).length > 0 ||
                        getFlippedDisksDirection(r, c, board, south).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, west).length > 0;
            }
            else if (r >= boardSize - 2 && c > 1 && c < boardSize - 2) { //right edge
                return getFlippedDisksDirection(r, c, board, east).length > 0 ||
                        getFlippedDisksDirection(r, c, board, west).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, north).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northEast).length > 0;
            }
            else if (r > 1 && r < boardSize - 2 && c <= 1) { //top edge
                return getFlippedDisksDirection(r, c, board, south).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, west).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, north).length > 0;
            }
            else if (r > 1 && r < boardSize - 2 && c >= boardSize - 2) { //bottom edge
                return getFlippedDisksDirection(r, c, board, east).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southEast).length > 0 ||
                        getFlippedDisksDirection(r, c, board, south).length > 0 ||
                        getFlippedDisksDirection(r, c, board, north).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northEast).length > 0;
            }
            else if (r <= 1 && c <= 1) { //top left corner
                return getFlippedDisksDirection(r, c, board, south).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, west).length > 0;
            }
            else if (r <= 1 && c >= boardSize - 2) { //bottom left corner
                return getFlippedDisksDirection(r, c, board, east).length > 0 ||
                        getFlippedDisksDirection(r, c, board, southEast).length > 0 ||
                        getFlippedDisksDirection(r, c, board, south).length > 0;
            }
            else if (r >= boardSize - 2 && c <= 1) { //top right corner
                return getFlippedDisksDirection(r, c, board, west).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northWest).length > 0 ||
                        getFlippedDisksDirection(r, c, board, north).length > 0;
            }
            else if (r >= boardSize - 2 && c >= boardSize - 2) { //bottom right corner
                return getFlippedDisksDirection(r, c, board, east).length > 0 ||
                        getFlippedDisksDirection(r, c, board, north).length > 0 ||
                        getFlippedDisksDirection(r, c, board, northEast).length > 0;
            }
        }
        return false;
    }

    public static int[][] getFlippedDisks(int r, int c, Board board) //Returns list of coordinates of disks that would be flipped
    {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();

        int[][] flippedDisks = new int[0][2];

        if (boardGrid[r][c] == 0) //If the square is empty
        {
            if (r > 1  && r < boardSize - 2 && c > 1 && c < boardSize - 2) { //middle
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, east));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southEast));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, south));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, west));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, north));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northEast));

                return flippedDisks;
            }
            else if (r <= 1 && c > 1 && c < boardSize - 2) { //left edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, east));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southEast));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, south));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, west));

                return flippedDisks;
            }
            else if (r >= boardSize - 2 && c > 1 && c < boardSize - 2) { //right edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, east));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, west));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, north));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northEast));

                return flippedDisks;
            }
            else if (r > 1 && r < boardSize - 2 && c <= 1) { //top edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, south));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, west));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, north));

                return flippedDisks;
            }
            else if (r > 1 && r < boardSize - 2 && c >= boardSize - 2) { //bottom edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, east));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southEast));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, south));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, north));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northEast));

                return flippedDisks;
            }
            else if (r <= 1 && c <= 1) { //top left corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, south));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, west));
                return flippedDisks;
            }
            else if (r <= 1 && c >= boardSize - 2) { //bottom left corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, east));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, southEast));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, south));

                return flippedDisks;
            }
            else if (r >= boardSize - 2 && c <= 1) { //top right corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, west));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northWest));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, north));

                return flippedDisks;
            }
            else if (r >= boardSize - 2 && c >= boardSize - 2) { //bottom right corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, east));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, north));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(r, c, board, northEast));

                return flippedDisks;
            }
        }
        return new int[0][0];
    }

    public static int[][] getFlippedDisksDirection(int r, int c, Board board, int direction) //Returns list of coordinates of disks in a certain direction that would be flipped
    {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();
        int currentPlayer = board.getCurrentPlayer();

        boolean foundEnemy = false;
        int[][] flippedDisks = new int[0][2];

        switch(direction)
        {
            case 2 :
                for(int i = 1; c - i >= 0; i++)
                    {
                        if(boardGrid[r][c - i] == 0){
                            return new int[0][2];
                        }
                        else if(boardGrid[r][c - i] != currentPlayer)
                        {
                            foundEnemy = true;
                            int[][] enemyCoordinate = new int[][]{{r, c - i}};
                            flippedDisks = combine(flippedDisks, enemyCoordinate);
                        }
                        else if (boardGrid[r][c - i] == currentPlayer)
                        {
                            if(foundEnemy)
                            {
                                return flippedDisks;
                            }
                            else
                            {
                                return new int[0][0];
                            }
                        }
                    }
                return new int[0][0];
            case 3 :
                for(int i = 1; c - i >= 0 && r + i < boardSize; i++)
                {
                    if(boardGrid[r + i][c - i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r + i][c - i] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r + i, c - i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r + i][c - i] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];
            case 4 :
                for(int i = 1; r + i < boardSize; i++)
                {
                    if(boardGrid[r + i][c] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r + i][c] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r + i, c}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r + i][c] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];
            case 5 :
                for(int i = 1; c + i < boardSize && r + i < boardSize; i++)
                {
                    if(boardGrid[r + i][c + i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r + i][c + i] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r + i, c + i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r + i][c + i] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];
            case 6 :
                for(int i = 1; c + i < boardSize; i++)
                {
                    if(boardGrid[r][c + i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r][c + i] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r, c + i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r][c + i] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];
            case 7 :
                for(int i = 1; c + i < boardSize && r - i >= 0; i++)
                {
                    if(boardGrid[r - i][c + i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r - i][c + i] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r - i, c + i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r - i][c + i] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];
            case 0 :
                for(int i = 1; r - i >= 0; i++)
                {
                    if(boardGrid[r - i][c] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r - i][c] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r - i, c}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r - i][c] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];
            case 1 :
                for(int i = 1; c - i >= 0 && r - i >= 0; i++)
                {
                    if(boardGrid[r - i][c - i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[r - i][c - i] != currentPlayer)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{r - i, c - i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[r - i][c - i] == currentPlayer)
                    {
                        if(foundEnemy)
                        {
                            return flippedDisks;
                        }
                        else
                        {
                            return new int[0][0];
                        }
                    }
                }
                return new int[0][0];

            default:

        }
        return new int[0][0];
    }

    public static int[][] getPossibleMoves(Board board)
    {
        int[][] possibleMoves = new int[0][2];
        int boardSize = board.getSize();
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (checkSquareAllowed(i, j, board))
                    possibleMoves = combine(possibleMoves, new int[][]{{i, j}});
        return possibleMoves;
    }

    public static boolean checkMovePossible(Board board)
    {
        int boardSize = board.getSize();
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (checkSquareAllowed(i, j, board)) return true;
        return false;
    }

    //counts the amount of possible moves
    public static int numberSquaresAllowed(Board board)
    {
        int boardSize = board.getSize();
        int nrOfAllowedSquares = 0;
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (checkSquareAllowed(i, j, board))
                    nrOfAllowedSquares++;
        return nrOfAllowedSquares;
    }

    public static int[][] combine(int[][] a, int[][] b)
    {
        int length = a.length + b.length;
        int[][] result = new int[length][2];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}