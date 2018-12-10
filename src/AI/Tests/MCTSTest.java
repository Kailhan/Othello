package AI.Tests;

import AI.MCTS;
import AI.Stupid;

public class MCTSTest {

    public static void main(String[] args) {
//        System.out.println("Enter totalSims of MCTS 1");
//        Scanner scanner = new Scanner(System.in);
//        int totalSims1 = scanner.nextInt();
//        System.out.println("Enter totalSims of MCTS 2");
//        scanner = new Scanner(System.in);
//        int totalSims2 = scanner.nextInt();
        //System.out.println("Amount of games to be simulated");
        //scanner = new Scanner(System.in);
        //int games = scanner.nextInt();
//        System.out.println("board size");
//        scanner = new Scanner(System.in);
//        int size = scanner.nextInt();
        int games = 100;
        int totalSims1 = 1000;
        int totalSims2 = 2;
        int size = 6;


        MCTS mcts1 = new MCTS(totalSims1);
        MCTS mcts2 = new MCTS(totalSims2);

        GenericTest.test(mcts1, new Stupid(), games/2, size);
        int mcts1Wins = GenericTest.getPlayer1Wins();
        int mcts2Wins = GenericTest.getPlayer2Wins();
        //System.out.println("MCTS 1 wins: " + GenericTest.getPlayer1Wins());
        //System.out.println("MCTS 2 wins: " + GenericTest.getPlayer2Wins());
        GenericTest.test(new Stupid(), mcts1, games/2, size);
        mcts1Wins += GenericTest.getPlayer2Wins();
        mcts2Wins += GenericTest.getPlayer1Wins();

        System.out.println("MCTS 1 wins: " + mcts1Wins);
        System.out.println("MCTS 2 wins: " + mcts2Wins);
        System.out.println("MCTS draws: " + (games-mcts1Wins-mcts2Wins));
    }
}