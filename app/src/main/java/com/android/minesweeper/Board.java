package com.android.minesweeper;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by juliaredston on 1/4/16.
 * this class creates a "board" for our game, made up of tiles
 * populates with mines
 */
public class Board {
    final int boardSize;
    Tile[][] board ;
    int mines = 5;

    public Board(){
        this(7, 5);
    }
    public Board(int b) {

    this(b, 5);
    }

    public Board(int b, int m){
        boardSize = b;
        mines = m;
        board = new Tile[boardSize][boardSize];
        for(int i =0; i < board.length; i++) {
            for(int z = 0; z < board[0].length; z++) {
                board[i][z] = new Tile(new Position(i,z));
            }
        }
        placeMines(mines);

    }


    /**
     *
     *
     * @param num number of mines to be placed
     */
    private void placeMines(int num) {
        ArrayList<Integer> rows = new ArrayList<Integer>();
        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (int i=0; i<board.length; i++) {
            rows.add(new Integer(i));
        }
        for (int i=0; i<board.length; i++) {
            cols.add(new Integer(i));
        }
        Collections.shuffle(rows);
        Collections.shuffle(cols);
        for (int i=0; i<num; i++) {
            getTileAtPostion(new Position(cols.get(i), rows.get(i))).setMine(true);
            Log.v("mines", "placing mine at " + cols.get(i) + ", " + rows.get(i));
        }

    }

    public Tile getTileAtPostion(Position p) {
        return board[p.getColumn()][p.getRow()];
    }
}
