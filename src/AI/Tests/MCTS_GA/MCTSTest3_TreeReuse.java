package AI.Tests.MCTS_GA;

import AI.MCTS;
import AI.MCTS_TreeReuse;
import AI.Stupid;
import AI.Tests.GenericTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class MCTSTest3_TreeReuse {

    public static void main(String[] args) {
        int minPlayouts = 5;
        int maxPlayouts = 10;
        int stepsPlayouts = 1;
        double minExplore = 0;
        double maxExplore = 2;
        double stepsExplore = 0.1;
        int scaling = 100;
        int minExploreInt = (int)Math.round(minExplore * scaling);
        int maxExploreInt = (int)Math.round(maxExplore * scaling);
        int stepsExploreInt = (int)Math.round(stepsExplore * scaling);
        int totalGames = 1000;
        int rowWidth = 6;

        int logCounter = 0;
        System.out.println((((maxPlayouts - minPlayouts)/stepsPlayouts) + 1));
        System.out.println((((maxExploreInt - minExploreInt)/stepsExploreInt) + 1));
        String [] log = new String[((((maxPlayouts - minPlayouts)/stepsPlayouts) + 1) * (((maxExploreInt - minExploreInt)/stepsExploreInt) + 1) * rowWidth) + (((maxPlayouts - minPlayouts)/stepsPlayouts) + 1) * (((maxExploreInt - minExploreInt)/stepsExploreInt)+1)];
        //String[] log = new String[660];
        System.out.println("loglength: " +  log.length);
        System.out.println("minExploreInt: " +  minExploreInt);
        System.out.println("maxExploreInt: " +  maxExploreInt);
        System.out.println("stepsExploreInt: " + stepsExploreInt);

        StringBuilder logBuilder8 = new StringBuilder();
        for(int i = minExploreInt; i <= maxExploreInt; i += stepsExploreInt) {
            long startTimeTot = System.nanoTime();
            for(int j = minPlayouts; j < maxPlayouts + 1; j += stepsPlayouts) {
                long startTime = System.nanoTime();
                MCTS_TreeReuse mcts = new MCTS_TreeReuse(j, (double)i/scaling);
                log[logCounter] = String.valueOf(j); logCounter++;
                log[logCounter] = String.valueOf((double)i/scaling); logCounter++;
                GenericTest.test(mcts, new Stupid(), totalGames/2, 8);
                log[logCounter] = String.valueOf(GenericTest.getPlayer1Wins()); logCounter++;
                log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
                GenericTest.test(new Stupid(), mcts, totalGames/2, 8);
                log[logCounter] = String.valueOf(GenericTest.getPlayer2Wins()); logCounter++;
                log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
                long endTime = System.nanoTime();
                log[logCounter] = String.valueOf(endTime-startTime); logCounter++;
                System.out.println("board8 iter: " + j + "time: " + ((endTime-startTime)/1000000000));
            }
            long endTimeTot = System.nanoTime();
            System.out.println("logCntr:" + logCounter);
            System.out.println("Done with exploreparam: " + (double)i/scaling);
            System.out.println(((endTimeTot-startTimeTot)/1000000000));
        }
        System.out.println("Done with board 8: " + System.currentTimeMillis());

        logBuilder8.append("maxPlayouts").append(",").append("explorationParam").append(",").append("board(8)_p1").append(",").append("draws").append(",").append("board(8)_p2").append(",").append("draws").append(",").append("iterTime");
        for(int i = 0; i < log.length; i++) {
            if(i % (rowWidth+1) == 0) {
                logBuilder8.append("\n");
            }
            logBuilder8.append(log[i]);
            if((i+1) % (rowWidth+1) != 0) logBuilder8.append(",");
        }

        try {
            String logCSV8 = logBuilder8.toString();
            String fileName8  = "minPlay_" + String.valueOf(minPlayouts) +
                    "_maxPlay_" + String.valueOf(maxPlayouts) +
                    "_steps_" + String.valueOf(stepsPlayouts) +
                    "_minExp_" + String.valueOf(minExplore) +
                    "_maxExp_" + String.valueOf(maxExplore) +
                    "_steps_" + String.valueOf(stepsExplore) +
                    "_totalGames_" + String.valueOf(totalGames) +
                    "_BOARDSIZE8ONLY_" + String.valueOf(8) +
                    "_SUBTREEREUSE_" +
                    "_time" + new Timestamp(System.currentTimeMillis()).toInstant().toString() +
                    ".csv";
            fileName8 = fileName8.replaceAll(":","_");
            BufferedWriter writer8 = new BufferedWriter(new FileWriter(fileName8));
            writer8.write(logCSV8);
            writer8.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
