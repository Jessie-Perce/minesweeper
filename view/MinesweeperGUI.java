package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application {
    @Override
    public void start(Stage stage) {
        HBox settings = new HBox();

        Scene scene = new Scene(settings);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
