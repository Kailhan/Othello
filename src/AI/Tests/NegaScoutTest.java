package AI.Tests;

import AI.GameTree;
import AI.NegaScout;
import AI.Node;
import Core.Board;
import AI.Stupid;

public class NegaScoutTest {

    final static int DEPTH = 3;
    public static void main(String[] args) {
        GameTree gameTree = new GameTree(4);
        Node<Board> root = gameTree.createTree();

        int games = 100;
        int totalSims1 = 1;
        int totalSims2 = 1;
        int size = 8;

        //GenericTest gt = new GenericTest();
        Stupid s = new Stupid();
        //gt.test(m,s, 5,8);
        Board board = new Board();
        NegaScout ns = new NegaScout(4, board);

        GenericTest.test(s, ns, games, size);
        int negascoutWins = GenericTest.getPlayer2Wins();
        int stupidWins = GenericTest.getPlayer1Wins();
        System.out.println("NegaScout wins: "+ negascoutWins);
        System.out.println("Stupid wins: "+ stupidWins);
    }
}
