package com.example.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    // false: red & true: yellow
    private boolean colorTrack = false;
    private boolean playerWon = false;
    private String winnerColor = "";

    // 0: empty, 1: red occupied space & 2: yellow occupied space
    private int [] spaceTracker = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int [][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private ArrayList<ImageView> chips = new ArrayList<ImageView>();

    public void dropIn (View view)
    {
        if ((spaceTracker[Integer.parseInt(view.getTag().toString())] < 1) && (!playerWon))
        {
            ImageView chip = (ImageView) view;
            chips.add(chip);
            chip.setTranslationY(-1500);
            if (!colorTrack) { chip.setImageResource(R.drawable.red); spaceTracker[Integer.parseInt(view.getTag().toString())] = 1; }
            else { chip.setImageResource(R.drawable.yellow); spaceTracker[Integer.parseInt(view.getTag().toString())] = 2; }
            colorTrack = !colorTrack;
            chip.animate().translationYBy(1500).rotation(3600).setDuration(300);
            for (int[] winningPosition : winningPositions)
            {
                if ((spaceTracker[winningPosition[0]] == spaceTracker[winningPosition[1]] && spaceTracker[winningPosition[1]] == spaceTracker[winningPosition[2]]) && (spaceTracker[winningPosition[1]] != 0))
                {
                    playerWon = true;
                    winnerColor = "";
                    if (colorTrack) winnerColor = "Red";
                    else winnerColor = "Yellow";
                    break;
                }
            }
            if (playerWon)
            {
                TextView winner = findViewById(R.id.winnerText);
                winner.setText(winnerColor + " wins!");
                winner.setVisibility(View.VISIBLE);
                Button playAgain = findViewById(R.id.playAgainButton);
                playAgain.setVisibility(View.VISIBLE);
            }
        }
    }

    public void restartGame(View view)
    {
        colorTrack = playerWon = false; winnerColor = "";
        for (int i = 0; i < 9; i++) { spaceTracker[i] = 0; }
        for (int i = 0; i < chips.size(); i++) { ImageView tempChip = chips.get(i); tempChip.setImageDrawable(null); }
        TextView winner = findViewById(R.id.winnerText);
        winner.setVisibility(View.INVISIBLE);
        Button playAgain = findViewById(R.id.playAgainButton);
        playAgain.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}