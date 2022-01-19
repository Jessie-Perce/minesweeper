package model;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private static final int MAX_ROWS = 99;
    private static final int MAX_COLS = 99;
    private static final double MAX_BOMBS_RATIO = .5;

    private final Square[][] board;
    private final int rows;
    private final int cols;
    private final int squares;
    private int revealed;
    private final int bombs;
    private GameState gameState;

    public Minesweeper(int rows, int cols, int bombs) {
        this.rows = Math.min(rows, MAX_ROWS);
        this.cols = Math.min(cols, MAX_COLS);
        this.squares = this.rows * this.cols;
        this.revealed = 0;
        this.bombs = Math.min(bombs, (int) (this.squares * MAX_BOMBS_RATIO));
        this.gameState = GameState.IN_PROGRESS;

        this.board = new Square[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = new Square(0);
            }
        }

        init();
    }

    private void init() {
        Random rng = new Random();
        int bombsToBePlaced = bombs;

        while(bombsToBePlaced > 0) {
            int i = rng.nextInt(rows);
            int j = rng.nextInt(cols);

            if (board[i][j].getValue() >= 0) {
                board[i][j] = new Square(-1);
                bombsToBePlaced--;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int numAdjBombs = 0;

                if (board[i][j].getValue() >= 0) {
                    if (i + 1 < rows && j + 1 < cols && board[i+1][j+1].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (i + 1 < rows && board[i+1][j].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (i + 1 < rows && j - 1 >= 0 && board[i+1][j-1].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (j + 1 < cols && board[i][j+1].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (j - 1 >= 0 && board[i][j-1].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (i - 1 >= 0 && j + 1 < cols && board[i-1][j+1].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (i - 1 >= 0 && board[i-1][j].getValue() < 0) {
                        numAdjBombs++;
                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && board[i-1][j-1].getValue() < 0) {
                        numAdjBombs++;
                    }

                    board[i][j] = new Square(numAdjBombs);
                }
            }
        }
    }

    public void reveal(int i, int j) {
        board[i][j].reveal();

        if (board[i][j].getValue() == 0) {
            if (i + 1 < rows && j + 1 < cols) {
                if (!board[i + 1][j + 1].getVisibility()) {
                    reveal(i + 1, j + 1);
                }
            }
            if (i + 1 < rows) {
                if (!board[i + 1][j].getVisibility()) {
                    reveal(i + 1, j);
                }
            }
            if (i + 1 < rows && j - 1 >= 0) {
                if (!board[i + 1][j - 1].getVisibility()) {
                    reveal(i + 1, j - 1);
                }
            }
            if (j + 1 < cols) {
                if (!board[i][j + 1].getVisibility()) {
                    reveal(i, j + 1);
                }
            }
            if (j - 1 >= 0) {
                if (!board[i][j - 1].getVisibility()) {
                    reveal(i, j - 1);
                }
            }
            if (i - 1 >= 0 && j + 1 < cols) {
                if (!board[i - 1][j + 1].getVisibility()) {
                    reveal(i - 1, j + 1);
                }
            }
            if (i - 1 >= 0) {
                if (!board[i - 1][j].getVisibility()) {
                    reveal(i - 1, j);
                }
            }
            if (i - 1 >= 0 && j - 1 >= 0) {
                if (!board[i - 1][j - 1].getVisibility()) {
                    reveal(i - 1, j - 1);
                }
            }
            revealed++;
        } else if (board[i][j].getValue() < 0) {
            gameState = GameState.LOST;
        } else {
            revealed++;
        }

        if (revealed == squares - bombs && gameState != GameState.LOST) {
            gameState = GameState.WON;
        }
    }

    public void mark(int i, int j) {
        board[i][j].mark();
    }

    public int getMaxRows() {
        return this.rows;
    }

    public int getMaxCols() {
        return this.cols;
    }

    public String getBoardString() {
        StringBuilder boardString = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardString.append("[").append(board[i][j].toString()).append("] ");
            }
            boardString.append("\n");
        }

        return boardString.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        String[] tokens;
        Minesweeper game = null;

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
                        game = new Minesweeper(rows, cols, bombs);
                    } else {
                        System.out.println("Invalid game: hit enter for help.");
                    }
                    break;
                case ("restart") :
                    if (tokens.length > 3) {
                        int rows = Integer.parseInt(tokens[1]);
                        int cols = Integer.parseInt(tokens[2]);
                        int bombs = Integer.parseInt(tokens[3]);
                        game = new Minesweeper(rows, cols, bombs);
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

            if (game != null && game.gameState == GameState.LOST) {
                System.out.println("Sorry! Better luck next time!");
            } else if (game != null && game.gameState == GameState.WON) {
                System.out.println("Congratulations! You won!");
            }
        }

        sc.close();
    }
}