package AI.Tests;

import AI.*;
import Core.Board;

public class EvaluationFunctionTest {

    private static final int SIZE = 8;
    private static final int GAMES = 1000;
    private static final int DEPTH = 3;

    public static void main(String[] args){

//        for(int i =0; i < 10; i++) {
            double[] polyWeights = new double[]{10, 30, 100, 30, 50, 20, 10, 70, 100};
            test1(polyWeights);
//        }

//
//        double[] polyWeight1 = new double[] {20,10,100,0,0,0,0,0,0};
//        double[] polyWeight2 = new double[] {10,20,100,0,0,0,0,0,0};
//        test2(polyWeight1,polyWeight2);

    }

    private static void test1(double[] polyWeights){

        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        Board board = new Board();
        GenericTest generic = new GenericTest();
        Stupid stupid = new Stupid();

        EvaluationFunction evaluator = new EvaluationFunction(board) ;
        evaluator.setWeightPoly(polyWeights);
        MiniMaxAlph miniMaxAlph = new MiniMaxAlph(DEPTH, board);

        generic.test( stupid,evaluator,GAMES, SIZE);


        System.out.println("EvaluationFunction wins: " + (generic.getPlayer1Wins()));
        System.out.println("Stupid wins: " + generic.getPlayer2Wins());
        System.out.println("Draws: " + generic.getDraws());
    }

    private static void test2(double[] polyWeights1, double[] polyWeights2){

        Board board = new Board();
        GenericTest generic = new GenericTest();
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        EvaluationFunction evaluator1 = new EvaluationFunction(board);
        EvaluationFunction evaluator2 = new EvaluationFunction(board);

        evaluator1.setWeightPoly(polyWeights1);
        evaluator2.setWeightPoly(polyWeights2);

        generic.test(evaluator1,evaluator2,GAMES,SIZE);
        System.out.println("Evaluationfunction 1 wins: " + generic.getPlayer1Wins());
        System.out.println("Evaluationfunction 2 wins: " + generic.getPlayer2Wins());
        System.out.println("Draws: " + generic.getDraws());

    }

    private static void test3(){

    }
}
