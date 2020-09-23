package com.onimaskesi.catchthat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.KeyEventDispatcher;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.onimaskesi.catchthat.R.drawable.start_button;

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
    Dialog finish_popup;

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

        finish_popup = new Dialog(this);

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

                TextView txtScore;
                TextView txtClose;

                finish_popup.setContentView(R.layout.finish_popup);
                txtScore = finish_popup.findViewById(R.id.txtScore);
                txtClose = finish_popup.findViewById(R.id.txtclose);
                txtScore.setText(""+score);

                txtClose.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        finish_popup.dismiss();
                    }
                });
                finish_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                finish_popup.show();

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
