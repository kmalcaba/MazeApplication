package com.example.kirsten.mazeapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Kirsten on 2018-03-04.
 */

public class MazeGenerator {

    private Cell[][] grid;
    private Cell[][] parent;

    private int height;
    private int width;

    public static int startX;
    public static int startY;
    public static int stopX;
    public static int stopY;

    private List<Integer> directions;
    private ArrayList<Cell> finalPath;

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    public MazeGenerator() {
        grid = null;
        parent = null;

        finalPath = new ArrayList<>();

        height = 0;
        width = 0;

        startX = 0;
        startY = 0;
        stopX = 0;
        stopY = 0;
    }

    private void init(int height, int width) {
        directions = Arrays.asList(TOP, RIGHT, BOTTOM, LEFT);

        if ((height != this.height) || (width != this.width)) {
            grid = new Cell[height][width];
            parent = new Cell[height][width];
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == null)
                    grid[i][j] = new Cell(i, j);

                grid[i][j].init();

                if (parent[i][j] == null)
                    parent[i][j] = new Cell(i, j);

                parent[i][j].init();
            }
        }

        this.height = height;
        this.width = width;
        stopX = height - 2;
        stopY = width - 1;
    }

    private Integer[] generateDirection() {
        Collections.shuffle(directions);

        return directions.toArray(new Integer[4]);
    }

    private void setToPath(Cell cell) {
        cell.setPath(0);
    }

    //returns a random integer to decide if the maze will overlap
    //with a slight bias to overlap
    private int overlapPath() {
        Random rand = new Random();
        int number;
        int trial = 3;

        do {
            number = rand.nextInt(5);
            if (number > 3)
                trial--;
            else
                trial = 0;
        } while (trial > 0);

        return number;
    }

    //method to generate a maze using DFS traversal
    private void generateMaze(Cell cell) {
        //randomizes directions
        Integer[] directions = generateDirection();

        //examine each direction
        for (Integer direction : directions) {
            switch (direction) {
                case TOP:
                    if (cell.getY() - 2 <= 0) //out of bounds
                        continue;

                    //if next two cells are walls (1)
                    //change them to a path
                    if (grid[cell.getX()][cell.getY() - 2].isWall() == 1) {
                        setToPath(grid[cell.getX()][cell.getY() - 1]);
                        setToPath(grid[cell.getX()][cell.getY() - 2]);
                        generateMaze(grid[cell.getX()][cell.getY() - 2]);
                    } else {
                        //decides if the path will overlap
                        int overlap = overlapPath();
                        if (overlap > 3)
                            setToPath(grid[cell.getX()][cell.getY() - 1]);

                        setEndOfMaze(grid[cell.getX()][cell.getY() - 2]);
                    }
                    break;

                case RIGHT:
                    if (cell.getX() + 2 >= width - 1) //out of bounds
                        continue;

                    //if next two cells are walls (1)
                    //change them to a path
                    if (grid[cell.getX() + 2][cell.getY()].isWall() == 1) {
                        setToPath(grid[cell.getX() + 1][cell.getY()]);
                        setToPath(grid[cell.getX() + 2][cell.getY()]);
                        generateMaze(grid[cell.getX() + 2][cell.getY()]);
                    } else {
                        //decides if the path will overlap
                        int overlap = overlapPath();
                        if (overlap > 3)
                            setToPath(grid[cell.getX() + 1][cell.getY()]);

                        setEndOfMaze(grid[cell.getX() + 2][cell.getY()]);
                    }
                    break;

                case BOTTOM:
                    if (cell.getY() + 2 >= height - 1) //out of bounds
                        continue;

                    //if next two cells are walls (1)
                    //change them to a path
                    if (grid[cell.getX()][cell.getY() + 2].isWall() == 1) {
                        setToPath(grid[cell.getX()][cell.getY() + 1]);
                        setToPath(grid[cell.getX()][cell.getY()  + 2]);
                        generateMaze(grid[cell.getX()][cell.getY()  + 2]);
                    } else {
                        //decides if the path will overlap
                        int overlap = overlapPath();
                        if (overlap > 3)
                            setToPath(grid[cell.getX()][cell.getY() + 1]);

                        setEndOfMaze(grid[cell.getX()][cell.getY() + 2]);
                    }
                    break;

                case LEFT:
                    if (cell.getX() - 2 <= 0) //out of bounds
                        continue;

                    //if next two cells are walls (1)
                    //change them to a path
                    if (grid[cell.getX() - 2][cell.getY()].isWall() == 1) {
                        setToPath(grid[cell.getX() - 1][cell.getY()]);
                        setToPath(grid[cell.getX() - 2][cell.getY()]);
                        generateMaze(grid[cell.getX() - 2][cell.getY()]);
                    } else {
                        //decides if the path will overlap
                        int overlap = overlapPath();
                        if (overlap > 3)
                            setToPath(grid[cell.getX() - 1][cell.getY()]);

                        setEndOfMaze(grid[cell.getX() - 2][cell.getY()]);
                    }
                    break;
            }
        }
    }

    private void setEndOfMaze(Cell cell) {
        if ((cell.getX() == stopX) && (cell.getY() == stopY - 1)) {
            if (cell.isWall() == 0) {
                setToPath(cell);
                setToPath(grid[cell.getX()][cell.getY() + 1]);
            }
        }
    }

    public Cell[][] generate(int height, int width) {
        init(height, width);

        Random rand = new Random();
        int[] coords = {1, 3};

        int size = rand.nextInt(coords.length);
        startX = coords[size];

        setToPath(grid[startX][startY]);
        setToPath(grid[startX][startY + 1]);

        generateMaze(grid[startX][startY + 1]);

        return grid;
    }

    //returns a Cell according to the given direction
    private Cell getNextCell(Cell cell, int direction) {
        switch(direction) {
            case TOP:
                if((cell.getX() - 1) >= 0)
                    return grid[cell.getX() - 1][cell.getY()];
                break;
            case RIGHT:
                if((cell.getY() + 1) < width)
                    return grid[cell.getX()][cell.getY() + 1];
                break;
            case BOTTOM:
                if((cell.getX() + 1) < height)
                    return  grid[cell.getX() + 1][cell.getY()];
                break;
            case LEFT:
                if((cell.getY() - 1) >= 0)
                    return grid[cell.getX()][cell.getY() - 1];
                break;

            default:
                return null;
        }

        return null;
    }

    public void solve() {
        findPath(grid[startX][startY], grid[stopX][stopY]);
    }

    //method to find a path using BFS traversal
    private void findPath(Cell startCell, Cell stopCell) {
        Queue<Cell> cellQueue = new LinkedList<>();
        Queue<Cell> path = new LinkedList<>();
        boolean isFound = false;

        Cell cell;
        cellQueue.add(startCell);

        OUTER:
        while (!cellQueue.isEmpty()) {
            cell = cellQueue.poll();
            Cell nextCell;

            for (Integer direction : directions) {
                nextCell = getNextCell(cell, direction);

                //the next cell should not be null, a wall, or be already visited
                if (nextCell != null && nextCell.isWall() == 0 && !nextCell.isVisited()) {
                    cellQueue.add(nextCell);
                    cell.setVisited(true);
                    nextCell.setVisited(true);
                    parent[nextCell.getX()][nextCell.getY()] = cell;

                    if (nextCell == stopCell) {
                        isFound = true;
                        break OUTER;
                    }
                }
            }
        }

        if (isFound) {
            cell = stopCell;
            while (cell != startCell) {
                path.add(cell);
                cell = parent[cell.getX()][cell.getY()];
            }
            path.add(cell);
            finalPath = reversePath(path);

            for (Cell c : finalPath) {
                c.setFinalPath(true);
                c.setVisited(false);
            }
        }
    }

    private ArrayList<Cell> reversePath(Queue<Cell> q) {
        Stack<Cell> s = new Stack<>();
        ArrayList<Cell> a = new ArrayList<>();

        while (!q.isEmpty())
            s.push(q.poll());

        while (!s.isEmpty())
            a.add(s.pop());

        return a;
    }

}
