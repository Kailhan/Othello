package AI.Genetic_Algorithm;

public class Population {

    private int size;
    private GeneticAlg_EvalFunc[] individuals;

    public Population(int size) {
        this.size = size;
        individuals = new GeneticAlg_EvalFunc[size];
    }



}
