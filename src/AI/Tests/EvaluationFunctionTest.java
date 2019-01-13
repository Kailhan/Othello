package AI.Tests;

import AI.EvaluationFunction;
import AI.Stupid;
import Core.Board;

public class EvaluationFunctionTest {


    public static void main(String[] args){

        test1();
    }

    private static void test1(){

        Board board = new Board();
        GenericTest generic = new GenericTest();
        Stupid stupid = new Stupid();
        EvaluationFunction evaluator = new EvaluationFunction(board) ;

        generic.test(evaluator, stupid,1000, 8);

        System.out.println("EvaluationFunction wins: " + (generic.getPlayer1Wins()));
        System.out.println("Stupid wins: " + generic.getPlayer2Wins());
        System.out.println("Draws: " + generic.getDraws());

    }
}
