package com.app.cronometro;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView eTxtTime;
    private Button btnStart, btnStop, btnRound, btnRestart, btnAgain;
    private LinearLayout lyStop, lyAgain;
    private boolean isRunning = false;
    private int seconds = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnStart.setOnClickListener(click -> {
            isRunning = true;
            lyStop.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isRunning) {
                        int hours = seconds / 3600;
                        int minutes = (seconds % 3600) / 60;
                        int secs = seconds % 60;
                        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                        eTxtTime.setText(time);
                        seconds++;
                    }

                    handler.postDelayed(this, 1000);
                }
            });
        });

        btnStop.setOnClickListener(click -> {
            if (isRunning) {
                isRunning = false;
                lyStop.setVisibility(View.GONE);
                lyAgain.setVisibility(View.VISIBLE);
            }
        });

        btnRestart.setOnClickListener(click -> {
            if (!isRunning) {
                isRunning = true;
                lyStop.setVisibility(View.VISIBLE);
                lyAgain.setVisibility(View.GONE);
            }
        });

        btnAgain.setOnClickListener(click -> {
            if (!isRunning) {
                handler.removeCallbacksAndMessages(null);
                eTxtTime.setText("0:00:00");
                seconds = 0;
                isRunning = true;
                lyAgain.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {
        eTxtTime = findViewById(R.id.chronometerTime);
        btnStart = findViewById(R.id.chronometerBtnStart);
        btnStop = findViewById(R.id.chronometerBtnStop);
        btnRound = findViewById(R.id.chronometerBtnRound);
        btnRestart = findViewById(R.id.chronometerBtnRestart);
        btnAgain = findViewById(R.id.chronometerBtnAgain);
        lyStop = findViewById(R.id.chronometerLayoutStop);
        lyAgain = findViewById(R.id.chronometerLayoutAgain);
    }
}