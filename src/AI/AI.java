package AI;
import AI.Tests.GenericTest;
import Core.Board;

public abstract class AI {

    protected EvaluationFunction evaluator;
    private double fitness;
    public abstract int[] getBestMove(Board board);

    public double evaluateFitness(int gamesToBeSimmed, int boardSize){
        long startTime = System.nanoTime();
        AI stupid = new Stupid();
        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1:gamesToBeSimmed;
        GenericTest.test(this, stupid, gamesToBeSimmed/2, boardSize);
        int winsFirstMove = GenericTest.getPlayer1Wins();
        GenericTest.test(stupid, this, gamesToBeSimmed/2, boardSize);
        int winsSecondMove = GenericTest.getPlayer2Wins();
        this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;
        long endTime = System.nanoTime();
        System.out.println("Full AI evaluateFitness cycle: " + (endTime-startTime)/1000000);
        return (winsFirstMove + winsSecondMove)/gamesToBeSimmed;
    }

    public double getFitness() {
        return fitness;
    }

    public void setEvaluator(EvaluationFunction evaluator) {
        this.evaluator = evaluator;
    }

    public EvaluationFunction getEvaluator() {
        return evaluator;
    }
}
