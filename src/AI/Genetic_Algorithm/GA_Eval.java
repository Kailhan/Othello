package AI.Genetic_Algorithm;

import AI.AI;

public class GA_Eval {

    public static void main(String[] args) {

        Population pop =  new Population();
        pop.calculateFitness(Population.GA_GAMES_TO_BE_SIMMED, Population.GA_BOARD_SIZE);
        int maxIterations = 1000;
        for(int i = 0; i < maxIterations; i++) {

        }
//        START
//        Generate the initial population
//        Compute fitness
//        REPEAT
//                Selection
//        Crossover
//                Mutation
//        Compute fitness
//        UNTIL population has converged
//        STOP

    }
}
