package AI.Genetic_Algorithm;

import AI.AI;

import static AI.Genetic_Algorithm.Population.GA_GAMES_TO_BE_SIMMED;

public class GA_Eval {

    public static void main(String[] args) {
        Population pop =  new Population();
        System.out.println("Created a population");
        AI[] selectedIndividuals = new AI[pop.getPopSize()*2];
        AI[] selectedIndividualsChildren = new AI[pop.getPopSize()];
        pop.calculateFitness(Population.GA_GAMES_TO_BE_SIMMED, pop.getBoardSize());
        System.out.println("Calculated fitness of initial population");
        int maxIterations = 1000;
        for(int i = 0; i < maxIterations; i++) {
            selectedIndividuals = pop.selection(2);
            for(int j = 0; j < pop.getPopSize(); j++) {
                selectedIndividualsChildren[j] = pop.randomWeightedCrossover(selectedIndividuals[j], selectedIndividuals[(pop.getPopSize()*2)-j]);
            }
            pop.setAIs(selectedIndividualsChildren);
            pop.nonUniformBitMutate(0.5, 0.5);
            pop.calculateFitness(GA_GAMES_TO_BE_SIMMED, pop.getBoardSize());
            System.out.println("iteration: " + i);
        }
    }
}
