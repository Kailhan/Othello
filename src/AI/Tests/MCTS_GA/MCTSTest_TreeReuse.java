package AI.Tests.MCTS_GA;

import AI.MCTS_TreeReuse;
import AI.Stupid;
import AI.Tests.GenericTest;

public class MCTSTest_TreeReuse {

    public static void main(String[] args) {
        int games = 1000;
        int totalSims1 = 10;
        double explorationParameter = 0;
        int size = 8;

        MCTS_TreeReuse mcts1 = new MCTS_TreeReuse(totalSims1, explorationParameter);

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
        System.out.println("boardsize: " + size);
        System.out.println("mcts1 totalsims: " + totalSims1);
        System.out.println("exploration: " + mcts1.getExplorationParameter());
        System.out.println("treeSize: " + mcts1.getRootNode().getTreeSize());
        System.out.println("treeHeight: " + mcts1.getRootNode().getHeight(mcts1.getRootNode()));
        System.out.println("totalGames: " + games);
        System.out.println("MCTS 1 win%: " + (double)mcts1Wins/games);
        System.out.println("MCTS 2 win%: " + (double)mcts2Wins/games);
        System.out.println("MCTS draw%: " + (double)(games-mcts1Wins-mcts2Wins)/games);

//        System.out.println("MCTS 1 wins: " + mcts1Wins);
//        System.out.println("MCTS 2 wins: " + mcts2Wins);
//        System.out.println("MCTS draws: " + (games-mcts1Wins-mcts2Wins));
    }
}