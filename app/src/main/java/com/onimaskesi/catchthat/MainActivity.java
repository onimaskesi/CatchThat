package com.onimaskesi.catchthat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.view.KeyEventDispatcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    TextView ScoreView;
    int score = 0;
    int lastscore = 0;
    int bestscore = 0;
    TextView timeView;
    Random random = new Random();
    TextView lastScoreView;
    TextView bestScoreView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imageButton);
        ScoreView = findViewById(R.id.ScoreView);
        timeView = findViewById(R.id.timeView);
        imageButton.setEnabled(false);
        lastScoreView = findViewById(R.id.lastScoreView);
        bestScoreView = findViewById(R.id.bestScoreView);

        sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        bestscore = sharedPreferences.getInt("best",0);
        bestScoreView.setText("Best Score: " + bestscore);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void start(View view){
        imageButton.setEnabled(true);

        new CountDownTimer(10000, 500) {
            @Override
            public void onTick(long l) {
                timeView.setText("Time: "+l/1000);
                imageButton.setX(random.nextInt(500));//max750
                imageButton.setY(random.nextInt(500));

            }

            @Override
            public void onFinish() {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("GAME OVER");
                alertDialog.setMessage("Your score is " + score);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "AGAIN!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                if(bestscore < score){
                    bestscore = score;
                    bestScoreView.setText("Best Score: " + bestscore);
                    sharedPreferences.edit().putInt("best",bestscore).apply();
                }
                lastscore = score;
                lastScoreView.setText("Last Score: " + lastscore);
                score = 0;
                ScoreView.setText("Score: " + score);
                imageButton.setEnabled(false);
                timeView.setText("Time: 10");

            }
        }.start();
    }

    public void score(View view){
        score++;
        ScoreView.setText("Score: " + score);
    }
}
