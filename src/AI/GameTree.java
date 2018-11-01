package AI;

import Core.Board;
import Core.Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameTree {

    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    //private int currentPossibleMoves[][];
    private Node parent;
    private int moveIndex;
    private final int TREE_DEPTH;

    public GameTree(int TREE_DEPTH){
        this.logic = new Logic();
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.parent = new Node(new int[2]);
        this.TREE_DEPTH = TREE_DEPTH;
        //currentPossibleMoves = new int[boardGrid.length][2];
    }

    public void createTreeLayer(Node root){
        this.boardGrid = board.getBoardGrid();
        //Node currentParent = new Node(new int[board.getSize()][board.getSize()]);
        Node currentChild = new Node(new int[board.getSize()][board.getSize()]);
        root.setData(boardGrid);
        //for(int depth = 0; depth < TREE_DEPTH; depth++) {
            for (int i = 0; i < boardGrid.length; i++) {
                for (int j = 0; j < boardGrid[0].length; j++) {
                    if (logic.checkSquareAllowed(i, j, board)) {
                        board.applyMove(i, j);
                        boardGrid = board.getBoardGrid();
                        currentChild.setData(boardGrid);
                        currentChild.setParent(root);
                    }
                }
            }
       // }
    }

    public Node createTree(){
        Node root = new Node(new int[board.getSize()][board.getSize()]);
        //List<Node<new int[board.getSize()][board.getSize()]>> children = new ArrayList<Node>(root.getChildren());
        createTreeLayer(root);
        for(int depth = 0; depth < TREE_DEPTH; depth++){
            for(int childNr = 0; childNr < root.getChildren().size(); childNr++){
                root = (Node) root.getChildren().get(childNr);
                //createTreeLayer((Node) root.getChildren().get(childNr));
                createTreeLayer(root);
            }
        }
        while(root.getParent()!= null){
            root = root.getParent();
        }
        return root;
    }

}
