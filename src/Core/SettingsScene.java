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

    private static String action_difficultyLevel;
    private static String action_gameMode;
    private static String action_boardSize;
    private int windowSize = 800;

    public SettingsScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Othello Game - Core.Settings");
        Label label = new Label("Welcome to the Othello game!");

        submit = new Button("Start");
        submit.setOnAction(e -> {
            if (action_difficultyLevel != null && playerMode.getValue() != null && size.getValue() != null) {
                //Core.Settings settings = Core.Settings.getSettings(action_difficultyLevel, action_gameMode, action_boardSize);
                //primaryStage.close();
            }
            GameScene gameScene = new GameScene(primaryStage);
            Node source = (Node)e.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Othello Game");
            this.primaryStage.setScene(gameScene.getGameScene());
            this.primaryStage.show();
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
    }

    public Scene getSettingsScene() {
        return scene;
    }
}