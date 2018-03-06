package com.example.kirsten.mazeapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Kirsten on 2018-03-04.
 */

public class MazeView extends View {

    private MazeGenerator maze;
    private Cell[][] cell;

    int height, width;
    private final static int TILE_SIZE = 32;

    private Bitmap imgPath;
    private Bitmap imgSolvedPath;
    private Bitmap imgTraversedPath;
    private Bitmap imgStartTile;
    private Bitmap imgExitTile;

    public MazeView(Context context) {
        super(context);
        setForegroundGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        maze = new MazeGenerator();
        height = 33;
        width = 33;
        cell = maze.generate(height, width);

        imgPath = BitmapFactory.decodeResource(context.getResources(), R.drawable.path);
        imgSolvedPath = BitmapFactory.decodeResource(context.getResources(), R.drawable.solved_path);
        imgTraversedPath = BitmapFactory.decodeResource(context.getResources(), R.drawable.traversed_path);
        imgStartTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_tile);
        imgExitTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.exit_tile);
    }

    public void generateNewMaze() {
        cell = maze.generate(height, width);
        invalidate();
    }

    public void solveMaze() {
        maze.solve();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int mX = x * TILE_SIZE;
                int mY = y * TILE_SIZE;

                if (cell[x][y].isWall() == 0) {
                    //if starting tile
                    if (x == MazeGenerator.startX && y == MazeGenerator.startY)
                        canvas.drawBitmap(imgStartTile, mX, mY, paint);
                    //if exit tile
                    else if (x == MazeGenerator.stopX && y == MazeGenerator.stopY)
                        canvas.drawBitmap(imgExitTile, mX, mY, paint);
                    //if part of the final path
                    else if (cell[x][y].isFinalPath())
                        canvas.drawBitmap(imgSolvedPath, mX, mY, paint);
                    //if tile has been traversed
                    else if(cell[x][y].isVisited())
                        canvas.drawBitmap(imgTraversedPath, mX, mY, paint);
                    else {
                        canvas.drawBitmap(imgPath, mX, mY, paint);
                    }
                }


            }
        }
    }

}
