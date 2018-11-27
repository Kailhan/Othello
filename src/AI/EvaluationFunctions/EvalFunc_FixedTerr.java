package AI.EvaluationFunctions;

import AI.AI;
import Core.Board;
import Core.Logic;
import Core.Settings;

public class EvalFunc_FixedTerr extends EvaluationFunction {

    private int[][] cellValues;

    public EvalFunc_FixedTerr(Board cBoard){
        this.board = cBoard;
        this.boardGrid = cBoard.getBoardGrid();
        this.settings = new Settings();
        this.cellValues = new int[board.getSize()][board.getSize()];
    }

    public EvalFunc_FixedTerr(){
        this(new Board());
    }

    public int[][] setTerritory(){
        if(board.getSize() == 4){

            cellValues[0][0]  = 10;
            cellValues[3][3]  = 10;
            cellValues[3][0]  = 10;
            cellValues[0][3]  = 10;

            cellValues[0][1]  = 5;
            cellValues[0][2]  = 5;
            cellValues[1][0]  = 5;
            cellValues[2][0]  = 5;
            cellValues[3][1]  = 5;
            cellValues[3][2]  = 5;
            cellValues[2][3]  = 5;
            cellValues[1][3]  = 5;

            return cellValues;
        }

        if(board.getSize() == 6) {

            cellValues[0][0] = 10;
            cellValues[5][5] = 10;
            cellValues[0][5] = 10;
            cellValues[5][0] = 10;

            cellValues[0][1] = 5;
            cellValues[1][1] = 5;
            cellValues[1][0] = 5;
            cellValues[0][4] = 5;
            cellValues[1][4] = 5;
            cellValues[1][5] = 5;
            cellValues[4][0] = 5;
            cellValues[4][1] = 5;
            cellValues[5][1] = 5;
            cellValues[5][4] = 5;
            cellValues[4][4] = 5;
            cellValues[4][5] = 5;

            cellValues[1][2] = 6;
            cellValues[1][3] = 6;
            cellValues[2][1] = 6;
            cellValues[3][1] = 6;
            cellValues[4][2] = 6;
            cellValues[4][3] = 6;
            cellValues[2][4] = 6;
            cellValues[3][4] = 6;

            cellValues[2][0] = 8;
            cellValues[3][0] = 8;
            cellValues[0][2] = 8;
            cellValues[0][3] = 8;
            cellValues[2][5] = 8;
            cellValues[3][5] = 8;
            cellValues[5][2] = 8;
            cellValues[5][3] = 8;

            return cellValues;
        }

        if(board.getSize() == 8){
            
            cellValues[0][0] = 10;
            cellValues[7][7] = 10;
            cellValues[0][7] = 10;
            cellValues[7][0] = 10;

            cellValues[0][1] = 5;
            cellValues[1][1] = 5;
            cellValues[1][0] = 5;
            cellValues[0][4] = 5;
            cellValues[1][4] = 5;
            cellValues[1][5] = 5;
            cellValues[6][0] = 5;
            cellValues[6][1] = 5;
            cellValues[7][1] = 5;
            cellValues[7][6] = 5;
            cellValues[6][6] = 5;
            cellValues[6][7] = 5;

            cellValues[2][1] = 6;
            cellValues[3][1] = 6;
            cellValues[4][1] = 6;
            cellValues[5][1] = 6;
            cellValues[1][2] = 6;
            cellValues[1][3] = 6;
            cellValues[1][4] = 6;
            cellValues[1][5] = 6;
            cellValues[2][6] = 6;
            cellValues[3][6] = 6;
            cellValues[4][6] = 6;
            cellValues[5][6] = 6;
            cellValues[6][2] = 6;
            cellValues[6][3] = 6;
            cellValues[6][4] = 6;
            cellValues[6][5] = 6;

            cellValues[2][2] = 7;
            cellValues[2][3] = 7;
            cellValues[2][4] = 7;
            cellValues[2][5] = 7;
            cellValues[5][2] = 7;
            cellValues[5][3] = 7;
            cellValues[5][4] = 7;
            cellValues[5][5] = 7;
            cellValues[3][2] = 7;
            cellValues[4][2] = 7;
            cellValues[3][5] = 7;
            cellValues[4][5] = 7;

            cellValues[2][0] = 8;
            cellValues[3][0] = 8;
            cellValues[4][0] = 8;
            cellValues[5][0] = 8;
            cellValues[0][2] = 8;
            cellValues[0][3] = 8;
            cellValues[0][4] = 8;
            cellValues[0][5] = 8;
            cellValues[2][7] = 8;
            cellValues[3][7] = 8;
            cellValues[4][7] = 8;
            cellValues[5][7] = 8;
            cellValues[7][2] = 8;
            cellValues[7][3] = 8;
            cellValues[7][4] = 8;
            cellValues[7][5] = 8;
            return cellValues;
        }
        
        return null;
    }

}