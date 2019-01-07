package AI.Tests;

import AI.GameTree;
import AI.Minimax;
import AI.NegaMax;
import AI.Node;
import Core.Board;

public class NegaMaxTest {

    final static int DEPTH = 3;
    public static void main(String[] args) {

        Board board = new Board();
        NegaMax negaMax = new NegaMax(DEPTH, board);
        GameTree gameTree = new GameTree(DEPTH, board);
        Node<Board> root = gameTree.createTree();

        int[] bestMove = new int[2];
        bestMove = negaMax.getBestMove(root.getData(), 1); //1 is black -1 is white;

        for (int i = 0; i < bestMove.length; i++) {
            System.out.println(i);
        }
    }
}
