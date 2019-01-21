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
import java.io.ObjectInputStream;

/**
 * In game screen where we can determine board size and starting AI's
 * @author Kailhan Hokstam
 */
public class SettingsScene extends VBox {

    private Stage primaryStage;
    private Scene scene;
    private Button submit;
    private Button loadBoard;
    private ComboBox<String> AI1;
    private ComboBox<String> AI2;
    private ComboBox<String> size;

    private static String[] AIs;
    private static int AI1Level;
    private static int AI2level;
    private static int action_boardSize;
    private static Board action_Board;
    private int windowSize = 800;

    public SettingsScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Othello Game - Settings");
        Label label = new Label("Welcome to the Othello game!");
        label.setTextFill(Color.web("#FFFFFF"));
        AIs = Settings.getAIs();

        submit = new Button("Start");
        submit.setOnAction(e -> {
            Settings settings = new Settings(AI1Level, AI2level, action_boardSize, action_Board);    //instantiating the settings object with the int values
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

        AI1 = new ComboBox<>();
        AI1.getItems().addAll(AIs);
        AI1.setPromptText("Select first AI");

        AI1.setOnAction(e -> {
            for (int i = 0; i < AIs.length; i++){
                if (AI1.getValue() == AIs[i]){
                    AI1Level = i;
                }
            }
        });

        AI2 = new ComboBox<>();
        AI2.getItems().addAll(AIs);
        AI2.setPromptText("Select second AI");

        AI2.setOnAction(e -> {
            for (int i = 0; i < AIs.length; i++){
                if (AI2.getValue() == AIs[i]){
                    AI2level = i;
                }
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
        layout.getChildren().addAll(label, AI1, AI2, size, loadBoard, submit);
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
