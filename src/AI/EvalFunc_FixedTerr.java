package AI;

import Core.Board;
import Core.Logic;
import Core.Settings;

public class EvalFunc_FixedTerr extends EvaluationFunction {

    private int[][] small;
    private int[][] medium;
    private int[][] large;

    public int[][] setTerritory(int j){

        small = new int[4][4];
        medium = new int[6][6];
        large = new int[8][8];

        if(j == 4){

            small[0][0]  = 10;
            small[3][3]  = 10;
            small[3][0]  = 10;
            small[0][3]  = 10;

            small[0][1]  = 5;
            small[0][2]  = 5;
            small[1][0]  = 5;
            small[2][0]  = 5;
            small[3][1]  = 5;
            small[3][2]  = 5;
            small[2][3]  = 5;
            small[1][3]  = 5;

            return small;
        }

        if(j == 6) {

            medium[0][0] = 10;
            medium[5][5] = 10;
            medium[0][5] = 10;
            medium[5][0] = 10;

            medium[0][1] = 5;
            medium[1][1] = 5;
            medium[1][0] = 5;
            medium[0][4] = 5;
            medium[1][4] = 5;
            medium[1][5] = 5;
            medium[4][0] = 5;
            medium[4][1] = 5;
            medium[5][1] = 5;
            medium[5][4] = 5;
            medium[4][4] = 5;
            medium[4][5] = 5;

            medium[1][2] = 6;
            medium[1][3] = 6;
            medium[2][1] = 6;
            medium[3][1] = 6;
            medium[4][2] = 6;
            medium[4][3] = 6;
            medium[2][4] = 6;
            medium[3][4] = 6;

            medium[2][0] = 8;
            medium[3][0] = 8;
            medium[0][2] = 8;
            medium[0][3] = 8;
            medium[2][5] = 8;
            medium[3][5] = 8;
            medium[5][2] = 8;
            medium[5][3] = 8;

            return medium;
        }

        if(j == 8){
            
            large[0][0] = 10;
            large[7][7] = 10;
            large[0][7] = 10;
            large[7][0] = 10;

            large[0][1] = 5;
            large[1][1] = 5;
            large[1][0] = 5;
            large[0][4] = 5;
            large[1][4] = 5;
            large[1][5] = 5;
            large[6][0] = 5;
            large[6][1] = 5;
            large[7][1] = 5;
            large[7][6] = 5;
            large[6][6] = 5;
            large[6][7] = 5;

            large[2][1] = 6;
            large[3][1] = 6;
            large[4][1] = 6;
            large[5][1] = 6;
            large[1][2] = 6;
            large[1][3] = 6;
            large[1][4] = 6;
            large[1][5] = 6;
            large[2][6] = 6;
            large[3][6] = 6;
            large[4][6] = 6;
            large[5][6] = 6;
            large[6][2] = 6;
            large[6][3] = 6;
            large[6][4] = 6;
            large[6][5] = 6;

            large[2][2] = 7;
            large[2][3] = 7;
            large[2][4] = 7;
            large[2][5] = 7;
            large[5][2] = 7;
            large[5][3] = 7;
            large[5][4] = 7;
            large[5][5] = 7;
            large[3][2] = 7;
            large[4][2] = 7;
            large[3][5] = 7;
            large[4][5] = 7;

            large[2][0] = 8;
            large[3][0] = 8;
            large[4][0] = 8;
            large[5][0] = 8;
            large[0][2] = 8;
            large[0][3] = 8;
            large[0][4] = 8;
            large[0][5] = 8;
            large[2][7] = 8;
            large[3][7] = 8;
            large[4][7] = 8;
            large[5][7] = 8;
            large[7][2] = 8;
            large[7][3] = 8;
            large[7][4] = 8;
            large[7][5] = 8;

            return large;
        }
        
        return null;
    }

}