package view;

import model.Board;
import model.GameState;
import java.util.Scanner;

public class MinesweeperCLI {

    private static Board starter(boolean restart, Scanner sc) {
        System.out.println(
            restart ? 
                "\nThanks for choosing to play again! \nPlease enter how many rows you'd like!" 
                : 
                "\nThanks for choosing to play! \nPlease enter how many rows you'd like!"
        );
        
        
        System.out.print(">> ");
        int rows = sc.nextInt();
        sc.nextLine();
        System.out.println("\nPlease enter how many columns you'd like!");
        System.out.print(">> ");
        int cols = sc.nextInt();
        sc.nextLine();
        System.out.println("\nPlease enter how many bombs you'd like!");
        System.out.print(">> ");
        int bombs = sc.nextInt();
        sc.nextLine();
        return new Board(rows, cols, bombs);
    }

    private static void reveal(Board game, Scanner sc) {
        if (game != null) {
            System.out.println("Enter which row the square is in.");
            System.out.print(">> ");
            int row = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter which column the square is in.");
            System.out.print(">> ");
            int col = sc.nextInt();
            sc.nextLine();

            if (row < game.getRows() && col < game.getCols()) {
                game.move(row, col);
            } else {
                System.out.println("Not valid dumbass.");
            }
        }
    }

    private static void mark(Board game, Scanner sc) {
        if (game != null) {
            System.out.println("Enter which row the square is in.");
            System.out.print(">> ");
            int row = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter which column the square is in.");
            System.out.print(">> ");
            int col = sc.nextInt();
            sc.nextLine();

            if (row < game.getRows() && col < game.getCols()) {
                game.mark(row, col);
            } else {
                System.out.println("Not valid dumbass.");
            }
        }
    }

    private static void help() {
        System.out.println("'start [rows] [cols] [bombs]': starts a game with the specified number of rows, columns, and bombs.");
        System.out.println("'restart [rows] [cols] [bombs]': starts a new game with the specified number of rows, columns, and bombs.");
        System.out.println("'reveal [row] [col]': reveals a square at the specified row and column.");
        System.out.println("'mark [row] [col]': marks a square at a specified row and column as a bomb.");
        System.out.println("'exit': exits the program.");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        String[] tokens;
        Board game = null;

        boolean sentinel = true;

        System.out.println("Welcome to Minesweeper! \n\nTo get started, type \"start\"! \n\nFor more info, hit 'Enter' or type \"help\"!");
        
        while (sentinel) {
            System.out.print("\n>> ");
            input = sc.nextLine();
            tokens = input.split(" ");

            switch (tokens[0]) {
                case ("start") :
                    game = starter(false, sc);
                    break;
                case ("restart") :
                    game = starter(true, sc);
                    break;
                case ("reveal") :
                    reveal(game, sc);
                    break;
                case ("mark") :
                    mark(game, sc);
                    break;
                case ("exit") :
                    sentinel = false;
                    break;
                default :
                    help();
            }

            if (game != null) {
                System.out.println(game.toString());
            }

            if (game != null && game.getGameState() == GameState.IN_PROGRESS) {
                System.out.println("Type \"reveal\" if you wish to reveal a square.");
                System.out.print("Type \"mark\" if you wish to mark a square as a potential bomb.");
            } else if (game != null && game.getGameState() == GameState.LOST) {
                System.out.println("Sorry! Better luck next time!");
                System.out.println("Type \"restart\" if you wish to play again.");
                System.out.print("Type \"exit\" if you would like to stop playing.");
            } else if (game != null && game.getGameState() == GameState.WON) {
                System.out.println("Congratulations! You won!");
            }
        }

        sc.close();
    }
}
