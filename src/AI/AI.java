package AI;
import AI.Tests.GenericTest;
import Core.Board;

public abstract class AI {

    protected EvaluationFunction evaluator;
    private double fitness;
    private double winsFirstMove;
    private double winsSecondMove;
    public abstract int[] getBestMove(Board board);

    public double evaluateFitness(int gamesToBeSimmed, int boardSize){
        AI stupid = new Stupid();
        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1:gamesToBeSimmed;
        GenericTest.test(this, stupid, gamesToBeSimmed/2, boardSize);
        winsFirstMove = GenericTest.getPlayer1Wins();
        GenericTest.test(stupid, this, gamesToBeSimmed/2, boardSize);
        winsSecondMove = GenericTest.getPlayer2Wins();
        //System.out.println("this.fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;");
        //System.out.println(gamesToBeSimmed);
        fitness = (winsFirstMove + winsSecondMove)/gamesToBeSimmed;
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

    public double getWinsFirstMove() {
        return winsFirstMove;
    }

    public double getWinsSecondMove() {
        return winsSecondMove;
    }
}
