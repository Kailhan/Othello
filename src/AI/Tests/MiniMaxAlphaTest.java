package AI.Tests;

import AI.*;
import Core.Board;

public class MiniMaxAlphaTest {

    final static int DEPTH = 5;

    public static void main(String[] args) {

        EvaluationFunction evaluator = new EvaluationFunction();
        Board board = new Board();
        MiniMaxAlph minimax = new MiniMaxAlph(evaluator, board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        minimax.search(root,Integer.MIN_VALUE, Integer.MAX_VALUE);
        minimax.getBestNode().getData().displayBoardGrid();

    }
}
