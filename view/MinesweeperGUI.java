package view;

import model.Board;

import java.awt.Dimension;
import java.awt.Toolkit;

import controller.Observer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application {

    private static final int[] easyNums = new int[]{9, 10};
    private static final int[] medNums = new int[]{16, 40};
    private static final int[] hardNums = new int[]{24, 99};

    private static Stage home;
    private static Stage difficultySelection;
    private static Stage gameStage;
    private static double[] dims = screenDims();
    private static Board game;

    public static double[] screenDims() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return new double[]{dim.getHeight(), dim.getWidth()};
    }

    private static void diffSelect() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Select a difficulty.");
        label.setFont(new Font(20));

        Button easy = GUIFactory.difficultyButton("Easy", "Board: 9 x 9 \nBombs: 10");
        easy.setOnAction(e -> {
            game = new Board(easyNums[0], easyNums[0], easyNums[1]);
            home.hide();
            difficultySelection.hide();
            gameStage = newGameScreen(easyNums[0], easyNums[0], easyNums[1]);
            gameStage.show();
        });

        Button med = GUIFactory.difficultyButton("Medium", "Board: 16 x 16 \nBombs: 40");
        med.setOnAction(e -> {
            game = new Board(medNums[0], medNums[0], medNums[1]);
            home.hide();
            difficultySelection.hide();
            gameStage = newGameScreen(medNums[0], medNums[0], medNums[1]);
            gameStage.show();
        });

        Button hard = GUIFactory.difficultyButton("Hard", "Board: 24 x 24 \nBombs: 99");
        hard.setOnAction(e -> {
            game = new Board(hardNums[0], hardNums[0], hardNums[1]);
            home.hide();
            difficultySelection.hide();
            gameStage = newGameScreen(hardNums[0], hardNums[0], hardNums[1]);
            gameStage.show();
        });

        box.getChildren().add(label);
        box.getChildren().add(easy);
        box.getChildren().add(med);
        box.getChildren().add(hard);

        difficultySelection = new Stage();
        difficultySelection.setScene(new Scene(box));
        difficultySelection.setTitle("Difficulty Selection");
        difficultySelection.setAlwaysOnTop(true);
        difficultySelection.setHeight(dims[0] / 4);
        difficultySelection.setWidth(dims[1] / 4);

        difficultySelection.show();
    }

    private static Stage newGameScreen(int rows, int cols, int bombs) {
        game = new Board(rows, cols, bombs);

        HBox top = new HBox();

        Label gameState = new Label(game.getGameState().toString());
        top.getChildren().add(gameState);

        Label score = new Label(Integer.toString(game.getRevealed()));
        top.getChildren().add(score);

        Label bombsLeft = new Label("0");
        top.getChildren().add(bombsLeft);

        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            gameStage.hide();
            gameStage = newGameScreen(rows, cols, bombs);
            gameStage.show();
        });
        top.getChildren().add(newGame);

        Button quit = new Button("Quit Game");
        quit.setOnAction(e -> {
            gameStage.hide();
            home.show();
        });
        top.getChildren().add(quit);
        
        GridPane board = new GridPane();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Button b = GUIFactory.gameButtons(i, j, game, i == (rows - 1) && j == (cols - 1));
                board.add(b, i, j);
            }
        }
        
        VBox outer = new VBox();
        outer.getChildren().add(top);
        outer.getChildren().add(board);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(outer));

        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        home = stage;
        home.setTitle("Minesweeper");
        home.setHeight(dims[0] / 2);
        home.setWidth(dims[1] / 2);

        Label title = new Label("Minesweeper");
        title.setFont(new Font(80));

        Button newGame = new Button("New Game");
        newGame.setPrefHeight(dims[0] / 20);
        newGame.setPrefWidth(dims[1] / 10);
        newGame.setOnAction(event -> diffSelect());

        // TODO: Home background
        // VBox left = new VBox();
        // left.setAlignment(Pos.CENTER_LEFT);
        // left.setMaxWidth(home.getWidth() - title.getWidth() / 2);
        // left.getChildren().add(GUIFactory.imageFactory("resources/images/minesweeper_image.jpeg", title.getWidth()));
        // left.getChildren().add(GUIFactory.imageFactory("resources/images/minesweeper_image.jpeg", title.getWidth()));
        // left.getChildren().add(GUIFactory.imageFactory("resources/images/minesweeper_image.jpeg", title.getWidth()));

        // VBox right = new VBox();
        // right.setAlignment(Pos.CENTER_RIGHT);
        // right.setMaxWidth(home.getWidth() - title.getWidth() / 2);
        // right.getChildren().add(GUIFactory.imageFactory("resources/images/minesweeper_image.jpeg", title.getWidth()));
        // right.getChildren().add(GUIFactory.imageFactory("resources/images/minesweeper_image.jpeg", title.getWidth()));
        // right.getChildren().add(GUIFactory.imageFactory("resources/images/minesweeper_image.jpeg", title.getWidth()));

        VBox top = new VBox();
        top.setAlignment(Pos.TOP_CENTER);
        top.getChildren().add(title);

        VBox bottom = new VBox();
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.getChildren().add(newGame);

        VBox mid = new VBox();
        mid.setAlignment(Pos.CENTER);
        mid.getChildren().add(top);
        mid.getChildren().add(bottom);

        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        // box.getChildren().add(left);
        box.getChildren().add(mid);
        // box.getChildren().add(right);

        home.setScene(new Scene(box));

        home.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}
