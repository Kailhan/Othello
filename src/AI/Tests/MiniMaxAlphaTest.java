package AI.Tests;

import AI.*;
import Core.Board;

public class MiniMaxAlphaTest {

    final static int DEPTH = 7;

    public static void main(String[] args) {


        Board board = new Board();
        EvaluationFunction evaluator = new EvaluationFunction(board);
        MiniMaxAlph minimax = new MiniMaxAlph(DEPTH,board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        System.out.println(minimax.search(root,Integer.MIN_VALUE, Integer.MAX_VALUE));

        Node<Board> child1 = minimax.selectMove(root);
        child1.getData().displayBoardGrid();

        //System.out.println(minimax.search2(root,Integer.MIN_VALUE, Integer.MAX_VALUE));

    }
}
