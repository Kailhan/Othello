package AI.Genetic_Algorithm;

import AI.EvaluationFunctions.GA_EvalFunc;

public class Population {

    private int size;
    private GA_EvalFunc[] individuals;

    public Population(int size) {
        this.size = size;
        individuals = new GA_EvalFunc[size];
    }



}
