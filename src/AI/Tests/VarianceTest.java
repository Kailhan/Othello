package AI.Tests;

import AI.MCTS;
import AI.Stupid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class VarianceTest {

    public static void main(String[] args) {
        int minGamesSimmed = 10;
        int maxGamesSimmed = 1000;
        int stepsGamesSimmed = 10;

        int parts = 10;
        int rowWidth = (parts*2) + 2;

        int logCounter = 0;

        String [] log = new String[((((maxGamesSimmed - minGamesSimmed)/stepsGamesSimmed) + 1) * rowWidth) + maxGamesSimmed/stepsGamesSimmed];

        StringBuilder logBuilder8 = new StringBuilder();

        for(int i = minGamesSimmed; i <= maxGamesSimmed; i += stepsGamesSimmed) {
            long startTimeTot = System.nanoTime();
            double average = 0;
            double[] storageForVariance = new double[parts];
            double variance = 0;
            log[logCounter] = String.valueOf(i); logCounter++;
            for(int j = 0; j < parts; j++) {
                GenericTest.test(new Stupid(), new Stupid(), i, 8);
                log[logCounter] = String.valueOf(GenericTest.getPlayer1Wins()); logCounter++;
                log[logCounter] = String.valueOf(GenericTest.getDraws()); logCounter++;
                storageForVariance[j] = (double)GenericTest.getPlayer1Wins()/i;
                average += storageForVariance[j];
            }
            average /= parts;
            log[logCounter] = String.valueOf(average); logCounter++;
            for(int j = 0; j < parts; j++) {
                variance += (average - storageForVariance[j]) * (average - storageForVariance[j]);
            }
            variance /= (parts - 1);
            log[logCounter] = String.valueOf(variance); logCounter++;
            long endTimeTot = System.nanoTime();
            //System.out.println("logCntr:" + logCounter);
            System.out.println("games simmed: " + i);
            System.out.println(((endTimeTot-startTimeTot)/1000000) + " ms");
        }

        logBuilder8.append("maxGamesSimmed").append(",");
        for(int i = 0; i < parts; i++) {
            logBuilder8.append(i+1).append("wins").append(",");
            logBuilder8.append(i+1).append("draws").append(",");
        }
        logBuilder8.append("AVG").append(",");
        logBuilder8.append("VAR");
        //logBuilder8.append("\n");

        for(int i = 0; i < log.length; i++) {

            if(i % (rowWidth + 1) == 0) {
                logBuilder8.append("\n");
                logBuilder8.append(log[i]);
                //logBuilder8.append(",");
            } else {
                logBuilder8.append(",");
                logBuilder8.append(log[i]);
            }
        }
        try {
            String logCSV8 = logBuilder8.toString();
            String fileName8  = "minGamesSimmed_" + String.valueOf(minGamesSimmed) +
                    "_maxGamesSimmed_" + String.valueOf(maxGamesSimmed) +
                    "_steps_" + String.valueOf(stepsGamesSimmed) +
                    "_parts_" + String.valueOf(parts) +
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
