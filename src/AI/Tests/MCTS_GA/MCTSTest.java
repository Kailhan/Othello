package AI.Tests.MCTS_GA;

import AI.MCTS;
import AI.Stupid;
import AI.Tests.GenericTest;

public class MCTSTest {

    public static void main(String[] args) {
        int games = 100;
        int totalSims1 = 100;
        int totalSims2 = 1;
        int size = 8;

        MCTS mcts1 = new MCTS(totalSims1, MCTS.STANDARD_EXPLORATION_PARAMETER);
        MCTS mcts2 = new MCTS(totalSims2, MCTS.STANDARD_EXPLORATION_PARAMETER);

        long startTime = System.nanoTime();

        GenericTest.test(mcts1, new Stupid(), games/2, size);
        int mcts1Wins = GenericTest.getPlayer1Wins();
        int mcts2Wins = GenericTest.getPlayer2Wins();

        System.out.println("MCTS 1 wins: " + GenericTest.getPlayer1Wins());
        System.out.println("MCTS 2 wins: " + GenericTest.getPlayer2Wins());
        System.out.println("MCTS draws: " + (games/2-GenericTest.getPlayer1Wins()-GenericTest.getPlayer2Wins()));
        System.out.println("halfway thru");

        GenericTest.test(new Stupid(), mcts1, games/2, size);
        mcts1Wins += GenericTest.getPlayer2Wins();
        mcts2Wins += GenericTest.getPlayer1Wins();

        System.out.println("MCTS 1 wins: " + GenericTest.getPlayer2Wins());
        System.out.println("MCTS 2 wins: " + GenericTest.getPlayer1Wins());
        System.out.println("MCTS draws: " + (games/2-GenericTest.getPlayer1Wins()-GenericTest.getPlayer2Wins()));

        long endTime = System.nanoTime();
        System.out.println(endTime-startTime);
        System.out.println("mcts1 totalsims: " + totalSims1);
        System.out.println("exploration: " + mcts1.getExplorationParameter());
        System.out.println("totalGames: " + games);
        System.out.println("MCTS 1 win%: " + (double)mcts1Wins/games);
        System.out.println("MCTS 2 win%: " + (double)mcts2Wins/games);
        System.out.println("MCTS draw%: " + (double)(games-mcts1Wins-mcts2Wins)/games);

//        System.out.println("MCTS 1 wins: " + mcts1Wins);
//        System.out.println("MCTS 2 wins: " + mcts2Wins);
//        System.out.println("MCTS draws: " + (games-mcts1Wins-mcts2Wins));
    }
}