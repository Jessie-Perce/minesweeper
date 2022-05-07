package view;

import controller.Observer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import model.Board;
import model.Square;

public class GUIFactory {

    private static double[] dims = MinesweeperGUI.screenDims();

    public static Button difficultyButton(String text, String tooltip) {
        Button b = new Button(text);
        b.setPrefHeight(40);
        b.setPrefWidth(120);

        return b;
    }

    public static ImageView imageFactory(String path, double offset) {
        Image image = new Image(path);

        ImageView view = new ImageView(image);
        view.setFitWidth(dims[1] - offset / 2);

        return view;
    }

    public static Button gameButtons(int row, int col, Board game, boolean last) {
        Button b = new Button();
        b.setPrefSize(25, 25);
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                MouseButton mb = e.getButton();

                if (mb == MouseButton.PRIMARY) {
                    game.move(row, col);
                } else if (mb == MouseButton.SECONDARY) {
                    game.mark(row, col);
                }
            }
        });

        game.registerObserver(new Observer() {
            @Override
            public void handle() {
                Square sq = game.getSquare(row, col);

                if (sq.getVisibility()) {
                    b.disarm();
                    b.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(0), Insets.EMPTY)));
                    String value = Integer.toString(sq.getValue());
                    value = !value.equals("-1") ? value : "X";
                    b.setText(value);
                } else if (sq.getMarked()) {
                    b.disarm();
                    b.setText("#");
                } else {
                    b.arm();
                    b.setText("");
                }
            }
        });

        return b;
    }
}
