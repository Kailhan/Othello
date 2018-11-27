package AI.Genetic_Algorithm;

import AI.EvaluationFunction;

import java.util.Random;

public class Population {

    private int popSize;
    private int boardSize;
    private Random rand;
    private double weightPolyBound;
    private double territoryBound;
    private EvaluationFunction[] individuals;

    public Population(int popSize, int boardSize, double weightPolyBound, double territoryBound) {
        this.popSize = popSize;
        this.boardSize = boardSize;
        this.rand = new Random();
        this.weightPolyBound = weightPolyBound;
        this.territoryBound = territoryBound;
        individuals = new EvaluationFunction[popSize];
        initPopulation();
    }

    public Population() {
        this(1000, 4, 10, 10);
    }

    public void initPopulation() {
        for(int i = 0; i < popSize; i++) {
            EvaluationFunction cEvalFunc = new EvaluationFunction();
            this.individuals[i] = cEvalFunc;
            cEvalFunc.setWeightPoly(initWeightPoly(16, weightPolyBound)); //size of weightpoly in evaluationfunction
            cEvalFunc.setTerritory(initTerritory(territoryBound));
        }
    }

    public double[] initWeightPoly(int weightPolySize, double bound) {
        double[] weightPoly = new double[weightPolySize];
        for(int i = 0; i < weightPolySize; i++) {
            weightPoly[i] = rand.nextDouble()*bound;
        }
        return weightPoly;
    }

    public double[][] initTerritory(double bound) {
        return combineTerritoryPart(createTerritoryPart(bound));
    }

    public double[][] createTerritoryPart(double bound) {
        double[][] territoryPart = new double[boardSize/2][boardSize/2]; //force symmetry
        for(int i = 0; i < boardSize/2; i++) {
            for(int j = 0; j < boardSize/2; j++) {
                territoryPart[i][j] = rand.nextDouble()*bound;
            }
        }
        return territoryPart;
    }

    /**
     * Territory parts should be symmetric so trying to save calculations
     * by only generating a part and then constructing whole terr
     * @param territoryPart assuming part is topleft
     * @return full territory based on part
     */

    public double[][] combineTerritoryPart(double[][] territoryPart) {
        double[][] territory = new double[boardSize][boardSize];
        for(int i = 0; i < territoryPart.length; i++) {
            for(int j = 0; j < territoryPart.length; j++) {
                territory[i][j] = territoryPart[i][j]; //topleft
                territory[i][territoryPart.length*2-j] = territoryPart[i][j]; //topright
                territory[territoryPart.length*2-i][j] = territoryPart[i][j]; //bottomleft
                territory[territoryPart.length*2-i][territoryPart.length*2-j] = territoryPart[i][j]; //bottomright
            }
        }
        return territory;
    }
}
