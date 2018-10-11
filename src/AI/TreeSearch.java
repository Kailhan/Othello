package AI;

import Core.*;

public class TreeSearch {

    private final static int WEIGHT1 = 100;
    private final static int WEIGHT2 = 100;
    private final static int WEIGHT3 = 100;
    private final static int WEIGHT4 = 100;
    private final static int BLACK = 1;
    private final static int WHITE = -1;

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


    //Look at the weights, e.g. number of moves should be as low as possible.
    //to do: finnish functions in stability
    //Maybe make room for opening moves (set of the best opening moves)

    public int evaluationFunction(){
        int totalscore = 0;
        int numberOfCorners;
        int stableScore;
        int numberOfMoves;

        int numberOfCoins =  ((board.getNrBlackSquares() - board.getNrWhiteSquares()) / (board.getNrBlackSquares() + board.getNrWhiteSquares()));

        if(Logic.numberSquaresAllowed(this.board, BLACK) + Logic.numberSquaresAllowed(this.board, WHITE) != 0){
            numberOfMoves =  ((Logic.numberSquaresAllowed(this.board, BLACK) - Logic.numberSquaresAllowed(this.board, WHITE)) / (Logic.numberSquaresAllowed(this.board, BLACK) + Logic.numberSquaresAllowed(this.board, WHITE)));
        }
        else numberOfMoves = 0;

        if(board.getBlackCorners() + board.getNrWhiteSquares() != 0) {
            numberOfCorners = ((board.getBlackCorners() - board.getNrWhiteSquares()) / (board.getBlackCorners() + board.getNrWhiteSquares()));
        }
        else numberOfCorners = 0;

        if(board.getStabilityBlack() + board.getStabilityWhite() != 0) {
            stableScore =  ((board.getStabilityBlack() - board.getStabilityWhite()) / (board.getStabilityBlack() + board.getStabilityWhite()));
        }
        else stableScore = 0;

        return totalscore = WEIGHT1 * numberOfCoins + WEIGHT2 * numberOfCorners + WEIGHT3 * numberOfMoves /*+ WEIGHT4 * stableScore*/;
        //to be checked if its the correct way to add scores. .
    }

    public void createTree(){
        this.boardGrid = board.getBoardGrid();
        for(int i = 0; i < boardGrid.length; i++){
            for(int j = 0; j < boardGrid[0].length; j++){
                if(logic.checkSquareAllowed(i,j,board,BLACK)) {
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