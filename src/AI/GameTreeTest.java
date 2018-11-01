package AI;

import Core.Board;

public class GameTreeTest {




    public static void main(String[] args){

        Board board = new Board(8);
        GameTree T = new GameTree(3);
        Node root = new Node(new int[board.getSize()][board.getSize()]);
        root = T.createTree();
        int[][] boardGrid = new int[8][8];
        boardGrid = (int[][]) root.getData();
        displayGrid(boardGrid);

    }

    public static void displayGrid(int[][] boardGrid){
        for(int i = 0; i < boardGrid.length; i++){
            for(int j = 0; j < boardGrid[0].length; j++){
                System.out.print(boardGrid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
