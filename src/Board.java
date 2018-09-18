public class Board {

    private int[][] boardGrid;
    private final int EMPTY = 0;
    private final int BLACK = 1;
    private final int WHITE = 2;

    private int size;

    public Board() {
        this(8);
    }

    public Board(int size) {
        size = (size < 4) ? 4: size; //enforces minimum size of 4
        size = ((size % 2) == 0) ? size : (size-1); //makes sure board is even
        this.size = size;
        boardGrid = new int[size][size];
        boardGrid[(size/2)-1][(size/2)-1] = BLACK;
        boardGrid[(size/2)-1][(size/2)] = WHITE;
        boardGrid[(size/2)][(size/2)-1] = WHITE;
        boardGrid[(size/2)][(size/2)] = BLACK;
    }

    public void displayBoardGrid() {
        for(int r = 0; r < size; r++) {
            for(int c = 0; c < size; c++)
                System.out.print(boardGrid[r][c] + " ");
            System.out.println();
        }
    }

    public int[][] getBoardGrid() {
        return boardGrid;
    }

    public void setBoardGrid(int[][] boardGrid) {
        this.boardGrid = boardGrid;
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfEmptySquares(){
        int numberOfEmptySquares = 0;
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[i].length; j++) {
                if(boardGrid[i][j] == 0) numberOfEmptySquares++;
            }
        }
        return  numberOfEmptySquares;
    }

    public int getNumberOfBlackSquares(){
        int numberOfBlackSquares = 0;
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[i].length; j++) {
                if(boardGrid[i][j] == 1) numberOfBlackSquares++;
            }
        }
        return  numberOfBlackSquares;
    }

    public int getNumberOfWhiteSquares(){
        int numberOfWhiteSquares = 0;
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[i].length; j++) {
                if(boardGrid[i][j] == 2) numberOfWhiteSquares++;
            }
        }
        return  numberOfWhiteSquares;
    }
}