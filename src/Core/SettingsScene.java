package Core;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class SettingsScene extends VBox {

    private Stage primaryStage;
    private Scene scene;
    private Button submit;
    private ComboBox<String> difficulty;
    private ComboBox<String> playerMode;
    private ComboBox<String> size;

    private static int action_difficultyLevel;
    private static int action_gameMode;
    private static int action_boardSize;
    private int windowSize = 800;

    public SettingsScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Othello Game - Core.Settings");
        Label label = new Label("Welcome to the Othello game!");

        submit = new Button("Start");
        submit.setOnAction(e -> {
            Settings settings = new Settings(action_difficultyLevel, action_gameMode, action_boardSize);    //instantiating the settings object with the int values
            GameScene gameScene = new GameScene(primaryStage, settings);
            Node source = (Node) e.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Othello Game");
            this.primaryStage.setScene(gameScene.getGameScene());
            this.primaryStage.show();
        });

        difficulty = new ComboBox<>();
        difficulty.getItems().addAll("Easy", "Medium", "Hard");
        difficulty.setPromptText("Select difficulty level");
        difficulty.setOnAction(e -> {
            if (difficulty.getValue() == "Easy") {
                action_difficultyLevel = Settings.EASY;
            } else if (difficulty.getValue() == "Medium") {
                action_difficultyLevel = Settings.MEDIUM;
            } else {
                action_difficultyLevel = Settings.HARD;
            }
        });

        playerMode = new ComboBox<>();
        playerMode.getItems().addAll("Human vs Human", "Human vs AI", "AI vs AI");
        playerMode.setPromptText("Select game mode");
        playerMode.setOnAction(e -> {
            if (playerMode.getValue() == "Human vs Human") {
                action_gameMode = Settings.HvH;
            } else if (playerMode.getValue() == "Human vs AI") {
                action_gameMode = Settings.HvA;
            } else {
                action_gameMode = Settings.AvA;
            }
        });

        size = new ComboBox<>();
        size.getItems().addAll("Small", "Medium", "Large");
        size.setPromptText("Select board size");
        size.setOnAction(e -> {
            if (size.getValue() == "Small") {
                action_boardSize = Settings.SIZE_SMALL;
            } else if (size.getValue() == "MEDIUM") {
                action_boardSize = Settings.SIZE_MEDIUM;
            } else {
                action_boardSize = Settings.SIZE_LARGE;
            }
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, difficulty, playerMode, size, submit);
        layout.setAlignment(Pos.CENTER);
        scene = new Scene(layout, windowSize, windowSize);
    }

    public Scene getSettingsScene() {
        return scene;
    }
}


