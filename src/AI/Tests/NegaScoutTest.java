package AI.Tests;

import AI.*;
import Core.Board;

public class NegaScoutTest {

    final static int DEPTH = 3;
    public static void main(String[] args) {
        GameTree gameTree = new GameTree(4);
        Node<Board> root = gameTree.createTree();

        int games = 10;
        int totalSims1 = 1;
        int totalSims2 = 1;
        int size = 8;

        //GenericTest gt = new GenericTest();
        Stupid s = new Stupid();
        //gt.test(m,s, 5,8);
        Board board = new Board();
        NegaScout ns = new NegaScout(4, board);

        MMAB_moveOrdering mmab = new MMAB_moveOrdering(10);
        NS_moveOrdering nsm = new NS_moveOrdering(10);

        GenericTest.test(mmab, s, games, size);
        int negascoutWins = GenericTest.getPlayer1Wins();
        int stupidWins = GenericTest.getPlayer2Wins();
        System.out.println("NegaScout wins: "+ negascoutWins);
        System.out.println("MMAB wins: "+ stupidWins);
    }
}
