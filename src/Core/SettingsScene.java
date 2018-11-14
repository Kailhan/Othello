package Core;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class SettingsScene extends VBox {

    private Stage primaryStage;
    private Scene scene;
    private Button submit;
    private Button loadBoard;
    private ComboBox<String> difficulty;
    private ComboBox<String> playerMode;
    private ComboBox<String> size;

    private static int action_difficultyLevel;
    private static int action_gameMode;
    private static int action_boardSize;
    private static Board action_Board;
    private int windowSize = 800;

    public SettingsScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Othello Game - Settings");
        Label label = new Label("Welcome to the Othello game!");
        label.setTextFill(Color.web("#FFFFFF"));

        submit = new Button("Start");
        submit.setOnAction(e -> {
            Settings settings = new Settings(action_difficultyLevel, action_gameMode, action_boardSize, action_Board);    //instantiating the settings object with the int values
            GameScene gameScene = new GameScene(primaryStage, settings);
            Node source = (Node) e.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Othello Game");
            this.primaryStage.setScene(gameScene.getGameScene());
            this.primaryStage.show();
        });
        loadBoard = new Button("Load a custom board");
        loadBoard.setOnAction(e -> {
            File recordsDir = new File(System.getProperty("user.home"), ".Othello/boards");
            if (! recordsDir.exists()) {
                recordsDir.mkdirs();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(recordsDir);
            fileChooser.setTitle("Open Board File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(selectedFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                action_Board = (Board) objectInputStream.readObject();
                objectInputStream.close();
            } catch (Exception err) {
                err.printStackTrace();
            }
        });

        difficulty = new ComboBox<>();
        difficulty.getItems().addAll("Mcts", "Stupid", "Minmax");
        difficulty.setPromptText("Select difficulty level");

        difficulty.setOnAction(e -> {
            if (difficulty.getValue() == "Mcts") {
                action_difficultyLevel = Settings.MCTS;
            } else if (difficulty.getValue() == "Stupid") {
                action_difficultyLevel = Settings.STUPID;
            } else if (difficulty.getValue() == "Minmax") {
                action_difficultyLevel = Settings.MINMAX;
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
            } else if (size.getValue() == "Medium") {
                action_boardSize = Settings.SIZE_MEDIUM;
            } else {
                action_boardSize = Settings.SIZE_LARGE;
            }
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, difficulty, playerMode, size, loadBoard, submit);
        layout.setAlignment(Pos.CENTER);
        File backgrFile = new File("src/Assets/Othello.jpg");
        BackgroundImage myBI= new BackgroundImage(new Image(backgrFile.toURI().toString(),800, 600,true,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout.setBackground(new Background(myBI));
        scene = new Scene(layout, 800, 600);
    }

    public Scene getSettingsScene() {
        return scene;
    }
}
