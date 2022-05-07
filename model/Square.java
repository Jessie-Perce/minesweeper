package model;

/**
 * A class representing a single square on
 * a minesweeper board.
 * @author Jessie Perce
 */
public class Square {
    /**
     * A boolean that tracks whether the user 
     * has revealed this square's value.
     */
    private boolean visible;

    /**
     * A boolean that tracks whether the user
     * has marked this square as a bomb.
     * NOTE: Marked bombs may not be revealed.
     */
    private boolean marked;

    /**
     * This square's hidden value, only accessible
     * when revealed.
     */
    private int value;

    /**
     * Constructs a new square for a board.
     * @param value the value intended for the square.
     */
    public Square(int value) {
        // sets visible and marked to false.
        this.visible = false;
        this.marked = false;

        // sets value as intended by user.
        this.value = value;
    }
    
    /**
     * Makes the square visible, allowing access to its
     * value.
     * NOTE: Can only reveal unmarked and unrevealed squares
     */
    public void reveal() {
        this.visible = !marked && !visible ? true : visible;
    }

    /**
     * Toggles the marked state between true and false.
     * NOTE: Can only mark unrevealed squares.
     */
    public void mark() {
        this.marked = !visible ? !marked : marked;
    }

    /**
     * Returns this square's value.
     * NOTE: Only returns value if the square is visible.
     * @return
     */
    public int getValue() {
        return value;
    }
    
    public boolean getVisibility() {
        return visible;
    }

    public boolean getMarked() {
        return this.marked;
    }

    public String toString() {
        if (visible) {
            return value >= 0 ? Integer.toString(value) : "X";
        } else if (marked) {
            return "*";
        } else {
            return " ";
        }
    }
}
