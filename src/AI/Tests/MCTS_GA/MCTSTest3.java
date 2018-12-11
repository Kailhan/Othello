package AI.Tests.MCTS_GA;

import AI.MCTS;
import AI.Stupid;
import AI.Tests.GenericTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class MCTSTest3 {

    public static void main(String[] args) {
        int minPlayouts = 200;
        int maxPlayouts = 300;
        int steps = 10;
        int totalGames = 100;
        int rowWidth = 5;

        int logCounter = 0;
        String[] log = new String[(((maxPlayouts - minPlayouts)/steps)+1)*rowWidth];
        StringBuilder logBuilder8 = new StringBuilder();

        for(int i = minPlayouts; i < maxPlayouts + 1; i += steps) {
            long startTime = System.nanoTime();
            MCTS mcts = new MCTS(i);
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
                logBuilder8.append((minPlayouts + ((i/rowWidth)*steps))).append(",");
            }
            logBuilder8.append(log[i]);
            if((i+1) % rowWidth != 0) logBuilder8.append(",");
        }

        try {
            String logCSV8 = logBuilder8.toString();
            String fileName8  = "minPlayouts_" + String.valueOf(minPlayouts) +
                    "_maxPlayouts_" + String.valueOf(maxPlayouts) +
                    "_steps_" + String.valueOf(steps) +
                    "_totalGames_" + String.valueOf(totalGames) +
                    "_BOARDSIZE8ONLY_" + String.valueOf(8) +
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
