package Rubiks_Cube;

public enum Sides {
    TOP (0), FORWARD (1), RIGHT (2), BACKWARD (3), LEFT (4), BOTTOM (5);
    private int index;
    Sides(int index) {
        this.index = index;
    }
    public int getIndex() { return index; }
}
