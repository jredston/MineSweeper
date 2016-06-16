package com.android.minesweeper;

import android.widget.ImageView;

/**
 * Created by juliaredston on 1/4/16.
 */
public class Tile {

    private Position myPos;
    private boolean mine;
    private ImageView myImage;
    private boolean isClicked = false;

    public Tile(Position p) {
        myPos = p;
        mine = false;

    }

    public void click(){
        isClicked = true;
    }
    public boolean getClicked(){
        return isClicked;
    }

    public Position getMyPos() {
        return myPos;
    }

    public ImageView getMyImage(){
        return myImage;
    }

    public void setMyImage(ImageView myImage){
         this.myImage = myImage;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
