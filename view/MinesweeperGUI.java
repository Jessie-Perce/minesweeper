package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application {
    public Button squareMaker() {
        Button square = new Button();

        square.setPrefSize(25, 25);
        square.setMinSize(25, 25);

        return square;
    }

    public GridPane boardMaker(int rows, int cols) {
        GridPane board = new GridPane();

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                Button button = squareMaker();
                GridPane.setConstraints(button, i, j);
                board.getChildren().add(button);
            }
        }
        return board;
    }

    @Override
    public void start(Stage stage) {
        GridPane board = boardMaker(5, 6);

        Scene scene = new Scene(board);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
