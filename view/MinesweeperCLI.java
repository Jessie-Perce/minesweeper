package view;

import model.Board;
import model.GameState;
import java.util.Scanner;

public class MinesweeperCLI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        String[] tokens;
        Board game = null;

        boolean sentinel = true;

        while (sentinel) {
            input = sc.nextLine();
            tokens = input.split(" ");

            switch (tokens[0]) {
                case ("start") :
                    if (tokens.length > 3) {
                        int rows = Integer.parseInt(tokens[1]);
                        int cols = Integer.parseInt(tokens[2]);
                        int bombs = Integer.parseInt(tokens[3]);
                        game = new Board(rows, cols, bombs);
                    } else {
                        System.out.println("Invalid game: hit enter for help.");
                    }
                    break;
                case ("restart") :
                    if (tokens.length > 3) {
                        int rows = Integer.parseInt(tokens[1]);
                        int cols = Integer.parseInt(tokens[2]);
                        int bombs = Integer.parseInt(tokens[3]);
                        game = new Board(rows, cols, bombs);
                    } else {
                        System.out.println("Invalid game: hit enter for help. Otherwise this game will continue.");
                    }
                    break;
                case ("reveal") :
                    if (game != null) {
                        int row = Integer.parseInt(tokens[1]);
                        int col = Integer.parseInt(tokens[2]);

                        if (row < game.getMaxRows() && col < game.getMaxCols()) {
                            game.reveal(row, col);
                        } else {
                            System.out.println("Not valid dumbass.");
                        }
                    }
                    break;
                case ("mark") :
                    if (game != null) {
                        int row = Integer.parseInt(tokens[1]);
                        int col = Integer.parseInt(tokens[2]);

                        if (row < game.getMaxRows() && col < game.getMaxCols()) {
                            game.mark(row, col);
                        } else {
                            System.out.println("Not valid dumbass.");
                        }
                    }
                    break;
                case ("exit") :
                    sentinel = false;
                    break;
                default :
                    System.out.println("'start [rows] [cols] [bombs]': starts a game with the specified number of rows, columns, and bombs.");
                    System.out.println("'restart [rows] [cols] [bombs]': starts a new game with the specified number of rows, columns, and bombs.");
                    System.out.println("'reveal [row] [col]': reveals a square at the specified row and column.");
                    System.out.println("'mark [row] [col]': marks a square at a specified row and column as a bomb.");
                    System.out.println("'exit': exits the program.");
            }

            if (game != null) {
                System.out.println(game.getBoardString());
            }

            if (game != null && game.getGameState() == GameState.LOST) {
                System.out.println("Sorry! Better luck next time!");
            } else if (game != null && game.getGameState() == GameState.WON) {
                System.out.println("Congratulations! You won!");
            }
        }

        sc.close();
    }
}
