package Core;

import java.net.SocketTimeoutException;


public class Logic {

    public static boolean checkSquareAllowed(int x, int y, Board board, int playerNr) //Returns true if the position clicked would result in disks being flipped
    {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();


        if (boardGrid[x][y] == 0) //If the square is empty
        {
            if (x > 1  && x < boardSize - 2 && y > 1 && y < boardSize - 2) { //middle
                if(getFlippedDisksDirection(x, y, board, playerNr, "N").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "E").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "S").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SW").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "W").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NW").length > 0)

                    return true;
            }
            else if (x <= 1 && y > 1 && y < boardSize - 2) { //left edge
                if(getFlippedDisksDirection(x, y, board, playerNr, "N").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "E").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "S").length > 0)
                    return true;
            }
            else if (x >= boardSize - 2 && y > 1 && y < boardSize - 2) { //right edge
                if(getFlippedDisksDirection(x, y, board, playerNr, "N").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "S").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SW").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "W").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NW").length > 0)
                    return true;
            }
            else if (x > 1 && x < boardSize - 2 && y <= 1) { //top edge
                if(getFlippedDisksDirection(x, y, board, playerNr, "E").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "S").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SW").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "W").length > 0)
                    return true;
            }
            else if (x > 1 && x < boardSize - 2 && y >= boardSize - 2) { //bottom edge
                if(getFlippedDisksDirection(x, y, board, playerNr, "N").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "E").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "W").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NW").length > 0)
                    return true;
            }
            else if (x <= 1 && y <= 1) { //top left corner
                if(getFlippedDisksDirection(x, y, board, playerNr, "E").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "S").length > 0)
                    return true;
            }
            else if (x <= 1 && y >= boardSize - 2) { //bottom left corner
                if(getFlippedDisksDirection(x, y, board, playerNr, "N").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NE").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "E").length > 0)
                    return true;
            }
            else if (x >= boardSize - 2 && y <= 1) { //top right corner
                if(getFlippedDisksDirection(x, y, board, playerNr, "S").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "SW").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "W").length > 0)
                    return true;
            }
            else if (x >= boardSize - 2 && y >= boardSize - 2) { //bottom right corner
                if(getFlippedDisksDirection(x, y, board, playerNr, "N").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "W").length > 0 ||
                        getFlippedDisksDirection(x, y, board, playerNr, "NW").length > 0)
                    return true;
            }
        }
        return false;
    }

    public static int[][] getFlippedDisks(int x, int y, Board board, int playerNr) //Returns list of coordinates of disks that would be flipped
    {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();

        int[][] flippedDisks = new int[0][2];

        if (boardGrid[x][y] == 0) //If the square is empty
        {
            if (x > 1  && x < boardSize - 2 && y > 1 && y < boardSize - 2) { //middle
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "N"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "E"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "S"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SW"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "W"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NW"));

                return flippedDisks;
            }
            else if (x <= 1 && y > 1 && y < boardSize - 2) { //left edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "N"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "E"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "S"));

                return flippedDisks;
            }
            else if (x >= boardSize - 2 && y > 1 && y < boardSize - 2) { //right edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "N"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "S"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SW"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "W"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NW"));

                return flippedDisks;
            }
            else if (x > 1 && x < boardSize - 2 && y <= 1) { //top edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "E"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "S"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SW"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "W"));

                return flippedDisks;
            }
            else if (x > 1 && x < boardSize - 2 && y >= boardSize - 2) { //bottom edge
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "N"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "E"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "W"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NW"));

                return flippedDisks;
            }
            else if (x <= 1 && y <= 1) { //top left corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "E"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "S"));
                    return flippedDisks;
            }
            else if (x <= 1 && y >= boardSize - 2) { //bottom left corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "N"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NE"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "E"));

                return flippedDisks;
            }
            else if (x >= boardSize - 2 && y <= 1) { //top right corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "S"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "SW"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "W"));

                return flippedDisks;
            }
            else if (x >= boardSize - 2 && y >= boardSize - 2) { //bottom right corner
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "N"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "W"));
                flippedDisks = combine(flippedDisks, getFlippedDisksDirection(x, y, board, playerNr, "NW"));

                return flippedDisks;
            }
        }
        return new int[0][0];
    }

    public static int[][] getFlippedDisksDirection(int x, int y, Board board, int playerNr, String direction) //Returns list of coordinates of disks in a certain direction that would be flipped
    {
        int[][] boardGrid = board.getBoardGrid();
        int boardSize = board.getSize();

        boolean foundEnemy = false;
        int[][] flippedDisks = new int[0][2];

        switch(direction)
        {
            case "N" :
                for(int i = 1; y - i >= 0; i++)
                    {
                        if(boardGrid[x][y - i] == 0)
                        {
                            return new int[0][2];
                        }
                        else if(boardGrid[x][y - i] == playerNr * -1)
                        {
                            foundEnemy = true;
                            int[][] enemyCoordinate = new int[][]{{x, y - i}};
                            flippedDisks = combine(flippedDisks, enemyCoordinate);
                        }
                        else if (boardGrid[x][y - i] == playerNr)
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
            case "NE" :
                for(int i = 1; y - i >= 0 && x + i < boardSize; i++)
                {
                    if(boardGrid[x + i][y - i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x + i][y - i] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x + i, y - i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x + i][y - i] == playerNr)
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
            case "E" :
                for(int i = 1; x + i < boardSize; i++)
                {
                    if(boardGrid[x + i][y] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x + i][y] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x + i, y}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x + i][y] == playerNr)
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
            case "SE" :
                for(int i = 1; y + i < boardSize && x + i < boardSize; i++)
                {
                    if(boardGrid[x + i][y + i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x + i][y + i] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x + i, y + i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x + i][y + i] == playerNr)
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
            case "S" :
                for(int i = 1; y + i < boardSize; i++)
                {
                    if(boardGrid[x][y + i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x][y + i] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x, y + i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x][y + i] == playerNr)
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
            case "SW" :
                for(int i = 1; y + i < boardSize && x - i >= 0; i++)
                {
                    if(boardGrid[x - i][y + i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x - i][y + i] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x - i, y + i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x - i][y + i] == playerNr)
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
            case "W" :
                for(int i = 1; x - i >= 0; i++)
                {
                    if(boardGrid[x - i][y] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x - i][y] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x - i, y}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x - i][y] == playerNr)
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
            case "NW" :
                for(int i = 1; y - i >= 0 && x - i >= 0; i++)
                {
                    if(boardGrid[x - i][y - i] == 0)
                    {
                        return new int[0][0];
                    }
                    else if(boardGrid[x - i][y - i] == playerNr * -1)
                    {
                        foundEnemy = true;
                        int[][] enemyCoordinate = new int[][]{{x - i, y - i}};
                        flippedDisks = combine(flippedDisks, enemyCoordinate);
                    }
                    else if (boardGrid[x - i][y - i] == playerNr)
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

    //counts the amount of possible moves
    public static int numberSquaresAllowed(Board board)
    {
        int boardSize = board.getSize();
        int nrOfAllowedSquares = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (checkSquareAllowed(i, j, board, board.getCurrentPlayer())) {
                    nrOfAllowedSquares++;
                }
            }
        }
        return nrOfAllowedSquares;
    }

    //count the amount of possible moves with manual option to choose the player
    public static int numberSquaresAllowed(Board board, int player)
    {
        int boardSize = board.getSize();
        int nrOfAllowedSquares = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (checkSquareAllowed(i, j, board, player)) {
                    nrOfAllowedSquares++;
                }
            }
        }
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