package AI.Genetic_Algorithm;

import AI.AI;
import AI.EvaluationFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static AI.EvaluationFunction.WEIGHT_POLY_SIZE;
import static AI.Genetic_Algorithm.Population_EvalFunc.*;

public class GA_DirectEvalFunc {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Population_EvalFunc pop =  new Population_EvalFunc();
        System.out.println("Created a population");
        EvaluationFunction[] selectedIndividuals = new EvaluationFunction[pop.getPopSize()*2];
        EvaluationFunction[] selectedIndividualsChildren = new EvaluationFunction[pop.getPopSize()];
        pop.calculateFitness(Population.GA_GAMES_TO_BE_SIMMED, pop.getBoardSize());
        System.out.println("Calculated fitness of initial population");
        int maxIterations = 1000;
        double minVariance = 1000;

        String[] gaLog = new String[(WEIGHT_POLY_SIZE + (pop.getBoardSize() * pop.getBoardSize()) + 2) * (1  + (maxIterations * pop.getPopSize()))];
        int gaLogIndex = 0;
        gaLog[gaLogIndex] = ("attributes"); gaLogIndex++;
        gaLog[gaLogIndex] = ("fitness"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("coinWeightPoly2"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("moveWeightPoly2"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly0"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly1"); gaLogIndex++;
        gaLog[gaLogIndex] = ("territoryWeightPoly2"); gaLogIndex++;



        int iterationCounter = 0;
        for(int i = 0; i < maxIterations; i++) {
            long starttime = System.nanoTime();
            for(int j = 0; j < pop.getAIs().length; j++) {
                gaLog[gaLogIndex] = String.valueOf(i); //attribute line
                gaLogIndex++;
                gaLog[gaLogIndex] = String.valueOf(pop.getAIs()[j].getFitness()); //fitness line
                gaLogIndex++;
                for(int k = 0; k < pop.getAIs()[j].getChromosome().length; k++) {
                    gaLog[gaLogIndex] = String.valueOf(pop.getAIs()[j].getChromosome()[k]);
                    gaLogIndex++;
                }
            }

            selectedIndividuals = pop.selection(SELECTION_RATIO);
            for(int j = 0; j < pop.getPopSize(); j++) {
                selectedIndividualsChildren[j] = pop.randomWeightedCrossover(selectedIndividuals[j], selectedIndividuals[((pop.getPopSize()*2) - 1) -j]);
                //selectedIndividualsChildren[j] = pop.randomCrossover(selectedIndividuals[j], selectedIndividuals[((pop.getPopSize()*2) - 1) -j]);
            }
            pop.setAIs(selectedIndividualsChildren);
            pop.nonUniformBitMutate(0.5, 0.5);
            pop.calculateFitness(GA_GAMES_TO_BE_SIMMED, pop.getBoardSize());
            System.out.println("iteration: " + i);
            iterationCounter = i;

            System.out.println("calcvariance: " + pop.calcVariance());
            if(pop.calcVariance() < minVariance) {
                System.out.println("pop.calcVariance() < " + minVariance);
                break;
            }
            long endtime = System.nanoTime();
            System.out.println("iteration time: " + (endtime-starttime)/1000000000.0);
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

        StringBuilder gaCSVLogBuilder = new StringBuilder();

        for(int i = 1; i < (WEIGHT_POLY_SIZE + 2) * (1  + ((iterationCounter + 1) * pop.getPopSize())); i++) {
            gaCSVLogBuilder.append(gaLog[i - 1]).append(",");
            if(i % ((WEIGHT_POLY_SIZE + 2)) == 0) gaCSVLogBuilder.append("\n");
        }

        gaCSVLogBuilder.append(gaLog[(WEIGHT_POLY_SIZE + (pop.getBoardSize() * pop.getBoardSize()) + 2) * (1  + ((iterationCounter + 1) * pop.getPopSize()))-1]);
        String gaCSVLog = gaCSVLogBuilder.toString();
        try {
            String fileName  = "_selectionRatio" + String.valueOf(SELECTION_RATIO) +
                    "_time_" + String.valueOf(endTime - startTime) +
                    "_maxIter_" + String.valueOf(maxIterations) +
                    "_iterConv_" + String.valueOf(iterationCounter) +
                    "_minVar" + String.valueOf(minVariance) +
                    "_boardSize_" + String.valueOf(GA_BOARD_SIZE) +
                    "_WPB_" + String.valueOf(GA_WEIGHT_POLY_BOUND) +
                    "_TB_" + String.valueOf(GA_TERRITORY_BOUND) +
                    "_GTB_" + String.valueOf(GA_GAMES_TO_BE_SIMMED) +
                    "_popSize_" + String.valueOf(GA_POP_SIZE) +
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
