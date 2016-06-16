package com.android.minesweeper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.DisplayMetrics;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private Board board;
    final int boardSize = 7; // board is boardSize by boardSize, area of boardSize squared
    final int numMines =5;
    boolean flagging = false;
    private int tilesToClick = boardSize*boardSize - numMines;

    final String[] items = new String[boardSize*boardSize];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridview = (GridView) findViewById(R.id.gridview);

        startGame(gridview);




        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                if (flagging) {
                    if(items[arg2].equals("flag")){
                        items[arg2]="   ";
                    } else {
                        items[arg2] = "flag";
                    }
                } else {
                    int mines = clickedTile(new Position(arg2 % boardSize, arg2 / boardSize));
                    if (mines == -1) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("GAME OVER")
                                .setMessage("Game Over, play again?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startGame(gridview);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        System.exit(0);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                    if(mines != -2) {
                        items[arg2] = " " + mines + " ";
                        tilesToClick--;
                        if(items[arg2].equals(" 0 ")){
                            tilesToClick++;
                        }
                        if (tilesToClick < 1) {
                            winGame(gridview);
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, items);
                gridview.setAdapter(adapter);


            }
        });






    }

    public void winGame(final GridView gridview){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("GAME OVER")
                .setMessage("You won! Play again?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(gridview);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void startGame(GridView gridview) {
        board = new Board(boardSize, numMines);
        tilesToClick = boardSize*boardSize - numMines;


        for(int i=0; i<items.length; i++){
            items[i] = "   ";
        }

        ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(),R.layout.mytextview,items);
        //gridview.setBackgroundColor(Color.GRAY);
        gridview.setNumColumns(boardSize);
        gridview.setGravity(Gravity.CENTER);
        gridview.setAdapter(ad);
    }

    private int clickedTile(Position p){
        Log.v("click","clicked tile "+p.getColumn() +", "+p.getRow());
        Tile t = board.getTileAtPostion(p);
        if(t.isMine()){
            Log.v("mines", "t is a mine");
            //game over
            return -1;
        } else {

            if(!t.getClicked()) {

                return mineCount(p);
            }
            return -2;

        }
    }

    public int mineCount(Position p){
        int mineCount =0;
        Log.v("mines","looking at "+p.getColumn()+", "+p.getRow());
        for( int y =-1; y<2; y++){
            for(int x=-1; x<2; x++){

                if(p.getColumn()+y>=0 && p.getColumn()+y<boardSize &&
                        p.getRow()+x>=0 && p.getRow()+x<boardSize && !(y==0 && x==0)){

                    if(board.getTileAtPostion(new Position(p.getColumn() +y, p.getRow()+x)).isMine()) {
                        mineCount++;

                        Log.v("mines", "minecount not zero, should not run look zeroes");
                    }
                }
            }


        }
        if(mineCount == 0){
            Log.v("zeroes","running look Zeroes");
            items[p.getRow()*boardSize+p.getColumn()] = " 0 ";
            tilesToClick--;

            lookZeroes(p);

        }
        return mineCount;
    }

    public void lookZeroes(Position p){
        int mines =0;
        int[] xs = {-1,0,0,1};
        int[] ys = {0,-1,1,0};
        for( int q =0; q<4; q++){
            int x = xs[q];
            int y = ys[q];

            if(p.getColumn()+y>=0 && p.getColumn()+y<boardSize &&
                    p.getRow()+x>=0 && p.getRow()+x<boardSize  && !board.getTileAtPostion(new Position(p.getColumn() +y, p.getRow()+x)).isMine() && !items[(p.getRow()+x)*boardSize+p.getColumn()+y].equals( " 0 ")){
                mineCount(new Position(p.getColumn()+y, p.getRow()+x));


            }



        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View view){
        Button button = (Button) findViewById(R.id.button1);
        flagging = !flagging;
        if(flagging){
            button.setText("Switch to clicking");
        } else {
            button.setText("switch to flagging");
        }

    }



}
