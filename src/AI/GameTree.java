package AI;

import Core.Board;
import Core.Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameTree {

    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    //private int currentPossibleMoves[][];
    private Node parent;
    private int moveIndex;
    private int treeDepth;


    public GameTree(int treeDepth, Board board) {
        this.logic = new Logic();
        this.board = new Board();
        this.boardGrid = board.getBoardGrid();
        this.parent = new Node(new int[2]);
        this.treeDepth = treeDepth;
        //currentPossibleMoves = new int[boardGrid.length][2];
    }

    public GameTree(int treeDepth){
        this(treeDepth, new Board());
    }

    public void createTreeLayer(Node root){
        this.boardGrid = board.getBoardGrid();
        //Node currentParent = new Node(new int[board.getSize()][board.getSize()]);
        Node currentChild = new Node(new int[board.getSize()][board.getSize()]);
        root.setData(boardGrid);
        for(int depth = 0; depth < treeDepth; depth++) {
            for (int i = 0; i < boardGrid.length; i++) {
                for (int j = 0; j < boardGrid[0].length; j++) {
                    if (logic.checkSquareAllowed(i, j, board)) {
                        Board tmpBoard = new Board(board);
                        tmpBoard.applyMove(i, j);
                        int[][] boardGrid = tmpBoard.getBoardGrid();
                        currentChild.setData(boardGrid);
                        currentChild.setParent(root);
                    }
                }
            }
            board.changePlayer();
        }
    }

    public Node createTree(){
        Node root = new Node(new int[board.getSize()][board.getSize()]);
        //List<Node<new int[board.getSize()][board.getSize()]>> children = new ArrayList<Node>(root.getChildren());
        createTreeLayer(root);
        for(int depth = 0; depth < treeDepth; depth++){
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

    public static void displayGrid(int[][] boardGrid){
        for(int i = 0; i < boardGrid.length; i++){
            for(int j = 0; j < boardGrid[0].length; j++){
                System.out.printf("%2d", boardGrid[i][j] + " ");
            }
            System.out.println();
        }
    }

//    public void displayTree(Node root) {
//        Queue<Node> nodes = new LinkedList<Node>();
//        nodes.add(root.getChildren());
//        nodes.add(nodes.remove().getChildren());
//    }

}
