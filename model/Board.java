package model;

import java.util.Random;

/**
 * A class representing a Minesweeper board.
 * @author Jessie Perce
 */
public class Board {
    /**
     * Max number of rows allowed by the program.
     */
    private static final int MAX_ROWS = 99;

    /**
     * Max number of columns allowed by the program.
     */
    private static final int MAX_COLS = 99;

    /**
     * Max ratio of bombs allowed by the program.
     */
    private static final double MAX_BOMBS_RATIO = .25;


    /**
     * Data structure to contain the board info in the form of 
     * Square class instances.
     */
    private final Square[][] board;

    /**
     * Number of rows in this board.
     */
    private final int rows;

    /**
     * Number of columns in this board.
     */
    private final int cols;

    /**
     * Number of squares in this board.
     */
    private final int squares;

    /**
     * Number of revealed squares in this board.
     */
    private int revealed;

    /**
     * Number of bombs in this board.
     */
    private final int bombs;

    /**
     * The current state of the game, represented by the GameState enum.
     */
    private GameState gameState;

    /**
     * Constructor that creates a GameBoard.
     * @param rows number of rows desired by the user.
     * @param cols number of cols desired by the user.
     * @param bombs number of bombs desired by the user.
     */
    public Board(int rows, int cols, int bombs) {
        // sets the number of rows and columns to be either the 
        // desired number of rows and columns, or the max allowed 
        // number of rows and columns. Whichever is smaller
        this.rows = Math.min(rows, MAX_ROWS);
        this.cols = Math.min(cols, MAX_COLS);

        // sets the number of squares in the game by multiplying
        // the newly determined number of rows and columns
        this.squares = this.rows * this.cols;

        // sets the number of revealed bombs to zero to start the game
        this.revealed = 0;

        // determines the number of bombs that will be placed, either the desired
        // number or the number determined by the max bombs ratio times the number
        // of squares
        this.bombs = Math.min(bombs, (int)(this.squares * MAX_BOMBS_RATIO));

        // sets GameState to in progress
        this.gameState = GameState.IN_PROGRESS;

        // makes the board data structure
        this.board = new Square[rows][cols];

        // fills data structure with empty squares, some of which will be replaced later
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = new Square(0);
            }
        }
    }

    private void init() {
        // TODO: implement init that works around starting location.
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

    public GameState getGameState() {
        return this.gameState;
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
}