package AI;

import Core.*;

public class Minimax {

    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    private int currentPossibleMoves[][];
    private Node parent;
    private int moveIndex;
    private int currentTreeLevel;
    private final int TREE_DEPTH;

    public Minimax(int TREE_DEPTH){
        this.TREE_DEPTH = TREE_DEPTH;
        this.logic = new Logic();
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.parent = new Node(new int[2]);
        currentPossibleMoves = new int[boardGrid.length][2];

    }

    public void createTree(){
        this.boardGrid = board.getBoardGrid();

        for(int depth = 0; depth < TREE_DEPTH; depth++) {
            for (int i = 0; i < boardGrid.length; i++) {
                for (int j = 0; j < boardGrid[0].length; j++) {
                    if (logic.checkSquareAllowed(i, j, board, currentTreeLevel % 2)) {
                        currentPossibleMoves[moveIndex][0] = i;
                        currentPossibleMoves[moveIndex][1] = j;
                        if (currentTreeLevel % 2 == 0) boardGrid[i][j] = 1;
                        else boardGrid[i][j] = -1;
                    }
                }
            }

            for (int i = 0; i < moveIndex; i++) {
                Node currentNode = new Node(new int[2]);
                currentNode.setData(currentPossibleMoves[moveIndex]);
                currentNode.setParent(parent);
                for(children: parent.getChildren())
            }

        }

        board.displayBoardGrid();


    }

}

/*
A list of possible moves for each turn needs to be created using Core.Logic.checkSquareAllowed
Then for each possible move we create a simulated board from which we create a new list of possible moves
 */