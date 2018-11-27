package AI.Genetic_Algorithm;

import AI.EvaluationFunction;
import AI.AI;
import AI.Stupid;
import Core.Board;
import Core.QuickSort;

import java.util.Random;

public class Population {

    private int popSize;
    private int boardSize;
    private Random rand;
    private double weightPolyBound;
    private double territoryBound;
    private EvaluationFunction[] individuals;
    private AI[] AIs;

    public Population(int popSize, int boardSize, double weightPolyBound, double territoryBound, AI ai) {
        this.popSize = popSize;
        this.boardSize = boardSize;
        this.rand = new Random();
        this.weightPolyBound = weightPolyBound;
        this.territoryBound = territoryBound;
        individuals = new EvaluationFunction[popSize];
        AIs = new AI[popSize];
        initPopulation();
    }

    public Population() {
        this(100, 4, 10, 10, new Stupid());
    }

    public void initPopulation() {
        for(int i = 0; i < popSize; i++) {
            EvaluationFunction cEvalFunc = new EvaluationFunction();
            this.individuals[i] = cEvalFunc;
            cEvalFunc.setWeightPoly(initWeightPoly(16, weightPolyBound)); //size of weightpoly in evaluationfunction
            cEvalFunc.setTerritory(initTerritory(territoryBound));
            this.AIs[i] = new GA_MiniMaxAlph(3, new Board(boardSize), cEvalFunc); //idk what the depth should be
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

    /**
     * Calculates and sort AIs
     * @param gamesToBeSimmed
     * @param boardSize
     */

    public void calculateFitness(int gamesToBeSimmed, int boardSize) {
        for(int i = 0; i < AIs.length; i++) {
            AIs[i].evaluateFitness(gamesToBeSimmed, boardSize);
        }
       AI[] AIsTemp = new QuickSort(AIs).getAIs();
        for(int i = 0; i < AIs.length; i++) {
            AIs[i] = AIsTemp[i];
        }
    }

    public EvaluationFunction randomCrossover(EvaluationFunction parent1, EvaluationFunction parent2) {
        double[] parent1WeightPoly = parent1.getWeightPoly();
        double[] parent2WeightPoly = parent1.getWeightPoly();
        double[][] parent1CellValues = parent2.getCellValues();
        double[][] parent2CellValues = parent2.getCellValues();
        double[] childWeightPoly = new double[parent1WeightPoly.length];
        double[][] childCellValues = new double[parent1CellValues.length][parent1CellValues[0].length];

        for(int i = 0; i < parent1WeightPoly.length; i++) {
            childWeightPoly[i] = (rand.nextInt(2) == 0) ? parent1WeightPoly[i] : parent2WeightPoly[i];
        }
        for(int i = 0; i < parent1CellValues.length; i++) {
            for (int j = 0; j < parent1CellValues[0].length; j++) {
                childCellValues[i][j] = (rand.nextInt(2) == 0) ? parent1CellValues[i][j] : parent2CellValues[i][j];
            }
        }
        EvaluationFunction tmpEvalFunc = new EvaluationFunction();
        tmpEvalFunc.setWeightPoly(initWeightPoly(16, weightPolyBound)); //size of weightpoly in evaluationfunction
        tmpEvalFunc.setTerritory(initTerritory(territoryBound));
        return tmpEvalFunc;
    }
}
