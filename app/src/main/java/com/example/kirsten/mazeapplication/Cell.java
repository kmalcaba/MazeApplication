package com.example.kirsten.mazeapplication;

/**
 * Created by Kirsten on 2018-03-04.
 */

public class Cell {

    private int isWall;
    private int x, y;
    private boolean visited;
    private boolean finalPath;

    public Cell(int x, int y) {

        this.x = x;
        this.y = y;

        init();
    }

    void init() {
        //default value is 1 (wall)
        this.isWall = 1;
        this.visited = false;
        this.finalPath = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isFinalPath() {
        return finalPath;
    }

    public void setFinalPath(boolean finalPath) {
        this.finalPath = finalPath;
    }

    public void setPath(int isWall) {
        this.isWall = isWall;
    }

    public int isWall() {
        return isWall;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
