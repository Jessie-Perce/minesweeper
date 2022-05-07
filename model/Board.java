package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import controller.Observer;

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
     * Collection of all observers
     */
    private List<Observer> observers;

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

        observers = new LinkedList<>();
    }

    /**
     * Fills the board with bombs, updates other values
     * and sets GameState to in progress, working around the first square revealed.
     * @param startRow the y-coord of the first move
     * @param startCol the x-coord of the first move
     */
    private void init(int startRow, int startCol) {
        Random rng = new Random();
        int bombsLeft = bombs;

        Set<Integer> badRows = new HashSet<>();
        badRows.add(startRow - 1);
        badRows.add(startRow);
        badRows.add(startRow + 1);

        Set<Integer> badCols = new HashSet<>();
        badCols.add(startRow - 1);
        badCols.add(startRow);
        badCols.add(startRow + 1);

        while (bombsLeft > 0) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if ((!badRows.contains(i) || !badRows.contains(i)) && bombsLeft > 0 && board[i][j].getValue() == 0 && rng.nextInt(0, squares) <= (int)(bombs / 2)) {
                        board[i][j]= new Square(-1);
                        bombsLeft--;
                    }
                }
            }
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j].getValue() == 0) {
                    board[i][j] = new Square(getAdjacencies(i, j));
                }
            }
        }
    }

    /**
     * Reveals a square, and if it was empty, reveals its neighbors.
     * Calls init upon submission of the first move.
     * @param row the y-coord of the first move
     * @param col the x-coord of the first move
     */
    public void move(int row, int col) {
        if (gameState == GameState.NOT_STARTED && !board[row][col].getMarked()) {
            init(row, col);
            gameState = GameState.IN_PROGRESS;
        }
        
        if (!board[row][col].getVisibility() && !board[row][col].getMarked()) {
            board[row][col].reveal();

            if (board[row][col].getValue() == 0) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if ((row + i) >= 0 && (row + i) < rows && (col + j) >= 0 && (col + j) < cols) {
                            move(row + i, col + j);
                        }
                    }
                }
            } else if (board[row][col].getValue() < 0) {
                gameState = GameState.LOST;
            }

            gameState = (revealed - squares) == bombs && gameState != GameState.LOST ? GameState.WON : gameState;
        }
        notifyObservers();
    }

    /**
     * Calls mark on a specific square, whether that square becomes
     * marked or not is up to the square to decide.
     * @param row y-coord of the square
     * @param col x-coord of the square
     */
    public void mark(int row, int col) {
        board[row][col].mark();
        notifyObservers();
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        observers.forEach(ob -> ob.handle());
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public int getbombs() {
        return this.bombs;
    }

    public int getRevealed() {
        return revealed;
    }

    public Square getSquare(int row, int col) {
        return board[row][col];
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

    private int getAdjacencies(int row, int col) {
        int adjacencies = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((row + i) >= 0 && (row + i) < rows && (col + j) >= 0 && (col + j) < cols) {
                    int curValue = board[row + i][col + j].getValue();
                    adjacencies += curValue < 0 ? 1 : 0;
                }
            }
        }

        return adjacencies;
    }
}