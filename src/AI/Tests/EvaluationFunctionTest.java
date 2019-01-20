package AI.Tests;

import AI.*;
import Core.Board;
import sun.net.www.content.text.Generic;

public class EvaluationFunctionTest {

    private static final int SIZE = 8;
    private static final int GAMES = 10000;
    private static final int DEPTH = 1;

    public static void main(String[] args){

        for(int i =0; i<10; i++) {
            test1(GAMES);
        }

//        double[] polyWeights2 = new double[] {19.31,0.0,0.0,16.1,0.0,0.0,12.59,0.0,0.0};
//        test2(polyWeights2);

    }

    private static void test1(int gamesToBeSimmed){

        Board board = new Board();
        double[] polyWeights = new double[] {19.31,0.0,0.0,16.1,0.0,0.0,12.59,0.0,0.0};
        //double[] polyWeights2 = new double[] {0,1,0,100,-1,0,75,2.5,-0.05};


        Stupid stupid = new Stupid();
//        EvaluationFunction evaluator = new EvaluationFunction(board);
//        EvaluationFunction evaluatorStatic = new EvaluationFunction(board,polyWeights);
        EvalRandom evaluator = new EvalRandom(board);
        EvalRandom evaluatorStatic = new EvalRandom(board, polyWeights);

        gamesToBeSimmed = (gamesToBeSimmed < 2) ? 2 : gamesToBeSimmed;
        gamesToBeSimmed = (gamesToBeSimmed % 2 != 0) ? gamesToBeSimmed + 1: gamesToBeSimmed;

        GenericTest.test(evaluator,stupid, gamesToBeSimmed/2, SIZE);
        int  winsFirstMove = GenericTest.getPlayer1Wins();
        int  PolyWins = GenericTest.getPlayer2Wins();
        int draws1 = GenericTest.getDraws();
        GenericTest.test(stupid, evaluator, gamesToBeSimmed/2, SIZE);
        int winsSecondMove = GenericTest.getPlayer2Wins();
        int polywins2 = GenericTest.getPlayer1Wins();
        int draws2 = GenericTest.getDraws();

        System.out.println("Evaluator win first move: " + winsFirstMove);
        //System.out.println("poly wins: " + PolyWins);
        System.out.println("Evaluator win second move: " + winsSecondMove);
        //System.out.println("poly wins " + polywins2);
        System.out.println("Draws: " +  (draws1+draws2));
        System.out.println("Total wins Evaluator: " + (winsFirstMove+winsSecondMove));
        //System.out.println("Total wins Poly: " + (PolyWins+polywins2));
        System.out.println("--------------------------------------");

    }

    private static void test2(double[] polyWeights1){

        Board board = new Board();
        GenericTest generic = new GenericTest();
        GameTree gameTree = new GameTree(DEPTH);
        Node<Board> root = gameTree.createTree();

        //EvaluationFunction evaluator = new EvaluationFunction(board);
        EvalRandom evaluator = new EvalRandom(board);
        EvalRandom evaluator2 = new EvalRandom(board,polyWeights1);



        generic.test(evaluator,evaluator2,GAMES,SIZE);
        System.out.println("Evaluationfunction 1 wins: " + generic.getPlayer1Wins());
        System.out.println("Evaluationfunction 2 wins: " + generic.getPlayer2Wins());
        System.out.println("Draws: " + generic.getDraws());

    }
}
