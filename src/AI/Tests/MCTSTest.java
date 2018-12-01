package AI.Tests;

import AI.EvaluationFunction;
import AI.MCTS;
import AI.MCTSNode;
import Core.Board;
import Core.Logic;

import java.util.Scanner;

import static Core.Board.BLACK;
import static Core.Board.WHITE;

public class MCTSTest {

    public static void main(String[] args) {
        System.out.println("Enter totalSims of MCTS 1");
        Scanner scanner = new Scanner(System.in);
        int totalSims1 = scanner.nextInt();
        System.out.println("Enter totalSims of MCTS 2");
        scanner = new Scanner(System.in);
        int totalSims2 = scanner.nextInt();
        //System.out.println("Amount of games to be simulated");
        //scanner = new Scanner(System.in);
        //int games = scanner.nextInt();
        int games = 1000;
        System.out.println("board size");
        scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        MCTS mcts1 = new MCTS(totalSims1);
        MCTS mcts2 = new MCTS(totalSims2);

        GenericTest.test(mcts1, mcts2, games, size);
        System.out.println("MCTS 1 wins: " + GenericTest.getPlayer1Wins());
        System.out.println("MCTS 2 wins: " + GenericTest.getPlayer2Wins());
        System.out.println("MCTS draws: " + GenericTest.getDraws());
    }
}