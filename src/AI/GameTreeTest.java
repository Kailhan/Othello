package AI;

import Core.Board;

public class GameTreeTest {

    public static void main(String[] args){

        Board board = new Board(8);
        GameTree T = new GameTree(1);
        Node root = new Node(new int[board.getSize()][board.getSize()]);
        root = T.createTree();
        int[][] boardGrid = new int[8][8];
        boardGrid = (int[][]) root.getData();
        //displayGrid(boardGrid);
        //T.displayTree(root);
    }


}
