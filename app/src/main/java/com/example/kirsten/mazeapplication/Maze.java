package com.example.kirsten.mazeapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Maze extends AppCompatActivity implements View.OnClickListener {

    MazeView maze;
    Button shuffleBtn;
    Button solveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.maze_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1100);

        maze = new MazeView(this);
        maze.setLayoutParams(params);
        maze.setForegroundGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        rl.addView(maze);

        shuffleBtn = (Button) findViewById(R.id.bShuffle);
        shuffleBtn.setOnClickListener(this);
        solveBtn = (Button) findViewById(R.id.bSolve);
        solveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bShuffle:
                maze.generateNewMaze();
                solveBtn.setEnabled(true);
                break;

            case R.id.bSolve:
                maze.solveMaze();
                solveBtn.setEnabled(false);
                break;
        }
    }
}
