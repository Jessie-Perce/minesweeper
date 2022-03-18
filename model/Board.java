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

        // sets GameState to not started
        this.gameState = GameState.NOT_STARTED;

        // makes the board data structure
        this.board = new Square[rows][cols];

        // fills data structure with empty squares, some of which will be replaced later
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = new Square(0);
            }
        }
    }

    /**
     * Fills the board with bombs, updates other values
     * and sets GameState to in progress, working around the first square revealed.
     * @param i the y-coord of the first move
     * @param j the x-coord of the first move
     */
    private void init(int i, int j) {
        // TODO: implement init that works around starting location.
    }

    /**
     * Reveals a square, and if it was empty, reveals its neighbors.
     * Calls init upon submission of the first move.
     * @param i the y-coord of the first move
     * @param j the x-coord of the first move
     */
    public void move(int i, int j) {
        // TODO: implement reveal that works calls init and does normal
        // reveal stuff
    }

    /**
     * Calls mark on a specific square, whether that square becomes
     * marked or not is up to the square to decide.
     * @param i y-coord of the square
     * @param j x-coord of the square
     */
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

    @Override
    public String toString() {
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