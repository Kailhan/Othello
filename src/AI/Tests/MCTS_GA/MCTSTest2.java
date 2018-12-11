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

        int maxPlayouts = 100;
        int totalGames = 100;

        int logCounter = 0;
        int rowWidth = 5;
        String[] log = new String[(maxPlayouts * rowWidth) * 3];
        //String[] log = new String[(maxPlayouts * 3) * 3];

        StringBuilder logBuilder4 = new StringBuilder();
        StringBuilder logBuilder6 = new StringBuilder();
        StringBuilder logBuilder8 = new StringBuilder();

        System.out.println("Start: " + System.currentTimeMillis());

        for(int i = 1; i < maxPlayouts + 1; i++) {
            long startTime = System.nanoTime();
            MCTS mcts = new MCTS(i);
            GenericTest.test(mcts, new Stupid(), totalGames/2, 4);
            log[logCounter] = String.valueOf(GenericTest.getPlayer1Wins()); logCounter++;
            log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
            GenericTest.test(new Stupid(), mcts, totalGames/2, 4);
            log[logCounter] = String.valueOf(GenericTest.getPlayer2Wins()); logCounter++;
            log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
            long endTime = System.nanoTime();
            log[logCounter] = String.valueOf(endTime-startTime); logCounter++;
            if(i%100==0) System.out.println("board4 iter: " + i);
        }

        logBuilder4.append("maxPlayouts").append(",").append("board(4)_p1").append(",").append("draws").append(",").append("board(4)_p2").append(",").append("draws").append(",").append("iterTime");
        for(int i = 0; i < (log.length/3)*1; i++) {
            if(i % rowWidth == 0) {
                logBuilder4.append("\n");
                logBuilder4.append((i/rowWidth)+1).append(",");
            }
            logBuilder4.append(log[i]);
            if((i+1) % rowWidth != 0) logBuilder4.append(",");
        }

        try {
            String logCSV4 = logBuilder4.toString();
            String fileName4  = "maxPlayouts_" + String.valueOf(maxPlayouts) +
                    "_totalGames_" + String.valueOf(totalGames) +
                    "_boardSize_4_" +
                    "_time" + new Timestamp(System.currentTimeMillis()).toInstant().toString() +
                    ".csv";
            fileName4 = fileName4.replaceAll(":","_");
            BufferedWriter writer4 = new BufferedWriter(new FileWriter(fileName4));
            writer4.write(logCSV4);
            writer4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done with board 4: " + System.currentTimeMillis());

        for(int i = 1; i < maxPlayouts + 1; i++) {
            long startTime = System.nanoTime();
            MCTS mcts = new MCTS(i);
            GenericTest.test(mcts, new Stupid(), totalGames/2, 6);
            log[logCounter] = String.valueOf(GenericTest.getPlayer1Wins()); logCounter++;
            log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
            GenericTest.test(new Stupid(), mcts, totalGames/2, 6);
            log[logCounter] = String.valueOf(GenericTest.getPlayer2Wins()); logCounter++;
            log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
            long endTime = System.nanoTime();
            log[logCounter] = String.valueOf(endTime-startTime); logCounter++;
            if(i%100==0) System.out.println("board6 iter: " + i);
        }

        logBuilder6.append("maxPlayouts").append(",").append("board(6)_p1").append(",").append("draws").append(",").append("board(6)_p2").append(",").append("draws").append(",").append("iterTime");
        for(int i = (log.length/3)*1; i < ((log.length/3)*2); i++) {
            if(i % rowWidth == 0) {
                logBuilder6.append("\n");
                logBuilder6.append(((i- (log.length/3))/rowWidth)+1).append(",");
            }
            logBuilder6.append(log[i]);
            if((i+1) % rowWidth != 0) logBuilder6.append(",");
        }

        try {
            String logCSV6 = logBuilder6.toString();
            String fileName6  = "maxPlayouts_" + String.valueOf(maxPlayouts) +
                    "_totalGames_" + String.valueOf(totalGames) +
                    "_boardSize_6_" +
                    "_time" + new Timestamp(System.currentTimeMillis()).toInstant().toString() +
                    ".csv";
            fileName6 = fileName6.replaceAll(":","_");
            BufferedWriter writer6 = new BufferedWriter(new FileWriter(fileName6));
            writer6.write(logCSV6);
            writer6.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done with board 6: " + System.currentTimeMillis());

        for(int i = 1; i < maxPlayouts + 1; i++) {
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
        for(int i = (log.length/3)*2; i < ((log.length/3)*3); i++) {
            if(i % rowWidth == 0) {
                logBuilder8.append("\n");
                logBuilder8.append((((i - (log.length/3)*2)/rowWidth)+1)).append(",");
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
