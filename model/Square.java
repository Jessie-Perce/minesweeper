package model;

public class Square {
    private boolean visible;
    private boolean marked;
    private int value;

    public Square(int value) {
        this.visible = false;
        this.marked = false;
        this.value = value;
    }
    
    public void reveal() {
        this.visible = true;
    }

    public void mark() {
        this.marked = !marked;
    }

    public int getValue() {
        return value;
    }
    
    public boolean getVisibility() {
        return visible;
    }

    public String toString() {
        if (visible) {
            return value >= 0 ? Integer.toString(value) : "X";
        } else if (marked) {
            return "•";
        } else {
            return " ";
        }
    }
}
