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

        int games = 30;
        int size = 8;

        AI.Stupid s = new AI.Stupid();

        GenericTest.test(s,negaMax, games, size);
        int minimaxWins = GenericTest.getPlayer2Wins();
        int stupidWins = GenericTest.getPlayer1Wins();
        System.out.println("NegaMax wins: "+ minimaxWins);
        System.out.println("Stupid wins: "+ stupidWins);


    }
}
