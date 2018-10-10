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