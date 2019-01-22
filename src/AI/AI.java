package AI;
import Core.Board;

/**
 * AI interface
 * @author Yvar Hulshof, Marco Rietjens
 */
public abstract class AI {

    protected EvaluationFunction evaluator;
    protected double fitness;
    protected double winsFirstMove;
    protected double winsSecondMove;
    public abstract int[] getBestMove(Board board);
    public abstract double evaluateFitness(int gamesToBeSimmed, int boardSize);

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
