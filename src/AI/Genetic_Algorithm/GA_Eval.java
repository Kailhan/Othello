package AI.Genetic_Algorithm;

import AI.AI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static AI.EvaluationFunction.WEIGHT_POLY_SIZE;
import static AI.Genetic_Algorithm.Population.*;

public class GA_Eval {

    public static double SELECTION_RATIO = 3;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Population pop =  new Population();
        System.out.println("Created a population");
        AI[] selectedIndividuals = new AI[pop.getPopSize()*2];
        AI[] selectedIndividualsChildren = new AI[pop.getPopSize()];
        pop.calculateFitness(Population.GA_GAMES_TO_BE_SIMMED, pop.getBoardSize());

        System.out.println("Calculated fitness of initial population");
        int maxIterations = 40;
        String[] gaLog = new String[(WEIGHT_POLY_SIZE + (pop.getBoardSize() * pop.getBoardSize()) + 2) * (1  + (maxIterations * pop.getPopSize()))];
        int gaLogIndex = 0;
        gaLog[gaLogIndex] = ("attributes"); gaLogIndex++;
        gaLog[gaLogIndex] = ("fitness"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly2"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly3"); gaLogIndex++;
        gaLog[gaLogIndex] = ("cornerWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("cornerWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("cornerWeightPoly2"); gaLogIndex++;
        gaLog[gaLogIndex] = ("cornerWeightPoly3"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly2"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly3"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly2"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly3"); gaLogIndex++;
        for(int i = 0; i < pop.getBoardSize(); i++) {
            for(int j = 0; j < pop.getBoardSize(); j++) {
                gaLog[gaLogIndex] = ("row" + i + "col" + j);
                gaLogIndex++;
            }
        }
        for(int i = 0; i < maxIterations; i++) {

            for(int j = 0; j < pop.getAIs().length; j++) {
                gaLog[gaLogIndex] = String.valueOf(i); //attribute line
                gaLogIndex++;
                AI tmpAI = pop.getAIs()[j];
                gaLog[gaLogIndex] = String.valueOf(tmpAI.getFitness()); //fitness line
                gaLogIndex++;
                //System.out.println("tmpAI.getEvaluator().getChromosome().length");
                //System.out.println(tmpAI.getEvaluator().getChromosome().length);
                for(int k = 0; k < tmpAI.getEvaluator().getChromosome().length; k++) {
                    gaLog[gaLogIndex] = String.valueOf(tmpAI.getEvaluator().getChromosome()[k]);
                    gaLogIndex++;
                }
            }

            selectedIndividuals = pop.selection(SELECTION_RATIO);
            for(int j = 0; j < pop.getPopSize(); j++) {
                selectedIndividualsChildren[j] = pop.randomWeightedCrossover(selectedIndividuals[j], selectedIndividuals[((pop.getPopSize()*2) - 1) -j]);
            }
            pop.setAIs(selectedIndividualsChildren);
            pop.nonUniformBitMutate(0.5, 0.5);
            pop.calculateFitness(GA_GAMES_TO_BE_SIMMED, pop.getBoardSize());
            System.out.println("iteration: " + i);
        }
        long endTime = System.nanoTime();
        System.out.println("Completed in: " + ((endTime - startTime)/1000000.0) + " ms");
        AI worstSpecimen = pop.getWorstSpecimen();
        System.out.println("Fitness of worst specimen: " + worstSpecimen.getFitness());
        //worstSpecimen.getEvaluator().printChromosome();
        System.out.println("Wins when having first move: " + worstSpecimen.getWinsFirstMove());
        System.out.println("Wins when having second move: " + worstSpecimen.getWinsSecondMove());

        AI topSpecimen = pop.getTopSpecimen();
        System.out.println("Fitness of top specimen: " + topSpecimen.getFitness());
        //topSpecimen.getEvaluator().printChromosome();
        System.out.println("Wins when having first move: " + topSpecimen.getWinsFirstMove());
        System.out.println("Wins when having second move: " + topSpecimen.getWinsSecondMove());

        String gaCSVLog = String.join(",", gaLog);
        try {
            String fileName = "depth_" + String.valueOf(DEPTH) +
                    "_boardSize_" + String.valueOf(GA_BOARD_SIZE) +
                    "_WPB_" + String.valueOf(GA_WEIGHT_POLY_BOUND) +
                    "_TB_" + String.valueOf(GA_TERRITORY_BOUND) +
                    "_GTB_" + String.valueOf(GA_GAMES_TO_BE_SIMMED) +
                    "_popSize_" + String.valueOf(GA_POP_SIZE) +
                    "_selectionRatio" + String.valueOf(SELECTION_RATIO) +
                    "_time_" + String.valueOf(endTime - startTime) +
                    ".csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(gaCSVLog);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("GA_Eval finished");
    }
}
