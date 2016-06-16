package com.android.minesweeper;

/**
 * Created by juliaredston on 1/4/16.
 */
public class Position {
    private int column;
    private int row;

    public Position(int x, int y) {
        column = x;
        row = y;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Position)) { return false;}
        else {
            Position p = (Position) o;
            return(p.getColumn() == this.column && p.getRow() == this.row);

        }
    }
}
