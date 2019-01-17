package AI.Tests;

import AI.*;
import Core.Board;

public class EvaluationFunctionTest {

    private static final int SIZE = 8;
    private static final int GAMES = 100;
    private static final int DEPTH = 1;

    public static void main(String[] args){

//        double[] polyWeights = new double[9];
//      test1(polyWeights);





         double[] polyWeight1 = new double[] {10.282435566733383,-0.2208326435675777,2.907362038915502,-18.54089231016665,17.906558153459244,-29.34779296568235,1.4012722350153906,17.230612271737314,7.354460684853822};
//        double[] polyWeight2 = new double[] {10,20,100,0,0,0,0,0,0};
        test2(polyWeight1);
    }

    private static int test1(double[] polyWeights){

        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        Board board = new Board();
        GenericTest generic = new GenericTest();
        Stupid stupid = new Stupid();

        EvaluationFunction evaluator = new EvaluationFunction(board) ;
//        double[] polyWeights2 = new double[] {50,50,50,50,50,50,50,50,50};
//        evaluator.setWeightPoly(polyWeights2);

        generic.test(evaluator,stupid,GAMES, SIZE);


        System.out.println("EvaluationFunction wins: " + (generic.getPlayer1Wins()));
        System.out.println("Stupid wins: " + generic.getPlayer2Wins());
        System.out.println("Draws: " + generic.getDraws());
        return generic.getPlayer1Wins();
    }

    private static void test2(double[] polyWeights1){

        Board board = new Board();
        GenericTest generic = new GenericTest();
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        EvaluationFunction evaluator1 = new EvaluationFunction(board);
        EvaluationFunction evaluator2 = new EvaluationFunction(board);

        evaluator1.setWeightPoly(polyWeights1);
        EvalRandom evaluator3 = new EvalRandom(board);
        MCTS evaluator5 = new MCTS(1000);


        generic.test(evaluator2,evaluator5,GAMES,SIZE);
        System.out.println("Evaluationfunction 1 wins: " + generic.getPlayer1Wins());
        System.out.println("Evaluationfunction 2 wins: " + generic.getPlayer2Wins());
        System.out.println("Draws: " + generic.getDraws());

    }

    private static double[] test3() {

        double[] bestVal = new double[4];
        double score = Integer.MIN_VALUE;
        double cScore;
        double startingH = -100;
        double startingJ = -100;
        double startingI = -100;
        double endingH = 100;
        double endingJ = 100;
        double endingI = 100;
        double incrementH = 20;
        double incrementJ = 20;
        double incrementI = 20;
        int count = 0;


        while(incrementI > 1){
        for (double i = startingI; i < endingI; i += incrementI) {
            for (double j = startingJ; j < endingJ; j += incrementJ) {
                for (double h = startingH; h < endingH; h += incrementH) {
                    double[] polyWeights = new double[]{i, 0, 0, j, 0, 0, h, 0, 0};
                    cScore = test1(polyWeights);
                    if (cScore > score) {
                        score = cScore;
                        bestVal[0] = i;
                        bestVal[1] = j;
                        bestVal[2] = h;
                        bestVal[3] = score;
                        System.out.println("best i: " + bestVal[0]);
                        System.out.println("best j: " + bestVal[1]);
                        System.out.println("best h: " + bestVal[2]);
                        System.out.println("score: " + cScore);
                    }
                }
            }
        }

            System.out.println("FIRST TIMEEEEEE");
            System.out.println();
            System.out.println();


        startingI = bestVal[0] - incrementI;
        startingJ = bestVal[1] - incrementJ;
        startingH = bestVal[2] - incrementH;

        endingI = bestVal[0] + incrementI;
        endingJ = bestVal[1] + incrementJ;
        endingH = bestVal[2] + incrementH;

        count++;
        incrementI = ((Math.abs(startingI) + Math.abs(endingI)) /(count * 10));
        incrementJ = ((Math.abs(startingJ) + Math.abs(endingJ)) /(count * 10));
        incrementH = ((Math.abs(startingH) + Math.abs(endingH)) /(count * 10));


            System.out.println("increment I: " + incrementI);
            System.out.println("starting I: " + startingI);
            System.out.println("ending I: " + endingI);
    }


        return bestVal;
    }
}
