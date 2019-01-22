package AI.Tests;

import AI.*;
import Core.Board;

public class NSvsMCTS {

    final static long TIME = 20;
    final static int DEPTH = 7;
    final static int GAMES = 1000;
    final static int SIZE  = 8;

    public static void main(String[] args) {
        test1();
        test2();
    }

    //value test
    private static void test1() {
        Board board = new Board();
        NS_moveOrdering ns = new NS_moveOrdering(TIME);
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();
        //System.out.println("value: " + ns.NegaSAlg(root,Integer.MIN_VALUE, Integer.MAX_VALUE, board.getCurrentPlayer()));
    }

    // generic test
    private static void test2(){

        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        MCTS mcts = new MCTS(TIME, 0.76);
        Board board = new Board();
        NS_moveOrdering ns = new NS_moveOrdering(TIME);

        GenericTest.test(mcts, ns, GAMES, SIZE);
        int negascoutWins = GenericTest.getPlayer2Wins();
        int mctsWins = GenericTest.getPlayer1Wins();
        System.out.println("Negascout wins: " + negascoutWins);
        System.out.println("MCTS wins: " + mctsWins);

    }
}
