package AI.Tests.MCTS_GA;

import AI.MCTS;
import AI.Stupid;
import AI.Tests.GenericTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class MCTSTest2 {
    public static void main(String[] args) {

        int maxPlayouts = 50;
        int totalGames = 1000;

        int logCounter = 0;
        int rowWidth = 5;
        String[] log = new String[(maxPlayouts * rowWidth) * 1];

        StringBuilder logBuilder8 = new StringBuilder();

        for(int i = 1; i < maxPlayouts + 1; i++) {
            long startTime = System.nanoTime();
            MCTS mcts = new MCTS(i, MCTS.STANDARD_EXPLORATION_PARAMETER);
            GenericTest.test(mcts, new Stupid(), totalGames/2, 8);
            log[logCounter] = String.valueOf(GenericTest.getPlayer1Wins()); logCounter++;
            log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
            GenericTest.test(new Stupid(), mcts, totalGames/2, 8);
            log[logCounter] = String.valueOf(GenericTest.getPlayer2Wins()); logCounter++;
            log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
            long endTime = System.nanoTime();
            log[logCounter] = String.valueOf(endTime-startTime); logCounter++;
            System.out.println("board8 iter: " + i + "time: " + ((endTime-startTime)/1000000000));
        }

        System.out.println("Done with board 8: " + System.currentTimeMillis());

        logBuilder8.append("maxPlayouts").append(",").append("board(8)_p1").append(",").append("draws").append(",").append("board(8)_p2").append(",").append("draws").append(",").append("iterTime");
        for(int i = 0; i < log.length; i++) {
            if(i % rowWidth == 0) {
                logBuilder8.append("\n");
                logBuilder8.append((i/rowWidth)+1).append(",");
            }
            logBuilder8.append(log[i]);
            if((i+1) % rowWidth != 0) logBuilder8.append(",");
        }

        try {
            String logCSV8 = logBuilder8.toString();
            String fileName8  = "maxPlayouts_" + String.valueOf(maxPlayouts) +
                    "_totalGames_" + String.valueOf(totalGames) +
                    "_boardSize_8_" +
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
