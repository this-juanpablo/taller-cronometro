package com.app.cronometro;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView eTxtTime;
    private Button btnStart, btnStop, btnRound, btnRestart, btnAgain;
    private LinearLayout lyStop, lyAgain;
    private boolean isRunning = false;
    private int seconds = 0;
    private int secondsRound = 0;
    private Handler handler = new Handler();
    private RoundListAdapter adapter = new RoundListAdapter(this, new ArrayList<>());
    private ArrayList<ArrayList<String>> rounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        rounds();

        btnStart.setOnClickListener(click -> {
            isRunning = true;
            lyStop.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isRunning) {
                        seconds++;
                        int hours = seconds / 3600;
                        int minutes = (seconds % 3600) / 60;
                        int secs = seconds % 60;
                        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                        eTxtTime.setText(time);
                        secondsRound++;
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
                rounds = new ArrayList<>();
                adapter.update(rounds);
                lyAgain.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });

        btnRound.setOnClickListener(click -> {
            if (rounds.size() < 10) {
                ArrayList<String> round = new ArrayList<>();
                round.add(eTxtTime.getText().toString());

                int secondsRound = this.secondsRound;
                this.secondsRound = 0;
                int hours = secondsRound / 3600;
                int minutes = (secondsRound % 3600) / 60;
                int secs = secondsRound % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                round.add(time);

                rounds.add(round);
                adapter.update(rounds);
            } else {
                Toast.makeText(this, "Solo es posible 10 vueltas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rounds() {
        final RecyclerView rw = findViewById(R.id.chronometerRVVuelta);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rw.setLayoutManager(manager);

        rw.setAdapter(adapter);
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