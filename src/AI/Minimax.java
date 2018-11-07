package AI;
import java.util.*;

import Core.*;

public class Minimax{

    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    private int currentPossibleMoves[][];
    private Node parent;
    private int moveIndex;

    public Minimax(){
        this.logic = new Logic();
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.parent = new Node(new int[2]);
        currentPossibleMoves = new int[boardGrid.length][2];
    }

    public void createTree(){
        this.boardGrid = board.getBoardGrid();
        for(int i = 0; i < boardGrid.length; i++){
            for(int j = 0; j < boardGrid[0].length; j++){
                if(logic.checkSquareAllowed(i,j,board)) {
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

    public void minimaxMethod(Node parent){
        List listChildren = parent.getChildren();
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