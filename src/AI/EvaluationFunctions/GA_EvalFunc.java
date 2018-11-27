package AI.EvaluationFunctions;

public class GA_EvalFunc extends EvaluationFunction {

    private int[][] small;
    private int[][] medium;
    private int[][] large;

    public int[][] setTerritory(int size) {
        small = new int[4][4];
        medium = new int[6][6];
        large = new int[8][8];
        return null;
    }
}
