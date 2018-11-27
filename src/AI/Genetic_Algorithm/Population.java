package AI.Genetic_Algorithm;

import AI.EvaluationFunction;

public class Population {

    private int size;
    private EvaluationFunction[] individuals;

    public Population(int size) {
        this.size = size;
        individuals = new EvaluationFunction[size];
    }



}
