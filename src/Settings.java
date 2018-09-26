public class Settings {

    //values to return in the class object:
    private int difficultyLevel;
    private int gameMode;
    private int boardSize;
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int HvH = 0;
    public static final int HvA = 1;
    public static final int AvA = 2;
    public static final int SMALL = 0;
    public static final int LARGE = 2;


    public Settings() {
        this(EASY,HvH,SMALL);
    }

    public Settings(int difficultyLevel, int gameMode, int boardSize) {
        this.difficultyLevel = difficultyLevel;
        this.gameMode = gameMode;
        this.boardSize = boardSize;
    }

    //getters & setters

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    public int getGameMode() {
        return gameMode;
    }
    public int getBoardSize() {
        return boardSize;
    }

}
