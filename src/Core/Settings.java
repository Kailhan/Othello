package Core;

public class Settings {

    //values to return in the class object:
    private String difficultyLevel;
    private String gameMode;
    private String boardSize;

    //getters & setters
    public void setDifficultyLevel(String difficultyLevel){
        this.difficultyLevel = difficultyLevel;
    }
    public void setGameMode (String gameMode) {
        this.gameMode = gameMode;
    }
    public void setBoardSize (String boardSize) {
        this.boardSize = boardSize;
    }
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    public String getGameMode() {
        return gameMode;
    }
    public String getBoardSize() {
        return boardSize;
    }
    public static Settings getSettings(String gameMode, String boardSize, String difficultyLevel) {
        Settings settings = new Settings();
        settings.setGameMode(gameMode);
        settings.setBoardSize(boardSize);
        settings.setDifficultyLevel(difficultyLevel);
        System.out.println(settings.boardSize);
        System.out.println(settings.difficultyLevel);
        System.out.println(settings.gameMode);
        return settings;
    }
}
