package model;

import java.util.Random;

public class Board {
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

    public Board(int rows, int cols, int bombs) {
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