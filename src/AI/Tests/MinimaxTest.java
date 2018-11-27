package AI.Tests;

import AI.EvaluationFunction;
import AI.GameTree;
import AI.Minimax;
import AI.Node;
import Core.Board;

public class MinimaxTest {

    final static int DEPTH = 6;
    public static void main(String[] args) {

        EvaluationFunction evaluator = new EvaluationFunction();
        Board board = new Board();
        Minimax minimax = new Minimax(3, board);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        minimax.minimaxAlg2(root);
        Node<Board> child1 = minimax.selectMove(root);
        child1.getData().displayBoardGrid();

        minimax.minimaxAlg2(child1);
        Node<Board> child2 = minimax.selectMove(child1);
        child2.getData().displayBoardGrid();



        //minimax.minimaxAlg(root, -1);
        //minimax.getBestNode().getData().displayBoardGrid();



    }


}
