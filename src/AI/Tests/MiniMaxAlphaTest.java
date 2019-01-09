package AI.Tests;

import AI.*;
import Core.Board;

public class MiniMaxAlphaTest {

    final static int DEPTH = 4;

    public static void main(String[] args) {
        Board board = new Board();
        MiniMaxAlph minimax = new MiniMaxAlph(DEPTH, board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        System.out.println(minimax.miniMaxAB(root,Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer()));

        Node<Board> child1 = minimax.selectMove(root);
        //child1.getData().displayBoardGrid();

        //System.out.println(minimax.search2(root,Integer.MIN_VALUE, Integer.MAX_VALUE));

//        Board board = new Board();
//        MiniMaxAlph minimax = new MiniMaxAlph(7, board);
//        GameTree gameTree = new GameTree(7, board);
//        Node<Board> root = gameTree.createTree();
//
//        Node<Board> child1 = minimax.selectMove(root);
//        int[] bestMove = new int[2];
//        try {
//            bestMove[0] = child1.getRow();
//            bestMove[1] = child1.getColumn();
//        }
//        catch(NullPointerException e) {
//            System.out.println("no more moves");
//        }

    }
}
