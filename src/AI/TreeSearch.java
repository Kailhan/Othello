package AI;

import Core.*;

public class TreeSearch {

    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    private int currentPossibleMoves[][];
    private Node parent;
    private int moveIndex;

    public TreeSearch(){
        this.logic = new Logic();
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.parent = new Node(new int[2]);
        currentPossibleMoves = new int[boardGrid.length][2];

    }

    //black is max
    //white is min
    //Look at which factor for the range (now sitting at 100 to -100) can increase/decrease;
    //to do: finnish functions in stability and do the moves functions...
    public int evaluationFunction(){
        int totalscore = 0;
        int corners;
        int stableScore;

        int numberOfCoins =  100 * (board.getNrBlackSquares() - board.getNrWhiteSquares()) / (board.getNrBlackSquares() + board.getNrWhiteSquares());
        int numberOfMoves =  100 * 6;//amount of moves



        if(board.getStabilityBlack() + board.getStabilityWhite() != 0) {
            stableScore = 100 * (board.getStabilityBlack() - board.getStabilityWhite()) / (board.getStabilityBlack() + board.getStabilityWhite());
        }
        else stableScore = 0;


        if(board.getBlackCorners() + board.getNrWhiteSquares() != 0) {
            corners = 100 * (board.getBlackCorners() - board.getNrWhiteSquares()) / (board.getBlackCorners() + board.getNrWhiteSquares());
        }
        else corners = 0;

        return totalscore = numberOfCoins + corners + numberOfMoves + stableScore;
    }

    public void createTree(){
        this.boardGrid = board.getBoardGrid();
        for(int i = 0; i < boardGrid.length; i++){
            for(int j = 0; j < boardGrid[0].length; j++){
                if(logic.checkSquareAllowed(i,j,board,1)) {
                    currentPossibleMoves[moveIndex][0] = i;
                    currentPossibleMoves[moveIndex][1] = j;
                }
            }
        }

        for(int i = 0; i < moveIndex; i++){
            Node currentNode = new Node(new int[2]);
            currentNode.setData(currentPossibleMoves[moveIndex]);
            currentNode.setParent(parent);
        }


    }

}

/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
 */