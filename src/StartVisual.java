import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class StartVisual extends Application {
    public static void main(String[] args) {launch(args);}

    private Stage settingsStage;
    private Stage gameStage;
    private Scene scene;
    private Button submit;
    private ComboBox<String> difficulty;
    private ComboBox<String> playerMode;
    private ComboBox<String> size;

    private static String action_difficultyLevel;
    private static String action_gameMode;
    private static String action_boardSize;
    private int windowSize = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.settingsStage = primaryStage;
        settingsStage.setTitle("Othello Game - Settings");
        Label label = new Label("Welcome to the Othello game!");

        submit = new Button("Start");
        submit.setOnAction(e -> {
            if (action_difficultyLevel != null && playerMode.getValue() != null && size.getValue() != null) {
                //Settings settings = Settings.getSettings(action_difficultyLevel, action_gameMode, action_boardSize);
                //settingsStage.close();
            }
            gameStage = new Stage();
            gameStage.setTitle("Othello Game");
            GameScene gameScene = new GameScene();
            gameStage.setScene(new Scene(gameScene.getbPane(), windowSize + gameScene.getTileSize()*3 + gameScene.getGap()*(gameScene.getBoardGrid().length+2), windowSize + gameScene.getGap()*(gameScene.getBoardGrid().length-1), Color.rgb(128, 128, 128)));
            gameStage.show();
            settingsStage.close();
        });

        difficulty = new ComboBox<>();
        difficulty.getItems().addAll("Easy", "Medium", "Hard");
        difficulty.setPromptText("Select difficulty level");
        difficulty.setOnAction(e -> action_difficultyLevel = difficulty.getValue());

        playerMode = new ComboBox<>();
        playerMode.getItems().addAll("Human vs Human", "Human vs AI", "AI vs AI");
        playerMode.setPromptText("Select game mode");
        playerMode.setOnAction(e -> action_gameMode = playerMode.getValue());

        size = new ComboBox<>();
        size.getItems().addAll("Small", "Medium", "Large");
        size.setPromptText("Select board size");
        size.setOnAction(e -> action_boardSize = size.getValue());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, difficulty, playerMode, size, submit);
        layout.setAlignment(Pos.CENTER);
        scene = new Scene(layout, windowSize, windowSize);
        settingsStage.setScene(scene);
        settingsStage.show();
        }

        public void setGameStage(Stage stage) {
            this.gameStage = stage;
        }

}


