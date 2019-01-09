package AI;

import Core.Board;
import Core.Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * The tree data structure which contains simulated boards, constructed layer by layer so that a specified depth can be used
 */

public class GameTree {

    private Logic logic;
    private Board board;
    private int[][] boardGrid;
    private int treeDepth;
    private Minimax minimax;

    public GameTree(int treeDepth, Board board) {
        this.logic = new Logic();
        this.board = board;
        this.boardGrid = board.getBoardGrid();
        this.treeDepth = treeDepth;
    }

    public GameTree(int treeDepth){
        this(treeDepth, new Board());
    }

    /**
     Constructs one increment of a new layer of nodes by adding the children of a parent in the layer above to this new layer
     The child nodes are created by applying moves to their parents' board and storing the resulting board
     */

    public ArrayList<Node<Board>> createLayerIncrement(Node<Board> root, ArrayList<Node<Board>> currentLayer){
            Board rootBoard = root.getData();
            for (int i = 0; i < boardGrid.length; i++) {
                for (int j = 0; j < boardGrid[0].length; j++) {
                    if (Logic.checkSquareAllowed(i, j, rootBoard)) {
                        Board tmpBoard = new Board(rootBoard); //makes use of the deep-copy method in board so that the child and parents' board are distinct objects
                        tmpBoard.applyMove(i, j);
                        tmpBoard.changePlayer();
                        Node currentChild = new Node(tmpBoard);
                        root.addChild(currentChild);
                        currentLayer.add(currentChild);
                    }
                }
            }
            return currentLayer;
    }

    /**
     * Creates the tree layer by layer by calling createTreeLayer() for every node in the current layer
     */

    public Node<Board> createTree(){
        Node<Board> root = new Node(board);
        ArrayList<Node<Board>> currentLayer = new ArrayList<>();
        ArrayList<Node<Board>> newLayer = new ArrayList<>();
        currentLayer = createLayerIncrement(root, currentLayer); //creating the first layer below the root
        for(int depth = 1; depth < treeDepth; depth++){
            for(Node<Board> currentRoot: currentLayer){      //incrementally constructing the new layer
                createLayerIncrement(currentRoot, newLayer); //by adding the children of all parents in this layer to the new layer
            }
            currentLayer = new ArrayList<>(newLayer);
            newLayer.clear(); //clearing ArrayList so that it can be used for the next layer
        }

        Node<Board> currentNode = root;
        for (Node<Board> child : currentNode.getChildren()) {
           child.getData().getCurrentPlayer();
        }
        return root;
    }

    public void displayGrid(Board board){
        boardGrid = board.getBoardGrid();
        String representation = "";
        for(int i = 0; i < boardGrid.length; i++){
            for(int j = 0; j < boardGrid[0].length; j++){
                //System.out.printf("%2d", boardGrid[i][j] + " ");
                if(boardGrid[i][j] == 0) representation = "0";
                if(boardGrid[i][j] == 1) representation = "1";
                if(boardGrid[i][j] == -1) representation = "2";
                System.out.print(representation + " ");
            }
            System.out.println();
        }
    }

    public void displayChildNodes(Node<Board> root){
        List<Node<Board>> currentLayer;
        currentLayer = root.getChildren();
        ArrayList<Node<Board>> newLayer = new ArrayList<>();
        //System.out.println("Layer: " + findLayerIndex(root));
        for(Node<Board> currentChild: currentLayer){
            System.out.println();
            displayGrid(currentChild.getData());
            newLayer.add(currentChild);
        }
        for(Node<Board> newChild: newLayer){
            displayChildNodes(newChild);
        }
    }


    public int getTreeDepth() {
        return treeDepth;
    }
}
