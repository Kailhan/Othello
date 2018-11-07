package AI;

import Core.*;
import java.util.*;

public class TreeSearch {



    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    private int currentPossibleMoves[][];
    private Node parent;
    private int moveIndex;
    private final static int BLACK = 1;
    private final static int WHITE = -1;
    private EvaluationFunction evalutor;



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
    
    //is not working, but is a beginning
    public int minimaxMethod(Board board, int depth){
        int score = Integer.MIN_VALUE;

        if(depth<=0){
            score = evalutor.evaluate();
        } else{
            board.changePlayer();
            int oppositePlayer = board.getCurrentPlayer();
            int result = -minimaxMethod(board, depth-1);
            if(result > score){
                score = result;
            }
        }
        return score;
    }

    public int valueMax(Node parent){
        List listChildren = parent.getChildren();
        int maxValue = (int)listChildren.get(0);
        for(int i = 0; i<listChildren.size(); i++){
            if((int)listChildren.get(i)>maxValue) maxValue=(int)listChildren.get(i);
        }
        return maxValue;
    }

    public int valueMin(Node parent){
        List listChildren = parent.getChildren();
        int minValue = (int)listChildren.get(0);
        for(int i = 0; i<listChildren.size(); i++){
            if((int)listChildren.get(i)<minValue) minValue=(int)listChildren.get(i);
        }
        return minValue;
    }
}




/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
 */
